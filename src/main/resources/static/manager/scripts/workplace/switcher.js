/**
 * Switchers for article management menu part
 */
document.getElementById("find-article").onclick = function() {
    $("#add-article-menu").fadeOut(500);
    $("#edit-article-menu").fadeOut(500);
    $("#delete-article-menu").fadeOut(500);
    $("#profile-menu").fadeOut(500);
    $("#find-article-menu").fadeIn(500);
}
document.getElementById("add-article").onclick = function() {
    $("#find-article-menu").fadeOut(500);
    $("#edit-article-menu").fadeOut(500);
    $("#delete-article-menu").fadeOut(500);
    $("#profile-menu").fadeOut(500);
    $("#add-article-menu").fadeIn(500);
}
document.getElementById("edit-article").onclick = function() {
    $("#find-article-menu").fadeOut(500);
    $("#add-article-menu").fadeOut(500);
    $("#delete-article-menu").fadeOut(500);
    $("#profile-menu").fadeOut(500);
    $("#edit-article-menu").fadeIn(500);
}
document.getElementById("delete-article").onclick = function() {
    $("#find-article-menu").fadeOut(500);
    $("#add-article-menu").fadeOut(500);
    $("#edit-article-menu").fadeOut(500);
    $("#profile-menu").fadeOut(500);
    $("#delete-article-menu").fadeIn(500);
}
document.getElementById("profile").onclick = function() {
    $("#find-article-menu").fadeOut(500);
    $("#add-article-menu").fadeOut(500);
    $("#edit-article-menu").fadeOut(500);
    $("#delete-article-menu").fadeOut(500);
    $("#profile-menu").fadeIn(500);
}