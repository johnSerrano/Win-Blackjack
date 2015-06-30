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
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class PlayActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        generateNewHands();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_play, menu);
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

    public void hit(View v)
    {
        evaluateDecision(0);
    }

    public void stay(View v)
    {
        evaluateDecision(1);
    }

    public void _double(View v)
    {
        evaluateDecision(2);
    }

    public void split(View v)
    {
        //Check if hand is pair
        String pc1 = (String) ((TextView) findViewById(R.id.playercard1)).getText();
        String pc2 = (String) ((TextView) findViewById(R.id.playercard2)).getText();

        if (!pc1.equals(pc2)) {
            //if not, toast and return
            Context context = getApplicationContext();
            CharSequence text = "You can only split pairs";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            return;

        } else if (pc1.equals("A")) {
            //can't split aces
            //maybe this should be variable in settings?
            Context context = getApplicationContext();
            CharSequence text = "You can't split aces";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            return;
        }

        evaluateDecision(3);
    }

    public void evaluateDecision(int action)
    {
        //0: hit 1:stay 2:double 3:split

        //if we already seleccted an option this turn,
        //we don't want to allow another selection
        //selecting an option shows the continue button
        //continue is hidden when the next turn begins
        if (findViewById(R.id.continuebutton).isShown()){
            return;
        }

        //strategy source: http://www.hitorstand.net/strategy.php
        int[][] pairs = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, //as
                {0, 0, 0, 3, 3, 3, 3, 0, 0, 0}, //2s
                {0, 0, 0, 3, 3, 3, 3, 0, 0, 0}, //3s
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, //4s
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, //5s
                {0, 0, 3, 3, 3, 3, 0, 0, 0, 0}, //6s
                {0, 3, 3, 3, 3, 3, 3, 3, 0, 0}, //7s
                {3, 3, 3, 3, 3, 3, 3, 3, 3, 3}, //8s
                {1, 3, 3, 3, 3, 3, 1, 3, 3, 1}, //9s
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, //10s (incl. face)
        };

        int[][] ace = {
                {0, 0, 0, 0, 2, 2, 0, 0, 0, 0}, //2
                {0, 0, 0, 0, 2, 2, 0, 0, 0, 0}, //3
                {0, 0, 0, 2, 2, 2, 0, 0, 0, 0}, //4
                {0, 0, 0, 2, 2, 2, 0, 0, 0, 0}, //5
                {0, 0, 2, 2, 2, 2, 0, 0, 0, 0}, //6
                {0, 1, 2, 2, 2, 2, 1, 1, 0, 0}, //7
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, //8
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, //9
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, //10
        };

        int[][] nopair = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},      //1 //doesn't happen
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},      //2 //doesn't happen
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},      //3 //doesn't happen
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},      //4 //only pairs, doesn't happen here
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},      //5 //hit all
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},      //6 //hit all
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},      //7 //hit all
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},      //8 //hit all
                {0, 0, 2, 2, 2, 2, 0, 0, 0, 0},      //9 //every day im doublin'
                {0, 2, 2, 2, 2, 2, 2, 2, 2, 0},      //10
                {0, 2, 2, 2, 2, 2, 2, 2, 2, 2},      //11
                {0, 0, 0, 1, 1, 1, 0, 0, 0, 0},      //12 //stand low, the dealer might bust
                {0, 1, 1, 1, 1, 1, 0, 0, 0, 0},      //13
                {0, 1, 1, 1, 1, 1, 0, 0, 0, 0},      //14
                {0, 1, 1, 1, 1, 1, 0, 0, 0, 0},      //15
                {0, 1, 1, 1, 1, 1, 0, 0, 0, 0},      //16
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},      //17 //stand all
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},      //18
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},      //19
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},      //20
        };

        //get card values
        String dealer = (String) ((TextView) findViewById(R.id.dealerval)).getText();
        String pc1 = (String) ((TextView) findViewById(R.id.playercard1)).getText();
        String pc2 = (String) ((TextView) findViewById(R.id.playercard2)).getText();

        int dealervalue = getIntFromCard(dealer);
        int playervalue1 = getIntFromCard(pc1);
        int playervalue2= getIntFromCard(pc2);

        int correct;

        if (pc1.equals(pc2)) {
            //pair
            correct = pairs[playervalue1-1][dealervalue-1];

        } else if (pc1.equals("A")) {
            //one ace
            correct = ace[playervalue2-2][dealervalue-1]; //subtract for index purposes

        } else if (pc2.equals("A")) {
            //one ace, other card
            correct = ace[playervalue1-2][dealervalue-1]; //subtract for index purposes

        } else {
            //no pair or ace,
            correct = nopair[playervalue1+playervalue2-1][dealervalue-1]; //subtract one because 0 index
        }

        answered(correct, action);
    }

    protected void answered(int correct, int action)
    {
        Button correctButton;
        Button actionButton;

        switch (correct) {
            case 0:
                correctButton = (Button) findViewById(R.id.hitbutton);
                break;
            case 1:
                correctButton = (Button) findViewById(R.id.standbutton);
                break;
            case 2:
                correctButton = (Button) findViewById(R.id.doublebutton);
                break;
            case 3:
                correctButton = (Button) findViewById(R.id.splitbutton);
                break;
            default:
                correctButton = (Button) findViewById(R.id.hitbutton); //might produce counterintuitive result, but at least nothing breaks
        }

        switch (action) {
            case 0:
                actionButton = (Button) findViewById(R.id.hitbutton);
                break;
            case 1:
                actionButton = (Button) findViewById(R.id.standbutton);
                break;
            case 2:
                actionButton = (Button) findViewById(R.id.doublebutton);
                break;
            case 3:
                actionButton = (Button) findViewById(R.id.splitbutton);
                break;
            default:
                actionButton = (Button) findViewById(R.id.hitbutton);
        }

        if (correct == action) {
            //show correct in green
            Drawable d = correctButton.getBackground();
            PorterDuffColorFilter filter = new PorterDuffColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
            d.setColorFilter(filter);

            //show green continue button on bottom
            d = findViewById(R.id.continuebutton).getBackground();
            filter = new PorterDuffColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
            d.setColorFilter(filter);
            ((Button) findViewById(R.id.continuebutton)).setVisibility(View.VISIBLE);

        } else {
            //show correct in green
            Drawable d = correctButton.getBackground();
            PorterDuffColorFilter filter = new PorterDuffColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
            d.setColorFilter(filter);
            ;
            //show action in red
            d = actionButton.getBackground();
            filter = new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
            d.setColorFilter(filter);

            //show red continue button
            d = findViewById(R.id.continuebutton).getBackground();
            filter = new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
            d.setColorFilter(filter);
            ((Button) findViewById(R.id.continuebutton)).setVisibility(View.VISIBLE);
        }

    }

    public void continueClicked(View v)
    {

        Drawable d = findViewById(R.id.hitbutton).getBackground();
        findViewById(R.id.hitbutton).invalidateDrawable(d);
        d.clearColorFilter();

        d = findViewById(R.id.standbutton).getBackground();
        findViewById(R.id.standbutton).invalidateDrawable(d);
        d.clearColorFilter();

        d = findViewById(R.id.doublebutton).getBackground();
        findViewById(R.id.doublebutton).invalidateDrawable(d);
        d.clearColorFilter();

        d = findViewById(R.id.splitbutton).getBackground();
        findViewById(R.id.splitbutton).invalidateDrawable(d);
        d.clearColorFilter();

        ((Button) findViewById(R.id.continuebutton)).setVisibility(View.INVISIBLE);

        generateNewHands();
    }

    protected int getIntFromCard(String arg)
    {
        int cardvalue;
        switch (arg) {
            case "2":
                cardvalue = 2;
                break;
            case "3":
                cardvalue = 3;
                break;
            case "4":
                cardvalue = 4;
                break;
            case "5":
                cardvalue = 5;
                break;
            case "6":
                cardvalue = 6;
                break;
            case "7":
                cardvalue = 7;
                break;
            case "8":
                cardvalue = 8;
                break;
            case "9":
                cardvalue = 9;
                break;
            case "10":
            case "J":
            case "Q":
            case "K":
                cardvalue = 10;
                break;
            case "A":
                cardvalue = 1;
                break;
            default:
                cardvalue = 0;
        }


        return cardvalue;
    }

    public void generateNewHands()
    {
        int min = 1;
        int max = 13;
        String[] result = new String[3];
        int[] rand = new int[3];

        for (int i = 0; i < 3; i++) {
            Random r = new Random();
            //random number from 1 to 13, inclusive
            rand[i] = r.nextInt(max - min + 1) + min;
        }

        for (int i = 0; i < 3; i++) {
            if (rand[i] > 10) {
                switch (rand[i]) {
                    case 11:
                        result[i] = "J";
                        break;
                    case 12:
                        result[i] = "Q";
                        break;
                    case 13:
                        result[i] = "K";
                        break;
                }
            } else if (rand[i] == 1) {
                result[i] = "A";
            } else {
                result[i] = Integer.toString(rand[i]);
            }
        }

        //add strings to @id/dealerval, @id/playercard1, @id/playercard2
        final TextView dealerVal = (TextView) findViewById(R.id.dealerval);
        dealerVal.setText(result[0]);

        final TextView playercard1 = (TextView) findViewById(R.id.playercard1);
        playercard1.setText(result[1]);

        final TextView playercard2 = (TextView) findViewById(R.id.playercard2);
        playercard2.setText(result[2]);
    }
}