
/* functions related to payment */

var hash = '';
var merchantId = "YMCA07021001";  
var transactionAmount = "0";
var JetDirectToken	= "9zmXvRRoAiCAlrmQfpj2hNavUGb56F28jQYtxOItdEsWkYWglSmffVtgues91o3emutnq8rU";
var paymentOrderId = Math.floor((Math.random()*1000000000000)+1);

// payment cart page
function submitPlaceOrderForm(){
	
	if(!checkAndUpdateCapacity()){
		return;
	}
		
	if($("#paymentInfoRadio").val()=='NewCard' || $("#paymentInfoRadio").val()=='NewBankInfo'){
		if(($("#paymentInfoRadio").val()=='NewCard' && $('#SaveCard').is(":checked") && $("#nickName").val()=="") 
				|| ($("#paymentInfoRadio").val()=='NewBankInfo' && $('#SaveCardACH').is(":checked") && $("#nickNameACH").val()=="")){
			 showErrorMessageForSignup("Please provide Nick Name");
			 return;
		}else{
			console.log(" in else ");
			if($("#paymentInfoRadio").val()=='NewCard'){
				processCCpayment();
			}else{
				processACHpayment();
			}
		}
	}else if($("#paymentInfoRadio").val()=='Cash' || $("#paymentInfoRadio").val()=='None' || $("#paymentInfoRadio").val()=='Check'){
		proceedtosignup('0','','0',$("#paymentInfoRadio").val());		
	}else{
		var selectedPaymentMethod = $("#paymentInfoRadio").data("kendoDropDownList").text();
		var paymentDDvalue = $('#paymentInfoRadio').val();
		var paymentDDValueSplit = paymentDDvalue.split("__");
		
		console.log("  type  --> "+paymentDDValueSplit[0]);
		console.log("  token --> "+paymentDDValueSplit[1]);
		console.log("  token --> "+paymentDDValueSplit[2]);
		
		var paymentMethodType = paymentDDValueSplit[0];
		var paymentMethodToken = paymentDDValueSplit[1];
		var paymentMethodId = paymentDDValueSplit[2];
		
		if (paymentMethodType && paymentMethodType == 'CREDIT') {
			processCCpaymentbytoken(paymentMethodId, paymentMethodToken);
		} else {
			processACHpaymentbytoken(paymentMethodId, paymentMethodToken);
		}
	}
}

function processCCpayment(){
	console.log("  processpayment  ");
	
	console.log("  AccountID  "+$("#AccountID").val());
	console.log("  cardNum  "+$("#cardNum").val());
	console.log("  addCardExpirationMonthSelect  "+$("#addCardExpirationMonthSelect").val());
	console.log("  addCardExpirationYearSelect  "+$("#addCardExpirationYearSelect").val());
	
	$(".k-loading-mask").show();
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
				if($('#SaveCard').is(":checked") && data.paymentId && data.paymentId > 0){
					$(".k-loading-mask").hide();
					showErrorMessageForSignup("Card Information already exist or inactivated. To activate please contact Y Agent.");
				   	console.log("Card Information already exist or inactivated. To activate please contact Y Agent.");
				   	
				   	UpdateCapacityAfterPaymentFail();
					return;
				}else{
					var saveCard = 'N';
					if($('#SaveCard').is(":checked")){
						saveCard = 'Y';
					}
					
					var paymentOrderNumber = paymentOrderId.toString();
					
					orderamount = $("#ordertotal").text();
					transactionAmount = $.trim(orderamount.replace("$",""));
					transactionAmount = $.trim(transactionAmount.replace(".00",""));
					transactionAmount = $.trim(transactionAmount.replace(",",""));
					
					console.log(" transactionAmount  "+transactionAmount);
					
					var jsSha = new jsSHA(merchantId+transactionAmount+JetDirectToken+paymentOrderId);
					hash = jsSha.getHash("SHA-512", "HEX");	
					console.log("  paymentOrderNumber  "+paymentOrderNumber);
					console.log("  hash  "+hash.toString());
					console.log("  transactionAmount  "+transactionAmount);
					
					console.log("  nickName11  "+$("#nickName").val());
					console.log("  fullName11  "+$("#fullName").val());
					console.log("  securityCode  "+$("#securityCode").val());
					console.log("  billingAddressLine1  "+$("#billingAddressLine1").val());
					console.log("  billingAddressLine2  "+$("#billingAddressLine2").val());
					console.log("  billingCity  "+$("#billingCity").val());
					console.log("  billingState  "+$("#billingState").val());
					console.log("  billingZip  "+$("#billingZip").val());
					console.log("  saveCard  "+saveCard);
					
					var jsonData = {
						trans_type : "SALE", // "TOKENIZE" // "AUTHONLY", "SALE"
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
						paymentOrderId : paymentOrderNumber,
						isSaveCard : saveCard
					};
					var win = document.getElementById("childIframeId").contentWindow;
					win.postMessage(jsonData, '*');
					console.log(" Payment request sent. ");
					
					$.ajax({
						type: "POST",
						url: "checkPaymentProcessStatus",
						data: { "orderNumber" : paymentOrderNumber },
						success: function( data ) {
						  console.log(" got data :: 11 ");
				  		  
				  		  if(data){
					  		  	console.log(" got data ::  >> "+data);
							    data_split_add_cart = data.split("__");
								if(data_split_add_cart[0]=='SUCCESS'){
									
									console.log(" Token : "+data_split_add_cart[1]);
									console.log(" PaymentMethodId : "+data_split_add_cart[2]);
									
									paymentMethodId = data_split_add_cart[2];
									
									proceedtosignup(paymentMethodId,'',paymentOrderNumber,"Credit");
									
								}else{
									console.log(" failed status : "+data_split_add_cart[1]);
									$(".k-loading-mask").hide();
									showErrorMessageForSignup("Error occured while processing payment. \n"+data_split_add_cart[1]);
									UpdateCapacityAfterPaymentFail();
								}
					  	  }else{
					  		  $(".k-loading-mask").hide();
					  		  showErrorMessageForSignup("There was some error. Please try again later.");
					  		  UpdateCapacityAfterPaymentFail();
					  	  }
					  },
					  error: function( xhr,status,error ){
						  $(".k-loading-mask").hide();
						  showErrorMessageForSignup("There was some error. Please try again later.");
						  UpdateCapacityAfterPaymentFail();
					  }
					});
				}
	  	  }else{
	  		  $(".k-loading-mask").hide();
	  		  showErrorMessageForSignup("There was some error. Please try again later.");
	  		  UpdateCapacityAfterPaymentFail();
			  return;
	  	  }
	  },
	  error: function( xhr,status,error ){
		  $(".k-loading-mask").hide();
  		  showErrorMessageForSignup("There was some error. Please try again later.");
  		  UpdateCapacityAfterPaymentFail();
		  return;
	  }
	});
}

