/*
*    Win Blackjack
*    Copyright (C) 2015  John Serrano
*
*    This program is free software: you can redistribute it and/or modify
*    it under the terms of the GNU General Public License as published by
*    the Free Software Foundation, either version 3 of the License, or
*    (at your option) any later version.
*
*    This program is distributed in the hope that it will be useful,
*    but WITHOUT ANY WARRANTY; without even the implied warranty of
*    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*    GNU General Public License for more details.
*
*    You should have received a copy of the GNU General Public License
*    along with this program.  If not, see <http://www.gnu.org/licenses/>.
* */

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