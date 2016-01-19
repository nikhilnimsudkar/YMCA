function getAutoPromotionMap(itemId, billingFreqency, amountMap, selectedProductTotalPrice, urlPromoCode){
	var isRecurring = checkIfRecurring(billingFreqency);
	
	var selectItemContact = '';
	if(urlPromoCode != null && urlPromoCode != undefined && urlPromoCode != ''){
		selectItemContact = getURLItemContactPromo(itemId, 0, urlPromoCode);
	}
	console.log(" itemId = "+itemId+",  isRecurring = "+isRecurring+",  selectItemContact = "+selectItemContact);
	
	var amtJson = JSON.parse(amountMap);
	
	console.log(" signupPrice = "+amtJson.signupPrice);
	console.log(" joinFee = "+amtJson.joinFee);
	console.log(" setupfee = "+amtJson.setupFee);
	console.log(" registrationFee = "+amtJson.registrationFee);
	console.log(" depositAmount = "+amtJson.depositAmount);
	
	$("#isRecurringHiddenInput").val(isRecurring);
	
	var contactId = $("#signupContactId").val();
	console.log("   contactId   ::   "+contactId);
	//var setupfee = 0;
	var selectedStartDate = '';
	var cartItems = [];
	var promotionMap = {};
	$.ajax({
		  type: "GET",
		  async: false,
		  url:"getPromoMap?itemId="+itemId+"&contactId="+contactId+"&isAuto=true&isRecurring="+isRecurring+"&amountJSON="+amountMap+"&selectedStartDate="+selectedStartDate+"&lstCartItem="+cartItems.join('_AND_')+"&urlItemContactPromo="+selectItemContact, 
		  dataType: "json",
		  success: function( data ) {
			  
			  //  console.log("  data  "+data);
			  console.log("  JSON.stringify(data)   ::   "+JSON.stringify(data));
			  
			  //var json = JSON.parse(data);
			  if(data != null)
				  promotionMap = data.promos;
			  
			  //  console.log("  promotionMap   ::::   "+promotionMap);
			  
		  },
		  error: function( xhr,status,error ){
			  console.log(xhr);
		  }
	});
	
	return promotionMap;
	
	//var totalPriceWithoutDiscount = selectedProductTotalPrice;
	
	//refreshPaymentInfo(promotionMap, amountMap, totalPriceWithoutDiscount);
}

function getManualPromoMap(selectedProdctId, billingFreqency, amountMap){
	var manualPromos = null;
	var isRecurring = checkIfRecurring(billingFreqency);
	var selectedStartDate = '';
	var cartItems = [];
	var contactId = $("#signupContactId").val();
	console.log("   contactId manual  ::   "+contactId);
	$.ajax({
		  type: "GET",
		  async: false,
		  url:"getPromoMap?itemId="+selectedProdctId+"&contactId="+contactId+"&isAuto=false&isRecurring="+isRecurring+"&amountJSON="+amountMap+"&selectedStartDate="+selectedStartDate+"&lstCartItem="+cartItems.join('_AND_')+"&urlItemContactPromo=", //+"&setupfee="+setupfee+"&registrationPrice="+registrationFee+"&depositAmount="+depositAmount+"&joinFee="+selectedJoiningFeeInfo+"&selectedStartDate="+selectedStartDate,
		  dataType: "json",
		  success: function( data ) {
			  
			  //  console.log("  data  "+data);
			  //console.log("  JSON.stringify(data)   ::   "+JSON.stringify(data));
			  
			  //var json = JSON.parse(data);
			  manualPromos = data.promos;
			  
			  //  console.log("  promotionMap   ::::   "+promotionMap);
			  
		  },
		  error: function( xhr,status,error ){
			  console.log(xhr);
		  }
	});
	return manualPromos;
}

