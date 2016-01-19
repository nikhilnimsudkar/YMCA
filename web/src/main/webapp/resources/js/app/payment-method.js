
/* functions related to payment method */

var hash = '';
var merchantId = "YMCA07021001";  
var transactionAmount = "0.00";
var JetDirectToken	= "9zmXvRRoAiCAlrmQfpj2hNavUGb56F28jQYtxOItdEsWkYWglSmffVtgues91o3emutnq8rU";
var paymentOrderId = Math.floor((Math.random()*1000000000000)+1);

function updatePaymentMethodDDAction(){
	$("#statusBlock_card").hide();
  	$("#tcErrorSpan_card").html("");
  	$("#tcErrorSpan_card").hide();
  	$("#tcSuccessSpan_card").html("");
  	$("#tcSuccessSpan_card").hide();
  	
  	$("#statusBlock_bank").hide();
  	$("#tcErrorSpan_bank").html("");
  	$("#tcErrorSpan_bank").hide();
  	$("#tcSuccessSpan_bank").html("");
  	$("#tcSuccessSpan_bank").hide();
  	
  	$("#statusBlock_update_bank").hide();
  	$("#tcErrorSpan_update_bank").html("");
  	$("#tcErrorSpan_update_bank").hide();
  	$("#tcSuccessSpan_update_bank").html("");
  	$("#tcSuccessSpan_update_bank").hide();
  	
  	$("#tcSuccessSpan_update_bank").hide();
  	
  	$('#remove_card_payment_method_signup_list').hide();
  	$('#remove_bank_payment_method_signup_list').hide();
  	
 	console.log(" -->  "+$("#paymentMethodSelect").val());
 
	if($("#paymentMethodSelect").val()=='PaymentMethod_Card'){
		
		$("#fullName").attr("value", "").attr("disabled", false).removeClass("k-state-disabled");
  		$("#cardNum").attr("value", "").attr("disabled", false).removeClass("k-state-disabled");
  		
  		$("#cardimage").show();
  		var currentYear = new Date().getFullYear();
  		$("#addCardExpirationMonthSelect").attr("value", "01").attr("disabled", false).removeClass("k-state-disabled");
  		$("#addCardExpirationYearSelect").attr("value", parseInt(currentYear)).attr("disabled", false).removeClass("k-state-disabled");
  		
  		$("#securityCode").attr("value", "").attr("disabled", false).removeClass("k-state-disabled");
  		
  		$("#questionMarkImgID").show();
  		
  		$("#billingAddressLine1").attr("value", "");
  		$("#billingAddressLine2").attr("value", "");
  		
  		$("#billingCity").attr("value", "");
  		$("#billingState").attr("value", "");
  		
  		$("#billingZip").attr("value", "");
  		$("#nickName").attr("value", "").attr("disabled", false).removeClass("k-state-disabled");
		
		$('#addBank').hide();	
		$('#addcard').show();
		$('#addCardButton').text("Add");
		$('#removeCardInfoButtonSpan').hide();
		$(".stepContainer").height($(".stepContainer").find("div:not(:hidden)").height());
		$("#SaveTypeID").attr("value", "ADD_CARD");
		$('#SecurityCodeDivID').show();
		$('#CardAddressDivID').show();
		$('#CardAddressTextDivID').hide();
		
		var currentYear = new Date().getFullYear();
		var addCardExpirationMonthSelectList = $("#addCardExpirationMonthSelect").data("kendoDropDownList");
  		addCardExpirationMonthSelectList.value("01");
  		var addCardExpirationYearSelectList = $("#addCardExpirationYearSelect").data("kendoDropDownList");
  		addCardExpirationYearSelectList.value(parseInt(currentYear));
		
	}else if($("#paymentMethodSelect").val()=='PaymentMethod_Bank'){
		$('#addcard').hide();
		$('#addBank').show();			
		$(".stepContainer").height($(".stepContainer").find("div:not(:hidden)").height());
		$("#SaveTypeID").attr("value", "ADD_BANK");
	} else{
		$("#SaveTypeID").attr("value", "UPDATE_CARD_OR_BANK");
		$('#addcard').hide();
		$('#addBank').hide();
		$(".stepContainer").height($(".stepContainer").find("div:not(:hidden)").height());
		
		var paymentType_Token = $("#paymentMethodSelect").val();
		
		if(paymentType_Token){
			paymentType_TokenSplit = paymentType_Token.split("__");
			paymentTypeSelected = paymentType_TokenSplit[0];
			tokenSelected = paymentType_TokenSplit[1];
		}
		
		console.log("  paymentTypeSelected "+paymentTypeSelected);
		console.log("  tokenSelected "+tokenSelected);
		
		if(paymentTypeSelected && paymentTypeSelected == 'CREDIT'){
			$("#paymentTokenIdSpan").html(tokenSelected);
			$("#paymentMethodIdHidInput").attr("value", tokenSelected); 
			$('#addCardButton').text("Update");
			$('#removeCardInfoButtonSpan').show();
			$('#SecurityCodeDivID').hide();
			$('#CardAddressDivID').hide();
			$('#CardAddressTextDivID').show();
			console.log(" paymentMethodSelect >>  "+tokenSelected);
			
			console.log("  on change .... ");
			
			$.ajax({
				  type: "GET",
				  url: "getPaymentMethodByToken?token="+tokenSelected,
				  dataType: "json",
				  success: function( data ) {
					  console.log(" got ");
				  	  if(data){
				  		
				  		  	console.log("  data "+data);
							console.log(" card :: "+data.cardNumber);
							console.log(" expirationMonth ::  "+data.expirationMonth);
							console.log(" expirationYear ::  "+data.expirationYear);
					  	 
					  		$('#addcard').show();
					  		
					  		$("#fullName").attr("value", data.fullName).attr("disabled", true).addClass("k-state-disabled");
					  		$("#cardNum").attr("width", "130px");
					  		$("#cardNum").attr("value", data.cardNumber).attr("disabled", true).addClass("k-state-disabled");
					  		
					  		$("#cardimage").hide();
					  		
					  		var addCardExpirationMonthSelectList = $("#addCardExpirationMonthSelect").data("kendoDropDownList");
					  		addCardExpirationMonthSelectList.value(data.expirationMonth);
					  		var addCardExpirationYearSelectList = $("#addCardExpirationYearSelect").data("kendoDropDownList");
					  		addCardExpirationYearSelectList.value(data.expirationYear);
					  		
					  		//$("#securityCode").attr("value", data.securityCode).attr("disabled", "disabled").addClass("k-state-disabled");
					  		//$("#securityCode").attr("value", "XXXX").attr("disabled", "disabled").addClass("k-state-disabled");
					  		
					  		$("#questionMarkImgID").hide();
					  		
					  		$("#billingAddressLine1").attr("value", data.billingAddressLine1);
					  		$("#billingAddressLine2").attr("value", data.billingAddressLine2);
					  		$("#billingCity").attr("value", data.billingCity);
					  		$("#billingState").attr("value", data.billingState);
					  		$("#billingZip").attr("value", data.billingZip);
					  		$("#nickName").attr("value", data.nickName);
					  		
					  		
					  		var billingAddressText = "";
					  		if(data.billingAddressLine1 && data.billingAddressLine1 != ''){
					  			billingAddressText += data.billingAddressLine1 + ', ';
					  		}
					  		if(data.billingAddressLine2 && data.billingAddressLine2 != ''){
					  			billingAddressText += data.billingAddressLine2 + ', ';
					  		}
					  		if(data.billingCity && data.billingCity != ''){
					  			billingAddressText += data.billingCity + ', ';
					  		}
					  		if(data.billingState && data.billingState != ''){
					  			billingAddressText += data.billingState + ', ';
					  		}
					  		if(data.billingZip && data.billingZip != ''){
					  			billingAddressText += data.billingZip;
					  		}
					  		
					  		//billingAddressText.
					  		
					  		$("#BillingAddressSpanID").html(""+billingAddressText);
					  		
				  	  }else {
				  		  /* $("#tcSuccessSpan").css("display", "none");		
						  $("#tcSuccessSpan" ).html("");	
						  $("#tcErrorSpan").css("display", "block");		
						  $( "#tcErrorSpan" ).html("There was some error. Please try again later");
						  $(".k-loading-mask").hide(); */
				  		  showCardErrorMessage("There was some error. Please try again later.");
						 console.log(" error while get data. ");
				  	  }
				  },
				  error: function( xhr,status,error ){
					  console.log("  Status   "+status);
					  console.log("  error   "+error);
					  console.log("  xhr   "+xhr.responseText);
					  showCardErrorMessage("There was some error. Please try again later.");
					  /* $("#tcSuccessSpan").css("display", "none");		
					  $("#tcSuccessSpan" ).html("");	
					  $("#tcErrorSpan").css("display", "block");		
					  $( "#tcErrorSpan" ).html("There was some error. Please try again later");
					  $(".k-loading-mask").hide(); */
					  console.log(" There was some error. Please try again later ");
				  }
			});
		}else if(paymentTypeSelected && paymentTypeSelected == 'ACH'){
			if(tokenSelected){
				
				$.ajax({
					  type: "GET",
					  url: "getPaymentMethodByToken?token="+tokenSelected,
					  dataType: "json",
					  success: function( data ) {
						  console.log(" got ");
					  	  if(data){
					  		  	console.log("  data "+data);
								console.log(" card :: "+data.cardNumber);
								console.log(" portalStatus ::  "+data.portalStatus);
								
								if(data.portalStatus != null){
									console.log(" portalStatus  "+data.portalStatus);
									$("#statusBlock_update_bank").show();
									//$("#tcSuccessSpan_update_bank").show();
									//$("#tcErrorSpan_update_bank").hide();
									
									if(data.portalStatus == 'VP'){
										console.log(" set html ");
										//$("#tcSuccessSpan_update_bank").html("Cannot update Bank Information."); // Selected payment method has Validation Pending status.
									}else if(data.portalStatus == 'ACTIVE'){
										//$("#tcSuccessSpan_update_bank").html("Cannot update Bank Information.");
										showBankUpdateSuccessMessage("Cannot update Bank Information.");
									}
								}
					  	  }else {
					  		$("#statusBlock_update_bank").show();
							$("#tcSuccessSpan_update_bank").hide();
							$("#tcErrorSpan_update_bank").show();
							$("#tcSuccessSpan_update_bank").html("There was some error. Please try again later.");
							 console.log(" error while get data. ");
					  	  }
					  },
					  error: function( xhr,status,error ){
						  console.log("  Status   "+status);
						  console.log("  error   "+error);
						  console.log("  xhr   "+xhr.responseText);
						  $("#statusBlock_update_bank").show();
						  $("#tcSuccessSpan_update_bank").hide();
						  $("#tcErrorSpan_update_bank").show();
						  $("#tcSuccessSpan_update_bank").html("There was some error. Please try again later.");
						  console.log(" There was some error. Please try again later ");
					  }
				});
			}
		}
	}
}

