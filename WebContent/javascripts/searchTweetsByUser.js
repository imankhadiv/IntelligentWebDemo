/**
 * 
 */
var status;
window.onload = function() {
	status = 1;// 1 for normal search, 2 for start streaming
	//3 for stream started and back to normal search
	//4 for stop streaming only
	$('#streamdisplay').val('');
	searchloop();
};

function searchloop() {
	var count = 100;
	setInterval(function() {
		if (start && count > 0) {
			count -= 1;
			var http_request;
			try {
				// Opera 8.0+, Firefox, Chrome, Safari
				http_request = new XMLHttpRequest();
			} catch (e) {
				// Internet Explorer Browsers
				try {
					http_request = new ActiveXObject("Msxml2.XMLHTTP");
				} catch (e) {
					try {
						http_request = new ActiveXObject("Microsoft.XMLHTTP");
					} catch (e) {
						// Something went wrong
						alert("Your browser broke!");
						return false;
					}
				}
			}
			// do something special
			http_request.open("Get", "SearchTweetsByUserServlet?"+daysbefore+"&status="+5, true);
			http_request.send();
			http_request.onreadystatechange = function() {
				if (http_request.readyState == 4) {
					var response = http_request.responseText;
					var jsonArray = $.parseJSON(response);
					if (jsonArray == null || jsonArray == "") {
//						alert("No result,please input again");
					} else {
						// alert(jsonArray.length);
						// alert(jsonArray[0].name);
						if(start==true)
							displayTable(jsonArray, 2);
					}
				}
			};
		}
	}, 5000);
}



function searchByUser(){
	var daysbefore = $('#daysbefore').val();
	if(daysbefore == 0 && status == 1)
		{//TODO first time start streaming
		status=2;
		}
	else if(daysbefore != 0 && status == 2)
		{//TODO stop streaming and search
		status=3;
		}
	else if(daysbefore == 0 && status == 2)
		{
		// nothing happens 
		// when the stream already starts and the day is 0
		}
	else 
		{//TODO else status when search
		status=1;
		}
	//alert(status);
	searchRequest();
}

function stopTweetStreaming(){
	status = 4;
	var http_request;
	try { http_request = new ActiveXObject('Msxml2.XMLHTTP'); }
	catch (e) {
	try { http_request = new ActiveXObject('Microsoft.XMLHTTP'); }
	catch (e2) {
	try { http_request = new XMLHttpRequest(); }
	catch (e3) { http_request = false; }
	} }
	http_request.open("Get", "SearchTweetsByUserServlet?"+"&status="+status, true);
	http_request.send();
	http_request.onreadystatechange = function(){
		if (http_request.readyState == 4 ) {
			document.getElementById("streamdisplay").innerHTML = "";
		}
	};
}

function searchRequest(){
	var userscreenname = $('#userscreenname').val();
	var daysbefore = $('#daysbefore').val();
	if(userscreenname == null || userscreenname==""){
		alert("please input a user screen name for twitter!");
	} else if(daysbefore == null || daysbefore == "" || daysbefore > 7 || isNaN(daysbefore)
			|| daysbefore < 0){
		alert("please input a valid past day range(0~7)");
	}
	else{
		var http_request;
		try { http_request = new ActiveXObject('Msxml2.XMLHTTP'); }
		catch (e) {
		try { http_request = new ActiveXObject('Microsoft.XMLHTTP'); }
		catch (e2) {
		try { http_request = new XMLHttpRequest(); }
		catch (e3) { http_request = false; }
		} }
		
		http_request.open("Get", "SearchTweetsByUserServlet?username="
				+userscreenname+"&days="+daysbefore+"&status="+status, true);
		http_request.send();
		http_request.onreadystatechange = function(){
			if (http_request.readyState == 4 && daysbefore != 0) {
				var response = http_request.responseText;
				var jsonArray = $.parseJSON(response);
				if (jsonArray == null) {
					alert("No result,please input again");
				} else {
					displayTable(jsonArray, 1);
					//TODO start =false and stop streaming
				}
			} else if (http_request.readyState == 4 && daysbefore == 0) {
				start=true;
				displayTable("",2);//clear all
			}
		};
		
	}
}

function displayTable(jsonArray, divIndex) {
	var html = '<div xmlns:j.0="http://somewhere/tweet#"><table class="table table-striped table-hover" ><tr>' + '<th>Twitter Content</th>'
			+ '<th>URL</th>' + '<th>Create Date</th>' + '</tr>';
	$.each(jsonArray, function(i, tweet) {
		html += '<tr about="http://somewhere/tweet#'+tweet.idString+'">' + '<td property="tweet:content">' + tweet.text + '</td><td>';
		html += '<a target="_blank" href="foursquareIndex.html?shorturl=' + tweet.expandedURL
				+ '" property="tweet:shortUrl">' + tweet.expandedURL + '</a>';
		html += '</td><td property="tweet:date">' + tweet.createdAt + '</td>' + ' </tr>';
	});
	html += '</table></div>';
	if (divIndex == 1) {
		document.getElementById("listdisplay").innerHTML = html;
		document.getElementById("streamdisplay").innerHTML = "";
	} else if (divIndex == 2) {
		document.getElementById("listdisplay").innerHTML = "";
		document.getElementById("streamdisplay").innerHTML = html;
	}

}