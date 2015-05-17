package com.fotistsalampounis.letsgotoateith.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by user on 14/4/2015.
 */
public class TransfersContract {



    // The "Content authority" is a name for the entire content provider, similar to the
    // relationship between a domain name and its website.  A convenient string to use for the
    // content authority is the package name for the app, which is guaranteed to be unique on the
    // device.
    public static final String CONTENT_AUTHORITY = "com.example.user.letsgotoateith";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Possible paths (appended to base content URI for possible URI's)
    // For instance, content://com.example.android.sunshine.app/weather/ is a valid path for
    // looking at weather data. content://com.example.android.sunshine.app/givemeroot/ will fail,
    // as the ContentProvider hasn't been given any information on what to do with "givemeroot".
    // At least, let's hope not.  Don't be that dev, reader.  Don't be that dev.
    public static final String PATH_USERS = "users";
    public static final String PATH_REG_CARS = "reg_cars";
    public static final String PATH_TRANSP = "transp";
    // To make it easy to query for the exact date, we normalize all dates that go into
    // the database to the start of the the Julian day at UTC.


    /* Inner class that defines the table contents of the weather table */
    public static final class UsersEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_USERS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USERS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USERS;

        public static final String TABLE_NAME = "users";

        // Column with the users name into the users table not null.
        public static final String COLUMN_USERNAME = "username";

        // Column with the users surname into the users table not null.
        public static final String COLUMN_FULLNAME = "fullname";

        // Column with the users school into the users table not null.
        public static final String COLUMN_SCHOOL = "school";

        // Column with the users area into the users table not null.
        public static final String COLUMN_AREA = "area";

        // Column with the users email into the users table not null.
        public static final String COLUMN_EMAIL = "email";

        // Column with the users Facebook link into the users table.
        public static final String COLUMN_FB = "fb";



        public static Uri buildUserUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildUserUri(int id) {
            return CONTENT_URI.buildUpon().appendPath(Integer.toString(id)).build();
        }

        public static Uri buildUserUri() {
            return CONTENT_URI.buildUpon().build();
        }
        public static  Uri buildUserUri(String username,String fullname,String school,String email,int area,String facebookLink){
            Uri.Builder ret=CONTENT_URI.buildUpon().appendPath(username)
                    .appendPath(fullname)
                    .appendPath(school)
                    .appendPath(email)
                    .appendPath(Integer.toString(area))
                    .appendPath(facebookLink);
            return ret.build();
        }
        public static int getIDFromUri(Uri uri) {
            return Integer.parseInt(uri.getPathSegments().get(1));
        }
        public static String getUsernameFromUri(Uri uri) {
            return uri.getPathSegments().get(2);
        }

        public static String getFullnameFromUri(Uri uri) {
            return uri.getPathSegments().get(3);
        }

        public static String getSchoolFromUri(Uri uri) {
            return uri.getPathSegments().get(4);
        }

        public static String getAreaFromUri(Uri uri) {
            return uri.getPathSegments().get(5);
        }

        public static String getEmailFromUri(Uri uri) {
            return uri.getPathSegments().get(6);
        }

        public static String getFbFromUri(Uri uri) {
            return uri.getPathSegments().get(7);
        }


    }

    public static final class CarsEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_REG_CARS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_REG_CARS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_REG_CARS;

        public static final String TABLE_NAME = "reg_cars";

        // Column with the number pf people the driver wants into the reg_cars table not null.
        public static final String COLUMN_PEOPLE = "people";

        // Column with the day the transport takes place into the reg_cars table not null.
        public static final String COLUMN_DAY = "day";

        // Column with the departure time into the reg_cars table not null.
        public static final String COLUMN_DEP_TIME = "dep_time";

        // Column with the return time into the reg_cars table not null.
        public static final String COLUMN_RET_TIME = "ret_time";

        // Column with the transports' frequency into the reg_cars table not null.
        public static final String COLUMN_FREQ = "freq";

