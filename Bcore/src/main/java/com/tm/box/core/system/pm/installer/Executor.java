package com.tm.box.core.system.pm.installer;

import com.tm.box.core.system.pm.BPackageSettings;
import com.tm.box.entity.pm.InstallOption;


public interface Executor {
    public static final String TAG = "InstallExecutor";

    int exec(BPackageSettings ps, InstallOption option, int userId);
}
