#include <android/log.h>
#include <dlfcn.h>
#include <signal.h>
#include <stdint.h>
#include <unwind.h>
#include <unistd.h>
#include "Dobby/dobby.h"
#include "xdl.h"

#define LOG_TAG "ProcessExitMonitor"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

static void (*orig_abort)() = nullptr;

static void (*orig_exit)(int status) = nullptr;

static void (*orig__exit)(int status) = nullptr;

static int (*orig_raise)(int sig) = nullptr;

static int (*orig_kill)(pid_t pid, int sig) = nullptr;

static int (*orig_tgkill)(int tgid, int tid, int sig) = nullptr;

struct BacktraceState {
    uintptr_t *frames;
    size_t capacity;
    size_t count;
};

static _Unwind_Reason_Code unwind_callback(_Unwind_Context *context, void *arg) {
    BacktraceState *state = static_cast<BacktraceState *>(arg);
    uintptr_t pc = _Unwind_GetIP(context);
    if (pc != 0 && state->count < state->capacity) {
        state->frames[state->count++] = pc;
    }
    return state->count >= state->capacity ? _URC_END_OF_STACK : _URC_NO_REASON;
}

static void dump_backtrace(const char *reason) {
    uintptr_t frames[32];
    BacktraceState state = {frames, 32, 0};
    _Unwind_Backtrace(unwind_callback, &state);

    LOGE("native backtrace for %s, frames=%zu", reason, state.count);
    for (size_t i = 0; i < state.count; ++i) {
        Dl_info info;
        if (dladdr(reinterpret_cast<void *>(frames[i]), &info) != 0 && info.dli_fname != nullptr) {
            uintptr_t base = reinterpret_cast<uintptr_t>(info.dli_fbase);
            const char *symbol = info.dli_sname == nullptr ? "<unknown>" : info.dli_sname;
            LOGE("  #%02zu pc %p %s +0x%zx (%s)", i,
                 reinterpret_cast<void *>(frames[i]),
                 info.dli_fname,
                 static_cast<size_t>(frames[i] - base),
                 symbol);
        } else {
            LOGE("  #%02zu pc %p <unknown>", i, reinterpret_cast<void *>(frames[i]));
        }
    }
}

static void log_signal_call(const char *name, int sig) {
    LOGE("%s called: pid=%d, tid=%d, signal=%d", name, getpid(), gettid(), sig);
    dump_backtrace(name);
}

static void log_exit_call(const char *name, int status) {
    LOGE("%s called: pid=%d, tid=%d, status=%d", name, getpid(), gettid(), status);
    dump_backtrace(name);
}

static void new_abort() {
    LOGE("abort called: pid=%d, tid=%d", getpid(), gettid());
    dump_backtrace("abort");
    if (orig_abort) {
        orig_abort();
    }
}

static void new_exit(int status) {
    log_exit_call("exit", status);
    if (orig_exit) {
        orig_exit(status);
    }
}

static void new__exit(int status) {
    log_exit_call("_exit", status);
    if (orig__exit) {
        orig__exit(status);
    }
}

static int new_raise(int sig) {
    log_signal_call("raise", sig);
    return orig_raise ? orig_raise(sig) : -1;
}

static int new_kill(pid_t pid, int sig) {
    LOGE("kill called: callerPid=%d, callerTid=%d, targetPid=%d, signal=%d", getpid(), gettid(),
         pid, sig);
    dump_backtrace("kill");
    return orig_kill ? orig_kill(pid, sig) : -1;
}

static int new_tgkill(int tgid, int tid, int sig) {
    LOGE("tgkill called: callerPid=%d, callerTid=%d, targetPid=%d, targetTid=%d, signal=%d",
         getpid(), gettid(), tgid, tid, sig);
    dump_backtrace("tgkill");
    return orig_tgkill ? orig_tgkill(tgid, tid, sig) : -1;
}

static void hook_libc_symbol(void *handle, const char *name, void *replace, void **origin) {
    void *target = xdl_dsym(handle, name, nullptr);
    if (target == nullptr) {
        LOGD("symbol not found: %s", name);
        return;
    }
    if (DobbyHook(target, replace, origin) == 0) {
        LOGD("hooked %s", name);
    } else {
        LOGE("failed to hook %s", name);
    }
}

__attribute__((constructor)) static void install_process_exit_monitor() {
    void *handle = xdl_open("libc.so", XDL_DEFAULT);
    if (handle == nullptr) {
        LOGE("xdl_open failed for libc.so");
        return;
    }

    hook_libc_symbol(handle, "abort", (void *) new_abort, (void **) &orig_abort);
    hook_libc_symbol(handle, "exit", (void *) new_exit, (void **) &orig_exit);
    hook_libc_symbol(handle, "_exit", (void *) new__exit, (void **) &orig__exit);
    hook_libc_symbol(handle, "raise", (void *) new_raise, (void **) &orig_raise);
    hook_libc_symbol(handle, "kill", (void *) new_kill, (void **) &orig_kill);
    hook_libc_symbol(handle, "tgkill", (void *) new_tgkill, (void **) &orig_tgkill);

    xdl_close(handle);
    LOGD("process exit monitor installed");
}
