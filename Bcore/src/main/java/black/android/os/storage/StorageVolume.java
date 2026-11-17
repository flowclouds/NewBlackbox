package black.android.os.storage;

import java.io.File;

import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BField;

@BClassName("android.os.storage.StorageVolume")
public interface StorageVolume {
    @BField
    File mInternalPath();

    @BField
    File mPath();
}
