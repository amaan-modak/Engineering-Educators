package teamnp.eguru;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.awt.image.BufferedImage;
import java.io.File;

import org.junit.Before;
import org.junit.Test;

public class FBDTest {
	
	FBDImage fbd;
	@Before
	public void setUp() throws Exception {
		fbd=new FBDImage();
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
