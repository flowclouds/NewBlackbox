package black.android.content;

import android.os.IInterface;

import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BField;

@BClassName("android.content.ContentProviderClient")
public interface ContentProviderClient {
    @BField
    IInterface mContentProvider();
}
