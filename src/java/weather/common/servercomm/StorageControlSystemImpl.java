package weather.common.servercomm;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Vector;
import weather.StorageControlSystem;
import weather.common.data.*;
import weather.common.data.resource.*;
import weather.common.servercomm.storage.StorageCommand;
import weather.common.servercomm.storage.StorageCommandType;
import weather.common.utilities.Debug;
import weather.common.utilities.WeatherException;

/**
 * This class implements the StorageControlSystem interface. 
 *
 * @author Bloomsburg University Software Engineering
 * @author Bill Katsak (2008)
 * @author Zach Rothweiler
 * @version Spring 2012
 */
//@SuppressWarnings("unchecked")
public class StorageControlSystemImpl implements StorageControlSystem, Serializable {
    private String hostname;
    private int port;
    private static StorageControlSystemImpl  storageImplementation = null;

    public static StorageControlSystemImpl getStorageSystem(){
        if(storageImplementation== null){
            storageImplementation = new StorageControlSystemImpl();
        }
        return storageImplementation;
    }
    /**
     * Constructs a <code>StorageControlSystem</code> and uses values from the
     * property file to initialize the fields
     */
    private StorageControlSystemImpl() {
        
        //Debug.println("set hostname");
        this.hostname = "148.137.9.29";
        //Debug.println("hostname : "+hostname);
        //Debug.println("set port");
        this.port = 9002;
        //Debug.println("port : "+port);       
    }

    /**
     * Specifies the signature of the method that will create the file
     * structure needed to save weather resource instances and data for
     * instructors. This method will not delete any existing data.
     * TODO: implement method
     * @param fileSystemRoot the root folder of the file system to which
     *    weather resource instances will be saved.
     * @throws WeatherException 
     */
    @Override
    public void createFileStructure(String fileSystemRoot) throws WeatherException {
        throw new RuntimeException("Not yet implemented");
    }


    /**
     * Places a specified <code>Resource</code> instance within the file 
     * storage system.
     * TODO: how to document both causes of <code>WeatherException</code>
     * @param resource the <code>Resource</code> instance to be stored.
     * @throws WeatherException if there is an issue establishing the 
     * <code>Socket</code>
     */
    @Override
    public void placeResourceInstance(ResourceInstance resource) throws WeatherException {
        StorageCommand command = new StorageCommand();
        command.setCommandType(StorageCommandType.STORE);
        command.setResourceInstance(resource);

        executeCommand(command);
    }

  
    /**
     * Gets the error log text file. The current error log file is returned
     * as a string.
     * TODO: implement method
     * @return the error log as a String.
     * @throws WeatherException
     */
    @Override
    public String getErrorLog() throws WeatherException {
        throw new RuntimeException("Not yet implemented");
    }

   
    /**
     * Sets the root directory to which all new resource instances will be
     * saved. The server will need to update
     * its property file so that this root directory is used on all
     * system restarts.
     *
     * TODO: implement method
     * 
     * @param rootDirectory the folder to set as the root for the file
     *    storage system.
     * @throws WeatherException
     */
    @Override
    public void setRootDirectory(String rootDirectory)
            throws WeatherException {
        throw new RuntimeException("Not yet implemented");
    }

    /**
     * Returns the current root folder of the file storage system.
     * TODO: implement method
     * @return the name of the root folder of the file storage
     *    system as a string.
     * @throws WeatherException
     */
    @Override
    public String getRootDirectory() throws WeatherException {
        throw new RuntimeException("Not yet implemented");
    }

    /**
     * Specifies the signature of the method that will retrieve a bundle
     * of resource instances within a certain range of resources.
     *
     * @param request the object specifying the resources being requested.
     * @return the the <code>resource</code> instances bundled in the
     *  <code>ResourceInstancesReturned</code> object.
     * @throws WeatherException
     */
    @Override
    public ResourceInstancesReturned getResourceInstances(ResourceInstancesRequested request) throws WeatherException {
        StorageCommand command = new StorageCommand();
        command.setCommandType(StorageCommandType.PROVIDE);
        command.setResourceRequest(request);

        return (ResourceInstancesReturned)sendCommand(command);
    }

    /**
     * Places a vector of <code>Resource</code> instances in the file system.
     * TODO: implement method
     * @param resourceInstances a vector of <code>Resource</code> instances to
     * be added to the file storage system.
     * @throws WeatherException
     */
    @Override
    public void placeResourceBundle(Vector<ResourceInstance> resourceInstances) throws WeatherException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * This method attempts to connect to the server stored in the class' data.  If
     * there is no response from the server for 10 seconds, a fatal exception is
     * thrown.
     * TODO: implement method
     * 
     * @return true if the server is available
     * @throws WeatherException
     * @throws ConnectException
     */
    @Override
    public boolean pingServer() throws ConnectException, WeatherException {
        Debug.println("in ping server");
        
        boolean value = false;
        Socket socket = new Socket();
        try {
            int timeout = 30;
            InetSocketAddress addr = new InetSocketAddress (hostname, port);
            socket.connect(addr, timeout);
            socket.close();
            value = true;
        } catch (SocketTimeoutException ex){
            value = false;
        } catch (IOException ex) {
            value = false;
        } catch (Exception ex) {
            value = false;
        } 
        //boolean value = true;
        Debug.println("Ping successful?: "+value);
        return (value);
    }
    
