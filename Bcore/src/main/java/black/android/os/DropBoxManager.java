package black.android.os;

import android.os.IInterface;

import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BField;

@BClassName("android.os.DropBoxManager")
public interface DropBoxManager {
    @BField
    IInterface mService();
}
