<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<% try{ 
	String contextPath = request.getContextPath();
%>

<style>
	.k-grid  .k-grid-header  .k-header {
	    white-space: normal;
	    word-wrap: break-word;
	}.
</style>

<script src="<%=contextPath %>/resources/js/app/common.js"></script>
<script src="<%=contextPath %>/resources/js/program-cart.js"></script>
<script src="<%=contextPath %>/resources/js/app/event-registration.js"></script>
<script src="<%=contextPath %>/resources/js/app/payment.js"></script>
<script src="<%=contextPath %>/resources/js/app/eventsignup-cart.js"></script>

<script>
	
</script>

<body>

<div id="main">
	<div id="content">

		<div id="event_details" class="k-block">
			
			
			<div id="purchase" >
				<%@ include file="../customer/PayPastDueAddCard.jsp" %> 
			</div> 
			
			 
			<div id="statusBlock">
				<span class="k-block k-success-colored" id="tcSuccessSpan"></span>
				<span class="k-block k-error-colored" id="tcErrorSpan"></span>
			</div>
		</div> 
	</div>
</div>

<iframe src="<%=contextPath %>/viewPaymentForm" height="1px" width="1px" id="childIframeId" style="position:absolute; bottom:-10px; left:-10px;"></iframe>

</body>

<% 	
	}catch(Exception e){
		e.printStackTrace();
	}
%>
