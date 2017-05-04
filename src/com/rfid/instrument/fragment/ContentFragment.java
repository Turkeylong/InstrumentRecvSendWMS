package com.rfid.instrument.fragment;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

import com.rfid.instrument.R;
import com.rfid.instrument.activity.CommitActivity;
import com.rfid.instrument.activity.CommitEditActivity;
import com.rfid.instrument.activity.DeviceActivity;
import com.rfid.instrument.activity.HistoryActivity;
import com.rfid.instrument.activity.MainActivity;
import com.rfid.instrument.activity.MipcaActivityCapture;
import com.rfid.instrument.adapter.CertificateListAdapter;
import com.rfid.instrument.adapter.CommitListAdapter;
import com.rfid.instrument.adapter.ContractListAdapter;
import com.rfid.instrument.util.Certificate;
import com.rfid.instrument.util.Commission;
import com.rfid.instrument.util.Contract;
import com.rfid.instrument.util.CustomDialog;
import com.rfid.instrument.util.DEVICE;
import com.rfid.instrument.util.GetDataTask;
import com.rfid.instrument.util.PublicUtil;
import com.rfid.instrument.util.RfidUtil;
import com.rfid.instrument.util.SystemUtil;
import com.rfid.sdk.public_utils.DebugLog;
import com.rfid.sdk.public_utils.HostPacketType;
import com.rfid.sdk.public_utils.Message_Type;
import com.rfid.sdk.public_utils.Public;
import com.rfid.sdk.rfidclass.TAG_TID;

public class ContentFragment extends Fragment implements OnClickListener, OnItemSelectedListener, OnTouchListener,CustomDialog.Builder.OnOkBtnClickListener {
	protected static final String TAG = "ContentFragment";
	public static Context context;
	private LinearLayout receve_layout,title_layout,recv_layout,find_layout,device_layout,wait_layout,stroe_postion_lin,device_Result_lin,devicelist_layout,contractList_lin,certificateList_lin,editpassword_lin;
	private ScrollView res_layout;
	private TableLayout table_layout;
	private View view;
	private View loadMoreView,loadMoreCerView,loadMoreConView;
	private Button loadMoreButton,loadMoreCertificate,loadMoreContract;
	private CheckBox cb_device_Result;
	private EditText editDevice,editCommit,device_Result;
	private TextView textView,device_State,device_Name,device_Model,device_Caliber,device_Length,device_Manufactor,device_FactoryNumber;
	private TextView entrustedInspectionId,clientsEmployer,entrustedDate,clientsContacts,clientsPhone,checkingProcessState,Result,clientRequirement;
	private TextView userId,userName,userEmployer,userEmployeraddress,userPostcode,userEmail,userRegtime;
	private EditText editNewPass,reeditNewPass;
	private TextView flowmeterName;//流量计名称
	private TextView flowmeterModel;//流量计型号
	private TextView flowmeterManufactor;//流量计生产厂家
	private TextView flowmeterFactoryNumber;//流量计出厂编号
	private TextView flowmeterNominalFlow;//标称流量
	private TextView flowmeterResult;//流量计检定结果
	private TextView flowmeterCaliber;//流量计口径
	private TextView flowmeterInside;//流量计内径
	private TextView flowmeterLength;//流量计长度
	private TextView flowmeterKcoefficient;//K系数
	private TextView flowmeterPressureLevel;//流量计耐压等级
	private TextView flowmeterAccuracyLevel;//流量计准确度等级
	private TextView flowmeterCommonFlow;//流量计常用流量
	//private TextView flowmeterPosition;//流量计存放位置
	//private TextView flowmeterUseState;//流量计使用状态
	private TextView flowmeterWorkTypes;//工作类型
	private TextView flowmeterAccessories;//其他附件
	private TextView flowmeterAppearance;//外观

	private TextView accessoriesFrontStraightPipe;//配件——前直管
	private TextView accessoriesRearStraightPipe;//配件-后直管
	private TextView accessoriesRectifier;//配件-整流器
	private TextView accessoriesJoint;//配件-接头
	private TextView accessoriesShim;//配件-垫片
	private TextView accessoriesWasher;//配件-垫圈
	private TextView accessoriesBoltNut;//配件-螺栓螺母套
	private Button searchBnt,findBnt,scanBnt,btn_change_state,btn_read_userinfo,btn_look_comitinfo;
	private Spinner sp_stroe_postion,sp_device_Result;
	private EditText flowchuchangID,stroe_postion,supervisor;
	private ListView devicelist,contractlist,certificatelist;
	
	private static int pageId;
	private int curPage,maxPage,pageSize=10,cerPage,cerMaxPage,cerPageSize=5,conPage,conMaxPage,conPageSize=10;
	public static RfidUtil rfid;
	private BTNBroadcastReceiver receiver;
	private GetDataTask mrequstTask = null;
	private Handler handler = null;
	private ArrayList<TAG_TID> tagTidList = new ArrayList();
	private Map<String, TAG_TID> tagTidMap = new HashMap<String, TAG_TID>();
	private boolean isBeginTask = false;
	private boolean isReadEPC = false;
	private boolean isScanEPC = false;
	private Commission order = null;
	private ArrayList<Commission> orderList = new ArrayList();
	private ArrayList<DEVICE> deviceList = new ArrayList();
	//private DeviceListAdapter device_adp = null;
	private CommitListAdapter commit_adp = null;
	private ContractListAdapter contract_adp = null;
	private CertificateListAdapter certificate_adp = null;
	private ArrayList<Contract> contract_list = new ArrayList();
	private ArrayList<Certificate> certificate_list = new ArrayList();
	private int state = 0;
	private ProgressDialog dialog;
	private SharedPreferences sp;//获得实例对象  
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		if (container == null) {
	          return null;
	       }
		view = inflater.inflate(R.layout.fragment_content, null);
		textView = (TextView)view.findViewById(R.id.textcontent);
		device_State = (TextView)view.findViewById(R.id.device_State);
		device_Name = (TextView)view.findViewById(R.id.device_Name);
		device_Model = (TextView)view.findViewById(R.id.device_Model);
		device_Caliber = (TextView)view.findViewById(R.id.device_Caliber);
		device_Length = (TextView)view.findViewById(R.id.device_Length);
		device_Manufactor = (TextView)view.findViewById(R.id.device_Manufactor);
		device_FactoryNumber = (TextView)view.findViewById(R.id.device_FactoryNumber);
		stroe_postion_lin = (LinearLayout) view.findViewById(R.id.stroe_postion_lin);
		sp_stroe_postion = (Spinner) view.findViewById(R.id.sp_stroe_postion);
		stroe_postion = (EditText) view.findViewById(R.id.stroe_postion);
		
		flowchuchangID = (EditText)view.findViewById(R.id.flowchuchangID);
		searchBnt = (Button) view.findViewById(R.id.searchBnt);
		searchBnt.setOnClickListener(this);
		sp_stroe_postion.setOnItemSelectedListener(this);
		btn_change_state = (Button) view.findViewById(R.id.btn_change_state);
		btn_read_userinfo = (Button) view.findViewById(R.id.btn_read_userinfo);
		btn_look_comitinfo = (Button) view.findViewById(R.id.btn_look_comitinfo);
		btn_change_state.setOnClickListener(this);
		btn_read_userinfo.setOnClickListener(this);
		btn_look_comitinfo.setOnClickListener(this);
		
		cb_device_Result = (CheckBox) view.findViewById(R.id.cb_device_Result);
		device_Result = (EditText)view.findViewById(R.id.device_Result);
		cb_device_Result.setOnClickListener(this);
		
		editDevice = (EditText)view.findViewById(R.id.editDevice);
		editCommit = (EditText)view.findViewById(R.id.editCommit);
		supervisor = (EditText)view.findViewById(R.id.supervisor);
		//editDevice.setEnabled(false);
		editCommit.setEnabled(false);
		receve_layout = (LinearLayout) view.findViewById(R.id.receve_layout);
		title_layout = (LinearLayout) view.findViewById(R.id.title_layout);
		recv_layout = (LinearLayout) view.findViewById(R.id.recv_layout);
		find_layout = (LinearLayout) view.findViewById(R.id.find_layout);
		wait_layout = (LinearLayout) view.findViewById(R.id.wait_layout);
		device_layout = (LinearLayout) view.findViewById(R.id.device_layout);
		res_layout = (ScrollView) view.findViewById(R.id.res_layout);
		table_layout = (TableLayout) view.findViewById(R.id.table_layout);
		devicelist_layout = (LinearLayout) view.findViewById(R.id.devicelist_layout);
		contractList_lin = (LinearLayout) view.findViewById(R.id.contractList_lin);
		certificateList_lin = (LinearLayout) view.findViewById(R.id.certificateList_lin);
		editpassword_lin = (LinearLayout) view.findViewById(R.id.editpassword_lin);

		scanBnt = (Button) view.findViewById(R.id.scanBnt);
		findBnt = (Button) view.findViewById(R.id.findBnt);
		entrustedInspectionId = (TextView)view.findViewById(R.id.entrustedInspectionId);
		flowmeterName = (TextView)view.findViewById(R.id.flowmeterName);
		flowmeterAccessories = (TextView)view.findViewById(R.id.flowmeterAccessories);
		clientsEmployer = (TextView)view.findViewById(R.id.clientsEmployer);
		entrustedDate = (TextView)view.findViewById(R.id.entrustedDate);
		clientsContacts = (TextView)view.findViewById(R.id.clientsContacts);
		clientsPhone = (TextView)view.findViewById(R.id.clientsPhone);
		
