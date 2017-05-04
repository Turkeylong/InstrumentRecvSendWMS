package com.rfid.instrument.util;

import java.io.Serializable;

public class Commission implements Serializable{
	private int id;//委托单号
	private String flowmeterId;
	private String entrustedInspectionNumber;//送检样品编号
	private String clientsContacts;//联系人
	private String clientsEmployer;//客户单位
	private String clientsEmployerAddress;//客户地址
	private String clientsPhone;//客户电话
	private String clientsPostcode;//客户邮编
	private String clientsEmail;//客户电子邮箱
	
	private String certificateClientsEmployer;//证书客户单位
	private String certificateClientsEmployerAddress;//证书客户单位地址
	
	private String flowmeterName;//流量计名称
	private String flowmeterCaliber;//流量计口径
	private String flowmeterInside;//流量计内径
	private String flowmeterLength;//流量计长度
	private String flowmeterModel;//流量计型号
	private String flowmeterFactoryNumber;//流量计出厂编号
	private String flowmeterManufactor;//流量计生产厂家
	private String flowmeterUseState;//流量计使用状态
	private String flowmeterNominalFlow;//标称流量
	private String flowmeterKcoefficient;//K系数
	private String flowmeterPressureLevel;//流量计耐压等级
	private String flowmeterAccessories;//其他附件
	private String flowmeterAppearance;//外观
	private String sampleDocuments;//样品资料
	private String flowmeterWorkTypes;//工作类型
	private String flowmeterAccuracyLevel;//流量计准确度等级
	private String flowmeterCommonFlow;//流量计常用流量
	private String Remarks;//备注
	private String clientsRequirement;// 客户要求
	private String entrustedPerson;//委托人
	private String entrustedDate;//委托日期
	private String sampleRecipient;//接收人
	private String sampleTakePerson;//取样人
	private String sampleTakeDate;//取样日期
	private String sampleSentPerson;//发样人
	
	private String flowmeterPosition;//流量计存放位置
	private String flowmeterResult;//流量计检定结果
	
	private int accessoriesFrontStraightPipe;//配件——前直管
	private int accessoriesRearStraightPipe;//配件-后直管
	private int accessoriesRectifier;//配件-整流器
	private int accessoriesJoint;//配件-接头
	private int accessoriesShim;//配件-垫片
	private int accessoriesWasher;//配件-垫圈
	private int accessoriesBoltNut;//配件-螺栓螺母套
	
	private String checkingProject;//检定项目
	private int checkingProcessState;//检定流程状态
	private String checkingPrice;//检定价格
	private String checkingDate;//鉴定日期
	private int contractId;//合同编号
	private int contractType;
	private int contractState;
	
