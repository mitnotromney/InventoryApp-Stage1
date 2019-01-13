package com.example.mit.bookstore;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.mit.bookstore.Data.BooksContract;
import com.example.mit.bookstore.Data.BooksDbHelper;

public class MainActivity extends AppCompatActivity {

    private BooksDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });
        mDbHelper = new BooksDbHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    private void displayDatabaseInfo() {

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                BooksContract.BookEntry._ID,
                BooksContract.BookEntry.COLUMN_BOOK_NAME,
                BooksContract.BookEntry.COLUMN_PRICE,
                BooksContract.BookEntry.COLUMN_QUANTITY,
                BooksContract.BookEntry.COLUMN_SUPPLIER_NAME,
                BooksContract.BookEntry.COLUMN_SUPPLIER_NUMBER};

        // Perform a query on the pets table
        Cursor cursor = db.query(
                BooksContract.BookEntry.TABLE_NAME,   // The table to query
                projection,            // The columns to return
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // The sort order

        TextView displayView = (TextView) findViewById(R.id.text_view_pet);

        try {
            displayView.setText("The books table contains " + cursor.getCount() + " books.\n\n");
            displayView.append(BooksContract.BookEntry._ID + " - " +
                    BooksContract.BookEntry.COLUMN_BOOK_NAME + " - " +
                    BooksContract.BookEntry.COLUMN_PRICE + " - " +
                    BooksContract.BookEntry.COLUMN_QUANTITY + " - " +
                    BooksContract.BookEntry.COLUMN_SUPPLIER_NAME + " - " +
                    BooksContract.BookEntry.COLUMN_SUPPLIER_NUMBER + "\n");

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(BooksContract.BookEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(BooksContract.BookEntry.COLUMN_BOOK_NAME);
            int priceColumnIndex = cursor.getColumnIndex(BooksContract.BookEntry.COLUMN_PRICE);
            int quantityColumnIndex = cursor.getColumnIndex(BooksContract.BookEntry.COLUMN_QUANTITY);
            int supNameColumnIndex = cursor.getColumnIndex(BooksContract.BookEntry.COLUMN_SUPPLIER_NAME);
            int supNumberColumnIndex = cursor.getColumnIndex(BooksContract.BookEntry.COLUMN_SUPPLIER_NUMBER);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentPrice = cursor.getString(priceColumnIndex);
                int currentQuantity = cursor.getInt(quantityColumnIndex);
                String currentSupName = cursor.getString(supNameColumnIndex);
                String currentSupNumber = cursor.getString(supNumberColumnIndex);
                displayView.append(("\n" + currentID + " - " +
                        currentName + " - " +
                        currentPrice + " - " +
                        currentQuantity + " - " +
                        currentSupName + " - " +
                        currentSupNumber));
            }
        } finally {
            cursor.close();
        }
    }

    private void insertBook() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(BooksContract.BookEntry.COLUMN_BOOK_NAME, BooksContract.BookEntry.COLUMN_BOOK_NAME);
        values.put(BooksContract.BookEntry.COLUMN_PRICE, BooksContract.BookEntry.COLUMN_PRICE);
        values.put(BooksContract.BookEntry.COLUMN_QUANTITY, BooksContract.BookEntry.QUANTITY_ONE);
        values.put(BooksContract.BookEntry.COLUMN_SUPPLIER_NAME, BooksContract.BookEntry.COLUMN_SUPPLIER_NAME);
        values.put(BooksContract.BookEntry.COLUMN_SUPPLIER_NUMBER, BooksContract.BookEntry.COLUMN_SUPPLIER_NUMBER);

        long newRowId = db.insert(BooksContract.BookEntry.TABLE_NAME, null, values);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.settings_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_insert_dummy_data:
                insertBook();
                displayDatabaseInfo();
                return true;
            case R.id.action_delete_all_entries:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
