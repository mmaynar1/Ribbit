package com.mitchum.maynard.ribbit;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.parse.*;

import java.util.List;


public class EditFriendsActivity extends ListActivity
{

    public static final String TAG = EditFriendsActivity.class.getSimpleName();
    private List<ParseUser> mUsers;
    private ParseRelation<ParseUser> mFriendsRelation;
    private ParseUser mCurrentUser;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        requestWindowFeature( Window.FEATURE_INDETERMINATE_PROGRESS );
        setContentView( R.layout.activity_edit_friends );

        getListView().setChoiceMode( ListView.CHOICE_MODE_MULTIPLE );
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        mCurrentUser = ParseUser.getCurrentUser();
        mFriendsRelation = mCurrentUser.getRelation( ParseConstants.KEY_FRIENDS_RELATION );


        setProgressBarIndeterminateVisibility( true );
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.orderByAscending( ParseConstants.KEY_USERNAME );
        query.setLimit( 1000 );
        query.findInBackground( new FindCallback<ParseUser>()
        {
            @Override
            public void done( List<ParseUser> users, ParseException exception )
            {
                setProgressBarIndeterminateVisibility( false );
                if ( exception == null )
                {
                    //success
                    mUsers = users;
                    String[] usernames = new String[mUsers.size()];

                    int i = 0;
                    for (ParseUser user : mUsers)
                    {
                        usernames[i] = user.getUsername();
                        ++i;
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>( EditFriendsActivity.this,
                                                                             android.R.layout.simple_list_item_checked,
                                                                             usernames );
                    setListAdapter( adapter );
                    addFriendCheckmarks();
                }
                else
                {
                    Log.e( TAG, exception.getMessage() );
                    AlertDialog.Builder builder = new AlertDialog.Builder( EditFriendsActivity.this );
                    builder.setMessage( exception.getMessage() );
                    builder.setTitle( R.string.sign_up_error_title );
                    builder.setPositiveButton( android.R.string.ok, null );
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        } );
    }

    private void addFriendCheckmarks()
    {
        mFriendsRelation.getQuery().findInBackground( new FindCallback<ParseUser>()
        {
            @Override
            public void done( List<ParseUser> friends, ParseException e )
            {
                if(e == null)
                {
                    //list returned - look for a match
                    for(int i = 0; i < mUsers.size(); ++i)
                    {
                        ParseUser user = mUsers.get( i );
                        for(ParseUser friend : friends)
                        {
                            if(friend.getObjectId().equals( user.getObjectId() ))
                            {
                                //have a match
                                getListView().setItemChecked( i, true );
                            }
                        }
                    }
                }
                else
                {
                    Log.e(TAG, e.getMessage());
                }
            }
        } );
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu )
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.edit_friends, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item )
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if ( id == R.id.action_settings )
        {
            return true;
        }
        return super.onOptionsItemSelected( item );
    }

    @Override
    protected void onListItemClick( ListView l, View v, int position, long id )
    {
        super.onListItemClick( l, v, position, id );

        if ( getListView().isItemChecked( position ) )
        {
            //add friend
            mFriendsRelation.add( mUsers.get( position ) );
            mCurrentUser.saveInBackground( new SaveCallback()
            {
                @Override
                public void done( ParseException e )
                {
                    if ( e != null )
                    {
                        Log.e( TAG, e.getMessage() );
                    }
                }
            } );
        }
        else
        {
            //remove friend

        }


    }
}
