package black.android.media;

import android.os.IInterface;

import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BField;
import com.tm.reflect.annotation.BStaticField;

@BClassName("android.media.MediaRouter")
public interface MediaRouter {
    @BStaticField
    Object sStatic();

    @BClassName("android.media.MediaRouter$Static")
    interface StaticKitkat {
        @BField
        IInterface mMediaRouterService();
    }

    @BClassName("android.media.MediaRouter$Static")
    interface Static {
        @BField
        IInterface mAudioService();
    }
}
