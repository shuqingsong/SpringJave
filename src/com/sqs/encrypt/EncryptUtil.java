package com.sqs.encrypt;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class EncryptUtil {

	final Logger logger = LoggerFactory.getLogger(EncryptUtil.class);
	private static final String DEFAULT_CHARSET="UTF-8";
	private static final String DES_ALGORITHM="DESede";
	private static final String AES_ALGORITHM="AES";
	private static final String RSA_ALGORITHM="RSA";
	private static final String SIGNATURE_ALGORITHM="SHA1withRSA";
	private static final String DES_IV = "01234567";//3DES(DESede)算法,CBC模式需要8位的向量
	private static final String AES_IV = "0123456789ABCDEF";//AES算法,CBC模式需要16位的向量

	 /**
	  * MD5/SHA加密(散列算法)(不可逆,无法解密)
	  * @param str 明文
	  * @param hashType 加密类型(MD5 和 SHA)
	  * @return secretStr 密文
	  */
	public String encryptMD5_SHA(String str,String hashType) {
		
		try {
			MessageDigest mdInst=MessageDigest.getInstance(hashType);//基于hashType算法生成MessageDigest对象
			byte[] md=mdInst.digest(str.getBytes(DEFAULT_CHARSET));//使用MessageDigest对象的方法,完成加密
			StringBuffer secretStr=new StringBuffer();
			for(int i=0;i<md.length;i++){
				secretStr.append(String.format("%02X", md[i]));//格式化为十六进制,不足2位前面补0,X字母大写(x字母小写)
			}
			
			return secretStr.toString();//返回密文
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			return null;
		}
	}
	
	 /**
	  * 生成3DES密钥(对称加密)(可逆,可解密)
	  * @param keySize 密钥长度(112~168)
	  * @return secretKey 3DES密钥
	  */
	public String generate3DESKey(int keySize) {
		
		try {
			KeyGenerator kg=KeyGenerator.getInstance(DES_ALGORITHM);//对称密钥生成器,基于DESede算法生成KeyGenerator对象
			kg.init(keySize);//初始化密钥生成器,密钥长度(112~168)
	        Key key=kg.generateKey();//生成一个密钥，保存在Key中
	        String secretKey=Base64.getEncoder().encodeToString(key.getEncoded());//获得Base64编码的密钥字符串
			
			return secretKey;//返回3DES密钥
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			return null;
		}
	}
	
	 /**
	  * 3DES加密
	  * @param str 明文
	  * @param secretKey 加密Key
	  * @return secretStr 密文
	  */
	public String encrypt3DES(String str,String secretKey) {
		
		try {
			byte[] secKeyStr=Base64.getDecoder().decode(secretKey);//获得Base64解码的加密Key字节数组
			SecretKeySpec secKey=new SecretKeySpec(secKeyStr,DES_ALGORITHM);//转换为3DES专用密钥
			
//			IvParameterSpec iv = new IvParameterSpec(DES_IV.getBytes(DEFAULT_CHARSET));
//			Cipher ciper=Cipher.getInstance("DESede/CBC/PKCS5Padding");//Cipher类用于加密和解密,基于DESede/CBC/PKCS5Padding(算法/模式/补码方式)生成Cipher对象
//			ciper.init(Cipher.ENCRYPT_MODE, secKey, iv);//初始化Cipher对象
			Cipher ciper=Cipher.getInstance("DESede/ECB/PKCS5Padding");//Cipher类用于加密和解密,基于DESede/ECB/PKCS5Padding(算法/模式/补码方式)生成Cipher对象
			ciper.init(Cipher.ENCRYPT_MODE, secKey);//初始化Cipher对象
			
			byte[] encryptStr=ciper.doFinal(str.getBytes(DEFAULT_CHARSET));//使用Cipher对象的方法,完成加密
			String secretStr=Base64.getEncoder().encodeToString(encryptStr);//获得Base64编码的密文字符串
			
			return secretStr;//返回密文
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			return null;
		}
	}
	
	 /**
	  * 3DES解密
	  * @param secretStr 密文
	  * @param secretKey 解密Key
	  * @return str 明文
	  */
	public String decrypt3DES(String secretStr,String secretKey) {
		
		try {
			byte[] secKeyStr=Base64.getDecoder().decode(secretKey);//获得Base64解码的解密Key字节数组
			SecretKeySpec secKey=new SecretKeySpec(secKeyStr,DES_ALGORITHM);//转换为3DES专用密钥
			
//			IvParameterSpec iv = new IvParameterSpec(DES_IV.getBytes(DEFAULT_CHARSET));
//			Cipher ciper=Cipher.getInstance("DESede/CBC/PKCS5Padding");//Cipher类用于加密和解密,基于DESede/CBC/PKCS5Padding(算法/模式/补码方式)生成Cipher对象
//			ciper.init(Cipher.DECRYPT_MODE, secKey, iv);//初始化Cipher对象
			Cipher ciper=Cipher.getInstance("DESede/ECB/PKCS5Padding");//Cipher类用于加密和解密,基于DESede/ECB/PKCS5Padding(算法/模式/补码方式)生成Cipher对象
			ciper.init(Cipher.DECRYPT_MODE, secKey);//初始化Cipher对象
			
			byte[] decryptStr=ciper.doFinal(Base64.getDecoder().decode(secretStr));//使用Cipher对象的方法,完成解密
			String str=new String(decryptStr,DEFAULT_CHARSET);//获得明文字符串

			return str;//返回明文
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			return null;
		}
	}

	 /**
	  * 生成AES密钥(对称加密)(可逆,可解密)
	  * @param keySize 密钥长度(128,192或256)
	  * @return secretKey AES密钥
	  */
	public String generateAESKey(int keySize) {
		
		try {
			KeyGenerator kg=KeyGenerator.getInstance(AES_ALGORITHM);//对称密钥生成器,基于AES算法生成KeyGenerator对象
			kg.init(keySize);//初始化密钥生成器,密钥长度(128,192或256)
	        Key key=kg.generateKey();//生成一个密钥，保存在Key中
	        String secretKey=Base64.getEncoder().encodeToString(key.getEncoded());//获得Base64编码的密钥字符串
			
			return secretKey;//返回AES密钥
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			return null;
		}
	}
	
	 /**
	  * AES加密
	  * @param str 明文
	  * @param secretKey 加密Key
	  * @return secretStr 密文
	  */
	public String encryptAES(String str,String secretKey) {
		
		try {
			byte[] secKeyStr=Base64.getDecoder().decode(secretKey);//获得Base64解码的加密Key字节数组
			SecretKeySpec secKey=new SecretKeySpec(secKeyStr,AES_ALGORITHM);//转换为AES专用密钥
			
//			IvParameterSpec iv = new IvParameterSpec(AES_IV.getBytes(DEFAULT_CHARSET));
//			Cipher ciper=Cipher.getInstance("AES/CBC/PKCS5Padding");//Cipher类用于加密和解密,基于AES/CBC/PKCS5Padding(算法/模式/补码方式)生成Cipher对象
//			ciper.init(Cipher.ENCRYPT_MODE, secKey, iv);//初始化Cipher对象
			Cipher ciper=Cipher.getInstance("AES/ECB/PKCS5Padding");//Cipher类用于加密和解密,基于AES/ECB/PKCS5Padding(算法/模式/补码方式)生成Cipher对象
			ciper.init(Cipher.ENCRYPT_MODE, secKey);//初始化Cipher对象
			
			byte[] encryptStr=ciper.doFinal(str.getBytes(DEFAULT_CHARSET));//使用Cipher对象的方法,完成加密
			String secretStr=Base64.getEncoder().encodeToString(encryptStr);//获得Base64编码的密文字符串
			
			return secretStr;//返回密文
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			return null;
		}
	}
	
	 /**
	  * AES解密
	  * @param secretStr 密文
	  * @param secretKey 解密Key
	  * @return str 明文
	  */
	public String decryptAES(String secretStr,String secretKey) {
		
		try {
			byte[] secKeyStr=Base64.getDecoder().decode(secretKey);//获得Base64解码的解密Key字节数组
			SecretKeySpec secKey=new SecretKeySpec(secKeyStr,AES_ALGORITHM);//转换为AES专用密钥
			
//			IvParameterSpec iv = new IvParameterSpec(AES_IV.getBytes(DEFAULT_CHARSET));
//			Cipher ciper=Cipher.getInstance("AES/CBC/PKCS5Padding");//Cipher类用于加密和解密,基于AES/CBC/PKCS5Padding(算法/模式/补码方式)生成Cipher对象
//			ciper.init(Cipher.DECRYPT_MODE, secKey, iv);//初始化Cipher对象
			Cipher ciper=Cipher.getInstance("AES/ECB/PKCS5Padding");//Cipher类用于加密和解密,基于AES/ECB/PKCS5Padding(算法/模式/补码方式)生成Cipher对象
			ciper.init(Cipher.DECRYPT_MODE, secKey);//初始化Cipher对象
			
			byte[] decryptStr=ciper.doFinal(Base64.getDecoder().decode(secretStr));//使用Cipher对象的方法,完成解密
			String str=new String(decryptStr,DEFAULT_CHARSET);//获得明文字符串

			return str;//返回明文
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			return null;
		}
	}
	
	 /**
	  * 生成RSA公私钥对(非对称加密)(可逆,可解密)
	  * @param keySize 密钥长度(512~2048)
	  * @return keyMap RSA公私钥对
	  */
	public Map generateRSAKey(int keySize) {
		
		try {
			KeyPairGenerator kpg=KeyPairGenerator.getInstance(RSA_ALGORITHM);//非对称密钥生成器,基于RSA算法生成KeyPairGenerator对象
			kpg.initialize(keySize);//初始化密钥生成器,密钥长度(512~2048)
			KeyPair kp=kpg.generateKeyPair();//生成一个密钥对，保存在keyPair中
			PublicKey pubKey=kp.getPublic();//获得公钥PublicKey对象
			PrivateKey priKey=kp.getPrivate();//获得私钥PrivateKey对象
			String publicKey=Base64.getEncoder().encodeToString(pubKey.getEncoded());//获得Base64编码的公钥字符串
			String privateKey=Base64.getEncoder().encodeToString(priKey.getEncoded());//获得Base64编码的私钥字符串
			Map keyMap=new HashMap();
			keyMap.put("PublicKey", publicKey);
			keyMap.put("PrivateKey", privateKey);
			
			return keyMap;//返回RSA公私钥对
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			return null;
		}
	}
	
	 /**
	  * RSA加密
	  * @param str 明文
	  * @param publicKey 公钥
	  * @return secretStr 密文
	  */
	public String encryptRSA(String str,String publicKey) {
		
		try {
			byte[] pubKeyStr=Base64.getDecoder().decode(publicKey);//获得Base64解码的公钥字节数组
			PublicKey pubKey=KeyFactory.getInstance(RSA_ALGORITHM).generatePublic(new X509EncodedKeySpec(pubKeyStr));//获得公钥PublicKey对象
			
			Cipher ciper=Cipher.getInstance(RSA_ALGORITHM);//Cipher类用于加密和解密,基于RSA算法生成Cipher对象
			ciper.init(Cipher.ENCRYPT_MODE, pubKey);//初始化Cipher对象
			byte[] encryptStr=ciper.doFinal(str.getBytes(DEFAULT_CHARSET));//使用Cipher对象的方法,完成加密
			String secretStr=Base64.getEncoder().encodeToString(encryptStr);//获得Base64编码的密文字符串
			
			return secretStr;//返回密文
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			return null;
		}
	}
	
	 /**
	  * RSA解密
	  * @param secretStr 密文
	  * @param privateKey 私钥
	  * @return str 明文
	  */
	public String decryptRSA(String secretStr,String privateKey) {
		
		try {
			byte[] priKeyStr = Base64.getDecoder().decode(privateKey);//获得Base64解码的私钥字节数组
			PrivateKey priKey=KeyFactory.getInstance(RSA_ALGORITHM).generatePrivate(new PKCS8EncodedKeySpec(priKeyStr));//获得私钥PrivateKey对象
			
			Cipher ciper=Cipher.getInstance(RSA_ALGORITHM);//Cipher类用于加密和解密,基于RSA算法生成Cipher对象
			ciper.init(Cipher.DECRYPT_MODE, priKey);//初始化Cipher对象
			byte[] decryptStr=ciper.doFinal(Base64.getDecoder().decode(secretStr));//使用Cipher对象的方法,完成解密
			String str=new String(decryptStr,DEFAULT_CHARSET);//获得明文字符串

			return str;//返回明文
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			return null;
		}
	}
	
	 /**
	  * RSA签名
	  * @param src 明文
	  * @param privateKey 私钥
	  * @return signData 签名
	  */
	public String signRSA(String str,String privateKey) {
		
		try {
			byte[] priKeyStr = Base64.getDecoder().decode(privateKey);//获得Base64解码的私钥字节数组
			PrivateKey priKey=KeyFactory.getInstance(RSA_ALGORITHM).generatePrivate(new PKCS8EncodedKeySpec(priKeyStr));//获得私钥PrivateKey对象
			
			Signature sign=Signature.getInstance(SIGNATURE_ALGORITHM);//Signature类用于签名和验签,基于SHA1withRSA算法生成Signature对象
			sign.initSign(priKey);//初始化Signature对象
			sign.update(str.getBytes(DEFAULT_CHARSET));
			byte[] signStr=sign.sign();//使用Signature对象的方法,完成签名
			String signData=Base64.getEncoder().encodeToString(signStr);//获得Base64编码的签名字符串
			
			return signData;//返回签名
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			return null;
		}
	}
	
	 /**
	  * RSA验签
	  * @param str 明文
	  * @param publicKey 公钥
	  * @param signData 签名
	  * @return verityResult trun成功 false失败
	  */
	public boolean verityRSA(String str,String publicKey,String signData) {
		
		try {
			byte[] pubKeyStr=Base64.getDecoder().decode(publicKey);//获得Base64解码的公钥字节数组
			PublicKey pubKey=KeyFactory.getInstance(RSA_ALGORITHM).generatePublic(new X509EncodedKeySpec(pubKeyStr));//获得公钥PublicKey对象
			
			Signature sign=Signature.getInstance(SIGNATURE_ALGORITHM);//Signature类用于签名和验签,基于SHA1withRSA算法生成Signature对象
			sign.initVerify(pubKey);//初始化Signature对象
			sign.update(str.getBytes(DEFAULT_CHARSET));
			boolean verityResult=sign.verify(Base64.getDecoder().decode(signData));//使用Signature对象的方法,完成验签

			return verityResult;//返回验签结果
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			return false;
		}
	}
}