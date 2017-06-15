package sec;

import java.awt.image.BufferedImage;
import java.io.File;

public class EngineeringEducator {
	File parentDir;
	String questionDir;
	BufferedImage fbdImg;
	BufferedImage modelImg;
	String assumptionsPath;
	String reasonsPath;
	public File getParentDir() {
		return parentDir;
	}
	public void setParentDir(File parentDir) {
		this.parentDir = parentDir;
	}
	public String getQuestionDir() {
		return questionDir;
	}
	public void setQuestionDir(String questionDir) {
		this.questionDir = questionDir;
	}
	public BufferedImage getFbdImg() {
		return fbdImg;
	}
	public void setFbdImg(BufferedImage fbdImg) {
		this.fbdImg = fbdImg;
	}
	public BufferedImage getModelImg() {
		return modelImg;
	}
	public void setModelImg(BufferedImage modelImg) {
		this.modelImg = modelImg;
	}
	public String getAssumptionsPath() {
		return assumptionsPath;
	}
	public void setAssumptionsPath(String assumptionsPath) {
		this.assumptionsPath = assumptionsPath;
	}
	public String getReasonsPath() {
		return reasonsPath;
	}
	public void setReasonsPath(String reasonsPath) {
		this.reasonsPath = reasonsPath;
	}
	
	
}
