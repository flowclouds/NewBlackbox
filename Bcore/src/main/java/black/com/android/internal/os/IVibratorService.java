package black.com.android.internal.os;

import android.os.IBinder;
import android.os.IInterface;

import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BStaticMethod;

@BClassName("android.os.IVibratorService")
public interface IVibratorService {
    @BClassName("android.os.IVibratorService$Stub")
    interface Stub {
        @BStaticMethod
        IInterface asInterface(IBinder IBinder0);
    }
}
