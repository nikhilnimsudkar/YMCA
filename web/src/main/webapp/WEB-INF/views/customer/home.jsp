<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%
    	String contextPath = request.getContextPath();
    %>
    
<body>

<div id="main">
	
	<div id="content">
		<div id="myprofile">
			<%@ include file="profile.jsp" %> 
		</div>
		
		<div id="programs">
			<%@ include file="programs.jsp" %> 
		</div>
		
		<div id="donation">
			<%@ include file="donation.jsp" %>
		</div>
		
		<!-- Block for cart pages -->
		<div id="program_details">
			<%@ include file="../cart/cartPages.jsp" %> 
		</div>
		
	</div>
</div>

</body>