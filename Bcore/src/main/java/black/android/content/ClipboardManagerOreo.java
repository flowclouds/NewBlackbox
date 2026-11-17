package black.android.content;

import android.os.IInterface;

import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BField;
import com.tm.reflect.annotation.BStaticField;

@BClassName("android.content.ClipboardManager")
public interface ClipboardManagerOreo {
    @BStaticField
    IInterface sService();

    @BField
    IInterface mService();
}
