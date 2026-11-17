package black.android.content;

import android.content.pm.ProviderInfo;
import android.os.IInterface;

import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BField;

@BClassName("android.app.ContentProviderHolder")
public interface ContentProviderHolderOreo {
    @BField
    ProviderInfo info();

    @BField
    boolean noReleaseNeeded();

    @BField
    IInterface provider();
}
