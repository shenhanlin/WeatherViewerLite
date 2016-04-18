/* 
 Created on : Oct 22, 2015, 9:58:10 AM
 Author     : hanlin shen
 *****************************************************************************************************
 * This part of code is picked from website linked blow
 * http://www.zztuku.com/JS/Tables/20121123_4272.html
 *This part of code is for making a div drgable
 *****************************************************************************************************
 */


/*-------------------------- +
 get, id, class, tagName
 +-------------------------- */
var get = {
    byId: function (id) {
        return typeof id === "string" ? document.getElementById(id) : id
    },
    byClass: function (sClass, oParent) {
        var aClass = [];
        var reClass = new RegExp("(^| )" + sClass + "( |$)");
        var aElem = this.byTagName("*", oParent);
        for (var i = 0; i < aElem.length; i++)
            reClass.test(aElem[i].className) && aClass.push(aElem[i]);
        return aClass
    },
    byTagName: function (elem, obj) {
        return (obj || document).getElementsByTagName(elem)
    }
};
var dragMinWidth = 500;
var dragMinHeight = 124;
/*-------------------------- +
 This function make the window dragable
 +-------------------------- */
function drag(oDrag, handle)
{
    var disX = dixY = 0;
    //var oMin = get.byClass("min", oDrag)[0];
    //var oMax = get.byClass("max", oDrag)[0];
    var oRevert = get.byClass("revert", oDrag)[0];
    //var oClose = get.byClass("close", oDrag)[0];
    handle = handle || oDrag;
    handle.style.cursor = "move";
    handle.onmousedown = function (event)
    {
        var event = event || window.event;
        disX = event.clientX - oDrag.offsetLeft;
        disY = event.clientY - oDrag.offsetTop;
        document.onmousemove = function (event)
        {
            var event = event || window.event;
            var iL = event.clientX - disX;
            var iT = event.clientY - disY;
            var maxL = document.documentElement.clientWidth - oDrag.offsetWidth;
            var maxT = document.documentElement.clientHeight - oDrag.offsetHeight;
            iL <= 0 && (iL = 0);
            iT <= 0 && (iT = 0);
            iL >= maxL && (iL = maxL);
            iT >= maxT && (iT = maxT);
            oDrag.style.left = iL + "px";
            oDrag.style.top = iT + "px";
            return false
        };
        document.onmouseup = function ()
        {
            document.onmousemove = null;
            document.onmouseup = null;
            this.releaseCapture && this.releaseCapture()
        };
        this.setCapture && this.setCapture();
        return false
    };
    //Maximize
//	oMax.onclick = function ()
//	{
//		oDrag.style.top = oDrag.style.left = 0;
//		oDrag.style.width = document.documentElement.clientWidth - 2 + "px";
//		oDrag.style.height = document.documentElement.clientHeight - 2 + "px";
//		this.style.display = "none";
//		oRevert.style.display = "block";
//	};
    //Revert
    oRevert.onclick = function ()
    {
        oDrag.style.left = (document.getElementById("leftBottomHalf").offsetLeft) + 5 + "px";
        oDrag.style.top = (document.getElementById("leftBottomHalf").offsetTop) + 5 + "px";
        oDrag.style.width = (document.getElementById("leftBottomHalf").offsetWidth) - 10 + "px";
        oDrag.style.height = (document.getElementById("leftBottomHalf").offsetHeight) - 10 + "px";
        document.getElementById("noteDivTextArea").style.cssText = 'width: 80%;';
    };
    //Minimize
//	oMin.onclick = oClose.onclick = function ()
//	{
//		oDrag.style.display = "none";
//		var oA = document.createElement("a");
//		oA.className = "open";
//		oA.href = "javascript:;";
//		oA.title = "还原";
//		document.body.appendChild(oA);
//		oA.onclick = function ()
//		{
//			oDrag.style.display = "block";
//			document.body.removeChild(this);
//			this.onclick = null;
//		};
//	};
//	oMin.onmousedown = oMax.onmousedown = oClose.onmousedown = function (event)
//	{
//		this.onfocus = function () {this.blur()};
//		(event || window.event).cancelBubble = true	
//	};
}

