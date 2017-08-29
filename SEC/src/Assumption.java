package teamnp.eguru;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

public class Assumption {
	String assumption = "";
	String answer = "";
	JLabel lblMessage;
	ArrayList<String> reasonList = new ArrayList<String>();
	HashMap<String,Reason> reasonObjMap = new HashMap<String,Reason>(); //Key = Reason, value = Reason class object
	ArrayList<JRadioButton> reasonRdbList = new ArrayList<JRadioButton>();
	// Button group for reasons radio buttons
	ButtonGroup bgroup = new ButtonGroup();

	boolean anywrongreason = false;

	
	/***Constructor***/
	public Assumption(String assumption, String answer) {
		// TODO Auto-generated constructor stub
		this.assumption = assumption;
		this.answer = answer;
		if(answer.equals("incorrect")){
			lblMessage = new JLabel("This assumption is incorrect, what could be the reason?");
			lblMessage.setFont(new Font("Georgia", Font.PLAIN, 16));
			lblMessage.setVisible(false);
		}
		else if (answer.equals("complicated")){
			lblMessage = new JLabel("This assumption is a complicating factor, what could be the reason?");
			lblMessage.setFont(new Font("Georgia", Font.PLAIN, 16));
			lblMessage.setVisible(false);
		}
		else if (answer.equals("correct")){
			lblMessage = new JLabel("This assumption is correct, what could be the reason?");
			lblMessage.setFont(new Font("Georgia", Font.PLAIN, 16));
			lblMessage.setVisible(false);
		}
	}
	/**************/

	/*****Getters & Setters****/
	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getAssumption() {
		return assumption;
	}

	public void setAssumption(String assumption) {
		this.assumption = assumption;
	}

	public ArrayList<String> getReasonList() {
		return reasonList;
	}
	/***********************/
	
	public JLabel getlblMessage(){
		return lblMessage;
	}
	
	public int setReasonList(int reasonIdx, ArrayList<String> content) {
		String reasonText = "";
		while (reasonIdx < content.size()) {
			reasonText = content.get(reasonIdx);
			if (!reasonText.equals("End Reasons")) {
				String[] reasonSplit = reasonText.split("\\|");
				reasonList.add(reasonSplit[0]);
				Reason reasonObj = new Reason(reasonSplit[0], reasonSplit[1]);
				reasonObjMap.put(reasonSplit[0],reasonObj);
				reasonIdx++;
			} else
				break;
		}
		setReasonRdbList();
		return reasonIdx;
	}
	
	public int getNumberOfReasons(){
		return reasonList.size();
	}
	
	public String getReason(int idx){
		return reasonList.get(idx);
	}
	
	public void setReasonRdbList() {
		int numAssum = getNumberOfReasons();
		for (int i = 0; i < numAssum; i++) {
			String reason = getReason(i);
			JRadioButton rdbReason = reasonObjMap.get(reason).CreateRadioButton(reason);
			bgroup.add(rdbReason);
			this.reasonRdbList.add(rdbReason);
		}
	}
	
	public ArrayList<JRadioButton> getReasonRdbList(){
		return reasonRdbList;
	}
	
	public void setRdbListVisibility(boolean visibilityFlag){
		for(int i = 0 ; i < reasonRdbList.size() ; i++){
			reasonRdbList.get(i).setVisible(visibilityFlag);
		}
	}
	
	public JCheckBox CreateCheckBox(String value) {
		JCheckBox chkbxAssumption = new JCheckBox(value);
		chkbxAssumption.setBackground(new Color(0, 44, 61));
		chkbxAssumption.setForeground(Color.WHITE);
		chkbxAssumption.setFont(new Font("Georgia", Font.PLAIN, 16));
		chkbxAssumption.setOpaque(true);
		return chkbxAssumption;
	}
		
	//Score calculation (int score)
	//
	public boolean ScoreCalculation(){
		for (int i = 0; i < reasonRdbList.size(); i++) {
			
			boolean ansRdbComparison = true;
			String reasonans = reasonObjMap.get(reasonList.get(i)).answer;
			if (reasonRdbList.get(i).isSelected() && reasonans.equals("valid"))
				ansRdbComparison = true;
			else if (!reasonRdbList.get(i).isSelected() && reasonans.equals("invalid"))
				ansRdbComparison = true;
			else
				ansRdbComparison = false;

			if (ansRdbComparison == false) {
				
				if(reasonans.equals("invalid")) {
					reasonRdbList.get(i).setText(reasonRdbList.get(i).getText()+"  (0)");
					reasonRdbList.get(i).setForeground(new Color(249, 29, 44)); // red
					anywrongreason = true;
				}
				else if(reasonans.equals("valid")){
					
					reasonRdbList.get(i).setForeground(new Color(49, 216, 23)); // green
					anywrongreason = true;
				}
				
			}

			if(reasonans.equals("valid") && reasonRdbList.get(i).isSelected()) {
				reasonRdbList.get(i).setText(reasonRdbList.get(i).getText()+"  ("+Question.perReasonScore+")");
				reasonRdbList.get(i).setForeground(new Color(49, 216, 23)); // green	
			}
			
			//disable the radio button
			disableRadioButton(reasonRdbList.get(i));
			
		}
		return anywrongreason;
	}
	
	public void disableRadioButton(JRadioButton rdbutton) {
		bgroup.remove(rdbutton);
		rdbutton.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(rdbutton.isSelected())
					rdbutton.setSelected(false);
				else
					rdbutton.setSelected(true);
			}
		});
	}
}
