package com.example.ourbook;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.example.ourbook.GlobleData2;

import android.widget.Toast;

//JsoupUtil的作用是根据输入的url，发起jsoup请求，获取服务器应答的doc并进行jsoup解析，输出服务器反馈的数据list

public class JsoupUtil {

	public static Integer sumNumber;
	public static String pageNumber;
	public static String preUrl;
	public static String nextUrl;
	public static int page = 1;// 默认第一页
	public static String bookNumber;
	
	private String user; // 登陆用户
	private String passwd; // 登陆密码
	private String result;// 返回的数据
	private String cookie;
	private String location;
	private final static String LOGIN_URL = "http://my1.hzlib.net/opac3/reader/dologin";
	
	
	// 登录
	public static Boolean loginUrl(String user, String passwd) {
			// TODO Auto-generated method stub
			// number=041210237&passwd=963210&select=cert_no&returnUrl=
		
		Map<String, String> map = new HashMap<String, String>();  
		map.put("rdid", user);  
		map.put("rdPasswd", passwd);  
		Response response = null;
		try {
			response = (Response) Jsoup.connect("http://my1.hzlib.net/opac3/reader/doLogin")  
			                .data(map)  
			                .method(Method.POST)  
			                .timeout(20000)  
			                .execute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
		if (((org.jsoup.Connection.Response) response).statusCode() == 200) {  
			System.out.println("登录成功！");
			System.out.println(response);
			 
			String sessionID = response.cookie("JSESSIONID");
			System.out.println(sessionID);
		    
			GlobleData2.cookies=sessionID;
		    return true;
		    
		}  else {
			System.out.println("登录失败，请检查账号和密码！");
			
			return false;
		}
			
				
				
		}

	
	
	public static void clearInfo() {
		page = 1;
		sumNumber = 0;
		pageNumber = null;
		preUrl = null;
		nextUrl = null;
	}
	
	

	public static List<Map<String, Object>> searchBook(String html) {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		Document doc;
		try {

			// 通过Jsoup的connect（）方法，将html转化成Document
			doc = Jsoup.connect(html).timeout(30 * 1000).get();
			System.out.println("Success to parse!");
			// System.out.println(doc);

			// 判断“本馆没有您检索的纸本馆藏书目”
			String err = doc.select("div[id=resultTile]").select("p").text().toString();
			if (err.equals("检索不到记录!"))
				return null;

			System.out.println("Test!");
			// 获取图书总数以及页码总数
			//sumNumber = getSumNumber(doc);
			//pageNumber = getPageNumber(doc);
			// 获得前后页的链接
			
				getPreAndNextUrl(doc);

			Elements tds=doc.select("td[class=bookmetaTD]");
			int Totaltds=tds.size();
			
		    
		    for(int i=0;i<Totaltds;i++) //从第0本书到第i本书
		    {
		  	 //Elements book=tds.get(i).select("div[class=bookmetaTitle]"); 
		  	 //span[class=bookmetaTitle]
		    	
		  	 Map<String,Object> map = new HashMap<String,Object>();
		  	 
		  	   for(int j=0;j<4;j++)  //每本书的属性
		  	   {
		  		map.put("book_title",tds.get(i).select("span[class=bookmetaTitle]").text());
		  		map.put("book_author",tds.get(i).getElementsContainingOwnText("著者:").select("a[href]").text());
		  		map.put("book_cbs",tds.get(i).getElementsContainingOwnText("出版社:").select("a[href]").text());
		  		map.put("book_number", tds.get(i).select("div[class=bookmeta]").attr("bookrecno"));
		  		
		  	   }
		  	
		  	list.add(map);
		  	
			}
		} catch (IOException e) {
			// 解析失败！
			e.printStackTrace();
			System.out.println("Failed to Parse!");
		}
		return list;
	}
	//获得
	// 获得前后页的链接
	public static void getPreAndNextUrl(Document doc) {
		// TODO Auto-generated method stub
		String MAIN_URL1="http://my1.hzlib.net";
		nextUrl=MAIN_URL1+doc.getElementsContainingOwnText("下一页").attr("href");
		preUrl=MAIN_URL1+doc.getElementsContainingOwnText("上一页").attr("href");
	}

	
	
 

	public static List<Map<String, Object>> searchBookDetail01(String html) {

		List<Map<String, Object>> list01 = new ArrayList<Map<String, Object>>();
		
		Document doc;
		try {

			// 通过Jsoup的connect（）方法，将html转化成Document
			
			doc = Jsoup.connect(html).timeout(30 * 1000).get();
			System.out.println("Success to parse!");
			// System.out.println(doc);

			Elements trs=doc.select("tr");
			
			int Totaltds=trs.size();
			 
		     Map<String,Object> map = new HashMap<String,Object>();//某本书的简介信息
		  	 
		  	   for(int j=0;j<Totaltds;j++)
		  	   {
		  		map.put(trs.get(j).select("td[class=leftTD]").text(),trs.get(j).select("td[class=rightTD]").text());
		  		
		  	   }
		  	list01.add(map);
		  		
		} catch (IOException e) {
			// 解析失败！
			e.printStackTrace();
			System.out.println("Failed to Parse!");
		}
		return list01;
	}
	
	 public static List<Map<String, Object>> searchBookDetail02(String html) {

			List<Map<String, Object>> list02 = new ArrayList<Map<String, Object>>();
			
			Document doc;
			try {

				// 通过Jsoup的connect（）方法，将html转化成Document
				
				doc = Jsoup.connect(html).timeout(30 * 1000).get();
				System.out.println("Success to parse!");
				// System.out.println(doc);

				Elements records=doc.select("record");
				int Totaltds=records.size();
				if (Totaltds==0)
					return null;
				
			    
			    for(int i=0;i<Totaltds;i++) //某本书的管藏信息：从第0本书到第i本书
			    {
			  	 //Elements book=tds.get(i).select("div[class=bookmetaTitle]"); 
			  	 //span[class=bookmetaTitle]
			  	 
			  	 Map<String,Object> map = new HashMap<String,Object>();
			  	 
			  	   for(int j=0;j<3;j++)
			  	   {
			  		map.put("索书号",records.get(i).select("callno").text());
			  		map.put("所属图书馆",records.get(i).select("curlibName").text());
			  		map.put("在馆复本",records.get(i).select("copycount").text());
			  		
			  	   }
			  	
			  	list02.add(map);
			  	
				}
			} catch (IOException e) {
				// 解析失败！
				e.printStackTrace();
				System.out.println("Failed to Parse!");
			}
			return list02;
		}

	// 获得当前借阅的数据
		public static List<Map<String, Object>> getBorrowedBook() {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			Map<String, Object> bookMap;
			
			try {
				Document doc = Jsoup.connect("http://my1.hzlib.net/opac3/reader/space").userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.117 Safari/537.36")
				        .cookie("JSESSIONID", GlobleData2.cookies).get();
				System.out.println(doc);
				//String err = doc.select("strong[class=iconerr]").text().toString();
				//if (err.equals("您的该项记录为空！"))
					
				
				Elements trs = doc.select("table[class]").first().select("tr");
		        for(int i = 1;i<trs.size();i++){
		        	bookMap = new HashMap<String, Object>();
		            Elements tds = trs.get(i).select("td");
		            for(int j = 0;j<tds.size();j++){
		                String text = tds.get(j).text();
		                System.out.println(text);
		                switch (j) {
						case 0:
							bookMap.put("barcode", tds.get(j).text());
							break;
						case 1:
							bookMap.put("booktitle", tds.get(j).text());
							break;
						case 2:
							
							break;
						case 3:
							break;
						case 4:
							break;
						case 5:
							break;
						case 6:
							bookMap.put("borrowedDate", tds.get(j).text());
							break;
						case 7:
							bookMap.put("paybackDate", tds.get(j).text());
							break;
						default:
							break;
						}
		            }
		            list.add(bookMap);
		        }
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return list;
		}
	 
	 
	 
	
}
