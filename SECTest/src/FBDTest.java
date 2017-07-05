

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FBDTest {
	
	EngineeringEducatorFBDImage fbd;
	@Before
	public void setUp() throws Exception {
		fbd=new EngineeringEducatorFBDImage();
		fbd.readImage(new File("TestQuestion/AllCorrect/fbd.png"));
	}

	@Test
	public void testReadImage() {
		//fail("Not yet implemented");
		assertNotNull(fbd.fbdImg);
	}

	@Test
	public void testGetImage() {
		//fail("Not yet implemented");
		fbd.getImage();
		assertNotNull(fbd.lblFbdImg);
		
	}

	@Test
	public void testImageResizing() {
		//fail("Not yet implemented");
		BufferedImage img = null;
		img=fbd.imageResizing(fbd.fbdImg, 500, 500);
		assertEquals(500, img.getWidth());
		assertEquals(500, img.getHeight());
	}

}
