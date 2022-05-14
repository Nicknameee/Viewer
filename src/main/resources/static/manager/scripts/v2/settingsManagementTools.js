function editPayment(element) {
    $(element).parent().parent().next().toggleClass('d-none')
}
$("#payment-mod").click(function () {
    $("#adding-payment-body").addClass('d-none')
    $("#history-body").addClass('d-none')
    $("#payment-body").removeClass('d-none')
})
$("#add-payment").click(function () {
    $("#payment-body").addClass('d-none')
    $("#history-body").addClass('d-none')
    $("#adding-payment-body").removeClass('d-none')
})
$("#home-mod").click(function () {
    $("#payment-body").addClass('d-none')
    $("#adding-payment-body").addClass('d-none')
    $("#history-body").removeClass('d-none')
})
let canConfirmEdit = true
let canConfirmAdd = true
let canConfirmDescription = true
function processBank(element , type) {
    if ($(element).find(":selected").text() === 'Choose bank') {
        $(element).parent().addClass('valid-dec')
        $(element).parent().attr('about' , 'You have to choose the bank')
        if (type === 'add') {
            canConfirmAdd = false
        }
        if (type === 'edit') {
            canConfirmEdit = false
        }
    }
    else {
        $(element).parent().attr('about' , '')
        $(element).parent().removeClass('valid-dec')
    }
}
function processCard(element , type , action) {
    let res = validate_card(element)
    if (!res.valid) {
        $(element).parent().addClass('valid-dec')
        $(element).parent().attr('about' , res.error)
        if (res.error.includes('empty') && action === 'change') {
            $(element).parent().attr('about' , '')
            $(element).parent().removeClass('valid-dec')
        }
        if (type === 'add') {
            canConfirmAdd = false
        }
        if (type === 'edit') {
            canConfirmEdit = false
        }
    }
    else {
        $(element).parent().attr('about' , '')
        $(element).parent().removeClass('valid-dec')
    }
    return res
}
function processIBAN(element , type , action) {
    let res = validate_IBAN(element)
    if (!res.valid) {
        $(element).parent().addClass('valid-dec')
        $(element).parent().attr('about' , res.error)
        if (res.error === null && action === 'change') {
            $(element).parent().attr('about' , '')
            $(element).parent().removeClass('valid-dec')
        }
        if (type === 'add') {
            canConfirmAdd = false
        }
        if (type === 'edit') {
            canConfirmEdit = false
        }
    }
    else {
        $(element).parent().attr('about' , '')
        $(element).parent().removeClass('valid-dec')
    }
    return res
}
function processReceiver(element , type , action) {
    let res = validate_receiver(element)
    if (!res.valid) {
        $(element).parent().addClass('valid-dec')
        $(element).parent().attr('about' , res.error)
        if (res.error === null && action === 'change') {
            $(element).parent().attr('about' , '')
            $(element).parent().removeClass('valid-dec')
        }
        if (type === 'add') {
            canConfirmAdd = false
        }
        if (type === 'edit') {
            canConfirmEdit = false
        }
    }
    else {
        $(element).parent().attr('about' , '')
        $(element).parent().removeClass('valid-dec')
    }
    return res
}
function processDescription(element , type , action) {
    let res = validate_description(element)
    if (!res.valid) {
        $(element).parent().addClass('valid-dec')
        $(element).parent().attr('about' , res.error)
        if (action === 'change') {
            $(element).parent().attr('about' , '')
            $(element).parent().removeClass('valid-dec')
        }
        canConfirmDescription = false
    }
    else {
        $(element).parent().attr('about' , '')
        $(element).parent().removeClass('valid-dec')
    }
    return res
}
async function confirmAdd(element) {
    let form = $(element).parent().parent()[0]
    let formData = new FormData(form)
    let bank= $(element).parent().parent().children('.BANK_BOX').children().find(":selected")
    let count = 0
    $(form).children().each(function () {
        if (count === 0) {
            processBank($(element).parent().parent().children('.BANK_BOX').children() , 'add')
        }
        if (count === 1) {
            processCard($(this).children().eq(0) , 'add' , 'submit')
        }
        if (count === 2) {
            processIBAN($(this).children().eq(0) , 'add' , 'submit')
        }
        if (count === 3) {
            processReceiver($(this).children().eq(0) , 'add' , 'submit')
        }
        count++;
    })
    if (bank !== 'Choose bank') {
        formData.append('bank' , $(bank).val())
    }
    if (formData.get('iban') === '') {
        formData.delete('iban')
    }
    if (canConfirmAdd) {
        let sessionValid = await checkSessionValidity()
        if (sessionValid) {
            $.ajax(
                {
                    url: "/api/manager/payment/create",
                    type: "POST",
                    data: formData,
                    cache: false,
                    processData: false,
                    contentType: false,
                    success:
                        function(response) {
                            if (response.success) {
                                sendAlertRequestAdminPage("Payment adding for bank '" + formData.get('bank') + "' with card number '" + formData.get('card') + "'")
                                sendAlertRequestHomePage()
                                $("#add-payment-sec-title").attr('about' , 'Payment data were registered successfully')
                                setTimeout(function () {
                                    $("#add-payment-sec-title").attr('about' , '');
                                    let link = window.location.href
                                    let url = new URL(link);
                                    url.searchParams.append('sec' , 'payment')
                                    location.href = url.href
                                } , 2000)
                            }
                            else {
                                $("#add-payment-sec-title").attr('about' , 'Error occurs while adding payment model , see logs')
                                setTimeout(function () {$("#add-payment-sec-title").attr('about' , '')} , 3000)
                                console.log(response.error)
                            }
                        },
                    error:
                        function(response) {
                            $("#add-payment-sec-title").attr('about' , 'Error occurs while adding payment model , see logs')
                            setTimeout(function () {$("#add-payment-sec-title").attr('about' , '')} , 3000)
                            console.log(response.error)
                        }
                }
            )
        }
        else {
            let link = location.protocol + location.host + "/api/authentication/user/login"
            let url = new URL(link);
            url.searchParams.append('session_invalid' , 'true')
            location.href = url.href
        }
    }
    canConfirmAdd = true
}
async function confirmEdit(element) {
    let form = $(element).parent().parent()[0]
    let formData = new FormData(form)
    let count = 0
    $(form).children().each(function () {
        if (count === 0) {
            processBank($(this).children().eq(0) , 'edit' , 'submit')
        }
        if (count === 1) {
            processCard($(this).children().eq(0) , 'edit' , 'submit')
        }
        if (count === 2) {
            processIBAN($(this).children().eq(0) , 'edit' , 'submit')
        }
        if (count === 3) {
            processReceiver($(this).children().eq(0) , 'add' , 'submit')
        }
        count++;
    })
    if (formData.get('iban') === '') {
        formData.delete('iban')
    }
    if (canConfirmEdit) {
        let sessionValid = await checkSessionValidity()
        if (sessionValid) {
            $.ajax(
                {
                    url: "/api/manager/payment/update",
                    type: "PUT",
                    data: formData,
                    cache: false,
                    processData: false,
                    contentType: false,
                    success:
                        function(response) {
                            if (response.success) {
                                sendAlertRequestAdminPage("Payment editing for bank '" + formData.get('bank') + "'")
                                sendAlertRequestHomePage()
                                $(element).parent().parent().parent().children().eq(0).attr('about' , 'Payment data were updated successfully')
                                setTimeout(function () {
                                    $("#add-payment-sec-title").attr('about' , '');
                                    let link = window.location.href
                                    let url = new URL(link);
                                    url.searchParams.append('sec' , 'payment')
                                    location.href = url.href
                                } , 2000)
                            }
                            else {
                                $("#add-payment-sec-title").attr('about' , 'Error occurs while updating payment model , see logs')
                                setTimeout(function () {$(element).parent().parent().parent().children().eq(0).attr('about' , '')} , 3000)
                                console.log(response.error)
                            }
                        },
                    error:
                        function(response) {
                            $("#add-payment-sec-title").attr('about' , 'Error occurs while updating payment model , see logs')
                            setTimeout(function () {$(element).parent().parent().parent().children().eq(0).attr('about' , '')} , 3000)
                            console.log(response.error)
                        }
                }
            )
        }
        else {
            let link = location.protocol + location.host + "/api/authentication/user/login"
            let url = new URL(link);
            url.searchParams.append('session_invalid' , 'true')
            location.href = url.href
        }
    }
    canConfirmEdit = true
}
async function deletePayment(element) {
    let conf = confirm("Are you sure you want to delete this payment model?")
    if (conf) {
        let sessionValid = await checkSessionValidity()
        if (sessionValid) {
            $.ajax(
                {
                    url: "/api/manager/payment/delete",
                    type: "DELETE",
                    data: {
                        id: $(element).val()
                    },
                    success:
                        function(response) {
                            if (response.success) {
                                sendAlertRequestAdminPage("Payment deleting with ID '" + $(element).val() + "'")
                                sendAlertRequestHomePage()
                                setTimeout(function () {
                                    $("#add-payment-sec-title").attr('about' , '');
                                    let link = window.location.href
                                    let url = new URL(link);
                                    url.searchParams.append('sec' , 'payment')
                                    location.href = url.href
                                } , 2000)
                            }
                            else {
                                console.log(response.error)
                            }
                        },
                    error:
                        function(response) {
                            console.log(response.error)
                        }
                }
            )
        }
        else {
            let link = location.protocol + location.host + "/api/authentication/user/login"
            let url = new URL(link);
            url.searchParams.append('session_invalid' , 'true')
            location.href = url.href
        }

    }
}