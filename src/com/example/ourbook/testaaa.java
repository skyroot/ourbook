package com.example.ourbook;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.os.Bundle;

public class testaaa extends Activity  {
		 
	 protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			
			Map<String,Object> map = new HashMap<String,Object>();
			Document doc;
			try {

				// 通过Jsoup的connect（）方法，将html转化成Document
				
				doc = Jsoup.connect("http://my1.hzlib.net/opac3/book/2003483383?view=simple").timeout(30 * 1000).get();
				System.out.println("Success to parse!");
				// System.out.println(doc);

				Elements trs=doc.select("tr");
				int Totaltds=trs.size();
				 
			      for(int j=0;j<Totaltds;j++)
			  	   {
			  		map.put(trs.get(j).select("td[class=leftTD]").text(),trs.get(j).select("td[class=rightTD]").text());
			  		
			  	   }
			  	
			  		
			} catch (IOException e) {
				// 解析失败！
				e.printStackTrace();
				System.out.println("Failed to Parse!");
			}
			 Iterator<String> iterator=map.keySet().iterator();
		        while(iterator.hasNext())
		        {
		            Object o=iterator.next() ;
		            //map所键
		           String key=(String)o;
		           //map所值
		           String value=(String)map.get(key);
		           //输所需格式：key:=value
		           System.out.println( key+":"+"="+value);
		        }
		}
}