<%@ page import="java.sql.ResultSet"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="twitter4j.*"%>

<c:import url="../header.jsp">
	<c:param name="title" value="History"></c:param>
</c:import>

<h1>Records in the system:</h1>
<table class="table table-striped table-hover">
	<thead>
	<tr>
		<th>Picture</th>
		<th>Name</th>
		<th>ScreenName</th>
		<th>Location</th>
	</tr>
	</thead>
	<%
		ResultSet people = (ResultSet) request.getAttribute("people");
	%>
	<%
		while (people.next()) {
	%>
	<tbody>
	<tr>
	    <td><img class="img-circle" id="history-img" src=<%=people.getString("picture")%> ></td>
	    
		<td><%=people.getString("name")%></td>
		<td><%=people.getString("screen_name")%></td>
		<td><%=people.getString("location")%></td>
	</tr>
	</tbody>
	<%
		}
	%>
</table>




<c:import url="../footer.jsp"></c:import>