let stompClientArticle = null
let stompClientHome = null

function connectToSocket() {
    let socket = new SockJS('/stomp');

    stompClientArticle = Stomp.over(socket);
    stompClientHome = Stomp.over(socket);

    stompClientArticle.connect({}, function (frame) {
    });

    stompClientHome.connect({}, function (frame) {
    });
}

function sendAlertRequestArticlePage(secret) {
    stompClientArticle.send("/socket/article/alert", {}, JSON.stringify(secret));
}

function sendAlertRequestHomePage() {
    stompClientHome.send("/socket/home/alert", {}, null);
}