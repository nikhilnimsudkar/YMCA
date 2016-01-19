<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ include file="../../layouts/include_taglib.jsp"%>
    <%
    	String contextPath = request.getContextPath();
    
    %>
    <head>
<link href="<%=contextPath %>/resources/css/smart_wizard.css" rel="stylesheet" type="text/css">
<link href="<%=contextPath %>/resources/css/style1.css" rel="stylesheet" type="text/css">
<script src="<%=contextPath %>/resources/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=contextPath %>/resources/js/jquery.smartWizard.js"></script>
<script src="<%=contextPath %>/resources/js/maskedinput.min.js"></script>
<script src="<%=contextPath %>/resources/js/additional-methods.min.js"></script>
<script src="<%=contextPath %>/resources/js/customer/membership/changeMembershipInfo.js"></script>

   
</head>    
<body>
<iframe src="<%=contextPath %>/viewPaymentForm" height="1px" width="1px" id="childIframeId" style=" position:absolute; bottom:-10px; left:-10px;"></iframe>
<div id="mainDiv">
	<div id="contentDiv">
		
		<div id="changeMembershipDetail">
			<div id="changeMembershipRightSection">
				<%@ include file="changeMembershipWizard.jsp" %> 
			</div>					 
		</div>
	</div>
</div>
</body>

<script>
	$(document).ready(function() {
		$("#page_name").html("Change Member Wizard");
	});
</script>