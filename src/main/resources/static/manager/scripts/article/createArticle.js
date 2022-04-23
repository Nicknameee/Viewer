function performReload(status) {
    drop()
    $("#title").val('')
    $("#content").val('')
    $("#media").val('')
    $("#response").text(status)
    $("#response-box").fadeIn(600)
    setTimeout(function() {
        $("#response-box").fadeOut(600)
    } , 5000)
}
$(document).ready(function () {
    $("#create-article-form").submit(function (event)
    {
        event.preventDefault();
        if (validate().bool === true)
        {
            let form = $("#create-article-form")[0]
            let dataFromForm = new FormData(form)
            $.ajax(
                {
                    url: "/api/manager/article/create",
                    type: "POST",
                    data: dataFromForm,
                    cache: false,
                    processData: false,
                    contentType: false,
                    success:
                        function(response) {
                            if (response.success) {
                                performReload("Article created successfully")
                            }
                            else {
                                performReload("Error occurs during creating an article")
                                console.log(response.error)
                            }
                        },
                    error:
                        function(response) {
                            performReload("Error occurs during creating an article")
                            console.log(response.error)
                        }
                }
            )
        }
    });
});