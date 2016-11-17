package com.example.administrator.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * 继承SQLiteOpenHelper类 在构造方法中传入Context,数据库名称，游标工厂(一般传入null默认的游标工厂)，数据库版本号(必须>=1)
 * 数据库默认存储在/data/data/应用程序包名/databases
 *
 */
public class MySQLiteOpenHelper extends SQLiteOpenHelper {

	/**
	 * 构造方法
	 * @param context
	 */
	public MySQLiteOpenHelper(Context context) {
		super(context, "mytea.db", null, 1);
	}

	/**
	 * 数据库第一次被创建的时候回调的方法
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table mySqatle(_id integer primary key autoincrement,title varcher," +
				"web_thumb varcher,create_time varcher,source varcher,item_id varcher)");
	}

	/**
	 * 数据库版本更新时回调的方法
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
	

}
