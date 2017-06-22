import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class EngineeringEducatorFBDImage {
	BufferedImage fbdImg;
	JLabel lblFbdImg = new JLabel("Free Body Diagram");
	
	public void ReadImage(File dir){
		try {
			BufferedImage img = ImageIO.read(dir);
			fbdImg = ImageResizing(img, 700, 300);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public JLabel SetImage(){
		lblFbdImg.setVerticalAlignment(SwingConstants.TOP);
		lblFbdImg.setIcon(new ImageIcon(fbdImg));
		lblFbdImg.setHorizontalTextPosition(JLabel.CENTER);
		lblFbdImg.setVerticalTextPosition(JLabel.BOTTOM);
		lblFbdImg.setForeground(Color.WHITE);
		return lblFbdImg;
	}
	
	/* IMAGE RESIZING
	 * Method to resize image to given width and height
	 * Input: 1. Image to be resized
	 * 		  2. Intended Width
	 * 		  3. Intended Height
	 * Output: Resized image
	 * */
	public static BufferedImage ImageResizing(BufferedImage img, int width, int height) {
		BufferedImage bimg = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
		Graphics2D g2d = (Graphics2D) bimg.createGraphics();
		g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
		g2d.drawImage(img, 0, 0, width, height, null);
		g2d.dispose();
		return bimg;
	}
}
