/**
 * This class creates a highchart
 * 
 * @author: Aleks Hartzler (aeh75945)
 */

/********************************
 ********************************
 *
 *   Variables
 * 
 ********************************/

var chart; //the highchart object
var graph; //the div the highchart will be placed in

var bar;   // a rect that tracks the progress of the vid

var firstDate = Date.UTC(2015, 2, 23, 6, 0, 0, 0);   //TEMP date 1 (in mills) TODO in the futere these dates will be decided by the dates in the date class
var secondDate = Date.UTC(2015, 2, 23, 9, 0, 0, 0);  //TEMP date 2 (in mills)
var diff = secondDate - firstDate;                   //The difference between the two dates

var yHeight; //the height of the plot area in pxs
var yOffset; //the top of the plot area in pxs

var cWidth = document.getElementById("graph").offsetWidth; //the width of the entire chart area
var cHeight = document.getElementById("graph").offsetHeight; //the height of the entire chart area


/********************************
 ********************************
 *
 *   Init Function
 * 
 ********************************/

/*
 * This function creates a chart and a bar that will move along the chart
 * 
 * TODO: Create a input tag of type select to select which values of the weather
 * Underground CSV to display (right now it's just dew point and temp)
 */
function chartInit() {
    graph = document.getElementById('graph');
    chart = new Highcharts.Chart(
    //A chart options object see http://www.highcharts.com/docs/getting-started/how-to-set-options
       //for more details
        {
            chart: {
                borderRadius: 3,
                width: cWidth,
                height: cHeight,
                renderTo: graph,
                className: 'mainTableDiv'
            },
            title: {
                text: 'Temerature vs Time'
            },
            xAxis: {
                type: 'datetime',
                labels: {
                    rotation: 40
                }
            },
            yAxis: {
                min: 0,
                title: {
                    text: 'Temperature (F)'
                },
                plotLines: [{
                    value: 0,
                    width: 1,
                    color: '#808080'
                }]
            },
            tooltip: {
                crosshairs: true
            },
            legend: {
                borderWidth: 0
            },
            series: [{
                name: 'Temperature',
                pointStart: getDataStart(),
                data: getTempData(),
                color: 'red'
            }, {
                name: 'Dew Point',
                pointStart: getDataStart(),
                data: getDewPtData(),
                color: 'green'
            }]
        }
    );
    
    yOffset = chart.yAxis[0].top;
    yHeight = chart.yAxis[0].height;
    
    //rendering the bar
    chart.renderer.rect(0, yOffset, 1, yHeight).
        attr({
            'id': 'bar',
            'x' : 0,
            'stroke-width': 1,
            stroke: 'black',
            zIndex: 0
        })
        .add();
    bar = document.getElementById('bar');
    bar.x.baseVal.value = chart.xAxis[0].toPixels(firstDate) - 1; //-1 adnusts the bar just enough to be centered over the date
}


/********************************
 ********************************
 *
 *   Timer Function
 * 
 ********************************/

/**
 * This funtion sets the bar to the ct precentage of the difference between
 * the dates of the video.
 * 
 * @param {float} ct the percentage through the camera video 
 */
function trackVideoChartBar(ct) {
    bar.x.baseVal.value = chart.xAxis[0].toPixels((ct * diff) + firstDate) - 1;
}