function validateAndProcessPaymentMethod(transSubType){
	console.log("  transSubType >>  "+transSubType);
	
	if(transSubType){
		if(transSubType == 'ADD_CARD'){
			
			console.log(" \n fullName >>>>  "+$("#fullName").val());
			console.log(" cardNum >>  "+$("#cardNum").val());
			console.log(" securityCode >>  "+$("#securityCode").val());
			console.log(" billingAddressLine1 >>  "+$("#billingAddressLine1").val());
			console.log(" billingAddressLine2 >>  "+$("#billingAddressLine2").val());
			console.log(" billingCity >>  "+$("#billingCity").val());
			console.log(" billingState >>  "+$("#billingState").val());
			console.log(" billingZip ==>>  "+$("#billingZip").val());
			console.log(" expirationMonth ===>>>  "+$("#addCardExpirationMonthSelect").val());
			console.log(" addCardExpirationYear ===>>>  "+$("#addCardExpirationYearSelect").val());
			
			addCardPaymentMethod();
			
		}else if(transSubType == 'UPDATE_CARD_OR_BANK'){
			
			console.log(" \n fullName >>  "+$("#fullName").val());
			console.log(" cardNum >>  "+$("#cardNum").val());
			console.log(" securityCode >>  "+$("#securityCode").val());
			console.log(" billingAddressLine1 >>  "+$("#billingAddressLine1").val());
			console.log(" billingAddressLine2 >>  "+$("#billingAddressLine2").val());
			console.log(" billingCity >>  "+$("#billingCity").val());
			console.log(" billingState >>  "+$("#billingState").val());
			console.log(" billingZip ==>>  "+$("#billingZip").val());
			console.log(" expirationMonth ===>>>>  "+$("#addCardExpirationMonthSelect").val());
			console.log(" addCardExpirationYear ===>>  "+$("#addCardExpirationYearSelect").val());
			
			updateCardPaymentMethodByToken();
			
		}else if(transSubType == 'ADD_BANK'){
			
			console.log(" ADD BANK INFO observed .. ");
			
			addBankInfoPaymentMethodByTokenize();
		}
	}
	// $(".k-loading-mask").hide();
}

