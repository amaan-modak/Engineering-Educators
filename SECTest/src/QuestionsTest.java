
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.Test;

public class QuestionsTest {

	EngineeringEducatorQuestion quest;
	EngineeringEducatorFBDImage fbd;
	EngineeringEducatorModelImage mi;

	@Before
	public void setUp() throws Exception {
		String path = "TestQuestion/";
		quest = new EngineeringEducatorQuestion(path);
		fbd = new EngineeringEducatorFBDImage();
		mi = new EngineeringEducatorModelImage();
	}

	@Test
	public void testGetAssumObj() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetNumberOfAssumptions() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAssumption() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetPerReasonScore() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPerReasonScore() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetMaxScore() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetPerAssumScore() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetPerAssumNegScore() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetModelImage() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetFbdImage() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAnswer() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAssumptionChkbxList() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetAssumptionChkbxList() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAssumptionCheckbox() {
		fail("Not yet implemented");
	}

	@Test
	public void testReadQuestion() {
		// fail("Not yet implemented");
		quest.readQuestion();
		File dir1 = new File(quest.questionPath);
		File[] files = dir1.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].getName().contains("fbd.")) {
				quest.readFbdImage(new File(files[i].getAbsolutePath()));
				assertNotNull(fbd.fbdImg);
			} else if (files[i].getName().contains("model.")) {
				quest.readModelImage(new File(files[i].getAbsolutePath()));
				assertNotNull(mi.modelImg);
			} else if (files[i].getName().contains("Questions.")) {

				quest.readTextFile(files[i].getPath());
				assertNotNull(quest.data);
			}
		}
	}

	@Test
	public void testReadFbdImage() {
		// fail("Not yet implemented");
		File dir = new File("TestQuestion/AllCorrect/fbd.png");
		fbd.readImage(dir);
		assertNotNull(fbd.fbdImg);
	}

	@Test
	public void testReadModelImage() {
		// fail("Not yet implemented");
		File dir = new File("TestQuestion/AllCorrect/model.png");
		mi.readImage(dir);
		assertNotNull(mi.modelImg);
	}

	@Test
	public void testReadTextFile() throws FileNotFoundException {
		// fail("Not yet implemented");
		quest.readTextFile("TestQuestion/AllCorrect/Questions.txt");
		assertNotNull(quest.data);
	}

	@Test
	public void testReadAssumptions() {
		// fail("Not yet implemented");
		quest.readTextFile("TestQuestion/AllCorrect/Questions.txt");
		quest.readAssumptions();
		assertNotEquals(0, quest.assumptionObjMap.size());
		assertNotEquals(0, quest.assumptions.size());

	}

	@Test
	public void testReadScores() {
		// fail("Not yet implemented");
		quest.readTextFile("TestQuestion/AllCorrect/Questions.txt");
		quest.readScores();
		assertNotEquals(0, quest.maxScore);
		assertNotEquals(0, quest.perAssumScore);
		assertNotEquals(-1, quest.perAssumNegScore);
		assertNotEquals(-1, quest.perReasonScore);
	}

	@Test
	public void testMessageType() {
		// fail("Not yet implemented");
		quest.readTextFile("TestQuestion/AllCorrect/Questions.txt");
		quest.readAssumptions();
		for (int i = 0; i < quest.assumptions.size(); i++) {
			// quest.MessageType(quest.assumptions.get(i));
			// assertEquals("correct",
			// quest.assumptionObjMap.get(quest.assumptions.get(i)).answer);
			// assertEquals("incorrect",
			// quest.assumptionObjMap.get(quest.assumptions.get(i)).answer);
			// assertEquals("complicated",
			// quest.assumptionObjMap.get(quest.assumptions.get(i)).answer);
			assertNotNull(quest.MessageType(quest.assumptions.get(i)));
		}

	}

	@Test
	public void testDisableCheckBoxes() {
		fail("Not yet implemented");

	}

	@Test
	public void testScoreCalculation() {
		// fail("Not yet implemented");
		int tempscore = 0;
		quest.readTextFile("TestQuestion/AllCorrect/Questions.txt");
		quest.readAssumptions();
		quest.setAssumptionChkbxList();
		quest.assumptionChkbxList.get(0).setSelected(true);
		quest.assumptionChkbxList.get(1).setSelected(true);
		quest.assumptionChkbxList.get(2).setSelected(true);
		quest.assumptionChkbxList.get(3).setSelected(true);
		tempscore = quest.ScoreCalculation(tempscore);
		// assertEquals(5, tempscore);
		assertEquals(5, quest.ScoreCalculation(5));
	}

	@Test
	public void testScoreCalculationReason() {
		// fail("Not yet implemented");
		int tempscore = 0;
		quest.readTextFile("TestQuestion/AllCorrect/Questions.txt");
		quest.readAssumptions();
		quest.setAssumptionChkbxList();
		quest.assumptionChkbxList.get(0).setSelected(false);
		quest.assumptionChkbxList.get(1).setSelected(false);
		quest.assumptionChkbxList.get(2).setSelected(true);
		quest.assumptionChkbxList.get(3).setSelected(true);
		quest.ScoreCalculation(tempscore);
		tempscore = quest.ScoreCalculation(5);
		assertNotEquals(0, quest.ScoreCalculationReason(tempscore));
	}

}
