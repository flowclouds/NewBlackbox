



#include <IO.h>
#include "UnixFileSystemHook.h"
#import "JniHook/JniHook.h"
#include "BaseHook.h"
#include "Obf.h"


HOOK_JNI(jstring, canonicalize0, JNIEnv *env, jobject obj, jstring path) {
    jstring redirect = IO::redirectPath(env, path);
    return orig_canonicalize0(env, obj, redirect);
}


HOOK_JNI(jint, getBooleanAttributes0, JNIEnv *env, jobject obj, jstring abspath) {
    jstring redirect = IO::redirectPath(env, abspath);
    return orig_getBooleanAttributes0(env, obj, redirect);
}


HOOK_JNI(jlong, getLastModifiedTime0, JNIEnv *env, jobject obj, jobject path) {
    jobject redirect = IO::redirectPath(env, path);
    return orig_getLastModifiedTime0(env, obj, redirect);
}


HOOK_JNI(jboolean, setPermission0, JNIEnv *env, jobject obj, jobject file, jint access,
         jboolean enable, jboolean owneronly) {
    jobject redirect = IO::redirectPath(env, file);
    return orig_setPermission0(env, obj, redirect, access, enable, owneronly);
}


HOOK_JNI(jboolean, createFileExclusively0, JNIEnv *env, jobject obj, jstring path) {
    jstring redirect = IO::redirectPath(env, path);
    return orig_createFileExclusively0(env, obj, redirect);
}


HOOK_JNI(jobjectArray, list0, JNIEnv *env, jobject obj, jobject file) {
    jobject redirect = IO::redirectPath(env, file);
    return orig_list0(env, obj, redirect);
}


HOOK_JNI(jboolean, createDirectory0, JNIEnv *env, jobject obj, jobject path) {
    jobject redirect = IO::redirectPath(env, path);
    return orig_createDirectory0(env, obj, redirect);
}


HOOK_JNI(jboolean, setLastModifiedTime0, JNIEnv *env, jobject obj, jobject file, jobject time) {
    jobject redirect = IO::redirectPath(env, file);
    return orig_setLastModifiedTime0(env, obj, redirect, time);
}


HOOK_JNI(jboolean, setReadOnly0, JNIEnv *env, jobject obj, jobject file) {
    jobject redirect = IO::redirectPath(env, file);
    return orig_setReadOnly0(env, obj, redirect);
}


HOOK_JNI(jboolean, getSpace0, JNIEnv *env, jobject obj, jobject file, jint t) {
    jobject redirect = IO::redirectPath(env, file);
    return orig_getSpace0(env, obj, redirect, t);
}

void UnixFileSystemHook::init(JNIEnv *env) {
    auto className = OBF(148);
    
    
    try {
        JniHook::HookJniFun(env, className.c_str(), OBF(149).c_str(), OBF(11).c_str(),
                            (void *) new_canonicalize0, (void **) (&orig_canonicalize0), false);
    } catch (...) {
        ALOGD("UnixFileSystemHook: Failed to hook canonicalize0, continuing without it");
    }
    



    
    
    try {
        JniHook::HookJniFun(env, className.c_str(), OBF(150).c_str(), OBF(151).c_str(),
                            (void *) new_getLastModifiedTime0, (void **) (&orig_getLastModifiedTime0),
                            false);
    } catch (...) {
        ALOGD("UnixFileSystemHook: Failed to hook getLastModifiedTime0, continuing without it");
    }
    
    try {
        JniHook::HookJniFun(env, className.c_str(), OBF(152).c_str(), OBF(153).c_str(),
                            (void *) new_setPermission0, (void **) (&orig_setPermission0), false);
    } catch (...) {
        ALOGD("UnixFileSystemHook: Failed to hook setPermission0, continuing without it");
    }
    
    try {
        JniHook::HookJniFun(env, className.c_str(), OBF(154).c_str(), OBF(155).c_str(),
                            (void *) new_createFileExclusively0,
                            (void **) (&orig_createFileExclusively0), false);
    } catch (...) {
        ALOGD("UnixFileSystemHook: Failed to hook createFileExclusively0, continuing without it");
    }
    
    try {
        JniHook::HookJniFun(env, className.c_str(), OBF(156).c_str(), OBF(157).c_str(),
                            (void *) new_list0, (void **) (&orig_list0), false);
    } catch (...) {
        ALOGD("UnixFileSystemHook: Failed to hook list0, continuing without it");
    }
    
    try {
        JniHook::HookJniFun(env, className.c_str(), OBF(158).c_str(), OBF(159).c_str(),
                            (void *) new_createDirectory0, (void **) (&orig_createDirectory0), false);
    } catch (...) {
        ALOGD("UnixFileSystemHook: Failed to hook createDirectory0, continuing without it");
    }
    
    try {
        JniHook::HookJniFun(env, className.c_str(), OBF(160).c_str(), OBF(161).c_str(),
                            (void *) new_setLastModifiedTime0, (void **) (&orig_setLastModifiedTime0),
                            false);
    } catch (...) {
        ALOGD("UnixFileSystemHook: Failed to hook setLastModifiedTime0, continuing without it");
    }
    
    try {
        JniHook::HookJniFun(env, className.c_str(), OBF(162).c_str(), OBF(159).c_str(),
                            (void *) new_setReadOnly0, (void **) (&orig_setReadOnly0), false);
    } catch (...) {
        ALOGD("UnixFileSystemHook: Failed to hook setReadOnly0, continuing without it");
    }
    
    try {
        JniHook::HookJniFun(env, className.c_str(), OBF(163).c_str(), OBF(164).c_str(),
                            (void *) new_getSpace0, (void **) (&orig_getSpace0), false);
    } catch (...) {
        ALOGD("UnixFileSystemHook: Failed to hook getSpace0, continuing without it");
    }
}