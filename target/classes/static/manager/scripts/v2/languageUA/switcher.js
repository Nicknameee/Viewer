/**
 * Switchers for menu points
 */
document.getElementById("article-management").onclick = function() {
    $("#profile-menu").fadeOut(500);
    $("#users-menu").fadeOut(500);
    $("#promo-menu").fadeOut(500);
    $("#settings-menu").fadeOut(500);
    $("#articles-menu").fadeIn(500);
    if ($(window).width() < 1200) toggler()
}
document.getElementById("user-management").onclick = function() {
    $("#articles-menu").fadeOut(500);
    $("#promo-menu").fadeOut(500);
    $("#profile-menu").fadeOut(500);
    $("#settings-menu").fadeOut(500);
    $("#users-menu").fadeIn(500);
    if ($(window).width() < 1200) toggler()
}
document.getElementById("promo-management").onclick = function() {
    $("#articles-menu").fadeOut(500);
    $("#users-menu").fadeOut(500);
    $("#profile-menu").fadeOut(500);
    $("#settings-menu").fadeOut(500);
    $("#promo-menu").fadeIn(500);
    if ($(window).width() < 1200) toggler()
}
document.getElementById("profile").onclick = function() {
    $("#articles-menu").fadeOut(500);
    $("#users-menu").fadeOut(500);
    $("#promo-menu").fadeOut(500);
    $("#settings-menu").fadeOut(500);
    $("#profile-menu").fadeIn(500);
    if ($(window).width() < 1200) toggler()
}
document.getElementById("settings-management").onclick = function() {
    $("#articles-menu").fadeOut(500);
    $("#users-menu").fadeOut(500);
    $("#promo-menu").fadeOut(500);
    $("#profile-menu").fadeOut(500);
    $("#settings-menu").fadeIn(500);
    if ($(window).width() < 1200) toggler()
}