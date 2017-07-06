import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class EngineeringEducatorEndPage {

	private JFrame endFrame;
	static JPanel screen;
	static int score = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		if(args.length > 0) {
			score = Integer.parseInt(args[0]);
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // Set Look and Feel of the UI
					EngineeringEducatorEndPage window = new EngineeringEducatorEndPage();
					window.endFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public EngineeringEducatorEndPage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	void initialize() {
		endFrame = new JFrame();
		endFrame.setBounds(0, 0, 800, 700);
		endFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		endFrame.setLocationRelativeTo(null);
		endFrame.setAlwaysOnTop(true);
		endFrame.setTitle("eGuru");
		endFrame.setResizable(false);
		
		/* Designing panel */
		screen = new JPanel(){
			@Override
			protected void paintComponent(Graphics gr) {
				super.paintComponent(gr);
				Graphics2D g2d = (Graphics2D) gr;
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				GradientPaint gp = new GradientPaint(0, 0, getBackground().brighter().brighter().brighter(),
						0, getHeight(), getBackground().darker().darker().darker().darker());
	            g2d.setPaint(gp);
	            g2d.fillRect(0, 0, getWidth(), getHeight()); 

	        }

	    };
		screen.setLayout(new BoxLayout(screen, BoxLayout.Y_AXIS));
		screen.setBackground(new Color(0, 44, 61));
		screen.setBorder(BorderFactory.createEmptyBorder(40,40,40,40)); //Padding around panel
		
		JLabel lblFinScore = new JLabel("Your Final Score is "+score);
		lblFinScore.setFont(new Font("Georgia", Font.BOLD+Font.ITALIC, 40));
		lblFinScore.setForeground(Color.WHITE);
		lblFinScore.setAlignmentX(0.5f);
		screen.add(Box.createVerticalStrut(10));
		screen.add(lblFinScore);
		screen.add(Box.createVerticalStrut(40));

		JLabel lblThank = new JLabel("<html><div style='text-align: center;'>From <strong>Team NP</strong> We Would Like To Say <br>Thank You For Using eGuru</div></html>");
		lblThank.setFont(new Font("Georgia", Font.ITALIC, 24));
		lblThank.setHorizontalAlignment(SwingConstants.CENTER);
		lblThank.setForeground(Color.WHITE);
		lblThank.setAlignmentX(0.5f);
		screen.add(lblThank, BorderLayout.NORTH);
		screen.add(Box.createVerticalStrut(40));
		
		/**
		 * Add Team Photo
		 */
		JLabel lblWcLogo = new JLabel("Team NP");
		lblWcLogo.setIcon(new ImageIcon (Toolkit.getDefaultToolkit().getImage((getClass().getResource("/images/team.jpeg")))));
		lblWcLogo.setAlignmentX(0.5f);
		lblWcLogo.setHorizontalTextPosition(JLabel.CENTER);
		lblWcLogo.setVerticalTextPosition(JLabel.BOTTOM);
		lblWcLogo.setForeground(Color.WHITE);
		lblWcLogo.setFont(new Font("Georgia", Font.ITALIC, 14));
		screen.add(lblWcLogo, BorderLayout.CENTER);
		screen.add(Box.createVerticalStrut(40));
		
		JLabel lblInst = new JLabel("<html><div style='text-align: center;'>PLEASE DO NOT EXIT OR CLOSE THIS WINDOW <br>UNTIL YOUR SCORE HAS BEEN NOTED.</div></html>");
		lblInst.setFont(new Font("Georgia", Font.BOLD, 24));
		lblInst.setHorizontalAlignment(SwingConstants.CENTER);
		lblInst.setForeground(Color.WHITE);
		lblInst.setAlignmentX(0.5f);
		screen.add(lblInst);
		screen.add(Box.createVerticalStrut(40));

		JLabel lblLuck = new JLabel("Wish You All The Best For Your Future!!");
		lblLuck.setFont(new Font("Georgia", Font.ITALIC, 28));
		lblLuck.setForeground(Color.WHITE);
		lblLuck.setAlignmentX(0.5f);
		screen.add(lblLuck);
		screen.add(Box.createVerticalStrut(40));

		//Exit Button
		JButton exitButton = new JButton("Exit");
		exitButton.setAlignmentX(0.5f);
		exitButton.setBackground(new Color(0, 44, 61));
		exitButton.setFocusPainted(false);
		screen.add(exitButton);
		screen.add(Box.createVerticalStrut(40));
		
		exitButton.addActionListener(new ActionListener() {		
			 @Override
			 public void actionPerformed(ActionEvent e) {
			 	endFrame.dispose();
			 	
			 }
		});	
				
		endFrame.getContentPane().add(screen);
	}

}
