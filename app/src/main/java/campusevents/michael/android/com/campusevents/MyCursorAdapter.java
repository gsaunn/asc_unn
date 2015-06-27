package campusevents.michael.android.com.campusevents;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;

/**
 * Created by User Pc on 6/18/2015.
 */
public class MyCursorAdapter extends CursorAdapter {
    private final LayoutInflater inflater;
    private  Cursor cursor;
    private Context context;
    int layout;
    Activity activity;
    private int position;
    private ListView listView;
    View list_item;
    TextView textView;
    TextView venue;
    LayoutInflater layoutInflater;

    public MyCursorAdapter(Context context, int layout, Cursor cursor, String[] from, int[] to,Activity activity, int position,ListView listView, int flags) {
        super(context,  cursor);
        this.layout = layout;
        this.cursor = cursor;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.activity = activity;
        this.position = position;
        this.listView = listView;
        layoutInflater = activity.getWindow().getLayoutInflater();
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return inflater.inflate(R.layout.list_view_item_layout,null);
    }


    /*Each row returned, the column values are set here*/

    @Override
    public void bindView(View view, Context context, final Cursor cursor) {
        TextView date = (TextView) view.findViewById(R.id.date);
        date.setText("Date: "+cursor.getString(cursor.getColumnIndex(TableDefnDB.DeptTableDfn.COLUMN_NAME_DATE)) + "  Time:  " + cursor.getString(cursor.getColumnIndex((TableDefnDB.DeptTableDfn.COLUMN_NAME_TIME)))); //concat date and time
        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(cursor.getString(cursor.getColumnIndex(TableDefnDB.DeptTableDfn.COLUMN_NAME_TITLE)));
        venue = (TextView) view.findViewById(R.id.venue);
        venue.setText(cursor.getString(cursor.getColumnIndex(TableDefnDB.DeptTableDfn.COLUMN_NAME_VENUE)));
        final TextView body = (TextView) view.findViewById(R.id.body);
        body.setText(cursor.getString(cursor.getColumnIndex(TableDefnDB.DeptTableDfn.COLUMN_NAME_BODY)));
        body.setTag(cursor.getString(cursor.getColumnIndex(TableDefnDB.DeptTableDfn.COLUMN_NAME_BODY)));
        final TextView organizer = (TextView) view.findViewById(R.id.organizer);
        organizer.setText(cursor.getString(cursor.getColumnIndex(TableDefnDB.DeptTableDfn.COLUMN_NAME_ORGANIZER)));
        organizer.setTag(cursor.getString(cursor.getColumnIndex(TableDefnDB.DeptTableDfn.COLUMN_NAME_ORGANIZER)));
        final LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.item_click_handle);
        linearLayout.setTag(cursor.getPosition() + 1);
        ImageView img = (ImageView) view.findViewById(R.id.imageView);
        if (img == null) {
            img = new ImageView(activity);
        }
        Picasso.with(context).load(cursor.getString(cursor.getColumnIndex(TableDefnDB.DeptTableDfn.COLUMN_NAME_IMAGEURL))).placeholder(R.drawable.ic_placeholder_03).fit().into(img);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity,MoreInfoActivity.class);
                intent.putExtra("body", body.getTag().toString());
                intent.putExtra("organizer", organizer.getTag().toString());
                activity.startActivity(intent);


            }
        });
    }


}
