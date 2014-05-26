<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap-responsive.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/stylesheets/style.css"/>
<script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false"></script>
 <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
 <script src="${pageContext.request.contextPath}/javascripts/getAddress.js"></script>
 <script src="${pageContext.request.contextPath}/javascripts/searchTweetsByUser.js"></script>
<script src="${pageContext.request.contextPath}/javascripts/myscript.js"></script>
<script src="${pageContext.request.contextPath}/javascripts/ajaxcalls.js"></script>
<script src="${pageContext.request.contextPath}/javascripts/getAddress.js"></script>

 



<title>${param.title}</title>
</head>
<body id="body" style="background-image:url('${pageContext.request.contextPath}/images/Twitter_logo_blue.png');background-repeat: no-repeat">


<div id="header">
    <nav class="navbar navbar-default navbar-top">
        <div class="navbar-inner navbar-content-center">
        <a class="brand" href="${pageContext.request.contextPath}/queryInterface.jsp">Home Page</a>
            <p class="text-muted credit"></p>
        </div>
    </nav>
</div>
<div id="navbar-background" >

<!-- <img src="/images/Twitter_logo_blue.png" alt="..." class="img-circle"> -->
<div class="container" id="main-container" >


