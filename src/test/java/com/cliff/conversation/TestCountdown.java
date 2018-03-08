package com.cliff.conversation;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.SsmlOutputSpeech;
import com.cliff.countdown.CountdownHandler;

public class TestCountdown {
	
	@Test
	public void testCountdown() {				
		SpeechletResponse ret = CountdownHandler.spellOutTheCountdown();
		assertNotNull(ret);
		System.out.println(((SsmlOutputSpeech)ret.getOutputSpeech()).getSsml());
		
	}
}