    /**
     * Creates a new command sent to the storage system to create a new default
     * nighttime movie.
     * @param resource The <code>Resource</code> for which the movie is created.
     * @param picture The <code>ImageInstance</code> used to create the movie.
     * @throws WeatherException if there's an error creating the movie file.
     */
    @Override
    public void setNewDefaultNightimeMovie(Resource resource, ImageInstance picture) throws WeatherException {
        Debug.println("Setting default night time movie.");
        picture.setResourceNumber(resource.getResourceNumber());
        StorageCommand command = new StorageCommand();
        command.setResourceInstance(picture);
        command.setCommandType(StorageCommandType.STORE_DEFAULT_NIGHT);

        executeCommand(command);
    }
    
    /**
     * Creates a command for the storage system to create a new no data movie.
     * @param picture The <code>ImageInstance</code> used to create the movie.
     * @throws WeatherException if there's an error creating the movie file.
     */
    @Override
    public void setNewDefaultGenericNoDataMovie(ImageInstance picture) throws WeatherException { 
        Debug.println("Setting default nodata movie.");
        StorageCommand command = new StorageCommand();
        command.setResourceInstance(picture);
        command.setCommandType(StorageCommandType.STORE_NO_DATA_MP4);

        executeCommand(command);
        
        command = new StorageCommand();
        command.setResourceInstance(picture);
        command.setCommandType(StorageCommandType.STORE_NO_DATA_AVI);

        executeCommand(command);
    }
    
    /**
     * Takes a resource, and a picture, and creates a new command sent to the
     * storage system to create a new default daytime (no data)
     * movie from this picture for the specific resource.
     * @param resource The resource to create a new daytime movie for.
     * @param picture The picture to use to create the movie.
     * @throws WeatherException if there's an error creating the movie file.
     */
    @Override
    public void setNewDefaultDaytimeMovie(Resource resource, ImageInstance picture) throws WeatherException {
        Debug.println("Setting default day time movie.");
        picture.setResourceNumber(resource.getResourceNumber());
        StorageCommand command = new StorageCommand();
        command.setResourceInstance(picture);
        command.setCommandType(StorageCommandType.STORE_DEFAULT_DAY);
        executeCommand(command);
    }
    
    /**
     * Retrieves a list of currently existing folders from the storage system.
     *
     * @return An 
     * <code>ArrayList</code> containing a list of the folder names.
     */
    @Override
    public ArrayList<String> retrieveFolderList(){
        StorageCommand command = new StorageCommand();
        command.setCommandType(StorageCommandType.PROVIDE_FOLDER_LIST);

        return (ArrayList<String>)sendCommand(command);
    }
    
    /**
     * Sends a command to the storage server and returns the Object response.
     * @param command The command to execute on the server.
     * @return The Object received back from the server.
     */
    private Object sendCommand(StorageCommand command){
        Object returned=null;
        Socket socket = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try{
            Debug.println(hostname + ":" + port);
            socket = new Socket(hostname, port);
  //          System.err.println(hostname + ":" + port);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            
            oos.writeObject(command);
            oos.flush();

            returned=ois.readObject();

            oos.close();
            ois.close();
        }
        catch (ClassNotFoundException ex) {
            WeatherException we = new WeatherException(4020, true, ex);
            System.err.println(ex.getMessage());
            we.show();
        }
        catch (UnknownHostException ex) {
            WeatherException we = new WeatherException(4023, true, ex);
            System.err.println("unknown host");
            we.show();
            
        }
        catch (IOException ex) {
            WeatherException we = new WeatherException(4023, true, ex);
            System.err.println("io exception trying to send command to storage system");
            ex.printStackTrace();
            we.show();
            
        }
        finally{
            closeSocket(socket);
        }
        
        return returned;
    }
    
    /**
     * Executes a command on the storage system.  This does not receive a 
     * response from the server.
     * @param command The command to execute on the storage system.
     */
    private void executeCommand(StorageCommand command){
        Socket socket=null;
        ObjectOutputStream oos=null;
        ObjectInputStream ois = null;
        try{
            socket = new Socket(hostname, port);
            Debug.println("socket created:"+socket);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            
            oos.writeObject(command);
            oos.flush();
            Debug.println("object written");

            oos.close();
            ois.close();
        }
        catch (UnknownHostException ex) {
            WeatherException we = new WeatherException(4023, true, ex);
            //throw we;
        }
        catch (IOException ex) {
            WeatherException we = new WeatherException(4023, true, ex);
          //  throw we;
        }
        finally{
            closeSocket(socket);
        }
    }
    
    /**
     * Closes the given socket.
     * @param s The socket to close.
     */
    private void closeSocket(Socket s){
        try{
            if (s != null) {
                s.close();
                Debug.println("socket closed");
            }
        }
        catch(IOException ex){
            
        }
    }
    
    /**
     * Returns the amount of free space on the storage system.
     * @return The amount of free space on the storage system.
     */
    @Override
    public double getFreeSpace(){
        double freeSpace=0;

        StorageCommand command = new StorageCommand();
        command.setCommandType(StorageCommandType.INFO_FREE_SPACE);

        return ((Double)sendCommand(command)).doubleValue();
    }
}
