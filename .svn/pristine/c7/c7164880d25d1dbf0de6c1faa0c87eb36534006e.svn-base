<script src="<%=contextPath %>/resources/js/program-cart-new.js"></script>
<script src="<%=contextPath %>/resources/js/app/common_new.js"></script>

<div id="prgcart">
	<%@ include file="../programs_cart.jsp" %> 
</div>
<div id="purchase" style="display:none;">
	<%@ include file="../payment/payment_cart.jsp" %> 
</div>
<div id="contactHealthHistoryDiv" style="display:none;">
	<%@ include file="../program/contactHealthHistory.jsp" %>
</div>
<div id="emergencyContact" style="display:none;">
	<%@ include file="../common/emergencyContact.jsp" %>
</div>

<div id="authorisedContact" style="display:none;">
	<%@ include file="../common/authorisedContact.jsp" %>
</div>
<div id="contactActivityDiv" style="display:none;">
	<jsp:include page="../signup/itemActivity.jsp" ></jsp:include>
</div>
<div id="contactTransportDiv" style="display:none;">
	<jsp:include page="../signup/itemTransport.jsp" ></jsp:include>
</div>
<div id="tandc" style="display:none;">
	<div id="termsDiv" class="formatdiv k-shadow" style="margin-left: 20px; padding: 15px; width: 735px; overflow-y: scroll; height:300px;">     
		${terms }
         	</div>
         	<br><br>
   	<div class="formatdiv" style="margin-left:20px;">
   		<input type="checkbox" name="chkTermsAndCond" style="margin-top: 10px;">&nbsp;I accept terms and conditions
   	</div> 
   	<br><br>
	<div class="formatdiv" style="margin-left:20px;">
		<span id="confirmsignup" class="k-button">Confirm</span>
	</div>
</div>		
<div style="clear: both;"></div>

<div id="statusBlock">
	<span class="k-block k-success-colored" id="tcSuccessSpan"></span>
	<span class="k-block k-error-colored" id="tcErrorSpan"></span>
</div>
			 
<script>
	$(document).ready(function() {
		$("#cart-header").appendTo("#shoppingcart");
		
		$( "#cart-info" ).click(function(){
			location.href='childcare?showcart=Y';
			/*
			$("#myprofile").hide();
			$("#programs").hide();
			$("#donation").hide();
			
			location.href = '#/checkout';
			$("#details-checkout").css("width", "1000px");	
			$("#program_details").css("border-width", "1px");
			setTimeout(function(){
				if(cartPreviewModel.totalPrice()=='$0.00'){
					$("#promo").hide();
				}else{
					$("#promo").show();
				}
			}, 500);*/
		});
		
		$("#tandc").hide();
		$("#confirmsignup").click(function(){
			if(!$("input[name='chkTermsAndCond']:checked").val()){
				var kendoWindow = $("<div />").kendoWindow({
		        	title: "Error",
		        	resizable: false,
		        	modal: true,
		        	width:400
		        });
		
	  			kendoWindow.data("kendoWindow")
		         .content($("#terms-conditions-ErrorBox").html())
		         .center().open();
	  			
		        kendoWindow
		        .find(".confirm-terms-conditions")
		        .click(function() {
		        	if ($(this).hasClass("confirm-terms-conditions")) {         		
		        		kendoWindow.data("kendoWindow").close();
		        	}
		        })
		        .end();
		        		
				return false;
	 		}else{
	 			fnCheckout();
	 		}
		});
	});
</script>
