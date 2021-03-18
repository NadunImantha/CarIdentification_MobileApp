package com.example.cw01;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class IdentifyCarImage extends AppCompatActivity {
    private static final String TAG = IdentifyCarMake.class.getSimpleName();
    private TextView carNameTextView;
    private ImageView imageView_1,imageView_2,imageView_3;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify_car_image);

        // Initialize the three of ImageViews
        imageView_1 = findViewById(R.id.carImage_imageView_1);
        imageView_2 = findViewById(R.id.carImage_imageView_2);
        imageView_3 = findViewById(R.id.carImage_imageView_3);
        dialog = new Dialog(this);

        // initialize car name textView
        carNameTextView = findViewById(R.id.carImage_carName_text);

        // create a MainActivity object
        MainActivity mainActivity = new MainActivity();

        // Create a random method
        Random random = new Random();

        // do the process while to get unique images
        while (true){
            // get random three images names from the MainActivity keyArray list
            String image_1 = mainActivity.keyArray[random.nextInt(mainActivity.hashMap.size())];
            String image_2 = mainActivity.keyArray[random.nextInt(mainActivity.hashMap.size())];
            String image_3 = mainActivity.keyArray[random.nextInt(mainActivity.hashMap.size())];

            // get the random cars brand names
            String carName_1 = mainActivity.hashMap.get(image_1);
            String carName_2 = mainActivity.hashMap.get(image_2);
            String carName_3 = mainActivity.hashMap.get(image_3);

            /*
            * add to a array list car name from exist random car images
            * set a random car name to the text view
            * display the user choice car name on the logcat pane
            */
            int randomNumber = random.nextInt(3);
            List<String> listOfCarNames = Arrays.asList(carName_1,carName_2,carName_3);
            carNameTextView.setText(listOfCarNames.get(randomNumber));
            Log.d(TAG+" - Car Name",listOfCarNames.get(randomNumber));

            /*
             * check if there have same car models
             * set these images to the three of imageViews
             */
            if (!carName_1.equals(carName_2) && !carName_1.equals(carName_3) && !carName_2.equals(carName_3)){
                int imageResourceId_1 = getResources().getIdentifier(image_1,"drawable","com.example.cw01");
                int imageResourceId_2 = getResources().getIdentifier(image_2,"drawable","com.example.cw01");
                int imageResourceId_3 = getResources().getIdentifier(image_3,"drawable","com.example.cw01");
                imageView_1.setImageResource(imageResourceId_1);
                imageView_2.setImageResource(imageResourceId_2);
                imageView_3.setImageResource(imageResourceId_3);


                // set the car actual names fot the image view content description
                imageView_1.setContentDescription(carName_1);
                imageView_2.setContentDescription(carName_2);
                imageView_3.setContentDescription(carName_3);

                // display the random three car names on the logcat pane
                Log.d(TAG+" - Car Image Names ", image_1+", "+image_2+", "+image_3);
                break;
            }
        }
    }



    // method go go back to the home activity
    public void backHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    // get the select image view description text
    CharSequence descriptionText = "imageDescription";

    // image click method
    public void clickImg(View view) {
        // set background border from drawable resources to all image views
        imageView_1.setBackground(getResources().getDrawable(R.drawable.image_border));
        imageView_2.setBackground(getResources().getDrawable(R.drawable.image_border));
        imageView_3.setBackground(getResources().getDrawable(R.drawable.image_border));

        // get the select image description text
        descriptionText = view.getContentDescription();

        // set the background color from drawable resources
        view.setBackground(getResources().getDrawable(R.drawable.image_select_background));
    }


    // method for check user choice answer
    public void submitBtn(View view) {
        if (descriptionText.equals(carNameTextView.getText())){
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

            Log.d(TAG+" - user choice is","correct");
            view.setVisibility(View.INVISIBLE);
        }
        else if (descriptionText.equals("imageDescription")){
            Toast.makeText(this,"Select a image.",Toast.LENGTH_SHORT).show();
        }
        else if (!descriptionText.equals(carNameTextView.getText())){
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

            Log.d(TAG+" - user choice is","wrong");
            view.setVisibility(View.INVISIBLE);
        }
    }


    /*
    * restart the activity without any transitions
    * refresh new random images
    */
    public void nextBtn(View view) {
        finish();
        overridePendingTransition(0,0);
        startActivity(getIntent());
    }
}