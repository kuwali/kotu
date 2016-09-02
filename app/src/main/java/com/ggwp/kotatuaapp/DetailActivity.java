package com.ggwp.kotatuaapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by kuwali on 7/26/16.
 */
public class DetailActivity extends AppCompatActivity {
    private ImageView detailImage;
    private TextView detailText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detailImage = (ImageView) findViewById(R.id.image_detail);
        detailText = (TextView) findViewById(R.id.text_detail);

    }
}
