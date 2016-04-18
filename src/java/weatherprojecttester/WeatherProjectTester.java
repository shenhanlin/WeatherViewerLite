/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weatherprojecttester;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.util.Collection;
import weather.StorageControlSystem;
import weather.common.data.ResourceInstancesRequested;
import weather.common.data.ResourceInstancesReturned;
import weather.common.data.RetrievalMethod;
import weather.common.data.resource.AVIInstance;
import weather.common.data.resource.MP4Instance;
import weather.common.data.resource.Resource;
import weather.common.data.resource.ResourceCollectionSpan;
import weather.common.data.resource.ResourceFileFormatType;
import weather.common.data.resource.ResourceInstance;
import weather.common.data.resource.ResourceRange;
import weather.common.data.resource.ResourceTimeZone;
import weather.common.data.resource.WeatherResourceType;
import weather.common.servercomm.StorageControlSystemImpl;
import weather.common.utilities.WeatherException;

/**
 *
 * @author ljc89611
 */
public class WeatherProjectTester {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws MalformedURLException, WeatherException {
        Collection<ResourceInstance> movies = getMovies(new Date(1429144130000L), 1428820087000L, 1428834487000L);
        for(ResourceInstance ri : movies)
            System.out.println(((MP4Instance)ri).getMovie());
    }
    public static Collection<ResourceInstance> getMovies(Date today, long start, long end) throws MalformedURLException, WeatherException{
        StorageControlSystem system = StorageControlSystemImpl.getStorageSystem();
        ResourceRange range = new ResourceRange(
                new Date(start), 
                new Date(end));
        Resource resource = new Resource(102, 
                WeatherResourceType.WeatherCamera, 
                "BU Weather Camera", 
                RetrievalMethod.url, 
                "BUWeatherCamera", 
                ResourceFileFormatType.mp4, 
                new URL("http://148.137.190.238/netcam.jpg"), 
                15, 
                true, 
                true, 
                today, 
                ResourceCollectionSpan.DaylightHours, 
                0, 
                0, 
                -76.4454F, 
                41.009F, 
                ResourceTimeZone.US_ET);
        ResourceInstancesRequested request = new ResourceInstancesRequested(range, 1, true, ResourceFileFormatType.mp4, resource);
        ResourceInstancesReturned returned = system.getResourceInstances(request);
        
        return returned.getResourceInstances();
    }
}
