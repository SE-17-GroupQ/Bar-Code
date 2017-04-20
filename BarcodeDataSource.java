package com.se17.attendancesystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pooja on 4/4/2017.
 */

public class BarcodeDataSource {
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_BARCODE_STRING };

    public BarcodeDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String data) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_BARCODE_STRING, data);
        database.insert(MySQLiteHelper.TABLE_COMMENTS, null, values);
    }

    public List<BarcodeData> getAll() {
        List<BarcodeData> data = new ArrayList<BarcodeData>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENTS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            BarcodeData comment = cursorToComment(cursor);
            data.add(comment);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return data;
    }


    public String getBarcodeString() {
        String barcode = "";
        Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENTS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            BarcodeData data = cursorToComment(cursor);
            barcode = data.getBarcodeString();
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return barcode;
    }

    private BarcodeData cursorToComment(Cursor cursor) {
        BarcodeData data = new BarcodeData();
        data.setBarcodeString(cursor.getString(0));
        return data;
    }
}