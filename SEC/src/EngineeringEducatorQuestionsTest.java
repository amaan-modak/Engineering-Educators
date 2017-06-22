import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class EngineeringEducatorQuestionsTest {
	
	EngineeringEducatorQuestions eeq;

	@Before
	public void setUp() throws Exception {
		eeq=new EngineeringEducatorQuestions();
	}

	@Test
	public void testFolderRandomSelection() {
		//fail("Not yet implemented");
		String randomFolder = null;
		randomFolder = eeq.FolderRandomSelection("TestFiles/TestQuestions/");
		assertNotNull(randomFolder);
		assertTrue(randomFolder.equals("TestFiles/TestQuestions/Q1") || randomFolder.equals("TestFiles/TestQuestions/Q2"));		
	}

}
