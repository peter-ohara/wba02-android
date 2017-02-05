var comments,
    discussionBtn,
    //checkBtn,
    page;

var discussionScreen = {
    open: function(){
        //slide discussion screen up
        comments.removeClass("pasco-comments-invisible");
    },
    close: function(){
        //slide discussion screen down
        console.log('add class');
        comments.addClass("pasco-comments-invisible");
    }
}

var checkMcq = {
    open: function(){
        //make percentage score visible
    },
    close: function(){
        //make percentage score invisible
    }
}

var runEventListeners = function(){
    //discussion button
    discussionBtn.click(function(){
        console.log("discussion btn clicked");
        discussionScreen.open();
    })
    
    //check button
//    checkBtn.click(function(){
//        checkMcq.open();
//    })

    //close button
    console.log(closeBtn.length)
    closeBtn.click(function(){
        discussionScreen.close();
    })
}

$(document).ready(function(){
    //init dom elements
    comments        = $("#pasco-comments");
    discussionBtn   = $("#discussionButton");
    //checkBtn        = $("#checkButton");
    page            = $(".page");
    closeBtn        = $("#pasco-comments .fa-times");

    //start eventlisteners
    runEventListeners();
});
