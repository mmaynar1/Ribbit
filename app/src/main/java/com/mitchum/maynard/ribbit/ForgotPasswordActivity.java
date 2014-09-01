package com.mitchum.maynard.ribbit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;
import com.parse.SignUpCallback;


public class ForgotPasswordActivity extends Activity
{
    private EditText mEmail;
    private Button mResetPasswordButton;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );

        //Must be called before setContentView
        requestWindowFeature( Window.FEATURE_INDETERMINATE_PROGRESS );
        setContentView( R.layout.activity_forgot_password );

        mEmail = (EditText) findViewById( R.id.emailField );
        mResetPasswordButton = (Button) findViewById( R.id.resetPasswordButton );

        mResetPasswordButton.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                String email = mEmail.getText().toString();
                email = email.trim();

                if ( email.isEmpty() )
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder( ForgotPasswordActivity.this );
                    builder.setMessage( R.string.forgot_password_error_message );
                    builder.setTitle( getString( R.string.forgot_password_error_title ) );
                    builder.setPositiveButton( android.R.string.ok, null );
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else
                {
                    //try to reset user password matching the email address
                    setProgressBarIndeterminateVisibility( true );
                    ParseUser.requestPasswordResetInBackground( email, new RequestPasswordResetCallback()
                    {
                        @Override
                        public void done( ParseException e )
                        {
                            if ( e == null )
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder( ForgotPasswordActivity.this );
                                builder.setMessage( R.string.forgot_password_success_message );
                                builder.setTitle( getString( R.string.forgot_password_success_title ) );
                                builder.setPositiveButton( android.R.string.ok, new DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public void onClick( DialogInterface dialogInterface, int i )
                                    {
                                        navigateToLogin();
                                    }
                                } );
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                            else
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder( ForgotPasswordActivity.this );
                                builder.setMessage( e.getMessage() );
                                builder.setTitle( R.string.forgot_password_error_title );
                                builder.setPositiveButton( android.R.string.ok, null );
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        }
                    } );

                }
            }
        } );
    }


    @Override
    public boolean onCreateOptionsMenu( Menu menu )
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.forgot_password, menu );
        return true;
    }

    private void navigateToLogin()
    {
        Intent intent = new Intent( this, LoginActivity.class );
        intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
        intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TASK );
        startActivity( intent );
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
}
