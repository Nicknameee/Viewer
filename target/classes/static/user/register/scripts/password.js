let passwordCount = 0
document.getElementById("password-eye").onclick = function () {
    passwordCount++;
    $("#password-eye").toggleClass('fa-eye-slash')
    $("#password-eye").toggleClass('fa-eye')
    if (passwordCount % 2 === 1) {
        $("#password").attr('type' , 'text')
    }
    else {
        $("#password").attr('type' , 'password')
    }
}

let passwordConfirmCount = 0
document.getElementById("password-confirm-eye").onclick = function () {
    passwordConfirmCount++;
    $("#password-confirm-eye").toggleClass('fa-eye-slash')
    $("#password-confirm-eye").toggleClass('fa-eye')
    if (passwordConfirmCount % 2 === 1) {
        $("#confirm-password").attr('type' , 'text')
    }
    else {
        $("#confirm-password").attr('type' , 'password')
    }
}