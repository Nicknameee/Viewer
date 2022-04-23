$(document).ready(function () {
    $("#delete-form").submit(function (event)
    {
        event.preventDefault();
        if (validate("delete").success === true)
        {
            $.ajax(
                {
                    url: "/api/manager/article/delete?title=" + $("#delete-title").val(),
                    type: "DELETE",
                    data: {
                    },
                    success:
                        function(response) {
                            if (response.success) {
                                performReload("Article deleted" , "delete")
                            }
                            else {
                                performReload("Article was not found" , "delete")
                                console.log(response.error)
                            }
                        },
                    error:
                        function(response) {
                            performReload("Article was not found" , "delete")
                            console.log(response.error)
                        }
                }
            )
        }
    });
});