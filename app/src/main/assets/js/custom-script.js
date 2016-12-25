$(document).ready(function() {
    $("#discussionButton").click(function(){
        $("#pasco-comments").slideToggle("fast", function(){
            $("pasco-comments").slideDown("slow");
        });
    });
});