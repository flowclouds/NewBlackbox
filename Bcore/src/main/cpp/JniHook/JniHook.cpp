



#include <jni.h>
#include "JniHook.h"
#include "Log.h"
#include "ArtMethod.h"
#include "Obf.h"

static struct {
    int api_level;
    unsigned int art_field_size;
    int art_field_flags_offset;

    unsigned int art_method_size;
    int art_method_flags_offset;
    int art_method_native_offset;

    int class_flags_offset;

    jclass method_utils_class;
    jmethodID get_method_desc_id;
    jmethodID get_method_declaring_class_id;
    jmethodID get_method_name_id;

} HookEnv;

static const char *GetMethodDesc(JNIEnv *env, jobject javaMethod) {
    auto desc = reinterpret_cast<jstring>(env->CallStaticObjectMethod(HookEnv.method_utils_class,
                                                                      HookEnv.get_method_desc_id,
                                                                      javaMethod));
    return env->GetStringUTFChars(desc, JNI_FALSE);
}

static const char *GetMethodDeclaringClass(JNIEnv *env, jobject javaMethod) {
    auto desc = reinterpret_cast<jstring>(env->CallStaticObjectMethod(HookEnv.method_utils_class,
                                                                      HookEnv.get_method_declaring_class_id,
                                                                      javaMethod));
    return env->GetStringUTFChars(desc, JNI_FALSE);
}

static const char *GetMethodName(JNIEnv *env, jobject javaMethod) {
    auto desc = reinterpret_cast<jstring>(env->CallStaticObjectMethod(HookEnv.method_utils_class,
                                                                      HookEnv.get_method_name_id,
                                                                      javaMethod));
    return env->GetStringUTFChars(desc, JNI_FALSE);
}

inline static uint32_t GetAccessFlags(const char *art_method) {
    return *reinterpret_cast<const uint32_t *>(art_method + HookEnv.art_method_flags_offset);
}

inline static bool SetAccessFlags(char *art_method, uint32_t flags) {
    *reinterpret_cast<uint32_t *>(art_method + HookEnv.art_method_flags_offset) = flags;
    return true;
}

inline static bool AddAccessFlag(char *art_method, uint32_t flag) {
    uint32_t old_flag = GetAccessFlags(art_method);
    uint32_t new_flag = old_flag | flag;
    return new_flag != old_flag && SetAccessFlags(art_method, new_flag);
}

inline static bool ClearAccessFlag(char *art_method, uint32_t flag) {
    uint32_t old_flag = GetAccessFlags(art_method);
    uint32_t new_flag = old_flag & ~flag;
    return new_flag != old_flag && SetAccessFlags(art_method, new_flag);
}

inline static bool HasAccessFlag(char *art_method, uint32_t flag) {
    uint32_t flags = GetAccessFlags(art_method);
    ALOGD("AccessFlag:flags = 0x%x,flag = 0x%x",flags,flag);
    return (flags & flag) == flag;
}


inline static bool IsNativeMethod(char *art_method) {
    try {
        return HasAccessFlag(art_method, kAccNative);
    } catch (...) {
        ALOGD("NativeCore: Error checking native method flag, assuming not native");
        return false;
    }
}

inline static bool ClearFastNativeFlag(char *art_method) {
    
    return HookEnv.api_level < __ANDROID_API_P__ && ClearAccessFlag(art_method, kAccFastNative);
}

static void *GetArtMethod(JNIEnv *env, jclass clazz, jmethodID methodId) {
    if (HookEnv.api_level >= __ANDROID_API_Q__) {
        jclass executable = env->FindClass(OBF_C(120));
        jfieldID artId = env->GetFieldID(executable, OBF_C(121), OBF_C(122));
        jobject method = env->ToReflectedMethod(clazz, methodId, true);
        return reinterpret_cast<void *>(env->GetLongField(method, artId));
    } else {
        return methodId;
    }
}

static void *GetFieldMethod(JNIEnv *env, jobject field) {
    if (HookEnv.api_level >= __ANDROID_API_Q__) {
        jclass fieldClass = env->FindClass(OBF_C(123));
        jmethodID getArtField = env->GetMethodID(fieldClass, OBF_C(124), OBF_C(125));
        return reinterpret_cast<void *>(env->CallLongMethod(field, getArtField));
    } else {
        return env->FromReflectedField(field);
    }
}

