package black.android.os.storage;

import android.os.storage.StorageVolume;

import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BStaticMethod;

@BClassName("android.os.storage.StorageManager")
public interface StorageManager {
    @BStaticMethod
    StorageVolume[] getVolumeList(int int0, int int1);
}
