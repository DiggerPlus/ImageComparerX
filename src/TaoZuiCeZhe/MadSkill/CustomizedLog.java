package TaoZuiCeZhe.MadSkill;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomizedLog {
	static{
		String filePath = System.getProperty("user.dir")+"/Result/";
		String fileName = "ImagesComparingResult.log";
		String topic = "图片相似度比对";
		try {
			creatCusomizedLogFile(filePath,fileName,topic);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected static void creatCusomizedLogFile(String filePath,String fileName,String topic) throws IOException{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currenTime = df.format(new Date());
		File file = new File(filePath);
		if(file.exists() == false){
			file.mkdirs();
		}
		else{
			FileWriter writer = new FileWriter(filePath + fileName);
			writer.write("主题:" + topic + "\n");
			writer.append("比对时间:" + currenTime + "\n");
			writer.append("===================================================================" + "\n");
			writer.close();
		}
	}
	
	public static void writeCustomizedLogFile(String filePath, String content) {  
        try {  
            FileWriter writer = new FileWriter(filePath, true);  
            writer.append(content+"\n");  
            writer.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }
}
