package com.tm.box.proxy;

import java.util.Locale;

import com.tm.box.BlackBoxCore;


public class ProxyManifest {
    public static final int FREE_COUNT = 50;

    public static boolean isProxy(String msg) {
        return getBindProvider().equals(msg) || msg.contains("proxy_content_provider_");
    }

    public static String getBindProvider() {
        return BlackBoxCore.getHostPkg() + ".box.SystemCallProvider";
    }

    public static String getProxyAuthorities(int index) {
        return String.format(Locale.CHINA, "%s.proxy_content_provider_%d", BlackBoxCore.getHostPkg(), index);
    }

    public static String getProxyPendingActivity(int index) {
        return String.format(Locale.CHINA, "com.tm.box.proxy.ProxyPendingActivity$P%d", index);
    }

    public static String getProxyActivity(int index) {
        return String.format(Locale.CHINA, "com.tm.box.proxy.ProxyActivity$P%d", index);
    }

    public static String TransparentProxyActivity(int index) {
        return String.format(Locale.CHINA, "com.tm.box.proxy.TransparentProxyActivity$P%d", index);
    }

    public static String getProxyService(int index) {
        return String.format(Locale.CHINA, "com.tm.box.proxy.ProxyService$P%d", index);
    }

    public static String getProxyJobService(int index) {
        return String.format(Locale.CHINA, "com.tm.box.proxy.ProxyJobService$P%d", index);
    }

    public static String getProxyFileProvider() {
        return BlackBoxCore.getHostPkg() + ".box.FileProvider";
    }

    public static String getProxyReceiver() {
        return BlackBoxCore.getHostPkg() + ".stub_receiver";
    }

    public static String getProcessName(int bPid) {
        return BlackBoxCore.getHostPkg() + ":p" + bPid;
    }
}
