package com.example.libmansys;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Student.db";
    public static final String TABLE_NAME = "books_table";
    public static final String STUDENTS_TABLE = "students_table";

    //Fields of Student registration
    public static final String REG = "REG";
    public static final String UN = "USERNAME";
    public static final String DEPT= "DEPARTMENT";
    public static final String PWD = "PASSWORD";


    //Fields of request sent by student
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "DEPARTMENT";
    public static final String COL_4 = "BOOK_NAME";
    public static final String COL_5 = "REGN";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,DEPARTMENT TEXT,BOOK_NAME TEXT,REGN TEXT)");
        db.execSQL("create table " + STUDENTS_TABLE +" (REG TEXT UNIQUE,USERNAME TEXT UNIQUE,DEPARTMENT TEXT,PASSWORD TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+STUDENTS_TABLE);
        onCreate(db);
    }

    public boolean insertData(String name,String department,String bookName,String regn) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,department);
        contentValues.put(COL_4,bookName);
        contentValues.put(COL_5,regn);
        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean insertStudent(String regNumber,String userName,String department,String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(REG,regNumber);
        contentValues.put(UN,userName);
        contentValues.put(DEPT,department);
        contentValues.put(PWD,password);

        long result = db.insert(STUDENTS_TABLE,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

    public boolean updateData(String id,String name,String surname,String marks) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,surname);
        contentValues.put(COL_4,marks);
        db.update(TABLE_NAME, contentValues, "ID = ?",new String[] { id });
        return true;
    }

    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?",new String[] {id});
    }


    public Cursor getStudent(String userName,String password)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+STUDENTS_TABLE+" where USERNAME =? AND PASSWORD=?",new String[]{userName,password});
        return res;
    }

    public Cursor isUserPresent(String reg,String username)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+STUDENTS_TABLE+" where USERNAME =? or REG=?",new String[]{username,reg});
        return res;
    }

}