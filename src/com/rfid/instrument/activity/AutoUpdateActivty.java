package com.rfid.instrument.activity;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.rfid.instrument.R;
import com.rfid.instrument.util.ConstantUtil;
import com.rfid.instrument.util.PublicUtil;
import com.rfid.instrument.util.UpdateUtil;
import com.rfid.instrument.util.UpdateUtil.UpdataInfo;
import com.rfid.sdk.public_utils.DebugLog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class AutoUpdateActivty extends Activity {
	private static final String TAG = "AutoUpdateActivty";

	private Activity activity = AutoUpdateActivty.this;
	private UpdateUtil update = null;
	private UpdataInfo info = null;
	private String versionname ="";
	private CheckVersionTask updateTask;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		
		super.onCreate(savedInstanceState);
		Window window = getWindow();  
        WindowManager.LayoutParams params = window.getAttributes();  
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_LOW_PROFILE;
        window.setAttributes(params);
		setContentView(R.layout.update);
		
		if (isConn())
		{
			update = new UpdateUtil(activity);
			try {
				versionname = update.getVersionName();
				DebugLog.d(TAG, "versionname="+versionname);
			} catch (Exception e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			new CountDownTimer(1000, 100)
			{
				@Override
				public void onTick(long millisUntilFinished)
				{
				}

				@Override
				public void onFinish()
				{
					updateTask = new CheckVersionTask();
					updateTask.execute();
					//autoLoginRes(false);
//					LogUtil.i(TAG, "执行升级检查");
//					jsonTask = new CheckVersionTask();
//					jsonTask.execute(apkDownPathDir + "/" + versionJson);
				
				}
			}.start();
		} 
		else
		{
			//sendMsg(0x999);
		}
	}

	/**
	 * 网络连接检查
	 * @return 是否连接
	 */
	public boolean isConn()
	{
		if(PublicUtil.isConnect(this))
		{
			return true;
		}
		else
		{
			PublicUtil.toastShow("网络无连接", this);
			return false;
		}
	}
	
	private void autoLoginRes(boolean success)
	{
		Intent intent = new Intent();
		if (success)
		{
			intent.setClass(activity, MainActivity.class);
		} else
		{
			intent.setClass(activity, LoginActivity.class);
		}
		startActivity(intent);
		finish();
	}
	
	/* 
	 * 从服务器获取xml解析并进行比对版本号  
	 */  
	public class CheckVersionTask {

		public void execute() {
			// TODO 自动生成的方法存根
			new Thread(new Runnable() {

		        @Override
		        public void run() {
		            // TODO Auto-generated method stub
			        try {  
			            //从资源文件获取服务器 地址   
			            String path = ConstantUtil.HTTP_UPDATE_URL;  
			            //包装成url的对象   
			            URL url = new URL(path);  
			            HttpURLConnection conn =  (HttpURLConnection) url.openConnection();   
			            conn.setConnectTimeout(5000);  
			            InputStream is =conn.getInputStream();   
			            info =  UpdateUtil.getUpdataInfo(is);  
			            
			            DebugLog.d(TAG,info.toString());
			            
			            if(info.getVersion().equals(versionname))
			            {  
			                DebugLog.i(TAG,"版本号相同无需升级");  
			                autoLoginRes(false);
			            }else{  
			            	DebugLog.i(TAG,"版本号不同 ,提示用户升级 ");  
			                Message msg = new Message();  
			                msg.what = UpdateUtil.UPDATA_CLIENT;  
			                handler.sendMessage(msg);  
			            }  
			        } catch (Exception e) {  
			            // 待处理   
			            Message msg = new Message();  
			            msg.what = UpdateUtil.GET_UNDATAINFO_ERROR;  
			            handler.sendMessage(msg);  
			            e.printStackTrace();  
			        }   
		        }
		    }).start();
		}  
	}  
	  
	Handler handler = new Handler(){  
	      
	    @Override  
	    public void handleMessage(Message msg) {  
	        // TODO Auto-generated method stub  
	        super.handleMessage(msg);  
	        switch (msg.what) {  
	        case UpdateUtil.UPDATA_CLIENT:  
	            //对话框通知用户升级程序   
	            showUpdataDialog();  
	            break;  
	        case UpdateUtil.GET_UNDATAINFO_ERROR:  
	            //服务器超时   
	            PublicUtil.toastShow("获取服务器更新信息失败", activity); 
	            autoLoginRes(false);  
	            break;    
	        case UpdateUtil.DOWN_ERROR:  
	            //下载apk失败  
	        	PublicUtil.toastShow("下载新版本失败", activity); 
	        	autoLoginRes(false); 
	            break;    
	        }  
	    }  
	};  
	  
	/* 
	 *  
	 * 弹出对话框通知用户更新程序  
	 *  
	 * 弹出对话框的步骤： 
	 *  1.创建alertDialog的builder.   
	 *  2.要给builder设置属性, 对话框的内容,样式,按钮 
	 *  3.通过builder 创建一个对话框 
	 *  4.对话框show()出来   
	 */  
	protected void showUpdataDialog() {  
	    AlertDialog.Builder builer = new Builder(this) ;   
	    builer.setTitle("版本升级");  
	    builer.setMessage(info.getDescription());  
	    //当点确定按钮时从服务器上下载 新的apk 然后安装   
	    builer.setPositiveButton("确定", new OnClickListener() {  
	    public void onClick(DialogInterface dialog, int which) {  
	    	DebugLog.i(TAG,"下载apk,更新");  
	            downLoadApk();  
	        }     
	    });  
	    //当点取消按钮时进行登录  
	    builer.setNegativeButton("取消", new OnClickListener() {  
	        public void onClick(DialogInterface dialog, int which) {  
	            // TODO Auto-generated method stub  
	        	autoLoginRes(false);
	        }  
	    });  
	    AlertDialog dialog = builer.create();  
	    dialog.show();  
	}  
	  
	/* 
	 * 从服务器中下载APK 
	 */  
	protected void downLoadApk() {  
	    final ProgressDialog pd;    //进度条对话框  
	    pd = new  ProgressDialog(this);  
	    pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);  
	    pd.setMessage("正在下载更新");  
	    pd.show();  
	    new Thread(){  
	        @Override  
	        public void run() {  
	            try {  
	                File file = UpdateUtil.getFileFromServer(info.getUrl(), pd);  
	                sleep(3000);  
	                installApk(file);  
	                pd.dismiss(); //结束掉进度条对话框  
	            } catch (Exception e) {  
	                Message msg = new Message();  
	                msg.what = UpdateUtil.DOWN_ERROR;  
	                handler.sendMessage(msg);  
	                e.printStackTrace();  
	            }  
	        }}.start();  
	}  
	  
	//安装apk   
	protected void installApk(File file) {  
	    Intent intent = new Intent();  
	    //执行动作  
	    intent.setAction(Intent.ACTION_VIEW);  
	    //执行的数据类型  
	    intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");  
	    startActivity(intent);  
	}  
}
