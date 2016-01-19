<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%
    	String contextPath = request.getContextPath();
    %>
     <link rel="stylesheet" href="<%=contextPath %>/resources/css/styles.css" type="text/css" media="screen"/>
	<link href="<%=contextPath %>/resources/css/ymca_bootstrap.css" rel="stylesheet" />   
	<link rel="stylesheet" href="<%=contextPath %>/resources/css/style1.css" type="text/css" media="screen"/>
	<script src="<%=contextPath %>/resources/js/jquery.blockUI.js"></script>
<body>
<div class="k-loading-mask"
		style="width: 100%; height: 100%; position: absolute; top: 150px; left: 0px; display: none; z-index: 9999">
	<span class="k-loading-text">Please wait...</span>
	<div class="k-loading-image">
		<div class="k-loading-color"></div>
	</div>
</div>

<div id="main">
	<div id="content">
		<div id="myprofile" style="width : 21%">
			<%@ include file="donationProfile.jsp" %> 
			<div>
				<div id="" class="k-block" style="padding: 10px;   background: white;  min-width: 255px;">
					<ul>
						<li><span>Every gift makes a difference.<br /></span></li>
				  		<li><span>Every one has role to play.<br /></span></li>
				  		<li><span>Together, we can achieve so much more.<br /></span></li>
				  	</ul>
				</div>
			</div>
		</div>
		<div id="donationDetailDiv">
			<div id="donationRightSection" style="width: 865px;">
				<%@ include file="createDonationForm.jsp" %> 
			</div>					 
		</div>
	</div>
</div>
</body>

<script>
	$(document).ready(function() {
		$("#top_profile").attr('class','');
		$("#top_login").attr('class','');
		$("#top_payment").attr('class','');
		$("#top_donation").attr('class','current');
		
		$("#page_name").html("DONATION INFORMATION");
		$("#page_name").css("font-size", "26px");
	});
</script>