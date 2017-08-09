
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.Test;

public class QuestionTest {

	Question question;
	FBDImage fbd;
	ModelImage mi;

	@Before
	public void setUp() throws Exception {
		String path = "TestQuestion/SomeIncorrect";
		question = new Question(path);
		fbd = new FBDImage();
		mi = new ModelImage();
	}

	@Test
	public void testGetAssumObj() {
		question.readQuestion();
		String assumptionText = "Forces are reasonably approximated using static analysis";
		Assumption assumptionObject = question.getAssumObj(assumptionText);
		assertEquals(assumptionText, assumptionObject.assumption);
		assertEquals("correct", assumptionObject.answer);
	}

	@Test
	public void testGetNumberOfAssumptions() {
		question.readQuestion();
		assertEquals(8, question.getNumberOfAssumptions());
	}

	@Test
	public void testGetAssumption() {
		String expectedString = "Lower leg remains approximately perpendicular to upper leg";
		question.readQuestion();
		assertEquals(expectedString, question.getAssumption(2));
	}


	@Test
	public void testGetPerReasonScore() {
		question.readQuestion();
		assertEquals(1, question.getPerReasonScore());
	}

	@Test
	public void testGetAnswer() {
		question.readQuestion();
		String assumptionText = "Forces are reasonably approximated using static analysis";
		assertEquals("correct", question.getAnswer(assumptionText));
		assumptionText = "Incorrect Assumption #1.1";
		assertEquals("incorrect", question.getAnswer(assumptionText));
	}


	@Test
	public void testReadQuestion() {
		// fail("Not yet implemented");
		question.readQuestion();
		
		//Test if assumptions are read correctly
		assertEquals("Hip acts as a pivot point (no lifting off the bed)", question.assumptions.get(0));
		assertEquals("Incorrect Assumption #1.2", question.assumptions.get(3));
		assertEquals("Complicating Assumption #Who Cares I Am Making All This Up?", question.assumptions.get(6));
		assertEquals("Incorrect Assumption that includes a lot of text to make certain you can handle it #1.3", question.assumptions.get(7));
		
		//Test if scores are read correctly
		assertEquals(3, question.perAssumScore);
		assertEquals(1, question.perReasonScore);
		assertEquals(-2, question.perAssumNegScore);
	}

	@Test
	public void testReadFbdImage() {
		// fail("Not yet implemented");
		File imageFile = new File("TestQuestion/SomeIncorrect/fbd.jpg");
		question.readFbdImage(imageFile);
		assertNotNull(question.fbdObj.fbdImg);
	}

	@Test
	public void testReadModelImage() {
		// fail("Not yet implemented");
		File imageFile = new File("TestQuestion/SomeIncorrect/model.jpg");
		question.readModelImage(imageFile);
		assertNotNull(question.modObj.modelImg);
	}

	@Test
	public void testReadTextFile() throws FileNotFoundException {
		// fail("Not yet implemented");
		question.readTextFile("TestQuestion/SomeIncorrect/Questions.txt");
		assertNotNull(question.data);
		assertEquals("MaxScore:12", question.data.get(0));
		assertEquals("Hip acts as a pivot point (no lifting off the bed)|correct", question.data.get(5));
		assertEquals("Invalid Reason #1.2.3|invalid", question.data.get(15));
		assertEquals("End Reasons", question.data.get(28));
		
	}

	@Test
	public void testReadAssumptions() {
		// fail("Not yet implemented");
		question.readTextFile("TestQuestion/SomeIncorrect/Questions.txt");
		question.readAssumptions();
		
		assertNotEquals(0, question.assumptionObjMap.size());
		assertNotEquals(0, question.assumptions.size());
		
		assertEquals("Hip acts as a pivot point (no lifting off the bed)", question.assumptions.get(0));
		assertEquals("Incorrect Assumption #1.2", question.assumptions.get(3));
		assertEquals("Complicating Assumption #Who Cares I Am Making All This Up?", question.assumptions.get(6));
		assertEquals("Incorrect Assumption that includes a lot of text to make certain you can handle it #1.3", question.assumptions.get(7));


	}

	@Test
	public void testReadScores() {
		// fail("Not yet implemented");
		question.readTextFile("TestQuestion/SomeIncorrect/Questions.txt");
		question.readScores();

		assertEquals(3, question.perAssumScore);
		assertEquals(1, question.perReasonScore);
		assertEquals(-2, question.perAssumNegScore);

	}

	@Test
	public void testScoreCalculation() {
		// fail("Not yet implemented");
		int tempscore = 0;
		question.readQuestion();
		// select all correct
		question.assumptionChkbxList.get(0).setSelected(true);
		question.assumptionChkbxList.get(2).setSelected(true);
		question.assumptionChkbxList.get(4).setSelected(true);
		question.assumptionChkbxList.get(5).setSelected(true);
		tempscore = question.ScoreCalculation(tempscore);	
		assertEquals(12, tempscore);
		
		
		// Now try with 2 correct 2 incorrect
		question = new Question("TestQuestion/SomeIncorrect");
		tempscore = 0;
		question.readQuestion();
		question.assumptionChkbxList.get(0).setSelected(true);
		question.assumptionChkbxList.get(1).setSelected(true);
		question.assumptionChkbxList.get(4).setSelected(true);
		question.assumptionChkbxList.get(6).setSelected(true);
		tempscore = question.ScoreCalculation(tempscore);	
		assertEquals(2, tempscore);
		
	
	}

	@Test
	public void testScoreCalculationReason() {
		
		//try with 2 correct 2 incorrect
		question = new Question("TestQuestion/SomeIncorrect");
		int tempscore = 0;
		question.readQuestion();
		question.assumptionChkbxList.get(0).setSelected(true);
		question.assumptionChkbxList.get(1).setSelected(true);
		question.assumptionChkbxList.get(4).setSelected(true);
		question.assumptionChkbxList.get(6).setSelected(true);
		tempscore = question.ScoreCalculation(tempscore);
		assertEquals(2, tempscore);
		
		// now select a valid reason
		Assumption firstIncorrectAssumption = question.getAssumObj("Incorrect Assumption #1.1");
		firstIncorrectAssumption.reasonRdbList.get(0).setSelected(true);
		
		tempscore = question.ScoreCalculationReason(tempscore);
		assertEquals(3, tempscore);

	}

}
