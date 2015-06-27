package campusevents.michael.android.com.campusevents;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by User Pc on 6/24/2015.
 */
public class SyncData  extends IntentService{

    public SyncData() {
        super("sync data");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) { // check if network is available
            // fetch data
            try {
                downloadUrl();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void downloadUrl() throws IOException  {
        try {
            URL url = new URL("http://petermichael908.byethost18.com/index.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(30000); // 30 secs is time out
            conn.setReadTimeout(35000);// 35 secs is read time out
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Connect online
            conn.connect();
            InputStream is = conn.getInputStream();
            readIt(is, 30000);
        }
        catch (MalformedURLException e) {

        }

    }

    private void readIt(InputStream is, int i)throws IOException, UnsupportedEncodingException {
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
        ArrayList<String> venueList = new ArrayList<>();
        ArrayList<String> titleList = new ArrayList<>();
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

    }
}
