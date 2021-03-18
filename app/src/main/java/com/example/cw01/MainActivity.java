package com.example.cw01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.HashMap;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    /*
     * create a hash map to get car image with car name
     * key is the image name
     * value is the actual car name
     * add 43 car images
     */
    public HashMap<String,String> hashMap = new HashMap<String, String>(){
        {
            put("cadillac_1","Cadillac");  put("cadillac","Cadillac");  put("benz","Benz");
            put("bmw","BMW");   put("toyota","Toyota"); put("porsche","Porsche");
            put("honda","Honda");   put("land_rover","Land Rover"); put("ferrari","Ferrari");
            put("bentley","Bentley");   put("aston_martin","Aston Martin"); put("audi","Audi");
            put("jaguar","Jaguar");   put("audi_1","Audi"); put("bmw_1","BMW");
            put("jeep","Jeep"); put("chevrolet","Chevrolet");   put("lamborghini","Lamborghini");
            put("nisan","Nisan");   put("mitsubishi","Mitsubishi"); put("toyota_1","Toyota");
            put("kia","KIA");   put("lexus","Lexus"); put("lexus_1","Lexus");
            put("audi_2","Audi"); put("audi_3","Audi"); put("bmw_2","BMW");
            put("lotus","Lotus");   put("ford","Ford"); put("land_rover_1","Land Rover");
            put("bmw_4","BMW"); put("hyundai","Hyundai"); put("benz_1","Benz");
            put("tesla","Tesla"); put("benz_2","Benz"); put("land_rover_2","Land Rover");
            put("lexus_3","Lexus"); put("toyota_2","Toyota"); put("toyota_3","Toyota");
            put("nisan_1","Nisan"); put("hyundai_1","Hyundai"); put("audi_4","Audi");
            put("suzuki","Suzuki");
        }
    };

    /*
    * in HashMap can not get index directly
    * therefore all the keys in the hashMap set in to the array list
    */
    public Set<String> keySet = hashMap.keySet();
    // convert keySet to the array using toArray method
    public String[] keyArray = keySet.toArray(new String[hashMap.size()]);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // method for launch the identify car make activity
    public void identifyCarMakeBtn(View view) {
        Intent intent = new Intent(this,IdentifyCarMake.class);
        startActivity(intent);
    }

    // method for launch the hint activity
    public void hintBtn(View view) {
        Intent intent = new Intent(this,Hint.class);
        startActivity(intent);
    }

    // method for launch the Identify car image activity
    public void carImageBtn(View view) {
        Intent intent = new Intent(this,IdentifyCarImage.class);
        startActivity(intent);
    }

    // method for launch the advance activity
    public void advanceBtn(View view) {
        Intent intent = new Intent(this,AdvanceLevel.class);
        startActivity(intent);
    }

    // Lifecycle callback methods
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG,"onRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"onPause");
    }

    @Override
    protected void onStop() {
        Log.d(TAG,"onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy");
    }
}