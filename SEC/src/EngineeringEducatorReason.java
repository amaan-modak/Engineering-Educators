import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

public class EngineeringEducatorReason {
	public ArrayList<Integer> answers = new ArrayList<Integer>();
	public ArrayList<ArrayList<JRadioButton>> listOfRdbtnListForReasons = new ArrayList<ArrayList<JRadioButton>>();
	public ArrayList<JLabel> reasonMsgLabelList = new ArrayList<JLabel>();
	String path;

	/*
	 * READ REASONS Method to read text file line by line and store the result
	 * in an array list Input: Path of the text file Output: Array list of
	 * String containing textual data of file
	 */
	public ArrayList<String> ReadReasons(String path) {
		FileInputStream fstream2;
		ArrayList<String> allReasons = new ArrayList<String>();
		try {
			fstream2 = new FileInputStream(path);
			BufferedReader br2 = new BufferedReader(new InputStreamReader(fstream2));
			String strLine2;
			// Read File Line By Line
			while ((strLine2 = br2.readLine()) != null) {
				allReasons.add(strLine2);
			}
			br2.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return allReasons;
	}

	public 	ArrayList<JRadioButton> CreateReasonList(ArrayList<String> tempReasons) {
		ArrayList<JRadioButton> reasonList = new ArrayList<JRadioButton>();
		ArrayList<String> reasons = new ArrayList<String>();
		System.out.println("Inside Create Reason List");
		for (int j = 0; j < tempReasons.size(); j++) {
			System.out.println(tempReasons.get(j));
		}
		for (int reasonIndex = 0; reasonIndex < tempReasons.size(); reasonIndex++) {
			String[] reasonSplit = tempReasons.get(reasonIndex).split("\\|");
			reasons.add(reasonSplit[0]);
			Integer reasonAnswer = Integer.parseInt(reasonSplit[1]);
			if (reasonAnswer == 1) {
				answers.add(reasonIndex);
			}

			JRadioButton rdbReason = CreateRadioButton(reasons.get(reasonIndex));
			rdbReason.setBackground(new Color(46, 42, 42));
			rdbReason.setForeground(Color.WHITE);
			rdbReason.setBorder(BorderFactory.createEmptyBorder(5, 30, 5, 30));
			reasonList.add(rdbReason);
			rdbReason.setVisible(false);
		}
		return reasonList;
	}
	
	public JRadioButton CreateRadioButton(String value){
		JRadioButton rdbReason = new JRadioButton(value);
		return rdbReason;
	}

	/*
	 * GET REASONS FOR INCORRECT ASSUMPTION Method to read reason's text file line by line and store the
	 * result in an array list  
	 * Input: Path of the text file, Assumption
	 * Output: Array list of String containing textual data of file
	 */
	public ArrayList<String> GetReasonsForIncorrectAssumption(int assumptionIdx, String path) {
		ArrayList<String> allReasons = new ArrayList<String>();
		allReasons = ReadReasons(path + "_" + (assumptionIdx + 1) + ".txt");
		return allReasons;
	}
	
	public int ScoreCalculation(int score){
		for (int i = 0; i < listOfRdbtnListForReasons.size(); i++) {
			if (listOfRdbtnListForReasons.get(i) == null)
				continue;
			// if there are reasons but they are not visible i.e.
			// that assumption was not selected then skip
			if (listOfRdbtnListForReasons.get(i).get(0).isVisible() == false)
				continue;
			for (int j = 0; j < listOfRdbtnListForReasons.get(i).size(); j++) {
				if (answers.get(i) == j && listOfRdbtnListForReasons.get(i).get(j).isSelected()) {
					listOfRdbtnListForReasons.get(i).get(j).setBackground(new Color(0, 102, 34));
					score += 1;
				} else if (answers.get(i) == j) {
					listOfRdbtnListForReasons.get(i).get(j).setBackground(new Color(0, 102, 34));
				} else if (answers.get(i) != j && listOfRdbtnListForReasons.get(i).get(j).isSelected()) {
					listOfRdbtnListForReasons.get(i).get(j).setBackground(new Color(204, 0, 0));
				}

			}
		}
		return score;
	}

	/*
	 * DISABLE RADIOBUTTONS Method to disable all the radio buttons Input:
	 * Arraylist containing radio buttons
	 */
	public void DisableRadioButton(ArrayList<JRadioButton> rdbList){
		for(int i = 0; i < rdbList.size() ; i++){
			if(rdbList.get(i).isEnabled())
				rdbList.get(i).setEnabled(false);
		}
	}
}
