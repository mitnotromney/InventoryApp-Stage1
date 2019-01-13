package com.example.mit.bookstore;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mit.bookstore.Data.BooksContract;
import com.example.mit.bookstore.Data.BooksDbHelper;

public class EditorActivity extends AppCompatActivity {

    private EditText mBookNameText;
    private EditText mPriceText;
    private Spinner mQuantitySpinner;
    private EditText mSupplierNameText;
    private EditText mSupplierNumberText;

    private int mQuantity = BooksContract.BookEntry.QUANTITY_ONE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        mBookNameText = (EditText) findViewById(R.id.edit_book_name);
        mPriceText = (EditText) findViewById(R.id.edit_book_price);
        mQuantitySpinner = (Spinner) findViewById(R.id.spinner_gender);
        mSupplierNameText = (EditText) findViewById(R.id.edit_supplier_name);
        mSupplierNumberText = (EditText) findViewById(R.id.edit_supplier_number);

        setupSpinner();
    }

    private void setupSpinner() {
        ArrayAdapter quantitySpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_quantity_options, android.R.layout.simple_spinner_item);

        quantitySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        mQuantitySpinner.setAdapter(quantitySpinnerAdapter);

        mQuantitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.quantity_one))) {
                        mQuantity = BooksContract.BookEntry.QUANTITY_ONE;
                    } else if (selection.equals(getString(R.string.quantity_two))) {
                        mQuantity = BooksContract.BookEntry.QUANTITY_TWO;
                    } else {
                        mQuantity = BooksContract.BookEntry.QUANTITY_THREE;
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mQuantity =  BooksContract.BookEntry.QUANTITY_ONE;
            }
        });
    }

    private void insertBook() {
        String nameString = mBookNameText.getText().toString().trim();
        String priceString = mPriceText.getText().toString().trim();
        String supNameString = mSupplierNameText.getText().toString().trim();
        String supNumberString = mSupplierNumberText.getText().toString().trim();

        BooksDbHelper mDbHelper = new BooksDbHelper(this);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(BooksContract.BookEntry.COLUMN_BOOK_NAME, nameString);
        values.put(BooksContract.BookEntry.COLUMN_PRICE, priceString);
        values.put(BooksContract.BookEntry.COLUMN_QUANTITY, mQuantity);
        values.put(BooksContract.BookEntry.COLUMN_SUPPLIER_NAME, supNameString);
        values.put(BooksContract.BookEntry.COLUMN_SUPPLIER_NUMBER, supNumberString);

        long newRowId = db.insert(BooksContract.BookEntry.TABLE_NAME, null, values);

        if (newRowId == -1) {
            Toast.makeText(this, "Error with saving book", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Book saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                insertBook();
                finish();
                return true;
            case R.id.action_delete:
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
