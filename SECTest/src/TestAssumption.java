import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;

public class TestAssumption {
	String assumption = "";
	String answer = "";
	ArrayList<String> reasonList = new ArrayList<String>();
	HashMap<String,TestReason> reasonObjMap = new HashMap<String,TestReason>(); //Key = Reason, value = Reason class object
	ArrayList<JRadioButton> reasonRdbList = new ArrayList<JRadioButton>();

	
	/***Constructor***/
	public TestAssumption(String assumption, String answer) {
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
		while (reasonIdx < content.size()) {
			reasonText = content.get(reasonIdx);
			if (!reasonText.equals("End Reasons")) {
				String[] reasonSplit = reasonText.split("\\|");
				reasonList.add(reasonSplit[0]);
				TestReason reasonObj = new TestReason(reasonSplit[0], reasonSplit[1]);
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
}
