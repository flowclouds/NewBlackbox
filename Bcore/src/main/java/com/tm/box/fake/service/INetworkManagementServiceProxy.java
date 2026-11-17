package com.tm.box.fake.service;

import static com.tm.box.app.BActivityThread.getUid;

import java.lang.reflect.Method;

import black.android.os.BRINetworkManagementServiceStub;
import black.android.os.BRServiceManager;
import com.tm.box.fake.hook.BinderInvocationStub;
import com.tm.box.fake.hook.MethodHook;
import com.tm.box.fake.hook.ProxyMethod;
import com.tm.box.fake.service.base.UidMethodProxy;
import com.tm.box.utils.MethodParameterUtils;
import com.tm.box.utils.Slog;


public class INetworkManagementServiceProxy extends BinderInvocationStub {
    public static final String NAME = "network_management";

    public INetworkManagementServiceProxy() {
        super(BRServiceManager.get().getService(NAME));
    }

    @Override
    protected Object getWho() {
        return BRINetworkManagementServiceStub.get().asInterface(BRServiceManager.get().getService(NAME));
    }

    @Override
    protected void inject(Object baseInvocation, Object proxyInvocation) {
        replaceSystemService(NAME);
    }

    @Override
    public boolean isBadEnv() {
        return false;
    }

    @Override
    protected void onBindMethod() {
        super.onBindMethod();
        addMethodHook(new UidMethodProxy("setUidCleartextNetworkPolicy", 0));
        addMethodHook(new UidMethodProxy("setUidMeteredNetworkBlacklist", 0));
        addMethodHook(new UidMethodProxy("setUidMeteredNetworkWhitelist", 0));
    }

    @ProxyMethod("getNetworkStatsUidDetail")
    public static class getNetworkStatsUidDetail extends MethodHook {
        @Override
        protected Object hook(Object who, Method method, Object[] args) throws Throwable {
            MethodParameterUtils.replaceFirstUid(args);
            MethodParameterUtils.replaceFirstAppPkg(args);
            return method.invoke(who, args);
        }
    }
}
