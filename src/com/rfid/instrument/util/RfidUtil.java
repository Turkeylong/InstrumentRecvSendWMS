package com.rfid.instrument.util;

import com.rfid.sdk.protocol.Reader_TCP_Protocol;
import com.rfid.sdk.protocol.Reader_UART_Protocol;
import com.rfid.sdk.public_utils.FormatTransfer;
import com.rfid.sdk.public_utils.Public;
import com.rfid.sdk.rfidclass.ANTERNNA;
import com.rfid.sdk.rfidclass.TAG_ACCESS;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;

public class RfidUtil {
	private static final String TAG = "RfidUtil";
	private static RfidUtil rfid = null;
	private Handler handler = null;
	private static Context mContext = null;
	private Reader_TCP_Protocol tcpProtocol;
	private Reader_UART_Protocol uartProtocol;
	public RfidUtil(Context context) {
		// TODO 自动生成的构造函数存根
		if (ConfigUtil.USE_EPC_METHOD == ConstantUtil.EPC_METHOD_COM)
		{
			uartProtocol = Reader_UART_Protocol.getInstance(context);
		}

		if (ConfigUtil.USE_EPC_METHOD == ConstantUtil.EPC_METHOD_WAN)
		{
			tcpProtocol = Reader_TCP_Protocol.getInstance(context);
		}
	}

	public void setHandler(Handler handler) {
		// TODO 自动生成的方法存根
		this.handler = handler;
		if(null != uartProtocol)
			uartProtocol.setHandler(handler);
	}
	public void connectReader() {
		// TODO 自动生成的方法存根
		if(null != uartProtocol)
			uartProtocol.connectReader();
	}
	public static RfidUtil getInstance(Context context) {
		// TODO 自动生成的方法存根
		mContext = context;
		if(null==rfid)
		{
			rfid=new RfidUtil(context);
		}
		return rfid;
	}
	public void startScan() {
		// TODO 自动生成的方法存根
		if(null != uartProtocol)
			uartProtocol.startInventory();
	}
	public void stopScan() {
		// TODO 自动生成的方法存根
		if(null != uartProtocol)
			uartProtocol.stopInventory();
	}
	
	public void reset() {
		// TODO 自动生成的方法存根
		if(null != uartProtocol)
			uartProtocol.reset();
	}

	public void setPower(boolean power_on) {
		// TODO 自动生成的方法存根
		if(null != uartProtocol)
		{
			if(power_on)
				uartProtocol.power_on();
			else
				uartProtocol.power_off();
		}
	}

	public void readEPC() {
		// TODO 自动生成的方法存根
		{
			TAG_ACCESS tag = new TAG_ACCESS(Public.EPC, 2, 8,0, 0);
			uartProtocol.readTagAccessBank(tag);
		}
	}
	
	public void writeEPC(String epc) {
		TAG_ACCESS tag = new TAG_ACCESS(Public.EPC, 1, 9,0, 0);
		tag.setData(FormatTransfer.string2shorts("4000"+epc));
		uartProtocol.writeTagAccessBank(tag);
	}
	
	public void setPower(final boolean enbale, final int power, final int dwelltime, final int inv_rounds, final int cycles)
	{
		if(null != uartProtocol)
		{
		ANTERNNA ant1 = new ANTERNNA(0, enbale, power, dwelltime, inv_rounds);
		//final ANTERNNA ant2 = new ANTERNNA(1, enbale, power, dwelltime, inv_rounds);
		ant1.setCycles((short) cycles);
		//ant2.setCycles((short) cycles);
		uartProtocol.setAntParm(0, ant1);
		//SystemClock.sleep(100);
		//uartProtocol.setAntParm(1, ant2);
		}
	}
}
