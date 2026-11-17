package black.android.net.wifi;

import android.os.IInterface;

import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BField;
import com.tm.reflect.annotation.BStaticField;

@BClassName("android.net.wifi.WifiManager")
public interface WifiManager {
    @BStaticField
    IInterface sService();

    @BField
    IInterface mService();
}
