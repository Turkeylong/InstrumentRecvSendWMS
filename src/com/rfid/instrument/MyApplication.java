package com.rfid.instrument;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;

import com.rfid.instrument.util.RfidUtil;

public class MyApplication extends Application
{
	public static Context context;
	public static RfidUtil rfid;
	private BootBroadcastReceiver mScreenBroadcastReceiver;
	private Handler handler = new Handler();
	@Override
	public void onCreate() {
		// TODO 自动生成的方法存根
		context = getApplicationContext();
		rfid = new RfidUtil(context);
		rfid.setHandler(handler);
		rfid.connectReader();
		rfid.setPower(false);
		registerScreenBroadcastReceiver();
		super.onCreate();
	}
	
	private void registerScreenBroadcastReceiver() {
		// TODO 自动生成的方法存根
		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_BOOT_COMPLETED);
		filter.addAction(Intent.ACTION_SCREEN_ON);
		filter.addAction(Intent.ACTION_SCREEN_OFF);
		filter.addAction("com.rfid.sdk.protocol.ACTION");
		registerReceiver(mScreenBroadcastReceiver, filter);
	}
}
