#include <android/log.h>
#include <unistd.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <stdio.h>
#include <string.h>
#include <errno.h>
#include <dirent.h>
#include "Dobby/dobby.h"
#include "xdl.h"
#include "Obf.h"

#define LOG_TAG "AntiDetection"
#ifdef SDK_RELEASE
#define LOGD(...) ((void)0)
#else
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#endif

struct SpoofedProp {
    const char* key;
    const char* value;
};


static int (*orig_system_property_get)(const char *name, char *value) = nullptr;





static const char* blocked_files[] = {
    
    OBF_C(42),
    OBF_C(43),
    OBF_C(44),
    OBF_C(45),
    OBF_C(46),
    OBF_C(47),
    OBF_C(48),
    OBF_C(49),
    OBF_C(50),
    OBF_C(51),
    OBF_C(52),
    OBF_C(53),
    OBF_C(54),
    OBF_C(55),
    OBF_C(56),
    OBF_C(57),
    OBF_C(58),
    
    
    OBF_C(59),
    OBF_C(60),
    OBF_C(61),
    OBF_C(62),
    OBF_C(63),
    OBF_C(64),
    OBF_C(65),
    OBF_C(66),
    OBF_C(67),
    OBF_C(68),
    OBF_C(69),
    OBF_C(70),
    
    
    OBF_C(71),
    OBF_C(72),
    OBF_C(73),
    OBF_C(74),
    OBF_C(75),
    OBF_C(76),
    OBF_C(77),
    OBF_C(78),
    OBF_C(79),
    OBF_C(80),
    OBF_C(81),
    OBF_C(82),
    OBF_C(83),
    OBF_C(84),
    OBF_C(85),
    OBF_C(86),
    OBF_C(87),
    OBF_C(88),
    OBF_C(89),
    OBF_C(90),
    
    nullptr
};

static const char* blocked_packages[] = {
    OBF_C(91),
    OBF_C(92), 
    OBF_C(93),
    OBF_C(94),
    OBF_C(95),
    OBF_C(96),
    OBF_C(97),
    OBF_C(98),
    OBF_C(99),
    OBF_C(100),
    OBF_C(101),
    OBF_C(102),
    OBF_C(103),
    OBF_C(104),
    OBF_C(105),
    OBF_C(106),
    OBF_C(107),
    OBF_C(108),
    OBF_C(109),
    OBF_C(110),
    OBF_C(111),
    OBF_C(112),
    OBF_C(113),
    OBF_C(114),
    OBF_C(115),
    OBF_C(116),
    OBF_C(117),
    nullptr
};

static bool is_blocked_file(const char* path) {
    if (!path) return false;
    for (int i = 0; blocked_files[i]; ++i) {
        if (strstr(path, blocked_files[i])) {
            return true;
        }
    }
    return false;
}

static bool is_blocked_package(const char* path) {
    if (!path) return false;
    for (int i = 0; blocked_packages[i]; ++i) {
        if (strstr(path, blocked_packages[i])) {
            return true;
        }
    }
    return false;
}


static int (*orig_access)(const char *pathname, int mode) = nullptr;
static int (*orig_stat)(const char *pathname, struct stat *buf) = nullptr;
static int (*orig_lstat)(const char *pathname, struct stat *buf) = nullptr;
static FILE* (*orig_fopen)(const char *pathname, const char *mode) = nullptr;
static int (*orig_open)(const char *pathname, int flags, ...) = nullptr;
static ssize_t (*orig_readlink)(const char *pathname, char *buf, size_t bufsiz) = nullptr;
static DIR* (*orig_opendir)(const char *name) = nullptr;



static bool is_safe_path(const char* path) {
    if (!path) return false;
    if (strstr(path, OBF_C(118))) return true;
    if (strstr(path, OBF_C(119))) return true;
    return false;
}

static int my_access(const char *pathname, int mode) {
    if (pathname && !is_safe_path(pathname) && (is_blocked_file(pathname) || is_blocked_package(pathname))) {
        errno = ENOENT;
        return -1;
    }
    return orig_access ? orig_access(pathname, mode) : -1;
}

static int my_stat(const char *pathname, struct stat *buf) {
    if (pathname && !is_safe_path(pathname) && (is_blocked_file(pathname) || is_blocked_package(pathname))) {
        errno = ENOENT;
        return -1;
    }
    return orig_stat ? orig_stat(pathname, buf) : -1;
}

static int my_lstat(const char *pathname, struct stat *buf) {
    if (pathname && !is_safe_path(pathname) && (is_blocked_file(pathname) || is_blocked_package(pathname))) {
        errno = ENOENT;
        return -1;
    }
    return orig_lstat ? orig_lstat(pathname, buf) : -1;
}

static FILE* my_fopen(const char *pathname, const char *mode) {
    if (pathname && !is_safe_path(pathname) && (is_blocked_file(pathname) || is_blocked_package(pathname))) {
        errno = ENOENT;
        return nullptr;
    }
    return orig_fopen ? orig_fopen(pathname, mode) : nullptr;
}

static int my_open(const char *pathname, int flags, ...) {
    if (pathname && !is_safe_path(pathname) && (is_blocked_file(pathname) || is_blocked_package(pathname))) {
        errno = ENOENT;
        return -1;
    }
    if (orig_open) {
        if (flags & O_CREAT) {
            va_list args;
            va_start(args, flags);
            mode_t mode = va_arg(args, mode_t);
            va_end(args);
            return orig_open(pathname, flags, mode);
        } else {
            return orig_open(pathname, flags);
        }
    }
    return -1;
}

static ssize_t my_readlink(const char *pathname, char *buf, size_t bufsiz) {
    if (pathname && !is_safe_path(pathname) && (is_blocked_file(pathname) || is_blocked_package(pathname))) {
        errno = ENOENT;
        return -1;
    }
    return orig_readlink ? orig_readlink(pathname, buf, bufsiz) : -1;
}

static DIR* my_opendir(const char *name) {
    if (name && !is_safe_path(name) && (is_blocked_file(name) || is_blocked_package(name))) {
        errno = ENOENT;
        return nullptr;
    }
    return orig_opendir ? orig_opendir(name) : nullptr;
}


static void install_file_hooks() {
    void* handle = xdl_open(OBF_C(7), XDL_DEFAULT);
    if (!handle) {
        LOGD("xdl_open failed for libc.so");
        return;
    }


    xdl_close(handle);
    LOGD("File system hooks installed");
}


__attribute__((constructor)) void install_antidetection_hooks() {
    LOGD("Installing anti-detection hooks...");
    install_file_hooks(); 
    LOGD("Anti-detection hooks installation complete");
}
