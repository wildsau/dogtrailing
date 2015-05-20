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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.wefika.flowlayout.FlowLayout;

import java.util.Date;

import de.wildsau.dogtrailing.DogTrailingApplication;
import de.wildsau.dogtrailing.R;
import de.wildsau.dogtrailing.entities.DaoSession;
import de.wildsau.dogtrailing.entities.TrailingSession;
import de.wildsau.dogtrailing.entities.TrailingSessionDao;
import de.wildsau.dogtrailing.model.SessionTag;
import de.wildsau.dogtrailing.services.LocationService;
import de.wildsau.dogtrailing.widgets.DateTimePickerFragment;
import de.wildsau.dogtrailing.widgets.TagView;


public class EditSessionActivity extends ActionBarActivity implements DateTimePickerFragment.OnDateTimeChangedListener, LocationService.LocationServiceListener {

    private static final String TAG = "EditSession";
    private static final String CREATED_DATE_TAG = "CreatedDateFragment";
    private static final String SEARCHED_DATE_TAG = "SearchedDateFragment";

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

    //region ### Activity Lifecycle ###

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
        locationProgressBar = (ProgressBar) findViewById(R.id.location_progress_bar);
        locationDetermineButton = (ImageView) findViewById(R.id.location_determine_button);

        locationRequested = false;

        locationService = new LocationService(this);
        locationService.addLocationServiceListener(this);

        //TODO: Testing Code

        FlowLayout tagList = (FlowLayout) findViewById(R.id.tag_list);

        for (SessionTag tag : SessionTag.values()) {
            TagView tv = new TagView(this);
            tv.setValue(tag);

            FlowLayout.LayoutParams params = new FlowLayout.LayoutParams(FlowLayout.LayoutParams.WRAP_CONTENT, FlowLayout.LayoutParams.WRAP_CONTENT);

            tv.setLayoutParams(params);


            tagList.addView(tv);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        updateUIWidgets();
    }

    @Override
    public void onStart() {
        super.onStart();

        locationService.start();
    }


    @Override
    public void onStop() {
        super.onStop();

        locationRequested = false;

        locationService.stop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_session, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //endregion


    //region ### User Interaction Listeners ###

    public void onCreationClicked(View view) {
        //TODO: Initialize with current value
        final DialogFragment createdDateTimePicker = new DateTimePickerFragment();

        createdDateTimePicker.show(getFragmentManager(), CREATED_DATE_TAG);
    }

    public void onSearchedClicked(View view) {
        //TODO: Initialize with current value
        final DialogFragment searchedDateTimePicker = new DateTimePickerFragment();

        searchedDateTimePicker.show(getFragmentManager(), SEARCHED_DATE_TAG);
    }

    public void onDetermineLocationClicked(View view) {
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

    public void onTagListClicked(View view) {

        showToast("Hurra");
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

    //TODO: Maybe move the menuItem to the fragment
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_save:
                save();

                finish();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //endregion


    //region ### LocationListener ###

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

    //endregion


    //region ### Database Interaction ###

    public void save() {
        TrailingSessionDao dao = getDaoSession().getTrailingSessionDao();

        currentSession.setTitle(sessionTitleEdit.getText().toString());
        currentSession.setCreated(new Date(System.currentTimeMillis()));

        //TODO: Only works for new entities
        dao.insert(currentSession);
    }

    private DaoSession getDaoSession() {
        return ((DogTrailingApplication) getApplication()).getDaoSession();
    }


    //endregion


    //region ### Toast Helper ###

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

    //endregion
}
