package com.cliff.music;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.amazon.speech.speechlet.Directive;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.speechlet.interfaces.audioplayer.AudioItem;
import com.amazon.speech.speechlet.interfaces.audioplayer.PlayBehavior;
import com.amazon.speech.speechlet.interfaces.audioplayer.Stream;
import com.amazon.speech.speechlet.interfaces.audioplayer.directive.PlayDirective;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.SimpleCard;


/**
 * Following urls work fine;
 * 	https://emit-media-production.s3.amazonaws.com/2mbs-d/fine-music-breakfast/2016/02/04/0600/201602040600_fine-music-breakfast_24.m4a
 * 	https://emit-media-production.s3.amazonaws.com/2mbs-d/jazz-after-hours/2016/02/10/2200/201602102200_jazz-after-hours_64.m4a
 * 	https://s3-eu-west-1.amazonaws.com/sherylcliff/sheryl-flashcard-1-5.mp3
 */
public class PlayMusicResponseBuilder implements Supplier<SpeechletResponse>{
	private String outputText; 
	List<String> urls = new ArrayList();
		
	public static PlayMusicResponseBuilder builder(){
        return new PlayMusicResponseBuilder();
    }
	
	@Override
	public SpeechletResponse get() {
        SimpleCard card = new SimpleCard();
        card.setTitle("Our Music");
        card.setContent(outputText);
        // Create the plain text output.
        PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
        outputSpeech.setText(outputText);
        //
        List<Directive> listDirectives = new ArrayList();
        listDirectives.addAll(addAudioItems());
        //
        SpeechletResponse response = new SpeechletResponse();
        response.setCard(card);
        response.setOutputSpeech(outputSpeech);
        response.setDirectives(listDirectives);
        response.setShouldEndSession(true);
        return response;
	}
	private List<PlayDirective> addAudioItems() {
		return urls.stream().map(this::createPlayDirective).collect(Collectors.toList());
	}
	
	private PlayDirective createPlayDirective(String url) {
		PlayDirective playDirective = new PlayDirective();
		Stream stream = new Stream();
        stream.setUrl(url);
        stream.setToken("12345");
        stream.setOffsetInMilliseconds(0);
        AudioItem audioItem = new AudioItem();
        audioItem.setStream(stream);
        playDirective.setAudioItem(audioItem);
        playDirective.setPlayBehavior(PlayBehavior.REPLACE_ALL);
        return playDirective;
	}
	public PlayMusicResponseBuilder withOutputText(String outputText) {
		this.outputText = outputText;
		return this;
	}
	public PlayMusicResponseBuilder withUrls(List<String> urls) {
		this.urls = urls;
		return this;
	}
}
