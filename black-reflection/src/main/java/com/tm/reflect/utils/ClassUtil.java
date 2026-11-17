package com.tm.reflect.utils;

import com.tm.reflect.annotation.BClass;
import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BClassNameNotProcess;


public class ClassUtil {
    public static Class<?> classReady(Class<?> clazz) {
        BClassNameNotProcess bClassNameNotProcess = clazz.getAnnotation(BClassNameNotProcess.class);
        if (bClassNameNotProcess != null) {
            return classReady(bClassNameNotProcess.value());
        }
        BClass annotation = clazz.getAnnotation(BClass.class);
        if (annotation != null) {
            return annotation.value();
        }
        BClassName bClassName = clazz.getAnnotation(BClassName.class);
        if (bClassName != null) {
            return classReady(bClassName.value());
        }
        return null;
    }

    private static Class<?> classReady(String clazz) {
        try {
            return Class.forName(clazz);
        } catch (ClassNotFoundException ignored) {
        }
        return null;
    }
}
