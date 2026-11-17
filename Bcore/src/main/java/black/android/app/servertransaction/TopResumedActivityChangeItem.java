package black.android.app.servertransaction;


import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BField;

@BClassName("android.app.servertransaction.TopResumedActivityChangeItem")
public interface TopResumedActivityChangeItem {
    @BField
    Boolean mOnTop();
}
