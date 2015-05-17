package com.fotistsalampounis.letsgotoateith.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.fotistsalampounis.letsgotoateith.data.TransfersContract.UsersEntry;
import com.fotistsalampounis.letsgotoateith.data.TransfersContract.CarsEntry;
import com.fotistsalampounis.letsgotoateith.data.TransfersContract.TransportsEntry;

/**
 * Created by user on 14/4/2015.
 */
public class TransfersDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "transfers.db";

    public TransfersDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_USERS_TABLE = "CREATE TABLE " +

                UsersEntry.TABLE_NAME + " (" +
                UsersEntry.COLUMN_USERNAME + " VARCHAR , " +
                UsersEntry.COLUMN_AREA + " INTEGER  , " +
                UsersEntry.COLUMN_SCHOOL + " VARCHAR , " +
                UsersEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                UsersEntry.COLUMN_EMAIL + " VARCHAR , " +
                UsersEntry.COLUMN_FB + " VARCHAR , " +
                UsersEntry.COLUMN_FULLNAME + " VARCHAR " +
                " );";

        final String SQL_CREATE_REG_CARS_TABLE = "CREATE TABLE " +
                CarsEntry.TABLE_NAME + " (" +
                CarsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                // the ID of the location entry associated with this weather data
                CarsEntry.COLUMN_PEOPLE + " INTEGER NOT NULL, " +
                CarsEntry.COLUMN_DAY + " INTEGER NOT NULL, " +
                CarsEntry.COLUMN_DEP_TIME + " INTEGER NOT NULL, " +
                CarsEntry.COLUMN_RET_TIME + " INTEGER NOT NULL," +
                CarsEntry.COLUMN_FREQ + " INTEGER NOT NULL, " +
                CarsEntry.COLUMN_PEOPLE_REG + " INTEGER NOT NULL, " +
                CarsEntry.COLUMN_DRIVER_ID+ " INTEGER NOT NULL REFERENCES "+ UsersEntry.TABLE_NAME+" ("+UsersEntry._ID+"), "+
                CarsEntry.COLUMN_AREA+" TEXT NOT NULL );";

        final String SQL_CREATE_TRANSP_TABLE = "CREATE TABLE " + TransportsEntry.TABLE_NAME + " (" +
                TransportsEntry.COLUMN_REG_CAR_ID+" INTEGER NOT NULL REFERENCES "+CarsEntry.TABLE_NAME + " (" + CarsEntry._ID + "), "+
//TODO: check if i seira pou ta vaze exei simasia
                //TODO:Remove conf columns
                TransportsEntry.COLUMN_CONFIRMED_U + " INTEGER NOT NULL, " +
                TransportsEntry.COLUMN_CONFIRMED_D + " INTEGER NOT NULL, " +
                TransportsEntry.COLUMN_USER_ID + " INTEGER NOT NULL REFERENCES " +
                UsersEntry.TABLE_NAME+" (" + UsersEntry._ID +")); ";

        sqLiteDatabase.execSQL(SQL_CREATE_USERS_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_REG_CARS_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_TRANSP_TABLE);
        //sqLiteDatabase.execSQL("INSERT INTO USERS values(ft,0,IT,001,ftteithe.gr,ft,FotisT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + UsersEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CarsEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TransportsEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
