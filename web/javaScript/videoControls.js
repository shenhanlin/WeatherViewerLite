/* 
 * This script is used to play two videos at the same time. The videos should
 * be completely synced up. if one pauses they both pause and if one plays
 * they both play. if you change the time of one, they both change. The
 * older brother video (the controlling video) is the camera video while the
 * younger brother video (the copying video) is the map video.
 * 
 * @author: Aleks Hartzler (aeh75945)
 */

/****************
 ****************
 *
 * Variables
 * 
 **************/

var camera;           // video that will be web cam     (source can change)
var map;              // video that will be weather map (source can change)

var playPauseButton;  // button used to play/pause videos
var cameraTrackBar;   // track bar used to show/change how far into the camera
                        // you are
//var speedTrackBar;    // track bar used to change the speed of the videos

var speed=0;             //value of the speed for played video
var speedLabel;       // label used to show speed

var vids = 4;

var count = 1;


var isEnded=false;
//var timer2;
/******************
 ******************
 *
 * Init methods
 *
 ***************/

/**
 * Initializes the elements and variables used
 *  - Camera Video
 *  - Map Video
 *  - Play/Pause button
 *  - speed Label
 *  - speed Trackbar
 *  - Camera Trackbar also see trackBarInit()
 */
function videoControlInit(){
    
    camera = document.getElementById("camera");
    //camera.addEventListener("ended", resetVid, false);
    camera.addEventListener("play", playPause, false);
    camera.addEventListener("pause", playPause, false);
    //camera.addEventListener("ended", function(){isEnded=true;}, false);
    
    map = document.getElementById("map");
    map.muted = true;
    
    playPauseButton = document.getElementById("playPauseButton");
    //playPauseButton.innerHTML = ' > ';
    
    speedLabel = document.getElementById("speedLabel");
    speedLabel.innerHTML = "1 ";
    
    //timer2= setInterval(timeCountdown(isPlay), 1);
    
    //camera.addEventListener("play", timer2 = self.setInterval(timeCountdown(), 1000/600));
    //camera.addEventListener("pause", timer2 = window.clearInterval(timer2));
    
    //speedTrackBar = document.getElementById("speedTrackBar");
    //speedTrackBar.addEventListener("input", speedChange, false);
    
    //cameraTrackBar  = document.getElementById("cameraTrackBar");
    //cameraTrackBar.addEventListener("input", trackBarChange, false);
    //cameraTrackBar.value = 0;
}

/**
 * This fucntion is called a lot.
 * It is called every time the video is paused or played or skiped forward
 *  or backwards or if the trackbar is changed by the user.
 * This function is probably more of a "just in case" function
 */
function syncUp() {
    if(camera.currentTime < map.duration)
        map.currentTime = camera.currentTime.toFixed(2);
    else {
        map.currentTime = map.duration.toFixed(2);
        map.pause();
    }
}

/******************
 ****************** 
 *
 * Play/Pause
 *
 ******************/

function playPause() {
    
    if(camera.paused) 
        play();
    else
        pause();
}

function play(){
    //playPauseButton.innerHTML = "||";
    //document.getElementById('pauseButton').style.cssText =' display: block;';
    //document.getElementById('playButton').style.cssText =' display: none';
    //isPlay=true;
    syncUp();
    //updateDates();
    //camera.play();
    map.play();
}

function pause(){
    
    //playPauseButton.innerHTML = ">";
    //document.getElementById('playButton').style.cssText ='display: block;';
    //document.getElementById('pauseButton').style.cssText ='display: none';
    //isPlay=false;
    syncUp();
    //camera.pause();
    map.pause();
}

/******************************
 ******************************
 *
 * Step Forward / Backward
 * 
 ******************************/

function stepForward()
{
    camera.currentTime += 1;
    syncUp();
    if(!camera.paused)
        play();
}

function stepBackward()
{
    camera.currentTime -= 1;
    syncUp();
    if(!camera.paused)
        play();
}

/***********************
 ***********************
 *
 * Speed change functions
 * 
 ***********************/

function speedChange(){
    //switch(speedTrackBar.value){
    switch(speed){        
        case -3: speedLabel.innerHTML = "1/4 "; speedChanger(.25) ; break;
        case -2: speedLabel.innerHTML = "1/2 "; speedChanger(.5)  ; break;
        case -1: speedLabel.innerHTML = "3/4 "; speedChanger(.75) ; break;
        case  0: speedLabel.innerHTML = "1   "; speedChanger(1)   ; break;
        case  1: speedLabel.innerHTML = "2   "; speedChanger(2)   ; break;
        case  2: speedLabel.innerHTML = "3   "; speedChanger(3)   ; break;
        case  3: speedLabel.innerHTML = "4   "; speedChanger(4)   ; break;
        default  : speedLabel.innerHTML = "ERR "; speedChanger(1)   ; break;
    }
}

function speedChanger(spdFctr){
    camera.defaultPlaybackRate = spdFctr;
    map.defaultPlaybackRate    = spdFctr;
    changeSpeed(spdFctr);
    syncUp();
}

/***********************
 ***********************
 *
 * Event Handlers
 * 
 ***********************/

/**
 * Called when the track bar is changed manualy; it sets the video to the
 * changed positon
 */
function trackBarChange() {
    camera.currentTime = cameraTrackBar.value / 100 * camera.duration;
    syncUp();
}

/**
 * called when the video is ended; resets the values of the vidoes
 */
function resetVid() {
    //playPauseButton.innerHTML = ">";
    camera.currentTime = 0;
    map.currentTime = 0;
    pause();
}

/**
 *  The function called when timer ticks
 *  Syncs track bar to video
 *   
 *   @param {float} ct the current time of the video
 */
function trackVideoTrackBar(ct) {
    cameraTrackBar.value = Math.floor(ct * 100);
}

/**
 *  The function that speed + 1 to play video faster
 *   
 */
function speedUp() {
    if(speed<3)
    {
        speed+=1;
        speedChange();
    }    
}
/**
 *  The function that speed - 1 to play video slower
 *   
 */
function speedDown() {
    if(speed>-3)
    {
        speed-=1;
        speedChange();
    }
}