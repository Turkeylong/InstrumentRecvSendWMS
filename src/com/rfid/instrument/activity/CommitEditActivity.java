package com.rfid.instrument.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.rfid.instrument.R;
import com.rfid.instrument.util.Commission;
import com.rfid.instrument.util.Contract;
import com.rfid.instrument.util.GetDataTask;
import com.rfid.instrument.util.PublicUtil;
import com.rfid.instrument.util.RfidUtil;
import com.rfid.sdk.public_utils.DebugLog;

public class CommitEditActivity extends Activity implements OnClickListener {
	protected static final String TAG = "CommitEditActivity";
	private Context context = CommitEditActivity.this;
	private EditText entrustedInspectionNumber;//�ͼ���Ʒ���',
	private EditText clientsContacts;//��ϵ��',
	private EditText clientsEmployer;//��λ',
	private EditText clientsEmployerAddress;//��λ��ַ',
	private EditText clientsPhone;//�绰',
	private EditText clientsPostcode;//��������'
	private EditText certificateClientsEmployer;//֤��ͻ���λ,
	private EditText certificateClientsEmployerAddress;//֤��ͻ���λ��ַ,
	
	private EditText flowmeterName;//����������',
	private EditText flowmeterCaliber;//�����ƿھ�',
	private EditText flowmeterInside;//�ھ�',
	private EditText flowmeterLength;//����',
	private EditText flowmeterModel;//�������ͺ�',
	private EditText flowmeterFactoryNumber;//�����Ƴ������'
	private EditText flowmeterManufactor;//��������������',
	private EditText flowmeterUseState;//������ʹ��״̬',
	private EditText flowmeterNominalFlow;//�������',
	private EditText flowmeterKcoefficient;//Kϵ��',
	private EditText flowmeterPressureLevel;//��������ѹ�ȼ�',
	
	//private TextView flowmeterAccessories;//��������',
	//private TextView flowmeterAppearance;//���',
	//private TextView sampleDocuments;//��Ʒ����',
	private EditText flowmeterWorkTypes;//��������', 
	private EditText flowmeterAccuracyLevel;//������׼ȷ�ȵȼ�',
	private EditText flowmeterCommonFlow;//�����Ƴ�������',
	private EditText clientRequirement;//�ͻ�Ҫ��,
	private EditText flowmeterRemarks;//��ע',

	private EditText entrustedPerson;//ί����',
	
	private EditText entrustedDate;//ί������',
	
	private EditText flowmeterPosition;//���λ��
	
	private Spinner spinner1,spinner2,spinner3,spinner4,spinner5,spinner6,spinner7;
	private ArrayAdapter<String> arr_adapter;
	private CheckBox checkBox6,checkBox7,checkBox8,checkBox9,checkBox10;
	
	private Button btnWriteFlowEPC,btnWriteCommEPC,btnCreateCommit,btnReturn;
	public static RfidUtil rfid = null;
	private Commission order = null;
	
