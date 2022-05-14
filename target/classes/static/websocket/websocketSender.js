let stompClientArticle = null
let stompClientHome = null
let stompClientAdmin = null

function connectToSocket() {
    let socket = new SockJS('/stomp');

    stompClientArticle = Stomp.over(socket);
    stompClientHome = Stomp.over(socket);
    stompClientAdmin = Stomp.over(socket);

    stompClientArticle.connect({}, function (frame) {
    });

    stompClientHome.connect({}, function (frame) {
    });

    stompClientAdmin.connect({}, function (frame) {
    });
}

function sendAlertRequestArticlePage(secret) {
    stompClientArticle.send("/socket/article/alert", {}, JSON.stringify(secret));
}

function sendAlertRequestHomePage() {
    stompClientHome.send("/socket/home/alert", {}, null);
}

function sendAlertRequestAdminPage(actionType) {
    let meta = {
        user: $("#email").text(),
        action: actionType
    }
    stompClientAdmin.send("/socket/admin/alert", {}, JSON.stringify(meta))
}