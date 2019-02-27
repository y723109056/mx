package com.mx.util;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 小米线儿
 * @time 2019/2/12 0012
 * @QQ 723109056
 * @blog https://blog.csdn.net/qq_31407255
 */

/**
 * 文件操作类
 */
public class FileUtil {

	/**
     * 
     * @param path
     * @return
     */
    public static String getParentPath(String path){
    	File file = new File(path);
    	return file.getParent().replaceAll("\\\\","/");
    }
    
    /**
     * 得到相对路径 ../
     * @param path
     * @return
     */
    public static String getCanonicalPath(String path){
    	File file = new File(path);
    	if(file.isAbsolute()){
    		String newPath = path.replaceAll("\\\\","/");
    		int n = newPath.indexOf("../");
	    	while(n>-1){
	    		int m = n>1?newPath.substring(0,n-1).lastIndexOf("/"):-1;
	    		if(m>-1){
	    			newPath = newPath.substring(0,m+1)+newPath.substring(n+3);
	    		}else{
	    			newPath = newPath.substring(n+3);
	    		}
	    		n = newPath.indexOf("../");
	    	}
	    	return newPath;
    	}else{
    		try {
    			return file.getCanonicalPath().replaceAll("\\\\","/");
    		} catch (IOException e) {
    			e.printStackTrace();
    			return path;
    		}
    	}
    }
    
    /**
     * 得到文件的绝对路径
     * @param path
     * @return
     */
    public static String getFileFullPath(String path){
    	String newPath = path.replaceAll("\\\\","/");
    	File file=new File(newPath);
    	return file.getAbsolutePath();
    }
    
    /**
     * 得到文件名
     * @param fullFileName
     * @return
     */
    public static String getFileName(String fullFileName){
        String path = fullFileName;
        int bit = 0;
        int n1 = path.lastIndexOf("/");
        if (n1 < 0) { n1 = path.lastIndexOf("\\"); }
        if (n1 > - 1) bit = 1;
        int s = n1 + bit;
        if(s>0){
        	return path.substring(s, path.length());
        }else{
        	return fullFileName;
        }
    }
	
	/**
	 * 拼接文件路径
	 * @param path
	 * @param filename
	 * @return
	 */
    public static String contact(String path,String filename)
    {
    	char c = path.charAt(path.length() - 1);
        if (c == '/' || c == '\\')
            return StringUtil.format("{0}{1}", new Object[] { path, filename });
        else{
        	if(filename.length()>0 && filename.charAt(0)=='/'){
        		return StringUtil.format("{0}{1}", new Object[] { path, filename });
        	}else{
        		return StringUtil.format("{0}/{1}", new Object[] { path, filename });
        	}
        }
    }
    
    /**
     * 判断文件是否存在
     * @param filename
     * @return
     */
    public static boolean isExistFile(String filename){
    	File f=new File(filename);
        return f.isFile() && f.exists();
    }
    
    /**
     * 判断文件路径是否存在
     * @param path
     * @return
     */
    public static boolean isExistFilePath(String path)
    {
        String filePath = getFileFullPath(path);
        File f=new File(filePath);
        return f.isDirectory() && f.exists();
    }
    
    /**
     * 根据文件路径得到文件集合
     * @param filePath 文件路径
     * @param fix 文件后缀
     * @return
     */
    public static String[] getFilesByPath(String filePath, String fix)
    {
    	String[] files = getFilesByPath(filePath);
        List<String> list = new ArrayList<String>();
        for(String file : files)
        {
        	String fileFix = getFileFix(file);
            if (fileFix.replace(".", "").equalsIgnoreCase(fix.replace(".", "")))
            {
                list.add(file);
            }
        }
        String[] cs = new String[list.size()];
        list.toArray(cs);
        return cs;
    }
    
    /**
     * 根据文件路径集合得到文件集合
     * @param filePaths 文件路径集合
     * @param fix 文件后缀
     * @return
     */
    public static String[] getFielsByPath(String[] filePaths, String fix)
    {
        List<String> list = new ArrayList<String>();
        for(String filePath : filePaths)
        {
        	String[] files = getFilesByPath(filePath);
            if (files.length > 0)
            {
                for(String file : files)
                {
                	String fileFix = getFileFix(file);
                    if (fileFix.equals(fix))
                    {
                        list.add(file);
                    }
                }
            }
        }
        String[] cs = new String[list.size()];
        list.toArray(cs);
        return cs;
    }
    
    public static FileInfo[] getAllFileInfosByPath(String[] filePaths, String fix)
    {
        List<FileInfo> list = new ArrayList<FileInfo>();
        String[] files = getFielsByPath(filePaths,fix);
        for(String file : files)list.add(new FileInfo(file));
        return list.toArray(new FileInfo[0]);
    }
    
    /**
     * 根据文件名得到后缀
     * @param filename 文件名
     * @return
     */
    public static String getFileFix(String filename)
    {
    	String name = getFileName(filename);
        int index = name.lastIndexOf('.');
        if (index < 0) return "";
        else
        {
            return name.substring(index, name.length());
        }
    }
    
    /**
     * 根据文件路径得到文件集合
     * @param filePath 文件路径
     * @return
     */
    public static String[] getFilesByPath(String filePath){
        if (isExistFilePath(filePath)){
        	String path = getFileFullPath(filePath);
        	List<String> files = new ArrayList<String>();
        	addAllFilesByPath(path,files);
        	return files.toArray(new String[0]);
        }
        else return new String[0];
    }
    
