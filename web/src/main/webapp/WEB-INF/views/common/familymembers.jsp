<div id="members" style="width: 780px;; padding: 12px;">
	<div class="k-window1" id="popup_add" style="display:none;"></div>
	<div>
		<span class="head" style="float:left;">Select Family Contact for Program</span>
		<div style="float:left; margin-left: 50px;"><span>OR</span></div>
		<div style="margin-left: 50px; float: left; margin-top: -5px;"><span  class="k-button" id=add_member_family_con style="float:left;">Add New Family Contact</span></div>
		</div>
	<div style="clear: both;"></div> 
	<div id="family_details" style="margin: 20px 20px 30px;  min-height: 220px;">
		<div id="allmembers"></div>
		
	</div>
	
	<div id="cartbtns" align="right">
		<button id="backtocc" class="k-button">Back</button>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<span id="checkoutBtn" class="k-button">Add to Cart</span>
		
	</div>
</div>

<script>
$(document).ready(function(){
	
	$( "#checkoutBtn" ).click(function(){
		$(".k-loading-mask").show();
		cart.set("errMsg","");
		$("#dspErr").hide();
		
		/*  if($("#pageType").val()=='CHILD CARE'){
			
			
			$('#allmembers').find('input[class="usercheck"]').each(function(){
				if($("#user_"+this.value).is(':checked')){
					var selectedStartDate = $("#ccStartDate_"+this.value).val();
					
				}
			});
			
		} */
		// added by lavy for in service price calculation
		//showInserviceDays();
		
		addToCart();
		
		//if(validateCheckout()){
			//console.log(cart);
			 
			 //setTimeout(function(){
				 	//if(cart.errMsg==""){
				 		// $("#familymembers").fadeOut(200);
				 		//showContactHealthHistoryOrCheckOut('CHILD CARE');
						//$("#checkout_content").show();
				 	//}
				 	//else{
				 		//setTimeout(function(){cart.set("errMsg","");},5100);
				 //	}
			 //}, 1000);
			 
		//}
		
		
		
		$(".k-loading-mask").hide();
	});
	
	$( "#backtocc" ).click(function(){
		$(location).prop('href',"childcare");
	});
	$( "#continueshop" ).click(function(){
		$(".k-loading-mask").show();
		cart.set("errMsg","");
		$("#dspErr").hide();
		
		if(validateCheckout()){

			setTimeout(function(){
			 	if(cart.errMsg==""){
			 		$("#familymembers").fadeOut("fast");
					$("#program").fadeIn("fast");
					getProgramSession();
			 	} else {
			 		
			 	}
		 }, 1000);
			
		}
		$(".k-loading-mask").hide();
	});
	
	$( "#backtoprogram" ).click(function(){
		    $(".k-loading-mask").show();
		    $("#tcSuccessSpan").css("display", "none");		
			$("#tcSuccessSpan" ).html("");	
			$("#tcErrorSpan").css("display", "none");		
			$( "#tcErrorSpan" ).html("");
		
			$("#familymembers").fadeOut(200);
			$("#program").fadeIn(100);
			getProgramSession();
	});
	
	var popup_add_common_window = $("#popup_add_common");
	$("#add_member_family_con").bind("click", function() {
		$("#familyContactPageType").val("FAMILY");
		addfamilyMemberCommon();
		popup_add_common_window.data("kendoWindow").center().open();
		$("#add_member_common").hide();
		$('Div[data-role="draggable"]').css('top', '20px');
	});

	var onClose = function() {
		 $("#add_member_common").show();
	}

	if (!popup_add_common_window.data("kendoWindow")) {
		popup_add_common_window.kendoWindow({
	        width: "700px",
	        title : "Add Family Contact",
	        modal : true,
	        close: onClose
	    });
	}

});
</script>