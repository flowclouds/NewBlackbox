package black.android.location;

import android.util.ArrayMap;

import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BField;

@BClassName("android.location.LocationManager")
public interface LocationManagerQ {
    @BField
    ArrayMap mGnssNmeaListeners();

    @BField
    ArrayMap mGnssStatusListeners();

    @BField
    ArrayMap mGpsNmeaListeners();

    @BField
    ArrayMap mGpsStatusListeners();

    @BField
    ArrayMap mListeners();
}
