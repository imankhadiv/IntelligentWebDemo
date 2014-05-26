<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:import url="header.jsp">
<c:param name="title" value="HomePage"></c:param> 
</c:import>

<div id="iman">
<div class="hero-unit " id="container" >
<h1>Welcome</h1>
<h4>Querying The Social Web</h4>

<a class="btn btn-primary btn-large" href="Tracking/tracking-discussions.jsp" >Track Discussions</a><br/>
<a class="btn btn-primary btn-large" href="Tracking/query-users.jsp">Queries About Users</a><br/>
<a class="btn btn-primary btn-large" href="searchTweetsByUseName.html">Points Of Interest</a><br/>
<a class="btn btn-primary btn-large" href="venue.jsp">Venue</a><br/>
<a class="btn btn-primary btn-large" href="foursquareIndex.html">Venue Information</a><br/>
<a class="btn btn-primary btn-large" href="Tracking/mixqueries.jsp">Mixed Queries</a><br/>
<a class="btn btn-primary btn-large" href="Tracking/profile.jsp">Recent Tweets</a><br/>
<a class="btn btn-primary btn-large" href="Tracking/history.jsp">History</a></br>

</div>
</div>





<c:import url="footer.jsp"></c:import>
