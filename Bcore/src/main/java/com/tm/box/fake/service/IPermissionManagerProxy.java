package com.tm.box.fake.service;

import android.content.pm.PackageManager;

import java.lang.reflect.Method;

import black.android.app.BRActivityThread;
import black.android.app.BRContextImpl;
import black.android.os.BRServiceManager;
import black.android.permission.BRIPermissionManagerStub;
import com.tm.box.BlackBoxCore;
import com.tm.box.fake.hook.BinderInvocationStub;
import com.tm.box.fake.service.base.PkgMethodProxy;
import com.tm.box.fake.service.base.ValueMethodProxy;
import com.tm.box.utils.Reflector;
import com.tm.box.utils.compat.BuildCompat;


public class IPermissionManagerProxy extends BinderInvocationStub {
    public static final String TAG = "IPermissionManagerProxy";

    private static final String P = "permissionmgr";

    public IPermissionManagerProxy() {
        super(BRServiceManager.get().getService(P));
    }

    @Override
    protected Object getWho() {
        return BRIPermissionManagerStub.get().asInterface(BRServiceManager.get().getService(P));
    }

    @Override
    protected void inject(Object baseInvocation, Object proxyInvocation) {
        replaceSystemService("permissionmgr");
        BRActivityThread.getWithException()._set_sPermissionManager(proxyInvocation);
        
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Class<?> returnType = method.getReturnType();
        if ((returnType == int.class || returnType == Integer.class)
                && method.getName().startsWith("check")
                && grantsPhoneState(args)) {
            return PackageManager.PERMISSION_GRANTED;
        }
        return super.invoke(proxy, method, args);
    }

    private static boolean grantsPhoneState(Object[] args) {
        if (args == null) {
            return false;
        }
        for (Object arg : args) {
            if ("android.permission.READ_PHONE_STATE".equals(arg)
                    || "android.permission.READ_PHONE_NUMBERS".equals(arg)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onBindMethod() {
        super.onBindMethod();
        addMethodHook(new ValueMethodProxy("addPermissionAsync", true));
        addMethodHook(new ValueMethodProxy("addPermission", true));
        addMethodHook(new ValueMethodProxy("performDexOpt", true));
        addMethodHook(new ValueMethodProxy("performDexOptIfNeeded", false));
        addMethodHook(new ValueMethodProxy("performDexOptSecondary", true));
        addMethodHook(new ValueMethodProxy("addOnPermissionsChangeListener", 0));
        addMethodHook(new ValueMethodProxy("removeOnPermissionsChangeListener", 0));
        addMethodHook(new ValueMethodProxy("checkDeviceIdentifierAccess", true));
        addMethodHook(new PkgMethodProxy("shouldShowRequestPermissionRationale"));
        if (BuildCompat.isOreo()) {
            addMethodHook(new ValueMethodProxy("notifyDexLoad", 0));
            addMethodHook(new ValueMethodProxy("notifyPackageUse", 0));
            addMethodHook(new ValueMethodProxy("setInstantAppCookie", false));
            addMethodHook(new ValueMethodProxy("isInstantApp", false));
        }
    }

    @Override
    public boolean isBadEnv() {
        return false;
    }

}
