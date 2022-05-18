function change(element) {
    /**
     * row - <tr>
     */
    let row = $(element).parent().parent().parent().parent();
    /**
     * btnCode - html code of confirming button
     */
    let btnCode = $($(row).children().last()[0]).html();
    /**
     * parsing html code of confirming button to an object
     */
    let updatedBtn = $.parseHTML(btnCode)
    /**
     * off disabling class on button
     */
    $(updatedBtn).removeClass('disabled')
    /**
     * injecting changes
     */
    $($(row).children().last()[0]).html($(updatedBtn))
    /**
     * getting an field to select value into
     */
    let value = $(element).parent().parent().children('button')[0];
    /**
     * setting a field with a chosen value
     */
    $(value).text($(element).text())
}
function showData(element) {
    for (let i = 1; i < $(element).parent().children().length; i++) {
        $(element).parent().children().eq(i).toggleClass('hide-768')
    }
}
async function userEditConfirm(element) {
    let row = $(element).parent().parent()
    let mail = $(row).children('.MAIL')
    let role = $(row).children('.ROLE_TD').children('.ROLE_BOX').children('.ROLE')
    let status = $(row).children('.STATUS_TD').children('.STATUS_BOX').children('.STATUS')
    let conf = confirm("Підтвердити внесені зміни?")
    if (conf) {
        let sessionValid = await checkSessionValidity()
        if (sessionValid) {
            $.ajax(
                {
                    url: "/api/manager/user/update",
                    type: "PUT",
                    data: {
                        mail: $(mail).text(),
                        role: $(role).text(),
                        status: $(status).text()
                    },
                    success:
                        function(response) {
                            if (response.success) {
                                sendAlertRequestAdminPage("User '" + $(mail).text()
                                    + "' was changed , ROLE: '" + $(role).text() + "' and STATUS '"
                                    + $(status).text() + "'")
                                $(element).addClass('disabled')
                            }
                        },
                    error:
                        function(response) {
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