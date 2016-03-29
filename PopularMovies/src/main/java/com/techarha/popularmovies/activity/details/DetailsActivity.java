package com.techarha.popularmovies.activity.details;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.techarha.popularmovies.R;

public class DetailsActivity extends AppCompatActivity {

    Bundle extra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
