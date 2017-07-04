import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JRadioButton;

public class EngineeringEducatorReason {
	String reason = "";
	String answer = "";
	
	public EngineeringEducatorReason(String reason, String answer) {
		// TODO Auto-generated constructor stub
		this.reason = reason;
		this.answer = answer;
	}
	
	public JRadioButton CreateRadioButton(String value){
		JRadioButton rdbReason = new JRadioButton(value);
		rdbReason.setBackground(new Color(0, 44, 61));
		rdbReason.setForeground(Color.WHITE);
		rdbReason.setFont(new Font("Georgia", Font.PLAIN, 16));
		rdbReason.setBorder(BorderFactory.createEmptyBorder(5, 30, 5, 30));
		return rdbReason;
	}

}
