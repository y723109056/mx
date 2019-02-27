package com.mx.util;

import sun.net.TelnetInputStream;
import sun.net.TelnetOutputStream;
import sun.net.ftp.FtpClient;
import sun.net.ftp.FtpProtocolException;

import java.io.*;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author 小米线儿
 * @time 2019/2/12 0012
 * @QQ 723109056
 * @blog https://blog.csdn.net/qq_31407255
 * ftp上传，下载
 */
public class FtpUtil {

    private String ip = "";

    private String username = "";

    private String password = "";

    private int port = -1;

    private String path = "";

    FtpClient ftpClient = null;

    OutputStream os = null;

    FileInputStream is = null;

    public FtpUtil(String serverIP, String username, String password) {
        this.ip = serverIP;
        this.username = username;
        this.password = password;
    }

    public FtpUtil(String serverIP, int port, String username, String password) {
        this.ip = serverIP;
        this.username = username;
        this.password = password;
        this.port = port;
    }

    /**
     * 连接ftp服务器
     *
     * @throws java.io.IOException
     */
    public boolean connectServer() {

        try {
            ftpClient = FtpClient.create();
            if(port == -1) {
                ftpClient.connect(new InetSocketAddress(this.ip, 21));
            } else {
                ftpClient.connect(new InetSocketAddress(this.ip, this.port));
            }
            ftpClient.login(this.username,this.password.toCharArray() );
            if (this.path.length() != 0){
                ftpClient.changeDirectory(this.path);// path是ftp服务下主目录的子目录
            }
            ftpClient.setBinaryType();// binary();// 用2进制上传、下载
            System.out.println(ftpClient.getWelcomeMsg()+",已登录到\"" + ftpClient.getWorkingDirectory() + "\"目录");
            return true;
        }catch (IOException e){
            return false;
        } catch (FtpProtocolException e) {
            return false;
        }
    }

    /**
     * 断开与ftp服务器连接
     *
     * @throws java.io.IOException
     */
    public boolean closeServer(){
        try{
            if (is != null) {
                is.close();
            }
            if (os != null) {
                os.close();
            }
            if (ftpClient != null) {
                ftpClient.close();
            }
            System.out.println("已从服务器断开");
            return true;
        }catch(IOException e){
            return false;
        }
    }

    /**
     * 检查文件夹在当前目录下是否存在
     * @param dir
     * @return
     */
    private boolean isDirExist(String dir){
        String pwd = "";
        try {
            pwd = ftpClient.getWorkingDirectory();
            ftpClient.changeDirectory(dir);
            ftpClient.changeDirectory(pwd);
        }catch(Exception e){
            return false;
        }
        return true;
    }

    /**
     * 在当前目录下创建文件夹
     * @param dir
     * @return
     * @throws Exception
     */
    private boolean createDir(String dir){
        try{
            ftpClient.setAsciiType();
            StringTokenizer s = new StringTokenizer(dir, "/"); //sign
            s.countTokens();
            String pathName = ftpClient.getWorkingDirectory();
            while(s.hasMoreElements()){
                pathName = pathName + "/" + (String) s.nextElement();
                ftpClient.makeDirectory(pathName);
                ftpClient.getLastResponseString();
            }
            ftpClient.setBinaryType();
            return true;
        }catch (IOException e){
            return false;
        } catch (FtpProtocolException e) {
            return false;
        }
    }

    /**
     * ftp上传
     * 如果服务器段已存在名为filename的文件夹，该文件夹中与要上传的文件夹中同名的文件将被替换
     *
     * @param filename 要上传的文件（或文件夹）名
     * @return
     * @throws Exception
     */
    public boolean upload(String filename){
        String newname = "";
        if(filename.indexOf("/") > -1){
            newname = filename.substring(filename.lastIndexOf("/") + 1);
        }else{
            newname = filename;
        }
        return upload(filename, newname);
    }

    /**
     * ftp上传
     * 如果服务器段已存在名为newName的文件夹，该文件夹中与要上传的文件夹中同名的文件将被替换
     *
     * @param fileName 要上传的文件（或文件夹）名
     * @param newName 服务器段要生成的文件（或文件夹）名
     * @return
     */
    public boolean upload(String fileName, String newName){
        try{
            String savefilename = new String(fileName.getBytes("ISO-8859-1"), "GBK");
            File file_in = new File(savefilename);//打开本地待长传的文件
            if(!file_in.exists()){
                throw new Exception("此文件或文件夹[" + file_in.getName() + "]有误或不存在!");
            }
            if(file_in.isDirectory()){
                upload(file_in.getPath(),newName,ftpClient.getWorkingDirectory());
            }else{
                uploadFile(file_in.getPath(),newName);
            }

            if(is != null){
                is.close();
            }
            if(os != null){
                os.close();
            }
            return true;
        }catch(Exception e){
            return false;
        }finally{
            try{
                if(is != null){
                    is.close();
                }
                if(os != null){
                    os.close();
                }
            }catch(IOException e){
            }
        }
    }

