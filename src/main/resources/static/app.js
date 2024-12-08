const stompClient = new StompJs.Client({
    brokerURL: 'ws://' + window.location.host + '/system-live-websocket'
});

stompClient.onConnect = (frame) => {
    setConnected(true);
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topics/livechat', (message) => {
        updateLiveChat(JSON.parse(message.body).content);
    });
};

stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
}

function connect() {
    stompClient.activate();
}

function disconnect() {
    stompClient.deactivate();
    setConnected(false);
    console.log("Disconnected");
}

function sendMessage() {
    const message = $("#message").val().trim();
    if (message === "") {
        return;
    }

    stompClient.publish({
        destination: "/app/new-message",
        body: JSON.stringify({'user': $("#user").val(), 'message': message})
    });
    $("#message").val(""); 
}

function updateLiveChat(message) {
    $("#livechat").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $( "#connect" ).click(() => connect());
    $( "#disconnect" ).click(() => disconnect());
    $( "#send" ).click(() => sendMessage());
});

function loadHistory() {
    fetch('/history')
        .then(response => response.json())
        .then(messages => {
            messages.forEach(message => {
                updateLiveChat(message.user + ": " + message.message);
            });
        });
}

$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $("#connect").click(() => {
        connect();
        loadHistory(); // Carregar o histórico ao conectar
    });
    $("#disconnect").click(() => disconnect());
    $("#send").click(() => sendMessage());
});