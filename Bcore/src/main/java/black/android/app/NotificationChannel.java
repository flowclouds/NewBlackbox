package black.android.app;


import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BField;

@BClassName("android.app.NotificationChannel")
public interface NotificationChannel {
    @BField
    String mId();
}
