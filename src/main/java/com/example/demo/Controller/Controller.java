package com.example.demo.Controller;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.sms.CallModel;
import com.example.demo.sms.MessageModel;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@RestController
public class Controller {
	
	private final static String SID_ACCOUNT = "AC01005a0b0f427c9ab768c5fecceeaeeb";
	private final static String AUTH_ID = "39306167f7cafb76f47bc0814a046523";
	private final static String FROM_NUMBER="+15402742934";
	
	static {
		   Twilio.init(SID_ACCOUNT, AUTH_ID);
		}
	
	@PostMapping(value = "/SendMessage")
	public void sendmsg(@RequestBody MessageModel msg)
	{
		msg.numbers.stream().forEach(number ->{
			Message message= Message.creator(new PhoneNumber(number), new PhoneNumber(FROM_NUMBER),
					   msg.message).create();
			System.out.println("Message Sent:" + message.getSid());
		});
	}
	
	@PostMapping(value = "/MakeCalls")
	public void makecalls(@RequestBody CallModel ca)
	{
		ca.numbers.stream().forEach(number ->{
		try {
			Call call = Call.creator(new PhoneNumber(number), new PhoneNumber(FROM_NUMBER),
						   new URI("http://demo.twilio.com/docs/voice.xml")).create();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		});
	}
	

}
