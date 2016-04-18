/* 
 * This script handles the date/time box. It automatically sets the first date
 * to four hours ago and the second date to now.
 * 
 * check out http://amsul.ca/pickadate.js/date/ and http://amsul.ca/pickadate.js/api/
 * to learn about pickadate
 * 
 * @author: Aleks Hartzler (aeh75945)
 */

/********************************
 ********************************
 *
 *   Variables
 * 
 ********************************/

var date1TxtBox; //The text box that will hold date 1

// Date 1 and Date 2 are used for getting the video and data and forcast map
//date 1 is the start and date 2 is the end.
var date1;       //The first date

var picker1;

var hCount=0;
var mCount=0;
var sCount=0;
var isPlayedBefore=false;
var tempS=6;
var time1;



/********************************
 ********************************
 *
 *  Init Function
 * 
 *******************************/

/**
 * Adds a pickadate ot the text boxes and sets up the dropdown menues and the
 * am/pm buttons
 */
function dateTimeInit()
{
    date1 = new Date();
    date1.setMinutes(0);
    date1.setSeconds(0);
    date1.setMilliseconds(0);
    date1.setHours(date1.getHours() - 4);
    time1 = date1.getHours();
    
    var input = $('#date1').pickadate({
        clear: '',
        selectYears: true,
        format: 'mmm d  yyyy',
        max: new Date()
    });
    picker1 = input.pickadate('picker');
    picker1.on({
        close: function () {
            readDate();
            //checkDate();
        }
    });
    date1TxtBox = document.getElementById('date1');
    updateDates();
}

/****************************************
 ****************************************
 *
 *  Event Handlers
 *
 ***************************************/

/**
 * This function is the event handler for the AM/PM buttons every time the 
 * button is clicked it chagnes the value to either AM or PM depending.
 * @param {button} button the button clicked
 */
function amPm(button) {
    button.value === 'AM' ? button.value = 'PM' : button.value = 'AM';
    readDate();
}

/**
 * This function is the event handler for the buttons next day and previous
 * day. This function adds numDays to the current dates. numDays can be neg.
 * @param {int} numDays the number of days to add. (can be < 0)
 * TODO make the maximum today's date and one hour ago
 */
function addDay(numDays) {
    readDate();

    date1.setDate(date1.getDate() + numDays);

    updateDates();
}

/**
 * This function is the event handler for the buttons next four hours and 
 * prvious four hours.
 * @param {int} numHours the number of hours to hadd to the dates. (can be < 0)
 * TODO make the maximum today's date and one hour ago
 */
function addHours(numHours) {
    readDate();
    date1.setHours(date1.getHours() + numHours);
    updateDates();
}

/***********************************
 *********************************** 
 * 
 * Read / Set dates
 *
 ***********************************/

/**
 * reads the dates and times from the text and comboboxes
 */
function readDate() {
    var date1Words = date1TxtBox.value.split(" ");
    date1.setMonth(parseMonth(date1Words[0]));
    date1.setDate(date1Words[1]);
    date1.setYear(date1Words[3]);
    updateDates();
}

/**
 * This function sets one date to match another date
 * @param {Date} dateChanging the date which will change
 * @param {Date} dateMaster the date to which dateChanging will be set.
 */
function set(dateChanging, dateMaster) {
    dateChanging.setYear(dateMaster.getFullYear());
    dateChanging.setMonth(dateMaster.getMonth());
    dateChanging.setDate(dateMaster.getDate());
    dateChanging.setHours(dateMaster.getHours());
    updateDates();
}

/**
 * changes the abbr to a number that corresponds with that appr. (jan is 0)
 * @param {string} dateAbbr the date abbr
 * @returns {Number} the date Number
 */
function parseMonth(dateAbbr) {
    switch (dateAbbr) {
        case "Jan" :
            return 0;
        case "Feb" :
            return 1;
        case "Mar" :
            return 2;
        case "Apr" :
            return 3;
        case "May" :
            return 4;
        case "Jun" :
            return 5;
        case "Jul" :
            return 6;
        case "Aug" :
            return 7;
        case "Sep" :
            return 8;
        case "Oct" :
            return 9;
        case "Nov" :
            return 10;
        case "Dec" :
            return 11;
        default:
            return -1;
    }
}

/**
 * Takes the current dates and sets the textboxes and comboboxes to them
 */
function updateDates() {
    date1TxtBox.value = formatDate(date1.toDateString());

    //to display the date in the index page date div.
    var startDateString;
    var endDateString;
    startDateString = "Start Time<br>" + formatDateString(date1);
    endDateString = "End Time<br>" + formatDateString(date1);
    document.getElementById('startDateDiv').innerHTML = startDateString;
    document.getElementById('endDateDiv').innerHTML = endDateString;
    
    localStorage.setItem('year', date1.getFullYear());
    localStorage.setItem('mm', date1.getMonth()+1);
    localStorage.setItem('dd', date1.getDate());

    //$('#date11').data('year', date1.getFullYear());
    //$('#date22').data('mm', date1.getMonth()+1);
    //$('#date33').data('dd', date1.getDate());
    updateVid();
    updateCSV();
    
    document.getElementById('year1').innerHTML = date1.getFullYear();
    document.getElementById('year2').innerHTML = date1.getFullYear();
    document.getElementById('month1').innerHTML = date1.getMonth()+1;
    document.getElementById('month2').innerHTML = date1.getMonth()+1;
    document.getElementById('day1').innerHTML = date1.getDate();
    document.getElementById('day2').innerHTML = date1.getDate();
    document.getElementById('hours1').innerHTML =formatHours(date1.getHours());
    document.getElementById('hours2').innerHTML =formatHours(date1.getHours()+5);
    document.getElementById('min1').innerHTML ="00";
    document.getElementById('min2').innerHTML ="00";
    document.getElementById('second1').innerHTML ="00";
    document.getElementById('second2').innerHTML ="00";
    
    
}
/**
 * This function will take a date and format it to a string mm/dd/yyy hh:mm
 * 
 * @param {date} date
 * @returns {string} dateString
 */
