package black.com.android.internal.telephony;

import android.os.IBinder;
import android.os.IInterface;

import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BStaticMethod;

@BClassName("com.android.internal.telephony.ITelephony")
public interface ITelephony {
    @BClassName("com.android.internal.telephony.ITelephony$Stub")
    interface Stub {
        @BStaticMethod
        IInterface asInterface(IBinder IBinder0);
    }
}
