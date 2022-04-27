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