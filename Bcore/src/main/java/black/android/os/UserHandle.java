package black.android.os;


import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BStaticMethod;

@BClassName("android.os.UserHandle")
public interface UserHandle {
    @BStaticMethod
    Integer myUserId();
}