function refreshPaymentInfo(promotionMap, amountMap, totalPriceWithoutDiscount){
	promotionMap =  validatePromo('callForMembership', null, promotionMap, amountMap);
	var totalDiscount = applyPromos(promotionMap);
	console.log("   totalPriceWithoutDiscount  "+totalPriceWithoutDiscount);
	console.log("   totalDiscount  "+totalDiscount);
	totalPrice = parseFloat(totalPriceWithoutDiscount) - parseFloat(totalDiscount);
	
	//console.log("  totalPriceWithoutDiscount  "+totalPriceWithoutDiscount+"  totalDiscount  "+totalDiscount+"  totalPrice  "+totalPrice);
	console.log("   totalPrice  "+totalPrice);
	$("#totalDiscountHiddenInput").val(totalDiscount);
	$("#paymentAmountSpan").html(totalPrice);
	$("#paymentTotalPriceTd").html(totalPrice);	
	$("#sumTotalPriceTd").html(totalPrice); 	
	
	var finalAmount = parseFloat($("#sumTotalPriceTd").text());
	
	$("#faAmtHiddenInput").val(parseFloat($("#faAmountTD").text()));
	
	if($("#faAmountTD").text() != ''){
		finalAmount = finalAmount - parseFloat($("#faAmountTD").text());
	}  	
	
	$("#sumFinalAmountTd").html(finalAmount);
	
	$("#promotionMapInput").attr("value", convertToPromoString(promotionMap));
}

