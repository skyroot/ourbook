package com.example.ourbook;



import java.io.UnsupportedEncodingException;

import com.example.ourbook.GlobleData2;
import com.example.ourbook.BorrowedActivity;
import com.example.ourbook.LoginActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivitytivty extends Activity {
	public EditText searchText;
	public Button searchButton;
	public Button mycollectButton;
	public Button myBorrowButton;
	public final String MAIN_URL="http://my1.hzlib.net/opac3/search";
	public final String URL1 = "?searchWay=title&q=";
	public final String URL2 = "&searchSource=reader&booktype=&scWay=dim&marcformat=&sortWay=score&sortOrder=desc&startPubdate=&endPubdate=&rows=10&hasholding=1";
	private Intent intent = new Intent();
	public static Boolean flag = false;
	
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setTitle("杭州图书馆查询系统");
	
		searchText = (EditText) findViewById(R.id.searchText);
		searchButton = (Button) findViewById(R.id.searchButton);
		
		searchButton.setOnClickListener(new OnClickListener() {
		     public void onClick(View v) {
			// TODO Auto-generated method stub
			
				// 字符编码，尤其对中文字符
			//获取输入查询的书名字符串
			 String str2=null;
			 str2 = searchText.getText().toString().trim();
				//try {
					
				//} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
				//	e1.printStackTrace();
				//}
				System.out.println(str2);	
				
				intent.putExtra("str2", str2);  
				intent.setClass(MainActivitytivty.this,Search.class);
				startActivity(intent);
		     }
	  });//searchButton.setOnClickListener结束
	
	
		myBorrowButton = (Button) findViewById(R.id.myBorrowButton);
		
		myBorrowButton.setOnClickListener(new OnClickListener() {
		     public void onClick(View v) {
			
			 	if (GlobleData2.flag) {
					intent.setClass(getApplicationContext(),
							BorrowedActivity.class);

				} else {
					intent.setClass(getApplicationContext(),
							LoginActivity.class);
					intent.putExtra("info", "borrowed");
				}
				startActivity(intent);
				
				
		}
	  });//searchButton.setOnClickListener结束
		
		mycollectButton = (Button) findViewById(R.id.mycollectButton);
		
		mycollectButton.setOnClickListener(new OnClickListener() {
		     public void onClick(View v) {
				intent.setClass(MainActivitytivty.this,Collect.class);
				startActivity(intent);
						
		}
	  });
	
		/*public static List<Map<String, Object>> searchBook(String html) {

			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			Map<String, Object> bookMap;

			Document doc;
			try {

				// 通过Jsoup的connect（）方法，将html转化成Document
				doc = Jsoup.connect(html).timeout(30 * 1000).get();
				System.out.println("Success to parse!");
				// System.out.println(doc);

				// 判断“本馆没有您检索的纸本馆藏书目”
				String err = doc.select("h3").text().toString();
				if (err.equals("本馆没有您检索的纸本馆藏书目"))
					return null;


				Elements books = doc.select("tr[bgcolor=#FFFFFF]");
				Iterator<Element> book = books.iterator();
				while (book.hasNext()) {
					Element em = book.next();
					System.out.println(em.text());
					// 这里的bookMap每次都要实例化一个，否则将会出现所有的内容都是最后一条的内容
					bookMap = new HashMap<String, Object>();
					// 经过多次验证，用Element(s)的text（）方法输出不带原来html的标签，而用toString的方法则会带标签
					// 用html（）方法得到标签括起来的内容
					// 解析图书部分内容
					Elements bookInfo = em.select("td");
					int totalTds = bookInfo.size();
					for (int j = 0; j < totalTds; j++) {
						switch (j) {
						case 0:// 图书序号
							bookMap.put("bookNo", bookInfo.get(j).html().toString());
							break;
						case 1:// 标题和链接
							bookMap.put("bookTitle", bookInfo.get(j).text());
							// bookMap.put("bookDetail", bookInfo.get(j).select("a")
							// .attr("href").toString());
							break;
						case 2:// 作者
							bookMap.put("bookAuthor", bookInfo.get(j).text());
							break;
						case 3:// 出版社
							bookMap.put("bookPublisher", bookInfo.get(j).text());
							break;
						case 4:// 索书号
							bookMap.put("bookCallno", bookInfo.get(j).text());
							break;
						case 5:// 文献类型
							bookMap.put("bookType", bookInfo.get(j).text());
							break;
						default:
							break;
						}
					}
					list.add(bookMap);
				}
			} catch (IOException e) {
				// 解析失败！
				e.printStackTrace();
				System.out.println("Failed to Parse!");
			}
			return list;
		}
	
	*/
	
}
}