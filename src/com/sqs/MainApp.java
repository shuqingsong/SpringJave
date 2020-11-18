package com.sqs;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.content.StringBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

import com.google.zxing.BarcodeFormat;
import com.sqs.base64.Base64Util;
import com.sqs.encrypt.EncryptUtil;
import com.sqs.helloworld.HelloWorld;
import com.sqs.helloworld.HelloWorld1;
import com.sqs.jdbc.Puser;
import com.sqs.jdbc.TestJDBCTemplate;
import com.sqs.mail.MailUtil;
import com.sqs.maps.MapsUtil;
import com.sqs.ocrandface.OcrAndFaceUtil;
import com.sqs.zxingcode.LogoConfig;
import com.sqs.zxingcode.ZxingConfig;
import com.sqs.zxingcode.ZxingUtil;

public class MainApp {

	final Logger logger = LoggerFactory.getLogger(MainApp.class);
	
	public static void main(String[] args) throws Exception {

		//Spring 的 BeanFactory 容器
		//XmlBeanFactory factory = new XmlBeanFactory(new ClassPathResource("Beans.xml"));
		//Spring ApplicationContext 容器
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		
//		HelloWorld obj = (HelloWorld) factory.getBean("helloWorld");
//		obj.getMessage();
//		HelloWorld obj1 = (HelloWorld) context.getBean("helloWorld");
//		obj1.getMessage();
//		HelloWorld1 obj2 = (HelloWorld1) context.getBean("helloWorld1");
//		obj2.getMessage();
//		obj2.getMessage1();
//		obj2.getAddressList();
//		obj2.getAddressSet();
//		obj2.getAddressMap();
	
		//连接Oracle数据库
		TestJDBCTemplate testJDBCTemplate = (TestJDBCTemplate)context.getBean("testJDBCTemplate");
		testJDBCTemplate.create("1","421087199511286512", "P00", "舒庆松", "17683744166", "1");
		Puser puser=testJDBCTemplate.query("421087199511286512", "P00");
		System.out.println(puser.getUserseq()+" "+puser.getIdno()+" "+puser.getIdtype()+" "+puser.getName()+" "+puser.getMobile()+" "+puser.getState());
	    List pusers = testJDBCTemplate.queryList();
	    for (int i=0;i<pusers.size();i++) {
	    	Puser puser1=(Puser) pusers.get(i);
	    	System.out.println(puser1.getUserseq()+" "+puser1.getIdno()+" "+puser1.getIdtype()+" "+puser1.getName()+" "+puser1.getMobile()+" "+puser1.getState());
	     }
	    testJDBCTemplate.update("421087199511286512", "P00", "0");
		Puser puser2=testJDBCTemplate.query("421087199511286512", "P00");
		System.out.println(puser2.getUserseq()+" "+puser2.getIdno()+" "+puser2.getIdtype()+" "+puser2.getName()+" "+puser2.getMobile()+" "+puser2.getState());
	    testJDBCTemplate.delete("421087199511286512", "P00");
	    
	    //条形码/二维码 生成与解析
//		ZxingUtil zxingUtil = (ZxingUtil) context.getBean("zxingUtil");
//		String imgPath1="G:/zxingUtil/BarCode1.png";
//		String contents1="17683744166";
//		int width1=300;
//		int height1=100;
//		zxingUtil.encodeQRCode_BarCode(contents1, width1, height1, imgPath1, new String("1"));//条形码
//		String decodeContent1=zxingUtil.decodeQRCode_BarCode(imgPath1);
		
//		String imgPath2="G:/zxingUtil/QRCode1.png";
//		String contents2="17683744166ABCabc我愛中國！";
//		int width2=300;
//		int height2=300;
//		zxingUtil.encodeQRCode_BarCode(contents2, width2, height2, imgPath2, new String("0"));//二维码
//		String decodeContent2=zxingUtil.decodeQRCode_BarCode(imgPath2);
		
//		String imgPath3="G:/zxingUtil/BarCode2.png";
//		String contents3="17683744166";
//		ZxingConfig zxingconfig3 = new ZxingConfig();
//		zxingconfig3.setContent(contents3);
//		zxingconfig3.setBarcodeformat(BarcodeFormat.CODE_128);
//		zxingconfig3.setWidth(300);
//		zxingconfig3.setHeight(100);
//		zxingconfig3.setHints(zxingUtil.CreateDecodeHintType());
//		BufferedImage bim3 = zxingUtil.encodeQRCode_BarCodeBufferedImage(zxingconfig3);
//		BufferedImage outImage3 = zxingUtil.encodeBarCodeWords(bim3, contents3, new File(imgPath3));

//		String imgPath4="G:/zxingUtil/QRCode2.png";
//		String logoPath4="G:/zxingUtil/1.png";
//		String contents4="17683744166ABCabc我愛中國！";
//		ZxingConfig zxingconfig4 = new ZxingConfig();
//		zxingconfig4.setContent(contents4);
//		zxingconfig4.setBarcodeformat(BarcodeFormat.QR_CODE);
//		zxingconfig4.setHints(zxingUtil.CreateDecodeHintType());
//		BufferedImage bim4 = zxingUtil.encodeQRCode_BarCodeBufferedImage(zxingconfig4);
//		BufferedImage outImage4 = zxingUtil.encodeQRCodeLogo(bim4, new File(logoPath4), new File(imgPath4));
		
//		String imgPath5="G:/zxingUtil/QRCodes.png";
//		String decodeContent=zxingUtil.decodeQRCode_BarCode(imgPath5);

		//地址/经纬度 解析
//		MapsUtil mapsUtil = (MapsUtil) context.getBean("mapsUtil");
//		String aliyunAdd=mapsUtil.aliyunGetAdd("30.511126","114.398099");
//		String[] aliyunLatlng=mapsUtil.aliyunGetLngAndLat("湖北省武汉市洪山区关山街道紫菘花园");
		
//		String tenxunAdd=mapsUtil.tenxunGetAdd("30.51161","114.394173");
//		String[] tenxunLatlng=mapsUtil.tenxunGetLngAndLat("湖北省武汉市洪山区关山街道紫菘花园");
		
//		String baiduAdd=mapsUtil.baiduGetAdd("30.517374454896783","114.4016440500094");
//		String[] baiduLatlng=mapsUtil.baiduGetLngAndLat("湖北省武汉市洪山区关山街道紫菘花园");
		
		//加解密/签名验签
//		EncryptUtil encryptUtil = (EncryptUtil) context.getBean("encryptUtil");
//		String str="123abcABC我是中国人";
//		String encryptMD5=encryptUtil.encryptMD5_SHA(str,"MD5");
//		String encryptSHA=encryptUtil.encryptMD5_SHA(str,"SHA");
		
//		String secretKey3DES=encryptUtil.generate3DESKey(168);
//		String encrypt3DES=encryptUtil.encrypt3DES(str,secretKey3DES);
//		String decrypt3DES=encryptUtil.decrypt3DES(encrypt3DES,secretKey3DES);
		
//		String secretKeyAES=encryptUtil.generateAESKey(128);
//		String encryptAES=encryptUtil.encryptAES(str,secretKeyAES);
//		String decryptAES=encryptUtil.decryptAES(encryptAES,secretKeyAES);
		
//		Map keyMap=encryptUtil.generateRSAKey(1024);
//		String encryptRSA=encryptUtil.encryptRSA(str,(String)keyMap.get("PublicKey"));
//		String decryptRSA=encryptUtil.decryptRSA(encryptRSA,(String)keyMap.get("PrivateKey"));
		
//		String signData=encryptUtil.signRSA(str,(String)keyMap.get("PrivateKey"));
//		boolean verityResult=encryptUtil.verityRSA(str,(String)keyMap.get("PublicKey"),signData);
		
		//电子邮箱
//		MailUtil mailUtil = (MailUtil) context.getBean("mailUtil");
//		String email1="shuqingsong@csii.com.cn";
//		String email2="jiaodezhen@csii.com.cn";
//		String title="武汉众邦银行存款证明";
//		String content="尊敬的武汉众邦银行用户：<br/>&nbsp;&nbsp;&nbsp;&nbsp;附件为我行为您提供的存款证明，请核验查收。<br/>&nbsp;&nbsp;&nbsp;&nbsp;如有疑问，请联系众邦银行客服4006886868。";
//		List filePath=new ArrayList();
//		for(int i=0;i<50;i++){
//			filePath.add("G:/mail/SQS1.pdf");
//			filePath.add("G:/mail/SQS2.pdf");
//		}
//		boolean success=mailUtil.smtpSend(email1,email2, title, content, filePath);
		
		//OCR和人脸识别
//		OcrAndFaceUtil ocrAndFaceUtil = (OcrAndFaceUtil) context.getBean("ocrAndFaceUtil");
//		String image1="G:/zxingUtil/IdCard.jpg";
//		String image2="G:/zxingUtil/IdCardBack.jpg";
//		String image3="G:/zxingUtil/BankCard.jpg";
//		String image4="G:/zxingUtil/XCS.jpg";
//		String image5="G:/zxingUtil/XC.jpg";
//		Map sendMap=new HashMap();
//		sendMap.put("image_base64", Base64Util.GetImageStr(image1));
//		String id_card_number=ocrAndFaceUtil.idCardOCR(sendMap);
//		sendMap.put("image_base64", Base64Util.GetImageStr(image2));
//		String valid_date=ocrAndFaceUtil.idCardOCR(sendMap);
		
//		sendMap.put("image_base64", Base64Util.GetImageStr(image3));
//		String number=ocrAndFaceUtil.bankCardOCR(sendMap);
		
//		sendMap.put("image_base64", Base64Util.GetImageStr(image4));
//		String face_token=ocrAndFaceUtil.faceDetect(sendMap);
		
//		sendMap.put("image_base64_1", Base64Util.GetImageStr(image4));
//		sendMap.put("image_base64_2", Base64Util.GetImageStr(image5));
//		boolean success1=ocrAndFaceUtil.faceCompare(sendMap);
		
//		sendMap.put("face_tokens", face_token);
//		String faceset_token=ocrAndFaceUtil.faceCreate(sendMap);
		
//		sendMap.put("image_base64", Base64Util.GetImageStr(image4));
//		sendMap.put("faceset_token", faceset_token);
//		boolean success2=ocrAndFaceUtil.faceSearch(sendMap);
		
	}

}