function processACHpayment(){
	console.log(" processACHpayment ");
	var orderamount = $("#ordertotal").text();
	var transactionAmount = $.trim(orderamount.replace("$",""));
	transactionAmount = $.trim(transactionAmount.replace(".00",""));
	transactionAmount = $.trim(transactionAmount.replace(",",""));
	
	var bankAccountNumber =  $("#bankAccountNumber").val();
	var bankRoutingNumber =  $("#bankRoutingNumber").val();
	var checkNumber =  $("#checkNumber").val();
	var accountName =  $("#bankAccountName").val();
	var totalAmt =  transactionAmount;
	var accountID =  $("#AccountID").val();
	var nickName =  $("#nickNameACH").val();
	var accountType =  $("#bankAccountTypeSelect").val();
	
	$(".k-loading-mask").show();
	$.ajax({
		async:false,
		type: "GET",
		dataType: "json",
		url: "isBankInfoAlreadyExist?accountID="+accountID+"&bankAccountNumber="+bankAccountNumber,
		success: function( data ) {
		  console.log(" got data ::  ");
  		  
  		  if(data){
	  		  	console.log(" got data ::  >> "+data);
	  		    console.log(" got data paymentId ::  >> "+data.paymentId);
				if(data.paymentId && data.paymentId > 0){
					showErrorMessageForSignup("Bank Information already exist or inactivated. To activate please contact Y Agent.");
					$(".k-loading-mask").hide();
					UpdateCapacityAfterPaymentFail();
					return;
				}else{
	
					$.ajax({
						  type: "POST",
						  url: "processACHPayment",	
						  data: { bankAccountNumber: bankAccountNumber, bankRoutingNumber : bankRoutingNumber, checkNumber : checkNumber, 
							  	  accountName : accountName, totalAmt : totalAmt, accountType : accountType, nickName : nickName, accountID : accountID},
						  success: function( data ) {
							  
							  if(data){
								  	console.log(" data :: "+data);
								  
								  	data_split_add_cart = data.split("__");
									if(data_split_add_cart[0]=='SUCCESS'){
										
										console.log(" Token : "+data_split_add_cart[1]);
										console.log(" PaymentMethodId : "+data_split_add_cart[2]);
										console.log(" TransactionId : "+data_split_add_cart[3]);
										
										paymentMethodId = data_split_add_cart[2];
										transactionId = data_split_add_cart[3];
										
										proceedtosignup(paymentMethodId,"",transactionId,"ACH");
										
									}else{
										console.log(" failed status : "+data_split_add_cart[1]);
										$(".k-loading-mask").hide();
										showErrorMessageForSignup("Error occured while processing payment. \n"+data_split_add_cart[1]);
										UpdateCapacityAfterPaymentFail();
									}
							  }else {
								  $(".k-loading-mask").hide();
								  showErrorMessageForSignup("Error occurred while processing the payment. Please try again later.");
								  UpdateCapacityAfterPaymentFail();
							  }
						  },
						  error: function( xhr,status,error ){
							  $(".k-loading-mask").hide();
							  showErrorMessageForSignup("Error occurred while processing the payment. Please try again later.");
							  UpdateCapacityAfterPaymentFail();
						  }
					});
				}
		  }else{
			  $(".k-loading-mask").hide();
			  showErrorMessageForSignup("There was some error. Please try again later.");
			  UpdateCapacityAfterPaymentFail();
			  return;
	  	  }
	  },
	  error: function( xhr,status,error ){
		  $(".k-loading-mask").hide();
		  showErrorMessageForSignup("There was some error. Please try again later.");
		  UpdateCapacityAfterPaymentFail();
		  return;
	  }
});
}

function processCCpaymentbytoken(paymentMethodId,token){
	console.log(" processpaymentbytoken  ");
	orderamount = $("#ordertotal").text();
	transactionAmount = $.trim(orderamount.replace("$",""));
	//alert(transactionAmount);
	transactionAmount = $.trim(transactionAmount.replace(".00",""));
	transactionAmount = $.trim(transactionAmount.replace(",",""));
	if (transactionAmount == "0.00") {
		transactionAmount = "0";
	}
	console.log(" paymentMethodId  >>  "+paymentMethodId);
	console.log(" token  >>  "+token);
	console.log(" transactionAmount  >>  "+transactionAmount);
	
	//var jsSha = new jsSHA(merchantId+transactionAmount+JetDirectToken+paymentOrderId);
	//hash = jsSha.getHash("SHA-512", "HEX");	
	//var jp_request_hash = hash;
	$(".k-loading-mask").show();
	if(transactionAmount > 0){
		$.ajax({
			  type: "POST",
			  url: "processPaymentByTokenId",	
			  data: { token: token, totalAmount : Math.ceil(transactionAmount)},
			  success: function( data ) {
				  console.log(" processPaymentByTokenId ");
				  if(data != null && data.responseText == "APPROVED"){	
					  console.log(" Success processPaymentByTokenId >> ");
					  
					  var transactionId = data.transId;
					  console.log(" transactionId  >>  "+transactionId);
					  proceedtosignup(paymentMethodId,'',transactionId,"Credit");
				  }else {
					  $(".k-loading-mask").hide();
					  $("#tcSuccessSpan").css("display", "none");		
					  $("#tcErrorSpan").css("display", "block");	
					  $( "#tcErrorSpan" ).html("The transaction failed..");	
					  UpdateCapacityAfterPaymentFail();
				  }
			  },
			  error: function( xhr,status,error ){
				  console.log(" Error processPaymentByTokenId ");
				  $(".k-loading-mask").hide();
				  $("#tcSuccessSpan").css("display", "none");		
				  $("#tcErrorSpan").css("display", "block");	
				  $( "#tcErrorSpan" ).html("There was some error. Please try again later");	
				  UpdateCapacityAfterPaymentFail();
			  }
		});
	}else{
		proceedtosignup(paymentMethodId,'','',"Credit");
	}
}

function processACHpaymentbytoken(paymentMethodId,token){
	console.log(" processACHpaymentbytoken  ");
	
	orderamount = $("#ordertotal").text();
	transactionAmount = $.trim(orderamount.replace("$",""));
	transactionAmount = $.trim(transactionAmount.replace(".00",""));
	transactionAmount = $.trim(transactionAmount.replace(",",""));
	
	console.log(" transactionAmount  >>  "+transactionAmount);
	console.log(" paymentMethodId  >>  "+paymentMethodId);
	console.log(" token  >>  "+token);
	
	//var selectedPaymentMethod = $("#paymentInfoRadio").data("kendoDropDownList").text();
	//var selectedPaymentMethodArr = selectedPaymentMethod.split(", ");
	//var checkNumber = selectedPaymentMethodArr[1];
	//var cardName = selectedPaymentMethodArr[0];
	//var jp_request_hash = hash;
	$(".k-loading-mask").show();
	if(transactionAmount > 0){
		$.ajax({
			  type: "POST",
			  url: "processACHPaymentByTokenId",	
			  data: { token: token, totalAmount : transactionAmount, checkNumber : '', cardName : ''},
			  success: function( data ) {				 
				  if(data != null && data.actCode == "000"){
					  var transactionId = data.transId; 
					  console.log(" Success processACHPaymentByTokenId >> ");
					  console.log(data);
					  proceedtosignup(paymentMethodId,'',transactionId,"ACH");
				 }else{
					  $(".k-loading-mask").hide();
					  console.log(" Error processACHPaymentByTokenId ");
					  $("#tcSuccessSpan").css("display", "none");		
					  $("#tcErrorSpan").css("display", "block");	
					  $( "#tcErrorSpan" ).html("Error occurred while processing the payment. Please try again later <br />ActionCode :<b>"+data.actCode+"</b> Error message : "+data.responseText);
					  UpdateCapacityAfterPaymentFail();
				 }
			  },
			  error: function( xhr,status,error ){
				  $(".k-loading-mask").hide();
				  $("#statusBlock-payment").css("display", "block");	
				  $("#tcloginErrorSpan-payment").css("display", "block");	
				  $( "#tcloginErrorSpan-payment" ).html("There was some error. Please try again later");	
				  UpdateCapacityAfterPaymentFail();
			  }
		});
	}else{
		proceedtosignup(paymentMethodId,'','',"ACH");
	}
}

