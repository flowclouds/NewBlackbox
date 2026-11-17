package black.android.hardware.display;

import android.os.IInterface;

import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BField;
import com.tm.reflect.annotation.BStaticMethod;

@BClassName("android.hardware.display.DisplayManagerGlobal")
public interface DisplayManagerGlobal {
    @BField
    IInterface mDm();

    @BStaticMethod
    Object getInstance();
}
