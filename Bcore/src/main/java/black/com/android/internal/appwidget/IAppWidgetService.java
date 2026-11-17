package black.com.android.internal.appwidget;

import android.os.IBinder;
import android.os.IInterface;

import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BStaticMethod;

@BClassName("com.android.internal.appwidget.IAppWidgetService")
public interface IAppWidgetService {
    @BClassName("com.android.internal.appwidget.IAppWidgetService$Stub")
    interface Stub {
        @BStaticMethod
        IInterface asInterface(IBinder IBinder0);
    }
}
