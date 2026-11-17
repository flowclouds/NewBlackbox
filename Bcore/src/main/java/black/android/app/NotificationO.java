package black.android.app;


import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BField;

@BClassName("android.app.Notification")
public interface NotificationO {
    @BField
    String mChannelId();

    @BField
    String mGroupKey();
}
