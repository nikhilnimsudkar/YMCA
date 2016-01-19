<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%
    	String contextPath = request.getContextPath();
    %>
    <script src="<%=contextPath %>/resources/js/app/common.js"></script>
    <script src="<%=contextPath %>/resources/js/program-cart.js"></script>
    <script src="<%=contextPath %>/resources/js/app/programsignup-cart.js"></script>
	<script src="<%=contextPath %>/resources/js/common.js"></script>
    
<div id="main">
	<div id="content">
		<div id="searchprogram" class="k-block">
			<%@ include file="search.jsp" %>
		</div>
		<div id="program_details" class="k-block">
			<div id="program">
				<!-- programInformation.jsp  -->
				<div id="program_session"></div>
			</div>
			<div id="familymembers" style="display:none;">
				<%@ include file="familymembers.jsp" %>
			</div>
			<div id="purchase" style="display:none;">
				<%@ include file="../payment/payment_cart.jsp" %> 
			</div>
			<div id="programsummary" style="display:none;">
				<%@ include file="../program/ProgramSummary.jsp" %>
			</div>
			<div style="clear: both;"></div> 
			<div id="prgcart">
				<%@ include file="../programs_cart.jsp" %> 
			</div>
			<div id="contactHealthHistoryDiv" style="display:none;">
				<%@ include file="../program/contactHealthHistory.jsp" %>
			</div>
			<div style="clear: both;"></div> 
			<div id="statusBlock">
				<span class="k-block k-success-colored" id="tcSuccessSpan"></span>
				<span class="k-block k-error-colored" id="tcErrorSpan"></span>
			</div>
		</div>
	</div>
</div>

<iframe src="<%=contextPath %>/viewPaymentForm" height="1px" width="1px" id="childIframeId" style="position:absolute; bottom:-10px; left:-10px;"></iframe>

<script>
	$(document).ready(function() {
		var eventMethod = window.addEventListener ? "addEventListener" : "attachEvent";
		var eventer = window[eventMethod];
		var messageEvent = eventMethod == "attachEvent" ? "onmessage" : "message";
		eventer(messageEvent,function(e) {
			var key = e.message ? "message" : "data";
			var data = e[key];
			
			var jp_request_hash = hash;
			var orderNumber = paymentOrderId;
		},false);
		
		
		$("#tcSuccessSpan").css("display", "block");		
		$("#tcSuccessSpan" ).html("Please search for Programs on left side to enroll.");	
		
		$("#top_profile").attr('class','');
		$("#top_login").attr('class','');
		$("#top_payment").attr('class','');
		$("#top_programregistration").attr('class','current');
		
		$("#page_name").html("PROGRAM MANAGEMENT");
		
		var gotocontact = '${gottocontact}';
		var item_Detail_Ids = $.sessionStorage.getItem('itemDetailsId');
		var isUserLoggedin = "${pageContext.request.userPrincipal.isAuthenticated()}";
		
		if(!isUserLoggedin)
			$("#cart-header").hide();
		
		if(gotocontact=="true" && item_Detail_Ids!=null && isUserLoggedin){
			 $("#program_details").css("border-width", "1px");	
			 $("#program_details").css("margin-top", "20px");	
			 
			 $("#tcSuccessSpan").css("display", "none");		
			 $("#tcSuccessSpan" ).html("");	
			 $("#tcErrorSpan").css("display", "none");		
			 $( "#tcErrorSpan" ).html("");
			 
			 $("#cart-header").show();
			 $("#add-to-cart").hide();
			 $("#program").fadeOut(200);
			 $("#backtofamily").show();
			 $("#cart-header").show();
			 
			 getFamilymembers();
		}
		
		$( "#addproduct" ).click(function(){
			
			console.log(" Add product to cart from program ");
			
			 $("#program_details").css("border-width", "1px");	
			 $("#program_details").css("margin-top", "20px");	
			 
			 $("#tcSuccessSpan").css("display", "none");		
			 $("#tcSuccessSpan" ).html("");	
			 $("#tcErrorSpan").css("display", "none");		
			 $( "#tcErrorSpan" ).html("");
			
			var item_Detail_Ids = [];
			$('#program_session').find('input[name="selectedItemSession"]').each(function(){
				console.log("  this.value program ::  "+this.value);
				if($(this).is(':checked')){
					var itemDetailId = this.value;
					item_Detail_Ids.push(itemDetailId);
				}
			});
			
			if(item_Detail_Ids==""){
				  $("#tcSuccessSpan").css("display", "none");		
				  $("#tcSuccessSpan" ).html("");	
				  $("#tcErrorSpan").css("display", "block");		
				  $( "#tcErrorSpan" ).html("Please select atleast one program");
				  return;
			}
			
			$.sessionStorage.setItem('itemDetailsId', item_Detail_Ids.join(','));
			
			$("#add-to-cart").hide();
			$("#program").fadeOut(200);
			$("#backtofamily").show();
			
			var isUserLoggedin = '${pageContext.request.userPrincipal.isAuthenticated()}';
			
			if(isUserLoggedin){
				getFamilymembers();
				$("#cart-header").show();
			}
			else{
				var window = $("#popup_add");
				window.clone().kendoWindow({
					title : "Login",
					content : "loginPopup?dispatchTo=Program",
					modal : true,
					width : "500px",
					height : "550px",
					data: { dispatchTo: 'program' },
					close : onClose
					
				}).data("kendoWindow").center().open();
				
				var onClose = function() {
			    }
				$('Div[data-role="draggable"]').css('top', '50px');
			}
			$.sessionStorage.setItem("CartType", "Program");
		});
	});
	
</script>

<style>
	.k-grid  .k-grid-header  .k-header {
	    white-space: normal;
	    word-wrap: break-word;
	}
</style>