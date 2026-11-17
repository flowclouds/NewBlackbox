package black.android.net.wifi;

import android.os.IBinder;
import android.os.IInterface;

import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BStaticMethod;

@BClassName("android.net.wifi.IWifiManager")
public interface IWifiManager {
    @BClassName("android.net.wifi.IWifiManager$Stub")
    interface Stub {
        @BStaticMethod
        IInterface asInterface(IBinder IBinder0);
    }
}
