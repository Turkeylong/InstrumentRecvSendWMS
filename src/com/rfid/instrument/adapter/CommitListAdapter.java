package com.rfid.instrument.adapter;

import java.io.Serializable;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rfid.instrument.R;
import com.rfid.instrument.activity.CommitEditActivity;
import com.rfid.instrument.util.Commission;

public class CommitListAdapter extends BaseAdapter {
	private static final String TAG = "CommitListAdapter";
	private Activity activity;
	private ArrayList<Commission> commitList;
	
	public CommitListAdapter(Activity act, ArrayList<Commission> commitList) {
		super();
		this.activity = act;
		this.commitList = commitList;
	}

	public class ViewHolder
	{
		TextView idText;
		TextView deviceNameText;
		TextView deviceStateText;
		TextView orderText;
		
		LinearLayout deviceitem;
	}
	
	public void addItem(ArrayList<Commission> item)
	{
		commitList.addAll(item);
	}
	
	@Override
	public int getCount() {
		// TODO 自动生成的方法存根
		return commitList.size();
	}

	@Override
	public Commission getItem(int position) {
		// TODO 自动生成的方法存根
		return commitList.get(position);
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
			convertView = LayoutInflater.from(activity).inflate(R.layout.device_list_item, null);
			holder = new ViewHolder();
			holder.deviceitem = (LinearLayout) convertView.findViewById(R.id.deviceitem);
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
		
		final Commission item = commitList.get(position);
		
		holder.idText.setText(""+(position+1));
		holder.deviceNameText.setText(item.getFlowmeterName()+item.getFlowmeterModel());
		holder.orderText.setText(""+item.getId());
		
		int state = item.getCheckingProcessState();
		
		if(state < 3)
		{
			holder.deviceNameText.setBackgroundColor(Color.YELLOW);
			holder.deviceitem.setOnClickListener(new View.OnClickListener()
			{

				@Override
				public void onClick(View v) {
					// TODO 自动生成的方法存根
					getItemDetail(item);
				}
				
			});
		}
		else
		{
			holder.deviceNameText.setBackgroundColor(Color.WHITE);
		}
		
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
				//holder.deviceitem.setBackgroundColor(Color.YELLOW);
			}
			break;
			case 3:
			{
				holder.deviceStateText.setText("流量计接收");
			}
			break;
			case 4:
			{
				holder.deviceStateText.setText("接收区"+item.getFlowmeterPosition()+",待检定。");
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
				holder.deviceStateText.setText("发放区"+item.getFlowmeterPosition()+",待取件。");
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
		
		return convertView;
	}

	private void getItemDetail(Commission item) {
		// TODO 自动生成的方法存根
		Intent intent = new Intent();
		intent.setClass(activity, CommitEditActivity.class);
		intent.putExtra("Commission", (Serializable)item);
		activity.startActivity(intent);
	}
}
