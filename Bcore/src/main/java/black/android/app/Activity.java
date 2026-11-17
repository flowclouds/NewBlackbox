package black.android.app;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.IBinder;

import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BField;
import com.tm.reflect.annotation.BMethod;

@BClassName("android.app.Activity")
public interface Activity {
    @BField
    ActivityInfo mActivityInfo();

    @BField
    String mEmbeddedID();

    @BField
    boolean mFinished();

    @BField
    android.app.Activity mParent();

    @BField
    int mResultCode();

    @BField
    Intent mResultData();

    @BField
    IBinder mToken();

    @BMethod
    void onActivityResult(int int0, int int1, Intent Intent2);
}
