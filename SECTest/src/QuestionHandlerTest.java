

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class QuestionHandlerTest {
	
EngineeringEducatorQuestionsHandler qh;
	

	@Before
	public void setUp() throws Exception {
		qh=new EngineeringEducatorQuestionsHandler("TestQuestion/");
	}


	@Test
	public void testFolderRandomSelection() {
		//fail("Not yet implemented");
		String randomFolder = null;
		randomFolder = qh.FolderRandomSelection();
		assertNotNull(randomFolder);
		assertTrue(randomFolder.equals("TestQuestion/AllCorrect") || randomFolder.equals("TestQuestion/SomeIncorrect"));
	
	}

	@Test
	public void testGetTotalQuestions() {
		assertEquals(2, qh.getTotalQuestions());
	}


	@Test
	public void testIsLastQuestion() {
		//fail("Not yet implemented");
		EngineeringEducatorQuestionsHandler qh2=new EngineeringEducatorQuestionsHandler("TestQuestion/");
		assertFalse(qh2.isLastQuestion());
		qh2.selectQuestion();
		assertFalse(qh2.isLastQuestion());
		qh2.selectQuestion();
		assertTrue(qh2.isLastQuestion());
	}

}
