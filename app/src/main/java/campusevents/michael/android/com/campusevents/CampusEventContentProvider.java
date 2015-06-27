package campusevents.michael.android.com.campusevents;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

/**
 * Created by User Pc on 6/12/2015.
 */
public class CampusEventContentProvider extends ContentProvider {
    // Creates a UriMatcher object.
    private UriMatcher dUriMatcher;
    private UriMatcher soUriMatcher;
    private UriMatcher relUriMatcher;
    private UriMatcher acaUriMatcher;
    ContentValues values;
    CampusEventDB campusEventDB;
    Uri uri;
    Intent intent;
    PendingIntent pendingIntent;
    String[] projection;

    @Override
    public boolean onCreate() {
        values = new ContentValues();
        campusEventDB = new CampusEventDB(getContext());
        dUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        dUriMatcher.addURI("campusevents.michael.android.com.campusevents.provider", "department", 2);
        relUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        relUriMatcher.addURI("campusevents.michael.android.com.campusevents.provider", "religion", 3);
        soUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        soUriMatcher.addURI("campusevents.michael.android.com.campusevents.provider", "social", 1);
        acaUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        acaUriMatcher.addURI("campusevents.michael.android.com.campusevents.provider", "academics", 4);
        intent = new Intent(getContext(), MainActivity.class);

        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        this.uri = uri;
        this.projection = projection;
        if (soUriMatcher.match(uri) == 1) {
            CampusEventDB simpleDb = new CampusEventDB(getContext());
            SQLiteDatabase db = simpleDb.getReadableDatabase();
            Cursor cursor = db.query(true, TableDefnDB.SocialTableDfn.TABLE_NAME, projection, null, null, null, null, null, null);
            return cursor;
        } else if (dUriMatcher.match(uri) == 2) {
            CampusEventDB simpleDb = new CampusEventDB(getContext());
            SQLiteDatabase db = simpleDb.getReadableDatabase();
            Cursor cursor = db.query(true, TableDefnDB.DeptTableDfn.TABLE_NAME, projection, null, null, null, null, null, null);
            return cursor;
        } else if (relUriMatcher.match(uri) == 3) {
            CampusEventDB simpleDb = new CampusEventDB(getContext());
            SQLiteDatabase db = simpleDb.getReadableDatabase();
            Cursor cursor = db.query(true, TableDefnDB.ReligionTableDfn.TABLE_NAME, projection, null, null, null, null, null, null);
            return cursor;
        } else if (acaUriMatcher.match(uri) == 4) {
            CampusEventDB simpleDb = new CampusEventDB(getContext());
            SQLiteDatabase db = simpleDb.getReadableDatabase();
            Cursor cursor = db.query(true, TableDefnDB.AcademicsTableDfn.TABLE_NAME, projection, null, null, null, null, null, null);
            return cursor;
        } else {
            return null;
        }
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public synchronized Uri insert(Uri uri, ContentValues values) {
        //checks if uri mathces any of the table
        this.uri = uri;

        if (dUriMatcher.match(uri) == 2) {
            this.values = values;
            SQLiteDatabase db = campusEventDB.getWritableDatabase();
            Long newRow = db.insert(TableDefnDB.DeptTableDfn.TABLE_NAME, null, values);
            if (newRow != -1) {
                NotificationManager manager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
                Notification notification = new NotificationCompat.Builder(getContext())
                        .setContentTitle("New Event")
                        .setContentText("New Event Arrived ")
                        .setTicker("Campus Events")
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setDefaults(Notification.DEFAULT_SOUND)
                        .setContentIntent(PendingIntent.getActivity(getContext(),0,intent,0))
                        .setAutoCancel(true)
                        .build();
                manager.notify(1, notification);// to be modified later to show notification for specific action
            }
            return (Uri.withAppendedPath(Uri.parse(ContractProvider.URI_CONTENT + "/" + TableDefnDB.DeptTableDfn.TABLE_NAME), newRow.toString()));
        } else if (soUriMatcher.match(uri) == 1) {
            this.values = values;
            SQLiteDatabase db = campusEventDB.getWritableDatabase();
            Long newRow = db.insert(TableDefnDB.SocialTableDfn.TABLE_NAME, null, values);
            if (newRow != -1) {
                NotificationManager manager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
                Notification notification = new NotificationCompat.Builder(getContext())
                        .setContentTitle("New Event")
                        .setContentText("New Event Arrived ")
                        .setTicker("Campus Events")
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setDefaults(Notification.DEFAULT_SOUND)
                        .setContentIntent(PendingIntent.getActivity(getContext(),0,intent,0))
                        .setAutoCancel(true)
                        .build();
                manager.notify(1, notification);// to be modified later to show notification for specific action
            }
                return (Uri.withAppendedPath(Uri.parse(ContractProvider.URI_CONTENT + "/" + TableDefnDB.SocialTableDfn.TABLE_NAME), newRow.toString()));

        } else if (relUriMatcher.match(uri) == 3) {
            this.values = values;
            SQLiteDatabase db = campusEventDB.getWritableDatabase();
            Long newRow = db.insert(TableDefnDB.ReligionTableDfn.TABLE_NAME, null, values);
            if (newRow != -1) {
                NotificationManager manager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
                Notification notification = new NotificationCompat.Builder(getContext())
                        .setContentTitle("New Event")
                        .setContentText("New Event Arrived ")
                        .setTicker("Campus Events")
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setDefaults(Notification.DEFAULT_SOUND)
                        .setContentIntent(PendingIntent.getActivity(getContext(),0,intent,0))
                        .setAutoCancel(true)
                        .build();
                manager.notify(1, notification);// to be modified later to show notification for specific action
            }
                return (Uri.withAppendedPath(Uri.parse(ContractProvider.URI_CONTENT + "/" + TableDefnDB.ReligionTableDfn.TABLE_NAME), newRow.toString()));

        }
        else if (acaUriMatcher.match(uri) == 4) {
            this.values = values;
            SQLiteDatabase db = campusEventDB.getWritableDatabase();
            Long newRow = db.insert(TableDefnDB.AcademicsTableDfn.TABLE_NAME, null, values);
            if (newRow != -1) {
                NotificationManager manager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
                Notification notification = new NotificationCompat.Builder(getContext())
                        .setContentTitle("New Event")
                        .setContentText("New Event Arrived ")
                        .setTicker("Campus Events")
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setDefaults(Notification.DEFAULT_SOUND)
                        .setContentIntent(PendingIntent.getActivity(getContext(),0,intent,0))
                        .setAutoCancel(true)
                        .build();
                manager.notify(1, notification);// to be modified later to show notification for specific action
            }
                return (Uri.withAppendedPath(Uri.parse(ContractProvider.URI_CONTENT + "/" + TableDefnDB.AcademicsTableDfn.TABLE_NAME), newRow.toString()));

        }
        else {
            return null;
        }
    }




    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
