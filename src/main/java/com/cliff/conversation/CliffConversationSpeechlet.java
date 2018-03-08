package com.cliff.conversation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.speechlet.SpeechletV2;
import com.amazon.speech.speechlet.interfaces.audioplayer.AudioPlayer;
import com.amazon.speech.speechlet.interfaces.audioplayer.request.PlaybackFailedRequest;
import com.amazon.speech.speechlet.interfaces.audioplayer.request.PlaybackFinishedRequest;
import com.amazon.speech.speechlet.interfaces.audioplayer.request.PlaybackNearlyFinishedRequest;
import com.amazon.speech.speechlet.interfaces.audioplayer.request.PlaybackStartedRequest;
import com.amazon.speech.speechlet.interfaces.audioplayer.request.PlaybackStoppedRequest;
import com.cliff.countdown.CountdownHandler;
import com.cliff.flash.FlashMessageHandler;
import com.cliff.flash.ReadDialogHandler;
import com.cliff.guest.GuestMessagesHandler;
import com.cliff.music.PlayMusicMessageHandler;
import com.cliff.repeat.RepeatDialogHandler;

public class CliffConversationSpeechlet implements AudioPlayer, SpeechletV2{
    private static final String ABOUT_THE_GUEST = "AboutTheGuest";
    private static final String GUEST_INFO = "GuestInfo";
    private static final String PLAY_MUSIC = "PlayMusic";
    private static final String READ_FLASH = "ReadFlash";
    private static final String CONVERSE = "Converse";
    private static final String REPEAT = "Repeat";
    private static final String COUNTDOWN = "ExamCountdown";
    
    private static final Logger log = LoggerFactory.getLogger(CliffConversationSpeechlet.class);

	@Override
	public SpeechletResponse onIntent(SpeechletRequestEnvelope<IntentRequest> requestEnvelope) {
        Intent intent = requestEnvelope.getRequest().getIntent();
        String intentName = (intent != null) ? intent.getName() : null;

        if (PLAY_MUSIC.equals(intentName))
        	return PlayMusicMessageHandler.playMusic(intent);
        else if (ABOUT_THE_GUEST.equals(intentName)) 
            return GuestMessagesHandler.aboutTheGuestResponse(intent);
        else if (GUEST_INFO.equals(intentName))
        	return GuestMessagesHandler.getWifiInfo();
        else if (REPEAT.equals(intentName))
        	return RepeatDialogHandler.repeat(requestEnvelope);
        else if(READ_FLASH.equals(intentName))
        	return FlashMessageHandler.readFromFile(requestEnvelope);
        else if(CONVERSE.equals(intentName))
        	return ReadDialogHandler.readFromFile(requestEnvelope);
        else if(COUNTDOWN.equals(intentName)) {
        	return CountdownHandler.spellOutTheCountdown();
        }
        log.debug("Didn't match any intent... '{}'", intentName);
        return null;
	}

	@Override
	public SpeechletResponse onPlaybackFailed(SpeechletRequestEnvelope<PlaybackFailedRequest> requestEnvelope) {
    	log.debug("inside onPlaybackFailed .....{} ", requestEnvelope.getRequest().getError().getMessage());
		return null;
	}

	@Override
	public SpeechletResponse onPlaybackFinished(SpeechletRequestEnvelope<PlaybackFinishedRequest> requestEnvelope) {
		return null;
	}

	@Override
	public SpeechletResponse onPlaybackNearlyFinished(SpeechletRequestEnvelope<PlaybackNearlyFinishedRequest> requestEnvelope) {
		return null;
	}

	@Override
	public SpeechletResponse onPlaybackStarted(SpeechletRequestEnvelope<PlaybackStartedRequest> requestEnvelope) {
		return null;
	}

	@Override
	public SpeechletResponse onPlaybackStopped(SpeechletRequestEnvelope<PlaybackStoppedRequest> requestEnvelope) {
		return null;
	}
	
	@Override
	public void onSessionStarted(SpeechletRequestEnvelope<SessionStartedRequest> requestEnvelope) {		
	}

	@Override
	public SpeechletResponse onLaunch(SpeechletRequestEnvelope<LaunchRequest> requestEnvelope) {
		return null;
	}

	@Override
	public void onSessionEnded(SpeechletRequestEnvelope<SessionEndedRequest> requestEnvelope) {
		log.debug("inside onSessionEnded ..... error '{}'", (requestEnvelope.getRequest().getError() != null)?requestEnvelope.getRequest().getError().getMessage():"no reported error");		
	}
}
