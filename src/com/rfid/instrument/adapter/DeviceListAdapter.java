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
		// TODO 自动生成的方法存根
		return tagTidList.size();
	}

	@Override
	public DEVICE getItem(int position) {
		// TODO 自动生成的方法存根
		return tagTidList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO 自动生成的方法存根
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO 自动生成的方法存根
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
				holder.deviceStateText.setText("检定申请");
			}
			break;
			case 2:
			{
				holder.deviceStateText.setText("合同办理");
			}
			break;
			case 3:
			{
				holder.deviceStateText.setText("流量计接收");
			}
			break;
			case 4:
			{
				holder.deviceStateText.setText("接收区"+item.getDevicePosition()+",待检定。");
			}
			break;
			case 5:
			{
				holder.deviceStateText.setText("安装到检定台");
			}
			break;
			case 6:
			{
				holder.deviceStateText.setText("设备检定中");
			}
			break;
			case 7:
			{
				holder.deviceStateText.setText("检定完拆卸中");
			}
			break;
			case 8:
			{
				holder.deviceStateText.setText("收发区"+item.getDevicePosition()+",待取件。");
			}
			break;
			case 9:
			{
				holder.deviceStateText.setText("流量计已发放");
			}
			break;
			case 10:
			{
				holder.deviceStateText.setText("证书已发放");
			}
			break;
		}

		holder.idText.setText(""+item.getId());
		holder.deviceNameText.setText(item.getDeviceName());
		holder.orderText.setText(""+item.getOrder());
		return convertView;
	}

}
