<%
	String contextPath = request.getContextPath();
%>

<div id="main">
	
	<div id="content">
		<div id="myprofile">
			<%@ include file="campProfile.jsp" %> 
		</div>
		
		<div id="program">
			<%@ include file="campattendance.jsp" %> 
		</div>
		
		
		
	</div>
</div>