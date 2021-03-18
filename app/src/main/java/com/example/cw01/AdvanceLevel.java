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

public class AdvanceLevel extends AppCompatActivity {
    private static final String TAG = AdvanceLevel.class.getSimpleName();
    ImageView imageView_1,imageView_2,imageView_3;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advance_level);

        // Initialize all variables
        imageView_1 = findViewById(R.id.advance_imageView_1);
        imageView_2 = findViewById(R.id.advance_imageView_2);
        imageView_3 = findViewById(R.id.advance_imageView_3);
        dialog = new Dialog(this);

        /*
        * Create a object of MainActivity to get car images from hash map
        * then get three random images from that hash map
        * then display these names of images on the logcat pane
        */
        MainActivity mainActivity = new MainActivity();
        Random random = new Random();
        String image_1 = mainActivity.keyArray[random.nextInt(mainActivity.hashMap.size())];
        String image_2 = mainActivity.keyArray[random.nextInt(mainActivity.hashMap.size())];
        String image_3 = mainActivity.keyArray[random.nextInt(mainActivity.hashMap.size())];
        Log.d(TAG+" - Car Images ",image_1+", "+image_2+", "+image_3);

        // set images to the imageViews
        int imageResource_1 = getResources().getIdentifier(image_1,"drawable","com.example.cw01");
        int imageResource_2 = getResources().getIdentifier(image_2,"drawable","com.example.cw01");
        int imageResource_3 = getResources().getIdentifier(image_3,"drawable","com.example.cw01");
        imageView_1.setImageResource(imageResource_1);
        imageView_2.setImageResource(imageResource_2);
        imageView_3.setImageResource(imageResource_3);

        // set the car actual names fot the image view content description
        imageView_1.setContentDescription(mainActivity.hashMap.get(image_1));
        imageView_2.setContentDescription(mainActivity.hashMap.get(image_2));
        imageView_3.setContentDescription(mainActivity.hashMap.get(image_3));
    }

    // method for back to home activity
    public void backHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    int attempts = 3;
    int score = 0;
    /*
    * method for check if all guessed answers are correct or not
    * user have only three attempts
    * increase the count, every time button was clicked
    */
    @SuppressLint("SetTextI18n")
    public void submitBtn(View view) {
        EditText editText_1 = findViewById(R.id.advance_guess_plainText_1);
        EditText editText_2 = findViewById(R.id.advance_guess_plainText_2);
        EditText editText_3 = findViewById(R.id.advance_guess_plainText_3);
        TextView correctAns_1 = findViewById(R.id.advance_carName_text_1);
        TextView correctAns_2 = findViewById(R.id.advance_carName_text_2);
        TextView correctAns_3 = findViewById(R.id.advance_carName_text_3);
        TextView scoreText = findViewById(R.id.score_text);

        // check if user click submit button with all empty guesses
        if (editText_1.getText().toString().isEmpty()
                && editText_2.getText().toString().isEmpty()
                && editText_3.getText().toString().isEmpty()){
            Toast.makeText(this, "Guess car names",Toast.LENGTH_SHORT).show();
            return;
        }

        // reduce the number of attempts
        attempts--;

        /*
         * check user input guess car names
         * wrong guesses display in red color
         * correct guesses display in green and set to non editable
         */
        if (imageView_1.getContentDescription().toString().toLowerCase().equals(editText_1.getText().toString().toLowerCase())){
            editText_1.setEnabled(false);
            editText_1.setTextColor(Color.parseColor("#25E56F"));
            score++;
        } else if (!editText_1.getText().toString().isEmpty()){
            editText_1.setTextColor(Color.parseColor("#E52525"));
        }

        if (imageView_2.getContentDescription().toString().toLowerCase().equals(editText_2.getText().toString().toLowerCase())){
            editText_2.setEnabled(false);
            editText_2.setTextColor(Color.parseColor("#25E56F"));
            score++;
        } else if (!editText_2.getText().toString().isEmpty()){
            editText_2.setTextColor(Color.parseColor("#E52525"));
        }

        if (imageView_3.getContentDescription().toString().toLowerCase().equals(editText_3.getText().toString().toLowerCase())){
            editText_3.setEnabled(false);
            editText_3.setTextColor(Color.parseColor("#25E56F"));
            score++;
        } else if (!editText_3.getText().toString().isEmpty()){
            editText_3.setTextColor(Color.parseColor("#E52525"));
        }

        // check all the attempts are used
        if (attempts == 0){
            // display the wrong popup window
            if (score != 3){
                // set correct layout to the dialog box
                dialog.setContentView(R.layout.wrong_popup_window);

                // set transparent dialog box background
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                // show popup window
                dialog.show();

                // method for close button for close the dialog box
                Button cCloseBtn = dialog.findViewById(R.id.w_close_btn);
                cCloseBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                Log.d(TAG+" - User guesses are","wrong");

            }

            // display correct answer bellow the wrong guess test edit
            if (!imageView_1.getContentDescription().toString().toLowerCase().equals(editText_1.getText().toString().toLowerCase())){
                correctAns_1.setText(imageView_1.getContentDescription());
            }

            if (!imageView_2.getContentDescription().toString().toLowerCase().equals(editText_2.getText().toString().toLowerCase())){
                correctAns_2.setText(imageView_2.getContentDescription());
            }

            if (!imageView_3.getContentDescription().toString().toLowerCase().equals(editText_3.getText().toString().toLowerCase())){
                correctAns_3.setText(imageView_3.getContentDescription());
            }

            // hide the submit button and display the next button
            view.setVisibility(View.INVISIBLE);
        }

        // display the scores
        scoreText.setText(""+score);
        scoreText.setTextSize(20);

        /*
        * user guessed all car names
        * display correct message and next button
        * else display remains attempts in toast message
        */
        if (score == 3){
            // set the wrong popup layout to the dialog box
            dialog.setContentView(R.layout.correct_popup_window);

            // set dialog box background to transparent
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            // show dialog
            dialog.show();

            // set close button action
            Button wCloseBtn = dialog.findViewById(R.id.c_close_btn);
            wCloseBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            // set submit button to invisible
            view.setVisibility(View.INVISIBLE);
            Log.d(TAG+" - User guesses are","correct");
        } else if (attempts == 2 || attempts == 1){
            Toast.makeText(this,attempts+" attempt remains",Toast.LENGTH_LONG).show();
        }
        score = 0;
    }

    /*
    * refresh the activity to continue the game
    * refresh the game without any transitions
    */
    public void nextBtn(View view) {
        finish();
        overridePendingTransition(0,0);
        startActivity(getIntent());
    }
}