function addCardPaymentMethod(){
	
	// Basic validations
	if($("#fullName").val() == ""){
		showCardErrorMessage("Please enter Name on Card.");
		return;
	}
	
	if($("#cardNum").val() == ""){
		showCardErrorMessage("Please enter Card Number.");
		return;
	}
	
	if($("#cardNum").val().length < 12){
		showCardErrorMessage("Card Number should have at least 12 digits.");
		return;
	}
	
	if($("#securityCode").val() == ""){
		showCardErrorMessage("Please enter Security Code.");
		return;
	}
	
	if($("#billingAddressLine1").val() == ""){
		showCardErrorMessage("Please enter Address Line 1.");
		return;
	}
	
	/*if($("#billingAddressLine2").val() == ""){
		showCardErrorMessage("Please enter Address Line 2.");
		return;
	}*/
	
	if($("#billingCity").val() == ""){
		showCardErrorMessage("Please enter City.");
		return;
	}
	
	if($("#billingState").val() == ""){
		showCardErrorMessage("Please select State.");
		return;
	}
	
	if($("#billingZip").val() == ""){
		showCardErrorMessage("Please enter Zip.");
		return;
	}
	
	if($("#nickName").val() == ""){
		showCardErrorMessage("Please enter Nick Name.");
		return;
	}
	
	$(".k-loading-mask").show();
	$(".k-loading-text").html("Please wait while request is submitted.");
	
	$.ajax({
		async:false,
		type: "GET",
		dataType: "json",
		url: "isCardAlreadyExist?accountID="+$("#AccountID").val()+"&cardNumber="+$("#cardNum").val()+"&expMonth="+$("#addCardExpirationMonthSelect").val()+"&expYear="+$("#addCardExpirationYearSelect").val(),
		success: function( data ) {
		  console.log(" got data ::  ");
  		  
  		  if(data){
	  		  	console.log(" got data ::  >> "+data);
	  		    console.log(" got data paymentId ::  >> "+data.paymentId);
				if(data.paymentId && data.paymentId > 0){
				   	console.log("Card Information already exist or inactivated. To activate please contact Y Agent.");
					showCardErrorMessage("Card Information already exist or inactivated. To activate please contact Y Agent.");
					$("#addcard").show();
					showSelectPaymentMethod();
					$(".k-loading-mask").hide();
					return;
				}else{
					transactionAmount = "0.00"; // To add payment method, keep the amount 0
					var jsSha = new jsSHA(merchantId+transactionAmount+JetDirectToken+paymentOrderId);
					hash = jsSha.getHash("SHA-512", "HEX");	
					console.log(" hash >>>>>  "+hash);
					
					var jsonData = {
						trans_type : "TOKENIZE", // "TOKENIZE" // "AUTHONLY", "SALE"
						ud1 : $("#AccountID").val(),
						ud2 : $("#nickName").val(),
						name : $("#fullName").val(),
						cardNum : $("#cardNum").val(),
						cscNumber : $("#securityCode").val(),
						expMonth : $("#addCardExpirationMonthSelect").val(),
						expYr : $("#addCardExpirationYearSelect").val(),
						amount : transactionAmount,
						AddressLine1 : $("#billingAddressLine1").val(),
						AddressLine2 : $("#billingAddressLine2").val(),
						City : $("#billingCity").val(),
						state : $("#billingState").val(),
						zipcode : $("#billingZip").val(),
						email : "test@gmail.com",
						contry : "USA",
						jetPayHash : hash.toString(),
						paymentOrderId : paymentOrderId.toString()
					};
					var win = document.getElementById("childIframeId").contentWindow;
					console.log(" win ===>>>>>>>>> "+win);
					win.postMessage(jsonData, '*');
					console.log(" Payment request sent. ");
					
					$.ajax({
						type: "POST",
						url: "checkPaymentMethodStatus",
						data: { "orderNumber" : paymentOrderId.toString() },
						success: function( data ) {
						  console.log(" got data :: 11 ");
				  		  
				  		  if(data){
					  		  	console.log(" got data ::  >> "+data);
							    data_split_add_cart = data.split("__");
								if(data_split_add_cart[0]=='SUCCESS'){
									$("#addcard").hide();
									hideSelectPaymentMethod();
						  		    $("#statusBlock11").show();
									console.log(" success status : "+data_split_add_cart[1]);
									$("#tcSuccessSpan11").show();
								   	$("#tcSuccessSpan11").html("Card Information added successfully. "+data_split_add_cart[1]);
								   	$("#tcErrorSpan11").hide();
								}else{
									console.log(" failed status : "+data_split_add_cart[1]);
								   	showCardErrorMessage("Error occured while processing payment method. \n"+data_split_add_cart[1]);
									$("#addcard").show();
									showSelectPaymentMethod();
									$(".k-loading-mask").hide();
								}
					  	  }else{
					  		  showCardErrorMessage("There was some error. Please try again later.");
							  $("#addcard").show();
							  showSelectPaymentMethod();
							  $(".k-loading-mask").hide();
					  	  }
				  		  $(".k-loading-mask").hide();
					  },
					  error: function( xhr,status,error ){
						  showCardErrorMessage("There was some error. Please try again later.");
						  $("#addcard").show();
						  showSelectPaymentMethod();
						  $(".k-loading-mask").hide();
					  }
					});
				}
	  	  }else{
	  		  showCardErrorMessage("There was some error. Please try again later.");
			  $("#addcard").show();
			  showSelectPaymentMethod();
			  $(".k-loading-mask").hide();
			  return;
	  	  }
  		 //  $(".k-loading-mask").hide();
	  },
	  error: function( xhr,status,error ){
		  $("#addcard").show();
		  showSelectPaymentMethod();
		  showCardErrorMessage("There was some error. Please try again later.");
		  $(".k-loading-mask").hide();
		  return;
	  }
	});
}

