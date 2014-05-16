<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:import url="../header.jsp">
	<c:param name="title" value="HomePage"></c:param>
</c:import>

<div class="hero-unit">
	<form name="track-discussion" method="post" class="horizantal"
		action="${pageContext.request.contextPath}/TrackDiscussions"
		onsubmit="return validateForm()">
		<h2>Tracking discussions</h2>

		<label>Keyword:</label> <input type="text" name="keyword"
			placeholder="Insert a keyword to search"> <label>Hashtag:</label>
		<input type="text" name="hashtag" placeholder="Search by Hashtag"><br>



		<label>Longitude:</label><input type="text" name="longitude"
			class="input-mini" placeholder="Sheffield"> <label>Latitude:</label>
		<input type="text" name="latitude" class="input-mini"
			placeholder="Sheffield"> <label class="checkbox"><input
			class="check-box" type="checkbox" name="checkbox"> Remove
			Default Location</label> <label>Radius:</label> <input type="text"
			name="radius" class="input-small" placeholder="100km"> <br />
		<input class="btn btn-primary btn-large" id="search-keyword"
			type="submit" value="Search">
		<p class="text-error" id="error"></p>

	</form>

	<table class="table table-striped table-borderd table-hover"
		id="query-users">

		<tr>
		<thead>
			<th>Image</th>
			<th>Name</th>
			<th>Username</th>
			<th>Tweet Text</th>
			<th>Retweeted?</th>
		</thead>
		</tr>
		<c:forEach var="person" items="${people}">
			<tr>

				<td><a
					href="${pageContext.request.contextPath}/TrackUsers?userId=${person.screenName}"><img
						class="img-polaroid" src="${person.profilePicture}"></a>
				<td><a
					href="${pageContext.request.contextPath}/TrackUsers?userId=${person.screenName}">${person.name}</a></td>
				<td><a
					href="${pageContext.request.contextPath}/TrackUsers?userId=${person.screenName}">${person.screenName}</a></td>
				<td><c:out value="${person.twittText}" /></td>

				<c:choose>
					<c:when test="${person.twittCount > 0}">

						<td><a
							href="<c:url value="/Tracking/retweet.jsp?id=${person.screenName}&count=${person.twittCount}"/>">${person.twittCount}</a></td>
					</c:when>

					<c:otherwise>
						<td>0</td>
					</c:otherwise>
				</c:choose>
			</tr>
		</c:forEach>


	</table>
</div>

<c:import url="../footer.jsp"></c:import>
