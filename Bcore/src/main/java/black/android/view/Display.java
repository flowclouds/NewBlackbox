package black.android.view;

import android.os.IInterface;

import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BStaticField;

@BClassName("android.view.Display")
public interface Display {
    @BStaticField
    IInterface sWindowManager();
}