function updateCardPaymentMethodByToken(){
	
	/*if($("#billingAddressLine1").val() == ""){
		showCardErrorMessage("Address Line 1 is required field.");
		return;
	}
	
	if($("#billingAddressLine2").val() == ""){
		showCardErrorMessage("Address Line 2 is required field.");
		return;
	}
	
	if($("#billingCity").val() == ""){
		showCardErrorMessage("City is required field.");
		return;
	}
	
	if($("#billingState").val() == ""){
		showCardErrorMessage("Please select State.");
		return;
	}
	
	if($("#billingZip").val() == ""){
		showCardErrorMessage("Zip is required field.");
		return;
	}*/
	
	if($("#nickName").val() == ""){
		showCardErrorMessage("Nick Name is required field.");
		return;
	}
	
	$(".k-loading-mask").show();
	$(".k-loading-text").html("Please wait while request is submitted.");
	console.log("  > "+$("#paymentMethodSelect").val());
	
	var paymentType_Token = $("#paymentMethodSelect").val();
	if(paymentType_Token){
		paymentType_TokenSplit = paymentType_Token.split("__");
		paymentTypeSelected = paymentType_TokenSplit[0];
		tokenSelected = paymentType_TokenSplit[1];
	}
	
	console.log(" tokenSelected :: "+tokenSelected);
	console.log(" tokenSelected :: "+tokenSelected);
	
	$.ajax({
		async:false,
		url: "updateCardPaymentMethodByToken",
		type: "POST",
		data: { 
			token:tokenSelected, cardExpMonth:$("#addCardExpirationMonthSelect").val(), cardExpYear:$("#addCardExpirationYearSelect").val(),
			nickName:$("#nickName").val()/*, addressLine1:$("#billingAddressLine1").val(), addressLine2:$("#billingAddressLine2").val(),
			billingCity:$("#billingCity").val(), billingState:$("#billingState").val(), billingZip:$("#billingZip").val()*/
		},
		dataType: "text",
		success: function( data ) {
			setTimeout(function(){
				console.log(" got data  "+data);
				if(data){
					data_split_update_card = data.split("__");
				  	if(data_split_update_card[0]=='SUCCESS'){
				  		$("#statusBlock11").show();
				  		$("#addcard").hide();
				  		hideSelectPaymentMethod();
				  		console.log(" success status : "+data_split_update_card[1]);
						$("#tcSuccessSpan11").show();
					   	$("#tcSuccessSpan11").html("Card Information updated successfully. "+data_split_update_card[1]);
					   	$("#tcErrorSpan11").hide();
				  	}else {
				  		console.log(" failed status :>>>>> "+data_split_update_card[1]);
				  		
				  		if(data_split_update_card[1]){
			  				errMsg = data_split_update_card[1];
				  		}else{
				  			errMsg="";
				  		}
				  		
				  		$("#addcard").show();
				  		showSelectPaymentMethod();
				  		$("#BackToPaymentInfoDivID").hide();
						showCardErrorMessage("Error occured while processing payment method. \n"+errMsg);
					}
				}else{
					 $("#addcard").show();
					 showSelectPaymentMethod();
					 showCardErrorMessage("There was some error. Please try again later.");
				}
				  	$(".k-loading-mask").hide();
			}, 5000);
		 },
		 error: function( xhr, status, error ) {
			 setTimeout(function(){
				 $("#addcard").show();
				 showSelectPaymentMethod();
				 showCardErrorMessage("There was some error. Please try again later.");
				 $(".k-loading-mask").hide();
				 console.log(" status "+status);
				 console.log(" error "+error);
				 console.log(" xhr ==>  "+xhr.responseText);
			}, 5000);
		 }
	});
}

