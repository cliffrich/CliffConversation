package com.cliff.conversation;

import java.util.HashSet;
import java.util.Set;
import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;

public class CliffConversationHandler extends SpeechletRequestStreamHandler {
    private static final Set<String> supportedApplicationIds = new HashSet<String>();
    
    static {
        /*
         * This Id can be found on https://developer.amazon.com/edw/home.html#/ "Edit" the relevant
         * Alexa Skill and put the relevant Application Ids in this Set.
         */
        supportedApplicationIds.add("amzn1.ask.skill.e3d70b3b-5645-4e43-bf5d-4b0f281f1a0e");
    }

    public CliffConversationHandler() {
		super(new CliffConversationSpeechlet(), supportedApplicationIds);
	}
}
