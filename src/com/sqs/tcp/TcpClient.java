package com.sqs.tcp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by SQS19911128 on 2020/12/29.
 */
public class TcpClient {

    private static String host = "localhost";// 默认连接到本机
    private static int port = 8189;// 默认连接到端口8189
    final static Logger logger = LoggerFactory.getLogger(TcpClient.class);

    public static void main(String[] args) throws Exception {
        chat();
    }

    public static void chat() throws Exception {

        Socket socket = null;
        DataOutputStream outputStream = null;
        DataInputStream inputStream = null;
        try {
            //1. 创建客户端对象
            socket = new Socket(host,port);
            //2. 获取字节输出流对象
            outputStream = new DataOutputStream(socket.getOutputStream());
            //3. 获取字节输入流对象
            inputStream = new DataInputStream(socket.getInputStream());
            //4. 数据交互
            Scanner scan=new Scanner(System.in);//从键盘接受数据
            while(true){
                if (scan.hasNextLine()) {
                    //4.1 向服务器发送数据
                    String request=scan.nextLine();//这是输入request
                    outputStream.writeUTF(request);
                    outputStream.flush();
                }
                //4.2 读取服务器发送的数据
                String result = inputStream.readUTF();
                //System.out.println(result);
                logger.info(result);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw e;
        } finally {
            //5. 释放资源
            if(null != outputStream){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    logger.error("输出流关闭异常"+e);
                }
            }
            if(null != inputStream){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error("输入流关闭异常"+e);
                }
            }
            if(null != socket){
                try {
                    socket.close();
                } catch (IOException e) {
                    logger.error("socket关闭异常"+e);
                }
            }
        }
    }
}
