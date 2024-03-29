function changeLanguage(element) {
    let link = location.protocol + location.host + location.pathname
    let url = new URL(link);
    if ($(element).text() === 'EN') {
        url.searchParams.append('lang' , 'EN')
    }
    if ($(element).text() === 'UA') {
        url.searchParams.append('lang' , 'UA')
    }
    location.href = url.href
}
$(window).resize(function () {
    if ($(window).width() > 991) {
        $('#navbarResponsive').removeClass().addClass('collapse show navbar-collapse')
        $('#menu-toggle-button').attr('aria-expanded' , false)
    }
    else {
        $('#navbarResponsive').removeClass().addClass('collapsing navbar-collapse')
        $('#menu-toggle-button').attr('aria-expanded' , true)
    }
})
window.onload = function () {
    scrollToTop()
    connectHome()
    setTags()
}
function scrollToTop() {
    window.scroll(0 , 0)
}
function showChangesAlert() {
    $("#changes-alert").removeClass('d-none')
}
window.onscroll = function () {
    if ($(window).scrollTop() > 0) {
        $("#mainNav").addClass("border-bottom")
    }
    else {
        $("#mainNav").removeClass("border-bottom")
    }
}
window.addEventListener('DOMContentLoaded', event => {

    // Navbar shrink function
    var navbarShrink = function () {
        const navbarCollapsible = document.body.querySelector('#mainNav');
        if (!navbarCollapsible) {
            return;
        }
        if (window.scrollY === 0) {
            navbarCollapsible.classList.remove('navbar-shrink')
        } else {
            navbarCollapsible.classList.add('navbar-shrink')
        }

    };

    // Shrink the navbar
    navbarShrink();

    // Shrink the navbar when page is scrolled
    document.addEventListener('scroll', navbarShrink);

    // Activate Bootstrap scrollspy on the main nav element
    const mainNav = document.body.querySelector('#mainNav');
    if (mainNav) {
        new bootstrap.ScrollSpy(document.body, {
            target: '#mainNav',
            offset: 74,
        });
    }
});
function menuToggle() {
    let aria = $('#menu-toggle-button').attr('aria-expanded')
    if (aria === 'true') {
        $('#navbarResponsive').removeClass().addClass('collapsing navbar-collapse')
        $('#menu-toggle-button').attr('aria-expanded' , false)
    }
    else {
        $('#navbarResponsive').removeClass().addClass('collapse show navbar-collapse')
        $('#menu-toggle-button').attr('aria-expanded' , true)
    }
}