function proceedtosignup(paymentId, jp_request_hash, orderNumber, paymentMode){
	console.log("  proceedtosignup  ");
	$(".k-loading-mask").show();
	
	//proceedtosignupforCC(paymentId, jp_request_hash, orderNumber, paymentMode);
	//return;
	
	var sessionData1 = [], contactData1 = [],
		data = $('form').find('input:not([name="selectedItemSession"])').serialize();

	var cartItems = [];
	var campActivity = []  ;
	var campTransport  = [] ;
	
	//console.log(" data  ::  "+data);
	
	//console.log("  proceedtosignup 383 "+cart.contents.length);
	if(cart.contents.length>0){
		$.each(cart.contents, function(i,itemDetailsSession) {
			/*
			var itemprice = 0;
			if(itemDetailsSession.contact.isMember)
				itemprice = (parseFloat(itemDetailsSession.item.memberprice) - parseFloat(itemDetailsSession.item.memberdiscount) - parseFloat(itemDetailsSession.discount1)) * itemDetailsSession.quantity;
			else
				itemprice = (parseFloat(itemDetailsSession.item.nonmemberprice) - parseFloat(itemDetailsSession.item.nonmemberdiscount) - parseFloat(itemDetailsSession.discount1)) * itemDetailsSession.quantity;
			*/
			
			//calculateItemAmountOnSignupRecord();
			var isChargeAmount = true;
        	if(itemDetailsSession.item.programType == 'Child Care' && (itemDetailsSession.item.category != 'In-Service' || itemDetailsSession.item.category != 'After School') && itemDetailsSession.item.hasConfirmedDays == 'false'){
				isChargeAmount = false;
			}else if(itemDetailsSession.waitlist){
				//isChargeAmount = false;
			}
        	
        	var itemprice = 0, itempriceOnSignup = 0, itempriceOnInvoice = 0, itempriceOnPayer = 0, remDiscountAmtOnSignup = 0;
			if(isChargeAmount){

				//if(itemDetailsSession.item.programType == 'CHILD CARE'){ // && itemDetailsSession.item.category == 'After School'
				
				if(itemDetailsSession.item.programType == 'Camp'){
					if(itemDetailsSession.isFullPayment){
						itemprice += calculateItemAmountOnCart(itemDetailsSession);
					}else{
						itemprice += calculateItemMinimumAmountOnCart(itemDetailsSession);
					}
				}else{
					itemprice += calculateItemAmountOnCart(itemDetailsSession);
				}
				
					//itemprice = calculateItemAmountOnCart(itemDetailsSession);
					itempriceOnSignup = calculateItemAmountOnSignupRecord(itemDetailsSession);
					itempriceOnPayer = calculateItemAmountOnPayerRecord(itemDetailsSession);
					itempriceOnInvoice = calculateItemAmountOnInvoiceRecord(itemDetailsSession);
					//remDiscountAmtOnSignup = getRemDiscountAmt(itemDetailsSession.invoiceArr);
					
					var invoiceArray = itemDetailsSession.invoiceArr;
					if(invoiceArray != undefined && invoiceArray.length > 0){
						for(var i=0; i<invoiceArray.length; i++){
							var invoice = invoiceArray[i];
							console.log(" INVOICE  (invoiceAmt:"+invoice.invoiceAmt+", billDate:"+invoice.billDate+", dueDate:"+invoice.dueDate+", invoiceDiscountAm:"+invoice.invoiceDiscountAmt+")");
						}
					}
					
					//console.log("   remDiscountAmtOnSignup  ::  "+remDiscountAmtOnSignup);
					
				/*}else{
					itemprice = (parseFloat(itemDetailsSession.signupPrice * parseInt(itemDetailsSession.noOfTicketsOrPackages)) + parseFloat(itemDetailsSession.joinFee) + parseFloat(itemDetailsSession.setupFee) + parseFloat(itemDetailsSession.registrationPrice) + parseFloat(itemDetailsSession.depositAmount) - parseFloat(itemDetailsSession.autoDiscount) - parseFloat(itemDetailsSession.discount1) - parseFloat(itemDetailsSession.FAamount)) * itemDetailsSession.quantity;
				}*/
			}
			
			//var discount = 0;
			//var fa = parseFloat(itemDetailsSession.FAamount);
			//var signupAmount = itemDetailsSession.signupPrice;
			//var proRatedSignupPrice = itemDetailsSession.proRatedSignupPrice;
			//var setupFee = itemDetailsSession.setupFee;
			//var registrationFee = itemDetailsSession.registrationPrice;
			//var depositAmount = itemDetailsSession.depositAmount;
			//var priceOption = itemDetailsSession.priceOption;
			//var billingOption = itemDetailsSession.billingOptionVal;
			//var noOfTickets = itemDetailsSession.noOfTickets;
			//var joinFee = itemDetailsSession.joinFee;
			//var specialrequest = itemDetailsSession.specialrequest;
			var gradeLevel = '';
			if(itemDetailsSession.gradeLevel){
				gradeLevel = itemDetailsSession.gradeLevel;
			}
			
			var selectedStartDate = '';
			if(itemDetailsSession.selectedStartDate){
				selectedStartDate = itemDetailsSession.selectedStartDate;
			}
			
			var FAobj = itemDetailsSession.FAobj;
			
			var FApercent = "0";
			var FAstartDate = " ";
			var FAendDate = " ";
			//console.log("FA length" +FAobj.length)
			if(FAobj!=null && FAobj.length>0){
				FApercent = FAobj[0].FApercent;
				FAstartDate = convertJsonDate(FAobj[0].FAstartDate);
				FAendDate = convertJsonDate(FAobj[0].FAendDate);
			}
			
			var hasWLDays = itemDetailsSession.item.hasWLDays;
			var selectedDays = itemDetailsSession.item.days;
			selectedDays = selectedDays.replace(new RegExp(",", 'g'), ";");
			
			var selectedDaysId = itemDetailsSession.item.dayId;
			selectedDaysId = selectedDaysId.replace(new RegExp(",", 'g'), ";");
			
			var WLDays = " ";
			if(hasWLDays && WLDays.length>0){
				 WLDays = itemDetailsSession.item.WLDays;
			}

			//var d = itemDetailsSession.item.prodId+"__"+itemDetailsSession.contact.contactId+"__"+itemprice+"__"+discount+"__"+fa+"__"+FAobj+"__"+signupAmount+"__"+setupFee+"__"+registrationFee+"__"+depositAmount+"__"+priceOption+"__"+FApercent+"__"+FAstartDate+"__"+FAendDate+"__"+itemDetailsSession.waitlist+"__"+noOfTickets+"__"+specialrequest+"__"+joinFee+"__"+billingOption;
			
			var itemDetailId = itemDetailsSession.item.itemDetailsId;
			
			var emgContact = $("input[name=emg_user_"+itemDetailId+"]:checked").val();
			//console.log("  emg_user_  ::::  "+$("input[name=emg_user_"+itemDetailId+"]:checked").val());
			var authContacts = '';
			var authVal = $("select#auth_user_"+itemDetailId).val();
			if(typeof authVal != 'undefined'){
				for(var k=0;k<authVal.length;k++){
					authContacts = authContacts +""+ authVal[k]+";";
				}
			}

			// Please convert this to a map, not good design, at   
			if (gradeLevel == '' || gradeLevel == null || typeof(gradeLevel) == 'undefined') gradeLevel = "";
			if (selectedStartDate == '' || selectedStartDate == null || typeof(selectedStartDate) == 'undefined') selectedStartDate = "";
			
			var parentSignUpItem = itemDetailsSession.parentSignUpItem ; 
			if (parentSignUpItem == '' || parentSignUpItem == null || typeof(parentSignUpItem) == 'undefined') parentSignUpItem = "";
			
			/*console.log(" Add to cart map ( itemDetailId: "+itemDetailsSession.item.prodId+", contactId: "+itemDetailsSession.contact.contactId+", isRecurring: "+itemDetailsSession.isRecurring+")");
			console.log("  selectedStartDate: "+selectedStartDate+"  dueDate: "+convertDateToMMDDYYYY(itemDetailsSession.dueDate)+"  billDate: "+convertDateToMMDDYYYY(itemDetailsSession.billDate)+"  nextBillDate: "+convertDateToMMDDYYYY(itemDetailsSession.nextBillDate));
			console.log("  signupAmount: "+signupAmount);
			console.log("  setupFee: "+setupFee+",  registrationFee: "+registrationFee+",  depositAmount: "+depositAmount+",  joinFee: "+joinFee+",  fa: "+fa);
			console.log("  itemAmountOnCart: "+itemprice);
			console.log("  itempriceOnSignup: "+itempriceOnSignup);
			console.log("  itempriceOnInvoiceAndPayerForNonRecurring: "+itempriceOnInvoiceAndPayerForNonRecurring);
			console.log("  itempriceOnInvoiceForRecurring: "+itempriceOnInvoiceForRecurring);
			*/
			var cartItemsMap = new Object();
			
			cartItemsMap.itemDetailId = itemDetailsSession.item.prodId;
			cartItemsMap.contactId = itemDetailsSession.contact.contactId;
			cartItemsMap.itemprice = itemprice;
			//cartItemsMap.discount = discount;
			cartItemsMap.fa = parseFloat(itemDetailsSession.FAamount);
			cartItemsMap.FAobj = FAobj;
			cartItemsMap.signupAmount = itemDetailsSession.signupPrice;
			cartItemsMap.setupFee = itemDetailsSession.setupFee;
			cartItemsMap.registrationFee = itemDetailsSession.registrationPrice;
			cartItemsMap.depositAmount = itemDetailsSession.depositAmount;
			cartItemsMap.priceOption= itemDetailsSession.priceOption;
			cartItemsMap.FApercent = FApercent;
			cartItemsMap.FAstartDate = FAstartDate;
			cartItemsMap.FAendDate = FAendDate;
			cartItemsMap.waitlist = itemDetailsSession.waitlist;
			cartItemsMap.noOfTickets = itemDetailsSession.noOfTickets;
			cartItemsMap.specialrequest = itemDetailsSession.specialrequest;
			cartItemsMap.joinFee = itemDetailsSession.joinFee;
			cartItemsMap.programType = itemDetailsSession.item.programType;
			cartItemsMap.hasWLDays = hasWLDays;
			cartItemsMap.WLDays = WLDays;
			cartItemsMap.selectedDays = selectedDays;
			cartItemsMap.selectedDaysId = selectedDaysId;
			cartItemsMap.billingOption = itemDetailsSession.billingOptionVal;
			cartItemsMap.itempriceOnSignup = itempriceOnSignup;
			//cartItemsMap.remDiscountAmtOnSignup = remDiscountAmtOnSignup;
			cartItemsMap.itempriceOnPayer = itempriceOnPayer;
			cartItemsMap.itempriceOnInvoice = itempriceOnInvoice;
			cartItemsMap.dueDateOnInvoice = convertDateToMMDDYYYY(itemDetailsSession.dueDateOnInvoice);
			cartItemsMap.billDateOnInvoice = convertDateToMMDDYYYY(itemDetailsSession.billDateOnInvoice);
			cartItemsMap.category = itemDetailsSession.item.category;
			cartItemsMap.gradeLevel = gradeLevel;
			cartItemsMap.selectedStartDate = selectedStartDate;
			cartItemsMap.parentSignUpItem = parentSignUpItem;
			cartItemsMap.activities = getActivitiesyByCIdAndItemId(itemDetailsSession.contact.contactId,itemDetailsSession.item.prodId);
			cartItemsMap.emgContact = emgContact;
			cartItemsMap.authContacts = authContacts;
			cartItemsMap.dueDate = convertDateToMMDDYYYY(itemDetailsSession.dueDate);
			cartItemsMap.billDate = convertDateToMMDDYYYY(itemDetailsSession.billDate);
			cartItemsMap.nextBillDate = convertDateToMMDDYYYY(itemDetailsSession.nextBillDate);
			cartItemsMap.isRecurring = itemDetailsSession.isRecurring;
			cartItemsMap.promos = convertToPromoString(itemDetailsSession.promotionMap);
			cartItemsMap.invoices = convertToInvoiceString(itemDetailsSession.invoiceArr);
			
			//console.log("  cartItemsMap  >>>>>  "+cartItemsMap);
			cartItems.push(JSON.stringify(cartItemsMap));
			
			
			//console.log("  cart items join   :::   "+ cartItems.join('_AND_'));
		});
	}
		/*}else if(cartType == 'Event'){
			if(event_cart.contents.length>0){
				$.each(event_cart.contents, function(i,itemDetailsSession) {
					var itemprice = 0;
					
					if(itemDetailsSession.contact.isMember)
						itemprice = (parseFloat(itemDetailsSession.item.memberprice) - parseFloat(itemDetailsSession.item.memberdiscount) - parseFloat(itemDetailsSession.discount1)) * itemDetailsSession.quantity;
					else
						itemprice = (parseFloat(itemDetailsSession.item.nonmemberprice) - parseFloat(itemDetailsSession.item.nonmemberdiscount) - parseFloat(itemDetailsSession.discount1)) * itemDetailsSession.quantity;
					
					cartItems.push(itemDetailsSession.item.prodId+"__"+itemDetailsSession.contact.contactId+"__"+itemprice);
				});
			}
		}*/
	//}
	console.log("  proceedtosignup 432 ");
	var orderamount = $("#ordertotal").text();
	var transactionAmount = $.trim(orderamount.replace("$",""));
	transactionAmount = $.trim(transactionAmount.replace(".00",""));
	transactionAmount = $.trim(transactionAmount.replace(",",""));

	var bankAccountNumber =  $("#bankAccountNumber").val();
	var bankRoutingNumber =  $("#bankRoutingNumber").val();
	var checkNumber =  $("#checkNumber").val();
	var accountName =  $("#bankAccountName").val();
	var totalAmt =  transactionAmount;
	var accountID =  $("#AccountID").val();
	var nickName =  $("#nickNameACH").val();
	var accountType =  $("#bankAccountTypeSelect").val();
	
	if($("#paymentInfoRadio").val()=='Check'){
		data += '&paymentMode='+paymentMode+'&jp_request_hash='+jp_request_hash+'&orderNumber='+orderNumber+'&paymentId='+paymentId+'&cartItems='+cartItems.join('_AND_')+'&bankAccountNumber='+bankAccountNumber +'&bankRoutingNumber='+bankRoutingNumber +'&checkNumber='+checkNumber +'&accountName='+accountName+'&totalAmt='+totalAmt;
	}else{
		data += '&paymentMode='+paymentMode+'&jp_request_hash='+jp_request_hash+'&orderNumber='+orderNumber+'&paymentId='+paymentId+'&cartItems='+cartItems.join('_AND_');
	}	
	data += "&optyId=" + $("optyId").val();

	//var itemActivity = $.sessionStorage.getItem("selectedItemActivity");
	//data += "&itemActivity=" + itemActivity.join(',');

	
	//console.log("  data :: "+data);
	console.log(" proceedtosignup 436 ");
	$.ajax({
		  type: "POST",
		  url: $('#signupFrm').attr( "action"),
		  async:false,
		  data: data,
		  success: function( data ) {
			 console.log(" proceedtosignup 443 ");
			 console.log(data);
			 var data_s = data.split("__");
			 console.log(" data_s 0 "+data_s[0]);
			 console.log(" data_s 1 "+data_s[1]);
			 console.log(" data_s 2 "+data_s[2]);
			 if(data_s[0]=="SUCCESS"){
				 console.log(" proceedtosignup 450 ");
				  $.sessionStorage.clear();
					cart.clear();
				  $(".k-loading-mask").hide();
				  $("#payment_cart").html("");
				  var successMsg = '';
				  
				  successMsg += '<div id="statusBlock" class="k-block k-success-colored" style="width: 90%;margin: 20px; padding: 10px;">Thank you for enrolling.<br>';
				  if(orderNumber && orderNumber != ''){
					  successMsg += 'Your transaction Id is: '+orderNumber+'';  
				  }
				  successMsg += '</div>';
				  
				  successMsg += '<div style="width: 90%;margin: 20px; padding: 10px;"><br><input id="newsignup" type="button" class="k-button" value="Go to Home" onclick="location.href=\'home\'"></div>';
				  $("#payment_cart").html(successMsg);
				  $("#searchprogram").html("");
				  $("#searchprogram").hide();
				  $("#program_details").css("width", "98%");
				  $("#searchevent").html("");
				  $("#searchevent").hide();
				  $("#event_details").css("width", "98%");
				  $("#tcErrorSpan").css("display", "none");		
				  $("#tcErrorSpan" ).html("");	
				  $("#tcSuccessSpan").css("display", "none");		
				  $("#tcSuccessSpan" ).html("");
			 }
			 else{
				  console.log(" 466  ");
				  $("#tcErrorSpan").css("display", "block");		
				  $("#tcErrorSpan" ).html("There is some error, please try after some time");	
				  $("#tcSuccessSpan").css("display", "none");		
				  $("#tcSuccessSpan" ).html("");
				  $(".k-loading-mask").hide();
			 }
		  },
		  error: function( xhr,status,error ){
			  console.log(" 476  ");
			  console.log(" 476  "+xhr);
			  console.log(" 476  "+status);
			  console.log(" 476  "+error);
			  $("#tcErrorSpan").css("display", "block");		
			  $("#tcErrorSpan" ).html("There is some error, please try after some time");	
			  $("#tcSuccessSpan").css("display", "none");		
			  $("#tcSuccessSpan" ).html("");
			  $(".k-loading-mask").hide();
		  }
	});
}

