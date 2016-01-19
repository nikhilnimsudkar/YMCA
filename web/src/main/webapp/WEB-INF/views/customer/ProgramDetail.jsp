<%
	String contextPath = request.getContextPath();
%>

<div id="main">
	
	<div id="content">
		<div id="myprofile">
			<%@ include file="programProfile.jsp" %> 
		</div>
		
		<div id="program">
			<%@ include file="programattendance.jsp" %> 
		</div>
		
		
		
	</div>
</div>