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
		//
		guestInfo.put("sheryl", "sheryl Have you done anything useful today");
		guestInfo.put("ruby", "Hi ruby Have I got something to eat");
		guestInfo.put("daniel", "welcome daniel Have you cleaned your ears");
		guestInfo.put("wifi", 
				"<speak>"
						+ "<amazon:effect name=\"whispered\">I want to tell you a secret.</amazon:effect>."
						+ "Have you got your pen and paper?<break time=\"10s\"/>"
						+ "<say-as interpret-as=\"spell-out\">7<break time=\"5s\"/>w<break time=\"5s\"/>7<break time=\"5s\"/>p<break time=\"5s\"/>3<break time=\"5s\"/>v<break time=\"5s\"/>m<break time=\"5s\"/>p<break time=\"5s\"/>k<break time=\"5s\"/>z<break time=\"5s\"/>z<break time=\"5s\"/>j<break time=\"5s\"/>z<break time=\"5s\"/>k<break time=\"5s\"/>6</say-as>."
						+ "<emphasis level=\"strong\">hope you noted down the wifi password. if not, ask again</emphasis>"
				+ "</speak>");
    }
	
	public static String aboutTheGuest(String guestName) {
		if(guestsAndKey.get(guestName) == null)
			return "reprompt";
		return guestInfo.get(guestsAndKey.get(guestName));
	}
	
	public static String getInformationAboutOurHome(String about) {
		return guestInfo.get(about);
	}
}
