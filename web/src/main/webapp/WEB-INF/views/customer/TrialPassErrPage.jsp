<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ include file="../../layouts/include_taglib.jsp"%>
    <%
    	String contextPath = request.getContextPath();
    %>
    
<body>

<div id="main">
	
	<div id="content" class="bootstrap-frm" style="border: 1px solid #DDD; max-width:950px;">
		<h2 style="color: #888; text-align: center;">Confirmation</h2>
		<h4 style="color: #888; text-align: center;">Please print the Registration Information.</h4>
			<%-- <c:when test="${alreadyUSedTP==null }"> --%>
			
			<div style="max-width : auto;" class="">
		
			<%-- </c:when>
			<c:otherwise>
			
			<h3>You have already used your Trial Pass..!!</h3>
			
			</c:otherwise> --%>
			<h1> Error : Trial Pass Membership not available..!!</h1>
			<h3>${ alreadyUSedTP}.!!</h3>
			<h4></h4>
			
		</div>	
		
		<div>
		<span id="" style="float:right; margin-top:10px;"><a href="/ymca-web/login">Go to Login Page</a></span>
		</div>	
	</div>
		



<script>
	/* $(document).ready(function() {
		//$("#confirmationData").kendoGrid();
		
	}); */
	$(document).ready(function() {
		$("#page_name").html("Trialpass Membership Wizard");
	});
</script>

</body>