function addBankInfoPaymentMethodByTokenize(){
		
	console.log(" bankAccountTypeSelect >>  "+$("#bankAccountTypeSelect").val());
	console.log(" bankAccountNumber >>  "+$("#bankAccountNumber").val());
	console.log(" bankRoutingNumber >>  "+$("#bankRoutingNumber").val());
	console.log(" bankAccountName >>  "+$("#bankAccountName").val());
	console.log(" checkNumber >>  "+$("#checkNumber").val());

	if($("#bankAccountNumber").val() == ""){
		showBankErrorMessage("Please enter Bank Account Number.");
		return;
	}
	
	if($("#bankAccountNumber").val().length < 4){
		showBankErrorMessage("Bank Account Number should have at least 4 digits.");
		return;
	}
	
	if($("#bankRoutingNumber").val() == ""){
		showBankErrorMessage("Please enter Bank Routing Number.");
		return;
	}
	
	if($("#checkNumber").val() == ""){
		showBankErrorMessage("Please enter Check Number.");
		return;
	}
	
	if($("#bankAccountName").val() == ""){
		showBankErrorMessage("Please enter Account Name.");
		return;
	}
	
	if($("#bankInfoNickName").val() == ""){
		showBankErrorMessage("Please enter Nick Name.");
		return;
	}
	
	console.log(" basic validation success ");
	
	$(".k-loading-mask").show();
	$(".k-loading-text").html("Please wait while request is submitted.");
	// console.log("  > "+$("#paymentMethodSelect").val());
	console.log(" AccountID :: "+$("#AccountID").val());
	
	//var checkNUmber = $("#checkNumber").val();
	
	/*if(!checkNumber){
		checkNumber = "";
	}*/
	
	$.ajax({
		async:false,
		type: "GET",
		dataType: "json",
		url: "isBankInfoAlreadyExist?accountID="+$("#AccountID").val()+"&bankAccountNumber="+$("#bankAccountNumber").val(),
		success: function( data ) {
		  console.log(" got data ::  ");
  		  
  		  if(data){
	  		  	console.log(" got data ::  >> "+data);
	  		    console.log(" got data paymentId ::  >> "+data.paymentId);
				if(data.paymentId && data.paymentId > 0){
				   	console.log("Bank Information already exist or inactivated. To activate please contact Y Agent. ::  "+data.paymentId);
					$("#addcard").hide();
					showSelectPaymentMethod();
					showBankErrorMessage("Bank Information already exist or inactivated. To activate please contact Y Agent.");
					$(".k-loading-mask").hide();
					return;
				}else{
					$.ajax({
						async:false,
						url: "addBankInfoByTokenize",
						type: "POST",
						data: { 
							accountID: $("#AccountID").val(), accountType:$("#bankAccountTypeSelect").val(), accountName:$("#bankAccountName").val(),
							accountNumber:$("#bankAccountNumber").val(), routingNumber:$("#bankRoutingNumber").val(), checkNumber:$("#checkNumber").val(), nickName:$("#bankInfoNickName").val()
						},
						dataType: "text",
						success: function( data ) {
							setTimeout(function(){
								console.log(" got response ");
								if(data){
									console.log(" got data "+data);
									data_split_add_bank = data.split("__");
								  	if(data_split_add_bank[0]=='SUCCESS'){
								  		console.log(" SUCCESS ");
								  		$("#statusBlock11").show();
								  		$("#addBank").hide();
								  		hideSelectPaymentMethod();
								  		console.log(" success status : "+data_split_add_bank[1]);
										$("#tcSuccessSpan11").show();
									   	$("#tcSuccessSpan11").html("Bank information added successfully. "+data_split_add_bank[1]);
									   	$("#tcErrorSpan11").hide();
									   	$(".k-loading-mask").hide();
								  	}else {
								  		console.log(" FAILED ");
								  		console.log(" failed status :>>>>> "+data_split_add_bank[1]);
								  		
								  		if(data_split_add_bank[1]){
							  				errMsg = data_split_add_bank[1];
								  		}else{
								  			errMsg = "";
								  		}
								  		
								  		$("#addBank").show();
								  		showSelectPaymentMethod();
								  		$("#BackToPaymentInfoDivID").hide();
										showBankErrorMessage("Error occured while processing payment method. \n"+errMsg);
										$(".k-loading-mask").hide();
									}
								}else{
									console.log(" FAILED : NULL RESPONSE ");
									 $("#addBank").show();
									 showSelectPaymentMethod();
									 showBankErrorMessage("There was some error. Please try again later.");
									 $(".k-loading-mask").hide();
								}
								  	
							}, 5000);
						 },
						 error: function( xhr, status, error ) {
							 setTimeout(function(){
								 console.log(" FAILED : There was some error ");
								 $("#addBank").show();
								 showSelectPaymentMethod();
								 showBankErrorMessage("There was some error. Please try again later.");
								 $(".k-loading-mask").hide();
								 console.log(" status "+status);
								 console.log(" error "+error);
								 console.log(" xhr ==>  "+xhr.responseText);
							}, 5000);
						 }
					});
				}
			  }else{
				  $("#addBank").show();
				  showSelectPaymentMethod();
				  showBankErrorMessage("There was some error. Please try again later.");
				  $(".k-loading-mask").hide();
				  return;
		  	  }
	  		  // $(".k-loading-mask").hide();
		  },
		  error: function( xhr,status,error ){
			  $("#addBank").show();
			  showSelectPaymentMethod();
			  showBankErrorMessage("There was some error. Please try again later.");
			  $(".k-loading-mask").hide();
			  return;
		  }
	});
}

function removePaymentMethod(){
	
	console.log(" removePaymentMethod observed ");

	var kendoWindow = $("<div />").kendoWindow({
		title : "Confirm",
		resizable : false,
		modal : true,
		width : 400
	});
	
	kendoWindow.data("kendoWindow").content($("#confirmDialogBox").html()).center().open();

	kendoWindow.find(".delete-confirm,.delete-cancel").click(
		function() {
			if ($(this).hasClass("delete-confirm")) {
				checkForSignupAndRemovePaymentMethod("", 'Y');
			}

			kendoWindow.data("kendoWindow").close();
		}).end();
}

