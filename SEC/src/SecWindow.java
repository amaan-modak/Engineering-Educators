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
		File dir = new File(folderPath);
		String[] quesfolders = new String[20];
		quesfolders = dir.list();
		Random rand = new Random();
		String folder = quesfolders[rand.nextInt(quesfolders.length)];
		//System.out.println("folder selected " + folder);
		path = folderPath+folder;
		System.out.println("new path=" + path);
		File dir1 = new File(path);
		File[] files = dir1.listFiles();
		BufferedImage fbdimg = null;
		BufferedImage modelimg = null;
		String assumptionspath = null;
		String reasonspath = null;
		is_submitted = false;
		for(int i = 0 ; i < files.length ; i++)
		{
			BufferedImage img = null;
			//BufferedImage fbdimg = null;
			//BufferedImage modelimg = null;
			System.out.println(files[i].getName());
			if(files[i].getName().equals("fbd.jpg")){
				try {
					img = ImageIO.read(new File(files[i].getPath()));
					//finimg = resizeImg(img,200,200);
					fbdimg = resizeImg(img,300,300);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//lblNewLabel.setIcon(new ImageIcon(finimg));
			}
			else if(files[i].getName().equals("model.jpg")){
				try {
					img = ImageIO.read(new File(files[i].getPath()));
					modelimg = resizeImg(img,300,300);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(files[i].getName().equals("assumptions.txt")){
					assumptionspath = files[i].getPath();
					System.out.println(assumptionspath + " assumptions");
			}
			else if(files[i].getName().equals("reasons.txt")){
				reasonspath = files[i].getPath();
				System.out.println(reasonspath + " reasons");
			}
			
		}
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
		
		lblNewLabel_1.setIcon(new ImageIcon(modelimg));
		panel1.add(lblNewLabel_1, "22, 2, fill, default"); 
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setVerticalAlignment(SwingConstants.TOP);
		
		lblNewLabel_2.setIcon(new ImageIcon(fbdimg));
		
		panel2.add(lblNewLabel_2, "22, 6, fill, default");
		JSplitPane splitpane= new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,false,panel1,panel2);
		splitpane.setAlignmentY(0.5f);
		
		panel.add(splitpane);
		
		//Checkboxes inside Internal Frame 1 (Assumptions)
		FileInputStream fstream = new FileInputStream(assumptionspath);
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

		String strLine;
		String[] a = new String[3];
		int i = 0;
		
		//Read File Line By Line
		while ((strLine = br.readLine()) != null){
			a[i] = strLine;
			System.out.println (strLine);
			i++;
		}
		br.close();
		//JSeparator sep2 = new JSeparator();
	    //sep2.setMaximumSize(new Dimension(0, 30));
	    //sep2.setOpaque(false);
	    //panel.add(sep2);
		JCheckBox chckbxNewCheckBox_4 = new JCheckBox(a[0]);
		//internalFrame.getContentPane().add(chckbxNewCheckBox_4, "22, 10, fill, default");
		panel.add(chckbxNewCheckBox_4, "22, 17, fill, default");
		JCheckBox chckbxNewCheckBox_5 = new JCheckBox(a[1]);
		//internalFrame.getContentPane().add(chckbxNewCheckBox_5, "22, 12, fill, default");
		panel.add(chckbxNewCheckBox_5, "22, 12, fill, default");
		JCheckBox chckbxNewCheckBox_6 = new JCheckBox(a[2]);
	//	internalFrame.getContentPane().add(chckbxNewCheckBox_6, "22, 14, fill, default");
		panel.add(chckbxNewCheckBox_6, "22, 14, fill, default");
		//JButton btnSubmit = new JButton("Submit");
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
		
		
		FileInputStream fstream2 = new FileInputStream(reasonspath);
		BufferedReader br2 = new BufferedReader(new InputStreamReader(fstream2));

		String strLine2;
		String[] r = new String[4];
		 i = 0;
		
		//Read File Line By Line
		while ((strLine2 = br2.readLine()) != null){
			r[i] = strLine2;
			System.out.println (strLine2);
			i++;
		}
		br2.close();

		
		//Checkboxes inside Internal Frame 2
		JCheckBox chckbxNewCheckBox = new JCheckBox(r[0]);
		//internalFrame_1.getContentPane().add(chckbxNewCheckBox, "2, 2, fill, default");
		panel.add(chckbxNewCheckBox);
			
				
		JCheckBox chckbxNewCheckBox_1 = new JCheckBox(r[1]);
		//internalFrame_1.getContentPane().add(chckbxNewCheckBox_1, "2, 4");
		panel.add(chckbxNewCheckBox_1);
		
		JCheckBox chckbxNewCheckBox_2 = new JCheckBox(r[2]);
		//internalFrame_1.getContentPane().add(chckbxNewCheckBox_2, "2, 6");
		panel.add(chckbxNewCheckBox_2);
		
		JCheckBox chckbxNewCheckBox_3 = new JCheckBox(r[3]);
		//internalFrame_1.getContentPane().add(chckbxNewCheckBox_3, "2, 8");
		panel.add(chckbxNewCheckBox_3);		
		//internalFrame_1.pack();
		//internalFrame_1.setResizable(true);
		//internalFrame_1.setVisible(false);
		
		//Submit button
		JButton submitButton = new JButton("SubmitZaki");
		//JButton retake=new JButton("Retake Test");
		
		
		submitButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
				if(is_submitted){
					if(chckbxNewCheckBox.isSelected() && !chckbxNewCheckBox_1.isSelected() && !chckbxNewCheckBox_2.isSelected() && !chckbxNewCheckBox_3.isSelected()){
					//JOptionPane.showMessageDialog(null, " RESUBMITTED!!! \n Score = 2");
					
					lblNewLabel2.setText("Current Score = 2");
					}
					else
					{
						//JOptionPane.showMessageDialog(null, "Resubmitted!! Score : 0" );
						lblNewLabel2.setText("Current Score = 0");
						
					}
					chckbxNewCheckBox.setEnabled(false);
					chckbxNewCheckBox_1.setEnabled(false);
					chckbxNewCheckBox_2.setEnabled(false);
					chckbxNewCheckBox_3.setEnabled(false);
					submitButton.setVisible(false);
					retakebtn.setVisible(true);
					
					
					
				}
				else{
					//If first assumption is the only correct one
					if(chckbxNewCheckBox_4.isSelected() && !chckbxNewCheckBox_5.isSelected()
							&&!chckbxNewCheckBox_6.isSelected()){
						//JOptionPane.showMessageDialog(null, " SUBMITTED!!! \n Score = 3");
						lblNewLabel2.setText("Score = 3");
						chckbxNewCheckBox_4.setEnabled(false);
						chckbxNewCheckBox_5.setEnabled(false);
						chckbxNewCheckBox_6.setEnabled(false);
						submitButton.setVisible(false);
						retakebtn.setVisible(true);
					}
					else{
						//JOptionPane.showMessageDialog(null, " INCORRECT ANSWER!!! Choose reason");
					chckbxNewCheckBox_4.setEnabled(false);
					chckbxNewCheckBox_5.setEnabled(false);
					chckbxNewCheckBox_6.setEnabled(false);
					//internalFrame_1.setVisible(true);
					}
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
