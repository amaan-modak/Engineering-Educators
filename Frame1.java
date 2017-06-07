import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class Frame1 {

	private JFrame frame;
	private JLabel lblNewLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) throws IOException{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame1 window = new Frame1();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Frame1() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setFont(new Font("Calibri", Font.BOLD, 14));
		frame.setForeground(Color.BLACK);
		frame.setBackground(Color.RED);
		frame.getContentPane().setBackground(new Color(51, 153, 204));
		frame.setBounds(100, 100, 832, 696);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Set Layout of Entire Frame as Form Layout
		frame.getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("20px"),
				ColumnSpec.decode("278px"),
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.UNRELATED_GAP_COLSPEC,
				ColumnSpec.decode("365px"),},
			new RowSpec[] {
				RowSpec.decode("30px"),
				RowSpec.decode("25px"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("338px"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("202px"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("25px"),}));
		
		//Final Score Displayed Here
		lblNewLabel = new JLabel(" ");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		frame.getContentPane().add(lblNewLabel, "6, 2, fill, top");
		
		//Internal Frame 1 starts
		JInternalFrame internalFrame = new JInternalFrame("New JInternalFrame");
		internalFrame.setResizable(true);
		frame.getContentPane().add(internalFrame, "2, 4, 9, 1, fill, fill");
		internalFrame.setVisible(true);

		ImageIcon img1 = new ImageIcon("C:\\Users\\Aslam\\Desktop\\UB Courses\\Fall 2016\\CSE 573 - CVIP\\Assignments\\Assignment 2\\Problem 1\\lena.jpg");
		ImageIcon img2 = new ImageIcon("C:\\Users\\Aslam\\Desktop\\UB Courses\\Fall 2016\\CSE 573 - CVIP\\Assignments\\Assignment 2\\Problem 1\\lena.jpg");
		
		JInternalFrame internalFrame_1 = new JInternalFrame("New JInternalFrame");
		//Set layout as Form Layout
		internalFrame.getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		
		//Image 1
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setIcon(img1);
		internalFrame.getContentPane().add(lblNewLabel_1, "22, 2, fill, default");
		
		//Image 2
		JLabel lblNewLabel_2 = new JLabel("New label");
		lblNewLabel_2.setIcon(img2);
		internalFrame.getContentPane().add(lblNewLabel_2, "22, 6, fill, default");
		
		//Checkboxes inside Internal Frame 1
		JCheckBox chckbxNewCheckBox_4 = new JCheckBox("A");
		internalFrame.getContentPane().add(chckbxNewCheckBox_4, "22, 10, fill, default");
		
		JCheckBox chckbxNewCheckBox_5 = new JCheckBox("B");
		internalFrame.getContentPane().add(chckbxNewCheckBox_5, "22, 12, fill, default");
		
		JCheckBox chckbxNewCheckBox_6 = new JCheckBox("C");
		internalFrame.getContentPane().add(chckbxNewCheckBox_6, "22, 14, fill, default");
		//Internal Frame 1 ends
		
		//Internal Frame 2 starts
		frame.getContentPane().add(internalFrame_1, "2, 6, 9, 1, fill, fill");
		internalFrame_1.getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		
		//Checkboxes inside Internal Frame 2
		JCheckBox chckbxNewCheckBox = new JCheckBox("1");
		internalFrame_1.getContentPane().add(chckbxNewCheckBox, "2, 2, fill, default");
		
		JCheckBox chckbxNewCheckBox_1 = new JCheckBox("2");
		internalFrame_1.getContentPane().add(chckbxNewCheckBox_1, "2, 4");
		
		JCheckBox chckbxNewCheckBox_3 = new JCheckBox("3");
		internalFrame_1.getContentPane().add(chckbxNewCheckBox_3, "2, 6");
		
		JCheckBox chckbxNewCheckBox_2 = new JCheckBox("4");
		internalFrame_1.getContentPane().add(chckbxNewCheckBox_2, "2, 8");
		
		//Submit Button Inside Internal Frame 2
		JButton button = new JButton("Submit");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, " RESUBMITTED!!! \n Score = 2 :-)");
				lblNewLabel.setText("Score = 2");
			}
		});
		button.setForeground(Color.WHITE);
		button.setFont(new Font("Calibri", Font.BOLD, 12));
		button.setBackground(new Color(0, 0, 153));
		internalFrame_1.getContentPane().add(button, "2, 12");
		internalFrame_1.setVisible(false);
		//internalFrame_1.setVisible(true);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, " SUBMITTED!!! \n Score = 3 :-)");
				lblNewLabel.setText("Score = 3");
			}
		});
		
		btnSubmit.setForeground(Color.WHITE);
		btnSubmit.setBackground(new Color(0, 0, 153));
		btnSubmit.setFont(new Font("Calibri", Font.BOLD, 12));
		frame.getContentPane().add(btnSubmit, "6, 8, fill, top");	
	}
}
