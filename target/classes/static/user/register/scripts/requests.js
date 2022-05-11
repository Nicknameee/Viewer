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
function validate_nickname() {
    drop()
    if ($("#nickname").val().match(/^[a-zA-Z0-9]{5,16}$/) == null) {
        result.bool = false
        result.error = "Valid username is required: length [5;16], symbols , digits"
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
let invalidInputCounter = 0;
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

    if (validate_password().bool === false) {
        let thisAlert = $("#password").parent();
        thisAlert.attr('data-validate' ,validate_password().error);
        $(thisAlert).addClass('alert-validate');
        invalidInputCounter++;
    }
    else {
        let thisAlert = $("#password").parent();
        $(thisAlert).removeClass('alert-validate');
    }

    if (validate_confirmation_password().bool  === false) {
        let thisAlert = $("#confirm-password").parent();
        thisAlert.attr('data-validate', validate_confirmation_password().error);
        $(thisAlert).addClass('alert-validate');
        invalidInputCounter++;
    }
    else {
        let thisAlert = $("#confirm-password").parent();
        $(thisAlert).removeClass('alert-validate');
    }

    if (validate_nickname().bool === false) {
        let thisAlert = $("#nickname").parent();
        thisAlert.attr('data-validate' ,validate_nickname().error);
        $(thisAlert).addClass('alert-validate');
        invalidInputCounter++;
    }
    else {
        let thisAlert = $("#nickname").parent();
        $(thisAlert).removeClass('alert-validate');
    }

    if (invalidInputCounter === 0 && !credentialsGood) {
        $.ajax(
            {
                url: "/api/manager/credentials/reserved",
                type: "GET",
                data: {
                    mail: $("#username").val() ,
                    username: $("#nickname").val()
                },
                success:
                    function(response) {
                        credentialsGood = response.success === true;
                        if (credentialsGood === false) {
                            if (response.error === "mail") {
                                let thisAlert = $("#username").parent();
                                thisAlert.attr('data-validate' ,"Mail address already reserved , try recovering");
                                $(thisAlert).addClass('alert-validate');
                            }
                            else {
                                let thisAlert = $("#username").parent();
                                $(thisAlert).removeClass('alert-validate');
                            }
                            if (response.error === "username") {
                                let thisAlert = $("#nickname").parent();
                                thisAlert.attr('data-validate' , "This username is taken");
                                $(thisAlert).addClass('alert-validate');
                            }
                            else {
                                let thisAlert = $("#nickname").parent();
                                $(thisAlert).removeClass('alert-validate');
                            }
                            if (response.error === "all") {
                                let thisAlertU = $("#username").parent();
                                thisAlertU.attr('data-validate' ,"Mail address already reserved , try recovering");
                                $(thisAlertU).addClass('alert-validate');

                                let thisAlert = $("#nickname").parent();
                                thisAlert.attr('data-validate' , "This username is taken");
                                $(thisAlert).addClass('alert-validate');
                            }
                        }
                        else {
                            prepareForm();
                            $.ajax(
                                {
                                    url: "/api/mail/verification",
                                    type: "POST",
                                    data: {
                                        recipient: $("#username").val() ,
                                        mailType: "HTML" ,
                                        userActionType: "CONFIRM_REGISTRATION"
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
                    },
                error:
                    function(response) {
                        credentialsGood = false
                    }
            }
        )
    }

    if (credentialsGood) {
        if (validate_code().bool === false) {
            let thisAlert = $("#code").parent();
            thisAlert.attr('data-validate', validate_code().error);
            $(thisAlert).addClass('alert-validate');
        } else {
            let thisAlert = $("#code").parent();
            $(thisAlert).removeClass('alert-validate');
            $.ajax(
                {
                    url: "/api/user/confirm",
                    type: "POST",
                    data: {
                        id: 0 ,
                        mail: $("#username").val() ,
                        actionType: "CONFIRM_REGISTRATION" ,
                        code: $("#code").val()
                    },
                    success:
                        function(response) {
                            if (response.success) {
                                let thisAlert = $("#code").parent();
                                $(thisAlert).removeClass('alert-validate');
                                $.ajax(
                                    {
                                        url: "/api/user/register",
                                        type: "POST",
                                        data: {
                                            mail: $("#username").val() ,
                                            password: $("#password").val() ,
                                            username: $("#nickname").val() ,
                                            code: $("#code").val()
                                        },
                                        success:
                                            function(response) {
                                                if (response.success) {
                                                    showMessage()
                                                    /*
                                                        We need give time to write new user in DB , so we can proceed an authentication
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
        $("#password-box").fadeOut(1250);
        $("#confirm-password-box").fadeOut(1250);
        $("#nickname-box").fadeOut(1250);
        $("#submit-box").fadeOut(1250);
        $("#have-acc").fadeOut(1250);
        /*
            Showing the required
         */
        $("#code-box").fadeIn(1250);
        $("#code-btn-box").fadeIn(1250);
}
function showMessage() {
    //$("#code-box").fadeOut(600);
    $("#code-btn-box").fadeOut(600);
    $("#code-box").html("<span class=\"login100-form-title\">Redirecting...</span>")
}