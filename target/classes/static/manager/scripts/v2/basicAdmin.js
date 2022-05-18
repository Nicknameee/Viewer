function showChangesAlert() {
    $("#changes-alert").removeClass('d-none')
}
function changeLanguage(element) {
    $.ajax({
        url: '/api/user/update/language',
        type: 'PUT',
        data: {
            mail: $("#email").text(),
            language: $(element).text()
        },
        success:
            function (response) {
                if (response.success) {
                    location.pathname = '/api/user/personal'
                }
                else {
                    alert("Error occurs while language changing")
                    console.log(response.error)
                }
            },
        error:
            function (response) {
                alert("Error occurs while language changing")
                console.log(response.error)
            }
    })

}