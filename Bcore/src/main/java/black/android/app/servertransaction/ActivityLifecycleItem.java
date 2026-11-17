package black.android.app.servertransaction;


import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BMethod;

@BClassName("android.app.servertransaction.ActivityLifecycleItem")
public interface ActivityLifecycleItem {
    @BMethod
    Integer getTargetState();
}
