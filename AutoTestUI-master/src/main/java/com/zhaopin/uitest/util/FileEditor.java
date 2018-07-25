package com.zhaopin.uitest.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.channels.FileChannel;

public class FileEditor {

	/**
	 * 创建文件
	 * @param context 内容
	 * @param targetPath 文件路径
	 * @param encoding 编码格式
	 */
	public static void createFile(String context, String targetPath, String encoding){
		File file = new File(targetPath);
		OutputStreamWriter ow = null;
		if (!file.exists()) {
			try {
				ow = new OutputStreamWriter(new FileOutputStream(file), encoding);
				ow.write(context);
				ow.flush();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if(ow != null) {
					try {
						ow.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			if(file.mkdir()) {
				System.out.println("无法创建指定目录！");
			}
		}
	}


	public static void copyFile(String srcPath, String targetPath){
		 File srcFile = new File(targetPath);
		 FileChannel in = null;
		 FileChannel out = null;
		 FileInputStream inStream = null;
		 FileOutputStream outStream = null;
		 try {
			inStream = new FileInputStream(srcFile);
			outStream = new FileOutputStream(targetPath);
			in = inStream.getChannel();
			out = outStream.getChannel();
			in.transferTo(0, in.size(), out);

		 } catch (IOException e) {
				e.printStackTrace();
		 } finally {

		        try {
					in.close();
					inStream.close();
					out.close();
					outStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		 }
	}
}
