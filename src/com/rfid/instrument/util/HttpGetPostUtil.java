package com.rfid.instrument.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import android.util.Log;

/**
 * @Description: 发起网络请求工具�?
 * @date:2015-4-9 下午2:18:14
 * @author: 罗德�?
 */
public class HttpGetPostUtil
{
	private static final String TAG="HttpGetPostUtil";
	private static final int TIME_OUT=10*1000;//10�?
	
	private static String COOKIE_VALUE=null;
	/**
	 * 向指定URL发�?GET方法的请�?
	 * @param url 发�?请求的URL
	 * @param params 请求参数，请求参数应该是name1=value1&name2=value2的形式�?
	 * @return URL�?��表远程资源的响应
	 */
	public static String sendHttpGet(String url, String params) 
	{
		String result = "";
		BufferedReader bufferedReader = null;
		try 
		{
			String urlName = url + "?" + params;
			URL realUrl = new URL(urlName);
			// 打开和URL之间的连�?
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属�?
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");			

			conn.setConnectTimeout(TIME_OUT);
			conn.setReadTimeout(TIME_OUT);
			// 建立实际的连�?
			conn.connect();		
			// 定义BufferedReader输入流来读取URL的响�?
			bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = bufferedReader.readLine()) != null) 
			{
				result += "\n" + line;
			}
			
			bufferedReader.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		finally
		{		
			if (bufferedReader != null) 
			{
				bufferedReader=null;
			}				
		}
		return result;
	}

	/**
	 * 向指定URL发�?POST方法的请�?
	 * @param url 发�?请求的URL
	 * @param params 请求参数，请求参数应该是name1=value1&name2=value2的形式�?
	 * @return URL�?��表远程资源的响应
	 */
	public static String sendHttpPost(String url, String params) 
	{
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try 
		{
			URL realUrl = new URL(url);
			// 打开和URL之间的连�?
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属�?
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			// 发�?POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setConnectTimeout(TIME_OUT);
			conn.setReadTimeout(TIME_OUT);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发�?请求参数
			out.print(params);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响�?
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) 
			{
				result += "\n" + line;
			}
			
			in.close();
			out.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流�?输入�?
		finally 
		{
			if (out != null) 
			{
				out=null;
			}
			if (in != null) 
			{
				in=null;
			}				
		}
		return result;
	}
	
	public static String testUrlConnection(String urlStr)
	{
        try
        {
        	Log.i(TAG, "����:"+urlStr);
            URL url=new URL(urlStr);
            HttpURLConnection con=(HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");      
            con.setConnectTimeout(5*1000);
            con.setReadTimeout(5*1000);
//            Map hfs=con.getHeaderFields();
//            Set<String> keys=hfs.keySet();
//            for(String str:keys)
//            {
//                List<String> vs=(List)hfs.get(str);
//                LogUtil.i(TAG, "�?��1:"+str);
//                for(String v:vs)
//                {
//                	LogUtil.i(TAG, "�?��2:"+v);            
//                }
//            }        
            String cookieValue=con.getHeaderField("Set-Cookie");
            Log.i(TAG, "返回:"+cookieValue);
            String sessionId=cookieValue.substring(0, cookieValue.indexOf(";"));  
            Log.i(TAG, "返回sessionID:"+sessionId);
           return sessionId;
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    } 
}
