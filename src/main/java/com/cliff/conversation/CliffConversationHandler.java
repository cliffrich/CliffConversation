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
        supportedApplicationIds.add("amzn1.ask.skill.c0e7e855-4bbd-4919-834e-dc37128bfd3a");
        supportedApplicationIds.add("amzn1.ask.skill.93804836-1c51-401b-ab18-7a533ec34ed3");
    }

    public CliffConversationHandler() {
		super(new CliffConversationSpeechlet(), supportedApplicationIds);
	}
}
