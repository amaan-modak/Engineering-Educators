import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;

public class EngineeringEducatorLogic {
	public ArrayList<String> FileReading(String path) {
		FileInputStream fstream2;
		ArrayList<String> txtLines = new ArrayList<String>();
		try {
			fstream2 = new FileInputStream(path);

			BufferedReader br2 = new BufferedReader(new InputStreamReader(fstream2));

			String strLine2;

			// Read File Line By Line
			while ((strLine2 = br2.readLine()) != null) {
				// r[i] = strLine2;
				txtLines.add(strLine2);
				System.out.println(strLine2);
			}
			br2.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return txtLines;
	}

	/* Reading and randomly selecting question folder */
	public void FolderRandomSelection(EngineeringEducator obj, String folderPath) {
		File dir = obj.getParentDir();
		String[] quesfolders = dir.list();
		Random rand = new Random();
		String folder = quesfolders[rand.nextInt(quesfolders.length)];
		// System.out.println("folder selected " + folder);
		obj.setQuestionDir(folderPath + folder);
	}

	public void DataPreProcessing(EngineeringEducator obj) {
		File dir1 = new File(obj.questionDir);
		File[] files = dir1.listFiles();
		for (int i = 0; i < files.length; i++) {
			BufferedImage img = null;
			System.out.println(files[i].getName());
			if (files[i].getName().equals("fbd.jpg")) {
				try {
					img = ImageIO.read(new File(files[i].getPath()));
					obj.setFbdImg(ImageResizing(img, 200, 200));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (files[i].getName().equals("model.jpg")) {
				try {
					img = ImageIO.read(new File(files[i].getPath()));
					obj.setModelImg(ImageResizing(img, 200, 200));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (files[i].getName().equals("assumptions.txt")) {
				obj.setAssumptionsPath(files[i].getPath());
				System.out.println(obj.assumptionsPath + " assumptions");
			} else if (files[i].getName().equals("reasons.txt")) {
				obj.setReasonsPath(files[i].getPath());
				System.out.println(obj.reasonsPath + " reasons");
			}
		}
	}

	public static BufferedImage ImageResizing(BufferedImage img, int width, int height) {
		System.out.println("Inside resizing");
		BufferedImage bimg = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
		Graphics2D g2d = (Graphics2D) bimg.createGraphics();
		g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
		g2d.drawImage(img, 0, 0, width, height, null);
		g2d.dispose();
		System.out.println("Exiting resizing");
		return bimg;
	}
	
	public boolean CheckAnswer(JCheckBox chkBox, int ans){
		if(chkBox.isSelected() && ans == 1)
			return true;
		else if(!chkBox.isSelected() && (ans == 0 || ans == 2))
			return true;
		return false;
	}
	
	public void DisableCheckBox(ArrayList<JCheckBox> chkList){
		for(int i = 0; i < chkList.size() ; i++){
			chkList.get(i).setEnabled(false);
		}
	}
	
	public void DisableRadioButton(ArrayList<JRadioButton> rdbList){
		for(int i = 0; i < rdbList.size() ; i++){
			if(rdbList.get(i).isEnabled())
				rdbList.get(i).setEnabled(false);
		}
	}
}
