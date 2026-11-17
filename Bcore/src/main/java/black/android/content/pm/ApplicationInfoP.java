package black.android.content.pm;


import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BField;
import com.tm.reflect.annotation.BMethod;

@BClassName("android.content.pm.ApplicationInfo")
public interface ApplicationInfoP {
    @BField
    String[] splitClassLoaderNames();

    @BMethod
    void setHiddenApiEnforcementPolicy(int int0);
}
