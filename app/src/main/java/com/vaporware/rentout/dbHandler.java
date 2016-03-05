package com.vaporware.rentout;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ReverendCode on 1/29/16.
 */
public class dbHandler extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "gearDataBase";
    public static final int DATABASE_VERSION = 1;
    //gear table name
    public static final String TABLE_GEAR = "gear";
    //gear table columns
    public static final String KEY_ID = "_id"; //underscore because Android
    public static final String KEY_STOCK_NUM = "stockNum";
    public static final String KEY_CURRENT_OWNER = "cur_owner";
    public static final String KEY_DATA = "data";
    //owner table
    public static final String TABLE_OWNERS = "owners";
    //owner table columns
    public static final String KEY_NAME = "name";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_OTHER = "other"; //I don't know what else we need

    public dbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_GEAR_TABLE = "CREATE TABLE " + TABLE_GEAR + "(" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_STOCK_NUM +
                " TEXT," + KEY_DATA + " BLOB)";

        String CREATE_OWNER_TABLE = "CREATE TABLE " + TABLE_OWNERS + " (" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +  KEY_DATA + " BLOB" + ")";
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


    public void addGear(EquipmentOuterClass.Equipment item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_STOCK_NUM, item.getStockNum());
        db.insert(TABLE_GEAR, null, values);
        long id = db.insert(TABLE_GEAR, null, values);
        EquipmentOuterClass.Equipment.Builder nBuilder = item.toBuilder();
        nBuilder.setId((int)id);
        //this should keep the autoincremented _id and the .proto id the same
        updateGear(nBuilder.build());
        db.close();
    }
    public void addOwner(Owner.Customer owner) {
        Owner.Customer.Builder builder = owner.toBuilder();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.putNull(KEY_DATA);
        long id = db.insert(TABLE_OWNERS,null, values);
        builder.setId((int) id);
        updateOwner(builder.build());

        db.close();
    }

    public EquipmentOuterClass.Equipment getItem(int id) { //maybe that error check will work
        if (!hasItem(id)) throw new Resources.NotFoundException("No Such Item");
        SQLiteDatabase db = this.getReadableDatabase();
        EquipmentOuterClass.Equipment.Builder myItem = EquipmentOuterClass.Equipment.newBuilder();
        String query = "SELECT * FROM " + TABLE_GEAR + " WHERE " + KEY_ID + " = " + id;

        Cursor cursor = db.rawQuery(query,null);
        db.close();
        myItem.mergeFrom((Message) ByteString.copyFromUtf8(cursor.getString(2)));
        cursor.close();
        return myItem.build();
    }

    public boolean hasItem(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_GEAR + " WHERE " + KEY_ID + " = " + id;
        Cursor cursor = db.rawQuery(query, null);
        db.close();
        if (cursor == null) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public Owner.Customer getOwner(int id) { //consider expanding this until you understand it better.
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_OWNERS, new String[]{KEY_ID, KEY_NAME, KEY_PHONE, KEY_OTHER},
                KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        db.close();
        if (cursor != null) {
            cursor.moveToFirst();
        }
        Owner.Customer.Builder builder = Owner.Customer.newBuilder();
        try {
            builder.mergeFrom(ByteString.copyFromUtf8(cursor.getString(2)));
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
        return builder.build();
    }

    public List<EquipmentOuterClass.Equipment> getAllItems() {
        List<EquipmentOuterClass.Equipment> gearList = new ArrayList<>();
        String getAllGearQuery = "SELECT * FROM " + TABLE_GEAR;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(getAllGearQuery, null);
        db.close();
        if (cursor.moveToFirst()) {

            do {
                EquipmentOuterClass.Equipment.Builder builder = EquipmentOuterClass.Equipment.newBuilder();
                try {
                    builder.mergeFrom(ByteString.copyFromUtf8(cursor.getString(2)));
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
                gearList.add(builder.build());

            } while (cursor.moveToNext());
        }
        return gearList;
    }

    public List<Owner.Customer> getAllOwners() {
        List<Owner.Customer> ownerList= new ArrayList<>();
        String getAllOwners = "SELECT * FROM " + TABLE_OWNERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(getAllOwners, null);
        db.close();
        if (cursor.moveToFirst()) {
            do {
                Owner.Customer.Builder builder = Owner.Customer.newBuilder();
                try {
                    builder.mergeFrom(ByteString.copyFromUtf8(cursor.getString(2)));
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
                ownerList.add(builder.build());
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

    public int updateGear(EquipmentOuterClass.Equipment item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_STOCK_NUM, item.getStockNum());
        values.put(KEY_DATA, String.valueOf(item.toByteString()));

        return db.update(TABLE_GEAR, values, KEY_ID + " = ?",
                new String[]{String.valueOf(item.getId()) });
    }

    public int updateOwner(Owner.Customer owner) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DATA,String.valueOf(owner.toByteString()));

        return db.update(TABLE_OWNERS,values,KEY_ID + " = ?",
                new String[]{String.valueOf(owner.getId())});
    }

    public void deleteGear(Gear item) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_GEAR, KEY_ID + " = ?",
                new String[] {String.valueOf(item.getId()) });
        db.close();
    }

    public void deleteOwner(Owner.Customer owner) { //maybe check to not delete "shop"
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_OWNERS, KEY_ID + " = ?",
                new String[] {String.valueOf(owner.getId()) });
        db.close();
    }

}

