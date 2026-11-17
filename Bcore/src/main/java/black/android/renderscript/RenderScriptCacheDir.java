package black.android.renderscript;

import java.io.File;

import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BStaticMethod;

@BClassName("android.renderscript.RenderScriptCacheDir")
public interface RenderScriptCacheDir {
    @BStaticMethod
    void setupDiskCache(File File0);
}
