package teamnp.eguru;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import teamnp.eguru.ForcePoint.EntityDirection;
import teamnp.eguru.ForcePoint.EntityProperty;
import teamnp.eguru.ForcePoint.EntityType;

public class ForceSelection {

	private Image forcePointImage = null;
	BufferedImage originalImage, canvasImage;
	JLabel imageLabel;
	// int imageWidth = 800;
	// int imageHeight = 450;

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
	JPanel gui;

	// Force Toolbar radiobuttons
	JRadioButton selectForce;
	JRadioButton selectMoment;
	JRadioButton selectProperyKnown;
	JRadioButton selectProperyUnknown;
	JRadioButton selectDirectionCW;
	JRadioButton selectDirectionCCW;
	JButton undoButton;
	JButton submitForces;
	int ANGLE_TOLERENCE = 5;

	boolean isForcePointSelected = false;
	ForcePoint firstForcePoint, secondForcePoint;
	BufferedImage subImage;
	SizedStack<BufferedImage> undoImageStack = new SizedStack<BufferedImage>(10);
	BufferedImage undoImage;
	public ForceSelection(String filename, BufferedImage inputImage) {
		initialize(inputImage);
		readForceFile(filename);
		startTest();

	}

	private void initialize(BufferedImage inputImage) {
		gui = new JPanel(new BorderLayout(4, 4));
		gui.setBorder(new EmptyBorder(5, 3, 5, 3));
		Dimension guiDim = Toolkit.getDefaultToolkit().getScreenSize();
		guiDim.setSize(guiDim.getWidth() - 200, guiDim.getHeight());
		gui.setMaximumSize(guiDim);
		gui.setAlignmentX(SwingConstants.CENTER);
		gui.setBackground(new Color(0, 44, 61));

		forcePointImage = Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/x-30.png"));
		canvasImage = FBDImage.imageResizing(inputImage, inputImage.getWidth(), inputImage.getHeight());
		// display white image
		imageLabel = new JLabel();
		imageLabel.setMaximumSize(MainPage.screenSize);
		imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		// imageLabel.setHorizontalTextPosition(JLabel.CENTER);
		// imageLabel.setVerticalTextPosition(JLabel.CENTER);
		setImage(canvasImage);

		JPanel imageView = new JPanel(new GridBagLayout());
		imageView.setBackground(new Color(0, 44, 61));
		imageView.add(imageLabel);
		gui.add(imageView, BorderLayout.CENTER);

		JToolBar forceToolbar = getForceToolBar();
		gui.add(forceToolbar, BorderLayout.EAST);

		displayForcePoint(100, 100);

	}

