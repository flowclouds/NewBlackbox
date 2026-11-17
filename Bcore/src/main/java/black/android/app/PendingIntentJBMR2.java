package black.android.app;

import android.content.Intent;
import android.os.IBinder;

import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BConstructor;
import com.tm.reflect.annotation.BMethod;

@BClassName("android.app.PendingIntent")
public interface PendingIntentJBMR2 {
    @BConstructor
    PendingIntentJBMR2 _new(IBinder IBinder0);

    @BMethod
    Intent getIntent();
}
