document.getElementById("logout").onclick = function () {
    $.ajax(
        {
            url: "/api/authentication/user/logout",
            type: "POST",
            data: {
            },
            success:
                function(response) {
                    location.pathname = "/api/authentication/user/login";
                },
            error:
                function(response) {
                    location.pathname = "/api/authentication/user/login";
                }
        }
    )
}