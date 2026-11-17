package black.android.telephony;

import android.telephony.CellIdentityCdma;
import android.telephony.CellSignalStrengthCdma;

import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BConstructor;
import com.tm.reflect.annotation.BField;

@BClassName("android.telephony.CellInfoCdma")
public interface CellInfoCdma {
    @BConstructor
    CellInfoCdma _new();

    @BField
    CellIdentityCdma mCellIdentityCdma();

    @BField
    CellSignalStrengthCdma mCellSignalStrengthCdma();
}
