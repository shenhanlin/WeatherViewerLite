/* 
 * 
 * This class will initialize all elements except for parseCSV.
 * In fact parseCSV has to be first otherwise things get messed up. 
 * Also this class holds the inerval(timer) for the page.
 * 
 * @author: Aleks Hartzler (aeh75945)
 */

/********************************
 ********************************
 *
 *   Variables
 * 
 ********************************/

var timer;  //timer that ticks every .1 of a second calls the onTick() function
var camera; //the video that is the camera, this is the master vid
var map;    //the video that is the forcast map, this is the slave vid

var cameraURL;
var mapURL;

var mapSelect;    //

var currentPrecentage = 0; //curret precentage played of the camera video

var isPlay = false;
/********************************
 ********************************
 *
 *   Event Listener
 * 
 ********************************/


//window.addEventListener("load", init, false); //the event listener that starts everything


/********************************
 ********************************
 *
 *   Init Functions
 * 
 ********************************/

/**
 * initializes or calls init functions for all components of the page.
 */
function init() {
    //document.getElementById('wrapper').style.cssText ='background-color: #red;';
    camera = document.getElementById("camera");
    map = document.getElementById("map");

    dateTimeInit();

    videoInit();

    videoControlInit();  //videoControls.js.videoControlInit()
    //charts.js.chartInit()


    camera.addEventListener("play", function () {
        isPlay = true;
    }, false);
    camera.addEventListener("pause", function () {
        isPlay = false;
    }, false);

    chartInit();
    timer = setInterval(onTick, 10);
}

/**
 * This function initializes the selects for the camera and maps. So that the
 * user can select which vidoes to watch
 */
function videoInit() {
    cameraSelect = document.getElementById("cameraSourceSelector");
    cameraSelect.innerHTML += "<option " +
            "value='102'>" +
            "BU Weather Camera</option>";

    mapSelect = document.getElementById("mapSourceSelector");

    //camera.src = '/WeatherViewerLite-2/videos/WeatherViewerCameraVideo/BUWeatherCamera20150323-100000.mp4';

    /**mapSelect.innerHTML += "<option " +
     "value='/WeatherViewerLite-2/videos/WeatherViewerMapVideo/ForecastMap_AMS20150323-100000.mp4'>" +
     "Forcast Map</option>";*/
    mapSelect.innerHTML += "<option " +
            "value='104'>" +
            "Intellicast_RADAR_National</option>";

    updateVid();
}

/********************************
 ********************************
 *
 *   Misc Functions
 * 
 ********************************/

/**
 *  loads the new vidoes upon selection from combo box
 */
function updateVid() {
    var year1 = localStorage.getItem('year');//$('#date11').data('year');
    var mm1 = localStorage.getItem('mm');//$('#date22').data('mm');
    var dd1 = localStorage.getItem('dd');//$('#date33').data('dd');
    cameraSelect = document.getElementById("cameraSourceSelector");
    mapSelect = document.getElementById("mapSourceSelector");

    cameraURL = createCameraFileName(cameraSelect.value, year1, mm1, dd1);
    mapURL = createMapFileName(mapSelect.value, year1, mm1, dd1);
    
    //check if the Video Exists
    if (!checkIfVideoFileExists(cameraURL)) {
        cameraURL = passVideoNotFindFileName();
    }
    
    
    //if(mapSelect.value==104)
    map.src = mapURL;
    //else
    //map.src=mapSelect.value;

    //if(cameraSelect.value==102)
    camera.src = cameraURL;

    camera.load();
    map.load();
}

/**
 * keeps track of the current time of the camera vid and synchs the 
 * trackbar and the graph bar to the current time.
 */
function onTick() {
    currentPrecentage = camera.currentTime / camera.duration;
    //trackVideoTrackBar(currentPrecentage);
    trackVideoChartBar(currentPrecentage);
    //timeCountdown(isPlay);
}



/*
 * 
 * //////////////////////the function blow may need to move to other js file//////////////
 */
/*
 * This function return the name of the video file
 */
function passCameraFileName() {
    var year1 = localStorage.getItem('year');//$('#date11').data('year');
    var mm1 = localStorage.getItem('mm');//$('#date22').data('mm');
    var dd1 = localStorage.getItem('dd');//$('#date33').data('dd');
    cameraSelect = document.getElementById("cameraSourceSelector");
    cameraURL = createCameraFileName(cameraSelect.value, year1, mm1, dd1);
    if (checkIfVideoFileExists(cameraURL)) {
        return cameraURL;
    } else {
        return passVideoNotFindFileName();
    }
}

/*
 * This function check if the video file exist or not
 */
function checkIfVideoFileExists(fileURL) {
    var xhr = new XMLHttpRequest();
    xhr.open('HEAD', fileURL, false);
    xhr.send();

    if (xhr.status == "404") {
        return false;
    } else {
        return true;
    }
}