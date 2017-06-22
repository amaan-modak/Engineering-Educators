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
		assertNotNull(eeq.FolderRandomSelection("Questions"));
	}

}
