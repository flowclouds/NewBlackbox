package black.android.os;


import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BStaticField;

@BClassName("android.os.Parcel")
public interface Parcel {
    @BStaticField
    int VAL_PARCELABLE();

    @BStaticField
    int VAL_PARCELABLEARRAY();
}
