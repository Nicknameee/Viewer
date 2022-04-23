/**
 *
 * @param status - performed response received from server
 * @param type - article action type
 */
function performReload(status , type) {
    drop()
    if (type === "find") {
        $("#find-title").val('')
        $("#find-content").val('')
        $("#find-media").val('')
        $("#find-response").html(status)
        $("#find-response-box").fadeIn(600)
        setTimeout(function () {
            $("#find-response-box").fadeOut(600)
        } , 3333)
    }
    if (type === "add") {
        $("#add-title").val('')
        $("#add-content").val('')
        $("#add-media").val('')
        $("#add-response").text(status)
        $("#add-response-box").fadeIn(600)
        setTimeout(function () {
            $("#add-response-box").fadeOut(600)
        } , 3333)
    }
    if (type === "edit") {
        $("#edit-title").val('')
        $("#edit-content").val('')
        $("#edit-media").val('')
        $("#edit-response").text(status)
        $("#edit-response-box").fadeIn(600)
        setTimeout(function () {
            $("#edit-response-box").fadeOut(600)
        } , 3333)
    }
    if (type === "delete") {
        $("#delete-title").val('')
        $("#delete-content").val('')
        $("#delete-media").val('')
        $("#delete-response").text(status)
        $("#delete-response-box").fadeIn(600)
        setTimeout(function () {
            $("#delete-response-box").fadeOut(600)
        } , 3333)
    }
}