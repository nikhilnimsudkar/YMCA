
var items = new kendo.data.DataSource({
    schema: 
    { 
        model: {} 
    },
    transport: 
    { 
    	read: function(operation) {
            var data = operation.data.data || [];
            operation.success(data);
        }
    	/*read:  {
		  url: location.protocol+"//"+location.host+"/ymca-web/getallProducts",
          data: function() {
              return {
            	  
             	 ids : $.sessionStorage.getItem('itemDetailsId')
              }
          },
		  dataType: "json"
		}*/
		
    }
});

var contacts = new kendo.data.DataSource({
    schema: 
    { 
        model: {} 
    },
    transport: 
    { 
        read: function(operation) {
            var data = operation.data.data || [];
            operation.success(data);
        }
    }
});
var alldata = [];
if($.sessionStorage.getItem('cart')!=null){
	alldata = JSON.parse($.sessionStorage.getItem('cart'));
}

var cart = kendo.observable({
    contents: alldata,
    cleared: false,
	errMsg: "",
	
    contentsCount: function() {
        return this.get("contents").length;
    },

    add: function(item,contact,parentSignUpItem,promos) {
    	console.log(" cart add observed "+item.itemDetailsId+", "+contact.contactId);
    	
        var found = false;
        this.set("cleared", false);
		var errMsg = this.errMsg;
		
        for (var i = 0; i < this.contents.length; i ++) {
            var current = this.contents[i];
            if (current.item.itemDetailsId == item.itemDetailsId && current.contact.contactId == contact.contactId) {
                found = true;
                break;
            }
        }
		
		var cartObj = this;
        if (found) {
			errMsg = errMsg + "Program " + current.item.name + " and Contact " + current.contact.fname + " " + current.contact.lname + " already exists in cart.";
			$("#uniform-user_"+item.itemDetailsId+"_"+current.contact.contactId +" span").attr("class", "");
			$("#user_"+item.itemDetailsId+"_"+current.contact.contactId).attr('checked', false);
			cartObj.set("errMsg",errMsg);
			$("#dspErr").show();
			//setTimeout(function(){$("#dspErr").slideUp();}, 5000);
		}
		else {
			var signupfound = false;
			if(item.programType != 'Event' && item.preventDuplicateSignup == 'Yes' && item.category != 'Transportation'){
				$.ajax({ url: 'program/checkProgramSignUp', 
			         async: false,
			         data:{ contactId: contact.contactId, itemDetailId: item.itemDetailsId, type: item.programType, category: item.category, selectedDaysStr: item.dayId, daysPreventDuplicateSignupStr: item.daysPreventDuplicateSignup, selectedDaysOrDateStr: item.days },
			         dataType: 'json',
			         success: function(data) {
						if(data.resp) {
							if(data.duplicateDays && data.duplicateDays != ''){
								errMsg = errMsg + "You have already signup for Program " + item.name + " and Contact " + contact.fname + " " + contact.lname + " for "+data.duplicateDays+".\n";
							}else{
								errMsg = errMsg + "You have already signup for Program " + item.name + " and Contact " + contact.fname + " " + contact.lname + ".\n";
							}
							$("#uniform-user_"+item.itemDetailsId+"_"+contact.contactId +" span").attr("class", "");
							$("#user_"+item.itemDetailsId+"_"+contact.contactId).attr('checked', false);
							cartObj.set("errMsg",errMsg);
							$("#dspErr").show();
							//setTimeout(function(){$("#dspErr").slideUp();}, 5000);
							signupfound = true;
						}
			          }
			        });
			}
			/*
			signup.fetch(function() {
				var data = this.data();
				for (var s = 0; s < data.length; s ++) {
					if(item.subType != 'EVENT'){
						if(data[s].itemDetailsId == item.itemDetailsId && data[s].contactId == contact.contactId){
							errMsg = errMsg + "\r\nYou have already signup for Program " + item.name + " and Contact " + contact.fname + " " + contact.lname + ".";
							$("#uniform-user_"+contact.contactId +" span").attr("class", "");
							$("#user_"+contact.contactId).attr('checked', false);
							cartObj.set("errMsg",errMsg);
							$("#dspErr").slideDown();
							setTimeout(function(){$("#dspErr").slideUp();}, 5000);
							signupfound = true;
							break;
						}
					}
				}
				
			});*/
			
			if(!signupfound){
				
				var signupPrice = 0;
				var originalsignupPrice = 0;
				var joinFee = 0;
				var priceOption = "";
				var billingFrequency = '';
				//var registrationPrice = 0;
				var depositAmount = 0;
				var autoDiscount = 0;
				var packageSize = 0;
				var isProgramAlreadyStarted = false;
				var isSelectedStartDateIsInCurrentMonth = false;
				var proRatedSignupPrice = 0;
				var billingOptionVal = 'Automatic';
				var inServiceWithChildCareProperty = null;
				var signupPriceVal = 0;
				var uniqueId = item.itemDetailsId + "_" + contact.contactId;
				var noOfMonths= 0;
				
				if(isInServiceWithChildCareForContact){
					$.ajax({
						  type: "GET",
						  async: false,
						  url:"getSystemPropertiesByPicklistName?picklistName=IN_SERVICE_WITH_CHILD_CARE",
						  dataType: "json",
						  success: function( data ) {
							  if(data.length>0){
								  inServiceWithChildCareProperty = data;
							  }
						  },
						  error: function( xhr,status,error ){
							  console.log(xhr);
						  }
					});
				}
				
				var setupfee = getSetupFeeByItemDetailIdAndPartyId(item.itemDetailsId,contact.contactId);
				var selectedStartDate = $("#ccStartDate_"+item.itemDetailsId+"_"+contact.contactId).val();
				var gradeLevel = $("#GradeLevel"+item.itemDetailsId+"_"+contact.contactId).val();
				var originalSetupFee = setupfee;
				
				var registrationPrice = getRegistrationFeeByItemDetailIdAndPartyId(item.itemDetailsId,contact.contactId);
				var originalRegistrationPrice = registrationPrice;
				
				if(item.programType=='Child Care' && item.category!='In-Service' && item.category!='After School'){
					var isChildCareAlreadyExistsForSameContact = false;
					for (var i = 0; i < this.contents.length; i ++) {
						var current = this.contents[i];
						if(current.item.programType=='Child Care' && current.item.category!='In-Service' && current.item.category!='After School'){
							isChildCareAlreadyExistsForSameContact = true;
							break;
						}
					}
					
					// if child care item exists for same contact don't charge them set up fee and registration price again
					if(isChildCareAlreadyExistsForSameContact){
						setupfee = 0;
						registrationPrice = 0;
					}
				}

				var pricingRule = getPricingRule(contact,item);
				var defaultPricingOption = pricingRule.priceOption;
				var isRecurring = checkIfRecurring(pricingRule.billingFrequency);
				
//				console.log(" priceOption  196  >>   "+priceOption);
				
				if (item.signuppriceArr.length >0) {
					for(i=0; i<item.signuppriceArr.length; i++){
						priceOption = item.signuppriceArr[i].priceOption;
						if(priceOption == 'Package'){
							packageSize = item.signuppriceArr[i].packageSize;
						}
					}
				}
				//console.log(" priceOption  196  >>   "+priceOption);
				//console.log(" pricingRule.priceoption  197  >>   "+pricingRule.priceOption);
				if (item.signuppriceArr.length >0) {
					priceOption = pricingRule.priceOption;
					joinFee = pricingRule.joinFee;
					billingFrequency = pricingRule.billingFrequency;
					
					signupPrice = getPriceByAge(contact,item,"memberPrice","nonmemberPrice");
					
					originalsignupPrice = signupPrice;
		    	}
				//console.log(" priceOption  206 >> "+priceOption);
				var isInServiceWithChildCareForContact = false;
				var isInServiceWithChildCareForContactAndCCProRated = false;
				// start added by lavy for billing option:
				// if cart has in-service without child care per contact, then billing option in in service can only be automatic regardless of automaticPayment_c in itemdetail
				// if cart has in-service with child care per contact, then billing option for in service should not be shown and it should follow what was selected for child care 
				var hideBillingOption = false;
				if(item.programType=='Child Care' && item.category=='In-Service'){
					automaticPaymentInd = true;
					
					for (var i = 0; i < this.contents.length; i ++) {
						var current = this.contents[i];
						if(current.item.programType=='Child Care' && current.item.category!='In-Service' && current.item.category!='After School'){
							// child care item exists
							var selectedDays = current.item.days.split(",");
							var WaitlistedDays = $.trim(current.item.WLDays).split(",");
							var hasConfirmedItem = true;
							if(WaitlistedDays!="" && selectedDays.length == WaitlistedDays.length){
								hasConfirmedItem = false;
							}
							
							if(contact.contactId==current.contact.contactId && hasConfirmedItem){
								hideBillingOption = true;
								isInServiceWithChildCareForContact = true;
								
								if(current.proRatedSignupPrice > 0){
									isInServiceWithChildCareForContactAndCCProRated = true;
								}
								
								var currentuniqueId = current.item.itemDetailsId + "_" + current.contact.contactId;
								$("#billing_option_"+currentuniqueId).hide();
								item.set("signuppriceArr[0].priceOption","Monthly");
								
								//in service pricing calculation if child care item for same contact exists
								var ccSelStartDate = current.selectedStartDate;
								var ccItemEndDate = current.item.end_date;
								if(ccSelStartDate!=null && ccItemEndDate!=null && ccSelStartDate!="" && ccItemEndDate!=""){
									noOfMonths= monthDiff(new Date(ccSelStartDate),new Date(convertJsonDate(ccItemEndDate)));
									if(noOfMonths!=null && typeof noOfMonths!='undefined' && noOfMonths>0){
										signupPrice = signupPrice/ noOfMonths; 
										signupPrice = signupPrice.toFixed(2);
									}
								}
								
								break;
							}
						}
					}
				}
				
				var invoiceDate = getComputedInvoiceDate(selectedStartDate);
				
				var billDate = getBillDate(item.billDateOption, item.billDateOffset, selectedStartDate, invoiceDate, isInServiceWithChildCareForContact, inServiceWithChildCareProperty);
				var dueDate = getDueDate(item.dueDateOption, item.dueDateOffset,item.start_date, selectedStartDate, billDate, isInServiceWithChildCareForContact, inServiceWithChildCareProperty);
				
				var nextBillDate = null;
				//var billDt = convertDateToDateStr(billDate);
				//if(item.programType=='Child Care'){	- Dynamic based on if recurring
					nextBillDate = getNextBillDate(priceOption, convertDateToDateStr(billDate), nextBillDate, pricingRule.billingFrequency);
				//}
				var billDateOnInvoice = getBillDateOnInvoice(billDate);
				var dueDateOnInvoice = getDueDateOnInvoice(dueDate);
				
				/*console.log("   invoiceDate   "+invoiceDate);
				console.log("   billDate   "+billDate);
				console.log("   dueDate   "+dueDate);
				console.log("   nextBillDate   "+nextBillDate);
				console.log("   billDateOnInvoice   "+billDateOnInvoice);
				console.log("   dueDateOnInvoice   "+dueDateOnInvoice);*/
				
				var signupPriceMap = calculateProRatedAmount(item, contact, defaultPricingOption, signupPrice, new Date(billDate), nextBillDate);
				
				var proRatedSignupPrice = signupPriceMap.totalProRatedSignupPrice;
				
				var proRatedSignupPriceForFutureSelectedStartDate = 0; //calculateProRatedAmountForFutureSelectedStartDate(item, contact, defaultPricingOption, signupPrice);

				//console.log(" isRecurring  ::  "+isRecurring+"  selectedStartDate :: "+selectedStartDate+", isInServiceWithChildCareForContact :: "+isInServiceWithChildCareForContact+", inServiceWithChildCareProperty  ::  "+inServiceWithChildCareProperty+",  signupPriceMap :: "+JSON.stringify(signupPriceMap)+",  nextBillDate  ::  "+ convertDateToDateStr(nextBillDate)+",  priceOption  ::  "+priceOption);
				
				var invoiceArr = getInvoiceArr(item, selectedStartDate, isInServiceWithChildCareForContact, inServiceWithChildCareProperty, signupPriceMap, convertDateToDateStr(nextBillDate), priceOption);
				
				
				if(item.programType=='Child Care' && item.category!='In-Service' && item.category!='After School'){
					var selectedDays = item.days.split(",");
					var WaitlistedDays = $.trim(item.WLDays).split(",");
					var hasConfirmedItem1 = true;
					if(WaitlistedDays!="" && selectedDays.length == WaitlistedDays.length){
						hasConfirmedItem1 = false;
					}
					
					if(hasConfirmedItem1){
						for (var i = 0; i < this.contents.length; i ++) {
							var current = this.contents[i];
							if(current.item.programType=='Child Care' && current.item.category=='In-Service' && current.signupPrice==current.originalsignupPrice){
								// in service item exists
								
								if(contact.contactId==current.contact.contactId){
									hideBillingOption = true;
									current.set("isInServiceWithChildCareForContact", true);
									current.set("hideBillingOption", hideBillingOption);
									if(proRatedSignupPrice > 0){
										current.set("isInServiceWithChildCareForContactAndCCProRated", true);
									}
									var currentuniqueId = current.item.itemDetailsId + "_" + current.contact.contactId;
									setTimeout(function(){$('#billing_option_'+currentuniqueId).hide();}, 500);
									$('#billing_option_'+currentuniqueId).hide();
									current.set("item.signuppriceArr[0].priceOption","Monthly");
									
									//in service pricing calculation if child care item for same contact exists
									var ccSelStartDate = selectedStartDate;
									var ccItemEndDate = item.end_date;
									if(ccSelStartDate!=null && ccItemEndDate!=null && ccSelStartDate!="" && ccItemEndDate!=""){
										noOfMonths= monthDiff(new Date(ccSelStartDate),new Date(convertJsonDate(ccItemEndDate)));
										if(noOfMonths!=null && typeof noOfMonths!='undefined' && noOfMonths>0){
											var ccsignupPrice = current.signupPrice/ noOfMonths;
											current.set("signupPrice", ccsignupPrice.toFixed(2));
										}
									}
									var uniId = item.itemDetailsId + "_" + contact.contactId;
									updateCart(uniId);
									break;
								}
							}
						}
					}
				}
				
				if(noOfMonths!=null && typeof noOfMonths!='undefined' && noOfMonths>0){
					$('#inserviceMessage').show();
					$('#inserviceMonths').text(noOfMonths);
					$('#inserviceMonthsText').text(noOfMonths);
				}
				
				// end added by lavy for billing option:
				// set Parent SignUp Item id for family camp
				if (typeof(parentSignUpItem) == 'undefined')  parentSignUpItem = "" ;
				//if (item.subType =='Family Camp') {
				if(item.category != 'Transportation'){
					var adultContact = getPrimaryOrFirstAdultContact();
					
					if (adultContact && adultContact.id != contact.id) {
							parentSignUpItem = adultContact.id+ "#" + item.id ;
					}
				}
				//}
				var minimumPaymentAllowedInd = false;
				if(item.paymentPlanInd && item.paymentPlanInd == 'Yes'){
					if(item.paymentPlanInd == 'Yes')
						minimumPaymentAllowedInd = true;
				}
				if(!selectedStartDate){
					selectedStartDate = '';
				}

				var automaticPaymentInd = false;
				if(item.automaticPaymentInd && item.automaticPaymentInd == 'Yes'){
					if(item.automaticPaymentInd == 'Yes')
						automaticPaymentInd = true;
				}
				
				var waitlist = false;
				if(item.programType=='Program' || item.programType=='Event' || (item.programType=='Child Care' && item.category=='After School')){
					var associatedItemsInCart = 0;
					for (var i = 0; i < this.contents.length; i ++) {
			            var current = this.contents[i];
			            if (current.item.itemDetailsId == item.itemDetailsId) {
			            	associatedItemsInCart = associatedItemsInCart+1;
			            }
			        }
					var capacity_c = parseInt(item.remainingCapacity) - associatedItemsInCart;
					if(item.remainingCapacity<=0 || capacity_c<=0)
						waitlist = true;
				}
				
				/*if (item.registrationpriceArr.length >0) {
					if(contact.isMember)
						registrationPrice = item.registrationpriceArr[0].memberPrice;
					else
						registrationPrice = item.registrationpriceArr[0].nonmemberPrice;
		    	}*/
				
				if (item.depositpriceArr.length >0) {
					if(contact.isMember)
						depositAmount = item.depositpriceArr[0].memberPrice;
					else
						depositAmount = item.depositpriceArr[0].nonmemberPrice;
		    	}
				
				var FAobj = getFAObj(item.itemDetailsId);
				var FAamount = 0;
				if(FAobj!=null && FAobj.length>0){
					var FApercent = FAobj[0].FApercent;
					if(proRatedSignupPrice > 0){
						FAamount = (FApercent * proRatedSignupPrice)/100;	
					}else{
						FAamount = (FApercent * signupPrice)/100;
					}
				}
				var FAamountUSD = kendo.format("{0:c}", FAamount);
				
				var cartItems = getCartItemsInMap(this.contents);
				
				/*var cartItems = [];
				if(this.contents.length>0){
					$.each(this.contents, function(i,cartItem) {
						
						var selectedStartDate = '';
						if(cartItem.selectedStartDate){
							selectedStartDate = cartItem.selectedStartDate;
						}
						if (selectedStartDate == '' || selectedStartDate == null || typeof(selectedStartDate) == 'undefined') selectedStartDate = "";
						
						var cartItemsMap = new Object();
						
						cartItemsMap.itemDetailId = cartItem.item.prodId;
						cartItemsMap.contactId = cartItem.contact.contactId;
						cartItemsMap.signupAmount = cartItem.signupPrice;
						cartItemsMap.setupFee = cartItem.setupFee;
						cartItemsMap.registrationFee = cartItem.registrationPrice;
						cartItemsMap.depositAmount = cartItem.depositAmount;
						cartItemsMap.priceOption= cartItem.priceOption;
						cartItemsMap.waitlist = cartItem.waitlist;
						cartItemsMap.noOfTickets = cartItem.noOfTickets;
						cartItemsMap.specialrequest = cartItem.specialrequest;
						cartItemsMap.joinFee = cartItem.joinFee;
						cartItemsMap.billingOption = cartItem.billingOptionVal;
						cartItemsMap.itempriceOnSignup = calculateItemAmountOnSignupRecord(cartItem);
						cartItemsMap.category = cartItem.item.category;
						cartItemsMap.selectedStartDate = selectedStartDate;
						cartItemsMap.dueDate = convertDateToMMDDYYYY(cartItem.dueDate);
						cartItemsMap.billDate = convertDateToMMDDYYYY(cartItem.billDate);
						cartItemsMap.nextBillDate = convertDateToMMDDYYYY(cartItem.nextBillDate);
						cartItemsMap.isRecurring = cartItem.isRecurring;
						
						console.log("  cartItemsMap  >>>>>  "+cartItemsMap);
						cartItems.push(JSON.stringify(cartItemsMap));
					});
				}*/
				
				var urlItemContactPromo =  $.sessionStorage.getItem('urlItemContactPromo');
				//console.log(" add cart : from session urlItemContactPromo "+urlItemContactPromo);
				urlItemContactPromo = $("#urlItemContactPromo").val();
				//console.log(" add cart11 : from hidden field urlItemContactPromo "+urlItemContactPromo);
				if(urlItemContactPromo != null && urlItemContactPromo != undefined && urlItemContactPromo != ''){
					var urlItemContactPromoA = urlItemContactPromo.split("_");
					
					if(urlItemContactPromoA.length > 0){
						var urlPromoItemDetailId = urlItemContactPromoA[0];
						var urlPromoContactId = urlItemContactPromoA[1];
						var urlPromoCode = urlItemContactPromoA[2];
						/*console.log(" add cart : urlPromoItemDetailId  "+urlPromoItemDetailId);
						console.log(" add cart : urlPromoContactId  "+urlPromoContactId);
						console.log(" add cart : urlPromoCode  "+urlPromoCode);*/
					}
				}
				
				var promotionMap = new Array();
				
				var signupPriceForPromo = 0;
				if(proRatedSignupPrice > 0){
					signupPriceForPromo = proRatedSignupPrice; 
				} else if(signupPrice > 0){
					signupPriceForPromo = signupPrice;
				}
				
				var amountMap = '{ "signupPrice":"'+signupPriceForPromo+'", "setupFee":"'+setupfee+'", "joinFee":"'+joinFee+'", "registrationFee":"'+registrationPrice+'", "depositAmount":"'+depositAmount+'"}';
				
				$.ajax({
					  type: "GET",
					  async: false,
					  url:"getPromoMap?itemId="+item.itemDetailsId+"&contactId="+contact.contactId+"&isAuto=true&isRecurring="+isRecurring+"&amountJSON="+amountMap+"&selectedStartDate="+selectedStartDate+"&lstCartItem="+cartItems.join('_AND_')+"&urlItemContactPromo="+urlItemContactPromo,
					  //url:"getPromoMap",
					  //data: { 'itemId':item, 'contactId': contact, 'isAuto': 'true' },
					  dataType: "json",
					  success: function( data ) {
						  
						  //console.log("  data  "+data);
						  //console.log("  JSON.stringify(data)   ::   "+JSON.stringify(data));
						  
						  //var json = JSON.parse(data);
						  promotionMap = data.promos;
						  
						//  console.log("  promotionMap   ::::   "+promotionMap);
						  
						  if(promotionMap == undefined){
							  promotionMap = "";
						  }
						  /*if(data.length>0){
							  inServiceWithChildCareProperty = data;
						  }*/
					  },
					  error: function( xhr,status,error ){
						  console.log(xhr);
					  }
				});
				
				//console.log("  promotionMap   -->   "+promotionMap.length);
				
				//console.log("  priceOption ==>  "+priceOption);
				
				this.contents.push({ item: item, contact:contact, quantity: 1, discount1: 0, discountcode1: '', dicountpriceUSD: '$0.00', uniqueId: uniqueId, 
					priceOption: priceOption, signupPrice: signupPrice, setupFee: setupfee, registrationPrice: registrationPrice, depositAmount: depositAmount, 
					FAobj: FAobj, FAamount: FAamount, FAamountUSD: FAamountUSD, waitlist: waitlist, noOfTicketsOrPackages: 1, noOfTickets: 1, signupPriceVal: signupPriceVal, 
					packageSize: packageSize, remainingCapacity: item.remainingCapacity, joinFee: joinFee, specialrequest: '', billingOptionVal: billingOptionVal, 
					automaticPaymentInd: automaticPaymentInd, proRatedSignupPrice: proRatedSignupPrice, itemTotalAmount:0.00, itemTotalMinimumAmount:0.00, minimumPaymentAllowedInd: minimumPaymentAllowedInd, 
					gradeLevel: gradeLevel, selectedStartDate: selectedStartDate, hideBillingOption: hideBillingOption, parentSignUpItem:parentSignUpItem, 
					dueDate: dueDate, billDate: billDate, isInServiceWithChildCareForContact: isInServiceWithChildCareForContact, nextBillDate: nextBillDate, isRecurring: isRecurring, 
					proRatedSignupPriceForFutureSelectedStartDate: proRatedSignupPriceForFutureSelectedStartDate, isInServiceWithChildCareForContactAndCCProRated: isInServiceWithChildCareForContactAndCCProRated, 
					originalsignupPrice:originalsignupPrice, originalSetupFee:originalSetupFee,	originalRegistrationPrice: originalRegistrationPrice, isMinPayment: false, noOfMonths: noOfMonths, 
					promos: promos, promotionMap: promotionMap, billingFrequency: billingFrequency, ignoreWhileRemoveForPromoFlag: false, billDateOnInvoice: billDateOnInvoice, dueDateOnInvoice: dueDateOnInvoice, isFullPayment: true, invoiceArr: invoiceArr })
				
				if(item.remainingCapacity<=0 || capacity_c<=0){
					$("#waitlisted_span_"+uniqueId).show();
				}else{
					$("#waitlisted_span_"+uniqueId).hide();
				}
			}
        }
		//console.log(JSON.stringify(this.contents));
    	//console.log("lavy");
        //$("#dspErr").hide();
		$.sessionStorage.setItem('cart', JSON.stringify(this.contents));
		//console.log($.sessionStorage.getItem('key_name'));
		//console.log(item);
		//$("select#signuppricingDD.kendodrop").kendoDropDownList();
		$("select#signuppricingDD.kendodrop").kendoDropDownList();
		$("select#billingOptionDD.kendodrop").kendoDropDownList();
		
		$("#uniform-user_"+item.itemDetailsId+"_"+contact.contactId +" span").attr("class", "");
		$("#user_"+item.itemDetailsId+"_"+contact.contactId).attr('checked', false);
		if(!signupfound)
			$("#cname_"+item.itemDetailsId+"_"+contact.contactId).css('color', '#999999');
    },
	
	/*updatePromo: function(item,contact,discount1,discountcode1) {
		//console.log(" cart update observed ");
        var found = false;
        this.set("cleared", false);

        for (var i = 0; i < this.contents.length; i ++) {
            var current = this.contents[i];
            if (current.item.itemDetailsId == item.itemDetailsId && current.contact.contactId == contact.contactId) {
                current.set("discount1", discount1);
				current.set("discountcode1", discountcode1);
				current.set("dicountpriceUSD", kendo.format("{0:c}",discount1));
				current.set("item.finalamount", discount1);
                found = true;
                break;
            }
        }

        if (found) {
            $.sessionStorage.setItem('cart', JSON.stringify(this.contents));
        }
    },*/
    
    updateSpecialRequest: function(item,contact,txt) {
    	for (var i = 0; i < this.contents.length; i ++) {
            var current = this.contents[i];
            if (current.item.itemDetailsId == item.itemDetailsId && current.contact.contactId == contact.contactId) {
            	current.set("specialrequest", txt);
            	break;
            }
    	}
    	//console.log(this.contents);
    	 $.sessionStorage.setItem('cart', JSON.stringify(this.contents));
    },
    
    updateCart: function(item,contact,updateCartFor,objVal) {
    	
    	//console.log("  498  updateCart  "+updateCartFor+" objVal  "+objVal);
    	
        var found = false;
        this.set("cleared", false);

        for (var i = 0; i < this.contents.length; i ++) {
            var current = this.contents[i];
           // console.log("   563  current.item.itemDetailsId  "+current.item.itemDetailsId);
          //  console.log("   item.itemDetailsId  "+item.itemDetailsId);
            if (current.item.itemDetailsId == item.itemDetailsId && current.contact.contactId == contact.contactId) {
            	var uniqueId = current.uniqueId;
            	var signupPrice = current.signupPrice;
            	var proRatedSignupPrice = current.proRatedSignupPrice;
            	var priceOption = current.priceOption;
            	var autoDiscount = 0; // current.autoDiscount;
            	//var customdiscountcode = current.discountcode1;
            	var FAobj = current.FAobj;
            	var FAamount = current.FAamount;
            	var noOfTicketsOrPackages = current.noOfTicketsOrPackages;
            	var remainingCapacity = current.remainingCapacity;
            	var joinFee = current.joinFee;
            	var billingFrequency = current.billingFrequency;
            	var billingOptionVal = current.billingOptionVal;
            	var selectedStartDate = current.selectedStartDate;
            	var nextBillDate = null;
            	if(updateCartFor == 'FinalAmount'){
            		//console.log("  objVal  "+objVal);
            		if(objVal == 'finalAmtMin'){
            			//console.log(" isFullPayment false ");
            			current.set("isFullPayment", false);
            		}else{
            			//console.log(" isFullPayment true ");
            			current.set("isFullPayment", true);
            		}
            	}
            	
            	if(updateCartFor == 'SignUpPrice'){
	            	if (item.signuppriceArr.length >0) {
	            		priceOption = item.signuppriceArr[objVal].priceOption;
	            		joinFee = item.signuppriceArr[objVal].joinFee;
	            		billingFrequency = item.signuppriceArr[objVal].billingFrequency;
						/*
	            		if(contact.isMember){
							signupPrice = item.signuppriceArr[objVal].memberPrice;
							autoDiscount = item.signuppriceArr[objVal].memberdiscount;
						}else{
							signupPrice = item.signuppriceArr[objVal].nonmemberPrice;
							autoDiscount = item.signuppriceArr[objVal].nonmemberdiscount;
						}
						*/
	            		signupPrice = getPriceByAge(contact,item,"memberPrice","nonmemberPrice", priceOption);
	            		//autoDiscount = getPriceByAge(contact,item,"memberdiscount","nonmemberdiscount");

	            		current.set("isRecurring", checkIfRecurring(billingFrequency));
	            		
	            		nextBillDate = getNextBillDate(priceOption, convertDateToDateStr(current.billDate), nextBillDate, billingFrequency);
	            		current.set("nextBillDate", nextBillDate);
	            		
	            		/*if(billingFrequency == 'Recurring'){
	            			current.set("isRecurring", true);
	            		}else{
	            			current.set("isRecurring", false);
	            		}*/
			    	}
	            	current.set("signupPriceVal", objVal);
            	}else if(updateCartFor == 'NoOfTicketsOrPackages'){
            		noOfTicketsOrPackages = objVal;
            	}else if(updateCartFor == 'BillingOption'){
            		billingOptionVal = objVal;
            	}
            	var signupPriceMap = calculateProRatedAmount(current.item, contact, priceOption, signupPrice, current.billDate, nextBillDate);
            	
            	var proRatedSignupPrice = signupPriceMap.totalProRatedSignupPrice;
            	
            //	console.log("  proRatedSignupPrice  >> > "+proRatedSignupPrice);
            	
            	var proRatedSignupPriceForFutureSelectedStartDate = 0; // calculateProRatedAmountForFutureSelectedStartDate(current.item, contact, priceOption, signupPrice);

//            	console.log(" 599 "+JSON.stringify(this.contents));
            	
            	if(current.item.programType == 'Event'){
	            	if($("#signuppricingDD_"+uniqueId).val() == 0){
	            		priceOption = 'Single';
	            		noOfTicketsOrPackagesLabel = "NO OF TICKETS:";
	            	}else{
	            		priceOption = 'Package';
	            		noOfTicketsOrPackagesLabel = "NO OF PACKAGES (Package Size "+current.packageSize+"):";
	            	}
	            	
	            	var noOfTickets = 0; 
            		if($("#signuppricingDD_"+uniqueId).val() == 0){
            			noOfTickets = noOfTicketsOrPackages;
            		}else{
            			noOfTickets = noOfTicketsOrPackages * current.packageSize;
            		}
            			
            		if(remainingCapacity < noOfTickets){
            			current.set("waitlist", true);
            			$("#waitlisted_span_"+uniqueId).show();
            		}else{
            			current.set("waitlist", false);
            			$("#waitlisted_span_"+uniqueId).hide();
            		}
            		
            		if(updateCartFor == 'SignUpPrice' || updateCartFor == 'NoOfTicketsOrPackages'){
            			
            			//console.log("noOfTickets   >>>>   "+noOfTickets+", noOfTicketsOrPackages	>>>>   "+noOfTicketsOrPackages);
            			
            			var promoMap = current.promotionMap;
	            		if(promoMap != undefined){
							for(var j=0; j < promoMap.length; j++){
								var promo = promoMap[j];
								if(promo.PromoRuleType == 'Sign Up'){
									//discount += parseFloat(promo.discountValue);
									//var signupPrice = getPriceByAge(contact,item,'memberPrice','nonmemberPrice', priceOption);
									
									
									
									var discount = computePromoDiscount(promo, signupPrice, noOfTicketsOrPackages);
									//console.log("  discount  >>  "+discount);
									promo.actualDiscountValue = discount;
									promoMap[j] = promo;
									
									//console.log(" 577 "+JSON.stringify(this.contents));
									current.set("signupPrice", signupPrice);
									current.set("promotionMap", promoMap);
									//console.log(" 578 "+JSON.stringify(this.contents));
									current.set("signupPriceVal", objVal);
									cartPreviewModel.updateCart(current,promo);
									//console.log(" 579 "+JSON.stringify(this.contents));
								}
							}
						}
            		}
            	}else{
            		noOfTicketsOrPackagesLabel = "NO OF TICKETS:";
            	}
            	
            	//console.log("  625 ");
            	current.set("noOfTicketsOrPackagesLabel", noOfTicketsOrPackagesLabel);
            	current.set("noOfTicketsOrPackages", noOfTicketsOrPackages);
            	current.set("noOfTickets", noOfTickets);
            	//console.log("  629 ");
            	if(FAobj!=null && FAobj.length>0){
            		var FApercent = FAobj[0].FApercent;
            		if(proRatedSignupPrice > 0){
            			FAamount = (FApercent * proRatedSignupPrice)/100;
            		}else{
            			FAamount = (FApercent * signupPrice)/100;
            		}
            	}

            	var FAamountUSD = kendo.format("{0:c}", FAamount);
            	
            	var inServiceWithChildCareProperty = null;
				if(current.isInServiceWithChildCareForContact){
					$.ajax({
						  type: "GET",
						  async: false,
						  url:"getSystemPropertiesByPicklistName?picklistName=IN_SERVICE_WITH_CHILD_CARE",
						  dataType: "json",
						  success: function( data ) {
							  if(data.length>0){
								  inServiceWithChildCareProperty = data;
							  }
						  },
						  error: function( xhr,status,error ){
							  console.log(xhr);
						  }
					});
				}
				//if(item.programType=='Child Care'){
					//var nextBillDate = getNextBillDate(priceOption, current.billDate, null); //current.item, current.selectedStartDate, current.isRecurring, current.isInServiceWithChildCareForContact, inServiceWithChildCareProperty);
					//current.set("nextBillDate", nextBillDate);
				//}
				//console.log(" 649 ");
            	/*current.set("noOfTicketsOrPackagesLabel", noOfTicketsOrPackagesLabel);
            	current.set("noOfTicketsOrPackages", noOfTicketsOrPackages);
            	current.set("noOfTickets", noOfTickets);*/
            	console.log(" 655 ");
            	
				var invoiceArr = getInvoiceArr(current.item, selectedStartDate, current.isInServiceWithChildCareForContact, current.inServiceWithChildCareProperty, signupPriceMap, convertDateToDateStr(nextBillDate), priceOption);
				
            	if(updateCartFor == 'FinalAmount'){
            		//console.log("  objVal  "+objVal);
            		if(objVal == 'finalAmtMin'){
            			//console.log(" isFullPayment false ");
            			current.set("isFullPayment", false);
            		}else{
            			//console.log(" isFullPayment true ");
            			current.set("isFullPayment", true);
            		}
            	}
            	
            	current.set("invoiceArr", invoiceArr);
	            current.set("priceOption", priceOption);
            	current.set("signupPrice", signupPrice);
            	current.set("proRatedSignupPrice", proRatedSignupPrice);
            	current.set("proRatedSignupPriceForFutureSelectedStartDate", proRatedSignupPriceForFutureSelectedStartDate);
            	current.set("billingOptionVal", billingOptionVal);
            	current.set("joinFee", joinFee);
            	//current.set("autoDiscount", autoDiscount);
				current.set("FAamount", FAamount);
				current.set("FAamountUSD", FAamountUSD);
            	/*if(customdiscountcode!="")
            		updatePromotionFn(customdiscountcode,current);*/
				
                found = true;
                
                if(updateCartFor == 'FinalAmount'){
            		//console.log("  objVal  "+objVal);
            		if(objVal == 'finalAmtMin'){
            			//console.log(" isFullPayment false ");
            			current.set("isFullPayment", false);
            		}else{
            			//console.log(" isFullPayment true ");
            			current.set("isFullPayment", true);
            		}
            	}
                //console.log(" 738 ");
                
                break;
            }
        }

        if (found) {
        	//console.log(" found 739 ");
        	//console.log(" 599 "+JSON.stringify(this.contents));
            $.sessionStorage.setItem('cart', JSON.stringify(this.contents));
        }
        fnApplyPromo(true);
    },
	
    updateWaitlist: function(itemIdsAndremainingCapacity) {
    	var itemIdAndremainingCapacity = itemIdsAndremainingCapacity.split(",");
    	//console.log("updateWaitlist: "+itemIdAndremainingCapacity.length);
    	if(itemIdAndremainingCapacity.length>0){
	    	for (var r = 0; r < itemIdAndremainingCapacity.length; r ++) {
		    	var itemId = itemIdAndremainingCapacity[r].split("__S__")[0];
		    	var remainingCapacity = itemIdAndremainingCapacity[r].split("__S__")[1];
		    	//var status = itemIdAndremainingCapacity[r].split("__S__")[2]; // available or waitlisted
		 
		    	var confirmedItems = 0;
	    		for (var i = 0; i < this.contents.length; i ++) {
		            var current = this.contents[i];
		            
		            if (current.item.itemDetailsId == itemId) {
		            	if(remainingCapacity>0){
		            		remainingCapacity = remainingCapacity-1;
			            	current.set("waitlist", false);
		            	}
		            	else{
		            		current.set("waitlist", true);
		            	}
		            	
		            }
		    	}
		    }
	    }
    },
    
    remove: function(item) {
    	console.log(" cart remove observed ");
    	//console.log(item); alert();
    	
    	var itemDetailIdDetele = item.item.itemDetailsId;
    	var contactIdDetele = item.contact.contactId;
    	
    	
    	var WLitemRemoved = false;
        for (var i = 0; i < this.contents.length; i ++) {
            var current = this.contents[i];
            if (current === item) {
            	if(current.waitlist)
                	WLitemRemoved = true;
            	
                this.contents.splice(i, 1);
				//$.sessionStorage.setItem('cart', JSON.stringify(this.contents));
                break;
            }
        }
        
        if(!WLitemRemoved){
        	for (var i = 0; i < this.contents.length; i ++) {
        		var current = this.contents[i];
        		if(current.item.itemDetailsId==item.itemDetailsId){
	        		if(current.waitlist){
	        			//alert("found");
	        			current.set("waitlist", false);
	        			var uniqueId = current.uniqueId;
	        			$("#waitlisted_span_"+uniqueId).hide();
	        			break;
	        		}
        		}
        	}
        }
        
        	if(item.item.programType=='Child Care' && item.item.category!='In-Service' && item.item.category!='After School'){
        	
	        	var isChildCareAlreadyExistsForSameContact = false;
				for (var i = 0; i < this.contents.length; i ++) {
					var current = this.contents[i];
					if(current.item.programType=='Child Care' && current.item.category!='In-Service' && current.item.category!='After School'){
						isChildCareAlreadyExistsForSameContact = true;
						// if child care item exists for same contact put the setup fee and registration fee as originally calculated 
						
						if(item.setupFee!="" && item.setupFee>0){
							// set up fee
							var setupFee = current.originalSetupFee;
							var currentuniqueId = current.item.itemDetailsId + "_" + current.contact.contactId;
							current.set("setupFee",setupFee);
							$('#setup_price_'+currentuniqueId).show(); 
							$('#setup_price_text_'+currentuniqueId).html("$"+setupFee.toFixed(2)); 
						}
						
						if(item.registrationPrice!="" && item.registrationPrice>0){
							// registration fee
							var registrationFee = current.originalRegistrationPrice;
							var currentuniqueId = current.item.itemDetailsId + "_" + current.contact.contactId;
							current.set("registrationPrice",registrationFee);
							$('#registration_price_'+currentuniqueId).show(); 
							$('#registration_price_text_'+currentuniqueId).html("$"+registrationFee.toFixed(2));
						}
						
						break;
					}
				}
        	}
        	
        	var selectedDays = item.item.days.split(",");
			var WaitlistedDays = $.trim(item.item.WLDays).split(",");
			var hasConfirmedItem = true;
			if(WaitlistedDays!="" && selectedDays.length == WaitlistedDays.length){
				hasConfirmedItem = false;
			}
			
			if(hasConfirmedItem){
	        	var isAnotherChildcareItemExistsForSameContact = false;
		        var isInserviceItemUpdated = false;
		        var inServiceObj = "";
		        
				for (var i = 0; i < this.contents.length; i ++) {
					var current = this.contents[i];
					
					if(current.item.programType=='Child Care' && current.item.subType!='In-Service' && current.item.subType!='After School'){
						if(item.contact.contactId==current.contact.contactId){
							isAnotherChildcareItemExistsForSameContact = true;
							var ccSelStartDate = current.selectedStartDate;
							var ccItemEndDate = current.item.end_date;
						}
					}
					
					if(current.item.programType=='Child Care' && current.item.subType=='In-Service'){
						// in service item exists
						
						if(item.contact.contactId==current.contact.contactId){
							var inservicesignupPriceOriginal = current.originalsignupPrice;
							var inservicesignupPrice = current.signupPrice;
							
							if(!isAnotherChildcareItemExistsForSameContact){
								isInserviceItemUpdated = true;
								hideBillingOption = false;
								current.set("hideBillingOption", hideBillingOption);
								current.set("item.signuppriceArr[0].priceOption","");
								
								var currentuniqueId = current.item.itemDetailsId + "_" + current.contact.contactId;
								setTimeout(function(){$('#billing_option_'+currentuniqueId).show();}, 500);
								$('#billing_option_'+currentuniqueId).show(); 
								
								current.set("signupPrice", inservicesignupPriceOriginal.toFixed(2));
								$('#signuppricingDD_'+current.uniqueId+' option[value=0]').text("($ "+inservicesignupPriceOriginal.toFixed(2)+")");
								
								inServiceObj = current;
							}
							//break;
						}
					}
					// if child care exist for same contact and also in service was updated, change it back
					if(inServiceObj!="" && isAnotherChildcareItemExistsForSameContact && isInserviceItemUpdated){
						hideBillingOption = true;
						inServiceObj.set("hideBillingOption", hideBillingOption);
						inServiceObj.set("item.signuppriceArr[0].priceOption","Monthly");
						
						var currentuniqueId = inServiceObj.item.itemDetailsId + "_" + inServiceObj.contact.contactId;
						setTimeout(function(){$('#billing_option_'+currentuniqueId).hide();}, 500);
						$('#billing_option_'+currentuniqueId).hide(); 
						
						if(ccSelStartDate!=null && ccItemEndDate!=null && ccSelStartDate!="" && ccItemEndDate!=""){
							var noOfMonths= monthDiff(new Date(ccSelStartDate),new Date(convertJsonDate(ccItemEndDate)));
							if(noOfMonths!=null && typeof noOfMonths!='undefined' && noOfMonths>0){
								//inservicesignupPrice = Math.round(current.signupPrice * noOfMonths);
								inservicesignupPrice = inservicesignupPriceOriginal / noOfMonths;
								
								inServiceObj.set("noOfMonths", noOfMonths);
								inServiceObj.set("signupPrice", inservicesignupPrice.toFixed(2));
								$('#signuppricingDD_'+inServiceObj.uniqueId+' option[value=0]').text("Monthly ($ "+inservicesignupPrice.toFixed(2)+")");
							}
						}
						
						break;
					}
				}
			}
			
			//	Get list of all item contact for the deleted item
			//	remove all item contact from cart for the deleting item
			//	add item contact one by one into the cart
			
			var j=0;
			var allRelatedItems = [];
			
			for (var i = 0; i < this.contents.length; i ++) {
	    		var current = this.contents[i];
	    		//console.log("  current.item.itemDetailsId    "+current.item.itemDetailsId);
	    		//console.log("  current.contact.contactId     "+current.contact.contactId);
	    		if(current.item.itemDetailsId==itemDetailIdDetele){
	    			allRelatedItems[j]=current;
	    			
	    			current.set("ignoreWhileRemoveForPromoFlag", true);
	    			//this.contents[i] = null;
	    			
	    			j++;
	    		}
			}
			
			
			for (var i = 0; i < allRelatedItems; i ++) {
				
			}
			
			
			
			for (var i = 0; i < this.contents.length; i ++) {
	    		var current = this.contents[i];
	    		
	    		//console.log("  item.itemDetailsId    "+item.item.itemDetailsId);
	    		//console.log("  current.item.itemDetailsId    "+current.item.itemDetailsId);
	    		
	    		if(current.item.itemDetailsId==itemDetailIdDetele){
	    			
	    			//console.log("	current.contact.contactId  ::    "+current.contact.contactId);
	    			
	    			//var ignoreItemFromCart = current.item.itemDetailsId+"_"+current.contact.contactId;
	    			
	    			var cartItems = getCartItemsInMap(this.contents);
	    			var promotionMap = new Array();
					
					var signupPriceForPromo = 0;
					if(current.proRatedSignupPrice > 0){
						signupPriceForPromo = current.proRatedSignupPrice; 
					} else if(current.signupPrice > 0){
						signupPriceForPromo = current.signupPrice;
					}
					
					var amountMap = '{ "signupPrice":"'+signupPriceForPromo+'", "setupFee":"'+current.setupfee+'", "joinFee":"'+current.joinFee+'", "registrationFee":"'+current.registrationPrice+'", "depositAmount":"'+current.depositAmount+'"}';
					
					$.ajax({
						  type: "GET",
						  async: false,
						  url:"getPromoMap?itemId="+current.item.itemDetailsId+"&contactId="+current.contact.contactId+"&isAuto=true&isRecurring="+current.isRecurring+"&amountJSON="+amountMap+"&selectedStartDate="+current.selectedStartDate+"&lstCartItem="+cartItems.join('_AND_')+"&urlItemContactPromo=",
						  //url:"getPromoMap",
						  //data: { 'itemId':item, 'contactId': contact, 'isAuto': 'true' },
						  dataType: "json",
						  success: function( data ) {
							  
							 // console.log("  data  "+data);
							 // console.log("  JSON.stringify(data)   ::   "+JSON.stringify(data));
							  
							  //var json = JSON.parse(data);
							  if(data != null)
								  promotionMap = data.promos;
							  else
								  promotionMap = {};
							//  console.log("  promotionMap   ::::   "+promotionMap);
						  },
						  error: function( xhr,status,error ){
							  console.log(xhr);
						  }
					});
	    			//console.log("  	promotionMap   ::::   "+JSON.stringify(promotionMap));
	    			
					current.set("promotionMap", promotionMap);
					current.set("ignoreWhileRemoveForPromoFlag", false);
					
					//console.log(" 578 "+JSON.stringify(this.contents));
					//current.set("signupPriceVal", objVal);
					cartPreviewModel.updateCart(current,'');
	    			
	    		}
	    	}
			
			
        $.sessionStorage.setItem('cart', JSON.stringify(this.contents));

    },

    empty: function() {
    	//console.log(" program-cart empty observed ");
        var contents = this.get("contents");
        contents.splice(0, contents.length);
        cart.errMsg = '';
		$.sessionStorage.clear();
		$("#c_promocode").val("");
		
		// all other div should be hide here 
		$("#contactActivityDiv").hide();
		$("#contactTransportDiv").hide();
		$("#authorisedContact").hide();
    },

    clear: function() {
    	//console.log(" cart clear observed ");
        var contents = this.get("contents");
        contents.splice(0, contents.length);
        this.set("cleared", true);
		$.sessionStorage.clear();
    },
    
    joinFeeAmt: function(item,contact) {
    	 
    	var joinFee = 0,
        contents = this.get("contents"),
        length = contents.length,
        i = 0;

	    for (; i < length; i ++) {
			var current = contents[i];
			if (current.item === item && current.contact === contact) {
				joinFee = parseFloat(contents[i].joinFee);
				break;
			}
	    }
	
	    return kendo.format("{0:c}", joinFee);
    },
    
    getWaitlistTxt: function(item,contact) {
    	var txt = "",
        contents = this.get("contents"),
        length = contents.length,
        i = 0;
    	
    	for (; i < length; i ++) {
			var current = contents[i];
			if(item.programType=='Program' || item.programType=='Event' || (item.programType=='Child Care' && item.category=='After School')){
				if (current.item === item && current.contact === contact && current.waitlist) {
					txt = "[ WAITLISTED ]";
					break;
				}
			}
			else if(item.programType=='Child Care'){
				if (current.item === item && current.contact === contact) {
					//console.log("CART WL TEXT: "+current.item.WL_Text_For_CC);
					//console.log("CART hasWLDays: "+current.item.hasWLDays);
					if(current.item.hasWLDays){
						txt = current.item.WL_Text_For_CC;
						break;
					}
				}
			}
	    }
    	
    	return txt;
    },
	
	itemamount: function(item,contact) {
		console.log(" cart itemamount observed ");
         var price = 0,
            contents = this.get("contents"),
            length = contents.length,
            i = 0;

         validatePromotions(contents);
         
         //console.log(" contents :: "+JSON.stringify(contents));
         
        for (; i < length; i ++) {
			var current = contents[i];
            if (current.item === item && current.contact === contact) {

            	var isChargeAmount = true;
            	if(current.item.programType == 'Child Care' && (current.item.category != 'In-Service' || current.item.category != 'After School') && current.item.hasConfirmedDays == 'false'){
    				isChargeAmount = false;
    			}else if(current.waitlist){
    				//isChargeAmount = false;
    			}
            	
            	/*if(current.waitlist){
            		isChargeAmount = false;
            	} else {
    				isChargeAmount = true;
            	}*/
            	//console.log("    isChargeAmount    "+isChargeAmount);
    			if(isChargeAmount){
	            	/*var signupPrice = parseFloat(contents[i].signupPrice);
	            	if(contents[i].proRatedSignupPrice > 0){
	            		signupPrice = parseFloat(contents[i].proRatedSignupPrice); 
	            	}*/
	            	
	            	//if(current.item.programType == 'CHILD CARE' && current.item.category != 'IN-SERVICE'){
	            		price = calculateItemAmountOnCart(current);
	            		//console.log("   price ::: "+price);
	            	/*}else{
	            		price = ((signupPrice * contents[i].noOfTicketsOrPackages) + parseFloat(contents[i].joinFee) + parseFloat(contents[i].setupFee) + parseFloat(contents[i].registrationPrice) + parseFloat(contents[i].depositAmount) - parseFloat(contents[i].autoDiscount) - parseFloat(contents[i].discount1) - parseFloat(contents[i].FAamount)) * contents[i].quantity;	
	            	}*/
    			}
            	
            	/*
            	if(contact.isMember)
            		price = (parseFloat(contents[i].signupPrice) + parseFloat(contents[i].setupFee) + parseFloat(contents[i].item.registrationpriceArr[0].memberprice) + parseFloat(contents[i].item.depositpriceArr[0].memberprice) - parseFloat(contents[i].item.signuppriceArr[0].memberdiscount) - parseFloat(contents[i].discount1)) * contents[i].quantity;
				else
					price = (parseFloat(contents[i].signupPrice) + parseFloat(contents[i].setupFee) + parseFloat(contents[i].item.registrationpriceArr[0].nonmemberPrice) + parseFloat(contents[i].item.depositpriceArr[0].nonmemberPrice) - parseFloat(contents[i].item.signuppriceArr[0].nonmemberdiscount) - parseFloat(contents[i].discount1)) * contents[i].quantity;
				*/
			}
        }
        
        return kendo.format("{0:c}", price);
	},
	
	itemamountMinimum: function(item,contact) {
		//console.log(" cart itemamountMinimum observed ");
         var price = 0,
            contents = this.get("contents"),
            length = contents.length,
            i = 0;

         validatePromotions(contents);
         
        for (; i < length; i ++) {
			var current = contents[i];
            if (current.item === item && current.contact === contact) {

            	var isChargeAmount = true;
            	if(current.item.programType == 'Child Care' 
            		&& (current.item.category != 'In-Service' || current.item.category != 'After School') 
            		&& current.item.hasConfirmedDays == 'false'){
    				isChargeAmount = false;
    			}else if(current.waitlist){
    				//isChargeAmount = false;
    			}
            	
            	/*if(current.waitlist){
            		isChargeAmount = false;
            	} else {
    				isChargeAmount = true;
            	}*/
            	//console.log("    isChargeAmount    "+isChargeAmount);
    			if(isChargeAmount){
	            	/*var signupPrice = parseFloat(contents[i].signupPrice);
	            	if(contents[i].proRatedSignupPrice > 0){
	            		signupPrice = parseFloat(contents[i].proRatedSignupPrice); 
	            	}*/
	            	
	            	//if(current.item.programType == 'CHILD CARE' && current.item.category != 'IN-SERVICE'){
	            		price = calculateItemMinimumAmountOnCart(current);
	            		//console.log("   price ::: "+price);
	            	/*}else{
	            		price = ((signupPrice * contents[i].noOfTicketsOrPackages) + parseFloat(contents[i].joinFee) + parseFloat(contents[i].setupFee) + parseFloat(contents[i].registrationPrice) + parseFloat(contents[i].depositAmount) - parseFloat(contents[i].autoDiscount) - parseFloat(contents[i].discount1) - parseFloat(contents[i].FAamount)) * contents[i].quantity;	
	            	}*/
    			}
            	
            	/*
            	if(contact.isMember)
            		price = (parseFloat(contents[i].signupPrice) + parseFloat(contents[i].setupFee) + parseFloat(contents[i].item.registrationpriceArr[0].memberprice) + parseFloat(contents[i].item.depositpriceArr[0].memberprice) - parseFloat(contents[i].item.signuppriceArr[0].memberdiscount) - parseFloat(contents[i].discount1)) * contents[i].quantity;
				else
					price = (parseFloat(contents[i].signupPrice) + parseFloat(contents[i].setupFee) + parseFloat(contents[i].item.registrationpriceArr[0].nonmemberPrice) + parseFloat(contents[i].item.depositpriceArr[0].nonmemberPrice) - parseFloat(contents[i].item.signuppriceArr[0].nonmemberdiscount) - parseFloat(contents[i].discount1)) * contents[i].quantity;
				*/
			}
        }
        
        return kendo.format("{0:c}", price);
	},
	
	itemProRatedPrice: function(item,contact) {
        var proRatedPrice = 0,
        contents = this.get("contents"),
        length = contents.length,
        i = 0;

        for (; i < length; i ++) {
			var current = contents[i];
            if (current.item === item && current.contact === contact) {
            	//console.log(" contents[i].proRatedSignupPrice "+contents[i].proRatedSignupPrice);
            	if(parseFloat(contents[i].proRatedSignupPrice) > 0 ){
            		proRatedPrice = parseFloat(contents[i].proRatedSignupPrice);
            	}/*else if(parseFloat(contents[i].proRatedSignupPriceForFutureSelectedStartDate) > 0 ){
            		proRatedPrice = parseFloat(contents[i].proRatedSignupPriceForFutureSelectedStartDate);
            	}*/
            	
            	break;
			}
        }

        return kendo.format("{0:c}", proRatedPrice);
	},
	
	totaldiscount: function() {
		//console.log(" cart totaldiscount observed ");
        var discount = 0,
            contents = this.get("contents"),
            length = contents.length,
            i = 0;

        var cart = this.get("cart");
      
        for (; i < length; i ++) {
			var current = contents[i];
			if(!current.waitlist){
				
				//console.log("  current.isMinPayment  :::   "+current.isMinPayment+",  current.promotionMap.length  :::   "+current.promotionMap.length);
				
				if(current.promotionMap != undefined && current.promotionMap != null){
					if(current.isMinPayment){
						if(current.promotionMap != undefined){
							for(var j=0; j < current.promotionMap.length; j++){
								var promo = current.promotionMap[j];
								if(promo.PromoRuleType == 'Deposit'){
									discount += parseFloat(promo.actualDiscountValue);
								}
							}
						}
					}else{
						
						if(current.promotionMap != undefined){
							for(var j=0; j < current.promotionMap.length; j++){
								var promo = current.promotionMap[j];
								if(promo.PromoRuleType == 'Sign Up' || promo.PromoRuleType == 'Registration' 
									|| promo.PromoRuleType == 'SetUpFee' || promo.PromoRuleType == 'JoinFee'){
									discount += parseFloat(promo.actualDiscountValue);
								}
							}
						}
					}
				}
				
				cartPreviewModel.updateCart(current,promo);
        	}
        }

        return kendo.format("{0:c}", discount);
	},
	
	totalFA: function() {
		//console.log(" cart totaldiscount observed ");
        var fa = 0,
            contents = this.get("contents"),
            length = contents.length,
            i = 0;

        for (; i < length; i ++) {
			var current = contents[i];
			if(!current.waitlist)
				fa += parseFloat(contents[i].FAamount);
        }

        return kendo.format("{0:c}", fa);
	},
	
    total: function() {
    	//console.log(" cart total observed ");
        var price = 0,
        contents = this.get("contents"),
        length = contents.length,
        i = 0;

        for (; i < length; i ++) {
        	var current = contents[i];
        	/*var signupPrice = parseFloat(contents[i].signupPrice);
        	if(contents[i].proRatedSignupPrice > 0){
        		signupPrice = parseFloat(contents[i].proRatedSignupPrice); 
        	}*/

        	var isChargeAmount = true;
        	
        	if(current.item.programType == 'Child Care' && (current.item.category != 'In-Service' || current.item.category != 'After School') && current.item.hasConfirmedDays == 'false'){
				isChargeAmount = false;
			}else if(current.waitlist){
				isChargeAmount = false;
			}
        	
        	/*if(current.waitlist){
        		isChargeAmount = false ;	
        	} else {
        		isChargeAmount = true ;
        	}*/
			if(isChargeAmount){
				//console.log("  current.isFullPayment ==> "+current.isFullPayment);
				if(current.isFullPayment){
					//console.log(" total --> isFullPayment true ");
					price += calculateItemAmountOnCart(current);
				}else{
					//console.log(" total --> isFullPayment false ");
					price += calculateItemMinimumAmountOnCart(current);
				}
			}
				/*
			if(current.contact.isMember)
            	price += (parseFloat(contents[i].item.signuppriceArr[0].memberprice) + parseFloat(contents[i].setupFee) + parseFloat(contents[i].item.registrationpriceArr[0].memberprice) + parseFloat(contents[i].item.depositpriceArr[0].memberprice) - parseFloat(contents[i].item.signuppriceArr[0].memberdiscount) - parseFloat(contents[i].discount1)) * contents[i].quantity;
			else
				price += (parseFloat(contents[i].item.signuppriceArr[0].nonmemberPrice) + parseFloat(contents[i].setupFee) + parseFloat(contents[i].item.registrationpriceArr[0].nonmemberPrice) + parseFloat(contents[i].item.depositpriceArr[0].nonmemberPrice) - parseFloat(contents[i].item.signuppriceArr[0].nonmemberdiscount) - parseFloat(contents[i].discount1)) * contents[i].quantity;
				*/
        }

        return kendo.format("{0:c}", price);
    },
    
    showInserviceMessage: function() {
		//console.log(" cart totaldiscount observed ");
        var noOfCCMonths = 0,
        	mess = "",
            contents = this.get("contents"),
            length = contents.length,
            i = 0;

        for (; i < length; i ++) {
			var current = contents[i];
			if(current.noOfMonths>0){
				noOfCCMonths = current.noOfMonths; 
				mess = "In-Service Sign up price will be divided by "+ noOfCCMonths +" months.";
				break;
			}
        }

        return mess;
	}
});

