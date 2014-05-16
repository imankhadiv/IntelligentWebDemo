<%@ page import="java.sql.ResultSet"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="twitter4j.*"%>

<c:import url="../header.jsp">
	<c:param name="title" value="Mixed Queries"></c:param>
</c:import>


<div class="hero-unit">
	<h2>Mixed Queries</h2>
	<form class="" name="mixed-queries" method="post"
		action="${pageContext.request.contextPath}/MixedQueries"
		onsubmit="return validateMixForm()">


		<label>Longitude:</label><input type="text" name="longitude"
			class="input-mini" placeholder="Sheffield"> <label>Latitude:</label>
		<input type="text" name="latitude" class="input-mini"
			placeholder="Sheffield"><br /> <label>Since:</label> <input
			type="text" class="input-mini" name="daysAgo" value=""
			placeholder="Number"><br /> <label class="checkbox"><input
			class="check-box" type="checkbox" name="checkbox"> Remove
			Default Location</label> <label>Radius:</label> <input type="text"
			name="radius" class="input-small" placeholder="100km"> <br />
		<label>Number:</label> <input type="text" name="number"
			class="input-mini" placeholder="Number"><br /> <input
			class="btn btn-primary btn-large" id="Search" type="submit"
			value="Search">

		<p class="text-error" id="error"></p>

	</form>
		<table class="table table-striped table-borderd table-hover"
		id="query-users">
		<tr>
		<thead>
			<th>Keyword</th>
			<th>Total</th>
		</thead>

		<tr />
		<c:forEach var="entry" items="${words}" varStatus="counter"
			end="${number}" begin="1">


			<tr>
				<td><c:out value="${entry.key}" /></th>

				<td><c:out value="${entry.value}" /></td>
			</tr>

		</c:forEach>

	</table>
	

</div>
<c:import url="../footer.jsp"></c:import>