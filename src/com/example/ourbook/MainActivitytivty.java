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
		setTitle("����ͼ��ݲ�ѯϵͳ");
	
		searchText = (EditText) findViewById(R.id.searchText);
		searchButton = (Button) findViewById(R.id.searchButton);
		
		searchButton.setOnClickListener(new OnClickListener() {
		     public void onClick(View v) {
			// TODO Auto-generated method stub
			
				// �ַ����룬����������ַ�
			//��ȡ�����ѯ�������ַ���
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
	  });//searchButton.setOnClickListener����
	
	
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
	  });//searchButton.setOnClickListener����
		
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

				// ͨ��Jsoup��connect������������htmlת����Document
				doc = Jsoup.connect(html).timeout(30 * 1000).get();
				System.out.println("Success to parse!");
				// System.out.println(doc);

				// �жϡ�����û����������ֽ���ݲ���Ŀ��
				String err = doc.select("h3").text().toString();
				if (err.equals("����û����������ֽ���ݲ���Ŀ"))
					return null;


				Elements books = doc.select("tr[bgcolor=#FFFFFF]");
				Iterator<Element> book = books.iterator();
				while (book.hasNext()) {
					Element em = book.next();
					System.out.println(em.text());
					// �����bookMapÿ�ζ�Ҫʵ����һ�������򽫻�������е����ݶ������һ��������
					bookMap = new HashMap<String, Object>();
					// ���������֤����Element(s)��text���������������ԭ��html�ı�ǩ������toString�ķ���������ǩ
					// ��html���������õ���ǩ������������
					// ����ͼ�鲿������
					Elements bookInfo = em.select("td");
					int totalTds = bookInfo.size();
					for (int j = 0; j < totalTds; j++) {
						switch (j) {
						case 0:// ͼ�����
							bookMap.put("bookNo", bookInfo.get(j).html().toString());
							break;
						case 1:// ���������
							bookMap.put("bookTitle", bookInfo.get(j).text());
							// bookMap.put("bookDetail", bookInfo.get(j).select("a")
							// .attr("href").toString());
							break;
						case 2:// ����
							bookMap.put("bookAuthor", bookInfo.get(j).text());
							break;
						case 3:// ������
							bookMap.put("bookPublisher", bookInfo.get(j).text());
							break;
						case 4:// �����
							bookMap.put("bookCallno", bookInfo.get(j).text());
							break;
						case 5:// ��������
							bookMap.put("bookType", bookInfo.get(j).text());
							break;
						default:
							break;
						}
					}
					list.add(bookMap);
				}
			} catch (IOException e) {
				// ����ʧ�ܣ�
				e.printStackTrace();
				System.out.println("Failed to Parse!");
			}
			return list;
		}
	
	*/
	
}
}