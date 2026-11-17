package black.android.content.pm;

import android.content.pm.PackageParser.SigningDetails;

import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BConstructor;
import com.tm.reflect.annotation.BField;

@BClassName("android.content.pm.SigningInfo")
public interface SigningInfo {
    @BConstructor
    android.content.pm.SigningInfo _new(SigningDetails SigningDetails0);

    @BField
    SigningDetails mSigningDetails();
}
