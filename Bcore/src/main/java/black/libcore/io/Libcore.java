package black.libcore.io;


import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BStaticField;

@BClassName("libcore.io.Libcore")
public interface Libcore {
    @BStaticField
    Object os();
}
