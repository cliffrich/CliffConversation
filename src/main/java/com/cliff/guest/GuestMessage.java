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
    }
	
	public static String aboutTheGuest(String guestName) {
		if(guestsAndKey.get(guestName) == null)
			return "reprompt";
		return guestInfo.get(guestsAndKey.get(guestName));
	}

}
