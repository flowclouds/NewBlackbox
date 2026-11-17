package black.android.widget;

import android.os.IInterface;

import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BStaticField;

@BClassName("android.widget.Toast")
public interface Toast {
    @BStaticField
    IInterface sService();
}
