package johnserrano.winblackjack;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

//TODO: this whole file
//this activity allows the user to look up a specific hand


public class TableActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        //populate spinners

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.cards, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner dealerSpinner = (Spinner) findViewById(R.id.dealerSpinner);
        dealerSpinner.setAdapter(adapter);

        Spinner playerSpinner1 = (Spinner) findViewById(R.id.playerSpinner1);
        playerSpinner1.setAdapter(adapter);

        Spinner playerSpinner2 = (Spinner) findViewById(R.id.playerSpinner2);;
        playerSpinner2.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_table, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
