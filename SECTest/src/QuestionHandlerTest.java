

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
		System.out.println(qh.FolderRandomSelection());
		assertNotNull(randomFolder);
		assertTrue(randomFolder.equals("TestQuestion/AllCorrect") || randomFolder.equals("TestQuestion/SomeInCorrect"));
	
	}

	@Test
	public void testGetTotalQuestions() {
		//fail("Not yet implemented");
		assertNotEquals(0, qh.getTotalQuestions());
	}

	@Test
	public void testSelectQuestion() {
		//fail("Not yet implemented");
		assertNotNull(qh.selectQuestion());
	}

	@Test
	public void testIsLastQuestion() {
		//fail("Not yet implemented");
		
	}

}
