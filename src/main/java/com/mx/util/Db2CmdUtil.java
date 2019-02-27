package com.mx.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;

/**
 * just for db2
 */
public class Db2CmdUtil {

	/**
	 *
	 * @param params
	 * dbHost
	 * dbPort
	 * dbNode
	 * dbUser
	 * dbPassword
	 * delFile
	 * dbTable
	 * @throws java.io.IOException
	 * @throws InterruptedException
	 */
	public static void importDb2DelFile(Map<String,String> params) throws IOException, InterruptedException {
		String execStr = "";
		Process pro = null;
		File execFile = null;
		if (SystemUtil.isWindows()) {
			execFile = new File(FormatUtil.formatDateToStr("yyyyMMddhhmmss", Calendar.getInstance().getTime())+".bat");
			execFile.deleteOnExit();
			BufferedWriter writer = new BufferedWriter(new FileWriter(execFile));
			writeNewLineToBuffWriter(writer, MessageFormat.format("db2 catalog tcpip node {0} remote {1} server {2}", params.get("dbNode"), params.get("dbHost"), params.get("dbPort")));
			writeNewLineToBuffWriter(writer, MessageFormat.format("db2 catalog db {0} at node {1}",params.get("dbNode"), params.get("dbNode")));
			writeNewLineToBuffWriter(writer, "db2 terminate");
			writeNewLineToBuffWriter(writer, MessageFormat.format("db2 connect to {0} user {1} using {2}", params.get("dbNode"),params.get("dbUser"),params.get("dbPassword")));
			writeNewLineToBuffWriter(writer, MessageFormat.format("db2 import from {0} of del replace into {1}",params.get("delFile"),params.get("dbTable")));
			writeNewLineToBuffWriter(writer, "db2 commit");
			writeNewLineToBuffWriter(writer, "exit 0");
			writer.close();
			execStr = "db2cmd -c -w " + execFile.getAbsolutePath().replace("\\","/");
		}else{
			//linux环境
			execFile = new File(FormatUtil.formatDateToStr("yyyyMMddhhmmss",Calendar.getInstance().getTime())+".sh");
			execFile.deleteOnExit();
			BufferedWriter writer = new BufferedWriter(new FileWriter(execFile));
			//添加一些头信息
			writeNewLineToBuffWriter(writer, "#!/bin/bash");
			writeNewLineToBuffWriter(writer, "PATH=$PATH");
			writeNewLineToBuffWriter(writer, "export PATH");
			writeNewLineToBuffWriter(writer, MessageFormat.format("db2 catalog tcpip node {0} remote {1} server {2}",params.get("dbNode"),params.get("dbHost"),params.get("dbPort")));
			writeNewLineToBuffWriter(writer, MessageFormat.format("db2 catalog db {0} at node {1}",params.get("dbNode"),params.get("dbNode")));
			writeNewLineToBuffWriter(writer, "db2 terminate");
			writeNewLineToBuffWriter(writer, MessageFormat.format("db2 connect to {0} user {1} using {2}",params.get("dbNode"),params.get("dbUser"),params.get("dbPassword")));
			writeNewLineToBuffWriter(writer, MessageFormat.format("db2 import from {0} of del replace into {1}",params.get("delFile"),params.get("dbTable")));
			writeNewLineToBuffWriter(writer, "db2 commit");
			writeNewLineToBuffWriter(writer, "rm -fr nul");
			writeNewLineToBuffWriter(writer, "exit 0");
			writer.close();
			execStr = "sh " + execFile.getAbsolutePath();
		}
		Runtime runtime = Runtime.getRuntime();
		pro = runtime.exec(execStr);
		while (true){
			if (pro.waitFor() == 0) {
				break;
			}
		}
		pro.destroy();
		execFile.deleteOnExit();
	}

