package com.example.gasmyr.isbusy.services;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import com.example.gasmyr.isbusy.R;
import com.example.gasmyr.isbusy.src.Licence;
import com.example.gasmyr.isbusy.src.Message;
import com.example.gasmyr.isbusy.utils.Constants;
import com.example.gasmyr.isbusy.utils.PhoneNumber;

import java.util.Date;

/**
 * Created by gasmyr on 05/01/16.
 */


public class SMSService extends Service {

    private static String SMS_SEND_TO ="";
    private static  String SMS_TO_SEND ="" ;

    @Override
    public IBinder onBind(Intent intent) {
        return null;

    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle = intent.getExtras();
        SMS_TO_SEND = bundle.getString(Constants.APPLICATION_SMS_RESPONDER_MESSAGE);
        SMS_SEND_TO=bundle.getString(Constants.APPLICATION_SMS_IS_SEND_TO);
           Message message=new Message();
           message.sendMessage(SMS_SEND_TO, SMS_TO_SEND.concat(" ").concat(getResources().getString(R.string.computeTimme)).
                   concat(computationDate()));
        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }
    private String computationDate(){
        Date now=new Date();
        String date=" ".concat(now.getHours() + "h:" + now.getMinutes() + "m:" + now.getSeconds() + "s .");
        return date;
    }

}
