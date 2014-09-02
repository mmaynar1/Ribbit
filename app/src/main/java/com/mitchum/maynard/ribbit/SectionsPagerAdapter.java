package com.mitchum.maynard.ribbit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Locale;

/**
     * A {@link android.support.v13.app.FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter
    {
        protected Context context;

        public SectionsPagerAdapter(Context context, FragmentManager fm )
        {
            super( fm );
            this.context = context;
        }

        @Override
        public Fragment getItem( int position )
        {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class in MainActivity).
            return MainActivity.PlaceholderFragment.newInstance( position + 1 );

        }

        @Override
        public int getCount()
        {
            return 2;
        }

        @Override
        public CharSequence getPageTitle( int position )
        {
            Locale l = Locale.getDefault();
            switch ( position )
            {
                case 0:
                    return context.getString( R.string.title_section1 ).toUpperCase( l );
                case 1:
                    return context.getString( R.string.title_section2 ).toUpperCase( l );
            }
            return null;
        }
    }

