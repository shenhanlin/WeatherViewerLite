package weather.common.servercomm.storage;

import java.io.Serializable;
import weather.common.data.ResourceInstancesRequested;
import weather.common.data.resource.ResourceInstance;

/**
 * A class that represents a command that can be executed on the StorageServer.
 * @author Bloomsburg University Software Engineering (2008)
 * @author Joe Sharp (2008)
 * @author Thomas Crouse (2012)
 * @version Spring 2012
 */
public class StorageCommand implements Serializable {
    /**
     * Determines if a de-serialized file is compatible with this class.
     *
     * Maintainers must change this value if and only if the new version
     * of this class is not compatible with old versions. See Sun docs
     * for <a href=http://java.sun.com/products/jdk/1.1/docs/guide
     * /serialization/spec/version.doc.html> details. </a>
     *
     * Not necessary to include in first version of the class, but
     * included here as a reminder of its importance.
     * @serial
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * The resource instance.
     */
    private ResourceInstance resourceInstance;
    
    /**
     * The resource request.
     */
    private ResourceInstancesRequested resourceRequest;
    
    /**
     * The type of storage command.
     */
    private StorageCommandType commandType;
    
    /**
     * Creates a new instance of StorageCommand with all fields set to null.
     */
    public StorageCommand() {
        this.resourceInstance = null;
        this.resourceRequest = null;
        this.commandType = null;
    }

    /**
     * Creates a new instance of StorageCommand with the given 
     * ResourceInstancesRequested and StorageCommandType.
     * @param request A request for resource instances.
     * @param type The type of storage command to be executed.
     */
    public StorageCommand (ResourceInstancesRequested request, StorageCommandType type) {
        this.resourceRequest = request;
        this.commandType = type;
        this.resourceInstance = null;
    }

    /**
     * Retrieves the resource request.
     * @return The resource request.
     */
    public ResourceInstancesRequested getResourceRequest() {
        return resourceRequest;
    }

    /**
     * Assigns the resource request.
     * @param resourceRequest The resource request to be assigned.
     */
    public void setResourceRequest(ResourceInstancesRequested resourceRequest) {
        this.resourceRequest = resourceRequest;
    }
    
    /**
     * Retrieves the storage command type.
     * @return The storage command type.
     */
    public StorageCommandType getCommandType() {
        return commandType;
    }

    /**
     * Assigns the storage command type to be used.
     * @param commandType The storage command type to be used.
     */
    public void setCommandType(StorageCommandType commandType) {
        this.commandType = commandType;
    }

    /**
     * Returns the value of the serialVersionID variable held within the object.
     * @return The serialVersionID.
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * Retrieves the resource instance.
     * @return The resource instance.
     */
    public ResourceInstance getResourceInstance() {
        return resourceInstance;
    }

    /**
     * Assigns the resource instance.
     * @param resourceInstance The resource instance to be assigned.
     */
    public void setResourceInstance(ResourceInstance resourceInstance) {
        this.resourceInstance = resourceInstance;
    }
}
