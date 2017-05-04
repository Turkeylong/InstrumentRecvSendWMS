package com.rfid.instrument.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rfid.instrument.R;
import com.rfid.instrument.util.PublicUtil;

public class TitleFragment extends Fragment {
	private TextView gobacktext,invtitle;
	private ImageView backImg,invImg;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		View view = inflater.inflate(R.layout.fragment_title, container, false);  
		
		gobacktext = (TextView)view.findViewById(R.id.gobacktext);
		invtitle = (TextView)view.findViewById(R.id.invtitle);
		backImg = (ImageView) view.findViewById(R.id.backImg);
		invImg = (ImageView) view.findViewById(R.id.invImg);
		
		if(PublicUtil.width > PublicUtil.height)
		{
			gobacktext.setVisibility(View.VISIBLE);
			invtitle.setVisibility(View.VISIBLE);
			backImg.setVisibility(View.VISIBLE);
			invImg.setVisibility(View.VISIBLE);
		}
		else
		{
			gobacktext.setVisibility(View.GONE);
			invtitle.setVisibility(View.GONE);
			backImg.setVisibility(View.GONE);
			invImg.setVisibility(View.GONE);
		}
		
		return view;
	}

}
