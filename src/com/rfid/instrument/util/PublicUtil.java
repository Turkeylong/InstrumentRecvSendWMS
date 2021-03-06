package com.rfid.instrument.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import com.rfid.instrument.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class PublicUtil {
	private static final String TAG="PublicUtil";
	public static int width = 0;
	public static int height = 0;
	public static int dpi = 0;
	
	public static String USER_SESSION       = "session";
	public static String USER_ID = "";
	public static String USER_NAME     		= "";
	public static String USER_UNIT       	= "";
	public static String USER_POWER 		= "";
	public static String USER_ADDR 			= "";
	public static String USER_POSTCODE 		= "";
	public static String USER_EMAIL 		= "";
	public static String USER_REG_TIME 		= "";
	public static String USER_ROLE			= "";
	
	public static final int REQ_SUBMIT_PWD = 2;
	public static final int REQ_COMMITE_INFO = 101;
	public static final int REQ_FLOWMETER_INFO = 102;
	public static final int REQ_CHANGE_STATE = 108;
	public static final int REQ_CONTRACT_STATE = 301;
	public static final int REQ_CERTIFICATE_STATE = 103;
	
	public static final int RESP_LOGIN_RES = 1000;
	public static final int RESP_FLOWMETER_INFO = 1001;
	public static final int RESP_DEVICE_INFO = 1002;
	public static final int RESP_CHANGE_STATE_INFO = 1003;
	public static final int RESP_ALL_DEVICE_INFO = 1004;
	public static final int RESP_ALL_CONTRACT_INFO = 1005;
	public static final int RESP_ALL_CERTIFICATE_INFO = 1006;
	public static final int RESP_COMMITE_INFO = 1007;
	public static final int RESP_CONTRACT_INFO = 1008;
	public static final int RESP_FLOWRECV_INFO = 1009;
	
	public static boolean isConnect(Context context)
	{
		boolean flag = false;
		try
		{
			ConnectivityManager connectivity = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivity != null)
			{
				// 获取网络连接管理的对象
				NetworkInfo info = connectivity.getActiveNetworkInfo();
				if (info != null && info.isConnected())
				{
					if (info.getState() == NetworkInfo.State.CONNECTED)
					{
						flag = true;
					}
				}
			}
		} catch (Exception e)
		{
			Log.e(TAG, e.toString());
		}
		return flag;
	}
	
	private static Toast toast;
	private static View toastRoot;
	private static TextView text;
	
	public static void toastShow(String mes, Context context)
	{
		if (null == toastRoot)
		{
			toastRoot = LayoutInflater.from(context).inflate(R.layout.zk_toast_item, null);
			//toastRoot.setAlpha(0.5f);
		}
		if (null == toast)
		{
			toast = new Toast(context);
			toast.setView(toastRoot);
			toast.setGravity(Gravity.CENTER, 0, 0);
		}
		if (null == text)
		{
			text = (TextView) toastRoot.findViewById(R.id.zk_toast_mes);
		}
		text.setText(mes);
		toast.show();
	}
	
	public static final int castToInt(Object value) throws JSONException {
        if (value == null) {
            return 0;
        }
 
        if (value instanceof Integer) {
            return (Integer) value;
        }
 
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
 
        if (value instanceof String) {
            String strVal = (String) value;
            if (strVal.length() == 0) {
                return 0;
            }
 
            return Integer.parseInt(strVal);
        }
 
        throw new JSONException("can not cast to int, value : " + value);
    }
	
	public static int getIntValue(Object value) {
        int res = 0;
        if (value == null) {
            return 0;
        }
 
        try {
			res = castToInt(value);
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
        return res;
    }

	public static boolean isPhoneNumber(String number)
	{
		boolean flag = false;
		Pattern pattern = Pattern.compile("^[1][0-9]{10}$");// [1][358]\\d{9}
		if (pattern.matcher(number).matches())
		{
			flag = true;
		}
		return flag;
	}

	// 中文转码
	public static String getURLEncoder(String str)
	{
		try
		{
			str = URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException ex)
		{
			ex.printStackTrace();
		}
		return str;
	}

	// 中文转码
	public static String getURLDecoder(String str)
	{
		String msg = str;
		try
		{
			msg = URLDecoder.decode(str, "UTF-8");
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		return msg;
	}

	public static boolean isNullOrEmpty(String str)
	{
		boolean flag = false;
		if (null == str || "".equals(str))
		{
			flag = true;
		}
		return flag;
	}

	/** 本地对象 **/
	public static SharedPreferences getShare(Context context)
	{
		SharedPreferences share = context.getSharedPreferences("zk_rfid_share_info", Context.MODE_PRIVATE
				+ Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE);
		return share;
	}

	public static SharedPreferences getPositionShare(Context context)
	{
		SharedPreferences share = context.getSharedPreferences("zk_rfid_share_pos", Context.MODE_PRIVATE
				+ Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE);
		return share;
	}

	/** email正则 **/
	public static boolean isEmail(String email)
	{
		boolean flag = false;
		try
		{
			Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");// 复杂匹配
			Matcher m = p.matcher(email);
			flag = m.matches();
		} catch (Exception e)
		{
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	/** 获取SD卡路径，指定文件夹 **/
	public static String getSDPath(String folder)
	{
		String sdDir = "";
		boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);// 判断sd卡是否存在
		if (sdCardExist)
		{
			sdDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + folder + "/";// 获取根目录
			File dir = new File(sdDir);
			if (!dir.exists())
			{
				dir.mkdirs();
			}
		}
		return sdDir;
	}

	/** Bitmap压缩 **/
	public static Bitmap compressImage(Bitmap image)
	{

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while (baos.toByteArray().length / 1024 > 15)
		{ // 循环判断如果压缩后图片是否大于10kb,大于继续压缩
			baos.reset();// 重置baos即清空baos
			options -= 10;// 每次都减少10
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
		return bitmap;
	}

	public static String convertIconToString(Bitmap bitmap)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();// outputstream
		bitmap.compress(CompressFormat.PNG, 100, baos);
		byte[] appicon = baos.toByteArray();// 转为byte数组
		return Base64.encodeToString(appicon, Base64.DEFAULT);
	}

	// 如果图片大于maxSize
	public static boolean isSurpassMaxSize(Bitmap bitmap, int maxSize)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		if (baos.toByteArray().length / 1024 > maxSize)
		{
			return true;
		}
		return false;
	}

	// 是否是数字
	public static boolean isNumber(String number)
	{
		boolean flag = true;
		if (null == number || "".equals(number))
		{
			flag = false;
		} else
		{
			for (int i = 0; i < number.length(); i++)
			{
				if (!Character.isDigit(number.charAt(i)))
				{
					flag = false;
					break;
				}
			}
		}
		return flag;
	}

	public static byte[] bitmapBytes(Bitmap bm)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		return baos.toByteArray();
	}

	// 图片圆角处理
	public static Bitmap toRoundCorner(Bitmap bitmap, int dpRound, Context context)
	{
		int pxRound = dip2px(context, dpRound);
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		// final int color = 0xff424242;
		// final int color=R.color.pt_goods_center_textcolor;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = pxRound;
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		// paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue)
	{
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	// 获取状态栏的高度
	public static int getStatusHeight(Context ctx)
	{
		Class<?> c = null;
		Object obj = null;
		Field field = null;
		int x = 0, sbar = 0;
		try
		{
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			sbar = ctx.getResources().getDimensionPixelSize(x);
			Log.e(TAG, "statusHeigth:" + sbar);
		} catch (Exception e1)
		{
			e1.printStackTrace();
		}
		return sbar;
	}

	public static int px2dip(Context context, float pxValue)
	{
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/** 获取指定长度随机数 **/
	public static String getRandom(int len)
	{
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		if (len == 0)
		{
			len = 1;
		}
		for (int i = 0; i < len; i++)
		{
			sb.append(String.valueOf(Math.abs(random.nextInt()) % 10));
		}
		return sb.toString();
	}

	// 获取MAC
	public static String getPhoneMac(Context context)
	{
		String mac = "";
		try
		{
			WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
			WifiInfo info = wifi.getConnectionInfo();
			mac = info.getMacAddress();
			if (mac.length() > 20)
			{
				mac = "";
			}
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return mac;
	}

	public static Map<String, String> getJsonMap(String jsonString) {
		// TODO 自动生成的方法存根
		Map<String, String> resultMap = null;
		try
		{
			if (null != jsonString && !"".equals(jsonString))
			{
				resultMap = new HashMap<String, String>();
				JSONObject jsonObject = new JSONObject(jsonString);

				for (Iterator<String> iter = jsonObject.keys(); iter.hasNext();)
				{
					String key = iter.next();
					resultMap.put(key, String.valueOf(jsonObject.get(key)));
				}
			}

		} catch (Exception e)
		{
			Log.e(TAG, "解析公共JSON异常：" + e.getMessage());
		}

		return resultMap;
	}
}
