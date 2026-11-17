package black.android.location;

import android.location.Location;
import android.os.IBinder;
import android.os.IInterface;

import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BMethod;
import com.tm.reflect.annotation.BStaticMethod;

@BClassName("android.location.ILocationListener")
public interface ILocationListener {
    @BMethod
    void onLocationChanged(Location Location0);

    @BClassName("android.location.ILocationListener$Stub")
    interface Stub {
        @BStaticMethod
        IInterface asInterface(IBinder IBinder0);
    }
}
