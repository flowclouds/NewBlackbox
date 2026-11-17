package black.android.app;

import android.os.IInterface;

import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BStaticField;
import com.tm.reflect.annotation.BStaticMethod;

@BClassName("android.app.ActivityManager")
public interface ActivityManagerOreo {
    @BStaticField
    Object IActivityManagerSingleton();

    @BStaticMethod
    IInterface getService();
}
