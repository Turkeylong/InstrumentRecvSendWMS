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
		// TODO �Զ����ɵķ������
		
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
				// TODO �Զ����ɵ� catch ��
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
//					LogUtil.i(TAG, "ִ���������");
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
	 * �������Ӽ��
	 * @return �Ƿ�����
	 */
	public boolean isConn()
	{
		if(PublicUtil.isConnect(this))
		{
			return true;
		}
		else
		{
			PublicUtil.toastShow("����������", this);
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
	 * �ӷ�������ȡxml���������бȶ԰汾��  
	 */  
	public class CheckVersionTask {

		public void execute() {
			// TODO �Զ����ɵķ������
			new Thread(new Runnable() {

		        @Override
		        public void run() {
		            // TODO Auto-generated method stub
			        try {  
			            //����Դ�ļ���ȡ������ ��ַ   
			            String path = ConstantUtil.HTTP_UPDATE_URL;  
			            //��װ��url�Ķ���   
			            URL url = new URL(path);  
			            HttpURLConnection conn =  (HttpURLConnection) url.openConnection();   
			            conn.setConnectTimeout(5000);  
			            InputStream is =conn.getInputStream();   
			            info =  UpdateUtil.getUpdataInfo(is);  
			            
			            DebugLog.d(TAG,info.toString());
			            
			            if(info.getVersion().equals(versionname))
			            {  
			                DebugLog.i(TAG,"�汾����ͬ��������");  
			                autoLoginRes(false);
			            }else{  
			            	DebugLog.i(TAG,"�汾�Ų�ͬ ,��ʾ�û����� ");  
			                Message msg = new Message();  
			                msg.what = UpdateUtil.UPDATA_CLIENT;  
			                handler.sendMessage(msg);  
			            }  
			        } catch (Exception e) {  
			            // ������   
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
	            //�Ի���֪ͨ�û���������   
	            showUpdataDialog();  
	            break;  
	        case UpdateUtil.GET_UNDATAINFO_ERROR:  
	            //��������ʱ   
	            PublicUtil.toastShow("��ȡ������������Ϣʧ��", activity); 
	            autoLoginRes(false);  
	            break;    
	        case UpdateUtil.DOWN_ERROR:  
	            //����apkʧ��  
	        	PublicUtil.toastShow("�����°汾ʧ��", activity); 
	        	autoLoginRes(false); 
	            break;    
	        }  
	    }  
	};  
	  
	/* 
	 *  
	 * �����Ի���֪ͨ�û����³���  
	 *  
	 * �����Ի���Ĳ��裺 
	 *  1.����alertDialog��builder.   
	 *  2.Ҫ��builder��������, �Ի��������,��ʽ,��ť 
	 *  3.ͨ��builder ����һ���Ի��� 
	 *  4.�Ի���show()����   
	 */  
	protected void showUpdataDialog() {  
	    AlertDialog.Builder builer = new Builder(this) ;   
	    builer.setTitle("�汾����");  
	    builer.setMessage(info.getDescription());  
	    //����ȷ����ťʱ�ӷ����������� �µ�apk Ȼ��װ   
	    builer.setPositiveButton("ȷ��", new OnClickListener() {  
	    public void onClick(DialogInterface dialog, int which) {  
	    	DebugLog.i(TAG,"����apk,����");  
	            downLoadApk();  
	        }     
	    });  
	    //����ȡ����ťʱ���е�¼  
	    builer.setNegativeButton("ȡ��", new OnClickListener() {  
	        public void onClick(DialogInterface dialog, int which) {  
	            // TODO Auto-generated method stub  
	        	autoLoginRes(false);
	        }  
	    });  
	    AlertDialog dialog = builer.create();  
	    dialog.show();  
	}  
	  
	/* 
	 * �ӷ�����������APK 
	 */  
	protected void downLoadApk() {  
	    final ProgressDialog pd;    //�������Ի���  
	    pd = new  ProgressDialog(this);  
	    pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);  
	    pd.setMessage("�������ظ���");  
	    pd.show();  
	    new Thread(){  
	        @Override  
	        public void run() {  
	            try {  
	                File file = UpdateUtil.getFileFromServer(info.getUrl(), pd);  
	                sleep(3000);  
	                installApk(file);  
	                pd.dismiss(); //�������������Ի���  
	            } catch (Exception e) {  
	                Message msg = new Message();  
	                msg.what = UpdateUtil.DOWN_ERROR;  
	                handler.sendMessage(msg);  
	                e.printStackTrace();  
	            }  
	        }}.start();  
	}  
	  
	//��װapk   
	protected void installApk(File file) {  
	    Intent intent = new Intent();  
	    //ִ�ж���  
	    intent.setAction(Intent.ACTION_VIEW);  
	    //ִ�е���������  
	    intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");  
	    startActivity(intent);  
	}  
}
