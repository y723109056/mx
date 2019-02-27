package com.mx.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;

/**
 * @author 小米线儿
 * @QQ 723109056
 * @blog https://blog.csdn.net/qq_31407255
 */
public class QuartzJob implements Job {

    public Logger logger = LoggerFactory.getLogger(this.getClass());
    /*private FTPClient ftpClient;
    private final static String STREN_CODING = "UTF-8";*/

    /**
     * @param Context
     * @throws org.quartz.JobExecutionException
     */
    @SuppressWarnings("deprecation")
	public void execute(JobExecutionContext Context) throws JobExecutionException {
        System.err.println("In　QuartzJob　-　executing　its　JOB　at "
            + Calendar.getInstance().getTime().toLocaleString().toString()+" by "+Context.getTrigger().getName());
    }

    //移到FtpServerUtil里
    /**
     * @param ip
     * @param port
     * @param userName
     * @param userPwd
     * @param path
     * @throws java.net.SocketException
     * @throws java.io.IOException     function:连接到服务器
     *//*
    public boolean connectServer(String ip, int port, String userName, String userPwd, String path) {
        ftpClient = new FTPClient();
        try {
            // 连接
            ftpClient.connect(ip, port);
            // 登录
            ftpClient.login(userName, userPwd);
            if (path != null && path.length() > 0) {
                // 跳转到指定目录
                ftpClient.changeWorkingDirectory(path);
            }
            return true;
        } catch (SocketException e) {
            logger.error("连接到ftp服务器异常！"+e);
            return false;
        } catch (IOException e) {
            logger.error("连接到ftp服务器异常！"+e);
            return false;
        }
    }

    *//**
     * @throws IOException function:关闭连接
     *//*
    public void closeServer() {
        if (ftpClient.isConnected()) {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (IOException e) {
                logger.error(e);
            }
        }
    }

    *//**
     * @param path
     * @return function:读取指定目录下的文件名
     * @throws IOException
     *//*
    public FTPFile[] getFileList(String path) {
        // 获得指定目录下所有文件名
        FTPFile[] ftpFiles = null;
        try {
            ftpFiles = ftpClient.listFiles(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ftpFiles == null ? new FTPFile[0] : ftpFiles;
    }

    *//**
     * @return function:从服务器上读取指定的文件
     *//*
    public List<String> readFileFromFTP(String fileName, String endString) {
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
            logger.error(e);
        }
        return list;
    }

    *//**
     * @param fileName function:删除文件
     *//*
    public void deleteFile(String fileName) {
        try {
            ftpClient.deleteFile(fileName);
        } catch (IOException e) {
            logger.error(e);
        }
    }

    public void downFile(String ftpPath, String targetPath, String fileName, String fileType){
        FTPFile[] ftpFiles = getFileList(ftpPath);
        logger.info("ftp服务器下文件数量:"+ftpFiles.length);
        String targetFileName = StringUtil.isNullOrEmpty(fileType) ? fileName : fileName.substring(0,fileName.indexOf(fileType));
        File targetFile = new File(targetPath);
        if (!targetFile.exists()) {// 文件不存在则创建
        	targetFile.mkdirs();
        }
        boolean hasTargetFile = false;
        try{
            for(FTPFile ftpFile : ftpFiles){
                byte[] bytes = ftpFile.getName().getBytes("iso-8859-1");
                String fn = new String(bytes,"utf8");
                if(fn.equals(fileName)){
                	hasTargetFile = true;
                    File localFile = new File(FileUtil.contact(targetPath, targetFileName));
                    OutputStream out = new FileOutputStream(localFile);
                    ftpClient.retrieveFile(fileName, out);
                    out.close();
                }
            }
            if(hasTargetFile){
            	logger.info("找到目标文件:"+fileName);
            }else{
            	logger.info("没找到目标文件:"+fileName);
            }
        } catch (IOException e) {
            logger.error("从ftp服务器获取文件到本地服务器出错"+e);
        }
    }*/
}
