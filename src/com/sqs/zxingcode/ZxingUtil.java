package com.sqs.zxingcode;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class ZxingUtil {

	final Logger logger = LoggerFactory.getLogger(ZxingUtil.class);
	
    /**
     * 條形碼 圖片添加文字
     * @param image  條形碼图片 BufferedImage
     * @param words  文字
     * @param imgPath 輸出圖片路徑
     */
    public BufferedImage encodeBarCodeWords(BufferedImage image, String words, File imgPath){
        	
        BufferedImage outImage = null;
        try {
        	outImage = new BufferedImage(image.getWidth(), image.getHeight()+40, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = outImage.createGraphics();

            //抗锯齿
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_DEFAULT);

            //白色填充整个屏幕
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0,0,image.getWidth(),image.getHeight()+40);
            
            //画条形码到新的面板
            g2d.drawImage(image, 0, 10, image.getWidth(), image.getHeight(), null);
            
            //画文字到新的面板   黑色、字体、字型、字号
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("微软雅黑", Font.PLAIN, 18));
            //文字长度
            int strWidth = g2d.getFontMetrics().stringWidth(words);
            //总长度减去文字长度的一半  （居中显示）
            int wordStartX=(image.getWidth() - strWidth) / 2;
            int wordStartY=image.getHeight()+30;
            // 画文字
            g2d.drawString(words, wordStartX, wordStartY);
            
            g2d.dispose();
            outImage.flush();
            ImageIO.write(outImage, "png", imgPath);
        } catch (Exception e) {
        	logger.error(e.getMessage());
        }
        return outImage;
    }


	/**
     * 二維碼圖片添加logo
     * @param bim 二維碼圖片 BufferedImage
     * @param logoPic Logo圖片路徑
     * @param imgPath 輸出圖片路徑
     */
    public BufferedImage encodeQRCodeLogo(BufferedImage image, File logoPic, File imgPath){
            
    	BufferedImage outImage = null;
        try {
        	outImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = outImage.createGraphics();
            
            //抗锯齿
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_DEFAULT);
 
            //白色填充整个屏幕
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0,0,image.getWidth(),image.getHeight());
            
            //画二維碼到新的面板
            g2d.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
            
            //讀取logo圖片
            BufferedImage logo = ImageIO.read(logoPic);

            //設置logo大小（二維碼圖片的20%）
            int widthLogo = logo.getWidth() > image.getWidth() * 2 / 10 ? (image.getWidth() * 2 / 10) : logo.getWidth();
            int	heightLogo = logo.getHeight() > image.getHeight() * 2 / 10 ? (image.getHeight() * 2 / 10) : logo.getHeight();
            
            //压缩logo
            Image logos = logo.getScaledInstance(widthLogo, heightLogo, Image.SCALE_SMOOTH);
            
            //logo圖片放置位置 中心位置
            int x = (image.getWidth() - widthLogo) / 2;
            int y = (image.getHeight() - heightLogo) / 2;
            
            //画logo到新的面板
            g2d.drawImage(logos, x, y, widthLogo, heightLogo, null);
            
            //设置笔画对象 绘制矩形  
            g2d.setStroke(new BasicStroke(5,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
            g2d.setColor(Color.WHITE);
            g2d.drawRoundRect(x, y, widthLogo, heightLogo, 20, 20);//绘制圆弧矩形  
//            g2d.drawRect(x, y, widthLogo, heightLogo);//绘制方形矩形 
            
            //设置笔画对象 绘制logo灰色边框矩形 
            g2d.setStroke(new BasicStroke(1,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));  
            g2d.setColor(Color.GRAY);
            g2d.drawRoundRect(x+2, y+2, widthLogo-4, heightLogo-4, 20, 20);//绘制圆弧矩形 
//           g2d.drawRect(x+2, y+2, widthLogo-4, heightLogo-4);//绘制方形矩形 

            g2d.dispose();
            logo.flush();
            outImage.flush();
            ImageIO.write(outImage, "png", imgPath);
        } catch (Exception e) {
        	logger.error(e.getMessage());
        }
        return outImage;
    }
 
    /**
     * 生成 二維碼/條形碼 BufferedImage
     * @param zxingconfig
     */
    public BufferedImage encodeQRCode_BarCodeBufferedImage(ZxingConfig zxingconfig){

        BufferedImage outImage = null;
        try {
        	BitMatrix bitMatrix = new MultiFormatWriter().encode(zxingconfig.getContent(), zxingconfig.getBarcodeformat(), zxingconfig.getWidth(), zxingconfig.getHeight(), zxingconfig.getHints());
        	outImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        } catch (Exception e) {
        	logger.error(e.getMessage());
        }
        return outImage;
    }
    
	 /**
     * 生成 二維碼/條形碼
     * @param contents
     * @param width
     * @param height
     * @param imgPath
     * @param codeType
     */
    public void encodeQRCode_BarCode(String contents, int width, int height, String imgPath, String codeType) {
        Map hints = CreateDecodeHintType();
        try {
        	//0:二維碼(BarcodeFormat.QR_CODE)   1:條形碼(BarcodeFormat.CODE_128)
        	BitMatrix bitMatrix = null;
        	if("0".equals(codeType)){
        		bitMatrix = new MultiFormatWriter().encode(contents, BarcodeFormat.QR_CODE, width, height, hints);
        	}else if("1".equals(codeType)){
        		bitMatrix = new MultiFormatWriter().encode(contents, BarcodeFormat.CODE_128, width, height, hints);
        	}else{
        		System.out.println("the codeType may be not exit.");
        	}
            MatrixToImageWriter.writeToStream(bitMatrix, "png", new FileOutputStream(imgPath));
        } catch (Exception e) {
        	logger.error(e.getMessage());
        }
    }

    /**
     * 解析  二維碼/條形碼
     * @param imgPath
     * @return
     */
    public String decodeQRCode_BarCode(String imgPath) {
        BufferedImage image = null;
        Result result = null;
        try {
            image = ImageIO.read(new File(imgPath));
            if (image == null) {
                System.out.println("the decode image may be not exit.");
            }
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            Map hints = new HashMap();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");//設置編碼

            result = new MultiFormatReader().decode(bitmap, hints);
            return result.getText();
        } catch (Exception e) {
        	logger.error(e.getMessage());
        }
        return null;
    }
 
    /**
     * 設置編碼參數
     */
	public Map CreateDecodeHintType(){
		
        Map hints = new HashMap();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);//設置糾錯級別（H最高級）
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");//設置編碼
        hints.put(EncodeHintType.MARGIN,2);
        
        return hints;
	}
	
}