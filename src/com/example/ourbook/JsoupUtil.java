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

//JsoupUtil�������Ǹ��������url������jsoup���󣬻�ȡ������Ӧ���doc������jsoup�������������������������list

public class JsoupUtil {

	public static Integer sumNumber;
	public static String pageNumber;
	public static String preUrl;
	public static String nextUrl;
	public static int page = 1;// Ĭ�ϵ�һҳ
	public static String bookNumber;
	
	private String user; // ��½�û�
	private String passwd; // ��½����
	private String result;// ���ص�����
	private String cookie;
	private String location;
	private final static String LOGIN_URL = "http://my1.hzlib.net/opac3/reader/dologin";
	
	
	// ��¼
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
			System.out.println("��¼�ɹ���");
			System.out.println(response);
			 
			String sessionID = response.cookie("JSESSIONID");
			System.out.println(sessionID);
		    
			GlobleData2.cookies=sessionID;
		    return true;
		    
		}  else {
			System.out.println("��¼ʧ�ܣ������˺ź����룡");
			
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

			// ͨ��Jsoup��connect������������htmlת����Document
			doc = Jsoup.connect(html).timeout(30 * 1000).get();
			System.out.println("Success to parse!");
			// System.out.println(doc);

			// �жϡ�����û����������ֽ���ݲ���Ŀ��
			String err = doc.select("div[id=resultTile]").select("p").text().toString();
			if (err.equals("����������¼!"))
				return null;

			System.out.println("Test!");
			// ��ȡͼ�������Լ�ҳ������
			//sumNumber = getSumNumber(doc);
			//pageNumber = getPageNumber(doc);
			// ���ǰ��ҳ������
			
				getPreAndNextUrl(doc);

			Elements tds=doc.select("td[class=bookmetaTD]");
			int Totaltds=tds.size();
			
		    
		    for(int i=0;i<Totaltds;i++) //�ӵ�0���鵽��i����
		    {
		  	 //Elements book=tds.get(i).select("div[class=bookmetaTitle]"); 
		  	 //span[class=bookmetaTitle]
		    	
		  	 Map<String,Object> map = new HashMap<String,Object>();
		  	 
		  	   for(int j=0;j<4;j++)  //ÿ���������
		  	   {
		  		map.put("book_title",tds.get(i).select("span[class=bookmetaTitle]").text());
		  		map.put("book_author",tds.get(i).getElementsContainingOwnText("����:").select("a[href]").text());
		  		map.put("book_cbs",tds.get(i).getElementsContainingOwnText("������:").select("a[href]").text());
		  		map.put("book_number", tds.get(i).select("div[class=bookmeta]").attr("bookrecno"));
		  		
		  	   }
		  	
		  	list.add(map);
		  	
			}
		} catch (IOException e) {
			// ����ʧ�ܣ�
			e.printStackTrace();
			System.out.println("Failed to Parse!");
		}
		return list;
	}
	//���
	// ���ǰ��ҳ������
	public static void getPreAndNextUrl(Document doc) {
		// TODO Auto-generated method stub
		String MAIN_URL1="http://my1.hzlib.net";
		nextUrl=MAIN_URL1+doc.getElementsContainingOwnText("��һҳ").attr("href");
		preUrl=MAIN_URL1+doc.getElementsContainingOwnText("��һҳ").attr("href");
	}

	
	
 

	public static List<Map<String, Object>> searchBookDetail01(String html) {

		List<Map<String, Object>> list01 = new ArrayList<Map<String, Object>>();
		
		Document doc;
		try {

			// ͨ��Jsoup��connect������������htmlת����Document
			
			doc = Jsoup.connect(html).timeout(30 * 1000).get();
			System.out.println("Success to parse!");
			// System.out.println(doc);

			Elements trs=doc.select("tr");
			
			int Totaltds=trs.size();
			 
		     Map<String,Object> map = new HashMap<String,Object>();//ĳ����ļ����Ϣ
		  	 
		  	   for(int j=0;j<Totaltds;j++)
		  	   {
		  		map.put(trs.get(j).select("td[class=leftTD]").text(),trs.get(j).select("td[class=rightTD]").text());
		  		
		  	   }
		  	list01.add(map);
		  		
		} catch (IOException e) {
			// ����ʧ�ܣ�
			e.printStackTrace();
			System.out.println("Failed to Parse!");
		}
		return list01;
	}
	
	 public static List<Map<String, Object>> searchBookDetail02(String html) {

			List<Map<String, Object>> list02 = new ArrayList<Map<String, Object>>();
			
			Document doc;
			try {

				// ͨ��Jsoup��connect������������htmlת����Document
				
				doc = Jsoup.connect(html).timeout(30 * 1000).get();
				System.out.println("Success to parse!");
				// System.out.println(doc);

				Elements records=doc.select("record");
				int Totaltds=records.size();
				if (Totaltds==0)
					return null;
				
			    
			    for(int i=0;i<Totaltds;i++) //ĳ����Ĺܲ���Ϣ���ӵ�0���鵽��i����
			    {
			  	 //Elements book=tds.get(i).select("div[class=bookmetaTitle]"); 
			  	 //span[class=bookmetaTitle]
			  	 
			  	 Map<String,Object> map = new HashMap<String,Object>();
			  	 
			  	   for(int j=0;j<3;j++)
			  	   {
			  		map.put("�����",records.get(i).select("callno").text());
			  		map.put("����ͼ���",records.get(i).select("curlibName").text());
			  		map.put("�ڹݸ���",records.get(i).select("copycount").text());
			  		
			  	   }
			  	
			  	list02.add(map);
			  	
				}
			} catch (IOException e) {
				// ����ʧ�ܣ�
				e.printStackTrace();
				System.out.println("Failed to Parse!");
			}
			return list02;
		}

	// ��õ�ǰ���ĵ�����
		public static List<Map<String, Object>> getBorrowedBook() {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			Map<String, Object> bookMap;
			
			try {
				Document doc = Jsoup.connect("http://my1.hzlib.net/opac3/reader/space").userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.117 Safari/537.36")
				        .cookie("JSESSIONID", GlobleData2.cookies).get();
				System.out.println(doc);
				//String err = doc.select("strong[class=iconerr]").text().toString();
				//if (err.equals("���ĸ����¼Ϊ�գ�"))
					
				
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
