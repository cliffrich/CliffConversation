package com.cliff.conversation;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.junit.Test;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.dialog.directives.DialogSlot;
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
	   
	   private Intent createIntent() {
		   Map<String, Slot> slots = new HashMap();
		   Slot s1 = Slot.builder().withName("slot1").withValue("value 1").build();
		   Slot s2 = Slot.builder().withName("slot2").withValue("value 2").build();
		   slots.put("slot1", s1);
		   slots.put("slot2", s2);
		   Intent intent = Intent.builder().withName("cliff").withSlots(slots).build();
		   return intent;
	   }
	   @Test
	   public void testMapToMap() {
		    Intent intent = createIntent();
	        Map<String,DialogSlot> dialogSlots = new HashMap<String,DialogSlot>();
	        intent.getSlots().forEach((key, slot)->{
	        	dialogSlots.put(key, new DialogSlot(slot.getName(), slot.getValue()));
	        });
	        printDialogSlots(dialogSlots, "lambda");
	        //
	        Map<String,DialogSlot> dialogSlots1 = new HashMap<String,DialogSlot>();
	        Iterator iter = intent.getSlots().entrySet().iterator();
	        
	        while (iter.hasNext()) {
	        	    	
	            Map.Entry pair = (Map.Entry)iter.next();
	            
	            DialogSlot dialogSlot = new DialogSlot();
	            
	            Slot slot = (Slot) pair.getValue();
	            
	            dialogSlot.setName(slot.getName());
	            
	            if (slot.getValue() != null)
	            	dialogSlot.setValue(slot.getValue());
	            
	            dialogSlots1.put((String) pair.getKey(), dialogSlot);
	        }
	        printDialogSlots(dialogSlots1, "non-lambda");

	   }
	   private void printDialogSlots(Map<String,DialogSlot> slots, String from) {
		   slots.forEach((k,v)->System.out.println(from +" - "+k+" - "+v.getName()));
	   }
}
