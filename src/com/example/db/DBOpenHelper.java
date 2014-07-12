package com.example.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBOpenHelper extends SQLiteOpenHelper {

	private static final String NAME="note.db";

	private static final int VERSION=1;

	public DBOpenHelper(Context context) {
		super(context, NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	
		//日程记事表
		db.execSQL("CREATE TABLE note(_id integer primary key autoincrement,date varchar(10),title varchar(10),content varchar(10))");
				
	}
	

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		  
		db.execSQL("DROP TABLE IF EXISTS note");
		onCreate(db);
	}

}
