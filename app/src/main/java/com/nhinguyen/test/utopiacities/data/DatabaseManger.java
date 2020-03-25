package com.nhinguyen.test.utopiacities.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nhinguyen.test.utopiacities.model.City;

import java.util.ArrayList;

/**
 * Created by NhiNguyen on 25/03/2020.
 */
public class DatabaseManger {
    DataBase helper;
    SQLiteDatabase db;
    public DatabaseManger(Context context) {
        helper = new DataBase(context);
        db = helper.getWritableDatabase();

    }

    public ArrayList<City> getMonHoc() {
        ArrayList<City> list = new ArrayList<>();
        String queryAllchar = "SELECT * FROM cities";
        Cursor cs = db.rawQuery(queryAllchar, null);
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
}
