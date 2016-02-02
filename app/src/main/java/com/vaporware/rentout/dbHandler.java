package com.vaporware.rentout;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ReverendCode on 1/29/16.
 */
public class dbHandler extends SQLiteOpenHelper {

    //default names for admin owners
    //TODO: change this to reflect some kind of preferences
    public static final String SHOP = "shop";


    public static final String DATABASE_NAME = "gearDataBase";
    public static final int DATABASE_VERSION = 1;
    //gear table name
    public static final String TABLE_GEAR = "gear";

    //gear table columns
    public static final String KEY_ID = "id";
    public static final String KEY_TYPE = "type";
    public static final String KEY_OWNER = "owner";

    public static final String TABLE_OWNERS = "owners";

    public static final String KEY_NAME = "name";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_OTHER = "other"; //I don't know what else we need

    public dbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_GEAR_TABLE = "CREATE TABLE " + TABLE_GEAR + "(" +
                KEY_ID + "INTEGER PRIMARY KEY," + KEY_TYPE + " TEXT" +
                KEY_OWNER + " TEXT)";
        String CREATE_OWNER_TABLE = "CREATE TABLE " + TABLE_OWNERS + "(" +
                KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT," +
                KEY_PHONE + " TEXT," + KEY_OTHER + " TEXT" + ")";
        db.execSQL(CREATE_GEAR_TABLE);
        db.execSQL(CREATE_OWNER_TABLE);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GEAR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OWNERS);
        onCreate(db);
    }
    //And then there were CRUD functions.. Can I get that as a menu option?

    public void addGear(Gear item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, item.getId());
        values.put(KEY_TYPE, item.getType());
        values.put(KEY_OWNER, SHOP);
        db.insert(TABLE_GEAR, null, values);
        db.close();

    }
    public void addOwner(Owner owner) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, owner.getId());
        values.put(KEY_NAME, owner.getName());
        values.put(KEY_PHONE, owner.getPhone());
        values.put(KEY_OTHER, owner.getOther());
        db.insert(TABLE_OWNERS, null, values);
        db.close();

    }




    public Gear getItem(int id) { //consider expanding this until you understand it better.
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_GEAR, new String[]{KEY_ID, KEY_TYPE, KEY_OWNER}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        db.close();
        if (cursor != null) {
            cursor.moveToFirst();
        }
        Gear gear = new Gear(Integer.parseInt(cursor.getString(0)),cursor.getString(1)
            , cursor.getString(2));
        return gear;
    }

    public Owner getOwner(int id) { //consider expanding this until you understand it better.
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_OWNERS, new String[]{KEY_ID, KEY_NAME, KEY_PHONE, KEY_OTHER},
                KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        db.close();
        if (cursor != null) {
            cursor.moveToFirst();
        }
        Owner owner = new Owner(Integer.parseInt(cursor.getString(0)),cursor.getString(1),
                cursor.getString(2),cursor.getString(3));
        return owner;
    }


    public List<Gear> getAllItems() {
        List<Gear> gearList = new ArrayList<Gear>();
        String getAllGearQuery = "SELECT * FROM " + TABLE_GEAR;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(getAllGearQuery, null);
        db.close();
        if (cursor.moveToFirst()) {
            do {
                Gear gear = new Gear(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),cursor.getString(2));
                gearList.add(gear);
            } while (cursor.moveToNext());
        }
        return gearList;
    }

    public List<Owner> getAllOwners() {
        List<Owner> ownerList= new ArrayList<Owner>();
        String getAllOwners = "SELECT * FROM " + TABLE_OWNERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(getAllOwners, null);
        db.close();
        if (cursor.moveToFirst()) {
            do {
                Owner owner = new Owner(Integer.parseInt(cursor.getString(0)),cursor.getString(1),
                        cursor.getString(2),cursor.getString(3));
                ownerList.add(owner);
            } while (cursor.moveToNext());
        }
        return ownerList;
    }

    public  int getGearCount() {
        String queryString = "SELECT * FROM " + TABLE_GEAR;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);
        cursor.close();
        db.close();//I don't know if this is needed here.
        return cursor.getCount();
    }

    public int getOwnerCount() {
        String queryString = "SELECT * FROM " + TABLE_OWNERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);
        cursor.close();
        db.close();//I don't know if this is needed here.
        return cursor.getCount();
    }

    public int updateGear(Gear item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TYPE, item.getType());
        values.put(KEY_OWNER, item.getOwner());
        values.put(KEY_OTHER, item.getOther());
        return db.update(TABLE_GEAR, values, KEY_ID + " = ?",
                new String[]{String.valueOf(item.getId()) });
    }

    public int updateOwner(Owner owner) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, owner.getName());
        values.put(KEY_PHONE, owner.getPhone());
        values.put(KEY_OTHER, owner.getOther());
        return db.update(TABLE_OWNERS, values, KEY_ID + " = ?",
                new String[] {String.valueOf(owner.getId()) });
    }

    public void deleteGear(Gear item) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_GEAR, KEY_ID + " = ?",
                new String[] {String.valueOf(item.getId()) });
        db.close();
    }

    public void deleteOwner(Owner owner) { //maybe check to not delete "shop"
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_OWNERS, KEY_ID + " = ?",
                new String[] {String.valueOf(owner.getId()) });
        db.close();
    }

}
