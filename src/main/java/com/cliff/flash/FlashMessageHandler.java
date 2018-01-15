package com.cliff.flash;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.cliff.util.ConversationResponseBuilder;

public class FlashMessageHandler {
    private static final Logger log = LoggerFactory.getLogger(FlashMessageHandler.class);

	public static SpeechletResponse readFromFile(SpeechletRequestEnvelope<IntentRequest> requestEnvelope) {
    	String speechText = null;
    	Intent intent = requestEnvelope.getRequest().getIntent();
    	Slot fileName = intent.getSlot("file");
    	log.debug("Intent '{}' and slot '{}'", intent.getName(), fileName.getValue());
    	if(fileName != null)
    		speechText = FlashReader.getFileConents(FlashReader.getFilename(fileName.getValue()), 0, 0);
    	return ConversationResponseBuilder.builder().withSsmlText(Optional.of((speechText==null)?"unable to locate the file to read from":speechText)).get();
    }
	
//	private String getApiEndpoint(SpeechletRequestEnvelope<IntentRequest> requestEnvelope) {
//	Session session = requestEnvelope.getSession();
//    SystemState systemState = requestEnvelope.getContext().getState(SystemInterface.class, SystemState.class);
//    return systemState.getApiEndpoint();
//}
//private void dispatchProgressiveResponse(String requestId, String text, SystemState systemState, String apiEndpoint) {
//    DirectiveEnvelopeHeader header = DirectiveEnvelopeHeader.builder().withRequestId(requestId).build();
//    SpeakDirective directive = SpeakDirective.builder().withSpeech(text).build();
//    DirectiveEnvelope directiveEnvelope = DirectiveEnvelope.builder()
//            .withHeader(header).withDirective(directive).build();
//
//    if(systemState.getApiAccessToken() != null && !systemState.getApiAccessToken().isEmpty()) {
//        String token = systemState.getApiAccessToken();
//        try {
//        	directiveEnvelope.enqueue(directiveEnvelope, apiEndpoint, token);
//        } catch (Exception e) {
//            log.error("Failed to dispatch a progressive response", e);
//        }
//    }
//}

}
