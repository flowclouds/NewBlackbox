package black.android.net.wifi;


import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BStaticField;

@BClassName("android.net.wifi.WifiScanner")
public interface WifiScanner {
    @BStaticField
    String GET_AVAILABLE_CHANNELS_EXTRA();
}
