package de.wildsau.dogtrailing;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends ActionBarActivity {

    public static final String EXTRA_MESSAGE = "de.wildsau.dogtrailing.MESSAGE";

    private RecyclerView sessionListRecyclerView;
    private RecyclerView.Adapter sessionListAdapter;
    private RecyclerView.LayoutManager sessionListLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setLogo(R.drawable.ic_launcher);
//        getSupportActionBar().setDisplayUseLogoEnabled(true);

        setContentView(R.layout.activity_main);

        sessionListRecyclerView = (RecyclerView) findViewById(R.id.session_list);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        sessionListRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        sessionListLayoutManager = new GridLayoutManager(this, 2);
        sessionListRecyclerView.setLayoutManager(sessionListLayoutManager);

        // specify an adapter (see also next example)
        sessionListAdapter = new SessionListAdapter(new String[]{"Hello World", "Here we are", "There we go!", "Dummies arround the world!"});
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
            case R.id.action_search:
                openSearch();
                return true;
            case R.id.action_settings:
                openSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openSettings() {
        System.out.println("Hurra!!!!");
    }

    private void openSearch() {
        System.out.println("Hurz!!!!");
    }

    public void sendMessage(View view) {
//        Intent intent = new Intent(this, MapsActivity.class);
//        EditText editText = (EditText) findViewById(R.id.edit_message);
//        String message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
//        startActivity(intent);
    }
}
