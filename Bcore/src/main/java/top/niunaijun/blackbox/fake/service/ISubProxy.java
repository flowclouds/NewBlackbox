package top.niunaijun.blackbox.fake.service;

import android.os.IBinder;

import java.lang.reflect.Method;

import black.android.os.BRServiceManager;
import black.com.android.internal.telephony.BRISubStub;
import top.niunaijun.blackbox.fake.hook.BinderInvocationStub;
import top.niunaijun.blackbox.utils.AttributionSourceUtils;
import top.niunaijun.blackbox.utils.MethodParameterUtils;

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
        MethodParameterUtils.replaceFirstAppPkg(args);
        AttributionSourceUtils.fixAttributionSourceInArgs(args);
        return super.invoke(proxy, method, args);
    }
}
