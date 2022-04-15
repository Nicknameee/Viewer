let result = {
    bool: true ,
    error: null
}
function drop() {
    result.bool = true
    result.error = null
}
function validate_promo() {
    drop()
    if ($("#promo").val().match(/^[a-zA-Z0-9-]{1,}$/) == null) {
        result.bool = false
        result.error = "Promo is invalid"
    }
    if (!result.bool) {
        showValidate($("#promo"))
    }
    else {
        hideValidate($("#promo"))
    }
    return result
}
function showValidate(input) {
    let thisAlert = $(input).parent();
    thisAlert.attr('data-validate' , result.error);
    $(thisAlert).addClass('input-validate');
}

function hideValidate(input) {
    let thisAlert = $(input).parent();
    $(thisAlert).removeClass('input-validate');
}
$(document).ready(function () {
    $("#form").submit(function (event)
    {
        event.preventDefault();
        if (validate_promo().bool === true)
        {
            $.ajax(
                {
                    url: "/api/manager/promo/use",
                    type: "POST",
                    data: {
                        code: $("#promo").val()
                    },
                    success:
                        function(response) {
                            if (!response.success) {
                                result.error = response.error;
                                showValidate($("#promo"))
                            }
                            else {
                                location.pathname = "/api/user/personal";
                            }
                        },
                    error:
                        function(response) {
                            result.error = response.error;
                            showValidate($("#promo"))
                        }
                }
            )
        }
    });
});