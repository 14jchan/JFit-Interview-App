package com.example.jfitsampleapplication.Depedencies;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/*
@author Jason Chan

DB Controller that is in charge of basic commands used by the activities. Holds the following capabilities:

*Initiates and sets up DB startup/update
*Stores/deletes/checks existance of a liked store in the DB

 */

public class DBController extends SQLiteOpenHelper {
    public DBController(Context context){
        super(context,"Userdata.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Liked_Stores (storeID varChar(35) PRIMARY KEY)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    private boolean resultToTF(long result){
        if(result == -1)
            return false;
        return true;
    }

    public boolean likeStore(String storeID){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("storeID", storeID);
        long result = sqLiteDatabase.insert("Liked_Stores", null, contentValues);
        return resultToTF(result);
    }

    public boolean dislikeStore(String storeID){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        long result = sqLiteDatabase.delete("Liked_Stores", "storeID=?", new String[]{storeID});
        return resultToTF(result);
    }

    public boolean getLike(String storeID){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from Liked_Stores where storeID=?", new String[]{storeID});
        if(cursor.getCount() > 0){
            return true;
        }
        return false;
    }
}
