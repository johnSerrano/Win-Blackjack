package johnserrano.winblackjack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainMenu extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActionBar().hide();

        setContentView(R.layout.activity_main_menu);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    public void openPlayActivity(View v) {
        Intent openPlay = new Intent(this, PlayActivity.class);
        startActivity(openPlay);
    }

    public void openSettingsActivity(View v) {
        Intent openSettings = new Intent(this, SettingsActivity.class);
        startActivity(openSettings);
    }

    public void openTableActivity(View v) {
        Intent openTable = new Intent(this, TableActivity.class);
        startActivity(openTable);
    }

}