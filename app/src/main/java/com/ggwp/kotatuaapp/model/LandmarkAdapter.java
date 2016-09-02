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
public class LandmarkAdapter extends ArrayAdapter<Places> {

    private int layoutResource;

    public LandmarkAdapter(Context context, int resource, List<Places> places) {
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
            TextView namalandmark = (TextView) view.findViewById(R.id.nama_landmark_text_view);
            TextView alamatlandmark = (TextView) view.findViewById(R.id.alamat_landmark_text_view);
            TextView teleponlandmark = (TextView) view.findViewById(R.id.telepon_landmark_text_view);
            TextView pricelandmark = (TextView) view.findViewById(R.id.price_landmark_text_view);
            TextView waktulandmark = (TextView) view.findViewById(R.id.waktu_landmark_text_view);

            if (namalandmark != null)
                namalandmark.setText(place.getName());
            if (alamatlandmark != null)
                alamatlandmark.setText(place.getAddress());
            if (teleponlandmark != null)
                teleponlandmark.setText(place.getPhone_number());
            if (pricelandmark != null)
                pricelandmark.setText(place.getPrice());
            if (waktulandmark != null)
                waktulandmark.setText(place.getWaktu());
        }
        return view;
    }
}
