/* 
 * Hanlin Shen
 * 11/17/2015
 * All the function in this js file is used for load local file
 */

var videoNode;
var fileURL;

//this function is from http://jsfiddle.net/dsbonev/cCCZ2/embedded/result,js,html,css/presentation/
(function localFileVideoPlayerInit(win) {
    var URL = win.URL || win.webkitURL,
            displayMessage = (function displayMessageInit() {
                var node = document.querySelector('#message');

                return function displayMessage(message, isError) {
                    node.innerHTML = message;
                    node.className = isError ? 'error' : 'info';
                };
            }()),
            playSelectedFile = function playSelectedFileInit(event) {
                var file = this.files[0];

                var type = file.type;

                var fullPath = document.getElementById("localVideoSelectInput").value;

                videoNode = document.querySelector("#camera");

                var canPlay = videoNode.canPlayType(type);

                canPlay = (canPlay === '' ? 'no' : canPlay);

                var message = 'Can play type "' + type + '": ' + canPlay;

                var isError = canPlay === 'no';

                displayMessage(message, isError);

                if (isError) {
                    return;
                }

                var fileURL = URL.createObjectURL(file);
                //videoNode.src = fileURL;
                document.getElementById("localVideoSrc").value = fileURL;
                //get the video file name
                if (fullPath) {
                    var startIndex = (fullPath.indexOf('\\') >= 0 ? fullPath.lastIndexOf('\\') : fullPath.lastIndexOf('/'));
                    var videoName = fullPath.substring(startIndex);
                    if (videoName.indexOf('\\') === 0 || videoName.indexOf('/') === 0) {
                        videoName = videoName.substring(1);
                    }
                    document.getElementById("videoNameSelected").innerHTML = videoName;
                }
            },
            inputNode = document.querySelector("#localVideoSelectInput");

    if (!URL) {
        displayMessage('Your browser is not ' +
                'supported!', true);

        return;
    }

    inputNode.addEventListener('change', playSelectedFile, false);
}(window));

//play the local Video
function playLocalVideo() {
    if (document.getElementById("localVideoSrc").value !== "null") {
        videoNode.src = document.getElementById("localVideoSrc").value;
    }
    closeMenuMaskDiv();
}