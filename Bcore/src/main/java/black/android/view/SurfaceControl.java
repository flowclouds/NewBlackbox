package black.android.view;

import android.graphics.Bitmap;

import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BStaticMethod;

@BClassName("android.view.SurfaceControl")
public interface SurfaceControl {
    @BStaticMethod
    Bitmap screnshot(int int0, int int1);
}
