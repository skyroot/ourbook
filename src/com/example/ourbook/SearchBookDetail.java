package com.example.ourbook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.ourbook.JsoupUtil;
import com.example.ourbook.Search.LoadBookInfo;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
//import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
//import android.widget.SimpleAdapter;
import android.widget.Toast;



//******************************************************************************
//SearchBookDetail.java的作用，输入来之intent方式传入的book_number即当前点击查询的某本书的bookno，形成所需向服务器的查询请求URL，
//调用JsoupUtil进行请求和返还数据解析，得到list；
//然后定义listview和adaper，绑定数据源list，输出到listview相对应xml上；
//
//**********************************************************************************


public class SearchBookDetail extends Activity  {
	private ListView listView01;
	private ListView listView02;
	private TextView textView;
	 List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
	 
	 protected void onCreate(Bundle savedInstanceState)
{
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			setTitle("图书详细信息");
			setContentView(R.layout.book_detail_info);
			String book_number = " ";

			Intent intent1 = this.getIntent();  
			book_number=intent1.getStringExtra("book_number");	
			System.out.println(book_number);
			
			listView01 = (ListView)findViewById(R.id.videos1);
			listView02 = (ListView)findViewById(R.id.videos2);	
			textView=(TextView)findViewById(R.id.book_name);
			
			String MAIN_URL1="http://my1.hzlib.net/opac3/book/";
			String MAIN_URL2="http://my1.hzlib.net/opac3/book/holdingpreview/";
			String LAST_URL1="?view=simple";
			String LAST_URL2="?curLibcodes=";
			
          System.out.println("已进入SearchBookDetail.java");	


try {
	book_number = URLEncoder.encode(book_number,"UTF-8");
} catch (UnsupportedEncodingException e1) {
	// TODO Auto-generated catch block
	e1.printStackTrace();
}

//System.out.println(URL3);	
String html1=MAIN_URL1+book_number+LAST_URL1;//某本书简介信息url
String html2=MAIN_URL2+book_number+LAST_URL2;//馆藏信息url

new LoadBookInfo01().execute(html1);
new LoadBookInfo02().execute(html2);
JsoupUtil.clearInfo();
	
}

	class LoadBookInfo01 extends	AsyncTask<String, ListView, List<Map<String, Object>>> {
//提取某本书的简介信息
@Override
protected void onPreExecute() {
	// TODO Auto-generated method stub
	System.out.println("onPreExecute");
	//mypDialog.show();
	super.onPreExecute();
}

@Override
protected List<Map<String, Object>> doInBackground(String... params) {
	// TODO Auto-generated method stub
	System.out.println("doInBackground");
	// System.out.println("html:" + html);
	// System.out.println(params[0]);
	return JsoupUtil.searchBookDetail01(params[0]);
}

@Override
protected void onPostExecute(List<Map<String, Object>> list01) {
	// TODO Auto-generated method stub
	System.out.println("onPostExecute");
	// 显示总数、页码及图书列表
	//mypDialog.cancel();
	if (list01 == null) {
		finish();
		Toast.makeText(getApplicationContext(), "本馆没有您检索的纸本馆藏书目",
				Toast.LENGTH_LONG).show();
	  } else {
		//sumNumber.setText(JsoupUtil.sumNumber.toString());
		//pageNumber.setText(JsoupUtil.pageNumber);
		
		 
		  
	      System.out.println(list01.get(0)); 
	      
		SimpleAdapter listAdapter = new SimpleAdapter(
				getApplicationContext(), list01, R.layout.lv01,
				new String[] { "ISBN:",  "出版发行:",  "摘要:",  "主要著者:"}, 
				new int[] { R.id.bookisbn,R.id.bookcbfx, R.id.bookzy,R.id.bookzyzz });
		
		listView01.setAdapter(listAdapter);
		
		super.onPostExecute(list01);
	          }
}
} //loadbook()结束
	
  class LoadBookInfo02 extends	AsyncTask<String, ListView, List<Map<String, Object>>> {
//提取某本书的馆藏信息列表
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			System.out.println("onPreExecute");
			//mypDialog.show();
			super.onPreExecute();
		}

		@Override
		protected List<Map<String, Object>> doInBackground(String... params) {
			// TODO Auto-generated method stub
			System.out.println("doInBackground");
			// System.out.println("html:" + html);
			// System.out.println(params[0]);
			return JsoupUtil.searchBookDetail02(params[0]);
		}

		@Override
		protected void onPostExecute(List<Map<String, Object>> list02) {
			// TODO Auto-generated method stub
			System.out.println("onPostExecute");
			// 显示总数、页码及图书列表
			//mypDialog.cancel();
			if (list02 == null) {
				//finish();
				System.out.println("无馆藏信息！");
				Toast.makeText(getApplicationContext(), "没有馆藏在馆图书",
						Toast.LENGTH_LONG).show();
			  } else {
				//sumNumber.setText(JsoupUtil.sumNumber.toString());
				//pageNumber.setText(JsoupUtil.pageNumber);
				
				  System.out.println(list02.get(0)); 
				  
				SimpleAdapter listAdapter = new SimpleAdapter(
						getApplicationContext(), list02, R.layout.lv02,
						new String[] { "索书号", "所属图书馆", "在馆复本"}, new int[] { R.id.callno,
							R.id.curlibName, R.id.copycount });
				listView02.setAdapter(listAdapter);
				
				super.onPostExecute(list02);
			          }
		}
		} //loadbook()结束 
}
