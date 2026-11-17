package black.android.os;

import android.os.IBinder;

import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BMethod;

@BClassName("android.os.Bundle")
public interface Bundle {
    @BMethod
    IBinder getIBinder(String String0);

    @BMethod
    void putIBinder(String String0, IBinder IBinder1);
}