    /**
     * 将del或ixf文件导入到数据库表中
     * 
     * @param basicParams
     *            基础参数map(包括节点名、别名、数据库url，用户名、密码等)
     * @param delFileNameMapList
     *            要操作的文件对应到系统表名称的map列表
     * @throws java.io.IOException
     * @throws InterruptedException
     */
	public static void importDb2DelFile(Map<String,String> basicParams,List<Map<String,String>> delFileNameMapList) throws IOException, InterruptedException {
		String execStr = "";
		Process pro = null;
		File execFile = null;
		if (SystemUtil.isWindows()) {
			execFile = new File(FormatUtil.formatDateToStr("yyyyMMddhhmmss",Calendar.getInstance().getTime())+".bat");
			execFile.deleteOnExit();
			BufferedWriter writer = new BufferedWriter(new FileWriter(execFile));
			writeNewLineToBuffWriter(writer, MessageFormat.format("db2 catalog tcpip node {0} remote {1} server {2}", basicParams.get("dbNode"), basicParams.get("dbHost"), basicParams.get("dbPort")));
			writeNewLineToBuffWriter(writer, MessageFormat.format("db2 catalog db {0} at node {1}",basicParams.get("dbNode"), basicParams.get("dbNode")));
			writeNewLineToBuffWriter(writer, "db2 terminate");
			writeNewLineToBuffWriter(writer, MessageFormat.format("db2 connect to {0} user {1} using {2}", basicParams.get("dbNode"),basicParams.get("dbUser"),basicParams.get("dbPassword")));
            if (delFileNameMapList != null && delFileNameMapList.size() > 0){
                for (Map<String,String> param : delFileNameMapList){
                    writeNewLineToBuffWriter(writer, MessageFormat.format("db2 import from {0} of {1} replace into {2}",param.get("delFile"),basicParams.get("fileType"),param.get("dbTable")));
                    writeNewLineToBuffWriter(writer, "db2 commit");
                }
            }
			writeNewLineToBuffWriter(writer, "exit 0");
			writer.close();
			execStr = "db2cmd -c -w " + execFile.getAbsolutePath().replace("\\","/");
		}else{
			//linux环境
			execFile = new File(FormatUtil.formatDateToStr("yyyyMMddhhmmss",Calendar.getInstance().getTime())+".sh");
			execFile.deleteOnExit();
			BufferedWriter writer = new BufferedWriter(new FileWriter(execFile));
			//添加一些头信息
			writeNewLineToBuffWriter(writer, "#!/bin/bash");
			writeNewLineToBuffWriter(writer, "PATH=$PATH");
			writeNewLineToBuffWriter(writer, "export PATH");
			writeNewLineToBuffWriter(writer, MessageFormat.format("db2 catalog tcpip node {0} remote {1} server {2}",basicParams.get("dbNode"),basicParams.get("dbHost"),basicParams.get("dbPort")));
			writeNewLineToBuffWriter(writer, MessageFormat.format("db2 catalog db {0} at node {1}",basicParams.get("dbNode"),basicParams.get("dbNode")));
			writeNewLineToBuffWriter(writer, "db2 terminate");
			writeNewLineToBuffWriter(writer, MessageFormat.format("db2 connect to {0} user {1} using {2}", basicParams.get("dbNode"), basicParams.get("dbUser"), basicParams.get("dbPassword")));
            if (delFileNameMapList != null && delFileNameMapList.size() > 0){
                for (Map<String,String> param : delFileNameMapList){
                    writeNewLineToBuffWriter(writer, MessageFormat.format("db2 import from {0} of {1} replace into {2}",param.get("delFile"),basicParams.get("fileType"),param.get("dbTable")));
                    writeNewLineToBuffWriter(writer, "db2 commit");
                }
            }
			writeNewLineToBuffWriter(writer, "rm -fr nul");
			writeNewLineToBuffWriter(writer, "exit 0");
			writer.close();
			execStr = "sh " + execFile.getAbsolutePath();
		}
		Runtime runtime = Runtime.getRuntime();
		pro = runtime.exec(execStr);
		while (true){
			if (pro.waitFor() == 0) {
				break;
			}
		}
		pro.destroy();
		execFile.deleteOnExit();
	}

	private static void writeNewLineToBuffWriter(BufferedWriter writer,String str) throws IOException {
		writer.write(str);
		writer.newLine();
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		Map<String,String> params = new HashMap<String, String>();
		params.put("dbHost","192.168.1.161");
		params.put("dbPort","50000");
		params.put("dbNode","ocrm");
		params.put("dbUser","db2user");
		params.put("dbPassword","888888");
		params.put("delFile","e:/test.del");
		params.put("dbTable","cus_customer");
		Db2CmdUtil.importDb2DelFile(params);
	}
}
