package com.rfid.instrument.activity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.widget.ListView;

import com.rfid.instrument.R;
import com.rfid.instrument.adapter.HistoryListAdapter;
import com.rfid.instrument.util.Commission;
import com.rfid.instrument.util.GetDataTask;
import com.rfid.instrument.util.PublicUtil;
import com.rfid.sdk.public_utils.DebugLog;

public class HistoryActivity extends Activity {
	protected static final String TAG = "HistoryActivity";
	private Activity activity = HistoryActivity.this;
	private ArrayList<Commission> commissionList = new ArrayList<Commission>();
	private HistoryListAdapter adapter = null;
	private ListView historylist;
	private String flowmeterId = "";
	private Handler handler = new Handler(){
		public void handleMessage(Message msg) 
		{
			switch (msg.what) 
			{
				case PublicUtil.RESP_FLOWMETER_INFO:
				{
					String res=msg.obj+"";
					DebugLog.i(TAG, "RESP_FLOWMETER_INFO:"+res);
					PraseCommissionJSON(res);
					ShowHistoryList();
					break;
				}
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_history);
		
		initView();
	}

	private void initView() {
		// TODO 自动生成的方法存根
		historylist = (ListView) findViewById(R.id.historylist);
		adapter = new HistoryListAdapter(activity,commissionList);
		historylist.setAdapter(adapter);
	}

	private void ShowHistoryList() {
		// TODO 自动生成的方法存根
		adapter.notifyDataSetChanged();
		DebugLog.d(TAG, "ShowHistoryList");
	}

	@Override
	protected void onStart() {
		// TODO 自动生成的方法存根
		super.onStart();
		Intent intent = getIntent();
		flowmeterId = intent.getStringExtra("commissionList");
		PraseCommission();
		
	}
	
	private void PraseCommission() {
		// TODO 自动生成的方法存根
		StringBuffer sb=new StringBuffer();
		sb.append("t=102");
		sb.append("&flowmeterId="+flowmeterId);
		
		GetDataTask mrequstTask=new GetDataTask(activity, handler);
		
		mrequstTask.setHandlerCode(PublicUtil.RESP_FLOWMETER_INFO);
		mrequstTask.setParams(sb.toString());
		mrequstTask.execute("");
	}

	private void PraseCommissionJSON(String result) {
		// TODO 自动生成的方法存根
		try
		{
			JSONObject jsonObject = new JSONObject(result).getJSONObject("result");
			JSONArray jsonArr = jsonObject.getJSONArray("DataList");
			
			int totalCount=jsonObject.getInt("totalCount");	
			
			DebugLog.i(TAG,"totalCount："+totalCount+",size="+jsonArr.length());
			
			if(totalCount == jsonArr.length())
			{
				commissionList.clear();
				for(int i=0;i<totalCount;i++)
				{
					JSONObject jsonObj=(JSONObject)jsonArr.get(i);
					
					Commission order = new Commission();
					
					 order.setId(jsonObj.getInt("entrustedInspectionId"));
					 order.setFlowmeterId(jsonObj.getString("flowmeterId"));
					 
					 order.setClientsContacts(jsonObj.getString("clientsContacts"));
					 order.setClientsEmployer(jsonObj.getString("clientsEmployer"));
					 order.setClientsEmployerAddress(jsonObj.getString("clientsEmployerAddress"));
					 order.setClientsPhone(jsonObj.getString("clientsPhone"));
					 order.setClientsEmail(jsonObj.getString("clientsEmail"));
					 order.setClientsPostcode(jsonObj.getString("clientsPostcode"));
					 
					 order.setFlowmeterName(jsonObj.getString("flowmeterName"));
					 order.setFlowmeterCaliber(jsonObj.getString("flowmeterCaliber"));
					 order.setFlowmeterInside(jsonObj.getString("flowmeterInside"));
					 order.setFlowmeterLength(jsonObj.getString("flowmeterLength"));
					 order.setFlowmeterModel(jsonObj.getString("flowmeterModel"));
					 order.setFlowmeterFactoryNumber(jsonObj.getString("flowmeterFactoryNumber"));
					 order.setFlowmeterManufactor(jsonObj.getString("flowmeterManufactor"));
					 order.setFlowmeterUseState(jsonObj.getString("flowmeterUseState"));
					 order.setFlowmeterNominalFlow(jsonObj.getString("flowmeterNominalFlow"));
					 
					 order.setFlowmeterPosition(jsonObj.getString("flowmeterPosition"));
					 order.setFlowmeterResult(jsonObj.getString("checkingQualified"));
					 
					 order.setFlowmeterKcoefficient(jsonObj.getString("flowmeterKcoefficient"));
					 order.setFlowmeterPressureLevel(jsonObj.getString("flowmeterPressureLevel"));
					 order.setFlowmeterAccessories(jsonObj.getString("flowmeterAccessories"));
					 order.setFlowmeterAppearance(jsonObj.getString("flowmeterAppearance"));
					 order.setSampleDocuments(jsonObj.getString("sampleDocuments"));
					 order.setFlowmeterWorkTypes(jsonObj.getString("flowmeterWorkTypes"));
					 order.setFlowmeterAccuracyLevel(jsonObj.getString("flowmeterAccuracyLevel"));
					 order.setFlowmeterCommonFlow(jsonObj.getString("flowmeterCommonFlow"));
					 order.setRemarks(jsonObj.getString("Remarks"));
					 order.setEntrustedPerson(jsonObj.getString("entrustedPerson"));
					 order.setEntrustedDate(jsonObj.getString("entrustedDate"));
					 order.setSampleRecipient(jsonObj.getString("sampleRecipient"));
					 order.setSampleTakePerson(jsonObj.getString("sampleTakePerson"));
					 order.setSampleTakeDate(jsonObj.getString("sampleTakeDate"));
					 order.setSampleSentPerson(jsonObj.getString("sampleSentPerson"));
						
					 order.setAccessoriesFrontStraightPipe(PublicUtil.getIntValue(jsonObj.getString("accessoriesFrontStraightPipe")));
					 order.setAccessoriesRearStraightPipe(PublicUtil.getIntValue(jsonObj.getString("accessoriesRearStraightPipe")));
					 order.setAccessoriesRectifier(PublicUtil.getIntValue(jsonObj.getString("accessoriesRectifier")));
					 order.setAccessoriesJoint(PublicUtil.getIntValue(jsonObj.getString("accessoriesJoint")));
					 order.setAccessoriesShim(PublicUtil.getIntValue(jsonObj.getString("accessoriesShim")));
					 order.setAccessoriesWasher(PublicUtil.getIntValue(jsonObj.getString("accessoriesWasher")));
					 order.setAccessoriesBoltNut(PublicUtil.getIntValue(jsonObj.getString("accessoriesBoltNut")));

					 order.setCheckingProject(jsonObj.getString("checkingProject"));
					 order.setCheckingProcessState(jsonObj.getInt("checkingProcessState"));
					 order.setCheckingPrice(jsonObj.getString("checkingPrice"));
					 order.setCheckingDate(jsonObj.getString("checkingDate"));
					 order.setContractId(jsonObj.getInt("contractId"));
					 
					commissionList.add(order);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		DebugLog.d(TAG, "size:"+commissionList.size());
	}
	
}
