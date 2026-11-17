package black.android.app;


import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BField;

@BClassName("android.app.LoadedApk")
public interface LoadedApkICS {
    @BField
    Object mCompatibilityInfo();
}
