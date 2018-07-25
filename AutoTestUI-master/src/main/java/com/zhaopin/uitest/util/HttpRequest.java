package com.zhaopin.uitest.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import com.zhaopin.uitest.base.Log;

public class HttpRequest {
	   public static String projectName = "深圳项目API";
	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String SendPost_keyValue(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";

		try {
			URLEncoder.encode(url, "utf-8");
			URL realUrl = new URL(url);
			// 打开和URL之间的链接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			int code = ((HttpURLConnection) conn).getResponseCode();
			//// 定义BufferedReader输入流来读取URL的响应
			if(code==200){
				in = new BufferedReader(new InputStreamReader(
						((HttpURLConnection) conn).getInputStream(), "utf-8"));
			}else{
				in = new BufferedReader(new InputStreamReader(
						((HttpURLConnection) conn).getErrorStream(), "utf-8"));
			}
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (MalformedURLException e) {
			result = e.getMessage();
			Log.error("执行Http Post请求" + url + "时，发生异常!" + e);
		} catch (IOException e) {
			result = e.getMessage();
			Log.error("打开和" + url + "之间的链接时，发生异常!" + e);
		} finally {
			if (out != null) {
				out.close();
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return result;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 *
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是json字符串 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String SendPost_json(String url, String parameters) {
		String result = "";
		try {
			//URLEncoder.encode(url, "utf-8");
			URL surl = new URL(url);// 创建连接
			HttpURLConnection connection = (HttpURLConnection) surl
					.openConnection();
			// 设置通用的请求属性
//			connection.setRequestProperty("Content-Type", "utf-8");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);

			connection.setInstanceFollowRedirects(true);
			connection.setRequestMethod("POST"); // 设置请求方式
			connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
			connection.setRequestProperty("Content-Type", "application/json;charset=utf-8"); // 设置发送数据的格式
			connection.connect();
			// 获取URLConnection对象对应的输出流
			OutputStreamWriter out = new OutputStreamWriter(
					connection.getOutputStream(),"utf-8"); // utf-8编码
			//发送请求
			out.append(parameters);
			out.flush();
			out.close();
			// 读取响应
			int length = (int) connection.getContentLength();// 获取长度
			int code = connection.getResponseCode();
			InputStream is = null;
			if(code==200){
				is = connection.getInputStream();
			}else{
				is = connection.getErrorStream();
			}
			if (length != -1) {
				byte[] data = new byte[length];
				byte[] temp = new byte[512];
				int readLen = 0;
				int destPos = 0;
				while ((readLen = is.read(temp)) > 0) {
					System.arraycopy(temp, 0, data, destPos, readLen);
					destPos += readLen;
				}
				result = new String(data, "UTF-8"); // utf-8编码
			}
		} catch (Exception e) {
			result = e.getMessage();
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result; // 自定义错误信息
	}

	


	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url) {
		String result = "";
		BufferedReader in = null;
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			int code = ((HttpURLConnection) connection).getResponseCode();
			//// 定义BufferedReader输入流来读取URL的响应
			if(code==200){
				in = new BufferedReader(new InputStreamReader(
						((HttpURLConnection) connection).getInputStream(), "utf-8"));
			}else{
				in = new BufferedReader(new InputStreamReader(
						((HttpURLConnection) connection).getErrorStream(), "utf-8"));
			}
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			result = e.getMessage();
			Log.error("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}
	
}
