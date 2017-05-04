package com.rfid.instrument;

import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.DisplayMetrics;

import com.rfid.instrument.activity.AutoUpdateActivty;
import com.rfid.instrument.util.RfidUtil;
import com.rfid.sdk.public_utils.DebugLog;
import com.rfid.sdk.public_utils.Public;

public class BootBroadcastReceiver extends BroadcastReceiver {
	private static final String TAG ="BootBroadcastReceiver"; 
	private static final String action_boot="android.intent.action.BOOT_COMPLETED"; 
	private static final String action_sreenoff="android.intent.action.SCREEN_OFF"; 
	public static RfidUtil rfid = null;
	public static final Context context = null;
	// ���̹�����  
	private static KeyguardManager mKeyguardManager;  
    // ������  
    private static KeyguardLock mKeyguardLock;  
    // ��Դ������  
    private static PowerManager mPowerManager;  
    // ������  
    private static PowerManager.WakeLock mWakeLock;  


	@Override
	public IBinder peekService(Context myContext, Intent service) {
		// TODO �Զ����ɵķ������
		
		mPowerManager = (PowerManager) myContext.getSystemService(Context.POWER_SERVICE); 
		mKeyguardManager = (KeyguardManager) myContext.getSystemService(Context.KEYGUARD_SERVICE);
		mWakeLock = mPowerManager.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.PARTIAL_WAKE_LOCK, "Tag");
		mKeyguardLock = mKeyguardManager.newKeyguardLock("");
		return super.peekService(myContext, service);
	}


	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO �Զ����ɵķ������
		
		String action = intent.getAction();
		DebugLog.d(TAG, action);
		if (Intent.ACTION_BOOT_COMPLETED.equals(action))
		{
			DisplayMetrics displaydm = context.getResources().getDisplayMetrics();
			//Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
			//if(display.getWidth() > display.getHeight())
			if(displaydm.widthPixels > displaydm.heightPixels)
			{
				Intent StartIntent=new Intent(context,AutoUpdateActivty.class); //���յ��㲥����ת��MainActivity 
				StartIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
				context.startActivity(StartIntent);
			}
		}
		else if(Intent.ACTION_SCREEN_OFF.equals(action))
		{
			rfid = RfidUtil.getInstance(context);
			rfid.reset();
			rfid.setPower(false);
			DebugLog.d(TAG, action_sreenoff);
		}
		else if(action.equals(Public.BROADCAST_BUTTON_ACTION))
		{
			
//			if(!mPowerManager.isScreenOn())
//			{
//				mWakeLock = mPowerManager.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.PARTIAL_WAKE_LOCK, "Tag");
//				mWakeLock.acquire();
//				 
//				mKeyguardLock.disableKeyguard();
//			}
//			else
//			{
//				//mWakeLock = mPowerManager.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.PARTIAL_WAKE_LOCK, "Tag");
//				//mWakeLock.release();
//				mKeyguardLock.reenableKeyguard();
//			}
			
			
			String msg = (String) intent.getExtras().get("button");
			
			DebugLog.d(TAG, msg);
			
			if(msg.equals("left_btn_Down"))
			{
				if(mPowerManager == null)
				{
					mPowerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE); 
				}
				if (mWakeLock == null) 
				{
					mWakeLock = mPowerManager.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK, "Tag");
			        //mWakeLock = mPowerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP| PowerManager.ON_AFTER_RELEASE, "MyWakeLock");
			    }

				mWakeLock.setReferenceCounted(true);
			    mWakeLock.acquire();
			}
			else if(msg.equals("left_btn_Up"))
			{
//				if (mWakeLock == null) {
//					mWakeLock = mPowerManager.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK, "Tag");
//			        //mWakeLock = mPowerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP| PowerManager.ON_AFTER_RELEASE, "MyWakeLock");
//			    }
				try{
			        mWakeLock.release();//always release before acquiring for safety just in case
			    }
			    catch(Exception e){
			        //probably already released
			    	DebugLog.e(TAG, e.getMessage());
			    }
			}
			else if(msg.equals("right_btn_Down"))
			{
				if(null!=mWakeLock)
				{
					mWakeLock.acquire();
					//mKeyguardLock.disableKeyguard();
				}
			}
			else if(msg.equals("right_btn_Up"))
			{
				if((null!=mWakeLock) && (mWakeLock.isHeld()))
					mWakeLock.release();
			}
		    // ��ʼ��������  
		     
		    // ���̽���  
		    
		}
	}

}