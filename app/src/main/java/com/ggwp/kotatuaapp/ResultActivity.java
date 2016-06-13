package com.ggwp.kotatuaapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by kuwali on 6/13/2016.
 */
public class ResultActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Intent i = getIntent();
        TextView Result = (TextView) findViewById(R.id.resultTextView);
        Result.setText(i.getStringExtra("Ganteng"));
    }
}
