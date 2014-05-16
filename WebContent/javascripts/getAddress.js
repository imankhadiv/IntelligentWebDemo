/**
 * 
 */

var geocoder;
var start;
var stream;
function initialize() {
	geocoder = new google.maps.Geocoder();
	$('#streamdisplay').val('');
	start = false;
	searchloop();
}

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
			http_request.open("GET", "StreamUserServlet", true);
			http_request.send();
			http_request.onreadystatechange = function() {
				if (http_request.readyState == 4) {
					var response = http_request.responseText;
					var jsonArray = $.parseJSON(response);
					if (jsonArray == null) {
						alert("No result,please input again");
					} else {
						// alert(jsonArray.length);
						// alert(jsonArray[0].name);
						if (start == true)
							displayTable(jsonArray, 2);
					}
				}
			};
		}
	}, 5000);
}

function codeAddress() {
	var address = $('#address').val();
	var range = $('#range').val();
	var daysbefore = $('#daysbefore').val();
	var lat;
	var lng;

	var http_request;
	if (address == null || address == "") {
		alert("please input a location key word");
	} else if (range == null || isNaN(range) || range == "") {
		alert("please input a search range");
	} else if (daysbefore == null || daysbefore == "" || isNaN(daysbefore)
			|| daysbefore > 7 || daysbefore < 0) {
		alert("please input a valid past range(0~7)");
	}
	// else if (strat && daysbefore == 0) {
	// alert("stop stream");
	// }
	else {
		geocoder
				.geocode(
						{
							'address' : address
						},
						function(results, status) {
							if (status == google.maps.GeocoderStatus.OK) {
								lat = results[0].geometry.location.lat();
								lng = results[0].geometry.location.lng();
								try {
									// Opera 8.0+, Firefox, Chrome, Safari
									http_request = new XMLHttpRequest();
								} catch (e) {
									// Internet Explorer Browsers
									try {
										http_request = new ActiveXObject(
												"Msxml2.XMLHTTP");
									} catch (e) {
										try {
											http_request = new ActiveXObject(
													"Microsoft.XMLHTTP");
										} catch (e) {
											// Something went wrong
											alert("Your browser broke!");
											return false;
										}
									}
								}

								http_request.open("GET",
										"SearchUserWithVenueServlet?lat=" + lat
												+ "&lng=" + lng + "&r=" + range
												+ "&days=" + daysbefore
												+ "&start=" + start, true);
								http_request.send();
								http_request.onreadystatechange = function() {
									if (http_request.readyState == 4
											&& daysbefore != 0) {
										var response = http_request.responseText;
										var jsonArray = $.parseJSON(response);
										if (jsonArray == null) {
											alert("No result,please input again");
										} else {
											displayTable(jsonArray, 1);
											// TODO start =false and stop
											// streaming
										}
									} else if (http_request.readyState == 4
											&& daysbefore == 0) {
										start = true;
										displayTable("", 2);// clear all
									}

								};

							} else {
								alert('Geocode was not successful for the following reason: '
										+ status);
							}
						});
	}
}

function displayTable(jsonArray, divIndex) {
	var html = '<table width="100%" border="1" ><tr>' + '<th>Photo</th>'
			+ '<th>TwitterID</th>' + '<th>Name</th>' + '<th>location</th>'
			+ '<th>description</th>' + '</tr>';
	$.each(jsonArray, function(i, user) {
		html += '<tr align="center">' + '<td><img src="' + user.photoURL
				+ '"/></td>' + '<td><a href="TrackUsers?userId=' + user.id
				+ '">' + user.id + '</a></td>' + '<td>' + user.name + '</td>'
				+ '<td>' + user.location + '</td>' + '<td>' + user.description
				+ '</td>' + '</tr>';
	});

	html += '</table>';
	if (divIndex == 1) {
		document.getElementById("listdisplay").innerHTML = html;
		document.getElementById("streamdisplay").innerHTML = "";
	} else if (divIndex == 2) {
		document.getElementById("listdisplay").innerHTML = "";
		document.getElementById("streamdisplay").innerHTML = html;
	}

}

function stopStreaming() {
	if (start == true) {
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
		start = false;// set stop
		http_request.open("GET", "SearchUserWithVenueServlet?lat=" + 0
				+ "&lng=" + 0 + "&r=" + 0 + "&days=" + 0 + "&start=" + start
				+ "&stream=stop", true);
		http_request.send();
		http_request.onreadystatechange = function() {
			if (http_request.readyState == 4) {

			} else {
				start = false;
			}
		};
	}
}

google.maps.event.addDomListener(window, 'load', initialize);
