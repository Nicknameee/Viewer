$("#add-article-btn").click(function () {
    $("#article-management-box").addClass('d-none')
    $("#adding-form-box").removeClass('d-none')
})
$("#turn-back").click(function () {
    $("#adding-form-box").addClass('d-none')
    $("#article-management-box").removeClass('d-none')
})

function editArticle(element) {
    $(element).parent().parent().next().toggleClass('d-none')
}

let canSubmitEdit = true
let canSubmitAdd = true
function processTitle(element , type , action) {
    let res = validate_title(element)
    if (!res.valid) {
        $(element).parent().addClass('valid-dec')
        $(element).parent().attr('about' , res.error)
        if (res.error.includes('empty') && action === 'change') {
            $(element).parent().attr('about' , '')
            $(element).parent().removeClass('valid-dec')
        }
        if (type === 'add') {
            canSubmitAdd = false
        }
        if (type === 'edit') {
            canSubmitEdit = false
        }
    }
    else {
        $(element).parent().attr('about' , '')
        $(element).parent().removeClass('valid-dec')
    }
    return res
}
function processContent(element , type , action) {
    let res = validate_content(element)
    if (!res.valid) {
        $(element).parent().addClass('valid-dec')
        $(element).parent().attr('about' , res.error)
        if (res.error.includes('empty') && action === 'change') {
            $(element).parent().attr('about' , '')
            $(element).parent().removeClass('valid-dec')
        }
        if (type === 'add') {
            canSubmitAdd = false
        }
        if (type === 'edit') {
            canSubmitEdit = false
        }
    }
    else {
        $(element).parent().removeClass('valid-dec')
        $(element).parent().attr('about' , '')
    }
    return res
}
function addFile(element , type , action) {
    if (!validate_files(element)) {
        $(element).parent().addClass('valid-dec')
        $(element).parent().attr('about' , 'Max size for each file is 10MB')
        if (type === 'add') {
            canSubmitAdd = false
        }
        if (type === 'edit') {
            canSubmitEdit = false
        }
    }
    else {
        $(element).parent().removeClass('valid-dec')
        $(element).parent().attr('about' , '')
    }
}
function dropFile(element) {
    let deletingFile = $(element).prev()
    let artId = $(element).parent().parent().children('.id-edit')
    $.ajax(
        {
            url: "/api/manager/article/resource",
            type: "DELETE",
            data: {
                filename: $(deletingFile).val(),
                id: $(artId).val()
            },
            success:
                function(response) {
                    if (response.success) {
                        $(element).parent().parent().prev().attr('about' , 'Resource has been removed successfully')
                        setTimeout(function () {$(element).parent().parent().prev().attr('about' , ''); $(element).parent().remove()} , 3000)
                    }
                    else {
                        $(element).parent().parent().prev().attr('about' , 'Error occurs while deleting , see logs')
                        setTimeout(function () {$(element).parent().parent().prev().attr('about' , '')} , 3000)
                        console.log(response.error)
                    }
                },
            error:
                function(response) {
                    $(element).parent().parent().prev().attr('about' , 'Error occurs while deleting , see logs')
                    setTimeout(function () {$(element).parent().parent().prev().attr('about' , '')} , 3000)
                    console.log(response.error)
                }
        }
    )
}
function previewFile(element) {
    let resourceType = $(element).attr('content')
    let overlap = document.createElement('div')
    $(overlap).attr('onclick' , 'closePreview(this)')
    $(overlap).attr('class' , 'light-bg')
    $(overlap).attr('id' , 'overlap')
    if (resourceType === 'IMAGE') {
        let image = document.createElement('img')
        $(image).attr('class' , 'pretty-img img-fluid')
        $(image).attr('src' , '/uploads/' + $(element).val())
        $(overlap).append(image)
    }
    else if (resourceType === 'VIDEO') {
        let frame = document.createElement('iframe')
        $(frame).attr('allowfullscreen' , 'true')
        $(frame).attr('frameborder' , '0')
        $(frame).attr('sandbox' , 'allow-scripts')
        $(frame).attr('autoplay' , false)
        $(frame).attr('class' , 'video')
        $(frame).attr('src' , '/uploads/' + $(element).val())
        $(overlap).append(frame)
    }
    if (document.getElementById("overlap") === null) {
        $('body').append(overlap)
        $(overlap).animate(
            {
                opacity: 1
            }
            , 200 , function () {})
    }
}
function closePreview(element) {
    $(element).remove()
}
function submitAdd(element) {
    let form = $(element).parent().parent()[0]
    let formData = new FormData(form)
    let count = 0
    $(form).children().each(function () {
        if (count === 0) {
            processTitle($(this).children().eq(0) , 'add' , 'submit')
        }
        if (count === 1) {
            processContent($(this).children().eq(0) , 'add' , 'submit')
        }
        if (count === 2) {
            addFile($(this).children().eq(0) , 'add' , 'submit')
        }
        count++;
    })
    if (formData.get('media').size === 0 && formData.get('media').name === '') {
        formData.set('media' , null)
    }
    if (canSubmitAdd) {
        $.ajax(
            {
                url: "/api/manager/article/create",
                type: "POST",
                data: formData,
                cache: false,
                processData: false,
                contentType: false,
                success:
                    function(response) {
                        if (response.success) {
                            $(element).parent().parent().parent().children().eq(0).attr('about' , 'Article created successfully')
                            setTimeout(function () {$(element).parent().parent().parent().children().eq(0).attr('about' , ''); location.reload()} , 2000)
                        }
                        else {
                            $(element).parent().parent().parent().children().eq(0).attr('about' , 'Error occurs while creating article , see logs')
                            setTimeout(function () {$(element).parent().parent().parent().children().eq(0).attr('about' , '')} , 3000)
                            console.log(response.error)
                        }
                    },
                error:
                    function(response) {
                        $(element).parent().parent().parent().children().eq(0).attr('about' , 'Error occurs while creating article , see logs')
                        setTimeout(function () {$(element).parent().parent().parent().children().eq(0).attr('about' , '')} , 3000)
                        console.log(response.error)
                    }
            }
        )
    }
    canSubmitAdd = true
}
function submitEdit(element) {
    let form = $(element).parent().parent()[0]
    let formData = new FormData(form)
    let count = 0
    $(form).children().each(function () {
        if (count === 0) {
            processTitle($(this).children().eq(0) , 'edit' , 'submit')
        }
        if (count === 1) {
            processContent($(this).children().eq(0) , 'edit' , 'submit')
        }
        if (count === 2) {
            addFile($(this).children().eq(0) , 'edit' , 'submit')
        }
        count++;
    })
    if (formData.get('media').size === 0 && formData.get('media').name === '') {
        formData.set('media' , null)
    }
    if (canSubmitEdit) {
        $.ajax(
            {
                url: "/api/manager/article/update",
                type: "PUT",
                data: formData,
                cache: false,
                processData: false,
                contentType: false,
                success:
                    function(response) {
                        if (response.success) {
                            $(element).parent().parent().parent().children().eq(0).attr('about' , 'Article updated successfully')
                            setTimeout(function () {$(element).parent().parent().parent().children().eq(0).attr('about' , ''); location.reload()} , 2000)
                        }
                        else {
                            $(element).parent().parent().parent().children().eq(0).attr('about' , 'Error occurs while updating article , see logs')
                            setTimeout(function () {$(element).parent().parent().parent().children().eq(0).attr('about' , '')} , 3000)
                            console.log(response.error)
                        }
                    },
                error:
                    function(response) {
                        $(element).parent().parent().parent().children().eq(0).attr('about' , 'Error occurs while updating article , see logs')
                        setTimeout(function () {$(element).parent().parent().parent().children().eq(0).attr('about' , '')} , 3000)
                        console.log(response.error)
                    }
            }
        )
    }
    canSubmitEdit = true
}
function deleteArticle(element) {
    let confirmation = confirm("Are you sure you want to delete this article?")
    if (confirmation) {
        let artId = $(element).parent().parent().next().children().eq(0).children().eq(1).children('.id-edit').val()
        $.ajax(
            {
                url: "/api/manager/article/delete",
                type: "DELETE",
                data: {
                    id: artId
                },
                success:
                    function(response) {
                        if (response.success) {
                            $(element).parent().parent().remove()
                            $(element).parent().parent().next().remove()
                            location.reload()
                        }
                        else {
                        }
                    },
                error:
                    function(response) {
                    }
            }
        )
    }
}