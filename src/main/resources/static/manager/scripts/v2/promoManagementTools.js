$("#promo-mod").click(function () {
    $("#adding-promo-body").addClass('d-none')
    $("#promo-body").removeClass('d-none')
})
$("#add-promo").click(function () {
    $("#promo-body").addClass('d-none')
    $("#adding-promo-body").removeClass('d-none')
})
let canConfirmAddPromo = true
function processType(element) {
    if ($(element).find(":selected").text() === 'Choose type') {
        $(element).parent().addClass('valid-dec')
        $(element).parent().attr('about' , 'You have to choose the type')
        canConfirmAddPromo = false
    }
    else {
        $(element).parent().attr('about' , '')
        $(element).parent().removeClass('valid-dec')
    }
}
async function confirmAddPromo(element) {
    let form = $(element).parent().parent()[0]
    let formData = new FormData(form)
    let type = $(element).parent().parent().children('.PROMO_BOX').children().find(":selected")
    let count = 0
    $(form).children().each(function () {
        if (count === 0) {
            processType($(element).parent().parent().children('.PROMO_BOX').children())
        }
        count++;
    })
    if ($(type).text() !== 'Choose type') {
        formData.append('type' , $(type).val())
    }
    if (canConfirmAddPromo) {
        let sessionValid = await checkSessionValidity()
        if (sessionValid) {
            $.ajax({
                url: "/api/manager/promo/create",
                type: "POST",
                data: formData,
                cache: false,
                processData: false,
                contentType: false,
                success:
                    function (response) {
                        if (response.success) {
                            sendAlertRequestAdminPage("Adding a promo with type '" + formData.get('type') + "'")
                            $("#add-promo-sec-title").attr('about' , 'Promo has been added successfully')
                            setTimeout(function () {
                                $("#add-promo-sec-title").attr('about' , '')
                                let link = window.location.protocol + window.location.host + window.location.pathname;
                                let url = new URL(link);
                                url.searchParams.append('sec' , 'promo');
                                location.href = url.href;
                            } , 1250)
                        }
                        else {
                            console.log(response.error)
                            $("#add-promo-sec-title").attr('about' , response.error)
                            setTimeout(function () {$("#add-promo-sec-title").attr('about' , '')} , 1250)
                        }
                    },
                error:
                    function (response) {
                        console.log(response.error)
                        $("#add-promo-sec-title").attr('about' , response.error)
                        setTimeout(function () {$("#add-promo-sec-title").attr('about' , '')} , 1250)
                    }
            })
        }
        else {
            let link = location.protocol + location.host + "/api/authentication/user/login"
            let url = new URL(link);
            url.searchParams.append('session_invalid' , 'true')
            location.href = url.href
        }
    }
    canConfirmAddPromo = true
}
async function deletePromo(element) {
    let confirmation = confirm("Are you sure you want to delete this promo?")
    if (confirmation) {
        let sessionValid = await checkSessionValidity()
        if (sessionValid) {
            $.ajax({
                url: "/api/manager/promo/delete",
                type: "DELETE",
                data: {
                    id: $(element).val()
                },
                success:
                    function (response) {
                        if (response.success) {
                            sendAlertRequestAdminPage("Deleting a promo with ID '" + $(element).val() + "'")
                            $(element).parent().parent().next().remove()
                            $(element).parent().parent().remove()
                            let link = window.location.protocol + window.location.host + window.location.pathname;
                            let url = new URL(link);
                            url.searchParams.append('sec' , 'promo');
                            location.href = url.href;
                        }
                        else {
                            console.log(response.error)
                        }
                    },
                error:
                    function (response) {
                        console.log(response.error)
                    }
            })
        }
        else {
            let link = location.protocol + location.host + "/api/authentication/user/login"
            let url = new URL(link);
            url.searchParams.append('session_invalid' , 'true')
            location.href = url.href
        }
    }
}