/* 
 * Hanlin Shen
 * 11/17/2015
 * All the function in this js file is for mune item 
 */


function openMenuFileOpenDiv(){
    document.getElementById("menuFileOpen").style.display = "block";
    document.getElementById("menuMask").style.display = "block";
}

function openMenuFileSaveDiv(){
    var cameraURL= passCameraFileName();
    document.getElementById("downloadButton").setAttribute("href", cameraURL);
    document.getElementById("downloadButton").setAttribute("download", cameraURL);
    document.getElementById("menuFileSave").style.display = "block";
    document.getElementById("menuMask").style.display = "block";
}

function closeMenuMaskDiv() {
    if(document.getElementById("menuFileOpen").style.display==="block"){
        document.getElementById("menuFileOpen").style.display = "none";
    }
    
    if(document.getElementById("menuFileSave").style.display==="block"){
        document.getElementById("menuFileSave").style.display = "none";
    }
    
    document.getElementById("menuMask").style.display = "none";
}