function convertDateToMMDDYYYY(date){
	var d = null;
	if(date != null){
		d = new Date(date);
	}
	
	if(d != null && d instanceof Date){
		var dd = d.getDate();
		var mm = d.getMonth() + 1;
		var yyyy = d.getFullYear();
	}else{
		return null;
	}
	return mm+"/"+dd+"/"+yyyy;
}

function calculateItemAmountOnSignupRecord(current){
	
	//console.log(" calculateItemAmountOnSignupRecord >>  "+current.isRecurring);
	var itemAmountOnSignupRecord = 0;
	if(current.isRecurring){
		//console.log(" calculateItemAmountOnSignupRecord recurring ");
		itemAmountOnSignupRecord = parseFloat(current.signupPrice);
	}else{
		//console.log(" calculateItemAmountOnSignupRecord non recurring ");
		var sumOfAdditive = 0, sumOfSignupPromoDiscount = 0, sumOfOtherPromoDiscount = 0;
		if (current.item.signuppriceArr.length >0) {
			var pricingRule = getPricingRule(current.contact,current.item);
			if(pricingRule.additiveOrSubtractive && pricingRule.additiveOrSubtractive == 'Additive'){
				
				if(parseFloat(current.proRatedSignupPrice) > 0){
					sumOfAdditive += current.proRatedSignupPrice;
				}else{
					sumOfAdditive += current.signupPrice;
				}

				if(current.promotionMap != undefined){
					for(var j=0; j < current.promotionMap.length; j++){
						var promo = current.promotionMap[j];
						if(promo.PromoRuleType == 'Sign Up'){
							sumOfSignupPromoDiscount += parseFloat(promo.actualDiscountValue);
						}
					}
				}
			}
		}
		
		if (current.item.registrationpriceArr.length >0) {
			if(current.item.registrationpriceArr[0].additiveOrSubtractive && current.item.registrationpriceArr[0].additiveOrSubtractive == 'Additive'){
				sumOfAdditive += parseFloat(current.registrationPrice);
				
				if(current.promotionMap != undefined){
					for(var j=0; j < current.promotionMap.length; j++){
						var promo = current.promotionMap[j];
						if(promo.PromoRuleType == 'Registration'){
							sumOfOtherPromoDiscount += parseFloat(promo.actualDiscountValue);
						}
					}
				}
			}
		}
		
		if (current.setupFee > 0 && current.item.setupfeepriceArr.length >0) {
			if(current.item.setupfeepriceArr[0].additiveOrSubtractive && current.item.setupfeepriceArr[0].additiveOrSubtractive == 'Additive'){
				sumOfAdditive += parseFloat(current.setupFee);
				
				if(current.promotionMap != undefined){
					for(var j=0; j < current.promotionMap.length; j++){
						var promo = current.promotionMap[j];
						if(promo.PromoRuleType == 'SetUpFee'){
							sumOfOtherPromoDiscount += parseFloat(promo.actualDiscountValue);
						}
					}
				}
			}
		}
		
		if (current.joinFee > 0) {
			sumOfAdditive += parseFloat(current.joinFee);
			
			if(current.promotionMap != undefined){
				for(var j=0; j < current.promotionMap.length; j++){
					var promo = current.promotionMap[j];
					if(promo.PromoRuleType == 'JoinFee'){
						sumOfOtherPromoDiscount += parseFloat(promo.actualDiscountValue);
					}
				}
			}
		}
		
		itemAmountOnSignupRecord = (parseFloat(sumOfAdditive) - (parseFloat(sumOfSignupPromoDiscount) + parseFloat(sumOfOtherPromoDiscount)) - parseFloat(current.FAamount)); //- parseFloat(current.discount1) 
	}
	
	/*if(current.isMinPayment){
		for(var j=0; j < current.promotionMap.length; j++){
			var promo = current.promotionMap[0];
			if(promo.PromoRuleType == 'Deposit'){
				itemAmountOnSignupRecord -= parseFloat(promo.actualDiscountValue);
			}
		}
	}else{
		for(var j=0; j < current.promotionMap.length; j++){
			var promo = current.promotionMap[j];
			if(promo.PromoRuleType == 'Sign Up' || promo.PromoRuleType == 'Registration' 
				|| promo.PromoRuleType == 'SetUpFee' || promo.PromoRuleType == 'JoinFee'){
				itemAmountOnSignupRecord -= parseFloat(promo.actualDiscountValue);
			}
		}
	}*/
			
	itemAmountOnSignupRecord = (itemAmountOnSignupRecord * current.quantity);
	return itemAmountOnSignupRecord;
}

