package black.android.telephony;

import android.telephony.CellIdentityGsm;
import android.telephony.CellSignalStrengthGsm;

import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BConstructor;
import com.tm.reflect.annotation.BField;

@BClassName("android.telephony.CellInfoGsm")
public interface CellInfoGsm {
    @BConstructor
    CellInfoGsm _new();

    @BField
    CellIdentityGsm mCellIdentityGsm();

    @BField
    CellSignalStrengthGsm mCellSignalStrengthGsm();
}
