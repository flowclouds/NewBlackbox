package com.tm.box.proxy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;

import com.tm.box.BlackBoxCore;
import com.tm.box.entity.am.PendingResultData;
import com.tm.box.proxy.record.ProxyBroadcastRecord;


public class ProxyBroadcastReceiver extends BroadcastReceiver {
    public static final String TAG = "ProxyBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        intent.setExtrasClassLoader(context.getClassLoader());
        ProxyBroadcastRecord record = ProxyBroadcastRecord.create(intent);
        if (record.mIntent == null) {
            return;
        }
        PendingResult pendingResult = goAsync();
        try {
            BlackBoxCore.getBActivityManager().scheduleBroadcastReceiver(record.mIntent, new PendingResultData(pendingResult), record.mUserId);
        } catch (RemoteException e) {
            pendingResult.finish();
        }
    }
}