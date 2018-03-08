package com.cliff.countdown;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.StringJoiner;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.cliff.util.ConversationResponseBuilder;

public class CountdownHandler {
    private static final LocalDate examDate = LocalDate.of(2018, Month.MAY, 13);
    
	public static SpeechletResponse spellOutTheCountdown() {
    	String speechText = null;
    	speechText = countdown();
    	return ConversationResponseBuilder.builder().withSsmlText(Optional.of((speechText==null)?"unable to workuout your countdown":speechText)).get();
    }
	private static String countdown() {
		StringBuilder strBuilder = new StringBuilder("<speak>");
		
	    LocalDate today = LocalDate.now();
	    long numberOfDays = today.until(examDate, ChronoUnit.DAYS);
	    long numberOfMonths = today.until(examDate, ChronoUnit.MONTHS);
	    StringJoiner joiner = new StringJoiner("<break time=\"1s\"/>");
		joiner.add("Daniel, you just have ").
			add(Long.toString(numberOfDays)+" days before your exams start on the 14th of May 2018.").
			add("It's also just "+Long.toString(numberOfMonths)+"months away.").
			add("You should put your heart and soul now, if you want to come out in flying colours");
	    return strBuilder.append(joiner.toString()).append("</speak>").toString();
	}
}
