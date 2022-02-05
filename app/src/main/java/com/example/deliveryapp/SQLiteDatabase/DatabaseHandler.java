package com.example.deliveryapp.SQLiteDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "cartManager";
    private static final String TABLE_CART = "cart";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PRICE = "price";
    private static final String KEY_QUANTITY = "quantity";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CART + " ("
                + KEY_ID + " TEXT,"
                + KEY_NAME + " TEXT,"
                + KEY_PRICE + " TEXT,"
                + KEY_QUANTITY + " TEXT)";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);

        // Create tables again
        onCreate(db);
    }

    // code to add the new cartList
    public void addToCart(CartListModel cartList) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, cartList.getID());
        values.put(KEY_NAME, cartList.getName());
        values.put(KEY_PRICE, cartList.getPrice());
        values.put(KEY_QUANTITY, cartList.getQuantity());

        // Inserting Row
        db.insert(TABLE_CART, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    // code to get the single contact
    CartListModel getCartItem(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CART, new String[] { KEY_ID,
                        KEY_NAME, KEY_PRICE}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        CartListModel contact = new CartListModel(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), Double.parseDouble(cursor.getString(2)), Integer.parseInt(cursor.getString(3)));
        // return contact
        return contact;
    }

    // code to get all contacts in a list view
    public List<CartListModel> getAllCartItems() {
        List<CartListModel> contactList = new ArrayList<CartListModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CART;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CartListModel cartList = new CartListModel();
                cartList.setID(Integer.parseInt(cursor.getString(0)));
                cartList.setName(cursor.getString(1));
                cartList.setPrice(Double.parseDouble(cursor.getString(2)));
                cartList.setQuantity(Integer.parseInt(cursor.getString(3)));
                // Adding cartList to list
                contactList.add(cartList);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    // code to update the single cartList
    public int updateCartItems(CartListModel cartList) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, cartList.getName());
        values.put(KEY_PRICE, cartList.getPrice());
        values.put(KEY_QUANTITY, cartList.getQuantity());

        // updating row
        return db.update(TABLE_CART, values, KEY_ID + " = ?",
                new String[] { String.valueOf(cartList.getID()) });
    }

    // Deleting single cartList
    public void deleteCartItems(CartListModel cartList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CART, KEY_ID + " = ?",
                new String[] { String.valueOf(cartList.getID()) });
        db.close();
    }

    //Delete All items
    public void deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CART,null,null);
        db.close();
    }
    // Getting contacts Count
    public int getCartItemsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CART;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

}
