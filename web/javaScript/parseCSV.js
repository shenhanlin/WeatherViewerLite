/* 
 * This class starts everything.
 * This script parses the csv file that will be associated with the date
 * and stores all the info in lists.
 * TODO: add the rest of the info to new lists.
 * 
 * @author: Aleks Hartzler (aeh75945)
 */

/********************************
 ********************************
 *
 *   Variables
 * 
 ********************************/

window.addEventListener("DOMContentLoaded", parseInit);

var url;//="/WeatherViewerLite-2/csv/Bloomsburg-Underground_11-17-2015.csv";
var csvRequest;

var year = [];
var month = [];
var day = [];
var hour = [];
var min = [];

var temp = [];
var dewPt = [];

/********************************
 ********************************
 *
 *  Init Function
 * 
 ********************************/

/**
 * This function gets the csv and parses it and puts the data into the
 * global arrays.
 */
function parseInit() {
    
    var dateTime;
    var time;
    var date; 
    var entries;
    var subentries;
    var year1=localStorage.getItem('year');//$('#date11').data('year');
    var mm1=localStorage.getItem('mm');//$('#date22').data('mm');
    var dd1=localStorage.getItem('dd');//$('#date33').data('dd');
    url=createCSVFileName(year1, mm1, dd1);
    //console.log("url %s", url);
    //Creating a new AJAX request that will request 'test.csv' from the current directory
    
    
    csvRequest = new Request({
        url: url,
        onSuccess: function (response) {
            entries = response.split('<br>,,,,,,,,,,,,,,,');
            
            for (var i = 1; i < entries.length - 1; i++) {
                subentries = entries[i].split(',');
                
                dateTime = subentries[0].split(' ');
                date = dateTime[0].split('/');
                
                year[i - 1] = date[0];
                month[i - 1] = date[1];
                day[i - 1] = date[2];
                
                time = dateTime[1].split(':');
                hour[i - 1] = time[0];
                min[i - 1] = time[1];

                temp[i - 1] = subentries[1];
                dewPt[i - 1] = subentries[2];
            }
        
            init(); //starts the rest of setting up the page
        },
        onFailure: function (response) {
            init();
        }
        
    }).send(); //Don't forget to send our request!
}

function parseInit2() {
    
    var dateTime;
    var time;
    var date; 
    var entries;
    var subentries;
    
    //Creating a new AJAX request that will request 'test.csv' from the current directory
    csvRequest = new Request({
        url: url,
        onSuccess: function (response) {
            entries = response.split('<br>,,,,,,,,,,,,,,,');
            
            for (var i = 1; i < entries.length - 1; i++) {
                subentries = entries[i].split(',');
                
                dateTime = subentries[0].split(' ');
                date = dateTime[0].split('/');
                
                year[i - 1] = date[0];
                month[i - 1] = date[1];
                day[i - 1] = date[2];
                
                time = dateTime[1].split(':');
                hour[i - 1] = time[0];
                min[i - 1] = time[1];

                temp[i - 1] = subentries[1];
                dewPt[i - 1] = subentries[2];
            }
            
            chartInit(); //starts the rest of setting up the page
        },
        onFailure: function (response) {
            chartInit();
        }
    }).send(); //Don't forget to send our request!
}

/********************************
 ********************************
 *
 *   getters for the arrays
 * 
 ********************************/

/**
 *  This function gets the date and temp
 * @returns {Array} A 2D array the first value of each element is the time and the 
 * second is the temp associated with that time
 */
function getTempData() {
    var ret = [];
    for(var i = 0; i < year.length; i++) {
        ret[i] = [Date.UTC(year[i], month[i] - 1, day[i], hour[i], min[i], 0, 0), +temp[i]];
    }
    return ret;
}

/**
 * This function gets the date and dew point
 * @returns {Array} A 2D array the first value of each element is the time and the 
 * second is the dew point temp associated with that time
 */
function getDewPtData() {
    var ret = [];
    for(var i = 0; i < year.length; i++) {
        ret[i] = [Date.UTC(year[i], month[i] - 1, day[i], hour[i], min[i], 0, 0), +dewPt[i]];
    }
    return ret;
}

/********************************
 ********************************
 *
 *   Temporary Functions
 * 
 ********************************/

/**
 * A temp function the starting date. Normally the date should be decidec by the date
 */
function getDataStart() {
    return getTempData()[0];
}

/**
 *  loads the new csv file
 */
function updateCSV() {
    
    var year1=localStorage.getItem('year');//$('#date11').data('year');
    var mm1=localStorage.getItem('mm');//$('#date22').data('mm');
    var dd1=localStorage.getItem('dd');//$('#date33').data('dd');
    
    url=createCSVFileName(year1, mm1, dd1);
    parseInit2();
}
