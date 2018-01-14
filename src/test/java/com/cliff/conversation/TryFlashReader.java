package com.cliff.conversation;

import org.junit.Test;

import com.cliff.flash.FlashReader;


public class TryFlashReader {
	
	   @Test
	   public void testFlashContent() {
			String flashContent = FlashReader.getFileConents("flash.txt", 0, 0);
			System.out.println(flashContent);
//			assertNotNull(response);
	   }
	   @Test
	   public void testFileName() {
			String fileName = FlashReader.getFilename("flash");
			System.out.println(fileName);
//			assertNotNull(response);
	   }
	   
	   @Test
	   public void testFormulaJson() {
		   String result = FlashReader.getFileConents("flash.txt", 2, 4);
		   System.out.println(result);
	   }
}
