function lightshot(obj) {
    $("#mainNav").animate(
        {
            opacity: 0
        },
        300,
        function () {
            let div = $(obj).parent();
            let img  = $(div).children('img').clone();
            $(img).css('border' , '2px solid white')
            $(img).addClass('pretty-img')
            $(img).attr('id' , 'opened-img')
            let bg = document.createElement('div')
            $(bg).css('opacity' , '0')
            $(bg).toggleClass('light-bg')
            $(bg).append(img)
            $(bg).attr('id' , 'overlap-entity')
            $(bg).attr('onclick' , 'overlapFunction(this)')
            if (document.getElementById("overlap") === null) {
                $('body').append(bg)
                $(bg).animate(
                    {
                        opacity: 1
                    }
                    , 200 , function () {})
            }
        }
    )
}
function overlapFunction() {
    $('#overlap-entity').animate(
        {
            opacity: 0
        }
        , 200 , function () {
            $("#mainNav").animate(
                {
                    opacity: 1
                }
                , 200 , function () {}
            )
        }
    )
    $('#overlap-entity').remove()
}