
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<c:import url="../header.jsp">
	<c:param name="title" value="Query Specific Users"></c:param>
</c:import>


<div class="hero-unit">
	<form class="form-inline" name="query-users" method="post"
		action="${pageContext.request.contextPath}/TrackUsers"
		id="query-users" onsubmit="return validateForm2()">


		<label>User Ids:</label> <input class="input-xlarge" type="text"
			name="userIds" value=""
			placeholder="You can enter up to 10 user ids."> <label>Number:</label>
		<input type="text" class="input-mini" name="number" value=""
			placeholder="Number"> <label>Since:</label> <input
			type="text" class="input-mini" name="daysAgo" value=""
			placeholder="Number"> <input class="btn btn-primary "
			type="submit" value="Search" id="query-users-btn">

		<p class="text-error" id="error"></p>

	</form>

	<table class="table table-striped table-borderd table-hover"
		id="query-users">
		<tr>
		<thead>
			<th>Keywords</th>
			<c:forEach var="person" items="${people}">
				<th><a
					href="${pageContext.request.contextPath}/TrackUsers?userId=${person.screenName}">${person.name}</a></th>

			</c:forEach>
			<th>total</th>
		</thead>

		<tr />
		<c:forEach var="entry" items="${map}" varStatus="counter"
			end="${number}" begin="1">


			<tr>
				<th><c:out value="${entry.key}" /></th>

				<c:forEach var="person" items="${people}">
					<c:choose>
						<c:when test="${person.map[entry.key] >= 1}">
							<td>${person.map[entry.key]}</td>
						</c:when>

						<c:otherwise>
							<td>0</td>
						</c:otherwise>
					</c:choose>

				</c:forEach>
				<td><c:out value="${entry.value}" /></td>
			</tr>

		</c:forEach>

	</table>
</div>


<c:import url="../footer.jsp"></c:import>
