<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="header.jsp">
	<c:param name="title" value="Venue"></c:param>
</c:import>



	<table class="table table-borderd table-hover" id="venuetable">
 		<tr>
 		<th>Location key word(eg. Sheffield) : </th>
 		<td><input id="address" type="text"  value="Sheffield"></td>
 		</tr>
 		<tr>
 		<th>Search range : </th>
 		<td> <input id="range" type="number" ></td>
 		</tr>
 		<tr>
 		<th>Past day range : </th>
 		<td> <input id="daysbefore" type="number" ></td>
 		</tr>
 		<tr>
 		<td colspan="2" align="right"> 
 		<a class="btn btn-primary btn-large" onclick="codeAddress()">Search</a>
 		<a class="btn btn-danger btn-large" onclick="stopStreaming()">Stop</a>
 		
 		</td>
 		</tr>
 	</table>
 <a>*if the day is 0, it would start streaming for 50seconds</a>
      
     
     
     
      
      <div id="listdisplay"></div>
      <div id="streamdisplay"></div>

<c:import url="footer.jsp"></c:import>
