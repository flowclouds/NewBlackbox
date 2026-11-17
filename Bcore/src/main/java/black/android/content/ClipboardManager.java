package black.android.content;

import android.os.IInterface;

import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BStaticField;
import com.tm.reflect.annotation.BStaticMethod;

@BClassName("android.content.ClipboardManager")
public interface ClipboardManager {
    @BStaticField
    IInterface sService();

    @BStaticMethod
    IInterface getService();
}
