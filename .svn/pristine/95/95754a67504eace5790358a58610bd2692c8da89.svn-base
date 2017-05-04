package com.rfid.instrument.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Xml;

import com.rfid.sdk.public_utils.DebugLog;

public class UpdateUtil {
	protected static final String TAG = "UpdateUtil";
	public static final int DOWN_ERROR = -1;
	public static final int GET_UNDATAINFO_ERROR = 0;
	public static final int UPDATA_CLIENT = 1;
	private int versionCode = -1;
    private String version;  
    private String description;  
    private String url;
    private Context context;
    
	public UpdateUtil(Context context) {
		super();
		this.context = context;
	}
	
	public static class UpdataInfo {  
	    private String version;  
	    private String url;  
	    private String description;  
	    
	    public UpdataInfo() {
			super();
		}
		public String getVersion() {  
	        return version;  
	    }  
	    public void setVersion(String version) {  
	        this.version = version;  
	    }  
	    public String getUrl() {  
	        return url;  
	    }  
	    public void setUrl(String url) {  
	        this.url = url;  
	    }  
	    public String getDescription() {  
	        return description;  
	    }  
	    public void setDescription(String description) {  
	        this.description = description;  
	    }
		@Override
		public String toString() {
			return "UpdataInfo [version=" + version + ", url=" + url
					+ ", description=" + description + "]";
		}  
	} 
	
	/* 
	 * 获取当前程序的版本号  
	 */  
	public String getVersionName() throws Exception{  
	    //获取packagemanager的实例   
	    PackageManager packageManager = context.getPackageManager();  
	    //getPackageName()是你当前类的包名，0代表是获取版本信息  
	    PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);  
	    return packInfo.versionName;   
	}
	
	/* 
	 * 用pull解析器解析服务器返回的xml文件 (xml封装了版本号) 
	 */  
	public static UpdataInfo getUpdataInfo(InputStream is) throws Exception{  
	    XmlPullParser  parser = Xml.newPullParser();    
	    parser.setInput(is, "utf-8");//设置解析的数据源   
	    int type = parser.getEventType();  
	    UpdataInfo info = new UpdataInfo();//实体  
	    while(type != XmlPullParser.END_DOCUMENT ){  
	        switch (type) {  
	        case XmlPullParser.START_TAG:  
	            if("version".equals(parser.getName())){  
	                info.setVersion(parser.nextText()); //获取版本号  
	            }else if ("url".equals(parser.getName())){  
	                info.setUrl(parser.nextText()); //获取要升级的APK文件  
	            }else if ("description".equals(parser.getName())){  
	                info.setDescription(parser.nextText()); //获取该文件的信息  
	            }  
	            break;  
	        }  
	        type = parser.next();  
	    }  
	    return info;  
	}
	
	public static File getFileFromServer(String path, ProgressDialog pd) throws Exception{  
	    //如果相等的话表示当前的sdcard挂载在手机上并且是可用的  
	    if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){  
	        URL url = new URL(path);  
	        HttpURLConnection conn =  (HttpURLConnection) url.openConnection();  
	        conn.setConnectTimeout(5000);  
	        //获取到文件的大小   
	        pd.setMax(conn.getContentLength());  
	        InputStream is = conn.getInputStream();  
	        File file = new File(Environment.getExternalStorageDirectory(), "njjl.apk");  
	        FileOutputStream fos = new FileOutputStream(file);  
	        BufferedInputStream bis = new BufferedInputStream(is);  
	        byte[] buffer = new byte[1024];  
	        int len ;  
	        int total=0;  
	        while((len =bis.read(buffer))!=-1){  
	            fos.write(buffer, 0, len);  
	            total+= len;  
	            //获取当前下载量  
	            pd.setProgress(total);  
	        }  
	        fos.close();  
	        bis.close();  
	        is.close();  
	        return file;  
	    }  
	    else{  
	        return null;  
	    }  
	}  

	public static int getVerCode(Context context,String appPackName)
	{
		int verCode = -1;
		try
		{
			verCode = context.getPackageManager().getPackageInfo(appPackName, 0).versionCode;
			DebugLog.i(TAG, "当前已安装应用版本号:" + verCode);
		} catch (Exception e)
		{
			DebugLog.e(TAG, e.getMessage());
		}
		return verCode;
	}
    
    private class GetJsonTask extends AsyncTask<String, String, String>
    {

		@Override
		protected String doInBackground(String... params) {
			// TODO 自动生成的方法存根
			DebugLog.i(TAG, "获得地址:" + params[0]);
			String json = HttpGetPostUtil.sendHttpGet(params[0], null);

			DebugLog.i(TAG, "获得json: " + json);
			if (!"".equals(json) && null != json)
			{
				try
				{
					// {"app":{"versionCode":"2","versionName":"1.0.1"}}
					
					JSONObject jsonobj = new JSONObject(json).getJSONObject("app");
					versionCode = Integer.parseInt(jsonobj.optString("versionCode", "0"));
					version = jsonobj.getString("versionName");
					DebugLog.i(TAG, "获得升级版本:"+versionCode);
				} catch (Exception e)
				{
					DebugLog.i(TAG, "获得升级版本信息异常:"+e.getMessage());
					versionCode = -1;
				}
			} else
			{
				versionCode = -1;
			}
			return versionCode + "";
		}
    	
		@Override
		protected void onPostExecute(String result)
		{
			super.onPostExecute(result);
			int currVerCode = getVerCode(context, ConstantUtil.HTTP_APK_Name);
			DebugLog.i(TAG, "currVCode:" + currVerCode + " ;newVCode:" + versionCode + " ;newVName:" + version);
			if (versionCode > currVerCode)
			{
				//FileUtil.deleteFile(apkName);
				//这里要升级
				//showUpdateDialog();
				
			} else
			{
				//sendMsg(ZKCmd.HAND_REQUEST_DATA);
			}
		}
    }
}
