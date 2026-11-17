package black.android.net.wifi;


import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BStaticMethod;

@BClassName("android.net.wifi.WifiSsid")
public interface WifiSsid {
    @BStaticMethod
    Object createFromAsciiEncoded(String asciiEncoded);
}
