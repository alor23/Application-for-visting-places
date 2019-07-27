package com.example.ApplicationForVistingplaces;

public class Places {


    private String title;
    private String description;
    private String url;
    private double latitude;
    private double longitiude;


    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitiude() {
        return longitiude;
    }

    public Places(String title, String description, String url, double latitude, double longitiude) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.latitude = latitude;
        this.longitiude = longitiude;
    }
}