function checkForSignupAndRemovePaymentMethod(msg, isDelete){
	
	var paymentType_Token = $("#paymentMethodSelect").val();
	if(paymentType_Token){
		paymentType_TokenSplit = paymentType_Token.split("__");
		paymentTypeSelected = paymentType_TokenSplit[0];
		tokenSelected = paymentType_TokenSplit[1];
	}
	console.log(" paymentTypeSelected :: "+paymentTypeSelected);
	console.log(" tokenSelected :: "+tokenSelected);
	
	$(".SignupRowClass").remove();
	$.ajax({
		  type: "POST",
		 url: "removePaymentMethod?accountID="+$("#AccountID").val()+"&token="+tokenSelected+"&isDelete="+isDelete,
		  dataType: "json",
		  success: function( data ) {
			  console.log(" got ");
		  	  if(data){
		  		
		  		  	console.log(" data ::  "+data);
		  			
		  			if(data.length>0){
		  				console.log("Cannot remove payment method, beacause it has open balance for following signups."+data.length);
		  				console.log("  AccountID   "+$("#AccountID").val());
		  				$.ajax({
							  type: "GET",
							  url: "getPaymentMethodOptionsForAccount?accountID="+$("#AccountID").val(),
							  dataType: "json",
							  success: function(paymentMethodOptions) {
								  console.log(" got "+paymentMethodOptions);
								  //console.log(" got "+paymentMethodOptions.length);
								 // console.log(" got "+paymentMethodOptions.size());
								  console.log(" got "+Object.keys(paymentMethodOptions).length);
								  
								  var paymentMethodCount = Object.keys(paymentMethodOptions).length;
								  //if(paymentMethodOptions && paymentMethodOptions.length && paymentMethodOptions.length > 1){
										  
									  var signupList = '';
									  
									  $.each(data, function(i,signup) {
										  
										  signupList += signup.SignupId;
										  signupList += '_';
										  
										  var spaceHere = $('<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>');
										  
					  						//var stDt = new Date(signup.SignupDate)
						  					console.log(" SignupId "+signup.SignupId);
						  					console.log(" SignupName "+signup.SignupName);
						  					console.log(" SignupDate "+signup.SignupDate);
						  					//console.log(" SignupDate format "+formatDate(stDt));
						  					
						  					if(paymentTypeSelected && paymentTypeSelected == 'CREDIT'){
						  						$('#remove_card_payment_method_signup_list').show();
						  						$('#updateCardInfoButtonSpan').hide();
						  					}else{
						  						$('#remove_bank_payment_method_signup_list').show();
						  						$("#statusBlock_update_bank").show();
						  					}
						  				  	
						  					hideBankUpdateMessage();
						  				  	hideBankMessage();
						  				  	hideCardMessage();
						  					
						  					// $('#removeSignUpRows').innerHTML = "<tr style='height: 20px;'><td>sdfghkj</td></tr>";
						  				  	if(paymentTypeSelected && paymentTypeSelected == 'CREDIT'){
						  				  		$('#remove_card_payment_method_signup_list_table tr').last().after('<tr style="height: 30px;" class="SignupRowClass"><td style="padding-left:4px;height: 30px;">'+signup.SignupName+'</td><td style="padding-bottom:3px;height: 30px;">'+signup.SignupDate+'</td><td valign="middle" style="padding-bottom:3px; height: 30px;" id="signupID'+signup.SignupId+'" nowrap width="200px"></td></tr>');
						  				  	}else{
						  				  		$('#remove_bank_payment_method_signup_list_table tr').last().after('<tr style="height: 30px;" class="SignupRowClass"><td style="padding-left:4px;height: 30px;">'+signup.SignupName+'</td><td style="padding-bottom:3px;height: 30px;">'+signup.SignupDate+'</td><td valign="middle" style="padding-bottom:3px; height: 30px;" id="signupID'+signup.SignupId+'" nowrap width="200px"></td></tr>');
						  				  	}
						  					var $button = $('<button/>', {
												  type: 'button',
												  'class': 'k-button',
												  id: ''+signup.SignupId,
												  text: 'Update',
												  style: 'background-color: #eb8120;color: #ffffff;background-image:none;font-weight:bold;',
												  click: function() {
													  console.log('Hello! My id is '+ this.id);
													  console.log('Hello! My val is '+ $("#select"+this.id+"").val());
													  
													  $.ajax({
														  type: "POST",
														  url: "updatePaymentMethodForSignup?token="+$("#select"+this.id+"").val()+"&signupid="+this.id+"&accountID="+$("#AccountID").val(),
														  dataType: "json",
														  success: function(updatePaymentMethodResult) {
															  console.log("  Success :: updatePaymentMethodResult.RESULT "+updatePaymentMethodResult.RESULT);
															  
															  if(paymentTypeSelected && paymentTypeSelected == 'CREDIT'){
																  $("#addcard").show();
																  //showCardSuccessMessage("Payment method reassigned successfully.");
																  checkForSignupAndRemovePaymentMethod("Payment method reassigned successfully.", 'N');
															  }else{
																  $("#addBank").hide();
																  console.log(" Success "+updatePaymentMethodResult.RESULT);
																  if(updatePaymentMethodResult.RESULT){
																	  if(updatePaymentMethodResult.RESULT == 'SUCCESS'){
																		  //showBankUpdateSuccessMessage("Payment method reassigned successfully.");
																		  checkForSignupAndRemovePaymentMethod("Payment method reassigned successfully.", 'N');
																	  }
																	  
																	  showSelectPaymentMethod();
																	  
																  }else{
																	  console.log("  Failed :: updatePaymentMethodResult.RESULT "+updatePaymentMethodResult.RESULT);
																	  if(paymentTypeSelected && paymentTypeSelected == 'CREDIT'){
																		  showCardErrorMessage("There was some error. Please try again later.");
																	  }else{
																		  showBankErrorMessage("There was some error. Please try again later.");
																	  }
																  }
															  }
														  },
									  					  error: function( xhr,status,error ){
															  console.log("  Status   "+status);
															  console.log("  error   "+error);
															  console.log("  xhr   "+xhr.responseText);
															  if(paymentTypeSelected && paymentTypeSelected == 'CREDIT'){
																  showCardErrorMessage("There was some error. Please try again later.");
															  }else{
																  showBankErrorMessage("There was some error. Please try again later.");
															  }
															  console.log(" There was some error. Please try again later ");
														  }
													});
												  }
						  					});
						  					var s = $('<select class="k-dropdown" style="font-weight: normal; font-size: x-small;" id="select'+signup.SignupId+'" />');
						  					for(var val in paymentMethodOptions){
												  console.log('val  >> '+val);
												  if(tokenSelected != val){
													  $('<option />', {value: val, text: paymentMethodOptions[val]}).appendTo(s);
												  }
											}
						  					console.log(" paymentMethodOptions.length "+paymentMethodOptions.length);
						  					console.log(" signupList.length "+signupList.length);
						  					if(paymentMethodCount > 1 && signupList.length > 0){
							  					s.appendTo($('#signupID'+signup.SignupId));
							  					spaceHere.appendTo($('#signupID'+signup.SignupId));
							  					$button.appendTo($('#signupID'+signup.SignupId));
							  					if(paymentTypeSelected && paymentTypeSelected == 'CREDIT'){
													  $("#CardSignupMsgSpan").html("Cannot remove payment method, beacause it has open balance for following signups. Please reassign signup to another payment method.");
												  }else{
													  $("#BankSignupMsgSpan").html("Cannot remove payment method, beacause it has open balance for following signups. Please reassign signup to another payment method.");
												  }
						  					}else{
						  						  if(paymentTypeSelected && paymentTypeSelected == 'CREDIT'){
													  $("#CardSignupMsgSpan").html("Cannot remove payment method, beacause it has open balance for following signups. \n\n To reassign signup to another payment method, please add at least one more payment method.");
												  }else{
													  $("#BankSignupMsgSpan").html("Cannot remove payment method, beacause it has open balance for following signups. \n\n To reassign signup to another payment method, please add at least one more payment method.");
												  }
						  					}
						  					if(msg && msg != ""){
							  					if(paymentTypeSelected && paymentTypeSelected == 'CREDIT'){
							  						showCardSuccessMessage(msg);
							  					}else{
							  						showBankUpdateSuccessMessage(msg);
							  					}
						  					}
						  				});
									  
									  if(paymentTypeSelected && paymentTypeSelected == 'CREDIT'){
										  $('#remove_card_payment_method_signup_list_table tr').last().after('<tr style="height: 30px;" class="SignupRowClass"><td style="padding-left:4px;height: 30px;"></td><td style="padding-bottom:3px;height: 30px;"></td><td valign="middle" style="padding-bottom:3px; height: 30px;" id="signupID00" nowrap width="200px"></td></tr>');  
									  }else{
										  $('#remove_bank_payment_method_signup_list_table tr').last().after('<tr style="height: 30px;" class="SignupRowClass"><td style="padding-left:4px;height: 30px;"></td><td style="padding-bottom:3px;height: 30px;"></td><td valign="middle" style="padding-bottom:3px; height: 30px;" id="signupID00" nowrap width="200px"></td></tr>');
									  }
									  
									  var s = $('<select class="k-dropdown" style="font-weight: normal; font-size: x-small;" id="select00" />');
					  					for(var val in paymentMethodOptions){
											  console.log('val  >> '+val);
											  if(tokenSelected != val){
												  $('<option />', {value: val, text: paymentMethodOptions[val]}).appendTo(s);
											  }
										}
					  					
					  					var $button = $('<button/>', {
											  type: 'button',
											  'class': 'k-button',
											  id: 'SignupId',
											  text: 'Update All',
											  style: 'background-color: #eb8120;color: #ffffff;background-image:none;font-weight:bold;',
											  click: function() {
												  console.log('Hello! My id is '+ this.id);
												  console.log('Hello! My val is '+ $("#select00").val());
												  
												 $.ajax({
													  type: "POST",
													  url: "updatePaymentMethodForSignup?token="+$("#select00").val()+"&signupid="+signupList+"&accountID="+$("#AccountID").val(),
													  dataType: "json",
													  success: function(updatePaymentMethodResult) {
														  console.log(" Success "+updatePaymentMethodResult.RESULT);
														  if(updatePaymentMethodResult.RESULT){
															  if(updatePaymentMethodResult.RESULT == 'SUCCESS'){
																  console.log("  Success :: updatePaymentMethodResult.RESULT "+updatePaymentMethodResult.RESULT);
																  
																  if(paymentTypeSelected && paymentTypeSelected == 'CREDIT'){
																	  $("#addcard").show();
																	  checkForSignupAndRemovePaymentMethod("Payment method reassigned successfully. Now the Payment method can be removed.", 'N');
																	  //showCardSuccessMessage("Payment method reassigned successfully. Now the Payment method can be removed.");
																  }else{
																	  //$("#addBank").show();
																	  console.log(" Success "+updatePaymentMethodResult.RESULT);
																	  if(updatePaymentMethodResult.RESULT){
																		  if(updatePaymentMethodResult.RESULT == 'SUCCESS'){
																			  //$("#tcSuccessSpan_update_bank").html("Payment method reassigned successfully.");
																			  //showBankUpdateSuccessMessage("Payment method reassigned successfully. Now the Payment method can be removed.");
																			  checkForSignupAndRemovePaymentMethod("Payment method reassigned successfully. Now the Payment method can be removed.", 'N');
																		  }else{
																			  showBankUpdateErrorMessage("There was some error. Please try again later.");
																		  }
																		  
																		  showSelectPaymentMethod();
																		  
																	  }else{
																		  console.log("  Failed :: updatePaymentMethodResult.RESULT "+updatePaymentMethodResult.RESULT);
																		  if(paymentTypeSelected && paymentTypeSelected == 'CREDIT'){
																			  showCardErrorMessage("There was some error. Please try again later.");
																		  }else{
																			  showBankUpdateErrorMessage("There was some error. Please try again later.");
																		  }
																	  }
																  }
																  
																  showSelectPaymentMethod();
																  
															  }else{
																  console.log("  Failed :: updatePaymentMethodResult.RESULT "+updatePaymentMethodResult.RESULT);
																  if(paymentTypeSelected && paymentTypeSelected == 'CREDIT'){
																	  showCardErrorMessage("There was some error. Please try again later.");																			  
																  }else{
																	  showBankUpdateErrorMessage("There was some error. Please try again later.");
																  }
															  }
														  }
													  },
								  					  error: function( xhr,status,error ){
														  console.log("  Status   "+status);
														  console.log("  error   "+error);
														  console.log("  xhr   "+xhr.responseText);
														  if(paymentTypeSelected && paymentTypeSelected == 'CREDIT'){
															  showCardErrorMessage("There was some error. Please try again later.");																			  
														  }else{
															  showBankUpdateErrorMessage("There was some error. Please try again later.");
														  }
														  $("#tcSuccessSpan").css("display", "none");		
														  $("#tcSuccessSpan" ).html("");	
														  $("#tcErrorSpan").css("display", "block");		
														  $( "#tcErrorSpan" ).html("There was some error. Please try again later");
														  $(".k-loading-mask").hide(); 
														  console.log(" There was some error. Please try again later ");
													  }
												});
											  }
					  					});
					  					
					  					var spaceHere = $('<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>');
									  
					  					if(paymentMethodCount > 1 && signupList.length > 0){
					  						s.appendTo($('#signupID00'));
						  					console.log(" space here .. ");
						  					spaceHere.appendTo($('#signupID00'));
						  					$button.appendTo($('#signupID00'));
						  					$("#BankReassignColumnLbl").html("Reassign");
						  					$("#CardReassignColumnLbl").html("Reassign");
					  					}else{
					  						$("#BankReassignColumnLbl").html("");
						  					$("#CardReassignColumnLbl").html("");
					  					}
					  					
						  				if(paymentTypeSelected && paymentTypeSelected == 'CREDIT'){
						  					var origHeight = $('#paymentMethodEdit').height();
							  				var newHeight = origHeight + (data.length*30) + 180;
						  					$('#paymentMethodEdit').css('height', ''+newHeight+'px');
						  				}else{
						  					var origHeight = $('#paymentMethodEdit').height();
							  				var newHeight = origHeight + (data.length*30);
						  					$('#paymentMethodEdit').css('height', ''+newHeight+'px');
						  				}
								 /* }else{
									  
								  }*/
							  },
		  					  error: function( xhr,status,error ){
								  console.log("  Status   "+status);
								  console.log("  error   "+error);
								  console.log("  xhr   "+xhr.responseText);
								  if(paymentTypeSelected && paymentTypeSelected == 'CREDIT'){
									  showCardErrorMessage("There was some error. Please try again later.");																			  
								  }else{
									  showBankErrorMessage("There was some error. Please try again later.");
								  }
								  console.log(" There was some error. Please try again later ");
							  }
						});
		  			}else{
		  				if(isDelete == 'Y'){
		  					showCommonSuccessMessage("Payment method inactivated successfully.");
		  				}else{
		  					$('#remove_card_payment_method_signup_list').hide();
		  					$('#remove_bank_payment_method_signup_list').hide();
		  				}
		  				console.log("Payment method inactivated successfully.");
		  			}
		  		  
		  	  }else {
		  		  if(paymentTypeSelected && paymentTypeSelected == 'CREDIT'){
					  showCardErrorMessage("There was some error. Please try again later.");																			  
				  }else{
					  showBankErrorMessage("There was some error. Please try again later.");
				  }
				  console.log(" error while get data. ");
		  	  }
		  },
		  error: function( xhr,status,error ){
			  console.log("  Status   "+status);
			  console.log("  error   "+error);
			  console.log("  xhr   "+xhr.responseText);
			  if(paymentTypeSelected && paymentTypeSelected == 'CREDIT'){
				  showCardErrorMessage("There was some error. Please try again later.");																			  
			  }else{
				  showBankErrorMessage("There was some error. Please try again later.");
			  }
			  console.log(" There was some error. Please try again later ");
		  }
	});
}

