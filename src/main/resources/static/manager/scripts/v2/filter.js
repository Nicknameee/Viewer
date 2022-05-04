function filterSearch(element , type) {
    if (type === 'articles') {
        $("#article-data").children().each(
            function () {
                let title = $.parseHTML($(this).children().eq(0).html())
                if ($(title).attr('about') === 'data-row') {
                    let row = $(this).children().eq(0).parent()
                    if ($(element).val().length > 0) {
                        if (!$(title).text().toLowerCase().includes($(element).val())) {
                            $(row).addClass('d-none')
                            $(row).next().addClass('d-none')
                        }
                        else {
                            $(row).removeClass('d-none')
                        }
                    }
                    else {
                        $(row).removeClass('d-none')
                    }
                }
            }
        )
    }
    if (type === 'users') {
        $("#user-data").children().each(
            function () {
                let username = $(this).children('.USER_TD').children('.USERNAME')
                if ($(element).val().length > 0) {
                    if (!$(username).text().toLowerCase().includes($(element).val())) {
                        $(username).parent().parent().addClass('d-none')
                    }
                    else {
                        $(username).parent().parent().removeClass('d-none')
                    }
                }
                else {
                    $(username).parent().parent().removeClass('d-none')
                }
            }
        )
    }
}
