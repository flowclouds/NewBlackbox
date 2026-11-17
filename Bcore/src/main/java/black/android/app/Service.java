package black.android.app;


import android.app.ActivityThread;
import android.app.Application;
import android.content.Context;
import android.os.IBinder;

import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BMethod;
import com.tm.reflect.annotation.BParamClass;

@BClassName("android.app.Service")
public interface Service {
    @BMethod
    void attach(Context context,
                @BParamClass(ActivityThread.class) Object thread, String className, IBinder token,
                Application application, Object activityManager);
}
