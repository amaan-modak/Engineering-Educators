import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JLabel;

public class EngineeringEducatorAssumption {
	ArrayList<String> assumptions = new ArrayList<String>();
	ArrayList<Integer> answers = new ArrayList<Integer>();
	ArrayList<JCheckBox> assumptionChkbxList = new ArrayList<JCheckBox>();
	boolean cumAnswerFlag = true;
	String path;
	

	/*
	 * READ ASSUMPTIONS Method to read text file line by line and store the
	 * result in an array list 
	 * Input: Path of the text file 
	 * of String containing textual data of file
	 */
	public void ReadAssumptions(String path) {
		FileInputStream fstream2;
		ArrayList<String> allAssumptions = new ArrayList<String>();
		try {
			fstream2 = new FileInputStream(path);
			BufferedReader br2 = new BufferedReader(new InputStreamReader(fstream2));
			String strLine2;
			// Read File Line By Line
			while ((strLine2 = br2.readLine()) != null) {
				allAssumptions.add(strLine2);
			}
			br2.close();

			// Splitting into assumptions and reasons
			for (int j = 0; j < allAssumptions.size(); j++) {
				String[] splitter = allAssumptions.get(j).split("\\|");
				assumptions.add(splitter[0]);
				Integer assumptionAnswer = Integer.parseInt(splitter[1]);
				answers.add(assumptionAnswer);
				JCheckBox chkbxAssumption = CreateCheckBox(assumptions.get(j));
				chkbxAssumption.setBackground(new Color(46, 42, 42));
				chkbxAssumption.setForeground(Color.WHITE);
				assumptionChkbxList.add(chkbxAssumption);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * CREATE CHECKBOX Method to create checkbox with given value
	 * Input: Path of the text file 
	 * Output: Checkbox
	 * of String containing textual data of file
	 */
	public JCheckBox CreateCheckBox(String value) {
		JCheckBox chkbxAssumption = new JCheckBox(value);
		return chkbxAssumption;
	}

	public JLabel TypeOfAssumption(EngineeringEducatorReason reasonObject, int ansType) {
		JLabel lblIncorrect = null;
		if (ansType == 1) {
			reasonObject.listOfRdbtnListForReasons.add(null);
			reasonObject.answers.add(-1);
			reasonObject.reasonMsgLabelList.add(null);
		} else {
			// for incorrect assumptions add reasons below it, but hide them
			// add a label saying this assumption is incorrect/ complicated
			if (ansType == 0) {
				lblIncorrect = new JLabel("This assumption is incorrect, what could be the reason?");
				lblIncorrect.setVisible(false);
				reasonObject.reasonMsgLabelList.add(lblIncorrect);
			} else if (ansType == 2) {
				lblIncorrect = new JLabel("This assumption is a complicating factor, what could be the reason?");
				lblIncorrect.setVisible(false);
				reasonObject.reasonMsgLabelList.add(lblIncorrect);
			}

		}
		return lblIncorrect;
	}

	public int ScoreCalculation(int score, EngineeringEducatorReason reasonObj) {
		cumAnswerFlag = true;
		for (int j = 0; j < assumptionChkbxList.size(); j++) {
			boolean ansChkbxComparison = true;
			ansChkbxComparison = CheckAnswer(assumptionChkbxList.get(j), answers.get(j));

			// if selection doesn't compare with answer make
			// background red otherwise green
			if (ansChkbxComparison == false) {
				assumptionChkbxList.get(j).setBackground(new Color(204, 0, 0)); // red

				if (reasonObj.listOfRdbtnListForReasons.get(j) == null)
					continue;
				for (int k = 0; k < reasonObj.listOfRdbtnListForReasons.get(j).size(); k++) {
					reasonObj.listOfRdbtnListForReasons.get(j).get(k).setVisible(true);
				}
				reasonObj.reasonMsgLabelList.get(j).setVisible(true);
			} else
				assumptionChkbxList.get(j).setBackground(new Color(102, 255, 102)); // green
			// compute cumulative answer
			cumAnswerFlag = cumAnswerFlag & ansChkbxComparison;
		}
		if (cumAnswerFlag) {
			score = score + 5;
		}
		return score;
	}

	/*
	 * CHECK ANSWER Method to check whether selected assumption is correct or
	 * not Input: Check box and actual answer for that check box Output: True if
	 * selected assumption is correct, otherwise false
	 */
	public boolean CheckAnswer(JCheckBox chkBox, int ans) {
		if (chkBox.isSelected() && ans == 1)
			return true;
		else if (!chkBox.isSelected() && (ans == 0 || ans == 2))
			return true;
		return false;
	}

	/*
	 * DISABLE CHECKBOX Method to disable all the checkboxes Input: Arraylist
	 * containing check boxes
	 */
	public void DisableCheckBox() {
		for (int i = 0; i < assumptionChkbxList.size(); i++) {
			assumptionChkbxList.get(i).setEnabled(false);
		}
	}
}
