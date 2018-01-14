package com.cliff.flash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.Directive;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.speechlet.IntentRequest.DialogState;
import com.amazon.speech.speechlet.dialog.directives.DelegateDirective;
import com.amazon.speech.speechlet.dialog.directives.DialogIntent;
import com.amazon.speech.speechlet.dialog.directives.DialogSlot;
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
	
	public static SpeechletResponse doDialogResponse(SpeechletRequestEnvelope<IntentRequest> requestEnvelope) {
    	log.debug("inside doDialogResponse..............");

		IntentRequest request = requestEnvelope.getRequest();
    	log.debug("Current dialog state '{}'", request.getDialogState());

		if(request.getDialogState() == null) {
//			DelegateDirective delegateDirective = new DelegateDirective();
//			DialogIntent updatedIntent = new DialogIntent(request.getIntent());
//	        Map<String, DialogSlot> slots = new HashMap();
//	        DialogSlot slotFileId = new DialogSlot();
//	        slotFileId.setName("fileOption");
//	        slotFileId.setValue("3");
//	        
//	        DialogSlot slotFrom = new DialogSlot();
//	        slotFrom.setName("from");
//	        slotFrom.setValue("0");
//	        
//	        DialogSlot slotTo = new DialogSlot();
//	        slotTo.setName("to");
//	        slotTo.setValue("0");
//	        
//	        slots.put("fileOption", slotFileId);
//	        slots.put("from", slotFrom);
//	        slots.put("to", slotTo);
//
//	        updatedIntent.setSlots(slots);
//	        delegateDirective.setUpdatedIntent(updatedIntent);
//	        
//	        List<Directive> directives = new ArrayList<>();
//	        directives.add(delegateDirective);
//
//	        // Create SpeechletResponse
//	        SpeechletResponse response = new SpeechletResponse();
//	        response.setDirectives(directives);
//	        response.setNullableShouldEndSession(false);
//	        response.setShouldEndSession(false);
//	        return response;
			log.debug("onIntent, inside dialogueState null scenario.....");
            DelegateDirective dd = new DelegateDirective();

            List<Directive> directiveList = new ArrayList<Directive>();
            directiveList.add(dd);

            SpeechletResponse speechletResp = new SpeechletResponse();
            speechletResp.setDirectives(directiveList);
            speechletResp.setShouldEndSession(false); 
            return speechletResp;
		}
	    if (request.getDialogState() != DialogState.COMPLETED) {
	    	log.debug("Current dialog state '{}'", request.getDialogState());
	    	
	        DelegateDirective delegateDirective = new DelegateDirective();

	        // Create updatedIntent and prefill information if needed
	    	if (request.getDialogState() == DialogState.STARTED) {
	    		log.debug("Inside Started state. will try to prefill slots with the values ..............");
		        DialogIntent updatedIntent = new DialogIntent(request.getIntent());
		        Map<String, DialogSlot> slots = new HashMap();
		        DialogSlot slotFileId = new DialogSlot();
		        slotFileId.setName("fileOption");
		        slotFileId.setValue("3");
		        
		        DialogSlot slotFrom = new DialogSlot();
		        slotFrom.setName("from");
		        slotFrom.setValue("0");
		        
		        DialogSlot slotTo = new DialogSlot();
		        slotTo.setName("to");
		        slotTo.setValue("0");
		        
		        slots.put("fileOption", slotFileId);
		        slots.put("from", slotFrom);
		        slots.put("to", slotTo);

		        updatedIntent.setSlots(slots);
		        delegateDirective.setUpdatedIntent(updatedIntent);
	    	}

	        List<Directive> directives = new ArrayList<>();
	        directives.add(delegateDirective);

	        // Create SpeechletResponse
	        SpeechletResponse response = new SpeechletResponse();
	        response.setDirectives(directives);
	        response.setNullableShouldEndSession(false);
	        return response;
	    } else {
	        String speechText = null;
	        String fileNameId = request.getIntent().getSlot("fileOption").getValue();
	        Slot fromSlot = request.getIntent().getSlot("from");
	        int from = Integer.valueOf(fromSlot.getValue());
	        Slot toSlot = request.getIntent().getSlot("to");
	        int to = Integer.valueOf(toSlot.getValue());
	    	log.debug("fileName '{}', from '{}', to '{}'", new Object[] {fileNameId, from, to});
	    	speechText = FlashReader.getFileConents(FlashReader.getFilename(fileNameId), from, to);
	    	return ConversationResponseBuilder.builder().withSsmlText(Optional.of((speechText==null)?"unable to locate the file to read from":speechText)).get();
	    }    
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
