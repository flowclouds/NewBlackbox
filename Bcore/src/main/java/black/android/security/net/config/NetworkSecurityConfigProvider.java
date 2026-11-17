package black.android.security.net.config;

import android.content.Context;

import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BStaticMethod;

@BClassName("android.security.net.config.NetworkSecurityConfigProvider")
public interface NetworkSecurityConfigProvider {
    @BStaticMethod
    void install(Context Context0);
}
