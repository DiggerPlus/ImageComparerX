package TaoZuiCeZhe.MadSkill;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;


public class ComparerX {

	/**
	 * @param args
	 * @throws SelfException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws SelfException, IOException {
		List<String> sourceImageFileNameList = new ArrayList<String>();
		List<String> targetImageFileNameList = new ArrayList<String>();
		ImageCompareInterface imageCompare = new ImageCompareImpl();
		InvocationHandler handler = new Handler(imageCompare);
		
//创建动态代理对象
		ImageCompareInterface proxy= (ImageCompareInterface) Proxy.newProxyInstance(ImageCompareImpl.class.getClassLoader(), 
				ImageCompareImpl.class.getInterfaces(), 
				handler);
		if(proxy != null){
			sourceImageFileNameList = ImageReader.getSourceImageFileNameList();
			targetImageFileNameList = ImageReader.getTargetImageFileNameList();
			 
			if(sourceImageFileNameList.isEmpty()){
				throw new SelfException("No images in source image folder!");
			}else if(targetImageFileNameList.isEmpty()){
				throw new SelfException("No images in target image folder!");
			}else{
				int sourceImageFileNums = sourceImageFileNameList.size();
				int targetImageFileNums = targetImageFileNameList.size();
				
				if(sourceImageFileNums >= targetImageFileNums){
					for(String ImageName:sourceImageFileNameList){			
						if(targetImageFileNameList.contains(ImageName)){
							InputStream sourceIs = ImageReader.getImageInputStream(ImageName, 1);
							InputStream targetIs = ImageReader.getImageInputStream(ImageName, 2);
							String sourceImageHashStr = proxy.getImageHASHString(sourceIs,ImageName,1);
							String targetImageHashStr = proxy.getImageHASHString(targetIs,ImageName,2);
							int diffs = proxy.HanMingDistance(sourceImageHashStr, targetImageHashStr);
							CompareResultRangeHelper.compareResult(diffs, ImageName, ImageName);
						}else{
							throw new SelfException("Can't find the image named="+ImageName+" in target image folder or using the different expand name!");
						}
						
					}
				}
				
				if(sourceImageFileNums < targetImageFileNums){
					for(String ImageName:targetImageFileNameList){			
						if(sourceImageFileNameList.contains(ImageName)){
							InputStream sourceIs = ImageReader.getImageInputStream(ImageName, 1);
							InputStream targetIs = ImageReader.getImageInputStream(ImageName, 2);
							String sourceImageHashStr = proxy.getImageHASHString(sourceIs,ImageName,1);
							String targetImageHashStr = proxy.getImageHASHString(targetIs,ImageName,2);
							int diffs = proxy.HanMingDistance(sourceImageHashStr, targetImageHashStr);
							CompareResultRangeHelper.compareResult(diffs, ImageName, ImageName);
						}else{
							throw new SelfException("Can't find the image named="+ImageName+" in source image folder or using the different expand name!");
						}
						
					}
				}
			}
			
		}
	}
}
