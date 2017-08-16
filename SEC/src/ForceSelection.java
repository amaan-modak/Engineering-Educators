import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import ForcePoint.EntityDirection;
import ForcePoint.EntityProperty;
import ForcePoint.EntityType;



public class ForceSelection {
	
	private Image forcePointImage = null;
	BufferedImage originalImage, canvasImage;
	JLabel imageLabel;
//	int imageWidth = 800;
//	int imageHeight = 450;

	// list of forcepoints
	ArrayList<ForcePoint> fpList = new ArrayList<ForcePoint>();

	// list of which contains data for all the forces, their angles, moments
	// etc.
	ArrayList<ForcePoint> forceDataList = new ArrayList<ForcePoint>();

	// list of correct forces loaded from file
	ArrayList<ForcePoint> correctForceDataList = new ArrayList<ForcePoint>();


	
	public ForceSelection(String filename, BufferedImage inputImage) {
		initialize(inputImage);
		readForceFile(filename);
	}
	
	private void initialize(BufferedImage inputImage) {
		forcePointImage = Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/x-30.png"));
		canvasImage = FBDImage.imageResizing(inputImage, inputImage.getWidth(), inputImage.getHeight());
		// display white image
		imageLabel = new JLabel();
		imageLabel.setMaximumSize(MainPage.screenSize);
		imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		//imageLabel.setHorizontalTextPosition(JLabel.CENTER);
		//imageLabel.setVerticalTextPosition(JLabel.CENTER);
		setImage(canvasImage);
		displayForcePoint(100, 100);

	}
	
	private void setImage(BufferedImage image) {
		originalImage = image;
		int w = image.getWidth();
		int h = image.getHeight();
		canvasImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g = this.canvasImage.createGraphics();

		g.drawImage(image, 0, 0, null);

		if (this.imageLabel != null) {
			imageLabel.setIcon(new ImageIcon(canvasImage));
	
		}
	}
	
	private void displayForcePoint(int x, int y) {
		Graphics2D g = canvasImage.createGraphics();
		g.setColor(Color.black);

		g.drawImage(forcePointImage, x - 15, y - 15, null);
		g.dispose();
		imageLabel.repaint();

	}
	
	public void readForceFile(String filename) {
		File f = new File(filename);
		ArrayList<String> fileText = readTextFile(f);
		ArrayList<String> forcesString = getForcesFromText(fileText);
//		drawOriginal();
		fpList.clear();
		correctForceDataList.clear();
		forceDataList.clear();
		for (int i = 0; i < forcesString.size(); i++) {
			ForcePoint fp = getFpFromString(forcesString.get(i));
			correctForceDataList.add(fp);
			if (!fpListContainsLocation(fpList, fp)) {
				fpList.add(fp);
			}
		}

		for (ForcePoint fp : correctForceDataList) {
			displayForcePoint(fp.x, fp.y);
			if (fp.isCorrect == true) {
				if (fp.type == EntityType.FORCE)
					displayArrow(fp.x, fp.y, fp.angle, fp.property);
				else if (fp.type == EntityType.MOMENT)
					displayMoment(fp.x, fp.y, fp.direction, fp.property);
			}
		}
	}
	
	public ArrayList<String> readTextFile(File f) {
		ArrayList<String> retString = new ArrayList<String>();
		;
		try {
			FileInputStream fstream = new FileInputStream(f);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

			String strLine;
			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				retString.add(strLine);
			}

			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retString;
	}

	private ArrayList<String> getForcesFromText(ArrayList<String> fileText) {
		ArrayList<String> pointsString = new ArrayList<String>();
		int dataIdx = 0;
		while (!fileText.get(dataIdx).contains("Forces:"))
			dataIdx++;
		dataIdx++;
		while (!fileText.get(dataIdx).contains("End Forces") && dataIdx < fileText.size()) {
			pointsString.add(fileText.get(dataIdx));
			dataIdx++;
		}
		return pointsString;

	}

	private ForcePoint getFpFromString(String str) {
		ForcePoint fp = null;
		String[] strSplit = str.split(",");
		int x = Integer.parseInt(strSplit[0]);
		int y = Integer.parseInt(strSplit[1]);

		boolean isCorrect = false;
		if (strSplit[2].equals("true"))
			isCorrect = true;
		else if (strSplit[2].equals("false"))
			isCorrect = false;

		EntityType type = null;
		if (strSplit[3].equals("Force"))
			type = EntityType.FORCE;
		else if (strSplit[3].equals("Moment"))
			type = EntityType.MOMENT;

		EntityProperty property = null;
		if (strSplit[4].equals("Known"))
			property = EntityProperty.KNOWN;
		else if (strSplit[4].equals("Unknown"))
			property = EntityProperty.UNKNOWN;

		EntityDirection direction = null;
		if (strSplit[5].equals("Clockwise"))
			direction = EntityDirection.CLOCKWISE;
		else if (strSplit[5].equals("Anticlockwise"))
			direction = EntityDirection.ANTICLOCKWISE;

		int angle = Integer.parseInt(strSplit[6]);

		fp = new ForcePoint(x, y);
		fp.setCorrect(isCorrect);
		fp.setType(type);
		fp.setProperty(property);
		fp.setDirection(direction);
		fp.setAngle(angle);
		return fp;
	}

}
