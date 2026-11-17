package com.tm.box.fake.service;

import android.util.Log;

import java.lang.reflect.Method;

import black.android.os.BRServiceManager;
import black.com.android.internal.telephony.BRITelephonyRegistryStub;
import com.tm.box.fake.hook.BinderInvocationStub;
import com.tm.box.fake.hook.MethodHook;
import com.tm.box.fake.hook.ProxyMethod;
import com.tm.box.utils.AttributionSourceUtils;
import com.tm.box.utils.MethodParameterUtils;


public class ITelephonyRegistryProxy extends BinderInvocationStub {
    public ITelephonyRegistryProxy() {
        super(BRServiceManager.get().getService("telephony.registry"));
    }

    @Override
    protected Object getWho() {
        return BRITelephonyRegistryStub.get().asInterface(BRServiceManager.get().getService("telephony.registry"));
    }

    @Override
    protected void inject(Object baseInvocation, Object proxyInvocation) {
        replaceSystemService("telephony.registry");
    }

    @Override
    public boolean isBadEnv() {
        return false;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MethodParameterUtils.replaceAllAppPkg(args);
        AttributionSourceUtils.fixAttributionSourceInArgs(args);
        if (method.getName().contains("ubscription")) {
            Log.i("ITelephonyRegistryProxy", method.getName());
        }
        return super.invoke(proxy, method, args);
    }

    @ProxyMethod("listenForSubscriber")
    public static class ListenForSubscriber extends MethodHook {

        @Override
        protected Object hook(Object who, Method method, Object[] args) throws Throwable {
            MethodParameterUtils.replaceFirstAppPkg(args);
            AttributionSourceUtils.fixAttributionSourceInArgs(args);
            return method.invoke(who, args);
        }
    }

    @ProxyMethod("listen")
    public static class Listen extends MethodHook {

        @Override
        protected Object hook(Object who, Method method, Object[] args) throws Throwable {
            MethodParameterUtils.replaceFirstAppPkg(args);
            AttributionSourceUtils.fixAttributionSourceInArgs(args);
            return method.invoke(who, args);
        }
    }
}
