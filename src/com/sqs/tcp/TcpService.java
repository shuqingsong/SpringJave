package com.sqs.tcp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by SQS19911128 on 2020/12/29.
 */
public class TcpService {

    private static int port = 8189;// 默认连接到端口8189
    final static Logger logger = LoggerFactory.getLogger(TcpService.class);

    public static void main(String[] args) throws Exception {
        service();
    }

    public static void service() throws Exception {

        ServerSocket serverSocket = null;
        Socket socket = null;
        DataInputStream inputStream = null;
        DataOutputStream outputStream = null;
        try {
            //1. 创建服务器ServerSocket对象
            serverSocket = new ServerSocket(port);
            //2. 获取客户端请求对象Socket
            socket = serverSocket.accept();
            //3. 获取字节输入流对象
            inputStream = new DataInputStream(socket.getInputStream());
            //4. 获取字节输出流对象
            outputStream = new DataOutputStream(socket.getOutputStream());
            //5. 数据交互
            Scanner scan=new Scanner(System.in);//从键盘接受数据
            while (true){
                //5.1 读取客户端数据
                String result = inputStream.readUTF();
                //System.out.println(result);
                logger.info(result);
                if (scan.hasNextLine()) {
                    //5.2 向客户端回写数据
                    String response=scan.nextLine();//这是输入response
                    outputStream.writeUTF(response);
                    outputStream.flush();
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw e;
        } finally {
            //6. 释放资源
            if(null != inputStream){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error("输入流关闭异常"+e);
                }
            }
            if(null != outputStream){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    logger.error("输出流关闭异常"+e);
                }
            }
            if(null != socket){
                try {
                    socket.close();
                } catch (IOException e) {
                    logger.error("socket关闭异常"+e);
                }
            }
            if(null != serverSocket){
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    logger.error("serverSocket关闭异常"+e);
                }
            }
        }
    }
}
