package black.android.content.pm;


import android.content.pm.PackageParser;

import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BStaticMethod;

@BClassName("android.content.pm.PackageParser")
public interface PackageParserNougat {
    @BStaticMethod
    void collectCertificates(PackageParser.Package p, int flags);
}
