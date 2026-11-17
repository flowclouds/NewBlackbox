package black.com.android.internal.policy;

import android.os.IInterface;

import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BStaticField;

@BClassName("com.android.internal.policy.PhoneWindow$WindowManagerHolder")
public interface PhoneWindow {
    @BStaticField
    IInterface sWindowManager();
}
