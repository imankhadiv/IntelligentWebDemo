function loadXMLDoc()
{
var xmlhttp;
if (window.XMLHttpRequest)
  {// code for IE7+, Firefox, Chrome, Opera, Safari
  xmlhttp=new XMLHttpRequest();
  }
else
  {// code for IE6, IE5
  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  }
xmlhttp.onreadystatechange=function()
  {
  if (xmlhttp.readyState==4 && xmlhttp.status==200)
    {
    document.getElementById("mix").innerHTML=xmlhttp.responseText;
    }
}
xmlhttp.open("POST","/MixedQueries",true);
xmlhttp.send();
}



//function loadXMLDoc()
//{
//	var xmlhttp;
//	if (window.XMLHttpRequest){// code for IE7+, Firefox, Chrome, Opera, Safari
//		xmlhttp=new XMLHttpRequest();
//	 }else{// code for IE6, IE5
//		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
//	}
//	
//	xmlhttp.onreadystatechange=function(){
//		  if (xmlhttp.readyState==4 && xmlhttp.status==200){
//			  	document.getElementById("..").innerHTML=xmlhttp.responseText;
//		    }
//	  };
//	  
//	xmlhttp.open("POST", "/TrackDiscussions",true);
//	xmlhttp.send();
//}

//$(document).ready(function(){
//	
//	function queryUsers(){
//		$.ajax({
//			url:"../TrackUsers",
//			method:"post",
//			dataType:"json",
//			data:$("#query-users").serialize(),
//			success:function(data){
//				$.each(data,function(d){
//					alert(d.number+" ");
//				});
//				alert("helllo there this is cave of progrramming");
//			},
//			error:function(){
//				console.log("error occured");
//			}
//		});
//		
//	}
//	function formSubmit(){
//		event.preventDefault();
//		queryUsers();
//	}
//	
//	$("#query-users-btn").on("click",function(e){
//		formSubmit();
//	});
//	
//	
//});