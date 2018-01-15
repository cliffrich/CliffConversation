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

public class ReadDialogHandler {
    private static final Logger log = LoggerFactory.getLogger(ReadDialogHandler.class);

	public static SpeechletResponse readFromFile(SpeechletRequestEnvelope<IntentRequest> requestEnvelope) {
        Intent intent = requestEnvelope.getRequest().getIntent();
        String intentName = (intent != null) ? intent.getName() : null;

        DialogState dialogueState = requestEnvelope.getRequest().getDialogState();
        
        log.info("onIntent from cliffConversation dialogueState={}, intentName={}", dialogueState, intentName);

        if (dialogueState.equals(DialogState.STARTED)) {
   	        DialogIntent dialogIntent = new DialogIntent();       
	        dialogIntent.setName(intentName);	        	    
	        dialogIntent.setSlots(createDialogSlotsFromExistingSlot(intent));	        	    
	        //Create a DelegateDirective
	        DelegateDirective dd = new DelegateDirective();
	        dd.setUpdatedIntent(dialogIntent);	        
	        return getStartedOrInProgressResponse(dd);
        } else if (dialogueState.equals(DialogState.COMPLETED)) {
        	log.debug("onIntent, inside DialogState.COMPLETED");
        	return readFromFile(intent);        	
	    } else { 		
	        log.debug("onIntent, inside DialogState.IN_PROGRESS");	        
	        //Create an empty DelegateDirective. This will tell the Alexa Engine to keep collecting information.	        
	        return getStartedOrInProgressResponse(new DelegateDirective());
        }
    
	}
	private static SpeechletResponse getStartedOrInProgressResponse(DelegateDirective dd) {
		List<Directive> directiveList = new ArrayList<Directive>();
        directiveList.add(dd);
        
        //Create a new SpeechletResponse and set the Directives to our List.
        SpeechletResponse speechletResp = new SpeechletResponse();
        speechletResp.setDirectives(directiveList);
        
        //Only end the session if we have all the info. Assuming we still need to 
        //get more, we keep the session open.
        speechletResp.setShouldEndSession(false); 
        
        return speechletResp;
	}
	
    private static SpeechletResponse readFromFile(Intent intent) {
        String fileName = intent.getSlot("fileOption").getValue();
        Slot fromSlot = intent.getSlot("from");
        int from = Integer.valueOf(fromSlot.getValue());
        Slot toSlot = intent.getSlot("to");
        int to = Integer.valueOf(toSlot.getValue());
    	log.debug("Inside 'readFromFile' with slot values fileName '{}', from '{}', to '{}'", new Object[] {fileName, from, to});

    	String speechText = FlashReader.getFileConents(FlashReader.getFilename(fileName), from, to);
    	return ConversationResponseBuilder.builder().withSsmlText(Optional.of((speechText==null)?"unable to locate the file to read from":speechText)).get();
    }
    
    private static Map<String,DialogSlot> createDialogSlotsFromExistingSlot(Intent intent) {
        Map<String,DialogSlot> dialogSlots = new HashMap<String,DialogSlot>();
        intent.getSlots().forEach((key, slot)->{
        	dialogSlots.put(key, new DialogSlot(slot.getName(), slot.getValue()));
        });
        return dialogSlots;
    }
    
}
