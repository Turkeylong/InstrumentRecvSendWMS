package com.rfid.instrument.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rfid.instrument.R;
import com.rfid.instrument.activity.MainActivity;
import com.rfid.instrument.fragment.ContentFragment;

public class HeaderListAdapter extends BaseAdapter
{
	private Context context;//����������
	private LayoutInflater listContainer;           //��ͼ���� 
	private List<Map<String,Object>> listData;		//���ݼ���
	public final class ListItemView{                //�Զ���ؼ�����    
        public ImageView image;    
        public TextView title;    
        public LinearLayout itemLin;       
	}    

	
	public HeaderListAdapter(Context context,List<Map<String,Object>> list)
	{
		this.context = context;
		listContainer = LayoutInflater.from(context);   //������ͼ���������������� 
		listData=list;
	}
	static class ViewHolder
	{
		ImageView  item_img_left;
		TextView   item_text;		
		LinearLayout itemLin;
	}
	@Override
	public int getCount() 
	{
		if(null!=listData)
		{
			return listData.size();	
		}
		return 0;
	}

	@Override
	public Object getItem(int arg0)
	{
		return listData.get(arg0);
	}

	@Override
	public long getItemId(int arg0)
	{
		return arg0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		//�Զ�����ͼ  
        ListItemView  listItemView = null;  
        if (convertView == null) {  
            listItemView = new ListItemView();
            //��ȡlist_item�����ļ�����ͼ  
            convertView = listContainer.inflate(R.layout.left_set_item, null);
            //��ȡ�ؼ�����  
            listItemView.image  = (ImageView) convertView.findViewById(R.id.item_img_left);
            listItemView.title = (TextView) convertView.findViewById(R.id.item_text);
            listItemView.itemLin = (LinearLayout)convertView.findViewById(R.id.itemLin);
            //���ÿؼ�����convertView  
            convertView.setTag(listItemView);  
        }
        else
        {
        	listItemView = (ListItemView)convertView.getTag();  
        }
        Map<String,Object> map = listData.get(position);
        listItemView.image.setImageBitmap((Bitmap) map.get("img"));  
        listItemView.title.setText(map.get("text")+"");
        listItemView.itemLin.setOnClickListener(new View.OnClickListener()
        {

        	@Override  
            public void onClick(View v) {  
                System.out.println("Click on the speaker image on ListItem "); 
            }  
  	  });
        return convertView;
	}

	
//	private void detail(String type)
//	{
//		Message msg=null;
//		if(ConfigUtil.LEFT_EDIT_PAWSSORD.equals(type))
//		{
//			  Intent edp=new Intent();
//			  edp.setClass(activity, EditPwdActivity.class);
//			  activity.startActivity(edp);
//		}		
//		if(ConfigUtil.LEFT_USER_INFO.equals(type))
//		{
//			 UserWork.showUserInfo(activity);
//		}	
//		
//		if(ConfigUtil.LEFT_MY_RECORD.equals(type))
//		{
//			msg=Message.obtain();
//			msg.obj=ConfigUtil.LEFT_MY_RECORD;
//			msg.what=ZKCmd.HAND_LEFT_ONCLICK;
//			handler.sendMessage(msg);
//		}	
//		
//		if(ConfigUtil.LEFT_MATERIAL_STORAGE.equals(type))
//		{
//			  Intent edp=new Intent();
//			  edp.setClass(activity, StorageMapActivity.class);
//			  activity.startActivity(edp);
//		}
//		
//		if(ConfigUtil.LEFT_MATERIAL_ADD.equals(type))
//		{
//			Intent edp=new Intent();
//			edp.setClass(activity, AddMaterialActivity.class);
//			activity.startActivity(edp);
//		}
//		
//	}
	
}