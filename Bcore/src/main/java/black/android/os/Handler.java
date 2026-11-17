package black.android.os;

import android.os.Handler.Callback;

import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BField;

@BClassName("android.os.Handler")
public interface Handler {
    @BField
    Callback mCallback();
}
