package com.example.gasmyr.isbusy.receivers;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.example.gasmyr.isbusy.R;
import com.example.gasmyr.isbusy.services.SMSService;
import com.example.gasmyr.isbusy.utils.Constants;
import com.example.gasmyr.isbusy.utils.PhoneNumber;

/**
 * Created by gasmyr on 05/01/16.
 */
public class MyPhoneStateListener extends PhoneStateListener {
    private SharedPreferences sharedPreferences;
    private Context context;
    public MyPhoneStateListener(Context context){
        super();
        this.context=context;
        init();
    }
    @Override
    public void onCallStateChanged(int state, String number){
        super.onCallStateChanged(state, number);

        switch (state){
            case TelephonyManager.CALL_STATE_IDLE:
                break;
            case TelephonyManager.CALL_STATE_RINGING:
                alertBusyEmergencyMessage(number);
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                break;
            default:
                break;
        }
    }

    public void init(){
        sharedPreferences=context.getSharedPreferences(Constants.APPLICATION_SHARE_PREF,Context.MODE_PRIVATE);
    }
    private void alertBusyEmergencyMessage(String phoneNumber) {
        init();
        String operator=sharedPreferences.getString(Constants.APPLICATION_USER_SIM_OPERATOR,Constants.APPLICATION_OPERATOR_ALL);
        PhoneNumber phone=new PhoneNumber(phoneNumber);
        if(operator.equalsIgnoreCase(Constants.APPLICATION_OPERATOR_MTN) && phone.isMtn()){
            loadService(phoneNumber);
        }
       else if(operator.equalsIgnoreCase(Constants.APPLICATION_OPERATOR_ORANGE) && phone.isOrange()){
            loadService(phoneNumber);
        }
        else if(operator.equalsIgnoreCase(Constants.APPLICATION_OPERATOR_NEXTTEL) && phone.isNexttel()){
            loadService(phoneNumber);
        }
        else if(operator.equalsIgnoreCase(Constants.APPLICATION_OPERATOR_ALL)){
            loadService(phoneNumber);
        }
        else{
        }
    }

    private void loadService(String phoneNumber){
        String messageToSend=sharedPreferences.getString(
                Constants.APPLICATION_CALL_RESPONDER_MESSAGE,context.getResources().getString(R.string.defaultCallMessage));
        Intent i = new Intent(context, SMSService.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra(Constants.APPLICATION_SMS_RESPONDER_MESSAGE, messageToSend);
        i.putExtra(Constants.APPLICATION_SMS_IS_SEND_TO, phoneNumber);
        context.startService(i);
    }

}
