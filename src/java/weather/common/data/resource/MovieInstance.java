package weather.common.data.resource;

import java.io.*;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.sql.Date;
import weather.common.utilities.WeatherException;

/**
 * The MovieInstance class specifies a weather movie.
 * The weather movie is created using QTJava and is of the type mov.
 *
 * @author Bloomsburg University Software Engineering 
 * @author Anthony Tersine (2007)
 * @author Daniel Wieand (2008)
 * @version Spring 2008
 */
public class MovieInstance extends ResourceInstance implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Byte Array version of a Movie.
     */
    public byte[] movie;

   /**
     * Creates a new instance of MovieInstance.
     */
    public MovieInstance() {
        super();
        super.setResourceType(WeatherResourceType.WeatherMovie);
    }

    /**
     * A constructor for MovieInstance.
     * @param resource The resource used to build the MovieInstance.
     */
    public MovieInstance(Resource resource) {
        super(resource);
    }

    /** 
     * Creates a new instance of MovieInstance.
     * @param time The Date.
     * @param resourceType The type of resource.
     * @param resourceNumber The number of this Resource.
     * @param fileName The filename.
     */
    public MovieInstance(Date time, WeatherResourceType resourceType,
                         int resourceNumber, String fileName) {
        super(time, resourceType, resourceNumber, fileName);
    }

    /**
     * Reads a file into the Byte Array.
     * @param file The file to be read in.
     * @throws WeatherException 
     */
    @Override
    public void readFile(File file) throws WeatherException {
        try {
            
            FileInputStream in = new FileInputStream(file);
            FileChannel fc = in.getChannel();
            movie = new byte[(int) fc.size()];
            ByteBuffer buffer = ByteBuffer.wrap(movie);
            fc.read(buffer);
            in.close();
        }
        catch (IOException ex) {
            throw new WeatherException(4003, ex);
        }
    }

    /**
     * TODO Maybe implement read from URL?
     * Reads a URL - Not Implemented.
     * @param url The URL to be read.
     */
    //@Override
    public void readURL(URL url) {
    /*   
    try {
    InputStream in = url.openStream();
    movie = new byte[0];
    byte[] buffer = new byte[10000];
    int currentSize = 0;
    while ((currentSize = in.read(buffer)) != -1) {
    byte[] movieFill = new byte[movie.length + currentSize];
    System.arraycopy(movie, 0, movieFill, 0, movie.length);
    System.arraycopy(buffer, 0, movieFill, movie.length, currentSize);
    movie = movieFill;
    }
    in.close();
    } catch (IOException ex) {
    log(Level.SEVERE, "Error reading URL.", ex);
    }*/
        throw new java.lang.UnsupportedOperationException();
    }

    /**
     * Writes the contents of the byte array to be written to a File.
     * @param file The file to be written.
     * @throws WeatherException 
     */
    @Override
    public void writeFile(File file) throws WeatherException {
        try {
            BufferedOutputStream out = null;

            out = new BufferedOutputStream(new FileOutputStream(file));
            out.write(movie);
            
            out.close();
        } 
        catch (FileNotFoundException ex1) {
            throw new WeatherException(4000, ex1);
        }
        catch (IOException ex2) {
            throw new WeatherException(4000, ex2);
        }
    }

    
    /**
     * Returns a java.io.File version of a QuickTime Movie from the Byte array.
     * @return A java.io.File version of a QuickTime Movie.
     * @throws WeatherException
     */
    public File getMovieFile() throws WeatherException {
        FileOutputStream fos;
        File movieFile = null;
        try {
            movieFile = File.createTempFile(this.getStorageFileName()
                                            + "_localtemp",".mov");
            fos=new FileOutputStream(movieFile);
            fos.write(movie);
            fos.close();
            return movieFile;
        }
        catch (IOException ex) {
                throw new WeatherException(4024,ex);
        }
    }
    
   
}
