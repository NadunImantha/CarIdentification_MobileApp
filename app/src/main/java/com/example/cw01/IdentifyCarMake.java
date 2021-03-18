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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class IdentifyCarMake extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = IdentifyCarImage.class.getSimpleName();
    private String selectCarName,carName;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify_car_make);

        // initialize the variables
        ImageView imageView = findViewById(R.id.make_imageView);
        Spinner spinner = findViewById(R.id.make_select_spinner);
        dialog = new Dialog(this);

        /*
        * create a arrayAdapter to get and store the data
        * set data to the adepter
        * set the custom spinner layouts
        */
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.custom_spinner_layout,
                getResources().getStringArray(R.array.car_names_array));
        adapter.setDropDownViewResource(R.layout.custom_dropdown_layout);
        spinner.setAdapter(adapter);
        // get user select item from spinner
        spinner.setOnItemSelectedListener(this);

        // create main activity object to get the hashMap to get the images
        MainActivity mainActivity = new MainActivity();

        // get a random car image and car name from keyArray in MainActivity
        Random random = new Random();
        String image = mainActivity.keyArray[random.nextInt(mainActivity.hashMap.size())];
        carName = mainActivity.hashMap.get(image);

        // display random car image name on the logcat pane
        Log.d(TAG+" - Car Image Name ",image);

        // get image resource id of the image and set to the imageView
        int resourceId = getResources().getIdentifier(image,"drawable","com.example.cw01");
        imageView.setImageResource(resourceId);
    }

    // method for go back to the home activity
    public void backHome(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    /*
    * display a toast message for user select item
    * if item position not equal to 0
    * because dismiss the select text from item list
    */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position != 0){
            Toast.makeText(this,parent.getItemAtPosition(position).toString(),Toast.LENGTH_SHORT).show();
            selectCarName = parent.getItemAtPosition(position).toString();
        } else {
            selectCarName = "null";
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /*
    * method for clicked identify btn
    */
    @SuppressLint("LongLogTag")
    public void identifyBtn(View view) {
        TextView correctCarName = findViewById(R.id.make_carName_text);

        // check if the user direct click the identify button or select Select item
        if (selectCarName.equals("null")){
            Toast.makeText(this,"Select a car name",Toast.LENGTH_SHORT).show();
            return;
        } else {
            // display the next button
            view.setVisibility(View.INVISIBLE);
        }

        // check user select correct or not
        if (selectCarName.toLowerCase().equals(carName.toLowerCase())){
            // set correct layout to the dialog
            dialog.setContentView(R.layout.correct_popup_window);

            // set background color to transparent
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            // show correct dialog
            dialog.show();

            // close button to dismiss the popup window
            Button closeCorrectPopup = dialog.findViewById(R.id.c_close_btn);
            closeCorrectPopup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            Log.d(TAG+" - user identification is","correct");

        } else {
            // set the wrong layout to the dialog
            dialog.setContentView(R.layout.wrong_popup_window);

            // set background transparent
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            // show dialog box
            dialog.show();

            // identify close button and close the dialog box
            Button closeWrongPopup = dialog.findViewById(R.id.w_close_btn);
            closeWrongPopup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            // display the correct car name
            correctCarName.setText(carName.toUpperCase());
            Log.d(TAG+" - user identification is","wrong");
        }
    }

    // method for refresh the layout with another car image
    // refresh without transition
    public void nextBtn(View view) {
        finish();
        overridePendingTransition(0,0);
        startActivity(getIntent());
    }
}