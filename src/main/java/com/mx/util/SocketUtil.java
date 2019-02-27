package com.mx.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
public class SocketUtil {

    /**
     * 发送socket请求
     * @param clientIp
     * @param clientPort
     * @param msg
     * @return
     */
    public static synchronized String socketPost(String clientIp,String clientPort,String msg){
        String rs = "";
        try {
            // 要连接的服务端IP地址和端口
            // 与服务端建立连接
            Socket socket = new Socket(clientIp, Integer.parseInt(clientPort));
            // 建立连接后获得输出流
            OutputStream outputStream = socket.getOutputStream();
            socket.getOutputStream().write(msg.getBytes("GBK"));
            //通过shutdownOutput高速服务器已经发送完数据，后续只能接受数据
            socket.shutdownOutput();

            InputStream inputStream = socket.getInputStream();
            byte[] bytes = new byte[1024];
            int len;
            StringBuilder sb = new StringBuilder();
            while ((len = inputStream.read(bytes)) != -1) {
                //注意指定编码格式，发送方和接收方一定要统一，建议使用UTF-8
                sb.append(new String(bytes, 0, len,"GBK"));
            }
            rs = sb.toString();
            inputStream.close();
            outputStream.close();
            socket.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return rs;

    }

    /**
     * 读取输入流
     * @param in
     * @return
     */
    private static byte[] readStream(InputStream in) throws Exception {
        if(in==null){
            return null;
        }

        byte[] b = null;
        ByteArrayOutputStream outSteam = null;
        try {
            byte[] buffer = new byte[1024];
            outSteam = new ByteArrayOutputStream();

            int len = -1;
            while ((len = in.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }

            b = outSteam.toByteArray();
        } catch (IOException e) {
            throw new Exception("读取流信息异常:"+e.getMessage());
        } finally{
            try {
                if(outSteam!=null){
                    outSteam.close();
                    outSteam = null;
                }
                if(in!=null){
                    in.close();
                    in = null;
                }
            } catch (IOException e) {
            }
        }
        return b;
    }

}
