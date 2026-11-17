package black.android.app;

import android.os.IInterface;

import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BField;

@BClassName("android.app.AppOpsManager")
public interface AppOpsManager {
    @BField
    IInterface mService();
}
