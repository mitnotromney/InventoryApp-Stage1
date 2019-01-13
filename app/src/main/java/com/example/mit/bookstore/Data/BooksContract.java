package com.example.mit.bookstore.Data;

import android.provider.BaseColumns;

public class BooksContract {

    public static abstract class BookEntry implements BaseColumns {

        public static final String TABLE_NAME = "books";
        public static final String BOOK_ID = "_id";
        public static final String COLUMN_BOOK_NAME = "name";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_SUPPLIER_NAME = "supName";
        public static final String COLUMN_SUPPLIER_NUMBER = "supNumber";

        public static final int QUANTITY_ONE = 1;
        public static final int QUANTITY_TWO = 2;
        public static final int QUANTITY_THREE = 3;

    }
}
