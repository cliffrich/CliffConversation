package com.cliff.guest;

import java.util.Optional;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.cliff.util.ConversationResponseBuilder;

public class GuestMessagesHandler {
	
	public static SpeechletResponse aboutTheGuestResponse(Intent intent) {
		String speechText = "welcome mate";
    	Slot slotGuest = intent.getSlot("guest");
//    	log.debug("Intent '{}' and slot '{}'", intent.getName(), slotGuest.getValue());

    	if(slotGuest != null)
    		speechText = GuestMessage.aboutTheGuest(slotGuest.getValue());
    	return ConversationResponseBuilder.builder().withSpeechText(Optional.of(speechText)).get();
	}
	
	public static SpeechletResponse getWifiInfo() {
		return ConversationResponseBuilder.builder().withSsmlText(Optional.of(GuestMessage.getInformationAboutOurHome("wifi"))).get();
	}
}
