package teamnp.eguru;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.awt.image.BufferedImage;
import java.io.File;

import org.junit.Before;
import org.junit.Test;

public class ModelTest {
	
	ModelImage mi;

	@Before
	public void setUp() throws Exception {
		mi=new ModelImage();
		mi.readImage(new File("TestQuestion/AllCorrect/model.png"));
	}

	@Test
	public void testReadImage() {
		//fail("Not yet implemented");
		assertNotNull(mi.modelImg);
	}

	@Test
	public void testGetImage() {
		//fail("Not yet implemented");
		mi.getImage();
		assertNotNull(mi.lblModelImg);
	}

	@Test
	public void testImageResizing() {
		//fail("Not yet implemented")
		BufferedImage img=null;
		img=mi.imageResizing(mi.modelImg, 500, 500);
		assertEquals(500, img.getWidth());
		assertEquals(500, img.getHeight());
	}

}
