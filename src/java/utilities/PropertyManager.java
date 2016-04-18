
package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;



/**
 * Utility class to access all properties and property files.
 *
 * 
 *
 */
public class PropertyManager {
  

    private static Properties properties;
 
  
    /**
     * Configures properties for this program.  
     * @param filePath Path to property file location.
     * 
     */
    public static void configure(String filePath) {
        properties = new Properties();
        if(filePath==null || filePath.isEmpty()) {
            
            Debug.println("Could not find properties at "+ filePath);
            return;
        }
        else Debug.println("Reading properties from "+ filePath);
        String propertyFilepath = filePath;
            
        try {
            properties.load(new FileInputStream(
                    new File(propertyFilepath)));
            Debug.println("Properties loaded from "+ filePath);
        } 
        catch (FileNotFoundException ex) {
            ErrorLogger.log(Level.SEVERE,"Could not open the properties file: "+ propertyFilepath,  ex);  
        } catch (IOException ex) {
            ErrorLogger.log(Level.SEVERE,"Could not read the properties file: "+ propertyFilepath,  ex);
        } 

    }

    /**
     * Obtains the requested property from the general properties file.
     *
     * @param key the name of the property requested.
     * @return The value of the property requested or null if 
     * this key does not exist in the property file. 
     */
    public static String getProperty(String key) {
        if (properties == null) {
            configure(null);
        }
        return properties.getProperty(key);
    }
    
   /**
    * Sets the a property value for the given key and stores it in the property file 
    * for the next program execution. 
    * 
    * @param key The key for this property value. 
    * @param value The property value associated with the given key. 
    */
    public static void setProperty(String key, String value) {
        if (properties == null) {
            configure(null);
        }
        properties.setProperty(key, value);
    }

    /**
    * Saves the current property collection to the default file. 
    * 
    */
    public static void saveFile() {
        saveFile(null);
        
    }

    
   /**
    * Saves the current property collection to a file. 
    * 
     * @param filePath The file path or null to use the default path. 
    */
    public static void saveFile(String filePath) {
        if (properties == null) return;
        if(filePath==null || filePath.isEmpty()){
         return;
        }
        
        try {
              properties.store(new FileOutputStream(new File(filePath)), null);
        } catch (IOException ex) {
            ErrorLogger.log(Level.SEVERE,"Could not save property file "+ 
                            filePath,  ex);
        }
    }


    public static boolean isProduction() {
        String value = getProperty("Configuration");
        if (value == null) return false;
        return value.equalsIgnoreCase("production");
    }

}
