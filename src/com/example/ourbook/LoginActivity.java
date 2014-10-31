package com.example.ourbook;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;

import com.example.ourbook.JsoupUtil;
import com.example.ourbook.GlobleData2;
import com.example.ourbook.BorrowedActivity;


import com.jiezhi.lib.StudentInfoActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	private Button loginButton;
	private EditText number; 
	private EditText password;
	private CheckBox recd;
	private ProgressDialog mypDialog;
	private Class<?> cls = BorrowedActivity.class;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
	/*String info = this.getIntent().getStringExtra("info");
		if (info.equals("borrowed")) {
			cls = BorrowedActivity.class;
		} else if (info.equals("studentInfo")) {
			cls = StudentInfoActivity.class;
		}*/
		//初始化登陆进度条
		mypDialog=new ProgressDialog(LoginActivity.this);
		//实例化
        mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //设置进度条风格，风格为圆形，旋转的
        mypDialog.setTitle("登陆");
        //设置ProgressDialog 标题
        mypDialog.setMessage("登陆中...");
        //设置ProgressDialog 提示信息
//        mypDialog.setIcon(R.drawable.android);
        //设置ProgressDialog 标题图标
//        mypDialog.setButton("Google",this);
        //设置ProgressDialog 的一个Button
        mypDialog.setIndeterminate(false);
        //设置ProgressDialog 的进度条是否不明确
//        mypDialog.setCancelable(true);
        //设置ProgressDialog 是否可以按退回按键取消

        
		recd=(CheckBox)findViewById(R.id.recd);
		number=(EditText)findViewById(R.id.number);
		password=(EditText)findViewById(R.id.pass);
		loginButton=(Button)findViewById(R.id.signin_button);
		
		SharedPreferences sp=getSharedPreferences("lib", MODE_APPEND);
		String user=sp.getString("user", "");
		String pass=sp.getString("password", "");
		
		if(user!=""){
			number.setText(user);
			password.setText(pass);
			recd.setChecked(true);
		}
		loginButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
						
					String user = number.getText().toString();
					String passwd = password.getText().toString();
					if (recd.isChecked()) {
						SharedPreferences pre = getSharedPreferences("lib",
								MODE_APPEND);
						Editor edit = pre.edit();
						edit.putString("user", user);
						edit.putString("passwd", passwd);
						// edit.putString("cookie", l.getCookie());
						edit.commit();

					} else {
						SharedPreferences sp = getSharedPreferences("lib",
								MODE_APPEND);
						sp.edit().clear();
					}
					Login login = new Login();
					login.execute(user, passwd);

				}
				
			
		});
	}
	
	class Login extends AsyncTask<String, Integer, Boolean> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			mypDialog.show();
			super.onPreExecute();
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

		@Override
		protected Boolean doInBackground(String... params) {
			// TODO Auto-generated method stub
			return JsoupUtil.loginUrl(params[0], params[1]);
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			mypDialog.cancel();
			if (result) {
				// 已经等录
				GlobleData2.flag = true;
				Intent i = new Intent();
				i.setClass(getApplicationContext(), cls);
				startActivity(i);
				finish();
			} else {
				GlobleData2.showToast(getApplicationContext(), "登录失败，请检查账号和密码！");
				// AlertDialog.Builder builder = new AlertDialog.Builder(
				// LoginActivity.this);
				// builder.setMessage("登录失败，请检查账号和密码！");
				// AlertDialog ad = builder.create();
				// ad.show();
			}
			super.onPostExecute(result);
		}
	}
}


