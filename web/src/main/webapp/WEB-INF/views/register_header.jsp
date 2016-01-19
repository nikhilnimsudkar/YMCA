<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ include file="../layouts/include_taglib.jsp" %> 
    <%
    	String contextPath = request.getContextPath();
    %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<link rel="stylesheet" href="<%=contextPath %>/resources/css/styles.css" type="text/css" media="screen"/>
<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/themes/base/jquery-ui.css" type="text/css" media="all" /> 

<link href="<%=contextPath %>/resources/css/kendo/kendo.common.min.css" rel="stylesheet" media="screen">
<link href="<%=contextPath %>/resources/css/kendo/kendo.default.min.css" rel="stylesheet" media="screen">
<link href="<%=contextPath %>/resources/css/kendo/kendo.dataviz.min.css" rel="stylesheet" />
<link href="<%=contextPath %>/resources/css/kendo/kendo.dataviz.default.min.css" rel="stylesheet" />
<link href="<%=contextPath %>/resources/css/ymca_bootstrap.css" rel="stylesheet" />   

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/jquery-ui.min.js" type="text/javascript"></script>
<script src="<%=contextPath %>/resources/js/kendo/kendo.web.min.js"></script>
<script src="<%=contextPath %>/resources/js/kendo/angular.min.js"></script>
<script src="<%=contextPath %>/resources/js/kendo/kendo.all.min.js"></script>
<script src="<%=contextPath %>/resources/js/kendo/jquery.slimscroll.min.js"></script>

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
	<div id="head_main" align="center">
		<div id="top">
			<div id="logo"></div>
			<div id="right_menu">
				<div id="top_nav">
					<ul>
						<li><a href="#">YMCA of Silicon Valley Membership</a></li>
						<li><a href="#">Financial Assistance</a></li>
						<li><a href="#">Downloads & Forms</a></li>
						<li><a href="#">Get involved</a></li>
						<li><a href="http://www.ymcasv.org/"><img src="<%=contextPath %>/resources/img/search.png"></a></li>
						<li>
							<c:if test="${pageContext.request.userPrincipal.isAuthenticated()}">
					            <span class="alert alert-danger alert-dismissable">
					                <span class="k-button" style="background-color: #eb8120; color: #ffffff; background-image:none;" onclick="location.href='logout'"><span>Log out</span></span>
					            </span>
						    </c:if>	
						 </li>
				    </ul>
				</div>	
				<div id="page_name">
					Registration Wizard
				</div>
			</div>
		</div>
		<div class="nav">&nbsp;</div>
		
		<!-- Dialog box for showing Errors  -->
		<div id="errorGenericDialogBox" title="Error">
			<p style="font-size: 12px;" id="errorDialogMessage"></p>
		</div>
	</div>
	
	
</body>
</html>