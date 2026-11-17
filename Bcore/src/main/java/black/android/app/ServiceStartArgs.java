package black.android.app;

import android.content.Intent;

import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BConstructor;
import com.tm.reflect.annotation.BField;

@BClassName("android.app.ServiceStartArgs")
public interface ServiceStartArgs {
    @BConstructor
    ServiceStartArgs _new(boolean boolean0, int int1, int int2, Intent Intent3);

    @BField
    Intent args();

    @BField
    int flags();

    @BField
    int startId();

    @BField
    boolean taskRemoved();
}
