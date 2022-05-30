function validateTag(element) {
    let val = $(element).val()
    if (val == null || val.length === 0) {
        return {
            valid: false,
            error: 'Tag value can not be empty'
        }
    }
    return {
        valid: true,
        error: null
    }
}
function processTag(element , action) {
    let res = validateTag(element)
    if (!res.valid) {
        if (action !== 'change') {
            $(element).parent().addClass('valid-dec')
            $(element).parent().attr('about' , res.error)
        }
    }
    else {
        $(element).parent().attr('about' , '')
        $(element).parent().removeClass('valid-dec')
    }
    return res.valid
}
function addTag(element) {
    let tagStructure =
        "<div class=\"input-group w-auto mx-1 my-1 TAG_WRAP\">" +
        "<input name=\"tags\" type=\"text\" class=\"form-control text-dark TAG_VALUE\" value=\"{{VALUE}}\" aria-describedby=\"basic-addon2\" readonly>" +
        "<span class=\"input-group-text\" onclick=\"dropTag(this)\">✖</span></div>"
    if (processTag($(element).prev() , 'add')) {
        let tagValue = $(element).prev().val()
        let tagList = $(element).parent().next()
        let tagElement = $.parseHTML(
            tagStructure.replaceAll('{{VALUE}}' , tagValue)
        )
        $(tagList).append(tagElement)
        $(element).prev().val('')
    }
}
function addTagFromExisting(element) {
    let tagStructure =
        "<div class=\"input-group w-auto mx-1 my-1 TAG_WRAP\">" +
        "<input name=\"tag\" type=\"text\" class=\"form-control text-dark TAG_VALUE\" value=\"{{VALUE}}\" aria-describedby=\"basic-addon2\" readonly>" +
        "<span class=\"input-group-text\" onclick=\"dropTag(this)\">✖</span></div>"
    if (processTag($(element).prev() , 'add')) {
        let tagValue = $(element).prev().val()
        let tagList = $(element).parent().parent().parent().next().next()
        let tagElement = $.parseHTML(
            tagStructure.replaceAll('{{VALUE}}' , tagValue)
        )
        $(tagList).append(tagElement)
    }
}
function dropTag(element) {
    $(element).parent().remove()
}
function deleteTag(element) {
    let stub = "<div class=\"input-group w-100 valid\">\n" +
        "            <input type=\"text\" class=\"form-control text-dark\" value=\"Немає тегів\" readonly>\n" +
        "       </div>"
    let stubElement = $.parseHTML(stub)
    let tagList = $(element).parent().parent()
    let tag = $(element).prev().prev().val()
    $.ajax({
        url: '/api/manager/tag/delete',
        type: 'DELETE',
        data: {
            content: tag
        },
        success:
            function (response) {
                if (response.success) {
                    $(element).parent().remove()
                    if ($(tagList).children().length === 0) {
                        $(tagList).append(stubElement)
                    }
                    $(document.getElementsByClassName('TAG_WRAP')).each(
                        function () {
                            if ($(this).children('.TAG_VALUE').val() === tag) {
                                $(this).remove()
                            }
                        }
                    )
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