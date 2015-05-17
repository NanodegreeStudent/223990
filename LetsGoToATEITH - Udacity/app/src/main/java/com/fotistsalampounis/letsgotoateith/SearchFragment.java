package com.fotistsalampounis.letsgotoateith;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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
import com.gc.materialdesign.widgets.SnackBar;
import com.rey.material.widget.Spinner;

/**
 * Created by user on 28/4/2015.
 */
public class SearchFragment extends Fragment {

    public SearchFragment() {
    }

    View rootView;
    Uri uri;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_search, container, false);

        com.rey.material.widget.Spinner whichDaySpinner = (com.rey.material.widget.Spinner)rootView.findViewById(R.id.whichDaySpinner);
        ArrayAdapter<String> adapterwhichDaySpinner = new ArrayAdapter<>(getActivity(), R.layout.row_spn, getResources().getStringArray(R.array.weekDaySpinner));
        adapterwhichDaySpinner.setDropDownViewResource(R.layout.row_spn_dropdown);
        whichDaySpinner.setAdapter(adapterwhichDaySpinner);

        com.rey.material.widget.Spinner depTimeSpinner = (com.rey.material.widget.Spinner)rootView.findViewById(R.id.depTimeSpinner);
        ArrayAdapter<String> adapterdepTimeSpinner = new ArrayAdapter<>(getActivity(), R.layout.row_spn, getResources().getStringArray(R.array.timeSpinner));
        adapterdepTimeSpinner.setDropDownViewResource(R.layout.row_spn_dropdown);
        depTimeSpinner.setAdapter(adapterdepTimeSpinner);

        com.rey.material.widget.Spinner retTimeSpinner = (com.rey.material.widget.Spinner)rootView.findViewById(R.id.retTimeSpinner);
        ArrayAdapter<String> adapterretTimeSpinner = new ArrayAdapter<>(getActivity(), R.layout.row_spn, getResources().getStringArray(R.array.timeSpinner));
        adapterretTimeSpinner.setDropDownViewResource(R.layout.row_spn_dropdown);
        retTimeSpinner.setAdapter(adapterretTimeSpinner);

        com.rey.material.widget.Spinner freqSpinner = (com.rey.material.widget.Spinner)rootView.findViewById(R.id.freqSpinner);
        ArrayAdapter<String> adapterfreqSpinner = new ArrayAdapter<>(getActivity(), R.layout.row_spn, getResources().getStringArray(R.array.freqSpinner));
        adapterfreqSpinner.setDropDownViewResource(R.layout.row_spn_dropdown);
        freqSpinner.setAdapter(adapterfreqSpinner);

        com.rey.material.widget.Spinner areaSpinner = (com.rey.material.widget.Spinner)rootView.findViewById(R.id.areaSpinner);
        ArrayAdapter<String> adapterareaSpinner = new ArrayAdapter<>(getActivity(), R.layout.row_spn, getResources().getStringArray(R.array.areaSpinner));
        adapterareaSpinner.setDropDownViewResource(R.layout.row_spn_dropdown);
        areaSpinner.setAdapter(adapterareaSpinner);

        ButtonRectangle sub=(ButtonRectangle)rootView.findViewById(R.id.submit_button);
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner sp;
                sp=(Spinner)rootView.findViewById(R.id.whichDaySpinner);
                int day=sp.getSelectedItemPosition();
                sp=(Spinner)rootView.findViewById(R.id.depTimeSpinner);
                int depTime=sp.getSelectedItemPosition();
                sp=(Spinner)rootView.findViewById(R.id.retTimeSpinner);
                int retTime=sp.getSelectedItemPosition();
                sp=(Spinner)rootView.findViewById(R.id.freqSpinner);
                int freq=sp.getSelectedItemPosition();
                sp=(Spinner)rootView.findViewById(R.id.areaSpinner);
                int area=sp.getSelectedItemPosition();

                uri= TransfersContract.CarsEntry.buildSearchRegUri(day,depTime,retTime,freq,area);

                Intent intent = new Intent(getActivity(), ResultsActivity.class);
                intent.putExtra(Constants.EXTRA_URI, uri.toString());
                startActivity(intent);
            }

        });


        return rootView;
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
