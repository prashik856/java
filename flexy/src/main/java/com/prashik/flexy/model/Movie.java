package com.prashik.flexy.model;

import com.prashik.flexy.utils.Utils;

import java.security.NoSuchAlgorithmException;

/**
 * Class to store Movie Object.
 *
 * @author prashik
 */
public class Movie {
    private String id;
    private String name;
    private String location;

    /**
     * Empty constructor.
     */
    public Movie() {
    }

    /**
     * Constructor for Movie class.
     * @param name the name of the movie.
     * @param location the location of the movie in local file system.
     */
    public Movie(String name, String location) throws NoSuchAlgorithmException {
        this.name = name;
        this.location = location;
        this.id = Utils.getMD5HashValue(location);
    }

    /**
     * Getter for getting file location from the server.
     * @return the location of the file.
     */
    public String getLocation() {
        return location;
    }

    /**
     * The Setter for the location.
     * @param location the location of the file in the server.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * The Getter for getting movie name.
     * @return the name of the movie.
     */
    public String getName() {
        return name;
    }

    /**
     * The Setter for the movie name.
     * @param name the name of the movie
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for id.
     * @return the id of the movie.
     */
    public String getId() {
        return id;
    }

    /**
     * Setter for id.
     * @param id the id generated of this movie.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * The Overridden method to return class as string.
     * @return class represented as a string.
     */
    @Override
    public String toString() {
        return "{id: " + this.id + " , name: " + this.name + ", location: " + this.location + "}";
    }
}
