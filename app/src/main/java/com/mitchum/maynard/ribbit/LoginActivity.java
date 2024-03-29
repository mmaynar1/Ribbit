package com.mitchum.maynard.ribbit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class LoginActivity extends Activity
{

    private EditText mUsername;
    private EditText mPassword;
    private Button mLoginButton;

    private TextView mSignUpTextView;
    private TextView mForgotPasswordTextView;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );

        //Must be called before setContentView
        requestWindowFeature( Window.FEATURE_INDETERMINATE_PROGRESS );

        setContentView( R.layout.activity_login );

        mSignUpTextView = (TextView) findViewById( R.id.signUpText );
        mSignUpTextView.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                Intent intent = new Intent( LoginActivity.this, SignUpActivity.class );
                startActivity( intent );
            }
        } );


        mForgotPasswordTextView = (TextView) findViewById( R.id.forgotPasswordText );
        mForgotPasswordTextView.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                Intent intent = new Intent( LoginActivity.this, ForgotPasswordActivity.class );
                startActivity( intent );
            }
        } );

        mUsername = (EditText) findViewById( R.id.usernameField );
        mPassword = (EditText) findViewById( R.id.passwordField );

        mLoginButton = (Button) findViewById( R.id.loginButton );

        mLoginButton.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                String username = mUsername.getText().toString();
                String password = mPassword.getText().toString();

                username = username.trim();
                password = password.trim();


                if ( username.isEmpty() || password.isEmpty() )
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder( LoginActivity.this );
                    builder.setMessage( R.string.login_error_message );
                    builder.setTitle( getString( R.string.login_error_title ) );
                    builder.setPositiveButton( android.R.string.ok, null );
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else
                {
                    //login
                    setProgressBarIndeterminateVisibility( true );
                    ParseUser.logInInBackground( username, password, new LogInCallback()
                    {
                        @Override
                        public void done( ParseUser parseUser, ParseException e )
                        {
                            setProgressBarIndeterminateVisibility( false );
                            if ( e == null )
                            {
                                //Success
                                Intent intent = new Intent( LoginActivity.this, MainActivity.class );
                                intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
                                intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TASK );
                                startActivity( intent );

                            }
                            else
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder( LoginActivity.this );
                                builder.setMessage( e.getMessage() );
                                builder.setTitle( R.string.login_error_title );
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
        getMenuInflater().inflate( R.menu.login, menu );
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
}
