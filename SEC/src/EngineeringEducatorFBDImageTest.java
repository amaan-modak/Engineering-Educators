import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;

public class EngineeringEducatorFBDImageTest {
	
	


	@Test
	public void testReadImage() {
		EngineeringEducatorFBDImage fbdImageClass = new EngineeringEducatorFBDImage();
		assertNull(fbdImageClass.fbdImg);
		fbdImageClass.ReadImage(new File("TestFiles/TestQuestions/Q1/fbd.jpg"));
		assertNotNull(fbdImageClass.fbdImg);
	}

	@Test
	public void testImageResizing() {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("TestFiles/TestQuestions/Q1/fbd.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		img= EngineeringEducatorFBDImage.ImageResizing(img, 500, 500);
		assertEquals(500, img.getWidth());
		assertEquals(500, img.getHeight());
		
	}

}
