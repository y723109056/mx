package com.mx.file;

import com.mx.util.FileUtil;
import org.apache.commons.io.FileUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Date;

/**
 * @author 小米线儿
 * @time 2019/2/23 0023
 * @QQ 723109056
 * @blog https://blog.csdn.net/qq_31407255
 */
public class FileProcessUtil {


    private final static Logger LOG = LoggerFactory.getLogger(FileProcessUtil.class);

    /**
     * 上传文件
     */
    public static void uploadFile(String fileName, String filePath, InputStream is){
        try {
            Date date = new Date();

            File f = new File(filePath);
            if (!f.exists()) { //目录不存在，则创建相应的目录
                f.mkdirs();
            }
            String saveName = System.currentTimeMillis()+fileName;
            String saveFilePath = FileUtil.contact(filePath, saveName);
            File obj = new File(saveFilePath);
            //将文件上传到服务器
            FileUtils.copyToFile(is, obj);


            //后缀名
            String FileFix = fileName.substring(fileName.lastIndexOf("."));
        } catch (Exception e) {
            LOG.error("uploadFile error",e);
        }

    }

    /**
     * 下载文件
     */

    public void downloadFile(String path,String fileName,HttpServletResponse response) throws IOException{

        String filePath = FileUtil.contact(path,fileName);
        File file = new File(filePath);

        response.setCharacterEncoding("utf-8");
        response.setContentType("application/force-download");// 设置强制下载不打开
        String file_Name = URLEncoder.encode(fileName, "UTF-8");
        response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名

        OutputStream os = response.getOutputStream();

        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {
            byte[] buffer = new byte[2048];
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis,2048);
            int i;
            while ((i = bis.read(buffer)) > 0) {
                os.write(buffer, 0, i);
            }
            os.flush();
        } catch (Exception e) {
            LOG.error("downloadFile error", e);
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                }
            }
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                }
            }
        }

    }

    private static void downFile(HttpServletResponse response, String str) {
        try {
            String path = str;
            String zipName =str.substring(str.lastIndexOf("\\")+1);
            File file = new File(path);
            if (file.exists()) {
                InputStream ins = new FileInputStream(path);
                BufferedInputStream bins = new BufferedInputStream(ins);// 放到缓冲流里面
                OutputStream outs = response.getOutputStream();// 获取文件输出IO流
                BufferedOutputStream bouts = new BufferedOutputStream(outs);
                response.setContentType("application/x-download");// 设置response内容的类型
                response.setHeader(
                        "Content-disposition",
                        "attachment;filename="
                                + URLEncoder.encode(zipName, "UTF-8"));// 设置头部信息
                int bytesRead = 0;
                byte[] buffer = new byte[1024];
                // 开始向网络传输文件流
                while ((bytesRead = bins.read(buffer)) != -1) {
                    bouts.write(buffer, 0, bytesRead);
                }
                bouts.flush();// 这里一定要调用flush()方法
                ins.close();
                bins.close();
                outs.close();
                bouts.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("文件下载出错", e);
        }
    }

    /**
     * 批量下载
     * @param request
     * @param response
     * @throws IOException
     */
    public static void downloadFiles(HttpServletRequest request, HttpServletResponse response,@Value("${file_root_path}")String fileRootPath) throws IOException {
        String ids = request.getParameter("ids");
        String[] split = ids.split(",");
        String userName = request.getParameter("userName");
        File downFile = new File(fileRootPath+"\\"+userName+"_所有资料.zip");
        if(!downFile.exists()){
            downFile.createNewFile();
        }
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(downFile));
//        File fileMount= infoAddedFilesService.exportFileMount(loanMountList,fileRootPath);//下载excel统计文件
//        downLoadFilesUtil(fileMount,zipName+"_所有资料",out);
        for (int i = 0; i < split.length; i++) {
//            AddedFilesExtend infoAddedFiles = AddedFilesService.getInfoAddedFilesExtendById(Integer.valueOf(split[i]));
            File file = getFileById(i);
            String fileType = FileUtil.getFileFix(file.getName());

            if(null != file){
                if(file.exists()) {
                    if(fileType.equals("Image")){
                        downLoadFilesUtil(file, userName+"_所有资料/图片/", out);
                    }else if(fileType.equals("Audio")){
                        downLoadFilesUtil(file,userName+"_所有资料/录音",out);
                    }else if(fileType.equals("Video")){
                        downLoadFilesUtil(file,userName+"_所有资料/视频",out);
                    }else if(fileType.equals("Other")){
                        downLoadFilesUtil(file,userName+"_所有资料/其他",out);
                    }
                }
            }
        }
        out.close();
        downFile(response, fileRootPath + "\\" + userName + "_贷款资料.zip");
        if(downFile.exists()){
            downFile.delete();
        }
    }

    private static void downLoadFilesUtil(File file,String dirPath ,ZipOutputStream out)throws IOException{
        try {
            FileInputStream fis = new FileInputStream(file);
            String dir=dirPath+'/'+file.getName();
            out.putNextEntry(new ZipEntry(dir));
            //设置压缩文件内的字符编码，不然会变成乱码
            out.setEncoding("GBK");
            int len;
            byte[] buffer = new byte[1024];
            // 读入需要下载的文件的内容，打包到zip文件
            while ((len = fis.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            out.closeEntry();
            fis.close();

        } catch (Exception e) {
            LOG.error("文件下载出错", e);
        }
    }


    private static File getFileById(int i) {
        //TO DO
        return null;
    }


}
