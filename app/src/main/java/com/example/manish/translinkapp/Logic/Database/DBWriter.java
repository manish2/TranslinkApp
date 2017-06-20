package com.example.manish.translinkapp.Logic.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by manis on 2017-06-17.
 */

public class DBWriter {
    private FeedReaderDbHelper _dbHelper;
    public DBWriter(Context caller) {
        _dbHelper = new FeedReaderDbHelper(caller);
    }
    public void writeDataToDB(String stopNumber) throws Exception {
        if(_dbHelper != null) {
            SQLiteDatabase db = _dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(FeedReaderContract.FeedEntry.COLUMN_STOP_NUMBER,stopNumber);
            // Insert the new row, returning the primary key value of the new row
            long newRowId = db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
        }
        else {
            throw new Exception("Could not open connection to database!");
        }
    }
}
