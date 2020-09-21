package com.sqs.zxingcode;

import java.util.Map;
import com.google.zxing.BarcodeFormat;

public class ZxingConfig {
	
	private String content;//二維碼內容
	private BarcodeFormat barcodeformat = BarcodeFormat.QR_CODE;//編碼類型 
	private int width = 300;//生成圖片寬度
	private int height = 300;//生成圖片高度
	private Map hints;//設置參數
	private String putPath;//圖片輸出路徑
	private boolean logoFlg = false;//是否添加logo圖片
	private boolean logoType = false;//是否自定義logo
	private LogoConfig LogoConfig;//logo圖片參數
	private String logoPath;//logo圖片路徑
	private byte[] logoByte;//不使用默認logo時傳入圖片流數據
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public BarcodeFormat getBarcodeformat() {
		return barcodeformat;
	}
	public void setBarcodeformat(BarcodeFormat barcodeformat) {
		this.barcodeformat = barcodeformat;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public Map getHints() {
		return hints;
	}
	public void setHints(Map hints) {
		this.hints = hints;
	}
	public String getPutPath() {
		return putPath;
	}
	public void setPutPath(String putPath) {
		this.putPath = putPath;
	}
	public boolean isLogoFlg() {
		return logoFlg;
	}
	public void setLogoFlg(boolean logoFlg) {
		this.logoFlg = logoFlg;
	}
	public boolean isLogoType() {
		return logoType;
	}
	public void setLogoType(boolean logoType) {
		this.logoType = logoType;
	}
	public LogoConfig getLogoConfig() {
		return LogoConfig;
	}
	public void setLogoConfig(LogoConfig logoConfig) {
		LogoConfig = logoConfig;
	}
	public String getLogoPath() {
		return logoPath;
	}
	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}
	public byte[] getLogoByte() {
		return logoByte;
	}
	public void setLogoByte(byte[] logoByte) {
		this.logoByte = logoByte;
	}	

}