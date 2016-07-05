package com.rfid.instrument.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.rfid.instrument.R;
import com.rfid.instrument.adapter.HeaderListAdapter;
import com.rfid.instrument.fragment.ContentFragment;
import com.rfid.instrument.fragment.HeaderFragment;
import com.rfid.instrument.util.BitmapUtil;

public class MainActivity extends Activity {
	private HeaderFragment fragHeader;
	private ContentFragment fragContent;
	private Activity activity;
	private Handler handler;
	private ListView header_listview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		activity = this;
		handler = new Handler();
		// 设置默认的Fragment
		setDefaultFragment();
	}

	@Override
	protected void onStart() {
		// TODO 自动生成的方法存根
		super.onStart();
		initHeaderData();
	}



	@Override
	public View onCreateView(View parent, String name, Context context,
			AttributeSet attrs) {
		// TODO 自动生成的方法存根
		return super.onCreateView(parent, name, context, attrs);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO 自动生成的方法存根
		//super.onSaveInstanceState(outState);
	}

	private void setDefaultFragment() {
		// TODO 自动生成的方法存根
		DisplayMetrics displaysMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaysMetrics);
		int width = displaysMetrics.widthPixels;
		int height = displaysMetrics.heightPixels;
		int dpi = displaysMetrics.densityDpi;
		String showSize = "手机屏幕分辨率： " +  width+"*"+height+":"+dpi+"dpi";
		System.out.println(showSize);
		Toast.makeText(MainActivity.this, showSize, Toast.LENGTH_LONG).show();

		FragmentManager fm = getFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();
		fragHeader = new HeaderFragment();
		transaction.replace(R.id.fragment_header, fragHeader);
		transaction.commit();
		if(width > height)
		{
			FragmentManager cont = getFragmentManager();
			FragmentTransaction cont_transaction = cont.beginTransaction();
			fragContent = new ContentFragment();
			cont_transaction.replace(R.id.fragment_container, fragContent);
			cont_transaction.commit();
		}
	}
	
	private void initHeaderData() {
		// TODO 自动生成的方法存根
		View rootView = fragHeader.getActivity().getLayoutInflater().inflate(R.layout.fragment_header, null); //先解析fragment_header.xml布局，得到一个view
		header_listview = (ListView) rootView.findViewById(R.id.left_listview);
		System.out.println("header_listview="+header_listview.toString());
		
		String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
           "Linux", "OS/2" };

           ArrayAdapter<String> files = new ArrayAdapter<String>(fragHeader.getActivity().getApplicationContext(), 
                    android.R.layout.simple_list_item_1, 
                    values);

           header_listview.setAdapter(files);
//		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
//		Map<String,Object> map=new HashMap<String, Object>();
//		Bitmap bmp=null;
//		
//		bmp=BitmapUtil.drawableToBitmap(activity, R.drawable.find);	
//		map=new HashMap<String, Object>();
//		map.put("img", bmp);
//		map.put("type", "find_info");
//		map.put("text", "获取物资标签信息");
//		list.add(map);
//		bmp=null;
//		
//		bmp=BitmapUtil.drawableToBitmap(activity, R.drawable.exception);	
//		map=new HashMap<String, Object>();
//		map.put("img", bmp);
//		map.put("type", "exception");
//		map.put("text", "系统异常信息");
//		list.add(map);
//		bmp=null;
//		
//		bmp=BitmapUtil.drawableToBitmap(activity, R.drawable.addmat);	
//		map=new HashMap<String, Object>();
//		map.put("img", bmp);
//		map.put("type", "addmat");
//		map.put("text", "物资新增");
//		list.add(map);
//		bmp=null;
//		
//		System.out.println(list.toString());
//		HeaderListAdapter headerList = new HeaderListAdapter(fragHeader.getActivity(), list);
//		header_listview.setAdapter(headerList);
	}
}
