package com.rfid.instrument.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rfid.instrument.R;
import com.rfid.instrument.adapter.HeaderListAdapter;
import com.rfid.instrument.util.BitmapUtil;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HeaderFragment extends Fragment {
	private Context context;
	private ListView header_listview;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onCreate(savedInstanceState);
		context = getActivity();
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		if (container == null) {
	          return null;
	       }
		View rootView = inflater.inflate(R.layout.fragment_header, null);
		header_listview = (ListView) rootView.findViewById(R.id.left_listview);

		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		Map<String,Object> map=new HashMap<String, Object>();
		Bitmap bmp=null;                        

		
		bmp=BitmapUtil.drawableToBitmap(getActivity(), R.drawable.find);	
		map=new HashMap<String, Object>();
		map.put("img", bmp);				//ͼƬ��Դ
		map.put("type", "find_info");		//��Ʒ���� 
		map.put("text", "�����豸ת��״̬��λ��");	//��Ʒ����
		list.add(map);						//���һ��
		bmp=null;
		
		bmp=BitmapUtil.drawableToBitmap(getActivity(), R.drawable.exception);	
		map=new HashMap<String, Object>();
		map.put("img", bmp);
		map.put("type", "exception");
		map.put("text", "ϵͳ�쳣��Ϣ");
		list.add(map);
		bmp=null;
		
		bmp=BitmapUtil.drawableToBitmap(getActivity(), R.drawable.addmat);	
		map=new HashMap<String, Object>();
		map.put("img", bmp);
		map.put("type", "addmat");
		map.put("text", "�����豸���");
		list.add(map);
		bmp=null;
		
		System.out.println(list.toString());
		HeaderListAdapter headerList = new HeaderListAdapter(context, list);
		header_listview.setAdapter(headerList);
		return  rootView;
	}

}
