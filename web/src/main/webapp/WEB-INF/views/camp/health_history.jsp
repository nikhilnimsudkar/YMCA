<div id="members" style="width: 780px;; padding: 12px;">
	<div class="k-window1" id="popup_add" style="display:none;"></div>
	<div>
		<span class="head" style="float:left;">Family Health History</span>		
	</div>
	<br>
	<div>
		<span class="head" style="float:left; margin-top: 50px;">Any Fears</span>
		<!-- <div style="float:left; margin-left: 50px;"><span>OR</span></div> -->
		<div style="margin-left: 150px; float: left; margin-top: 50px;"><input name="fears" id="fears" class="k-textbox" style="width: 400px;" value=""></div>
	</div>
	<br>
	<div id="cartbtns" align="center">
		<button id="nextFear" class="k-button" style="margin-top: 10px;">Next</button>
	</div>
	<br>
	<div>
		<span class="head" style="float:left; margin-top: 50px;">Any Allergies</span>
		<!-- <div style="float:left; margin-left: 50px;"><span>OR</span></div> -->
		<div style="margin-left: 130px; float: left; margin-top: 50px;"><input name="allergies" id="allergies" class="k-textbox" style="width: 400px;" value=""></div>
	</div>
	<br>
	<div id="cartbtns" align="center">
		<button id="nextAllergy" class="k-button" style="margin-top: 10px;">Next</button>
	</div>
	<!-- <div style="clear: both;"></div> 
	<div id="family_details" style="margin: 20px 20px 30px;  min-height: 220px;">
		<div id="allmembers"></div>		
	</div>
	
	<div id="cartbtns" align="center">
	<button id="checkoutBtn" class="k-button">Proceed to Checkout</button>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<span>OR</span>
		<button id="backtoprogram" class="k-button">Back to Camp List</button>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<span id="checkoutBtn" class="k-button">View Cart</span>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<button id="continueshop" class="k-button">ADD TO CART & CONTINUE SHOPPING</button>
		
	</div> -->
</div>

<script>
$(document).ready(function(){
	
	$( "#checkoutBtn" ).click(function(){
		$(".k-loading-mask").show();
		cart.set("errMsg","");
		$("#dspErr").hide();
		
		if(validateCheckout()){
			//console.log(cart);
			 setTimeout(function(){
				 	if(cart.errMsg==""){
				 		$("#familymembers").fadeOut(200);
						$("#checkout_content").show();
				 	}
				 	else{
				 		//setTimeout(function(){cart.set("errMsg","");},5100);
				 	}
			 }, 1000);
			 
		}
		$(".k-loading-mask").hide();
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
					getCampSession();
			 	} else {
			 		
			 	}
		 }, 1000);
			
		}
		$(".k-loading-mask").hide();
	});
	
	$( "#backtoprogram" ).click(function(){
		/* $(".k-loading-mask").show();
	    $("#tcSuccessSpan").css("display", "none");		
		$("#tcSuccessSpan" ).html("");	
		$("#tcErrorSpan").css("display", "none");		
		$( "#tcErrorSpan" ).html("");
	
		$("#familymembers").fadeOut(200);
		$("#program").fadeIn(100);
		getCampSession(); */
	$(".k-loading-mask").show();
    $("#tcSuccessSpan").css("display", "none");		
	$("#tcSuccessSpan" ).html("");	
	$("#tcErrorSpan").css("display", "none");		
	$( "#tcErrorSpan" ).html("");

	$("#program").hide();
	$("#purchase").fadeOut();
	$("#payment_cart").hide();
	$("#familymembers").hide();
	$("#add-to-cart").hide();
	$("#healthhistory").fadeIn(100);
	$(".k-loading-mask").hide();
	});
	
	var window = $("#popup_add");
	$("#add_member").bind("click", function() {	
		/*
		window.clone().kendoWindow({
			title : "Add Family",
			content : "addMember",
			modal : false,
			width : "700px",
			close : onClose,
			//deactivate : function(e) {
			//	this.destroy();
			//}
		}).data("kendoWindow").center().open();
		*/
		addfamilyMember();
		window.data("kendoWindow").center().open();
		$("#add_member").hide();
		//$('[data-role="draggable"]').css('top', '20px');
		$('Div[data-role="draggable"]').css('top', '20px');
	});
	
	var onClose = function() {
		 $("#add_member").show();
    }
	
	if (!window.data("kendoWindow")) {
        window.kendoWindow({
            width: "700px",
            title : "Add Family Member",
            modal : true,
            close: onClose
        });
    }
});
</script>