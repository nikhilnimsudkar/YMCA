<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%
    	String contextPath = request.getContextPath();
    %>
<%@ include file="../../layouts/include_taglib.jsp" %>
<body>

<div id="main">
	
	<div id="content">
		<div id="myprofile">
			<%@ include file="memberprofile.jsp" %> 
		</div>
		
		<div id="programs">
			<%@ include file="currentmember.jsp" %> 
		</div>
		
		
		
	</div>
</div>



</body>
