package black.android.ddm;


import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BStaticMethod;

@BClassName("android.ddm.DdmHandleAppName")
public interface DdmHandleAppName {
    @BStaticMethod
    void setAppName(String String0, int i);
}
