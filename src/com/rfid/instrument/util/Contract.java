package com.rfid.instrument.util;

public class Contract {
	int contractId;
	String contractNumber;
	String contractUintName;
	String contractCreationUserName;
	String contractCreationDate;
	String contractSigningUserName;
	String contractSigningDate;
	int contractType;
	int contractState;
	int contractPrice;
	int checkingProcessState;
	public int getContractId() {
		return contractId;
	}
	public void setContractId(int contractId) {
		this.contractId = contractId;
	}
	
	public int getCheckingProcessState() {
		return checkingProcessState;
	}
	public void setCheckingProcessState(int checkingProcessState) {
		this.checkingProcessState = checkingProcessState;
	}
	public String getContractNumber() {
		return contractNumber;
	}
	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}
	public String getContractUintName() {
		return contractUintName;
	}
	public void setContractUintName(String contractUintName) {
		this.contractUintName = contractUintName;
	}
	public String getContractCreationUserName() {
		return contractCreationUserName;
	}
	public void setContractCreationUserName(String contractCreationUserName) {
		this.contractCreationUserName = contractCreationUserName;
	}
	public String getContractCreationDate() {
		return contractCreationDate;
	}
	public void setContractCreationDate(String contractCreationDate) {
		this.contractCreationDate = contractCreationDate;
	}
	public String getContractSigningUserName() {
		return contractSigningUserName;
	}
	public void setContractSigningUserName(String contractSigningUserName) {
		this.contractSigningUserName = contractSigningUserName;
	}
	public String getContractSigningDate() {
		return contractSigningDate;
	}
	public void setContractSigningDate(String contractSigningDate) {
		this.contractSigningDate = contractSigningDate;
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
	public int getContractPrice() {
		return contractPrice;
	}
	public void setContractPrice(int contractPrice) {
		this.contractPrice = contractPrice;
	}
	@Override
	public String toString() {
		return "Contract [contractId=" + contractId + ", contractNumber="
				+ contractNumber + ", contractUintName=" + contractUintName
				+ ", contractCreationUserName=" + contractCreationUserName
				+ ", contractCreationDate=" + contractCreationDate
				+ ", contractSigningUserName=" + contractSigningUserName
				+ ", contractSigningDate=" + contractSigningDate
				+ ", contractType=" + contractType + ", contractState="
				+ contractState + ", contractPrice=" + contractPrice
				+ ", checkingProcessState=" + checkingProcessState + "]";
	}

}
