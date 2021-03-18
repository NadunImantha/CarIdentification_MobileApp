package com.example.cw01;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class Hint extends AppCompatActivity {
    private static final String TAG = Hint.class.getSimpleName();
    private TextView hint;
    private String hintText = "";
    String carName;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hint);

        // initialize the variables
        ImageView carImageView = findViewById(R.id.hint_imageView);
        hint = findViewById(R.id.hint_carName_text);
        dialog = new Dialog(this);

        // create a main activity object to get the hash map
        MainActivity mainActivity = new MainActivity();

        // get the keyArray from the mainActivity and get a random image from that
        Random random = new Random();
        String image = mainActivity.keyArray[random.nextInt(mainActivity.hashMap.size())];

        // get the car name
        carName = mainActivity.hashMap.get(image);

        // display the random image name on the logcat pane
        Log.d(TAG+" - Car Image Name ",image);

        // get the image resource id and set to the image view
        int imgResourceId = getResources().getIdentifier(image,"drawable","com.example.cw01");
        carImageView.setImageResource(imgResourceId);

        // displaying the actual car name as dashes
        for (int i=0; i<carName.length(); i++){
            hintText += "-";
        }
        hint.setText(hintText);
    }

    // method for go back to the home activity
    public void backHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    // get attempts
    int wrongAttempts = 3;

    // method for guess the car name character by character
    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    public void submitBtn(View view) {
        EditText guessEditText = findViewById(R.id.hint_guess_plainText);
        int checkGuess = 0;

        // get user input guess letter
        String guess = guessEditText.getText().toString().toUpperCase();

        // check if the edit text is empty
        if (guess.isEmpty()){
            Toast.makeText(this, "Guess a character",Toast.LENGTH_SHORT).show();
            return;
        }

        // check user input guess
        for (int i=0; i<carName.length(); i++){
            char carNameLetter = carName.toUpperCase().charAt(i);
            char guessLetter = guess.charAt(0);

            // turn hint dashes string to char array
            char[] hintCharArray = hintText.toCharArray();

            // replace the dash
            if (carNameLetter == guessLetter){
                hintCharArray[i] = guessLetter;
                hintText = String.valueOf(hintCharArray);
                checkGuess = 1;
            }
        }

        // set hint text to back
        hint.setText(hintText);

        // count wrong guesses
        if (checkGuess == 0){
            wrongAttempts--;
            // display a toast message
            if (wrongAttempts != 0){
                Toast.makeText(this,wrongAttempts+" attempts remains",Toast.LENGTH_SHORT).show();
            }
        }

        /*
        * display wrong popup window with correct car name
        * attempts = 0
        * and
        * guess word is wrong
        */
        if (wrongAttempts == 0 && !hintText.toUpperCase().equals(carName.toUpperCase())){
            // set the wrong window layout to the dialog
            dialog.setContentView(R.layout.wrong_popup_window);

            // set popup window background transparent
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            // show popup
            dialog.show();

            // method for close button to close the popup window
            Button wCloseBtn = dialog.findViewById(R.id.w_close_btn);
            wCloseBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            // display correct car name
            hint.setText(carName);
            hint.setTextColor(Color.parseColor("#FFFF29"));

            Log.d(TAG+" - guess is","wrong");
            view.setVisibility(View.INVISIBLE);
        }

        // display the correct message if user guess correctly
        if (hintText.toUpperCase().equals(carName.toUpperCase())){
            // set the wrong window layout to the dialog
            dialog.setContentView(R.layout.correct_popup_window);

            // set popup window background transparent
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            // show popup
            dialog.show();

            // method for close button to close the popup window
            Button wCloseBtn = dialog.findViewById(R.id.c_close_btn);
            wCloseBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });


            Log.d(TAG+" - guess is","correct");
            view.setVisibility(View.INVISIBLE);
        }

        // clear text edit
        guessEditText.setText(null);
    }

    /*
    * next button activity
    * refresh the activity without transitions
    */
    public void nextBtn(View view) {
        finish();
        overridePendingTransition(0,0);
        startActivity(getIntent());
    }
}