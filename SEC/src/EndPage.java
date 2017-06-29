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

public class EndPage {

	private JFrame endFrame;
	static JPanel screen;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // Set Look and Feel of the UI
					EndPage window = new EndPage();
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
	public EndPage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		endFrame = new JFrame();
		endFrame.setBounds(0, 0, 800, 600);
		endFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		endFrame.setLocationRelativeTo(null);
		endFrame.setAlwaysOnTop(true);
		endFrame.setTitle("Engineering Educators");
		endFrame.setResizable(false);
		
		/* Designing panel */
		screen = new JPanel(){
			@Override
			protected void paintComponent(Graphics gr) {
				super.paintComponent(gr);
				Graphics2D g2d = (Graphics2D) gr;
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				GradientPaint gp = new GradientPaint(0, 0, getBackground().brighter().brighter().brighter(),
						0, getHeight(), getBackground().darker().darker().darker());
	            g2d.setPaint(gp);
	            g2d.fillRect(0, 0, getWidth(), getHeight()); 

	        }

	    };
		screen.setLayout(new BoxLayout(screen, BoxLayout.Y_AXIS));
		screen.setBackground(new Color(0, 44, 61));
		screen.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50)); //Padding around panel
		
		JLabel lblThank = new JLabel("Thank You For Using Engineering Educators");
		lblThank.setFont(new Font("Georgia", Font.ITALIC, 28));
		lblThank.setForeground(Color.WHITE);
		lblThank.setAlignmentX(0.5f);
		screen.add(lblThank, BorderLayout.NORTH);
		screen.add(Box.createVerticalStrut(40));
		
		JLabel lblFinScore = new JLabel("Your Final Score is 6");
		lblFinScore.setFont(new Font("Georgia", Font.BOLD+Font.ITALIC, 34));
		lblFinScore.setForeground(Color.WHITE);
		lblFinScore.setAlignmentX(0.5f);
		screen.add(lblFinScore);
		screen.add(Box.createVerticalStrut(40));

		//Team Photo
		JLabel lblWcLogo = new JLabel("");
		lblWcLogo.setIcon(new ImageIcon (Toolkit.getDefaultToolkit().getImage((getClass().getResource("/images/logoalt.png")))));
		lblWcLogo.setAlignmentX(0.5f);
		screen.add(lblWcLogo, BorderLayout.CENTER);
		screen.add(Box.createVerticalStrut(40));
		
		JLabel lblInst = new JLabel("<html><div style='text-align: center;'>PLEASE DO NOT EXIT OR CLOSE THIS SCREEN <br>UNTIL THE TA RECORDS YOUR FINAL SCORE.</div></html>");
		lblInst.setFont(new Font("Georgia", Font.PLAIN, 24));
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
		screen.add(exitButton, BorderLayout.SOUTH);
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
