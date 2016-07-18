package com.ggwp.kotatuaapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.github.aakira.expandablelayout.Utils;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationItem;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationView;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import com.synnapps.carouselview.ViewListener;

import java.util.ArrayList;
import java.util.List;

import mehdi.sakout.fancybuttons.FancyButton;


public class MainActivity extends AppCompatActivity {
    private MaterialSearchView searchView;
    CarouselView customCarouselView;
    int NUMBER_OF_PAGES = 4;

    int[] sampleImages = {R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher};
    String[] sampleTitles = {"Orange", "Grapes", "Strawberry", "Cherry", "Apricot"};
    String[] sampleNetworkImageURLs = {
            "https://placeholdit.imgix.net/~text?txtsize=15&txt=image1&txt=350%C3%97150&w=350&h=150",
            "https://placeholdit.imgix.net/~text?txtsize=15&txt=image2&txt=350%C3%97150&w=350&h=150",
            "https://placeholdit.imgix.net/~text?txtsize=15&txt=image3&txt=350%C3%97150&w=350&h=150",
            "https://placeholdit.imgix.net/~text?txtsize=15&txt=image4&txt=350%C3%97150&w=350&h=150",
            "https://placeholdit.imgix.net/~text?txtsize=15&txt=image5&txt=350%C3%97150&w=350&h=150"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        BottomNavigationItem bottomNavigationItem = new BottomNavigationItem
                ("Record", ContextCompat.getColor(this, R.color.firstColor), R.mipmap.ic_launcher);
        BottomNavigationItem bottomNavigationItem1 = new BottomNavigationItem
                ("Like", ContextCompat.getColor(this, R.color.secondColor), R.mipmap.ic_launcher);
        bottomNavigationView.addTab(bottomNavigationItem);
        bottomNavigationView.addTab(bottomNavigationItem1);

        customCarouselView = (CarouselView) findViewById(R.id.carouselView);
        customCarouselView.setPageCount(NUMBER_OF_PAGES);
        // set ViewListener for custom view
        customCarouselView.setViewListener(viewListener);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Do some magic
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
            }
        });

        FancyButton sejarahButton = (FancyButton) findViewById(R.id.btn_sejarah);
        sejarahButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SejarahActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout mapsAndDirectionLinearLayout = (LinearLayout) findViewById(R.id.mapsAndDirectionLinearLayout);
        TextView mapsAndDirectionTextView = (TextView) findViewById(R.id.mapsAndDirectionTextView);
        mapsAndDirectionLinearLayout.setOnClickListener(mapsClickListener);
        mapsAndDirectionTextView.setOnClickListener(mapsClickListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }


    ViewListener viewListener = new ViewListener() {

        @Override
        public View setViewForPosition(int position) {
            View customView = getLayoutInflater().inflate(R.layout.view_custom, null);
            //set view attributes here
            TextView labelTextView = (TextView) customView.findViewById(R.id.labelTextView);
            ImageView fruitImageView = (ImageView) customView.findViewById(R.id.fruitImageView);

            fruitImageView.setImageResource(sampleImages[position]);
            labelTextView.setText(sampleTitles[position]);

            //customView.setIndicatorGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP);

            return customView;
        }


    };

    View.OnClickListener mapsClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, MapsActivity.class);
            startActivity(intent);
        }
    };
}