function showSelectPaymentMethod(){
	$("#selectPaymentMethodLbl").show();
	$("#paymentMethodSelectDiv").show();
}

function hideSelectPaymentMethod(){
	$("#selectPaymentMethodLbl").hide();
	$("#paymentMethodSelectDiv").hide();
}

function showCardSuccessMessage(cardMsg){
	$("#statusBlock_card").show();
	$("#tcErrorSpan_card").hide();
	$("#tcErrorSpan_card").html("");
	$("#tcSuccessSpan_card").show();
	$("#tcSuccessSpan_card").html(cardMsg);
}

function showCardErrorMessage(cardMsg){
	$("#statusBlock_card").show();
	$("#tcErrorSpan_card").show();
	$("#tcErrorSpan_card").html(cardMsg);
	$("#tcSuccessSpan_card").hide();
}

function hideCardMessage(){
	$("#statusBlock_card").hide();
	$("#tcErrorSpan_card").hide();
	$("#tcErrorSpan_card").html("");
	$("#tcSuccessSpan_card").hide();
	$("#tcSuccessSpan_card").html("");
}

function showBankErrorMessage(bankMsg){
	$("#statusBlock_bank").show();
	$("#tcErrorSpan_bank").show();
	$("#tcErrorSpan_bank").html(bankMsg);
	$("#tcSuccessSpan_bank").hide();
}

