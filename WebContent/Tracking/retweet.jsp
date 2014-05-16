
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:import url="../header.jsp">
	<c:param name="title" value="HomePage"></c:param>
</c:import>


<h1>List of people who have retweeted @<c:out value="${param.id}"></c:out>'s tweet</h1>
<table class="table table-striped table-borderd table-hover">
	<tr></tr>

	<tr>
		<th>Profile Picture</th>
		<th>Name</th>
	</tr>


	<tr><tr/>
		<c:forEach var="person" items="${retwittPeople}" begin="0" end="${param.count}">
	
		<c:if test="${fn:contains(person.twittText,param.id)}">
			<tr>
				<td><img class="img-polaroid" src="${person.profilePicture}"></td>
				<td><h4><a
					href="${pageContext.request.contextPath}/TrackUsers?userId=${person.screenName}">${person.screenName}</a></h4></td>
			</tr>
		</c:if>
	</c:forEach>
</table>



<c:import url="../footer.jsp"></c:import>
