package teamnp.eguru;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
Class having responsibility of maintaining question
Question comprises of Model Image, FBD and "List" of Assumptions along with their answers
It also holds information about maximum score of the question, mark for each correct assumption which are read from file
*/
public class Question {

	String questionPath = "";
	FBDImage fbdObj = new FBDImage();
	ModelImage modObj = new ModelImage();
	ArrayList<String> data = new ArrayList<String>();
	ArrayList<String> assumptions = new ArrayList<String>();
	ArrayList<JCheckBox> assumptionChkbxList = new ArrayList<JCheckBox>();
	ArrayList<String> hintList=new ArrayList<String>();
	HashMap<String, Assumption> assumptionObjMap = new HashMap<String, Assumption>();
	String problemDescription;
	JLabel fbdSelectionLabel;
	FBDSelection fbdSelection;
	String fbdDataFileName;
	String forceDataFileName;
	BufferedImage forceImage;
	ForceSelection forceSelection;
	// Key = assumption, value = Assumption class object

	int perReasonScore = 0;
	int minScore=0;
	int maxScore=0;

	int perAssumScore = 0;
	int perAssumNegScore = 0;
	
	int perFBDScore=0;
	int perFBDNegScore=0;
	int perHintScore=0;
	int perForceScore=0;
	
	boolean anywrong = false;

	/*** Constructor ***/
	public Question(String questionPath) {
		// TODO Auto-generated constructor stub
		this.questionPath = questionPath;
	}

	/***************/

	/*** Getters & Setters ***/

	public Assumption getAssumObj(String assumption) {
		return assumptionObjMap.get(assumption);
	}

	public int getNumberOfAssumptions() {
		return assumptions.size();
	}

	public String getAssumption(int idx) {
		return assumptions.get(idx);
	}

	public void setMaxScore(int score) {
		maxScore = score;
	}

	public void setPerReasonScore(int score) {
		perReasonScore = score;
	}

	public int getPerReasonScore() {
		return this.perReasonScore;
	}

	public int getPerHintScore(){
		return this.perHintScore;
	}
	
	public int getPerFBDScore(){
		return this.perFBDScore;
	}

	public int getPerFBDNegScore(){
		return this.perFBDNegScore;
	}
	
	public int getPerForceScore(){
		return this.perForceScore;
	}
	
	public void setPerAssumScore(int score) {
		perAssumScore = score;
	}

	public void setPerAssumNegScore(int score) {
		perAssumNegScore = score;
	}
	
	public void setPerFBDScore(int score){
		perFBDScore=score;
	}
	
	public void setPerFBDNegScore(int score){
		perFBDNegScore=score;
	}
	
	public void setPerHintScore(int score){
		perHintScore=score;
	}
	
