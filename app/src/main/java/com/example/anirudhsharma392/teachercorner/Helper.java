package com.example.anirudhsharma392.teachercorner;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;

public class Helper extends SQLiteOpenHelper {


    public static final String Dname = "Database.db";
    public static final int Version = 10;
    public static final String TName = "TableName";
    public static final String Date = "Date";
    public static final String Time = "Time";


    SQLiteDatabase myDb;

    public Helper(Context context) {
        super(context, TName, null, 10);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String queryTable = "Create Table " + TName + "( " + Date + " TEXT, " + Time + " TEXT " + ")";
        sqLiteDatabase.execSQL(queryTable);

    }
    @Override public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public boolean insertData(String dt, String tm) {
        ContentValues content = new ContentValues();
        content.put(Date, dt);
        content.put(Time, tm);
        long result = myDb.insert(TName, null, content);
        if(result == -1)
            return false;
        else
            return true;
    }
    public Cursor getAllRecords()
    {
        return myDb.rawQuery("Select * From " + TName,null);

    }

    public void openDb() throws SQLException
    {
        myDb = this.getWritableDatabase();
    }
    public void closeDb()
    {
        if(myDb!=null && myDb.isOpen())
        {
            myDb.close();
        }
    }


}
