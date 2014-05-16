
	function validateForm() {
		
		var keyword = document.forms["track-discussion"]["keyword"].value;
		var hashtag = document.forms["track-discussion"]["hashtag"].value;
		var longitude = document.forms["track-discussion"]["longitude"].value;
		var latitude = document.forms["track-discussion"]["latitude"].value;
		var radius = document.forms["track-discussion"]["radius"].value;
		
		if ((keyword == null || keyword == "") && (hashtag == null || hashtag == "")) {
			document.getElementById("error").innerHTML = "Please enter a keyword or hashtag to search for.";
			
			return false;
		}
		if(longitude != null && isNaN(longitude)){
			document.getElementById("error").innerHTML = "Longitude must be numeric!";
			return false;
		}
		if(latitude != null && isNaN(latitude)){
			document.getElementById("error").innerHTML = "Latitude must be numeric!";
			return false;
		}
		if(radius != null && isNaN(radius)){
			document.getElementById("error").innerHTML = "Radius must be numeric!";
			return false;
		}		
}
	function validateForm2() {
		var UserIds = document.forms["query-users"]["userIds"].value;
		var words = document.forms["query-users"]["number"].value;
		var days = document.forms["query-users"]["daysAgo"].value;
	
		
		if (UserIds == null || UserIds == "") {
			document.getElementById("error").innerHTML = "Please enter up to 10 user ids.";
			
			return false;
		}
		if(words == null || words == ""){
			document.getElementById("error").innerHTML = "Please indicate a number to search for most frequent. ";
			return false;
		}
		if(words != null && isNaN(words) ){
			document.getElementById("error").innerHTML = "Please enter a numerci value. ";
			return false;
			
		}
		if(days == null || days == ""){
			document.getElementById("error").innerHTML = "Please indicate number of days. ";
			return false;
		}
		if(isNaN(days)){
			document.getElementById("error").innerHTML = "Please enter a numerci value. ";
			return false;
			
		}
		
	}
	function validateProfile(){
				var userId = document.forms["profile"]["userId"].value;
				if(userId == null || userId == "") {
					document.getElementById("error").innerHTML = "Please enter a user id";
					return false;
				}

	}

	function validateMixForm(){
		
		var longitude = document.forms["mixed-queries"]["longitude"].value;
		var latitude = document.forms["mixed-queries"]["latitude"].value;
		var radius = document.forms["mixed-queries"]["radius"].value;
		var daysAgo = document.forms["mixed-queries"]["daysAgo"].value;
		var number = document.forms["mixed-queries"]["number"].value;

		if(longitude != null && isNaN(longitude)){
			document.getElementById("error").innerHTML = "Longitude must be numeric!";
			return false;
		}
		if(latitude != null && isNaN(latitude)){
			document.getElementById("error").innerHTML = "Latitude must be numeric!";
			return false;
		}
		if(radius != null && isNaN(radius)){
			document.getElementById("error").innerHTML = "Radius must be numeric!";
			return false;
		}		
		if(daysAgo == null || daysAgo == "" || isNaN(daysAgo) ){
			document.getElementById("error").innerHTML = "Enter number of days";
			return false;
		}
		if(number == null || number == "" || isNan(number) ){
			document.getElementById("error").innerHTML = "Number of keywords must be filled out";
			return false;

		}
		
}
	
		