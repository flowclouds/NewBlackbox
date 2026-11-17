package black.android.media;

import android.os.IInterface;

import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BStaticField;
import com.tm.reflect.annotation.BStaticMethod;

@BClassName("android.media.AudioManager")
public interface AudioManager {
    @BStaticField
    IInterface sService();

    @BStaticMethod
    void getService();
}
