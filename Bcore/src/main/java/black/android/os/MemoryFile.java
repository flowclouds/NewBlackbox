package black.android.os;

import java.io.FileDescriptor;

import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BMethod;

@BClassName("android.os.MemoryFile")
public interface MemoryFile {
    @BMethod
    FileDescriptor getFileDescriptor();
}
