package com.mx.util;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

public class ZipUtil {
	
	//解压指定zip文件 
	public static void unZip(String unZipfileName,String path){//unZipfileName需要解压的zip文件名 
    	unZip(unZipfileName,path,null);
    }
    
	//解压指定zip文件 
    @SuppressWarnings("rawtypes")
	public static void unZip(String unZipfileName,String path,String[] filters){//unZipfileName需要解压的zip文件名 

        FileOutputStream fileOut; 
        File file; 
        InputStream inputStream;
        int readedBytes = 1024*64;
        byte[] buf = new byte[readedBytes];

        try{ 
        	ZipFile zipFile = new ZipFile(unZipfileName);
            for(Enumeration entries = zipFile.getEntries(); entries.hasMoreElements();){ 
                ZipEntry entry = (ZipEntry)entries.nextElement(); 
                String filename = FileUtil.contact(path,entry.getName());
                file = new File(filename);
                if(entry.isDirectory()){ 
                    file.mkdirs(); 
                } 

                else{ 
                    //如果指定文件的目录不存在,则创建之. 
                    File parent = file.getParentFile(); 

                    if(!parent.exists()){ 

                        parent.mkdirs(); 

                    } 

                    boolean flag = false;
                    if(filters!=null && filters.length>0){
                    	for(String filter : filters){
                    		if(filter.equalsIgnoreCase(entry.getName()))
                    			flag = true;
                    	}
                    }else{
                    	flag = true;
                    }
                    
                    if(flag){
	                    inputStream = zipFile.getInputStream(entry); 
	                    fileOut = new FileOutputStream(file); 
	                    while((readedBytes = inputStream.read(buf) ) > 0){ 
	
	                        fileOut.write(buf , 0 ,readedBytes ); 
	                    } 
	                    fileOut.close(); 
	                    inputStream.close();
                    }
                }    

            }
            zipFile.close(); 
        }catch(IOException ioe){ 

            ioe.printStackTrace(); 

        } 

    }
    
    /**
     * 压缩文件
     * @param filename
     * @param path
     */
    public static void doZip(String filename,String path){
    	File zipFile = new File(path);
    	try{
    		ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(filename)));
    		handleDir(zipFile,zos);
    		zos.close();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    private static void handleDir(File dir,ZipOutputStream out) throws Exception {
    	File[] files = dir.listFiles();
    	if(files.length == 0){
    		out.putNextEntry(new ZipEntry(dir.toString()+"/"));
    		out.closeEntry();
    	}else{
    		for(File f : files){
    			if(f.isDirectory()){
    				handleDir(f,out);
    			}else{
    				FileInputStream fis = new FileInputStream(f);
    				String name = dir.getName();
    				
    				out.putNextEntry(new ZipEntry(name+"/"+f.getName()));
    				
    				int n = 0;
    				byte[] bytes = new byte[8092];
    				while((n=fis.read(bytes))>0){
    					out.write(bytes,0,n);
    				}
    				fis.close();
    				out.closeEntry();
    			}
    		}
    	}
    }


    /**
     *
     * @param resourceFile 带路径的源文件
     * @param targetFile 带路径的目标文件
     */
    public static void unZipFile(String resourceFile, String targetFile){
        try{
            // 读取文件
            File file=new File(resourceFile);
            if(!file.exists()){
                return;
            }
            FileInputStream inputStream  = new FileInputStream(file);
            GZIPInputStream gzi = new GZIPInputStream(inputStream);
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(targetFile) );
            do {
                int b = gzi.read();
                if (b==-1) break;
                bos.write(b);
            } while (true);
            gzi.close();
            bos.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * 按行读取文件内容
     * @param fileName 文件名称
     * @param endString 文件结束符
     * @return
     */
    public static List<String> readFileByLine(String fileName, String endString, String encoding){
        List<String> list = new ArrayList<String>();
        try {
            // 从服务器上读取指定的文件
            // 获取到本服务器的制定位置
            File file=new File(fileName);
            if(!file.exists()){
                return list;
            }
            FileInputStream stream = new FileInputStream(file);
            GZIPInputStream gzi = new GZIPInputStream(stream);
            InputStreamReader inputReader = new InputStreamReader(gzi, encoding);
            BufferedReader reader=new BufferedReader(inputReader);
            String line = null;
            while ((line = reader.readLine()) != null) {
                if(endString.equals(line) || line.contains(endString)){
                    break;
                } else {
                    list.add(line);
                }
            }
            if (reader != null) {
                reader.close();
            }
            if (inputReader != null) {
                inputReader.close();
            }
            if (stream != null) {
                stream.close();
            }
            file.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