function applyPromos(promotionMap){
	
	var totalDiscount = 0;
	
	if(promotionMap != undefined){
		
		var signUpPromoDiscount = "", signUpPromoCount = 0, signUpPromoDiscountValue = 0;
		var depositPromoDiscount = "", depositPromoCount = 0;
		var setupFeePromoDiscount = "", setupFeePromoCount = 0;
		var joinFeePromoDiscount = "", joinFeePromoCount = 0;
		var regFeePromoDiscount = "", regFeePromoCount = 0;
		
		
		for(var j=0; j < promotionMap.length; j++){
			
			var promo = promotionMap[j];
			console.log("  PROMO ::  "+promo.PromoRuleType+"  "+promo.discountValue);
			if(promo.PromoRuleType == 'Sign Up'){
				//console.log(" Sign up :: discount value ::  "+ parseFloat(promo.discountValue));
				signUpPromoCount++;
				signUpPromoDiscount += "<tr><td width='60%' title="+promo.Description+">"+promo.PromoCode+"</td>";
				signUpPromoDiscount += "<td width='40%' align='right' style='padding-right: 0px;'><span style='color: red;'>-$</span><span style='color: red;'>"+promo.discountValue+"</span></td></tr>";
				if(promo.noOfDiscountMonths > 0){
					signUpPromoDiscount += "<tr><td width='60%' >No of discount months</td>";
					signUpPromoDiscount += "<td width='40%' align='right' style='padding-right: 0px;'><span>"+promo.noOfDiscountMonths+"</span></td></tr>";
				}
				signUpPromoDiscountValue += parseFloat(promo.discountValue);
				totalDiscount += parseFloat(promo.discountValue);
			}else if(promo.PromoRuleType == 'Deposit'){
				//console.log(" Deposit :: discount value ::  "+ parseFloat(promo.discountValue));
				depositPromoCount++;
				depositPromoDiscount += "<tr><td width='60%'>"+promo.PromoCode+"</td>";
				depositPromoDiscount += "<td width='40%' align='right' style='padding-right: 0px;'><span style='color: red;'>-$</span><span style='color: red;'>"+promo.discountValue+"</span></td></tr>";
				if(promo.noOfDiscountMonths > 0){
					depositPromoDiscount += "<tr><td width='60%' >No of discount months</td>";
					depositPromoDiscount += "<td width='40%' align='right' style='padding-right: 0px;'><span>"+promo.noOfDiscountMonths+"</span></td></tr>";
				}
				totalDiscount += parseFloat(promo.discountValue);
			}/*else if(promo.PromoRuleType == 'SetUpFee'){
				//console.log(" Set up fee :: discount value ::  "+ parseFloat(promo.discountValue));
				setupFeePromoCount++;
				setupFeePromoDiscount += "<tr><td width='60%'>"+promo.PromoCode+"</td>";
				setupFeePromoDiscount += "<td width='40%' align='right' style='padding-right: 0px;'><span style='color: red;'>-$</span><span style='color: red;'>"+promo.discountValue+"</span></td></tr>";
				totalDiscount += parseFloat(promo.discountValue);
			}*/else if(promo.PromoRuleType == 'Registration'){
				//console.log(" Reg :: discount value ::  "+ parseFloat(promo.discountValue));
				regFeePromoCount++;
				regFeePromoDiscount += "<tr><td width='60%'>"+promo.PromoCode+"</td>";
				regFeePromoDiscount += "<td width='40%' align='right' style='padding-right: 0px;'><span style='color: red;'>-$</span><span style='color: red;'>"+promo.discountValue+"</span></td></tr>";
				if(promo.noOfDiscountMonths > 0){
					regFeePromoDiscount += "<tr><td width='60%' >No of discount months</td>";
					regFeePromoDiscount += "<td width='40%' align='right' style='padding-right: 0px;'><span>"+promo.noOfDiscountMonths+"</span></td></tr>";
				}
				totalDiscount += parseFloat(promo.discountValue);
			}else if(promo.PromoRuleType == 'JoinFee'){
				//console.log(" Join fee :: discount value ::  "+ parseFloat(promo.discountValue));
				joinFeePromoCount++;
				joinFeePromoDiscount += "<tr><td width='60%'>"+promo.PromoCode+"</td>";
				joinFeePromoDiscount += "<td width='40%' align='right' style='padding-right: 0px;'><span style='color: red;'>-$</span><span style='color: red;'>"+promo.discountValue+"</span></td></tr>";
				if(promo.noOfDiscountMonths > 0){
					joinFeePromoDiscount += "<tr><td width='60%' >No of discount months</td>";
					joinFeePromoDiscount += "<td width='40%' align='right' style='padding-right: 0px;'><span>"+promo.noOfDiscountMonths+"</span></td></tr>";
				}
				totalDiscount += parseFloat(promo.discountValue);
			}
		}
		
		
		var otherPromoDiscountValue = totalDiscount - signUpPromoDiscountValue;
		
		$("#signUpPromoDiscountHiddenInput").val(signUpPromoDiscountValue);
		$("#otherPromoDiscountHiddenInput").val(otherPromoDiscountValue);
		
		//$("#sumOfAdditivesHiddenInput").val(otherPromoDiscountValue);
		
		if(signUpPromoCount > 0){
			$("#SignUpPromoDiscount").show();
			$("#SignUpPromoDiscountTable").html(signUpPromoDiscount);
		}else{
			$("#SignUpPromoDiscountTable").html("");
			$("#SignUpPromoDiscount").hide();
		}
		if(depositPromoCount > 0){
			$("#DepositPromoDiscount").show();
			$("#DepositPromoDiscountTable").html(depositPromoDiscount);
		}else{
			$("#DepositPromoDiscountTable").html("");
			$("#DepositPromoDiscount").hide();
		}
		/*if(setupFeePromoCount > 0){
			$("#SetUpFeePromoDiscount").show();
			$("#SetUpFeePromoDiscountTable").html(setupFeePromoDiscount);
		}*/
		if(regFeePromoCount > 0){
			$("#RegFeePromoDiscount").show();
			$("#RegFeePromoDiscountTable").html(regFeePromoDiscount);
		}else{
			$("#RegFeePromoDiscountTable").html("");
			$("#RegFeePromoDiscount").hide();
		}
		if(joinFeePromoCount > 0){
			$("#JoinFeePromoDiscount").show();
			$("#JoinFeePromoDiscountTable").html(joinFeePromoDiscount);
		}else{
			$("#JoinFeePromoDiscountTable").html("");
			$("#JoinFeePromoDiscount").hide();
		}
	}
	return totalDiscount;
}

