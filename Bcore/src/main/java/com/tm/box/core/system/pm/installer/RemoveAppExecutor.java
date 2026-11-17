package com.tm.box.core.system.pm.installer;

import com.tm.box.core.env.BEnvironment;
import com.tm.box.core.system.pm.BPackageSettings;
import com.tm.box.entity.pm.InstallOption;
import com.tm.box.utils.FileUtils;


public class RemoveAppExecutor implements Executor {
    @Override
    public int exec(BPackageSettings ps, InstallOption option, int userId) {
        FileUtils.deleteDir(BEnvironment.getAppDir(ps.pkg.packageName));
        return 0;
    }
}
