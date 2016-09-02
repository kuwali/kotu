package com.ggwp.kotatuaapp.model;

/**
 * Created by kuwali on 7/23/16.
 */
public class Places {

    private String name;
    private double lat;
    private double lng;
    private String place_id;
    private String address;
    private String phone_number;
    private String vicinity;
    private String price;
    private String rating;
    private String waktu;
    private String cuisine;

    public Places(String name, double lat, double lng, String place_id, String vicinity, String address, String phone_number, String price, String rating) {
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.place_id = place_id;
        this.vicinity = vicinity;
        this.address = address;
        this.phone_number = phone_number;
        this.price = price;
        this.rating = rating;
    }

    public Places(String name, double lat, double lng, String place_id, String address, String phone_number, String price) {
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.place_id = place_id;
        this.address = address;
        this.phone_number = phone_number;
        this.vicinity = "";
        this.price = price;
    }


    public Places(double lat, double lng, String name, String place_id, String address, String phone_number, String price, String waktu, String cuisine) {
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.place_id = place_id;
        this.address = address;
        this.phone_number = phone_number;
        this.price = price;
        this.waktu = waktu;
        this.cuisine = cuisine;
    }

    public Places(double lat, double lng, String name, String place_id, String address, String phone_number, String price, String waktu) {
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.place_id = place_id;
        this.address = address;
        this.phone_number = phone_number;
        this.price = price;
        this.waktu = waktu;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public String getPrice() { return this.price; }

    public void setPrice(String price) { this.price = price; }

    public String getRating() { return this.rating; }

    public void setRating(String rating) { this.rating = rating; }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getcuisine() {
        return cuisine;
    }

    public void setcuisine(String cuisine) {
        this.cuisine = cuisine;
    }
}