function populateChangePaymentInfoSection(){

	console.log("  populatePaymentInfoSection  starts  ");
	var joinFee = 0, signupPrice = 0, proratedSignupPrice = 0, regFee = 0, depositAmt = 0, setupfee = 0, billingFrequency = '';
	var itemDetailId =  $("#productIdHidInput").val();
	var paymentFrequency =  $("#paymentFrequency").val();
	var urlPromoCode =  $("#urlPromoCode").val();
	var manualPromoCode = $("#c_promocode").val();
	
	var contacts = getSelectedContacts();
	console.log("  contacts  =>  "+contacts);
	
	$("#signupContactId").val(contacts);
	
	if(paymentFrequency == 'Monthly'){
		if($("#joinFeeMonthly").val() != ''){
			joinFee = parseFloat($("#joinFeeMonthly").val());
		}
		if($("#signupPriceMonthly").val() != ''){
			signupPrice = parseFloat($("#signupPriceMonthly").val());
		}
		if($("#monthlyBillingFreqency").val() != ''){
			billingFrequency = parseFloat($("#monthlyBillingFreqency").val());
		}
	}else if(paymentFrequency == 'Yearly'){
		if($("#joinFeeYealy").val() != ''){
			joinFee = parseFloat($("#joinFeeYealy").val());
		}
		if($("#signupPriceYearly").val() != ''){
			signupPrice = parseFloat($("#signupPriceYearly").val());
		}
		if($("#annualBillingFreqency").val() != ''){
			billingFrequency = parseFloat($("#annualBillingFreqency").val());
		}
	}
	if($("#sumProRatePriceTd").text() != ''){
		proratedSignupPrice = parseFloat($("#sumProRatePriceTd").text());
		console.log("   proratedSignupPrice    "+proratedSignupPrice);
	}
	if($("#registrationFee").val() != ''){
		regFee = parseFloat($("#registrationFee").val());
	}
	if($("#depositAmount").val() != ''){
		depositAmt = parseFloat($("#depositAmount").val());
	}
	
	if(regFee > 0){
		$("#sumRegistrationFeeTR").show();
		$("#sumRegistrationFee").html(regFee);	
	}
	if(depositAmt > 0){
		$("#sumDepositAmountTR").show();
		$("#sumDepositAmount").html(depositAmt);
	}
	if(joinFee > 0){
		$("#sumJoinFeeTR").show();
		$("#sumJoinFeeTd").html(joinFee);
	}
	if(signupPrice > 0){
		$("#sumTierPriceTd").html(signupPrice);
	}
	
	var sumOfAdditives = parseFloat(signupPrice) + parseFloat(joinFee) + parseFloat(setupfee) + parseFloat(regFee);
	
	console.log("  sumOfAdditives   "+sumOfAdditives);
	
	$("#sumOfAdditivesHiddenInput").val(sumOfAdditives);
	
	if(proratedSignupPrice > 0){
		signupPrice = proratedSignupPrice;
	}
	
	var amountMap = '{ "signupPrice":"'+signupPrice+'", "setupFee":"'+setupfee+'", "joinFee":"'+joinFee+'", "registrationFee":"'+regFee+'", "depositAmount":"'+depositAmt+'"}';
	
	console.log("  signupPrice  ::  "+signupPrice+",  joinFee  ::  "+joinFee+",  regFee  ::  "+regFee+",  depositAmt  ::  "+depositAmt);
	var totalPriceWithoutDiscount = signupPrice + joinFee + regFee + depositAmt + setupfee;
	var promotionMap = getAutoPromotionMap(itemDetailId, billingFrequency, amountMap, totalPriceWithoutDiscount, urlPromoCode);
	
	if(manualPromoCode != null && manualPromoCode != undefined && manualPromoCode != ''){
		var manualPromos = getManualPromoMap(itemDetailId, billingFrequency, amountMap);
		var found = false;
		if(manualPromos != null && manualPromos != undefined){
			for(var j=0; j < manualPromos.length; j++){
				var promo = manualPromos[j];
				console.log("  PROMO ::  "+promo.PromoRuleType+"  "+promo.discountValue);
				if(promo.PromoCode == manualPromoCode){
					
					var isAlreadyExist = false;
					for(var a=0; a<promotionMap.length; a++){
						if(promotionMap[a].PromoCode == promo.PromoCode){
							isAlreadyExist = true;
						}
					}
					if(!isAlreadyExist){
						promotionMap.push(promo);
					}
					found = true;
				}
			}
		}
		if (!found) {
			  $("#tcSuccessSpan").css("display", "none");		
			  $("#tcSuccessSpan" ).html("");	
			  $("#tcErrorSpan").css("display", "block");		
			  $( "#tcErrorSpan" ).html("The Promo code you enetered is invalid");
		}
	}
	console.log("  promotionMap   ::::   "+promotionMap);
	promotionMap =  validatePromo('callForMembership', null, promotionMap, amountMap);
	var totalDiscount = applyPromos(promotionMap);
	console.log("   totalPriceWithoutDiscount  "+totalPriceWithoutDiscount);
	console.log("   totalDiscount  "+totalDiscount);
	totalPrice = parseFloat(totalPriceWithoutDiscount) - parseFloat(totalDiscount);
	
	//console.log("  totalPriceWithoutDiscount  "+totalPriceWithoutDiscount+"  totalDiscount  "+totalDiscount+"  totalPrice  "+totalPrice);
	console.log("   totalPrice  "+totalPrice);
	$("#totalDiscountHiddenInput").val(Math.round(totalDiscount * 100) / 100);
	$("#paymentAmountSpan").html(Math.round(totalPrice * 100) / 100);
	$("#paymentTotalPriceTd").html(Math.round(totalPrice * 100) / 100);	
	$("#sumTotalPriceTd").html(Math.round(totalPrice * 100) / 100); 	
	
	var finalAmount = parseFloat($("#sumTotalPriceTd").text());
	
	$("#faAmtHiddenInput").val(parseFloat($("#faAmountTD").text()));
	
	if($("#faAmountTD").text() != ''){
		finalAmount = finalAmount - parseFloat($("#faAmountTD").text());
	}  	
	//var prorateAmt = $("#sumProRatePriceTd").text();
	$("#sumFinalAmountTd").html(finalAmount);
	
	$("#promotionMapInput").attr("value", convertToPromoString(promotionMap));
	
	console.log("  populatePaymentInfoSection  ends  ");

}

