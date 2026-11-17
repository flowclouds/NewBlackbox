package black.android.app;


import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BField;
import com.tm.reflect.annotation.BStaticField;

@BClassName("android.app.WallpaperManager")
public interface WallpaperManager {
    @BStaticField
    Object sGlobals();

    @BClassName("android.app.WallpaperManager$Globals")
    interface Globals {
        @BField
        Object mService();
    }
}