	private Handler handler = new Handler()
	{
		public void handleMessage(Message msg) 
		{
			switch (msg.what) 
			{
				case PublicUtil.RESP_CONTRACT_INFO:
				{
					String res=msg.obj+"";
					ParseJson(res);
					break;
				}
				case PublicUtil.RESP_COMMITE_INFO:
				{
					String res=msg.obj+"";
					try
					{
						JSONObject jsonObject = new JSONObject(res).getJSONObject("result");
						int state=jsonObject.getInt("state");	
						if(state == 1)
						{
							PublicUtil.toastShow("���ճɹ�", context);
						}
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					break;
				}
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_commition);
		
		initView();
	}

	private void initView() {
		// TODO �Զ����ɵķ������
		// ��������Դ
		String[] mItems = getResources().getStringArray(R.array.num);
		//������
        arr_adapter= new ArrayAdapter<String>(this,R.layout.spinner_item, mItems);
        		//new ArrayAdapter<String>(this, R.layout.spinner_item, mItems);
        //������ʽ
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
		spinner1=(Spinner) findViewById(R.id.spinner1);
		spinner2=(Spinner) findViewById(R.id.spinner2);
		spinner3=(Spinner) findViewById(R.id.spinner3);
		spinner4=(Spinner) findViewById(R.id.spinner4);
		spinner5=(Spinner) findViewById(R.id.spinner5);
		spinner6=(Spinner) findViewById(R.id.spinner6);
		spinner7=(Spinner) findViewById(R.id.spinner7);
		
		spinner1.setAdapter(arr_adapter);
		spinner2.setAdapter(arr_adapter);
		spinner3.setAdapter(arr_adapter);
		spinner4.setAdapter(arr_adapter);
		spinner5.setAdapter(arr_adapter);
		spinner6.setAdapter(arr_adapter);
		spinner7.setAdapter(arr_adapter);
		
		//checkBox6,checkBox7,checkBox8,checkBox9,checkBox10
		checkBox6=(CheckBox) findViewById(R.id.checkBox6);
		checkBox7=(CheckBox) findViewById(R.id.checkBox7);
		checkBox8=(CheckBox) findViewById(R.id.checkBox8);
		checkBox9=(CheckBox) findViewById(R.id.checkBox9);
		checkBox10=(CheckBox) findViewById(R.id.checkBox10);
		
		flowmeterPosition = (EditText) findViewById(R.id.edit_flowmeterPosition);
		entrustedInspectionNumber = (EditText) findViewById(R.id.edit_entrustedInspectionNumber);
		clientsContacts = (EditText) findViewById(R.id.edit_clientsContacts);
		clientsEmployer = (EditText) findViewById(R.id.edit_clientsEmployer);
		clientsEmployerAddress = (EditText) findViewById(R.id.edit_clientsEmployerAddress);
		clientsPhone = (EditText) findViewById(R.id.edit_clientsPhone);
		clientsPostcode = (EditText) findViewById(R.id.edit_clientsPostcode);
		certificateClientsEmployer = (EditText) findViewById(R.id.edit_certificateClientsEmployer);
		certificateClientsEmployerAddress = (EditText) findViewById(R.id.edit_certificateClientsEmployerAddress);
		
		flowmeterName = (EditText) findViewById(R.id.edit_flowmeterName);
		flowmeterCaliber = (EditText) findViewById(R.id.edit_flowmeterCaliber);
		flowmeterInside = (EditText) findViewById(R.id.edit_flowmeterInside);
		flowmeterLength = (EditText) findViewById(R.id.edit_flowmeterLength);
		flowmeterModel = (EditText) findViewById(R.id.edit_flowmeterModel);
		flowmeterFactoryNumber = (EditText) findViewById(R.id.edit_flowmeterFactoryNumber);
		flowmeterManufactor = (EditText) findViewById(R.id.edit_flowmeterManufactor);
		flowmeterUseState = (EditText) findViewById(R.id.edit_flowmeterUseState);
		flowmeterNominalFlow = (EditText) findViewById(R.id.edit_flowmeterNominalFlow);
		flowmeterKcoefficient = (EditText) findViewById(R.id.edit_flowmeterKcoefficient);
		flowmeterPressureLevel = (EditText) findViewById(R.id.edit_flowmeterPressureLevel);
		
		flowmeterWorkTypes = (EditText) findViewById(R.id.edit_flowmeterWorkTypes);
		flowmeterAccuracyLevel = (EditText) findViewById(R.id.edit_flowmeterAccuracyLevel);
		flowmeterCommonFlow = (EditText) findViewById(R.id.edit_flowmeterCommonFlow);
		clientRequirement = (EditText) findViewById(R.id.edit_clientRequirement);
		flowmeterRemarks = (EditText) findViewById(R.id.edit_flowmeterRemarks);
		
		btnWriteFlowEPC = (Button) findViewById(R.id.btnWriteFlowEPC);
		btnWriteCommEPC = (Button) findViewById(R.id.btnWriteCommEPC);
		btnCreateCommit = (Button) findViewById(R.id.btnCreateCommit);
		btnReturn = (Button) findViewById(R.id.btnReturn);
		
		btnWriteFlowEPC.setOnClickListener(this);
		btnWriteCommEPC.setOnClickListener(this);
		btnCreateCommit.setOnClickListener(this);
		btnReturn.setOnClickListener(this);
		
		rfid = RfidUtil.getInstance(context);
		rfid.reset();
		rfid.setPower(false);
	}
	
	@Override
	protected void onStart() {
		// TODO �Զ����ɵķ������
		super.onStart();
		Intent intent = getIntent();
		order = (Commission) intent.getSerializableExtra("Commission");
		if(order != null)
		{
			entrustedInspectionNumber.setText(order.getEntrustedInspectionNumber());
			clientsContacts.setText(order.getClientsContacts());
			clientsEmployer.setText(order.getClientsEmployer());
			clientsEmployerAddress.setText(order.getClientsEmployerAddress());
			clientsPhone.setText(order.getClientsPhone());
			clientsPostcode.setText(order.getClientsPostcode());
			
			certificateClientsEmployer.setText(order.getCertificateClientsEmployer());
			certificateClientsEmployerAddress.setText(order.getCertificateClientsEmployerAddress());
			flowmeterName.setText(order.getFlowmeterName());
			flowmeterCaliber.setText(order.getFlowmeterCaliber());
			flowmeterInside.setText(order.getFlowmeterInside());
			flowmeterLength.setText(order.getFlowmeterLength());
			flowmeterModel.setText(order.getFlowmeterModel());
			flowmeterFactoryNumber.setText(order.getFlowmeterFactoryNumber());
			flowmeterManufactor.setText(order.getFlowmeterManufactor());
			flowmeterUseState.setText(order.getFlowmeterUseState());
			flowmeterNominalFlow.setText(order.getFlowmeterNominalFlow());
			flowmeterKcoefficient.setText(order.getFlowmeterKcoefficient());
			flowmeterPressureLevel.setText(order.getFlowmeterPosition());
			
			//flowmeterAccessories.setText(order.getFlowmeterAccessories());
			//flowmeterAppearance.setText(order.getFlowmeterAppearance());
			//sampleDocuments.setText(order.getSampleDocuments());
			flowmeterWorkTypes.setText(order.getFlowmeterWorkTypes());
			flowmeterAccuracyLevel.setText(order.getFlowmeterAccuracyLevel());
			flowmeterCommonFlow.setText(order.getFlowmeterCommonFlow());
			clientRequirement.setText(order.getClientsRequirement());
			flowmeterRemarks.setText(order.getRemarks());

//			entrustedPerson.setText(order.getEntrustedPerson());
//			entrustedDate.setText(order.getEntrustedDate());
		}
	}
	
	private void ParseJson(String result) {
		// TODO �Զ����ɵķ������
		try
		{
			JSONObject jsonObject = new JSONObject(result).getJSONObject("result");
			JSONArray jsonArr = jsonObject.getJSONArray("ContractList");
			
			int totalCount=jsonObject.getInt("totalCount");	
			DebugLog.i(TAG,"totalCount��"+totalCount+",size="+jsonArr.length());
			
			if(totalCount == 1)
			{
				JSONObject jsonObj=(JSONObject)jsonArr.get(0);
				Contract obj = new Contract();
				
				obj.setContractId(jsonObj.getInt("contractId"));
				obj.setContractNumber(jsonObj.getString("contractNumber"));
				obj.setContractUintName(jsonObj.getString("contractUintName"));
				obj.setContractCreationUserName(jsonObj.getString("contractCreationUserName"));
				obj.setContractCreationDate(jsonObj.getString("contractCreationDate"));
				obj.setContractSigningUserName(jsonObj.getString("contractSigningUserName"));
				obj.setContractSigningDate(jsonObj.getString("contractSigningDate"));
				obj.setContractType(jsonObj.getInt("contractType"));
				obj.setContractState(jsonObj.getInt("contractState"));
				obj.setContractPrice(jsonObj.getInt("contractPrice"));
				
				int contract_state = obj.getContractState();
				DebugLog.i(TAG,"contract_state��"+contract_state);
				if(contract_state > 0)
				{
					if(obj.getContractId() > 0)
					{
						submitRecevice(order);
					}
					else
					{
						PublicUtil.toastShow("��ͬ��δǩ�������ܽ��ա�", context);
					}
				}
				else
				{
					PublicUtil.toastShow("��ͬ��δǩ�������ܽ��ա�", context);
				}
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void submitRecevice(Commission order)
	{
		order.setClientsContacts(clientsContacts.getText().toString());
		order.setClientsPhone(clientsPhone.getText().toString());
		order.setClientsPostcode(clientsPostcode.getText().toString());
		order.setClientsEmployer(clientsEmployer.getText().toString());
		order.setClientsEmployerAddress(clientsEmployerAddress.getText().toString());
		order.setClientsRequirement(clientRequirement.getText().toString());
		order.setCertificateClientsEmployer(certificateClientsEmployer.getText().toString());
		order.setCertificateClientsEmployerAddress(certificateClientsEmployerAddress.getText().toString());
		
		order.setFlowmeterName(flowmeterName.getText().toString());
		order.setFlowmeterCaliber(flowmeterCaliber.getText().toString());
		order.setFlowmeterInside(flowmeterInside.getText().toString());
		order.setFlowmeterLength(flowmeterLength.getText().toString());
		order.setFlowmeterModel(flowmeterModel.getText().toString());
		order.setFlowmeterFactoryNumber(flowmeterFactoryNumber.getText().toString());
		order.setFlowmeterManufactor(flowmeterManufactor.getText().toString());
		order.setFlowmeterUseState(flowmeterUseState.getText().toString());
		order.setFlowmeterWorkTypes(flowmeterWorkTypes.getText().toString());
		order.setFlowmeterAccuracyLevel(flowmeterAccuracyLevel.getText().toString());
		order.setFlowmeterCommonFlow(flowmeterCommonFlow.getText().toString());
		order.setRemarks(flowmeterRemarks.getText().toString());
		order.setFlowmeterNominalFlow(flowmeterNominalFlow.getText().toString());
		order.setFlowmeterKcoefficient(flowmeterKcoefficient.getText().toString());
		order.setFlowmeterPressureLevel(flowmeterPressureLevel.getText().toString());
		
		//spinner1,spinner2,spinner3,spinner4,spinner5,spinner6,spinner7
        order.setAccessoriesFrontStraightPipe(spinner1.getSelectedItemPosition());
        order.setAccessoriesRearStraightPipe(spinner2.getSelectedItemPosition());
        order.setAccessoriesRectifier(spinner3.getSelectedItemPosition());
        order.setAccessoriesJoint(spinner4.getSelectedItemPosition());
        order.setAccessoriesShim(spinner5.getSelectedItemPosition());
        order.setAccessoriesWasher(spinner6.getSelectedItemPosition());
        order.setAccessoriesBoltNut(spinner7.getSelectedItemPosition());
		
        ArrayList<CheckBox> cb_flowmeterAppearance = new ArrayList<CheckBox>();
        cb_flowmeterAppearance.add(checkBox6);
        cb_flowmeterAppearance.add(checkBox7);
        cb_flowmeterAppearance.add(checkBox8);
        cb_flowmeterAppearance.add(checkBox9);
        cb_flowmeterAppearance.add(checkBox10);
        
        StringBuffer flowmeterAppearance = new StringBuffer();

  		 for(CheckBox tmp:cb_flowmeterAppearance)
  		 {
			 if(tmp.isChecked())
			 {
				 flowmeterAppearance.append(tmp.getText()+"��");
			 } 
  		 }
  		 order.setFlowmeterAppearance(flowmeterAppearance.toString());
		
		GetDataTask mrequstTask=new GetDataTask(context, handler);
		StringBuffer sb=new StringBuffer();
		sb.append("t="+PublicUtil.REQ_COMMITE_INFO);
		sb.append("&flowmeterId="+order.getFlowmeterId());
		
		sb.append("&entrustedInspectionId="+order.getId());
		
		if(flowmeterPosition.getText().toString().isEmpty())
			sb.append("&checkingProcessState=3");
		else
		{
			sb.append("&checkingProcessState=4");
			sb.append("&flowmeterPosition="+flowmeterPosition.getText().toString());
		}
		sb.append("&clientsContacts="+order.getClientsContacts());
		sb.append("&clientsEmployer="+order.getClientsEmployer());
		sb.append("&clientsEmployerAddress="+order.getClientsEmployerAddress());
		sb.append("&certificateClientsEmployer="+order.getCertificateClientsEmployer());
		sb.append("&certificateClientsEmployerAddress="+order.getCertificateClientsEmployerAddress());
		sb.append("&clientsPhone="+order.getClientsPhone());
		sb.append("&clientsPostcode="+order.getClientsPostcode());
		sb.append("&clientsEmail="+order.getClientsEmail());
		sb.append("&clientsRequirement="+order.getClientsRequirement());
		
		sb.append("&flowmeterName="+order.getFlowmeterName());
		sb.append("&flowmeterCaliber="+order.getFlowmeterCaliber());
		sb.append("&flowmeterInside="+order.getFlowmeterInside());
		sb.append("&flowmeterLength="+order.getFlowmeterLength());
		sb.append("&flowmeterModel="+order.getFlowmeterModel());
		sb.append("&flowmeterAccessories="+order.getFlowmeterAccessories());
		sb.append("&flowmeterAppearance="+order.getFlowmeterAppearance());
		sb.append("&sampleDocuments="+order.getSampleDocuments());
		sb.append("&flowmeterWorkTypes="+order.getFlowmeterWorkTypes());
		sb.append("&flowmeterAccuracyLevel="+order.getFlowmeterAccuracyLevel());
		sb.append("&flowmeterCommonFlow="+order.getFlowmeterCommonFlow());
		sb.append("&flowmeterManufactor="+order.getFlowmeterManufactor());
		sb.append("&flowmeterFactoryNumber="+order.getFlowmeterFactoryNumber());
		sb.append("&flowmeterUseState="+order.getFlowmeterUseState());
		
		//accessoriesFrontStraightPipe=0, accessoriesRearStraightPipe=0, accessoriesRectifier=0, accessoriesJoint=0, accessoriesShim=0, accessoriesWasher=0, accessoriesBoltNut=0
		sb.append("&accessoriesFrontStraightPipe="+order.getAccessoriesFrontStraightPipe());
		sb.append("&accessoriesRearStraightPipe="+order.getAccessoriesRearStraightPipe());
		sb.append("&accessoriesRectifier="+order.getAccessoriesRectifier());
		sb.append("&accessoriesJoint="+order.getAccessoriesJoint());
		sb.append("&accessoriesShim="+order.getAccessoriesShim());
		sb.append("&accessoriesWasher="+order.getAccessoriesWasher());
		sb.append("&accessoriesBoltNut="+order.getAccessoriesBoltNut());
		
		sb.append("&Remarks="+order.getRemarks());
		sb.append("&sampleRecipient="+PublicUtil.USER_NAME);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");    
		String date=sdf.format(new java.util.Date());
		sb.append("&entrustedDate="+date);
		
		mrequstTask.setHandlerCode(PublicUtil.RESP_COMMITE_INFO);
		mrequstTask.setParams(sb.toString());
		mrequstTask.execute("");
	}

	@Override
	public void onClick(View v) {
		// TODO �Զ����ɵķ������
		switch (v.getId())
		{
			case R.id.btnWriteFlowEPC:
			{
				String flowmeterId = order.getFlowmeterId();
				if(flowmeterId.isEmpty())
				{
					
				}
				else
				{
					String hexflowmeterId = String.format("%032X", Integer.valueOf(flowmeterId));//Integer.toHexString(Integer.valueOf(flowmeterId));
					//PublicUtil.toastShow("�豸��ţ�"+hexflowmeterId, context);
					rfid.setPower(true);
					SystemClock.sleep(500);
					rfid.writeEPC(hexflowmeterId);
				}
				break;
			}
			case R.id.btnWriteCommEPC:
			{
				String CommNum = order.getEntrustedInspectionNumber();
				if(CommNum.isEmpty())
				{
					PublicUtil.toastShow("��δ����ί�е��ţ����Ȱ����춨ί�С�", context);
				}
				break;
			}
			case R.id.btnCreateCommit:
			{
				int state = order.getCheckingProcessState();
				if(state == 2)
				{
					GetDataTask mrequstTask=new GetDataTask(context, handler);
					StringBuffer sb=new StringBuffer();
					sb.append("t="+PublicUtil.REQ_CONTRACT_STATE);
					sb.append("&flowmeterId="+order.getFlowmeterId());
					sb.append("&entrustedInspectionId="+order.getId());
					
					mrequstTask.setHandlerCode(PublicUtil.RESP_CONTRACT_INFO);
					mrequstTask.setParams(sb.toString());
					mrequstTask.execute("");
				}
				else
				{
					if(order.getClientsEmployer().contains(context.getString(R.string.contract_uint)))
					{
						PublicUtil.toastShow("�ڲ������ƣ�δ�ڼƻ��ڡ�", context);
					}
					else
					{
						if((order.getCheckingProcessState()>=3) && (order.getCheckingProcessState()<=9))
						{
							PublicUtil.toastShow("�Ѿ��������ڼ춨�У������ٴν��ա�", context);
						}
						else
							PublicUtil.toastShow("��ͬ��δ���������Ȱ������ͬ��", context);
					}
					
				}
				break;
			}
			case R.id.btnReturn:
			{
				finish();
				break;
			}
		}
	}
}