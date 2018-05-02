package com.example.komal.todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME="DBNAME";
    private  static  final int DB_VERSION=1;
    public static final String DB_TABLE="Task";
    public static final String DB_COLUMN="TaskName";



    public DbHelper(Context context) {

        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

     String TABLE= "CREATE TABLE "+DB_TABLE+"("+" ID INTEGER PRIMARY KEY AUTOINCREMENT, "+DB_COLUMN+" TEXT NOT NULL);";
        sqLiteDatabase.execSQL(TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
      String query=String.format("DELETE TABLE IF EXISTS %s",DB_TABLE);
      sqLiteDatabase.execSQL(query);
      onCreate(sqLiteDatabase);
    }


    public void insertNewTask(String task){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(DB_COLUMN,task);

        db.insertWithOnConflict(DB_TABLE,null,values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public void deleteTask(String task){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(DB_TABLE,DB_COLUMN+" =  ?",new String[] {task});
        db.close();
    }

    public ArrayList<String> getTaskList(){
        ArrayList<String> taskList=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query(DB_TABLE,new String[]{DB_COLUMN},null,null,null,null,null);
        while(cursor.moveToNext()){
            int index=cursor.getColumnIndex(DB_COLUMN);
            taskList.add(cursor.getString(index));

        }
        cursor.close();
        db.close();
        return taskList;
    }
}
