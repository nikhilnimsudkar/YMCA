<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%
    	String contextPath = request.getContextPath();
    %>
    
<body>

<div id="main2">
	
	<div id="content1" >
		<div id="myprofile" >
			<%@ include file="YAgentProfile.jsp" %> 
		</div>
		
		<div id="CenterSecYagent2"  >
			<%@ include file="YAgentCenterSec2.jsp" %> 
		</div>
		
		<div id="RightSecYagent2"  >
			<%@ include file="YAgentRightSec2.jsp" %> 
		</div>
		
		
		
	</div>
</div>



</body>