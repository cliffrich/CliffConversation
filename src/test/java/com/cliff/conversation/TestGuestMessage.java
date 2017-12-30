package com.cliff.conversation;

import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import com.cliff.guest.GuestMessage;

public class TestGuestMessage {
	
   @Test
   public void testSalutationMessage() {
	  String ret = GuestMessage.aboutTheGuest("sheryl");
//      assertNotNull(ret);
   }
}
