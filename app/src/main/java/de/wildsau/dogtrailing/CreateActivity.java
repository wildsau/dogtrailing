package de.wildsau.dogtrailing;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.Calendar;


public class CreateActivity extends ActionBarActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private EditText editCreation;
    private java.text.DateFormat timeFormat;
    private java.text.DateFormat dateFormat;
    private EditText editSession;
    private GoogleApiClient googleApiClient;
    private Location lastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        timeFormat = DateFormat.getTimeFormat(getApplicationContext());
        dateFormat = DateFormat.getDateFormat(getApplicationContext());


        editCreation = (EditText) findViewById(R.id.edit_creation);
        editSession = (EditText) findViewById(R.id.edit_searched);
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
        buildGoogleApiClient();
    }

    protected synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        googleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        lastLocation = LocationServices.FusedLocationApi.getLastLocation(
                googleApiClient);
        if (lastLocation != null) {
            new DetectingLocationDialog(lastLocation.toString()).show(getFragmentManager(), "alert");
        } else {
            //TODO: Error Handling
            new DetectingLocationDialog("No location found!").show(getFragmentManager(), "alert");
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        //TODO: Detecting location on startup and choose button image
        new DetectingLocationDialog("Suspended!").show(getFragmentManager(), "alert");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        //TODO: Error Handling
        new DetectingLocationDialog("Connection Failed!").show(getFragmentManager(), "alert");

    }

    public static class DetectingLocationDialog extends DialogFragment {

        private final String message;

        DetectingLocationDialog(String message) {
            this.message = message;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(message)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // FIRE ZE MISSILES!
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }
}