function checkMinPaymentCanBeChargedBasedOn(current){
	var isMinPaymentCanBeChargedBasedOn = false;
	//console.log("  current.item.allowMinPaymentBasedOn  "+current.item.allowMinPaymentBasedOn);
	//console.log("  current.dueDate  "+current.dueDate);
	//console.log("  current.billDate  "+current.billDate);
	if(current.item.allowMinPaymentBasedOn && current.item.allowMinPaymentBasedOn != undefined){
		var today = new Date();
		if(current.item.allowMinPaymentBasedOn == 'Due Date'){
			if(today < current.dueDate){
				isMinPaymentCanBeChargedBasedOn = true;
			}
		}else if(current.item.allowMinPaymentBasedOn == 'Bill Date'){
			if(today < current.billDate){
				isMinPaymentCanBeChargedBasedOn = true;
			}
		}
	}
	//console.log("  isMinPaymentCanBeChargedBasedOn  "+isMinPaymentCanBeChargedBasedOn);
	return isMinPaymentCanBeChargedBasedOn;
}

function computeItemTotal(current, isWithoutDiscount) {
	//console.log("  computeItemTotal  ....  ");
	var sumOfAdditive = 0, sumOfSubtractive = 0, itemAmountOnCart = 0, promoDiscount = 0;
	if (current.item.signuppriceArr.length >0) {
		var pricingRule = getPricingRule(current.contact,current.item);
		if(pricingRule.additiveOrSubtractive && pricingRule.additiveOrSubtractive == 'Subtractive'){
			sumOfSubtractive += (parseFloat(current.signupPrice) * parseInt(current.noOfTicketsOrPackages));
		} else {
			
			//console.log("   current.proRatedSignupPrice >>  "+current.proRatedSignupPrice);
			if(current.proRatedSignupPrice > 0){
				sumOfAdditive += (parseFloat(current.proRatedSignupPrice) * parseInt(current.noOfTicketsOrPackages));
			}else{
				sumOfAdditive += (parseFloat(current.signupPrice) * parseInt(current.noOfTicketsOrPackages));
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
	
	/*if (current.item.depositpriceArr.length >0) {
		if(current.item.depositpriceArr[0].additiveOrSubtractive && current.item.depositpriceArr[0].additiveOrSubtractive == 'Subtractive'){
			sumOfSubtractive += parseFloat(current.depositAmount);
		}else{
			sumOfAdditive += parseFloat(current.depositAmount);
		}
	}*/
	
	if (current.setupFee > 0 && current.item.setupfeepriceArr.length >0) {
		if(current.item.setupfeepriceArr[0].additiveOrSubtractive && current.item.setupfeepriceArr[0].additiveOrSubtractive == 'Subtractive'){
			sumOfSubtractive += parseFloat(current.setupFee);
		}else{
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
	
	if(isWithoutDiscount){
		itemAmountOnCart = sumOfAdditive - sumOfSubtractive - parseFloat(current.FAamount);	
	}else{
		itemAmountOnCart = sumOfAdditive - sumOfSubtractive - parseFloat(promoDiscount) - parseFloat(current.FAamount);
	}
	
	itemAmountOnCart = (itemAmountOnCart * current.quantity);
	//console.log("  itemAmountOnCart  "+itemAmountOnCart);
	//console.log("  current.autoDiscount  "+current.autoDiscount);
	return itemAmountOnCart ;
}

function computeItemMinimumPayment(current, isWithoutDiscount, isMinPaymentCanBeChargedBasedOn) {
	//console.log(" computeItemMinimumPayment .. ");
	var minimumPayment = 0, promoDiscount = 0;
	
	if (current.item.signuppriceArr.length >0) {
		var pricingRule  = getPricingRule(current.contact,current.item);
		//if((pricingRule.includedInMinimumPayment && pricingRule.includedInMinimumPayment == 'Yes') || (current.isInServiceWithChildCareForContact && current.isInServiceWithChildCareForContactAndCCProRated) || (current.programType == 'CHILD CARE' && current.category == 'IN-SERVICE')){}else 
		if(current.proRatedSignupPrice > 0){
			if(isMinPaymentCanBeChargedBasedOn == false){
				minimumPayment += parseFloat(current.proRatedSignupPrice);
			}
			
			if(current.promotionMap != undefined){
				for(var j=0; j < current.promotionMap.length; j++){
					var promo = current.promotionMap[j];
					if(promo.PromoRuleType == 'Sign Up'){
						promoDiscount += parseFloat(promo.actualDiscountValue);
					}
				}
			}
			
		}/*else if(current.proRatedSignupPriceForFutureSelectedStartDate > 0){
			minimumPayment += parseFloat(current.proRatedSignupPriceForFutureSelectedStartDate);
		}*/
	}
	
	if (current.item.registrationpriceArr.length >0) {
		if(current.item.registrationpriceArr[0].includedInMinimumPayment && current.item.registrationpriceArr[0].includedInMinimumPayment == 'Yes'){
			minimumPayment += parseFloat(current.registrationPrice);
			
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
	
	if (current.item.depositpriceArr.length >0) {
		if(current.item.depositpriceArr[0].includedInMinimumPayment && current.item.depositpriceArr[0].includedInMinimumPayment == 'Yes'){
			minimumPayment += parseFloat(current.depositAmount);
			
			if(current.promotionMap != undefined){
				for(var j=0; j < current.promotionMap.length; j++){
					var promo = current.promotionMap[j];
					if(promo.PromoRuleType == 'Deposit'){
						promoDiscount += parseFloat(promo.actualDiscountValue);
					}
				}
			}
		}
	}
	
	if (current.setupFee > 0 && current.item.setupfeepriceArr.length >0) {
		if(current.item.setupfeepriceArr[0].includedInMinimumPayment && current.item.setupfeepriceArr[0].includedInMinimumPayment == 'Yes'){
			minimumPayment += parseFloat(current.setupFee);
			
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
		minimumPayment += parseFloat(current.joinFee);
		
		if(current.promotionMap != undefined){
			for(var j=0; j < current.promotionMap.length; j++){
				var promo = current.promotionMap[j];
				if(promo.PromoRuleType == 'JoinFee'){
					promoDiscount += parseFloat(promo.actualDiscountValue);
				}
			}
		}
	}
	
	if(isWithoutDiscount){
		minimumPayment = (minimumPayment) * current.quantity;
	}else{
		minimumPayment = (minimumPayment - promoDiscount) * current.quantity;
	}
	
	
	return minimumPayment ;
}

function isMinimumPayment(current, isMinPaymentCanBeChargedBasedOn){
	var isMinPayment = false;
	
	//console.log("  current.item.paymentPlanInd   "+current.item.paymentPlanInd);
	//console.log("  current.isInServiceWithChildCareForContact   "+current.isInServiceWithChildCareForContact);
	//console.log("  current.isInServiceWithChildCareForContactAndCCProRated   "+current.isInServiceWithChildCareForContactAndCCProRated);
	
	if(((current.item.paymentPlanInd && current.item.paymentPlanInd == 'Yes') 
			&& isMinPaymentCanBeChargedBasedOn) 
			|| (current.isInServiceWithChildCareForContact && !current.isInServiceWithChildCareForContactAndCCProRated)){
		isMinPayment = true;
	}
	return isMinPayment;
}

function calculateItemAmountOnCart(current){
	//console.log("  calculateItemAmountOnCart ....  ");
	var itemAmountOnCart = 0;
	var isMinPaymentCanBeChargedBasedOn = checkMinPaymentCanBeChargedBasedOn(current);
	
	var isMinPayment = isMinimumPayment(current, isMinPaymentCanBeChargedBasedOn);
	console.log("  isMinPayment   " +isMinPayment);
	current.set("isMinPayment", isMinPayment);
	
	//if((current.item.paymentPlanInd && current.item.paymentPlanInd == 'Yes') || isMinPaymentCanBeChargedBasedOn || (current.isInServiceWithChildCareForContact && !current.isInServiceWithChildCareForContactAndCCProRated)){
	if(current.item.programType != 'Camp' && isMinPayment){
		itemAmountOnCart =  computeItemMinimumPayment(current, false, isMinPaymentCanBeChargedBasedOn);
		//current.isFullPayment = false;
		//console.log("  computeItemMinimumPayment.itemAmountOnCart  :: "+itemAmountOnCart);
	}else{
		itemAmountOnCart =  computeItemTotal(current);
		//current.isFullPayment = true;
		//console.log("  computeItemTotal.itemAmountOnCart  :: "+itemAmountOnCart);
	}
	current.itemTotalAmount = computeItemTotal(current);
	//console.log("  calculateItemAmountOnCart.itemAmountOnCart  :: "+itemAmountOnCart);
	return itemAmountOnCart;
}

function calculateItemMinimumAmountOnCart(current){
	//console.log("  calculateItemMinimumAmountOnCart ....  ");
	var itemAmountOnCart = 0;
	var isMinPaymentCanBeChargedBasedOn = checkMinPaymentCanBeChargedBasedOn(current);
	//if((current.item.paymentPlanInd && current.item.paymentPlanInd == 'Yes') || isMinPaymentCanBeChargedBasedOn || (current.isInServiceWithChildCareForContact && !current.isInServiceWithChildCareForContactAndCCProRated)){
		//current.set("isMinPayment", true);
		itemAmountOnCart =  computeItemMinimumPayment(current, false, isMinPaymentCanBeChargedBasedOn);
		//console.log("  computeItemMinimumPayment.itemAmountOnCart  :: "+itemAmountOnCart);
	//}
	//current.itemTotalAmount = computeItemTotal(current);
	current.itemTotalMinimumAmount = itemAmountOnCart;
	//current.isFullPayment = false;
	//console.log("  calculateItemAmountOnCart.itemAmountOnCart  :: "+itemAmountOnCart);
	return itemAmountOnCart;
}

function getSignUpPrice(current) {
	var signUpPrice = 0 ;
	if(current.proRatedSignupPrice > 0){
		signUpPrice += (parseFloat(current.proRatedSignupPrice) * parseInt(current.noOfTicketsOrPackages));
	}/*else if(current.proRatedSignupPriceForFutureSelectedStartDate > 0){
		signUpPrice += (parseFloat(current.proRatedSignupPriceForFutureSelectedStartDate) * parseInt(current.noOfTicketsOrPackages));
	}*/ else {
		signUpPrice += (parseFloat(current.signupPrice) * parseInt(current.noOfTicketsOrPackages));
	}
	return signUpPrice ;
}

function calculateSubTotalOnCart(current){
	//console.log(" calculateSubTotalOnCart .. ");
	var subTotalOnCart = 0;
	var isMinPaymentCanBeChargedBasedOn = checkMinPaymentCanBeChargedBasedOn(current);
	
	if((current.item.programType != 'Camp' && current.isMinPayment) || (current.item.programType == 'Camp' && !current.isFullPayment)){
		//console.log(" calculateSubTotalOnCart .. MinPayment ");
		subTotalOnCart = computeItemMinimumPayment(current, true, isMinPaymentCanBeChargedBasedOn);
		//console.log("   calculateSubTotalOnCart :: MinAmount ");
		/*if (current.item.signuppriceArr.length >0) {
			var pricingRule = getPricingRule(current.contact,current.item);
			if((pricingRule.includedInMinimumPayment && pricingRule.includedInMinimumPayment == 'Yes') || (current.isInServiceWithChildCareForContact && current.isInServiceWithChildCareForContactAndCCProRated) || (current.item.programType == 'CHILD CARE' && current.item.category == 'IN-SERVICE')){
				subTotalOnCart += parseFloat(current.signupPrice);
			}else if(current.proRatedSignupPrice > 0){
				subTotalOnCart += parseFloat(current.proRatedSignupPrice);
			}else if(current.proRatedSignupPriceForFutureSelectedStartDate > 0){
				subTotalOnCart += parseFloat(current.proRatedSignupPriceForFutureSelectedStartDate);
			}
			if(current.proRatedSignupPrice > 0){
				subTotalOnCart += parseFloat(current.proRatedSignupPrice);
			}else if(current.proRatedSignupPriceForFutureSelectedStartDate > 0){
				subTotalOnCart += parseFloat(current.proRatedSignupPriceForFutureSelectedStartDate);
			} else {
				subTotalOnCart += parseFloat(current.signupPrice);
			}
		}
		
		if (current.item.registrationpriceArr.length >0) {
			if(current.item.registrationpriceArr[0].includedInMinimumPayment && current.item.registrationpriceArr[0].includedInMinimumPayment == 'Yes'){
				subTotalOnCart += parseFloat(current.registrationPrice);
			}
		}
		
		if (current.item.depositpriceArr.length >0) {
			if(current.item.depositpriceArr[0].includedInMinimumPayment && current.item.depositpriceArr[0].includedInMinimumPayment == 'Yes'){
				subTotalOnCart += parseFloat(current.depositAmount);
			}
		}
		
		if (current.setupFee > 0 && current.item.setupfeepriceArr.length >0) {
			if(current.item.setupfeepriceArr[0].includedInMinimumPayment && current.item.setupfeepriceArr[0].includedInMinimumPayment == 'Yes'){
				subTotalOnCart += parseFloat(current.setupFee);
			}
		}
		
		subTotalOnCart = subTotalOnCart * current.quantity;*/
	}
	else{
		//console.log(" calculateSubTotalOnCart .. FullPayment ");
		subTotalOnCart = computeItemTotal(current, true);
		//console.log("   calculateSubTotalOnCart :: FullAmount ");
		/*var sumOfAdditive = 0;
		var sumOfSubtractive = 0;
		
		if (current.item.signuppriceArr.length >0) {
			var pricingRule = getPricingRule(current.contact,current.item);
			if(pricingRule.additiveOrSubtractive && pricingRule.additiveOrSubtractive == 'Subtractive'){
				sumOfSubtractive += getSignUpPrice(current) ;
			}else {
				sumOfAdditive += getSignUpPrice(current) ;
			}
		}
		
		if (current.item.registrationpriceArr.length >0) {
			if(current.item.registrationpriceArr[0].additiveOrSubtractive && current.item.registrationpriceArr[0].additiveOrSubtractive == 'Subtractive'){
				sumOfSubtractive += parseFloat(current.registrationPrice);
			}else  {
				sumOfAdditive += parseFloat(current.registrationPrice);
			}
		}
		
		
		if (current.setupFee > 0 && current.item.setupfeepriceArr.length >0) {
			if(current.item.setupfeepriceArr[0].additiveOrSubtractive && current.item.setupfeepriceArr[0].additiveOrSubtractive == 'Subtractive'){
				sumOfSubtractive += parseFloat(current.setupFee);
			}else {
				sumOfAdditive += parseFloat(current.setupFee);
			}
		}*/
		
		//itemAmountOnCart = parseFloat(current.signupPrice) + parseFloat(current.setupFee) + parseFloat(current.registrationPrice) + parseFloat(current.depositAmount);
		//subTotalOnCart = (sumOfAdditive - sumOfSubtractive - parseFloat(current.autoDiscount) - parseFloat(current.discount1) - parseFloat(current.FAamount))* current.quantity;
		
		//subTotalOnCart = (sumOfAdditive - sumOfSubtractive)* current.quantity;
	}
	
	return subTotalOnCart;
}



var layoutModel = kendo.observable({
    cart: cart
});

var cartPreviewModel = kendo.observable({
    cart: cart,

    cartContentsClass: function() {
        return this.cart.contentsCount() > 0 ? "active" : "empty";
    },

    removeFromCart: function(e) {
        //this.get("cart").remove(e.data);
		var cartData = this.get("cart");
		var kendoWindow = $("<div />").kendoWindow({
			title : "Confirm",
			resizable : false,
			modal : true,
			width : 400
		});

		kendoWindow.data("kendoWindow").content(
				$("#delete-confirmation").html()).center().open();

		kendoWindow.find(".delete-confirm,.delete-cancel").click(
		function() {
			if ($(this).hasClass("delete-confirm")) {
				//alert("Deleting record...");
				$(".k-loading-mask").show();
				cartData.remove(e.data);
				$(".k-loading-mask").hide();
			}

			kendoWindow.data("kendoWindow").close();
		}).end();
    },
	
	updateCart: function(cartItem,promo) {
		
		var uniqueId = cartItem.item.itemDetailsId+"_"+cartItem.contact.contactId;
		
		//console.log(" 1650   updateCart  uniqueId  >>>  "+uniqueId);
		
		var cartObj = this.cart;
		
		for (var c = 0; c < cartObj.contents.length; c ++) {
			
			var current = cartObj.contents[c];
			if(current.uniqueId == uniqueId){
				
				var promotionMap = current.promotionMap;
				
				//console.log(" promotionMap.length :: "+promotionMap.length);
				//console.log(" promotionMap :: "+JSON.stringify(current.promotionMap));
				
				$("#manual_promo_signup_"+uniqueId).html( "" );
				$("#manual_promo_setupfee_"+uniqueId).html( "" );
				$("#manual_promo_joinfee_"+uniqueId).html( "" );
				$("#manual_promo_registration_"+uniqueId).html( "" );
				$("#manual_promo_deposit_"+uniqueId).html( "" );
				$("#auto_promo_signup_"+uniqueId).html( "" );
				
				if(promotionMap != undefined){
					for(var a=0; a<promotionMap.length; a++){
						
						var promo = promotionMap[a];
						if(promo.AutoPromo == 'No' && promo.actualDiscountValue > 0){
							var promoHtml = "";
							var ruleType = promo.PromoRuleType;
							var promoUniqueId = uniqueId+"_"+promo.PromoCode+"_"+ruleType.replace(/ /g, '');
							
							//console.log("   promoUniqueId  "+promoUniqueId+" >> promo.discountValue  "+promo.discountValue);
							
							if(promo.actualDiscountValue > 0){
								promoHtml += "<p class='auto_promo'><span class='table-price'><input id='manual_promo_"+promoUniqueId+"' type='text' class='k-textbox' style='width:150px;' value="+promo.PromoCode+">" +
										"			<input id='old_manual_promo_"+promoUniqueId+"' type='hidden' class='k-textbox' style='width:150px;' value="+promo.PromoCode+">";
								promoHtml += "<span class='tick_img' onclick=applyPromotionFor('"+uniqueId+"','"+ encodeURIComponent(promo.PromoCode) +"','"+ encodeURIComponent(promo.PromoRuleType) +"')><img width='40' src='resources/img/tick_cross.jpg' /></span>";
								promoHtml += "<span class='cross_img' onclick=hidePromotion('"+uniqueId+"','"+ encodeURIComponent(promo.PromoCode) +"','"+ encodeURIComponent(promo.PromoRuleType) +"')><img width='40' src='resources/img/tick_cross.jpg' /></span></span>";
								promoHtml += "<span class='table-price cartpricedisplay'>";
									promoHtml += "<span class='discountpricelbl'>-</span>";
									promoHtml += "<span class='table-price discountpricelbl' >"+ promo.actualDiscountValue +"</span>";
								promoHtml += "</span></p>";
							}
							// style='top:13px; display: inline-block; float: right;height:40px;' 
							// style='top:13px; display: inline-block; height:40px;' 
							if(promo.noOfDiscountMonths > 0){
								promoHtml += "<p class='auto_promo'>";
									promoHtml += "<span class='table-price'>No of month's for discount:</span>";
									promoHtml += "<span class='table-price cartpricedisplay'>"+ promo.noOfDiscountMonths +"</span>";
								promoHtml += "</p>";
							}
							//promoHtml += "</span>";
							
							if(promo.PromoRuleType == 'Sign Up'){
								$("#manual_promo_signup_"+uniqueId).html( "" );
								$("#manual_promo_signup_"+uniqueId).show('slow');
								$("#manual_promo_signup_"+uniqueId).html( promoHtml );
							}else if(promo.PromoRuleType == 'SetUpFee'){
								$("#manual_promo_setupfee_"+uniqueId).html( "" );
								$("#manual_promo_setupfee_"+uniqueId).show('slow');
								$("#manual_promo_setupfee_"+uniqueId).html( promoHtml );
							}else if(promo.PromoRuleType == 'JoinFee'){
								$("#manual_promo_joinfee_"+uniqueId).html( "" );
								$("#manual_promo_joinfee_"+uniqueId).show('slow');
								$("#manual_promo_joinfee_"+uniqueId).html( promoHtml );
							}else if(promo.PromoRuleType == 'Registration'){
								$("#manual_promo_registration_"+uniqueId).html( "" );
								$("#manual_promo_registration_"+uniqueId).show('slow');
								$("#manual_promo_registration_"+uniqueId).html( promoHtml );
							}else if(promo.PromoRuleType == 'Deposit'){
								$("#manual_promo_deposit_"+uniqueId).html( "" );
								$("#manual_promo_deposit_"+uniqueId).show('slow');
								$("#manual_promo_deposit_"+uniqueId).html( promoHtml );
							}
						} else if(promo.AutoPromo == 'Yes' && promo.actualDiscountValue > 0){
							
							var promoHtml = "";
							var ruleType = promo.PromoRuleType;
							console.log(" promo.PromoCode  "+promo.PromoCode);
							if(promo.PromoRuleType == 'Sign Up'){
								promoHtml += "<span class='table-price' title="+promo.Description+">"+promo.PromoCode+"</span>";
								promoHtml += "<span class='cartpricedisplay'>";
								promoHtml += "<span class='discountpricelbl'>-</span>";
								promoHtml += "	<span class='table-price discountpricelbl' >"+promo.actualDiscountValue+"<span>";
								promoHtml += "</span>";
								
								$("#auto_promo_signup_"+uniqueId).html( "" );
								$("#auto_promo_signup_"+uniqueId).show();
								$("#auto_promo_signup_"+uniqueId).html( promoHtml );
							}
						}
					}
				}
			}
		}
			
	},
	
	updateWaitlistIncart: function(itemIdsAndremainingCapacity) {
		//console.log("updateWaitlistIncart: "+itemIdsAndremainingCapacity);
		cart.updateWaitlist(itemIdsAndremainingCapacity);
	},
	
    emptyCart: function() {
        cart.empty();
		$("#checkout_content").hide();
		emptycart();
    },

    itemPrice: function(cartItem) {
    	var signupPrice = cartItem.signupPrice;
    	if(cartItem.proRatedSignupPrice && cartItem.proRatedSignupPrice > 0){
    		signupPrice = cartItem.proRatedSignupPrice;
    	}
    	return kendo.format("{0:c}", signupPrice);
    },
    
    setupPriceTxt: function(cartItem) {
       	 return kendo.format("{0:c}", cartItem.setupFee);
    },
    
    registrationPriceTxt: function(cartItem) {
	    return kendo.format("{0:c}", parseFloat(cartItem.registrationPrice));
    },
    
    depositAmountTxt: function(cartItem) {
	    return kendo.format("{0:c}", parseFloat(cartItem.depositAmount));
    },

    joinFeeTxt: function(cartItem) {
		//return kendo.format("{0:c}", parseFloat(cartItem.joinFee));
		return this.get("cart").joinFeeAmt(cartItem.item,cartItem.contact);
    },
    
    proRatedSignupPriceTxt: function(cartItem) {
    	return this.get("cart").itemProRatedPrice(cartItem.item,cartItem.contact);
    },
    
    waitlistTxt: function(cartItem) {
    	return this.get("cart").getWaitlistTxt(cartItem.item,cartItem.contact);
    },
	
    noOfTicketsOrPackagesVal: function(cartItem) {
    	return cartItem.noOfTicketsOrPackages;
    },
    
    noOfTicketsOrPackagesLabel: function(cartItem) {
    	if(!cartItem.noOfTicketsOrPackagesLabel || cartItem.noOfTicketsOrPackagesLabel == ''){
    		cartItem.noOfTicketsOrPackagesLabel = 'NO OF TICKETS:';
    	}
    	return cartItem.noOfTicketsOrPackagesLabel;
    },
    
    signupPriceOption: function(cartItem) {
    	return cartItem.signupPriceVal;
    },
    
	finalPrice: function(cartItem) {
        //return kendo.format("{0:c}", this.get("cart").itemamount(cartItem.item,cartItem.contact));
		
		var finalPri = this.get("cart").itemamount(cartItem.item,cartItem.contact);
		//console.log("  finalPri :: "+finalPri);
		return finalPri;
    },
    
    finalPriceMinimum: function(cartItem) {
        //return kendo.format("{0:c}", this.get("cart").itemamount(cartItem.item,cartItem.contact));
		
		var finalPri = this.get("cart").itemamountMinimum(cartItem.item,cartItem.contact);
		//console.log("  finalPri :: "+finalPri);
		return finalPri;
    },
	
	totalDiscountAmount: function() {
		 return this.get("cart").totaldiscount();
    },
	
    totalFAPrice: function() {
        return this.get("cart").totalFA();
    },
    itemTotalAmountTxt: function(cartItem) {
        return kendo.format("{0:c}", cartItem.itemTotalAmount);
    },
    totalPrice: function() {
        return this.get("cart").total();
    },
    
    InserviceMessage: function (){
    	return this.get("cart").showInserviceMessage();
    },
	
    manualPromoEntered: function() {
		 return $.sessionStorage.getItem('manualPromoEntered');
    },
   
	updatePromoCart: function(e) {
		 $("#tcSuccessSpan").css("display", "none");		
		 $("#tcSuccessSpan" ).html("");	
		 $("#tcErrorSpan").css("display", "none");		
		 $( "#tcErrorSpan" ).html("");
		var d_promocode = e.data.discountcode1;
		var found = false;
		
		found = updatePromotionFn(d_promocode,e.data);
		
		if (!found) {
              $("#tcSuccessSpan").css("display", "none");		
			  $("#tcSuccessSpan" ).html("");	
			  $("#tcErrorSpan").css("display", "block");		
			  $( "#tcErrorSpan" ).html("The Promo code you enetered is invalid");
        }
		
    },
	
	hidePromoFromCart: function(e) {
        //cart.updatePromo(e.data.item,e.data.contact,parseFloat(0),"");
		//console.log("  e.data.uniqueId  >> "+e.data.uniqueId);
		
		$("#manual_promo_setupfee_"+e.data.uniqueId).hide();
		//$("#custom_promo_"+e.data.uniqueId).hide();
    },
	
    proceed: function(e) {
        this.get("cart").clear();
        sushi.navigate("/");
    }
});

var indexModel = kendo.observable({
    items: items,
    cart: cart,

    addToCart: function(e) {
		//console.log(e.data);
        cart.add(e.data);
    }
});

var detailModel = kendo.observable({
    imgUrl: function() {
        return "/images/200/" + this.get("current").image
    },

    price: function() {
        return kendo.format("{0:c}", this.get("current").price);
    },

    addToCart: function(e) {
        cart.add(this.get("current"));
    },

    setCurrent: function(itemID) {
        this.set("current", items.get(itemID));
    },

    previousHref: function() {
        var id = this.get("current").id - 1;
        if (id === 0) {
           id = items.data().length - 1;
        }

        return "#/menu/" + id;
    },

    nextHref: function() {
        var id = this.get("current").id + 1;

        if (id === items.data().length) {
           id = 1;
        }

        return "#/menu/" + id;
    },

    kCal: function() {
        return kendo.toString(this.get("current").stats.energy /  4.184, "0.0000");
    }
});

// added by Lavy
var productModel = kendo.observable({
	setCurrent: function(itemID) {
		//console.log(items.get(itemID));
        this.set("current", items.get(itemID));
    },	
	addToCart: function(e) {
		//console.log("Add to cart");
		//console.log(this.get("current"));
        cart.add(this.get("current"));
		location.href = '#/checkout';
    },
	removeFromCart: function(e) {
		//console.log(this.get("current"));
        this.get("cart").remove(this.get("current"));
    }
});

// Views and layouts
var layout = new kendo.Layout("layout-template", { model: layoutModel });
var cartPreview = new kendo.Layout("cart-preview-template", { model: cartPreviewModel });
var index = new kendo.View("index-template", { model: indexModel });
var checkout = new kendo.View("checkout-template", {model: cartPreviewModel });
var detail = new kendo.View("detail-template", { model: detailModel });
//added by Lavy
var productindex = new kendo.View("product-template", { model: productModel });

var sushi = new kendo.Router({
    init: function() {
         //console.log("router init");
        layout.render("#application");
    }
});

var viewingDetail = false;

// Routing
sushi.route("/", function() {
	//console.log(" sushi.route / ");
    //console.log("router root route")
    viewingDetail = false;
    layout.showIn("#content", index);
    layout.showIn("#pre-content", cartPreview);
});

sushi.route("/product/:itemContact", function(itemContactMapStr) {
	//console.log(" sushi.route /product/:itemContact ");
	var itemContactMap = itemContactMapStr.split(",");
	var addItemToCart = false;
	
	//console.log("  1626 ");
	/*
	$.ajax({
		  type: "GET",
		  async: false,
		  url:"getPromotions?itemContactMapStr="+itemContactMapStr+"&isAuto=All",
		  dataType: "json",
		  success: function( data ) {
			 
			  for(var i = 0; i<itemContactMap.length; i++){
					var itemContactStr = itemContactMap[i];
					var itemContact = itemContactStr.split("_");
					var item = itemContact[0];
					var contact = itemContact[1];
					
					//console.log(" itemContactStr "+itemContactStr);
					
					var cid =  contacts.get(contact);
					var iid = items.get(item);
					var promoMap = null;
					if(data && data != null){
						promoMap = data[itemContactStr];
						//console.log("  all promos ::: "+ JSON.stringify(promoMap));
					}
					
					addItemToCart = true;
					
					cart.add(iid,cid,null,promoMap);
			  }
			  
		  },
		  error: function( xhr,status,error ){
			  console.log(xhr);
		  }
	});*/
	
	
	for(var i = 0; i<itemContactMap.length; i++){
		var itemContactStr = itemContactMap[i];
		var itemContact = itemContactStr.split("_");
		var item = itemContact[0];
		var contact = itemContact[1];
		
		//console.log(" itemContactStr "+itemContactStr);
		
		var cid =  contacts.get(contact);
		var iid = items.get(item);
		var promoMap = null;
		//if(data && data != null){
		//	promoMap = data[itemContactStr];
			//console.log("  all promos ::: "+ JSON.stringify(promoMap));
		//}
		
		addItemToCart = true;
		
		cart.add(iid,cid,null,promoMap);
	}
	
	
	//console.log("  1643 ");
	
	/*$.each(contactId,function(j){
		contacts.fetch(function() {
			var cid =  contacts.get(contactId[j]);	
			$.each(itemDetailsId,function(i){
				items.fetch(function() {
					var iid = items.get(itemDetailsId[i]);
					addItemToCart = true;
						//alert(addItemToCart);
					cart.add(iid,cid);
					//if(iid!='undefined')
				});
			});
		 });
	});*/
	
	location.href = '#/checkout';
});

sushi.route("/checkout", function() {
	//console.log(" sushi.route /checkout ");
    viewingDetail = false;
    layout.showIn("#checkout_content", checkout);
    cartPreview.hide();
    
    $("#promo").show();
	setTimeout(function(){
		if(cartPreviewModel.totalPrice()=='$0.00'){
			$("#promo").hide();
		}else{
			$("#promo").show();
		}
	}, 500);
});

sushi.route("/menu/:id", function(itemID) {
	//console.log(" sushi.route /menu/:id ");
	//alert(itemID);					  
    layout.showIn("#pre-content", cartPreview);
    var transition = "",
        current = detailModel.get("current");
	
	//console.log(detailModel);
    if (viewingDetail && current) {
        transition = current.id < itemID ? "tileleft" : "tileright";
    }

    items.fetch(function(e) {
        if (detailModel.get("current")) { // existing view, start transition, then update content. This is necessary for the correct view transition clone to be created.
			layout.showIn("#content", detail, transition);
            detailModel.setCurrent(itemID);
        } else {
            // update content first
            detailModel.setCurrent(itemID);
            layout.showIn("#content", detail, transition);
        }
    });

    viewingDetail = true;
});

$(function() {
    sushi.start();
	
});

function calculateProRatedAmount(item, contact, pricingOption, signupPrice, billDate, nextBillDate){
	
	//console.log(" calculateProRatedAmount  .. pricingOption  "+pricingOption+"  signupPrice  "+signupPrice+"  billDate  "+billDate);
	
	var proRatedSignupPrice = 0;
	var billDateTemp = billDate;
	var signupPriceMap = new Object();
	if($("#ccStartDate_"+item.itemDetailsId+"_"+contact.contactId) && $("#ccStartDate_"+item.itemDetailsId+"_"+contact.contactId).val() != undefined){
		var isProgramAlreadyStarted = false;
		var isSelectedStartDateIsInCurrentMonth = false;
		var selectedStartDate = null;
		if(contact.contactId && contact.contactId > 0){
			selectedStartDate = $("#ccStartDate_"+item.itemDetailsId+"_"+contact.contactId).val();
		}
		
		var programStartDate = new Date(convertJsonDate(item.start_date));
		var programEndDate = new Date(convertJsonDate(item.end_date));
		var programStartDateDay = programStartDate.getDate();
		var programEndDateDay = programEndDate.getDate();

		var today = new Date();
		var currentDay = today.getDate();
		var currentMonth = today.getMonth();
		var currentYear = today.getYear();
		var currentFullYear = today.getFullYear()
		var noOfProgramDays = daydiff(programStartDate, programEndDate);
	
		var selectedStartDate = new Date(selectedStartDate);
		var selectedStartDateMonth = selectedStartDate.getMonth();
		var selectedStartDateFullYear = selectedStartDate.getFullYear();
		
		if(programStartDate.getTime() != selectedStartDate.getTime()){
		
			if(contact.contactId && contact.contactId > 0){
				if(selectedStartDateFullYear == currentFullYear && selectedStartDateMonth == currentMonth){
					//console.log(" selected month is current month ");
					isSelectedStartDateIsInCurrentMonth = true;
				}
			}
		
			//console.log("  pricingOption "+pricingOption);
			// calculate pro rated signup price
			//if(isProgramAlreadyStarted){
				if(pricingOption && pricingOption == 'Monthly'){
					if(isSelectedStartDateIsInCurrentMonth){
						var lastDate = new Date(selectedStartDate.getFullYear(), selectedStartDate.getMonth() + 1, 0);
						var lastDay = lastDate.getDate();
						//console.log(" lastDay   >>   "+lastDay);
						var selectedStartDay = selectedStartDate.getDate();
						var noOfRemainingProgramDays = lastDay - selectedStartDay + 1;
						//console.log("  noOfRemainingProgramDays 11 >>>  "+noOfRemainingProgramDays);
						if(noOfRemainingProgramDays > 0){
							proRatedSignupPrice = (signupPrice / 30) * noOfRemainingProgramDays;
						}
					}else{
						proRatedSignupPrice = calculateProRatedAmountForFutureSelectedStartDate(item, contact, pricingOption, signupPrice);
					}
				}else if(pricingOption && (pricingOption == 'Annual' || pricingOption == 'Package')){
					var noOfRemainingProgramDays = daydiff(selectedStartDate, programEndDate);
					//console.log("  noOfRemainingProgramDays  22  >>   "+noOfRemainingProgramDays+"  noOfProgramDays  "+noOfProgramDays);
					proRatedSignupPrice = (signupPrice / noOfProgramDays) * noOfRemainingProgramDays;
				}
			//}
		}
		//console.log("  proRatedSignupPrice 11 >>  "+proRatedSignupPrice);
		signupPriceMap.currentProRatedSignupPrice = proRatedSignupPrice;
		
		if(billDateTemp && billDateTemp != null){
			var billDatePlusOneMonth = new Date(new Date(billDateTemp).setMonth(billDateTemp.getMonth()+1));
			//console.log("  billDatePlusOneMonth  "+billDatePlusOneMonth);
			var today = new Date();
			if(nextBillDate != null && today > nextBillDate){ 	// billDatePlusOneMonth){
				// add next month price
				proRatedSignupPrice += parseFloat(signupPrice);
			}
		}
		//console.log("  proRatedSignupPrice 22 >>  "+proRatedSignupPrice);
		signupPriceMap.totalProRatedSignupPrice = proRatedSignupPrice;
	}else{
		signupPriceMap.currentProRatedSignupPrice = 0;
		signupPriceMap.totalProRatedSignupPrice = proRatedSignupPrice;
	}
	return signupPriceMap;
}

function calculateProRatedAmountForFutureSelectedStartDate(item, contact, pricingOption, signupPrice){
	var proRatedSignupPriceForRecurring = 0;
	if($("#ccStartDate_"+item.itemDetailsId+"_"+contact.contactId)){
		var isProrationApplicable = false;
		var isSelectedStartDateIsInFutureMonth = false, isSelectedStartDateIsInProgramDuration = false;
		var selectedStartDateStr = $("#ccStartDate_"+item.itemDetailsId+"_"+contact.contactId).val();
		
		var programStartDate = new Date(convertJsonDate(item.start_date));
		var programEndDate = new Date(convertJsonDate(item.end_date));
		var programStartDateDay = programStartDate.getDate();
		var programEndDateDay = programEndDate.getDate();
		
		var today = new Date();
		var currentDay = today.getDate();
		var currentMonth = today.getMonth();
		var currentYear = today.getYear();
		var currentFullYear = today.getFullYear()
		var noOfProgramDays = daydiff(programStartDate, programEndDate);
	
		var selectedStartDate = new Date(selectedStartDateStr);
		var selectedStartDateDay = selectedStartDate.getDate();
		var selectedStartDateMonth = selectedStartDate.getMonth();
		var selectedStartDateFullYear = selectedStartDate.getFullYear();
		
		if(programStartDate.getTime() != selectedStartDate.getTime()){
		
			if(selectedStartDateMonth != currentMonth){
				//console.log(" selected month is future month ");
				isSelectedStartDateIsInFutureMonth = true;
			}
		
			if(programStartDate < selectedStartDate && selectedStartDate <= programEndDate){
				isSelectedStartDateIsInProgramDuration = true;
			}
			
			// calculate pro rated signup price
				if(pricingOption && pricingOption == 'Monthly'){
					if(isSelectedStartDateIsInFutureMonth && isSelectedStartDateIsInProgramDuration){
						var lastDateOfTheSelectedStartDateMonth = new Date(selectedStartDate.getFullYear(), selectedStartDate.getMonth() + 1, 0);
						var lastDayOfTheSelectedStartDateMonth = lastDateOfTheSelectedStartDateMonth.getDate();
						//console.log(" lastDayOfTheSelectedStartDateMonth   >>   "+lastDayOfTheSelectedStartDateMonth);
						var noOfRemainingProgramDays = lastDayOfTheSelectedStartDateMonth - selectedStartDateDay + 1;
						//console.log("  noOfRemainingProgramDays  >>>  "+noOfRemainingProgramDays);
						if(noOfRemainingProgramDays > 0 && noOfRemainingProgramDays < 30){
							proRatedSignupPriceForRecurring = (signupPrice / 30) * noOfRemainingProgramDays;
						}
					}
				}else if(pricingOption && (pricingOption == 'Annual' || pricingOption == 'Package')){
					var noOfRemainingProgramDays = daydiff(selectedStartDate, programEndDate);
					//console.log("  noOfRemainingProgramDays    >>   "+noOfRemainingProgramDays);
					proRatedSignupPriceForRecurring = (signupPrice / noOfProgramDays) * noOfRemainingProgramDays;
				}
		}
	}
	return proRatedSignupPriceForRecurring;
}

function daydiff(first, second) {
    return (second-first)/(1000*60*60*24);
}

function updatePromotionFn(d_promocode,cartObj){
	var found = false;
	if(d_promocode!='' && typeof d_promocode !='undefined'){
		$.ajax({
			  type: "GET",
			  async: false,
			  url:"getPromodetails?promocode="+d_promocode,
			  dataType: "json",
			  success: function( data ) {
				  if(data.length==1){
					  $.each(data, function(i,promo) {
							if(promo.itemDetails.length>0){
								  $.each(promo.itemDetails, function(j,itemInfo) {
										  if(cartObj.item.itemDetailsId == itemInfo.itemDetailsId){
											  	  var p_price = parseFloat(0);
											  	  /*
												  if(cartObj.contact.isMember)
												  	p_price = cartObj.item.signuppriceArr[0].memberprice;
												  else
												  	p_price = cartObj.item.signuppriceArr[0].nonmemberPrice;
												  */
											  	  p_price = cartObj.signupPrice;
											  	  proRatedSignupPrice = cartObj.proRatedSignupPrice;
											  	
											  	  var signupPrice = p_price;
											  	  if(proRatedSignupPrice && proRatedSignupPrice >0){
											  		signupPrice = proRatedSignupPrice;
											  	  }
											  	  
												 // alert(p_price);
											  	  var d_price = parseFloat(0);
												  var discount_type = promo.discounttype;
												  if(discount_type=='AMOUNT')
													  d_price = promo.actualDiscountValue;
												  else if(discount_type=='PERCENTAGE'){
													  d_price = parseFloat(promo.discountPercentage) * parseFloat(signupPrice);
													  d_price = d_price/100;
												  }
												  //cart.updatePromo(cartObj.item,cartObj.contact,d_price,d_promocode);
												  found = true;
										  }
										  
								  });
						    }
						 	
					  });
				  }
				  else{
					  $("#tcSuccessSpan").css("display", "none");		
					  $("#tcSuccessSpan" ).html("");	
					  $("#tcErrorSpan").css("display", "block");		
					  $( "#tcErrorSpan" ).html("The Promo code you enetered is invalid");
					  return;
				  }
			  },
			  error: function( xhr,status,error ){
				  //alert("1" +status);
				  console.log(xhr);
				 
			  }
		});
	}
	return found;
}

function updateCart(uniqueId, updateCartFor, obj) {
	//alert(uniqueId);
	//console.log(index);
	//console.log(this);
	//alert(obj.val());
	
	var objVal = '';
	if(updateCartFor){
		objVal = obj.val();
	}
	
	/*if(signupIndexObj != null){
		updateCartFor = 'SignUpPrice';
		objVal = signupIndexObj.val();
	}else if(noOfTicketsOrPackagesObj != null){
		updateCartFor = 'NoOfTicketsOrPackages';
		objVal = noOfTicketsOrPackagesObj.val();
	}else{
		BillingOption
	}*/
	
	var itemDetailId = uniqueId.split("_")[0];
	var contactId = uniqueId.split("_")[1];
	//cartPreviewModel.updateCart(cartObj.contents[c],parseFloat(discount),promocode);
	contacts.fetch(function() {
		var cid =  contacts.get(contactId);	
		items.fetch(function() {
			//nsole.log("   itemDetailId    "+itemDetailId);
			//nsole.log("   items    "+items);
			var iid = items.get(itemDetailId);
			//nsole.log("   iid    "+iid);
			cart.updateCart(iid,cid,updateCartFor,objVal);
		});
	});
}

function updateSpecialRequest(uniqueId, obj) {
	//if(obj!=null && !typeof obj === undefined ){
		var itemDetailId = uniqueId.split("_")[0];
		var contactId = uniqueId.split("_")[1];
		
		var spRequestText = obj.val();
		//alert(spRequestText);
		contacts.fetch(function() {
			var cid =  contacts.get(contactId);	
			items.fetch(function() {
				var iid = items.get(itemDetailId);
				cart.updateSpecialRequest(iid,cid,spRequestText);
			});
		});
	//}
}



function addToCart(showInservice){
	
	$("#tcSuccessSpan").css("display", "none");
	$("#tcSuccessSpan" ).html("");
	$("#tcErrorSpan").css("display", "none");
	$("#tcErrorSpan" ).html("");
	$("#dspErr").slideUp();
	
	var pageType = $("#pageType").val();
	
	var itemDetailsId = $.sessionStorage.getItem('itemDetailsId');
	
	var itemDetailsIdArr = itemDetailsId.split(",");
	var adultContactSelectedFamilyCamp = false;
	var isFamilyCamp = false ;
	for(var i=0;i<itemDetailsIdArr.length;i++){
		var item = itemDetailsIdArr[i];
		var anyContactSelectedForItem = false;
		
		$('#allmembers').find('input[class="usercheck"]').each(function(){
			if($("#user_"+this.value).is(':checked')){
				var productContact = this.value;
				var map = productContact.split("_");
				
				var product = map[0];
				var contact = map[1];
				
				if(product == item){
					anyContactSelectedForItem = true;
					
					var itemObj = getItemFromDataSource(product);
					var contactObj = getContactDataSource(contact);
					if (itemObj.subType == 'Family Camp') {
						isFamilyCamp = true;
						if (contactObj.age >= 18) {
							adultContactSelectedFamilyCamp = true;	
						} 
					}
				}
			}
		});
		
		if(pageType && pageType == 'EVENT' || pageType && pageType == 'FACILITY'){
			anyContactSelectedForItem = true;
		}
		
		if(isFamilyCamp && !adultContactSelectedFamilyCamp){
			$("#tcSuccessSpan").css("display", "none");
			$("#tcSuccessSpan" ).html("");
			$("#tcErrorSpan").css("display", "block");
			$("#tcErrorSpan" ).html("Please select at least one adult contact for family camp");
			return;
		}
		
		if(!anyContactSelectedForItem){
			console.log(" Please select at least one contact ");
			$("#tcSuccessSpan").css("display", "none");
			$("#tcSuccessSpan" ).html("");
			$("#tcErrorSpan").css("display", "block");
			$("#tcErrorSpan" ).html("Please select at least one contact for each item");
			return;
		}
	}
	
	var isValidStartDate = true;
	$('#allmembers').find('input[class="usercheck"]').each(function(){
		if($("#user_"+this.value).is(':checked')){
			if($("#ccStartDate_"+this.value) && $("#ccStartDate_"+this.value).val() == ''){
				$("#ccStartDate_"+this.value).focus();
				$("#tcSuccessSpan").css("display", "none");
				$("#tcSuccessSpan" ).html("");
				$("#tcErrorSpan").css("display", "block");
				$("#tcErrorSpan" ).html("Please enter start date for selected contact");
				isValidStartDate = false;
			} else {
				if($("#ccStartDate_"+this.value) && $("#ccStartDate_"+this.value).val() != undefined){
					
					var productContact = this.value;
					var map = productContact.split("_");
					
					var product = map[0];
					var contact = map[1];
					
					var item = items.get(product);
					var programStartDate = new Date(convertJsonDate(item.start_date));
					var selectedStartDateStr = $("#ccStartDate_"+this.value).val();
					
					if(isValidDate(selectedStartDateStr)){
						var selectedStartDate = new Date($("#ccStartDate_"+this.value).val());
						if(selectedStartDate < programStartDate){
							$("#tcSuccessSpan").css("display", "none");
							$("#tcSuccessSpan" ).html("");
							$("#tcErrorSpan").css("display", "block");
							$("#tcErrorSpan" ).html("Start date should be greater than program start date ("+convertJsonDate(item.start_date)+")");
							isValidStartDate = false;
						}
					}else{
						$("#tcSuccessSpan").css("display", "none");
						$("#tcSuccessSpan" ).html("");
						$("#tcErrorSpan").css("display", "block");
						$("#tcErrorSpan" ).html("Please enter valid start date");
						isValidStartDate = false;
					}
				}
			}
		}
	});
	
	if(!isValidStartDate){
		return;
	}
	
	// validate membership period
	var programsExpiredMsg = '';
	for(var i=0;i<itemDetailsIdArr.length;i++){
		var item = itemDetailsIdArr[i];
		var anyItemContactNotWithinRegistrationDate = false;
		
		$('#allmembers').find('input[class="usercheck"]').each(function(){
			if($("#user_"+this.value).is(':checked')){
				var productContact = this.value;
				var map = productContact.split("_");
				
				var product = map[0];
				var contact = map[1];
				
				if(product == item){
					anyContactSelectedForItem = true;
					
					var itemObj = getItemFromDataSource(product);
					var contactObj = getContactDataSource(contact);
					
					if(!withinRegistrationDate(itemObj, contactObj)){
						anyItemContactNotWithinRegistrationDate = true;
						 
						programsExpiredMsg = programsExpiredMsg + itemObj.name +' & ' + contactObj.fname +" "+contactObj.lname+ "<br>";
						 
					}
				}
			}
		});
		
		//console.log("  anyItemContactNotWithinRegistrationDate  :::  "+anyItemContactNotWithinRegistrationDate);
		
		if(anyItemContactNotWithinRegistrationDate){
			$("#tcSuccessSpan").css("display", "none");
			$("#tcSuccessSpan" ).html("");
			$("#tcErrorSpan").css("display", "block");
			$("#tcErrorSpan" ).html("Registration is not available for the following classes and contacts: <br> "+programsExpiredMsg);
			return;
		}
		
		/*if(pageType && pageType == 'EVENT' || pageType && pageType == 'FACILITY'){
			anyContactSelectedForItem = true;
		}
		
		if(isFamilyCamp && !adultContactSelectedFamilyCamp){
			$("#tcSuccessSpan").css("display", "none");
			$("#tcSuccessSpan" ).html("");
			$("#tcErrorSpan").css("display", "block");
			$("#tcErrorSpan" ).html("Please select at least one adult contact for family camp");
			return;
		}
		
		if(!anyContactSelectedForItem){
			console.log(" Please select at least one contact ");
			$("#tcSuccessSpan").css("display", "none");
			$("#tcSuccessSpan" ).html("");
			$("#tcErrorSpan").css("display", "block");
			$("#tcErrorSpan" ).html("Please select at least one contact for each item");
			return;
		}*/
	}
	
	
	
	var contactData = [];
	var itemContactMap = [];
	
	$('#allmembers').find('input[class="usercheck"]').each(function(){
		if($("#user_"+this.value).is(':checked')){
			
			var productContact = this.value;
			var map = productContact.split("_");
			
			var product = map[0];
			var contact = map[1];
			
			contactData.push(contact);
			
			itemContactMap.push(productContact);
		}
	});
	
	//var pageType = $("#pageType").val();
	if(pageType && pageType == 'EVENT' || pageType && pageType == 'FACILITY'){
		
		var contact = $("#loggedInUserContactId").val(); 
		contactData.push(contact);
		
		var itemDetailsIdArr = itemDetailsId.split(",");
		$.each(itemDetailsIdArr,function(i){
			var product = itemDetailsIdArr[i];
			if(product && contact){
				itemContactMap.push(product+"_"+contact);
			}
		});
		
		loadContacts();
		initProducts();
	}
	
	$.sessionStorage.setItem('itemContactMapStr', itemContactMap.join(","));
	
	var inServiceItemDetailId =  $("input[name=inServiceRadio]:checked").val();
	
	if(typeof showInservice=='undefined'){
		showInservice = true;
	}
	
	if(showInservice && pageType && pageType == 'CHILD CARE' && typeof inServiceItemDetailId!='undefined'){
		showInserviceIndividualDays(inServiceItemDetailId);
		$("#inserviceDaysselect").show();
		$("#familymembers").hide();
		items.fetch(function() {
			var data = this.data();
			//console.log(data);
			var iid = items.get(inServiceItemDetailId);
			//console.log(iid);
			if (iid.signuppriceArr.length >0) {
				$("#memberpackagePrice").html(currencyFormat(iid.signuppriceArr[0].memberPrice));
				//alert(iid.signuppriceArr[0].nonmemberPrice);
				$("#nonmemberpackagePrice").html(currencyFormat(iid.signuppriceArr[0].nonmemberPrice));
			}
		});
		return;
	}
	
	//console.log(itemDetailsId);
	//alert();
		var itemDetailIds = itemDetailsId.replace(",,", ",");
		if(itemDetailIds){
			$.ajax({
				  type: "GET", 
				  url:"isHealthHistoryRequired?itemDetailId="+itemDetailIds,
				  async:false,
				  dataType: "json",
				  success: function( data ) {
					  if(data && data.RESULT == 'YES'){
						  //$("#contactHealthHistoryDiv").slideDown();
						  $("#checkout_content").hide();
						  if(validateCheckout(pageType)){
							  setTimeout(function(){
								  if(cart.errMsg==""){
									  $("#familymembers").fadeOut(200);
									  $("#inserviceDaysselect").hide();
									  getContactHistory(contactData);
								  }
							  }, 1000);
						  }
					  }else{
						  if(validateCheckout(pageType)){
							  setTimeout(function(){
								  if(cart.errMsg==""){
									  $("#familymembers").fadeOut(200);
									  $("#inserviceDaysselect").hide();
									  
									  if(pageType && pageType == 'CAMP'){
										  showCampActivityAndTransportService();
									  } else if(pageType && pageType == 'CHILD CARE'){
										  	showEmergencyContactIfFlagIsSet();
									  }else{
											$("#checkout_content").show();
									  }
								  }
							  }, 1000);
						  }
					  }
				  },
				  error: function( xhr,status,error ){
					  console.log(" isHealthHistoryRequired "+xhr);
				  }
			});
		//}
	}
	//$("#tcSuccessSpan").css("display", "none");
	//$("#tcSuccessSpan" ).html("");
	//$("#tcErrorSpan").css("display", "none");
	//$("#tcErrorSpan" ).html("");
}
