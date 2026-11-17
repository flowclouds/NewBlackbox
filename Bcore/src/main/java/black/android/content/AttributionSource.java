package black.android.content;

import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BField;
import com.tm.reflect.annotation.BMethod;


@BClassName("android.content.AttributionSource")
public interface AttributionSource {
    @BField
    Object mAttributionSourceState();

    @BMethod
    Object getNext();
}
