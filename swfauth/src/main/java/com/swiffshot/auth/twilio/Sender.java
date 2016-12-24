package com.swiffshot.auth.twilio;

import com.twilio.sdk.creator.api.v2010.account.MessageCreator;
import com.twilio.sdk.type.PhoneNumber;


//messaging id  id:  secret:
public class Sender
{

    public void send(String to, String message)
    {
	new MessageCreator("", new PhoneNumber(to), new PhoneNumber("+17209032989"), message) ;
    }
}
