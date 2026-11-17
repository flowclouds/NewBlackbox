package com.tm.box.fake;

import com.tm.jni.ReflectCore;


public class FakeCore {
    public static void init() {
        ReflectCore.set(android.app.ActivityThread.class);
    }
}
