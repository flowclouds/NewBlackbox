package com.tm.box.fake.service;

import android.os.IBinder;
import android.util.Log;

import java.lang.reflect.Method;
import java.util.List;

import black.android.os.BRServiceManager;
import black.com.android.internal.telephony.BRISubStub;
import com.tm.box.fake.hook.BinderInvocationStub;
import com.tm.box.utils.AttributionSourceUtils;
import com.tm.box.utils.MethodParameterUtils;

public class ISubProxy extends BinderInvocationStub {
    public ISubProxy() {
        super(BRServiceManager.get().getService("isub"));
    }

    @Override
    protected Object getWho() {
        IBinder binder = BRServiceManager.get().getService("isub");
        return BRISubStub.get().asInterface(binder);
    }

    @Override
    protected void inject(Object baseInvocation, Object proxyInvocation) {
        replaceSystemService("isub");
    }

    @Override
    public boolean isBadEnv() {
        return false;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MethodParameterUtils.replaceAllAppPkg(args);
        AttributionSourceUtils.fixAttributionSourceInArgs(args);
        String pkg = firstString(args);
        try {
            Object result = super.invoke(proxy, method, args);
            if (method.getName().contains("ub")) {
                int size = result instanceof List ? ((List<?>) result).size() : -1;
                Log.i("ISubProxy", method.getName() + " pkg=" + pkg + " size=" + size + " null=" + (result == null));
            }
            return result;
        } catch (Throwable t) {
            Log.e("ISubProxy", method.getName() + " pkg=" + pkg, t);
            throw t;
        }
    }

    private static String firstString(Object[] args) {
        if (args == null) {
            return null;
        }
        for (Object arg : args) {
            if (arg instanceof String) {
                return (String) arg;
            }
        }
        return null;
    }
}
