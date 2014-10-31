package com.example.ourbook;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {   
	
	public static final String DATABASE_NAME="book.db";
	
    public DBOpenHelper(Context context) {   
        super(context, DATABASE_NAME, null, 1);   
    }   
   
    //���ݿ��һ�δ���ʱ����ã�   
    public void onCreate(SQLiteDatabase db) {   
        db.execSQL("create table mybook(_id integer primary key autoincrement, booktitle varchar(20), bookauthor varchar(20),bookcbs varchar(20))");   
    }   
   
    //���ݿ��ļ��汾�ŷ����仯ʱ����   
    
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}  
    
}