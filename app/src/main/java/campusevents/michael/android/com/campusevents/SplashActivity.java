package campusevents.michael.android.com.campusevents;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by User Pc on 6/16/2015.
 */
public class SplashActivity extends Activity {
    ProgressBar pb;
    Button retry;
    Activity activity;
    Cursor cursor;
    SQLiteDatabase db;
    TextView splash_text;
    TextView no_network;
    private final String  USER = "newUser";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set window to full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash);
        splash_text = (TextView) findViewById(R.id.splash_text);
        no_network = (TextView) findViewById(R.id.no_network);
        retry = (Button) findViewById(R.id.retry);
        pb = (ProgressBar) findViewById(R.id.progress);
        //check if app is newly installed
        final SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        if (prefs.getInt(USER, 0) == 0) {
            // download data from online database into app database because user is new.
            ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) { // check if network is available
                // fetch data
                new DownloadDataTask().execute(new int[]{1});
            }
            else {
                //Network not available
                splash_text.setVisibility(View.GONE);
                // display retry button
                pb.setVisibility(View.GONE);
                retry.setVisibility(View.VISIBLE);
                no_network.setVisibility(View.VISIBLE);
                retry.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                        if (networkInfo != null && networkInfo.isConnected()) { // check if network is available
                            // fetch data
                            splash_text.setVisibility(View.VISIBLE);
                            pb.setVisibility(View.VISIBLE);
                            retry.setVisibility(View.GONE);
                            no_network.setVisibility(View.GONE);
                            new DownloadDataTask().execute(new int[]{1});
                        }
                        else{
                            splash_text.setVisibility(View.GONE);
                            // display retry button
                            pb.setVisibility(View.GONE);
                            retry.setVisibility(View.VISIBLE);
                            no_network.setVisibility(View.VISIBLE);
                            Toast.makeText(SplashActivity.this, "No Network",Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        }
        else if (prefs.getInt("newUser", 1) == 1) {
            //user is already existing
            splash_text.setText("Loading data...");
            startActivity(new Intent(this,MainActivity.class));
        }
    }
    private class DownloadDataTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {
            try {
                downloadUrl();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return null; // this is returned to onPostExecute
        }

        private void downloadUrl() throws IOException {
            try {
                URL url = new URL("http://petermichael908.byethost18.com/index.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(300000); // 300 secs is time out
                conn.setReadTimeout(500000);// 500 secs is read time out
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Connect online
                conn.connect();
                InputStream is = conn.getInputStream();
                readIt(is, 50000);
            }
            catch (MalformedURLException e) {
                // Unable to connect. Poor network
               SplashActivity.this.runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       splash_text.setVisibility(View.GONE);
                       // display retry button
                       pb.setVisibility(View.GONE);
                       retry.setVisibility(View.VISIBLE);
                       retry.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                               NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                               if (networkInfo != null && networkInfo.isConnected()) { // check if network is available
                                   // fetch data
                                   splash_text.setVisibility(View.VISIBLE);
                                   pb.setVisibility(View.VISIBLE);
                                   retry.setVisibility(View.GONE);
                                   no_network.setVisibility(View.GONE);
                                   new DownloadDataTask().execute(new int[]{1});
                               }
                               else {
                                   splash_text.setVisibility(View.GONE);
                                   // display retry button
                                   pb.setVisibility(View.GONE);
                                   retry.setVisibility(View.VISIBLE);
                               }

                           }
                       });
                       no_network.setVisibility(View.GONE);
                       Toast.makeText(SplashActivity.this, "Could not complete setup",Toast.LENGTH_LONG).show();
                   }
               });
            }
        }

        private void readIt(InputStream is, int i) throws IOException, UnsupportedEncodingException {
            Reader reader = null;
            reader = new InputStreamReader(is, "UTF-8");
            char[] buffer = new char[i];
            reader.read(buffer);
            String parsedString = new String(buffer);
            // after fetching raw html - parses it.
            ArrayList<Object> deptList = new ArrayList<Object>();
            ArrayList<Object> socialList = new ArrayList<Object>();
            ArrayList<Object> acaList = new ArrayList<Object>();
            ArrayList<Object> relList = new ArrayList<Object>();
            deptList = Parser.parseDept(parsedString);
            socialList = Parser.parseSocial(parsedString);
            acaList = Parser.parseAcademics(parsedString);
            relList = Parser.parseReligion(parsedString);
            ContentResolver contentResolver = getContentResolver();
            ArrayList<String> dateList = new ArrayList<>();
            ArrayList<String> titleList = new ArrayList<>();
            ArrayList<String> venueList = new ArrayList<>();
            ArrayList<String> bodyList = new ArrayList<>();
            ArrayList<String> categoryList = new ArrayList<>();
            ArrayList<String> organizerList = new ArrayList<>();
            ArrayList<String> timeList = new ArrayList<>();
            ArrayList<String> imageurlList = new ArrayList<>();
            dateList = (ArrayList<String>) deptList.get(0);
            titleList = (ArrayList<String>) deptList.get(1);
            venueList = (ArrayList<String>) deptList.get(2);
            bodyList = (ArrayList<String>) deptList.get(3);
            categoryList = (ArrayList<String>) deptList.get(4);
            organizerList = (ArrayList<String>) deptList.get(5);
            timeList = (ArrayList<String>) deptList.get(6);
            imageurlList = (ArrayList<String>) deptList.get(7);
            ContentValues values = new ContentValues();
            Uri depturi = Uri.parse(ContractProvider.URI_CONTENT + "/" + TableDefnDB.DeptTableDfn.TABLE_NAME);
            Uri socuri = Uri.parse(ContractProvider.URI_CONTENT + "/" + TableDefnDB.SocialTableDfn.TABLE_NAME);
            Uri acauri = Uri.parse(ContractProvider.URI_CONTENT + "/" + TableDefnDB.AcademicsTableDfn.TABLE_NAME);
            Uri reluri = Uri.parse(ContractProvider.URI_CONTENT + "/" + TableDefnDB.ReligionTableDfn.TABLE_NAME);
            for (int m = 0; m < dateList.size(); m++) {
                values.put(TableDefnDB.DeptTableDfn.COLUMN_NAME_DATE, dateList.get(m));
                values.put(TableDefnDB.DeptTableDfn.COLUMN_NAME_TITLE, titleList.get(m));
                values.put(TableDefnDB.DeptTableDfn.COLUMN_NAME_VENUE, venueList.get(m));
                values.put(TableDefnDB.DeptTableDfn.COLUMN_NAME_BODY, bodyList.get(m));
                values.put(TableDefnDB.DeptTableDfn.COLUMN_NAME_CATEGORY, categoryList.get(m));
                values.put(TableDefnDB.DeptTableDfn.COLUMN_NAME_ORGANIZER, organizerList.get(m));
                values.put(TableDefnDB.DeptTableDfn.COLUMN_NAME_TIME, timeList.get(m));
                values.put(TableDefnDB.DeptTableDfn.COLUMN_NAME_IMAGEURL, imageurlList.get(m));
                contentResolver.insert(depturi, values);
            }

            dateList = (ArrayList<String>) socialList.get(0);
            titleList = (ArrayList<String>) socialList.get(1);
            venueList = (ArrayList<String>) socialList.get(2);
            bodyList = (ArrayList<String>) socialList.get(3);
            categoryList = (ArrayList<String>) socialList.get(4);
            organizerList = (ArrayList<String>) socialList.get(5);
            timeList = (ArrayList<String>) socialList.get(6);
            imageurlList = (ArrayList<String>) socialList.get(7);
            for (int m = 0; m < dateList.size(); m++) {
                values.put(TableDefnDB.SocialTableDfn.COLUMN_NAME_DATE, dateList.get(m));
                values.put(TableDefnDB.SocialTableDfn.COLUMN_NAME_TITLE, titleList.get(m));
                values.put(TableDefnDB.SocialTableDfn.COLUMN_NAME_VENUE, venueList.get(m));
                values.put(TableDefnDB.SocialTableDfn.COLUMN_NAME_BODY, bodyList.get(m));
                values.put(TableDefnDB.SocialTableDfn.COLUMN_NAME_CATEGORY, categoryList.get(m));
                values.put(TableDefnDB.SocialTableDfn.COLUMN_NAME_ORGANIZER, organizerList.get(m));
                values.put(TableDefnDB.SocialTableDfn.COLUMN_NAME_TIME, timeList.get(m));
                values.put(TableDefnDB.SocialTableDfn.COLUMN_NAME_IMAGEURL, imageurlList.get(m));
                contentResolver.insert(socuri, values);
            }
            dateList = (ArrayList<String>) acaList.get(0);
            titleList = (ArrayList<String>) acaList.get(1);
            venueList = (ArrayList<String>) acaList.get(2);
            bodyList = (ArrayList<String>) acaList.get(3);
            categoryList = (ArrayList<String>) acaList.get(4);
            organizerList = (ArrayList<String>) acaList.get(5);
            timeList = (ArrayList<String>) acaList.get(6);
            imageurlList = (ArrayList<String>) acaList.get(7);
            for (int m = 0; m < dateList.size(); m++) {
                values.put(TableDefnDB.AcademicsTableDfn.COLUMN_NAME_DATE, dateList.get(m));
                values.put(TableDefnDB.AcademicsTableDfn.COLUMN_NAME_TITLE, titleList.get(m));
                values.put(TableDefnDB.AcademicsTableDfn.COLUMN_NAME_VENUE, venueList.get(m));
                values.put(TableDefnDB.AcademicsTableDfn.COLUMN_NAME_BODY, bodyList.get(m));
                values.put(TableDefnDB.AcademicsTableDfn.COLUMN_NAME_CATEGORY, categoryList.get(m));
                values.put(TableDefnDB.AcademicsTableDfn.COLUMN_NAME_ORGANIZER, organizerList.get(m));
                values.put(TableDefnDB.AcademicsTableDfn.COLUMN_NAME_TIME, timeList.get(m));
                values.put(TableDefnDB.AcademicsTableDfn.COLUMN_NAME_IMAGEURL, imageurlList.get(m));
                contentResolver.insert(acauri, values);
            }
            dateList = (ArrayList<String>) relList.get(0);
            titleList = (ArrayList<String>) relList.get(1);
            venueList = (ArrayList<String>) relList.get(2);
            bodyList = (ArrayList<String>) relList.get(3);
            categoryList = (ArrayList<String>) relList.get(4);
            organizerList = (ArrayList<String>) relList.get(5);
            timeList = (ArrayList<String>) relList.get(6);
            imageurlList = (ArrayList<String>) relList.get(7);
            for (int m = 0; m < dateList.size(); m++) {
                values.put(TableDefnDB.ReligionTableDfn.COLUMN_NAME_DATE, dateList.get(m));
                values.put(TableDefnDB.ReligionTableDfn.COLUMN_NAME_TITLE, titleList.get(m));
                values.put(TableDefnDB.ReligionTableDfn.COLUMN_NAME_VENUE, venueList.get(m));
                values.put(TableDefnDB.ReligionTableDfn.COLUMN_NAME_BODY, bodyList.get(m));
                values.put(TableDefnDB.ReligionTableDfn.COLUMN_NAME_CATEGORY, categoryList.get(m));
                values.put(TableDefnDB.ReligionTableDfn.COLUMN_NAME_ORGANIZER, organizerList.get(m));
                values.put(TableDefnDB.ReligionTableDfn.COLUMN_NAME_TIME, timeList.get(m));
                values.put(TableDefnDB.ReligionTableDfn.COLUMN_NAME_IMAGEURL, imageurlList.get(m));
                contentResolver.insert(reluri, values);
            }
            // at this point data was successful loaded. create new user.
            final SharedPreferences prefs = getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt(USER, 1);
            editor.commit();
            //start alarm manager
            AlarmManager alarmManager = (AlarmManager) SplashActivity.this.getSystemService(Context.ALARM_SERVICE);
            Intent alarmIntent = new Intent(SplashActivity.this,SyncData.class);
            PendingIntent pendingIntent = PendingIntent.getService(SplashActivity.this,0,alarmIntent,0);
            //repeat every 30 secs
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(),20 * 1000,pendingIntent);
            // start main activity from splash activity thread
            SplashActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    SplashActivity.this.startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }
            });


        }

    }
}
