$(document).ready(function () {

	$.get("js/usage.txt",function(data){
		var code = "<pre>" + data + "</pre>";
		$(".usage_code").append(code);
	});

	$.get("js/installationOne.txt",function(data){
		var code = "<pre>" + data + "</pre>";
		$(".install_code_One").append(code);
	});

	$.get("js/installationTwo.txt",function(data){
		var code = "<pre>" + data + "</pre>";
		$(".install_code_Two").append(code);
	});

	//for scrolling in main page
	$(".scroll_button").click(function(e) {
   		$('html,body').animate({
        	scrollTop: $(".feature_container").offset().top},
        'slow');
	});
	
});