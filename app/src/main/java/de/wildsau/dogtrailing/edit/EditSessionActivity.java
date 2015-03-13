package de.wildsau.dogtrailing.edit;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.ActionBarActivity;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Date;

import de.wildsau.dogtrailing.DogTrailingApplication;
import de.wildsau.dogtrailing.R;
import de.wildsau.dogtrailing.entities.DaoSession;
import de.wildsau.dogtrailing.entities.TrailingSession;
import de.wildsau.dogtrailing.entities.TrailingSessionDao;
import de.wildsau.dogtrailing.services.LocationService;


public class EditSessionActivity extends ActionBarActivity implements LocationService.LocationServiceListener, DateTimePickerFragment.OnDateTimeChangedListener {

    private static final String TAG = "EditSession";
    private static final String CREATED_DATE_TAG = "CreatedDateFragment";
    private static final String SEARCHED_DATE_TAG = "SearchedDateFragment";

    //TODO: The following fields have to be handeled.
//    private Long id;
//    private String title;
//    private String notes;
//    private String distractions;
//    private String finds;
//    private Boolean test;
//    private Boolean blind;
//    private java.util.Date created;
//    private java.util.Date searched;
//    private Long exposureTime;
//    private String weather;
//    private Integer temperature;
//    private Integer humidity;
//    private String wind;
//    private String windDirection;
//    private String terrain;
//    private String locality;
//    private Boolean selfCreated;
//    private String laidBy;
//    private String searchItem;
//    private String dogHandler;
//    private String dog;
//    private Double length;
//    private Integer startingBehaviour;
//    private Integer cornerWork;
//    private Integer searchBehaviour;
//    private Integer distractionsBehaviour;
//    private Integer overallImpression;
//    private Integer overallImpressionDogHandler;


    private java.text.DateFormat timeFormat;
    private java.text.DateFormat dateFormat;

    private EditText sessionTitleEdit;
    private EditText creationEdit;
    private EditText searchedEdit;
    private ProgressBar locationProgressBar;
    private ImageView locationDetermineButton;
    private EditText locationEdit;
    private Spinner searchBehaviourSpinner;

    //TODO: This works only for create session
    private TrailingSession currentSession = new TrailingSession();

    private LocationService locationService;

    private boolean locationRequested;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_session);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_cancel);

        timeFormat = DateFormat.getTimeFormat(this);
        dateFormat = DateFormat.getDateFormat(this);

        sessionTitleEdit = (EditText) findViewById(R.id.session_title_edit);
        creationEdit = (EditText) findViewById(R.id.creation_edit);
        searchedEdit = (EditText) findViewById(R.id.searched_edit);
        locationEdit = (EditText) findViewById(R.id.location_edit);

        initSearchBehaviourSpinner();

        locationProgressBar = (ProgressBar) findViewById(R.id.location_progress_bar);
        locationDetermineButton = (ImageView) findViewById(R.id.location_determine_button);

        locationRequested = false;

        locationService = new LocationService(this);
        locationService.addLocationServiceListener(this);
    }

    private void initSearchBehaviourSpinner() {
        searchBehaviourSpinner = (Spinner) findViewById(R.id.search_behaviour_spinner);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, new String[]{"ABC", "DEF", "IJK"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchBehaviourSpinner.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_session, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_save:
                saveData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cancel");
        builder.setMessage("Do you really like to discard your changes?");
        builder.setPositiveButton("Continue Editing", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Nothing
            }
        });
        builder.setNegativeButton("Discard", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditSessionActivity.super.onBackPressed();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
        //super.onBackPressed();
    }


    protected void saveData() {
        TrailingSessionDao dao = getDaoSession().getTrailingSessionDao();

        currentSession.setTitle(sessionTitleEdit.getText().toString());
        currentSession.setCreated(new Date(System.currentTimeMillis()));

        //TODO: Only works for new entities
        dao.insert(currentSession);

        finish();
    }

    public void pickCreatedDateTime(final View v) {
        //TODO: Initialize with current value
        final DialogFragment createdDateTimePicker = new DateTimePickerFragment();

        createdDateTimePicker.show(getFragmentManager(), CREATED_DATE_TAG);
    }

    public void pickSearchedDateTime(final View v) {
        //TODO: Initialize with current value
        final DialogFragment searchedDateTimePicker = new DateTimePickerFragment();

        searchedDateTimePicker.show(getFragmentManager(), SEARCHED_DATE_TAG);
    }

    @Override
    public void onDateTimeChanged(DateTimePickerFragment dateTimePickerFragment, Date dateTime) {
        String text = dateFormat.format(dateTime) + " - " + timeFormat.format(dateTime);

        String tag = dateTimePickerFragment.getTag();
        //TODO: Save to local field
        if (CREATED_DATE_TAG.equals(tag)) {
            creationEdit.setText(text);
        } else if (SEARCHED_DATE_TAG.equals(tag)) {
            searchedEdit.setText(text);
        }
    }

    public void determineLocation(View view) {
        if (locationService.isConnected()) {
            if (locationService.isLocationServiceEnabledOnDevice()) {
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
    public void onAddressResolvedFailed(String errorMessage) {
        showToast(errorMessage);
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
        if (!locationService.isConnected()) {
            locationDetermineButton.setImageResource(R.drawable.ic_action_location_off);
        } else if (!locationService.isLocationServiceEnabledOnDevice()) {
            locationDetermineButton.setImageResource(R.drawable.ic_action_location_searching);
        } else {
            locationDetermineButton.setImageResource(R.drawable.ic_action_location_found);
        }
        if (locationRequested) {
            locationProgressBar.setVisibility(ProgressBar.VISIBLE);
            locationDetermineButton.setVisibility(ImageView.GONE);
        } else {
            locationProgressBar.setVisibility(ProgressBar.GONE);
            locationDetermineButton.setVisibility(ImageView.VISIBLE);
        }
    }

    private DaoSession getDaoSession() {
        return ((DogTrailingApplication) getApplication()).getDaoSession();
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
