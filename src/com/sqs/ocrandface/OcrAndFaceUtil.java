package com.sqs.ocrandface;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.sqs.http.HttpUtil;

public class OcrAndFaceUtil {
	
	final Logger logger = LoggerFactory.getLogger(OcrAndFaceUtil.class);
	String idcardapi;
	String bankcardapi;
	String bankcardapi1;
	String createapi;
	String searchapi;
	String detectapi;
	String compareapi;
	String apikey;
	String apisecret;
	
	 /**
	  * 调用OCR进行 中华人民共和国第二代身份证检测和识别
	  * @param map
	  * @return
	  */
	public String idCardOCR(Map map) {
		try {
			HttpClient httpClient = new HttpUtil().createHttpClient();
			HttpPost httpPost = new HttpPost(idcardapi);
			
		    MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		    
	        builder.addPart("api_key",new StringBody(apikey,ContentType.MULTIPART_FORM_DATA)); 
	        builder.addPart("api_secret",new StringBody(apisecret,ContentType.MULTIPART_FORM_DATA));
	        
	        //image_url image_file image_base64
	        builder.addPart("image_base64",new StringBody((String) map.get("image_base64"),ContentType.MULTIPART_FORM_DATA));
	        //是否返回身份证照片合法性检查结果  0(不返回,默认值) 1(返回)
	        builder.addPart("legality",new StringBody("0",ContentType.MULTIPART_FORM_DATA));
	        
	        HttpEntity entity = builder.build();
	        httpPost.setEntity(entity);
	        
	        HttpResponse response = httpClient.execute(httpPost);
	        if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
	            String resultJson = EntityUtils.toString(response.getEntity(), "UTF-8");
	            Map backMap = (Map) JSONObject.parse(resultJson);
	            logger.info(backMap.toString());
	            System.out.println(backMap.toString());
	            if(!StringUtils.isEmpty(backMap.get("error_message"))){
	    			logger.error((String) backMap.get("error_message"));
	    			return null;
	            }
	            List cards=(List) backMap.get("cards");
	            if(null!=cards && 0<cards.size()){
		            Map card=(Map)cards.get(0);
		            if("front".equals(card.get("side"))){
		            	return (String)card.get("id_card_number");
		            }else{
		            	return (String)card.get("valid_date");
		            }
	            }else{
	            	return null;
	            }
	        }else{
	        	if(!StringUtils.isEmpty(response.getEntity())){
		            String resultJson = EntityUtils.toString(response.getEntity(), "UTF-8");
		            Map backMap = (Map) JSONObject.parse(resultJson);
		            if(!StringUtils.isEmpty(backMap.get("error_message"))){
		    			logger.error((String) backMap.get("error_message"));
		    			return null;
		            }else{
						logger.error("调用接口失败");
						return null;
		            }
	        	}else{
					logger.error("调用接口失败");
					return null;
	        	}
	        }
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	
	 /**
	  * 调用OCR进行 各类银行卡检测和识别
	  * @param map
	  * @return
	  */
	public String bankCardOCR(Map map) {
		try {
			HttpClient httpClient = new HttpUtil().createHttpClient();
			HttpPost httpPost = new HttpPost(bankcardapi);
			
		    MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		    
	        builder.addPart("api_key",new StringBody(apikey,ContentType.MULTIPART_FORM_DATA)); 
	        builder.addPart("api_secret",new StringBody(apisecret,ContentType.MULTIPART_FORM_DATA));
	        
	        //image_url image_file image_base64
	        builder.addPart("image_base64",new StringBody((String) map.get("image_base64"),ContentType.MULTIPART_FORM_DATA));
	        
	        HttpEntity entity = builder.build();
	        httpPost.setEntity(entity);
	        
	        HttpResponse response = httpClient.execute(httpPost);
	        if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
	            String resultJson = EntityUtils.toString(response.getEntity(), "UTF-8");
	            Map backMap = (Map) JSONObject.parse(resultJson);
	            logger.info(backMap.toString());
	            System.out.println(backMap.toString());
	            if(!StringUtils.isEmpty(backMap.get("error_message"))){
	    			logger.error((String) backMap.get("error_message"));
	    			return null;
	            }
	            List bank_cards=(List) backMap.get("bank_cards");
	            if(null!=bank_cards && 0<bank_cards.size()){
		            Map bank_card=(Map)bank_cards.get(0);
		            return (String)bank_card.get("number");
	            }else{
	            	return null;
	            }
	        }else{
	        	if(!StringUtils.isEmpty(response.getEntity())){
		            String resultJson = EntityUtils.toString(response.getEntity(), "UTF-8");
		            Map backMap = (Map) JSONObject.parse(resultJson);
		            if(!StringUtils.isEmpty(backMap.get("error_message"))){
		    			logger.error((String) backMap.get("error_message"));
		    			return null;
		            }else{
						logger.error("调用接口失败");
						return null;
		            }
	        	}else{
					logger.error("调用接口失败");
					return null;
	        	}
	        }
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	
	 /**
	  * 调用Face++创建一个人脸的集合 FaceSet，用于存储人脸标识 face_token
	  * @param map
	  * @return
	  */
	public String faceCreate(Map map) {
		try {
			HttpClient httpClient = new HttpUtil().createHttpClient();
			HttpPost httpPost = new HttpPost(createapi);
			
		    MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		    
	        builder.addPart("api_key",new StringBody(apikey,ContentType.MULTIPART_FORM_DATA)); 
	        builder.addPart("api_secret",new StringBody(apisecret,ContentType.MULTIPART_FORM_DATA));
	        
	        //人脸集合的名字，最长256个字符，不能包括字符^@,&=*'"
//	        builder.addPart("display_name",new StringBody((String) map.get("display_name"),ContentType.MULTIPART_FORM_DATA));
	        //账号下全局唯一的 FaceSet自定义标识，可以用来管理 FaceSet对象
//	        builder.addPart("outer_id",new StringBody((String) map.get("outer_id"),ContentType.MULTIPART_FORM_DATA));
	        //FaceSet自定义标签组成的字符串，用来对 FaceSet分组
//	        builder.addPart("tags",new StringBody((String) map.get("tags"),ContentType.MULTIPART_FORM_DATA));
	        //人脸标识 face_token，可以是一个或者多个，用逗号分隔。最多不超过5个 face_token
	        builder.addPart("face_tokens",new StringBody((String) map.get("face_tokens"),ContentType.MULTIPART_FORM_DATA));
	        //自定义用户信息，不大于16 KB，不能包括字符^@,&=*'"
//	        builder.addPart("user_data",new StringBody((String) map.get("user_data"),ContentType.MULTIPART_FORM_DATA));
	        //在传入 outer_id 的情况下，如果 outer_id已经存在，是否将 face_token加入已经存在的 FaceSet中 
	        //0(不将 face_tokens 加入已存在的 FaceSet 中，直接返回 FACESET_EXIST 错误) 1(将 face_tokens 加入已存在的 FaceSet 中)
	        builder.addPart("force_merge",new StringBody("0",ContentType.MULTIPART_FORM_DATA));
	        
	        HttpEntity entity = builder.build();
	        httpPost.setEntity(entity);
	        
	        HttpResponse response = httpClient.execute(httpPost);
	        if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
	            String resultJson = EntityUtils.toString(response.getEntity(), "UTF-8");
	            Map backMap = (Map) JSONObject.parse(resultJson);
	            logger.info(backMap.toString());
	            System.out.println(backMap.toString());
	            if(!StringUtils.isEmpty(backMap.get("error_message"))){
	    			logger.error((String) backMap.get("error_message"));
	    			return null;
	            }
	            String faceset_token=(String) backMap.get("faceset_token");
	            String outer_id=(String) backMap.get("outer_id");
		        return faceset_token;
	        }else{
	        	if(!StringUtils.isEmpty(response.getEntity())){
		            String resultJson = EntityUtils.toString(response.getEntity(), "UTF-8");
		            Map backMap = (Map) JSONObject.parse(resultJson);
		            if(!StringUtils.isEmpty(backMap.get("error_message"))){
		    			logger.error((String) backMap.get("error_message"));
		    			return null;
		            }else{
						logger.error("调用接口失败");
						return null;
		            }
	        	}else{
					logger.error("调用接口失败");
					return null;
	        	}
	        }
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	
	 /**
	  * 调用Face++在一个已有的 FaceSet中找出与目标人脸最相似的一张或多张人脸，返回置信度和不同误识率下的阈值
	  * @param map
	  * @return
	  */
	public boolean faceSearch(Map map) {
		try {
			HttpClient httpClient = new HttpUtil().createHttpClient();
			HttpPost httpPost = new HttpPost(searchapi);
			
		    MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		    
	        builder.addPart("api_key",new StringBody(apikey,ContentType.MULTIPART_FORM_DATA)); 
	        builder.addPart("api_secret",new StringBody(apisecret,ContentType.MULTIPART_FORM_DATA));
	        
	        //face_token image_url image_file image_base64
	        builder.addPart("image_base64",new StringBody((String) map.get("image_base64"),ContentType.MULTIPART_FORM_DATA));
	        //faceset_token(用来搜索的 FaceSet 的标识) outer_id(用户自定义的 FaceSet 标识)
	        builder.addPart("faceset_token",new StringBody((String) map.get("faceset_token"),ContentType.MULTIPART_FORM_DATA));
	        //控制返回比对置信度最高的结果的数量。合法值为一个范围 [1,5] 的整数。默认值为 1
	        builder.addPart("return_result_count",new StringBody("1",ContentType.MULTIPART_FORM_DATA));
	        
	        HttpEntity entity = builder.build();
	        httpPost.setEntity(entity);
	        
	        HttpResponse response = httpClient.execute(httpPost);
	        if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
	            String resultJson = EntityUtils.toString(response.getEntity(), "UTF-8");
	            Map backMap = (Map) JSONObject.parse(resultJson);
	            logger.info(backMap.toString());
	            System.out.println(backMap.toString());
	            if(!StringUtils.isEmpty(backMap.get("error_message"))){
	    			logger.error((String) backMap.get("error_message"));
	    			return false;
	            }
	            Map thresholds=(Map) backMap.get("thresholds");
	            List results=(List) backMap.get("results");
	            if(null!=results && 0<results.size()){
		            Map result=(Map)results.get(0);
		            BigDecimal confidence=(BigDecimal) result.get("confidence");
		            if(!StringUtils.isEmpty(confidence) && !StringUtils.isEmpty(thresholds)){
			            BigDecimal $1e_5=(BigDecimal) thresholds.get("1e-5");
			            if(confidence.compareTo($1e_5)<0){//比对结果置信度<1e-5(误识率为十万分之一的置信度阈值)
			    			logger.error("不是同一个人");
			    			System.out.println("不是同一个人");
			    			return false;
			            }
			            return true;
		            }else{
		    			logger.error("不是同一个人");
		    			System.out.println("不是同一个人");
		    			return false;
		            }
	            }else{
	    			logger.error("不是同一个人");
	    			System.out.println("不是同一个人");
	            	return false;
	            }
	        }else{
	        	if(!StringUtils.isEmpty(response.getEntity())){
		            String resultJson = EntityUtils.toString(response.getEntity(), "UTF-8");
		            Map backMap = (Map) JSONObject.parse(resultJson);
		            if(!StringUtils.isEmpty(backMap.get("error_message"))){
		    			logger.error((String) backMap.get("error_message"));
		    			return false;
		            }else{
						logger.error("调用接口失败");
						return false;
		            }
	        	}else{
					logger.error("调用接口失败");
					return false;
	        	}
	        }
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}
	
	 /**
	  * 调用Face++进行 人脸检测和人脸分析
	  * @param map
	  * @return
	  */
	public String faceDetect(Map map) {
		try {
			HttpClient httpClient = new HttpUtil().createHttpClient();
			HttpPost httpPost = new HttpPost(detectapi);
			
		    MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		    
	        builder.addPart("api_key",new StringBody(apikey,ContentType.MULTIPART_FORM_DATA)); 
	        builder.addPart("api_secret",new StringBody(apisecret,ContentType.MULTIPART_FORM_DATA));
	        
	        //image_url image_file image_base64
	        builder.addPart("image_base64",new StringBody((String) map.get("image_base64"),ContentType.MULTIPART_FORM_DATA));
	        //是否检测并返回人脸关键点  0(不检测,默认值) 1(检测,返回 83 个人脸关键点) 2(检测,返回 106 个人脸关键点)
	        builder.addPart("return_landmark",new StringBody("0",ContentType.MULTIPART_FORM_DATA));
	        //是否检测并返回根据人脸特征判断出的年龄、性别、情绪等属性    none(不检测属性,默认值) gender,age,smiling,headpose
	        builder.addPart("return_attributes",new StringBody("none",ContentType.MULTIPART_FORM_DATA));
	        //颜值评分分数区间的最小值
	        builder.addPart("beauty_score_min",new StringBody("0",ContentType.MULTIPART_FORM_DATA));
	        //颜值评分分数区间的最大值
	        builder.addPart("beauty_score_max",new StringBody("100",ContentType.MULTIPART_FORM_DATA));
	        
	        HttpEntity entity = builder.build();
	        httpPost.setEntity(entity);
	        
	        HttpResponse response = httpClient.execute(httpPost);
	        if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
	            String resultJson = EntityUtils.toString(response.getEntity(), "UTF-8");
	            Map backMap = (Map) JSONObject.parse(resultJson);
	            logger.info(backMap.toString());
	            System.out.println(backMap.toString());
	            if(!StringUtils.isEmpty(backMap.get("error_message"))){
	    			logger.error((String) backMap.get("error_message"));
	    			return null;
	            }
	            List faces=(List) backMap.get("faces");
	            if(null!=faces && 0<faces.size()){
		            Map face=(Map)faces.get(0);
		            return (String)face.get("face_token");
	            }else{
	            	return null;
	            }
	        }else{
	        	if(!StringUtils.isEmpty(response.getEntity())){
		            String resultJson = EntityUtils.toString(response.getEntity(), "UTF-8");
		            Map backMap = (Map) JSONObject.parse(resultJson);
		            if(!StringUtils.isEmpty(backMap.get("error_message"))){
		    			logger.error((String) backMap.get("error_message"));
		    			return null;
		            }else{
						logger.error("调用接口失败");
						return null;
		            }
	        	}else{
					logger.error("调用接口失败");
					return null;
	        	}
	        }
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	
	 /**
	  * 调用Face++进行 人脸比对
	  * @param map
	  * @return
	  */
	public boolean faceCompare(Map map) {
		try {
			HttpClient httpClient = new HttpUtil().createHttpClient();
			HttpPost httpPost = new HttpPost(compareapi);
			
		    MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		    
	        builder.addPart("api_key",new StringBody(apikey,ContentType.MULTIPART_FORM_DATA)); 
	        builder.addPart("api_secret",new StringBody(apisecret,ContentType.MULTIPART_FORM_DATA));
	        
			//face_token1 image_url1 image_file1 image_base64_1
	        builder.addPart("image_base64_1",new StringBody((String) map.get("image_base64_1"),ContentType.MULTIPART_FORM_DATA));
	        //face_token2 image_url2 image_file2 image_base64_2
	        builder.addPart("image_base64_2",new StringBody((String) map.get("image_base64_2"),ContentType.MULTIPART_FORM_DATA));
	        
	        HttpEntity entity = builder.build();
	        httpPost.setEntity(entity);
	        
	        HttpResponse response = httpClient.execute(httpPost);
	        if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
	            String resultJson = EntityUtils.toString(response.getEntity(), "UTF-8");
	            Map backMap = (Map) JSONObject.parse(resultJson);
	            logger.info(backMap.toString());
	            System.out.println(backMap.toString());
	            if(!StringUtils.isEmpty(backMap.get("error_message"))){
	    			logger.error((String) backMap.get("error_message"));
	    			return false;
	            }
	            Map thresholds=(Map) backMap.get("thresholds");
	            BigDecimal confidence=(BigDecimal) backMap.get("confidence");
	            if(!StringUtils.isEmpty(confidence) && !StringUtils.isEmpty(thresholds)){
		            BigDecimal $1e_5=(BigDecimal) thresholds.get("1e-5");
		            if(confidence.compareTo($1e_5)<0){//比对结果置信度<1e-5(误识率为十万分之一的置信度阈值)
		    			logger.error("不是同一个人");
		    			System.out.println("不是同一个人");
		    			return false;
		            }
		            return true;
	            }else{
	    			logger.error("不是同一个人");
	    			System.out.println("不是同一个人");
	    			return false;
	            }
	        }else{
	        	if(!StringUtils.isEmpty(response.getEntity())){
		            String resultJson = EntityUtils.toString(response.getEntity(), "UTF-8");
		            Map backMap = (Map) JSONObject.parse(resultJson);
		            if(!StringUtils.isEmpty(backMap.get("error_message"))){
		    			logger.error((String) backMap.get("error_message"));
		    			return false;
		            }else{
						logger.error("调用接口失败");
						return false;
		            }
	        	}else{
					logger.error("调用接口失败");
					return false;
	        	}
	        }
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public String getIdcardapi() {
		return idcardapi;
	}

	public void setIdcardapi(String idcardapi) {
		this.idcardapi = idcardapi;
	}

	public String getBankcardapi() {
		return bankcardapi;
	}

	public void setBankcardapi(String bankcardapi) {
		this.bankcardapi = bankcardapi;
	}

	public String getBankcardapi1() {
		return bankcardapi1;
	}

	public void setBankcardapi1(String bankcardapi1) {
		this.bankcardapi1 = bankcardapi1;
	}

	public String getCreateapi() {
		return createapi;
	}

	public void setCreateapi(String createapi) {
		this.createapi = createapi;
	}

	public String getSearchapi() {
		return searchapi;
	}

	public void setSearchapi(String searchapi) {
		this.searchapi = searchapi;
	}

	public String getDetectapi() {
		return detectapi;
	}

	public void setDetectapi(String detectapi) {
		this.detectapi = detectapi;
	}

	public String getCompareapi() {
		return compareapi;
	}

	public void setCompareapi(String compareapi) {
		this.compareapi = compareapi;
	}

	public String getApikey() {
		return apikey;
	}

	public void setApikey(String apikey) {
		this.apikey = apikey;
	}

	public String getApisecret() {
		return apisecret;
	}

	public void setApisecret(String apisecret) {
		this.apisecret = apisecret;
	}

}