	public void setPerForceScore(int score){
		perForceScore=score;
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
				readFbdImage(new File(files[i].getAbsolutePath()));
			} else if (files[i].getName().contains("model.")) {
				readModelImage(new File(files[i].getAbsolutePath()));
			} else if (files[i].getName().contains("Questions.")) {
				readTextFile(files[i].getPath());
			} else if (files[i].getName().contains("fbdData.")) {
				fbdDataFileName = files[i].getPath();
			} else if (files[i].getName().contains("forceData.")) {
				forceDataFileName = files[i].getPath();
			} else if (files[i].getName().contains("fbdCropped.")) {
				try {
					forceImage = ImageIO.read(new File(files[i].getAbsolutePath()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		fbdSelection = new FBDSelection(fbdDataFileName, fbdObj.originalImage);
		forceSelection = new ForceSelection(forceDataFileName, forceImage);
		problemDescription = readProblemDescription();
		readAssumptions();
		readScores();
		setAssumptionChkbxList();
		readHints();
	}
	
	public JLabel getFBDSelectionImageLabel() {
		return fbdSelection.getImageLabel();
	}
	
	public boolean getFBDAnswer() {
		return fbdSelection.getFinalAnswer();
	}
	
	public void startFBDSelection() {
		fbdSelection.startTest();
	}
	
	public int getFBDRetryAttempts(){
		return fbdSelection.retryAttempts;
	}
	
	public JLabel getForceSelectionImageLabel() {
		return forceSelection.getImageLabel();
	}
	
	public boolean getForceAnswer() {
		return forceSelection.getFinalAnswer();
	}
	
	public void startForceSelection() {
		forceSelection.startTest();
	}
	
	public JPanel getForceGui() {
		return forceSelection.getGui();
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
			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
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
	
	private String readProblemDescription() {
		int dataIdx = 0;
		String retString = "";
		while(!data.get(dataIdx).contains("Question:"))
			dataIdx++;
		dataIdx++;
		while(!data.get(dataIdx).contains("End Question") && dataIdx<data.size()) {
			retString += data.get(dataIdx);
			dataIdx++;
		}
		return retString;
	}
	

	public void readAssumptions() {
		// Read each line of assumption
		for (int dataIdx = 0; dataIdx < data.size(); dataIdx++) {
			if (data.get(dataIdx).contains("Assumptions:")) {
				int assumIdx = dataIdx + 1;
				while (assumIdx < data.size() && (!data.get(assumIdx).contains("End Assumptions"))) {
					String[] splitter = data.get(assumIdx).split("\\|");
					Assumption assumObj = new Assumption(splitter[0], splitter[1]);
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
	
	public void readHints(){
		for (int dataIdx = 0; dataIdx < data.size(); dataIdx++) {
			if (data.get(dataIdx).contains("Hints:")) {
				int hintIdx = dataIdx + 1;
				while (hintIdx < data.size()){
					hintList.add(data.get(hintIdx));
					System.out.println(hintList);
					hintIdx+=1;
				}
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

			if (data.get(j).contains("ScorePerReason:")) {
				String[] score = data.get(j).trim().split("\\:");
				setPerReasonScore(new Integer(score[1]));
			}

			if (data.get(j).contains("ScorePerNegAssum:")) {
				String[] score = data.get(j).trim().split("\\:");
				setPerAssumNegScore(new Integer(score[1]));
			}
			
			if (data.get(j).contains("FBDScore:")) {
				String[] score = data.get(j).trim().split("\\:");
				setPerFBDScore(new Integer(score[1]));
			}
			
			if (data.get(j).contains("FBDNegScore:")) {
				String[] score = data.get(j).trim().split("\\:");
				setPerFBDNegScore(new Integer(score[1]));
			}
			
			if (data.get(j).contains("HintScore:")) {
				String[] score = data.get(j).trim().split("\\:");
				setPerHintScore(new Integer(score[1]));
			}
			
			if (data.get(j).contains("ForceScore:")) {
				String[] score = data.get(j).trim().split("\\:");
				setPerForceScore(new Integer(score[1]));
			}
			
		}
	}

	public void disableCheckBoxes() {
		for (int i = 0; i < assumptionChkbxList.size(); i++) {
			final int chkbxIndex = i;
			assumptionChkbxList.get(i).addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if(assumptionChkbxList.get(chkbxIndex).isSelected())
						assumptionChkbxList.get(chkbxIndex).setSelected(false);
					else
						assumptionChkbxList.get(chkbxIndex).setSelected(true);
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
					assumptionObjMap.get(assumptions.get(j)).getlblMessage().setVisible(true);
					tempscore+=perAssumNegScore;
					anywrong = true;
				} else if (assumans.equals("complicated")) {
					assumptionChkbxList.get(j).setBackground(new Color(204, 51, 0));// red
					assumptionObjMap.get(assumptions.get(j)).getlblMessage().setVisible(true);
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
		score += tempscore;
				
		if(score<minScore)
			score=minScore;
		else if(score>maxScore)
			score=maxScore;
		
		return score;
	}

	public int ScoreCalculationReason(int score) {
		int tempscore = 0;
		anywrong = false;
		for (int j = 0; j < assumptions.size(); j++) {
			if ((assumptionObjMap.get(assumptions.get(j)).reasonRdbList.size() > 0)) {
				boolean reasonAnsStatus = assumptionObjMap.get(assumptions.get(j)).ScoreCalculation();
				if (!reasonAnsStatus) {
					tempscore += perReasonScore;
				}
			}
		}
		score += tempscore;
		if(score<minScore)
			score=minScore;
		else if(score>maxScore)
			score=maxScore;

		return score;
	}
}
