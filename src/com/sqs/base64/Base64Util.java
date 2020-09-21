package com.sqs.base64;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Base64Util {

	final static Logger logger = LoggerFactory.getLogger(Base64Util.class);
	
	/**
	 * 根据图片路径对图片进行BASE64编码
	 * @param imgFilePath 图片文件所在路径
	 * @return 图片的base64编码字符串
	 */
	public static String GetImageStr(String imgFilePath) {
		
		if(null==imgFilePath || ("").equals(imgFilePath)){
			return null;
		}
		byte[] data = null;
		InputStream in = null;
		try {
			in = new FileInputStream(imgFilePath);
			int count = -1;//in.available有可能为0所以不能用0做默认值,否则会死循环
			while (count ==-1) {
				count = in.available();
			}
			if(count<=0){
				return null;
			}
			data = new byte[count];
			in.read(data);
		} catch (IOException e) {
			logger.error("异常",e);
			return null;
		} finally {
			try {
				if (null != in) {
					in.close();
				}
			} catch (IOException e) {
				logger.error("异常",e);
			}
		}
		return Base64.getEncoder().encodeToString(data);
	}
	
	/**
	 * 对BASE64字符串解码并保存成文件
	 * @param imgStr base64编码的字符串
	 * @param imgFilePath 保存图片的文件路径
	 * @return 成功或失败
	 */
	public static boolean GenerateImage(String imgStr, String imgFilePath) {
		
		if (null==imgStr || ("").equals(imgStr) || null==imgFilePath || ("").equals(imgFilePath)){
			return false;
		}
		OutputStream out = null;
		BufferedOutputStream bos = null;
		try {
			byte[] bytes = Base64.getDecoder().decode(imgStr);
			out = new FileOutputStream(imgFilePath);
			bos = new BufferedOutputStream(out);
			bos.write(bytes);
			bos.flush();
			return true;
		} catch (Exception e) {
			logger.error("异常",e);
			return false;
		} finally {
			try {
				if (null != bos){
					bos.close();
				}
				if (null != out){
					out.close();
				}
			} catch (IOException e) {
				logger.error("异常",e);
			}
		}
	}
}
