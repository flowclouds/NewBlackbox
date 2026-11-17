package black.android.content.pm;

import android.content.pm.PackageParser.Package;

import java.io.File;

import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BConstructor;
import com.tm.reflect.annotation.BMethod;

@BClassName("android.content.pm.PackageParser")
public interface PackageParserLollipop {
    @BConstructor
    android.content.pm.PackageParser _new();
















    @BMethod
    void collectCertificates(Package p, int flags);

    @BMethod
    Package parsePackage(File File0, int flags);
}
