package com.example.assignment_1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver {

    @Override

    public void onReceive(Context context, Intent intent) {

        SmsMessage[] messages = Telephony.Sms.Intents.getMessagesFromIntent(intent);
        for (int i = 0; i < messages.length; i++){

            SmsMessage currentMessage = messages[i];
            String message = currentMessage.getDisplayMessageBody();
            Intent msgIntent = new Intent();
            msgIntent.setAction(SMSKeys.SMS_FILTER);
            msgIntent.putExtra(SMSKeys.SMS_MSG_KEY, message);
            context.sendBroadcast(msgIntent);
        }

    }
}