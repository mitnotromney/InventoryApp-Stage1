package com.example.mit.bookstore.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BooksDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = BooksDbHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "shelter.db";

    private static final int DATABASE_VERSION = 1;

    public BooksDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_BOOKS_TABLE =  "CREATE TABLE " + BooksContract.BookEntry.TABLE_NAME + " ("
                + BooksContract.BookEntry.BOOK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + BooksContract.BookEntry.COLUMN_BOOK_NAME + " TEXT NOT NULL,"
                + BooksContract.BookEntry.COLUMN_PRICE + " TEXT NOT NULL,"
                + BooksContract.BookEntry.COLUMN_QUANTITY + " INTEGER NOT NULL,"
                + BooksContract.BookEntry.COLUMN_SUPPLIER_NAME + " TEXT NOT NULL,"
                + BooksContract.BookEntry.COLUMN_SUPPLIER_NUMBER + " TEXT NOT NULL);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_BOOKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
