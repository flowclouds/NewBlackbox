package black.android.app;

import android.app.PendingIntent;
import android.content.Context;

import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BMethod;

@BClassName("android.app.Notification")
public interface Notification {
    @BMethod
    void setLatestEventInfo(Context Context0, CharSequence CharSequence1, CharSequence CharSequence2, PendingIntent PendingIntent3);
}
