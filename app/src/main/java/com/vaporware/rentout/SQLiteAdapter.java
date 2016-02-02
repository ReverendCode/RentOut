package com.vaporware.rentout;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteAdapter {

    public static final String MYDATABASE_NAME = "rentOutDB";
    public static final String GEAR_TABLE = "equipment";
    public static final int MYDATABASE_VERSION = 1;
    public static final String KEY_ID = "id";
    public static final String KEY_TYPE = "type";
    public static final String COMMA = ", ";


    //create table MY_DATABASE (ID integer primary key, Content text not null);
    private static final String SCRIPT_CREATE_DATABASE =
            "create table " + GEAR_TABLE + " ("
                    + KEY_ID + " text not null);";

    private SQLiteHelper sqLiteHelper;
    private SQLiteDatabase sqLiteDatabase;

    private Context context;

    public SQLiteAdapter(Context c){
        context = c;
    }

    public SQLiteAdapter openToRead() throws android.database.SQLException {
        sqLiteHelper = new SQLiteHelper(context, MYDATABASE_NAME, null, MYDATABASE_VERSION);
        sqLiteDatabase = sqLiteHelper.getReadableDatabase();
        return this;
    }

    public SQLiteAdapter openToWrite() throws android.database.SQLException {
        sqLiteHelper = new SQLiteHelper(context, MYDATABASE_NAME, null, MYDATABASE_VERSION);
        sqLiteDatabase = sqLiteHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        sqLiteHelper.close();
    }

    public long insert(String content){

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ID, content);
        return sqLiteDatabase.insert(GEAR_TABLE, null, contentValues);
    }

    public int deleteAll(){
        return sqLiteDatabase.delete(GEAR_TABLE, null, null);
    }

    public String queueAll(){
        String[] columns = new String[]{KEY_ID};
        Cursor cursor = sqLiteDatabase.query(GEAR_TABLE, columns,
                null, null, null, null, null);
        String result = "";

        int index_CONTENT = cursor.getColumnIndex(KEY_ID);
        for(cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()){
            result = result + cursor.getString(index_CONTENT) + "\n";
        }

        return result;
    }

    public class SQLiteHelper extends SQLiteOpenHelper {

        public SQLiteHelper(Context context, String name,
                            CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub
            db.execSQL(SCRIPT_CREATE_DATABASE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub

        }

    }

}