package com.example.ourbook;

import com.example.ourbook.Search.MyAdapter;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
/*  
 Collect.java��Ҫʹ��������ҵ��ղذ�ť�󣬴򿪵��ղ�չʾҳ������ǰ�����sqlite mybook�ж������ݣ�չʾ��listview��
 */
public class Collect extends Activity {
	private ListView listView;
	private MyAdapter adapter;

	private SQLiteDatabase db;
	private Cursor cursor;
	protected void onCreate(Bundle savedInstanceState) 
	 
	{
		
		super.onCreate(savedInstanceState);
		setTitle("�ҵ�ͼ���ղ�");
		
		setContentView(R.layout.collect);
		listView = (ListView)findViewById(R.id.collect_section_list);
		
		
		
		db = (new DBOpenHelper(getApplicationContext())).getWritableDatabase();
		
		Cursor cursor=db.rawQuery("select * from mybook ORDER BY _id",null);
		   if(cursor != null){
		        
		          startManagingCursor(cursor);

		       }  
		
SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.collect_list, cursor,new String[]{"booktitle", "bookauthor", "bookcbs"}, new int[]{R.id.bookTitle,R.id.bookAuthor, R.id.bookCbs });
				
		listView.setAdapter(adapter);
		stopManagingCursor(cursor);
	}
	
	
	
	
	 protected void onDestroy(){
	  		super.onDestroy();
	  		db.close();
	  	}
	
	
	
	
}