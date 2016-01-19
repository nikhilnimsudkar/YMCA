<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ include file="../../layouts/include_taglib.jsp" %>
    
    <%
    	String contextPath = request.getContextPath();
    %>
    <link rel="stylesheet" href="<%=contextPath %>/resources/css/styles.css" type="text/css" media="screen"/>
	<link href="<%=contextPath %>/resources/css/ymca_bootstrap.css" rel="stylesheet" />   
	<link rel="stylesheet" href="<%=contextPath %>/resources/css/style1.css" type="text/css" media="screen"/>
	
	<link rel="stylesheet" href="<%=contextPath %>/resources/css/jquery-ui.css" type="text/css" media="all" />  
	<link href="<%=contextPath %>/resources/css/kendo/kendo.common.min.css" rel="stylesheet" media="screen">
	<link href="<%=contextPath %>/resources/css/kendo/kendo.default.min.css" rel="stylesheet" media="screen">
	<link href="<%=contextPath %>/resources/css/kendo/kendo.dataviz.min.css" rel="stylesheet" />
	<link href="<%=contextPath %>/resources/css/kendo/kendo.dataviz.default.min.css" rel="stylesheet" />
	
	<script src="<%=contextPath %>/resources/js/kendo/kendo.web.min.js"></script>
	<script src="<%=contextPath %>/resources/js/kendo/angular.min.js"></script>
	<script src="<%=contextPath %>/resources/js/kendo/kendo.all.min.js"></script>
	<script src="<%=contextPath %>/resources/js/kendo/jquery.slimscroll.min.js"></script>
    
<body>
<div id="main">
	<div id="content">	
		<c:if test="${not empty agentUid}">			
			<div id="myprofile" style="width : 21%">
				<%@ include file="donationProfile.jsp" %> 				
			</div>
			<div id="donationDetailDiv">
				<div id="donationRightSection" style="width: 865px;">
					<%@ include file="donationEmployerMatchDashboard.jsp" %> 
				</div>					 
			</div>
		</c:if>
	</div>
</div>
</body>
<script>
	$(document).ready(function() {		
		$("#top_profile").attr('class','');
		$("#top_login").attr('class','');
		$("#top_payment").attr('class','');
		$("#top_donation").attr('class','current');
		
		$("#page_name").html("Employer Donations");
		$("#page_name").css("font-size", "26px");
					
	});
</script>