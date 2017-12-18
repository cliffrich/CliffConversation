package com.cliff.conversation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.Speechlet;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.cliff.guest.GuestMessage;

public class CliffConversationSpeechlet implements Speechlet {
    private static final Logger log = LoggerFactory.getLogger(CliffConversationSpeechlet.class);
    private static final String ABOUT_THE_GUEST = "AboutTheGuest";
    @Override
    public void onSessionStarted(final SessionStartedRequest request, final Session session)
            throws SpeechletException {
        log.info("onSessionStarted requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());
        // any initialization logic goes here
    }

    @Override
    public SpeechletResponse onLaunch(final LaunchRequest request, final Session session) throws SpeechletException {
        log.info("onLaunch requestId={}, sessionId={}", request.getRequestId(), session.getSessionId());
        return getWelcomeResponse();
    }

    @Override
    public SpeechletResponse onIntent(final IntentRequest request, final Session session) throws SpeechletException {
        log.info("onIntent requestId={}, sessionId={}", request.getRequestId(), session.getSessionId());

        Intent intent = request.getIntent();
        String intentName = (intent != null) ? intent.getName() : null;

        if (ABOUT_THE_GUEST.equals(intentName)) 
            return aboutTheGuestResponse(intent);
        else if ("AMAZON.StopIntent".equals(intentName) || "AMAZON.CancelIntent".equals(intentName)) 
            return ConversationResponseBuilder.builder().withSpeechText("Goodbye").get();
        else 
            throw new SpeechletException("Invalid Intent");
    }

    @Override
    public void onSessionEnded(final SessionEndedRequest request, final Session session) throws SpeechletException {
        log.info("onSessionEnded requestId={}, sessionId={}", request.getRequestId(), session.getSessionId());
        // any cleanup logic goes here
    }

    private SpeechletResponse getWelcomeResponse() {
        String speechText = "Welcome to our home, what can I do for you";
        return ConversationResponseBuilder.builder().withSpeechText(speechText).withRepromptText(speechText).get();
    }


    private SpeechletResponse aboutTheGuestResponse(Intent intent) {
    	String speechText = "welcome mate";
    	Slot slotGuest = intent.getSlot("guest");
    	if(slotGuest != null)
    		speechText = GuestMessage.aboutTheGuest(slotGuest.getValue());
        return ConversationResponseBuilder.builder().withSpeechText(speechText).get();
    }
}