    private static void addAllFilesByPath(String path,List<String> files){
    	File dir = new File(path);
    	if(dir.exists() && dir.isDirectory()){
    		for(String str : dir.list()){
    			String file = FileUtil.contact(dir.getAbsolutePath(),str);
    			File f = new File(file);
    			if(f.exists() && !f.isHidden()){
    				if(f.isFile())files.add(file);
    				else if(f.isDirectory()){
        				addAllFilesByPath(file,files);
        			}
    			}
    		}
    	}
    }
    
    public static void mkdirs(String[] paths){
    	if(paths!=null && paths.length>0){
    		for(String path : paths)
    			mkdirs(path);
    	}
    }
    
    /**
     * 创建文件路径
     * @param path
     */
    public static void mkdirs(String path)
    {
    	File f = new File(path);
		if (!f.exists())
			f.mkdirs();
    }

    /**
     * 删除目录
     * @param dir
     */
    public static boolean deleteDir(File dir){
    	if(dir.isDirectory()){
    		String[] children = dir.list();
    		
    		for(int i=0;i<children.length;i++){
    			boolean success = deleteDir(new File(dir,children[i]));
    			if(!success){
        			return false;
        		}
    		}
    	}
    	
    	return dir.delete();
    }
    
    /**
     * 得到文件大小
     * @param fullFilename 文件不存在返回-1
     * @return
     */
    public static long getFilesize(String fullFilename){
    	File file = new File(fullFilename);
    	if(file.exists()){
    		return file.length();
    	}
    	return -1;
    }
    
    /**
     * 是否为绝对路径
     * @param path
     * @return
     */
    public static boolean isAbsolutePath(String path)
    {
    	File file=new File(path);
    	return file.isAbsolute();
    }

	/**
	 * 创建文件路径
	 * @param path
	 */
	public static void createDirectory(String path)
	{
		String fullPath = getFileFullPath(path);
		if(!isExistFilePath(fullPath))
		{
			File f=new File(fullPath);
			f.mkdirs();
		}
	}
    
    /**
     * 得到文件信息对像
     * @param fullFileName
     * @return
     */
    public static FileInfo getFileInfo(String fullFileName)
    {
        return new FileInfo(fullFileName);
    }

	public static boolean isValidSystemFilePath(String path){
	  if(isWindows()){//是否windows系统
            File file = new File(path);
            return file.isDirectory();
        }else{//是否linux系统
            return path.matches("([\\\\/][\\w-]+)*$");
        }
    }

	public static boolean isWindows() {
	    boolean result = false;
	    if (System.getProperty("os.name").toUpperCase().contains("WINDOWS")) {
	        result = true;
	    }
	    return result;
	}

    /**
     * 拷贝文件
     * @param sourceFile
     * @param newFile
     */
    public static void copyFile(File sourceFile, File newFile){
        File nf=newFile;
        File sf=sourceFile;
        nf.mkdirs();
        if(nf.exists())nf.delete();

        try {
            FileInputStream input = new FileInputStream(sf);
            BufferedInputStream inBuff=new BufferedInputStream(input);

            FileOutputStream output = new FileOutputStream(nf);
            BufferedOutputStream outBuff=new BufferedOutputStream(output);

            byte[] b = new byte[1024 * 5];
            int len;
            while ((len =inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            outBuff.flush();
            inBuff.close();
            outBuff.close();
            output.close();
            input.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

	/**
	 * @Author: yangdw
	 * @param fullFileName
	 * @param encoding
	 * @Description: 读取文件的内容
	 * @Date: 9:24 2017/9/29
	 */
	public static String getFileString(String fullFileName,String encoding){
		String line;
		StringBuilder sb=new StringBuilder();
		try {
			File file=new File(fullFileName);
			FileInputStream stream=new FileInputStream(file);
			InputStreamReader inputReader = new InputStreamReader(stream,encoding);
			BufferedReader reader=new BufferedReader(inputReader);
			while ((line = reader.readLine()) != null) {
				sb.append(line+"\r\n");
			}
			stream.close();
			inputReader.close();
			reader.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String fileString=sb.toString();
		return fileString;
	}

    /**
     * 图片链接访问，根据资源路径返回输出流
     * @param filePath
     * @param response
     * @param rootPath
     */
    public static void downloadFile(String filePath, HttpServletResponse response, String rootPath) {
        File file = new File(filePath);
        response.setContentType("image/jpeg");
        response.setCharacterEncoding("utf-8");
        OutputStream os = null;
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {
            os = response.getOutputStream();
            if (!file.exists())
                file = new File(getHoldPlaceFilename(rootPath));
            byte[] buffer = new byte[2048];
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            int i ;
            while ((i = bis.read(buffer)) > 0) {
                os.write(buffer, 0, i);
            }
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null)
                    os.close();
                if (bis != null)
                    bis.close();
                if (fis != null)
                    bis.close();
            } catch (IOException e) {}
        }
    }

    private static String getHoldPlaceFilename(String path){
        String imagePath = FileUtil.contact(path, "apk");
        String filename = FileUtil.contact(imagePath, "default.jpg");
        return filename;
    }

}