function dragMap(oDrag, handle)
{
    var disX = dixY = 0;
    handle = handle || oDrag;
    handle.style.cursor = "move";
    handle.onmousedown = function (event)
    {
        var event = event || window.event;
        disX = event.clientX - oDrag.offsetLeft;
        disY = event.clientY - oDrag.offsetTop;
        document.onmousemove = function (event)
        {
            var event = event || window.event;
            var iL = event.clientX - disX;
            var iT = event.clientY - disY;
            var maxL = document.documentElement.clientWidth - oDrag.offsetWidth;
            var maxT = document.documentElement.clientHeight - oDrag.offsetHeight;
            iL <= 0 && (iL = 0);
            iT <= 0 && (iT = 0);
            iL >= maxL && (iL = maxL);
            iT >= maxT && (iT = maxT);
            oDrag.style.left = iL + "px";
            oDrag.style.top = iT + "px";
            return false
        };
        document.onmouseup = function ()
        {
            document.onmousemove = null;
            document.onmouseup = null;
            this.releaseCapture && this.releaseCapture()
        };
        this.setCapture && this.setCapture();
        return false
    };
}


/*-------------------------- +
 This function can resize the box
 +-------------------------- */
function resize(oParent, handle, isLeft, isTop, lockX, lockY)
{
    handle.onmousedown = function (event)
    {
        var event = event || window.event;
        var disX = event.clientX - handle.offsetLeft;
        var disY = event.clientY - handle.offsetTop;
        var iParentTop = oParent.offsetTop;
        var iParentLeft = oParent.offsetLeft;
        var iParentWidth = oParent.offsetWidth;
        var iParentHeight = oParent.offsetHeight;
        document.onmousemove = function (event)
        {
            var event = event || window.event;
            var iL = event.clientX - disX;
            var iT = event.clientY - disY;
            var maxW = document.documentElement.clientWidth - oParent.offsetLeft - 2;
            var maxH = document.documentElement.clientHeight - oParent.offsetTop - 2;
            var iW = isLeft ? iParentWidth - iL : handle.offsetWidth + iL;
            var iH = isTop ? iParentHeight - iT : handle.offsetHeight + iT;
            isLeft && (oParent.style.left = iParentLeft + iL + "px");
            isTop && (oParent.style.top = iParentTop + iT + "px");
            iW < dragMinWidth && (iW = dragMinWidth);
            iW > maxW && (iW = maxW);
            lockX || (oParent.style.width = iW + "px");
            if (iW < 800) {
                document.getElementById("noteDivTextArea").style.width = "65%";
            }
            else {
                document.getElementById("noteDivTextArea").style.width = "80%";
            }

            iH < dragMinHeight && (iH = dragMinHeight);
            iH > maxH && (iH = maxH);
            lockY || (oParent.style.height = iH + "px");
            //change text box height when drg box resize
            document.getElementById("noteDivTextArea").style.height = iH - 100 + "px";
            if ((isLeft && iW == dragMinWidth) || (isTop && iH == dragMinHeight))
                document.onmousemove = null;
            return false;
        };
        document.onmouseup = function ()
        {
            document.onmousemove = null;
            document.onmouseup = null;
        };
        return false;
    }
}
;
window.onload = window.onresize = function ()
{
    var oMapDrag = document.getElementById("mapDrag");
    var oMapTitle = get.byClass("mapTitleLable", oMapDrag)[0];
    var oMapL = get.byClass("mapResizeL", oMapDrag)[0];
    var oMapT = get.byClass("mapResizeT", oMapDrag)[0];
    var oMapR = get.byClass("mapResizeR", oMapDrag)[0];
    var oMapB = get.byClass("mapResizeB", oMapDrag)[0];
    var oMapLT = get.byClass("mapResizeLT", oMapDrag)[0];
    var oMapTR = get.byClass("mapResizeTR", oMapDrag)[0];
    var oMapBR = get.byClass("mapResizeBR", oMapDrag)[0];
    var oMapLB = get.byClass("mapResizeLB", oMapDrag)[0];
    dragMap(oMapDrag, oMapTitle);
    //四角
    resizeMap(oMapDrag, oMapLT, true, true, false, false);
    resizeMap(oMapDrag, oMapTR, false, true, false, false);
    resizeMap(oMapDrag, oMapBR, false, false, false, false);
    resizeMap(oMapDrag, oMapLB, true, false, false, false);
    //四边
    resizeMap(oMapDrag, oMapL, true, false, false, true);
    resizeMap(oMapDrag, oMapT, false, true, true, false);
    resizeMap(oMapDrag, oMapR, false, false, false, true);
    resizeMap(oMapDrag, oMapB, false, false, true, false);
    oMapDrag.style.left = (document.getElementById("rightHalf").offsetLeft) + 5 + "px";
    oMapDrag.style.top = (document.getElementById("rightHalf").offsetTop) + 1 + "px";


    var oGraphDrag = document.getElementById("graphDrag");
    var oGraphTitle = get.byClass("graphTitleLable", oGraphDrag)[0];
    var oGraphL = get.byClass("graphResizeL", oGraphDrag)[0];
    var oGraphT = get.byClass("graphResizeT", oGraphDrag)[0];
    var oGraphR = get.byClass("graphResizeR", oGraphDrag)[0];
    var oGraphB = get.byClass("graphResizeB", oGraphDrag)[0];
    var oGraphLT = get.byClass("graphResizeLT", oGraphDrag)[0];
    var oGraphTR = get.byClass("graphResizeTR", oGraphDrag)[0];
    var oGraphBR = get.byClass("graphResizeBR", oGraphDrag)[0];
    var oGraphLB = get.byClass("graphResizeLB", oGraphDrag)[0];
    dragMap(oGraphDrag, oGraphTitle);
    //四角
    resizeGraph(oGraphDrag, oGraphLT, true, true, false, false);
    resizeGraph(oGraphDrag, oGraphTR, false, true, false, false);
    resizeGraph(oGraphDrag, oGraphBR, false, false, false, false);
    resizeGraph(oGraphDrag, oGraphLB, true, false, false, false);
    //四边
    resizeGraph(oGraphDrag, oGraphL, true, false, false, true);
    resizeGraph(oGraphDrag, oGraphT, false, true, true, false);
    resizeGraph(oGraphDrag, oGraphR, false, false, false, true);
    resizeGraph(oGraphDrag, oGraphB, false, false, true, false);
    oGraphDrag.style.left = (document.getElementById("mapDrag").offsetLeft) + 1 + "px";
    oGraphDrag.style.top = (document.getElementById("rightHalf").offsetTop) + 430 + "px";



    var oDrag = document.getElementById("drag");
    var oTitle = get.byClass("title", oDrag)[0];
    var oL = get.byClass("resizeL", oDrag)[0];
    var oT = get.byClass("resizeT", oDrag)[0];
    var oR = get.byClass("resizeR", oDrag)[0];
    var oB = get.byClass("resizeB", oDrag)[0];
    var oLT = get.byClass("resizeLT", oDrag)[0];
    var oTR = get.byClass("resizeTR", oDrag)[0];
    var oBR = get.byClass("resizeBR", oDrag)[0];
    var oLB = get.byClass("resizeLB", oDrag)[0];
    drag(oDrag, oTitle);
    //四角
    resize(oDrag, oLT, true, true, false, false);
    resize(oDrag, oTR, false, true, false, false);
    resize(oDrag, oBR, false, false, false, false);
    resize(oDrag, oLB, true, false, false, false);
    //四边
    resize(oDrag, oL, true, false, false, true);
    resize(oDrag, oT, false, true, true, false);
    resize(oDrag, oR, false, false, false, true);
    resize(oDrag, oB, false, false, true, false);
    //oDrag.style.left = (document.documentElement.clientWidth - oDrag.offsetWidth) / 2 + "px";
    //oDrag.style.top = (document.documentElement.clientHeight - oDrag.offsetHeight) / 2 + "px";
    oDrag.style.left = (document.getElementById("leftBottomHalf").offsetLeft) + 5 + "px";
    oDrag.style.top = (document.getElementById("leftBottomHalf").offsetTop) + 5 + "px";
    oDrag.style.width = (document.getElementById("leftBottomHalf").offsetWidth) - 10 + "px";
    oDrag.style.height = (document.getElementById("leftBottomHalf").offsetHeight) - 10 + "px";
    if (oDrag.offsetWidth < 810) {
        document.getElementById("noteDivTextArea").style.width = "65%";
    }
    else {
        document.getElementById("noteDivTextArea").style.width = "80%";
    }
};

