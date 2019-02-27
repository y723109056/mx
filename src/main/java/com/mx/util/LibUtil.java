package com.mx.util;

import java.lang.reflect.Field;

public class LibUtil {
	/**
	 * 添加java.library.path目录
	 * @param path
	 */
	public static void addLibraryPath(String path){  
		try {
			Field field = ClassLoader.class.getDeclaredField("usr_paths");
			field.setAccessible(true);  
			String[] paths = (String[])field.get(null);  
			for (int i = 0; i < paths.length; i++) {  
				if (path.equals(paths[i])) {  
					return;  
				}  
			}  
			String[] tmp = new String[paths.length+1];  
				System.arraycopy(paths,0,tmp,0,paths.length);  
				tmp[paths.length] = path;  
				field.set(null,tmp);  
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 添加java.library.path目录
	 * @param path
	 */
	public static void setLibraryPath(String path){
		try {
			if(SystemUtil.isWindows()){
				String paths = System.getProperty("java.library.path");
				paths+=";"+path;
				System.out.println(paths);
				System.setProperty("java.library.path", paths);
			}else{
				String paths = System.getProperty("java.library.path");
				paths+=":"+path;
				System.out.println(paths);
				System.setProperty("java.library.path", paths);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
