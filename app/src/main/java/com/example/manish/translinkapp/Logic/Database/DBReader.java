package com.example.manish.translinkapp.Logic.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by manis on 2017-06-19.
 */

public class DBReader {
    private FeedReaderDbHelper _dbHelper;
    public DBReader(Context caller) {
        _dbHelper = new FeedReaderDbHelper(caller);
    }
    public List<String> readDataFromDB() throws Exception {
        ArrayList<String> routeNo = new ArrayList<>();
        String query = "SELECT * FROM " + FeedReaderContract.FeedEntry.TABLE_NAME + " ORDER BY " + FeedReaderContract.FeedEntry._ID + " desc";
        if(_dbHelper != null) {
            SQLiteDatabase db = _dbHelper.getReadableDatabase();
            Cursor c =  db.rawQuery(query,null);
            while(c.moveToNext()) {
                routeNo.add(c.getString(1));
            }
            return routeNo;
        }
        else
        {
            throw new Exception("Could not open connection to database!");
        }
    }
}
