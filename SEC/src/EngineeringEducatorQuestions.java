import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class EngineeringEducatorQuestions {
	ArrayList<String> displayedQuestionFolders = new ArrayList<String>();
	/*
	 * FOLDER RANDOM SELECTION Method to randomly select a question folder
	 * Input: 1. EngineeringEducator class object 2. Path of the directory
	 * containing question folders
	 */
	public String FolderRandomSelection(String folderPath) {
		String questionPath = null;
		try{
			File dir = new File(folderPath);
			String[] quesfolders = dir.list();
			Random rand = new Random();
			
			String folder = quesfolders[rand.nextInt(quesfolders.length)];
			questionPath = folderPath + folder;
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return questionPath;
	}
	
	public int getTotalQuestions(String folderPath){
		String questionPath = null;
		int num = 0;
		try{
			File dir = new File(folderPath);
			String[] quesfolders = dir.list();
			num = quesfolders.length;
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return num;
	}
}
