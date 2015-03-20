package de.wildsau.dogtrailing.services;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.provider.Settings;
import android.support.annotation.StringRes;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;

import de.wildsau.dogtrailing.R;

/**
 * Created by becker on 09.03.2015.
 */
public class LocationService implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "LocationService";

    private final Context context;
    private GoogleApiClient googleApiClient;

    private boolean addressRequested = false;

    private final List<LocationServiceListener> listeners = new ArrayList<LocationServiceListener>();

    /**
     * Receiver registered with this activity to get the response from FetchAddressIntentService.
     */
    private AddressResultReceiver resultReceiver;


    public LocationService(Context context) {
        this.context = context;

        resultReceiver = new AddressResultReceiver(new Handler());

        buildGoogleApiClient();
    }


    //region ### Lifecycle Management ###

    public void start() {
        googleApiClient.connect();
    }

    public void stop() {
        if (googleApiClient.isConnected() || googleApiClient.isConnecting()) {
            googleApiClient.disconnect();
            fireOnDisconnected();
        }
    }

    //endregion

    //region ### Business Logic Methods ###

    public void retrieveCurrentLocation() {
        Log.i(TAG, LocationServices.FusedLocationApi.getLastLocation(googleApiClient).toString());
        LocationRequest request = LocationRequest.create()
                .setNumUpdates(1)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient,request,new SingleLocationRequestListener());
    }

    public boolean retrieveAddress(Location location){
        if(location == null){
            showToast(R.string.no_location_provided_for_address);
            Log.i(TAG, "App tried to retrieve address for Null-Location.");
            return false;
        }

        // Determine whether a Geocoder is available.
        if (!Geocoder.isPresent()) {
            showToast(R.string.no_geocoder_available);
            Log.w(TAG,"Geocoder.isPresent() == false");
            return false;
        }

        //TODO: Correct State handling
        // We only start the service to fetch the address if GoogleApiClient is connected.
        if (googleApiClient.isConnected()) {
            startIntentService(location);
        }

        addressRequested = true;
        return true;
    }

    public boolean isConnected(){
        return googleApiClient.isConnected();
    }

    /**
     * Creates an intent, adds location data to it as an extra, and starts the intent service for
     * fetching an address.
     */
    protected void startIntentService(Location location) {
        // Create an intent for passing to the intent service responsible for fetching the address.
        Intent intent = new Intent(context, FetchAddressIntentService.class);

        // Pass the result receiver as an extra to the service.
        intent.putExtra(FetchAddressIntentService.Constants.RECEIVER, resultReceiver);

        // Pass the location data as an extra to the service.
        intent.putExtra(FetchAddressIntentService.Constants.LOCATION_DATA_EXTRA, location);

        // Start the service. If the service isn't already running, it is instantiated and started
        // (creating a process for it if needed); if it is running then it remains running. The
        // service kills itself automatically once all intents are processed.
        context.startService(intent);
    }


    public boolean isLocationServiceEnabledOnDevice() {
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER) || manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    public void startEnableLocationServicesIntent() {
        buildEnableLocationAlertDialog().show();
    }

    private AlertDialog buildEnableLocationAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setMessage(R.string.enable_location_services_message)
                .setTitle(R.string.enable_location_services_title);

        builder.setPositiveButton(R.string.activate, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent viewIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(viewIntent);
            }
        });
        builder.setNegativeButton(R.string.datetime_picker_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog, do nothing.
            }
        });


        return builder.create();
    }


    //TODO: Toast for location services disabled & Useraction for enabling Location Services.
    //endregion

    //region ### Location Listener Callbacks ###

    public final class SingleLocationRequestListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            Log.i(TAG, "onLocationChanged() fired!");
            LocationService.this.fireOnCurrentLocationFound(location);
        }
    }

    //endregion

    //region ### Google Api Client Callbacks ###

    @Override
    public void onConnected(Bundle bundle) {
        Log.i(TAG,"GoogleApiClient connected");
        fireOnConnected();
    }

    @Override
    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        Log.i(TAG, "GoogleApiClient connection suspended. cause=" + cause);
        String causeMessage;

        switch (cause) {
            case GoogleApiClient.ConnectionCallbacks.CAUSE_NETWORK_LOST:
                causeMessage = "Network Lost!";
                break;
            case GoogleApiClient.ConnectionCallbacks.CAUSE_SERVICE_DISCONNECTED:
                causeMessage = "Service Disconnected!";
                break;
            default:
                causeMessage = "Unknown";
        }

        showToast(context.getString(R.string.google_api_client_connection_suspended, causeMessage));
        fireOnDisconnected();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i(TAG, "GoogleApiClient connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
        showToast(context.getString(R.string.google_api_client_connection_failed, result.getErrorCode()));
        fireOnDisconnected();
    }

    //endregion

    //region ### Event Handling ###

    public void addLocationServiceListener(LocationServiceListener listener){
        listeners.add(listener);
    }

    protected void fireOnCurrentLocationFound(Location location){
        Log.i(TAG, "onCurrentLocationFound() fired!");
        for(LocationServiceListener listener : listeners){
            listener.onCurrentLocationFound(location);
        }
    }

    protected void fireOnAddressResolved(Address address){
        Log.i(TAG, "onAddressResolved() fired!");
        for(LocationServiceListener listener : listeners){
            listener.onAddressResolved(address);
        }
    }

    protected void fireOnAddressResolvedFailed(String errorMessage){
        Log.i(TAG, "onAddressResolvedFailed() fired!");
        for(LocationServiceListener listener : listeners){
            listener.onAddressResolvedFailed(errorMessage);
        }
    }

    protected void fireOnConnected(){
        Log.i(TAG, "onConnected() fired!");
        for(LocationServiceListener listener : listeners){
            listener.onConnected();
        }
    }

    protected void fireOnDisconnected(){
        Log.i(TAG, "onDisconnected() fired!");
        for(LocationServiceListener listener : listeners){
            listener.onDisconnected();
        }
    }

    //endregion

    //region ### Callback Interface ###

    public interface LocationServiceListener{

        void onCurrentLocationFound(Location location);

        void onAddressResolved(Address address);

        void onAddressResolvedFailed(String errorMessage);

        void onConnected();

        void onDisconnected();
    }

    //endregion

    //region ### AddressResultReceiver ###

    /**
     * Receiver for data sent from FetchAddressIntentService.
     */
    class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        /**
         * Receives data sent from FetchAddressIntentService and updates the UI in MainActivity.
         */
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            // Show a toast message if an address was found.
            if (resultCode == FetchAddressIntentService.Constants.SUCCESS_RESULT) {
                // Display the address string or an error message sent from the intent service.
                Address addressOutput = resultData.getParcelable(FetchAddressIntentService.Constants.RESULT_DATA_KEY);

                fireOnAddressResolved(addressOutput);

                showToast(R.string.address_found);
            } else {
                String errorMessage = resultData.getString(FetchAddressIntentService.Constants.ERROR_MESSAGE_DATA_KEY);

                fireOnAddressResolvedFailed(errorMessage);
            }

            addressRequested = false;
        }
    }


    //endregion

    //region ### Helper Methods ###

    protected synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }


    protected void showToast(String text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

    /**
     * Shows a toast with the given string ressource.
     */
    protected void showToast(@StringRes int resId) {
        Toast.makeText(context, resId, Toast.LENGTH_LONG).show();
    }

    //endregion
}
