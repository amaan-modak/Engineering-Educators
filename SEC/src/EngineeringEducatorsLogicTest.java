/**
 * 
 */
package sec;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class EngineeringEducatorsLogicTest {

	EngineeringEducatorLogic eel;
	EngineeringEducator ee;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		eel =new EngineeringEducatorLogic();
		ee=new EngineeringEducator();
	}

	/**
	 * Test method for {@link sec.EngineeringEducatorLogic#FileReading(java.lang.String)}.
	 */
	@Test
	public void testFileReading() {
		//assertNull(EngineeringEducatorLogic.FileReading(eel));
		assertNotNull(eel.FileReading(null));
	}

	/**
	 * Test method for {@link sec.EngineeringEducatorLogic#FolderRandomSelection(sec.EngineeringEducator, java.lang.String)}.
	 */
	@Test
	public void testFolderRandomSelection() {
		assertNotEquals(ee.getParentDir(),null);
	}

	/**
	 * Test method for {@link sec.EngineeringEducatorLogic#DataPreProcessing(sec.EngineeringEducator)}.
	 */
	@Test
	public void testDataPreProcessing() {
		assertNotNull(ee.questionDir);
	}

	/**
	 * Test method for {@link sec.EngineeringEducatorLogic#ImageResizing(java.awt.image.BufferedImage, int, int)}.
	 */
	@Test
	public void testImageResizing() {
		assertNotNull(EngineeringEducatorLogic.ImageResizing(null, 0, 0));
	}

	/**
	 * Test method for {@link sec.EngineeringEducatorLogic#CheckAnswer(javax.swing.JCheckBox, int)}.
	 */
	@Test
	public void testCheckAnswer() {
		assertNotEquals(null, eel.CheckAnswer(null, 0));
	}

	/**
	 * Test method for {@link sec.EngineeringEducatorLogic#DisableCheckBox(java.util.ArrayList)}.
	 */
	@Test
	public void testDisableCheckBox() {
		//assertEquals(eel.DisableCheckBox(null), null);
	}

	/**
	 * Test method for {@link sec.EngineeringEducatorLogic#DisableRadioButton(java.util.ArrayList)}.
	 */
	@Test
	public void testDisableRadioButton() {
	//	assertNotNull(eel.DisableRadioButton(null));
	}

}
