package teamnp.eguru;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class MainPage {
	/**
	 * Launch the application.
	 */

	//variables for fbd selection
	JLabel fbdAnswer;
	int fbdRetryAttempts;
	int forceRetryAttempts;
	JLabel fbdHintText;
	static int hintcounter=0;
	
	// Global declaration of local variables
	boolean isSubmitted = false;
	int score = 0;
	static String folderPath = "Questions/";
	static String questionPath = "";
	

	// Global declaration of GUI elements
	private JFrame frame;
	static JPanel panel;
	
	JLabel imageLabel;
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); 

	// Global declaration of class objects
	static Question questObject;
	static QuestionsHandler qHandleObject  = new QuestionsHandler(folderPath);
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					if(qHandleObject.selectRandomFolder == false){
						qHandleObject.questionsInOrder();
						System.out.println("Done");
					}
					questionPath = qHandleObject.selectQuestion();
					questObject = new Question(questionPath);
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // Set Look and Feel of the UI
					MainPage window = new MainPage();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				System.out.println("Deleting temp files");
				File lp = new File(qHandleObject.gitCloneFolderName);
				try {
					qHandleObject.deleteDir(lp);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}
	
	
	/**
	 * Create the application.
	 * 
	 * @param path
	 */
	public MainPage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 */
	private void initialize() {
		/* Local variable initialization */
		JLabel lblTitle, lblScore, lblQuestion;
		
		isSubmitted = false;
		questObject.readQuestion();
		/* Designing frame */
		if (frame == null)
			frame = new JFrame();
		else {
			frame.getContentPane().removeAll();
		}
		frame.setBounds(0, 0, 1366, 768);
		frame.setSize(1366, 768);
		frame.setForeground(Color.BLACK);
		frame.setBackground(Color.RED);
		frame.getContentPane().setBackground(new Color(0, 44, 61));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("eGuru");
//		frame.setAlwaysOnTop(true);
		frame.setLocationRelativeTo(null);

		/* Designing panel */
		if (panel == null)
			panel = new JPanel();
		else
			panel.removeAll();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(new Color(0, 44, 61));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30)); //Padding around panel

		/* Labels to display title and score at the top of the panel */
		lblScore = new JLabel("Score = "+score);
		lblTitle = new JLabel("");
		lblTitle.setIcon(new ImageIcon (Toolkit.getDefaultToolkit().getImage((getClass().getResource("/images/logoalt.png"))))); //For alternate logo design
		lblScore.setFont(new Font("Georgia", Font.ITALIC+Font.BOLD, 28));
		lblScore.setForeground(Color.WHITE);
		lblScore.setMaximumSize(screenSize);
		lblScore.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblTitle);
		panel.add(lblScore);
		panel.add(Box.createVerticalStrut(20));
		
		//Label for problem description
		JLabel quesText = new JLabel();
		quesText.setText(questObject.problemDescription);
		quesText.setMaximumSize(new Dimension((int) (0.9*screenSize.getWidth()),(int) screenSize.getHeight()));
		quesText.setForeground(Color.WHITE);
		quesText.setFont(new Font("Arial", Font.PLAIN, 16));
		
		panel.add(quesText);

		/*
		 * Designing split panel to accommodate Model image and Free Body
		 * Diagram
		 */
		JPanel innerPanel1 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) innerPanel1.getLayout();
		flowLayout_1.setVgap(20);
		flowLayout_1.setAlignment(FlowLayout.CENTER);

		JPanel innerPanel2 = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) innerPanel2.getLayout();
		flowLayout_2.setVgap(20);
		flowLayout_2.setAlignment(FlowLayout.CENTER);

		innerPanel1.setBackground(new Color(0, 44, 61));
		innerPanel2.setBackground(new Color(0, 44, 61));

		// Label for model image
		innerPanel1.add(questObject.getModelImage());

		// Label for FBD image
		innerPanel2.add(questObject.getFbdImage());

		JSplitPane splitpane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, false, innerPanel1, innerPanel2);
		splitpane.setAlignmentY(0.5f);
		splitpane.setResizeWeight(0.5);
		splitpane.setDividerSize(0);
		splitpane.setMaximumSize(screenSize);
		splitpane.setBorder(null);
		panel.add(splitpane);

		/* Gap between splitpane, and assumptions and reasons */
		panel.add(Box.createVerticalStrut(10));
		
		/* Question String */
	    lblQuestion = new JLabel("Q: Which of the following assumptions are needed?");
	    lblQuestion.setForeground(Color.WHITE);
		lblQuestion.setFont(new Font("Georgia", Font.PLAIN, 17));
		panel.add(Box.createVerticalStrut(20));
		panel.add(lblQuestion);
		panel.add(Box.createVerticalStrut(5));

		/* Designing check boxes for assumptions */
		GUISettingAssumptionsList();

		//End Test Button
		JButton endButton = new JButton("End Test");
		endButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(!qHandleObject.questionsFolderExists){
					//String localPath = "Git";
					System.out.println("Deleting temp files");
					File lp = new File(qHandleObject.gitCloneFolderName);
					try {
						qHandleObject.deleteDir(lp);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				frame.dispose();
				String [] arguments = {""+score};
				EndPage.main(arguments);
			}
		});
		endButton.setVisible(false);
		
		// Next Button
		JButton nxtButton = new JButton("Next Question");
		nxtButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				questionPath = qHandleObject.selectQuestion();
				questObject = new Question(questionPath);
				initialize();
			}
		});
		nxtButton.setVisible(false);

		// Submit button
		JButton submitButton = new JButton("Submit");
		/* Gap between options and button */
		panel.add(Box.createVerticalStrut(20));
		panel.add(submitButton);
		
				
		//FBD Image Selection Start
		fbdRetryAttempts = questObject.getFBDRetryAttempts();
		JLabel imageQuesText = new JLabel("Select the points which form valid FBD. You have " + fbdRetryAttempts + 
																										" attempts: ");
		
		imageQuesText.setForeground(Color.WHITE);
		imageQuesText.setFont(new Font("Georgia", Font.BOLD, 16));
		imageQuesText.setVisible(false);

		panel.add(Box.createVerticalStrut(10));
		panel.add(imageQuesText);
		
		forceRetryAttempts = questObject.getForceRetryAttempts();
		JLabel forceQuesText = new JLabel("Draw appropriate forces and moments. You have " + forceRetryAttempts + " attempts:");
		forceQuesText.setForeground(Color.WHITE);
		forceQuesText.setFont(new Font("Georgia", Font.BOLD, 16));
		forceQuesText.setVisible(false);
		
		JPanel fbdPanel = new JPanel(new GridBagLayout());
		imageLabel = questObject.getFBDSelectionImageLabel();
		panel.add(Box.createVerticalStrut(20));
		
		fbdPanel.setMaximumSize(screenSize);
		fbdPanel.setAlignmentX(SwingConstants.CENTER);
		fbdPanel.add(imageLabel);
		fbdPanel.setBackground(new Color(0, 44, 61));
		panel.add(fbdPanel);
		panel.add(Box.createVerticalStrut(20));
		imageLabel.setVisible(false);
		
		JButton restartFBD = new JButton("Restart FBD");
		restartFBD.setVisible(false);
		
		JButton retryFBD = new JButton("Retry");
		retryFBD.setVisible(false);
		
		JButton retryForce = new JButton("Retry");
		retryForce.setVisible(false);
		
		JButton hintFBD = new JButton("Hint");
		hintFBD.setVisible(false);
		
		fbdAnswer = new JLabel("Answer: ");
		fbdAnswer.setForeground(Color.WHITE);
		fbdAnswer.setFont(new Font("Georgia", Font.BOLD, 16));
		fbdAnswer.setVisible(false);

		fbdHintText = new JLabel("Hint: ");
		fbdHintText.setForeground(Color.WHITE);
		fbdHintText.setFont(new Font("Georgia", Font.BOLD, 16));
		fbdHintText.setVisible(false);
		
		JButton forceSubmit = new JButton("Submit");
		JButton forceRestart = new JButton("Restart");
		JLabel forceAnswer = new JLabel("Answer: ");
				
		JPanel forceButtons = new JPanel(new GridBagLayout());
		forceButtons.add(forceSubmit);
		forceButtons.add(forceRestart);
		forceButtons.add(retryForce);
		forceButtons.setAlignmentY(0.5f);
		forceButtons.setBackground(new Color(0, 44, 61));
		forceButtons.setMaximumSize(screenSize);
		forceButtons.setAlignmentX(SwingConstants.CENTER);
				
		JPanel forceAns= new JPanel(new GridBagLayout());
		GridBagConstraints c1 = new GridBagConstraints();
		c1.gridx=1;
		c1.ipady = 15;      
		c1.weightx = 0.0;
		c1.gridwidth = 3;
		c1.gridx = 0;
		c1.gridy = 1;
		panel.add(Box.createVerticalStrut(20));
		forceAns.add(forceAnswer);
		forceAns.add(nxtButton,c1);
		forceAns.setAlignmentY(0.5f);
		forceAns.setBackground(new Color(0, 44, 61));
		forceAns.setMaximumSize(screenSize);
		forceAns.setAlignmentX(SwingConstants.CENTER);
				
		forceAns.setVisible(false);
		panel.add(forceAns);
				
		
		JButton submitFBD = new JButton("Submit");
		submitFBD.setVisible(false);
		
		
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (isSubmitted) {
					score = questObject.ScoreCalculationReason(score);
					lblScore.setText("Score = " + score);
					submitButton.setVisible(false);
					imageLabel.setVisible(true);
					questObject.startFBDSelection();
					imageQuesText.setVisible(true);
					restartFBD.setVisible(true);
					submitFBD.setVisible(true);
				} else {
					score = questObject.ScoreCalculation(score);
					lblScore.setText("Score = " + score);
					if(!questObject.anywrong){
						submitButton.setVisible(false);
						imageLabel.setVisible(true);
						questObject.startFBDSelection();
						imageQuesText.setVisible(true);
						restartFBD.setVisible(true);
						submitFBD.setVisible(true);
					}
					questObject.disableCheckBoxes();
					isSubmitted = true;
				}
			}
		});
		

		restartFBD.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fbdAnswer.setText("Answer: ");
				fbdAnswer.setAlignmentX(SwingConstants.CENTER);
				fbdAnswer.setVisible(false);
				questObject.startFBDSelection();
				
			}
		});
		
		retryFBD.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fbdAnswer.setText("Answer: ");
				fbdAnswer.setAlignmentX(SwingConstants.CENTER);
				submitFBD.setVisible(true);
				restartFBD.setVisible(true);
				retryFBD.setVisible(false);
				fbdAnswer.setVisible(false);
				questObject.startFBDSelection();
			}
		});
		
		retryForce.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				forceAnswer.setText("Answer: ");
				forceAnswer.setAlignmentX(SwingConstants.CENTER);
				forceSubmit.setVisible(true);
				forceRestart.setVisible(true);
				retryForce.setVisible(false);
				forceAnswer.setVisible(false);
				questObject.startForceSelection();
			}
		});
		
		hintFBD.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				score=score+questObject.getPerFBDHintScore();
				if(score<0) score = 0;
				if(score>0)
					lblScore.setText("Score = " + score);
				else
					lblScore.setText("Score = " + "0");
				fbdAnswer.setVisible(false);
				hintFBD.setVisible(false);
