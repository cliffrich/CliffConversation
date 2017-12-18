package com.cliff.guest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GuestMessage {
    private static final Logger logger = LogManager.getLogger(GuestMessage.class);

	public static String aboutTheGuest(String guestName) {
		logger.debug("Processing GuestMessage for {}", guestName);
		if(guestName.contains("sheryl"))
			return "welcome sheryl Have you done anything useful today";
		else if(guestName.contains("daniel"))
			return "welcome daniel Have you cleaned your ears";
		else if(guestName.contains("ruby"))
			return "welcome ruby Have I got something to eat";
		return "welcome rubbish " + guestName;
	}

}
