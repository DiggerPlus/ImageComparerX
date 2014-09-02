package TaoZuiCeZhe.MadSkill;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ImageReader {
	private static final String sourceFolderPath = System.getProperty("user.dir") + "/SourceImages/";
	private static final String targetFolderPath = System.getProperty("user.dir") + "/TargetImages/";
	
	public static List<String> getSourceImageFileNameList() {
		long startTime = System.nanoTime();
		List<String> sourceImageFileNameList = new ArrayList<String>();
		File file = new File(sourceFolderPath);
		if(file.isDirectory()){
			String[] nameList = file.list();
			if(nameList != null){
				for(String name:nameList){
					sourceImageFileNameList.add(name);
				}
			}
		}
		long endTime = System.nanoTime() - startTime;
		System.out.println("[INFO]从SourceImages文件夹下获取到["+sourceImageFileNameList.size()+"]个图片文件!耗时: "+endTime + " ns");
		return sourceImageFileNameList;
	}
	
	public static List<String> getTargetImageFileNameList() {
		long startTime = System.nanoTime();
		List<String> targetImageFileNameList = new ArrayList<String>();
		File file = new File(targetFolderPath);
		if(file.isDirectory()){
			String[] nameList = file.list();
			if(nameList != null){
				for(String name:nameList){
					targetImageFileNameList.add(name);
				}
			}
		}
		long endTime = System.nanoTime() - startTime;
		System.out.println("[INFO]从TargetImages文件夹下获取到["+targetImageFileNameList.size()+"]个图片文件!耗时: "+endTime + " ns");
		return targetImageFileNameList;
	}
	
	public static InputStream getImageInputStream(String imageName,int choosing) throws SelfException{
		FileInputStream resultIs = null;
//1 = SOURCE ; 2 = TARGET
		if(choosing == 1){
			try {
				resultIs = new FileInputStream(new File(sourceFolderPath+imageName));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		else if(choosing == 2){
			try {
				resultIs = new FileInputStream(new File(targetFolderPath+imageName));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}else{
			throw new SelfException("Only support 1 or 2 to select source and target path!");
		}
		return resultIs;
	}
}
