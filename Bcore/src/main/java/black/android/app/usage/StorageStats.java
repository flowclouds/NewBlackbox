package black.android.app.usage;


import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BConstructor;
import com.tm.reflect.annotation.BField;

@BClassName("android.app.usage.StorageStats")
public interface StorageStats {
    @BConstructor
    StorageStats _new();

    @BField
    long cacheBytes();

    @BField
    long codeBytes();

    @BField
    long dataBytes();
}
