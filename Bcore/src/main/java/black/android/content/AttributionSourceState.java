package black.android.content;

import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BField;


@BClassName("android.content.AttributionSourceState")
public interface AttributionSourceState {
    @BField
    String packageName();

    @BField
    int uid();
}
