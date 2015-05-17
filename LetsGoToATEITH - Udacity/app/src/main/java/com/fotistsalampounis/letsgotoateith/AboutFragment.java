package com.fotistsalampounis.letsgotoateith;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.letsgotoateith.R;

/**
 * Created by user on 26/4/2015.
 */
public class AboutFragment extends Fragment{


        public AboutFragment() {
            //TODO:Add references from images
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_about, container, false);
            return rootView;
        }
}
