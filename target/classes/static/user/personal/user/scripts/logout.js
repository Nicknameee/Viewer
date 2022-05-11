document.getElementById("logout").onclick = function () {
    $.ajax(
        {
            url: "/api/authentication/user/logout",
            type: "POST",
            data: {
            },
            success:
                function(response) {
                    let link = location.protocol + location.host + "/api/authentication/user/login"
                    let url = new URL(link);
                    url.searchParams.append('logout' , 'true')
                    location.href = url.href
                },
            error:
                function(response) {
                    let link = location.protocol + location.host + "/api/authentication/user/login"
                    let url = new URL(link);
                    url.searchParams.append('logout' , 'true')
                    location.href = url.href
                }
        }
    )
}