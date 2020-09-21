package com.sqs.maps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class MapsUtil {

	final Logger logger = LoggerFactory.getLogger(MapsUtil.class);
	
	String aliyunKey;
	String aliyunApi1;
	String aliyunApi2;
	
	String tenxunKey;
	String tenxunApi1;
	String tenxunApi2;
	
	String baiduKey;
	String baiduApi1;
	String baiduApi2;
	
	/**
	  * (阿里)高德根据经纬度获取地区名接口：
	  * https://restapi.amap.com/v3/geocode/regeo?key=%s&location=%s,%s&output=json&radius=1000&extensions=all //GET请求
	  * key:用户申请注册的key(a31342ce42091e4057fc5b2a168aa0ef)
	  * location:待解析的经度,纬度(经度前，纬度后)
	  * output:可以是xml或json
	  * radius:搜索半径  radius取值范围在0~3000，默认是1000,单位：米  可选
	  * extensions:返回结果控制  默认取值是 base，也就是返回基本地址信息；取值为 all 时会返回基本地址信息、附近 POI 内容、道路信息以及道路交叉口信息。 可选
	  * 例子https://restapi.amap.com/v3/geocode/regeo?key=<用户的key>&location=116.310003,39.991957&output=json&radius=1000&extensions=all  //GET请求
	  */
	 /**
	  * 调用(阿里)高德地图API获取位置 
	  * @param lat 纬度
	  * @param lng 经度
	  * @return
	  */
	public String aliyunGetAdd(String lat,String lng) {
	
        String res = "";
//        String ak = "a31342ce42091e4057fc5b2a168aa0ef";
//        String url = String .format("https://restapi.amap.com/v3/geocode/regeo?key=%s&location=%s,%s&output=json&radius=1000&extensions=all",ak,lng,lat);
        String url = String .format(aliyunApi1,aliyunKey,lng,lat);
        try {
        	URL myURL = new URL(url); 
            URLConnection  httpsConn = (URLConnection) myURL.openConnection();// 不使用代理 
            if (httpsConn != null) { 
            	BufferedReader br = new BufferedReader(new InputStreamReader( httpsConn.getInputStream(), "UTF-8")); 
                String line = "";
    			while((line = br.readLine())!= null) {
    				res += line+"\n";
    			}
    			br.close();
            } 
        } catch (Exception e) { 
        	logger.error(e.getMessage());
            return null;
        }
		JSONObject jsonObject = JSONObject.parseObject(res);
		if("1".equals(jsonObject.getString("status"))){
			JSONObject regeocode=JSONObject.parseObject(jsonObject.getString("regeocode"));
			JSONObject addressComponent=JSONObject.parseObject(regeocode.getString("addressComponent"));
			String formatted_address=regeocode.getString("formatted_address");
			System.out.println("高德地图："+formatted_address);
			return formatted_address;
		}else{
			return null;
		}
	}

	/**
	  * (阿里)高德根据地区名获取经纬度接口：
	  * https://restapi.amap.com/v3/geocode/geo?key=%s&address=%s&output=json //GET请求
	  * key:用户申请注册的key(a31342ce42091e4057fc5b2a168aa0ef)
	  * address:待解析的地址
	  * output:可以是xml或json
	  * 例子https://restapi.amap.com/v3/geocode/geo?key=<用户的key>&address=北京市朝阳区阜通东大街6号&output=json  //GET请求
	  */
    /** 
     * 调用(阿里)高德地图API获取经纬度 
     * @param addr 地址 
     * @return 
     */
    public String[] aliyunGetLngAndLat(String addr) { 
    	
    	String res = "";
        String address = ""; 
        try { 
            address = java.net.URLEncoder.encode(addr, "UTF-8"); 
        }catch (UnsupportedEncodingException e) { 
        	logger.error(e.getMessage());
            return null;
        }
//        String key = "a31342ce42091e4057fc5b2a168aa0ef";
//        String url = String .format("https://restapi.amap.com/v3/geocode/geo?key=%s&address=%s&output=json",key,address);
        String url = String .format(aliyunApi2,aliyunKey,address);
        try { 
        	URL myURL = new URL(url); 
        	URLConnection httpsConn = (URLConnection) myURL.openConnection();// 不使用代理 
            if (httpsConn != null) { 
            	BufferedReader br = new BufferedReader(new InputStreamReader( httpsConn.getInputStream(), "UTF-8"));
                String line = "";
    			while((line = br.readLine())!= null) {
    				res += line+"\n";
    			}
    			br.close();
            } 
        } catch (Exception e) { 
        	logger.error(e.getMessage());
            return null;
        } 
		JSONObject jsonObject = JSONObject.parseObject(res);
		if("1".equals(jsonObject.getString("status"))){
			JSONArray geocodes=JSONObject.parseArray(jsonObject.getString("geocodes"));
			String location=((JSONObject)geocodes.get(0)).getString("location");
			String lat=location.split(",")[1];//纬度
			String lng=location.split(",")[0];//经度
			System.out.println("高德地图："+lat+","+lng);
			return new String[]{lat,lng};
		}else{
			return null;
		}
    }

	/**
	  * 腾讯根据经纬度获取地区名接口：
	  * https://apis.map.qq.com/ws/geocoder/v1/?key=%s&location=%s,%s&output=json&get_poi=1 //GET请求
	  * key:用户申请注册的key(FPRBZ-IQWKO-7NCWI-SMO6L-3MNT7-ZLFY2)
	  * location:待解析的纬度,经度(纬度前，经度后)
	  * output:支持JSON/JSONP，默认JSON
	  * get_poi：是否返回周边POI列表 1.返回；0不返回(默认)
	  * 例子https://apis.map.qq.com/ws/geocoder/v1/?key=<用户的key>&location=39.984154,116.307490&output=json&get_poi=1  //GET请求
	  */
	 /**
	  * 调用腾讯地图API获取位置 
	  * @param lat 纬度
	  * @param lng 经度
	  * @return
	  */
	public String tenxunGetAdd(String lat,String lng) {
	
        String res = "";
//        String ak = "FPRBZ-IQWKO-7NCWI-SMO6L-3MNT7-ZLFY2"; 
//        String url = String .format("https://apis.map.qq.com/ws/geocoder/v1/?key=%s&location=%s,%s&output=json&get_poi=1",ak,lat,lng);
        String url = String .format(tenxunApi1,tenxunKey,lat,lng);
        try {
        	URL myURL = new URL(url); 
            URLConnection  httpsConn = (URLConnection) myURL.openConnection();// 不使用代理 
            if (httpsConn != null) { 
            	BufferedReader br = new BufferedReader(new InputStreamReader( httpsConn.getInputStream(), "UTF-8")); 
                String line = "";
    			while((line = br.readLine())!= null) {
    				res += line+"\n";
    			}
    			br.close();
            } 
        } catch (Exception e) { 
        	logger.error(e.getMessage());
            return null;
        }
		JSONObject jsonObject = JSONObject.parseObject(res);
		if("0".equals(jsonObject.getString("status"))){
			JSONObject result=JSONObject.parseObject(jsonObject.getString("result"));
			JSONObject formatted_addresses=JSONObject.parseObject(result.getString("formatted_addresses"));
			JSONObject addressComponent=JSONObject.parseObject(result.getString("address_component"));
			String address=result.getString("address");
			System.out.println("腾讯地图："+address);
			return address;
		}else{
			return null;
		}
	}
		
	/**
	  * 腾讯根据地区名获取经纬度接口：
	  * https://apis.map.qq.com/ws/geocoder/v1/?key=%s&address=%s&output=json //GET请求
	  * key:用户申请注册的key(FPRBZ-IQWKO-7NCWI-SMO6L-3MNT7-ZLFY2)
	  * address:待解析的地址
	  * output:支持JSON/JSONP，默认JSON
	  * 例子https://apis.map.qq.com/ws/geocoder/v1/?key=<用户的key>&address=北京市海淀区彩和坊路海淀西大街74号&output=json //GET请求
	  */
    /** 
     * 调用腾讯地图API获取经纬度 
     * @param addr 地址 
     * @return 
     */
    public String[] tenxunGetLngAndLat(String addr) { 
    	
    	String res = "";
        String address = ""; 
        try { 
            address = java.net.URLEncoder.encode(addr, "UTF-8"); 
        }catch (UnsupportedEncodingException e) { 
        	logger.error(e.getMessage());
            return null;
        }
//        String key = "FPRBZ-IQWKO-7NCWI-SMO6L-3MNT7-ZLFY2";
//        String url = String .format("https://apis.map.qq.com/ws/geocoder/v1/?key=%s&address=%s&output=json",key,address);
        String url = String .format(tenxunApi2,tenxunKey,address);
        try { 
        	URL myURL = new URL(url); 
        	URLConnection httpsConn = (URLConnection) myURL.openConnection();// 不使用代理 
            if (httpsConn != null) { 
            	BufferedReader br = new BufferedReader(new InputStreamReader( httpsConn.getInputStream(), "UTF-8"));
                String line = "";
    			while((line = br.readLine())!= null) {
    				res += line+"\n";
    			}
    			br.close();
            } 
        } catch (Exception e) { 
        	logger.error(e.getMessage());
            return null;
        } 
		JSONObject jsonObject = JSONObject.parseObject(res);
		if("0".equals(jsonObject.getString("status"))){
			JSONObject result=JSONObject.parseObject(jsonObject.getString("result"));
			JSONObject location=JSONObject.parseObject(result.getString("location"));
			String lat=location.getString("lat");//纬度
			String lng=location.getString("lng");//经度
			System.out.println("腾讯地图："+lat+","+lng);
			return new String[]{lat,lng};
		}else{
			return null;
		}
    }
    
	/**
	  * 百度根据经纬度获取地区名接口：
	  * http://api.map.baidu.com/reverse_geocoding/v3/?ak=%s&location=%s,%s&output=json&coordtype=wgs84ll //GET请求
	  * ak:用户申请注册的key(qLIGWA7hqu7zdfDCloGmGhQP2C8VPp3T)
	  * location:待解析的纬度,经度(纬度前，经度后)
	  * output:可以是xml或json
	  * coordtype:坐标的类型，目前支持的坐标类型包括：bd09ll（百度经纬度坐标）、bd09mc（百度米制坐标）、gcj02ll（国测局经纬度坐标，仅限中国）、wgs84ll（ GPS经纬度）
	  * 例子http://api.map.baidu.com/reverse_geocoding/v3/?ak=<用户的key>&location=31.225696563611,121.49884033194&output=json&coordtype=wgs84ll  //GET请求
	  */
	 /**
	  * 调用百度地图API获取位置 
	  * @param lat 纬度
	  * @param lng 经度
	  * @return
	  */
	public String baiduGetAdd(String lat,String lng) {
	
        String res = "";
//        String ak = "qLIGWA7hqu7zdfDCloGmGhQP2C8VPp3T"; 
//        String url = String .format("http://api.map.baidu.com/reverse_geocoding/v3/?ak=%s&location=%s,%s&output=json&coordtype=wgs84ll",ak,lat,lng);
        String url = String .format(baiduApi1,baiduKey,lat,lng);
        try {
        	URL myURL = new URL(url); 
            URLConnection  httpsConn = (URLConnection) myURL.openConnection();// 不使用代理 
            if (httpsConn != null) { 
            	BufferedReader br = new BufferedReader(new InputStreamReader( httpsConn.getInputStream(), "UTF-8")); 
                String line = "";
    			while((line = br.readLine())!= null) {
    				res += line+"\n";
    			}
    			br.close();
            } 
        } catch (Exception e) { 
        	logger.error(e.getMessage());
            return null;
        }
		JSONObject jsonObject = JSONObject.parseObject(res);
		if("0".equals(jsonObject.getString("status"))){
			JSONObject result=JSONObject.parseObject(jsonObject.getString("result"));
			JSONObject addressComponent=JSONObject.parseObject(result.getString("addressComponent"));
			String formatted_address=result.getString("formatted_address");
			System.out.println("百度地图："+formatted_address);
			return formatted_address;
		}else{
			return null;
		}
	}
		
	/**
	  * 百度根据地区名获取经纬度接口：
	  * http://api.map.baidu.com/geocoding/v3/?ak=%s&address=%s&output=json //GET请求
	  * ak:用户申请注册的key(qLIGWA7hqu7zdfDCloGmGhQP2C8VPp3T)
	  * address:待解析的地址
	  * output:可以是xml或json
	  * 例子http://api.map.baidu.com/geocoding/v3/?ak=<用户的key>&address=北京市海淀区上地十街10号&output=json //GET请求
	  */
    /** 
     * 调用百度地图API获取经纬度 
     * @param addr 地址 
     * @return 
     */
    public String[] baiduGetLngAndLat(String addr) { 
    	
    	String res = "";
        String address = ""; 
        try { 
            address = java.net.URLEncoder.encode(addr, "UTF-8"); 
        }catch (UnsupportedEncodingException e) { 
        	logger.error(e.getMessage());
            return null;
        }
//        String key = "qLIGWA7hqu7zdfDCloGmGhQP2C8VPp3T";
//        String url = String .format("http://api.map.baidu.com/geocoding/v3/?ak=%s&address=%s&output=json",key,address);
        String url = String .format(baiduApi2,baiduKey,address);
        try { 
        	URL myURL = new URL(url); 
        	URLConnection httpsConn = (URLConnection) myURL.openConnection();// 不使用代理 
            if (httpsConn != null) { 
            	BufferedReader br = new BufferedReader(new InputStreamReader( httpsConn.getInputStream(), "UTF-8"));
                String line = "";
    			while((line = br.readLine())!= null) {
    				res += line+"\n";
    			}
    			br.close();
            } 
        } catch (Exception e) { 
        	logger.error(e.getMessage());
            return null;
        } 
		JSONObject jsonObject = JSONObject.parseObject(res);
		if("0".equals(jsonObject.getString("status"))){
			JSONObject result=JSONObject.parseObject(jsonObject.getString("result"));
			JSONObject location=JSONObject.parseObject(result.getString("location"));
			String lat=location.getString("lat");//纬度
			String lng=location.getString("lng");//经度
			System.out.println("百度地图："+lat+","+lng);
			return new String[]{lat,lng};
		}else{
			return null;
		}
    }

	public String getAliyunKey() {
		return aliyunKey;
	}

	public void setAliyunKey(String aliyunKey) {
		this.aliyunKey = aliyunKey;
	}

	public String getAliyunApi1() {
		return aliyunApi1;
	}

	public void setAliyunApi1(String aliyunApi1) {
		this.aliyunApi1 = aliyunApi1;
	}

	public String getAliyunApi2() {
		return aliyunApi2;
	}

	public void setAliyunApi2(String aliyunApi2) {
		this.aliyunApi2 = aliyunApi2;
	}

	public String getTenxunKey() {
		return tenxunKey;
	}

	public void setTenxunKey(String tenxunKey) {
		this.tenxunKey = tenxunKey;
	}

	public String getTenxunApi1() {
		return tenxunApi1;
	}

	public void setTenxunApi1(String tenxunApi1) {
		this.tenxunApi1 = tenxunApi1;
	}

	public String getTenxunApi2() {
		return tenxunApi2;
	}

	public void setTenxunApi2(String tenxunApi2) {
		this.tenxunApi2 = tenxunApi2;
	}

	public String getBaiduKey() {
		return baiduKey;
	}

	public void setBaiduKey(String baiduKey) {
		this.baiduKey = baiduKey;
	}

	public String getBaiduApi1() {
		return baiduApi1;
	}

	public void setBaiduApi1(String baiduApi1) {
		this.baiduApi1 = baiduApi1;
	}

	public String getBaiduApi2() {
		return baiduApi2;
	}

	public void setBaiduApi2(String baiduApi2) {
		this.baiduApi2 = baiduApi2;
	}

}