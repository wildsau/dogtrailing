package de.wildsau.dogtrailing.edit;

import android.app.DialogFragment;
import android.app.Fragment;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class EditSessionDetailsFragment extends Fragment implements DateTimePickerFragment.OnDateTimeChangedListener, LocationService.LocationServiceListener, View.OnClickListener {

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

    public EditSessionDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_edit_session_details, container, false);

        timeFormat = DateFormat.getTimeFormat(getActivity());
        dateFormat = DateFormat.getDateFormat(getActivity());

        sessionTitleEdit = (EditText) rootView.findViewById(R.id.session_title_edit);
        creationEdit = (EditText) rootView.findViewById(R.id.creation_edit);
        searchedEdit = (EditText) rootView.findViewById(R.id.searched_edit);
        locationEdit = (EditText) rootView.findViewById(R.id.location_edit);
        locationProgressBar = (ProgressBar) rootView.findViewById(R.id.location_progress_bar);
        locationDetermineButton = (ImageView) rootView.findViewById(R.id.location_determine_button);

        //Init listeners!

        creationEdit.setOnClickListener(this);
        searchedEdit.setOnClickListener(this);
        locationDetermineButton.setOnClickListener(this);

        locationRequested = false;

        locationService = new LocationService(this.getActivity());
        locationService.addLocationServiceListener(this);

        //TODO: Testing Code

        FlowLayout tagList = (FlowLayout) rootView.findViewById(R.id.tag_list);

        for (SessionTag tag : SessionTag.values()) {
            TagView tv = new TagView(this.getActivity());
            tv.setValue(tag);

            FlowLayout.LayoutParams params = new FlowLayout.LayoutParams(FlowLayout.LayoutParams.WRAP_CONTENT, FlowLayout.LayoutParams.WRAP_CONTENT);

            tv.setLayoutParams(params);


            tagList.addView(tv);
        }

        return rootView;
    }

    @Override
    public void onResume() {
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

        locationService.stop();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.searched_edit:
                pickSearchedDateTime(v);
                break;
            case R.id.creation_edit:
                pickCreatedDateTime(v);
                break;
            case R.id.location_determine_button:
                determineLocation(v);
                break;
            default:
                return;
        }
    }


    public void pickCreatedDateTime(final View v) {
        //TODO: Initialize with current value
        final DialogFragment createdDateTimePicker = new DateTimePickerFragment();
        createdDateTimePicker.setTargetFragment(this, 0);

        createdDateTimePicker.show(getFragmentManager(), CREATED_DATE_TAG);
    }

    public void pickSearchedDateTime(final View v) {
        //TODO: Initialize with current value
        final DialogFragment searchedDateTimePicker = new DateTimePickerFragment();
        searchedDateTimePicker.setTargetFragment(this, 0);

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

    public void save() {
        TrailingSessionDao dao = getDaoSession().getTrailingSessionDao();

        currentSession.setTitle(sessionTitleEdit.getText().toString());
        currentSession.setCreated(new Date(System.currentTimeMillis()));

        //TODO: Only works for new entities
        dao.insert(currentSession);
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
        return ((DogTrailingApplication) getActivity().getApplication()).getDaoSession();
    }

    /**
     * Shows a toast with the given text.
     */
    protected void showToast(String text) {
        Toast.makeText(this.getActivity(), text, Toast.LENGTH_LONG).show();
    }


    /**
     * Shows a toast with the given string ressource.
     */
    protected void showToast(@StringRes int resId) {
        Toast.makeText(this.getActivity(), resId, Toast.LENGTH_LONG).show();
    }


}
