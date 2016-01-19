<div id="members" style="width: 780px;; padding: 12px;">
	<div class="k-window1" id="popup_add" style="display:none;"></div>
	<div>
		<span class="head" style="float:left;">Select Family Contact for Program</span>
		<div style="float:left; margin-left: 50px;"><span>OR</span></div>
		<div style="margin-left: 50px; float: left; margin-top: -5px;"><span  class="k-button" id="add_member" style="float:left;">Add New Family Contact</span></div>
	</div>
	<div style="clear: both;"></div> 
	<div id="family_details" style="margin: 20px 20px 30px;  min-height: 220px;">
		<div id="allmembers"></div>
		
	</div>
	
	<div id="cartbtns" align="right">
		<button id="backtoprogram" class="k-button">Back to Program List</button>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<span id="checkoutBtn" class="k-button">Add to Cart</span>
		<!-- &nbsp;&nbsp;&nbsp;&nbsp;
		<button id="continueshop" class="k-button">ADD TO CART & CONTINUE SHOPPING</button> -->
		
	</div>
</div>

<script>
$(document).ready(function(){
	
	$( "#checkoutBtn" ).click(function(){
		$(".k-loading-mask").show();
		cart.set("errMsg","");
		$("#dspErr").hide();
		
		//if(validateCheckout()){
			//console.log(cart);
			 
			 //setTimeout(function(){
				 	//if(cart.errMsg==""){
				 		// $("#familymembers").fadeOut(200);
				 		addToCart();
				 		//   showContactHealthHistoryOrCheckOut('PROGRAM');
						//$("#checkout_content").show();
				 	//}
				 	//else{
				 		//setTimeout(function(){cart.set("errMsg","");},5100);
				 //	}
			 //}, 1000);
			 
		//}
		
		
		
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
            title : "Add Family Contact",
            modal : true,
            close: onClose
        });
    }
});

$(document).on('click', '#addmemberBtn', function(){							
	var url = "isEmailExists";
	var validator1 = $(document).find("#addMembershipForm").validate({
		debug: true,
		errorElement: "em",			
		errorPlacement: function(error, element) {
			$element = $(element);			
			//console.log($element.hasClass("dateOfBirth"));
			if($element.attr("id") == "dateOfBirth"){
				$element.parent().parent().parent().next("td").html("");
				element.addClass("inputErrorr");
				error.appendTo(element.parent().parent().parent().next("td"));
				element.parent().parent().parent().next("td").addClass("textMsgError");
				$element.parent().parent().parent().next("td").css("color","red");
			}else{
				element.addClass("inputErrorr");
				error.appendTo(element.parent("td").next("td"));
				element.parent("td").next("td").addClass("textMsgError");
				$element.parent("td").next("td").css("color","red");
			}
			//$("#wizard"). smartWizard("fixHeight");
			/* element.addClass("inputErrorr");
			error.appendTo(element.parent("td").next("td"));
			element.parent("td").next("td").addClass("textMsgError"); */
		},
		success: function(label, element) {
			//alert("success");
			//label.text("ok!").addClass("success");
			var $element = $(element);
			var $label = $(label);
	       	$element.removeClass("inputErrorr");
			$element.removeClass("error");
			$element.addClass("success");
			
			$label.removeClass("textMsgError");
			$label.removeClass("error");
			$label.addClass("success");
			//$("#wizard"). smartWizard("fixHeight");
			
		},
		rules: {
			"firstName": {
				required: true,
				minlength: 2,
				maxlength: 20
			},
			"lastName": {
				required: true,
				minlength: 2,
				maxlength: 20
			},
			"dateOfBirth": {
				required: true,
				//check_date_of_birth: true,
			},
			"Email" : {				
				email: true				
			}						
			
		},
		messages: {
			"firstName": {				
				required: "Please enter your First Name",
				minlength: "First Name must consist of at least 2 characters"
			},
			
			"lastName": {				
				required: "Please enter your Last Name",
				minlength: "Last Name must consist of at least 2 characters"
			},			
			"dateOfBirth" : {
		    	  required: "Please enter your Date of Birth",
		    	  //check_date_of_birth : "You must be less than "+ $("#kidsAgeValidation").html() +" years of age, or choose another membership product"
					
		    },
	        "Email" : {				
				email: "Please enter correct email address"				
			}
		}				
	});	
	
	if(!$("#addMembershipForm").valid()) {
		 //console.log("")
		 return false;
	} else {
		//console.log("Validation Success submittting form");
		addcurrentfamilymember();
	}
	
	
});