bool CheckFlags(void *artMethod) {
    char *method = static_cast<char *>(artMethod);
    
    
    try {
        if (!HasAccessFlag(method, kAccNative)) {
            ALOGD("Method is not native, skipping hook");
            return false;
        }
        ClearFastNativeFlag(method);
        return true;
    } catch (...) {
        ALOGD("Error checking method flags, assuming not native");
        return false;
    }
}

void JniHook::HookJniFun(JNIEnv *env, jobject java_method, void *new_fun,
                         void **orig_fun, bool is_static) {
    const char *class_name = GetMethodDeclaringClass(env, java_method);
    const char *method_name = GetMethodName(env, java_method);
    const char *sign = GetMethodDesc(env, java_method);
    HookJniFun(env, class_name, method_name, sign, new_fun, orig_fun, is_static);
}

void
JniHook::HookJniFun(JNIEnv *env, const char *class_name, const char *method_name, const char *sign,
                    void *new_fun, void **orig_fun, bool is_static) {
    if (HookEnv.art_method_native_offset == 0) {
        return;
    }
    jclass clazz = env->FindClass(class_name);
    if (!clazz) {
        ALOGD("findClass fail: %s %s", class_name, method_name);
        env->ExceptionClear();
        return;
    }
    jmethodID method = nullptr;
    if (is_static) {
        method = env->GetStaticMethodID(clazz, method_name, sign);
    } else {
        method = env->GetMethodID(clazz, method_name, sign);
    }
    if (!method) {
        env->ExceptionClear();
        ALOGD("get method id fail: %s %s", class_name, method_name);
        return;
    }
    JNINativeMethod gMethods[] = {
            {method_name, sign, (void *) new_fun},
    };

    auto artMethod = reinterpret_cast<uintptr_t *>(GetArtMethod(env, clazz, method));
    if (!CheckFlags(artMethod)) {
        ALOGD("Skipping hook for non-native method: %s.%s", class_name, method_name);
        return;
    }
    *orig_fun = reinterpret_cast<void *>(artMethod[HookEnv.art_method_native_offset]);
    if (env->RegisterNatives(clazz, gMethods, 1) < 0) {
        ALOGE("jni hook error. class：%s, method：%s", class_name, method_name);
        return;
    }
    
    if (HookEnv.api_level == __ANDROID_API_O__ || HookEnv.api_level == __ANDROID_API_O_MR1__) {
        AddAccessFlag((char *) artMethod, kAccFastNative);
    }
    ALOGD("register class：%s, method：%s success!", class_name, method_name);
}

__attribute__((section (".mytext")))  JNICALL void native_offset
        (JNIEnv *env, jclass obj) {
}

__attribute__((section (".mytext")))  JNICALL void native_offset2
        (JNIEnv *env, jclass obj) {
}

__attribute__((section (".mytext")))  JNICALL void set_method_accessible
        (JNIEnv *env, jclass obj, jclass clazz, jobject method) {
    jmethodID methodId = env->FromReflectedMethod(method);
    char *art_method = static_cast<char *>(GetArtMethod(env, clazz, methodId));
    AddAccessFlag(art_method, kAccPublic);
    if (HookEnv.api_level >= __ANDROID_API_Q__) {
        AddAccessFlag(art_method, kAccPublicApi);
    }
}

__attribute__((section (".mytext")))  JNICALL void set_field_accessible
        (JNIEnv *env, jclass obj, jclass clazz, jobject field) {
    char *artField = static_cast<char *>(GetFieldMethod(env, field));
    AddAccessFlag(artField, kAccPublic);
    if (HookEnv.api_level >= __ANDROID_API_Q__) {
        AddAccessFlag(artField, kAccPublicApi);
    }
    ClearAccessFlag(artField, kAccFinal);
}

void registerNative(JNIEnv *env) {
    auto hookClass = OBF(130);
    auto setAccessible = OBF(138);
    jclass clazz = env->FindClass(hookClass.c_str());
    JNINativeMethod gMethods[] = {
            {OBF_C(126),  OBF_C(21),                                            (void *) native_offset},
            {OBF_C(127), OBF_C(21),                                            (void *) native_offset2},
            {setAccessible.c_str(), OBF_C(128), (void *) set_method_accessible},
            {setAccessible.c_str(), OBF_C(129),  (void *) set_field_accessible},
    };
    if (env->RegisterNatives(clazz, gMethods, sizeof(gMethods) / sizeof(gMethods[0])) < 0) {
        ALOGE("jni register error.");
    }
}

