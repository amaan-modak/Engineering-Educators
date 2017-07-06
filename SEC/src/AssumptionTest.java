import static org.junit.Assert.*;

import org.junit.Test;

public class AssumptionTest {


	@Test
	public void testSetReasonList() {
		Question question;
		String path = "TestQuestion/SomeIncorrect";
		question = new Question(path);
		question.readTextFile("TestQuestion/SomeIncorrect/Questions.txt");
		Assumption assumption = new Assumption("Incorrect Assumption #1.1", "incorrect");
		
		int reasonIdx = 6;
		reasonIdx = assumption.setReasonList(reasonIdx, question.data);
		assertEquals(9, reasonIdx);
	}

	@Test
	public void testGetNumberOfReasons() {
		Question question;
		String path = "TestQuestion/SomeIncorrect";
		question = new Question(path);
		question.readTextFile("TestQuestion/SomeIncorrect/Questions.txt");
		Assumption assumption = new Assumption("Incorrect Assumption #1.1", "incorrect");
		
		int reasonIdx = 6;
		reasonIdx = assumption.setReasonList(reasonIdx, question.data);
		
		assertEquals(3, assumption.getNumberOfReasons());

	}

	@Test
	public void testGetReason() {
		Question question;
		String path = "TestQuestion/SomeIncorrect";
		question = new Question(path);
		question.readTextFile("TestQuestion/SomeIncorrect/Questions.txt");
		Assumption assumption = new Assumption("Incorrect Assumption #1.1", "incorrect");
		
		int reasonIdx = 6;
		reasonIdx = assumption.setReasonList(reasonIdx, question.data);
		
		assertEquals("Valid Reason #1.1.1", assumption.getReason(0));
	}


	@Test
	public void testScoreCalculation() {
		Question question;
		String path = "TestQuestion/SomeIncorrect";
		question = new Question(path);
		question.readTextFile("TestQuestion/SomeIncorrect/Questions.txt");
		Assumption assumption = new Assumption("Incorrect Assumption #1.1", "incorrect");
		
		//select correct reason
		int reasonIdx = 6;
		reasonIdx = assumption.setReasonList(reasonIdx, question.data);
		assumption.reasonRdbList.get(0).setSelected(true);
		
		boolean isanywrong = assumption.ScoreCalculation();
		assertEquals(false, isanywrong);
		
		//select wrong reason
		assumption.reasonRdbList.get(1).setSelected(true);
		isanywrong = assumption.ScoreCalculation();
		assertEquals(true, isanywrong);
	}

}
