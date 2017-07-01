import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class QuestionHandler {
	static ArrayList<String> displayedQuestionFolders = new ArrayList<String>();
	static String folderPath = "";
	
	public QuestionHandler(String folderPath){
		this.folderPath = folderPath;
	}
	
	public static String FolderRandomSelection() {
		String questionPath = null;
		try{
			System.out.println(folderPath);
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
	
	public static int getTotalQuestions(){
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
	
	public String selectQuestion(){
		boolean isDisplayed = false;
		String questionPath = "";
		int totQuestions = getTotalQuestions();
		do{
			questionPath = FolderRandomSelection();
			if(!displayedQuestionFolders.contains(questionPath)){
				displayedQuestionFolders.add(questionPath);
				isDisplayed = false;
			}
			else{
				isDisplayed = true;
			}
		}while(isDisplayed && displayedQuestionFolders.size() != totQuestions);
		return questionPath;
		

	}

	public boolean isLastQuestion(){
		int totQuestions = getTotalQuestions();
		if(totQuestions == displayedQuestionFolders.size())
			return true;
		return false;
	}
}
