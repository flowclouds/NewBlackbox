package black.android.net;

import android.os.IBinder;
import android.os.IInterface;

import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BStaticMethod;


@BClassName("android.net.IVpnManager")
public interface IVpnManager {

    @BClassName("android.net.IVpnManager$Stub")
    interface Stub {
        @BStaticMethod
        IInterface asInterface(IBinder IBinder0);
    }
}
