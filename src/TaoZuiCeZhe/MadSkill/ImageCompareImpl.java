package TaoZuiCeZhe.MadSkill;

import java.awt.Graphics2D;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class ImageCompareImpl implements ImageCompareInterface{
	private int size = 64;
	private int smallerSize = 8;
	private double[] c;
	
	public ImageCompareImpl(){
		initBinaryString();
	}
	
	private void initBinaryString() {
	  c = new double[size];	      
      for (int i=1;i<size;i++) {
         c[i]=1;
      }
	  c[0]=1/Math.sqrt(2.0);
	}

	@Override
	public String getImageHASHString(InputStream is,String imageName,int choosing) throws IOException, SelfException {
		BufferedImage img = ImageIO.read(is);
	      
	      /*  
	       *重置图片大小为：64*64pixs 
	       */
	      img = resize(img, size, size);
	      
	      /*  
	       *将图片色调全部转换为一种灰度级色调，方便二进制
	       *处理，排除颜色对于图片匹配度的干扰
	       */
	      img = grayscale(img);
	      
	      double[][] vals = new double[size][size];
	      
	      for (int x = 0; x < img.getWidth(); x++) {
	          for (int y = 0; y < img.getHeight(); y++) {
	              vals[x][y] = getPixelRGB(img, x, y);
	          }
	      }
	      
	      /*  
	       *  对获取的所有像素点的RGB值进行DCT计算
	       *  DCT算法是专门针对于图像压缩计算方法，
	       *  如果您感兴趣，请参看： 
	       *  http://zh.wikipedia.org/wiki/%E7%A6%BB%E6%95%A3%E4%BD%99%E5%BC%A6%E5%8F%98%E6%8D%A2
	       */
	      long start = System.currentTimeMillis();
	      double[][] dctVals = applyDCT(vals);
	      
	      
	      /* 重置DCT，仅仅取压缩后的全图左上角8*8 pixs的区间作为代表
	       * （因为此区间代表了变化低频区域，那么低频区的差异性更客观地代表图片整体的匹配度）
	       */
	      
	      /* 
	       * 计算DCT的平均值 
	       */
	      double total = 0;
	      
	      for (int x = 0; x < smallerSize; x++) {
	          for (int y = 0; y < smallerSize; y++) {
	              total += dctVals[x][y];
	          }
	      }
	      total -= dctVals[0][0];
	      
	      double avg = total / (double) ((smallerSize * smallerSize) - 1);
	  
	      /*  
	       * 深度转换DCT，将结果全部组成为0/1组合队列
	       */
	      String hash = "";
	      
	      for (int x = 0; x < smallerSize; x++) {
	          for (int y = 0; y < smallerSize; y++) {
	              if (x != 0 && y != 0) {
	                  hash += (dctVals[x][y] > avg?"1":"0");
	              }
	          }
	      }
	      if(choosing == 1){
	    	  System.out.println("[INFO]对SourceImages中的图片名为["+imageName+"]进行的DCT图片计算完成,耗时: " + (System.currentTimeMillis() - start)+" ms");
	      }else if(choosing == 2){
	    	  System.out.println("[INFO]对TargetImages中的图片名为["+imageName+"]进行的DCT图片计算完成,耗时: " + (System.currentTimeMillis() - start)+" ms");
	      }else{
	    	throw new SelfException("Only support 1,2 to select source path and target path!");  
	      }
	      return hash;
	}


	private BufferedImage resize(BufferedImage image, int newWidth, int newHeight) {
		 BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
		 if(image != null){
		     Graphics2D graph = resizedImage.createGraphics();
		     graph.drawImage(image, 0, 0, newWidth, newHeight, null);
		     graph.dispose(); 
		 }else{
			 System.err.println("Getting images meet trouble when re-size it!");
		 }
	     return resizedImage;
	}


	private BufferedImage grayscale(BufferedImage img) {
		if(img != null){
			  ColorConvertOp colorConvert = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
		      if(colorConvert != null){
		    	  colorConvert.filter(img, img);  
		      }
		  }	  
	      return img;
	}


	private int getPixelRGB(BufferedImage img, int x, int y) {
		int rgbIntValue = 0;
		if(img != null){
			rgbIntValue = img.getRGB(x, y);
		}else{
			System.err.println("Getting images meet trouble when get each pixel");
		}
		return rgbIntValue;
	}


	private double[][] applyDCT(double[][] f) {
	     int N = size;
	      
	     double[][] F = new double[N][N];
	      for (int u=0;u<N;u++) {
	        for (int v=0;v<N;v++) {
	          double sum = 0.0;
	          for (int i=0;i<N;i++) {
	            for (int j=0;j<N;j++) {
//DCT公式实现，专业术语称之为“离散余弦变换”            	
	              sum+=Math.cos(((2*i+1)/(2.0*N))*u*Math.PI)*Math.cos(((2*j+1)/(2.0*N))*v*Math.PI)*(f[i][j]);
	            }
	          }
	          sum*=((c[u]*c[v])/4.0);
	          F[u][v] = sum;
	        }
	      }
	      return F;
	}
	
	/*
	 * 汉明距离实现
	 * 什么是汉明距离？请参看  http://zh.wikipedia.org/wiki/%E6%B1%89%E6%98%8E%E8%B7%9D%E7%A6%BB
	 */
	@Override
	public int HanMingDistance(String sourceHashStr, String targetHashStr) {
		int counter = 0;
	      for (int k = 0; k < sourceHashStr.length();k++) {
	          if(sourceHashStr.charAt(k) != targetHashStr.charAt(k)) {
	              counter++;
	          }
	      }
	      return counter;
	}
}
