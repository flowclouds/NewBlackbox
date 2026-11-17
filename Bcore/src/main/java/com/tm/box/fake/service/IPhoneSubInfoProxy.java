package com.tm.box.fake.service;

import java.lang.reflect.Method;

import black.android.telephony.BRTelephonyManager;
import com.tm.box.fake.hook.ClassInvocationStub;
import com.tm.box.utils.AttributionSourceUtils;
import com.tm.box.utils.MethodParameterUtils;


public class IPhoneSubInfoProxy extends ClassInvocationStub {
    public static final String TAG = "IPhoneSubInfoProxy";

    public IPhoneSubInfoProxy() {
        if (BRTelephonyManager.get()._check_sServiceHandleCacheEnabled() != null) {
            BRTelephonyManager.get()._set_sServiceHandleCacheEnabled(true);
        }
        if (BRTelephonyManager.get()._check_getSubscriberInfoService() != null) {
            BRTelephonyManager.get().getSubscriberInfoService();
        }
    }

    @Override
    protected Object getWho() {
        return BRTelephonyManager.get().sIPhoneSubInfo();
    }

    @Override
    protected void inject(Object baseInvocation, Object proxyInvocation) {
        BRTelephonyManager.get()._set_sIPhoneSubInfo(proxyInvocation);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MethodParameterUtils.replaceAllAppPkg(args);
        AttributionSourceUtils.fixAttributionSourceInArgs(args);
        return super.invoke(proxy, method, args);
    }

    @Override
    public boolean isBadEnv() {
        return false;
    }
}
