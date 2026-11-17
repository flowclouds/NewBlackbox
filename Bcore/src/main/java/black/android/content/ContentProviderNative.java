package black.android.content;

import android.os.IBinder;
import android.os.IInterface;

import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BStaticMethod;

@BClassName("android.content.ContentProviderNative")
public interface ContentProviderNative {
    @BStaticMethod
    IInterface asInterface(IBinder IBinder0);
}
