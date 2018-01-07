package com.cliff.util;

import java.util.Optional;
import java.util.function.Supplier;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;
import com.amazon.speech.ui.SsmlOutputSpeech;
import com.cliff.guest.GuestMessage;

public class ConversationResponseBuilder implements Supplier<SpeechletResponse>{
	private Optional<String> speechText = Optional.empty(), ssmlText = Optional.empty(), repromptText = Optional.empty(); 
	
	private static final String REPROMPT_MESSAGE = "I didn't recognise the name you mentioned. please can you say again?";
	
	public static ConversationResponseBuilder builder(){
        return new ConversationResponseBuilder();
    }
	
	@Override
	public SpeechletResponse get() {
		if(speechText.isPresent())
			return getPlainTextSpeech();
		else
			return getSsmlSpeech();
	}
	private SpeechletResponse getPlainTextSpeech() {
        SimpleCard card = new SimpleCard();
        card.setTitle("Our home");
        card.setContent(speechText.get());
        // Create the plain text output.
        PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
    	outputSpeech.setText(speechText.get());
        if (repromptText != null) {
            PlainTextOutputSpeech repromptSpeech = new PlainTextOutputSpeech();
            repromptSpeech.setText(repromptText.get());
            Reprompt reprompt = new Reprompt();
            reprompt.setOutputSpeech(repromptSpeech);
            return SpeechletResponse.newAskResponse(outputSpeech, reprompt, card);
        } else {
            return SpeechletResponse.newTellResponse(outputSpeech, card);
        }
	}
	
	private SpeechletResponse getSsmlSpeech() {
        SsmlOutputSpeech outputSpeech = new SsmlOutputSpeech();
    	outputSpeech.setSsml(ssmlText.get());
    	return SpeechletResponse.newTellResponse(outputSpeech);
	}
	public ConversationResponseBuilder withSpeechText(Optional<String> speechText) {
		this.speechText = speechText;
		if(speechText.equals(GuestMessage.REPROMPT))
			this.repromptText = Optional.of(REPROMPT_MESSAGE);
		return this;
	}
	
	public ConversationResponseBuilder withSsmlText(Optional<String> ssmlText) {
		this.ssmlText = ssmlText;
		return this;
	}
	
	public ConversationResponseBuilder withRepromptText(Optional<String> repromptText) {
		this.repromptText = repromptText;
		return this;
	}
}
