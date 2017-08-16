$(document).ready(function () {

	$.get("js/usage.txt",function(data){
		var code = "<pre>" + data + "</pre>";
		$(".usage_code").append(code);
	});

	$.get("js/installation.txt",function(data){
		var code = "<pre>" + data + "</pre>";
		$(".install_code").append(code);
	});

	//for scrolling in main page
	$(".scroll_button").click(function(e) {
   		$('html,body').animate({
        	scrollTop: $(".feature_container").offset().top},
        'slow');
	});
	
});