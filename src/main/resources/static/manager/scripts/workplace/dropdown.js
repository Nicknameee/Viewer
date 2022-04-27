$(window).resize(function () {
    if ($(window).width() < 1200) {
        $("#dropdown").removeClass('d-none')
        $("#home").addClass('d-none')
        $("#find-article").addClass('d-none')
        $("#add-article").addClass('d-none')
        $("#edit-article").addClass('d-none')
        $("#delete-article").addClass('d-none')
        $("#user-management").addClass('d-none')
        $("#promo-management").addClass('d-none')
        $("#profile").addClass('d-none')
        $("#logout").addClass('d-none')
        $("#menu").children('table').removeClass('h-100')
        $("#menu").removeClass('min-vh-100')
    }
    else {
        $("#dropdown").addClass('d-none')
        $("#home").removeClass('d-none')
        $("#find-article").removeClass('d-none')
        $("#add-article").removeClass('d-none')
        $("#edit-article").removeClass('d-none')
        $("#delete-article").removeClass('d-none')
        $("#user-management").removeClass('d-none')
        $("#promo-management").removeClass('d-none')
        $("#profile").removeClass('d-none')
        $("#logout").removeClass('d-none')
        $("#menu").children('table').addClass('h-100')
        $("#menu").addClass('min-vh-100')
    }

    if ($(window).width() < 768) {
        $("#user-data").addClass('d-flex flex-column align-content-center')
        let rows = $("#user-data").children('tr').addClass('row w-100 mx-0');
        $("#username-col").text('Users')
        $("#mail-col").addClass('d-none')
        $("#role-col").addClass('d-none')
        $("#status-col").addClass('d-none')
        $("#online-col").addClass('d-none')
        $("#confirm-col").addClass('d-none')
    }
    else {
        $("#user-data").removeClass('d-flex flex-column align-content-center')
        let rows = $("#user-data").children('tr').removeClass('row w-100 mx-0');
        $("#username-col").text('Username')
        $("#mail-col").removeClass('d-none')
        $("#role-col").removeClass('d-none')
        $("#status-col").removeClass('d-none')
        $("#online-col").removeClass('d-none')
        $("#confirm-col").removeClass('d-none')
    }
})
window.onload = function () {
    if ($(window).width() < 1200) {
        $("#dropdown").removeClass('d-none')
        $("#home").addClass('d-none')
        $("#find-article").addClass('d-none')
        $("#add-article").addClass('d-none')
        $("#edit-article").addClass('d-none')
        $("#delete-article").addClass('d-none')
        $("#user-management").addClass('d-none')
        $("#promo-management").addClass('d-none')
        $("#profile").addClass('d-none')
        $("#logout").addClass('d-none')
        $("#menu").children('table').removeClass('h-100')
        $("#menu").removeClass('min-vh-100')
    }
    else {
        $("#dropdown").addClass('d-none')
    }

    if ($(window).width() < 768) {
        $("#user-data").addClass('d-flex flex-column align-content-center')
        let rows = $("#user-data").children('tr').addClass('row w-100 mx-0');
        $("#username-col").text('Users')
        $("#mail-col").addClass('d-none')
        $("#role-col").addClass('d-none')
        $("#status-col").addClass('d-none')
        $("#online-col").addClass('d-none')
        $("#confirm-col").addClass('d-none')
    }
    else {
        $("#user-data").removeClass('d-flex flex-column align-content-center')
        let rows = $("#user-data").children('tr').removeClass('row w-100 mx-0');
        $("#username-col").text('Username')
        $("#mail-col").removeClass('d-none')
        $("#role-col").removeClass('d-none')
        $("#status-col").removeClass('d-none')
        $("#online-col").removeClass('d-none')
        $("#confirm-col").removeClass('d-none')
    }
}
function toggler() {
    $("#menu").toggleClass('min-vh-100')
    $("#menu").children('table').toggleClass('h-auto')
    $("#home").toggleClass('d-none')
    $("#find-article").toggleClass('d-none')
    $("#add-article").toggleClass('d-none')
    $("#edit-article").toggleClass('d-none')
    $("#delete-article").toggleClass('d-none')
    $("#user-management").toggleClass('d-none')
    $("#promo-management").toggleClass('d-none')
    $("#profile").toggleClass('d-none')
    $("#logout").toggleClass('d-none')
}
$("#dropdown").click(function () {
    toggler()
})