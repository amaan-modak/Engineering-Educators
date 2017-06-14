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

/* Class defining all the methods used to create logic */
public class EngineeringEducatorLogic {
	
	/* FILE READING
	 * Method to read text file line by line and store the result in an array list
	 * Input: Path of the text file
	 * Output: Array list of String containing textual data of file 
	 * */
	public ArrayList<String> FileReading(String path) {
		FileInputStream fstream2;
		ArrayList<String> txtLines = new ArrayList<String>();
		try {
			fstream2 = new FileInputStream(path);
			BufferedReader br2 = new BufferedReader(new InputStreamReader(fstream2));
			String strLine2;
			// Read File Line By Line
			while ((strLine2 = br2.readLine()) != null) {
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

	/* FOLDER RANDOM SELECTION
	 * Method to randomly select a question folder
	 * Input: 1. EngineeringEducator class object 
	 * 		  2. Path of the directory containing question folders
	 * */
	public void FolderRandomSelection(EngineeringEducator obj, String folderPath) {
		File dir = obj.getParentDir();
		String[] quesfolders = dir.list();
		Random rand = new Random();
		String folder = quesfolders[rand.nextInt(quesfolders.length)];
		obj.setQuestionDir(folderPath + folder);
	}

	/* DATA PRE-PROCESSING
	 * Method to read contents of Question Folder and initialize class variables
	 * Input: EngineeringEducator class object
	 * */
	public void DataPreProcessing(EngineeringEducator obj) {
		File dir1 = new File(obj.questionDir);
		File[] files = dir1.listFiles();
		for (int i = 0; i < files.length; i++) {
			BufferedImage img = null;
			System.out.println(files[i].getName());
			if (files[i].getName().equals("fbd.jpg") || files[i].getName().equals("fbd.png")) {
				try {
					img = ImageIO.read(new File(files[i].getPath()));
					obj.setFbdImg(ImageResizing(img, 700, 300));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (files[i].getName().equals("model.jpg") || files[i].getName().equals("model.png")) {
				try {
					img = ImageIO.read(new File(files[i].getPath()));
					obj.setModelImg(ImageResizing(img, 500, 300));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (files[i].getName().equals("assumptions.txt")) {
				obj.setAssumptionsPath(files[i].getPath());
				System.out.println(obj.assumptionsPath + " assumptions");
			}
			obj.setReasonsPath(dir1.getPath()+"/reasons");
			System.out.println(obj.reasonsPath + " reasons");
		}
	}

	/* IMAGE RESIZING
	 * Method to resize image to given width and height
	 * Input: 1. Image to be resized
	 * 		  2. Intended Width
	 * 		  3. Intended Height
	 * Output: Resized image
	 * */
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
	
	/* CHECK ANSWER
	 * Method to check whether selected assumption is correct or not
	 * Input: Check box and actual answer for that check box
	 * Output: True if selected assumption is correct, otherwise false
	 * */
	public boolean CheckAnswer(JCheckBox chkBox, int ans){
		if(chkBox.isSelected() && ans == 1)
			return true;
		else if(!chkBox.isSelected() && (ans == 0 || ans == 2))
			return true;
		return false;
	}
	
	/* DISABLE CHECKBOX
	 * Method to disable all the checkboxes
	 * Input: Arraylist containing check boxes*/
	public void DisableCheckBox(ArrayList<JCheckBox> chkList){
		for(int i = 0; i < chkList.size() ; i++){
			chkList.get(i).setEnabled(false);
		}
	}
	
	/* DISABLE RADIOBUTTONS
	 * Method to disable all the radio buttons
	 * Input: Arraylist containing radio buttons*/	
	public void DisableRadioButton(ArrayList<JRadioButton> rdbList){
		for(int i = 0; i < rdbList.size() ; i++){
			if(rdbList.get(i).isEnabled())
				rdbList.get(i).setEnabled(false);
		}
	}
}