function hideBankMessage(){
	$("#statusBlock_bank").hide();
	$("#tcErrorSpan_bank").html("");
	$("#tcErrorSpan_bank").hide();
	$("#tcSuccessSpan_bank").html("");
	$("#tcSuccessSpan_bank").hide();
}

function hideBankUpdateMessage(){
	$("#tcSuccessSpan_update_bank").html("");
   	$("#tcSuccessSpan_update_bank").hide();
  	$("#tcErrorSpan_update_bank").html("");
  	$("#tcErrorSpan_update_bank").hide();
}

function showBankUpdateSuccessMessage(msg){
	$("#tcSuccessSpan_update_bank").html(msg);
   	$("#tcSuccessSpan_update_bank").show();
  	$("#tcErrorSpan_update_bank").html("");
  	$("#tcErrorSpan_update_bank").hide();
}

function showBankUpdateErrorMessage(msg){
	$("#tcSuccessSpan_update_bank").html("");
   	$("#tcSuccessSpan_update_bank").hide();
  	$("#tcErrorSpan_update_bank").html(msg);
  	$("#tcErrorSpan_update_bank").show();
}

function showCommonSuccessMessage(commonMsg){
	$("#statusBlock11").show();
	$("#addBank").hide();
	$("#addcard").hide();
	hideSelectPaymentMethod();
	$("#tcSuccessSpan11").html(commonMsg);
	$("#tcSuccessSpan11").show();
	$("#tcErrorSpan11").html("");
   	$("#tcErrorSpan11").hide();
   
   	$("#tcSuccessSpan_update_bank").html("");
   	$("#tcSuccessSpan_update_bank").hide();
  	$("#tcErrorSpan_update_bank").html("");
  	$("#tcErrorSpan_update_bank").hide();
  	$("#statusBlock_update_bank").hide();
}

function showCommonErrorMessage(commonMsg){
	$("#statusBlock11").show();
	$("#addBank").hide();
	$("#addCard").hide();
	hideSelectPaymentMethod();
	$("#tcSuccessSpan11").hide();
   	$("#tcSuccessSpan11").html("");
   	$("#tcErrorSpan11").show();
   	$("#tcErrorSpan11").html(commonMsg);
}

function hideCommonMessage(){
	$("#statusBlock11").hide();
	//$("#addBank").hide();
	//$("#addCard").hide();
	//$("#selectPaymentMethodLbl").hide();
	//$("#paymentMethodSelectDiv").hide();
	$("#tcSuccessSpan11").html("");
	$("#tcSuccessSpan11").hide();
	$("#tcErrorSpan11").html("");
   	//$("#tcSuccessSpan11").html("Payment method removed successfully.");
   	$("#tcErrorSpan11").hide();
}