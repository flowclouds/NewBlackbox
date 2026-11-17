package black.java.lang;

import java.util.List;

import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BField;

@BClassName("java.lang.ThreadGroup")
public interface ThreadGroup {
    @BField
    List<java.lang.ThreadGroup> groups();

    @BField
    java.lang.ThreadGroup parent();
}
