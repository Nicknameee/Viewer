function runTags() {
    let chosenTags = []
    const elements = document.querySelectorAll(`[id^="select2-filterTag-container-choice"]`);
    elements.forEach(element => {
        chosenTags.push(element.textContent)
    });
    filterByTags(chosenTags)
}
function setTags() {
    let content = []
    $('#tag-values').children('.value').each(
        function () {
            content.push($(this).html())
        }
    )
    $(".prompt").select2({
        data:content,
        width: '100%',
        multiple:true,
        placeholder:"Tags",
    });
}
function filterByTags(tags) {
    $("#filterArticle").parent().parent().children('.ART_PLACE').children('.ART_BOX').children('.ART_ITEM').children('.ARTICLE_NAME_BOX').each(
        function () {
            if (tags !== null && tags.length > 0) {
                let count = 0
                $(this).children('.ARTICLE_TAG').each(
                    function () {
                        if (tags.includes($(this).html())) {
                            count++
                        }
                    }
                )
                if (count === 0) {
                    $(this).parent().parent().addClass('d-none')
                }
                else {
                    $(this).parent().parent().removeClass('d-none')
                }
            }
            else {
                $(this).parent().parent().removeClass('d-none')
            }
        }
    )
}