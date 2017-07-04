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

public class EngineeringEducatorWelcomePage {

	private JFrame homeFrame;
	static JPanel screen;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // Set Look and Feel of the UI
					EngineeringEducatorWelcomePage window = new EngineeringEducatorWelcomePage();
					window.homeFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public EngineeringEducatorWelcomePage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("serial")
	private void initialize() {
		homeFrame = new JFrame();
		homeFrame.setBounds(0, 0, 800, 600);
		homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		homeFrame.setLocationRelativeTo(null);
		homeFrame.setAlwaysOnTop(true);
		homeFrame.setTitle("Engineering Educators");
		homeFrame.setResizable(false);
		
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
		
		JLabel lblIntro = new JLabel("Welcome to Engineering Educators");
		lblIntro.setFont(new Font("Georgia", Font.ITALIC, 32));
		lblIntro.setForeground(Color.WHITE);
		lblIntro.setAlignmentX(0.5f);
		screen.add(Box.createVerticalStrut(40));
		screen.add(lblIntro, BorderLayout.NORTH);
		screen.add(Box.createVerticalStrut(40));

		JLabel lblWcLogo = new JLabel("");
		lblWcLogo.setIcon(new ImageIcon (Toolkit.getDefaultToolkit().getImage((getClass().getResource("/images/altlogo.png")))));
		lblWcLogo.setAlignmentX(0.5f);
		screen.add(lblWcLogo, BorderLayout.CENTER);
		screen.add(Box.createVerticalStrut(40));
		
		JLabel lblInst = new JLabel("<html><div style='text-align: center;'>Click On The Start Test Button<br> Whenever You Are Ready To Begin</div></html>");
		lblInst.setFont(new Font("Georgia", Font.PLAIN, 24));
		lblInst.setHorizontalAlignment(SwingConstants.CENTER);
		lblInst.setForeground(Color.WHITE);
		lblInst.setAlignmentX(0.5f);
		screen.add(lblInst);
		screen.add(Box.createVerticalStrut(40));

		JLabel lblLuck = new JLabel("GOOD LUCK!");
		lblLuck.setFont(new Font("Georgia", Font.ITALIC, 28));
		lblLuck.setForeground(Color.WHITE);
		lblLuck.setAlignmentX(0.5f);
		screen.add(lblLuck);
		screen.add(Box.createVerticalStrut(40));

		//Start Test Button
		JButton startButton = new JButton("Start Test");
		startButton.setAlignmentX(0.5f);
		startButton.setBackground(new Color(0, 44, 61));
		startButton.setFocusPainted(false);
		screen.add(startButton, BorderLayout.SOUTH);
		screen.add(Box.createVerticalStrut(40));
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
				homeFrame.dispose();
				EngineeringEducatorMainPage.main(null);
			}
		});
				
		homeFrame.getContentPane().add(screen);
	}

}