//				retryFBD.setVisible(true);
				fbdHintText.setText(questObject.FBDhintList.get(hintcounter));
				fbdHintText.setVisible(true);
				fbdHintText.setAlignmentX(SwingConstants.CENTER);
				hintcounter++;
			}
		});
		
		
		submitFBD.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				fbdHintText.setVisible(false);
				if(!questObject.getFBDAnswer()){
					//If incorrect
					score=score+questObject.getPerFBDNegScore();
					if(score<0) score = 0;
					if(score>0)
						lblScore.setText("Score = " + score);
					else
						lblScore.setText("Score = " + "0");
					fbdRetryAttempts--;
					if(fbdRetryAttempts>0){
						retryFBD.setVisible(true);
						hintFBD.setVisible(true);
						fbdAnswer.setVisible(true);
						fbdAnswer.setText("Answer: Incorrect. You have " + fbdRetryAttempts + " more attempts.");
					}
					else{
						retryFBD.setVisible(false);
						hintFBD.setVisible(false);
						fbdAnswer.setVisible(true);
						fbdAnswer.setText("Answer: Incorrect. You have used all attempts.");
						
						forceQuesText.setVisible(true);
						panel.add(questObject.getForceGui());
						panel.add(Box.createVerticalStrut(20));
						panel.add(Box.createVerticalStrut(20));
						panel.add(forceButtons);
						panel.add(Box.createVerticalStrut(20));
						panel.add(forceAns);
						panel.add(nxtButton);
						panel.add(endButton);

						

					}
					
				}
				else{
					//If correct
					score=score+questObject.getPerFBDScore();
					if(score<0) score = 0;
					if(score>0)
						lblScore.setText("Score = " + score);
					else
						lblScore.setText("Score = " + "0");
					fbdAnswer.setVisible(true);
					hintFBD.setVisible(false);
					fbdAnswer.setText("Answer: Correct");
					
					forceQuesText.setVisible(true);
					panel.add(questObject.getForceGui());
					panel.add(Box.createVerticalStrut(20));
					panel.add(Box.createVerticalStrut(20));
					panel.add(forceButtons);
					panel.add(Box.createVerticalStrut(20));
					panel.add(forceAns);
					panel.add(nxtButton);
					panel.add(endButton);

					
				}
				
				submitFBD.setVisible(false);
				restartFBD.setVisible(false);
				
				if(fbdRetryAttempts == 0){
					//Force attempts visible
				}
					
			}
			
		});
		
		JPanel p = new JPanel(new GridBagLayout());
		
		p.add(restartFBD);
		p.add(submitFBD);
		p.add(retryFBD);
		p.add(hintFBD);
		p.setAlignmentY(0.5f);
		p.setBackground(new Color(0, 44, 61));
		p.setMaximumSize(screenSize);
		p.setAlignmentX(SwingConstants.CENTER);
		panel.add(p);
		JPanel p1 = new JPanel(new GridBagLayout());
		panel.add(Box.createVerticalStrut(20));
		
		p1.add(fbdAnswer);
		//p1.add(nxtButton,c);
		p1.add(fbdHintText);
		p1.setAlignmentY(0.5f);
		p1.setBackground(new Color(0, 44, 61));
		p1.setMaximumSize(screenSize);
		p1.setAlignmentX(SwingConstants.CENTER);
		
		panel.add(p1);
		

		//Force part starts here
		//panel.add(questObject.getForceGui());
		panel.add(Box.createVerticalStrut(20));
		panel.add(Box.createVerticalStrut(20));
		panel.add(forceQuesText);
		
		// Force Answer label
		forceAnswer.setForeground(Color.WHITE);
		forceAnswer.setFont(new Font("Georgia", Font.BOLD, 16));
		
		
		forceSubmit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				forceAns.setVisible(true);
				if( questObject.getForceAnswer() ) {
					score=score+questObject.getPerForceScore();
					forceAnswer.setText("Answer: Correct");
					
					if(qHandleObject.isLastQuestion()) {
						nxtButton.setVisible(false);
						endButton.setVisible(true);
					}	
					else {
						nxtButton.setVisible(true);
					}

					
				} else {
					score=score+questObject.getPerForceNegScore();
					if(score<0) score = 0;
					forceRetryAttempts--;
					if(forceRetryAttempts > 0) {
						retryForce.setVisible(true);
						forceAnswer.setVisible(true);
						forceAnswer.setText("Answer: Incorrect. You have "+ forceRetryAttempts+" more attempts");
					}
					else {
						retryForce.setVisible(false);
						forceAnswer.setVisible(true);
						forceAnswer.setText("Answer: Incorrect. You have used all attempts.");
						if(qHandleObject.isLastQuestion()) {
							nxtButton.setVisible(false);
							endButton.setVisible(true);
						}	
						else {
							nxtButton.setVisible(true);
						}

					}
					
					
				}
				
				forceSubmit.setVisible(false);
				forceRestart.setVisible(false);
				
				if(score>0)
					lblScore.setText("Score = " + score);
				else
					lblScore.setText("Score = " + "0");
				
			}
		});
		
		forceRestart.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				forceAns.setVisible(false);
				questObject.startForceSelection();
			}
		});
		
		panel.add(nxtButton);
		
		JScrollPane scrollPane = new JScrollPane(panel);
		//hides horizontal scroll bar
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		//vertical scroll speed
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		frame.setVisible(true);
	}

	/*
	 * GUI SETTING ASSUMPTION LIST Method to display assumption check boxes on
	 * the GUI and initialize reason lists based on the type of assumption
	 * (Correct/Incorrect/Complicated) Input: List of all assumptions
	 */
	public void GUISettingAssumptionsList() {
				
		// Split all assumptions in answer and statements, create check boxes
		// Traverse through check boxes and add them on panel
		//EngineeringEducatorAssumption.getAssumptions();
		//JLabel lblIncorrect = assumObject.AssumptionType(reasonObject);

		int numAssum = questObject.getNumberOfAssumptions();
		for (int i = 0; i < numAssum; i++) {
			JCheckBox chkBox = questObject.getAssumptionCheckbox(i);
			panel.add(chkBox);
			Assumption assumObject = questObject.getAssumObj(chkBox.getText());
			JLabel lblMessage = assumObject.getlblMessage();
//			panel.add(Box.createVerticalGlue());
			panel.add(Box.createRigidArea(new Dimension(0,5)));
			if (lblMessage != null) {
				lblMessage.setForeground(Color.WHITE);
				lblMessage.setBorder(BorderFactory.createEmptyBorder(5, 30, 5, 30));
				panel.add(lblMessage);
				// Calling method to add reasons for incorrect assumption
				GUISettingReasonsList(chkBox.getText());
			}
			
			
		}
	}

	/*
	 * GUI SETTING REASONS LIST Method to display all reasons on the GUI based
	 * on the assumption selected
	 */

	public void GUISettingReasonsList(String assumption) {
		// Read reason file for incorrect assumption
		ArrayList<JRadioButton> rdbList = new ArrayList<JRadioButton>();
		Assumption assumObject = questObject.getAssumObj(assumption);
		rdbList = assumObject.getReasonRdbList();
		
		if (rdbList != null) {
			// Create radio buttons for all reasons
			for (int rdbIndex = 0; rdbIndex < rdbList.size(); rdbIndex++) {
				// Adding radio buttons onto panel
				panel.add(rdbList.get(rdbIndex));
			}
		}
		assumObject.setRdbListVisibility(false);
	}
	
}