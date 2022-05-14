let stompClient = null;

function connectAdmin() {
    let socket = new SockJS('/stomp');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/topic/admin', function (response) {
            let meta = JSON.parse(response.body)
            if (meta.mail !== $("#email").text()) {
                $("#emailAlert").text(meta.mail)
                $("#actionAlert").text(meta.action)
                showChangesAlert()
            }
        });
    });
}