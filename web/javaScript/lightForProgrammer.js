/* 
 Created on : Oct 23, 2015
 Author     : hanlin Shen
 *****************************************************************************************************
 * This part of code is only for programmer and use for change backgound color for
 * the main page and show or hid the border for div
 *****************************************************************************************************
 */

var color;
function openLight(){
    document.getElementById('wrapper').style.cssText ='background-color: #cccccc;';
    document.getElementById('openLight').style.cssText ='visibility: hidden;';
    document.getElementById('closeLight').style.cssText ='visibility: visible;';
    document.getElementById('rightHalf').style.cssText ='border: 0px;';
}
function closeLight(){
    document.getElementById('wrapper').style.cssText ='background-color: #000000;';
    document.getElementById('openLight').style.cssText ='visibility: visible;';
    document.getElementById('closeLight').style.cssText ='visibility: hidden;';
}

//        <button id="openLight" class = "lightButton" onClick="openLight()">
//        </button>
//        <div id="closeLight" style="visibility: hidden;" class = "lightButton"  onClick="closeLight()">
//        </div>