	/*
	"entrustedInspectionId": "12", 
	"clientsContacts": "3", 
	"clientsEmployer": "中石油东部管道有限公司", 
	"clientsEmployerAddress": "南京", 
	"clientsPhone": "13499998888", 
	"clientsPostcode": "3", 
	"clientsEmail": "sy@163.com", 
	"flowmeterName": "天然气设备", 
	"flowmeterCaliber": "5", 
	"flowmeterInside": "3", 
	"flowmeterLength": "3", 
	"flowmeterModel": "JX-012305012", 
	"flowmeterFactoryNumber": "3", 
	"flowmeterManufactor": "3", 
	"flowmeterUseState": "3", 
	"flowmeterNominalFlow": "3", 
	"flowmeterKcoefficient": "3", 
	"flowmeterPressureLevel": "3", 
	"flowmeterAccessories": "流量计说明书，上期检定证书，维修后测试报告，书面测试要求，cvbcvb", 
	"flowmeterAppearance": "铭牌信息完整，外观完好，流量计内腔清洁，流量计探头清洁，涡轮转子转动正常，", 
	"sampleDocuments": "", 
	"flowmeterWorkTypes": "", 
	"flowmeterAccuracyLevel": "3", 
	"flowmeterCommonFlow": "3", 
	"Remarks": "cvbcb", 
	"entrustedPerson": "", 
	"entrustedDate": "2016/11/9 0:00:00", 
	"sampleRecipient": "", 
	"sampleTakePerson": "", 
	"sampleTakeDate": "2016/11/9 0:00:00", 
	"sampleSentPerson": "", 
	"accessoriesFrontStraightPipe": "0", 
	"accessoriesRearStraightPipe": "0", 
	"accessoriesRectifier": "0", 
	"accessoriesJoint": "0", 
	"accessoriesShim": "0", 
	"accessoriesWasher": "0", 
	"accessoriesBoltNut": "0", 
	"checkingProject": "", 
	"checkingProcessState": "1", 
	"checkingPrice": "4522", 
	"checkingDate": "", 
	"contractId": "3"*/
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getContractType() {
		return contractType;
	}
	public void setContractType(int contractType) {
		this.contractType = contractType;
	}
	public int getContractState() {
		return contractState;
	}
	public void setContractState(int contractState) {
		this.contractState = contractState;
	}
	public String getFlowmeterId() {
		return flowmeterId;
	}
	public void setFlowmeterId(String flowmeterId) {
		this.flowmeterId = flowmeterId;
	}
	public String getClientsContacts() {
		return clientsContacts;
	}
	public void setClientsContacts(String clientsContacts) {
		this.clientsContacts = clientsContacts;
	}
	public String getClientsEmployer() {
		return clientsEmployer;
	}
	public void setClientsEmployer(String clientsEmployer) {
		this.clientsEmployer = clientsEmployer;
	}
	public String getClientsEmployerAddress() {
		return clientsEmployerAddress;
	}
	public void setClientsEmployerAddress(String clientsEmployerAddress) {
		this.clientsEmployerAddress = clientsEmployerAddress;
	}
	public String getClientsPhone() {
		return clientsPhone;
	}
	public void setClientsPhone(String clientsPhone) {
		this.clientsPhone = clientsPhone;
	}
	public String getClientsPostcode() {
		return clientsPostcode;
	}
	public void setClientsPostcode(String clientsPostcode) {
		this.clientsPostcode = clientsPostcode;
	}
	public String getClientsEmail() {
		return clientsEmail;
	}
	public void setClientsEmail(String clientsEmail) {
		this.clientsEmail = clientsEmail;
	}
	public String getFlowmeterName() {
		return flowmeterName;
	}
	public void setFlowmeterName(String flowmeterName) {
		this.flowmeterName = flowmeterName;
	}
	public String getFlowmeterCaliber() {
		return flowmeterCaliber;
	}
	public void setFlowmeterCaliber(String flowmeterCaliber) {
		this.flowmeterCaliber = flowmeterCaliber;
	}
	public String getFlowmeterInside() {
		return flowmeterInside;
	}
	public void setFlowmeterInside(String flowmeterInside) {
		this.flowmeterInside = flowmeterInside;
	}
	public String getFlowmeterLength() {
		return flowmeterLength;
	}
	public void setFlowmeterLength(String flowmeterLength) {
		this.flowmeterLength = flowmeterLength;
	}
	public String getFlowmeterModel() {
		return flowmeterModel;
	}
	public void setFlowmeterModel(String flowmeterModel) {
		this.flowmeterModel = flowmeterModel;
	}
	public String getFlowmeterFactoryNumber() {
		return flowmeterFactoryNumber;
	}
	public void setFlowmeterFactoryNumber(String flowmeterFactoryNumber) {
		this.flowmeterFactoryNumber = flowmeterFactoryNumber;
	}
	public String getFlowmeterManufactor() {
		return flowmeterManufactor;
	}
	public void setFlowmeterManufactor(String flowmeterManufactor) {
		this.flowmeterManufactor = flowmeterManufactor;
	}
	public String getFlowmeterUseState() {
		return flowmeterUseState;
	}
	public void setFlowmeterUseState(String flowmeterUseState) {
		this.flowmeterUseState = flowmeterUseState;
	}
	public String getFlowmeterNominalFlow() {
		return flowmeterNominalFlow;
	}
	public void setFlowmeterNominalFlow(String flowmeterNominalFlow) {
		this.flowmeterNominalFlow = flowmeterNominalFlow;
	}
	public String getFlowmeterKcoefficient() {
		return flowmeterKcoefficient;
	}
	public void setFlowmeterKcoefficient(String flowmeterKcoefficient) {
		this.flowmeterKcoefficient = flowmeterKcoefficient;
	}
	public String getFlowmeterPressureLevel() {
		return flowmeterPressureLevel;
	}
	public void setFlowmeterPressureLevel(String flowmeterPressureLevel) {
		this.flowmeterPressureLevel = flowmeterPressureLevel;
	}
	public String getFlowmeterAccessories() {
		return flowmeterAccessories;
	}
	public void setFlowmeterAccessories(String flowmeterAccessories) {
		this.flowmeterAccessories = flowmeterAccessories;
	}
	public String getFlowmeterPosition() {
		return flowmeterPosition;
	}
	public void setFlowmeterPosition(String flowmeterPosition) {
		this.flowmeterPosition = flowmeterPosition;
	}
	public String getFlowmeterAppearance() {
		return flowmeterAppearance;
	}
	public void setFlowmeterAppearance(String flowmeterAppearance) {
		this.flowmeterAppearance = flowmeterAppearance;
	}
	public String getSampleDocuments() {
		return sampleDocuments;
	}
	public void setSampleDocuments(String sampleDocuments) {
		this.sampleDocuments = sampleDocuments;
	}
	public String getFlowmeterWorkTypes() {
		return flowmeterWorkTypes;
	}
	public void setFlowmeterWorkTypes(String flowmeterWorkTypes) {
		this.flowmeterWorkTypes = flowmeterWorkTypes;
	}
	public String getFlowmeterAccuracyLevel() {
		return flowmeterAccuracyLevel;
	}
	public void setFlowmeterAccuracyLevel(String flowmeterAccuracyLevel) {
		this.flowmeterAccuracyLevel = flowmeterAccuracyLevel;
	}
	public String getFlowmeterCommonFlow() {
		return flowmeterCommonFlow;
	}
	public void setFlowmeterCommonFlow(String flowmeterCommonFlow) {
		this.flowmeterCommonFlow = flowmeterCommonFlow;
	}
	public String getRemarks() {
		return Remarks;
	}
	public void setRemarks(String remarks) {
		Remarks = remarks;
	}
	public String getEntrustedPerson() {
		return entrustedPerson;
	}
	public void setEntrustedPerson(String entrustedPerson) {
		this.entrustedPerson = entrustedPerson;
	}
	public String getEntrustedDate() {
		return entrustedDate;
	}
	public void setEntrustedDate(String entrustedDate) {
		this.entrustedDate = entrustedDate;
	}
	public String getSampleRecipient() {
		return sampleRecipient;
	}
	public void setSampleRecipient(String sampleRecipient) {
		this.sampleRecipient = sampleRecipient;
	}
	public String getSampleTakePerson() {
		return sampleTakePerson;
	}
	public void setSampleTakePerson(String sampleTakePerson) {
		this.sampleTakePerson = sampleTakePerson;
	}
	public String getSampleTakeDate() {
		return sampleTakeDate;
	}
	public void setSampleTakeDate(String sampleTakeDate) {
		this.sampleTakeDate = sampleTakeDate;
	}
	public String getSampleSentPerson() {
		return sampleSentPerson;
	}
	public void setSampleSentPerson(String sampleSentPerson) {
		this.sampleSentPerson = sampleSentPerson;
	}
	public int getAccessoriesFrontStraightPipe() {
		return accessoriesFrontStraightPipe;
	}
	public void setAccessoriesFrontStraightPipe(int accessoriesFrontStraightPipe) {
		this.accessoriesFrontStraightPipe = accessoriesFrontStraightPipe;
	}
	public int getAccessoriesRearStraightPipe() {
		return accessoriesRearStraightPipe;
	}
	public void setAccessoriesRearStraightPipe(int accessoriesRearStraightPipe) {
		this.accessoriesRearStraightPipe = accessoriesRearStraightPipe;
	}
	public int getAccessoriesRectifier() {
		return accessoriesRectifier;
	}
	public void setAccessoriesRectifier(int accessoriesRectifier) {
		this.accessoriesRectifier = accessoriesRectifier;
	}
	public int getAccessoriesJoint() {
		return accessoriesJoint;
	}
	public void setAccessoriesJoint(int accessoriesJoint) {
		this.accessoriesJoint = accessoriesJoint;
	}
	public int getAccessoriesShim() {
		return accessoriesShim;
	}
	public void setAccessoriesShim(int accessoriesShim) {
		this.accessoriesShim = accessoriesShim;
	}
	public int getAccessoriesWasher() {
		return accessoriesWasher;
	}
	public void setAccessoriesWasher(int accessoriesWasher) {
		this.accessoriesWasher = accessoriesWasher;
	}
	public int getAccessoriesBoltNut() {
		return accessoriesBoltNut;
	}
	public void setAccessoriesBoltNut(int accessoriesBoltNut) {
		this.accessoriesBoltNut = accessoriesBoltNut;
	}
	public String getCheckingProject() {
		return checkingProject;
	}
	public void setCheckingProject(String checkingProject) {
		this.checkingProject = checkingProject;
	}
	public int getCheckingProcessState() {
		return checkingProcessState;
	}
	public void setCheckingProcessState(int checkingProcessState) {
		this.checkingProcessState = checkingProcessState;
	}
	public String getCheckingPrice() {
		return checkingPrice;
	}
	public void setCheckingPrice(String checkingPrice) {
		this.checkingPrice = checkingPrice;
	}
	public String getCheckingDate() {
		return checkingDate;
	}
	public void setCheckingDate(String checkingDate) {
		this.checkingDate = checkingDate;
	}
	public int getContractId() {
		return contractId;
	}
	public void setContractId(int contractId) {
		this.contractId = contractId;
	}
	public String getFlowmeterResult() {
		return flowmeterResult;
	}
	public void setFlowmeterResult(String flowmeterResult) {
		this.flowmeterResult = flowmeterResult;
	}
	public String getClientsRequirement() {
		return clientsRequirement;
	}
	public void setClientsRequirement(String clientsRequirement) {
		this.clientsRequirement = clientsRequirement;
	}
	public String getCertificateClientsEmployer() {
		return certificateClientsEmployer;
	}
	public void setCertificateClientsEmployer(String certificateClientsEmployer) {
		this.certificateClientsEmployer = certificateClientsEmployer;
	}
	public String getCertificateClientsEmployerAddress() {
		return certificateClientsEmployerAddress;
	}
	public void setCertificateClientsEmployerAddress(
			String certificateClientsEmployerAddress) {
		this.certificateClientsEmployerAddress = certificateClientsEmployerAddress;
	}
	
