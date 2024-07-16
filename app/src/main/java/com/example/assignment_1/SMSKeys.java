package com.example.assignment_1;

public class SMSKeys {

    // used as a channel to broadcast the message
//Any application aware of this channel can listen to the broadcasts
    public static final String SMS_FILTER = "SMS_FILTER";

    //Within the broadcast, we would like to send information
// and this will be the key to identifying that information, in this case, the SMS message
    public static final String SMS_MSG_KEY = "SMS_MSG_KEY";



}
