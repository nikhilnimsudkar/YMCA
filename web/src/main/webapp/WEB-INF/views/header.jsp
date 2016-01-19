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
<%-- 
<link rel="stylesheet" href="<%=contextPath %>/resources/css/jquery-ui.css" type="text/css" media="all" /> 
 --%>
<link href="<%=contextPath %>/resources/css/kendo/kendo.common.min.css" rel="stylesheet" media="screen">
<link href="<%=contextPath %>/resources/css/kendo/kendo.default.min.css" rel="stylesheet" media="screen">
<link href="<%=contextPath %>/resources/css/kendo/kendo.rtl.min.css" rel="stylesheet" />
<link href="<%=contextPath %>/resources/css/kendo/kendo.dataviz.min.css" rel="stylesheet" />
<link href="<%=contextPath %>/resources/css/kendo/kendo.dataviz.default.min.css" rel="stylesheet" />
<link href="<%=contextPath %>/resources/css/ymca_bootstrap.css" rel="stylesheet" />   
<link rel="stylesheet" href="<%=contextPath %>/resources/css/uniform/themes/default/css/uniform.default.css" media="screen" />

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/jquery-ui.min.js" type="text/javascript"></script>
<%-- 
<script src="<%=contextPath %>/resources/js/jquery.min.js"></script>
<script src="<%=contextPath %>/resources/js/jquery-ui.min.js"></script>
<script src="<%=contextPath %>/resources/js/kendo/kendo.web.min.js"></script>
<script src="<%=contextPath %>/resources/js/kendo/kendo.ui.core.min.js"></script>
<script src="<%=contextPath %>/resources/js/kendo/kendo.data.min.js"></script>
<script src="<%=contextPath %>/resources/js/kendo/kendo.datepicker.min"></script>
--%>
<script src="<%=contextPath %>/resources/js/kendo/kendo.all.min.js"></script>
<script src="<%=contextPath %>/resources/js/kendo/angular.min.js"></script>

<script src="<%=contextPath %>/resources/js/kendo/jquery.slimscroll.min.js"></script>
<script src="<%=contextPath %>/resources/js/uniform/jquery.uniform.min.js"></script>

<%-- 
<script src="http://cdn.kendostatic.com/2014.3.1119/js/kendo.all.min.js"></script>
<script src="<%=contextPath %>/resources/js/kendo/kendo.all.min.js"></script>
--%>
<script src="<%=contextPath %>/resources/js/jquery.html5storage.min.js"></script>
<script src="<%=contextPath %>/resources/js/sha512.js"></script>
<script src="<%=contextPath %>/resources/js/jszip.min.js"></script>
<script src="<%=contextPath %>/resources/js/jquery.blockUI.js"></script>



<script>
function validateUserName($email) {
	  var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
	  if( !emailReg.test( $email ) ) {
	    return false;
	  } else {
	    return true;
	  }
}

function logoutAction(){
	$.sessionStorage.clear();
	location.href='/ymca-web/logout';
}

function eventRegistrationAction(){
	$.sessionStorage.removeItem('search_event_filter');
}

function programRegistrationAction(){
	$.sessionStorage.removeItem('search_program_filter');
}

function childCareRegistrationAction(){
	$.sessionStorage.removeItem('search_child_care_filter');
}

function campRegistrationAction(){
	
}

function redirectLogin(){
	$.sessionStorage.clear();
	location.href='/ymca-web/login';
}

function isLoggedInUserAgent(){
	var isLoggedInUserAgent = $("#agentLoggedInFlagSpan").html();		
	if(typeof isLoggedInUserAgent != 'undefined' && isLoggedInUserAgent !=""){				
		return true;
	}else{		
		return false;
	}	
}

