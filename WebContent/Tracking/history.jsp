<%@ page import="java.sql.ResultSet"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="twitter4j.*"%>
<%@ page import="beans.*"%>
<%@ page import="java.util.*"%>

<c:import url="../header.jsp">
	<c:param name="title" value="History"></c:param>
</c:import>
<div class="hero-unit " id="container">


	<h3>Records in the system:</h3>
	<form name="track-discussion" method="get" class="horizantal"
		action="${pageContext.request.contextPath}/TrackDiscussions"
		onsubmit="">
		<h2>Tracking discussions</h2>
		<input type="hidden" name="action" value="history"> <label>User
			Name:</label> <input type="text" name="userName"
			placeholder="Insert an user name"> <label>User id:</label> <input
			type="text" name="userId" placeholder="inser user id"><br>
		<input type="submit">


	</form>
	<%
		Person person = (Person) request.getAttribute("person");
	%>
	<%
		if(person != null){
	%>
	<div class="hero-unit" id="profile">
		<div class="span2">
			<p><strong><%=person.getName()%></strong></p>
			<p><strong><span>@</span><%= person.getScreenName()%></strong></p>
			<% if(person.getLocation() != null){ %>
			<p><strong><%=person.getLocation()%></strong></p>
			<%} %>
			<% if(person.getDescription()!= null){ %>
			<p><strong><%=person.getDescription()%></strong></p>
			<%} %>
		</div>
		<div class="span4">
			<div>
				<img class="img-circle" id="profileimage" src="<%=person.getProfilePicture()%>">
			</div>
		</div>
		
		
		<% Map<String,Integer> map = person.getMap(); %>
		<table class="table">
		
		<hr/>
		
		<th>Keywords</th>
		<tr/>
		<% int i=0; %>
		<% for(String item:map.keySet()){ %>
		<% i+=1; %>
		<td><%=item %></td>
		<% if(i%5 ==0) {%>
		<tr>
		<%} %>
		
		<%} %>
		</table>
			
	</div>
	<table class="table table-striped table-borderd table-hover">
	
		<tr>
			<th>content</th>
			<th>Short url</th>
			<th>Retweet People</th>
			<th>Venue</th>
			<th>Date</th>
		</tr>
	<%
		for(Tweet tweet:person.getTweets()){
	%>

		
		<td><%= tweet.getContent() %></td>
		<td><%= tweet.getShortUrl() %></td>
		<td><%= tweet.getRetweetPeople()%></td>
		<td><%= tweet.getHasVenue()%></td>
		<td><%= tweet.getDate()%></td>
		<tr/>

		<%}} %>


	</table>
	
	
	
	
	
</div>


<c:import url="../footer.jsp"></c:import>