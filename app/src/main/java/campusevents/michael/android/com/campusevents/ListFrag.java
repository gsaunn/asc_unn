package campusevents.michael.android.com.campusevents;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;


/**
 * Created by User Pc on 6/18/2015.
 */
public class ListFrag extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String ARG_POSITION = "position";
    private MyCursorAdapter cursorAdapter;
    private int position;
    private Cursor cursor;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        position = getArguments().getInt(ARG_POSITION);
        getLoaderManager().initLoader(position,null,this);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_content_list,null);
        cursorAdapter = new MyCursorAdapter(getActivity(),R.layout.list_view_item_layout,null,new String[] {TableDefnDB.DeptTableDfn._ID,TableDefnDB.DeptTableDfn.COLUMN_NAME_DATE,TableDefnDB.DeptTableDfn.COLUMN_NAME_TITLE,TableDefnDB.DeptTableDfn.COLUMN_NAME_VENUE,TableDefnDB.DeptTableDfn.COLUMN_NAME_IMAGEURL}, new int[] {R.id.date,R.id.title,R.id.venue,R.id.imageView},getActivity(),position,(ListView)view.findViewById(android.R.id.list),0);
        setListAdapter(cursorAdapter);
        return inflater.inflate(R.layout.main_content_list,null);
    }


    public static ListFragment newInstance(int position) {
        ListFrag f = new ListFrag();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (id == 0) { // social
            Uri uri = Uri.parse(ContractProvider.URI_CONTENT + "/" + TableDefnDB.SocialTableDfn.TABLE_NAME);
            return new CursorLoader(getActivity(), uri, new String[]{TableDefnDB.SocialTableDfn._ID, TableDefnDB.SocialTableDfn.COLUMN_NAME_DATE, TableDefnDB.SocialTableDfn.COLUMN_NAME_TITLE, TableDefnDB.SocialTableDfn.COLUMN_NAME_VENUE, TableDefnDB.SocialTableDfn.COLUMN_NAME_BODY, TableDefnDB.SocialTableDfn.COLUMN_NAME_CATEGORY, TableDefnDB.SocialTableDfn.COLUMN_NAME_ORGANIZER, TableDefnDB.SocialTableDfn.COLUMN_NAME_TIME, TableDefnDB.SocialTableDfn.COLUMN_NAME_IMAGEURL}, null, null, null);
        }
        else if (id == 1) { // dept
            Uri uri = Uri.parse(ContractProvider.URI_CONTENT + "/" + TableDefnDB.DeptTableDfn.TABLE_NAME);
            return new CursorLoader(getActivity(), uri, new String[]{TableDefnDB.DeptTableDfn._ID, TableDefnDB.DeptTableDfn.COLUMN_NAME_DATE, TableDefnDB.DeptTableDfn.COLUMN_NAME_TITLE, TableDefnDB.DeptTableDfn.COLUMN_NAME_VENUE, TableDefnDB.DeptTableDfn.COLUMN_NAME_BODY, TableDefnDB.DeptTableDfn.COLUMN_NAME_CATEGORY, TableDefnDB.DeptTableDfn.COLUMN_NAME_ORGANIZER, TableDefnDB.DeptTableDfn.COLUMN_NAME_TIME, TableDefnDB.DeptTableDfn.COLUMN_NAME_IMAGEURL}, null, null, null);
        }
        else if (id == 2) { // academics
            Uri uri = Uri.parse(ContractProvider.URI_CONTENT + "/" + TableDefnDB.AcademicsTableDfn.TABLE_NAME);
            return new CursorLoader(getActivity(), uri, new String[]{TableDefnDB.AcademicsTableDfn._ID, TableDefnDB.AcademicsTableDfn.COLUMN_NAME_DATE, TableDefnDB.AcademicsTableDfn.COLUMN_NAME_TITLE, TableDefnDB.AcademicsTableDfn.COLUMN_NAME_VENUE, TableDefnDB.AcademicsTableDfn.COLUMN_NAME_BODY, TableDefnDB.AcademicsTableDfn.COLUMN_NAME_CATEGORY, TableDefnDB.AcademicsTableDfn.COLUMN_NAME_ORGANIZER, TableDefnDB.AcademicsTableDfn.COLUMN_NAME_TIME, TableDefnDB.AcademicsTableDfn.COLUMN_NAME_IMAGEURL}, null, null, null);
        }
        else { // relgion
            Uri uri = Uri.parse(ContractProvider.URI_CONTENT + "/" + TableDefnDB.ReligionTableDfn.TABLE_NAME);
            return new CursorLoader(getActivity(), uri, new String[]{TableDefnDB.ReligionTableDfn._ID, TableDefnDB.ReligionTableDfn.COLUMN_NAME_DATE, TableDefnDB.ReligionTableDfn.COLUMN_NAME_TITLE, TableDefnDB.ReligionTableDfn.COLUMN_NAME_VENUE, TableDefnDB.ReligionTableDfn.COLUMN_NAME_BODY, TableDefnDB.ReligionTableDfn.COLUMN_NAME_CATEGORY, TableDefnDB.ReligionTableDfn.COLUMN_NAME_ORGANIZER, TableDefnDB.ReligionTableDfn.COLUMN_NAME_TIME, TableDefnDB.ReligionTableDfn.COLUMN_NAME_IMAGEURL}, null, null, null);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        cursorAdapter.swapCursor(data);
        }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


}

