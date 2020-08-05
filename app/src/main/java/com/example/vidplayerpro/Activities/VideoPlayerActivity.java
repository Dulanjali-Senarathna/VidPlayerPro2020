package com.example.vidplayerpro.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.vidplayerpro.R;

public class VideoPlayerActivity extends AppCompatActivity {

    Uri videoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        Intent intent = getIntent();

        if(intent!=null)
        {
            String uri_str= intent.getStringExtra("videoUri");
            videoUri = Uri.parse(uri_str);
        }
    }
}
