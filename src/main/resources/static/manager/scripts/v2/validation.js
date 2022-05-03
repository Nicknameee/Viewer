/**
 * Article form part validation
 * @param input - validated input
 */
function validate_title(input) {
    let result = {
        valid: true ,
        error: null
    }
    if ($(input).val().length > 64)
    {
        result.valid = false
        result.error = 'Title length is invalid , max length 64'
    }
    if ($(input).val().length === 0) {
        result.valid = false
        result.error = 'Title can not be empty'
    }
    return result;
}
function validate_content(input) {
    let result = {
        valid: true ,
        error: null
    }
    if ($(input).val().length > 5000)
    {
        result.valid = false
        result.error = 'Content length is invalid , max length 5000'
    }
    if ($(input).val().length === 0) {
        result.valid = false
        result.error = 'Content can not be empty'
    }
    return result;
}
function validate_files(input) {
    if (input === undefined || input.files === undefined) return true
    const maxSize = 10 * 1024 * 1024;
    let files = input.files
    for (let i = 0; i < files.length; i++) {
        if (files.item(i).size > maxSize) {
            return false
        }
    }
    return true
}
function validate_card(input) {
    let result = {
        valid: true ,
        error: null
    }
    if ($(input).val().length === 0) {
        result.valid = false
        result.error = 'Card number can not be empty'
        return result
    }
    if (!$(input).val().trim().match("^[0-9]{16}$"))
    {
        result.valid = false
        result.error = 'Card number is invalid - you must input 16 numbers'
    }
    return result;
}
function validate_IBAN(input) {
    let result = {
        valid: true ,
        error: null
    }
    if ($(input).val().length > 0 && !$(input).val().trim().match("^[a-zA-Z]{2}[0-9]{8}[0]{5}[0-9]{14}$"))
    {
        result.valid = false
        result.error = 'IBAN is invalid - checkout https://privatbank.ua/iban'
    }
    return result;
}
function validate_description(input) {
    let result = {
        valid: true ,
        error: null
    }
    if ($(input).val().length === 0)
    {
        result.valid = false
        result.error = 'Description can not be empty'
    }
    return result;
}