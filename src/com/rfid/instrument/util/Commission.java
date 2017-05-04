package com.rfid.instrument.util;

import java.io.Serializable;

public class Commission implements Serializable{
	private int id;//ί�е���
	private String flowmeterId;
	private String entrustedInspectionNumber;//�ͼ���Ʒ���
	private String clientsContacts;//��ϵ��
	private String clientsEmployer;//�ͻ���λ
	private String clientsEmployerAddress;//�ͻ���ַ
	private String clientsPhone;//�ͻ��绰
	private String clientsPostcode;//�ͻ��ʱ�
	private String clientsEmail;//�ͻ���������
	
	private String certificateClientsEmployer;//֤��ͻ���λ
	private String certificateClientsEmployerAddress;//֤��ͻ���λ��ַ
	
	private String flowmeterName;//����������
	private String flowmeterCaliber;//�����ƿھ�
	private String flowmeterInside;//�������ھ�
	private String flowmeterLength;//�����Ƴ���
	private String flowmeterModel;//�������ͺ�
	private String flowmeterFactoryNumber;//�����Ƴ������
	private String flowmeterManufactor;//��������������
	private String flowmeterUseState;//������ʹ��״̬
	private String flowmeterNominalFlow;//�������
	private String flowmeterKcoefficient;//Kϵ��
	private String flowmeterPressureLevel;//��������ѹ�ȼ�
	private String flowmeterAccessories;//��������
	private String flowmeterAppearance;//���
	private String sampleDocuments;//��Ʒ����
	private String flowmeterWorkTypes;//��������
	private String flowmeterAccuracyLevel;//������׼ȷ�ȵȼ�
	private String flowmeterCommonFlow;//�����Ƴ�������
	private String Remarks;//��ע
	private String clientsRequirement;// �ͻ�Ҫ��
	private String entrustedPerson;//ί����
	private String entrustedDate;//ί������
	private String sampleRecipient;//������
	private String sampleTakePerson;//ȡ����
	private String sampleTakeDate;//ȡ������
	private String sampleSentPerson;//������
	
	private String flowmeterPosition;//�����ƴ��λ��
	private String flowmeterResult;//�����Ƽ춨���
	
	private int accessoriesFrontStraightPipe;//�������ǰֱ��
	private int accessoriesRearStraightPipe;//���-��ֱ��
	private int accessoriesRectifier;//���-������
	private int accessoriesJoint;//���-��ͷ
	private int accessoriesShim;//���-��Ƭ
	private int accessoriesWasher;//���-��Ȧ
	private int accessoriesBoltNut;//���-��˨��ĸ��
	
	private String checkingProject;//�춨��Ŀ
	private int checkingProcessState;//�춨����״̬
	private String checkingPrice;//�춨�۸�
	private String checkingDate;//��������
	private int contractId;//��ͬ���
	private int contractType;
	private int contractState;
	
	/*
	"entrustedInspectionId": "12", 
	"clientsContacts": "3", 
	"clientsEmployer": "��ʯ�Ͷ����ܵ����޹�˾", 
	"clientsEmployerAddress": "�Ͼ�", 
	"clientsPhone": "13499998888", 
	"clientsPostcode": "3", 
	"clientsEmail": "sy@163.com", 
	"flowmeterName": "��Ȼ���豸", 
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
	"flowmeterAccessories": "������˵���飬���ڼ춨֤�飬ά�޺���Ա��棬�������Ҫ��cvbcvb", 
	"flowmeterAppearance": "������Ϣ�����������ã���������ǻ��࣬������̽ͷ��࣬����ת��ת��������", 
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
