package com.sqs.zxingcode;

import java.awt.Color;

public class LogoConfig {
	
	public static final Color DEFAULT_BORDERCOLOR = Color.WHITE;//logo默認邊框顏色
	public static final int DEFAULT_BORDER = 2;//logo默認邊框寬度
	public static final int DEFAULT_LOGOPART = 5;//logo大小默認圖片的1/5
	private final int border = DEFAULT_BORDER;//邊框寬度
	private final Color borderColor;//邊框顏色
	private final int logoPart;//邊框外圍寬度

	public LogoConfig() {
		this(DEFAULT_BORDERCOLOR, DEFAULT_LOGOPART);
	}

	public LogoConfig(Color borderColor, int logoPart) {
		this.borderColor = borderColor;
		this.logoPart = logoPart;
	}

	public int getBorder() {
		return border;
	}
	public Color getBorderColor() {
		return borderColor;
	}
	public int getLogoPart() {
		return logoPart;
	}
		    

}