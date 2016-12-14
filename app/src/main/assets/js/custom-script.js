

$(document).ready(function(){
  $("#checkButton").click(function(){
    $(".progress").show(600);
    $(".percentage").addClass("showPercentage");
  });

  /*
  $('#discussionButton').click(function(){
  if ( $('#pasco-comments').css('visibility') == 'hidden' )
    $('#pasco-comments').css('visibility','visible');
  else
    $('#video-over').css('visibility','hidden');
  });
  $('#comments-container').comments({
    sendText: 'Comment'
  });

  $( "#book" ).slideUp( "slow", function() {
    // Animation complete.
  });
  */

  $("#discussionButton").click(function(){
    $("#pasco-comments").slideToggle("fast", function(){
      $("pasco-comments").slideDown("slow");
  });
    
});



  
});