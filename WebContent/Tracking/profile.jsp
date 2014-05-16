
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="twitter4j.*"%>

<c:import url="../header.jsp">
	<c:param name="title" value="UserProfile"></c:param>
</c:import>

<div class="hero-unit">
	<form name="profile" method="get" class="horizantal"
		action="${pageContext.request.contextPath}/TrackUsers"
		onsubmit="return validateProfile();">
		<h3>The Last 100 Tweet Messages</h3>

		<div class="input-prepend">
			<span class="add-on"><i class="icon-search"></i></span> <input
				type="text" name="userId" id="userId" placeholder="Search by user Id"
				value="${param.userId}">
		</div>
		<input class="btn btn-primary btn-medium" id="" type="submit"
			value="Search">
		
		<p class="text-error" id="error"></p>

	</form>
	<%
		if (request.getAttribute("status") != null) {
	%>
	<% ResponseList<Status> status = (ResponseList<Status>) request.getAttribute("status");%>
	<% if (status.size() > 0 ){ %>

	<div class="hero-unit" id="profile">
		<div class="span2">
			<p><strong><%=status.get(0).getUser().getName()%></strong></p>
			<p><strong><span>@</span><%=status.get(0).getUser().getScreenName()%></strong></p>
			<p><strong><%=status.get(0).getUser().getLocation()%></strong></p>
			<p><strong><%=status.get(0).getUser().getDescription()%></strong></p>
		</div>
		<div class="span4">
			<div>
				<img class="img-circle" id="profileimage" src=<%=status.get(0).getUser().getOriginalProfileImageURL()%>>
			</div>
		</div>
		<div class="clearfix"></div>
	</div>
	<table class="table table-striped table-borderd table-hover"
		id="query-users">
		<%int i = 0;%>

		<tr>
			<th></th>
			<th>TweetMessage</th>
			<th>Created At</th>
		</tr>
		<%
			for (Status sts : status) {
		%>
		<tr>
			<td><%=++i%></td>
			<td><%=sts.getText()%></td>
			<td><%=sts.getCreatedAt()%></td>
		</tr>

		<%
			}
		%>



	</table>
	<%}%><%} %>
	
</div>








<c:import url="../footer.jsp"></c:import>