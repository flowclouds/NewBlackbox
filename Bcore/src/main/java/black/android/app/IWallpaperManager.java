package black.android.app;

import android.os.IBinder;
import android.os.IInterface;

import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BStaticMethod;

@BClassName("android.app.IWallpaperManager")
public interface IWallpaperManager {
    @BClassName("android.app.IWallpaperManager$Stub")
    interface Stub {
        @BStaticMethod
        IInterface asInterface(IBinder IBinder0);
    }
}
