package com.swiffshot.auth.twilio;

import com.twilio.sdk.creator.api.v2010.account.MessageCreator;
import com.twilio.sdk.type.PhoneNumber;

public class Sender
{

    public void send(String to, String message)
    {
	new MessageCreator("AC6388e03b75867a5381d99387a8ad3270", new PhoneNumber(to), new PhoneNumber("+17209032989"), message) ;
    }
}
