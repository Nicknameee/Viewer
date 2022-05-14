function colspanOn() {
    $(document.getElementsByClassName('colspan')).attr('colspan' , '2')
}
function colspanOff() {
    $(document.getElementsByClassName('colspan')).attr('colspan' , '1')
}
$(window).resize(function () {
    /**
     * Menu transformation logic
     */
    if ($(window).width() < 1200) {
        $("#dropdown").removeClass('d-none')
        $("#home").addClass('d-none')
        $("#article-management").addClass('d-none')
        $("#user-management").addClass('d-none')
        $("#promo-management").addClass('d-none')
        $("#profile").addClass('d-none')
        $("#settings-management").addClass('d-none')
        $("#logout").addClass('d-none')
        $("#menu").children('table').removeClass('h-100')
        $("#menu").removeClass('min-vh-100')
    }
    else {
        $("#dropdown").addClass('d-none')
        $("#home").removeClass('d-none')
        $("#article-management").removeClass('d-none')
        $("#user-management").removeClass('d-none')
        $("#promo-management").removeClass('d-none')
        $("#profile").removeClass('d-none')
        $("#settings-management").removeClass('d-none')
        $("#logout").removeClass('d-none')
        $("#menu").children('table').addClass('h-100')
        $("#menu").addClass('min-vh-100')
    }

    if ($(window).width() < 768) {
        $("#add-payment").text('Add $')
        $("#payment-mod").text('List $')
        /**
         * Users management
         */
        let rows = $("#user-data").children('tr').addClass('row mx-0');
        $("#username-col").text('Users')
        $("#mail-col").addClass('d-none')
        $("#role-col").addClass('d-none')
        $("#status-col").addClass('d-none')
        $("#online-col").addClass('d-none')
        colspanOn()
        $("#user-head").addClass('row mx-0')
    }
    else {
        $("#add-payment").text('Add payment')
        $("#payment-mod").text('List payment')
        /**
         * Users management
         */
        let rows = $("#user-data").children('tr').removeClass('row mx-0');
        $("#username-col").text('Username')
        $("#mail-col").removeClass('d-none')
        $("#role-col").removeClass('d-none')
        $("#status-col").removeClass('d-none')
        $("#online-col").removeClass('d-none')
        colspanOff()
        $("#user-head").removeClass('row mx-0')
    }
})
window.onload = async function () {
    let sessionValid = await checkSessionValidity()
    if (!sessionValid) {
        let link = location.protocol + location.host + "/api/authentication/user/login"
        let url = new URL(link);
        url.searchParams.append('session_invalid' , 'true')
        location.href = url.href
    }
    connectToSocket()
    connectAdmin()
    let link = window.location.href
    let url = new URL(link);
    let sec = url.searchParams.get("sec");
    if (sec === 'payment') {
        $("#articles-menu").addClass('hide')
        $("#settings-menu").removeClass('hide')
    }
    if (sec === 'promo') {
        $("#articles-menu").addClass('hide')
        $("#promo-menu").removeClass('hide')
    }
    if ($(window).width() < 1200) {
        $("#dropdown").removeClass('d-none')
        $("#home").addClass('d-none')
        $("#article-management").addClass('d-none')
        $("#user-management").addClass('d-none')
        $("#promo-management").addClass('d-none')
        $("#profile").addClass('d-none')
        $("#settings-management").addClass('d-none')
        $("#logout").addClass('d-none')
        $("#menu").children('table').removeClass('h-100')
        $("#menu").removeClass('min-vh-100')
    }
    else {
        $("#dropdown").addClass('d-none')
    }

    if ($(window).width() < 768) {
        $("#add-payment").text('Add $')
        $("#payment-mod").text('List $')
        /**
         * Users management
         */
        let rows = $("#user-data").children('tr').addClass('row mx-0');
        $("#username-col").text('Users')
        $("#mail-col").addClass('d-none')
        $("#role-col").addClass('d-none')
        $("#status-col").addClass('d-none')
        $("#online-col").addClass('d-none')
        colspanOn()
        $("#user-head").addClass('row mx-0')
    }
    else {
        $("#add-payment").text('Add payment')
        $("#payment-mod").text('List payment')
        /**
         * Users management
         */
        let rows = $("#user-data").children('tr').removeClass('row mx-0');
        $("#username-col").text('Username')
        $("#mail-col").removeClass('d-none')
        $("#role-col").removeClass('d-none')
        $("#status-col").removeClass('d-none')
        $("#online-col").removeClass('d-none')
        colspanOff()
        $("#user-head").removeClass('row mx-0')
    }
}
function toggler() {
    $("#menu").toggleClass('min-vh-100')
    $("#menu").children('table').toggleClass('h-auto')
    $("#home").toggleClass('d-none')
    $("#article-management").toggleClass('d-none')
    $("#user-management").toggleClass('d-none')
    $("#promo-management").toggleClass('d-none')
    $("#profile").toggleClass('d-none')
    $("#settings-management").toggleClass('d-none')
    $("#logout").toggleClass('d-none')
}
$("#dropdown").click(function () {
    toggler()
})