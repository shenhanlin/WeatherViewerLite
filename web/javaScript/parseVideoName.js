/* 
 * This file use to create a file name for open the video
 *  
 */

function createCameraFileName(id, year, mm, dd)
{
    var cameraFileName="/WeatherViewerLite-2/videos/WeatherViewerCameraVideo/R"+id+"_"+mm+"-"+dd+"-"+year+".mp4";
    //document.getElementById('wrapper').style.cssText ='background-color: red;';
    //document.write(id);
    return cameraFileName;
}

function createMapFileName(id, year, mm, dd)
{
    var mapFileName="/WeatherViewerLite-2/videos/WeatherViewerMapVideo/R"+id+"_"+mm+"-"+dd+"-"+year+".mp4";
    //document.write(id);
    //document.getElementById('wrapper').style.cssText ='background-color: red;';
    return mapFileName;
}

function createCSVFileName(year, mm, dd)
{
    var csvFileName="/WeatherViewerLite-2/csv/Bloomsburg-Underground_"+mm+"-"+dd+"-"+year+".csv";
    return csvFileName;
}

/*
 * This funtion will return a file name that file is video not find video
 * when there is no video of that day you should use this funtion so the webpage
 * still showing something
 */
function passVideoNotFindFileName()
{
    var cameraFileName="/WeatherViewerLite-2/videos/WeatherViewerCameraVideo/VideoNotFind.mp4";
    return cameraFileName;
}
