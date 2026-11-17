package black.android.webkit;


import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BStaticField;
import com.tm.reflect.annotation.BStaticMethod;

@BClassName("android.webkit.WebViewFactory")
public interface WebViewFactory {
    @BStaticField
    Boolean sWebViewSupported();

    @BStaticMethod
    Object getUpdateService();
}
