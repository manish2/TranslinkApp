package com.example.manish.translinkapp.Logic.Database;

import android.provider.BaseColumns;

/**
 * Created by manis on 2017-06-17.
 */

public final class FeedReaderContract {
    private FeedReaderContract() {}

    /* Inner class that defines the table contents */
    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "Stops";
        public static final String COLUMN_STOP_NUMBER = "StopNumber";
    }
}
