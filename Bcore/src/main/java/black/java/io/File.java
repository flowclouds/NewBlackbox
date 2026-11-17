package black.java.io;


import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BStaticField;

@BClassName("java.io.File")
public interface File {
    @BStaticField
    Object fs();
}
