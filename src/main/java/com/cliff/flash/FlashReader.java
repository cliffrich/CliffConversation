package com.cliff.flash;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import com.cliff.util.JsonArrayStreamDataSupplier;

public class FlashReader {
	private static final Map<String, String> validFiles = new HashMap<>();
	private static final int totalNoOfRecords = 50;
	
	static {
		validFiles.put("1", "flash.txt");
		validFiles.put("2", "maths.txt");
		validFiles.put("3", "physics.txt");
		validFiles.put("4", "history.txt");
    }
	
	public static String getFilename(String key) {
		return validFiles.computeIfAbsent(key, (k)->"physics.txt");
	}
	
	public static String getFileConents(String filename, int from, int to) {
		if(from == 0)
			from = 1;
		if(to == 0)
			to = totalNoOfRecords;
		int size  = to - from + 1;
		ClassLoader classLoader = FlashReader.class.getClassLoader();
        File file = new File(classLoader.getResource("flash/"+filename).getFile());
        StringJoiner speech = new StringJoiner("<break time=\"1s\"/>", "<speak>", "</speak>");
        new JsonArrayStreamDataSupplier<>(file, QandA.class).getStream().skip(from-1).limit(size).collect(Collectors.toList()).forEach(qAndA -> {
        	speech.add(qAndA.getNo()).add(qAndA.getQ()).add(qAndA.getA());});
        return speech.toString();
	}
}
