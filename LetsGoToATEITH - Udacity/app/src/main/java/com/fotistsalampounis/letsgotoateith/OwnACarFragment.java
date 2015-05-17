package com.fotistsalampounis.letsgotoateith;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
//import android.widget.Spinner;
import com.example.user.letsgotoateith.R;
import com.rey.material.widget.Spinner;
import com.fotistsalampounis.letsgotoateith.data.TransfersContract;
import com.gc.materialdesign.views.ButtonRectangle;
import com.gc.materialdesign.widgets.Dialog;
import com.gc.materialdesign.widgets.SnackBar;

/**
 * Created by user on 18/4/2015.
 */
public class OwnACarFragment extends Fragment{

        private int userId;
        private String username;
        View rootView;

        public OwnACarFragment() {
        }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.fragment_own_car, container, false);

            ButtonRectangle submitButton=(ButtonRectangle)rootView.findViewById(R.id.submit_button);

            Spinner howManyPeopleSpinner = (Spinner)rootView.findViewById(R.id.howManyPeopleSpinner);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.row_spn, getResources().getStringArray(R.array.peopleSpinner));
            adapter.setDropDownViewResource(R.layout.row_spn_dropdown);
            howManyPeopleSpinner.setAdapter(adapter);

            Spinner whichDaySpinner = (Spinner)rootView.findViewById(R.id.whichDaySpinner);
            ArrayAdapter<String> adapterwhichDaySpinner = new ArrayAdapter<>(getActivity(), R.layout.row_spn, getResources().getStringArray(R.array.weekDaySpinner));
            adapterwhichDaySpinner.setDropDownViewResource(R.layout.row_spn_dropdown);
            whichDaySpinner.setAdapter(adapterwhichDaySpinner);

            Spinner depTimeSpinner = (Spinner)rootView.findViewById(R.id.depTimeSpinner);
            ArrayAdapter<String> adapterdepTimeSpinner = new ArrayAdapter<>(getActivity(), R.layout.row_spn, getResources().getStringArray(R.array.timeSpinner));
            adapterdepTimeSpinner.setDropDownViewResource(R.layout.row_spn_dropdown);
            depTimeSpinner.setAdapter(adapterdepTimeSpinner);

            Spinner retTimeSpinner = (Spinner)rootView.findViewById(R.id.retTimeSpinner);
            ArrayAdapter<String> adapterretTimeSpinner = new ArrayAdapter<>(getActivity(), R.layout.row_spn, getResources().getStringArray(R.array.timeSpinner));
            adapterretTimeSpinner.setDropDownViewResource(R.layout.row_spn_dropdown);
            retTimeSpinner.setAdapter(adapterretTimeSpinner);

            Spinner freqSpinner = (Spinner)rootView.findViewById(R.id.freqSpinner);
            ArrayAdapter<String> adapterfreqSpinner = new ArrayAdapter<>(getActivity(), R.layout.row_spn, getResources().getStringArray(R.array.freqSpinner));
            adapterfreqSpinner.setDropDownViewResource(R.layout.row_spn_dropdown);
            freqSpinner.setAdapter(adapterfreqSpinner);

            Spinner areaSpinner = (Spinner)rootView.findViewById(R.id.areaSpinner);
            ArrayAdapter<String> adapterareaSpinner = new ArrayAdapter<>(getActivity(), R.layout.row_spn, getResources().getStringArray(R.array.areaSpinner));
            adapterareaSpinner.setDropDownViewResource(R.layout.row_spn_dropdown);
            areaSpinner.setAdapter(adapterareaSpinner);

            submitButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(final View flatButton) {
                    Dialog dialog = new Dialog(getActivity(), getString(R.string.ownAcarDataPopUpTitle), getString(R.string.ownAcarDataPopUp));
                    dialog.setOnAcceptButtonClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            Intent intent = getActivity().getIntent();
                            username = intent.getStringExtra(Constants.EXTRA_USERNAME);
                            userId=getActivity().getSharedPreferences(getString(R.string.pref_key), Context.MODE_PRIVATE).getInt(Constants.EXTRA_USERID, -5555);
                            insertQuery(rootView);
                        }
                    });
                    dialog.addCancelButton(getString(R.string.dialogCancel),new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            if(!MainActivity.mTwoPane)
                                getActivity().finish();
                        }
                    });
                    dialog.show();
                }
            });

            return rootView;
        }

    public void insertQuery(View rootview){
        Spinner sp;

        sp=(Spinner)rootview.findViewById(R.id.howManyPeopleSpinner);
        int people=sp.getSelectedItemPosition();
        sp=(Spinner)rootview.findViewById(R.id.whichDaySpinner);
        int day=sp.getSelectedItemPosition();
        sp=(Spinner)rootview.findViewById(R.id.depTimeSpinner);
        int depTime=sp.getSelectedItemPosition();
        sp=(Spinner)rootview.findViewById(R.id.retTimeSpinner);
        int retTime=sp.getSelectedItemPosition();
        sp=(Spinner)rootview.findViewById(R.id.freqSpinner);
        int freq=sp.getSelectedItemPosition();
        sp=(Spinner)rootview.findViewById(R.id.areaSpinner);
        int area=sp.getSelectedItemPosition();

        Uri uri= TransfersContract.CarsEntry.buildRegUri(people,day,depTime,retTime,freq,area);

        ContentValues mNewValues = new ContentValues();

        mNewValues.put(TransfersContract.CarsEntry.COLUMN_PEOPLE, people);
        mNewValues.put(TransfersContract.CarsEntry.COLUMN_DAY, day);
        mNewValues.put(TransfersContract.CarsEntry.COLUMN_DEP_TIME, depTime);
        mNewValues.put(TransfersContract.CarsEntry.COLUMN_RET_TIME, retTime);
        mNewValues.put(TransfersContract.CarsEntry.COLUMN_FREQ, freq);
        mNewValues.put(TransfersContract.CarsEntry.COLUMN_DRIVER_ID, userId);
        mNewValues.put(TransfersContract.CarsEntry.COLUMN_PEOPLE_REG, 0);
        mNewValues.put(TransfersContract.CarsEntry.COLUMN_AREA, area);
        Context context=getActivity();
        ;

        registerTransp(TransfersContract.CarsEntry.getCarIdFromUri(context.getContentResolver().insert(uri, mNewValues)));
    }

    private void registerTransp(int reg_car){
        Uri uri= TransfersContract.TransportsEntry.buildTranspUri2();
        ContentValues mNewValues = new ContentValues();

        mNewValues.put(TransfersContract.TransportsEntry.COLUMN_REG_CAR_ID,reg_car);
        mNewValues.put(TransfersContract.TransportsEntry.COLUMN_USER_ID,getActivity().getSharedPreferences(getString(R.string.pref_key), Context.MODE_PRIVATE).getInt(Constants.EXTRA_USERID, -5555));
        mNewValues.put(TransfersContract.TransportsEntry.COLUMN_CONFIRMED_D,1);
        mNewValues.put(TransfersContract.TransportsEntry.COLUMN_CONFIRMED_U, 1);

        new AsyncTask<Object, Void, Void>() {
            @Override
            protected Void doInBackground(Object... params) {
                Context context=getActivity();
                context.getContentResolver().insert((Uri)params[0],(ContentValues)params[1]);
                return null;
            }
        }.execute(uri, mNewValues);


        Dialog dialog = new Dialog(getActivity(), getString(R.string.ownAcarPopUpTitle), getString(R.string.ownAcarPopUp));
        dialog.setOnAcceptButtonClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                if (!MainActivity.mTwoPane)
                    getActivity().finish();
            }
        });
        dialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (MainActivity.mTwoPane)
            return true;
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            new SnackBar(getActivity(), "Are you sure you want to logout?", "Yes", new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    SharedPreferences prefs=getActivity().getSharedPreferences(getString(R.string.pref_key), Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=prefs.edit();
                    editor.putInt(Constants.EXTRA_USERID, -1);
                    editor.putString(getString(R.string.pref_username_key), "-1");
                    editor.commit();
                    Intent broadcastIntent = new Intent();
                    broadcastIntent.setAction("com.package.ACTION_LOGOUT");
                    getActivity().sendBroadcast(broadcastIntent);
                    Intent it = new Intent(getActivity(), LoginActivity.class);
                    startActivity(it);
                }
            }).show();
        }

        return super.onOptionsItemSelected(item);
    }
}
