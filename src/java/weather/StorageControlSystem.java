package weather;

import java.net.ConnectException;
import java.util.ArrayList;
import weather.common.data.*;
import weather.common.data.resource.*;
import weather.common.utilities.WeatherException;

/**
 *
 * This interface specifies the requests that the storage retrieval system will
 * accept. Methods to get and clear the error log are specified by this
 * interface.
 *
 * @author Bloomsburg University Software Engineering
 * @author Bill Katsak (2008)
 * @version Spring 2008
 */
/*
 * Note -- as of now -- does not change what it does when a resource setting
 * changes Could implement ResourceChangeListener in future if it would help our
 * system. Need to determine what to do in some cases. For example, when a
 * resource is added -- make a new directory When modified -- copy old data over
 * to new location if folder name changed When removed -- archive data ? When
 * frequency changes or active or valid -- do nothing.
 *
 */
public interface StorageControlSystem {

    /**
     * Specifies the signature of the method that will create the file structure
     * needed to save weather resource instances and data for instructors. This
     * method will not delete any existing data.
     *
     * @param fileSystemRoot The root folder of the file system where weather
     * resource instances are saved.
     * @throws WeatherException
     */
    public void createFileStructure(String fileSystemRoot)
            throws WeatherException;

    /**
     * Specifies the signature of the method to place a specified weather
     * resource instance into a file system.
     *
     * @param resourceInstance A weather resource instance to be put into the
     * file system.
     * @throws WeatherException
     */
    public void placeResourceInstance(ResourceInstance resourceInstance)
            throws WeatherException;

    /**
     * Specifies the signature of the method that allows of vector of resources
     * to be placed within a file system.
     *
     * @param resourceInstances A vector of resource instances to be placed
     * within the file system.
     * @throws WeatherException
     */
    public void placeResourceBundle(java.util.Vector<ResourceInstance> resourceInstances) throws WeatherException;

    /**
     * Specifies the signature of the method that will retrieve a bundle of
     * resource instances within a certain range of resources.
     *
     * @param request The object specifying the resources being requested.
     * @return The resource instances bundled in the ResourceInstancesReturned
     * object.
     * @throws WeatherException
     */
    public ResourceInstancesReturned getResourceInstances(ResourceInstancesRequested request)
            throws WeatherException;
    
    /**
     * This method attempts to connect to the server stored in the class data.
     * If there is no response from the server, a fatal exception is thrown.
     *
     * @return True if the storage system is available and responding to
     * requests, false otherwise.
     */
    public boolean pingServer() throws ConnectException, WeatherException;

    /**
     * Specifies the signature of the method that will get the error log text
     * file. The current error log file is returned as a string.
     *
     * @return The text version of the error log.
     * @throws WeatherException
     */
    public String getErrorLog() throws WeatherException;

    /**
     * Specifies the signature of the method that will set the root directory
     * for all new resource instances. The server will need to update its
     * property file so that this root directory is used on all system restarts.
     *
     * @param rootDirectory The root folder of the file system to which all new
     * resource instances will be saved.
     * @throws WeatherException
     */
    public void setRootDirectory(String rootDirectory)
            throws WeatherException;

    /**
     * Specifies the signature of the method that will return the current root
     * folder under which all resource instances are currently being saved.
     *
     * @return The name of the root folder of the file system as a string value.
     * @throws WeatherException
     */
    public String getRootDirectory() throws WeatherException;

    /**
     * Sets a new default nighttime movie for a specific resource. It will be
     * stored in the resource folder under "Generic Movies/DefaultNight"
     * as to files with .avi and .mp4 extensions.
     *
     * @param resource The resource to create a new nighttime movie for.
     * @param picture The picture to use to create the movie.
     * @throws WeatherException if there's a problem creating a movie or placing
     * it in the storage system.
     */
    public void setNewDefaultNightimeMovie(Resource resource, ImageInstance picture)
            throws WeatherException;

    /**
     * Sets a new default daytime movie for a specific resource. It will be
     * stored in the resource folder under "Generic Movies/DefaultDay"
     * as to files with .avi and .mp4 extensions.
     *
     * @param resource The resource to create a new nighttime movie for.
     * @param picture The picture to use to create the movie.
     * @throws WeatherException if there's a problem creating a movie or placing
     * it in the storage system.
     */
    public void setNewDefaultDaytimeMovie(Resource resource, ImageInstance picture)
            throws WeatherException;

    /**
     * Sets a new default no-data movie for the entire file system. It will be
     * stored in the storage root folder as "NoData.avi".
     *
     * @param picture The picture to use to create the movie.
     * @throws WeatherException if there's a problem creating a movie or placing
     * it in the storage system.
     */
    public void setNewDefaultGenericNoDataMovie(ImageInstance picture)
            throws WeatherException;

    /**
     * Retrieves a list of currently existing folders from the storage system.
     *
     * @return An
     * <code>ArrayList</code> containing a list of the folder names.
     */
    public ArrayList<String> retrieveFolderList();

    /**
     * Returns the amount of free space on the storage system.
     *
     * @return The amount of free space on the storage system.
     */
    public double getFreeSpace();
}
