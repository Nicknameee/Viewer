function editPayment(element) {
    $(element).parent().parent().next().toggleClass('d-none')
}

function deletePayment(element) {
    /**
     * @AJAX - request for deleting chosen article
     * @param id - article identifier
     */
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
function processBank(element , type , action) {
    if ($(element).val().length < 1) {
        $(element).parent().addClass('valid-dec')
        $(element).parent().attr('about' , 'Bank name can not be empty')
        if (action === 'change') {
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
function confirmAdd(element) {
    let form = $(element).parent().parent()[0]
    let formData = new FormData(form)
    let count = 0
    $(form).children().each(function () {
        if (count === 0) {
            processBank($(this).children().eq(0) , 'add' , 'submit')
        }
        if (count === 1) {
            processCard($(this).children().eq(0) , 'add' , 'submit')
        }
        if (count === 2) {
            processIBAN($(this).children().eq(0) , 'add' , 'submit')
        }
        count++;
    })
    if (canConfirmAdd) {
        /**
         * @AJAX - request on editing
         */
        $("#add-art-sec-title").attr('about' , 'RESPONSE_STATUS')
        setTimeout(function () {$("#add-art-sec-title").attr('about' , '')} , 3000)
    }
    canConfirmAdd = true
}
function confirmEdit(element) {
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
        count++;
    })
    if (canConfirmEdit) {
        /**
         * @AJAX - request on editing
         */
        $(element).parent().parent().parent().children().eq(0).attr('about' , 'RESPONSE_STATUS')
        setTimeout(function () {$(element).parent().parent().parent().children().eq(0).attr('about' , '')} , 3000)
    }
    canConfirmEdit = true
}
function submitHomePageText(element) {
    let form = $(element).parent().parent()[0]
    let formData = new FormData(form)
    let count = 0
    $(form).children().each(function () {
        if (count === 0) {
            processDescription($(this).children().eq(0) , 'add' , 'submit')
        }
        count++
    })
    if (canConfirmDescription) {
        $(element).parent().parent().parent().children().eq(0).attr('about' , 'RESPONSE_STATUS')
        setTimeout(function () {$(element).parent().parent().parent().children().eq(0).attr('about' , '')} , 3000)
    }
    canConfirmDescription = true
}