package com.digipay.digipay;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by AGADA on 12/12/2017.
 */

public class smsreciever extends BroadcastReceiver {
    public static final String sms_bundle = "pdus";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle intentextras = intent.getExtras();

        if (intentextras !=null){
            Object[] sms = (Object[]) intentextras.get(sms_bundle);
             String smsmessage = "";

             for (int i=0; i< sms.length;i++){
                 SmsMessage smsMessage_ = SmsMessage.createFromPdu((byte[])sms[i]);
                  String smsbody = smsMessage_.getMessageBody().toString();
                 String address = smsMessage_.getOriginatingAddress();
                 long raw_date= smsMessage_.getTimestampMillis();
                 Date date = new Date(raw_date);

                 SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
                 String final_date = format.format(date);

                 if (address.equals("MobileMoney")){
                     smsmessage += "SMS From: "+address + "\n"+"Date: " + final_date + "\n\n";
                     smsmessage += smsbody + "\n";
                     Toast.makeText(context,"Message Received",Toast.LENGTH_SHORT).show();
                     MainActivity inst = MainActivity.instance().instance();
                     if (inst!=null){
                         inst.updateList(smsmessage);
                     }
                 }


             }


            MainActivity inst = MainActivity.instance().instance();
            if (inst!=null){
                inst.updateList(smsmessage);
            }
        }
    }
}