        // Column with the driver's id into the reg_cars table not null foreign from users.
        public static final String COLUMN_DRIVER_ID = "driver_id";

        // Column with how many people chose this transport into the reg_cars table not null.
        public static final String COLUMN_PEOPLE_REG = "people_reg";

        // Column with area into the reg_cars table not null.
        public static final String COLUMN_AREA = "area";

        public static Uri buildCarUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildRegUri(int people,int day,int depTime,int retTime,int freq,int area) {
            return CONTENT_URI.buildUpon()
                    .appendPath(Integer.toString(day))
                    .appendPath(Integer.toString(people))
                    .appendPath(Integer.toString(depTime))
                    .appendPath(Integer.toString(retTime))
                    .appendPath(Integer.toString(freq))
                    .appendPath(Integer.toString(area)).build();
        }

        public static Uri buildSearchRegUri(int day,int depTime,int retTime,int freq,int area) {
            return CONTENT_URI.buildUpon()
                    .appendPath(Integer.toString(day))
                    .appendPath(Integer.toString(depTime))
                    .appendPath(Integer.toString(retTime))
                    .appendPath(Integer.toString(freq))
                    .appendPath(Integer.toString(area)).build();
        }


//        public static int getIDFromUri(Uri uri) {
//            return Integer.parseInt(uri.getPathSegments().get(1));
//        }
//        public static int getPeopleFromUri(Uri uri) {
//            return Integer.parseInt(uri.getPathSegments().get(2));
//        }
        public static int getCarIdFromUri(Uri uri) {
            return Integer.parseInt(uri.getPathSegments().get(1));
        }

        public static int getDayFromUri(Uri uri) {
            return Integer.parseInt(uri.getPathSegments().get(1));
        }
        public static int getDepFromUri(Uri uri) {
            return Integer.parseInt(uri.getPathSegments().get(2));
        }
        public static int getRetFromUri(Uri uri) {
            return Integer.parseInt(uri.getPathSegments().get(3));
        }
        public static int getFreqFromUri(Uri uri) {
            return Integer.parseInt(uri.getPathSegments().get(4));
        }
        public static int getAreaFromUri(Uri uri) {
            return Integer.parseInt(uri.getPathSegments().get(5));
        }
        public static int getDriverIDFromUri(Uri uri) {
            return Integer.parseInt(uri.getPathSegments().get(6));
        }
        public static int getPeopleRegFromUri(Uri uri) {
            return Integer.parseInt(uri.getPathSegments().get(7));
        }

        public static int getIdFromUri(Uri uri) {
            return Integer.parseInt(uri.getPathSegments().get(6));
        }
    }

    public static final class TransportsEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TRANSP).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TRANSP;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TRANSP;

        public static final String TABLE_NAME = "transp";

        // Column with the car id from the reg_cars table not null foreign.
        public static final String COLUMN_REG_CAR_ID = "reg_car_id";

        // Column with the int value if transport is confirmed from the passenger not null.
        public static final String COLUMN_CONFIRMED_U = "confirmed_u";

        // Column with the int value if transport is confirmed from the driver not null.
        public static final String COLUMN_CONFIRMED_D = "confirmed_d";

        // Column with user id from the table users not null foreign.
        public static final String COLUMN_USER_ID = "user_id";

        public static int getRegCarIdFromUri(Uri uri) {
            return Integer.parseInt(uri.getPathSegments().get(1));
        }

        public static int getUserIdFromUri(Uri uri) {
            return Integer.parseInt(uri.getPathSegments().get(1));
        }

        public static Uri buildTranspUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildUsersTranspUri() {
            return CONTENT_URI.buildUpon().build();
        }

        public static Uri buildTranspUri2() {
            return CONTENT_URI.buildUpon().appendPath("1").appendPath("1").build();
        }

        public static Uri buildTranspUri() {
            return CONTENT_URI.buildUpon().build();
        }
    }


}
