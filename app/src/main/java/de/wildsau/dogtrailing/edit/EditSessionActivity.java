package de.wildsau.dogtrailing.edit;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import de.wildsau.dogtrailing.R;


public class EditSessionActivity extends ActionBarActivity {

    private static final String TAG = "EditSession";

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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_session);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_cancel);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_session, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //TODO: Maybe move the menuItem to the fragment
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_save:
                EditSessionDetailsFragment fragment = (EditSessionDetailsFragment) getFragmentManager().findFragmentById(R.id.edit_details);

                fragment.save();

                finish();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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








}
