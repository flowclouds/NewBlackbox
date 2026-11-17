package black.android.app.job;

import android.os.IBinder;
import android.os.PersistableBundle;

import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BField;

@BClassName("android.app.job.JobParameters")
public interface JobParameters {
    @BField
    IBinder callback();

    @BField
    PersistableBundle extras();

    @BField
    int jobId();
}
