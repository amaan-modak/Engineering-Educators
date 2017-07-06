import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;

/*
Class having responsibility of maintaining question
Question comprises of Model Image, FBD and "List" of Assumptions along with their answers
It also holds information about maximum score of the question, mark for each correct assumption which are read from file
*/
public class EngineeringEducatorQuestion {

	String questionPath = "";
	EngineeringEducatorFBDImage fbdObj = new EngineeringEducatorFBDImage();
	EngineeringEducatorModelImage modObj = new EngineeringEducatorModelImage();
	ArrayList<String> data = new ArrayList<String>();
	ArrayList<String> assumptions = new ArrayList<String>();
	ArrayList<JCheckBox> assumptionChkbxList = new ArrayList<JCheckBox>();
	HashMap<String, EngineeringEducatorAssumption> assumptionObjMap = new HashMap<String, EngineeringEducatorAssumption>();
	// Key = assumption, value = Assumption class object

	int perReasonScore = 0;
	int minScore=0;

	int maxScore = 0, perAssumScore = 0;
	int perAssumNegScore = 0;
	boolean anywrong = false;

	/*** Constructor ***/
	public EngineeringEducatorQuestion(String questionPath) {
		// TODO Auto-generated constructor stub
		this.questionPath = questionPath;
	}

	/***************/

	/*** Getters & Setters ***/

	public EngineeringEducatorAssumption getAssumObj(String assumption) {
		return assumptionObjMap.get(assumption);
	}

	public int getNumberOfAssumptions() {
		return assumptions.size();
	}

	public String getAssumption(int idx) {
		return assumptions.get(idx);
	}

	public void setPerReasonScore(int score) {
		perReasonScore = score;
	}

	public int getPerReasonScore() {
		return this.perReasonScore;
	}

	public void setMaxScore(int score) {
		maxScore = score;
	}

	public void setPerAssumScore(int score) {
		perAssumScore = score;
	}

	public void setPerAssumNegScore(int score) {
		perAssumNegScore = score;
	}

	public JLabel getModelImage() {
		return modObj.getImage();
	}

	public JLabel getFbdImage() {
		return fbdObj.getImage();
	}

	public String getAnswer(String assumption) {
		return assumptionObjMap.get(assumption).getAnswer();
	}

	public ArrayList<JCheckBox> getAssumptionChkbxList() {
		return assumptionChkbxList;
	}

	public void setAssumptionChkbxList() {
		int numAssum = getNumberOfAssumptions();
		for (int i = 0; i < numAssum; i++) {
			String assumption = getAssumption(i);
			JCheckBox chkBox = assumptionObjMap.get(assumption).CreateCheckBox(assumption);
			this.assumptionChkbxList.add(chkBox);
		}
	}

	public JCheckBox getAssumptionCheckbox(int idx) {
		return assumptionChkbxList.get(idx);
	}

	/*****************/

