package black.android.app.servertransaction;

import java.util.List;

import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BField;

@BClassName("android.app.servertransaction.ActivityResultItem")
public interface ActivityResultItem {
    @BField
    List mResultInfoList();
}
