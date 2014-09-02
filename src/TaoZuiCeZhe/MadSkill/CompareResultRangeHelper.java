package TaoZuiCeZhe.MadSkill;

public class CompareResultRangeHelper {
	private static String filePath = System.getProperty("user.dir")+"/Result/";
	private static String fileName = "ImagesComparingResult.log";
	
	public static void compareResult(int diffs,String sourceImageName,String targetImageName){
		System.out.println("[INFO]开始判断图片相似度... ...");
		long startTime = System.currentTimeMillis();
		if(diffs == 0 ){
			String content1 = "Source ImageName<=>"+sourceImageName;
			String content2 = "Target ImageName<=>"+targetImageName;
			String content3 = "比对结果： 完全一致！";
			String content4 = "===================================================================";
			CustomizedLog.writeCustomizedLogFile(filePath+fileName, content1);
			CustomizedLog.writeCustomizedLogFile(filePath+fileName, content2);
			CustomizedLog.writeCustomizedLogFile(filePath+fileName, content3);
			CustomizedLog.writeCustomizedLogFile(filePath+fileName, content4);
		}
		if(diffs > 0 && diffs <= 5){
			String content1 = "Source ImageName<=>"+sourceImageName;
			String content2 = "Target ImageName<=>"+targetImageName;
			String content3 = "比对结果： 非常相似！";
			String content4 = "===================================================================";
			CustomizedLog.writeCustomizedLogFile(filePath+fileName, content1);
			CustomizedLog.writeCustomizedLogFile(filePath+fileName, content2);
			CustomizedLog.writeCustomizedLogFile(filePath+fileName, content3);
			CustomizedLog.writeCustomizedLogFile(filePath+fileName, content4);
		}
		
		if(diffs > 5 && diffs <= 10){
			String content1 = "Source ImageName<=>"+sourceImageName;
			String content2 = "Target ImageName<=>"+targetImageName;
			String content3 = "比对结果： 有轻微差别！";
			String content4 = "===================================================================";
			CustomizedLog.writeCustomizedLogFile(filePath+fileName, content1);
			CustomizedLog.writeCustomizedLogFile(filePath+fileName, content2);
			CustomizedLog.writeCustomizedLogFile(filePath+fileName, content3);
			CustomizedLog.writeCustomizedLogFile(filePath+fileName, content4);
		}
		
		if(diffs > 10 && diffs <= 15){
			String content1 = "Source ImageName<=>"+sourceImageName;
			String content2 = "Target ImageName<=>"+targetImageName;
			String content3 = "比对结果： 有较明显差别！";
			String content4 = "===================================================================";
			CustomizedLog.writeCustomizedLogFile(filePath+fileName, content1);
			CustomizedLog.writeCustomizedLogFile(filePath+fileName, content2);
			CustomizedLog.writeCustomizedLogFile(filePath+fileName, content3);
			CustomizedLog.writeCustomizedLogFile(filePath+fileName, content4);
		}
		
		if(diffs > 15){
			String content1 = "Source ImageName<=>"+sourceImageName;
			String content2 = "Target ImageName<=>"+targetImageName;
			String content3 = "比对结果： 差别非常明显！";
			String content4 = "===================================================================";
			CustomizedLog.writeCustomizedLogFile(filePath+fileName, content1);
			CustomizedLog.writeCustomizedLogFile(filePath+fileName, content2);
			CustomizedLog.writeCustomizedLogFile(filePath+fileName, content3);
			CustomizedLog.writeCustomizedLogFile(filePath+fileName, content4);
		}
		long endTime = System.currentTimeMillis() - startTime;
		System.out.println("[INFO]图片相似度判断结束，请在'ImagesComparingResult.log'中查看比对结果，总耗时："+endTime+" ms");
	}
}
