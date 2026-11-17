package black.android.app;

import android.graphics.drawable.Icon;

import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BField;

@BClassName("android.app.Notification")
public interface NotificationM {
    @BField
    Icon mLargeIcon();

    @BField
    Icon mSmallIcon();
}
