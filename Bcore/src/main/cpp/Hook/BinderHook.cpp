



#include "BinderHook.h"
#include "Obf.h"
#include <IO.h>
#include <BoxCore.h>
#include "UnixFileSystemHook.h"
#import "JniHook/JniHook.h"



HOOK_JNI(jint, getCallingUid, JNIEnv *env, jobject obj) {
    int orig = orig_getCallingUid(env, obj);
    return BoxCore::getCallingUid(env, orig);
}


void BinderHook::init(JNIEnv *env) {
    auto clazz = OBF(140);
    auto name = OBF(9);
    auto sig = OBF(141);
    JniHook::HookJniFun(env, clazz.c_str(), name.c_str(), sig.c_str(), (void *) new_getCallingUid,
                        (void **) (&orig_getCallingUid), true);
}