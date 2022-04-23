let result = {
    success: null,
    error: null
}
function drop() {
    result.success = true
    result.error = null
}
/**
 * ONCHANGE listeners for FIND - function
 */
document.getElementById("find-title").addEventListener("change" , function () {
    let length = $("#find-title").val().length;
    if (length > 32) {
        $("#find-title").addClass('signal')
        $("#find-title").parent().addClass('alert-validate')
        showValidate($("#find-title") , "Title is too long , max symbols count is 32")
    }
    else {
        $("#find-title").removeClass('signal')
        $("#find-title").parent().removeClass('alert-validate')
        hideValidate($("#find-title"))
    }
});
/**
 * ONCHANGE listeners for ADD - function
 */
document.getElementById("add-title").addEventListener("change" , function () {
    let length = $("#add-title").val().length;
    if (length > 32) {
        $("#add-title").addClass('signal')
        $("#add-title").parent().addClass('alert-validate')
        showValidate($("#add-title") , "Title is too long , max symbols count is 32")
    }
    else {
        $("#add-title").removeClass('signal')
        $("#add-title").parent().removeClass('alert-validate')
        hideValidate($("#add-title"))
    }
});
document.getElementById("add-content").addEventListener("change" , function () {
    let length = $("#add-content").val().length;
    if (length > 5000) {
        $("#add-content").addClass('signal')
        $("#add-content").parent().addClass('alert-validate')
        showValidate($("#add-content") , "Text is too long , max symbols count is 5000")
    }
    else {
        $("#add-content").removeClass('signal')
        $("#add-content").parent().removeClass('alert-validate')
        hideValidate($("#add-content"))
    }
});
document.getElementById("add-media").addEventListener("change" , function () {
    const maxSize = 10 * 1024 * 1024;
    let files = document.getElementById("add-media").files
    alert(files.length)
    alert(files.item(0).size)
    for (let i = 0; i < files.length; i++) {
        if (files.item(i).size > maxSize) {
            $("#add-media").addClass('signal')
            $("#add-media").parent().addClass('alert-validate')
            showValidate($("#add-media") , "Too large file , max allowed size 10MB")
        }
        else {
            $("#add-media").removeClass('signal')
            $("#add-media").parent().removeClass('alert-validate')
            hideValidate($("#add-media"))
        }
    }
});
/**
 * ONCHANGE listeners for EDIT - function
 */
document.getElementById("edit-title").addEventListener("change" , function () {
    let length = $("#edit-title").val().length;
    if (length > 32) {
        $("#edit-title").addClass('signal')
        $("#edit-title").parent().addClass('alert-validate')
        showValidate($("#edit-title") , "Title is too long , max symbols count is 32")
    }
    else {
        $("#edit-title").removeClass('signal')
        $("#edit-title").parent().removeClass('alert-validate')
        hideValidate($("#edit-title"))
    }
});
document.getElementById("edit-content").addEventListener("change" , function () {
    let length = $("#edit-content").val().length;
    if (length > 5000) {
        $("#edit-content").addClass('signal')
        $("#edit-content").parent().addClass('alert-validate')
        showValidate($("#edit-content") , "Text is too long , max symbols count is 5000")
    }
    else {
        $("#edit-content").removeClass('signal')
        $("#edit-content").parent().removeClass('alert-validate')
        hideValidate($("#edit-content"))
    }
});
document.getElementById("edit-media").addEventListener("change" , function () {
    const maxSize = 10 * 1024 * 1024;
    let files = document.getElementById("edit-media").files
    alert(files.length)
    alert(files.item(0).size)
    for (let i = 0; i < files.length; i++) {
        if (files.item(i).size > maxSize) {
            $("#edit-media").addClass('signal')
            $("#edit-media").parent().addClass('alert-validate')
            showValidate($("#edit-media") , "Too large file , max allowed size 10MB")
        }
        else {
            $("#edit-media").removeClass('signal')
            $("#edit-media").parent().removeClass('alert-validate')
            hideValidate($("#edit-media"))
        }
    }
});
/**
 * ONCHANGE listeners for DELETE - function
 */
document.getElementById("delete-title").addEventListener("change" , function () {
    let length = $("#delete-title").val().length;
    if (length > 32) {
        $("#delete-title").addClass('signal')
        $("#delete-title").parent().addClass('alert-validate')
        showValidate($("#delete-title") , "Title is too long , max symbols count is 32")
    }
    else {
        $("#delete-title").removeClass('signal')
        $("#delete-title").parent().removeClass('alert-validate')
        hideValidate($("#delete-title"))
    }
});
/**
 *
 * @param type - article action type
 * @returns {{success: null, error: null}} - results of validation
 */
function validate(type) {
    drop()
    if (type === "find") {
        validate_title($("#find-title"))
    }
    if (type === "add") {
        validate_title($("#add-title"))
        validate_content($("#add-content"))
        validate_files($("#add-media"))
    }
    if (type === "edit") {
        validate_title($("#edit-title"))
        validate_content($("#edit-content"))
        validate_files($("#edit-media"))
    }
    if (type === "delete") {
        validate_title($("#delete-title"))
    }
    return result
}
/**
 * Article form part validation
 * @param input - validated input
 */
function validate_title(input) {
    let length = input.val().length;
    if (length > 32) {
        result.success = false
        result.error = "Title is too long , max symbols count is 32"
    }
}
function validate_content(input) {
    let length = input.val().length
    if (length > 5000) {
        result.success = false
        result.error = "Text is too long , max symbols count is 5000"
    }
}
function validate_files(input) {
    const maxSize = 10 * 1024 * 1024;
    let files = input.get(0)
    for (let i = 0; i < files.length; i++) {
        if (files.item(i).size > maxSize) {
            result.success = false
            result.error = "Too large file , max allowed size 10MB"
        }
    }
}
/**
 *
 * @param input - validated input
 * @param error - figured error while validating
 */
function showValidate(input , error) {
    let thisAlert = $(input).parent();
    thisAlert.attr('about' , error);
    $(thisAlert).addClass('alert-validate');
}
/**
 *
 * @param input - validated input
 */
function hideValidate(input) {
    let thisAlert = $(input).parent();
    $(thisAlert).removeClass('alert-validate');
}