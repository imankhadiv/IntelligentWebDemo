/**
 * 
 */
window.onload = function() {
	$("#table").hide();
	$('#shorturl').val(getUrlParam('shorturl'));
};
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); 
    var r = window.location.search.substr(1).match(reg);  
    if (r != null) return unescape(r[2]); return null; 
}

function loadJSON() {
	var http_request;
	var shorturl = $('#shorturl').val();
	var type = $('#type').val();
	if (shorturl == null || shorturl == "") {
		alert("please input a url");
	} else {

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
		http_request.open("GET", "FoursquareServlet?shorturl=" + shorturl,
				true);
		http_request.send();
		http_request.onreadystatechange = function() {
			if (http_request.readyState == 4) {
				var temp =http_request.responseText;
				// Javascript function JSON.parse to parse JSON data
				var jsonObj = $.parseJSON(temp);
				if (jsonObj == null) {
					alert("The url you just typed is an invalid foursquare url,please input again");
					$('#shorturl').val("");
				} else {
					var userJson = jsonObj.user;
					var venueJson = jsonObj.venue;
					document.getElementById("first_name").innerHTML = userJson.firstName;
					document.getElementById("last_name").innerHTML = userJson.lastName;
					document.getElementById("gender").innerHTML = userJson.gender;
					document.getElementById("venue_name").innerHTML = venueJson.venue_name;
					document.getElementById("country").innerHTML = venueJson.country;
					document.getElementById("city").innerHTML = venueJson.city;
					document.getElementById("post_code").innerHTML = venueJson.postcode;
					document.getElementById("address").innerHTML = venueJson.address;
					document.getElementById("latitude").innerHTML = venueJson.latitude;
					document.getElementById("longitude").innerHTML = venueJson.longitude;
					document.getElementById("userphoto").src = userJson.photoURL;
					document.getElementById("venuepic").src = venueJson.photoURL;
					document.getElementById("venueURL").innerHTML = venueJson.URL;
					document.getElementById("category").innerHTML = venueJson.categories;
					$("#table").show();
					initialize(venueJson.latitude,venueJson.longitude,15,type,venueJson);
//					initialize();
				}

			}
		};
		
	}
};

var map;
var service;
var infowindow;

function initialize(lat,lng,mzoom,type,venueJson) {
  var pyrmont = new google.maps.LatLng(lat,lng);
  infoWindow = new google.maps.InfoWindow();
  map = new google.maps.Map(document.getElementById('map-canvas'), {
      center: pyrmont,
      zoom: mzoom
    });
  var mymarker = new google.maps.Marker({
      position: pyrmont,
      map: map,
      title: venueJson.venue_name
  });
  google.maps.event.addListener(mymarker, 'click', function() {
	  infoWindow.setContent('<div class="table table-striped table-hover">'+venueJson.venue_name+'<br/><img src="'+venueJson.photoURL+'" width="80" height="80"/>'
			  +'<br />Address: ' + venueJson.address + '<br />'+'Category: ' + venueJson.category+'</div>');
//	  infoWindow.setContent('<img src="' + venueJson.photoURL + '" /><br/><font style="color:#000;">' + venueJson.name + 
//	          '<br />Address: ' + venueJson.address + '<br />Category: ' + //venueJson.category + 
//	          '</font>'
//	      );
      infoWindow.open(map, mymarker);
  });
 
      
    
  
  var request = {
    location: pyrmont,
    radius: '500',
    query: type
  };
 
  service = new google.maps.places.PlacesService(map);
  service.textSearch(request, callback);
}

function callback(results, status) {
  if (status == google.maps.places.PlacesServiceStatus.OK) {
    for (var i = 0; i < results.length; i++) {
      var place = results[i];
      if(results[i]!=null)
  		{
    	  createMarker(results[i]);
  		}
    }
    
  }
  
}
function createMarker(place) {
	
	  var marker = new google.maps.Marker({
	    map: map,
	    position: place.geometry.location,
	    icon: {
	      // Star
	      path: 'M 0,-24 6,-7 24,-7 10,4 15,21 0,11 -15,21 -10,4 -24,-7 -6,-7 z',
	      fillColor: '#ffff00',
	      fillOpacity: 1,
	      scale: 1/4,
	      strokeColor: '#bd8d2c',
	      strokeWeight: 1
	    }
	  });

	  google.maps.event.addListener(marker, 'click', function() {
		  service.getDetails(place, function(result, status) {
	      if (status != google.maps.places.PlacesServiceStatus.OK) {
	        return;
	      }
		  var photos = place.photos;
		  var url;
		  if (typeof(photos) == "undefined")
		  {
			  url=result.icon;
			  } else {
				url = photos[0].getUrl({
					'maxWidth' : 200,
					'maxHeight' : 200
				});
			}
			  var rate = "no rating result";
			if (typeof (result.rating) != "undefined") {
				rate = result.rating;
			}
		  var vicinity="no vicinity result";
		  if (typeof(result.vicinity) != "undefined")
		  {
			  vicinity=result.vicinity;
		  }	 
	      infoWindow.setContent('<div style="height:250px; width: 250px;">'+'<font style="color:#000;">' + result.name + 
		          '<br />Rating: ' + rate + '<br />Vicinity: ' + vicinity + 
		          '</font><br/><img src="' + url + '" width="120" height="120" />'+'</div>'
		      );
	      infoWindow.open(map, marker);
	    });
	  });
}
//function initialize(lat,lng,mzoom,venueName) {
//    var mapOptions = {
//      center: new google.maps.LatLng(lat, lng),
//      zoom: mzoom
//    };
//     map = new google.maps.Map(document.getElementById("map-canvas"),
//        mapOptions);
//    
//    
//    
//     marker = new google.maps.Marker({
//    	position: new google.maps.LatLng(lat, lng),
//    	map: map,
//    	title:venueName
//    	});
//     infowindow = new google.maps.InfoWindow({
//    	content: "hellow!"
//    	});
//    marker.setMap(map);
//    google.maps.event.addListener(marker, 'click', function() {
//        infowindow.open(map,marker);
//      });
//  }