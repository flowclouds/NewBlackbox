package black.android.app;

import android.os.IBinder;

import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BMethod;

@BClassName("android.app.ClientTransactionHandler")
public interface ClientTransactionHandler {
    @BMethod
    Object getActivityClient(IBinder IBinder0);
}
