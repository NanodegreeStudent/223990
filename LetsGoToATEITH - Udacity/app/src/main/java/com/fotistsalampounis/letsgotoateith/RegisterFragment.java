package com.fotistsalampounis.letsgotoateith;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.user.letsgotoateith.R;
import com.fotistsalampounis.letsgotoateith.data.TransfersContract;
import com.gc.materialdesign.views.ButtonRectangle;
import com.gc.materialdesign.widgets.Dialog;
import com.rey.material.widget.EditText;
import com.rey.material.widget.Spinner;


/**
 * Created by user on 19/4/2015.
 */
public class RegisterFragment extends Fragment {

    EditText username,fullname,school,email,facebookLink;
    Spinner area;
    public RegisterFragment() {
    }

    private String usernameS,fullnameS,schoolS,emailS,facebookS;
    private int areaS;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_register, container, false);

        Spinner areaSpinner = (Spinner)rootView.findViewById(R.id.areaSpinner);
        ArrayAdapter<String> adapterareaSpinner = new ArrayAdapter<>(getActivity(), R.layout.row_spn, getResources().getStringArray(R.array.areaSpinner));
        adapterareaSpinner.setDropDownViewResource(R.layout.row_spn_dropdown);
        areaSpinner.setAdapter(adapterareaSpinner);
        fullname=(EditText)rootView.findViewById(R.id.fullname);
        username=(EditText)rootView.findViewById(R.id.username);
        school=(EditText)rootView.findViewById(R.id.school);
        email=(EditText)rootView.findViewById(R.id.email);
        facebookLink=(EditText)rootView.findViewById(R.id.facebookLink);
        area=(Spinner)rootView.findViewById(R.id.areaSpinner);
        ButtonRectangle signUp=(ButtonRectangle) rootView.findViewById(R.id.signUpButton);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean flag=true;
                usernameS= username.getText().toString();
                areaS=area.getSelectedItemPosition();
                fullnameS=fullname.getText().toString();

                schoolS=school.getText().toString();

                emailS= email.getText().toString();

                if(!facebookLink.getText().toString().trim().isEmpty()) {
                    facebookS = facebookLink.getText().toString();
                }

                if(usernameS.trim().isEmpty()){
                    username.setError(getString(R.string.usernameErrorHint));
                    flag=false;
                }
                if(fullnameS.trim().isEmpty()){
                    fullname.setError(getString(R.string.fullnameErrorHint));
                    flag=false;
                }
                if(schoolS.trim().isEmpty()){
                    school.setError(getString(R.string.schoolErrorHint));
                    flag=false;
                }
                if(emailS.trim().isEmpty()){
                    email.setError(getString(R.string.emailErrorHint));
                    flag=false;
                }
                else if(!emailS.trim().contains("@teithe.gr")){
                    email.setError(getString(R.string.emailTeitheErrorHint));
                    flag=false;
                }

                if(flag){
                    Uri uri= TransfersContract.UsersEntry.buildUserUri();
                    ContentValues mNewValues = new ContentValues();

                    mNewValues.put(TransfersContract.UsersEntry.COLUMN_USERNAME, usernameS);
                    mNewValues.put(TransfersContract.UsersEntry.COLUMN_FULLNAME, fullnameS);
                    mNewValues.put(TransfersContract.UsersEntry.COLUMN_SCHOOL, schoolS);
                    mNewValues.put(TransfersContract.UsersEntry.COLUMN_AREA, areaS);
                    mNewValues.put(TransfersContract.UsersEntry.COLUMN_EMAIL, emailS);
                    if(facebookS!=null) {
                        mNewValues.put(TransfersContract.UsersEntry.COLUMN_FB, facebookS);
                    }
                    else
                        mNewValues.put(TransfersContract.UsersEntry.COLUMN_FB, "No Facebook Data");

                    new AsyncTask<Object, Void, Void>() {
                        @Override
                        protected Void doInBackground(Object... params) {
                            Context context=getActivity();
                            context.getContentResolver().insert((Uri)params[0],(ContentValues)params[1]);
                            return null;
                        }
                    }.execute(uri, mNewValues);


                    Dialog dialog = new Dialog(getActivity(), getString(R.string.userRegPopup), getString(R.string.registerPopUp));
                    dialog.setOnAcceptButtonClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            getActivity().finish();
                        }
                    });

                    dialog.show();

                }
            }

        });
        return rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            Utilities.logout(getActivity());
        }

        return super.onOptionsItemSelected(item);
    }
}
