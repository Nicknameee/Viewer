let error;
function valid()
{
    let input = $('.validate-input .input100');
    let check = true;

    for (var i = 0; i < input.length; i++) {
        if (validate(input[i]) === false) {
            showValidate(input[i]);
            check=false;
        }
        else {
            hideValidate(input[i])
        }
    }
    return check;
}
function validate (input) {
    if($(input).attr('id') === 'username') {
        if($(input).val().trim().match(/^([a-zA-Z0-9_\-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9\-]+\.)+))([a-zA-Z]{1,5}|[0-9]{1,3})(\]?)$/) == null) {
            error = "Username is invalid: address@xyz.com"
            return false;
        }
        return true;
    }
    if ($(input).attr('id') === 'password') {
        if ($(input).val().match(/^[a-zA-Z0-9@#]{6,16}$/) == null) {
            error = "Password value is invalid:\na-zA-Z0-9@# , range [6;16]"
            return false;
        }
        return true;
    }
}

function showValidate(input) {
    let thisAlert = $(input).parent();
    thisAlert.attr('data-validate' , error);
    $(thisAlert).addClass('alert-validate');
}

function hideValidate(input) {
    let thisAlert = $(input).parent();
    $(thisAlert).removeClass('alert-validate');
}