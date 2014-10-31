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

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;








import android.app.Activity;
import android.content.Intent;
//import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
//import android.widget.SimpleAdapter;
import android.widget.Toast;

public class Searchbackup extends Activity{
	 List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
	 
	 protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			setTitle("图书查询结果");
			setContentView(R.layout.search);
			
			
			
			ListView listview = (ListView)findViewById(R.id.search_section_list);
			
			HttpClient client = new HttpClient(); 
    // 设置代理服务器地址和端口      
    //client.getHostConfiguration().setProxy("proxy_host_addr",proxy_port); 
    // 使用 GET 方法 ，如果服务器需要通过 HTTPS 连接，那只需要将下面 URL 中的 http 换成 https 
			
String MAIN_URL="http://my1.hzlib.net/opac3/search";
String URL1 = "?searchWay=title&q=";
String URL2 = "&searchSource=reader&booktype=&scWay=dim&marcformat=&sortWay=score&sortOrder=desc&startPubdate=&endPubdate=&rows=10&hasholding=1";
//String URL3 = "安卓";
System.out.println("已进入Search.java");	
//String URL3=null;
String str2 = "安卓 ";

//Intent intent1 = this.getIntent();  
//str2=intent1.getStringExtra("str2");
//System.out.println(str2);	

String URL3=null;
try {
	URL3 = URLEncoder.encode(str2,"UTF-8");
} catch (UnsupportedEncodingException e1) {
	// TODO Auto-generated catch block
	e1.printStackTrace();
}

System.out.println(URL3);	
String url = MAIN_URL + URL1 + URL3+URL2;
//String USER_AGENT = "Mozilla/4.0 (compatible; MSIE 7.0; Win32)";  
//String userAgent = USER_AGENT;  
//HttpParams.setUserAgent(httpParams, userAgent); 

Document doc = null;
try {
	doc = Jsoup.connect(url).timeout(30 * 1000).get();
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}

System.out.println(doc);



   /* GetMethod method=new GetMethod(url);
    method.addRequestHeader("Content-type" , "text/html;charset=ISO-8859-1");
    //使用POST方法
    //HttpMethod method = new PostMethod("http://www.baidu.com");
    
    
   //method.addHeader(HttpHeaders.USER_AGENT, userAgent);
    client.getParams().setParameter(HttpMethodParams.USER_AGENT,"Mozilla/5.0 (X11; U; Linux i686; zh-CN; rv:1.9.1.2) Gecko/20090803 Fedora/3.5.2-2.fc11 Firefox/3.5.2");
    try {
		client.executeMethod(method);
	} catch (HttpException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
    //RequestUserAgent(userAgent);
    
    System.out.println(url);
    System.out.println(method.getResponseCharSet());
    
    //打印服务器返回的状态
     System.out.println(method.getStatusLine());
     
    //打印返回的信息
    //System.out.println(method.getResponseBodyAsString());
    
    //******使用getResposeBodyAsString（）-----》使用getResposeBodyAsStream（）来获取服务器返还信息的string化*********
    BufferedReader reader = null;
	try {
		reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(),"UTF-8"));
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    StringBuffer stringBuffer = new StringBuffer();
    String str1 = "";
    try {
		while((str1 = reader.readLine())!=null){
			stringBuffer.append(str1);
		}
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    String html = stringBuffer.toString();
    
   // System.out.println(html);
    //释放连接
    method.releaseConnection();
    */
    
   //开始使用Jsoup解析服务器返还的查询结果html string
    
   
    //Document doc=Jsoup.parse(html);
    Elements tds=doc.select("td[class=bookmetaTD]");
    
    
    String sumNumber = doc.select("div[id=search_meta]").text();
    String pageNumber = doc.select("span[class=disabled]").text();
    System.out.println(sumNumber);
    System.out.println(pageNumber);
    //Elements pngs = doc.select("img[class=bookcover_img]");
    int Totaltds=tds.size();
    //int Totalpngs=pngs.size();
    System.out.println(Totaltds);
   // System.out.println(Totalpngs);
    
    if (Totaltds == 0) {
		Toast.makeText(getApplicationContext(), "检索不到记录!！",
				Toast.LENGTH_LONG).show();
		finish();
	} 
    else {
    
    for(int i=0;i<Totaltds;i++) //从第0本书到第i本书
    {
  	 //Elements book=tds.get(i).select("div[class=bookmetaTitle]"); 
  	 //span[class=bookmetaTitle]
  	 
  	 Map<String,Object> map=new HashMap<String,Object>();
  	 
  	for(int j=0;j<3;j++)
  	{
  		map.put("book_title",tds.get(i).select("span[class=bookmetaTitle]").text());
  		map.put("book_author",tds.get(i).getElementsContainingOwnText("著者:").select("a[href]").text());
  		map.put("book_cbs",tds.get(i).getElementsContainingOwnText("出版社:").select("a[href]").text());
  		
  	}
  	
  	list.add(map);
  	
    }
    //System.out.println(list.get(1));
    //System.out.println(list.get(2));
    //System.out.println(list.get(3));
    
   
  	SimpleAdapter listAdapter = new SimpleAdapter(
			getApplicationContext(), list, R.layout.book_list,
			new String[] { "book_title", "book_author", "book_cbs"}, new int[] { R.id.bookTitle,
					R.id.bookAuthor, R.id.bookCbs }); 
  	//ArrayAdapter<Map<String, Object>> adapter = new ArrayAdapter<Map<String, Object>>(this, android.R.layout.simple_list_item_1, list); 
	listview.setAdapter(listAdapter);
	//setContentView(listview);
  	 //System.out.println(book_author);
	} 
	}
	 }

