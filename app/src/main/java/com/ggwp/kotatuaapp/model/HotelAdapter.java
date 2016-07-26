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
public class HotelAdapter extends ArrayAdapter<Places> {

    private int layoutResource;

    public HotelAdapter(Context context, int resource, List<Places> places) {
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
            TextView namaHotel = (TextView) view.findViewById(R.id.nama_hotel_text_view);
            TextView alamatHotel = (TextView) view.findViewById(R.id.alamat_hotel_text_view);
            TextView teleponHotel = (TextView) view.findViewById(R.id.telepon_hotel_text_view);
            TextView priceHotel = (TextView) view.findViewById(R.id.price_hotel_text_view);
            TextView ratingHotel = (TextView) view.findViewById(R.id.rating_hotel_text_view);

            if (namaHotel != null)
                namaHotel.setText(place.getName());
            if (alamatHotel != null)
                alamatHotel.setText(place.getAddress());
            if (teleponHotel != null)
                teleponHotel.setText(place.getPhone_number());
            if (priceHotel != null)
                priceHotel.setText(place.getPrice());
            if (ratingHotel != null)
                ratingHotel.setText(place.getRating());
        }
        return view;
    }
}
