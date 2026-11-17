package com.tm.box.core.system.pm.installer;

import com.tm.box.core.env.BEnvironment;
import com.tm.box.core.system.pm.BPackageSettings;
import com.tm.box.entity.pm.InstallOption;
import com.tm.box.utils.FileUtils;


public class RemoveUserExecutor implements Executor {

    @Override
    public int exec(BPackageSettings ps, InstallOption option, int userId) {
        String packageName = ps.pkg.packageName;
        
        FileUtils.deleteDir(BEnvironment.getDataDir(packageName, userId));
        FileUtils.deleteDir(BEnvironment.getDeDataDir(packageName, userId));
        FileUtils.deleteDir(BEnvironment.getExternalDataDir(packageName, userId));
        return 0;
    }
}
