package com.prashik.flexy.utils;

import com.prashik.flexy.model.Movie;
import com.prashik.flexy.model.Star;
import jakarta.json.Json;
import jakarta.json.JsonBuilderFactory;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Helper class which contains common utility functions
 *
 * @author prashik
 */
public class Utils {
    private static final Logger logger = getLogger(Utils.class.getName());

    /**
     * Empty private constructor so that this class cannot be instantiated.
     */
    private Utils() {

    }

    /**
     * Helper method to create logger for a class
     *
     * @param className the name of the class logger belongs to
     * @return the built logger for the class
     */
    public static Logger getLogger(String className) {
        Configurator.setLevel("com.prashik.flexy", Level.DEBUG);
        Configurator.setRootLevel(Level.DEBUG);
        return LogManager.getLogger(className);
    }

    /**
     * Helper method to create JSON Builder
     *
     * @return the empty JSON Builder created
     */
    public static JsonBuilderFactory getJSONBuilder() {
        return Json.createBuilderFactory(Collections.emptyMap());
    }

    /**
     * Function to check if the directory path provided in config path exists or not.
     *
     * @param directory The local directory present in the server
     * @param API The target API of the path provided
     * @return the path provided in the config yaml
     * @throws NoSuchFileException the function throws not found exception if path is not found and exists.
     */
    public static Path checkDirectory(String directory, String API) throws NoSuchFileException {
        logger.info("{} Directory Value: {}", API, directory);

        // Check if directory exists, if not, quit.
        Path directoryPath = Path.of(directory);

        if(!Files.exists(directoryPath)) {
            logger.error("Could not locate {} directory {}. Exiting..",
                    API, directoryPath);
            throw new NoSuchFileException(directoryPath + " not found.");
        }

        return directoryPath;
    }

    /**
     * Utility function to get the list of all files or subdirectories in a directory.
     * The function will return subdirectories if isDirectory is provided as true.
     * Else it will return only files present in the input directory.
     *
     * @param path the location of the input directory in file system.
     * @param isDirectory if the output is supposed to give only directories
     * @return the list of files or subdirectories depending on the input isDirectory.
     */
    public static ArrayList<String> getListOutput(Path path, boolean isDirectory) {
        logger.info("IsDirectory Value: {}", isDirectory);
        File file = new File(String.valueOf(path));
        File[] listOutput = file.listFiles();
        ArrayList<String> allDirectories = new ArrayList<>();
        ArrayList<String> allFiles = new ArrayList<>();
        if(listOutput != null) {
            for(File item: listOutput) {
                if(item.isDirectory()) {
                    allDirectories.add(item.getName());
                } else {
                    allFiles.add(item.getName());
                }
            }
        }

        if(isDirectory) {
            return allDirectories;
        }

        return allFiles;
    }

    /**
     * The function to get the MD5 hash value of the given string.
     *
     * @param string the given input string.
     * @return the md5 hash value of the string.
     */
    public static String getMD5HashValue(String string) throws NoSuchAlgorithmException {
        MessageDigest m = MessageDigest.getInstance("MD5");
        byte[] md5hashByte = m.digest(string.getBytes(StandardCharsets.UTF_8));
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < md5hashByte.length; ++i) {
            stringBuffer.append(Integer.toHexString((md5hashByte[i] & 0xFF) | 0x100).substring(1,3));
        }
        return stringBuffer.toString();
    }

    /**
     * Utility Function to get all the Stars
     *
     * @param starsDirectoryPath the path to the directory in local file system.
     * @return the list of Star objects.
     */
    public static ArrayList<Star> getAllStars(Path starsDirectoryPath) throws NoSuchAlgorithmException {
        ArrayList<Star> stars = new ArrayList<>();
        ArrayList<String> subDirectories = Utils.getListOutput(starsDirectoryPath, true);

        for(String subDirectory: subDirectories) {
            Path starDirectoryPath = Path.of(starsDirectoryPath + "/" + subDirectory);
            ArrayList<String> allStarMoviesString = Utils.getListOutput(starDirectoryPath, false);
            ArrayList<Movie> allStarMovies = new ArrayList<>();
            for(String item: allStarMoviesString) {
                Movie movie = new Movie(item, starDirectoryPath + "/" + item);
                allStarMovies.add(movie);
            }
            stars.add(new Star(subDirectory, allStarMovies));
        }
        return stars;
    }
}
