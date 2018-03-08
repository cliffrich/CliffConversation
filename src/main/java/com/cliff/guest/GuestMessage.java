package com.cliff.guest;

import java.util.HashMap;
import java.util.Map;

public class GuestMessage {
	private static final Map<String, String> guestsAndKey = new HashMap<>();
	private static final Map<String, String> guestInfo = new HashMap<>();
	public static final String REPROMPT ="reprompt";

	static {
		guestsAndKey.put("cheryl", "sheryl");
		guestsAndKey.put("sheryl", "sheryl");
		guestsAndKey.put("bubble", "sheryl");
		guestsAndKey.put("ruby", "ruby");
		guestsAndKey.put("rooby", "ruby");
		guestsAndKey.put("daniel", "daniel");
		guestsAndKey.put("jay", "jay");

		//
		guestInfo.put("sheryl", "sheryl Have you done anything useful today");
		guestInfo.put("ruby", "Hi ruby Have I got something to eat");
		guestInfo.put("daniel", "welcome daniel Have you cleaned your ears");
		guestInfo.put("jay", "welcome jayasingh.Jaysingh is also called the mustache man of reading. A very handsome guy, I can say, he is the heart-throb of reading university.");
		guestInfo.put("wifi", 
				"<speak>"
						+ "<amazon:effect name=\"whispered\">I want to tell you a secret.</amazon:effect><break time=\"2s\"/>"
						+ "Have you got your pen and paper?<break time=\"5s\"/>"
						+ "<say-as interpret-as=\"spell-out\">7w7<break time=\"3s\"/>p3v<break time=\"3s\"/>mpk<break time=\"3s\"/>zzj<break time=\"3s\"/>zk6</say-as>."
						+ "<emphasis level=\"strong\">hope you noted down the wifi password. if not, ask again</emphasis>"
				+ "</speak>");
    }
	
	public static String aboutTheGuest(String guestName) {
		if(guestsAndKey.get(guestName) == null)
			return REPROMPT+guestName;
		return guestInfo.get(guestsAndKey.get(guestName));
	}
	
	public static String getInformationAboutOurHome(String about) {
		return guestInfo.get(about);
	}
}
