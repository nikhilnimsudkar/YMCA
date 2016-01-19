<%@ page language="java" contentType="text/html; charset=ISO-8859-1"    pageEncoding="ISO-8859-1"%>
    <%@ include file="../../layouts/include_taglib.jsp" %> 
<%
	String contextPath = request.getContextPath();
%>
<head>
<title>YMCA AGENT CONSOLE</title>

<link rel="stylesheet" href="<%=contextPath %>/resources/css/styles.css" type="text/css" media="screen"/>

<link href="<%=contextPath %>/resources/css/kendo/kendo.common.min.css" rel="stylesheet" media="screen">
<link href="<%=contextPath %>/resources/css/kendo/kendo.default.min.css" rel="stylesheet" media="screen">
<link href="<%=contextPath %>/resources/css/kendo/kendo.rtl.min.css" rel="stylesheet" />
<link href="<%=contextPath %>/resources/css/kendo/kendo.dataviz.min.css" rel="stylesheet" />
<link href="<%=contextPath %>/resources/css/kendo/kendo.dataviz.default.min.css" rel="stylesheet" />
<link href="<%=contextPath %>/resources/css/ymca_bootstrap.css" rel="stylesheet" />   
<link rel="stylesheet" href="<%=contextPath %>/resources/css/uniform/themes/default/css/uniform.default.css" media="screen" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="<%=contextPath %>/resources/js/kendo/kendo.all.min.js"></script>
<script src="<%=contextPath %>/resources/js/jquery.blockUI.js"></script>
</head>
<div id="head_main" align="center">
	<div id="top">
		<div id="logo" onclick="location.href='home'" style="cursor:pointer;"></div>
		<div id="right_menu">
			<div id="top_nav">
				<ul>
					<li><a href="#">YMCA of Silicon Valley Membership</a></li>
					<li><a href="#">Financial Assistance</a></li>
					<li style="margin-right: 125px;"><a href="#">Downloads & Forms</a></li>
			    </ul>
			</div>
			<div id="page_name" style="margin-right: 0px">
				YMCA AGENT CONSOLE
			</div>					
		</div>
	</div>
	<div class="nav" >
		<ul>
			<li><a id="top_login" href="<%=contextPath %>/home" class="current">Member Home</a></li>
			<li><a id="top_childcare" href="<%=contextPath %>/Childcare">Child Care</a></li>
			<li><a id="top_donation" href="<%=contextPath %>/createDonation">Donations</a></li>
			<li><a href="<%=contextPath %>/viewScheduledProgram" id="top_schedule">Schedules</a></li>
			<li>
				<a href="#" id="top_programregistration">Registration</a>
				<ul>
					<li><a href="programRegistration" onclick="programRegistrationAction()">Program Registration</a></li>
					<li><a href="eventRegistration" onclick="eventRegistrationAction()">Event Registration</a></li>
				</ul>
			</li>
		</ul>
		<br />
	</div>
</div>