function formatDateString(date) {
    var dateString;
    var timeString;
    if (date.getHours() < 10) {
        timeString = "0" + date.getHours();
    } else {
        timeString = date.getHours();
    }
    if (date.getMinutes() < 10) {
        timeString += ":0" + date.getMinutes();
    } else {
        timeString += ":" + date.getMinutes();
    }

    return dateString = (date.getMonth() + 1) + "/"
            + date.getDate() + "/"
            + date.getFullYear() + "   "
            + timeString;
}


/**
 * Gets the min date date2 can be
 * @returns {getMinDate.ret|Date} the min date that date2 can be
 */
function getMinDate() {
    var ret = new Date();
    ret.setYear(date1.getFullYear());
    ret.setMonth(date1.getMonth());
    ret.setDate(date1.getDate());
    ret.setHours(date1.getHours());
    ret.setMinutes(0);
    ret.setSeconds(0);
    ret.setMilliseconds(0);
    return ret;
}

/**
 * Gets the max date date2 can be
 * @returns {getMaxDate.ret|Date} the max date that date2 can be
 */
function getMaxDate() {
    var ret = new Date();
    ret.setYear(date1.getFullYear());
    ret.setMonth(date1.getMonth());

    ret.setDate(date1.getDate() + 7);
    if (compareTo(ret, new Date()) > 0)
        ret.setDate(new Date().getDate());


    ret.setHours(date1.getHours());
    ret.setMinutes(0);
    ret.setSeconds(0);
    ret.setMilliseconds(0);
    return ret;
}

/************************************
 ************************************
 * 
 *  Format Dates
 * 
 ************************************/

/**
 * Formats the hours for the hours combobox.
 * 
 * @param {Number} hours the hours in a 24hr format
 * @returns {Number} the hours in a 12 hour format
 */
function formatHours(hours) {
    if (hours === 0)
        return 12;
    if (hours > 12)
        return hours - 12;
    return hours;
}

/**
 * 
 * Formats the date to the format the date picker outputs
 * 
 * @param {type} dateString the date string in the format 'dayofweek mm dd yyyy'
 * @returns {String} the date string in the format 'mm dd  yyyy'
 */
function formatDate(dateString) {
    var words = dateString.split(' ');
    return "" + words[1] + " " + words[2] + "  " + words[3];
}

/******************************
 ******************************
 *
 * Charting Methods
 * 
 ******************************/

/**
 * gets the first date for chart
 * 
 * @returns {Number} the milliseconds of the date 1
 */
function getChartStartDate() {
    return Date.UTC(date1.getYear(), date1.getMonth(), date1.getDate(),
            0, 0);
}

/**
 * read the name of the function
 * 
 * @param {Date} date1 the first date
 * @param {Date} date2 the second date
 * @returns {Number} The time in millis that is the difference between
 */
function differenceInDates(date1, date2) {
    return Date.UTC(date1.getYear(), date1.getMonth(), date1.getDate(), date1.getHours(), 0, 0, 0)
            - Date.UTC(date2.getYear(), date2.getMonth(), date2.getDate(), date2.getHours(), 0, 0, 0);
}

function checkDate() {
    if (compareTo(date2, getMinDate()) < 0) {
        set(date2, date1);
        return;
}

}

function compareTo(date1, date2) {
    if (differenceInDates(date1, date2) > 0)
        return 1;
    if (differenceInDates(date1, date2) < 0)
        return -1;
    return 0;
}

function changeSpeed(speed)
{
    tempS *= speed;
}

function timeCountdown(isPlay)//not working correct
{
    if(isPlayedBefore)
    {
        updateDates();
        isPlayedBefore=false;
        
    }
    else
    {
        if (isPlay)
        {
            sCount += tempS;
            //sCount = Math.round(sCount);
            if (sCount < 10)
            {
                document.getElementById('second1').innerHTML = "0" + sCount;
                document.getElementById('second2').innerHTML = "0" + sCount;
            }
            else if (sCount >= 60)
            {
                sCount -= 60;
                document.getElementById('second1').innerHTML = "00";
                document.getElementById('second2').innerHTML = "00";

                mCount++;
                if (mCount < 10)
                {
                    document.getElementById('min1').innerHTML = "0" + mCount;
                    document.getElementById('min2').innerHTML = "0" + mCount;
                }
                else if (mCount == 60 || (document.getElementById("camera").ended))
                {
                    mCount = 0;
                    document.getElementById('min1').innerHTML = "00";
                    document.getElementById('min2').innerHTML = "00";
                    time1++;
                    document.getElementById('hours1').innerHTML = formatHours(time1);
                    document.getElementById('hours2').innerHTML = formatHours(time1+5);
                    isPlayedBefore = true;
                }
                else
                {
                    document.getElementById('min1').innerHTML = mCount;
                    document.getElementById('min2').innerHTML = mCount;
                }
            }
            else
            {
                document.getElementById('second1').innerHTML = sCount;
                document.getElementById('second2').innerHTML = sCount;
            }
        }
    }
}