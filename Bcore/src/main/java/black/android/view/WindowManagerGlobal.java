package black.android.view;

import android.os.IInterface;

import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BStaticField;

@BClassName("android.view.WindowManagerGlobal")
public interface WindowManagerGlobal {
    @BStaticField
    int ADD_PERMISSION_DENIED();

    @BStaticField
    IInterface sWindowManagerService();
}
