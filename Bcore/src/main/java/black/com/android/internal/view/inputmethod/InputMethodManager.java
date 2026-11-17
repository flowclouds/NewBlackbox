package black.com.android.internal.view.inputmethod;

import android.os.IInterface;

import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BField;

@BClassName("android.view.inputmethod.InputMethodManager")
public interface InputMethodManager {
    @BField
    IInterface mService();
}