	// Call methods to set fbd, model images and method to read questions.txt
	public void readQuestion() {
		File dir1 = new File(questionPath);
		File[] files = dir1.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].getName().contains("fbd.")) {
				System.out.println(new File(files[i].getPath() + "      filename"));
				readFbdImage(new File(files[i].getAbsolutePath()));
			} else if (files[i].getName().contains("model.")) {
				readModelImage(new File(files[i].getAbsolutePath()));
			} else if (files[i].getName().contains("Questions.")) {
				System.out.println(files[i] + " files ");
				readTextFile(files[i].getPath());
			}
		}
		readAssumptions();
		readScores();
		setAssumptionChkbxList();
	}

	public void readFbdImage(File imgFile) {
		fbdObj.readImage(imgFile);
	}

	public void readModelImage(File imgFile) {
		modObj.readImage(imgFile);
	}

	public void readTextFile(String txtPath) {
		try {
			FileInputStream fstream = new FileInputStream(txtPath);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			String strLine;
			String[] splittedElements = null;
			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {

				// splittedElements=strLine.trim().split("\\n");
				data.add(strLine);
			}

			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void readAssumptions() {
		// Read each line of assumption
		for (int dataIdx = 0; dataIdx < data.size(); dataIdx++) {
			if (data.get(dataIdx).contains("Assumptions:")) {
				int assumIdx = dataIdx + 1;
				while (assumIdx < data.size()) {
					String[] splitter = data.get(assumIdx).split("\\|");
					System.out.println(splitter[0]);
					EngineeringEducatorAssumption assumObj = new EngineeringEducatorAssumption(splitter[0], splitter[1]);
					assumptionObjMap.put(splitter[0], assumObj);
					assumptions.add(splitter[0]);
					if (splitter[1].equals("incorrect") || splitter[1].equals("complicated")) {
						// If incorrect/complicated, tell assumption class to
						// create
						// reason list for that particular assumption, pass the
						// data of text file, index from where list of reasons
						// starts
						// method will return index where list of reasons ends
						int reasonIdx = assumIdx + 1;
						int endReasonIdx = assumObj.setReasonList(reasonIdx, data);
						assumIdx = endReasonIdx + 1;
					} else
						// move to next assumption
						assumIdx++;
				}
				break;
			}
		}
	}

	public void readScores() {
		// Splitting into assumptions and reasons
		for (int j = 0; j < data.size(); j++) {
			if (data.get(j).contains("MaxScore:")) {
				String[] score = data.get(j).trim().split("\\:");
				setMaxScore(new Integer(score[1]));
			}
			if (data.get(j).contains("ScorePerAssum:")) {
				String[] score = data.get(j).trim().split("\\:");
				setPerAssumScore(new Integer(score[1]));
			}

			// Might be moved to assumption class
			if (data.get(j).contains("ScorePerReason:")) {
				String[] score = data.get(j).trim().split("\\:");
				setPerReasonScore(new Integer(score[1]));
			}

			if (data.get(j).contains("ScorePerNegAssum:")) {
				String[] score = data.get(j).trim().split("\\:");
				setPerAssumNegScore(new Integer(score[1]));
			}
		}
	}

	public JLabel MessageType(String assumption) {
		String assumtype = assumptionObjMap.get(assumption).getAnswer();
		JLabel lblMessage = null;

		if (assumtype.equals("correct")) {
			// reasonObject.listOfRdbtnListForReasons.add(null);
			// reasonObject.answers.add(-1);
			// reasonObject.reasonMsgLabelList.add(null);
		}
		if (assumtype.equals("incorrect")) {
			lblMessage = new JLabel("This assumption is incorrect, what could be the reason?");
			lblMessage.setFont(new Font("Georgia", Font.PLAIN, 16));
			lblMessage.setVisible(false);
			// reasonObject.reasonMsgLabelList.add(lblIncorrect);
		}
		if (assumtype.equals("complicated")) {
			lblMessage = new JLabel("This assumption is a complicating factor, what could be the reason?");
			lblMessage.setFont(new Font("Georgia", Font.PLAIN, 16));
			lblMessage.setVisible(false);
			// reasonObject.reasonMsgLabelList.add(lblIncorrect);
		}

		return lblMessage;

	}

	public void disableCheckBoxes() {
		for (int i = 0; i < assumptionChkbxList.size(); i++) {
//			assumptionChkbxList.get(i).setEnabled(false);
			final int chkbxIndex = i;
			assumptionChkbxList.get(i).addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if(assumptionChkbxList.get(chkbxIndex).isSelected())
						assumptionChkbxList.get(chkbxIndex).setSelected(false);
					else
						assumptionChkbxList.get(chkbxIndex).setSelected(true);
//					assumptionChkbxList.get(chkbxIndex).setSelected(false);
				}
			});
		}

	}

	// SCORE CALCULATION (int score)
	// For each assumption object, get answer from assumption class
	// If correct, then add perAssumption mark to score, do bg manipulation
	// If incorrect/complicated, do bg manipulation, make list of radio button
	// visible
	// Return score
	public int ScoreCalculation(int score) {
		int tempscore = 0;
		for (int j = 0; j < assumptionChkbxList.size(); j++) {
			boolean ansChkbxComparison = true;
			String assumans = getAnswer(assumptions.get(j));
			// System.out.println(assumptions.get(j));
			if (assumptionChkbxList.get(j).isSelected() && assumans.equals("correct"))
				ansChkbxComparison = true;
			else if (!assumptionChkbxList.get(j).isSelected()
					&& (assumans.equals("incorrect") || assumans.equals("complicated")))
				ansChkbxComparison = true;
			else
				ansChkbxComparison = false;
			if (ansChkbxComparison == false) {

				if (assumans.equals("incorrect")) {
					assumptionChkbxList.get(j).setBackground(new Color(204, 0, 0)); // red
					if(tempscore==0)
						tempscore += minScore;
					else
						tempscore+=perAssumNegScore;
					anywrong = true;
				} else if (assumans.equals("complicated")) {
					assumptionChkbxList.get(j).setBackground(new Color(204, 51, 0));// red
					if(tempscore==0)
						tempscore += minScore;
					else
						tempscore+=perAssumNegScore;
					anywrong = true;
				} else if (assumans.equals("correct")) {
					assumptionChkbxList.get(j).setBackground(new Color(0, 102, 34)); // green
					Font defultFont = assumptionChkbxList.get(j).getFont();
					Font boldFont = new Font(defultFont.getFontName(), Font.BOLD+Font.ITALIC, defultFont.getSize());
					assumptionChkbxList.get(j).setFont(boldFont);
				}
				
				//show reasons for incorrect assumption
				if (assumptionObjMap.get(assumptions.get(j)).reasonRdbList.size() > 0) {
					for (int k = 0; k < assumptionObjMap.get(assumptions.get(j)).reasonRdbList.size(); k++) {
						assumptionObjMap.get(assumptions.get(j)).reasonRdbList.get(k).setVisible(true);
					}
				}
			}
			else if (ansChkbxComparison == true && !assumans.equals("correct")) {
				assumptionChkbxList.get(j).setEnabled(false);
			}
			
			
			if (assumans.equals("correct")) {
				assumptionChkbxList.get(j).setBackground(new Color(0, 102, 34)); // green
				Font defultFont = assumptionChkbxList.get(j).getFont();
				Font boldFont = new Font(defultFont.getFontName(), Font.BOLD+Font.ITALIC, defultFont.getSize());
				assumptionChkbxList.get(j).setFont(boldFont);
			}
			if (ansChkbxComparison == true && assumans.equals("correct")) {
				tempscore += perAssumScore;
			}

		}
		if (tempscore <= maxScore) {
			score += tempscore;
		}
		return score;
	}

	public int ScoreCalculationReason(int score) {
		int tempscore = 0;
		anywrong = false;
		System.out.println("Per Reason Score" + perReasonScore);
		for (int j = 0; j < assumptions.size(); j++) {
			if ((assumptionObjMap.get(assumptions.get(j)).reasonRdbList.size() > 0)) {
				boolean reasonAnsStatus = assumptionObjMap.get(assumptions.get(j)).ScoreCalculation();
				System.out.println(reasonAnsStatus);
				System.out.println(tempscore);
				if (!reasonAnsStatus) {
					tempscore += perReasonScore;
				}
			}
		}
		System.out.println(anywrong);
		if (tempscore <= maxScore) {
			score += tempscore;
		} else {
			score += maxScore;
		}

		return score;
	}
}
