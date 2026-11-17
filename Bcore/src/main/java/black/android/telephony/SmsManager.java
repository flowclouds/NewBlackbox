package black.android.telephony;


import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BMethod;

@BClassName("android.telephony.SmsManager")
public interface SmsManager {
    @BMethod
    Boolean getAutoPersisting();

    @BMethod
    void setAutoPersisting(boolean boolean0);
}
