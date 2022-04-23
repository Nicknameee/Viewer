let result = {
    success: null,
    error: null
}
function drop() {
    result.bool = true
    result.error = null
}
document.getElementById("title").addEventListener("change" , function () {
    let length = $("#title").val().length;
    if (length > 32) {
        $("#title").addClass('signal')
        $("#title").parent().addClass('alert-validate')
        showValidate($("#title") , "Title is too long , max symbols count is 32")
    }
    else {
        $("#title").removeClass('signal')
        $("#title").parent().removeClass('alert-validate')
        hideValidate($("#title"))
    }
});
document.getElementById("content").addEventListener("change" , function () {
    let length = $("#content").val().length;
    if (length > 5000) {
        $("#content").addClass('signal')
        $("#content").parent().addClass('alert-validate')
        showValidate($("#content") , "Text is too long , max symbols count is 5000")
    }
    else {
        $("#content").removeClass('signal')
        $("#content").parent().removeClass('alert-validate')
        hideValidate($("#content"))
    }
});
document.getElementById("media").addEventListener("change" , function () {
    const maxSize = 10 * 1024 * 1024;
    let files = document.getElementById("media").files
    for (let i = 0; i < files.length; i++) {
        if (files.item(i).size > maxSize) {
            $("#media").addClass('signal')
            $("#media").parent().addClass('alert-validate')
            showValidate($("#media") , "Too large file , max allowed size 10MB")
        }
        else {
            $("#media").removeClass('signal')
            $("#media").parent().removeClass('alert-validate')
            hideValidate($("#media"))
        }
    }
});
function validate() {
    drop()
    validate_title()
    validate_content()
    validate_files()
    return result
}
function validate_title() {
    let length = $("#title").val().length;
    if (length > 32) {
        result.bool = false
        result.error = "Title is too long , max symbols count is 32"
        showValidate($("#title") , result.error)
    }
}
function validate_content() {
    let length = $("#content").val().length
    if (length > 5000) {
        result.bool = false
        result.error = "Text is too long , max symbols count is 5000"
        showValidate($("#title") , result.error)
    }
}
function validate_files() {
    const maxSize = 10 * 1024 * 1024;
    let files = $("#media").get(0)
    let enough = false
    for (let i = 0; i < files.length && enough; i++) {
        if (files.item(i).size > maxSize) {
            result.bool = false
            result.error = "Too large file detected, max allowed size 10MB"
            showValidate($("#title") , result.error)
        }
    }
}
function showValidate(input , error) {
    let thisAlert = $(input).parent();
    thisAlert.attr('about' , error);
    $(thisAlert).addClass('alert-validate');
}
function hideValidate(input) {
    let thisAlert = $(input).parent();
    $(thisAlert).removeClass('alert-validate');
}