package com.example.ourbook;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {   
	
	public static final String DATABASE_NAME="book.db";
	
    public DBOpenHelper(Context context) {   
        super(context, DATABASE_NAME, null, 1);   
    }   
   
    //数据库第一次创建时候调用，   
    public void onCreate(SQLiteDatabase db) {   
        db.execSQL("create table mybook(_id integer primary key autoincrement, booktitle varchar(20), bookauthor varchar(20),bookcbs varchar(20))");   
    }   
   
    //数据库文件版本号发生变化时调用   
    
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}  
    
}