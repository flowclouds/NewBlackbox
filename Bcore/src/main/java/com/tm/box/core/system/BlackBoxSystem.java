package com.tm.box.core.system;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import com.tm.box.BlackBoxCore;
import com.tm.box.core.env.AppSystemEnv;
import com.tm.box.core.env.BEnvironment;
import com.tm.box.core.system.accounts.BAccountManagerService;
import com.tm.box.core.system.am.BActivityManagerService;
import com.tm.box.core.system.am.BJobManagerService;
import com.tm.box.core.system.location.BLocationManagerService;
import com.tm.box.core.system.notification.BNotificationManagerService;
import com.tm.box.core.system.os.BStorageManagerService;
import com.tm.box.core.system.pm.BPackageInstallerService;
import com.tm.box.core.system.pm.BPackageManagerService;

import com.tm.box.core.system.user.BUserHandle;
import com.tm.box.core.system.user.BUserManagerService;
import com.tm.box.entity.pm.InstallOption;
import com.tm.box.utils.FileUtils;

import com.tm.box.core.system.JarManager;


public class BlackBoxSystem {
    private static BlackBoxSystem sBlackBoxSystem;
    private final List<ISystemService> mServices = new ArrayList<>();
    private final static AtomicBoolean isStartup = new AtomicBoolean(false);

    public static BlackBoxSystem getSystem() {
        if (sBlackBoxSystem == null) {
            synchronized (BlackBoxSystem.class) {
                if (sBlackBoxSystem == null) {
                    sBlackBoxSystem = new BlackBoxSystem();
                }
            }
        }
        return sBlackBoxSystem;
    }

    public void startup() {
        if (isStartup.getAndSet(true))
            return;
        BEnvironment.load();

        mServices.add(BPackageManagerService.get());
        mServices.add(BUserManagerService.get());
        mServices.add(BActivityManagerService.get());
        mServices.add(BJobManagerService.get());
        mServices.add(BStorageManagerService.get());
        mServices.add(BPackageInstallerService.get());

        mServices.add(BProcessManagerService.get());
        mServices.add(BAccountManagerService.get());
        mServices.add(BLocationManagerService.get());
        mServices.add(BNotificationManagerService.get());

        for (ISystemService service : mServices) {
            service.systemReady();
        }

        List<String> preInstallPackages = AppSystemEnv.getPreInstallPackages();
        for (String preInstallPackage : preInstallPackages) {
            try {
                if (!BPackageManagerService.get().isInstalled(preInstallPackage, BUserHandle.USER_ALL)) {
                    PackageInfo packageInfo = BlackBoxCore.getPackageManager().getPackageInfo(preInstallPackage, 0);
                    BPackageManagerService.get().installPackageAsUser(packageInfo.applicationInfo.sourceDir, InstallOption.installBySystem(), BUserHandle.USER_ALL);
                }
            } catch (PackageManager.NameNotFoundException ignored) {
            }
        }
        
        JarManager.getInstance().initializeAsync();
        
        
     
    }
}
