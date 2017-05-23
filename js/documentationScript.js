$(document).ready(function(){
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
			var idName = k + '_content';
			var groupSection = "#" + idName;

			$(".inner").append('<div id="' +idName+'"</div>');
			var inner_data = '<h1 class = "dark_text_color ' + k + '_content "> ' + k + '</h1>';
			var side_menu = '<li  class = "groupings" id = ' + k + '>' + k + '</li>'
			$(groupSection).append(inner_data);
			$(groupSection).append("<hr>");
			$(".vertical_menu_list").append(side_menu);
			$(".mobile_menu_list").append(side_menu);
			$('#'+k).append('<ul class = "submenu" id="'+ k + '_sub"></ul>');

			//adding information for each method
			$.each(v,function(key,value){
				var name_data = "<h2><b>" + value.MethodName + "</b></h2>";
				$(groupSection).append(name_data);
				$('#'+k+'_sub').append('<li id="' + HTMLEncode(value.MethodKey) + '">' + value.MethodName + '</li>');
				var paramDescription = [];
				if(typeof value.Comment != 'undefined'){
					var comment = value.Comment.replace(new RegExp('\\*','g'),'');
					var arr = comment.split("@param");
					
					for (var c in arr){
							var comment_data = "<p>" + HTMLEncode(arr[c]) + "</p>";
							$(groupSection).append(comment_data);
					}
				}

				$(groupSection).append("<h3>Parameters: </h3>");
				var count = 1;
				$.each(value.Parameters,function(m,n){
					var param_data = "No Parameters";
					
					if(typeof m != 'undefined' || typeof n != 'undefined'){
						param_data = "<p>" + HTMLEncode(n.Type) + " " + HTMLEncode(n.Name) + "</p>";
						
					}
					$(groupSection).append(param_data);
				});
				$(groupSection).append("<h3>Returns: </h3>")
				var returnType_data = "<p>" + HTMLEncode(value.ReturnType) + "</p>";
				$(groupSection).append(returnType_data);
	
				$(groupSection).append("<h3>Example:</h3>");
				var sample_code = '<div class = "sample_code"><pre class="prettyprint java">'
				+ HTMLEncode(value.Example) + 
				 '</pre></div>';
				PR.prettyPrint();
				$(groupSection).append(sample_code);
			});

			
		});

		$(".submenu li").click(function(){
			var id = this.id;
			console.log(id);
			$('html,body').animate({
				scrollTop: $('#' + id + '_content').offset().top -88
			},'fast');
			
		});

		$(".vertical_menu_list li").click(function(){
			var id = this.id;
			$('html,body').animate({
				scrollTop: $('#' + id + '_content').offset().top -88
			},'fast');
			
		});



		//menu animations on the mobile view
		$(".mobile_menu li").click(function(){
			var id = this.id;
			$('.inner').css('position','static');
			$('html,body').animate({
				scrollTop: $('#' + id + '_content').offset().top -88
			},'slow');
			$('.mobile_menu').toggleClass('expanded');
			$('.mobile_menu').css('display','none');

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

				var prev_id =  mapGroupClass($(".inner div:not(.sample_code)").last().prev().attr('id'));
				$('#'+prev_id).css('font-weight','400');
				$('#'+prev_id).css('font-size','100%');
			}

		});

	});

	$('.hamburger_button').click(function(){
		$('.mobile_menu').slideToggle('slow', function(){
			$('.mobile_menu').toggleClass('expanded');
		});
	});
			

	$('.inner').click(function(){
		if($('.mobile_menu').hasClass('expanded')){
			$('.mobile_menu').removeClass('expanded');
			$('.mobile_menu').css('display','none');
		}
	});


});