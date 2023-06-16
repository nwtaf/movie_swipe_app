package com.example.finalproject;

public class Movie {
    //member variables
    private String posterPath;
    private String title;
    private String overview;
    private String releaseDate;
    private String id;

    //movie constructor
    public Movie(String posterPath, String title, String overview, String releaseDate, String id) {
        this.posterPath = posterPath;
        this.title = title;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.id = id;
    }

    //getters
    public String getPosterPath() {
        return posterPath;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getId() {
        return id;
    }
}

