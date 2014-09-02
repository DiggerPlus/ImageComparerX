package TaoZuiCeZhe.MadSkill;

import java.io.IOException;
import java.io.InputStream;

public interface ImageCompareInterface {
//获取图片的二进制表示队列	
	public String getImageHASHString(InputStream is,String imageName,int choosing) throws IOException, SelfException;
//汉明距离实现
	public int HanMingDistance(String sourceHashStr,String targetHashStr);
}
