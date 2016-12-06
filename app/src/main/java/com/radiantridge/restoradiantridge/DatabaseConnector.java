package com.radiantridge.restoradiantridge;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.sql.Timestamp;

/**
 * A DatabaseConnector class that implements CRUD methods for the Restaurant object.
 * @author Alena Shulzhenko
 * @version 2016-12-06
 */

public class DatabaseConnector extends SQLiteOpenHelper {
    public static final String TAG = "DBConnector";
    // table name
    public static final String TABLE_RESTOS = "restos";
    //column names
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_GENRE = "genre";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_RATING = "rating";
    public static final String COLUMN_NOTES = "notes";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_SOURCE = "source";
    public static final String COLUMN_HEROKU_ID = "heroku";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String COLUMN_DATE_CREATED = "createdDate";
    public static final String COLUMN_DATE_MODIFIED = "modifiedDate";
    // file name
    private static final String DATABASE_NAME = "restos.db";
    // if the version number is increased the onUpdate() will be called
    private static final int DATABASE_VERSION = 1;
    // static instance to share DBHelper
    private static DatabaseConnector dbh = null;

    /**
     * Private constructor for DatabaseConnector
     * @param context the reference to the Context class.
     */
    private DatabaseConnector(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    /**
     * The lifecycle method that creates restos database.
     * @param database the database.
     */
    @Override
    public void onCreate(SQLiteDatabase database) {
        String sql = "create table "
                + TABLE_RESTOS + "( "
                + COLUMN_ID + " integer primary key autoincrement, "
                + COLUMN_NAME + " text not null, "
                + COLUMN_GENRE + " text, "
                + COLUMN_PRICE + " integer, "
                + COLUMN_RATING + " real, "
                + COLUMN_NOTES + " text, "
                + COLUMN_PHONE + " text, "
                + COLUMN_SOURCE + " integer, "
                + COLUMN_HEROKU_ID + " integer, "
                + COLUMN_ADDRESS + " text, "
                + COLUMN_LATITUDE + " real, "
                + COLUMN_LONGITUDE + " real, "
                + COLUMN_DATE_CREATED + " integer, "
                + COLUMN_DATE_MODIFIED + " integer "
                + ");";
        database.execSQL(sql);
        Log.i(TAG, "onCreate()");
    }

    /**
     * The static Database Connector initializer to make sure to have one instance
     * in app.
     * @param context the reference to the Context object
     *                (the class where this helper is initialized).
     * @return DatabaseConnector instance.
     */
    public static DatabaseConnector getDatabaseConnector(Context context) {
        if (dbh == null) {
            dbh = new DatabaseConnector(context.getApplicationContext());
            Log.i(TAG, "getDatabaseConnector, dbh == null");
        }
        Log.i(TAG, "getDatabaseConnector()");
        return dbh;
    }

    /**
     * The lifecycle method that updates restos database.
     * @param db the new database to upgrade the old version.
     * @param oldVersion the old version number of the database.
     * @param newVersion the new version number of the database.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, DatabaseConnector.class.getName() + "Upgrading database from version "
                + oldVersion + " to " + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTOS);
        onCreate(db);
        Log.i(TAG, "onUpgrade()");
    }

    /**
     * Adds the Restaurant object to the database.
     * @param resto the Restaurant object to add to the database.
     * @return the id of the added Restaurant object.
     */
    public long addResto(Restaurant resto) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, resto.getName());
        cv.put(COLUMN_GENRE, resto.getGenre());
        cv.put(COLUMN_PRICE, resto.getPriceRange());
        cv.put(COLUMN_RATING, resto.getStarRating());
        cv.put(COLUMN_NOTES, resto.getNotes());
        cv.put(COLUMN_PHONE, resto.getPhone());
        cv.put(COLUMN_SOURCE, resto.getSource());
        cv.put(COLUMN_HEROKU_ID, resto.getHerokuId());
        cv.put(COLUMN_ADDRESS, resto.getAddress());
        cv.put(COLUMN_LATITUDE, resto.getLatitude());
        cv.put(COLUMN_LONGITUDE, resto.getLongitude());
        cv.put(COLUMN_DATE_CREATED, resto.getCreatedTime().getTime());
        cv.put(COLUMN_DATE_MODIFIED, resto.getModifiedTime().getTime());

