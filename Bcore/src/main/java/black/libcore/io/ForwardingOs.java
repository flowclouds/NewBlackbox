package black.libcore.io;


import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BField;

@BClassName("libcore.io.ForwardingOs")
public interface ForwardingOs {
    @BField
    Object os();
}