		checkingProcessState = (TextView)view.findViewById(R.id.checkingProcessState);
		
		device_Result_lin = (LinearLayout) view.findViewById(R.id.device_Result_lin);
		sp_device_Result = (Spinner) view.findViewById(R.id.sp_device_Result);
		sp_device_Result.setOnItemSelectedListener(this);
		
		flowmeterManufactor = (TextView)view.findViewById(R.id.flowmeterManufactor);
		flowmeterModel = (TextView)view.findViewById(R.id.flowmeterModel);
		flowmeterNominalFlow = (TextView)view.findViewById(R.id.flowmeterNominalFlow);
		flowmeterPressureLevel = (TextView)view.findViewById(R.id.flowmeterPressureLevel);
		flowmeterCommonFlow = (TextView)view.findViewById(R.id.flowmeterCommonFlow);
		flowmeterAccuracyLevel = (TextView)view.findViewById(R.id.flowmeterAccuracyLevel);
		flowmeterInside = (TextView)view.findViewById(R.id.flowmeterInside);
		flowmeterCaliber = (TextView)view.findViewById(R.id.flowmeterCaliber);
		flowmeterWorkTypes = (TextView)view.findViewById(R.id.flowmeterWorkTypes);
		flowmeterLength = (TextView)view.findViewById(R.id.flowmeterLength);
		flowmeterFactoryNumber = (TextView)view.findViewById(R.id.flowmeterFactoryNumber); 
		flowmeterResult = (TextView)view.findViewById(R.id.flowmeterResult); 
		flowmeterKcoefficient = (TextView)view.findViewById(R.id.flowmeterKcoefficient); 
		flowmeterAppearance = (TextView)view.findViewById(R.id.flowmeterAppearance);
		//flowmeterUseState = (TextView)view.findViewById(R.id.flowmeterUseState);
		
		accessoriesFrontStraightPipe = (TextView)view.findViewById(R.id.accessoriesFrontStraightPipe);
		accessoriesRearStraightPipe = (TextView)view.findViewById(R.id.accessoriesRearStraightPipe);
		accessoriesRectifier = (TextView)view.findViewById(R.id.accessoriesRectifier);
		accessoriesJoint = (TextView)view.findViewById(R.id.accessoriesJoint);
		accessoriesShim = (TextView)view.findViewById(R.id.accessoriesShim);
		accessoriesWasher = (TextView)view.findViewById(R.id.accessoriesWasher);
		accessoriesBoltNut = (TextView)view.findViewById(R.id.accessoriesBoltNut);
		
		userId = (TextView)view.findViewById(R.id.userId);
		userName = (TextView)view.findViewById(R.id.userName);
		userEmployer = (TextView)view.findViewById(R.id.userEmployer);
		userEmployeraddress = (TextView)view.findViewById(R.id.userEmployeraddress);
		userPostcode = (TextView)view.findViewById(R.id.userPostcode);
		userEmail = (TextView)view.findViewById(R.id.userEmail);
		userRegtime = (TextView)view.findViewById(R.id.userRegtime);
		
		editNewPass = (EditText) view.findViewById(R.id.editNewPass);
		reeditNewPass = (EditText) view.findViewById(R.id.reeditNewPass);
		clientRequirement = (TextView)view.findViewById(R.id.clientRequest);
		Result = (TextView)view.findViewById(R.id.flowmeterResult);
		
		handler=new Handler()
		{
			public void handleMessage(Message msg) 
			{
				switch (msg.what) 
				{
					case PublicUtil.RESP_FLOWMETER_INFO:
					{
						isBeginTask = false;
						String res=msg.obj+"";
						//Log.i(TAG,res);
						ParseJson(res);
						ShowInfo();
						break;
					}
					case PublicUtil.RESP_FLOWRECV_INFO:
					{
						isBeginTask = false;
						String res=msg.obj+"";
						ParseJson(res);
						HandlerRecv();
						break;
					}
					case PublicUtil.RESP_DEVICE_INFO:
					{
						isBeginTask = false;
						String res=msg.obj+"";
						DebugLog.i(TAG, "RESP_DEVICE_INFO:"+res);
						ParseJson(res);
						ShowDeviceInfo();
						break;
					}
					case PublicUtil.RESP_CHANGE_STATE_INFO:
					{
						isBeginTask = false;
						stroe_postion.setText(" ");
						String res=msg.obj+"";
						DebugLog.i(TAG, "RESP_DEVICE_INFO:"+res);
						ParseCommitJson(res);
						ShowDeviceInfo();
						break;
					}
					case PublicUtil.RESP_ALL_DEVICE_INFO:
					{
						isBeginTask = false;
						String res=msg.obj+"";
						DebugLog.i(TAG, "RESP_ALL_DEVICE_INFO:"+res);
						ParseJson(res);
						ShowAllDeviceList();
						break;
					}
					case PublicUtil.RESP_ALL_CONTRACT_INFO:
					{
						isBeginTask = false;
						String res=msg.obj+"";
						DebugLog.i(TAG, "RESP_ALL_CONTRACT_INFO:"+res);
						ParseContractJson(res);
						ShowAllContractList();
						break;
					}
					case PublicUtil.RESP_ALL_CERTIFICATE_INFO:
					{
						isBeginTask = false;
						String res=msg.obj+"";
						DebugLog.i(TAG, "RESP_ALL_CERTIFICATE_INFO:"+res);
						ParseCertificateJson(res);
						ShowAllCertificateList();
						break;
					}
					case PublicUtil.RESP_COMMITE_INFO:
					{
						isBeginTask = false;
						String res=msg.obj+"";
						DebugLog.i(TAG, "RESP_COMMITE_INFO:"+res);
						break;
					}
					case PublicUtil.RESP_CONTRACT_INFO:
					{
						isBeginTask = false;
						String res=msg.obj+"";
						DebugLog.i(TAG, "RESP_CONTRACT_INFO:"+res);
						IsContractRecv(res);
						break;
					}
					case HostPacketType.RFID_PACKET_TYPE_COMMAND_BEGIN:
					{
						DebugLog.i(TAG,"RFID_PACKET_TYPE_COMMAND_BEGIN");
						String msg_type = msg.getData().getString(Message_Type.msg_type);
						tagTidList.clear();
						isReadEPC = false;
						break;
					}
					case HostPacketType.RFID_PACKET_TYPE_18K6C_INVENTORY:
					{
						DebugLog.i(TAG,"RFID_PACKET_TYPE_18K6C_INVENTORY");
						String msg_type = msg.getData().getString(Message_Type.msg_type);
						TAG_TID tag_data = (TAG_TID) msg.getData().getSerializable(Message_Type.tag_inventory);
						TAG_TID tag_tid = tagTidMap.get(tag_data.EPC);
              		  
              			if(tag_tid == null)
              			{
              				tagTidMap.put(tag_data.EPC, tag_data);
              				DebugLog.i(TAG, tag_data.EPC+"put Map");
              			}
              			else
              			{
              				if(tag_tid.getTimes() > tag_data.getTimes())
              				{
              					tagTidMap.remove(tag_data.EPC);
              					tagTidMap.put(tag_data.EPC, tag_data);
              					DebugLog.i(TAG, tag_data.EPC+"update Map");
              				}
              			}
						break;
					}
					case HostPacketType.RFID_PACKET_TYPE_18K6C_TAG_ACCESS:
					{
						DebugLog.i(TAG,"RFID_PACKET_TYPE_18K6C_TAG_ACCESS");
						int err = msg.getData().getInt(Message_Type.tag_err);
						if(err == 0)
						{
							String msg_type = msg.getData().getString(Message_Type.msg_type);
							DebugLog.i(TAG,"msg_type="+msg_type);
							if(msg_type.equals(Message_Type.tag_read))
							{
							  
	                  		  String data = msg.getData().getString(Message_Type.tag_read);
	                      	  DebugLog.i(TAG,""+data);
	                      	  //SystemUtil.startAlarm(getActivity());
	                      	}
							else if(msg_type.equals(Message_Type.tag_read_EPC))
	                      	{
							  isReadEPC = true;
							  SystemUtil.startAlarm(getActivity());
	                  		  String data = msg.getData().getString(Message_Type.tag_read_EPC);
	                      	  DebugLog.i(TAG,""+data);
	                      	  parseEPC(data);
	                      	  
	                      	}
							else if(msg_type.equals(Message_Type.tag_check))
							{
								isReadEPC = true;
								int error = msg.getData().getInt(Message_Type.tag_err);
								short wrcnt = msg.getData().getShort(Message_Type.tag_write);
								DebugLog.i(TAG,"err:"+error+",write:"+wrcnt);
								if(error == 0)
									PublicUtil.toastShow("写入成功！", context);
								else
									PublicUtil.toastShow("错误码："+error, context);
							}
						}
						else
						{
							DebugLog.i(TAG,"err="+err);
						}

						break;
					}
					case HostPacketType.RFID_PACKET_TYPE_COMMAND_END:
					{
						DebugLog.i(TAG,"RFID_PACKET_TYPE_COMMAND_END");
						String msg_type = msg.getData().getString(Message_Type.msg_type);
						
						for (String   key:tagTidMap.keySet())
						{
							TAG_TID tag_data = tagTidMap.get(key);
							tagTidList.add(tag_data);
						}
						
						DebugLog.i("TAG",""+tagTidList.toString());

						if(isScanEPC)
						{
							if(tagTidList.size() > 0)
								ShowDeviceList();
//							if(tagTidList.size() == 0)
//								PublicUtil.toastShow("没有发现新的电子标签！", context);
							isScanEPC = false;
						}
						else
						{
							if(!isReadEPC)
								PublicUtil.toastShow("没有发现电子标签！", context);
						}
							
						//invenAdapter.notifyDataSetChanged();
						break;
					}
				}
			}
		};
		
