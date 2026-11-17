package black.android.content.res;

import android.content.res.Configuration;
import android.util.DisplayMetrics;

import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BConstructor;
import com.tm.reflect.annotation.BMethod;

@BClassName("android.content.res.AssetManager")
public interface AssetManager {
    @BConstructor
    android.content.res.AssetManager _new();

    @BMethod
    Integer addAssetPath(String String0);

    @BMethod
    Configuration getConfiguration();

    @BMethod
    DisplayMetrics getDisplayMetrics();
}
