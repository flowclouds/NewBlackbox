package black.android.app;

import android.content.ComponentName;
import android.os.IBinder;

import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BMethod;

@BClassName("android.app.IServiceConnection")
public interface IServiceConnectionO {
    @BMethod
    void connected(ComponentName ComponentName0, IBinder IBinder1, boolean boolean2);
}
