import static org.junit.Assert.*;

import javax.swing.JCheckBox;

import org.junit.Test;

public class EngineeringEducatorAssumptionTest {

	@Test
	public void testReadAssumptions() {
		EngineeringEducatorAssumption assumptionClass = new EngineeringEducatorAssumption();
		assumptionClass.ReadAssumptions("TestFiles/TestQuestions/Q1/assumptions.txt");
		assertEquals("assumption1", assumptionClass.assumptions.get(0));
		assertEquals("assumption2", assumptionClass.assumptions.get(1));
		assertEquals("assumption3", assumptionClass.assumptions.get(2));
		assertEquals(0, assumptionClass.answers.get(0).intValue());
		assertEquals(1, assumptionClass.answers.get(1).intValue());
		assertEquals(2, assumptionClass.answers.get(2).intValue());
	}

	@Test
	public void testScoreCalculation() {
		int score = 0;
		EngineeringEducatorAssumption assumptionClass = new EngineeringEducatorAssumption();
		assumptionClass.ReadAssumptions("TestFiles/TestQuestions/Q1/assumptions.txt");

		assumptionClass.assumptionChkbxList.get(0).setSelected(false);
		assumptionClass.assumptionChkbxList.get(1).setSelected(true);
		assumptionClass.assumptionChkbxList.get(2).setSelected(false);
		
		score = assumptionClass.ScoreCalculation(score, null);
		assertEquals(5, score);
		
		
		
	}

	@Test
	public void testCheckAnswer() {
		JCheckBox chkbx1 = new JCheckBox();
		EngineeringEducatorAssumption assumptionClass = new EngineeringEducatorAssumption();
		chkbx1.setSelected(true);
		assertEquals(assumptionClass.CheckAnswer(chkbx1, 1), true);
		chkbx1.setSelected(false);
		assertEquals(assumptionClass.CheckAnswer(chkbx1, 0), true);
	}

}
