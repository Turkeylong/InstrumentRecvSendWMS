package com.rfid.instrument.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rfid.instrument.R;
import com.rfid.instrument.util.DEVICE;

public class DeviceListAdapter extends BaseAdapter {
	private static final String TAG = "DeviceListAdapter";
	private Context context;
	private ArrayList<DEVICE> tagTidList;
	
	public DeviceListAdapter(Context context, ArrayList<DEVICE> tagTidList) {
		super();
		this.context = context;
		this.tagTidList = tagTidList;
	}

	public class ViewHolder
	{
		TextView idText;
		TextView deviceNameText;
		TextView deviceStateText;
		TextView orderText;
	}
	
	@Override
	public int getCount() {
		// TODO �Զ����ɵķ������
		return tagTidList.size();
	}

	@Override
	public DEVICE getItem(int position) {
		// TODO �Զ����ɵķ������
		return tagTidList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO �Զ����ɵķ������
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO �Զ����ɵķ������
		ViewHolder holder;
		if (convertView == null)
		{
			convertView = LayoutInflater.from(context).inflate(R.layout.device_list_item, null);
			holder = new ViewHolder();
			holder.idText = (TextView) convertView.findViewById(R.id.itemid);
			holder.deviceNameText = (TextView) convertView.findViewById(R.id.deviceName);
			holder.deviceStateText = (TextView) convertView.findViewById(R.id.deviceState);
			holder.orderText = (TextView) convertView.findViewById(R.id.order);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		
		DEVICE item = tagTidList.get(position);
		
		int state = item.getDeviceState();
		switch(state)
		{
			case 1:
			{
				holder.deviceStateText.setText("�춨����");
			}
			break;
			case 2:
			{
				holder.deviceStateText.setText("��ͬ����");
			}
			break;
			case 3:
			{
				holder.deviceStateText.setText("�����ƽ���");
			}
			break;
			case 4:
			{
				holder.deviceStateText.setText("������"+item.getDevicePosition()+",���춨��");
			}
			break;
			case 5:
			{
				holder.deviceStateText.setText("��װ���춨̨");
			}
			break;
			case 6:
			{
				holder.deviceStateText.setText("�豸�춨��");
			}
			break;
			case 7:
			{
				holder.deviceStateText.setText("�춨���ж��");
			}
			break;
			case 8:
			{
				holder.deviceStateText.setText("�շ���"+item.getDevicePosition()+",��ȡ����");
			}
			break;
			case 9:
			{
				holder.deviceStateText.setText("�������ѷ���");
			}
			break;
			case 10:
			{
				holder.deviceStateText.setText("֤���ѷ���");
			}
			break;
		}

		holder.idText.setText(""+item.getId());
		holder.deviceNameText.setText(item.getDeviceName());
		holder.orderText.setText(""+item.getOrder());
		return convertView;
	}

}