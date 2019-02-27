package com.mx.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FtpServerUtil {
	public Logger logger = LoggerFactory.getLogger(this.getClass());
	private static FTPClient ftpClient;
    private final static String STREN_CODING = "UTF-8";
    
	/**
     * @param ip
     * @param port
     * @param userName
     * @param userPwd
     * @param path
     * @throws java.net.SocketException
     * @throws java.io.IOException     function:连接到服务器
     */
    public static boolean connectServer(String ip, int port, String userName, String userPwd, String path) {
        ftpClient = new FTPClient();
        try {
            int reply;
            ftpClient.connect(ip, port);
            ftpClient.login(userName, userPwd);
            ftpClient.setControlEncoding("GBK");
            FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_UNIX);
            conf.setServerLanguageCode("zh");
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                return true;
            }
            /*// 连接
            /*ftpClient.connect(ip, port);
            // 登录
            ftpClient.login(userName, userPwd);*//*
            if (path != null && path.length() > 0) {
                // 跳转到指定目录
                ftpClient.changeWorkingDirectory(path);
            }*/
            return true;
        } catch (SocketException e) {
        	e.printStackTrace();
            return false;
        } catch (IOException e) {
        	e.printStackTrace();
            return false;
        }
    }

    /**
     * @throws java.io.IOException function:关闭连接
     */
    public static void closeServer() {
        if (ftpClient.isConnected()) {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (IOException e) {
            	e.printStackTrace();
            }
        }
    }

    /**
     * @param path
     * @return function:读取指定目录下的文件名
     * @throws java.io.IOException
     */
    public static FTPFile[] getFileList(String path) {
        // 获得指定目录下所有文件名
        FTPFile[] ftpFiles = null;
        try {
            ftpFiles = ftpClient.listFiles(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ftpFiles == null ? new FTPFile[0] : ftpFiles;
    }

    /**
     * @return function:从服务器上读取指定的文件
     */
    public static List<String> readFileFromFTP(String fileName, String endString) {
        InputStream ins = null;
        List<String> list = new ArrayList<String>();
        try {
            // 从服务器上读取指定的文件
            // 获取到本服务器的制定位置,并从txt转化成excel
            ins = ftpClient.retrieveFileStream(fileName);
            if(ins == null){
                 return list;
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(ins, STREN_CODING));
            String line = null;
            while ((line = reader.readLine()) != null) {
                if(endString.equals(line)){
                    break;
                } else {
                    list.add(line);
                }
            }
            reader.close();
            if (ins != null) {
                ins.close();
            }
            // 主动调用一次getReply()把接下来的226消费掉. 这样做是可以解决这个返回null问题
            ftpClient.getReply();
        } catch (IOException e) {
        	e.printStackTrace();
        }
        return list;
    }

    /**
     * @param fileName function:删除文件
     */
    public static void deleteFile(String fileName) {
        try {
            ftpClient.deleteFile(fileName);
        } catch (IOException e) {
        	e.printStackTrace();
        }
    }

    public static boolean downFile(String ftpPath, String targetPath, String fileName, String fileType){
        FTPFile[] ftpFiles = getFileList(ftpPath);
        String targetFileName = StringUtil.isNullOrEmpty(fileType) ? fileName : fileName.substring(0,fileName.indexOf(fileType));
        File targetFile = new File(targetPath);
        if (!targetFile.exists()) {// 文件不存在则创建
        	targetFile.mkdirs();
        }
        boolean isFileExist = false;
        try{
            for(FTPFile ftpFile : ftpFiles){
                byte[] bytes = ftpFile.getName().getBytes("iso-8859-1");
                String fn = new String(bytes,"utf-8");
                if(fn.equals(fileName)){
                	isFileExist = true;
                    File localFile = new File(FileUtil.contact(targetPath, targetFileName));
                    OutputStream out = new FileOutputStream(localFile);
                    ftpClient.retrieveFile(fileName, out);
                    out.close();
                }
            }
        } catch (IOException e) {
        	e.printStackTrace();
        }
        return isFileExist;
    }

}
