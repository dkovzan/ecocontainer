'use strict';

var connectBtn = document.querySelector('#connectBtn');
var disconnectBtn = document.querySelector('#disconnectBtn');
var containerIdValue = document.querySelector("#containerId");
var temperatureValue = document.querySelector("#temp");
var humidityValue = document.querySelector("#humid");
var pressureValue = document.querySelector("#pressure");
var createdOnValue = document.querySelector("#createdOn");
var internalTimeValue = document.querySelector("#internalTime");
var notification = document.querySelector("#notification");

var stompClient = null;
var weather = null;

function init() {
    connect();
}

function connect() {
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, onConnected, onError);
    //event.preventDefault();
}

function onConnected() {
    // Subscribe to the Public Topic
    stompClient.subscribe('/topic/weather', onMessageReceived);
}

function onError(error) {
    console.log(error);
    notification.innerHTML = error;
}

function onMessageReceived(payload) {
    weather = JSON.parse(payload.body);
    fillWeather(weather);
    console.log("Received weather is inserted in HTML");
}

function fillWeather(weather) {
    //containerIdValue.innerHTML = weather.container_id;
    temperatureValue.innerHTML = weather.temperature;
    //pressureValue.innerHTML = weather.pressure;
    humidityValue.innerHTML = weather.humidity + '%';
}

function disconnect() {
    stompClient.disconnect();
}

window.addEventListener("load", init, false);

//connectBtn.addEventListener('click', connect, true);
//disconnectBtn.addEventListener('click', disconnect, true);