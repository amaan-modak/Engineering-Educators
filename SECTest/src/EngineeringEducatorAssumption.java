import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;

public class EngineeringEducatorAssumption {
	String assumption = "";
	String answer = "";
	ArrayList<String> reasonList = new ArrayList<String>();
	HashMap<String,EngineeringEducatorReason> reasonObjMap = new HashMap<String,EngineeringEducatorReason>(); //Key = Reason, value = Reason class object
	ArrayList<JRadioButton> reasonRdbList = new ArrayList<JRadioButton>();
	// Button group for reasons radio buttons
	ButtonGroup bgroup = new ButtonGroup();

	boolean anywrongreason = false;

	
	/***Constructor***/
	public EngineeringEducatorAssumption(String assumption, String answer) {
		// TODO Auto-generated constructor stub
		this.assumption = assumption;
		this.answer = answer;
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
	
	public int setReasonList(int reasonIdx, ArrayList<String> content) {
		String reasonText = "";
		System.out.println(reasonIdx);
		while (reasonIdx < content.size()) {
			reasonText = content.get(reasonIdx);
			//System.out.println(reasonText);
			if (!reasonText.equals("End Reasons")) {
				String[] reasonSplit = reasonText.split("\\|");
				reasonList.add(reasonSplit[0]);
				EngineeringEducatorReason reasonObj = new EngineeringEducatorReason(reasonSplit[0], reasonSplit[1]);
				reasonObjMap.put(reasonSplit[0],reasonObj);
				reasonIdx++;
			} else
				break;
		}
		//System.out.println(reasonList.get(0));
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
		//System.out.println(numAssum);
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
		return chkbxAssumption;
	}
		
	//Score calculation (int score)
	//
	public boolean ScoreCalculation(){
		for (int i = 0; i < reasonRdbList.size(); i++) {
			
			boolean ansRdbComparison = true;
			String reasonans = reasonObjMap.get(reasonList.get(i)).answer;
			//System.out.println(assumptions.get(j));
			if (reasonRdbList.get(i).isSelected() && reasonans.equals("valid"))
				ansRdbComparison = true;
			else if (!reasonRdbList.get(i).isSelected() && reasonans.equals("invalid"))
				ansRdbComparison = true;
			else
				ansRdbComparison = false;

			if (ansRdbComparison == false) {
				
				if(reasonans.equals("invalid")) {
					reasonRdbList.get(i).setForeground(new Color(249, 29, 44)); // red
					anywrongreason = true;
				}
				else if(reasonans.equals("valid")){
					
					reasonRdbList.get(i).setForeground(new Color(49, 216, 23)); // green
					anywrongreason = true;
				}
				
			}
			System.out.println(reasonans);
			if(reasonans.equals("valid")) {
				reasonRdbList.get(i).setForeground(new Color(49, 216, 23)); // green	
			}
			
			//disable the radio button
			disableRadioButton(reasonRdbList.get(i));
			
		}
				//DisableRadioButton(listOfRdbtnListForReasons.get(i));
		//DisableRadioButton(listOfRdbtnListForReasons.get(i));
		return anywrongreason;
	}
	
	public void disableRadioButton(JRadioButton rdbutton) {
//		rdbutton.setEnabled(false);
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