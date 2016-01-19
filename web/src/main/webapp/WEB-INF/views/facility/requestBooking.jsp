<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%
    	String contextPath = request.getContextPath();
    %>
    
<body>

<div id="main2">
	
	<div id="content1" >
		<div id="myprofile" >
			<%@ include file="profile.jsp" %> 
		</div>
		
		<div id="CenterSecYagent2"  >
			<%@ include file="requestBookingFormTemp.jsp" %> 
		</div>
		
		<%-- <div id="RightSecYagent2"  >
			<%@ include file="rightSec2.jsp" %> 
		</div> --%>
		
		
		
	</div>
</div>



</body>