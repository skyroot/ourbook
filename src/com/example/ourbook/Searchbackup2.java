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
import com.example.ourbook.SearchBookDetail;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
//import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
//import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
//******************************************************************************
//search.java的作用，输入来之intent方式传入的str2即查询的书名字符串，形成所需向服务器的查询请求URL，调用JsoupUtil进行请求和返还数据解析，得到list；
//然后定义listview和adaper，绑定数据源list，输出到listview相对应xml上；
//接收listview上的onItemClick（），获取到当前item对应的bookno，intent方式传递并启动searchBookDeatail.java
//**********************************************************************************

public class Searchbackup2 extends Activity  {
	private ListView listView;
	private String html;
	private TextView sumNumber;
	private TextView pageNumber;
	

	private Button nextButton;
	private Button preButton;
	//private Button button;
	List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
	 
	 protected void onCreate(Bundle savedInstanceState) 
	 
{
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			setTitle("图书查询结果");
			setContentView(R.layout.search);
					
			listView = (ListView)findViewById(R.id.search_section_list);
			
				
String MAIN_URL="http://my1.hzlib.net/opac3/search";

String URL1 = "?searchWay=title&q=";
String URL2 = "&searchSource=reader&booktype=&scWay=dim&marcformat=&sortWay=score&sortOrder=desc&startPubdate=&endPubdate=&rows=10&hasholding=1";

System.out.println("已进入Search.java");	
String URL3=null;
String str2 = " ";

Intent intent1 = this.getIntent();  
str2=intent1.getStringExtra("str2");

System.out.println(str2);	

 try {
	URL3 = URLEncoder.encode(str2,"UTF-8");
     } catch (UnsupportedEncodingException e1) 
     {
	// TODO Auto-generated catch block
	e1.printStackTrace();
     }

 System.out.println(URL3);	
 String url = MAIN_URL + URL1 + URL3+URL2;

 new LoadBookInfo().execute(url);
 JsoupUtil.clearInfo();
  
 Button nextButton=(Button)findViewById(R.id.next);
 Button preButton=(Button)findViewById(R.id.pre);
	
// button=(Button)findViewById(R.id.collect_btn);
 
  nextButton.setOnClickListener(new OnClickListener()
  	  {
		@Override
		public void onClick(View v) 
		{
			// TODO Auto-generated method stub
			new LoadBookInfo().execute(JsoupUtil.nextUrl);
			JsoupUtil.page++;
			
		}
        }); 
	
  preButton.setOnClickListener(new OnClickListener()
		{
		  @Override
		   public void onClick(View v) 
		   {
			// TODO Auto-generated method stub
			new LoadBookInfo().execute(JsoupUtil.preUrl);
			JsoupUtil.page--;
			
		   }
		   }); 
  
  /*button.setOnClickListener(new OnClickListener()
	  {
	@Override
	public void onClick(View v) 
	{
		// TODO Auto-generated method stub
		 
		
	}
    }); */
  
  
 }
	 
	 class LoadBookInfo extends	AsyncTask<String, ListView, List<Map<String, Object>>> 
{

       @Override
       protected void onPreExecute() 
      {
	// TODO Auto-generated method stub
	System.out.println("onPreExecute");
	//mypDialog.show();
	super.onPreExecute();
      }

      @Override
      protected List<Map<String, Object>> doInBackground(String... params) 
     {
	// TODO Auto-generated method stub
	System.out.println("doInBackground");
	// System.out.println("html:" + html);
	// System.out.println(params[0]);
	return JsoupUtil.searchBook(params[0]);
     }

     @Override
     protected void onPostExecute(List<Map<String, Object>> result) 
     {
	// TODO Auto-generated method stub
	System.out.println("onPostExecute");
	// 显示总数、页码及图书列表
	//mypDialog.cancel();
	if (result == null) {
		finish();
		Toast.makeText(getApplicationContext(), "本馆没有您检索的纸本馆藏书目",
				Toast.LENGTH_LONG).show();
	   } else 
	        {
		//sumNumber.setText(JsoupUtil.sumNumber.toString());
		//pageNumber.setText(JsoupUtil.pageNumber);
		
		
		SimpleAdapter listAdapter = new SimpleAdapter(
				getApplicationContext(), result, R.layout.book_list,
				new String[] { "book_title", "book_author", "book_cbs"}, new int[] { R.id.bookTitle,
					R.id.bookAuthor, R.id.bookCbs });
		listView.setAdapter(listAdapter);
				
		listView.setOnItemClickListener(new OnItemClickListener()
			{  
			  
			   @SuppressWarnings("unchecked")  
			   @Override  
			   public void onItemClick(AdapterView<?> parent, View view,  
			     int position, long id) {  
			    ListView listView = (ListView)parent;  
			    HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(position);  
			    String book_number = map.get("book_number");  
			    System.out.println(book_number);
			    Intent mainIntent1 = new Intent();
				mainIntent1.putExtra("book_number", book_number);  
				mainIntent1.setClass(Searchbackup2.this,SearchBookDetail.class);
				startActivity(mainIntent1);
			    
			    }  
			 });  
		 super.onPostExecute(result);
	         }
         }

}//onCreate()结束
}//Search.class 结束


