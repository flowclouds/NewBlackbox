package black.android.content;

import android.os.IInterface;

import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BField;
import com.tm.reflect.annotation.BStaticField;

@BClassName("android.content.ContentResolver")
public interface ContentResolver {
    @BStaticField
    IInterface sContentService();

    @BField
    String mPackageName();
}
