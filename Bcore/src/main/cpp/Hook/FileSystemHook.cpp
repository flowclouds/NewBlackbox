



#include "FileSystemHook.h"
#include "Log.h"
#include "Obf.h"
#include "xdl.h"
#include <sys/stat.h>
#include <fcntl.h>
#include <stdarg.h>
#include <cstring>
#include <errno.h>


static int (*orig_open)(const char *pathname, int flags, ...) = nullptr;
static int (*orig_open64)(const char *pathname, int flags, ...) = nullptr;

static bool blocked_name(const char *pathname) {
    static const char *keys[] = {
            OBF_C(142),
            OBF_C(143),
            OBF_C(144),
            OBF_C(145),
            OBF_C(146),
            nullptr
    };
    if (pathname == nullptr) return false;
    for (int i = 0; keys[i] != nullptr; ++i) {
        if (strstr(pathname, keys[i])) return true;
    }
    return false;
}


int new_open(const char *pathname, int flags, ...) {
    
    if (blocked_name(pathname)) {
        ALOGD("FileSystemHook: Blocking problematic file access: %s", pathname);
        errno = ENOENT;
        return -1;
    }
    
    
    va_list args;
    va_start(args, flags);
    mode_t mode = va_arg(args, mode_t);
    va_end(args);
    
    return orig_open(pathname, flags, mode);
}


int new_open64(const char *pathname, int flags, ...) {
    
    if (blocked_name(pathname)) {
        ALOGD("FileSystemHook: Blocking problematic file access (64): %s", pathname);
        errno = ENOENT;
        return -1;
    }
    
    
    va_list args;
    va_start(args, flags);
    mode_t mode = va_arg(args, mode_t);
    va_end(args);
    
    return orig_open64(pathname, flags, mode);
}

void FileSystemHook::init() {
    ALOGD("FileSystemHook: Initializing file system hooks");
    
    
    void* handle = xdl_open(OBF_C(7), XDL_DEFAULT);
    if (!handle) {
        ALOGE("FileSystemHook: Failed to open libc.so");
        return;
    }
    
    
    orig_open = (int (*)(const char*, int, ...))xdl_sym(handle, OBF_C(8), nullptr);
    if (orig_open) {
        
        
        ALOGD("FileSystemHook: Found open function at %p", orig_open);
    } else {
        ALOGE("FileSystemHook: Failed to find open function");
    }
    
    
    orig_open64 = (int (*)(const char*, int, ...))xdl_sym(handle, OBF_C(147), nullptr);
    if (orig_open64) {
        ALOGD("FileSystemHook: Found open64 function at %p", orig_open64);
    } else {
        ALOGE("FileSystemHook: Failed to find open64 function");
    }
    
    xdl_close(handle);
}
