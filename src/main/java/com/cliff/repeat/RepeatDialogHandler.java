package com.cliff.repeat;

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
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.cliff.util.ConversationResponseBuilder;

public class RepeatDialogHandler {
    private static final Logger log = LoggerFactory.getLogger(RepeatDialogHandler.class);
    private static final String PHRASE = "phrase";
//    public static SpeechletResponse repeat(Intent) {
//        // Get a random space fact from the space facts list
//        int factIndex = (int) Math.floor(Math.random() * SPACE_FACTS.length);
//        String fact = SPACE_FACTS[factIndex];
//
//        // Create com.amazon.speech output
//        String speechText = "Cliff, here's your space fact: " + fact;
//
//        // Create the Simple card content.
//        SimpleCard card = new SimpleCard();
//        card.setTitle("SpaceGeek");
//        card.setContent(speechText);
//
//        // Create the plain text output.
//        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
//        speech.setText(speechText);
//
//        return SpeechletResponse.newTellResponse(speech, card);
//    }
	public static SpeechletResponse repeat(SpeechletRequestEnvelope<IntentRequest> requestEnvelope) {
        Intent intent = requestEnvelope.getRequest().getIntent();
        String intentName = (intent != null) ? intent.getName() : null;

        DialogState dialogueState = requestEnvelope.getRequest().getDialogState();
        
        log.info("dialogueState={}, intentName={}", dialogueState, intentName);

        if (dialogueState.equals(DialogState.STARTED)) {
   	        DialogIntent dialogIntent = new DialogIntent();       
	        dialogIntent.setName(intentName);	        	    
	        dialogIntent.setSlots(createDialogSlotsFromExistingSlot(intent, dialogueState));	        	    
	        //Create a DelegateDirective
	        DelegateDirective dd = new DelegateDirective();
	        dd.setUpdatedIntent(dialogIntent);	        
	        return getStartedOrInProgressResponse(dd, Optional.empty());
        } else if (dialogueState.equals(DialogState.COMPLETED)) {
        	log.debug("onIntent, inside DialogState.COMPLETED");
        	return completTheConversation(intent);        	
	    } else { 		
        	Slot slot = intent.getSlot(PHRASE);
	        log.debug("You said '{}'", slot.getValue());	        

  	        DialogIntent dialogIntent = new DialogIntent();       
	        dialogIntent.setName(intentName);	        	    
	        dialogIntent.setSlots(createDialogSlotsFromExistingSlot(intent, dialogueState));	        	    
	        //Create a DelegateDirective
	        DelegateDirective dd = new DelegateDirective();
	        dd.setUpdatedIntent(dialogIntent);	        
        	String speechText = "<speak>"+ slot.getValue()+"<break time=\"2s\"/></speak>";
        	SpeechletResponse speechletResp = ConversationResponseBuilder.builder()
        			.withSsmlText(Optional.of((speechText==null)?"nothing to repeat":speechText))
        			.withRepromptText(Optional.of("say some more")).get();
        	
//        	List<Directive> directiveList = new ArrayList<Directive>();
//            directiveList.add(dd);
//            speechletResp.setDirectives(directiveList);
//        	speechletResp.setShouldEndSession(false); 
        	return speechletResp;
//	        return getStartedOrInProgressResponse(dd, Optional.of(slot.getValue()));
        }
    
	}
	private static SpeechletResponse getStartedOrInProgressResponse(DelegateDirective dd, Optional<String> speechText) {
		List<Directive> directiveList = new ArrayList<Directive>();
        directiveList.add(dd);
        
        //Create a new SpeechletResponse and set the Directives to our List.
        SpeechletResponse speechletResp = new SpeechletResponse();
        speechletResp.setDirectives(directiveList);
        
        if(speechText.isPresent()) {
        	PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
        	outputSpeech.setText(speechText.get().concat(" say some more"));
        	speechletResp.setOutputSpeech(outputSpeech);
        }
        
        //Only end the session if we have all the info. Assuming we still need to get more, we keep the session open.
        speechletResp.setShouldEndSession(false); 
        
        return speechletResp;
	}
	
    private static SpeechletResponse completTheConversation(Intent intent) {
    	return ConversationResponseBuilder.builder().withSsmlText(Optional.of("Thanks talking to me. Good bye")).get();
    }
    
    private static Map<String,DialogSlot> createDialogSlotsFromExistingSlot(Intent intent, DialogState state) {
        Map<String,DialogSlot> dialogSlots = new HashMap<String,DialogSlot>();
        intent.getSlots().forEach((key, slot)->{
        	dialogSlots.put(key, new DialogSlot(slot.getName(), state.equals(DialogState.STARTED)?slot.getValue():null));
        });
        return dialogSlots;
    }
    
}