        long id = getWritableDatabase().insert(TABLE_RESTOS, null, cv);
        Log.d(TAG, "Inserted resto, name: " + resto.getName() + ", id: " + id);
        return id;
    }

    /**
     * Deletes the Restaurant object with the given id.
     * @param id the id of the Restaurant object to delete.
     */
    public void deleteResto(int id) {
        getWritableDatabase().delete(TABLE_RESTOS, COLUMN_ID + " = ?",
                new String[] { String.valueOf(id) });
        Log.d(TAG, "Deleted resto with the id: " + id);
    }

    /**
     * Updates the Restaurant object in the database.
     * @param resto the Restaurant object to update.
     */
    public void updateResto(Restaurant resto) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, resto.getName());
        cv.put(COLUMN_GENRE, resto.getGenre());
        cv.put(COLUMN_PRICE, resto.getPriceRange());
        cv.put(COLUMN_RATING, resto.getStarRating());
        cv.put(COLUMN_NOTES, resto.getNotes());
        cv.put(COLUMN_PHONE, resto.getPhone());
        cv.put(COLUMN_SOURCE, resto.getSource());
        cv.put(COLUMN_HEROKU_ID, resto.getHerokuId());
        cv.put(COLUMN_ADDRESS, resto.getAddress());
        cv.put(COLUMN_LATITUDE, resto.getLatitude());
        cv.put(COLUMN_LONGITUDE, resto.getLongitude());
        cv.put(COLUMN_DATE_CREATED, resto.getCreatedTime().getTime());
        cv.put(COLUMN_DATE_MODIFIED, resto.getModifiedTime().getTime());

        long id = getWritableDatabase().update(TABLE_RESTOS, cv, COLUMN_ID + " = ?",
                new String[] { String.valueOf(resto.getDatabaseId()) });
        Log.d(TAG, "Updated resto, name: " + resto.getName() + ", id: " + id);
    }

    /**
     * Returns all Restaurant objects in the database.
     * @return all Restaurant objects in the database.
     */
    public Restaurant[] getAllRestos() {
        Cursor cursor = getReadableDatabase().query(TABLE_RESTOS, null, null, null,
                null, null, COLUMN_NAME, null);
        return getRestosFromCursor(cursor);
    }

    /**
     * Searches for Restaurant objects with the name like the one provided.
     * @param name the name key.
     * @return the Restaurant objects with the name like the one provided.
     */
    public Restaurant[] searchByName(String name) {
        Cursor cursor = getReadableDatabase().query(TABLE_RESTOS, null, COLUMN_NAME + " like ? ",
                new String[] { "%"+name+"%" },
                null, null, COLUMN_NAME, null);
        return getRestosFromCursor(cursor);
    }

    /**
     * Searches for Restaurant objects with the genre like the one provided.
     * @param genre the genre key.
     * @return the Restaurant objects with the genre like the one provided.
     */
    public Restaurant[] searchByGenre(String genre) {
        Cursor cursor = getReadableDatabase().query(TABLE_RESTOS, null, COLUMN_GENRE + " like ? ",
                new String[] { "%"+genre+"%" },
                null, null, COLUMN_NAME, null);
        return getRestosFromCursor(cursor);
    }

    /**
     * Searches for Restaurant objects with the city like the one provided.
     * @param city the city key.
     * @return the Restaurant objects with the city like the one provided.
     */
    public Restaurant[] searchByCity(String city) {
        Cursor cursor = getReadableDatabase().query(TABLE_RESTOS, null, COLUMN_ADDRESS + " like ? ",
                new String[] { "%"+city+"%" },
                null, null, COLUMN_NAME, null);
        return getRestosFromCursor(cursor);
    }


    /**
     * Searches for Restaurant objects with the name, genre, or city like the key provided.
     * @param key the key to search against in the database.
     * @return the Restaurant objects with the name, genre, or city like the key provided.
     */
    public Restaurant[] search(String key) {
        String searchKey = "%"+key+"%";
        Cursor cursor = getReadableDatabase().query(TABLE_RESTOS, null,
                COLUMN_ADDRESS + " like ? or " + COLUMN_GENRE + " like ? or "
                        + COLUMN_NAME + " like ? ",
                new String[] { searchKey, searchKey, searchKey },
                null, null, COLUMN_NAME, null);
        return getRestosFromCursor(cursor);
    }

    /**
     * Returns the Restaurant object with the provided id.
     * @param id the id of the Restaurant object to find.
     * @return the Restaurant object with the provided id.
     */
    public Restaurant getResto(int id) {
        Cursor cursor = getReadableDatabase().query(TABLE_RESTOS, null,
                COLUMN_ID + " = ? ",
                new String[] { String.valueOf(id) },
                null, null, null, null);
        Restaurant[] restos = getRestosFromCursor(cursor);
        return restos.length == 0 ? null : restos[0];
    }

    /**
     * Populates restos array using the information from the cursor.
     * @param cursor the SQLiteCursor with the results from the query.
     * @return the restos array with the results from the query.
     */
    private Restaurant[] getRestosFromCursor(Cursor cursor) {
        Restaurant resto;
        int length = cursor.getCount();
        Restaurant[] restos = new Restaurant[length];
        int index = 0;
        while(cursor.moveToNext()) {
            resto = new Restaurant();
            resto.setDatabaseId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
            resto.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            resto.setGenre(cursor.getString(cursor.getColumnIndex(COLUMN_GENRE)));
            resto.setPriceRange(cursor.getInt(cursor.getColumnIndex(COLUMN_PRICE)));
            resto.setStarRating(cursor.getDouble(cursor.getColumnIndex(COLUMN_RATING)));
            resto.setNotes(cursor.getString(cursor.getColumnIndex(COLUMN_NOTES)));
            resto.setPhone(cursor.getString(cursor.getColumnIndex(COLUMN_PHONE)));
            resto.setSource(cursor.getInt(cursor.getColumnIndex(COLUMN_SOURCE)));
            resto.setHerokuId(cursor.getInt(cursor.getColumnIndex(COLUMN_HEROKU_ID)));
            resto.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS)));
            resto.setLatitude(cursor.getDouble(cursor.getColumnIndex(COLUMN_LATITUDE)));
            resto.setLongitude(cursor.getDouble(cursor.getColumnIndex(COLUMN_LONGITUDE)));
            resto.setCreatedTime(new Timestamp(cursor.getInt(cursor.getColumnIndex(COLUMN_DATE_CREATED))));
            resto.setModifiedTime(new Timestamp(cursor.getInt(cursor.getColumnIndex(COLUMN_DATE_MODIFIED))));
            restos[index] = resto;
            Log.d(TAG, "Queried resto, name: " + resto.getName() + ", id: " + resto.getDatabaseId());
            index++;
        }
        cursor.close();
        return restos;
    }
}
