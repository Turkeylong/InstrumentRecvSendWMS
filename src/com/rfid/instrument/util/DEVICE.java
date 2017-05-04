package com.rfid.instrument.util;

public class DEVICE {
	int id;
	int deviceState;
	String deviceName;
	String devicePosition;
	int order;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDeviceState() {
		return deviceState;
	}
	public void setDeviceState(int deviceState) {
		this.deviceState = deviceState;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public String getDevicePosition() {
		return devicePosition;
	}
	public void setDevicePosition(String devicePosition) {
		this.devicePosition = devicePosition;
	}
	@Override
	public String toString() {
		return "DEVICE [id=" + id + ", deviceState=" + deviceState
				+ ", deviceName=" + deviceName + ", devicePosition="
				+ devicePosition + ", order=" + order + "]";
	}
	
	
}
