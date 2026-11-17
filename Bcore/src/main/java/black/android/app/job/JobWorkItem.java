package black.android.app.job;

import android.content.Intent;

import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BConstructor;
import com.tm.reflect.annotation.BField;
import com.tm.reflect.annotation.BMethod;

@BClassName("android.app.job.JobWorkItem")
public interface JobWorkItem {
    @BConstructor
    JobWorkItem _new(Intent Intent0);

    @BField
    int mDeliveryCount();

    @BField
    Object mGrants();

    @BField
    int mWorkId();

    @BMethod
    Intent getIntent();
}
