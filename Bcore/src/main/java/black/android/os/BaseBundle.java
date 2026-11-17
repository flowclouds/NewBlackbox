package black.android.os;

import android.os.Parcel;

import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BField;

@BClassName("android.os.BaseBundle")
public interface BaseBundle {
    @BField
    Parcel mParcelledData();
}