		order = ((MainActivity)getActivity()).getCommit();
		
		context = getActivity().getApplicationContext();
		rfid = RfidUtil.getInstance(context);
		
		devicelist = (ListView) view.findViewById(R.id.devicelist);
		contractlist = (ListView) view.findViewById(R.id.contractlist);
		certificatelist = (ListView) view.findViewById(R.id.certificatelist);
		//inflater.inflate(R.layout.fragment_content, null);
		//loadMoreView = view.inflate(context,R.layout.load_more, null);
		
		loadMoreView = view.inflate(context,R.layout.load_more, null);
        loadMoreButton = (Button) loadMoreView.findViewById(R.id.loadMoreButton);
        loadMoreButton.setOnClickListener(this);
        
        loadMoreCerView = view.inflate(context,R.layout.load_more, null);
    	loadMoreCertificate = (Button) loadMoreCerView.findViewById(R.id.loadMoreButton);
    	loadMoreCertificate.setOnClickListener(this);
    	
    	loadMoreConView = view.inflate(context,R.layout.load_more, null);
    	loadMoreContract = (Button) loadMoreConView.findViewById(R.id.loadMoreButton);
    	loadMoreContract.setOnClickListener(this);
    	
        devicelist.addFooterView(loadMoreView);   //设置列表底部视图  
        certificatelist.addFooterView(loadMoreCerView);
        contractlist.addFooterView(loadMoreConView);
        
		//device_adp = new DeviceListAdapter(context, deviceList);
		//devicelist.setAdapter(device_adp);
		commit_adp = new CommitListAdapter(getActivity(), orderList);
		devicelist.setAdapter(commit_adp);
		contract_adp = new ContractListAdapter(context,contract_list);
		contractlist.setAdapter(contract_adp);
		certificate_adp = new CertificateListAdapter(context,certificate_list);
		certificatelist.setAdapter(certificate_adp);	
		
		sp = getActivity().getSharedPreferences("userInfo", Context.MODE_WORLD_READABLE);
		
//		if(PublicUtil.width > PublicUtil.height)
//		{
//			scanBnt.setText("扫描设备标签");
//			findBnt.setText("扫描委托单");
//		}
//		else
//		{
//			scanBnt.setText("输入设备标签");
//			findBnt.setText("输入委托单");
//			view.setOnTouchListener(this);
//		}
		
		rfid.setPower(true);
		rfid.setHandler(handler);
		SystemClock.sleep(500);// 延迟再关闭
		rfid.connectReader();
		rfid.setPower(true,200, 1000, 2000, 200);
		
		if(null != getArguments())
		{
			pageId = getArguments().getInt("type");
			if(pageId == 0)
			{
				title_layout.setVisibility(View.GONE);
				find_layout.setVisibility(View.VISIBLE);
				find_layout.setOnTouchListener(this);
				recv_layout.setVisibility(View.GONE);
				wait_layout.setVisibility(View.GONE);
				res_layout.setVisibility(View.GONE);
				devicelist_layout.setVisibility(View.GONE);
				contractList_lin.setVisibility(View.GONE);
				certificateList_lin.setVisibility(View.GONE);
				editpassword_lin.setVisibility(View.GONE);
				devicelist_layout.setVisibility(View.GONE);
				findBnt.setOnClickListener(this);
				if(order != null)
				{
					editCommit.setText(""+order.getId());
					ShowRes();
				}
			}
			else if(pageId == 1)//
			{
				title_layout.setVisibility(View.GONE);
				recv_layout.setVisibility(View.VISIBLE);
				recv_layout.setOnTouchListener(this);
				find_layout.setVisibility(View.GONE);
				wait_layout.setVisibility(View.GONE);
				res_layout.setVisibility(View.GONE);
				editpassword_lin.setVisibility(View.GONE);
				devicelist_layout.setVisibility(View.GONE);
				scanBnt.setOnClickListener(this);
			}
			else if(pageId == 2)
			{
				if(PublicUtil.USER_POWER.indexOf("流量计接收") != -1)
				{
					title_layout.setVisibility(View.GONE);
					find_layout.setVisibility(View.GONE);
					recv_layout.setVisibility(View.GONE);
					wait_layout.setVisibility(View.GONE);
					res_layout.setVisibility(View.GONE);
					devicelist_layout.setVisibility(View.GONE);
					contractList_lin.setVisibility(View.GONE);
					certificateList_lin.setVisibility(View.GONE);
					editpassword_lin.setVisibility(View.GONE);
					devicelist_layout.setVisibility(View.GONE);
					receve_layout.setVisibility(View.VISIBLE);
				}
				else
				{
					PublicUtil.toastShow("没有流量计接收的权限，请联系管理员！", context);
				}
			}
			else if(pageId == 3)
			{
				title_layout.setVisibility(View.GONE);
				find_layout.setVisibility(View.GONE);
				recv_layout.setVisibility(View.GONE);
				wait_layout.setVisibility(View.GONE);
				res_layout.setVisibility(View.GONE);
				devicelist_layout.setVisibility(View.GONE);
				contractList_lin.setVisibility(View.GONE);
				certificateList_lin.setVisibility(View.GONE);
				editpassword_lin.setVisibility(View.GONE);
				devicelist_layout.setVisibility(View.VISIBLE);
				//ShowAllDeviceRes();
				curPage = 1;
				orderList.clear();
				ShowPageDeviceRes(curPage++,pageSize);
			}
			else if(pageId == 4)
			{
				if(PublicUtil.USER_POWER.indexOf("合同办理") != -1)
				{
					title_layout.setVisibility(View.GONE);
					find_layout.setVisibility(View.GONE);
					recv_layout.setVisibility(View.GONE);
					wait_layout.setVisibility(View.GONE);
					res_layout.setVisibility(View.GONE);
					editpassword_lin.setVisibility(View.GONE);
					devicelist_layout.setVisibility(View.GONE);
					contractList_lin.setVisibility(View.VISIBLE);
					certificateList_lin.setVisibility(View.GONE);
					//ShowAllContractRes();
					conPage = 1;
					contract_list.clear();
					ShowPageContractRes(conPage++,conPageSize);
				}
				else
				{
					PublicUtil.toastShow("没有合同管理的权限，请联系管理员！", context);
				}
			}
			else if(pageId == 5)
			{
				if(PublicUtil.USER_POWER.indexOf("证书管理") != -1)
				{
					title_layout.setVisibility(View.GONE);
					find_layout.setVisibility(View.GONE);
					recv_layout.setVisibility(View.GONE);
					wait_layout.setVisibility(View.GONE);
					res_layout.setVisibility(View.GONE);
					editpassword_lin.setVisibility(View.GONE);
					devicelist_layout.setVisibility(View.GONE);
					contractList_lin.setVisibility(View.GONE);
					certificateList_lin.setVisibility(View.VISIBLE);
					//ShowAllCertificateRes();
					cerPage = 1;
					certificate_list.clear();
					ShowPageCertificateRes(cerPage++,cerPageSize);
				}
				else
				{
					PublicUtil.toastShow("没有证书管理的权限，请联系管理员！", context);
				}
			}
			else if(pageId == 6)
			{
				title_layout.setVisibility(View.GONE);
				find_layout.setVisibility(View.GONE);
				recv_layout.setVisibility(View.GONE);
				wait_layout.setVisibility(View.GONE);
				res_layout.setVisibility(View.GONE);
				editpassword_lin.setVisibility(View.GONE);
				devicelist_layout.setVisibility(View.GONE);
				contractList_lin.setVisibility(View.GONE);
				certificateList_lin.setVisibility(View.GONE);
				editpassword_lin.setVisibility(View.VISIBLE);
				
				userId.setText(PublicUtil.USER_ID);
				userName.setText(PublicUtil.USER_NAME);
				userEmployer.setText(PublicUtil.USER_UNIT);
				userEmployeraddress.setText(PublicUtil.USER_ADDR);
				userPostcode.setText(PublicUtil.USER_POSTCODE);
				userEmail.setText(PublicUtil.USER_EMAIL);
				userRegtime.setText(PublicUtil.USER_REG_TIME);
			}
			else if(pageId == 8)
			{
				sp.edit().putBoolean("AUTO_ISCHECK", false).commit();
				System.exit(0);//正常退出App 
			}
			else
			{
				title_layout.setVisibility(View.VISIBLE);
				recv_layout.setVisibility(View.GONE);
				find_layout.setVisibility(View.GONE);
				wait_layout.setVisibility(View.GONE);
				res_layout.setVisibility(View.GONE);
				//textView.setText(""+getArguments().getString("item"));
			}
		}
		
