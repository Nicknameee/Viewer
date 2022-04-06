/****Validate part****/
let result = {
    bool: true,
    error: null
}
function drop() {
    result.bool = true;
    result.error = null;
}
function validate_username() {
    drop()
    if ($("#username").val().match(/^([a-zA-Z0-9_\-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9\-]+\.)+))([a-zA-Z]{1,5}|[0-9]{1,3})(\]?)$/) == null) {
        result.bool = false
        result.error = "Valid email is required: address@gmail.com"
    }
    return result
}
function validate_password() {
    drop()
    if ($("#password").val().match(/^[a-zA-Z0-9@]{8,16}$/) == null) {
        result.bool = false
        result.error = "Valid password is required: length [8;16], symbols , digits , @"
    }
    return result
}
function validate_confirmation_password() {
    drop()
    if ($("#confirm-password").val() !== $("#password").val()) {
        result.bool = false
        result.error = "Passwords does not match"
    }
    return result
}
function validate_code() {
    drop()
    if ($("#code").val().match(/^[a-zA-Z0-9-]{1,}$/) == null) {
        result.bool = false
        result.error = "Code is invalid"
    }
    return result
}
/****Request part***/
let credentialsGood = false;
let invalidInputCounter;
$("#form").submit(function (event) {
    invalidInputCounter = 0;
    event.preventDefault();
    if (validate_username().bool === false) {
        let thisAlert = $("#username").parent();
        thisAlert.attr('data-validate' ,validate_username().error);
        $(thisAlert).addClass('alert-validate');
        invalidInputCounter++;
    }
    else {
        let thisAlert = $("#username").parent();
        $(thisAlert).removeClass('alert-validate');
    }

    if (invalidInputCounter === 0 && !credentialsGood) {
        $.ajax(
            {
                url: "/api/manager/credentials/reserved",
                type: "GET",
                data: {
                    mail: $("#username").val()
                },
                success:
                    function(response) {
                        credentialsGood = response.success !== true;
                        if (credentialsGood === true) {
                            let thisAlert = $("#username").parent();
                            $(thisAlert).removeClass('alert-validate');
                            prepareForm();
                            $.ajax(
                                {
                                    url: "/api/mail/verification",
                                    type: "POST",
                                    data: {
                                        recipient: $("#username").val() ,
                                        mailType: "HTML" ,
                                        userActionType: "RESTORE_PASSWORD"
                                    },
                                    success:
                                        function(response) {
                                        },
                                    error:
                                        function(response) {
                                        }
                                }
                            )
                        }
                        else {
                            if (response.error === null) {
                                let thisAlert = $("#username").parent();
                                thisAlert.attr('data-validate' ,"Mail address was not recognized as registered");
                                $(thisAlert).addClass('alert-validate');
                            }
                        }
                    },
                error:
                    function(response) {
                        credentialsGood = false
                    }
            }
        )
    }

    if (credentialsGood === true) {
        let validateCount = 0;
        if (validate_code().bool === false) {
            let thisAlert = $("#code").parent();
            thisAlert.attr('data-validate', validate_code().error);
            $(thisAlert).addClass('alert-validate');
            validateCount++;
        }
        else {
            let thisAlert = $("#code").parent();
            $(thisAlert).removeClass('alert-validate');
        }
        if (validate_password().bool === false) {
            let thisAlert = $("#password").parent();
            thisAlert.attr('data-validate', validate_password().error);
            $(thisAlert).addClass('alert-validate');
            validateCount++;
        }
        else {
            let thisAlert = $("#password").parent();
            $(thisAlert).removeClass('alert-validate');
        }
        if (validate_confirmation_password().bool  === false) {
            let thisAlert = $("#confirm-password").parent();
            thisAlert.attr('data-validate', validate_confirmation_password().error);
            $(thisAlert).addClass('alert-validate');
            validateCount++;
        }
        else {
            let thisAlert = $("#confirm-password").parent();
            $(thisAlert).removeClass('alert-validate');
        }

        if (validateCount === 0) {
            $.ajax(
                {
                    url: "/api/user/confirm",
                    type: "POST",
                    data: {
                        id: 0 ,
                        mail: $("#username").val() ,
                        actionType: "RESTORE_PASSWORD" ,
                        code: $("#code").val()
                    },
                    success:
                        function(response) {
                            if (response.success) {
                                let thisAlert = $("#code").parent();
                                $(thisAlert).removeClass('alert-validate');
                                $.ajax(
                                    {
                                        url: "/api/user/update/password",
                                        type: "PUT",
                                        data: {
                                            mail: $("#username").val() ,
                                            newPassword: $("#password").val()
                                        },
                                        success:
                                            function(response) {
                                                if (response.success) {
                                                    showMessage()
                                                    /*
                                                        Updating password takes a time
                                                     */
                                                    setTimeout($.ajax(
                                                        {
                                                            url: "/api/authentication/user/login",
                                                            type: "POST",
                                                            data: {
                                                                username: $("#username").val() ,
                                                                password: $("#password").val() ,
                                                            },
                                                            success:
                                                                function (data) {
                                                                    setTimeout(function () {
                                                                        location.pathname = "/api/user/personal"
                                                                    } , 1500)
                                                                },
                                                            error:
                                                                function (data) {
                                                                    setTimeout(function () {
                                                                        location.pathname = "/api/user/personal"
                                                                    } , 1500)
                                                                }
                                                        }
                                                    ) , 1250)
                                                }
                                            },
                                        error:
                                            function(response) {
                                            }
                                    }
                                )
                            }
                            else {
                                let thisAlert = $("#code").parent();
                                thisAlert.attr('data-validate', response.error);
                                $(thisAlert).addClass('alert-validate');
                            }
                        },
                    error:
                        function(response) {
                        }
                }
            )
        }
    }
})

function prepareForm() {
        /*
         Hiding the rest
        */
        $("#username-box").fadeOut(1250);
        $("#submit-box").fadeOut(1250);
        /*
            Showing the required
         */
        $("#password-box").fadeIn(1250);
        $("#confirm-password-box").fadeIn(1250);
        $("#code-box").fadeIn(1250);
        $("#code-btn-box").fadeIn(1250);
}

function showMessage() {
    $("#password-box").fadeOut(600);
    $("#confirm-password-box").fadeOut(600);
    $("#code-btn-box").fadeOut(600);
    $("#code-box").html("<span class=\"login100-form-title\">Redirecting...</span>")
}