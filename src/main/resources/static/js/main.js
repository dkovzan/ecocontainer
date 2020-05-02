'use strict';

const host = "https://ecocontainer.herokuapp.com";

var connectBtn = document.querySelector('#connectBtn');
var disconnectBtn = document.querySelector('#disconnectBtn');
var containerIdValue = document.querySelector("#containerId");
var airTempValue = document.querySelector("#airTemp");
var airHumidityValue = document.querySelector("#airHumidity");
var co2Value = document.querySelector("#co2");
var waterPhValue = document.querySelector("#waterPh");
var waterEcValue = document.querySelector("#waterEc");
var airVentilation = document.querySelector("#airVentilation");
var lightGrow = document.querySelector("#lightGrow");
var lightSeed = document.querySelector("#lightSeed");
var lightWork = document.querySelector("#lightWork");
var createdOnValue = document.querySelector("#createdOn");
var internalTimeValue = document.querySelector("#internalTime");
var notification = document.querySelector("#notification");

var stompClient = null;
var containerData = null;

var xhr = new XMLHttpRequest();

function init() {
    xhr.open('GET', host + '/api/containerData/latest');
    xhr.send();
    xhr.onreadystatechange = function() {
        if (this.readyState !== 4) return;

        if(this.status !== 200) {
            alert('error while sending request GET /api/containerData/latest');
            return;
        }

        containerData = JSON.parse(this.responseText);
        fillContainerData(containerData);
    };

    connect();
}

function connect() {
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, onConnected, onError);
    //event.preventDefault();
}

function onConnected() {
    stompClient.subscribe('/topic/containerData', onMessageReceived);
}

function onError(error) {
    console.log(error);
    notification.innerHTML = error;
}

function onMessageReceived(payload) {
    containerData = JSON.parse(payload.body);
    fillContainerData(containerData);
    console.log("Received containerData is inserted in HTML");
}

function fillContainerData(containerData) {
    //containerIdValue.innerHTML = containerData.container_id;
    airTempValue.innerHTML = containerData.airTemp;
    airHumidityValue.innerHTML = containerData.airHumidity;
    co2Value.innerHTML = containerData.airCo2;
    waterPhValue.innerHTML = containerData.waterPh;
    waterEcValue.innerHTML = containerData.waterEc;
    airVentilation.innerHTML = getSwitchValue(containerData.airVentilation);
    lightGrow.innerHTML = getSwitchValue(containerData.lightGrow);
    lightSeed.innerHTML = getSwitchValue(containerData.lightSeed);
    lightWork.innerHTML = getSwitchValue(containerData.lightWork);
}

/*
function disconnect() {
    stompClient.disconnect();
}
*/

function getSwitchValue(on) {
    return on ? "ON" : "OFF";
}

window.addEventListener("load", init, false);
