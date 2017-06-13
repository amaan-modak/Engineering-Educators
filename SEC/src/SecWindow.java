import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.Rectangle;
import javax.swing.SwingConstants;

public class SecWindow {

	private static JFrame frame;
	private JLabel lblNewLabel;

	/**
	 * Launch the application.
	 */
	static String path = "";
	static String folderPath = "";
	static JPanel panel;
	static EngineeringEducator eduObject = new EngineeringEducator();
	static EngineeringEducatorLogic eduLogicObj = new EngineeringEducatorLogic();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// path=args[0];
					folderPath = "Questions/";
					System.out.println("path" + folderPath);
					eduObject.setParentDir(new File(folderPath));
					eduLogicObj.FolderRandomSelection(eduObject, folderPath);
					SecWindow window = new SecWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * 
	 * @param path
	 * @throws IOException
	 */
	public SecWindow() throws IOException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws IOException
	 */
	private void initialize() throws IOException {
		System.out.println("new path=" + eduObject.questionDir);
		File dir1 = new File(eduObject.questionDir);
		eduLogicObj.DataPreProcessing(eduObject);
		if (frame == null)
			frame = new JFrame();
		else {

			frame.dispose();
			frame = new JFrame();
		}
		frame.setBounds(100, 100, 450, 300);
		frame.setForeground(Color.BLACK);
		frame.setBackground(Color.RED);
		frame.getContentPane().setBackground(new Color(51, 153, 204));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// create a jtextarea
		if (panel == null)
			panel = new JPanel();
		else
			panel.removeAll();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		// Final Score Displayed Here
		lblNewLabel = new JLabel(" ");
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel.add(lblNewLabel);
		JInternalFrame internalFrame = new JInternalFrame("ENGINEERING EDUCATORS");
		internalFrame.setNormalBounds(new Rectangle(10, 10, 200, 200));
		// panel.add(internalFrame, "2, 4, 9, 1, fill, fill");
		panel.add(internalFrame);
		// Set layout as Form Layout
		internalFrame.getContentPane()
				.setLayout(new FormLayout(new ColumnSpec[] { FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
						FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC,
						FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
						FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC,
						FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
						FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
						FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC,
						FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, },
						new RowSpec[] { FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
								FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
								FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
								FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
								FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
								FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
								FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
								FormSpecs.DEFAULT_ROWSPEC, }));

		// Model
		JLabel lblNewLabel_1 = new JLabel("Real world Scenario");
		lblNewLabel_1.setAlignmentY(50);
		lblNewLabel_1.setIcon(new ImageIcon(eduObject.modelImg));
		internalFrame.getContentPane().add(lblNewLabel_1, "22, 2, fill, default");

		// FBD
		JLabel lblNewLabel_2 = new JLabel("Free Body Diagram");
		lblNewLabel_2.setIcon(new ImageIcon(eduObject.fbdImg));
		internalFrame.getContentPane().add(lblNewLabel_2, "22, 6, fill, default");

		// Checkboxes inside Internal Frame 1 (Assumptions)

		ArrayList<String> tempAssumptions = eduLogicObj.FileReading(eduObject.assumptionsPath);
		ArrayList<String> assumptions = new ArrayList<String>();
		ArrayList<Integer> assumptionAns = new ArrayList<Integer>();
		ArrayList<JCheckBox> assumptionList = new ArrayList<JCheckBox>();
		
		
		// Checkboxes inside Internal Frame 2
		for (int j = 0; j < tempAssumptions.size(); j++) {
			String[] splitter = tempAssumptions.get(j).split("\\|");
			assumptions.add(splitter[0]);
			assumptionAns.add(Integer.parseInt(splitter[1]));
			JCheckBox chkAssumption = new JCheckBox(assumptions.get(j));
			assumptionList.add(chkAssumption);
			internalFrame.getContentPane().add(chkAssumption, "22," + new Integer((j * 2) + 10).toString());
		}

		JButton retakebtn = new JButton("Retake Test");
		retakebtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {

					initialize();

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		retakebtn.setVisible(false);

		internalFrame.pack();
		internalFrame.setResizable(true);
		internalFrame.setVisible(true);
		// Internal Frame 1 ends

		// Internal Frame 2 starts (Reasons)
		JInternalFrame internalFrame_1 = new JInternalFrame("SELECT VALID REASONS");
		panel.add(internalFrame_1);
		internalFrame_1.getContentPane().setLayout(new FormLayout(
				new ColumnSpec[] { FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"), },
				new RowSpec[] { FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, }));

		ArrayList<String> tempReasons = eduLogicObj.FileReading(eduObject.reasonsPath);
		ArrayList<JRadioButton> reasonList = new ArrayList<JRadioButton>();
		ArrayList<String> reasons = new ArrayList<String>();
		ArrayList<Integer> reasonAns = new ArrayList<Integer>();
		// Radio buttons inside Internal Frame 2
		for (int j = 0; j < tempReasons.size(); j++) {
			String[] splitter = tempReasons.get(j).split("\\|");
			reasons.add(splitter[0]);
			reasonAns.add(Integer.parseInt(splitter[1]));
			JRadioButton rdbReason = new JRadioButton(reasons.get(j));
			reasonList.add(rdbReason);
			internalFrame_1.getContentPane().add(rdbReason, "2," + new Integer((j + 1) * 2).toString());
		}
		internalFrame_1.pack();
		internalFrame_1.setResizable(true);
		internalFrame_1.setVisible(false);

		// Submit button
		JButton submitButton = new JButton("Submit");
		// JButton retake=new JButton("Retake Test");

		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (internalFrame_1.isVisible()) {
					if (reasonList.get(0).isSelected()) {
						JOptionPane.showMessageDialog(null, " RESUBMITTED!!! \n Score = 2");
						lblNewLabel.setText("Score = 2");
					} else {
						JOptionPane.showMessageDialog(null, "Resubmitted!! Score : 0");
						lblNewLabel.setText("Score = 0");

					}
					eduLogicObj.DisableRadioButton(reasonList);
					submitButton.setVisible(false);
					retakebtn.setVisible(true);

				} else {
					boolean ans = true;
					// If first assumption is the only correct one
					for(int j = 0 ; j < assumptionList.size() ; j++){
						ans = ans & eduLogicObj.CheckAnswer(assumptionList.get(j), assumptionAns.get(j));
					}
					if (ans) {
						JOptionPane.showMessageDialog(null, " SUBMITTED!!! \n Score = 3");
						lblNewLabel.setText("Score = 3");
						submitButton.setVisible(false);
						retakebtn.setVisible(true);
					} else {
						JOptionPane.showMessageDialog(null, " INCORRECT ANSWER!!! Choose reason");
						internalFrame_1.setVisible(true);
					}
					eduLogicObj.DisableCheckBox(assumptionList);
					
				}
			}
		});
		panel.add(submitButton);
		panel.add(retakebtn);
		JScrollPane scrollPane = new JScrollPane(panel);
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		frame.setVisible(true);

	}
}