function calculateItemAmountOnPayerRecord(current){
	
	//console.log(" calculateItemAmountOnPayerRecord >>  "+current.isRecurring);
	
	var itemAmountOnPayerRecord = 0;
	if(current.isRecurring){
		//console.log(" calculateItemAmountOnPayerRecord recurring ");
		itemAmountOnPayerRecord = parseFloat(current.signupPrice);
		
	}else{
		//console.log(" calculateItemAmountOnPayerRecord non recurring ");
		var sumOfAdditive = 0, sumOfSignupPromoDiscount = 0, sumOfOtherPromoDiscount = 0;
		if (current.item.signuppriceArr.length >0) {
			var pricingRule = getPricingRule(current.contact,current.item);
			if(pricingRule.additiveOrSubtractive && pricingRule.additiveOrSubtractive == 'Additive'){
				sumOfAdditive += current.signupPrice;

				if(current.promotionMap != undefined){
					for(var j=0; j < current.promotionMap.length; j++){
						var promo = current.promotionMap[j];
						if(promo.PromoRuleType == 'Sign Up'){
							sumOfSignupPromoDiscount += parseFloat(promo.actualDiscountValue);
						}
					}
				}
			}
		}
		
		if (current.item.registrationpriceArr.length >0) {
			if(current.item.registrationpriceArr[0].additiveOrSubtractive && current.item.registrationpriceArr[0].additiveOrSubtractive == 'Additive'){
				sumOfAdditive += parseFloat(current.registrationPrice);
				
				if(current.promotionMap != undefined){
					for(var j=0; j < current.promotionMap.length; j++){
						var promo = current.promotionMap[j];
						if(promo.PromoRuleType == 'Registration'){
							sumOfOtherPromoDiscount += parseFloat(promo.actualDiscountValue);
						}
					}
				}
			}
		}
		
		if (current.setupFee > 0 && current.item.setupfeepriceArr.length >0) {
			if(current.item.setupfeepriceArr[0].additiveOrSubtractive && current.item.setupfeepriceArr[0].additiveOrSubtractive == 'Additive'){
				sumOfAdditive += parseFloat(current.setupFee);
				
				if(current.promotionMap != undefined){
					for(var j=0; j < current.promotionMap.length; j++){
						var promo = current.promotionMap[j];
						if(promo.PromoRuleType == 'SetUpFee'){
							sumOfOtherPromoDiscount += parseFloat(promo.actualDiscountValue);
						}
					}
				}
			}
		}
		
		if (current.joinFee > 0) {
			sumOfAdditive += parseFloat(current.joinFee);
			
			if(current.promotionMap != undefined){
				for(var j=0; j < current.promotionMap.length; j++){
					var promo = current.promotionMap[j];
					if(promo.PromoRuleType == 'JoinFee'){
						sumOfOtherPromoDiscount += parseFloat(promo.actualDiscountValue);
					}
				}
			}
		}
		
		itemAmountOnPayerRecord = (parseFloat(sumOfAdditive) - (parseFloat(sumOfSignupPromoDiscount) + parseFloat(sumOfOtherPromoDiscount)) - parseFloat(current.FAamount)); 
	}
	
	itemAmountOnPayerRecord = (itemAmountOnPayerRecord * current.quantity);
	return itemAmountOnPayerRecord;
}

