package weather.common.servercomm.storage;

import java.io.Serializable;

/**
 * This class specifies all the available storage server 
 * commands that can be sent from the client. 
 * @author Bloomsburg University Software Engineering
 * @version Spring 2008
 */
public enum StorageCommandType implements Serializable{
   
    /** 
     * Store stores any file for a resource. 
     */
    STORE,
    
    /**
     * STORE_DEFAULT_DAY and
     * STORE_DEFAULT_NIGHT is for a particular resource.
     * STORE_NO_DATA_MP4 and STORE_NO_DATA_AVI are for generic movies not associated
     * with any resource (sad looking dog).
     */
    STORE_DEFAULT_NIGHT, 
    STORE_DEFAULT_DAY,
    STORE_NO_DATA_MP4,
    STORE_NO_DATA_AVI,
    
    /**
     * Provide will send back the files requested.
     * 
     */
    PROVIDE,
    
    /** 
     * Returns a the names of the folders used to store data in our 
     * storage system.
     * 
     */
    PROVIDE_FOLDER_LIST,
    
    /**
     * INFO_FREE_SPACE retrieves the amount of free space at storage system root.
     */
    INFO_FREE_SPACE,
    
    QUIT;

    public static final long serialVersionUID = 1L;
}