function addcurrentfamilymember(){
		$(".k-loading-mask").show();
		$(".k-loading-text").html("Please wait while the member is added");
	
	 	$("#tcSuccessSpan").css("display", "none");		
	 	$("#tcSuccessSpan" ).html("");	
	 	$("#tcErrorSpan1").css("display", "none");		
	 	$( "#tcErrorSpan1" ).html("");
  
		//alert($('#addMembershipForm').serialize());
		$.ajax({
			  type: "POST",
			  url: $('#addMembershipForm').attr( "action"),
			  data: $('#addMembershipForm').serialize(),
			  success: function( data ) {
				  var jsonDataArr = jQuery.parseJSON(data);
				  if(jsonDataArr != null && jsonDataArr.length > 0){			  
				  	  if(jsonDataArr[0] != null && jsonDataArr[0].Success != null && jsonDataArr[0].Success =='Success'){
					  	  $("#tcErrorSpan").css("display", "none");		
						  $("#tcErrorSpan" ).html("");	
						  //$("#tcSuccessSpan").css("display", "block");		
						  //$("#tcSuccessSpan" ).html("Profile Information Updated successfully.");
						  setTimeout(function(){$(".k-loading-text").fadeOut("slow");}, 100);
						  setTimeout(function(){$(".k-loading-text").html("Member was added successfully");$(".k-loading-text").fadeIn("slow");}, 300);
					  	  setTimeout(function(){$(".k-loading-text").fadeOut("slow");}, 500);
					  	  //setTimeout(function(){location.href='view_membership';}, 7000);
					  	  //setTimeout(function(){location.reload();}, 7000);
					  	  getFamilymembers();
					  	  closeAllwindow();
					  	  $(".k-loading-mask").hide();
					  	//location.href='view_membership';
					  	  
				  	  }
				  	 else if(jsonDataArr[0] != null && jsonDataArr[0].Duplicate != null && jsonDataArr[0].Duplicate=='Duplicate'){
				  		  $("#tcSuccessSpan1").css("display", "none");		
						  $("#tcSuccessSpan1" ).html("");	
						  $("#tcErrorSpan1").css("display", "block");		
						  $( "#tcErrorSpan1" ).html("User Information already exists!");
						  $(".k-loading-mask").hide();
				  	  }
				  	  else{
				  		  $("#tcSuccessSpan1").css("display", "none");		
						  $("#tcSuccessSpan1" ).html("");	
						  $("#tcErrorSpan1").css("display", "block");		
						  $( "#tcErrorSpan1" ).html("There was some error. Please try again later");
						  $(".k-loading-mask").hide();
				  	  }
				  }else{					  
					  $("#tcSuccessSpan1").css("display", "none");		
					  $("#tcSuccessSpan1" ).html("");	
					  $("#tcErrorSpan1").css("display", "block");		
					  $( "#tcErrorSpan1" ).html("There was some error. Please try again later");
					  $(".k-loading-mask").hide();
				  }
				  //$(".k-loading-mask").hide();
			  },
			  error: function( xhr,status,error ){
				  //alert("1" +status);
				  console.log(xhr);
				  $("#tcSuccessSpan1").css("display", "none");		
				  $("#tcSuccessSpan1" ).html("");	
				  $("#tcErrorSpan1").css("display", "block");		
				  $( "#tcErrorSpan1" ).html("There was some error. Please try again later");
				  $(".k-loading-mask").hide();
			  }
		});
}
</script>