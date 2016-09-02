package com.ggwp.kotatuaapp.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ggwp.kotatuaapp.R;

import java.util.List;

/**
 * Created by kuwali on 7/23/16.
 */
public class RestaurantAdapter extends ArrayAdapter<Places> {

    private int layoutResource;

    public RestaurantAdapter(Context context, int resource, List<Places> places) {
        super(context, resource, places);
        this.layoutResource = resource;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(layoutResource, null);
        }

        Places place = getItem(position);
        if (place != null) {
            TextView namaRestaurant = (TextView) view.findViewById(R.id.nama_Restaurant_text_view);
            TextView alamatRestaurant = (TextView) view.findViewById(R.id.alamat_Restaurant_text_view);
            TextView teleponRestaurant = (TextView) view.findViewById(R.id.telepon_Restaurant_text_view);
            TextView priceRestaurant = (TextView) view.findViewById(R.id.price_Restaurant_text_view);
            TextView waktuRestaurant = (TextView) view.findViewById(R.id.waktu_Restaurant_text_view);
            TextView cuisineRestaurant = (TextView) view.findViewById(R.id.cuisine_Restaurant_text_view);

            if (namaRestaurant != null)
                namaRestaurant.setText(place.getName());
            if (alamatRestaurant != null)
                alamatRestaurant.setText(place.getAddress());
            if (teleponRestaurant != null)
                teleponRestaurant.setText(place.getPhone_number());
            if (priceRestaurant != null)
                priceRestaurant.setText(place.getPrice());
            if (waktuRestaurant != null)
                waktuRestaurant.setText(place.getWaktu());
            if (cuisineRestaurant != null)
                cuisineRestaurant.setText(place.getcuisine());
        }
        return view;
    }
}
