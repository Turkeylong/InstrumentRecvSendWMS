package com.rfid.instrument.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rfid.instrument.R;
import com.rfid.instrument.util.Commission;

public class HistoryListAdapter extends BaseAdapter {
	private static final String TAG = "HistoryListAdapter";
	private Context context;
	private ArrayList<Commission> commissionList;
	
	public HistoryListAdapter(Context context,
			ArrayList<Commission> commissionList) {
		super();
		this.context = context;
		this.commissionList = commissionList;
	}
	
	public class ViewHolder
	{
		TextView idText;
		TextView deviceNameText;
		TextView checkingDateText;
		TextView checkingResultText;
		
	}

	@Override
	public int getCount() {
		// TODO 自动生成的方法存根
		return commissionList.size();
	}

	@Override
	public Commission getItem(int position) {
		// TODO 自动生成的方法存根
		return commissionList.get(position);
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
			convertView = LayoutInflater.from(context).inflate(R.layout.history_list_item, null);
			holder = new ViewHolder();
			holder.idText = (TextView) convertView.findViewById(R.id.historydeviceName);
			holder.deviceNameText = (TextView) convertView.findViewById(R.id.historydeviceName);
			holder.checkingDateText = (TextView) convertView.findViewById(R.id.checkingDate);
			holder.checkingResultText = (TextView) convertView.findViewById(R.id.checkingResult);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		
		Commission item = commissionList.get(position);
		
		holder.idText.setText(String.valueOf(position+1));
		holder.deviceNameText.setText(item.getFlowmeterName()+item.getFlowmeterModel());
		holder.checkingDateText.setText(item.getCheckingDate());
		holder.checkingResultText.setText(item.getFlowmeterResult());
		return convertView;
	}

}
