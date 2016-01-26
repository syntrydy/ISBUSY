package com.example.gasmyr.isbusy.receivers;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import com.example.gasmyr.isbusy.R;
import com.example.gasmyr.isbusy.services.SMSService;
import com.example.gasmyr.isbusy.utils.Constants;
import com.example.gasmyr.isbusy.utils.PhoneNumber;

/**
 * Created by gasmyr on 05/01/16.
 */


public class SMSReceiver extends BroadcastReceiver {
    private static String SENDER_NUMBER = "";
    private static String SMS_BODY = "";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Object[] pdus = (Object[]) bundle.get("pdus");
            SmsMessage[] message = new SmsMessage[pdus.length];
            for (int j = 0; j < pdus.length; j++) {
                message[j] = SmsMessage.createFromPdu((byte[]) pdus[j]);
            }
            if (message.length > -1) {
                SMS_BODY = message[0].getMessageBody();

                SENDER_NUMBER = message[0]
                        .getDisplayOriginatingAddress();
                sharedPreferences = context.getSharedPreferences(Constants.APPLICATION_SHARE_PREF, Context.MODE_PRIVATE);
                boolean canPerformWork = sharedPreferences.getBoolean(Constants.APPLICATION_SMS_RESPONDER_IS_ACTIVATE, false);
                String messageToSend = sharedPreferences.getString(
                        Constants.APPLICATION_SMS_RESPONDER_MESSAGE, context.getResources().getString(R.string.defaultSmsMessage));
                editor=sharedPreferences.edit();
                editor.putString(Constants.PHONE, SENDER_NUMBER);
                editor.commit();
                boolean canSend=isCompatible(SENDER_NUMBER);
                if (SMS_BODY.length() >= 2 && canPerformWork && canSend) {
                        Intent i = new Intent(context, SMSService.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.putExtra(Constants.APPLICATION_SMS_RESPONDER_MESSAGE, messageToSend);
                        i.putExtra(Constants.APPLICATION_SMS_IS_SEND_TO, SENDER_NUMBER);
                        context.startService(i);
                }
            }
        }

    }

    private boolean isCompatible(String phoneNumber) {
        boolean canSend=false;
        String operator=sharedPreferences.getString(Constants.APPLICATION_USER_SIM_OPERATOR,Constants.APPLICATION_OPERATOR_ALL);
        PhoneNumber phone=new PhoneNumber(phoneNumber);
        if(operator.equalsIgnoreCase(Constants.APPLICATION_OPERATOR_MTN) && phone.isMtn()){
           canSend=true;
        }
        else if(operator.equalsIgnoreCase(Constants.APPLICATION_OPERATOR_ORANGE) && phone.isOrange()){
            canSend=true;
        }
        else if(operator.equalsIgnoreCase(Constants.APPLICATION_OPERATOR_NEXTTEL) && phone.isNexttel()){
            canSend=true;
        }
        else if(operator.equalsIgnoreCase(Constants.APPLICATION_OPERATOR_ALL)){
            canSend=true;
        }
        else{
        }
        return canSend;
    }
}