/*function getRemDiscountAmt(invoiceArray){
	if(invoiceArray != undefined && invoiceArray.length > 0){
		var invoice = invoiceArray[invoiceArray.length-1];
		return invoice.remDiscountAmt;
	}
	return 0;
}*/

function calculateItemAmountOnInvoiceRecord(current){
	
	//console.log(" calculateItemAmountOnInvoiceRecord >>  "+current.isRecurring);
	
	var invoiceArray = current.invoiceArr;
	
	var itemAmountOnInvoiceRecord = 0;
	if(current.isRecurring){
		//console.log(" calculateItemAmountOnInvoiceRecord recurring ");
		var sumOfAdditive = 0, totalSignupDiscountValue = 0, signupDiscountAmt = 0, sumOfSignupPromoMonthlyDiscount = 0, sumOfOtherPromoMonthlyDiscount = 0;
		
		if(current.promotionMap != undefined){
			for(var j=0; j < current.promotionMap.length; j++){
				var promo = current.promotionMap[j];
				if(promo.PromoRuleType == 'Sign Up'){
					totalSignupDiscountValue += parseFloat(promo.discountValue);
					sumOfSignupPromoMonthlyDiscount += parseFloat(promo.monthlyDiscountAmount);
				}else if(promo.PromoRuleType == 'Registration'){
					sumOfOtherPromoMonthlyDiscount += parseFloat(promo.monthlyDiscountAmount);
				}else if(promo.PromoRuleType == 'SetUpFee'){
					sumOfOtherPromoMonthlyDiscount += parseFloat(promo.monthlyDiscountAmount);
				}else if(promo.PromoRuleType == 'JoinFee'){
					sumOfOtherPromoMonthlyDiscount += parseFloat(promo.monthlyDiscountAmount);
				}
			}
		}
		
		//console.log(" totalSignupDiscountValue :: "+totalSignupDiscountValue+",  sumOfSignupPromoMonthlyDiscount  ::  "+sumOfSignupPromoMonthlyDiscount+",  sumOfOtherPromoMonthlyDiscount  ::  "+sumOfOtherPromoMonthlyDiscount);
		
		if(invoiceArray != undefined && invoiceArray.length > 0){
			
			if(invoiceArray.length == 1){	//	Single invoice
				
				var invoice = invoiceArray[0];
				
				//console.log(" INVOICE  (invoiceAmt:"+invoice.invoiceAmt+", billDate:"+invoice.billDate+", dueDate:"+invoice.dueDate);
				
				if (current.item.signuppriceArr.length >0) {
					var pricingRule = getPricingRule(current.contact,current.item);
					if(pricingRule.additiveOrSubtractive && pricingRule.additiveOrSubtractive == 'Additive'){
						sumOfAdditive += invoice.invoiceAmt;
						if(invoice.invoiceAmt <= sumOfSignupPromoMonthlyDiscount){
							signupDiscountAmt = invoice.invoiceAmt;
						}else{
							signupDiscountAmt = sumOfSignupPromoMonthlyDiscount;
						}
					}
				}
				
				if (current.item.registrationpriceArr.length >0) {
					if(current.item.registrationpriceArr[0].additiveOrSubtractive && current.item.registrationpriceArr[0].additiveOrSubtractive == 'Additive'){
						sumOfAdditive += parseFloat(current.registrationPrice);
						
						/*if(current.promotionMap != undefined){
							for(var j=0; j < current.promotionMap.length; j++){
								var promo = current.promotionMap[j];
								if(promo.PromoRuleType == 'Registration'){
									sumOfOtherPromoMonthlyDiscount += parseFloat(promo.monthlyDiscountAmount);
								}
							}
						}*/
					}
				}
				
				if (current.setupFee > 0 && current.item.setupfeepriceArr.length >0) {
					if(current.item.setupfeepriceArr[0].additiveOrSubtractive && current.item.setupfeepriceArr[0].additiveOrSubtractive == 'Additive'){
						sumOfAdditive += parseFloat(current.setupFee);
						
						/*if(current.promotionMap != undefined){
							for(var j=0; j < current.promotionMap.length; j++){
								var promo = current.promotionMap[j];
								if(promo.PromoRuleType == 'SetUpFee'){
									sumOfOtherPromoMonthlyDiscount += parseFloat(promo.monthlyDiscountAmount);
								}
							}
						}*/
					}
				}
				
				if (current.joinFee > 0) {
					sumOfAdditive += parseFloat(current.joinFee);
					
					/*if(current.promotionMap != undefined){
						for(var j=0; j < current.promotionMap.length; j++){
							var promo = current.promotionMap[j];
							if(promo.PromoRuleType == 'JoinFee'){
								sumOfOtherPromoMonthlyDiscount += parseFloat(promo.monthlyDiscountAmount);
							}
						}
					}*/
				}
				
				var actualAmtOnInvoice = (parseFloat(sumOfAdditive) - (parseFloat(signupDiscountAmt) - parseFloat(sumOfOtherPromoMonthlyDiscount)));
				
				invoice.remDiscountAmt = parseFloat(totalSignupDiscountValue) - parseFloat(signupDiscountAmt);
				invoice.invoiceAmt = parseFloat(actualAmtOnInvoice) * current.quantity;
				
				invoiceArray[0] = invoice;
				
			}else if(invoiceArray.length == 2){				//	2 invoices
				
				//	Process invoice 1
				var invoice1 = invoiceArray[0];
				
				//console.log(" INVOICE 1 (invoiceAmt:"+invoice1.invoiceAmt+", billDate:"+invoice1.billDate+", dueDate:"+invoice1.dueDate);
				
				var sumOfSignupPromoMonthlyDiscountForInvoice1 = 0, sumOfSignupPromoMonthlyDiscountForInvoice1Remaining = 0, sumOfSignupPromoMonthlyDiscountForInvoice2 = 0;
				
				if (current.item.signuppriceArr.length >0) {
					var pricingRule = getPricingRule(current.contact,current.item);
					if(pricingRule.additiveOrSubtractive && pricingRule.additiveOrSubtractive == 'Additive'){
						sumOfAdditive += invoice1.invoiceAmt;
						
						if(invoice1.invoiceAmt > 0){
							if(parseFloat(invoice1.invoiceAmt) <= parseFloat(sumOfSignupPromoMonthlyDiscount)){
								sumOfSignupPromoMonthlyDiscountForInvoice1 = parseFloat(invoice1.invoiceAmt);
								//sumOfSignupPromoMonthlyDiscountForInvoice1Remaining = parseFloat(sumOfSignupPromoMonthlyDiscount) - parseFloat(sumOfSignupPromoMonthlyDiscountForInvoice1);
							}else{
								sumOfSignupPromoMonthlyDiscountForInvoice1 = parseFloat(sumOfSignupPromoMonthlyDiscount);
							}
						}
					}
				}
				
				if (current.item.registrationpriceArr.length >0) {
					if(current.item.registrationpriceArr[0].additiveOrSubtractive && current.item.registrationpriceArr[0].additiveOrSubtractive == 'Additive'){
						sumOfAdditive += parseFloat(current.registrationPrice);
						
						/*if(current.promotionMap != undefined){
							for(var j=0; j < current.promotionMap.length; j++){
								var promo = current.promotionMap[j];
								if(promo.PromoRuleType == 'Registration'){
									sumOfOtherPromoMonthlyDiscount += parseFloat(promo.monthlyDiscountAmount);
								}
							}
						}*/
					}
				}
				
				if (current.setupFee > 0 && current.item.setupfeepriceArr.length >0) {
					if(current.item.setupfeepriceArr[0].additiveOrSubtractive && current.item.setupfeepriceArr[0].additiveOrSubtractive == 'Additive'){
						sumOfAdditive += parseFloat(current.setupFee);
						
						/*if(current.promotionMap != undefined){
							for(var j=0; j < current.promotionMap.length; j++){
								var promo = current.promotionMap[j];
								if(promo.PromoRuleType == 'SetUpFee'){
									sumOfOtherPromoMonthlyDiscount += parseFloat(promo.monthlyDiscountAmount);
								}
							}
						}*/
					}
				}
				
				if (current.joinFee > 0) {
					sumOfAdditive += parseFloat(current.joinFee);
					
					/*if(current.promotionMap != undefined){
						for(var j=0; j < current.promotionMap.length; j++){
							var promo = current.promotionMap[j];
							if(promo.PromoRuleType == 'JoinFee'){
								sumOfOtherPromoMonthlyDiscount += parseFloat(promo.monthlyDiscountAmount);
							}
						}
					}*/
				}
				
				//console.log("  sumOfSignupPromoMonthlyDiscountForInvoice1  ::  "+sumOfSignupPromoMonthlyDiscountForInvoice1+",  sumOfSignupPromoMonthlyDiscountForInvoice1Remaining  ::  "+sumOfSignupPromoMonthlyDiscountForInvoice1Remaining);
				
				
				
				var actualAmtOnInvoice1 = (parseFloat(sumOfAdditive) - (parseFloat(sumOfSignupPromoMonthlyDiscountForInvoice1) - parseFloat(sumOfOtherPromoMonthlyDiscount)));
				invoice1.invoiceDiscountAmt = sumOfSignupPromoMonthlyDiscountForInvoice1;
				invoice1.remDiscountAmt = parseFloat(totalSignupDiscountValue) - parseFloat(sumOfSignupPromoMonthlyDiscountForInvoice1);
				invoice1.invoiceAmt = actualAmtOnInvoice1 * current.quantity;
				invoiceArray[0] = invoice1;
				
				
				//console.log("  1st Invoice ==> totalSignupDiscountValue "+totalSignupDiscountValue+", sumOfSignupPromoMonthlyDiscountForInvoice1 "+sumOfSignupPromoMonthlyDiscountForInvoice1);
				
				// Process invoice 2
				var invoice2 = invoiceArray[1];
				//console.log(" INVOICE 2 (invoiceAmt:"+invoice2.invoiceAmt+", billDate:"+invoice2.billDate+", dueDate:"+invoice2.dueDate);
				
				if(invoice2.invoiceAmt > 0){
					
					var sumOfSignupPromoMonthlyDiscount1 = sumOfSignupPromoMonthlyDiscount; // + sumOfSignupPromoMonthlyDiscountForInvoice1Remaining;
					
					if(parseFloat(invoice2.invoiceAmt) <= parseFloat(sumOfSignupPromoMonthlyDiscount1)){
						sumOfSignupPromoMonthlyDiscountForInvoice2 = parseFloat(invoice2.invoiceAmt);
						//sumOfSignupPromoMonthlyDiscountForInvoice1Remaining = parseFloat(sumOfSignupPromoMonthlyDiscount) - parseFloat(sumOfSignupPromoMonthlyDiscountForInvoice1);
					}else{
						sumOfSignupPromoMonthlyDiscountForInvoice2 = parseFloat(sumOfSignupPromoMonthlyDiscount1);
					}
					
					//console.log("  sumOfSignupPromoMonthlyDiscountForInvoice2  ::  "+sumOfSignupPromoMonthlyDiscountForInvoice2);
					
					var actualAmtOnInvoice2 = parseFloat(invoice2.invoiceAmt) - parseFloat(sumOfSignupPromoMonthlyDiscountForInvoice2);
					invoice2.invoiceDiscountAmt = sumOfSignupPromoMonthlyDiscountForInvoice2;
					invoice2.remDiscountAmt = parseFloat(invoice1.remDiscountAmt) - sumOfSignupPromoMonthlyDiscountForInvoice2; 
					invoice2.invoiceAmt = actualAmtOnInvoice2 * current.quantity;
					invoiceArray[1] = invoice2;
				}
			}
		}
		
	}else{
		//console.log(" calculateItemAmountOnInvoiceRecord non recurring ");
		var sumOfAdditive = 0, sumOfSignupPromoDiscount = 0, sumOfOtherPromoDiscount = 0;
		
		if(invoiceArray != undefined && invoiceArray.length > 0){
			
			if(invoiceArray.length == 1){	//	Single invoice
				
				var invoice = invoiceArray[0];
		
				if (current.item.signuppriceArr.length >0) {
					var pricingRule = getPricingRule(current.contact,current.item);
					if(pricingRule.additiveOrSubtractive && pricingRule.additiveOrSubtractive == 'Additive'){
						sumOfAdditive += current.signupPrice;
		
						if(current.promotionMap != undefined){
							for(var j=0; j < current.promotionMap.length; j++){
								var promo = current.promotionMap[j];
								if(promo.PromoRuleType == 'Sign Up'){
									sumOfSignupPromoDiscount += parseFloat(promo.actualDiscountValue);
								}
							}
						}
					}
				}
				
				if (current.item.registrationpriceArr.length >0) {
					if(current.item.registrationpriceArr[0].additiveOrSubtractive && current.item.registrationpriceArr[0].additiveOrSubtractive == 'Additive'){
						sumOfAdditive += parseFloat(current.registrationPrice);
						
						if(current.promotionMap != undefined){
							for(var j=0; j < current.promotionMap.length; j++){
								var promo = current.promotionMap[j];
								if(promo.PromoRuleType == 'Registration'){
									sumOfOtherPromoDiscount += parseFloat(promo.actualDiscountValue);
								}
							}
						}
					}
				}
				
				if (current.setupFee > 0 && current.item.setupfeepriceArr.length >0) {
					if(current.item.setupfeepriceArr[0].additiveOrSubtractive && current.item.setupfeepriceArr[0].additiveOrSubtractive == 'Additive'){
						sumOfAdditive += parseFloat(current.setupFee);
						
						if(current.promotionMap != undefined){
							for(var j=0; j < current.promotionMap.length; j++){
								var promo = current.promotionMap[j];
								if(promo.PromoRuleType == 'SetUpFee'){
									sumOfOtherPromoDiscount += parseFloat(promo.actualDiscountValue);
								}
							}
						}
					}
				}
				
				if (current.joinFee > 0) {
					sumOfAdditive += parseFloat(current.joinFee);
					
					if(current.promotionMap != undefined){
						for(var j=0; j < current.promotionMap.length; j++){
							var promo = current.promotionMap[j];
							if(promo.PromoRuleType == 'JoinFee'){
								sumOfOtherPromoDiscount += parseFloat(promo.actualDiscountValue);
							}
						}
					}
				}
				
				itemAmountOnInvoiceRecord = (parseFloat(sumOfAdditive) - (parseFloat(sumOfSignupPromoDiscount) + parseFloat(sumOfOtherPromoDiscount)));
				invoice.remDiscountAmt = 0; 
				invoice.invoiceAmt = (itemAmountOnInvoiceRecord * current.quantity);
			}
		}
	}
	
	return itemAmountOnInvoiceRecord;
}

