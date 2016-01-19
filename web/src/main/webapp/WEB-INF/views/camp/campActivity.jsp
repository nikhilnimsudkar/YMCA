<div id="members" style="width: 780px;; padding: 12px;">
	<div class="k-window1" id="popup_add" style="display:none;"></div>
	<div>
		<span class="head" style="float:left;">Residential Camp</span>		
	</div>
	<br>
	<div>
		 <table id='camp_activity' class='camp_desc' style='table-layout: auto; width: 200px;'>
			<colgroup>
				<col style='width: 100px' />
				<col style='width: 100px' />
			</colgroup>
			<thead><tr>				  		
				<th data-field='campActivity'>Camp Activity</th>
				<th data-field='campName'>Priority</th>
				</tr></thead> 
			<tbody><tr>
				<td><span class='name'>Horse Riding</span></td>
				<td><span class='name'><input name="Priority" id="Priority" class="k-textbox" style="width: 100px;" value=""></span></td>
				</tr><tr>
				<td><span class='name'>Dance</span></td>
				<td><span class='name'><input name="Priority" id="Priority" class="k-textbox" style="width: 100px;" value=""></span></td>
				</tr><tr>
				<td><span class='name'>Fencing</span></td>
				<td><span class='name'><input name="Priority" id="Priority" class="k-textbox" style="width: 100px;" value=""></span></td>
				</tr><tr>
				<td><span class='name'>Yoga</span></td>
				<td><span class='name'><input name="Priority" id="Priority" class="k-textbox" style="width: 100px;" value=""></span></td>
				</tr>
				</tbody></table>
			
			
		<div style="margin-left: 150px; float: left; margin-top: 50px;"><input name="fears" id="fears" class="k-textbox" style="width: 400px;" value=""></div>
	</div>
	
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