angular.module('chatMod').factory('ChatService', function() {

    var service = {};

    service.connect = function(appointmentId) {

        if(service.websocket) { return; }

        console.log("ws://176.36.11.25:8080/chat/websocket/" + appointmentId);

        var websocket = new WebSocket("ws://176.36.11.25:8080/chat/websocket/" + appointmentId);

        var ONLINE;

        window.onbeforeunload = function() {
            websocket.close();
        }

        websocket.onopen = function(event) {
            console.log("Connection succesful");
            service.callback(websocket.readyState);
        };

        websocket.onclose = function(event){
            service.callback('Close');
        }
        websocket.onerror = function(event) {
            service.callback('Error');
        }

        websocket.onmessage = function(event) {
            service.callback(JSON.parse(event.data));
        };

        service.websocket = websocket;
    }

    service.send = function(message) {
        service.websocket.send(message);
    }

    service.subscribe = function(callback) {
        service.callback = callback;
    }

    return service;
})
