package black.android.app;

import java.io.File;

import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BField;

@BClassName("android.app.ContextImpl")
public interface ContextImplICS {
    @BField
    File mExternalCacheDir();

    @BField
    File mExternalFilesDir();
}
