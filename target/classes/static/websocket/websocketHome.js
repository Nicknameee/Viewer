let stompClient = null;

function connectHome() {
    let socket = new SockJS('/stomp');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/topic/home', function (response) {
            let al = JSON.parse(response.body).isAlert
            if (al) {
                showChangesAlert()
            }
        });
    });
}