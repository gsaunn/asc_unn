package campusevents.michael.android.com.campusevents;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.common.collect.Range;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by User Pc on 6/20/2015.
 */

public class PostEvent extends Activity {
    private EditText post_title;
    private EditText post_venue;
    private EditText post_organizer;
    private EditText post_body;
    private EditText post_category;
    private EditText post_date;
    private EditText post_time;
    private TextView pick_image_text;
    private ImageView post_image;
    private Button post_btn;
    AwesomeValidation mAwesomeValidation;
    private String imgPath;
    private String fileName;
    RequestParams params = new RequestParams();
    int item;
    private DatePickerDialog fromPickerDialog;
    private SimpleDateFormat dateFormat;
    private ProgressDialog prgDialog;
    private Bitmap bitmap;
    private String encodedString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_event);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#54873234")));
        getActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));// hides icon
        post_title = (EditText) findViewById(R.id.post_event_title);
        post_venue = (EditText) findViewById(R.id.post_event_venue);
        post_organizer = (EditText) findViewById(R.id.post_event_organizer);
        post_body = (EditText) findViewById(R.id.post_event_body);
        post_category = (EditText) findViewById(R.id.post_event_category);
        post_date = (EditText) findViewById(R.id.post_event_date);
        post_time = (EditText) findViewById(R.id.post_event_time);
        pick_image_text = (TextView)findViewById(R.id.post_event_pick_image);
        post_image = (ImageView) findViewById(R.id.image_plaeholder_post_event);
        post_btn = (Button)findViewById(R.id.post_event_btn);
        dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        prgDialog = new ProgressDialog(this);
        // Set Cancelable as False
        prgDialog.setCancelable(false);
        mAwesomeValidation = new AwesomeValidation(ValidationStyle.COLORATION);
        addValidation(PostEvent.this);
        post_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAwesomeValidation.validate()){
                   // form  validated
                    uploadImage(post_btn);
                };
            }
        });
        pick_image_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //select a picture from gallery
                loadImagefromGallery(pick_image_text);
            }
        });
        post_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(PostEvent.this);
                builder.setTitle("Select a Category");
                builder.setSingleChoiceItems(new String[]{"Social", "Department", "Religion","Academics"},-1,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      item = which;
                    }
                });
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (item){
                            case 0: post_category.setText("Social");
                                break;
                            case 1: post_category.setText("Department");
                                break;
                            case 2: post_category.setText("Religion");
                                break;
                            case 3: post_category.setText("Academics");
                                break;

                        }

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        post_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                fromPickerDialog = new DatePickerDialog(PostEvent.this,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year,monthOfYear,dayOfMonth);
                        post_date.setText(dateFormat.format(newDate.getTime()));
                    }
                }, calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                fromPickerDialog.show();
                 }
        });
        post_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(PostEvent.this,new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                       post_time.setText(new StringBuilder().append(pad(hourOfDay)).append(":")
                                                            .append(pad(minute)));
                    }

                    private String pad(int i) {
                        if (i >= 10)
                            return String.valueOf(i);
                        else
                            return "0" +String.valueOf(i);

                    }
                },Calendar.getInstance().get(Calendar.HOUR_OF_DAY),Calendar.getInstance().get(Calendar.MINUTE),false);
                timePickerDialog.show();
            }
        });
    }

    private void loadImagefromGallery(View pick_image_text) {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, 1);
    }

    private void addValidation(final PostEvent activity) {
        mAwesomeValidation.addValidation(activity, R.id.post_event_title,   "[^|$]+", R.string.validation_title);
        mAwesomeValidation.addValidation(activity, R.id.post_event_venue, "[^|$]+", R.string.validation_venue);
        mAwesomeValidation.addValidation(activity, R.id.post_event_organizer,  "[^|$]+", R.string.validation_organizer);
        mAwesomeValidation.addValidation(activity, R.id.post_event_body,  "[^|$]+", R.string.validation_body);
        mAwesomeValidation.addValidation(activity, R.id.post_event_category, "[^|$]+", R.string.validation_category);
        mAwesomeValidation.addValidation(activity, R.id.post_event_time, "[^|$]+", R.string.validation_time);
        mAwesomeValidation.addValidation(activity, R.id.post_event_date, "[^|$]+", R.string.validation_date);


    }

    // When Upload button is clicked
    public void uploadImage(View v) {
        // When Image is selected from Gallery
        if (imgPath != null && !imgPath.isEmpty()) {
            prgDialog.setMessage("Validating image...");
            prgDialog.show();
            // Convert image to String using Base64
            encodeImagetoString();
            // When Image is not selected from Gallery
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    "You must select image from gallery before you try to upload",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void encodeImagetoString() {
        new AsyncTask<Void, Void, String>() {

            protected void onPreExecute() {

            };

            @Override
            protected String doInBackground(Void... params) {
                BitmapFactory.Options options = null;
                options = new BitmapFactory.Options();
                options.inSampleSize = 3;
                bitmap = BitmapFactory.decodeFile(imgPath,
                        options);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // Must compress the Image to reduce image size to make upload easy
                bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
                byte[] byte_arr = stream.toByteArray();
                // Encode Image to String
                encodedString = Base64.encodeToString(byte_arr, 0);

                return "";
            }

            @Override
            protected void onPostExecute(String msg) {
                prgDialog.setMessage("Processing data...");
                // Put converted Image string into Async Http Post param plus all parameters collected from form
                params.put("posttitle", post_title.getText().toString());
                params.put("postvenue",post_venue.getText().toString());
                params.put("postorganizer",post_organizer.getText().toString());
                params.put("postbody",post_body.getText().toString());
                params.put("postcategory", new String(post_category.getText().toString()).toLowerCase());
                params.put("postdate", post_date.getText().toString());
                params.put("posttime",post_time.getText().toString());
                params.put("image", encodedString);
                // Trigger Image upload
                triggerFormUpload();
            }
        }.execute(null, null, null);

    }

    private void triggerFormUpload() {
        makeHTTPCall();
    }

    private void makeHTTPCall() {
        prgDialog.setMessage("Uploading Event...");
        AsyncHttpClient client = new AsyncHttpClient();
        // Don't forget to change the IP address to your LAN address. Port no as well.
        client.post("http://petermichael908.byethost18.com/res/main.php",
                params, new AsyncHttpResponseHandler() {
                    // When the response returned by REST has Http
                    // response code '200'
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        // Hide Progress Dialog
                        String category = post_category.getText().toString();
                        if(category.contains("Social"))
                        {
                            Uri uri = Uri.parse(ContractProvider.URI_CONTENT + "/" + TableDefnDB.SocialTableDfn.TABLE_NAME);
                            ContentValues values = new ContentValues();
                            values.put(TableDefnDB.SocialTableDfn.COLUMN_NAME_DATE, post_date.getText().toString());
                            values.put(TableDefnDB.SocialTableDfn.COLUMN_NAME_TITLE, post_title.getText().toString());
                            values.put(TableDefnDB.SocialTableDfn.COLUMN_NAME_VENUE, post_venue.getText().toString());
                            values.put(TableDefnDB.SocialTableDfn.COLUMN_NAME_BODY, post_body.getText().toString());
                            values.put(TableDefnDB.SocialTableDfn.COLUMN_NAME_CATEGORY, post_category.getText().toString());
                            values.put(TableDefnDB.SocialTableDfn.COLUMN_NAME_ORGANIZER, post_organizer.getText().toString());
                            values.put(TableDefnDB.SocialTableDfn.COLUMN_NAME_TIME, post_time.getText().toString());
                            values.put(TableDefnDB.SocialTableDfn.COLUMN_NAME_IMAGEURL, "http://petermichael908.byethost18.com/res/uploadedimg/"+fileName);
                            getContentResolver().insert(uri,values);

                        }
                        if(category.contains("Department"))
                        {
                            Uri uri = Uri.parse(ContractProvider.URI_CONTENT + "/" + TableDefnDB.DeptTableDfn.TABLE_NAME);
                            ContentValues values = new ContentValues();
                            values.put(TableDefnDB.DeptTableDfn.COLUMN_NAME_DATE, post_date.getText().toString());
                            values.put(TableDefnDB.DeptTableDfn.COLUMN_NAME_TITLE, post_title.getText().toString());
                            values.put(TableDefnDB.DeptTableDfn.COLUMN_NAME_VENUE, post_venue.getText().toString());
                            values.put(TableDefnDB.DeptTableDfn.COLUMN_NAME_BODY, post_body.getText().toString());
                            values.put(TableDefnDB.DeptTableDfn.COLUMN_NAME_CATEGORY, post_category.getText().toString());
                            values.put(TableDefnDB.DeptTableDfn.COLUMN_NAME_ORGANIZER, post_organizer.getText().toString());
                            values.put(TableDefnDB.DeptTableDfn.COLUMN_NAME_TIME, post_time.getText().toString());
                            values.put(TableDefnDB.DeptTableDfn.COLUMN_NAME_IMAGEURL, "http://petermichael908.byethost18.com/res/uploadedimg/"+fileName);
                            getContentResolver().insert(uri,values);
                        }
                        if(category.contains("Academics"))
                        {
                            Uri uri = Uri.parse(ContractProvider.URI_CONTENT + "/" + TableDefnDB.AcademicsTableDfn.TABLE_NAME);
                            ContentValues values = new ContentValues();
                            values.put(TableDefnDB.AcademicsTableDfn.COLUMN_NAME_DATE, post_date.getText().toString());
                            values.put(TableDefnDB.AcademicsTableDfn.COLUMN_NAME_TITLE, post_title.getText().toString());
                            values.put(TableDefnDB.AcademicsTableDfn.COLUMN_NAME_VENUE, post_venue.getText().toString());
                            values.put(TableDefnDB.AcademicsTableDfn.COLUMN_NAME_BODY, post_body.getText().toString());
                            values.put(TableDefnDB.AcademicsTableDfn.COLUMN_NAME_CATEGORY, post_category.getText().toString());
                            values.put(TableDefnDB.AcademicsTableDfn.COLUMN_NAME_ORGANIZER, post_organizer.getText().toString());
                            values.put(TableDefnDB.AcademicsTableDfn.COLUMN_NAME_TIME, post_time.getText().toString());
                            values.put(TableDefnDB.AcademicsTableDfn.COLUMN_NAME_IMAGEURL, "http://petermichael908.byethost18.com/res/uploadedimg/"+fileName);
                            getContentResolver().insert(uri,values);
                        }
                        if(category.contains("Religion"))
                        {
                            Uri uri = Uri.parse(ContractProvider.URI_CONTENT + "/" + TableDefnDB.ReligionTableDfn.TABLE_NAME);
                            ContentValues values = new ContentValues();
                            values.put(TableDefnDB.ReligionTableDfn.COLUMN_NAME_DATE, post_date.getText().toString());
                            values.put(TableDefnDB.ReligionTableDfn.COLUMN_NAME_TITLE, post_title.getText().toString());
                            values.put(TableDefnDB.ReligionTableDfn.COLUMN_NAME_VENUE, post_venue.getText().toString());
                            values.put(TableDefnDB.ReligionTableDfn.COLUMN_NAME_BODY, post_body.getText().toString());
                            values.put(TableDefnDB.ReligionTableDfn.COLUMN_NAME_CATEGORY, post_category.getText().toString());
                            values.put(TableDefnDB.ReligionTableDfn.COLUMN_NAME_ORGANIZER, post_organizer.getText().toString());
                            values.put(TableDefnDB.ReligionTableDfn.COLUMN_NAME_TIME, post_time.getText().toString());
                            values.put(TableDefnDB.ReligionTableDfn.COLUMN_NAME_IMAGEURL, "http://petermichael908.byethost18.com/res/uploadedimg/"+fileName);
                            getContentResolver().insert(uri,values);
                        }
                        prgDialog.hide();
                        Toast.makeText(getApplicationContext(), "Successfully Uploaded",
                                Toast.LENGTH_LONG).show();
                    }
                    // When the response returned by REST has Http
                    // response code other than '200' such as '404',
                    // '500' or '403' etc

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        // Hide Progress Dialog
                        prgDialog.hide();
                        // When Http response code is '404'
                        if (statusCode == 404) {
                            Toast.makeText(getApplicationContext(),
                                    "Check Internet Connectivity",
                                    Toast.LENGTH_LONG).show();
                        }
                        // When Http response code is '500'
                        else if (statusCode == 500) {
                            Toast.makeText(getApplicationContext(),
                                    "Check Internet Connectivity",
                                    Toast.LENGTH_LONG).show();
                        }
                        // When Http response code other than 404, 500
                        else {
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Check Internet Connectivity "
                                            , Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == 1 && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgPath = cursor.getString(columnIndex);
                cursor.close();
                // Set the Image in ImageView

                post_image.setImageBitmap(BitmapFactory
                        .decodeFile(imgPath));
                post_image.setVisibility(View.VISIBLE);
                //select another image
                post_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadImagefromGallery(post_image);
                    }
                });
                pick_image_text.setVisibility(View.GONE);
                // Get the Image's file name
                String fileNameSegments[] = imgPath.split("/");
                fileName = fileNameSegments[fileNameSegments.length - 1];
                // Put file name in Async Http Post Param which will used in Php web app
                params.put("filename", fileName);


            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem item = menu.add("About the Developer");
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                DialogFragment mDialog = MyDialog.newInstance();
                mDialog.show(getFragmentManager(), "Alert");
                return  true;
        }


    }

}
