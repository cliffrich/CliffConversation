package com.cliff.conversation;

//import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.amazon.speech.speechlet.SpeechletResponse;
import com.cliff.music.PlayMusicResponseBuilder;

public class TestPlayMusicResponseBuilder {
	
   @Test
   public void testPlayMusicResponse() {
		List<String> urls = new ArrayList();
		urls.add("https://s3-eu-west-1.amazonaws.com/sherylcliff/sheryl-flashcard-1-5.mp3");
		urls.add("https://s3-eu-west-1.amazonaws.com/sherylcliff/sheryl-flashcard-6-10.mp3");
		String outputSpeech = "From Cliff";

		SpeechletResponse response = PlayMusicResponseBuilder.builder().withOutputText(outputSpeech).withUrls(urls).get();

//		assertNotNull(response);
   }
}
