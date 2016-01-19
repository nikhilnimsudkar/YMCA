<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%
    	String contextPath = request.getContextPath();
    
    %>
    <head>
    <%-- <meta name="_csrf" content="${_csrf.token}"/>
    
    <meta name="_csrf_header" content="${_csrf.headerName}"/> --%>
<link rel="stylesheet" href="<%=contextPath %>/resources/css/styles.css" type="text/css" media="screen"/>
<link rel="stylesheet" href="<%=contextPath %>/resources/css/jquery-ui.css" type="text/css" media="all" />  
<link href="<%=contextPath %>/resources/css/kendo/kendo.common.min.css" rel="stylesheet" media="screen">
<link href="<%=contextPath %>/resources/css/kendo/kendo.default.min.css" rel="stylesheet" media="screen">
<link href="<%=contextPath %>/resources/css/kendo/kendo.dataviz.min.css" rel="stylesheet" />
<link href="<%=contextPath %>/resources/css/kendo/kendo.dataviz.default.min.css" rel="stylesheet" />
<link href="<%=contextPath %>/resources/css/ymca_bootstrap.css" rel="stylesheet" />   
<link rel="stylesheet" href="<%=contextPath %>/resources/css/style1.css" type="text/css" media="screen"/>

<!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/jquery-ui.min.js" type="text/javascript"></script> -->
 <script src="<%=contextPath %>/resources/js/jquery.min.js"></script>
<script src="<%=contextPath %>/resources/js/jquery-ui.min.js"></script>
<script src="<%=contextPath %>/resources/js/kendo/kendo.web.min.js"></script>
<script src="<%=contextPath %>/resources/js/kendo/angular.min.js"></script>
<script src="<%=contextPath %>/resources/js/kendo/kendo.all.min.js"></script>
<script src="<%=contextPath %>/resources/js/kendo/jquery.slimscroll.min.js"></script>
<script src="<%=contextPath %>/resources/js/sha512.js"></script>

<link href="<%=contextPath %>/resources/css/smart_wizard.css" rel="stylesheet" type="text/css">
<script src="<%=contextPath %>/resources/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=contextPath %>/resources/js/jquery.smartWizard.js"></script>
<script src="<%=contextPath %>/resources/js/maskedinput.min.js"></script>
<script src="<%=contextPath %>/resources/js/additional-methods.min.js"></script>
<script type="text/javascript" src="<%=contextPath %>/resources/js/kendo/products.js"></script>   
<script type="text/javascript" src="<%=contextPath %>/resources/js/customer/membership/memberSignupWizard.js "></script>
<script src="<%=contextPath %>/resources/js/jquery.html5storage.min.js"></script>

<script>
function validateUserName($email) {
	  var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
	  if( !emailReg.test( $email ) ) {
	    return false;
	  } else {
	    return true;
	  }
}

$(document).ready(function(){
	$("#errorGenericDialogBox").dialog({
        autoOpen: false,
        modal:true,
        resizable: false,
        width:400,        
        buttons: {
    		Ok: function() {
      			$(this).dialog("close");
      			    			
    		} 
		}
    });
});
</script>

</head>    
<body>
<iframe src="<%=contextPath %>/viewPaymentForm" height="1px" width="1px" id="childIframeId" style=" position:absolute; bottom:-10px; left:-10px;"></iframe>
<div id="main12">
	<div id="content23">
		
		<div id="donationDetailDiv34">
			<div id="donationRightSection55">
				<%@ include file="memberSignupWizard.jsp" %> 
			</div>					 
		</div>
	</div>
</div>
</body>

<script>
	$(document).ready(function() {
		$("#page_name").html("Become Member Wizard");
	});
</script>