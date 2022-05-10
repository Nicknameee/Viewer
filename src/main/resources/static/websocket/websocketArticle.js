let stompClient = null;

function connectArticle() {
    let socket = new SockJS('/stomp');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/topic/article', function (response) {
            let secret = JSON.parse(response.body).secret
            let path = location.pathname
            let index = path.lastIndexOf('/')
            let secretCurrent = path.substring(index + 1)
            if (secret === secretCurrent) {
                alert("There are incoming changes here , reload page")
            }
        });
    });
}