function populatePaymentInfoSection(){
	console.log("  populatePaymentInfoSection  starts  ");
	var joinFee = 0, signupPrice = 0, proratedSignupPrice = 0, regFee = 0, depositAmt = 0, setupfee = 0, billingFrequency = '';
	var itemDetailId =  $("#productIdHidInput").val();
	var paymentFrequency =  $("#paymentFrequency").val();
	var urlPromoCode =  $("#urlPromoCode").val();
	var manualPromoCode = $("#c_promocode").val();
	
	var contacts = getSelectedContacts();
	console.log("  contacts  =>  "+contacts);
	
	$("#signupContactId").val(contacts);
	
	if(paymentFrequency == 'Monthly'){
		if($("#joinFeeMonthly").val() != ''){
			joinFee = parseFloat($("#joinFeeMonthly").val());
		}
		if($("#signupPriceMonthly").val() != ''){
			signupPrice = parseFloat($("#signupPriceMonthly").val());
		}
		if($("#monthlyBillingFreqency").val() != ''){
			billingFrequency = parseFloat($("#monthlyBillingFreqency").val());
		}
	}else if(paymentFrequency == 'Yearly'){
		if($("#joinFeeYealy").val() != ''){
			joinFee = parseFloat($("#joinFeeYealy").val());
		}
		if($("#signupPriceYearly").val() != ''){
			signupPrice = parseFloat($("#signupPriceYearly").val());
		}
		if($("#annualBillingFreqency").val() != ''){
			billingFrequency = parseFloat($("#annualBillingFreqency").val());
		}
	}
	if($("#proratedSignupPrice").val() != ''){
		proratedSignupPrice = parseFloat($("#proratedSignupPrice").val());
		console.log("   proratedSignupPrice    "+proratedSignupPrice);
	}
	if($("#registrationFee").val() != ''){
		regFee = parseFloat($("#registrationFee").val());
	}
	if($("#depositAmount").val() != ''){
		depositAmt = parseFloat($("#depositAmount").val());
	}
	
	if(regFee > 0){
		$("#sumRegistrationFeeTR").show();
		$("#sumRegistrationFee").html(regFee);	
	}
	if(depositAmt > 0){
		$("#sumDepositAmountTR").show();
		$("#sumDepositAmount").html(depositAmt);
	}
	if(joinFee > 0){
		$("#sumJoinFeeTR").show();
		$("#sumJoinFeeTd").html(joinFee);
	}
	if(signupPrice > 0){
		$("#sumTierPriceTd").html(signupPrice);
	}
	
	var sumOfAdditives = parseFloat(signupPrice) + parseFloat(joinFee) + parseFloat(setupfee) + parseFloat(regFee);
	
	console.log("  sumOfAdditives   "+sumOfAdditives);
	
	$("#sumOfAdditivesHiddenInput").val(sumOfAdditives);
	
	if(proratedSignupPrice > 0){
		signupPrice = proratedSignupPrice;
	}
	
	var amountMap = '{ "signupPrice":"'+signupPrice+'", "setupFee":"'+setupfee+'", "joinFee":"'+joinFee+'", "registrationFee":"'+regFee+'", "depositAmount":"'+depositAmt+'"}';
	
	console.log("  signupPrice  ::  "+signupPrice+",  joinFee  ::  "+joinFee+",  regFee  ::  "+regFee+",  depositAmt  ::  "+depositAmt);
	var totalPriceWithoutDiscount = signupPrice + joinFee + regFee + depositAmt + setupfee;
	var promotionMap = getAutoPromotionMap(itemDetailId, billingFrequency, amountMap, totalPriceWithoutDiscount, urlPromoCode);
	
	if(manualPromoCode != null && manualPromoCode != undefined && manualPromoCode != ''){
		var manualPromos = getManualPromoMap(itemDetailId, billingFrequency, amountMap);
		var found = false;
		if(manualPromos != null && manualPromos != undefined){
			for(var j=0; j < manualPromos.length; j++){
				var promo = manualPromos[j];
				console.log("  PROMO ::  "+promo.PromoRuleType+"  "+promo.discountValue);
				if(promo.PromoCode == manualPromoCode){
					
					var isAlreadyExist = false;
					for(var a=0; a<promotionMap.length; a++){
						if(promotionMap[a].PromoCode == promo.PromoCode){
							isAlreadyExist = true;
						}
					}
					if(!isAlreadyExist){
						promotionMap.push(promo);
					}
					found = true;
				}
			}
		}
		if (!found) {
			  $("#tcSuccessSpan").css("display", "none");		
			  $("#tcSuccessSpan" ).html("");	
			  $("#tcErrorSpan").css("display", "block");		
			  $( "#tcErrorSpan" ).html("The Promo code you enetered is invalid");
		}
	}
	console.log("  promotionMap   ::::   "+promotionMap);
	promotionMap =  validatePromo('callForMembership', null, promotionMap, amountMap);
	var totalDiscount = applyPromos(promotionMap);
	console.log("   totalPriceWithoutDiscount  "+totalPriceWithoutDiscount);
	console.log("   totalDiscount  "+totalDiscount);
	totalPrice = parseFloat(totalPriceWithoutDiscount) - parseFloat(totalDiscount);
	
	//console.log("  totalPriceWithoutDiscount  "+totalPriceWithoutDiscount+"  totalDiscount  "+totalDiscount+"  totalPrice  "+totalPrice);
	console.log("   totalPrice  "+totalPrice);
	$("#totalDiscountHiddenInput").val(Math.round(totalDiscount * 100) / 100);
	$("#paymentAmountSpan").html(Math.round(totalPrice * 100) / 100);
	$("#paymentTotalPriceTd").html(Math.round(totalPrice * 100) / 100);	
	$("#sumTotalPriceTd").html(Math.round(totalPrice * 100) / 100); 	
	
	var finalAmount = parseFloat($("#sumTotalPriceTd").text());
	
	$("#faAmtHiddenInput").val(parseFloat($("#faAmountTD").text()));
	
	if($("#faAmountTD").text() != ''){
		finalAmount = finalAmount - parseFloat($("#faAmountTD").text());
	}  	
	
	$("#sumFinalAmountTd").html(finalAmount);
	
	$("#promotionMapInput").attr("value", convertToPromoString(promotionMap));
	
	console.log("  populatePaymentInfoSection  ends  ");
}

