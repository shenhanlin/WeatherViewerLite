/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package weather.common.data.resource;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.StringTokenizer;
import java.util.logging.Level;
import weather.common.utilities.Debug;
import weather.common.utilities.LocalFileManager;
import weather.common.utilities.WeatherException;
import weather.common.utilities.WeatherLogger;

/**
 *
 * @author Curt Jones
 */
public class MP4Instance extends AVIInstance {
    
    /**
     * Determines if a de-serialized file is compatible with this class.
     *
     * Maintainers must change this value if and only if the new version of this
     * class is not compatible with old versions. See Sun docs for <a
     * href=http://java.sun.com/products/jdk/1.1/docs/guide
     * /serialization/spec/version.doc.html> details. </a>
     *
     * Not necessary to include in first version of the class, but included here
     * as a reminder of its importance.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new instance of MP4Instance. ResourceType is set to
     * WeatherResourceType.MP4Video and and the storage filename is "None".
     */
    public MP4Instance() {
        super();
        super.setResourceType(WeatherResourceType.MP4Video);
        this.setStorageFileName("None");
    }
    
     /**
     * Creates a new instance of MP4Instance from the provided Resource and
     * File.
     *
     * @param resource The resource used to build the MP4Instance.
     * @param file The file to contain a byte array of the movie.
     */
    public MP4Instance(Resource resource, File file) {
        super(resource);
        super.setResourceType(WeatherResourceType.MP4Video);
        this.file = file;
    }

    /**
     * Creates a new instance of AVIInstance from the provided File.
     *
     * @param file The file to contain a byte array of the movie.
     */
    public MP4Instance(File file) {
        super();
        super.setResourceType(WeatherResourceType.MP4Video);
        this.file = file;
        this.setStorageFileName(file.getName());
    }

    /**
     * Creates a new instance of MP4Instance from the provided Resource.
     *
     * @param resource The resource used to build the MP4Instance.
     */
    public MP4Instance(Resource resource) {
        super(resource);
        super.setResourceType(WeatherResourceType.MP4Video);
    }

    /**
     * Creates a new instance of MP4Instance.
     *
     * @param time The date and time the resource was created.
     * @param resourceType The type of Resource.
     * @param resourceNumber The number of this Resource.
     * @param fileName The filename of this MP4Instance.
     */
    public MP4Instance(Date time, WeatherResourceType resourceType,
            int resourceNumber, String fileName) {
        super(time, resourceType, resourceNumber, fileName);
    }

    
    
    /**
     * Write the file to the disk.
     *
     * NOTE: This method may only be called from the client side after being
     * downloaded from the server.
     */
    @Override
    public void writeToDisk() {
        
        try {
            Debug.println("Writing a " + this.getResourceType()+" in MP4Instance.java writeToDisk method. File name is " + getSerializedFileName());
            if (this.getResourceType() == WeatherResourceType.WeatherCamera) {
                file = new File(LocalFileManager.getAVICameraDir() + File.separator + getSerializedFileName() + ".mp4");
            }
            else if(this.getResourceType() == WeatherResourceType.WeatherMapLoop){
                file = new File(LocalFileManager.getAVIMaploopDir() + File.separator + getSerializedFileName() + ".mp4");
            }
            else{
               // Debug.println("A non-video resource type was trying to be written. " + this.getStorageFileName());
                file = new File(LocalFileManager.getAVIDir()+File.separator+getSerializedFileName()+".mp4");
            }
            if(file.exists()) {
                return;
            }
            writeFile(file);
        } catch (WeatherException ex) {
            WeatherLogger.log(Level.SEVERE, "Error while writing MP4 file to disk.", ex);
        }
//        catch (IOException ex) {
//            WeatherLogger.log(Level.SEVERE, "Error while writing MP4 file to disk.", ex);
//        }
    }
    
       /**
     * Set the fields from the current filename of this instance.
     */
    @Override
    public void setFieldsFromFilename() {
        StringTokenizer tokenizer = new StringTokenizer(file.getName());

        setStorageFileName(tokenizer.nextToken(","));
        setResourceNumber(Integer.parseInt(tokenizer.nextToken(",")));
        setTime(new Date((Long.parseLong(tokenizer.nextToken(",")))));
        setStartTime(Long.parseLong(tokenizer.nextToken(",")));
        setEndTime(Long.parseLong(tokenizer.nextToken(",.")));
        if(file.getParent().equals(LocalFileManager.getAVICameraDir())){
            setResourceType(WeatherResourceType.WeatherCamera);
        }
        else if(file.getParent().equals(LocalFileManager.getAVIMaploopDir())){
            setResourceType(WeatherResourceType.WeatherMapLoop);
        }
        else {
            setResourceType(WeatherResourceType.MP4Video);
        }
    }

     /**
     * Returns the serialized file name for writing this movie to the hard disk.
     * This contains only the file name and not the entire path.
     */
    @Override
    public String getSerializedFileName() {
        StringBuilder filename = new StringBuilder();
        String delimiter = ",";

        filename.append(getStorageFileName());
        filename.append(delimiter);
        filename.append(getResourceNumber());
        filename.append(delimiter);
        filename.append(getTime().getTime());
        filename.append(delimiter);
        filename.append(getResourceRange().getStartTime().getTime());
        filename.append(delimiter);
        filename.append(getResourceRange().getStopTime().getTime());
        //Debug.println("You need to see this : "+filename.toString());
        return filename.toString();
    }
    
     /**
     * Returns a String representation of the absolute pathname of the file
     * containing the movie's byte array
     *
     * @return A String representation of the absolute pathname of the file
     * containing the movie's byte array.
     */

    @Override
    public String getMovie() {
        return file.getAbsolutePath();
    }
    
    /**
     * Returns the File that the movie byte array was written to.
     *
     * @return The File that the movie byte array was written to.
     */
    @Override
    public File getMovieFile() {
        return file;
    }
    
     /**
     * Writes the movie as a byte array to a provided File.
     *
     * @param file The file to write the movie byte array to.
     * @throws WeatherException
     */
    @Override
    public void writeFile(File file) throws WeatherException {
        try {
            BufferedOutputStream out =
                    new BufferedOutputStream(new FileOutputStream(file));
            try {
                out.write(movie);
            } catch (IOException ex) {
                throw new WeatherException(4000, ex);
            } finally {
                out.close();
            }
        } catch (FileNotFoundException ex1) {
            throw new WeatherException(4000, ex1);
        } catch (IOException ex2) {
            throw new WeatherException(4000, ex2);
        }

        // Remember where we kept the file.
        this.file = file;


    }

}
