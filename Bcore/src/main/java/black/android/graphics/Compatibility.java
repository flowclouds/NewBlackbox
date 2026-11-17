package black.android.graphics;

import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BStaticMethod;


@BClassName("android.graphics.Compatibility")
public interface Compatibility {
    @BStaticMethod
    void setTargetSdkVersion(int targetSdkVersion);
}
