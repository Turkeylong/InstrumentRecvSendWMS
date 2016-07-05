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
		// ��ȡ��������䲢��ʾ
		mEmail = getIntent().getStringExtra(EXTRA_EMAIL);
		mEmailView = (EditText) findViewById(R.id.username_edit);
		mEmailView.setText(mEmail);
		// ������༭�����ж�����̵�ѡ������Ӧ����
		mPasswordView = (EditText) findViewById(R.id.password_edit);
		mPasswordView.setOnEditorActionListener(
				new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.login || id == EditorInfo.IME_NULL) {// �ж������ѡ�������
							attemptLogin();
							return true;
						}
						return false;
					}
				});
		mLoginFormView = (ScrollView) findViewById(R.id.login_form);
		mLoginStatusView = (LinearLayout) findViewById(R.id.login_status);
		mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);
		// �ύ������Ӧ����
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
		//���������Ĵ�����ʾΪ��
		mEmailView.setError(null);
		mPasswordView.setError(null);
		//��ȡ���������������
		mEmail = mEmailView.getText().toString();
		mPassword = mPasswordView.getText().toString();
		boolean cancel = false;
		View focusView = null;
		// �������������ĸ�ʽ������Ϊ�գ�����С��4λ�������ʽ�������»�ý��㣬����ʾ��������
		if (TextUtils.isEmpty(mPassword)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		} else if (mPassword.length() < 4) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		}
		// ���������ʽ
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
			//�����ʽ������������»�����뽹��
			focusView.requestFocus();
		} else {
			//�������ĸ�ʽ��ȷ����ʾ��֤�ȴ��Ի��򣬲�������֤�߳�
			mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
			showProgress(true);
			mAuthTask = new UserLoginTask();
			mAuthTask.execute((Void) null);
		}
	}
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)//ָ��Ӧ�ó����API�汾
	private void showProgress(final boolean show) {
		//��ȡ����ƽ̨�İ汾��Ӧ�õİ汾�Ա�ʵ�ֹ��ܵļ�����
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);//��ȡϵͳ�����ʱ��

			mLoginStatusView.setVisibility(View.VISIBLE);//������֤�Ի���Ϊ����
			mLoginStatusView.animate().setDuration(shortAnimTime)//���ö�����ʾʱ��
					.alpha(show ? 1 : 0)//���ö�������Ч��
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);//���ݲ������Ƹÿؼ���ʾ������
						}
					});
			mLoginFormView.setVisibility(View.VISIBLE);//��������������
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
			mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);//���ݲ������Ƹÿؼ���ʾ������
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}
	public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {//��̨�����߳�
			try {
				//ģ���û���֤��ʱ
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				return false;
			}

			for (String credential : DUMMY_CREDENTIALS) {//����������֤�Զ����û�������
				String[] pieces = credential.split(":");//�ָ��ַ������������������뿪
				if (pieces[0].equals(mEmail)) {
					return pieces[1].equals(mPassword);
				}
			}
			return true;
		}

		@Override
		protected void onPostExecute(final Boolean success) {//�߳̽������ui����
			mAuthTask = null;
			showProgress(false);//������֤��ʱ�Ի���

			if (success) {
				finish();
				Intent intent=new Intent(LoginActivity.this,MainActivity.class);
				startActivity(intent);
			} else {//�������������ý��㣬����ʾ����
				mPasswordView
						.setError(getString(R.string.error_incorrect_password));
				mPasswordView.requestFocus();
			}
		}
		//ȡ����֤
		@Override
		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
		}
		
		
	}
}
