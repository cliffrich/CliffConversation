package com.cliff.music;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.SpeechletResponse;

public class PlayMusicMessageHandler {
    private static final Logger log = LoggerFactory.getLogger(PlayMusicMessageHandler.class);

	public static SpeechletResponse playMusic(Intent intent) {
		List<String> urls = new ArrayList();
		urls.add("https://s3-eu-west-1.amazonaws.com/sherylcliff/sheryl-flashcard-1-5.mp3");
//		urls.add("https://s3-eu-west-1.amazonaws.com/sherylcliff/sheryl-flashcard-6-10.mp3");
    	String outputSpeech = "From Cliff";
//    	Slot slotStation = intent.getSlot("station");
    	log.debug("Intent '{}' and slot '{}'", intent.getName(), null);

    	return PlayMusicResponseBuilder.builder().withOutputText(outputSpeech).withUrls(urls).get();
    }
}