		IntentFilter filter = new IntentFilter();  
        filter.addAction(Public.BROADCAST_BUTTON_ACTION); 
        receiver = new BTNBroadcastReceiver();
        getActivity().registerReceiver(receiver, filter); 
		
		
		return view; 
	}

	@Override
	public void onDestroyView() {
		// TODO 自动生成的方法存根
		getActivity().unregisterReceiver(receiver);
		super.onDestroyView();
	}

	private void parseEPC(String data) {
		// TODO 自动生成的方法存根
		if(!isReadEPC)
			PublicUtil.toastShow("没有发现电子标签！", context);
		if(data.length() == 24)//长度为委托单长度
		{
			long commitid = Long.valueOf(data.substring(6,6+9),16);
			long deviceid = Long.valueOf(data.substring(6+9,6+18),16);
			DebugLog.i(TAG, "commitid="+commitid+",deviceid="+deviceid);
			String commitid_str = String.valueOf(commitid);
			String deviceid_str = String.valueOf(deviceid);
			if(pageId == 0)
			{
				editCommit.setText(commitid_str);
				ShowRes();
			}
			else if(pageId == 1)
			{
				editDevice.setText(deviceid_str);
				ShowDeviceRes();
			}
		}
		else if(data.length() == 32)//长度为送检设备长度
		{
			long deviceid = Long.valueOf(data.substring(24,32),16);
			DebugLog.i(TAG, "deviceid="+deviceid+",str="+data.substring(24,32));
			String deviceid_str = String.valueOf(deviceid);
			if(pageId == 1)
			{
				editDevice.setText(deviceid_str);
				ShowDeviceRes();
			}
		}
	}

	public class BTNBroadcastReceiver extends BroadcastReceiver
	{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO 自动生成的方法存根
			String action = intent.getAction();
			if(action.toString().equals(Public.BROADCAST_BUTTON_ACTION))
			{
				String msg = (String) intent.getExtras().get("button");
				DebugLog.d("test","SendMsg:"+msg);
				
				if(msg.equals("right_btn_Down"))
				{
					DebugLog.d("test","查找物资开始");
					isScanEPC = true;
					dialog = ProgressDialog.show(context, null, "正在查找，请稍候...", true, false); 
					ScanEPC();
					
				}
				else if(msg.equals("right_btn_Up"))
				{
					DebugLog.d("test","查找物资结束");
					StopScan();
					dialog.dismiss();
				}
				
				if(msg.equals("left_btn_Down"))
				{
					DebugLog.d("test","pageId="+pageId);
					if(pageId == 0)
					{
						DebugLog.d("test","扫描流量计信息");
						ReadEPC();
					}
					else if (pageId==1)
					{
						DebugLog.d("test","扫描委托单信息");
						ReadEPC();
					}
					else
					{
						DebugLog.d("test","扫描电子标签");
						ReadEPC();
					}
				}
				else if(msg.equals("left_btn_Up"))
				{
					rfid.reset();
				}
			}
		}
		
	}
	
	private void ScanEPC() {
		// TODO 自动生成的方法存根
		rfid.setPower(true);
		rfid.setHandler(handler);
		SystemClock.sleep(500);// 延迟再关闭
		rfid.setPower(true,300, 1000, 2000, 200);
		rfid.startScan();
	}
	
	private void StopScan()
	{
		rfid.stopScan();
		SystemClock.sleep(700);// 延迟再关闭
		rfid.reset();
		rfid.setPower(false);
	}
	
	private void ReadEPC() {
		// TODO 自动生成的方法存根
		rfid.setPower(true);
		rfid.setPower(true,200, 1000, 2000, 200);
		rfid.readEPC();
	}
	
	private void ShowDeviceList()
	{
		Intent intent = new Intent(getActivity(), DeviceActivity.class);
		intent.putExtra("deviceList", (Serializable)tagTidList);
	    startActivity(intent);
	    rfid.setPower(false);
	}
	
	protected void ParseJson(String result)
	{
		try
		{
			JSONObject jsonObject = new JSONObject(result).getJSONObject("result");
			JSONArray jsonArr = jsonObject.getJSONArray("DataList");
			
			int totalCount=jsonObject.getInt("totalCount");	
			
			if(totalCount%pageSize == 0)
			{
				maxPage = totalCount/pageSize;
			}
			else
			{
				maxPage = totalCount/pageSize +1;
			}
			
			DebugLog.i(TAG,"totalCount："+totalCount+",size="+jsonArr.length());
			
			//if(totalCount == jsonArr.length())
			{
				DebugLog.i(TAG,"获取设备："+totalCount);
				order = null;
				//orderList.clear();
				 for(int i=0;i<jsonArr.length();i++)
				 {
					 JSONObject jsonObj=(JSONObject)jsonArr.get(i);
					 Commission order = new Commission();
					 
					 order.setId(jsonObj.getInt("entrustedInspectionId"));
					 order.setEntrustedInspectionNumber(jsonObj.getString("entrustedInspectionNumber"));
					 order.setFlowmeterId(jsonObj.getString("flowmeterId"));
					 
					 order.setClientsContacts(jsonObj.getString("clientsContacts"));
					 order.setClientsEmployer(jsonObj.getString("clientsEmployer"));
					 order.setClientsEmployerAddress(jsonObj.getString("clientsEmployerAddress"));
					 order.setClientsPhone(jsonObj.getString("clientsPhone"));
					 order.setClientsEmail(jsonObj.getString("clientsEmail"));
					 order.setClientsPostcode(jsonObj.getString("clientsPostcode"));
					 
					 order.setClientsRequirement(jsonObj.getString("clientsRequirement"));
					 
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
					 order.setCheckingProcessState(PublicUtil.getIntValue(jsonObj.getString("checkingProcessState")));
					 order.setCheckingPrice(jsonObj.getString("checkingPrice"));
					 order.setCheckingDate(jsonObj.getString("checkingDate"));
					 order.setContractId(jsonObj.getInt("contractId"));
					 
					 
					 order.setCertificateClientsEmployer(jsonObj.getString("certificateClientsEmployer"));
					 order.setCertificateClientsEmployerAddress(jsonObj.getString("certificateClientsEmployerAddress"));
					 
					 if(order.getContractId() > 0)
					 {
						 this.order = order;
					 }
					 else if(context.getString(R.string.contract_uint).equals(order.getClientsEmployer()))
					 {
						 this.order = order;
					 }
					 DebugLog.i(TAG,order.toString());
					 orderList.add(order);
				 }
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	protected void ParseCommitJson(String result)
	{
		try
		{
			JSONObject jsonObject = new JSONObject(result).getJSONObject("result");
			
			int state = jsonObject.getInt("state");	
			if(state == 1)
			{
				PublicUtil.toastShow("操作成功。", context);
			}
			else
			{
				PublicUtil.toastShow("操作失败，请重新尝试！", context);
			}
			
			JSONArray jsonArr = jsonObject.getJSONArray("msg");
			
			int totalCount=jsonObject.getInt("totalCount");	
			
			DebugLog.i(TAG,"totalCount："+totalCount+",size="+jsonArr.length());
			
			if(totalCount == jsonArr.length())
			{
				DebugLog.i(TAG,"获取设备："+totalCount);
				order = null;
				orderList.clear();
				 for(int i=0;i<jsonArr.length();i++)
				 {
					 JSONObject jsonObj=(JSONObject)jsonArr.get(i);
					 Commission order = new Commission();
					 
					 order.setId(jsonObj.getInt("entrustedInspectionId"));
					 order.setEntrustedInspectionNumber(jsonObj.getString("entrustedInspectionNumber"));
					 order.setFlowmeterId(jsonObj.getString("flowmeterId"));
					 
					 order.setClientsContacts(jsonObj.getString("clientsContacts"));
					 order.setClientsEmployer(jsonObj.getString("clientsEmployer"));
					 order.setClientsEmployerAddress(jsonObj.getString("clientsEmployerAddress"));
					 order.setClientsPhone(jsonObj.getString("clientsPhone"));
					 order.setClientsEmail(jsonObj.getString("clientsEmail"));
					 order.setClientsPostcode(jsonObj.getString("clientsPostcode"));
					 
					 order.setClientsRequirement(jsonObj.getString("clientsRequirement"));
					 
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
					 
					 order.setCertificateClientsEmployer(jsonObj.getString("certificateClientsEmployer"));
					 order.setCertificateClientsEmployerAddress(jsonObj.getString("certificateClientsEmployerAddress"));
					 
					 this.order = order;
					 DebugLog.i(TAG,order.toString());
					 orderList.add(order);
				 }
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	protected void ParseCertificateJson(String result) 
	{
		// TODO 自动生成的方法存根
		try
		{
			JSONObject jsonObject = new JSONObject(result).getJSONObject("result");
			JSONArray jsonArr = jsonObject.getJSONArray("DataList");
			
			int totalCount=jsonObject.getInt("totalCount");	
			
			DebugLog.i(TAG,"totalCount："+totalCount+",size="+jsonArr.length());
			
			
			if(totalCount%cerPageSize == 0)
			{
				cerMaxPage = totalCount/cerPageSize;
			}
			else
			{
				cerMaxPage = totalCount/cerPageSize +1;
			}
			
			//if(totalCount == jsonArr.length())
			{
				//certificate_list.clear();
				for(int i=0;i<jsonArr.length();i++)
				{
					JSONObject jsonObj=(JSONObject)jsonArr.get(i);
					
					Certificate obj = new Certificate();
					obj.setCertificateNumber(jsonObj.getString("certificateNumber"));
					obj.setDeviceName(jsonObj.getString("flowmeterName"));
					obj.setDeviceState(jsonObj.getString("flowmeterModel"));
					obj.setCertificateCreateDate(jsonObj.getString("certificateCreateDate"));
					obj.setCertificateFilingDate(jsonObj.getString("certificateFilingDate"));
					obj.setCertificateReleaseDate(jsonObj.getString("certificateReleaseDate"));
					obj.setCertificateReleaseInfo(jsonObj.getString("certificateReleaseInfo"));
					obj.setCertificateState(jsonObj.getString("certificateState"));
					
					DebugLog.i(TAG,obj.toString());
					certificate_list.add(obj);
				}
				DebugLog.i(TAG,certificate_list.toString());
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	protected void ParseContractJson(String result) 
	{
		// TODO 自动生成的方法存根
		try
		{
			JSONObject jsonObject = new JSONObject(result).getJSONObject("result");
			JSONArray jsonArr = jsonObject.getJSONArray("ContractList");
			
			int totalCount=jsonObject.getInt("totalCount");	
			
			DebugLog.i(TAG,"totalCount："+totalCount+",size="+jsonArr.length());
			
			if(totalCount%conPageSize == 0)
			{
				conMaxPage = totalCount/conPageSize;
			}
			else
			{
				conMaxPage = totalCount/conPageSize +1;
			}
			
			//if(totalCount == jsonArr.length())
			{
				//contract_list.clear();
				for(int i=0;i<jsonArr.length();i++)
				{
					JSONObject jsonObj=(JSONObject)jsonArr.get(i);
					
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
					
					DebugLog.i(TAG,obj.toString());
					contract_list.add(obj);
				}
				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	protected void IsContractRecv(String result) {
		// TODO 判断是否在合同内的接收
		try{
			JSONObject jsonObject = new JSONObject(result).getJSONObject("result");
			JSONArray jsonArr = jsonObject.getJSONArray("ContractList");
			
			int totalCount=jsonObject.getInt("totalCount");	
			
			DebugLog.i(TAG,"totalCount："+totalCount+",size="+jsonArr.length());
			
			if(totalCount == jsonArr.length())
			{
				orderList.clear();
				for(int i=0;i<jsonArr.length();i++)
				{
					JSONObject jsonObj=(JSONObject)jsonArr.get(i);
					
					Commission order = new Commission();
					
					 order.setId(jsonObj.getInt("entrustedInspectionId"));
					 order.setEntrustedInspectionNumber(jsonObj.getString("entrustedInspectionNumber"));
					 order.setFlowmeterId(jsonObj.getString("flowmeterId"));
					 
					 order.setClientsContacts(jsonObj.getString("clientsContacts"));
					 order.setClientsEmployer(jsonObj.getString("clientsEmployer"));
					 order.setClientsEmployerAddress(jsonObj.getString("clientsEmployerAddress"));
					 order.setClientsPhone(jsonObj.getString("clientsPhone"));
					 order.setClientsEmail(jsonObj.getString("clientsEmail"));
					 order.setClientsPostcode(jsonObj.getString("clientsPostcode"));
					 
					 order.setClientsRequirement(jsonObj.getString("clientsRequirement"));
					 
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
					 order.setContractType(jsonObj.getInt("contractType"));
					 order.setContractState(jsonObj.getInt("contractState"));
					 
					 order.setCertificateClientsEmployer(jsonObj.getString("certificateClientsEmployer"));
					 order.setCertificateClientsEmployerAddress(jsonObj.getString("certificateClientsEmployerAddress"));
					 
					DebugLog.i(TAG,order.toString());
					orderList.add(order);
				}
				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		boolean isKuangJia=false,isRecvOk=false,isCheckOver=false;
		
		Commission order = new Commission();
		for(int i=0;i<orderList.size();i++)
		{
			order = orderList.get(i);
			int type = order.getContractType();
			int state = order.getContractState();
			int process = order.getCheckingProcessState();
			DebugLog.d(TAG, "type="+type+",state="+state);
			if(type == 0)
			{
				isKuangJia = true;
				if(process == 2)
				{
					isKuangJia = false;
					isRecvOk = true;
					break;
				}
				if(process > 8)
				{
					isCheckOver = true;
				}
				continue;
			}
			else
			{
				if(process == 2)
				{
					isKuangJia = false;
					isRecvOk = true;
//					if(!isCheckOver)
//						PublicUtil.toastShow("有合同流程未走完，请注意核对接收！", context);
					break;
				}
				if(process > 8)
				{
					isCheckOver = true;
				}
			}
		}
		
		if(isRecvOk)//可以接收
		{
			submitRecevice(this.order);
		}
		else if(isKuangJia)
		{
			PublicUtil.toastShow("有框架合同，请在电脑端核对接收！", context);
			
		}
		else
		{
			PublicUtil.toastShow("对不起，未办理合同不能接收，请先办理合同！", context);
		}
	}
	
	protected void HandlerRecv(){
		wait_layout.setVisibility(View.GONE);
		Commission item = new Commission();
		if(null == order)
		{
			PublicUtil.toastShow("没有检定过该编号流量计。", context);
			item.setFlowmeterFactoryNumber(flowchuchangID.getText().toString());
			item.setCheckingProcessState(2);
			return;
		}
		else
		{
			item.setFlowmeterId(order.getFlowmeterId());
			item.setEntrustedInspectionNumber(order.getEntrustedInspectionNumber());
			item.setClientsContacts(order.getClientsContacts());
			item.setClientsEmail(order.getClientsEmail());
			item.setClientsEmployer(order.getClientsEmployer());
			item.setClientsEmployerAddress(order.getClientsEmployerAddress());
			item.setClientsPhone(order.getClientsPhone());
			item.setClientsPostcode(order.getClientsPostcode());
			item.setClientsRequirement(order.getClientsRequirement());
		
			item.setCertificateClientsEmployer(order.getCertificateClientsEmployer());
			item.setCertificateClientsEmployerAddress(order.getCertificateClientsEmployerAddress());
		
			item.setFlowmeterName(order.getFlowmeterName());
			item.setFlowmeterCaliber(order.getFlowmeterCaliber());
			item.setFlowmeterInside(order.getFlowmeterInside());
			item.setFlowmeterLength(order.getFlowmeterModel());
			item.setFlowmeterModel(order.getFlowmeterModel());
			item.setFlowmeterFactoryNumber(order.getFlowmeterFactoryNumber());
			item.setFlowmeterManufactor(order.getFlowmeterManufactor());
			item.setFlowmeterKcoefficient(order.getFlowmeterKcoefficient());
			item.setFlowmeterPressureLevel(order.getFlowmeterPressureLevel());
			item.setFlowmeterWorkTypes(order.getFlowmeterWorkTypes());
			item.setFlowmeterAccuracyLevel(order.getFlowmeterAccuracyLevel());
			item.setFlowmeterCommonFlow(order.getFlowmeterCommonFlow());
			
			item.setCheckingProcessState(order.getCheckingProcessState());
			item.setId(order.getId());
		}
		
		Intent intent = new Intent();
		intent.setClass(getActivity(), CommitEditActivity.class);
		intent.putExtra("Commission", (Serializable)item);
		getActivity().startActivity(intent);
	}
	
	protected void ShowInfo() {
		// TODO 自动生成的方法存根
		
		 wait_layout.setVisibility(View.GONE);
		 
		if(null == order)
		{
			PublicUtil.toastShow("无此检验单", context);
			return;
		}
		
		 entrustedInspectionId.setText(order.getEntrustedInspectionNumber());
		 flowmeterName.setText(order.getFlowmeterName());
		 flowmeterManufactor.setText(order.getFlowmeterManufactor());
		 flowmeterModel.setText(order.getFlowmeterModel());
		 flowmeterNominalFlow.setText(order.getFlowmeterNominalFlow());
		 flowmeterPressureLevel.setText(order.getFlowmeterPressureLevel());
		 flowmeterCommonFlow.setText(order.getFlowmeterCommonFlow());
		 flowmeterAccuracyLevel.setText(order.getFlowmeterAccuracyLevel());
		 flowmeterPressureLevel.setText(order.getFlowmeterPressureLevel());
		 flowmeterInside.setText(order.getFlowmeterCommonFlow());
		 flowmeterCaliber.setText(""+order.getFlowmeterCaliber());
		 flowmeterWorkTypes.setText(order.getFlowmeterWorkTypes());
		 flowmeterLength.setText(""+order.getFlowmeterLength());
		 flowmeterAccessories.setText(order.getFlowmeterAccessories());
		 
		 flowmeterFactoryNumber.setText(order.getFlowmeterFactoryNumber());
		 flowmeterKcoefficient.setText(order.getFlowmeterKcoefficient());
		 flowmeterAppearance.setText(order.getFlowmeterAppearance());
		 
		 String result = order.getFlowmeterResult();
		 
		 if(null != result)
		 {
			 if(result.equals("0"))
			 {
				 flowmeterResult.setText("未检定");
				 Result.setText("未检定");
			 }
			 else if (result.equals("1"))
			 {
				 flowmeterResult.setText("合格");
				 Result.setText("合格");
			 }
			 else if (result.equals("2"))
			 {
				 flowmeterResult.setText("不合格");
				 Result.setText("不合格");
			 }
		 }
		 
		 switch(order.getCheckingProcessState())
		 {
			case 1:
			{
				checkingProcessState.setText("检定申请");
			}
			break;
			case 2:
			{
				checkingProcessState.setText("合同办理");
			}
			break;
			case 3:
			{
				checkingProcessState.setText("流量计接收");
			}
			break;
			case 4:
			{
				checkingProcessState.setText("已接收到样品接收区,存放于"+order.getFlowmeterPosition());
			}
			break;
			case 5:
			{
				checkingProcessState.setText("安装到检定台");
			}
			break;
			case 6:
			{
				checkingProcessState.setText("设备检定中");
			}
			break;
			case 7:
			{
				checkingProcessState.setText("检定完拆卸中");
			}
			break;
			case 8:
			{
				checkingProcessState.setText("检定完毕，放于样品发放区"+order.getFlowmeterPosition()+",等待客户取件。");
			}
			break;
			case 9:
			{
				checkingProcessState.setText("流量计已发放");
			}
			break;
			case 10:
			{
				checkingProcessState.setText("证书已发放");
			}
			break;
		 }
		 
		 accessoriesFrontStraightPipe.setText(order.getAccessoriesFrontStraightPipe()+"段");
		 accessoriesRearStraightPipe.setText(order.getAccessoriesRearStraightPipe()+"段");
		 accessoriesRectifier.setText(order.getAccessoriesRectifier()+"个");
		 accessoriesJoint.setText(order.getAccessoriesJoint()+"个");
		 accessoriesShim.setText(order.getAccessoriesShim()+"只");
		 accessoriesWasher.setText(order.getAccessoriesWasher()+"只");
		 accessoriesBoltNut.setText(order.getAccessoriesBoltNut()+"套");
		 
		 clientRequirement.setText(order.getClientsRequirement());
		 Result.setText(order.getFlowmeterResult());
		 
		 clientsEmployer.setText(order.getClientsEmployer());
		 entrustedDate.setText(order.getEntrustedDate());
		 clientsContacts.setText(order.getClientsContacts());
		 clientsPhone.setText(order.getClientsPhone());
		 res_layout.setVisibility(View.VISIBLE);

	}
	
	protected void ShowDeviceInfo()
	{
		if(null == order)
		{
			PublicUtil.toastShow("无此设备编号", context);
			return;
		}
		state = order.getCheckingProcessState();
		switch(state)
		{
			case 1:
			{
				device_State.setText("检定申请");
				sp_stroe_postion.setSelection(0);
				sp_stroe_postion.setEnabled(false);
			}
			break;
			case 2:
			{
				device_State.setText("合同办理");
				sp_stroe_postion.setSelection(0);
				sp_stroe_postion.setEnabled(false);
			}
			break;
			case 3:
			{
				device_State.setText("流量计接收");
				btn_change_state.setText("确认接收入库");
				sp_stroe_postion.setSelection(1);
				sp_stroe_postion.setEnabled(true);
			}
			break;
			case 4:
			{
				device_State.setText("已接收到接收区,存放于"+order.getFlowmeterPosition());
				btn_change_state.setText("安装到检定台");
				sp_stroe_postion.setSelection(2);
				sp_stroe_postion.setEnabled(true);
			}
			break;
			case 5:
			{
				device_State.setText("安装到检定台");
				sp_stroe_postion.setSelection(0);
				sp_stroe_postion.setEnabled(false);
			}
			break;
			case 6:
			{
				device_State.setText("设备检定中");
				btn_change_state.setText("确认设备检定完毕通知拆卸");
				sp_stroe_postion.setSelection(0);
				sp_stroe_postion.setEnabled(false);
			}
			break;
			case 7:
			{
				device_State.setText("检定完拆卸中");
				btn_change_state.setText("确认设备已拆卸完毕入发件室");
				sp_stroe_postion.setSelection(3);
				sp_stroe_postion.setEnabled(true);
			}
			break;
			case 8:
			{
				device_State.setText("检定完毕，放于发放区"+order.getFlowmeterPosition()+",等待客户取件。");
				sp_stroe_postion.setSelection(0);
				sp_stroe_postion.setEnabled(false);
				btn_change_state.setText("确认发放流量计");
				
			}
			break;
			case 9:
			{
				device_State.setText("流量计已发放");
				btn_change_state.setText("再次接收流量计");
				sp_stroe_postion.setSelection(0);
				sp_stroe_postion.setEnabled(false);
			}
			break;
			case 10:
			{
				device_State.setText("证书已发放");
				btn_change_state.setText("查看证书");
				sp_stroe_postion.setSelection(0);
				sp_stroe_postion.setEnabled(false);
			}
			break;
		}
		
		switch(sp_stroe_postion.getSelectedItemPosition())
		{
			case 0:
			{
				stroe_postion_lin.setVisibility(View.GONE);
				break;
			}
			case 1:
			{
				stroe_postion_lin.setVisibility(View.VISIBLE);
				break;
			}
			case 2:
			{
				stroe_postion_lin.setVisibility(View.GONE);
				break;
			}
			case 3:
			{
				stroe_postion_lin.setVisibility(View.VISIBLE);
				break;
			}
		}
		
		if(state == 5)
		{
			btn_change_state.setText("确认已安装完毕");
			btn_read_userinfo.setText("确认检定完毕");
			btn_look_comitinfo.setText("确认已拆卸完毕");
			device_Result_lin.setVisibility(View.VISIBLE);
		}
		else
		{
			btn_read_userinfo.setText("读取设备检定信息");
			if(state == 8)
				btn_look_comitinfo.setText("核对委托单");
			else
				btn_look_comitinfo.setText("查看委托信息");
			if(state == 6)
			{
				device_Result_lin.setVisibility(View.VISIBLE);
			}
			else
			{
				device_Result_lin.setVisibility(View.GONE);
			}
		}
		
		device_Name.setText(order.getFlowmeterName());
		device_Model.setText(order.getFlowmeterModel());
		device_Caliber.setText(""+order.getFlowmeterCaliber());
		device_Length.setText(""+order.getFlowmeterLength());
		device_Manufactor.setText(order.getFlowmeterManufactor());
		device_FactoryNumber.setText(order.getFlowmeterFactoryNumber());
		device_layout.setVisibility(View.VISIBLE);
		wait_layout.setVisibility(View.GONE);
	}
	
	private void ShowAllDeviceList() {
		// TODO 自动生成的方法存根
//		int count = 1;
//		deviceList.clear();
//		for(Commission tmp:orderList)
//		{
//			DEVICE dev = new DEVICE();
//			DebugLog.d("test", tmp.toString());
//
//			try
//			{
//				if((!tmp.getFlowmeterName().isEmpty()) && (!tmp.getFlowmeterModel().isEmpty()))
//				{
//					dev.setId(count++);
//					dev.setDeviceState(tmp.getCheckingProcessState());
//					dev.setDevicePosition(tmp.getFlowmeterPosition());
//					dev.setDeviceName(tmp.getFlowmeterName()+tmp.getFlowmeterModel());
//					dev.setOrder(tmp.getId());
//					DebugLog.d("test", dev.toString());
//					deviceList.add(dev);
//				}
//
//				
//			}
//			catch(Exception e)
//			{
//				e.printStackTrace();
//			}
//		}
		//DebugLog.d("test", deviceList.toString());
//		if(null != device_adp)
//			device_adp.notifyDataSetChanged();
		loadMoreButton.setText("加载更多");
		commit_adp.notifyDataSetChanged();
		wait_layout.setVisibility(View.GONE);
		devicelist_layout.setVisibility(View.VISIBLE);
		
	}
	
	private void ShowAllContractList()
	{
		if(null != contract_adp)
		{
			loadMoreContract.setText("加载更多");
			contract_adp.notifyDataSetChanged();
		}
		wait_layout.setVisibility(View.GONE);
		contractList_lin.setVisibility(View.VISIBLE);
	}
	
	private void ShowAllCertificateList()
	{
		DebugLog.d("test", certificate_list.toString());
		if(null != certificate_adp)
		{
			loadMoreCertificate.setText("加载更多");
			certificate_adp.notifyDataSetChanged();
		}
		wait_layout.setVisibility(View.GONE);
		certificateList_lin.setVisibility(View.VISIBLE);
	}
	
	private void ShowAllCertificateRes() {
		// TODO 自动生成的方法存根
		if(!isBeginTask)
		{
			isBeginTask = true;
			mrequstTask=new GetDataTask(getActivity(), handler);
			StringBuffer sb=new StringBuffer();
			sb.append("t="+PublicUtil.REQ_CERTIFICATE_STATE);
			sb.append("&checkingProcessStateLessThan=12&checkingProcessStateGreaterThan=9");
			//sb.append("&checkingProcessState=11");

			mrequstTask.setHandlerCode(PublicUtil.RESP_ALL_CERTIFICATE_INFO);
			mrequstTask.setParams(sb.toString());
			mrequstTask.execute("");
			
			wait_layout.setVisibility(View.VISIBLE);
			certificateList_lin.setVisibility(View.GONE);
		}
	}
	
	private void ShowPageCertificateRes(int page,int pageSize)
	{
		if(!isBeginTask)
		{
			isBeginTask = true;
			mrequstTask=new GetDataTask(getActivity(), handler);
			StringBuffer sb=new StringBuffer();
			sb.append("t="+PublicUtil.REQ_CERTIFICATE_STATE);
			sb.append("&checkingProcessStateLessThan=12&checkingProcessStateGreaterThan=9");
			sb.append("&page="+page);
			sb.append("&pageSize="+pageSize);

			mrequstTask.setHandlerCode(PublicUtil.RESP_ALL_CERTIFICATE_INFO);
			mrequstTask.setParams(sb.toString());
			mrequstTask.execute("");
			
			wait_layout.setVisibility(View.VISIBLE);
			certificateList_lin.setVisibility(View.GONE);
		}
	}

	private void ShowAllContractRes() {
		// TODO 自动生成的方法存根
		if(!isBeginTask)
		{
			isBeginTask = true;
			mrequstTask=new GetDataTask(getActivity(), handler);
			StringBuffer sb=new StringBuffer();
			sb.append("t="+PublicUtil.REQ_CONTRACT_STATE);

			mrequstTask.setHandlerCode(PublicUtil.RESP_ALL_CONTRACT_INFO);
			mrequstTask.setParams(sb.toString());
			mrequstTask.execute("");
			
			wait_layout.setVisibility(View.VISIBLE);
			contractList_lin.setVisibility(View.GONE);
		}
	}
	
	private void ShowPageContractRes(int page,int pageSize)
	{
		if(!isBeginTask)
		{
			isBeginTask = true;
			mrequstTask=new GetDataTask(getActivity(), handler);
			StringBuffer sb=new StringBuffer();
			sb.append("t="+PublicUtil.REQ_CONTRACT_STATE);
			sb.append("&page="+page);
			sb.append("&pageSize="+pageSize);

			mrequstTask.setHandlerCode(PublicUtil.RESP_ALL_CONTRACT_INFO);
			mrequstTask.setParams(sb.toString());
			mrequstTask.execute("");
			
			wait_layout.setVisibility(View.VISIBLE);
			contractList_lin.setVisibility(View.GONE);
		}
	}

	private void ShowAllDeviceRes() {
		// TODO 自动生成的方法存根
		if(!isBeginTask)
		{
			isBeginTask = true;
			mrequstTask=new GetDataTask(getActivity(), handler);
			StringBuffer sb=new StringBuffer();
			sb.append("t="+PublicUtil.REQ_FLOWMETER_INFO);
			//sb.append("t="+PublicUtil.REQ_FLOWMETER_INFO);
			//sb.append("&checkingProcessStateLessThan=11");

			mrequstTask.setHandlerCode(PublicUtil.RESP_ALL_DEVICE_INFO);
			mrequstTask.setParams(sb.toString());
			mrequstTask.execute("");
			
			wait_layout.setVisibility(View.VISIBLE);
			devicelist_layout.setVisibility(View.GONE);
		}
	}
	
	private void ShowPageDeviceRes(int page,int pageSize)
	{
		isBeginTask = true;
		mrequstTask=new GetDataTask(getActivity(), handler);
		StringBuffer sb=new StringBuffer();
		sb.append("t="+PublicUtil.REQ_FLOWMETER_INFO);
		sb.append("&page="+page);
		sb.append("&pageSize="+pageSize);

		mrequstTask.setHandlerCode(PublicUtil.RESP_ALL_DEVICE_INFO);
		mrequstTask.setParams(sb.toString());
		mrequstTask.execute("");
		
		wait_layout.setVisibility(View.VISIBLE);
		devicelist_layout.setVisibility(View.GONE);
	}
	
	private void ShowDeviceRes() {
		// TODO 自动生成的方法存根
		if(editDevice.getText().toString().isEmpty())
		{
			return;
		}
		else
		{
			DebugLog.d(TAG, "isBeginTask="+isBeginTask);
		}
		if(!isBeginTask)
		{
			isBeginTask = true;
			mrequstTask=new GetDataTask(getActivity(), handler);
			StringBuffer sb=new StringBuffer();
			sb.append("t="+PublicUtil.REQ_CERTIFICATE_STATE);
			sb.append("&flowmeterId="+editDevice.getText().toString());

			mrequstTask.setHandlerCode(PublicUtil.RESP_DEVICE_INFO);
			mrequstTask.setParams(sb.toString());
			mrequstTask.execute("");
			
			wait_layout.setVisibility(View.VISIBLE);
			device_layout.setVisibility(View.GONE);
		}
		//device_layout.setVisibility(View.VISIBLE);
	}

	private void ShowRes() {
		// TODO 自动生成的方法存根
		if(editCommit.getText().toString().isEmpty())
		{
			return;
		}
		if(!isBeginTask)
		{
			isBeginTask = true;
			mrequstTask=new GetDataTask(getActivity(), handler);
			StringBuffer sb=new StringBuffer();
			sb.append("t="+PublicUtil.REQ_CERTIFICATE_STATE);
			sb.append("&entrustedInspectionId="+editCommit.getText().toString());

			mrequstTask.setHandlerCode(PublicUtil.RESP_FLOWMETER_INFO);
			mrequstTask.setParams(sb.toString());
			mrequstTask.execute("");
			
			wait_layout.setVisibility(View.VISIBLE);
			res_layout.setVisibility(View.GONE);
		}

	}
	
	private void ChangeStateReq(int update_state) {
		// TODO 自动生成的方法存根
		String posCode = "";
		DebugLog.i(TAG, "state="+state);
		sp_stroe_postion.setEnabled(false);
		switch(state)
		{
			case 1:
			{
				PublicUtil.toastShow("申请检定中，请前去办理合同先。", context);
				break;
			}
			case 2:
			{
				//PublicUtil.toastShow("合同办理中，请办完合同后再接收。", context);
				//CustomDialog dialog = new CustomDialog(getActivity(),R.style.Transparent); 
				
				ReviceFlowmeter();
				break;
			}
			case 3:
			{
				update_state = 4;
				posCode = stroe_postion.getText().toString();
				DebugLog.d(TAG, posCode);
				break;
			}
			case 4:
			{
				update_state = 5;
				posCode = "";
				break;
			}
			case 5:
			{
				if(update_state<6)
					update_state = 6;
				posCode = "";
				break;
			}
			case 6:
			{
				update_state = 7;
				posCode = "";
				break;
			}
			case 7:
			{
				update_state = 8;
				posCode = stroe_postion.getText().toString();
				DebugLog.d(TAG, posCode);
				break;
			}
			case 8:
			{
				PublicUtil.toastShow("请先核对流量计信息后再确认发放！", context);
				//update_state = 9;
				break;
			}
			case 9:
			{
				PublicUtil.toastShow("流量计已被客户领取，等待证书办理。", context);
				ReviceFlowmeter();
				break;
			}
			case 10:
			{
				PublicUtil.toastShow("流量计已检定完毕,查看证书。", context);
				ReviceFlowmeter();                                       
				break;
			}
			case 11:
			{
				ReviceFlowmeter();
				break;
			}
		}
		
		if(state < 3 || state > 7)
			return;
		
		if((!isBeginTask) && (!device_Result.getText().toString().equals("0")))
		{
			//DebugLog.d(TAG, PublicUtil.USER_POWER.substring(5, 6));
			//if(PublicUtil.USER_POWER.substring(5, 6).equals("1") )
			if(PublicUtil.USER_POWER.indexOf("周计划") != -1)
			{
				if(order.getCheckingProcessState()>4)
				{
					if(order.getCheckingDate().isEmpty())
					{
						PublicUtil.toastShow("没有安排检定计划。", context);
					}
					else
					{
						isBeginTask = true;
						mrequstTask=new GetDataTask(getActivity(), handler);
						StringBuffer sb=new StringBuffer();
						//sb.append("t=101");
						sb.append("t="+PublicUtil.REQ_CHANGE_STATE);
//						String str = editDevice.getText().toString();
//						if(str.contains("-"))
//						{
//							sb.append("&entrustedInspectionNumber="+str);
//						}
//						else
//						{
//							sb.append("&flowmeterId="+str);//order.getId()
//						}
						sb.append("&flowmeterId="+order.getFlowmeterId());
						//sb.append("&entrustedInspectionIdList='"+order.getId()+"'");
						sb.append("&checkingProcessState="+update_state);
						sb.append("&flowmeterPosition="+posCode);
						
						if(cb_device_Result.isChecked())
							sb.append("&checkingQualified="+device_Result.getText().toString());
						else
							sb.append("&checkingQualified="+sp_device_Result.getSelectedItem().toString());
						sb.append("&checkingPerson="+supervisor.getText().toString());
						mrequstTask.setHandlerCode(PublicUtil.RESP_CHANGE_STATE_INFO);
						mrequstTask.setParams(sb.toString());
						mrequstTask.execute("");
						
						wait_layout.setVisibility(View.VISIBLE);
						res_layout.setVisibility(View.GONE);
					}
				}
				else
				{
					isBeginTask = true;
					mrequstTask=new GetDataTask(getActivity(), handler);
					StringBuffer sb=new StringBuffer();
					//sb.append("t=101");
					sb.append("t="+PublicUtil.REQ_CHANGE_STATE);
//					String str = editDevice.getText().toString();
//					if(str.contains("-"))
//					{
//						sb.append("&entrustedInspectionNumber="+str);
//					}
//					else
//					{
//						sb.append("&flowmeterId="+str);//order.getId()
//					}
					sb.append("&flowmeterId="+order.getFlowmeterId());
					//sb.append("&entrustedInspectionIdList='"+order.getId()+"'");
					sb.append("&checkingProcessState="+update_state);
					sb.append("&flowmeterPosition="+posCode);

					mrequstTask.setHandlerCode(PublicUtil.RESP_CHANGE_STATE_INFO);
					mrequstTask.setParams(sb.toString());
					mrequstTask.execute("");
					
					wait_layout.setVisibility(View.VISIBLE);
					res_layout.setVisibility(View.GONE);
				}
			}
			else
			{
				PublicUtil.toastShow("没有检定操作权限", context);
			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		switch (v.getId())
		{
			case R.id.findBnt:
			{
				if(PublicUtil.height > PublicUtil.width)
				{
					Intent intent = new Intent();
					intent.setClass(getActivity(), MipcaActivityCapture.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivityForResult(intent, 1);
				}
				else
					ReadEPC();//ShowRes();
				break;
			}
			case R.id.scanBnt:
			{
				if(editDevice.getText().toString().isEmpty())
					ReadEPC();//ShowDeviceRes();
				else
				{
					if(!isBeginTask)
					{
						isBeginTask = true;
						mrequstTask=new GetDataTask(getActivity(), handler);
						StringBuffer sb=new StringBuffer();
						sb.append("t="+PublicUtil.REQ_CERTIFICATE_STATE);
						sb.append("&entrustedInspectionNumber="+editDevice.getText().toString());

						mrequstTask.setHandlerCode(PublicUtil.RESP_DEVICE_INFO);
						mrequstTask.setParams(sb.toString());
						mrequstTask.execute("");
						
						wait_layout.setVisibility(View.VISIBLE);
						device_layout.setVisibility(View.GONE);
					}
				}
				break;
			}
			case R.id.btn_change_state:
			{
				ChangeStateReq(state);
				break;
			}
			case R.id.btn_read_userinfo:
			{
				if(state == 5)
					ChangeStateReq(6);
				else
				{
					Intent StartIntent=new Intent(context,HistoryActivity.class); //接收到广播后，跳转到MainActivity 
					StartIntent.putExtra("commissionList", editDevice.getText().toString());
					StartIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivity(StartIntent);
				}
				break;
			}
			case R.id.btn_look_comitinfo:
			{
				if(state == 5)
					ChangeStateReq(7);
				else
				{
					Intent StartIntent=new Intent(context,CommitActivity.class); //接收到广播后，跳转到MainActivity 
					StartIntent.putExtra("Commission", (Serializable)order);
					StartIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivity(StartIntent);
				}
				break;
			}
			case R.id.cb_device_Result:
			{
				if(cb_device_Result.isChecked())
					sp_device_Result.setEnabled(false);
				else
					sp_device_Result.setEnabled(true);
				break;
			}
			case R.id.loadMoreButton:
			{
				DebugLog.d(TAG, "pageId="+pageId+",cerPage="+cerPage+",cerPageSize="+cerPageSize);
				if(pageId == 2)
				{
					if(curPage<maxPage)
					{
						ShowPageDeviceRes(curPage++,pageSize);
						loadMoreButton.setText("加载中...");   
					}
				}
				else if(pageId == 3)
				{
					
					if(conPage<conMaxPage)//cerPage,cerMaxPage
					{
						ShowPageContractRes(conPage++,conPageSize);
						loadMoreContract.setText("加载中...");   
					}
				}
				else if(pageId == 4)
				{
					
					if(cerPage<cerMaxPage)//cerPage,cerMaxPage
					{
						ShowPageCertificateRes(cerPage++,cerPageSize);
						loadMoreCertificate.setText("加载中...");   
					}
				}

		        handler.postDelayed(new Runnable() {  
		            @Override  
		            public void run() {  
		            	if(pageId == 2)
						{
			                if(curPage == maxPage)
			                {
			                	loadMoreButton.setText("加载完毕");
			                }
						}
		            	else if(pageId == 3)
						{
			                if(conPage == conMaxPage)
			                {
			                	loadMoreContract.setText("加载完毕");
			                }
						}
						else if(pageId == 4)
						{
			                if(cerPage == cerMaxPage)
			                {
			                	loadMoreCertificate.setText("加载完毕");
			                }
						}

		            }  
		        }, 2000); 
				break;
			}
			case R.id.searchBnt:
			{
				if(flowchuchangID.getText().toString().isEmpty())
				{
					PublicUtil.toastShow("出厂编号不能为空！", context);
				}
				else
				{
					mrequstTask=new GetDataTask(getActivity(), handler);
					StringBuffer sb=new StringBuffer();
					sb.append("t=102");
					sb.append("&flowmeterFactoryNumber="+flowchuchangID.getText().toString());
					//sb.append("&page=1&pageSize=1");
					mrequstTask.setHandlerCode(PublicUtil.RESP_FLOWRECV_INFO);
					mrequstTask.setParams(sb.toString());
					mrequstTask.execute("");
				}
				
				break;
			}
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,long id) {
		// TODO 自动生成的方法存根
		switch(parent.getId())
		{
			case R.id.sp_stroe_postion:
			{
				DebugLog.d(TAG,"position="+position);
				if((position == 0)||(position == 2))
				{
					stroe_postion_lin.setVisibility(View.GONE);
				}
				else
				{
					stroe_postion_lin.setVisibility(View.VISIBLE);
				}
				break;
			}
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> adp) {
		// TODO 自动生成的方法存根
		
	}

	private float startX, startY, offsetX, offsetY;

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO 自动生成的方法存根
		DebugLog.d(TAG, "action="+event.getAction());
		switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            startX = event.getX();
            startY = event.getY();
            break;
        case MotionEvent.ACTION_UP:
            offsetX = event.getX() - startX;
            offsetY = event.getY() - startY;

            if (offsetX < 0) 
            {
                System.out.println("left");
            } 
            else if (offsetX > 0) 
            {
                System.out.println("right");
                FragmentManager manager = getFragmentManager();
        		FragmentTransaction transaction = manager.beginTransaction();
        		HeaderFragment fragHeader = new HeaderFragment();
        		transaction.replace(R.id.fragment_header, fragHeader,"headerFragment");
        		transaction.commit();
                //transaction.hide(manager.findFragmentByTag("headerFragment")).show(manager.findFragmentByTag("detailFragment")).commit(); // 隐藏当前的fragment，显示下一个
            }
            if (offsetY < 0) 
            {
            	System.out.println("up");
            } 
            else if (offsetY > 0) 
            {
            	System.out.println("down");
            }
            break;
        }
        return true;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO 自动生成的方法存根
		super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
		case 1:
			if(resultCode == android.app.Activity.RESULT_OK )
			{
				Bundle bundle = data.getExtras();
				//显示扫描到的内容
				try
				{
					String commitNo = bundle.getString("result");
					int beginIndex = commitNo.indexOf("?");
					if(commitNo.substring(beginIndex+1,beginIndex+3).endsWith("id"))
					{
						editCommit.setText(commitNo.substring(beginIndex+4));
						ShowRes();
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				
				//显示
				//fragContent.setImageBitmap((Bitmap) data.getParcelableExtra("bitmap"));
			}
			break;
		}
	}

	public void autoScanNo(String string) {
		// TODO 自动生成的方法存根
		editCommit.setText(string);
	}

	@Override
	public void onDestroy() {
		// TODO 自动生成的方法存根
		super.onDestroy();
		DebugLog.d(TAG, "onDestroy");
	}

	@Override
	public void onResume() {
		// TODO 自动生成的方法存根
		super.onResume();
		DebugLog.d(TAG, "onResume");
	}

	@Override
	public void onPause() {
		// TODO 自动生成的方法存根
		super.onPause();
		DebugLog.d(TAG, "onPause");
		rfid.reset();
		rfid.setPower(false);
	}
	
	private void ReviceFlowmeter() {
		// TODO 自动生成的方法存根
		CustomDialog.Builder builder = new CustomDialog.Builder(order,getActivity(),R.style.Transparent);
		builder.setOkButton(this,new DialogInterface.OnClickListener()
		{

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO 自动生成的方法存根
				dialog.dismiss();  
                //设置你的操作事项 ,提交送检委托单
				
			}
			
		});
		builder.setCancleButton(new DialogInterface.OnClickListener()
		{

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO 自动生成的方法存根
				dialog.dismiss();  
			}
			
		});
		builder.create().show();
	}

	@Override
	public void onOkBtnClicked(Commission order) {
		// TODO 自动生成的方法存根
		this.order = order;
		DebugLog.d(TAG, "Commission="+order.toString());
		
		//Step 1 公司内流量计接收
		if(context.getString(R.string.contract_uint).equals(order.getClientsEmployer()))
		{
			if(order.getCheckingProcessState() >= 9)
			{
				submitRecevice(order);
			}
			else if(order.getCheckingProcessState() < 3)
			{
				submitRecevice(order);
			}
			else
			{
				PublicUtil.toastShow("已经正在检定，不能重复接收~！", context);
			}
		}
		else//Step 2 获取合同列表
		{
			mrequstTask=new GetDataTask(getActivity(), handler);
			StringBuffer sb=new StringBuffer();
			sb.append("t="+PublicUtil.REQ_CONTRACT_STATE);
			sb.append("&flowmeterId="+editDevice.getText().toString());
			
			mrequstTask.setHandlerCode(PublicUtil.RESP_CONTRACT_INFO);
			mrequstTask.setParams(sb.toString());
			mrequstTask.execute("");
		}
	}
	
	private void submitRecevice(Commission order)
	{
		mrequstTask=new GetDataTask(getActivity(), handler);
		StringBuffer sb=new StringBuffer();
		sb.append("t="+PublicUtil.REQ_COMMITE_INFO);
		sb.append("&flowmeterId="+editDevice.getText().toString());
		
		sb.append("&entrustedInspectionId="+order.getId());
		
		sb.append("&checkingProcessState=3");
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
}
