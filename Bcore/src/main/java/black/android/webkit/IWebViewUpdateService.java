package black.android.webkit;


import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BMethod;

@BClassName("android.webkit.IWebViewUpdateService")
public interface IWebViewUpdateService {
    @BMethod
    String getCurrentWebViewPackageName();

    @BMethod
    Object waitForAndGetProvider();
}
