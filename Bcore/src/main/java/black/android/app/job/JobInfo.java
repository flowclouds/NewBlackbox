package black.android.app.job;

import android.content.ComponentName;

import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BField;

@BClassName("android.app.job.JobInfo")
public interface JobInfo {
    @BField
    long flexMillis();

    @BField
    long intervalMillis();

    @BField
    int jobId();

    @BField
    ComponentName service();
}
