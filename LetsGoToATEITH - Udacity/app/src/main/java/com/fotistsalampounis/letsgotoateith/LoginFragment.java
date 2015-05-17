package com.fotistsalampounis.letsgotoateith;

/**
 * Created by user on 14/4/2015.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.letsgotoateith.R;
import com.fotistsalampounis.letsgotoateith.data.TransfersContract;
import com.gc.materialdesign.views.ButtonRectangle;
import com.rey.material.widget.EditText;


public class LoginFragment extends Fragment {

    String usernameStr;
    private static final int QUERY_LOADER = 100;
    private EditText username;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    public LoginFragment() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs=getActivity().getSharedPreferences(getString(R.string.pref_key), Context.MODE_PRIVATE);
        editor=prefs.edit();
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);


        ButtonRectangle loginButton =(ButtonRectangle)rootView.findViewById(R.id.loginButton);
        TextView aboutButton=(TextView)rootView.findViewById(R.id.aboutText);
        TextView registerButton=(TextView)rootView.findViewById(R.id.registerText);
        username= (EditText) rootView.findViewById(R.id.username);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AboutActivity.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernameStr=username.getText().toString();
                checkUser();

            }

        });


        return rootView;
    }

    private void checkUser(){
        getLoaderManager().destroyLoader(QUERY_LOADER);
        getLoaderManager().initLoader(QUERY_LOADER, null, new LoaderManager.LoaderCallbacks<Cursor>() {
            @Override
            public Loader<Cursor> onCreateLoader(int id, Bundle args) {

                return new CursorLoader(getActivity(),
                        TransfersContract.UsersEntry.CONTENT_URI,
                        new String[]{TransfersContract.UsersEntry._ID},
                        TransfersContract.UsersEntry.COLUMN_USERNAME+" = ?",
                        new String[]{usernameStr},
                        null);
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                if(data.moveToFirst()) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.putExtra(Constants.EXTRA_USERNAME, usernameStr);
                    intent.putExtra(Constants.EXTRA_USERID, data.getInt(0));
                    editor.putInt(Constants.EXTRA_USERID, data.getInt(0));
                    editor.putString(getString(R.string.pref_username_key), usernameStr);
                    editor.commit();
                    intent.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                    startActivity(intent);
                    getActivity().finish();
                }
                else{
                    username.setText("");
                    username.setError("Wrong Username");
                }
            }

            @Override
            public void onLoaderReset(Loader<Cursor> loader) {


            }
        });


    }

}