function resizeGraph(oParent, handle, isLeft, isTop, lockX, lockY)
{
    handle.onmousedown = function (event)
    {
        var event = event || window.event;
        var disX = event.clientX - handle.offsetLeft;
        var disY = event.clientY - handle.offsetTop;
        var iParentTop = oParent.offsetTop;
        var iParentLeft = oParent.offsetLeft;
        var iParentWidth = oParent.offsetWidth;
        var iParentHeight = oParent.offsetHeight;
        document.onmousemove = function (event)
        {
            var event = event || window.event;
            var iL = event.clientX - disX;
            var iT = event.clientY - disY;
            var maxW = document.documentElement.clientWidth - oParent.offsetLeft - 2;
            var maxH = document.documentElement.clientHeight - oParent.offsetTop - 2;
            var iW = isLeft ? iParentWidth - iL : handle.offsetWidth + iL;
            var iH = isTop ? iParentHeight - iT : handle.offsetHeight + iT;
            isLeft && (oParent.style.left = iParentLeft + iL + "px");
            isTop && (oParent.style.top = iParentTop + iT + "px");
            iW < dragMinWidth && (iW = dragMinWidth);
            iW > maxW && (iW = maxW);
            lockX || (oParent.style.width = iW + "px");
            chartInit();
            iH < dragMinHeight && (iH = dragMinHeight);
            iH > maxH && (iH = maxH);
            lockY || (oParent.style.height = iH + "px");
            var temp = iH - document.getElementById("graphControl").offsetHeight;
            temp = temp - document.getElementById("graphTitle").offsetHeight;
            document.getElementById("graph").style.height = temp + 10 + "px";
            chartInit();
            if ((isLeft && iW == dragMinWidth) || (isTop && iH == dragMinHeight))
                document.onmousemove = null;
            return false;
        };
        document.onmouseup = function ()
        {
            document.onmousemove = null;
            document.onmouseup = null;
        };
        return false;
    }
}
;

