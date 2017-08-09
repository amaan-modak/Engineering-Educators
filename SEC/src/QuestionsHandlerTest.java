

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class QuestionsHandlerTest {
	
QuestionsHandler qh;
	

	@Before
	public void setUp() throws Exception {
		qh=new QuestionsHandler("TestQuestion/");
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
		QuestionsHandler qh2=new QuestionsHandler("TestQuestion/");
		assertFalse(qh2.isLastQuestion());
		qh2.selectQuestion();
		assertFalse(qh2.isLastQuestion());
		qh2.selectQuestion();
		assertTrue(qh2.isLastQuestion());
	}

}