/*function calculateItemAmountOnInvoiceForRecurring(current){
	
	var itemAmountOnInvoiceForRecurring = 0, sumOfAdditive = 0, sumOfSubtractive = 0, promoDiscount = 0;
	
	if (current.item.signuppriceArr.length >0) {
		var pricingRule = getPricingRule(current.contact,current.item);
		if(pricingRule.additiveOrSubtractive && pricingRule.additiveOrSubtractive == 'Subtractive'){
			if(current.proRatedSignupPriceForFutureSelectedStartDate > 0){
				sumOfSubtractive += current.proRatedSignupPriceForFutureSelectedStartDate;
			}else{
				sumOfSubtractive += current.signupPrice;
			}
		}else {
			if(current.proRatedSignupPriceForFutureSelectedStartDate > 0){
				sumOfAdditive += current.proRatedSignupPriceForFutureSelectedStartDate;
			}else{
				sumOfAdditive += current.signupPrice;
			}
			
			if(current.promotionMap != undefined){
				for(var j=0; j < current.promotionMap.length; j++){
					var promo = current.promotionMap[j];
					if(promo.PromoRuleType == 'Sign Up'){
						promoDiscount += parseFloat(promo.actualDiscountValue);
					}
				}
			}
		}
	}
	
	if (current.item.registrationpriceArr.length >0) {
		if(current.item.registrationpriceArr[0].additiveOrSubtractive && current.item.registrationpriceArr[0].additiveOrSubtractive == 'Subtractive'){
			sumOfSubtractive += parseFloat(current.registrationPrice);
		}else {
			sumOfAdditive += parseFloat(current.registrationPrice);
			
			if(current.promotionMap != undefined){
				for(var j=0; j < current.promotionMap.length; j++){
					var promo = current.promotionMap[j];
					if(promo.PromoRuleType == 'Registration'){
						promoDiscount += parseFloat(promo.actualDiscountValue);
					}
				}
			}
		}
	}

	
	if (current.setupFee > 0 && current.item.setupfeepriceArr.length >0) {
		if(current.item.setupfeepriceArr[0].additiveOrSubtractive && current.item.setupfeepriceArr[0].additiveOrSubtractive == 'Subtractive'){
			sumOfSubtractive += parseFloat(current.setupFee);
		}else {
			sumOfAdditive += parseFloat(current.setupFee);
			
			if(current.promotionMap != undefined){
				for(var j=0; j < current.promotionMap.length; j++){
					var promo = current.promotionMap[j];
					if(promo.PromoRuleType == 'SetUpFee'){
						promoDiscount += parseFloat(promo.actualDiscountValue);
					}
				}
			}
		}
	}
	

	if (current.joinFee > 0) {
		sumOfAdditive += parseFloat(current.joinFee);
		
		if(current.promotionMap != undefined){
			for(var j=0; j < current.promotionMap.length; j++){
				var promo = current.promotionMap[j];
				if(promo.PromoRuleType == 'JoinFee'){
					promoDiscount += parseFloat(promo.actualDiscountValue);
				}
			}
		}
	}
	
	itemAmountOnInvoiceRecord = (parseFloat(sumOfAdditive) - parseFloat(sumOfSubtractive) - parseFloat(promoDiscount) - parseFloat(current.FAamount));	// - parseFloat(current.discount1) 

	itemAmountOnInvoiceRecord = (itemAmountOnInvoiceRecord * current.quantity); //- parseFloat(current.autoDiscount)
	
	return itemAmountOnInvoiceRecord;
}*/

