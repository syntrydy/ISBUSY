package com.example.gasmyr.isbusy.receivers;

/**
 * Created by gasmyr on 25/05/15.
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.example.gasmyr.isbusy.utils.Constants;

public class CallReceiver extends BroadcastReceiver {
    private  MyPhoneStateListener myPhoneStateListener;
    private SharedPreferences sharedPreferences;
    private TelephonyManager telephonyManager;
    private  static boolean bool=false;

    @Override
    public void onReceive(Context context, Intent intent){
        myPhoneStateListener=new MyPhoneStateListener(context);
        telephonyManager=(TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        sharedPreferences=context.getSharedPreferences(Constants.APPLICATION_SHARE_PREF,Context.MODE_PRIVATE);
        boolean canPerformWork = sharedPreferences.getBoolean(Constants.APPLICATION_CALL_RESPONDER_IS_ACTIVATE, false);
        if(!bool && canPerformWork){
            telephonyManager.listen(myPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
            bool=true;
        }

    }
}
