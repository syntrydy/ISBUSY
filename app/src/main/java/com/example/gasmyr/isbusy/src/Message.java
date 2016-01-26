package com.example.gasmyr.isbusy.src;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.example.gasmyr.isbusy.utils.PhoneNumber;

import java.util.ArrayList;

/**
 * Created by gasmyr on 05/01/16.
 */
public class Message {
    private SmsManager smsManager;
    private ArrayList<String> messageParts;
    public Message() {
        this.smsManager = SmsManager.getDefault();
    }

    public  void sendMessage( String number,String fullMessage){
        if(fullMessage.length()>=140){
            messageParts=this.smsManager.divideMessage(fullMessage);
            this.smsManager.sendMultipartTextMessage(number,null,messageParts,null,null);
        }
        else{
            this.smsManager.sendTextMessage(number, null,fullMessage, null, null);
        }
    }
}
