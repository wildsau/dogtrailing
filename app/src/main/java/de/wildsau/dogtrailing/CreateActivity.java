package de.wildsau.dogtrailing;

import android.app.DialogFragment;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.annotation.StringRes;
import android.support.v7.app.ActionBarActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.Calendar;

import de.wildsau.dogtrailing.services.LocationService;


public class CreateActivity extends ActionBarActivity implements LocationService.LocationServiceListener {

    private static final String TAG = "EditSession";

    private java.text.DateFormat timeFormat;
    private java.text.DateFormat dateFormat;
    private EditText editCreation;
    private EditText editSession;
    private ProgressBar progressBar;
    private ImageView determineLocationButton;
    private EditText addressEdit;

    private LocationService locationService;

    private boolean locationRequested;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);



        timeFormat = DateFormat.getTimeFormat(getApplicationContext());
        dateFormat = DateFormat.getDateFormat(getApplicationContext());


        editCreation = (EditText) findViewById(R.id.edit_creation);
        editSession = (EditText) findViewById(R.id.edit_searched);

        addressEdit = (EditText) findViewById(R.id.edit_address);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        determineLocationButton = (ImageView) findViewById(R.id.button_determine_location);

        locationRequested = false;

        locationService = new LocationService(this);
        locationService.addLocationServiceListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        updateUIWidgets();
    }

    @Override
    protected void onStart() {
        super.onStart();

        locationService.start();
    }


    @Override
    protected void onStop() {
        super.onStop();

        locationService.stop();
    }

    public void showDateTimePickerDialog(final View v) {
        final DialogFragment newFragment = new DatePickerFragment() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                super.onDateSet(view, year, month, day);

                Calendar c = Calendar.getInstance();
                c.set(year, month, day);
                String text = dateFormat.format(c.getTime()) + " - " + timeFormat.format(c.getTime());
                if (v == editCreation) {
                    editCreation.setText(text);
                } else {
                    editSession.setText(text);
                }
            }
        };

        newFragment.show(getFragmentManager(), "datePicker");
        //TODO: Pick Time
    }

    public void determineLocation(View view) {
        if(locationService.isConnected()){
            if(locationService.isLocationServiceEnabledOnDevice()){
                locationRequested = true;
                locationService.retrieveCurrentLocation();
            } else {
                locationService.startEnableLocationServicesIntent();
            }
        }

        updateUIWidgets();
    }

    //TODO: Store Results
    @Override
    public void onCurrentLocationFound(Location location) {
        showToast(location.toString());
        locationService.retrieveAddress(location);
    }

    @Override
    public void onAddressResolved(Address address) {
        showToast(address.toString());
        locationRequested = false;
        updateUIWidgets();
    }

    @Override
    public void onConnected() {
        updateUIWidgets();
    }

    @Override
    public void onDisconnected() {
        updateUIWidgets();
    }

    /**
     * Toggles the visibility of the progress bar. Enables or disables the Fetch Address button.
     */
    private void updateUIWidgets() {
        if(!locationService.isConnected()){
            determineLocationButton.setImageResource(R.drawable.ic_action_location_off);
        } else if(!locationService.isLocationServiceEnabledOnDevice()){
            determineLocationButton.setImageResource(R.drawable.ic_action_location_searching);
        } else {
            determineLocationButton.setImageResource(R.drawable.ic_action_location_found);
        }
        if (locationRequested) {
            progressBar.setVisibility(ProgressBar.VISIBLE);
            determineLocationButton.setVisibility(ImageView.GONE);
        } else {
            progressBar.setVisibility(ProgressBar.GONE);
            determineLocationButton.setVisibility(ImageView.VISIBLE);
        }
    }


    /**
     * Shows a toast with the given text.
     */
    protected void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }


    /**
     * Shows a toast with the given string ressource.
     */
    protected void showToast(@StringRes int resId) {
        Toast.makeText(this, resId, Toast.LENGTH_LONG).show();
    }

}
