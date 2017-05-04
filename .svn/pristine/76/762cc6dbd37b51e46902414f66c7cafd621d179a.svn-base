package com.rfid.instrument.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rfid.instrument.R;
import com.rfid.instrument.util.Contract;
import com.rfid.sdk.public_utils.DebugLog;

public class ContractListAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<Contract> contract_list = new ArrayList();
	
	public ContractListAdapter(Context context,ArrayList<Contract> list) 
	{
		super();
		this.context = context;
		this.contract_list = list;
	}

	public class ViewHolder
	{
		LinearLayout contractList_lin;
		TextView contractId;
		TextView contractUintName;
		TextView contractType;
		TextView contractState;
		TextView contractSigningDate;
	}
	
	@Override
	public int getCount() {
		// TODO 自动生成的方法存根
		return contract_list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO 自动生成的方法存根
		return contract_list.get(position);
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
			convertView = LayoutInflater.from(context).inflate(R.layout.contract_list_item, null);
			holder = new ViewHolder();
			holder.contractList_lin = (LinearLayout) convertView.findViewById(R.id.contractList_lin);
			holder.contractId = (TextView) convertView.findViewById(R.id.contractId);
			holder.contractUintName = (TextView) convertView.findViewById(R.id.contractUintName);
			holder.contractType = (TextView) convertView.findViewById(R.id.contractType);
			holder.contractState = (TextView) convertView.findViewById(R.id.contractState);
			holder.contractSigningDate = (TextView) convertView.findViewById(R.id.contractSigningDate);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		
		Contract temp = contract_list.get(position);
		DebugLog.d("test", temp.toString());
		
		holder.contractId.setText(""+temp.getContractNumber());
		holder.contractUintName.setText(temp.getContractUintName());
		holder.contractSigningDate.setText(temp.getContractSigningDate());
		
		int type = temp.getContractType();
		if(type == 0)
		{
			holder.contractType.setText("框架合同");
		}
		else if(type == 1)
		{
			holder.contractType.setText("普通合同");
		}
		else if(type == 2)
		{
			holder.contractType.setText("超声流量计服务合同");
		}
		int state = temp.getContractState();
		switch(state)//0，合同起草;1，合同签订（待结算）；2，合同变更，3合同办理（未结算），4、合同办理（已结算）
		{
			case 0:
				holder.contractState.setText("合同起草");
				holder.contractState.setBackgroundColor(Color.RED);
				break;
			case 1:
				holder.contractState.setText("待结算");
				holder.contractState.setBackgroundColor(Color.YELLOW);
				break;
			case 2:
				holder.contractState.setText("合同变更");
				break;
			case 3:
				holder.contractState.setText("未结算");
				holder.contractState.setBackgroundColor(Color.RED);
				break;
			case 4:
				holder.contractState.setText("已结算");
				holder.contractState.setBackgroundColor(Color.GREEN);
				break;
		}
		
		return convertView;
	}

}
