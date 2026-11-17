package black.android.content;


import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BField;

@BClassName("android.content.PeriodicSync")
public interface PeriodicSync {
    @BField
    long flexTime();
}
