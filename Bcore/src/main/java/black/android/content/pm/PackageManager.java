package black.android.content.pm;

import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BField;
import com.tm.reflect.annotation.BMethod;
import com.tm.reflect.annotation.BStaticMethod;

@BClassName("android.content.pm.PackageManager")
public interface PackageManager {
    @BStaticMethod
    void disableApplicationInfoCache();
}