    /**
     * 真正用于上传的方法
     * @param fileName
     * @param newName
     * @param path
     * @throws Exception
     */
    private void upload(String fileName, String newName,String path) throws Exception{
        String savefilename = new String(fileName.getBytes("ISO-8859-1"), "GBK");
        File file_in = new File(savefilename);//打开本地待长传的文件
        if(!file_in.exists()){
            throw new Exception("此文件或文件夹[" + file_in.getName() + "]有误或不存在!");
        }
        if(file_in.isDirectory()){
            if(!isDirExist(newName)){
                createDir(newName);
            }
            ftpClient.changeDirectory(newName);
            File sourceFile[] = file_in.listFiles();
            for(int i = 0; i < sourceFile.length; i++){
                if(!sourceFile[i].exists()){
                    continue;
                }
                if(sourceFile[i].isDirectory()){
                    this.upload(sourceFile[i].getPath(),sourceFile[i].getName(),path+"/"+newName);
                }else{
                    this.uploadFile(sourceFile[i].getPath(),sourceFile[i].getName());
                }
            }
        }else{
            uploadFile(file_in.getPath(),newName);
        }
        ftpClient.changeDirectory(path);
    }



    /**
     *  upload 上传文件
     *
     * @param filename 要上传的文件名
     * @param newname 上传后的新文件名
     * @return -1 文件不存在 >=0 成功上传，返回文件的大小
     * @throws Exception
     */
    public void uploadFile(String filename, String newname) throws Exception {
        TelnetOutputStream os = null;
        FileInputStream is = null;
        try {
            // 将远程文件加入输出流中
            os = (TelnetOutputStream) ftpClient.putFileStream(newname, false);

            // 获取本地文件的输入流
            File file_in = new File(filename);
            is = new FileInputStream(file_in);

            // 创建一个缓冲区
            byte[] bytes = new byte[1024];
            int c;
            while ((c = is.read(bytes)) != -1) {
                os.write(bytes, 0, c);
            }
            System.out.println("upload success");
        } catch (IOException ex) {
            System.out.println("not upload");
            throw new RuntimeException(ex);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
            } finally {
                try {
                    if (os != null) {
                        os.close();
                    }
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * 从ftp下载文件到本地
     *
     * @param filename 服务器上的文件名
     * @param newfilename 本地生成的文件名
     * @return
     * @throws Exception
     */
    public long downloadFile(String filename, String newfilename){
        long result = 0;
        TelnetInputStream is = null;
        FileOutputStream os = null;
        try{
            is = (TelnetInputStream) ftpClient.getFileStream(filename);
            File outfile = new File(newfilename);
            os = new FileOutputStream(outfile);
            byte[] bytes = new byte[1024];
            int c;
            while ((c = is.read(bytes)) != -1) {
                os.write(bytes, 0, c);
                result = result + c;
            }
        }catch (IOException e){
        } catch (FtpProtocolException e) {
        } finally{
            try {
                if(is != null){
                    is.close();
                }
                if(os != null){
                    os.close();
                }
            } catch (IOException e) {
            }
        }
        return result;
    }

    /**
     * 取得相对于当前连接目录的某个目录下所有文件列表
     *
     * @param path
     * @return
     */
    public List getFileList(String path){
        List list = new ArrayList();
        DataInputStream dis;
        try {
            dis = new DataInputStream(ftpClient.nameList(this.path + path));
            String filename = "";
            while((filename = dis.readLine()) != null){
                list.add(filename);
            }
        } catch (IOException e) {
        } catch (FtpProtocolException e) {
        }
        return list;
    }



    public static void main(String[] args){
        FtpUtil ftp = new FtpUtil("192.168.43.29",21,"ftp","ftp");
        ftp.connectServer();
        boolean result = ftp.upload("D:/home/record/CityCredit", "/record/CityCredit");
        System.out.println(result?"上传成功！":"上传失败！");
        List list = ftp.getFileList("/record/CityCredit");
        for(int i=0;i<list.size();i++){
            String name = list.get(i).toString();
            System.out.println(name);
        }
        ftp.closeServer();
        /**
         FTP远程命令列表
         USER    PORT    RETR    ALLO    DELE    SITE    XMKD    CDUP    FEAT
         PASS    PASV    STOR    REST    CWD     STAT    RMD     XCUP    OPTS
         ACCT    TYPE    APPE    RNFR    XCWD    HELP    XRMD    STOU    AUTH
         REIN    STRU    SMNT    RNTO    LIST    NOOP    PWD     SIZE    PBSZ
         QUIT    MODE    SYST    ABOR    NLST    MKD     XPWD    MDTM    PROT
         在服务器上执行命令,如果用sendServer来执行远程命令(不能执行本地FTP命令)的话，所有FTP命令都要加上\r\n
         ftpclient.sendServer("XMKD /test/bb\r\n"); //执行服务器上的FTP命令
         ftpclient.readServerResponse一定要在sendServer后调用
         nameList("/test")获取指目录下的文件列表
         XMKD建立目录，当目录存在的情况下再次创建目录时报错
         XRMD删除目录
         DELE删除文件
         */
    }

}