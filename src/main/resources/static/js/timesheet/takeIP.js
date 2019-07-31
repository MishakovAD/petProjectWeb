//СКРИПТ РАБОТАЕТ, но не везде. Определяет именно мой IP без всяких переадресаций. Ест ьсмысл сделать 2 ip у юзера. Этот может не заполняться.
var urlSendingIP = "getUserIP";

/**
 * Get the user IP throught the webkitRTCPeerConnection
 * @param onNewIP {Function} listener function to expose the IP locally
 * @return undefined
 * http://geekhouse.tech/how-to-get-the-client-ip-address-with-javascript-only/
 */
function getUserIP(onNewIP) { //  onNewIp - your listener function for new IPs
    //compatibility for firefox and chrome
    var myPeerConnection = window.RTCPeerConnection || window.mozRTCPeerConnection || window.webkitRTCPeerConnection;
    var pc = new myPeerConnection({
            iceServers: []
        }),
        noop = function() {},
        localIPs = {},
        ipRegex = /([0-9]{1,3}(\.[0-9]{1,3}){3}|[a-f0-9]{1,4}(:[a-f0-9]{1,4}){7})/g,
        key;

    function iterateIP(ip) {
        if (!localIPs[ip]) onNewIP(ip);
        localIPs[ip] = true;
    }

    //create a bogus data channel
    pc.createDataChannel("");

    // create offer and set local description
    pc.createOffer().then(function(sdp) {
        sdp.sdp.split('\n').forEach(function(line) {
            if (line.indexOf('candidate') < 0) return;
            line.match(ipRegex).forEach(iterateIP);
        });

        pc.setLocalDescription(sdp, noop, noop);
    }).catch(function(reason) {
        // An error occurred, so handle the failure to connect
    });

    //listen for candidate events
    pc.onicecandidate = function(ice) {
        if (!ice || !ice.candidate || !ice.candidate.candidate || !ice.candidate.candidate.match(ipRegex)) return;
        ice.candidate.candidate.match(ipRegex).forEach(iterateIP);
    };
}

// Usage

getUserIP(function(ip){
    console.log("getUserIp");
    sendIPToServer(urlSendingIP, {
        getUserIP: ip
    });

});

// Отправка запроса на сервер.
function sendIPToServer(url, data) {
    return $.ajax({
        url: url,
        data: data,
        datatype : "application/json",
        // По умолчанию идет GET.
        // method: 'POST',
        // Отменим кеширование.
        cache: false,
        success: function (response) {
            //alert("Запрос на сервер отправлен успешно!");
        }
    });
}