function resizeMap(oParent, handle, isLeft, isTop, lockX, lockY)
{
    handle.onmousedown = function (event)
    {
        var event = event || window.event;
        var disX = event.clientX - handle.offsetLeft;
        var disY = event.clientY - handle.offsetTop;
        var iParentTop = oParent.offsetTop;
        var iParentLeft = oParent.offsetLeft;
        var iParentWidth = oParent.offsetWidth;
        var iParentHeight = oParent.offsetHeight;
        document.onmousemove = function (event)
        {
            var event = event || window.event;
            var iL = event.clientX - disX;
            var iT = event.clientY - disY;
            var maxW = document.documentElement.clientWidth - oParent.offsetLeft - 2;
            var maxH = document.documentElement.clientHeight - oParent.offsetTop - 2;
            var iW = isLeft ? iParentWidth - iL : handle.offsetWidth + iL;
            var iH = isTop ? iParentHeight - iT : handle.offsetHeight + iT;
            isLeft && (oParent.style.left = iParentLeft + iL + "px");
            isTop && (oParent.style.top = iParentTop + iT + "px");
            iW < dragMinWidth && (iW = dragMinWidth);
            iW > maxW && (iW = maxW);
            lockX || (oParent.style.width = iW + "px");
            iH < dragMinHeight && (iH = dragMinHeight);
            iH > maxH && (iH = maxH);
            lockY || (oParent.style.height = iH + "px");

            var temp = iH - document.getElementById("mapControl").offsetHeight;
            temp = temp - document.getElementById("mapTitle").offsetHeight;
            document.getElementById("m_map").style.height = temp + 10 + "px";

            if ((isLeft && iW == dragMinWidth) || (isTop && iH == dragMinHeight))
                document.onmousemove = null;
            return false;
        };
        document.onmouseup = function ()
        {
            document.onmousemove = null;
            document.onmouseup = null;
        };
        return false;
    }
}
;

function revertALL() {
    document.getElementById("mapDrag").style.maxheight = 430 + "px";
    document.getElementById("mapDrag").style.maxwidth = 500 + "px";
    document.getElementById("m_map").style.maxheight = 371 + "px";
    document.getElementById("mapDrag").style.left = (document.getElementById("rightHalf").offsetLeft) + 5 + "px";
    document.getElementById("mapDrag").style.top = (document.getElementById("rightHalf").offsetTop) + 1 + "px";
    document.getElementById("graphDrag").style.maxheight = 420 + "px";
    document.getElementById("graphDrag").style.maxwidth = 500 + "px";
    document.getElementById("graph").style.maxheight = 360 + "px";
    document.getElementById("graphDrag").style.left = (document.getElementById("mapDrag").offsetLeft) + 1 + "px";
    document.getElementById("graphDrag").style.top = (document.getElementById("rightHalf").offsetTop) + 430 + "px";
    chartInit();
}
;

function hideNoteDiv(){
    document.getElementById("drag").style.display ="none";
    document.getElementById("showNote").style.display ="block";
}

function showNoteDiv(){
    document.getElementById("drag").style.display ="block";
    document.getElementById("showNote").style.display ="none";
}