package campusevents.michael.android.com.campusevents;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by User Pc on 6/26/2015.
 */
public class MoreInfoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if(intent != null) {
            setContentView(R.layout.more_info_layout);
            TextView body_text_view = (TextView) findViewById(R.id.more_info_body);
            String body = intent.getStringExtra("body");
            String organizer = intent.getStringExtra("organizer");
            body_text_view.setText(body);
            ActionBar actionBar = getActionBar();
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#54873234")));

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
        DialogFragment mDialog = MyDialog.newInstance();
        mDialog.show(getFragmentManager(), "Alert");
        return  true;
    }


}
