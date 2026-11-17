package black.android.app;

import java.io.File;

import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BConstructor;

@BClassName("android.app.SharedPreferencesImpl")
public interface SharedPreferencesImpl {
    @BConstructor
    SharedPreferencesImpl _new(File File0, int int1);
}
