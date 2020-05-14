package com.example.Sic_Bo;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;


import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {


    TextView rollResult;        //Field to hold the rol result text

    int score;                  //Field to hold the score
    int die1, die2, die3;       //Field to hold dice values
    int coins;                  //Field to hold coin number
    int tempCoins;              //Field to hold a temp coin number for the process of showing the right coins number while changing bet amount
    int betAmount;              //Field to hold bet amount
    double winMultiplier;       //Field to hold the win reward multiplier
    boolean small = false;      //Field to hold the small boolean
    boolean guessSmall = false; //Field to hold the guess of player
    boolean win = true;         //Field to hold the game result (win or lose//)
    boolean enoughCoins = true; //Field to hold the boolean value of enough coins
    Random rand;                //Field for simulating dice
    TextView coinsText;         //Field to hold the coin text
    TextView betAmountText;     //Field to hold the bet amount text

    //ArrayList to hold all three dice values
    ArrayList<Integer> dice;

    //ArrayList to hold all three dice images
    ArrayList<ImageView> diceImageViews;

    private Context mContext;
    private PopupWindow betPopUp;
    private RelativeLayout betPopUpLayout;



    AppCompatRadioButton rbSmall, rbLarge; //fields for holding the "small" & "large" button


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Create greeting
        Toast greetingMsg = Toast.makeText(getApplicationContext(), "Welcome to Sic Bo", Toast.LENGTH_SHORT);
        greetingMsg.setGravity(Gravity.CENTER,0, 100);
        greetingMsg.show();

        // Get the application context

        //roll button logic
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                coins = tempCoins; // confirm and set the real coins number after the roll button is click
                rollDice(view);
                if (win){
                    //if player win the round, rewards the coins and reset the betAmount
                    coins += betAmount*winMultiplier;
                    coinsText.setText(""+ coins);
                    betAmount = 0;
                    betAmountText.setText("Bet amount:" + betAmount);
                }else{
                    //if player lost, just show the remaining coins and reset the betAmount
                    coinsText.setText(""+ coins);
                    betAmount = 0;
                    betAmountText.setText("Bet amount:" + betAmount);
                }
            }
        });

        //method
        FloatingActionButton bet = (FloatingActionButton) findViewById(R.id.bet);

        //onClick effect to popup window for choosing bet amount
        bet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyCustomAlertDialog();
            }
        });


        //Initialization
        //initializing instances

        rollResult = (TextView) findViewById(R.id.rollResult);
        coinsText = (TextView) findViewById(R.id.coinsText);
        betAmountText = (TextView) findViewById(R.id.betAmountText);
        coins = 50;
        betAmount = 0;
        betAmountText.setText("Bet: "+betAmount);
        coinsText.setText(""+coins);
        coinsText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_attach_money_orange_24dp,0,0,0);

        //initialing the new random number
        rand = new Random();

        //create array list container for the dice values
        dice = new ArrayList<Integer>();

        //links to images
        ImageView die1Images = (ImageView) findViewById(R.id.die1Image);
        ImageView die2Images = (ImageView) findViewById(R.id.die2Image);
        ImageView die3Images = (ImageView) findViewById(R.id.die3Image);

        //create array list container for three image
        diceImageViews = new ArrayList<ImageView>();
        diceImageViews.add(die1Images);
        diceImageViews.add(die2Images);
        diceImageViews.add(die3Images);

        rbSmall = (AppCompatRadioButton) findViewById(R.id.rbSmall);
        rbLarge = (AppCompatRadioButton) findViewById(R.id.rbLarge);

    }

    public void MyCustomAlertDialog(){
        final Dialog MyDialog = new Dialog(MainActivity.this);
        MyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        MyDialog.setContentView(R.layout.bet_popup);

        Button bet100 = (Button)MyDialog.findViewById(R.id.bet_100);
        Button bet200 = (Button)MyDialog.findViewById(R.id.bet_200);
        Button bet300 = (Button)MyDialog.findViewById(R.id.bet_300);

        ImageButton close = (ImageButton)MyDialog.findViewById(R.id.betPopUpCloseBtn);

        bet100.setEnabled(true);
        close.setEnabled(true);

        bet100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if the coin is enough

                //show the toaster meg
                Toast bet100Msg = Toast.makeText(getApplicationContext(), "Bet 100 coins ", Toast.LENGTH_SHORT);
                bet100Msg.setGravity(Gravity.CENTER,0,0);
                bet100Msg.show();

                //set the betAmount and coin amount
                setBetAmount100(v);
                MyDialog.cancel();
            }
        });

        bet200.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if the coin is enough

                //show the toaster meg
                Toast bet200Msg = Toast.makeText(getApplicationContext(), "Bet 200 coins ", Toast.LENGTH_SHORT);
                bet200Msg.setGravity(Gravity.CENTER,0,0);
                bet200Msg.show();

                //set the betAmount and coin amount
                setBetAmount200(v);
                MyDialog.cancel();
            }
        });

        bet300.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if the coin is enough

                //show the toaster meg
                Toast bet100Msg = Toast.makeText(getApplicationContext(), "Bet 300 coins ", Toast.LENGTH_SHORT);
                bet100Msg.setGravity(Gravity.CENTER,0,0);
                bet100Msg.show();

                //set the betAmount and coin amount
                setBetAmount300(v);
                MyDialog.cancel();
            }
        });


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDialog.cancel();
            }
        });

        MyDialog.show();
    }

    public boolean coinAmountChecker(){
        Toast coinErrorMsg;
        String msg;
        if (coins<=0){
            enoughCoins = false;
            msg = "You don't coins! Please add some coins";
            coinErrorMsg = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
            coinErrorMsg.setGravity(Gravity.CENTER,0,0);
            coinErrorMsg.show();
        }
        else if (coins < betAmount){
            enoughCoins = false;

        }
        else{
            enoughCoins = true;
        }
        return enoughCoins;
    }


    public void setBetAmount100(View v) {
        int oneTimeAmount = 100;
        betAmount = oneTimeAmount;

        if (coinAmountChecker()){
            tempCoins = coins;
            coinsText.setText("" + coins);  //Show original num of coins
            winMultiplier = 1.2;
            tempCoins -= oneTimeAmount;
            coinsText.setText("" + tempCoins);
            betAmountText.setText("Bet amount: " + betAmount);
        }
        else{
            Toast coinErrorMsg;
            String msg;
            msg = "You don't have enough coins! Please choose the lower bet amount or add some coins";
            coinErrorMsg = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
            coinErrorMsg.setGravity(Gravity.CENTER,0,0);
            coinErrorMsg.show();
        }

    }

    public void setBetAmount200(View v) {
        int oneTimeAmount = 200;
        betAmount = oneTimeAmount;

        if (coinAmountChecker()){
            tempCoins = coins;
            coinsText.setText("" + coins);  //Show original num of coins
            winMultiplier = 1.5;

            tempCoins -= oneTimeAmount;
            coinsText.setText("" + tempCoins);
            betAmountText.setText("Bet amount: " + betAmount);
        }
        else{
            Toast coinErrorMsg;
            String msg;
            msg = "You don't have enough coins! Please choose the lower bet amount or add some coins";
            coinErrorMsg = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
            coinErrorMsg.setGravity(Gravity.CENTER,0,0);
            coinErrorMsg.show();
        }

    }

    public void setBetAmount300(View v) {
        int oneTimeAmount = 300;
        betAmount = oneTimeAmount;

        if (coinAmountChecker()){
            tempCoins = coins;
            coinsText.setText("" + coins);  //Show original num of coins
            winMultiplier = 2;
            tempCoins -= oneTimeAmount;
            coinsText.setText("" + tempCoins);
            betAmountText.setText("Bet amount: " + betAmount);
        }
        else{
            Toast coinErrorMsg;
            String msg;
            msg = "You don't have enough coins! Please choose the lower bet amount or add some coins";
            coinErrorMsg = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
            coinErrorMsg.setGravity(Gravity.CENTER,0,0);
            coinErrorMsg.show();
        }
    }




    public void rollDice(View v){
        rollResult.setText("Dice Rolled! To play again, bet first by tapping the lower left 'B', choose you guess then tap the dice button to roll!");



        //generating a random number from 1 to 6;
        die1 = rand.nextInt(6) + 1;
        die2 = rand.nextInt(6) + 1;
        die3 = rand.nextInt(6) + 1;
        int sum = die1 + die2 + die3;

        //set dice values into an ArrayList
        dice.clear();
        dice.add(die1);
        dice.add(die2);
        dice.add(die3);

        for (int dieOfSet = 0; dieOfSet < 3; dieOfSet++){
            String imageName = "die_" + dice.get(dieOfSet) + ".png";
            try {
                InputStream stream = getAssets().open(imageName);
                Drawable d = Drawable.createFromStream(stream,null);
                diceImageViews.get(dieOfSet).setImageDrawable(d);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //displaying the result msg
        String msg;

        //set win boolean value according to sum of three dies and the player's guess
        if (sum <= 9){
            small = true;  //result is Small
            if (guessSmall == small){    //if guessing is correct
                msg = "You rolled a " + sum + " ! It's Small! You win!";
                win = true;
            }else{                      //if guessing is wrong
                msg = "You rolled a " + sum + " ! It's Small! You lose!";
                win = false;
            }
        }else{
            small = false;  //result is Large
            if (guessSmall == small){    //if guessing is correct
                msg = "You rolled a " + sum + " ! It's Large! You win!";
                win = true;
            }else{                      //if guessing is wrong
                msg = "You rolled a " + sum + " ! It's Large! You lose!";
                win = false;
            }
        }
        Toast toastMsg = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
        toastMsg.setGravity(Gravity.CENTER,Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL);
        toastMsg.show();
    }


    //logic for bet small or large buttons
    public void onRadioButtonClicked(View view){

        boolean isSelected = ((AppCompatRadioButton)view).isChecked();
        switch (view.getId()){
            case R.id.rbSmall:
                if (isSelected){
                    rbSmall.setTextColor(Color.WHITE);
                    rbLarge.setTextColor(Color.RED);
                    guessSmall = true;
                }
                break;
            case R.id.rbLarge:
                if (isSelected) {
                    rbLarge.setTextColor(Color.WHITE);
                    rbSmall.setTextColor(Color.RED);
                    guessSmall = false;
                }
                break;
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
