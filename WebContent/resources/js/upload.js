//$(document).ready(function() {
//	$.ajax({
//		url : "http://rest-service.guides.spring.io/greeting"
//	}).then(function(data) {
//		$('.greeting-id').append(data.id);
//		$('.greeting-content').append(data.content);
//	});
//});
// (function() {
// var flickerAPI =
// "http://api.flickr.com/services/feeds/photos_public.gne?jsoncallback=?";
//		  $.getJSON( flickerAPI, {
//		    tags: "mount rainier",
//		    tagmode: "any",
//		    format: "json"
//		  })
//		    .done(function( data ) {
//		      $.each( data.items, function( i, item ) {
//		        $( "<img>" ).attr( "src", item.media.m ).appendTo( "#images" );
//		        if ( i === 3 ) {
//		          return false;
//		        }
//		      });
//		    });
//		})();
	(function(){
	    var cookies;

	    function readCookie(name,c,C,i){
	        if(cookies){ return cookies[name]; }

	        c = document.cookie.split('; ');
	        cookies = {};

	        for(i=c.length-1; i>=0; i--){
	           C = c[i].split('=');
	           cookies[C[0]] = C[1];
	        }

	        return cookies[name];
	    }

	    window.readCookie = readCookie; // or expose it however you want
	})();
$(function() {
	$("#top").click(function() {
		$("html,body").stop().animate({
			scrollTop : "0"
		}, 1000);
	});
});
$(window).scroll(function() {
	var uzunluk = $(document).scrollTop();
	if (uzunluk > 300)
		$("#top").fadeIn(500);
	else {
		$("#top").fadeOut(500);
	}
});
function doPopup(source) {
    //you can use the source to access a information that you need
	 window.open("http://www.w3schools.com");
}

PrimeFaces.validator['custom.emailValidator'] = {

	pattern : /\S+@\S+/,

	validate : function(element, value) {
		// use element.data() to access validation metadata, in this case there
		// is none.
		if (!this.pattern.test(value)) {
			throw {
				summary : 'Validation Error',
				detail : value + ' is not a valid email.'
			}
		}
	}
};
function handleLoginRequest(xhr, status, args) {
	if (args.validationFailed || !args.loggedIn) {
		PF('dlgUserLogin').jq.effect("shake", {
			times : 5
		}, 100);
	} else {
		PF('dlgUserLogin').hide();
		$('#loginLink').fadeOut();
		// setTimeout(function() { window.location=window.location;},2000);
	}
}
// $(document).ready(function(){
// $("#hadi").click(function(){
// $("#panelLogin").hide();
// });

// });
function denee() {
	jQuery.get('http://localhost/foo.txt', function(data) {
		alert(data);
	});
}
function scrollWin() {
	// Getting the height of the document
	var n = $(document).height();
	$('html, body').animate({
		scrollTop : 700
	}, 1000);
}
function hide() {
	$("#divFiles").hide();
}
function selected() {
	$(document).ready(function() {
		$("#frmShareLink\\:opFileRequest").select();
	});
}
