package com.cliff.conversation;

import java.util.function.Supplier;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;

public class ConversationResponseBuilder implements Supplier<SpeechletResponse>{
	private String speechText, repromptText; 
	
	public static ConversationResponseBuilder builder(){
        return new ConversationResponseBuilder();
    }
	
	@Override
	public SpeechletResponse get() {
        SimpleCard card = new SimpleCard();
        card.setTitle("Our home");
        card.setContent(speechText);
        // Create the plain text output.
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);
        if (repromptText != null) {
            PlainTextOutputSpeech repromptSpeech = new PlainTextOutputSpeech();
            repromptSpeech.setText(repromptText);
            Reprompt reprompt = new Reprompt();
            reprompt.setOutputSpeech(repromptSpeech);
            return SpeechletResponse.newAskResponse(speech, reprompt, card);
        } else {
            return SpeechletResponse.newTellResponse(speech, card);
        }
	}
	
	public ConversationResponseBuilder withSpeechText(String speechText) {
		this.speechText = speechText;
		return this;
	}
	
	public ConversationResponseBuilder withRepromptText(String repromptText) {
		this.repromptText = repromptText;
		return this;
	}
}
