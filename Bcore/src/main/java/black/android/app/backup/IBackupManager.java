package black.android.app.backup;

import android.os.IBinder;
import android.os.IInterface;

import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BStaticMethod;

@BClassName("android.app.backup.IBackupManager")
public interface IBackupManager {
    @BClassName("android.app.backup.IBackupManager$Stub")
    interface Stub {
        @BStaticMethod
        IInterface asInterface(IBinder IBinder0);
    }
}
