package com.alphaa.inzodiac.tabmodule.activity.aboutmodule;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.alphaa.inzodiac.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        TextView header1 = findViewById(R.id.header1);
        header1.setText(getIntent().getStringExtra("name"));
    }
}