package de.wildsau.dogtrailing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;

import de.wildsau.dogtrailing.entities.DaoSession;
import de.wildsau.dogtrailing.model.TrailingSession;


public class MainActivity extends ActionBarActivity {

    public static final String EXTRA_MESSAGE = "de.wildsau.dogtrailing.MESSAGE";

    private TrailingSession[] demoData = new TrailingSession[1];

    private RecyclerView sessionListRecyclerView;
    private RecyclerView.Adapter sessionListAdapter;
    private RecyclerView.LayoutManager sessionListLayoutManager;

    private void createDemoData() {
        Calendar c = Calendar.getInstance();

        demoData[0] = new TrailingSession();
        demoData[0].setTitle("Lange Fährte ohne Hund");
        demoData[0].setNotes("Lore Ipsum Lore Impsum");
        demoData[0].setLength(400.00);
        demoData[0].setLocation("Pullach");
        demoData[0].setCreatedDateTime(c.getTime());
        c.add(Calendar.HOUR, 27);
        demoData[0].setSearchedDateTime(c.getTime());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createDemoData();

//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setLogo(R.drawable.ic_launcher);
//        getSupportActionBar().setDisplayUseLogoEnabled(true);

        setContentView(R.layout.activity_main);

        sessionListRecyclerView = (RecyclerView) findViewById(R.id.session_list);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        sessionListRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        sessionListLayoutManager = new LinearLayoutManager(this);
        sessionListRecyclerView.setLayoutManager(sessionListLayoutManager);

        //TODO: Reloading of adapter and closing
        // specify an adapter (see also next example)
        sessionListAdapter = new TrailingSessionListAdapter(getDaoSession());
        sessionListRecyclerView.setAdapter(sessionListAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_add:
                openAdd();
                return true;
            case R.id.action_settings:
                openSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openSettings() {
        showToast("Hurra!!!!");
    }

    private void openAdd() {
        Intent intent = new Intent(this, EditSessionActivity.class);
        startActivity(intent);
    }

    public void sendMessage(View view) {
//        Intent intent = new Intent(this, MapsActivity.class);
//        EditText editText = (EditText) findViewById(R.id.edit_message);
//        String message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
//        startActivity(intent);
    }

    private DaoSession getDaoSession() {
        return ((DogTrailingApplication) getApplication()).getDaoSession();
    }

    protected void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }
}
