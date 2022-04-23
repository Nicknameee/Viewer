$(document).ready(function () {
    $("#add-form").submit(function (event)
    {
        event.preventDefault();
        if (validate("add").success === true)
        {
            let form = $("#add-form")[0]
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
                                performReload("Article created successfully" , "add")
                            }
                            else {
                                performReload("Error occurs during creating an article" , "add")
                                console.log(response.error)
                            }
                        },
                    error:
                        function(response) {
                            performReload("Error occurs during creating an article" , "add")
                            console.log(response.error)
                        }
                }
            )
        }
    });
});