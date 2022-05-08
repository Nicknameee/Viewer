$("#filterArticle").on('keyup paste' , function () {
    $("#filterArticle").parent().parent().children('.ART_PLACE').children('.ART_BOX').children('.ART_ITEM').children('.ARTICLE_NAME_BOX').children('.ARTICLE_NAME').each(
        function () {
            if ($("#filterArticle").val().length > 0) {
                if (!$(this).text().toLowerCase().includes($("#filterArticle").val())) {
                    $(this).parent().parent().parent().addClass('d-none')
                }
                else {
                    $(this).parent().parent().parent().removeClass('d-none')
                }
            }
            else {
                $(this).parent().parent().parent().removeClass('d-none')
            }
        }
    )
})