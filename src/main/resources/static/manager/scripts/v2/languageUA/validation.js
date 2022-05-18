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
        result.error = 'Довжина заголовку не має перевищувати 64'
    }
    if ($(input).val().length === 0) {
        result.valid = false
        result.error = 'Заголовок не може бути пустим'
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
        result.error = 'Довжина тексту не має перевищувати 5000'
    }
    if ($(input).val().length === 0) {
        result.valid = false
        result.error = 'Текст не можу бути пустим'
    }
    return result;
}
function validate_files(input) {
    if (input === undefined || input.files === undefined) return true
    const maxSize = 250 * 1024 * 1024;
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
        result.error = 'Номер картки не може бути пустим'
        return result
    }
    if (!$(input).val().replace(/\s/g, "").match("^[0-9]{16}$"))
    {
        result.valid = false
        result.error = 'Номер картки некоректний - повинно бути 16 цифр'
    }
    return result;
}
function validate_IBAN(input) {
    let result = {
        valid: true ,
        error: null
    }
    if ($(input).val().length > 0 && !$(input).val().replace(/\s/g, "").match("^[a-zA-Z]{2}[0-9]{8}[0]{5}[0-9]{14}$"))
    {
        result.valid = false
        result.error = 'IBAN некоректний - відвідайте https://privatbank.ua/iban'
    }
    return result;
}
function validate_receiver(input) {
    let result = {
        valid: true ,
        error: null
    }
    if ($(input).val().length === 0) {
        result.valid = false
        result.error = 'Отримувач не може бути пустим'
        return result
    }
    if (!$(input).val().match('^[a-zA-Zа-яА-ЯіІїЇьЬєЄ\\s]{1,}$'))
    {
        result.valid = false
        result.error = 'Некоректне ім`я отримувача - мають бути лише символи'
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
        result.error = 'Опис не може бути пустим'
    }
    return result;
}