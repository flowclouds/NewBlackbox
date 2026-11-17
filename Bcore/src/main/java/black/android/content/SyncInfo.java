package black.android.content;

import android.accounts.Account;

import com.tm.reflect.annotation.BClassName;
import com.tm.reflect.annotation.BConstructor;

@BClassName("android.content.SyncInfo")
public interface SyncInfo {
    @BConstructor
    SyncInfo _new(int int0, Account Account1, String String2, long long3);
}
