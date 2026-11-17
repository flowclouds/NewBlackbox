package black.android.view;

import java.io.File;

import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BStaticMethod;

@BClassName("android.view.ThreadedRenderer")
public interface ThreadedRenderer {
    @BStaticMethod
    void setupDiskCache(File File0);
}
