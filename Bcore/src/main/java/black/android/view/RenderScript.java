package black.android.view;

import java.io.File;

import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BStaticMethod;

@BClassName("android.renderscript.RenderScript")
public interface RenderScript {
    @BStaticMethod
    void setupDiskCache(File File0);
}
