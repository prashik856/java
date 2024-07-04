package com.prashik.flexy.model;

import com.prashik.flexy.utils.Utils;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * Class to store Stars information.
 *
 * @author prashik
 */
public class Star {
    private String id;
    private String name;
    private ArrayList<Movie> movies;

    /**
     * Constructor using name and movies.
     *
     * @param name the star name
     * @param movies the list of movies
     */
    public Star(String name, ArrayList<Movie> movies) throws NoSuchAlgorithmException {
        this.name = name;
        this.movies = movies;
        this.id = Utils.getMD5HashValue(name);
    }

    /**
     * Empty Constructor
     */
    public Star() {

    }

    /**
     * Getter for name.
     * @return the star name.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for name.
     * @param name the name of the star.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for all movies.
     * @return the list of all movies of the star.
     */
    public ArrayList<Movie> getMovies() {
        return movies;
    }

    /**
     * Setter for all movies.
     * @param movies The list of all movies of the star.
     */
    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    /**
     * Getter for id.
     * @return the id generated of this star.
     */
    public String getId() {
        return id;
    }

    /**
     * Setter for id.
     * @param id the id generated of this star.
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
        return "{id: " + this.id + " , name: " + this.name + ", movies: " + this.movies.toString() + "}";
    }
}
