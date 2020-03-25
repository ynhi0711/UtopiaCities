package com.nhinguyen.test.utopiacities.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.nhinguyen.test.utopiacities.model.City;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Nhi on 3/19/2018.
 */

public class DataBase extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "utopia_cities.sqlite";
    Context context;
    String path = "";
    SQLiteDatabase db;

    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
        path = context.getFilesDir().getParent() + "/databases/" + DATABASE_NAME;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (newVersion != oldVersion) {
            createDatabase();
        }
    }

    public SQLiteDatabase openDatabase() {
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
        return db;
    }

    public void coppyDatabase() {
        try {
            InputStream is = context.getAssets().open("utopia_cities.sqlite");
            OutputStream os = new FileOutputStream(path);
            byte[] buffer = new byte[1024];
            int lenght = 0;
            while ((lenght = is.read(buffer)) > 0) {
                os.write(buffer, 0, lenght);
            }
            os.flush();
            os.close();
            is.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("Log", "COPPY DATABASE");
    }

    public boolean checkDatabase() {
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (checkDB != null) {
            checkDB.close();
        }

        return checkDB != null ? true : false;
    }

    public void createDatabase() {
        boolean kt = checkDatabase();
        if (kt) {
            Log.d("LOG ", "connect database");
        } else {
            Log.d("LOG ", "can not connect database");
            this.getWritableDatabase();
            coppyDatabase();
        }

    }

    //Đây là 1 ví dụ hàm Querry tất cả Môn học từ Table TB_MONHOC trong database
    public ArrayList<City> getMonHoc() {
        ArrayList<City> list = new ArrayList<>();
        String queryAllchar = "SELECT * FROM cities";
        Cursor cs =getWritableDatabase().rawQuery(queryAllchar, null);
        try {
            cs.moveToFirst();
            while (cs.isAfterLast() == false) {
                list.add(new City());
                cs.moveToNext();
            }
        } finally {
            if (cs != null) {
                cs.close();
            }
        }
        return list;
    }

//    public SQLiteDatabase opendatabase() {
//        try {
//            String mypath = DB_PATH + DB_NAME;
//            database = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READWRITE);
//        } catch (SQLiteException sle) {
//            System.out.println("Exception while opning database : " + sle);
//        }
//        return database;
//    }
}
