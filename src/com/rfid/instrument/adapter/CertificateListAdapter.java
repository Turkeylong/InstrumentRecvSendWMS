package com.rfid.instrument.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rfid.instrument.R;
import com.rfid.instrument.util.Certificate;
import com.rfid.sdk.public_utils.DebugLog;

public class CertificateListAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<Certificate> certificate_list = new ArrayList();

	public CertificateListAdapter(Context context,
			ArrayList<Certificate> certificate_list) {
		super();
		this.context = context;
		this.certificate_list = certificate_list;
	}
	
	public class ViewHolder
	{
		LinearLayout certificateList_lin;
		TextView certificateId;
		TextView deviceName;
		TextView certificateState;
		TextView certificateDate;
	}

	@Override
	public int getCount() {
		// TODO 自动生成的方法存根
		return certificate_list.size();
	}

	@Override
	public Certificate getItem(int position) {
		// TODO 自动生成的方法存根
		return certificate_list.get(position);
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
			convertView = LayoutInflater.from(context).inflate(R.layout.certificate_list_item, null);
			holder = new ViewHolder();
			holder.certificateList_lin = (LinearLayout) convertView.findViewById(R.id.certificateList_lin);
			holder.certificateId = (TextView) convertView.findViewById(R.id.certificateId);
			holder.deviceName = (TextView) convertView.findViewById(R.id.deviceName);
			holder.certificateState = (TextView) convertView.findViewById(R.id.certificateState);
			holder.certificateDate = (TextView) convertView.findViewById(R.id.certificateDate);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		
		Certificate temp = certificate_list.get(position);
		DebugLog.d("test", temp.toString());
		
		holder.certificateId.setText(""+temp.getCertificateNumber());
		holder.deviceName.setText(temp.getDeviceName()+temp.getDeviceState());
		holder.certificateState.setText(temp.getCertificateState());
		holder.certificateDate.setText(temp.getCertificateFilingDate());
		
		return convertView;
	}

}