/*
function addcccard (){
//alert($('#addCardInfoForm').serialize());
	var payId = 0;
	$.ajax({
		  type: "POST",
		  async:false,
		  url: "addCardInfo",
		  data: $('#addCardInfoForm').serialize(),
		  success: function( data ) {
			  data_split = data.split("__S__");
		  	  if(data_split[0]=='SUCCESS'){
			  	  $("#tcErrorSpan").css("display", "none");		
				  $("#tcErrorSpan" ).html("");	
			  	  $("#tcSuccessSpan").css("display", "block");		
				  $("#tcSuccessSpan" ).html("Card Information Added successfully");
				  $(".k-loading-mask").hide();
				  payId = data_split[1];
			  	  //setTimeout(function(){location.reload();}, 7000);
			  	  
		  	  }else if(data == 'NOT_FOUND'){
		  		  $("#tcSuccessSpan").css("display", "none");		
				  $("#tcSuccessSpan" ).html("");	
				  $("#tcErrorSpan").css("display", "block");		
				  $( "#tcErrorSpan" ).html("Payment Information Not Found.");
				  $(".k-loading-mask").hide();
				  
		  	  }else {
		  		  $("#tcSuccessSpan").css("display", "none");		
				  $("#tcSuccessSpan" ).html("");	
				  $("#tcErrorSpan").css("display", "block");		
				  $( "#tcErrorSpan" ).html("Please verify you entered information correctly");
				  $(".k-loading-mask").hide();
		  	  }
		  },
		  error: function( xhr,status,error ){
			  $("#tcSuccessSpan").css("display", "none");		
			  $("#tcSuccessSpan" ).html("");	
			  $("#tcErrorSpan").css("display", "block");		
			  $( "#tcErrorSpan" ).html("There was some error. Please try again later");
			  $(".k-loading-mask").hide();
		  }
	});
	
	return payId;
}
*/
function showErrorMessageForSignup(msg){
	$("#tcErrorSpan").css("display", "block");		
	$("#tcErrorSpan").html(msg);
	$("#tcSuccessSpan").css("display", "none");
}

function gotosignupprogram(){
	programRegistrationAction();
	location.href='program';
}

function gotochildcareprogram(){
	childCareRegistrationAction(); 
	location.href='childcare';
}