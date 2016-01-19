<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%
    	String contextPath = request.getContextPath();
    
    %>
    <head>
    <%-- <meta name="_csrf" content="${_csrf.token}"/>
    
    <meta name="_csrf_header" content="${_csrf.headerName}"/> --%>
<link rel="stylesheet" href="<%=contextPath %>/resources/css/style1.css" type="text/css" media="screen"/>
<link href="<%=contextPath %>/resources/css/smart_wizard.css" rel="stylesheet" type="text/css">
<script src="<%=contextPath %>/resources/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=contextPath %>/resources/js/jquery.smartWizard.js"></script>
<script src="<%=contextPath %>/resources/js/maskedinput.min.js"></script>
<script src="<%=contextPath %>/resources/js/additional-methods.min.js"></script>
<script type="text/javascript" src="<%=contextPath %>/resources/js/kendo/products.js"></script> 
 
<%-- <script type="text/javascript" src="<%=contextPath %>/resources/js/jquery-2.0.0.min.js"></script>  --%> 
</head>    
<body>
<iframe src="<%=contextPath %>/viewPaymentForm" height="1px" width="1px" id="childIframeId" style=" position:absolute; bottom:-10px; left:-10px;"></iframe>
<div id="main12">
	<div id="content23">
		
		<div id="donationDetailDiv34">
			<div id="donationRightSection55">
				<%@ include file="trailP_demo.jsp" %> 
			</div>					 
		</div>
	</div>
</div>
</body>

<script>
	$(document).ready(function() {
		$("#page_name").html("Trialpass Membership Wizard");
		var dobYearForm = new Date().getFullYear();
	    dobYearForm = parseInt(dobYearForm.toString());
	    for(var i=0; i<100 ; i++){
	    	$('#dobYearForm').append($('<option>', {value: dobYearForm,text: dobYearForm}));
	    	dobYearForm = dobYearForm - 1;
	    }
	    $("#dobYearForm").kendoDropDownList();    
	    $("#dobDateForm").kendoDropDownList();
	    $("#dobMonthForm").kendoDropDownList();
	});
</script>