package black.android.util;


import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BField;
import com.tm.reflect.annotation.BMethod;

@BClassName("android.util.Singleton")
public interface Singleton {
    @BField
    Object mInstance();

    @BMethod
    Object get();
}