function getProductHeader(productName){
	var prodHeaderInfo = '';
	  if(productName != null && (productName =='One Adult w/ Kids' || productName =='Two Adults w/ Kids' || productName =='Three Adults w/ or w/o kids')){
		  if(productName =='One Adult w/ Kids'){
			  prodHeaderInfo = 'One Adult<br />w/ Kids';
		  }else if(productName =='Two Adults w/ Kids'){
			  prodHeaderInfo = 'Two Adults<br />w/ Kids';
		  }else{
			  prodHeaderInfo = 'Three Adults<br />w/ or w/o kids';
		  }
	  }else{
		  prodHeaderInfo = productName;
	  }
	  return prodHeaderInfo;
}

function getAreaType(location_RecordName){
	var areaType = '';
	if(location_RecordName == 'All Branches'){
		areaType = "AllArea";
	}else if(location_RecordName == 'Bay Area'){
		areaType = "BayArea";
	}else{
		areaType = "ThisBranchOnly";
	}
	return areaType;
}

function getPriceOptionHTML(signupPricingRuleList){
	var productPricingOptionRadioHtml = '';
	  if(signupPricingRuleList){
		  for(var s = 0; s<signupPricingRuleList.length; s++){
			  if(signupPricingRuleList[s].Monthly){
				  if(productPricingOptionRadioHtml.indexOf('Monthly') === -1 ){
					  productPricingOptionRadioHtml = productPricingOptionRadioHtml + '<option value="Monthly" selected="selected">Monthly</option>';
				  }    		    						  
			  }else if(signupPricingRuleList[s].Annual){
				  if(productPricingOptionRadioHtml.indexOf('Annual') === -1){
					  productPricingOptionRadioHtml = productPricingOptionRadioHtml + '<option value="Annual" >Annual</option>';
				  }
			  }
		  }
	  }
	  
	  return productPricingOptionRadioHtml;
}

function getSelectedContacts(){ 
	var contacts = ''; 
	$(document).find('select.selectContactDropDw').each(function(i, obj) {				
			var selectedContacts = $(obj).data("kendoDropDownList");
			var dropDownVal = selectedContacts.value();
			var dropDownArr = dropDownVal.split("\|\|");
			
			console.log("  dropDownArr[dropDownArr.length - 1]  =>  "+dropDownArr[dropDownArr.length - 1]);
			
			contacts = contacts + dropDownArr[dropDownArr.length - 1] + ',';
	});
	
	contacts = contacts.slice(0,-1);
	return contacts;
}