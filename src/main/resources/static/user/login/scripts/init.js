$(document).ready(function () {
    $("#form").submit(function (event)
    {
        if (valid() !== true)
        {
            event.preventDefault();
        }
    });
});