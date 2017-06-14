import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
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
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

import java.awt.Rectangle;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;

public class SecWindow {

	private JFrame frame;
	boolean is_submitted = false;
	

	/**
	 * Launch the application.
	 */
	static String path="";
	static String folderPath="";
	static  JPanel panel;
	static EngineeringEducator eduObject = new EngineeringEducator();
	static EngineeringEducatorLogic eduLogicObj = new EngineeringEducatorLogic();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//path=args[0];
					folderPath="Questions/";
					System.out.println("path" + folderPath);
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
	 * @param path 
	 * @throws IOException 
	 */
	public SecWindow() throws IOException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
	private void initialize() throws IOException {
		
		//String path = "C:\\Users\\manish k\\workspace\\SecProject\\Questions\\";
		eduObject.setParentDir(new File(folderPath));
		eduLogicObj.FolderRandomSelection(eduObject, folderPath);
		System.out.println("new path=" + eduObject.questionDir);
		File dir1 = new File(eduObject.questionDir);
		eduLogicObj.DataPreProcessing(eduObject);
		is_submitted = false;
	
		if(frame==null)
			frame = new JFrame();
		else{
			
			frame.dispose();
			frame=new JFrame();
		}
		frame.setBounds(100, 100, 450, 300);
		frame.setForeground(Color.BLACK);
		frame.setBackground(Color.RED);
		frame.getContentPane().setBackground(new Color(51, 153, 204));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// create a jtextarea
		if(panel==null)
			panel = new JPanel();
		else
			panel.removeAll();
		//panel.setLayout(new BorderLayout());
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        
      //Final Score Displayed Here
        JLabel lblNewLabel,lblNewLabel2;
        lblNewLabel2=new JLabel("SCORE=0");
      	lblNewLabel = new JLabel("ENGINEERING EDUCATORS ");
      	lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
      	lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
      	lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
      	panel.add(lblNewLabel);
      	panel.add(lblNewLabel2);
      	JSeparator sep = new JSeparator();
	    sep.setMaximumSize(new Dimension(0, 30));
	    sep.setOpaque(false);
	    panel.add(sep);
      	
	    //splitpane
	    JPanel panel1= new JPanel();
	    FlowLayout flowLayout_1 = (FlowLayout) panel1.getLayout();
	    flowLayout_1.setHgap(150);
	    flowLayout_1.setVgap(50);
	    flowLayout_1.setAlignment(FlowLayout.LEFT);
	    JPanel panel2=new JPanel();
	    FlowLayout flowLayout = (FlowLayout) panel2.getLayout();
	    flowLayout.setHgap(150);
	    flowLayout.setVgap(50);
	    flowLayout.setAlignment(FlowLayout.LEFT);
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setVerticalAlignment(SwingConstants.TOP);
		
		lblNewLabel_1.setIcon(new ImageIcon(eduObject.getModelImg()));
		panel1.add(lblNewLabel_1, "22, 2, fill, default"); 
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setVerticalAlignment(SwingConstants.TOP);
		
		lblNewLabel_2.setIcon(new ImageIcon(eduObject.getFbdImg()));
		
		panel2.add(lblNewLabel_2, "22, 6, fill, default");
		JSplitPane splitpane= new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,false,panel1,panel2);
		splitpane.setAlignmentY(0.5f);
		
		panel.add(splitpane);
		
		//Checkboxes inside Internal Frame 1 (Assumptions)
		ArrayList<String> tempAssumptions = eduLogicObj.FileReading(eduObject.assumptionsPath);
		ArrayList<String> assumptions = new ArrayList<String>();
		ArrayList<Integer> assumptionAns = new ArrayList<Integer>();
		ArrayList<JCheckBox> assumptionChkbxList = new ArrayList<JCheckBox>();
		
		for (int j = 0; j < tempAssumptions.size(); j++) {
			String[] splitter = tempAssumptions.get(j).split("\\|");
			assumptions.add(splitter[0]);
			assumptionAns.add(Integer.parseInt(splitter[1]));
			JCheckBox chkbxAssumption = new JCheckBox(assumptions.get(j));
			assumptionChkbxList.add(chkbxAssumption);
			panel.add(chkbxAssumption, "22," + new Integer((j * 2) + 10).toString()+", fill, default");
		}
		
		
		JButton retakebtn=new JButton("Retake Test");
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
					panel.add(rdbReason, "2," + new Integer((j + 1) * 2).toString());
				}
		
		
		//Submit button
		JButton submitButton = new JButton("Submit");
		//JButton retake=new JButton("Retake Test");
		
		
		submitButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
				if(is_submitted){
					if(reasonList.get(0).isSelected()){
					//JOptionPane.showMessageDialog(null, " RESUBMITTED!!! \n Score = 2");
					
					lblNewLabel2.setText("Current Score = 2");
					}
					else
					{
						//JOptionPane.showMessageDialog(null, "Resubmitted!! Score : 0" );
						lblNewLabel2.setText("Current Score = 0");
						
					}
					eduLogicObj.DisableRadioButton(reasonList);
					submitButton.setVisible(false);
					retakebtn.setVisible(true);
					
					
					
				}
				else{
					boolean ans = true;
					// If first assumption is the only correct one
					for(int j = 0 ; j < assumptionChkbxList.size() ; j++){
						ans = ans & eduLogicObj.CheckAnswer(assumptionChkbxList.get(j), assumptionAns.get(j));
					}
					if(ans){
						//JOptionPane.showMessageDialog(null, " SUBMITTED!!! \n Score = 3");
						lblNewLabel2.setText("Score = 3");
						submitButton.setVisible(false);
						retakebtn.setVisible(true);
					}
					else{
						//JOptionPane.showMessageDialog(null, " INCORRECT ANSWER!!! Choose reason");
					//internalFrame_1.setVisible(true);
					}
					eduLogicObj.DisableCheckBox(assumptionChkbxList);
					is_submitted = true;
				}
			}
		});
		panel.add(submitButton);
		panel.add(retakebtn);
		JScrollPane scrollPane = new JScrollPane(panel);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.setVisible(true);
	
	}
	public static BufferedImage resizeImg(BufferedImage img, int width, int height){
		System.out.println("Inside resizing");
		BufferedImage bimg = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
		Graphics2D g2d = (Graphics2D) bimg.createGraphics();
		g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
		g2d.drawImage(img, 0, 0, width,height,null);
		g2d.dispose();
		System.out.println("Exiting resizing");
		return bimg;
	}

}