$(document).ready(function(){
	//$("input[type='checkbox']").uniform();
	//$("input[type='radio']").uniform();
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
	
	<c:if test="${pageContext.request.userPrincipal.isAuthenticated()}">
	/*
	$.ajax({
		  type: "GET",
		  url: "getLoggedInUserDetails",	
		  async: false,
		  dataType: "json",
		  success: function( data ) {
			  var userName = "";
			  if(data.length>0){
					 $.each(data, function(i,pr) {
						 userName = pr.loggedInUserName;
						 loggedInUserContactId = pr.loggedInUserContactId;
					 });
			  }
			  $("#userNameSpan").html(userName);
			  $("#loggedInUserContactId").val(loggedInUserContactId);
		  },
		  error: function( xhr,status,error ){
			  //console.log("Error");
			  //console.log(xhr);
			  $("#userNameSpan").html("");
			  $("#tcSuccessSpan").css("display", "none");		
			  $("#tcSuccessSpan" ).html("");	
			  $("#tcErrorSpan").css("display", "block");		
			  $( "#tcErrorSpan" ).html("There was some error. Please try again later");			
		  }
	});
	*/
	</c:if>	
});
</script>
</head>
<body>
	<div id="head_main" align="center">
		<div id="top">
			<div id="logo" onclick="location.href='home'" style="cursor:pointer;"></div>
			<div id="right_menu">
				<div id="top_nav">
					<ul>
						<li><a href="#">YMCA of Silicon Valley Membership</a></li>
						<li><a href="#">Financial Assistance</a></li>
						<li style="margin-right: 125px;"><a href="#">Downloads & Forms</a></li>
						<!--
						<li style="margin-right: 145px;"><a href="#">Get involved</a></li>
						<li><a href="http://www.ymcasv.org/"><img src="<%=contextPath %>/resources/img/search.png"></a></li>
						 -->
						 <c:if test="${!pageContext.request.userPrincipal.isAuthenticated()}">
						<li style="margin-right: 110px;"><a href="#" style="color:#eb8120; font-size: 14px;margin-top: -3px;font-weight:bold;text-decoration: underline;" onclick="redirectLogin()">Login</a></li> 
							
						</c:if>	
						<c:if test="${pageContext.request.userPrincipal.isAuthenticated()}">						
							<li><input type="hidden" id="loggedInUserContactId" value="${usInfo.contactId}"></li> 
							<%-- <li><span class="head" style="width:100px;padding-top:50px;color:#eb8120;font-weight:bold;">WELCOME ${usInfo.username}!</span> --%>
							<li><span id="shoppingcart" style="float: left; position: absolute; right: 250px; display: inline; top: 22px; text-decoration: underline;"></span></li>
							<li>
					            <span class="alert alert-danger alert-dismissable">
					                <span class="k-button" style="background-color: #eb8120; color: #ffffff; background-image:none;" onclick="logoutAction()"><span>Log out</span></span>
					            </span>
					        </li>
						</c:if>	
				    </ul>
				</div>	
				<div>
					<c:if test="${!pageContext.request.userPrincipal.isAuthenticated() && not empty fn:trim(agentUid)}">
						<span id="loginUsrNamee" style="float: left; margin-left: 145px; right: 20px;margin-top: 0px; display: inline; top: 22px; text-decoration: underline;">
							<span class="head" style="width:100px;color:#eb8120;font-weight:bold; font-size: 13px;">
								<span id="userNameSpan">Impersonate By ${agentUid}</span> 
							</span> 
						</span>						
					</c:if>
					<c:if test="${pageContext.request.userPrincipal.isAuthenticated()}">
						<span id="loginUsrNamee" style="float: left; margin-left: 145px; right: 20px;margin-top: 0px; display: inline; top: 22px; text-decoration: underline;">
							<span class="head" style="width:100px;color:#eb8120;font-weight:bold; font-size: 13px;">
								WELCOME<span id="userNameSpan"> ${usInfo.firstName} ${usInfo.lastName}! </span> <c:if test="${not empty agentUid}"> - Impersonate By ${agentUid}</c:if>
							</span> 	
													
						</span>
					</c:if>
					<c:if test="${not empty agentUid}">
						<span id="agentLoggedInFlagSpan" style="display:none;">${agentUid}</span>
					</c:if>
					<div id="page_name">
						MEMBER PROFILE
					</div>
				</div>
			</div>
		</div>
		<div class="nav" >
			<ul>
				<li><a id="top_login" href="<%=contextPath %>/home" class="current">Member Home</a></li>
				<c:if test="${pageContext.request.userPrincipal.isAuthenticated()}">
					<li><a id="top_profile" href="<%=contextPath %>/view_membership">Profile</a></li>
					
				</c:if>
				<li><a id="top_donation" href="<%=contextPath %>/donationHome">Donations</a></li>
				<li><a href="<%=contextPath %>/viewScheduledProgram" id="top_schedule">Schedules</a></li>
				<li>
					<a href="#" id="top_programregistration">Registration</a>
					<ul>
						<li><a href="<%=contextPath %>/program" onclick="programRegistrationAction()">Program Registration</a></li>
						<li><a href="<%=contextPath %>/event" onclick="eventRegistrationAction()">Event Registration</a></li>
						<li><a href="<%=contextPath %>/camp" onclick="campRegistrationAction()">Camp Registration</a></li>
						<li><a href="<%=contextPath %>/childcare" onclick="childCareRegistrationAction()">Child Care Registration</a></li>
					</ul>
				</li>
				<li><a id="top_facility" href="<%=contextPath %>/facilityBooking">Facility</a></li>
				<c:if test="${pageContext.request.userPrincipal.isAuthenticated()}">
					<li><a id="top_payment" href="<%=contextPath %>/viewPaymentInformation">Payment Information</a></li>
				</c:if>
				<c:if test="${not empty fn:trim(agentUid)}">
					<li><a id="top_yagentConsole" href="<%=contextPath %>/admin/YAgentConsole?agentUid=12234&sso=testsoo">Agent Dashboard</a></li>
				</c:if>				
			</ul>
			<br />
		</div>
		<!-- Dialog box for showing Errors  -->
		<div id="errorGenericDialogBox" title="Error">
			<p style="font-size: 12px;" id="errorDialogMessage"></p>
		</div>
	</div>
	
	<!-- 
	<table border="0" width="1000">
		<tbody>
			<tr>
				<td><img src="<%--=contextPath --%>/resources/img/Header.png" border="0" height="150" usemap="#headerMap"></td>
			</tr>
		</tbody>
	</table>	
	<c:if test="${not empty userId}">
            <div class="alert alert-danger alert-dismissable">
                <div class="k-button" style="position:absolute; left:90%; top:26px; background-color: #eb8120; color: #ffffff; background-image:none;" onclick="location.href='logout'"><span>Log out</span></div>
            </div>
    </c:if>	
    
	<map name="headerMap" id="headerMap">
		<area target="" href="home" coords="60,110,200,140" title="" alt="member_home" shape="rect">
		<area target="" href="view_membership" coords="220,110,350,140" title="" alt="profile" shape="rect">
		<area target="" href="#" coords="370,110,500,140" title="" alt="donation" shape="rect">
		<area target="" href="#" coords="510,110,630,140" title="" alt="schedule" shape="rect">
		<area target="" href="#" coords="640,110,860,140" title="" alt="program_registration" shape="rect">
		<area target="" href="#" coords="880,110,1080,140" title="" alt="payment_information" shape="rect">
		<area target="" href="http://www.ymcasv.org/" coords="50,10,180,100" title="" alt="ymca_home" shape="rect">		
	</map> -->
</body>
</html>