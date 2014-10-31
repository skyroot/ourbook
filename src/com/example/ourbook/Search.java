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
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
//import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
//import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
//******************************************************************************
//search.java的作用，输入来之intent方式传入的str2即查询的书名字符串，形成所需向服务器的查询请求URL，调用JsoupUtil进行请求和返还数据解析，得到

//list；
//然后定义listview和adaper，绑定数据源list，输出到listview相对应xml上；
//接收listview上的onItemClick（），获取到当前item对应的bookno，intent方式传递并启动searchBookDeatail.java
//**********************************************************************************

public class Search extends Activity  {
	private ListView listView;
	private MyAdapter adapter;

	private Button nextButton;
	private Button preButton;
	private Button button;
	private String html;
	private TextView sumNumber;
	private TextView pageNumber;
	private SQLiteDatabase db;
	private Cursor cursor;
	
	//List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
	 
	 protected void onCreate(Bundle savedInstanceState) 
	 
{
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			setTitle("图书查询结果");
			
			setContentView(R.layout.search);
			listView = (ListView)findViewById(R.id.search_section_list);
			
			
			
			db = (new DBOpenHelper(getApplicationContext())).getWritableDatabase();
				
String MAIN_URL="http://my1.hzlib.net/opac3/search";

String URL1 = "?searchWay=title&q=";
String URL2 = 

"&searchSource=reader&booktype=&scWay=dim&marcformat=&sortWay=score&sortOrder=desc&startPubdate=&endPubdate=&rows=10&hasholding=1";

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

 

 


          adapter = new MyAdapter(this);  
           new LoadBookInfo().execute(url);
 JsoupUtil.clearInfo();
  
Button nextButton=(Button)findViewById(R.id.next);
 Button preButton=(Button)findViewById(R.id.pre);

//button=(Button)findViewById(R.id.collect_btn);
 
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
		 
		Toast.makeText(getApplicationContext(), "感谢您的收藏",
				Toast.LENGTH_LONG).show();
	}
    }); 
  
  */
 }
	 
	//ViewHolder静态类
	    static class ViewHolder
	    {
	        public Button new_collect_btn;
	    	public TextView new_bookTitle;
	        public TextView new_bookAuthor;
	        public TextView new_bookCbs;
	    }
	    
	    public class MyAdapter extends BaseAdapter
	    {    
	        private LayoutInflater mInflater ;
	        private List<Map<String,Object>> list;
	        private MyAdapter(Context context)
	        {
	            //根据context上下文加载布局，这里的是Demo17Activity本身，即this
	            this.mInflater = LayoutInflater.from(context);
	        }

	        
	        public void setList(List<Map<String,Object>> list) {
	            //How many items are in the data set represented by this Adapter.
	            //在此适配器中所代表的数据集中的条目数
	        	//System.out.println(result);
	        	//System.out.println("hello getcount:   "+result.size());
	        	this.list=list;
	        	
	        	
	        }
	        
	        @Override
	        public int getCount() {
	            //How many items are in the data set represented by this Adapter.
	            //在此适配器中所代表的数据集中的条目数
	        	//System.out.println(result);
	        	//System.out.println("hello getcount:   "+result.size());
	        	return list.size();
	        	
	        }

	        @Override
	        public Object getItem(int position) {
	            // Get the data item associated with the specified position in the data set.
	            //获取数据集中与指定索引对应的数据项
	        	System.out.println("hello getitem");
	            return position;
	        }

	        @Override
	        public long getItemId(int position) {
	            //Get the row id associated with the specified position in the list.
	            //获取在列表中与指定索引对应的行id
	            return position;
	        }
	        
	        //Get a View that displays the data at the specified position in the data set.
	        //获取一个在数据集中指定索引的视图来显示数据
	        @Override
	        public View getView(final int position, View convertView, ViewGroup parent) {
	            
	        	System.out.println("hello getview");
	        	ViewHolder holder = null;
	            //如果缓存convertView为空，则需要创建View
	            if(convertView == null)
	            {
	                holder = new ViewHolder();
	                //根据自定义的Item布局加载布局
	                convertView = mInflater.inflate(R.layout.book_list, null);
	               holder.new_collect_btn = (Button)convertView.findViewById(R.id.collect_btn);
	                holder.new_bookTitle = (TextView)convertView.findViewById(R.id.bookTitle);
	                holder.new_bookAuthor = (TextView)convertView.findViewById(R.id.bookAuthor);
	                holder.new_bookCbs = (TextView)convertView.findViewById(R.id.bookCbs);
	                //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
	                convertView.setTag(holder);
	            }else
	            {
	                holder = (ViewHolder)convertView.getTag();
	            }
	           holder.new_collect_btn.setBackgroundResource(R.drawable.icon_favourite_normal);
	            holder.new_bookTitle.setText((String)list.get(position).get("book_title"));
	            holder.new_bookAuthor.setText((String)list.get(position).get("book_author"));
	            holder.new_bookCbs.setText((String)list.get(position).get("book_cbs"));
	            

		       holder.new_collect_btn.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
							/*SharedPreferences sp=getSharedPreferences("preference",Context.MODE_PRIVATE);
							Editor editor=sp.edit();
							editor.putString("book_title", (String)list.get(position).get("book_title"));
							editor.putString("book_author", (String)list.get(position).get("book_author"));
							editor.putString("book_cbs", (String)list.get(position).get("book_cbs"));
							
							if(editor.commit())*/
						 String booktitle = (String)list.get(position).get("book_title");
					     String bookauthor = (String)list.get(position).get("book_author");
					     String bookcbs = (String)list.get(position).get("book_cbs");
					     
					     System.out.println((String)list.get(position).get("book_title"));
					     System.out.println("bookauthor");  
					     System.out.println("bookcbs");
					     
						db.execSQL("insert into mybook values(null,?,?,?)", new String[]{booktitle,bookauthor,bookcbs});
						
						Toast.makeText(getApplicationContext(), "感谢您的收藏",
								Toast.LENGTH_LONG).show();
					}
				});
	            return convertView;
	        }
	        
	    }
	 
	 
	 
	 
	 
	 
	 class LoadBookInfo extends	AsyncTask<String, ListView, List<Map<String, Object>>> 
{
		 private List<Map<String, Object>> list;
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
	list= JsoupUtil.searchBook(params[0]);
	return list;
     }

     @Override
     protected void onPostExecute(final List<Map<String, Object>> result) 
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
		
		
		/*SimpleAdapter adapter = new SimpleAdapter(
				getApplicationContext(), result, R.layout.book_list,
				new String[] { "book_title", "book_author", "book_cbs"}, new int[] { R.id.bookTitle,
					R.id.bookAuthor, R.id.bookCbs });
		*/
		   
		 
		 System.out.println(result);
		 
		 
		   System.out.println(result.size());
		   
		   adapter.setList(result);
		listView.setAdapter(adapter);
				
		listView.setOnItemClickListener(new OnItemClickListener()
			{  
			  
			    
			   @Override  
			   public void onItemClick(AdapterView<?> parent, View view,  
			     int position, long id) {  
			  //  ListView listView = (ListView)parent;  
			    @SuppressWarnings("unchecked")
				//HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(position);  
			    
                             String book_number = (String)result.get(position).get("book_number");  
			    
                            System.out.println(book_number);
			    Intent mainIntent1 = new Intent();
				mainIntent1.putExtra("book_number", book_number);  
				mainIntent1.setClass(Search.this,SearchBookDetail.class);
				startActivity(mainIntent1);
			    
			    }  
			 });  
		super.onPostExecute(result);
	         }
         }

     
     	
}//onCreate()结束
	 protected void onDestroy(){
  		super.onDestroy();
  		db.close();
  	}
}//Search.class 结束


