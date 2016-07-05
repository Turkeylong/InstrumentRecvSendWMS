package com.rfid.instrument.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.rfid.instrument.R;

public class LoginActivity extends Activity {
	private static final String EXTRA_EMAIL = "";
	private static final String[] DUMMY_CREDENTIALS = new String[]{
        "foo@163.com:20135115",
        "bar@163.com:20135115",
        "123@123:1234"
	};
	private String mEmail;
	private EditText mEmailView;
	private EditText mPasswordView;
	private ScrollView mLoginFormView;
	private LinearLayout mLoginStatusView;
	private TextView mLoginStatusMessageView;
	private UserLoginTask mAuthTask;
	private String mPassword;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		// Set up the login form.
		// 获取引入的邮箱并显示
		mEmail = getIntent().getStringExtra(EXTRA_EMAIL);
		mEmailView = (EditText) findViewById(R.id.username_edit);
		mEmailView.setText(mEmail);
		// 在密码编辑界面判断软键盘的选择，做对应操作
		mPasswordView = (EditText) findViewById(R.id.password_edit);
		mPasswordView.setOnEditorActionListener(
				new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.login || id == EditorInfo.IME_NULL) {// 判断软件盘选择的内容
							attemptLogin();
							return true;
						}
						return false;
					}
				});
		mLoginFormView = (ScrollView) findViewById(R.id.login_form);
		mLoginStatusView = (LinearLayout) findViewById(R.id.login_status);
		mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);
		// 提交按键响应处理
		findViewById(R.id.sign_in_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						attemptLogin();
					}
				});
	}

	public void attemptLogin() {
		if (mAuthTask != null) {
			return;
		}
		//设置输入框的错误提示为空
		mEmailView.setError(null);
		mPasswordView.setError(null);
		//获取输入框的邮箱和密码
		mEmail = mEmailView.getText().toString();
		mPassword = mPasswordView.getText().toString();
		boolean cancel = false;
		View focusView = null;
		// 设置密码输入框的格式（不能为空，不能小于4位）如果格式错误重新获得焦点，并提示错误内容
		if (TextUtils.isEmpty(mPassword)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		} else if (mPassword.length() < 4) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		}
		// 设置邮箱格式
		if (TextUtils.isEmpty(mEmail)) {
			mEmailView.setError(getString(R.string.error_field_required));
			focusView = mEmailView;
			cancel = true;
		} else if (!mEmail.contains("@")) {
			mEmailView.setError(getString(R.string.error_invalid_email));
			focusView = mEmailView;
			cancel = true;
		}
		if (cancel) {
			//如果格式错误，输入框重新获得输入焦点
			focusView.requestFocus();
		} else {
			//如果输入的格式正确，显示验证等待对话框，并启动验证线程
			mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
			showProgress(true);
			mAuthTask = new UserLoginTask();
			mAuthTask.execute((Void) null);
		}
	}
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)//指出应用程序的API版本
	private void showProgress(final boolean show) {
		//获取运行平台的版本与应用的版本对比实现功能的兼容性
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);//获取系统定义的时间

			mLoginStatusView.setVisibility(View.VISIBLE);//设置验证对话框为可显
			mLoginStatusView.animate().setDuration(shortAnimTime)//设置动画显示时间
					.alpha(show ? 1 : 0)//设置动画渐变效果
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);//跟据参数控制该控件显示或隐藏
						}
					});
			mLoginFormView.setVisibility(View.VISIBLE);//设置输入界面可显
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);//跟据参数控制该控件显示或隐藏
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}
	public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {//后台运行线程
			try {
				//模拟用户验证耗时
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				return false;
			}

			for (String credential : DUMMY_CREDENTIALS) {//遍历数组验证自定义用户及密码
				String[] pieces = credential.split(":");//分割字符串，将密码个邮箱分离开
				if (pieces[0].equals(mEmail)) {
					return pieces[1].equals(mPassword);
				}
			}
			return true;
		}

		@Override
		protected void onPostExecute(final Boolean success) {//线程结束后的ui处理
			mAuthTask = null;
			showProgress(false);//隐藏验证延时对话框

			if (success) {
				finish();
				Intent intent=new Intent(LoginActivity.this,MainActivity.class);
				startActivity(intent);
			} else {//密码错误，输入框获得焦点，并提示错误
				mPasswordView
						.setError(getString(R.string.error_incorrect_password));
				mPasswordView.requestFocus();
			}
		}
		//取消验证
		@Override
		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
		}
		
		
	}
}
