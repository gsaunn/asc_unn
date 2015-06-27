package campusevents.michael.android.com.campusevents;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class CampusEventDB extends SQLiteOpenHelper {
    private static final String TEXT_TYPE = " TEXT";
    private static final String NOT_NULL = " NOT NULL";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_TABLE_DEPT =
            "CREATE TABLE " + TableDefnDB.DeptTableDfn.TABLE_NAME + " (" +
                    TableDefnDB.DeptTableDfn._ID + " INTEGER PRIMARY KEY," +
                    TableDefnDB.DeptTableDfn.COLUMN_NAME_DATE + TEXT_TYPE + NOT_NULL  + COMMA_SEP +
                    TableDefnDB.DeptTableDfn.COLUMN_NAME_TITLE + TEXT_TYPE + NOT_NULL  + COMMA_SEP +
                    TableDefnDB.DeptTableDfn.COLUMN_NAME_VENUE + TEXT_TYPE + NOT_NULL  + COMMA_SEP +
                    TableDefnDB.DeptTableDfn.COLUMN_NAME_CATEGORY + TEXT_TYPE + NOT_NULL  + COMMA_SEP +
                    TableDefnDB.DeptTableDfn.COLUMN_NAME_ORGANIZER + TEXT_TYPE + NOT_NULL  + COMMA_SEP +
                    TableDefnDB.DeptTableDfn.COLUMN_NAME_TIME + TEXT_TYPE + NOT_NULL  + COMMA_SEP +
                    TableDefnDB.DeptTableDfn.COLUMN_NAME_IMAGEURL + TEXT_TYPE + NOT_NULL  + COMMA_SEP +
                    TableDefnDB.DeptTableDfn.COLUMN_NAME_BODY  + TEXT_TYPE + NOT_NULL + " UNIQUE" +
    // Any other options for the CREATE command
            " )";

    private static final String SQL_CREATE_TABLE_SOCIAL =
            "CREATE TABLE " + TableDefnDB.SocialTableDfn.TABLE_NAME + " (" +
                    TableDefnDB.SocialTableDfn._ID + " INTEGER PRIMARY KEY," +
                    TableDefnDB.SocialTableDfn.COLUMN_NAME_DATE + TEXT_TYPE + NOT_NULL  + COMMA_SEP +
                    TableDefnDB.SocialTableDfn.COLUMN_NAME_TITLE + TEXT_TYPE + NOT_NULL  + COMMA_SEP +
                    TableDefnDB.SocialTableDfn.COLUMN_NAME_VENUE + TEXT_TYPE + NOT_NULL  + COMMA_SEP +
                    TableDefnDB.SocialTableDfn.COLUMN_NAME_CATEGORY + TEXT_TYPE + NOT_NULL  + COMMA_SEP +
                    TableDefnDB.SocialTableDfn.COLUMN_NAME_ORGANIZER + TEXT_TYPE + NOT_NULL  + COMMA_SEP +
                    TableDefnDB.SocialTableDfn.COLUMN_NAME_TIME + TEXT_TYPE + NOT_NULL  + COMMA_SEP +
                    TableDefnDB.SocialTableDfn.COLUMN_NAME_IMAGEURL + TEXT_TYPE + NOT_NULL  + COMMA_SEP +
                    TableDefnDB.SocialTableDfn.COLUMN_NAME_BODY  + TEXT_TYPE + NOT_NULL + " UNIQUE" +
                    // Any other options for the CREATE command
                    " )";

    private static final String SQL_CREATE_TABLE_RELIGION =
            "CREATE TABLE " + TableDefnDB.ReligionTableDfn.TABLE_NAME + " (" +
                    TableDefnDB.ReligionTableDfn._ID + " INTEGER PRIMARY KEY," +
                    TableDefnDB.ReligionTableDfn.COLUMN_NAME_DATE + TEXT_TYPE + NOT_NULL  + COMMA_SEP +
                    TableDefnDB.ReligionTableDfn.COLUMN_NAME_TITLE + TEXT_TYPE + NOT_NULL  + COMMA_SEP +
                    TableDefnDB.ReligionTableDfn.COLUMN_NAME_VENUE + TEXT_TYPE + NOT_NULL  + COMMA_SEP +
                    TableDefnDB.ReligionTableDfn.COLUMN_NAME_CATEGORY + TEXT_TYPE + NOT_NULL  + COMMA_SEP +
                    TableDefnDB.ReligionTableDfn.COLUMN_NAME_ORGANIZER + TEXT_TYPE + NOT_NULL  + COMMA_SEP +
                    TableDefnDB.ReligionTableDfn.COLUMN_NAME_TIME + TEXT_TYPE + NOT_NULL  + COMMA_SEP +
                    TableDefnDB.ReligionTableDfn.COLUMN_NAME_IMAGEURL + TEXT_TYPE + NOT_NULL  + COMMA_SEP +
                    TableDefnDB.ReligionTableDfn.COLUMN_NAME_BODY  + TEXT_TYPE + NOT_NULL + " UNIQUE" +
                    // Any other options for the CREATE command
                    " )";

    private static final String SQL_CREATE_TABLE_Academics =
            "CREATE TABLE " + TableDefnDB.AcademicsTableDfn.TABLE_NAME + " (" +
                    TableDefnDB.AcademicsTableDfn._ID + " INTEGER PRIMARY KEY," +
                    TableDefnDB.AcademicsTableDfn.COLUMN_NAME_DATE + TEXT_TYPE + NOT_NULL  + COMMA_SEP +
                    TableDefnDB.AcademicsTableDfn.COLUMN_NAME_TITLE + TEXT_TYPE + NOT_NULL  + COMMA_SEP +
                    TableDefnDB.AcademicsTableDfn.COLUMN_NAME_VENUE + TEXT_TYPE + NOT_NULL  + COMMA_SEP +
                    TableDefnDB.AcademicsTableDfn.COLUMN_NAME_CATEGORY + TEXT_TYPE + NOT_NULL  + COMMA_SEP +
                    TableDefnDB.AcademicsTableDfn.COLUMN_NAME_ORGANIZER + TEXT_TYPE + NOT_NULL  + COMMA_SEP +
                    TableDefnDB.AcademicsTableDfn.COLUMN_NAME_TIME + TEXT_TYPE + NOT_NULL  + COMMA_SEP +
                    TableDefnDB.AcademicsTableDfn.COLUMN_NAME_IMAGEURL + TEXT_TYPE + NOT_NULL   + COMMA_SEP +
                    TableDefnDB.AcademicsTableDfn.COLUMN_NAME_BODY  + TEXT_TYPE + NOT_NULL + " UNIQUE" +
                    // Any other options for the CREATE command
                    " )";


    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "CampusEvent.db";

    public CampusEventDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_DEPT);
        db.execSQL(SQL_CREATE_TABLE_SOCIAL);
        db.execSQL(SQL_CREATE_TABLE_RELIGION);
        db.execSQL(SQL_CREATE_TABLE_Academics);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