void JniHook::InitJniHook(JNIEnv *env, int api_level) {
    registerNative(env);
    HookEnv.api_level = api_level;

    jclass clazz = env->FindClass(OBF_C(130));
    jmethodID nativeOffsetId = env->GetStaticMethodID(clazz, OBF_C(126), OBF_C(21));
    jmethodID nativeOffset2Id = env->GetStaticMethodID(clazz, OBF_C(127), OBF_C(21));

    jfieldID nativeOffsetFieldId = env->GetStaticFieldID(clazz, OBF_C(131), OBF_C(132));
    jfieldID nativeOffsetField2Id = env->GetStaticFieldID(clazz, OBF_C(133), OBF_C(132));

    void *nativeOffsetField = GetFieldMethod(env, env->ToReflectedField(clazz, nativeOffsetFieldId,
                                                                        true));
    void *nativeOffsetField2 = GetFieldMethod(env, env->ToReflectedField(clazz, nativeOffsetField2Id,
                                                                         true));
    HookEnv.art_field_size = (size_t) nativeOffsetField2 - (size_t) nativeOffsetField;

    void *nativeOffset = GetArtMethod(env, clazz, nativeOffsetId);
    void *nativeOffset2 = GetArtMethod(env, clazz, nativeOffset2Id);
    HookEnv.art_method_size = (size_t) nativeOffset2 - (size_t) nativeOffset;

    int i = 0;
    
    auto artMethod = reinterpret_cast<uintptr_t *>(nativeOffset);
    for (i = 0; i < HookEnv.art_method_size; ++i) {
        if (reinterpret_cast<void *>(artMethod[i]) == native_offset) {
            HookEnv.art_method_native_offset = i;
            break;
        }
    }
    if(i == HookEnv.art_method_size){
        ALOGE("init jni hook error. art_method_native_offset not found!");
        return;
    }

    uint32_t flags = 0x0;
    flags = flags | kAccPublic;
    flags = flags | kAccStatic;
    flags = flags | kAccNative;
    flags = flags | kAccFinal;
    if (api_level >= __ANDROID_API_Q__) {
        flags = flags | kAccPublicApi;
    }
    if (api_level >= __ANDROID_API_S__) {
        flags = flags | kAccNterpInvokeFastPathFlag;
    }

    char *start = reinterpret_cast<char *>(artMethod);
    for (i = 1; i < HookEnv.art_method_size; ++i) {
        auto value = *(uint32_t *) (start + i * sizeof(uint32_t));

        if (value == flags) {
            HookEnv.art_method_flags_offset = i * sizeof(uint32_t);
            break;
        }
    }
    if(i == HookEnv.art_method_size){
        ALOGE("init jni hook error. art_method_flags_offset not found!");
        return;
    }

    flags = 0x0;
    flags = flags | kAccPublic;
    flags = flags | kAccStatic;
    flags = flags | kAccFinal;
    if (api_level >= __ANDROID_API_Q__) {
        flags = flags | kAccPublicApi;
    }
    char *fieldStart = reinterpret_cast<char *>(nativeOffsetField);
    for (i = 1; i < HookEnv.art_field_size; ++i) {
        auto value = *(int32_t *) (fieldStart + i * sizeof(int32_t));
        if (value == flags) {
            HookEnv.art_field_flags_offset = i * sizeof(int32_t);
            break;
        }
    }
    if(i == HookEnv.art_field_size){
        ALOGE("init jni hook error. art_field_flags_offset not found!");
        return;
    }

    auto methodSig = OBF(139);
    HookEnv.method_utils_class = env->FindClass(OBF_C(134));
    HookEnv.get_method_desc_id = env->GetStaticMethodID(HookEnv.method_utils_class, OBF_C(135),
                                                        methodSig.c_str());
    HookEnv.get_method_declaring_class_id = env->GetStaticMethodID(HookEnv.method_utils_class,
                                                                   OBF_C(136),
                                                                   methodSig.c_str());
    HookEnv.get_method_name_id = env->GetStaticMethodID(HookEnv.method_utils_class, OBF_C(137),
                                                        methodSig.c_str());
}

