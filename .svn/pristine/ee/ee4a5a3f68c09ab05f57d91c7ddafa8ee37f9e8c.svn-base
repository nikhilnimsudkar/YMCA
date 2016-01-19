<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<link rel="stylesheet" href="${contextPath }/resources/css/style1.css" type="text/css" media="screen"/>
    
<body>
	<div id="content">
		<div id="myprofile25">
			<%@ include file="PaymentProfile.jsp" %> 
		</div>
		<div id="paymentDetailDiv">
			<div id="PaymentMethodDivID">
				<%@ include file="PaymentMethodInfo.jsp" %>
			</div>
			<div id="PaymentMethodInfoEditDivID" style="display: none;">
				<%@ include file="PaymentMethodInfoEdit.jsp" %>
			</div>
		</div>
	</div>
</body>

<script>
	$(document).ready(function() {
		$("#top_profile").attr('class','');
		$("#top_login").attr('class','');
		$("#top_payment").attr('class','current');
		$("#page_name").html("PAYMENT INFORMATION");
	});
</script>