	public String getEntrustedInspectionNumber() {
		return entrustedInspectionNumber;
	}
	
	public void setEntrustedInspectionNumber(String entrustedInspectionNumber) {
		this.entrustedInspectionNumber = entrustedInspectionNumber;
	}
	
	@Override
	public String toString() {
		return "Commission [id=" + id + ", flowmeterId=" + flowmeterId
				+ ", entrustedInspectionNumber=" + entrustedInspectionNumber
				+ ", clientsContacts=" + clientsContacts + ", clientsEmployer="
				+ clientsEmployer + ", clientsEmployerAddress="
				+ clientsEmployerAddress + ", clientsPhone=" + clientsPhone
				+ ", clientsPostcode=" + clientsPostcode + ", clientsEmail="
				+ clientsEmail + ", certificateClientsEmployer="
				+ certificateClientsEmployer
				+ ", certificateClientsEmployerAddress="
				+ certificateClientsEmployerAddress + ", flowmeterName="
				+ flowmeterName + ", flowmeterCaliber=" + flowmeterCaliber
				+ ", flowmeterInside=" + flowmeterInside + ", flowmeterLength="
				+ flowmeterLength + ", flowmeterModel=" + flowmeterModel
				+ ", flowmeterFactoryNumber=" + flowmeterFactoryNumber
				+ ", flowmeterManufactor=" + flowmeterManufactor
				+ ", flowmeterUseState=" + flowmeterUseState
				+ ", flowmeterNominalFlow=" + flowmeterNominalFlow
				+ ", flowmeterKcoefficient=" + flowmeterKcoefficient
				+ ", flowmeterPressureLevel=" + flowmeterPressureLevel
				+ ", flowmeterAccessories=" + flowmeterAccessories
				+ ", flowmeterAppearance=" + flowmeterAppearance
				+ ", sampleDocuments=" + sampleDocuments
				+ ", flowmeterWorkTypes=" + flowmeterWorkTypes
				+ ", flowmeterAccuracyLevel=" + flowmeterAccuracyLevel
				+ ", flowmeterCommonFlow=" + flowmeterCommonFlow + ", Remarks="
				+ Remarks + ", clientsRequirement=" + clientsRequirement
				+ ", entrustedPerson=" + entrustedPerson + ", entrustedDate="
				+ entrustedDate + ", sampleRecipient=" + sampleRecipient
				+ ", sampleTakePerson=" + sampleTakePerson
				+ ", sampleTakeDate=" + sampleTakeDate + ", sampleSentPerson="
				+ sampleSentPerson + ", flowmeterPosition=" + flowmeterPosition
				+ ", flowmeterResult=" + flowmeterResult
				+ ", accessoriesFrontStraightPipe="
				+ accessoriesFrontStraightPipe
				+ ", accessoriesRearStraightPipe="
				+ accessoriesRearStraightPipe + ", accessoriesRectifier="
				+ accessoriesRectifier + ", accessoriesJoint="
				+ accessoriesJoint + ", accessoriesShim=" + accessoriesShim
				+ ", accessoriesWasher=" + accessoriesWasher
				+ ", accessoriesBoltNut=" + accessoriesBoltNut
				+ ", checkingProject=" + checkingProject
				+ ", checkingProcessState=" + checkingProcessState
				+ ", checkingPrice=" + checkingPrice + ", checkingDate="
				+ checkingDate + ", contractId=" + contractId
				+ ", contractType=" + contractType + ", contractState="
				+ contractState + "]";
	}
}
