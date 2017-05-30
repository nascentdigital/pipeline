$(document).ready(function(){

	// function to change all html entities into unicode
	function HTMLEncode(str){
  		var i = str.length,
      		aRet = [];

  		while (i--) {
  			var iC = str[i].charCodeAt();
   			if (iC < 47 || (iC>57 && iC<65) || iC > 127 || (iC>90 && iC<97)) {
      			aRet[i] = '&#'+iC+';';
   			} else {
      			aRet[i] = str[i];
    		}
   		}
  		return aRet.join('');    
	}

	function mapGroupClass(str){
		var newStr = str.substring(0,str.length-8);
		return newStr;
	}

	$.getJSON("js/docgen.json",function(data){
		addEventListener('load', function(event) { PR.prettyPrint(); }, false);
		// this function iterates through each key value pair
		$.each(data, function(k,v){
			var groupName = k;
			k=k.replace(" ","");
			var idName = k + '_content';
			var groupSection = "#" + idName;

			$(".inner").append('<div id="' +idName+'"</div>');
			var inner_data = '<h1 class = "dark_text_color ' + k + '_content "> ' + groupName + '</h1>';
			var side_menu = '<li id = ' + k + '>' + groupName + '</li>'
			$(groupSection).append(inner_data);
			$(groupSection).append("<hr>");
			$(".vertical_menu_list").append(side_menu);
			$(".mobile_menu_list").append(side_menu);
			$('#'+k).append('<ul class = "submenu" id="'+ k + '_sub"></ul>');
			$('.mobile_menu_list #'+k).append('<ul class = "mobile_submenu" id="'+ k + '_sub"></ul>');


			//adding information for each method
			$.each(v,function(key,value){
				var method = HTMLEncode(value.MethodKey) + "_content";
				$(groupSection).append('<div class="methods" id="' + method + '"">');
				var name_data = '<h2>' + HTMLEncode(value.MethodSignature) + "</h2>";
				$('#'+method).append(name_data);
				$('#'+k+'_sub').append('<li id="' + HTMLEncode(value.MethodKey) + '">' +HTMLEncode(value.Key) + '</li>');
				$('.mobile_menu_list #'+k+'_sub').append('<li id="' + HTMLEncode(value.MethodKey) + '">' + value.MethodName + '</li>');
				var paramDescription = [];

				// parse comments
				if(typeof value.Comment != 'undefined'){
						var comment_data = "<p>" + value.Comment + "</p>";
						$('#'+method).append(comment_data);
				}

				$('#'+method).append("<h3>Parameters: </h3>");
				var count = 1;
				var param_data;

				// displaying parameter info
				if(value.Parameters.length==0){

					param_data = "No Parameters";
					$('#'+method).append(param_data);

				}else{

					$.each(value.Parameters,function(m,n){
						if(typeof m != 'undefined' || typeof n != 'undefined'){
							param_data = "<p><i>" + HTMLEncode(n.Type) + " " + HTMLEncode(n.Name) + ": </i>";
							if(n.ParamDescrip != undefined){
								param_data+="       " + HTMLEncode(n.ParamDescrip)+"</p>";
							}
						}
						$('#'+method).append(param_data);
					});

				}

				// if there's exception
				if(value.Throws != ""){
					$('#'+method).append("<h3>Throws: </h3>")
					var exception_data = "<p>" + value.Throws + "</p>";
					$('#'+method).append(exception_data);
				}

				$('#'+method).append("<h3>Returns: </h3>")
				var returnType_data = "<p>" + HTMLEncode(value.ReturnType) + "</p>";
				$('#'+method).append(returnType_data);
	
				$('#'+method).append("<h3>Example:</h3>");
				var sample_code = '<div class = "sample_code"><pre class="prettyprint java">'
				+ HTMLEncode(value.Example) + 
				'</pre></div>';
				PR.prettyPrint();
				$('#'+method).append(sample_code);
				$(groupSection).append('</div>');
			});
		});

		$(".submenu li").click(function(){
			var id = this.id;
			$('html,body').animate({
				scrollTop: $('#' + id + '_content').offset().top -88
			},'fast');
		});

		$(".vertical_menu_list li").click(function(e){
			if(e.target !== this){
				return;
			}
			var id = this.id;
			$('html,body').animate({
				scrollTop: $('#' + id + '_content').offset().top -88
			},'fast');
			
		});

		//menu animations on the mobile view
		$("#mobile_menu li").click(function(){
			var id = this.id;
			$('.mobile_submenu').css('display','none');
			$('#' + id +' .mobile_submenu').css('display','block');
		});

		$(".mobile_submenu li").click(function(e){
			var id = this.id;
				if(e.target !== this){
					return;
				}
			$('.inner').css('position','static');
			$('html,body').animate({
				scrollTop: $('#' + id + '_content').offset().top -88
			},'slow');
			$('#mobile_menu').css('width','0');
		});

		$(window).scroll(function(){
			var margin_top = 100;
			var scrollOffset = $(window).scrollTop();
			$(".inner div:not(.sample_code)").each(function(){
				
				var elementOffset = $('#'+this.id).offset().top;
				var menu_id = mapGroupClass(this.id);
				if( scrollOffset >=  elementOffset - margin_top &&  scrollOffset <= elementOffset+$('#'+this.id).height()-margin_top){
					$('#'+menu_id).css('font-weight','800');
					$('#'+menu_id).css('font-size','120%');
					$('#'+menu_id + ' .submenu').css('display','block');
				}else{
					$('#'+menu_id).css('font-weight','400');
					$('#'+menu_id).css('font-size','100%');
					$('#'+menu_id + ' .submenu').css('display','none');
				}
			});

			var lastDivPos = $(window).scrollTop() - $(".inner div:not(.sample_code)").last().offset().top;
			if(lastDivPos>= (0 - $(window).height()) + $(window).height()*0.5){
				var id = mapGroupClass($(".inner div:not(.sample_code)").last().attr('id'));
				$('#'+id).css('font-weight','800');
				$('#'+id).css('font-size','120%');
				$('#'+id + ' .submenu').css('display','block');
				var prev_id =  mapGroupClass($(".inner div:not(.sample_code)").last().prev().attr('id'));
				$('#'+prev_id).css('font-weight','400');
				$('#'+prev_id).css('font-size','100%');
				$('#'+prev_id + ' .submenu').css('display','none');
			}

		});

	});
});

function openNav(){
	document.getElementById("mobile_menu").style.width="100vw";
	$('.mobile_submenu').css('display','none');
}
function closeNav(){
	document.getElementById("mobile_menu").style.width="0";
	$('.mobile_submenu').css('display','none');
}
