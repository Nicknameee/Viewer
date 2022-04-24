$(document).ready(function () {
    $("#find-form").submit(function (event)
    {
        event.preventDefault();
        if (validate("find").success === true)
        {
            $.ajax(
                {
                    url: "/api/manager/article/read?title=" + $("#find-title").val(),
                    type: "GET",
                    data: {
                    },
                    success:
                        function(response) {
                            if (response.success && response.article !== null && response.article.secret !== null) {
                                performReload("Article found", "find")
                                let link = document.createElement('a');
                                link.id = "article-link"
                                link.className = "w-100 d-flex justify-content-center link"
                                link.setAttribute('href' , '/api/all/article/' + response.article.secret)
                                link.innerHTML = "Here is this article..."
                                document.getElementById("find-response-box").insertAdjacentElement("afterend" , link)
                            }
                            else {
                                performReload("Article was not found" , "find")
                                console.log(response.error)
                            }
                        },
                    error:
                        function(response) {
                            performReload("Article was not found" , "find")
                            console.log(response.error)
                        }
                }
            )
        }
    });
});