package com.mx.util;


import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

public class ImageCompressUtil {

	/**
	 * 等比例压缩算法： 
	 * 算法思想：根据压缩基数和压缩比来压缩原图，生产一张图片效果最接近原图的缩略图
	 * @param srcURL 原图地址
	 * @param deskURL 缩略图地址
	 * @param comBase 压缩基数
	 * @param scale 压缩限制(宽/高)比例  一般用1：
	 * 当scale>=1,缩略图height=comBase,width按原图宽高比例;若scale<1,缩略图width=comBase,height按原图宽高比例
	 * @throws Exception
	 * @author 小米线儿
	 * @createTime 2014-12-16
	 * @lastModifyTime 2014-12-16
	 */
	public static void saveMinPhoto(String sourceFile,String newFile,double scale) throws Exception {
		Image src = ImageIO.read(new File(sourceFile));
		int srcHeight = src.getHeight(null);
		int srcWidth = src.getWidth(null);
		double comBase=srcWidth/2;
		int deskHeight = 0;// 缩略图高
		int deskWidth = 0;// 缩略图宽
		double srcScale = (double) srcHeight / srcWidth;
		/**缩略图宽高算法*/
		if ((double) srcHeight > comBase || (double) srcWidth > comBase) {
			if (srcScale >= scale || 1 / srcScale > scale) {
				if (srcScale >= scale) {
					deskHeight = (int) comBase;
					deskWidth = srcWidth * deskHeight / srcHeight;
				} else {
					deskWidth = (int) comBase;
					deskHeight = srcHeight * deskWidth / srcWidth;
				}
			} else {
				if ((double) srcHeight > comBase) {
					deskHeight = (int) comBase;
					deskWidth = srcWidth * deskHeight / srcHeight;
				} else {
					deskWidth = (int) comBase;
					deskHeight = srcHeight * deskWidth / srcWidth;
				}
			}
		} else {
			deskHeight = srcHeight;
			deskWidth = srcWidth;
		}
		BufferedImage tag = new BufferedImage(deskWidth, deskHeight, BufferedImage.TYPE_3BYTE_BGR);
		tag.getGraphics().drawImage(src, 0, 0, deskWidth, deskHeight, null); //绘制缩小后的图
		FileOutputStream deskImage = new FileOutputStream(new File(newFile)); //输出到文件流
		ImageIO.write(tag, "jpg", deskImage);
		//JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(deskImage);
		//encoder.encode(tag); //近JPEG编码
		deskImage.close();
	}

	public static void main(String args[]) throws Exception {
		long start=System.currentTimeMillis();
		String str = "C:\\Users\\Administrator\\Desktop\\啊啊啊.jpg";
		String str1 = "D:\\345.jpg";
		
		ImageCompressUtil.saveMinPhoto(str,str1, 0.9d);
		long end=System.currentTimeMillis();
		long use=end-start;
		System.out.println("响应时间："+use+"毫秒");
	}
}