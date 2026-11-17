package black.android.os.health;

import android.os.IInterface;

import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BField;

@BClassName("android.os.health.SystemHealthManager")
public interface SystemHealthManager {
    @BField
    IInterface mBatteryStats();
}
