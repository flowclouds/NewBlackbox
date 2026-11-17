package black.android.location;


import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BField;
import com.tm.reflect.annotation.BMethod;

@BClassName("android.location.LocationRequest")
public interface LocationRequestL {
    @BField
    boolean mHideFromAppOps();

    @BField
    String mProvider();

    @BField
    Object mWorkSource();

    @BMethod
    String getProvider();
}
