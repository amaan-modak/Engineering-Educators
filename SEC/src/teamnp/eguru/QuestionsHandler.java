package teamnp.eguru;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


import org.apache.commons.lang3.SystemUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.apache.commons.io.FileUtils;

public class QuestionsHandler {
	static ArrayList<String> displayedQuestionFolders = new ArrayList<String>();
	static String folderPath = "";
	static boolean questionsFolderExists;
    String gitCloneFolderName = "temp";
	
	public QuestionsHandler(String folderPath){
		File direx = new File(folderPath);
		 		if(!direx.exists() && !direx.isDirectory()){
		 			System.out.println("Folder doesnot exist");
		 			questionsFolderExists = false;
		 			try {
		 				folderPath = GitClone();
		 			} catch (IOException e) {
		 				// TODO Auto-generated catch block
		 				e.printStackTrace();
		 			}			
		 		}
		 		else{
		 			questionsFolderExists = true;
		 		}
		QuestionsHandler.folderPath = folderPath;
	}
	
	public static String FolderRandomSelection() {
		String questionPath = null;
		try{
			File dir = new File(folderPath);
			String[] quesfolders = dir.list(new FilenameFilter() {
				  @Override
				  public boolean accept(File current, String name) {
				    return new File(current, name).isDirectory();
				  }
				});
			
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
			String[] quesfolders = dir.list(new FilenameFilter() {
				  @Override
				  public boolean accept(File current, String name) {
				    return new File(current, name).isDirectory();
				  }
				});
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
	public String GitClone() throws IOException{
		
		String localPath = gitCloneFolderName; //The directory where the repo will be cloned (This is also needed to call the deleteDir() method)
//        String remotePath = "https://github.com/amaan-modak/Engineering-Educators.git"; //URL for the GIT repo
		
    	if(SystemUtils.IS_OS_WINDOWS){
    		System.out.println("Windows");
    		Path path = Paths.get(localPath);
    		Files.setAttribute(path, "dos:hidden", Boolean.TRUE, LinkOption.NOFOLLOW_LINKS);
    	}
    	if(SystemUtils.IS_OS_LINUX || SystemUtils.IS_OS_MAC_OSX ||SystemUtils.IS_OS_MAC){
    		localPath = ".".concat(localPath);
    	}
    	
    	gitCloneFolderName = localPath;

		String remotePath = "https://github.com/bakshizaki/eGuruQuestions.git"; //URL for the GIT repo
        FileRepository localRepo = new FileRepository(localPath + "/.git");
        File lp = new File(localPath); //Converting input string into directory
        Git git = new Git(localRepo); //Creating GIT repo
        
        if(lp.exists()){
        	deleteDir(lp);	
        }
        
        try {
       		System.out.println("Downloading");
       		//This is to clone the repo, all fields needed
       		git = Git.cloneRepository()
				  .setURI(remotePath)
				  .setDirectory(lp)
				  .setBranchesToClone(Arrays.asList("master"))
				  .setBranch("master")
				  .setCloneAllBranches(false)
				  .call();
       		System.out.println("Download Complete");
       	}
        catch (Exception e) {
       		// TODO Auto-generated catch block
       		System.out.println("catch1");
       		e.printStackTrace();
       		System.out.println("catch2");
       	}


		localPath = localPath + "/Questions/";
		return localPath;
	}
	void deleteDir(File file) throws IOException {
//		System.out.println("deleting");
		if(file == null || !file.exists())
			return;
	    File[] contents = file.listFiles();
	    if (contents != null) {
	        for (File f : contents) {
	            deleteDir(f);
	        }
	    }
	    file.delete();
		//FileUtils.deleteDirectory(file);
	}


}