#include "DexFileHook.h"
#include <IO.h>
#include <BoxCore.h>
#include "UnixFileSystemHook.h"
#import "JniHook/JniHook.h"
#include <sys/stat.h>
#include "Obf.h"

HOOK_JNI(jobject, openDexFileNative, JNIEnv *env, jobject obj,jstring sourceName, jstring outputName, jint flags,jobject loader, jobject elements) {
    const char *sourceNameC = env->GetStringUTFChars(sourceName, JNI_FALSE);
    ALOGD("openDexFileNative: %s", sourceNameC);
    if(strstr(sourceNameC, OBF_C(38)) != nullptr){




        DexFileHook::setFileReadonly(sourceNameC);
    }
    jobject orig = orig_openDexFileNative(env, obj,sourceName,outputName,flags,loader,elements);
    env->ReleaseStringUTFChars(sourceName, sourceNameC);
    return orig;
}


void DexFileHook::init(JNIEnv *env) {
    if (BoxCore::getApiLevel() >= __ANDROID_API_U__) {
        auto clazz = OBF(165);
        auto name = OBF(166);
        auto sig = OBF(167);
        JniHook::HookJniFun(env, clazz.c_str(), name.c_str(), sig.c_str(), (void *) new_openDexFileNative,
                            (void **) (&orig_openDexFileNative), true);
    }
}

void DexFileHook::setFileReadonly(const char* filePath) {
    struct stat fileStat;

    
    if (stat(filePath, &fileStat) != 0) {
        ALOGD("DexFileHook::setFileReadonly: %s 不存在",filePath);
        return;
    }

    
    
    if (chmod(filePath, S_IRUSR) != 0) {
        ALOGD("DexFileHook::setFileReadonly: 设置文件 %s 为只读时出错",filePath);
    } else {
        ALOGD("DexFileHook::setFileReadonly: 设置文件 %s 为只读成功",filePath);
    }
}
