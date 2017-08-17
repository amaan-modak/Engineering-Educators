package teamnp.eguru;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;



public class FBDSelection {

	ArrayList<ZPoint> cutsList = new ArrayList<ZPoint>();
	int cutCount = 1;
	// this list contains correct answer loaded from file
	ArrayList<Line2D> lineList = new ArrayList<Line2D>();
	
	//this list contains answer entered by student
	ArrayList<Line2D> answerLineList = new ArrayList<Line2D>();


	Point fbdStart;
	Point fbdRecent;
	ZPoint firstPoint, secondPoint;
	BufferedImage originalImage, canvasImage;
	private Image scissor = null;
	JLabel imageLabel;
//	int imageWidth = 800;
//	int imageHeight = 450;
	boolean isFBDAnswered = false;
	boolean finalAnswer = false;
	int retryAttempts = 3;
	int fbdScore;
	int fbdNegScore;
	String hint;
	
	public FBDSelection(String filename, String fbdHintFile, BufferedImage inputImage) {

		initialize(inputImage);
		readFBDFile(filename);
		readFBDHintFile(fbdHintFile);
	}
	
	public void readFBDHintFile(String fbdHintFile){
		File fHint = new File(fbdHintFile);
		try {
			FileInputStream fstream = new FileInputStream(fHint);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

			hint = br.readLine();

			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initialize(BufferedImage inputImage) {
		scissor = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/scissor-30.png"));
		canvasImage = FBDImage.imageResizing(inputImage, inputImage.getWidth(), inputImage.getHeight());
		// display white image
		imageLabel = new JLabel();
		imageLabel.setMaximumSize(MainPage.screenSize);
		imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		//imageLabel.setHorizontalTextPosition(JLabel.CENTER);
		//imageLabel.setVerticalTextPosition(JLabel.CENTER);
		setImage(canvasImage);
		displayPoints(0, 100, 100);

	}
	
	public JLabel getImageLabel() {
		return imageLabel;
	}

	public void readFBDFile(String filename) {
		File f = new File(filename);
		ArrayList<String> fileText = readTextFile(f);
		ArrayList<String> pointsString = getPointsFromText(fileText);
		clearFBDData();
		cutsList.clear();

		for (int i = 0; i < pointsString.size(); i++) {
			String[] pointsplit = pointsString.get(i).split(",");
			int x = new Integer(pointsplit[0]);
			int y = new Integer(pointsplit[1]);
			displayPoints(i, x, y);
			cutsList.add(new ZPoint(x, y, i + 1));
		}
		cutCount = pointsString.size() + 1;

		ArrayList<String> linesString = getLinesFromText(fileText);
		for (int i = 0; i < linesString.size(); i++) {
			String[] points = linesString.get(i).split("\\|");
			String[] point1 = points[0].split(",");
			int point1x = Integer.parseInt(point1[0]);
			int point1y = Integer.parseInt(point1[1]);

			String[] point2 = points[1].split(",");
			int point2x = Integer.parseInt(point2[0]);
			int point2y = Integer.parseInt(point2[1]);

			Point p1 = new Point(point1x, point1y);
			Point p2 = new Point(point2x, point2y);
			// drawLineOnImage(p1, p2);
			lineList.add(new Line2D.Double(p1, p2));
		}
		
		hint = getHintFromText(fileText);
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

	private ArrayList<String> getPointsFromText(ArrayList<String> fileText) {
		ArrayList<String> pointsString = new ArrayList<String>();
		int dataIdx = 0;
		while (!fileText.get(dataIdx).contains("Points:"))
			dataIdx++;
		dataIdx++;
		while (!fileText.get(dataIdx).contains("End Points") && dataIdx < fileText.size()) {
			pointsString.add(fileText.get(dataIdx));
			dataIdx++;
		}
		return pointsString;

	}

	void clearFBDData() {
		fbdStart = null;
		fbdRecent = null;
		firstPoint = null;
		secondPoint = null;
		lineList.clear();
	}
	
	void clearFBDDefinigVariables() {
		fbdStart = null;
		fbdRecent = null;
		firstPoint = null;
		secondPoint = null;
	}


	private void displayPoints(int i, int x, int y) {
		Graphics2D g = canvasImage.createGraphics();
		g.setColor(Color.black);

		g.drawImage(scissor, x - 15, y - 15, null);
		g.drawString(Integer.toString(i + 1), x - 15, y - 15);
		g.dispose();
		imageLabel.repaint();
	}
	
	private String getHintFromText(ArrayList<String> fileText){
		String hint = "";
		int dataIdx = 0;
		while (!fileText.get(dataIdx).contains("Hint:"))
			dataIdx++;
		
		String[] hintString = fileText.get(dataIdx).trim().split("\\:");
		hint = hintString[1];
		
		return hint;
	}

	private ArrayList<String> getLinesFromText(ArrayList<String> fileText) {
		ArrayList<String> linesString = new ArrayList<String>();
		int dataIdx = 0;
		while (!fileText.get(dataIdx).contains("Lines:"))
			dataIdx++;
		dataIdx++;
		while (!fileText.get(dataIdx).contains("End Lines") && dataIdx < fileText.size()) {
			linesString.add(fileText.get(dataIdx));
			dataIdx++;
		}
		return linesString;

	}

	void drawLineOnImage(Point p1, Point p2) {
		Graphics2D g = canvasImage.createGraphics();
		Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 9 }, 0);
		g.setStroke(dashed);
		g.setColor(Color.black);
		g.drawLine(p1.x, p1.y, p2.x, p2.y);
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
	
	public void startTest() {
		//reset stuff
		clearFBDDefinigVariables();
		answerLineList.clear();

		drawOriginal();
		drawPoints(cutsList);

		finalAnswer = false;
		
		isFBDAnswered=false;
		for (MouseListener m : imageLabel.getMouseListeners()) {
			imageLabel.removeMouseListener(m);
		}
		
		imageLabel.addMouseListener(new TestModeListener());
	}
	
	void drawOriginal() {
		Graphics2D g = canvasImage.createGraphics();
		g.drawImage(originalImage, 0, 0, canvasImage.getWidth(), canvasImage.getHeight(), 0, 0,
				canvasImage.getWidth(), canvasImage.getHeight(), null);
		g.dispose();
		imageLabel.repaint();
	}
	
	void drawPoints(ArrayList<ZPoint> cutsList) {
		Graphics2D g = canvasImage.createGraphics();
		for (ZPoint p : cutsList) {
			if (p.isCorrect()) {
				// System.out.println(p.getCutCounter());
				p.setCorrect(false);
			}
			g.drawImage(scissor, p.x - 15, p.y - 15, null);
			g.setColor(Color.BLACK);
			g.drawString(Integer.toString(p.getCutCounter()), p.x - 15, p.y - 15);

		}
		g.dispose();
		imageLabel.repaint();
	}
	
	class TestModeListener extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			Point clickedPoint = e.getPoint();
			for (ZPoint p : cutsList) {
				Rectangle clickThresholdRectangle = new Rectangle(p.x - 15, p.y - 15, 30, 30);
				if (clickThresholdRectangle.contains(clickedPoint)) {
					if (fbdStart != null && fbdStart == p) {
						isFBDAnswered = true;

					}
					if (fbdStart == null) {
						fbdStart = p;
						firstPoint = p;
					}
					Graphics2D g = canvasImage.createGraphics();
					g.setColor(Color.green);
					g.drawRect(p.x - 15, p.y - 15, 30, 30);
					if (fbdRecent != null) {
						secondPoint = p;
						Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0,
								new float[] { 9 }, 0);
						g.setStroke(dashed);
						g.setColor(Color.black);
						g.drawLine(fbdRecent.x, fbdRecent.y, p.x, p.y);
//						System.out.println("First: "+firstPoint.getCutCounter()+", Second: "+secondPoint.getCutCounter());
						answerLineList.add(new Line2D.Double(firstPoint, secondPoint));
						firstPoint = secondPoint;
					}
					p.setCorrect(true);
					fbdRecent = p;
					g.dispose();
					imageLabel.repaint();
					
					if(isFBDAnswered) {
						for (MouseListener m : imageLabel.getMouseListeners()) {
							imageLabel.removeMouseListener(m);
						}
						
						//check if same
//						System.out.println("Answered");
//						for(Line2D l: answerLineList) {
//							System.out.println((int)l.getX1()+","+(int)l.getY1()+"|"+(int)l.getX2()+","+(int)l.getY2());
//						}
//						System.out.println("Stored");
//						for(Line2D l: lineList) {
//							System.out.println((int)l.getX1()+","+(int)l.getY1()+"|"+(int)l.getX2()+","+(int)l.getY2());
//						}
						
						boolean isFBDSame = true;
						for(Line2D line: answerLineList) {
							if(!isListContainLine(lineList, line)) {
								isFBDSame = false;
							}
								
						}
						if(isFBDSame == true)
							finalAnswer = true;
						else
							finalAnswer = false;
					}

				}
			}
			super.mouseClicked(e);
		}
		
		boolean isLineEqual(Line2D l1, Line2D l2) {
			if(l1.getP1().equals(l2.getP1()) && l1.getP2().equals(l2.getP2()))
				return true;
			else if(l1.getP1().equals(l2.getP2()) && l1.getP2().equals(l2.getP1()))
				return true;
			else
				return false;
		}
		
		boolean isListContainLine(ArrayList<Line2D> list, Line2D line) {
			for(Line2D l:list) {
				if(isLineEqual(l, line))
					return true;
			}
			return false;
		}

		
	}
	
	public boolean getFinalAnswer() {
		return finalAnswer;
	}

}
