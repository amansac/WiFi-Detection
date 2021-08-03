package com.aman.sdk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.wear.ambient.AmbientModeSupport;

import com.aman.sdk.databinding.ActivityMainBinding;

public class WifiActivity extends FragmentActivity implements AmbientModeSupport.AmbientCallbackProvider {

    // declaring objects of Button class
//    private Button start, stop;
//    private TextView mTextView;
//    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        mTextView = binding.textView1;

        startService(new Intent(this, MyService.class));

        // assigning ID of startButton/
    }

    @Override
    public AmbientModeSupport.AmbientCallback getAmbientCallback() {
//        final AmbientModeSupport.AmbientCallback ambientCallback = new AmbientModeSupport.AmbientCallback();
//        return ambientCallback;
        return null;
    }

//    public void onClick(View view) {
//
//        // process to be performed
//        // if start button is clicked
//        if(view == start){
//
//            // starting the service
//            startService(new Intent( this, MyService.class ) );
//        }
//
//        // process to be performed
//        // if stop button is clicked
//        else if (view == stop){
//
//            // stopping the service
//            stopService(new Intent( this, MyService.class ) );
//
//        }
//  }
}