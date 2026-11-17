package black.android.telephony;


import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BConstructor;
import com.tm.reflect.annotation.BField;

@BClassName("android.telephony.CellSignalStrengthGsm")
public interface CellSignalStrengthGsm {
    @BConstructor
    CellSignalStrengthGsm _new();

    @BField
    int mBitErrorRate();

    @BField
    int mSignalStrength();
}
