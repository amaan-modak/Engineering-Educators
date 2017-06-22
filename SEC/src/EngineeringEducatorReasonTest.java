import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class EngineeringEducatorReasonTest {

	@Test
	public void testReadReasons() {
		EngineeringEducatorReason reasonObject = new EngineeringEducatorReason();
		ArrayList<String> allReasons = reasonObject.ReadReasons("TestFiles/TestQuestions/Q1/reasons.txt");
		assertEquals("reason1", allReasons.get(0));
		assertEquals("reason2", allReasons.get(1));
		assertEquals("reason3", allReasons.get(2));
		assertEquals("reason4", allReasons.get(3));
		
	}

}
