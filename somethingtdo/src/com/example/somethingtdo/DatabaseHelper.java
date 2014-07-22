package com.example.somethingtdo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {
	
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "SomethingToDo.db";
	private static final String TABLE_NAME = "Accounts";
	private static final String TABLE_NAME_2 = "UserDet";
	
	private Context context;
	private SQLiteDatabase db;
	private SQLiteStatement insertStmt;
	private SQLiteStatement insertStmt2;
	private static final String INSERT = "insert into " + TABLE_NAME + "(name, password) values (?, ?)" ;
	private static final String INSERT2 = "insert into " + TABLE_NAME_2 + "(name, date, location, interests) values (?, ?, ?, ?)";



public DatabaseHelper(Context context) {
    this.context = context;
    SomethingToDoOpenHelper openHelper = new SomethingToDoOpenHelper(this.context);
    this.db = openHelper.getWritableDatabase();
    this.insertStmt = this.db.compileStatement(INSERT);
    this.insertStmt2 = this.db.compileStatement(INSERT2);
    
 }
public long insert(String name, String password) {
    this.insertStmt.bindString(1, name);
    this.insertStmt.bindString(2, password);
    return this.insertStmt.executeInsert();
 }

public long insert(String name, String date, String location, String interests) {
    this.insertStmt2.bindString(1, name);
    this.insertStmt2.bindString(2, date);
    this.insertStmt2.bindString(3, location);
    this.insertStmt2.bindString(4, interests);
    
    return this.insertStmt2.executeInsert();
 }

 public void deleteAll() {

    this.db.delete(TABLE_NAME, null, null);
    this.db.delete(TABLE_NAME_2, null, null);
 }
 
 public List<String> selectAll(String username, String password) {
     List<String> list = new ArrayList<String>();
     Cursor cursor = this.db.query(TABLE_NAME, new String[] { "name", "password" }, "name = '"+ username +"' AND password= '"+ password+"'", null, null, null, "name desc");
     if (cursor.moveToFirst()) {
       do {
       	 list.add(cursor.getString(0));
       	 list.add(cursor.getString(1));
        } while (cursor.moveToNext()); 
     }
     if (cursor != null && !cursor.isClosed()) {
        cursor.close();
     }
     return list;
  }
 
 public List<String> searchAndGet(String username) {
     List<String> list = new ArrayList<String>();
     Cursor cursor = this.db.query(TABLE_NAME_2, new String[] { "name", "date", "location", "interests" }, "name = '"+ username +"'", null, null, null, "name desc");
     if (cursor.moveToFirst()) {
       do {
       	 list.add(cursor.getString(0));
       	 list.add(cursor.getString(1));
       	 list.add(cursor.getString(2));
      	 list.add(cursor.getString(3));
        } while (cursor.moveToNext()); 
     }
     if (cursor != null && !cursor.isClosed()) {
        cursor.close();
     }
     return list;
  }
 
 public void updateInterests (String username, String interestList){
	 
	 ContentValues contentValues= new ContentValues();
	 contentValues.put("interests", interestList);
	 
	 this.db.update(TABLE_NAME_2, contentValues, "name = '"+ username+"'", null);
	 
 }
 
public void updateDate (String username, String setDate){
	 
	 ContentValues contentValues= new ContentValues();
	 contentValues.put("date", setDate);
	 
	 this.db.update(TABLE_NAME_2, contentValues, "name = '"+ username+"'", null);
	 
 }

public void updateLocation (String username, String setLocation){
	 
	 ContentValues contentValues= new ContentValues();
	 contentValues.put("location", setLocation);
	 
	 this.db.update(TABLE_NAME_2, contentValues, "name = '"+ username+"'", null);
	 
}

 private static class SomethingToDoOpenHelper extends SQLiteOpenHelper {
	   SomethingToDoOpenHelper(Context context) {
  	  super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       db.execSQL("CREATE TABLE " + TABLE_NAME + "(id INTEGER PRIMARY KEY, name TEXT, password TEXT)");
       db.execSQL("CREATE TABLE " + TABLE_NAME_2 + "(id INTEGER PRIMARY KEY, name TEXT, date TEXT, location TEXT, interests TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

       Log.w("Example", "Upgrading database; this will drop and recreate the tables.");
       db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
       db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_2);
       onCreate(db);
    }
 }
}