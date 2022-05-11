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