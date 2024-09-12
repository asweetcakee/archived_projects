$(window).on('load', function(){
	$('#cube-loader').delay(1000).fadeOut('slow')
})
$(function() {
  $('.front').click(function() {
    $(".back").css("-webkit-transform", "rotateY(360deg)")
    $(".front").css("-webkit-transform", "rotateY(180deg)")
  }); 
  $('.back').click(function() {
    $(".front").css("-webkit-transform", "rotateY(0deg)")
    $(".back").css("-webkit-transform", "rotateY(180deg)")
  });
});

