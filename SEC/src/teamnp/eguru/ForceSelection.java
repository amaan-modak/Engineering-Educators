package teamnp.eguru;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
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

import teamnp.eguru.ForcePoint.EntityDirection;
import teamnp.eguru.ForcePoint.EntityProperty;
import teamnp.eguru.ForcePoint.EntityType;



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

	ForcePoint.EntityType currentType = null;
	ForcePoint.EntityProperty currentProperty = null;
	ForcePoint.EntityDirection currentDirection = null;

	static final Color KNOWN_FORCE_COLOR = new Color(0, 153, 51);
	static final Color UNKNOWN_FORCE_COLOR = Color.RED;
	int arrowLineLength = 50;
	int arrowHeaderSize = 5;
	int arrowLenght = arrowLineLength + arrowHeaderSize;

	
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
	
	boolean fpListContainsLocation(ArrayList<ForcePoint> fpList, ForcePoint fp) {
		boolean retValue = false;
		for (ForcePoint p : fpList) {
			if (p.x == fp.x && p.y == fp.y) {
				retValue = true;
			}
		}
		return retValue;
	}

	private void displayMoment(int x, int y, EntityDirection direction, EntityProperty property) {
		Color arcColor = null;
		currentProperty = property;
		currentDirection = direction;
		Point p = new Point(x, y);
		Graphics2D g = canvasImage.createGraphics();
		int arcRadius = arrowLenght / 2;
		if (currentProperty == EntityProperty.KNOWN)
			arcColor = KNOWN_FORCE_COLOR;
		else if (currentProperty == EntityProperty.UNKNOWN)
			arcColor = UNKNOWN_FORCE_COLOR;
		g.setColor(arcColor);

		if (currentDirection == EntityDirection.CLOCKWISE) {
			g.drawArc(p.x - arcRadius, p.y - arcRadius, 2 * arcRadius, 2 * arcRadius, 300, 120);

			Point arcArrowStartPoint = new Point((int) (arcRadius * Math.cos(Math.toRadians(315))),
					(int) (-1 * arcRadius * Math.sin(Math.toRadians(315))));
			arcArrowStartPoint = new Point(p.x + arcArrowStartPoint.x, p.y + arcArrowStartPoint.y);
			Point arcArrowEndPoint = new Point((int) (arcRadius * Math.cos(Math.toRadians(300))),
					(int) (-1 * arcRadius * Math.sin(Math.toRadians(300))));
			arcArrowEndPoint = new Point(p.x + arcArrowEndPoint.x, p.y + arcArrowEndPoint.y);
			drawArrow(arcArrowStartPoint, arcArrowEndPoint);

		} else if (currentDirection == EntityDirection.ANTICLOCKWISE) {
			g.drawArc(p.x - arcRadius, p.y - arcRadius, 2 * arcRadius, 2 * arcRadius, 120, 120);

			Point arcArrowStartPoint = new Point((int) (arcRadius * Math.cos(Math.toRadians(135))),
					(int) (-1 * arcRadius * Math.sin(Math.toRadians(135))));
			arcArrowStartPoint = new Point(p.x + arcArrowStartPoint.x, p.y + arcArrowStartPoint.y);
			Point arcArrowEndPoint = new Point((int) (arcRadius * Math.cos(Math.toRadians(120))),
					(int) (-1 * arcRadius * Math.sin(Math.toRadians(120))));
			arcArrowEndPoint = new Point(p.x + arcArrowEndPoint.x, p.y + arcArrowEndPoint.y);
			drawArrow(arcArrowStartPoint, arcArrowEndPoint);

		}

		g.dispose();
		imageLabel.repaint();

	}

	private void displayArrow(int x, int y, int angle, EntityProperty property) {
		Point p1 = new Point(x, y);
		int new_X = (int) (arrowLineLength * Math.cos(Math.toRadians(angle)));
		int new_Y = (int) (-1 * arrowLineLength * Math.sin(Math.toRadians(angle)));
		Point p2 = new Point(x + new_X, y + new_Y);
		currentProperty = property;
		drawArrow(p1, p2);

	}
	
	private void drawArrow(Point p1, Point p2) {
		int x1 = (int) p1.getX();
		int y1 = (int) p1.getY();
		int x2 = (int) p2.getX();
		int y2 = (int) p2.getY();

		Color arrowColor = null;

		Graphics2D g2d = canvasImage.createGraphics();
		AffineTransform tx = new AffineTransform();
		Line2D.Double line = new Line2D.Double(x1, y1, x2, y2);

		if (currentProperty == EntityProperty.KNOWN)
			arrowColor = KNOWN_FORCE_COLOR;
		else if (currentProperty == EntityProperty.UNKNOWN)
			arrowColor = UNKNOWN_FORCE_COLOR;

		g2d.setColor(arrowColor);
		g2d.drawLine(x1, y1, x2, y2);

		Polygon arrowHead = new Polygon();
		arrowHead.addPoint(0, 5);
		arrowHead.addPoint(-5, -5);
		arrowHead.addPoint(5, -5);

		tx.setToIdentity();
		double angle = Math.atan2(line.y2 - line.y1, line.x2 - line.x1);
		tx.translate(line.x2, line.y2);
		tx.rotate((angle - Math.PI / 2d));

		Graphics2D g = (Graphics2D) g2d.create();
		g.setColor(arrowColor);

		g.setTransform(tx);
		g.fill(arrowHead);
		g.dispose();

		imageLabel.repaint();
	}


}
