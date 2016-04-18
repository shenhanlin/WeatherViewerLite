/* 
 Author     : Heming Zhang
 *****************************************************************************************************
 * This part if for show the second order menu, when you click the main menu
 *****************************************************************************************************
 */
/*
function loadFun() 
{
    
    var array = document.getElementsByTagName("ul").item(0).childNodes;
    
    for (var i = 0; i < array.length; i++) {
        
        var childnodes = array[i].childNodes;
        for (var j = 0; j < childnodes.length; j++) {
            
            if (childnodes[j].tagName == "UL") {
                childnodes[j].style.display = "none";
            }
        }
    }
}
*/


var arrays = new Array("file","search","bookmark","lessons","weblinks","weathercamera","help");  
var temp;

function Show(tagId) {
    for (var i = 0; i < arrays.length; i++) {
        if (arrays[i] == tagId) {
            if(temp!=tagId && document.getElementById(arrays[i]).style.display!="block")
            {
                document.getElementById(arrays[i]).parentNode.style.backgroundColor = "#3184a1";
                document.getElementById(arrays[i]).style.display = "block";
                temp=tagId;
            }
            else
            {
                document.getElementById(arrays[i]).parentNode.style.backgroundColor = "#3184a1";
                document.getElementById(arrays[i]).style.display = "none";
                temp="";
            }
        }
    }
}
/*
function loadFun() 
{
    for (var i = 0; i < arrays.length; i++) { 
        document.getElementById(arrays[i]).parentNode.style.backgroundColor = "RGB(216,216,216)"; 
        document.getElementById(arrays[i]).style.display = "none"; 
    }
}*/