	public JPanel getGui() {
		return gui;
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
		// drawOriginal();
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
			// if (fp.isCorrect == true) {
			// if (fp.type == EntityType.FORCE)
			// displayArrow(fp.x, fp.y, fp.angle, fp.property);
			// else if (fp.type == EntityType.MOMENT)
			// displayMoment(fp.x, fp.y, fp.direction, fp.property);
			// }
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

	public JLabel getImageLabel() {
		return imageLabel;
	}

	JToolBar getForceToolBar() {
		JToolBar tb = new JToolBar(JToolBar.VERTICAL);
		tb.setFloatable(false);

		selectForce = new JRadioButton("Force");
		selectForce.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				currentType = EntityType.FORCE;
				selectDirectionCW.setEnabled(false);
				selectDirectionCCW.setEnabled(false);
			}
		});

		selectMoment = new JRadioButton("Moment");
		selectMoment.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				currentType = EntityType.MOMENT;
				selectDirectionCW.setEnabled(true);
				selectDirectionCCW.setEnabled(true);
			}
		});

		selectProperyKnown = new JRadioButton("Known");
		selectProperyKnown.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				currentProperty = EntityProperty.KNOWN;

			}
		});

		selectProperyUnknown = new JRadioButton("Unknown");
		selectProperyUnknown.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				currentProperty = EntityProperty.UNKNOWN;
			}
		});

		selectDirectionCW = new JRadioButton("Clockwise");
		selectDirectionCW.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				currentDirection = EntityDirection.CLOCKWISE;

			}
		});

		selectDirectionCCW = new JRadioButton("Anticlockwise");
		selectDirectionCCW.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				currentDirection = EntityDirection.ANTICLOCKWISE;

			}
		});

		undoButton = new JButton("Undo");
		undoButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				undo();

			}
		});

		submitForces = new JButton("Submit");
		submitForces.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CheckAnswer checkAns = new CheckAnswer();
				boolean answer = checkAns.getAnswer();

			}

		});

		ButtonGroup bg2 = new ButtonGroup();
		bg2.add(selectForce);
		bg2.add(selectMoment);

		ButtonGroup bg = new ButtonGroup();
		bg.add(selectProperyKnown);
		bg.add(selectProperyUnknown);

		ButtonGroup bg3 = new ButtonGroup();
		bg3.add(selectDirectionCW);
		bg3.add(selectDirectionCCW);

		tb.add(selectForce);
		tb.add(selectMoment);
		tb.addSeparator();
		tb.add(selectProperyKnown);
		tb.add(selectProperyUnknown);
		tb.addSeparator();
		tb.add(selectDirectionCW);
		tb.add(selectDirectionCCW);
		tb.addSeparator();
		tb.add(undoButton);
		tb.add(submitForces);
		// tb.setVisible(false);
		return tb;

	}

	class DrawEntityListener extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			if (currentType == EntityType.FORCE) {
				drawForce(e);
			} else if (currentType == EntityType.MOMENT) {
				drawMoment(e);
			}

			super.mouseClicked(e);
		}

	}

	void drawForce(MouseEvent e) {
		Point clickedPoint = e.getPoint();
		if (isForcePointSelected == false) {

			for (ForcePoint p : fpList) {

				Rectangle clickThresholdRectangle = new Rectangle(p.x - 15, p.y - 15, 30, 30);
				if (clickThresholdRectangle.contains(clickedPoint)) {
					isForcePointSelected = true;
					firstForcePoint = p;

					// Graphics2D g = canvasImage.createGraphics();
					// g.setColor(Color.green);
					// g.drawRect(p.x - 15, p.y - 15, 30, 30);
					// g.dispose();
					// imageLabel.repaint();

					subImage = deepCopy(canvasImage);
					undoImageStack.push(deepCopy(canvasImage));
					int x1 = (p.x - arrowLenght) < 0 ? 0 : (p.x - arrowLenght);
					int y1 = (p.y - arrowLenght) < 0 ? 0 : (p.y - arrowLenght);
					int x2 = (p.x + arrowLenght) > canvasImage.getWidth() ? canvasImage.getWidth()
							: (p.x + arrowLenght);
					int y2 = (p.y + arrowLenght) > canvasImage.getHeight() ? canvasImage.getHeight()
							: (p.y + arrowLenght);
					int width = x2 - x1;
					int height = y2 - y1;

					// subImage = subImage.getSubimage(p.x - arrowLenght, p.y -
					// arrowLenght, arrowLenght * 2,
					// arrowLenght * 2);

					subImage = subImage.getSubimage(x1, y1, width, height);

				}
			}
		} else {
			isForcePointSelected = false;
			secondForcePoint = new ForcePoint(e.getPoint());
			double distance = firstForcePoint.distance(secondForcePoint);
			double ratio = arrowLineLength / distance;
			double new_X = ((1 - ratio) * firstForcePoint.getX()) + ratio * secondForcePoint.getX();
			double new_Y = ((1 - ratio) * firstForcePoint.getY()) + ratio * secondForcePoint.getY();
			ForcePoint newPoint = new ForcePoint((int) new_X, (int) new_Y);
			Graphics2D g = canvasImage.createGraphics();
			// g.drawImage(subImage, (int) firstForcePoint.getX() - arrowLenght,
			// (int) firstForcePoint.getY() - arrowLenght, (int)
			// firstForcePoint.getX() + arrowLenght,
			// (int) firstForcePoint.getY() + arrowLenght, 0, 0, arrowLenght *
			// 2, arrowLenght * 2, null);

			ForcePoint p = firstForcePoint;
			int x1 = (p.x - arrowLenght) < 0 ? 0 : (p.x - arrowLenght);
			int y1 = (p.y - arrowLenght) < 0 ? 0 : (p.y - arrowLenght);
			int x2 = (p.x + arrowLenght) > canvasImage.getWidth() ? canvasImage.getWidth() : (p.x + arrowLenght);
			int y2 = (p.y + arrowLenght) > canvasImage.getHeight() ? canvasImage.getHeight() : (p.y + arrowLenght);

			g.drawImage(subImage, x1, y1, x2, y2, 0, 0, subImage.getWidth(), subImage.getHeight(), null);
			g.dispose();
			imageLabel.repaint();

			drawArrow(firstForcePoint, newPoint);

			// mark the point as correct
			firstForcePoint.setCorrect(true);

			ForcePoint pointToAdd = new ForcePoint(firstForcePoint.x, firstForcePoint.y);
			pointToAdd.setCorrect(true);
			pointToAdd.setType(EntityType.FORCE);
			pointToAdd.setProperty(
					currentProperty == EntityProperty.KNOWN ? EntityProperty.KNOWN : EntityProperty.UNKNOWN);
			// calculate angle
			double angleRad = Math.atan2((new_Y - firstForcePoint.getY()) * -1, new_X - firstForcePoint.getX());
			double angleDeg = Math.toDegrees(angleRad);
			angleDeg = (angleDeg + 360) % 360;
			pointToAdd.setAngle((int) angleDeg);

			forceDataList.add(pointToAdd);
		}
	}

	void drawMoment(MouseEvent e) {
		Point clickedPoint = e.getPoint();
		Color arcColor = null;
		for (ForcePoint p : fpList) {
			Rectangle clickThresholdRectangle = new Rectangle(p.x - 15, p.y - 15, 30, 30);
			if (clickThresholdRectangle.contains(clickedPoint)) {

				undoImageStack.push(deepCopy(canvasImage));
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

				// mark p as correct
				p.setCorrect(true);
				ForcePoint pointToAdd = new ForcePoint(p.x, p.y);
				pointToAdd.setCorrect(true);
				pointToAdd.setType(EntityType.MOMENT);
				pointToAdd.setProperty(
						currentProperty == EntityProperty.KNOWN ? EntityProperty.KNOWN : EntityProperty.UNKNOWN);
				pointToAdd.setDirection(currentDirection == EntityDirection.CLOCKWISE ? EntityDirection.CLOCKWISE
						: EntityDirection.ANTICLOCKWISE);

				forceDataList.add(pointToAdd);

			}
		}
	}

	class DrawForcesMotionListener implements MouseMotionListener {

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseMoved(MouseEvent e) {
			if (isForcePointSelected == true) {
				secondForcePoint = new ForcePoint(e.getPoint());
				double distance = firstForcePoint.distance(secondForcePoint);
				if (distance == 0)
					return;
				double ratio = arrowLineLength / distance;
				double new_X = ((1 - ratio) * firstForcePoint.getX()) + ratio * secondForcePoint.getX();
				double new_Y = ((1 - ratio) * firstForcePoint.getY()) + ratio * secondForcePoint.getY();
				ForcePoint newPoint = new ForcePoint((int) new_X, (int) new_Y);
				double angleRad = Math.atan2((new_Y - firstForcePoint.getY()) * -1, new_X - firstForcePoint.getX());
				double angleDeg = Math.toDegrees(angleRad);
				angleDeg = (angleDeg + 360) % 360;

				Graphics2D g = canvasImage.createGraphics();
				// g.drawImage(subImage, (int) firstForcePoint.getX() -
				// arrowLenght,
				// (int) firstForcePoint.getY() - arrowLenght, (int)
				// firstForcePoint.getX() + arrowLenght,
				// (int) firstForcePoint.getY() + arrowLenght, 0, 0, arrowLenght
				// * 2, arrowLenght * 2, null);
				ForcePoint p = firstForcePoint;
				int x1 = (p.x - arrowLenght) < 0 ? 0 : (p.x - arrowLenght);
				int y1 = (p.y - arrowLenght) < 0 ? 0 : (p.y - arrowLenght);
				int x2 = (p.x + arrowLenght) > canvasImage.getWidth() ? canvasImage.getWidth() : (p.x + arrowLenght);
				int y2 = (p.y + arrowLenght) > canvasImage.getHeight() ? canvasImage.getHeight() : (p.y + arrowLenght);

				g.drawImage(subImage, x1, y1, x2, y2, 0, 0, subImage.getWidth(), subImage.getHeight(), null);

				g.setColor(Color.BLACK);
				g.drawString(Integer.toString((int) angleDeg) + "°", firstForcePoint.x - 15, firstForcePoint.y - 15);
				g.dispose();
				imageLabel.repaint();
				drawArrow(firstForcePoint, newPoint);

			}

		}

	}

	static BufferedImage deepCopy(BufferedImage bi) {
		ColorModel cm = bi.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = bi.copyData(null);
		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}

	public class SizedStack<T> extends Stack<T> {
		private int maxSize;

		public SizedStack(int size) {
			super();
			this.maxSize = size;
		}

		@Override
		public T push(T object) {
			// If the stack is too big, remove elements until it's the right
			// size.
			while (this.size() >= maxSize) {
				this.remove(0);
			}
			return super.push(object);
		}
	}

	public void startTest() {
		drawOriginal();
		drawForcePoints(fpList);
		// setup defauls of forceToolbar
		selectForce.setSelected(true);
		selectMoment.setSelected(false);
		selectProperyKnown.setSelected(true);
		selectProperyUnknown.setSelected(false);
		selectDirectionCW.setSelected(true);
		selectDirectionCCW.setSelected(false);
		selectDirectionCW.setEnabled(false);
		selectDirectionCCW.setEnabled(false);
		submitForces.setVisible(true);

		forceDataList.clear();

		currentType = EntityType.FORCE;
		currentProperty = EntityProperty.KNOWN;
		currentDirection = EntityDirection.CLOCKWISE;

		for (MouseListener m : imageLabel.getMouseListeners()) {
			imageLabel.removeMouseListener(m);
		}

		imageLabel.addMouseListener(new DrawEntityListener());
		imageLabel.addMouseMotionListener(new DrawForcesMotionListener());

	}

	void drawOriginal() {
		Graphics2D g = canvasImage.createGraphics();
		g.drawImage(originalImage, 0, 0, canvasImage.getWidth(), canvasImage.getHeight(), 0, 0, canvasImage.getWidth(),
				canvasImage.getHeight(), null);
		g.dispose();
		imageLabel.repaint();
	}

	void drawForcePoints(ArrayList<ForcePoint> fpList) {
		Graphics2D g = canvasImage.createGraphics();
		for (ForcePoint p : fpList) {
			if (p.isCorrect()) {
				p.setCorrect(false);
			}
			g.drawImage(forcePointImage, p.x - 15, p.y - 15, null);
			// g.setColor(Color.BLACK);
			// g.drawString(Integer.toString(p.getCutCounter()), p.x - 15, p.y -
			// 15);

		}
		g.dispose();
		imageLabel.repaint();
	}

	public boolean getFinalAnswer() {
		CheckAnswer ca = new CheckAnswer();
		return ca.getAnswer();
	}

	class CheckAnswer {
		public boolean getAnswer() {
			// there are 2 list, correctForceDataList (A) contains all
			// correct answers, forceDataList (B) contains answer entered by
			// user
			// To check correctness we have to check every correct
			// ForcePoint from A is in B, and every correct ForcePoint frmo
			// B is in A.

			boolean forceFinalAnswer = true;

			for (ForcePoint p : correctForceDataList) {
				if (p.isCorrect()) {
					if (!forceListContains(forceDataList, p))
						forceFinalAnswer = false;
				}
			}
			for (ForcePoint p : forceDataList) {
				if (p.isCorrect()) {
					if (!forceListContains(correctForceDataList, p))
						forceFinalAnswer = false;
				}
			}

//			forceDataList.clear();
			return forceFinalAnswer;

		}

		private boolean forceListContains(ArrayList<ForcePoint> forceList, ForcePoint p) {
			if (p.type == EntityType.FORCE) {
				return forceListContainsForce(forceList, p);
			} else if (p.type == EntityType.MOMENT) {
				return forceListContainsMoment(forceList, p);
			}
			return false;
		}

		private boolean forceListContainsMoment(ArrayList<ForcePoint> forceList, ForcePoint p) {
			for (ForcePoint listPoint : forceList) {
				if (p.x == listPoint.x && p.y == listPoint.y && listPoint.isCorrect == true && p.type == listPoint.type
						&& p.getProperty() == listPoint.getProperty() && p.getDirection() == listPoint.getDirection())
					return true;
			}
			return false;
		}

		private boolean forceListContainsForce(ArrayList<ForcePoint> forceList, ForcePoint p) {
			for (ForcePoint listPoint : forceList) {
				if (p.x == listPoint.x && p.y == listPoint.y && listPoint.isCorrect == true && p.type == listPoint.type
						&& p.getProperty() == listPoint.getProperty()
						&& equalWithTolerance(p.getAngle(), listPoint.getAngle()))
					return true;
			}
			return false;
		}

		private boolean equalWithTolerance(int angle1, int angle2) {
			int upperTolerance = angle2 + ANGLE_TOLERENCE;
			int lowerTolerance = angle2 - ANGLE_TOLERENCE;

			if (lowerTolerance < 0) {
				lowerTolerance = 360 + lowerTolerance;
				if ((angle1 >= 0 && angle1 <= upperTolerance) || (angle1 >= lowerTolerance && angle1 <= 359))
					return true;
				else
					return false;
			}
			if (upperTolerance > 359) {
				upperTolerance = upperTolerance - 360;
				if ((angle1 >= 0 && angle1 <= upperTolerance) || (angle1 >= lowerTolerance && angle1 <= 359))
					return true;
				else
					return false;
			} else {
				if ((angle1 <= angle2 + ANGLE_TOLERENCE) && (angle1 >= angle2 - ANGLE_TOLERENCE))
					return true;
				else
					return false;
			}
		}
	}

	private void undo() {

		Graphics2D g = canvasImage.createGraphics();
		if (undoImageStack.isEmpty())
			return;
		undoImage = undoImageStack.pop();
		if (undoImage != null) {
			g.drawImage(undoImage, 0, 0, canvasImage.getWidth(), canvasImage.getHeight(), 0, 0, canvasImage.getWidth(),
					canvasImage.getHeight(), null);
			g.dispose();
			imageLabel.repaint();
			ForcePoint pointToRemove = forceDataList.get(forceDataList.size() - 1);
			forceDataList.remove(forceDataList.size() - 1);
			// if this list does not have this point anymore i.e. all
			// forces/moments related to it are removed, then mark the point
			// as
			// incorrect in fpList
			if (!fpListContainsLocation(forceDataList, pointToRemove)) {
				for (ForcePoint p : fpList) {
					if (p.getLocation().equals(pointToRemove.getLocation())) {
						p.setCorrect(false);
					}
				}
			}
		}

	}
}
