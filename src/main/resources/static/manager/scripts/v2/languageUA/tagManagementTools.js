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
function dropTag(element) {
    $(element).parent().remove()
}