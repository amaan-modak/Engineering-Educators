import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;

public class EngineeringEducatorModelImageTest {

	@Test
	public void testReadImage() {
		EngineeringEducatorModelImage modelImageClass = new EngineeringEducatorModelImage();
		assertNull(modelImageClass.modelImg);
		modelImageClass.ReadImage(new File("TestFiles/TestQuestions/Q1/fbd.jpg"));
		assertNotNull(modelImageClass.modelImg);

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
		
		img= EngineeringEducatorModelImage.ImageResizing(img, 500, 500);
		assertEquals(500, img.getWidth());
		assertEquals(500, img.getHeight());
		
	}

}
