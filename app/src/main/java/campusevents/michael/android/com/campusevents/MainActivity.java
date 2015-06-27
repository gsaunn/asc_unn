package campusevents.michael.android.com.campusevents;

import android.app.ActionBar;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends FragmentActivity {
        MyAdapter adapter;
        ViewPager pager;
        PagerSlidingTabStrip tabs;
        ActionBar actionBar;
        public Intent intent;
        public static Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_pager);
        adapter = new MyAdapter(getSupportFragmentManager());
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(pager);
        actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#54873234")));

    }


    public static class MyAdapter extends FragmentPagerAdapter {

    private final String[] TITLES = {"Social","department","Academics","religion"}; // title for the tab

    public MyAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return TITLES.length; //returns the number of fragment to create.
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }

    @Override
    public Fragment getItem(int position) {
        return ListFrag.newInstance(position);
    }
}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            MainActivity.this.startActivity(new Intent(MainActivity.this,PostEvent.class));
           return  true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem item = menu.add("Post Event");
        item.setIcon(android.R.drawable.ic_input_add);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return true;
    }


}