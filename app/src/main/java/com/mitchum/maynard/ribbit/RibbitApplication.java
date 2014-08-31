package com.mitchum.maynard.ribbit;

import android.app.Application;
import com.parse.Parse;
import com.parse.ParseObject;

public class RibbitApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        Parse.initialize( this, "sw7vmfrj9epDKWBCe1g2dcxuStbKYh7g88ebT5Il", "9wZrt3PBpNMwUmppyORMF3aVHZyKFZv8LM5xRGLQ" );


    }
}
