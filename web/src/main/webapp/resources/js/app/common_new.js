
/* Common functions */

function convertJsonDate(dtObj){
	try {
		var dtStr = "";
		var sday = ("0" + (dtObj.date)).slice(-2);
		var smonth = ("0" + (dtObj.month + 1)).slice(-2);
		var syear = dtObj.year;
		var strYear = syear.toString();
		if(strYear.length==3)
			strYear = "20"+strYear.substring(1,strYear.length);
		else
			strYear = syear;
		dtStr = smonth + "/" + sday + "/" + strYear;
		return dtStr;
	} catch(e) {
		return "";
	}
}

function convertTimeToDate(dtObj){
	try {
		var date1 = new Date(dtObj);
		var dateStr =  date1.toString("MM/dd/yyyy");   
		//alert(dateStr);
		return dateStr ;
	} catch(e) {
		return "";
	}
}

function convertDateToDateStr(date){
	var dateStr = '';
	if(date != null){
		var day = date.getDate();
	    var monthIndex = date.getMonth() + 1;
	    var year = date.getFullYear();
		var dateStr =  monthIndex+"/"+day+"/"+year;   
	}
	return dateStr;
}

function convertDateStrToDate(dateStr){
	var dd = null;
	if(dateStr != null && dateStr != undefined && dateStr != ''){
		var dateStrA = dateStr.split("/");
		if(dateStrA.length == 3){
			var month = dateStrA[0] - 1;
		    var day = dateStrA[1];
		    var year = dateStrA[2];
		    dd = new Date(year,month,day);
		}
	}
	return dd;
}

function convertJavaDateToJSDate(javaDate){
	var jsDate = null;
	if(javaDate != null && javaDate != undefined){
		var javaDateStr = javaDate;
		var javaDateA = javaDateStr.split(" ");
		jsDate = new Date(javaDateA[0]);
	}
	return jsDate;
}

function currencyFormat (num) {
    return "$" + num.toFixed(2).replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1,")
}

function formatTime(date) {
	var UTCtime = date.getTime();
	//console.log(UTCtime);
	var now = new Date();
	var timezoneOffset = now.getTimezoneOffset();
	//console.log(timezoneOffset);
	var timezoneInMillisecond = timezoneOffset * 60000;
	
	var date = new Date(UTCtime + timezoneInMillisecond);
	//console.log(date);
	  var hours = date.getHours();
	  var minutes = date.getMinutes();
	  var ampm = hours >= 12 ? 'pm' : 'am';
	  hours = hours % 12;
	  hours = hours ? hours : 12; // the hour '0' should be '12'
	  minutes = minutes < 10 ? '0'+minutes : minutes;
	  var strTime = hours + ':' + minutes + ' ' + ampm;
	  return strTime;
}

function formatDate(inputFormat) {
	  function pad(s) { return (s < 10) ? '0' + s : s; }
	  var d = new Date(inputFormat);
	  return [pad(d.getMonth()+1), pad(d.getDate()), pad(d.getFullYear ())].join('/');
}

function isNumber(val){
   if (isNaN(val) || val == 'undefined' || val == null) 
	   return 0;
   else
	   return val;
}

function backtoregistration(){
	
	$(".k-loading-mask").show();
    
    $("#tcSuccessSpan").css("display", "none");		
	$("#tcSuccessSpan" ).html("");	
	$("#tcErrorSpan").css("display", "none");		
	$( "#tcErrorSpan" ).html("");
	$("#purchase").fadeOut();
	
	$("#familymembers").fadeOut(200);
	commonSearch();
}

function ArrNoDupe(a) {
    var temp = {};
    for (var i = 0; i < a.length; i++)
        temp[a[i]] = true;
    var r = [];
    for (var k in temp)
        r.push(k);
    return r;
}

function unique(list) {
    var result = [];
    $.each(list, function(i, e) {
        if ($.inArray(e, result) == -1) result.push(e);
    });
    return result;
}

/*function validateCheckout(cartType) {
	 $("#tcSuccessSpan").css("display", "none");		
	 $("#tcSuccessSpan" ).html("");	
	 $("#tcErrorSpan").css("display", "none");		
	 $( "#tcErrorSpan" ).html("");
	console.log(" validateCheckout 86 ");
	var contactData = [];
	console.log(" validateCheckout 92 ");
	if(cartType && (cartType == 'EVENT' || cartType == 'FACILITY')){
		console.log(" validateCheckout 89 ");
		contactData.push($("#loggedInUserContactId").val());
	} else {
		$('#allmembers').find('input[class="usercheck"]').each(function(){
			if($("#user_"+this.value).is(':checked')){
				contactData.push(this.value);
			}
		});
	}
	var itemDetailsId = $.sessionStorage.getItem('itemDetailsId');
	if(withinRegistrationDate(itemDetailsId,contactData.join(','))){
		console.log(" validateCheckout 116 ");
		if(checkAgeRange(itemDetailsId,contactData.join(','))){
			console.log(" validateCheckout 118 ");
			$.sessionStorage.setItem('contact_ids', contactData.join(','));
			location.href = '#/product/'+itemDetailsId+'/'+contactData.join(',');
			//console.log(" validateCheckout 122 ");
			return true;
		}
	}else{
		console.log(" validateCheckout 124 ");
		$("#tcSuccessSpan").css("display", "none");		
		$("#tcSuccessSpan" ).html("");	
		$("#tcErrorSpan").css("display", "block");
		$( "#tcErrorSpan" ).html("Registration date has expired for some of the Selected Programs");
	}
	return false;
}*/

function validateCheckout(cartType) {
	 $("#tcSuccessSpan").css("display", "none");		
	 $("#tcSuccessSpan" ).html("");	
	 $("#tcErrorSpan").css("display", "none");		
	 $( "#tcErrorSpan" ).html("");

	 var itemContactMapStr = $.sessionStorage.getItem('itemContactMapStr');
	 var itemDetailsId = $.sessionStorage.getItem('itemDetailsId');
	 location.href = '#/product/'+itemContactMapStr;
	
	 /*var itemContactMapStr = $.sessionStorage.getItem('itemContactMapStr');
	var itemDetailsId = $.sessionStorage.getItem('itemDetailsId');
	if(withinRegistrationDate()){
			location.href = '#/product/'+itemContactMapStr;
			return true;
	}else{
		console.log(" validateCheckout 124 ");
		$("#tcSuccessSpan").css("display", "none");		
		$("#tcSuccessSpan" ).html("");	
		$("#tcErrorSpan").css("display", "block");
		$( "#tcErrorSpan" ).html("Registration date has expired for some of the Selected Programs");
	}*/
	return true;
}



function loadContacts() {
	$.ajax({
		  type: "GET",
		  async: false,
		  url:"getContacts",
		  dataType: "json",
		  success: function( data ) {
			  contacts.read({ data: data});
		  },
		  error: function( xhr,status,error ){
			  console.log(xhr);
		  }
	});
}

function getFamilymembers(selectItemContact){

	if($("#pageType").val() == 'EVENT'){
		addToCart();
		return;
	}
	
	if($("#pageType").val() != 'FACILITY'){
		var itemDetailsId = $.sessionStorage.getItem('itemDetailsId');
		loadContacts();
		initProducts();
		
		var ccDataLst = $.sessionStorage.getItem('ccDataLst');
		if(ccDataLst!=null && typeof ccDataLst != 'undefined'){
			ccDataLst = ccDataLst.split(";;");
			//console.log("Inside add child care loop");
			//alert(ccDataLst.length);
			items.fetch(function() {
				var data = this.data();
				//console.log(data);
				for (var i = 0; i < ccDataLst.length; i ++) {
					var ccData = ccDataLst[i].split("__");
					
					var itemDetailId = ccData[0];
					if(typeof itemDetailId != 'undefined' && itemDetailId!=-9){
						if(itemDetailsId.indexOf(itemDetailId)>=0){
							var iid = items.get(itemDetailId);
							if (iid.signuppriceArr.length >0) {
								 iid.set("signuppriceArr[0].memberPrice",ccData[1]);
								 iid.set("signuppriceArr[0].nonmemberPrice",ccData[1]);
								 iid.set("days",ccData[2]);
								 iid.set("dayId",ccData[3]);
								 iid.set("WL_Text_For_CC",ccData[4]);
								 iid.set("WLDays",ccData[5]);
								 iid.set("hasWLDays",ccData[6]);
								 iid.set("hasConfirmedDays",ccData[7]);
							}
						}
					}
				}
			});
		}
		
		 // console.log("   getfm selectItemContact  "+selectItemContact);
		  
		  var selectItemDetailId = '';
		  var selectContactId = '';
		  if(selectItemContact != null){
			  var itemContactA = selectItemContact.split("_");
			  
			  selectItemDetailId = itemContactA[0];
			  selectContactId = itemContactA[1];
		  }
		  
		//  console.log("   getfm selectItemDetailId  "+selectItemDetailId+"   getfm selectContactId  "+selectContactId);
		
		  var isAnyAllowToPickStartDate = false;
		  var isAnyAllowToPickGrade = false;
		  var products = items.data();
		  for(var i=0;i<products.length;i++){
			 var item = products[i];
			 if(item.allowToPickStartDate && item.allowToPickStartDate == 'Yes'){
				  isAnyAllowToPickStartDate = true;
			  }
			  if(item.allowToPickGrade && item.allowToPickGrade == 'Yes'){
				  isAnyAllowToPickGrade = true;
			  }
		  }
		  
		  var allProducts = "<table>";
		  allProducts = allProducts + "<tr style='margin-left: 10px;'>";
		  allProducts = allProducts + "<td style='margin-left: 20px;' width='150px'><b>Items</b></td>";
		  allProducts = allProducts + "<td>";
		  allProducts = allProducts + "<table>";
		  allProducts = allProducts + "<tr>";
		  allProducts = allProducts + "<td style='margin-left: 10px;' width='30px'></td>";
		  allProducts = allProducts + "<td style='margin-left: 20px;' width='150px'><b>Contact</b></td>";
		  if(isAnyAllowToPickStartDate){
			  allProducts = allProducts + "<td style='margin-left: 20px;' width='150px'><b>Start date</b></td>";
		  }
		  if(isAnyAllowToPickGrade){
			  allProducts = allProducts + "<td style='margin-left: 20px;' width='150px'><b>Grade Level</b></td>";
		  }
		  allProducts = allProducts + "</tr>";
		  allProducts = allProducts + "</table>";
		  allProducts = allProducts + "</td>";
		  allProducts = allProducts + "</tr>";
		  
		  allProducts = allProducts + "<tr><td colspan='2'><hr></td></tr>";
		  
		  for(var i=0;i<products.length;i++){
			  var product = products[i];
			  allProducts = allProducts + "<tr style='margin-left: 10px;'>";
			  allProducts = allProducts + "<td style='margin-left: 20px; vertical-align: top;' width='150px'>";
			  allProducts = allProducts + "<span class='name'><b>"+product.name+"</b></span>";
			  allProducts = allProducts + "</td>";
			  
			  allProducts = allProducts + "<td>";
			  allProducts = allProducts + "<table>";
			  var contactsData = contacts.data();
			  var isAnyContactForProduct = false;
			  for(var j=0;j<contactsData.length;j++){
				  var contact = contactsData[j];
				  var cGender = contact.gender ;
				  if (typeof(cGender) == 'undefined') {
					  cGender = "";
				  }
				  var pGender = product.gender ;
				  if (typeof(pGender) == 'undefined') {
					  pGender = "";
				  }
				  
				  if(contact.age > 0 && contact.age >= product.minAge && contact.age <= product.maxAge && (pGender ==''  || cGender =='' || pGender.indexOf(cGender.substring(0,1)) >= 0 )) {
					  
					  //console.log("  product.id "+product.id+"  contact.contactId "+contact.contactId);
					  
					  isAnyContactForProduct = true;
					  
					  // block to check if contact has already signup for program and if we want to prevent duplicate signup
					  var showContacts = true;
					  if(product.programType != 'Event' && product.preventDuplicateSignup == 'Yes' && product.category != 'Transportation'){
						  $.ajax({ 
							  	 url: 'program/checkProgramSignUp', 
						         async: false,
						         data:{ contactId: contact.contactId, itemDetailId: item.itemDetailsId, type: item.programType, category: item.category, selectedDaysStr: item.dayId, daysPreventDuplicateSignupStr: item.daysPreventDuplicateSignup, selectedDaysOrDateStr: item.days },
						         dataType: 'json',
						         success: function(data) {
									if(data.resp) {
										showContacts = false;
									}
						          }
						   });
					  }
					  
					  if(showContacts){
						  allProducts = allProducts + "<tr>";
						  if(selectItemDetailId == product.id && selectContactId == contact.contactId){
							  allProducts = allProducts + "<td style='margin-left: 10px;' width='30px'><span><input type='checkbox' name='user_"+product.id+"_"+contact.contactId+"' id='user_"+product.id+"_"+contact.contactId+"' onclick='processOption("+product.id+","+contact.contactId+")' value='"+product.id+"_"+contact.contactId+"' class='usercheck' checked></span></td>";
						  }else{
							  allProducts = allProducts + "<td style='margin-left: 10px;' width='30px'><span><input type='checkbox' name='user_"+product.id+"_"+contact.contactId+"' id='user_"+product.id+"_"+contact.contactId+"' onclick='processOption("+product.id+","+contact.contactId+")' value='"+product.id+"_"+contact.contactId+"' class='usercheck'></span></td>";
						  }
						  		
						  allProducts = allProducts + "<td style='margin-left: 20px;' width='150px'><span class='name' id='cname_"+product.id+"_"+contact.contactId+"'>"+contact.fname+" "+contact.lname+"</span></td>";
						  if(product.allowToPickStartDate && product.allowToPickStartDate == 'Yes'){
							  allProducts = allProducts + "<td style='margin-left: 20px;' width='150px'><span><input style='width:130px;' name='ccStartDate_"+product.id+"_"+contact.contactId+"' id='ccStartDate_"+product.id+"_"+contact.contactId+"'></span></td>";
						  }
						  if(product.allowToPickGrade && product.allowToPickGrade == 'Yes'){
							  allProducts = allProducts + "<td style='margin-left: 20px;' width='150px'><span><select name='GradeLevel' id='GradeLevel"+product.id+"_"+contact.contactId+"'>" +
							  		"<option value='PreK'>Pre-K</option>" +
							  		"<option value='K'>K</option>" +
							  		"<option value='1'>1</option>" +
							  		"<option value='2'>2</option>" +
							  		"<option value='3'>3</option>" +
							  		"<option value='4'>4</option>" +
							  		"<option value='5'>5</option>" +
							  		"<option value='6'>6</option>" +
							  		"<option value='7'>7</option>" +
							  		"<option value='8'>8</option>" +
							  		"<option value='9'>9</option>" +
							  		"<option value='10'>10</option>" +
							  		"<option value='11'>11</option>" +
							  		"<option value='12'>12</option>" +
							  		"</select></span></td>";
						  }
						  allProducts = allProducts + "</tr>";
					  }
				  }
			  }
			  if(!isAnyContactForProduct){
				  allProducts = allProducts + "<tr><td style='margin-left: 10px;' colspan='3'><span>No contact found within the program age range or gender.";
				  allProducts = allProducts + "</span></td></tr>";
			  }
			  allProducts = allProducts + "</table>";
			  allProducts = allProducts + "</td>";
			  allProducts = allProducts + "</tr>";
		  }
		  allProducts = allProducts + "</table>";
		  
		  $("#familymembers").slideDown();
		  $("#allmembers").html(allProducts);
		  
		  for(var i=0;i<products.length;i++){
			  var product = products[i];
			  var contactsData = contacts.data();
			  for(var j=0;j<contactsData.length;j++){
				  var contact = contactsData[j];
				  if(product.allowToPickStartDate && product.allowToPickStartDate == 'Yes'){
					  $("#ccStartDate_"+product.id+"_"+contact.contactId).kendoDatePicker({
						  format: "MM/dd/yyyy",
						  min: new Date()
					  });
				  }
			  }
		  }
		  $("select[name='GradeLevel']").kendoDropDownList();
		  $("input[type='checkbox']").uniform();
	}
}

function showContactHealthHistoryOrCheckOut(cartType){
	console.log("   cartType  ::  "+cartType);
	var contactData = [];
	
	if(cartType && cartType == 'Event' || cartType && cartType == 'Facility'){
		contactData.push($("#loggedInUserContactId").val());
	}
	
	$('#allmembers').find('input[class="usercheck"]').each(function(){
		if($("#user_"+this.value).is(':checked')){
			contactData.push(this.value);
		}
	});
	if(contactData.length <= 0){
		  console.log("No contacts to show health history.");
		  $("#tcSuccessSpan").css("display", "none");		
		  $("#tcSuccessSpan" ).html("");	
		  $("#tcErrorSpan").css("display", "block");		
		  $( "#tcErrorSpan" ).html("Please select atleast one Member");
		  return ;
	}
	console.log(" showContactHealthHistory program ");
	var itemDetailsId = $.sessionStorage.getItem('itemDetailsId');
	var itemDetailIds = itemDetailsId.replace(",,", ",");
	console.log(" showContactHealthHistory   itemDetailIds  --->  "+itemDetailIds);
	if(itemDetailIds){
		$.ajax({
			  type: "GET", 
			  url:"isHealthHistoryRequired?itemDetailId="+itemDetailIds,
			  async:false,
			  dataType: "json",
			  success: function( data ) {
				  console.log(" showContactHealthHistory 361 ");
				  if(data && data.RESULT == 'YES'){
					  console.log(" getContactHistory 363 ");
					  //$("#contactHealthHistoryDiv").slideDown();
					  $("#checkout_content").hide();
					  if(validateCheckout(cartType)){
					  //if(validateCheckout(cartType)){
						  setTimeout(function(){
							  if(cart.errMsg==""){
								  $("#familymembers").fadeOut(200);
								  getContactHistory(contactData);
							  }
						  }, 1000);
					  }
				  }else{
					  console.log(" showContactHealthHistory 373 ");
					  if(validateCheckout(cartType)){
						  setTimeout(function(){
							  if(cart.errMsg==""){
								  $("#familymembers").fadeOut(200);
								  showEmergencyContactIfFlagIsSet();
								  /*if(cartType && cartType == 'CAMP'){
									  showCampActivityAndTransportService();
								  } else if(cartType && cartType == 'CHILD CARE'){
									  	showEmergencyContactIfFlagIsSet();
								  }else{
										$("#checkout_content").show();
								  }*/
							  }
						  }, 1000);
					  }
				  }
			  },
			  error: function( xhr,status,error ){
				  console.log(" isHealthHistoryRequired "+xhr);
			  }
		});
	}else{
		$("#contactHealthHistoryDiv").slideDown();
	}
}

function hideContactHealthHistory(){
	$("#contactHealthHistoryDiv").hide();
}

function addfamilyMember(){
	$.ajax({
		  type: "GET",
		  url:"addMember",
		  async:false,
		  success: function( data ) {
			  $("#popup_add").html(data);
			 // $("#popup_add").show();
		  },
		  error: function( xhr,status,error ){
			  console.log(xhr);
		  }
	});
}

function closeAllwindow(){

	 $("div.k-window1").each(function(index, element){
		var window = $(this);
		if (!window.data("kendoWindow")) {
			window.kendoWindow({
				width: "1050px"
			});
		}
		window.data("kendoWindow").close();
	});
}

function fnSignupTerms(){
	var isTCNeeded = false;
	//var products = items.data();
	if(cart.contents.length>0){
		$.each(cart.contents, function(i,itemsData) {
			var product = itemsData.item;
			if(product.needTC && product.needTC == 'Yes'){
				isTCNeeded = true;
			}
		});
	}
	
	$("#tcSuccessSpan").css("display", "none");		
	 $("#tcSuccessSpan" ).html("");	
	 $("#tcErrorSpan").css("display", "none");		
	 $( "#tcErrorSpan" ).html("");
	 
	/*for(var i=0;i<products.length;i++){
		var product = products[i];
		if(product.needTC && product.needTC == 'Yes'){
			isTCNeeded = true;
		}
	}*/
	
	if(isTCNeeded){
		$("#details-checkout").fadeOut("fast");
		$("#chkTermsAndCond").attr('checked', false);
		$("#tandc").show();
	}else{
		fnCheckout();
	}
}

function fnCheckout(){
	
	// all other should be hide here
	$("#contactActivityDiv").hide();
	$("#contactTransportDiv").hide();
	$("#tandc").hide();
	
	//console.log(" Checkout action ");
	
	 $("#tcSuccessSpan").css("display", "none");		
	 $("#tcSuccessSpan" ).html("");	
	 $("#tcErrorSpan").css("display", "none");		
	 $( "#tcErrorSpan" ).html("");
	 
	 /*
	 if(cartPreviewModel.totalPrice()=='$0.00'){
		 $("#tcErrorSpan").css("display", "block");		
		 $( "#tcErrorSpan" ).html("Please add atleast 1 program to signup");
		 return;
	 }*/
	// validation start
	 
	//fillPaymentMethodDD();
	$(".k-loading-mask").show();
	$("#payment_cart").show();
	$("#details-checkout").fadeOut("fast");
	$("#purchaseinfoPg").css("width", "1000px");	
	$("#purchase").fadeIn("slow", function(){
			$(".k-loading-mask").show();
			$("#pmPrimary").hide();
			$("#autopayBtn").hide();
			
			var trrow = "";
			if(cart.contents.length>0){
				$.each(cart.contents, function(i,itemDetailsSession) {
					
					//if(!itemDetailsSession.waitlist){
						var stdt = convertJsonDate(itemDetailsSession.item.start_date);
						var enddt = convertJsonDate(itemDetailsSession.item.end_date);
						var stTime = new Date(itemDetailsSession.item.start_time.time);
						var endTime = new Date(itemDetailsSession.item.end_time.time);
						var selectedStartDate = itemDetailsSession.selectedStartDate;
						/*
						if(itemDetailsSession.contact.isMember)
							discount = parseFloat(itemDetailsSession.item.memberdiscount) + parseFloat(itemDetailsSession.discount1);
						else
							discount = parseFloat(itemDetailsSession.item.nonmemberdiscount) + parseFloat(itemDetailsSession.discount1);
						
						if(itemDetailsSession.contact.isMember)
							itemprice = (parseFloat(itemDetailsSession.item.memberprice) - parseFloat(itemDetailsSession.item.memberdiscount) - parseFloat(itemDetailsSession.discount1)) * itemDetailsSession.quantity;
						else
							itemprice = (parseFloat(itemDetailsSession.item.nonmemberprice) - parseFloat(itemDetailsSession.item.nonmemberdiscount) - parseFloat(itemDetailsSession.discount1)) * itemDetailsSession.quantity;
						*/
						
						var signupPrice = itemDetailsSession.signupPrice;
						if(itemDetailsSession.proRatedSignupPrice && itemDetailsSession.proRatedSignupPrice > 0){
							signupPrice = itemDetailsSession.proRatedSignupPrice;
						}
						var discount = 0;
						if(itemDetailsSession.isMinPayment){
							if(itemDetailsSession.promotionMap != undefined){
								for(var j=0; j < itemDetailsSession.promotionMap.length; j++){
									var promo = itemDetailsSession.promotionMap[0];
									if(promo.PromoRuleType == 'Deposit'){
										discount += parseFloat(promo.actualDiscountValue);
									}
								}
							}
							//discount += parseFloat(getDiscountFromMap(itemDetailsSession, 'Deposit'));
						}else{
							
							/*discount += parseFloat(getDiscountFromMap(itemDetailsSession, 'Sign Up'));
							discount += parseFloat(getDiscountFromMap(itemDetailsSession, 'Registration'));
							discount += parseFloat(getDiscountFromMap(itemDetailsSession, 'SetUpFee'));
							discount += parseFloat(getDiscountFromMap(itemDetailsSession, 'JoinFee'));*/
							if(itemDetailsSession.promotionMap != undefined){
								for(var j=0; j < itemDetailsSession.promotionMap.length; j++){
									var promo = itemDetailsSession.promotionMap[j];
									if(promo.PromoRuleType == 'Sign Up' || promo.PromoRuleType == 'Registration' 
										|| promo.PromoRuleType == 'SetUpFee' || promo.PromoRuleType == 'JoinFee'){
										discount += parseFloat(promo.actualDiscountValue);
									}
								}
							}
							//discount = parseFloat(itemDetailsSession.autoDiscount) + parseFloat(itemDetailsSession.discount1);
						}
						
						var fa = parseFloat(itemDetailsSession.FAamount);
						
						var itemprice = 0;
						var subtotal = 0;
						
						var isChargeAmount = true;
		            	if(itemDetailsSession.item.programType == 'Child Care' && (itemDetailsSession.item.category != 'In-Service' || itemDetailsSession.item.category != 'After School') && itemDetailsSession.item.hasConfirmedDays == 'false'){
		    				isChargeAmount = false;
		    			}else if(itemDetailsSession.waitlist){
		    				//isChargeAmount = false;
		    			}
		            	
						//if(itemDetailsSession.item.programType == 'CHILD CARE'){ // && itemDetailsSession.item.category == 'After School'
						if(isChargeAmount){
			            	//if(itemDetailsSession.item.programType == 'CHILD CARE' && itemDetailsSession.item.category != 'IN-SERVICE'){
								//itemprice = calculateItemAmountOnCart(itemDetailsSession);
								if(itemDetailsSession.item.programType == 'Camp'){
									if(itemDetailsSession.isFullPayment){
										//console.log(" total --> isFullPayment true ");
										itemprice += calculateItemAmountOnCart(itemDetailsSession);
									}else{
										//console.log(" total --> isFullPayment false ");
										itemprice += calculateItemMinimumAmountOnCart(itemDetailsSession);
									}
								}else{
									itemprice += calculateItemAmountOnCart(itemDetailsSession);
								}
								
								subtotal = calculateSubTotalOnCart(itemDetailsSession);
							/*}else{
								itemprice = ((parseFloat(signupPrice) * itemDetailsSession.noOfTicketsOrPackages) + parseFloat(itemDetailsSession.joinFee) + parseFloat(itemDetailsSession.setupFee)  + parseFloat(itemDetailsSession.registrationPrice) + parseFloat(itemDetailsSession.depositAmount) - parseFloat(itemDetailsSession.autoDiscount) - parseFloat(itemDetailsSession.discount1) - parseFloat(itemDetailsSession.FAamount)) * itemDetailsSession.quantity;
								subtotal = (parseFloat(signupPrice) * itemDetailsSession.noOfTicketsOrPackages) + parseFloat(itemDetailsSession.joinFee) + parseFloat(itemDetailsSession.setupFee) + parseFloat(itemDetailsSession.registrationPrice) + parseFloat(itemDetailsSession.depositAmount);
							}*/
						}
						trrow = trrow + "<table width='100%'><tr>";
						trrow = trrow + "<td><span><strong>" + itemDetailsSession.contact.fname + " " + itemDetailsSession.contact.lname + "";
						
						if(itemDetailsSession.item.programType=='Program' || itemDetailsSession.item.programType=='Event' || (itemDetailsSession.item.programType=='Child Care' && itemDetailsSession.item.category == 'After School')){
							if(itemDetailsSession.waitlist)
								trrow = trrow + "<span class='alertNotice'> (WAITLISTED)</span>"
						}
						else if(itemDetailsSession.item.programType=='Child Care'){
							if(itemDetailsSession.item.hasWLDays){
								trrow = trrow + "<span class='alertNotice'> ("+itemDetailsSession.item.WL_Text_For_CC+")</span>"
							}
						}
								
								
						trrow = trrow + "<br>"  + itemDetailsSession.item.name + " [Category: " + itemDetailsSession.item.category + "]</strong>";
								if(selectedStartDate && selectedStartDate != ''){
									trrow = trrow + "<br>Selected Start Date: "+selectedStartDate;
								}
								trrow = trrow + "</span></td>";
						if(itemDetailsSession.item.category != 'Transportation'){
							trrow = trrow + "<td><span>" + itemDetailsSession.item.days + "</span></td>";
						}
						trrow = trrow + "<td><span>" + stdt;
						if(itemDetailsSession.item.category != 'Transportation'){
							trrow = trrow + " - " + enddt;
						}
						trrow = trrow + "</span></td>";
						trrow = trrow + "<td><span>" + formatTime(stTime);
						if(itemDetailsSession.item.category != 'Transportation'){
							trrow = trrow + " - " +  formatTime(endTime);
						}
						trrow = trrow + "</span></td>";
						trrow = trrow + "</tr>";
						
						trrow = trrow + "<tr height='10px;'>";
						trrow = trrow + "<td colspan='3' align='right'>Sub Total</td>";
						/*
						if(itemDetailsSession.contact.isMember)
							trrow = trrow + "<td align='center'><span>" + currencyFormat(itemDetailsSession.item.memberprice) + "</span></td>";
						else
							trrow = trrow + "<td align='center'><span>" + currencyFormat(itemDetailsSession.item.nonmemberprice) + "</span></td>";
						*/
	
						trrow = trrow + "<td align='center'><span>" + currencyFormat(subtotal) + "</span></td>";
						trrow = trrow + "</tr>";
						
						trrow = trrow + "<tr height='10px;'>";
						trrow = trrow + "<td colspan='3' align='right'>Discount</td>";
						trrow = trrow + "<td align='center'><span class='discountpricelbl'>-" + currencyFormat(discount) + "</span></td>";
						trrow = trrow + "</tr>";
						
						trrow = trrow + "<tr height='10px;'>";
						trrow = trrow + "<td colspan='3' align='right'>FA Amount</td>";
						trrow = trrow + "<td align='center'><span class='discountpricelbl'>-" + currencyFormat(fa) + "</span></td>";
						trrow = trrow + "</tr>";
						
						trrow = trrow + "<tr height='10px;'>";
						trrow = trrow + "<td colspan='3' align='right'>Final amount1</td>";
						trrow = trrow + "<td align='center'><span>" + currencyFormat(itemprice) + "</span></td>";
						trrow = trrow + "</tr>";
						
						trrow = trrow + "<tr height='10px;'>";
						trrow = trrow + "<td></td>";
						trrow = trrow + "</tr></table>";
					//}
				});
			}
			$("#cartitem").html(trrow);
			$("#ordertotal").text(cartPreviewModel.totalPrice());
			
			$(".k-loading-mask").hide();
	});
}

function cancelProgram(signupId){
	
}

function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(location.search);
    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}

function checkKeyPressEventIsNumeric(e){
	if(e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)){
		return false;
    }
}

function fillPaymentMethodDD(){
	var paymentMethodHtml = '';		
	$.ajax({
		  
		  type: "GET",
		  url: "viewPaymentInformationByLoggedInUser",								  
		  success: function( data ) {				  	  
			  if(data != null && data.length > 0){	
				  $.ajax({
					  type: "GET",
					  url: "getACHPaymentInformationByLoggedInUser",								  
					  success: function( achData ) {											  										  
						  									  
						  if(data != null && data.length > 0){														 
							  for(var i = 0; i<data.length; i++){        
								   var dataArr = data[i];
								   //p.portalStatus, p.paymentType, p.isPrimary, p.paymentId, p.nickName, p.cardNumber, p.expirationMonth, p.expirationYear										       
								   //paymentInfoRadio						   
								   /* if(dataArr[2]){
									   paymentMethodHtml += '<option selected value="'+ dataArr[8] +'">'+ dataArr[4] +' '+dataArr[5] +' '+dataArr[6] +'/'+dataArr[7]+'</option>';
									   $("#paymentTokenIdSpan").html(dataArr[8]);
								   }else{
									   paymentMethodHtml += '<option value="'+ dataArr[8] +'">'+ dataArr[4] +' '+dataArr[5] +' '+dataArr[6] +'/'+dataArr[7]+'</option>';
								   }	 */				
								   if(dataArr[2]){
									   paymentMethodHtml += '<option selected value="'+ dataArr[9] + '_' + dataArr[6] +'">'+ dataArr[7] +' '+dataArr[3] +' '+dataArr[4] +'/'+dataArr[5]+'</option>';
									   $("#paymentTokenIdSpan").html(dataArr[6]);
									}else{
									   paymentMethodHtml += '<option value="'+ dataArr[9] + '_' + dataArr[6] +'">'+ dataArr[7] +' '+dataArr[3] +' '+dataArr[4] +'/'+dataArr[5]+'</option>';
									}
							  }
							  paymentMethodHtml += '<option value="New">Add New Card</option>';
							  paymentMethodHtml += '<option value="NewBank">Add New Bank Info</option>';
							  return paymentMethodHtml;
						  }
				  		  if(achData != null && achData.length > 0){
							  for(var i = 0; i<achData.length; i++){        
							       var dataArr = achData[i];
							       //p.transId,  p.bankRoutingNumber, p.fullName, p.paymentType,  p.checkingAccountNumber, p.driversLicenseNumber, p.stateOfDL, p.phoneNumber, p.nickName, p.portalStatus,  p.tokenNumber , p.paymentId, p.isPrimary											       
							       if(dataArr[12]){
							    	   paymentMethodHtml += '<option selected value="'+ dataArr[11] + '_' + dataArr[10] +'">'+ dataArr[2] +', '+dataArr[4] +'</option></span>';
							    	   $("#paymentTokenIdSpan").html(dataArr[8]);
							       }else{
							    	   paymentMethodHtml += '<option value="'+ dataArr[11] + '_' + dataArr[10] +'">'+ dataArr[2] +', '+dataArr[4] +'</option></span>';
							       }													       
						      }
							  paymentMethodHtml += '<option value="New">Add New Card</option>';
							  paymentMethodHtml += '<option value="NewBank">Add New Bank Info</option>';
							  return paymentMethodHtml;
				  		  }
					      
					  },
					  error: function( xhr,status,error ){
						  $("#statusBlock").css("display", "block");	
						  $("#tcloginErrorSpan").css("display", "block");	
						  $( "#tcloginErrorSpan" ).html("There was some error. Please try again later");	
						  $(".k-loading-mask").hide();								  		 
					  }
					});
				}										 
		  },
		  error: function( xhr,status,error ){
			  $("#statusBlock").css("display", "block");	
			  $("#tcloginErrorSpan").css("display", "block");	
			  $( "#tcloginErrorSpan" ).html("There was some error. Please try again later");	
			  $(".k-loading-mask").hide();								  		 
		  }
	});
	
	//paymentMethodHtml += '<option value="New">Add New Card</option>';
    //paymentMethodHtml += '<option value="NewBank">Add New Bank Info</option>';
    //$("#paymentInfoRadio").html(paymentMethodHtml);
    //$("#paymentInfoRadio").kendoDropDownList();
}

function computePrice(arr, type){
	//console.log(arr);
	var pricecell = "";
 	$.each(arr, function(i,price) {

 		if(price.type=='Sign Up'){
 			pricecell = pricecell + "<span>" +price.priceOption + "</span>";
 			if(price.priceOption!="")
 				pricecell = pricecell + "<span>: </span>";
 			
 			if(type=="M")
 				pricecell = pricecell + "<span>$" +price.memberPrice + "</span>";
 			else
 				pricecell = pricecell + "<span>$" +price.nonmemberPrice + "</span>";
 			
 			pricecell = pricecell + "<br>";
 		}
 	});
 	
 	return pricecell;
}

function getSetupFeeByItemDetailIdAndPartyId(itemDetailId,partyId){
	var setupfee = 0;
	$.ajax({
		  type: "GET",
		  async: false,
		  url:"getSetupFeeByItemDetailIdAndPartyId?itemDetailId="+itemDetailId+"&partyId="+partyId,
		  dataType: "json",
		  success: function( data ) {
			  //console.log(data);
			  setupfee = data;
		  },
		  error: function( xhr,status,error ){
			  //alert("1" +status);
			  $("#tcSuccessSpan").css("display", "none");		
			  $("#tcSuccessSpan" ).html("");	
			  $("#tcErrorSpan").css("display", "block");		
			  $( "#tcErrorSpan" ).html("Error while getting setup fee");
			 
		  }
	});
	return setupfee;
}

function getRegistrationFeeByItemDetailIdAndPartyId(itemDetailId,partyId){
	var registrationFee = 0;
	$.ajax({
		  type: "GET",
		  async: false,
		  url:"getRegistrationFeeByItemDetailIdAndPartyId?itemDetailId="+itemDetailId+"&partyId="+partyId,
		  dataType: "json",
		  success: function( data ) {
			  //console.log("Registration Fee for "+itemDetailId +" and " + partyId + ": " +data);
			  registrationFee = data;
		  },
		  error: function( xhr,status,error ){
			  //alert("1" +status);
			  $("#tcSuccessSpan").css("display", "none");		
			  $("#tcSuccessSpan" ).html("");	
			  $("#tcErrorSpan").css("display", "block");		
			  $( "#tcErrorSpan" ).html("Error while getting Regsitration fee");
			 
		  }
	});
	return registrationFee;
}

function getFAObj(itemDetailId){
	var FAObj;
	$.ajax({
		  type: "GET",
		  async: false,
		  url:"getFA?itemDetailId="+itemDetailId,
		  dataType: "json",
		  success: function( data ) {
			  //console.log(data);
			  FAObj = data;
		  },
		  error: function( xhr,status,error ){
			  //alert("1" +status);
			  $("#tcSuccessSpan").css("display", "none");		
			  $("#tcSuccessSpan" ).html("");	
			  $("#tcErrorSpan").css("display", "block");		
			  $( "#tcErrorSpan" ).html("Error while getting setup fee");
			 
		  }
	});
	return FAObj;
}

/*function withinRegistrationDate(iIds, cIds){
	var proceed = false;
	
	var itemDetailsId = iIds.split(",");
	var contactId = cIds.split(",");
	for(var c = 0; c<contactId.length; c++){  
		for(var i = 0; i<itemDetailsId.length; i++){  
			$.ajax({
				  type: "GET",
				  async: false,
				  url:"checkProgramRegistrationDate?itemDetailId="+itemDetailsId[i]+"&contactId="+contactId[c],
				  dataType: "json",
				  success: function( data ) {
					  //console.log(data);
					  if(data)
						  proceed = true;
					  else
						  proceed = false;
				  },
				  error: function( xhr,status,error ){
					  proceed = false;
					 
				  }
			});
			if(!proceed)
				break;
		}
	}
	
	//alert(proceed);
	return proceed;
}*/

function withinRegistrationDate(item, contact){
	var proceed = false;
	
	var memberRegistrationStartDate = convertJavaDateToJSDate(item.memberRegistrationStartDate);
	var memberRegistrationEndDate = convertJavaDateToJSDate(item.memberRegistrationEndDate);
	var nonMemberRegistrationStartDate = convertJavaDateToJSDate(item.nonMemberRegistrationStartDate);
	var nonMemberRegistrationEndDate = convertJavaDateToJSDate(item.nonMemberRegistrationEndDate);
	//console.log("item.end_date  >>  "+item.end_date_str);
	var end_date = convertJavaDateToJSDate(item.end_date_str);
	
	var today = new Date();
	/*var memberRegistrationStartDate = new Date(item.memberRegistrationStartDate);
	var memberRegistrationEndDate = new Date(memberRegistrationEndDateStr);
	var nonMemberRegistrationStartDate = new Date(item.nonMemberRegistrationStartDate);
	var nonMemberRegistrationEndDate = new Date(item.nonMemberRegistrationEndDate);*/
	
	/*console.log(" withinRegistrationDate -- contact.isMember  "+contact.isMember);
	console.log(" withinRegistrationDate -- memberRegistrationStartDate  "+memberRegistrationStartDate);
	console.log(" withinRegistrationDate -- memberRegistrationEndDate  "+memberRegistrationEndDate);
	console.log(" withinRegistrationDate -- nonMemberRegistrationStartDate  "+nonMemberRegistrationStartDate);
	console.log(" withinRegistrationDate -- nonMemberRegistrationEndDate  "+nonMemberRegistrationEndDate);
	*/
	
	var isYagent = false;
	$.ajax({
		  type: "GET",
		  async: false,
		  url:"isYAgent",
		  success: function( data ) {
			  if(data && data == 'true'){
				  isYagent = true;
			  }
		  },
		  error: function( xhr,status,error ){
			  console.log(xhr);
		  }
	});
	//console.log(" isYagent  "+isYagent);
	if(isYagent){
		if(today <= end_date){
			proceed = true;
		}
	}else{
		if(contact.isMember){
			if(memberRegistrationStartDate <= today && today <= memberRegistrationEndDate){
				proceed = true;
			}
		}else{
			if(nonMemberRegistrationStartDate <= today && today <= nonMemberRegistrationEndDate){
				proceed = true;
			}
		}
	}
	
	if(!proceed){
		
		var isRegistrationExpired = false;
		if(contact.isMember){
			if(today > memberRegistrationEndDate){
				isRegistrationExpired = true;
			}
		}else{
			if(today > nonMemberRegistrationEndDate){
				isRegistrationExpired = true;
			}
		}
		//console.log("   isRegistrationExpired   "+isRegistrationExpired);
		
		if(!isRegistrationExpired){
			$.ajax({
				  type: "GET",
				  async: false,
				  url:"isBypassMembershipRegistrationDateRange?itemId="+item.itemDetailsId+"&contactId="+contact.contactId,
				  //url:"getPromoMap",
				  //data: { 'itemId':item, 'contactId': contact, 'isAuto': 'true' },
				  dataType: "json",
				  success: function( data ) {
					  
					  if(data && data.RESULT == 'YES'){
						  
						  proceed = true;
						  
						  //console.log("	isBypassMembershipRegistrationDateRange  YES ");
					  }else{
						  //console.log("	isBypassMembershipRegistrationDateRange  NO ");
					  }
					  
				  },
				  error: function( xhr,status,error ){
					  console.log(xhr);
				  }
			});
		}
	}
	
	//console.log("  proceed :: "+proceed);
	
	/*var itemContactMapStr = $.sessionStorage.getItem('itemContactMapStr');
	var itemContactMap = itemContactMapStr.split(",");
	
	for(var i = 0; i<itemContactMap.length; i++){
		var itemContactStr = itemContactMap[i];
		var itemContact = itemContactStr.split("_");
		$.ajax({
			  type: "GET",
			  async: false,
			  url:"checkProgramRegistrationDate?itemDetailId="+itemContact[0]+"&contactId="+itemContact[1],
			  dataType: "json",
			  success: function( data ) {
				  //console.log(data);
				  if(data)
					  proceed = true;
				  else
					  proceed = false;
			  },
			  error: function( xhr,status,error ){
				  proceed = false;
				 
			  }
		});
		if(!proceed)
			break;
	}*/
	return proceed;
}

function checkAndUpdateCapacity(){
	var proceed = false;
	var itemIdsAndremainingCapacity = "";
	var itemIdsConfirmedAndWaitlistedItems = [];
	
	//var itemDetailsId = $.sessionStorage.getItem('itemDetailsId');
	//uniqueitemDetailsId = ArrNoDupe(itemDetailsId);
	//console.log(uniqueitemDetailsId);
	
	//alert(itemDetailsId);
	if(cart.contents.length>0){
		var itemDetailsId = getUniqueItemDetailIdsFromCart(cart).join(",");
		
		var itemDetailId = itemDetailsId.split(",");
		for(var k=0; k<itemDetailId.length; k++){
			var itemIdsAndSelectedDays = [];
			var confirmedItems = 0;
			var waitlistedItems = 0;
			
			$.each(cart.contents, function(i,itemsData) {
				if(itemDetailId[k]==itemsData.item.itemDetailsId){
				
					if(itemsData.item.programType=='Child Care' && itemsData.item.category!='After School'){ // this is for childcare and in-service
						var daysId = itemsData.item.dayId;
						daysId = daysId.replace(new RegExp(",", 'g'), ";");
						var hasWLDays = itemsData.item.hasWLDays;
						var selectedDays = itemsData.item.days;
						selectedDays = selectedDays.replace(new RegExp(",", 'g'), ";");
						
						var WLDays = " ";
						if(WLDays.length>0){
							 WLDays = itemsData.item.WLDays;
						}
						itemIdsAndSelectedDays.push(itemDetailId[k]+" AND "+selectedDays+" AND "+daysId+" AND "+hasWLDays+" AND "+WLDays);
					}
					else{
						//console.log("   itemsData.item.programType  >>>  "+itemsData.item.programType);
						var noOfTickets = 1;
						if(itemsData.item.programType == 'Event'){
							noOfTickets = parseInt(itemsData.noOfTickets)
						}
						//console.log("   itemsData.noOfTickets  >>>  "+itemsData.noOfTickets);
						if(itemsData.item.itemDetailsId==itemDetailId[k]){
							if(itemsData.waitlist)
								waitlistedItems = parseInt(waitlistedItems) + parseInt(noOfTickets);
							else
								confirmedItems = parseInt(confirmedItems) + parseInt(noOfTickets);
						}
					}
				}
			});
			itemIdsConfirmedAndWaitlistedItems.push(itemDetailId[k]+"_"+confirmedItems+"_"+waitlistedItems+"_"+itemIdsAndSelectedDays.join('::')+"_END");
		}
	}
	
	//console.log(itemIdsConfirmedAndWaitlistedItems);
	//alert(itemIdsConfirmedAndWaitlistedItems);
	if(itemIdsConfirmedAndWaitlistedItems.length>0){
		$.ajax({
			  type: "GET",
			  async: false,
			  url:"checkAndUpdateCapacity?itemIdsConfirmedAndWaitlistedItems="+itemIdsConfirmedAndWaitlistedItems.join(",").replace(new RegExp("__", 'g'), "_ _"),
			  success: function( data ) {
				  //alert("data: "+data)
				  //console.log(data);
				  var capacityUpdateStatus = data.split("^")[0];
				  itemIdsAndremainingCapacity = data.split("^")[1];
				  //alert("capacityUpdateStatus:"+capacityUpdateStatus);
				  //console.log(data);
				  if(capacityUpdateStatus=="true")
					  proceed = true;
				  else{
					  proceed = false;
					  //alert("itemIdsAndremainingCapacity: "+itemIdsAndremainingCapacity);
					  
				  }
			  },
			  error: function( xhr,status,error ){
				  proceed = false;
				 
			  }
		});
	}
	
	/*
	if(!proceed && itemIdsAndSelectedDays.length>0){
		proceed = true;
	}*/
	//UpdateCapacityAfterPaymentFail();
	//proceed = false;
	if(!proceed){
		$("#tcSuccessSpan").css("display", "none");		
		$("#tcSuccessSpan" ).html("");	
		$("#tcErrorSpan").css("display", "block");		
		$( "#tcErrorSpan" ).html("Sorry the programs you selected has already been booked, Please check your updated cart");
		
		if(itemIdsAndremainingCapacity!=""){
			 cartPreviewModel.updateWaitlistIncart(itemIdsAndremainingCapacity);
		}
		
		setTimeout(function(){
			gotocartpage();
		}, 3000);
		
	}
	
	return proceed;
}

function UpdateCapacityAfterPaymentFail(){
	var itemDetailsId = $.sessionStorage.getItem('itemDetailsId');
	//itemDetailsId = ArrNoDupe(itemDetailsId);
	
	if(cart.contents.length>0){

		$.each(cart.contents, function(i,itemsData) {
			var itemIdsAndSelectedDays = [];
			var confirmedItems = 0;
			var waitlistedItems = 0;
			
			if(itemsData.item.programType=='Child Care' && itemsData.item.category!='After School'){
				var daysId = itemsData.item.dayId;
				var hasWLDays = itemsData.item.hasWLDays;
				var selectedDays = itemsData.item.days;
				selectedDays = selectedDays.replace(new RegExp(",", 'g'), ";");
				
				var WLDays = " ";
				if(WLDays.length>0){
					 WLDays = itemsData.item.WLDays;
				}
				itemIdsAndSelectedDays.push(selectedDays+" AND "+daysId+" AND "+hasWLDays+" AND "+WLDays);
				
			} else {
				var qty = 1 ;
				if(itemsData.item.programType == 'Event'){
				     qty = parseInt(itemsData.noOfTickets)
				 }
				if(itemsData.waitlist)
					waitlistedItems = waitlistedItems + qty;
				else
					confirmedItems = confirmedItems + qty;
			}

			// TODO need to optimize and make call a single call  
			var itemDetailId =  itemsData.item.itemDetailsId ;
			$.ajax({
				  type: "GET",
				  async: false,
				  url:"UpdateCapacityAfterPaymentFail?itemDetailsId="+itemDetailId+"&confirmedItems="+confirmedItems+"&waitlistedItems="+waitlistedItems+"&itemIdsAndSelectedDays="+itemIdsAndSelectedDays.join('::'),
				  dataType: "json",
				  success: function( data ) {
					  console.log(data);
				  },
				  error: function( xhr,status,error ){
					  console.log(error);
				  }
			});
			
		});
	}
	
/*	$.ajax({
		  type: "GET",
		  async: false,
		  url:"UpdateCapacityAfterPaymentFail?itemDetailsId="+itemDetailsId+"&confirmedItems="+confirmedItems+"&waitlistedItems="+waitlistedItems,
		  dataType: "json",
		  success: function( data ) {
			  console.log(data);
		  },
		  error: function( xhr,status,error ){
			 			 
		  }
	});*/
}

/*function checkAgeRange(iIds, cIds){
	var proceed = false;
	var errMsg = "";
	
	$.ajax({
		  type: "GET",
		  async: false,
		  url:"checkAgeRange?itemDetailIds="+iIds+"&contactIds="+cIds,
		  success: function( data ) {
			  console.log(data);
			  var ageStatus = data.split("_")[0];
			  errMsg = data.split("_")[1];
			 // alert(ageStatus);
			  if(ageStatus=="true")
				  proceed = true;
			  else
				  proceed = false;
		  },
		  error: function( xhr,status,error ){
			  proceed = false;
			 
		  }
	});
	
	//alert(proceed);
	if(!proceed){
		$("#tcSuccessSpan").css("display", "none");		
		$("#tcSuccessSpan" ).html("");	
		$("#tcErrorSpan").css("display", "block");		
		$( "#tcErrorSpan" ).html("You cannot register for program due to below errors: <br>"+errMsg);
		return;
	}
	
	return proceed;
}*/

function isMember(contactId){
	var proceed = false;
	
	$.ajax({
		  type: "GET",
		  async: false,
		  url:"checkIfMember?contactId="+contactId,
		  success: function( data ) {
			  
			  if(data)
				  proceed = true;
			  else
				  proceed = false;
		  },
		  error: function( xhr,status,error ){
			  proceed = false;
			 
		  }
	});
	
	return proceed;
}

function showCampActivityAndTransportService(){
	//$("#checkout_content").show();
	hideAllWizrad();

	var cIds = "";
	var item_Detail_Ids =  "" ; 
	$('#allmembers').find('input[class="usercheck"]').each(function(){
		if($("#user_"+this.value).is(':checked')){
			var cId = this.value.split("_")[1];
			var contact = getContactDataSource(cId);
			cIds =  cIds + this.value +"_" + contact.fname + " "  + contact.lname  + "," ;
		}
	});
	/*$('#program_session').find('input[name="selectedItemSession"]').each(function(){
		if($(this).is(':checked')){
			item_Detail_Ids = item_Detail_Ids + this.value + "," ;
		}
	});*/
	
	item_Detail_Ids = $.sessionStorage.getItem('itemDetailsId');
	
	var ccItemDetailIds = [];
	$('#childcare_list').find('input[name="days_slot"]').each(function(){
		if($("#slot_"+this.value).is(':checked')){
			var itemdayId = this.value;
			var itemDetailsId = itemdayId.split("_")[1];
			ccItemDetailIds.push(itemDetailsId);
		}
	});
	var inServiceItemDetailId =  $("input[name=inServiceRadio]:checked").val();
	if(inServiceItemDetailId && inServiceItemDetailId > 0){
		ccItemDetailIds.push(inServiceItemDetailId);
	}
	
	var afterSchoolItemDetailId =  $("input[name=afterSchoolRadio]:checked").val();
	if(afterSchoolItemDetailId && afterSchoolItemDetailId > 0){
		ccItemDetailIds.push(afterSchoolItemDetailId);
	}
	ccItemDetailIds = ArrNoDupe(ccItemDetailIds);
	
	console.log("  ccItemDetailIds  1276 : "+ccItemDetailIds);
	
	//item_Detail_Ids = item_Detail_Ids.slice(0,-1);
	//item_Detail_Ids = item_Detail_Ids + ""+ ccItemDetailIds.join(",");
	cIds = cIds.slice(0,-1);
	console.log("  item_Detail_Ids  1277 : "+item_Detail_Ids);
	loadActivityTransportData(item_Detail_Ids,cIds,false);
	//gotocheckout();
}

function showEmergencyContactIfFlagIsSet(){
	var itemDetailIds = $.sessionStorage.getItem('itemDetailsId');
	$.ajax({
		  type: "GET", 
		  url:"showEmergencyContact?itemDetailsId="+itemDetailIds,
		  async:false,
		  dataType: "json",
		  success: function( data ) {
			  if(data){
				  $("#familymembers").fadeOut(200);
				  $("#checkout_content").hide();
				  $("#contactHealthHistoryDiv").hide();
				  showEmergencyContact();
			  }else{
				  /*
				  $("#familymembers").fadeOut(200);
				  $("#checkout_content").show();
				  $("#contactHealthHistoryDiv").hide();*/
				  //gotocheckout();
				  showAuthorisedContactIfFlagIsSet();
			  }
		  },
		  error: function( xhr,status,error ){
			  //alert("1" +status);
			  console.log(" Error on showEmergencyContactIfFlagIsSet: "+xhr);
			  showAuthorisedContactIfFlagIsSet();
		  }
	});
}

/*function showEmergencyContact(){
	$.ajax({
		  type: "GET",
		  async: false,
		  url:"getEmergencyContacts",
		  dataType: "json",
		  success: function( data ) {
			  if(data.length>0){
				  var allContacts = "";
				 
				  $.each(data, function(i,member) {
					  //alert(member.isChild);
					  allContacts = allContacts + "<div style='margin: 10px;'>";
					  allContacts = allContacts + "<div style='float:left; width:200px;'>";
					  if(member.isAdult)
						  allContacts = allContacts + "<span><input type='radio' name='emergency_user_' id='user_"+member.contactId+"' value='"+member.contactId+"' class='usercheck' checked='checked'></span>";
					  else
						  allContacts = allContacts + "<span><input type='radio' name='emergency_user_' id='user_"+member.contactId+"' value='"+member.contactId+"' class='usercheck'></span>";
					  allContacts = allContacts + "&nbsp;&nbsp;<span class='name'>"+member.fname + " " +member.lname+"</span>";
					  allContacts = allContacts + "</div>";
					  
					  
					  allContacts = allContacts + "<div style='float:left;'>";
					  allContacts = allContacts + "&nbsp;&nbsp;<span>["+member.age+"]</span>";
					  allContacts = allContacts + "</div>";
					  
					  
					  allContacts = allContacts + "</div>";
					  allContacts = allContacts + "<div style='clear: both;'></div> ";
				  });
				  
				  $("#emergencyContact").slideDown();
				  $("#allEmergencyContacts").html(allContacts);
				  $("input[type='checkbox']").uniform();
				  $("#input[name='membertype_']").kendoDropDownList();
			  }
		  },
		  error: function( xhr,status,error ){
			  //alert("1" +status);
			  console.log(xhr);
			 
		  }
	});
}*/


function hideAllWizrad(){
	$("#familymembers").fadeOut(200);
	$("#contactHealthHistoryDiv").hide();
	$("#emergencyContact").hide();
	$("#authorisedContact").hide();
	$("#contactActivityDiv").hide();
	$("#contactTransportDiv").hide();
	$(".k-loading-mask").hide();
	$("#dspErr").hide();
}
function gotocheckout(){
	hideAllWizrad();
	$(".k-loading-mask").show();
	$("#details-checkout").css("width", "1000px");	
	$("#checkout_content").show();
	$(".k-loading-mask").hide();
	$("#tcSuccessSpan").css("display", "none");
	$("#tcSuccessSpan" ).html("");
	$("#tcErrorSpan").css("display", "none");
	$("#tcErrorSpan" ).html("");
}

function showAuthorisedContactIfFlagIsSet(){
	var itemDetailIds = $.sessionStorage.getItem('itemDetailsId');
	$.ajax({
		  type: "GET", 
		  url:"showAuthorisedContact?itemDetailsId="+itemDetailIds,
		  async:false,
		  dataType: "json",
		  success: function( data ) {
			  if(data){
				  $("#familymembers").fadeOut(200);
				  $("#checkout_content").hide();
				  $("#contactHealthHistoryDiv").hide();
				  $("#emergencyContact").hide();
				  showAuthorisedContact();
			  }else{
				  $("#familymembers").fadeOut(200);
				  $("#checkout_content").hide();
				  $("#contactHealthHistoryDiv").hide();
				  $("#emergencyContact").hide();
				  
				  //showCampActivityAndTransportService();
				  gotocheckout();
			  }
		  },
		  error: function( xhr,status,error ){
			  //alert("1" +status);
			  console.log(" Error on showAuthorisedContactIfFlagIsSet: "+xhr);
			  //showCampActivityAndTransportService();
			  //gotocheckout();
		  }
	});
}

function showEmergencyContact(){
	$.ajax({
		  type: "GET",
		  async: false,
		  url:"getEmergencyContacts",
		  dataType: "json",
		  success: function( data ) {
			  if(data.length>0){
				  loadContacts();
				  var products = items.data();
				  
				  var allProducts = "<table>";
				  allProducts = allProducts + "<tr style='margin-left: 10px;'>";
				  allProducts = allProducts + "<td style='margin-left: 20px;' width='150px'><b>Items</b></td>";
				  allProducts = allProducts + "<td>";
				  allProducts = allProducts + "<table>";
				  allProducts = allProducts + "<tr>";
				  allProducts = allProducts + "<td style='margin-left: 10px;' width='30px'></td>";
				  allProducts = allProducts + "<td style='margin-left: 20px;' width='150px'><b>Contact</b></td>";
				  allProducts = allProducts + "<td style='margin-left: 20px;' width='150px'><b>Age</b></td>";
				  allProducts = allProducts + "</tr>";
				  allProducts = allProducts + "</table>";
				  allProducts = allProducts + "</td>";
				  allProducts = allProducts + "</tr>";
				  
				  allProducts = allProducts + "<tr><td colspan='2'><hr></td></tr>";
				  
				  for(var i=0;i<products.length;i++){
					  var product = products[i];
					  allProducts = allProducts + "<tr style='margin-left: 10px;'>";
					  allProducts = allProducts + "<td style='margin-left: 20px; vertical-align: top;' width='150px'>";
					  allProducts = allProducts + "<span class='name'><b>"+product.name+"</b></span>";
					  allProducts = allProducts + "</td>";
					  
					  allProducts = allProducts + "<td>";
					  allProducts = allProducts + "<table>";
					  var noOfContacts = 0;
					  var contactsData = contacts.data();
					  for(var j=0;j<contactsData.length;j++){
						  var contact = contactsData[j];
						  var isEmergencyContact = false;
						  var emergencyContact;
						  for(var k=0;k<data.length;k++){
							  if(contact.contactId == data[k].id){
								  isEmergencyContact = true;
								  emergencyContact = data[k];
							  }
						  }
						  if(isEmergencyContact){
							  if(emergencyContact.age >= 18){
								  noOfContacts++;
								  allProducts = allProducts + "<tr>";
								  
									  if(emergencyContact && emergencyContact.isLoggedInUser && emergencyContact.isLoggedInUser == 'Y'){
										  allProducts = allProducts + "<td style='margin-left: 10px;' width='30px'><span><input type='radio' name='emg_user_"+product.id+"' id='emg_user_"+product.id+"_"+contact.contactId+"' value='"+contact.contactId+"' class='usercheck'  checked='checked'></span></td>";  
									  }else{
										  allProducts = allProducts + "<td style='margin-left: 10px;' width='30px'><span><input type='radio' name='emg_user_"+product.id+"' id='emg_user_"+product.id+"_"+contact.contactId+"' value='"+contact.contactId+"' class='usercheck'  ></span></td>";
									  }
									  
									  allProducts = allProducts + "<td style='margin-left: 20px;' width='150px'><span class='name'>"+contact.fname+" "+contact.lname+"</span></td>";
									  if(emergencyContact){
										  allProducts = allProducts + "<td style='margin-left: 20px;' width='150px'><span>["+emergencyContact.age+"]</span></td>";
								  }
								  
								  allProducts = allProducts + "</tr>";
							  }
						  }
					  }
					  
					  if(noOfContacts == 0){
						  allProducts = allProducts + "<tr><td style='margin-left: 10px;' colspan='3'><span>No adult contact found.";
						  allProducts = allProducts + "</span></td></tr>";
					  }
					  
					  allProducts = allProducts + "</table>";
					  allProducts = allProducts + "</td>";
					  allProducts = allProducts + "</tr>";
				  }
				  
				  $("#EmgContactCounter").val(noOfContacts);
				 
				  allProducts = allProducts + "</table>";
				  
				  $("#emergencyContact").slideDown();
				  $("#allEmergencyContacts").html(allProducts);
				  $("input[type='radio']").uniform();
			  }
		  },
		  error: function( xhr,status,error ){
			  console.log(xhr);
		  }
	});
}

function isValidEmergencyContact(){
	var products = items.data();
	for(var i=0;i<products.length;i++){
		  var product = products[i];
		  if($("#EmgContactCounter").val() > 0){
			  // Do Nothing
			  /*if(!$("input[name=emg_user_"+product.id+"]:checked").val()){
				  	$("#tcSuccessSpan").css("display", "none");
					$("#tcSuccessSpan" ).html("");
					$("#tcErrorSpan").css("display", "block");
					$("#tcErrorSpan" ).html("Please select emergency contact for each item");
					return false;
			  }*/
		  }else{
			  	$("#tcSuccessSpan").css("display", "none");
				$("#tcSuccessSpan" ).html("");
				$("#tcErrorSpan").css("display", "block");
				$("#tcErrorSpan" ).html("Please select emergency contact for each item");
				return false;
		  }
	}
	return true;
}

function showAuthorisedContact(){
	$.ajax({
		  type: "GET",
		  async: false,
		  url:"getAuthorisedContacts",
		  dataType: "json",
		  success: function( data ) {
			  if(data.length>0){
				  loadContacts();
				  var products = items.data();
				  
				  var allProducts = "<table>";
				  allProducts = allProducts + "<tr style='margin-left: 10px;'>";
				  allProducts = allProducts + "<td style='margin-left: 20px;' width='150px'><b>Items</b></td>";
				  allProducts = allProducts + "<td>";
				  allProducts = allProducts + "<table>";
				  allProducts = allProducts + "<tr>";
				  allProducts = allProducts + "<td style='margin-left: 10px;' width='30px'></td>";
				  allProducts = allProducts + "<td style='margin-left: 20px;' width='250px'><b>Contact</b></td>";
				  /*allProducts = allProducts + "<td style='margin-left: 20px;' width='150px'><b>Age</b></td>";*/
				  allProducts = allProducts + "</tr>";
				  allProducts = allProducts + "</table>";
				  allProducts = allProducts + "</td>";
				  allProducts = allProducts + "</tr>";
				  
				  allProducts = allProducts + "<tr><td colspan='2'><hr></td></tr>";
				  
				  for(var i=0;i<products.length;i++){
					  var product = products[i];
					  allProducts = allProducts + "<tr style='margin-left: 10px;'>";
					  allProducts = allProducts + "<td style='margin-left: 20px; vertical-align: top;' width='150px'>";
					  allProducts = allProducts + "<span class='name'><b>"+product.name+"</b></span>";
					  allProducts = allProducts + "</td>";
					  
					  allProducts = allProducts + "<td>";
					  allProducts = allProducts + "<table>";
					  
					  var contactsData = contacts.data();
					  
					  allProducts = allProducts + "<tr>";
					  allProducts = allProducts + "<td style='margin-left: 10px;' width='250px'><span>";
					  allProducts = allProducts + "<select id='auth_user_"+product.id+"' multiple='multiple'>";
					  for(var j=0;j<contactsData.length;j++){
						  var contact = contactsData[j];
						  var isEmergencyContact = false;
						  var emergencyContact;
						  for(var k=0;k<data.length;k++){
							  if(contact.contactId == data[k].id){
								  isEmergencyContact = true;
								  emergencyContact = data[k];
							  }
						  }
						  if(isEmergencyContact){
							  allProducts = allProducts + "<option value='"+contact.contactId+"'>"+contact.fname+" "+contact.lname+" ["+emergencyContact.age+"]</option>";
						  }
					  }
					  allProducts = allProducts + "</select>";
					  
					  allProducts = allProducts + "</span></td>";
					  allProducts = allProducts + "</tr>";
					  
					  allProducts = allProducts + "</table>";
					  allProducts = allProducts + "</td>";
					  allProducts = allProducts + "</tr>";
				  }
				 
				  allProducts = allProducts + "</table>";
				  
				  $("#authorisedContact").slideDown();
				  $("#allAuthorisedContacts").html(allProducts);
				  for(var i=0;i<products.length;i++){
					  var product = products[i];
					  $('#auth_user_'+product.id+' :nth-child(1)').prop('selected', true);
					  /*$("#auth_user_"+product.id).multiselect({
						  includeSelectAllOption: true
					  });*/
				  }
			  }
		  },
		  error: function( xhr,status,error ){
			  console.log(xhr);
		  }
	});
}

/*function showAuthorisedContact(){
	$.ajax({
		  type: "GET",
		  async: false,
		  url:"getAuthorisedContacts",
		  dataType: "json",
		  success: function( data ) {
			  if(data.length>0){
				  var allContacts = "";
				 
				  $.each(data, function(i,member) {
					  //alert(member.isChild);
					  allContacts = allContacts + "<div style='margin: 10px;'>";
					  allContacts = allContacts + "<div style='float:left; width:200px;'>";
					  if(member.isAdult)
						  allContacts = allContacts + "<span><input type='radio' name='authorised_user_' id='user_"+member.contactId+"' value='"+member.contactId+"' class='usercheck' checked='checked'></span>";
					  else
						  allContacts = allContacts + "<span><input type='radio' name='authorised_user_' id='user_"+member.contactId+"' value='"+member.contactId+"' class='usercheck'></span>";
					  allContacts = allContacts + "&nbsp;&nbsp;<span class='name'>"+member.fname + " " +member.lname+"</span>";
					  allContacts = allContacts + "</div>";
					  
					  allContacts = allContacts + "<div style='float:left;'>";
					  allContacts = allContacts + "&nbsp;&nbsp;<span>["+member.age+"]</span>";
					  allContacts = allContacts + "</div>";
					  
					  
					  allContacts = allContacts + "</div>";
					  allContacts = allContacts + "<div style='clear: both;'></div> ";
				  });
				  
				  //alert(allContacts);
				  $("#authorisedContact").slideDown();
				  $("#allAuthorisedContacts").html(allContacts);
				  
				  $("input[type='checkbox']").uniform();
				  $("#input[name='membertype_']").kendoDropDownList();
			  }
		  },
		  error: function( xhr,status,error ){
			  //alert("1" +status);
			  console.log(xhr);
			 
		  }
	});
}*/

function replaceAll(string, find, replace) {
	  return string.replace(new RegExp(escapeRegExp(find), 'g'), replace);
}

function getActivitiesyByCIdAndItemId(cId,itemId){
	var actParam = "";
	if (typeof(activityDataSource) != "undefined") {
		var data = activityDataSource.data();
		for (var i = 0; i < data.length; i++) {
			var actData = data[i] ;	
			if (cId == actData.contactId && itemId == actData.parentItemId && (actData.priority != null || actData.priority != 'null')) {
				actParam = actParam  + actData.cId + "$" + actData.parentItemId +  "$" + actData.associatedItemId + "$"  + actData.priority + "$"  + actData.name + "#";
			}
		}
	}
	//console.log(" actParam "+actParam);
	if (actParam == "") actParam = " ";
	return actParam;
}

function showInserviceIndividualDays(itemDetailId){
	$.ajax({
		  type: "GET",
		  async: false,
		  url:"showInserviceIndividualDays?itemDetailId="+itemDetailId,
		  dataType: "json",
		  success: function( data ) {
			  if(data.length>0){
				  var allContacts = "";
				  
				  var hasPastDateInDays = false;
				  $.each(data, function(i,itemDetailDay) {
					  //alert(member.isChild);
					  var showdisplay = "block";
					  if(itemDetailDay.hasPastDates){
						  showdisplay = "none";
						  hasPastDateInDays = true;
					  }
					  //alert(showdisplay);
					  allContacts = allContacts + "<div style='margin: 10px; display:"+showdisplay+"'>";
					  allContacts = allContacts + "<div style='float:left; width:200px;'>";
						  allContacts = allContacts + "<span><input type='checkbox' name='inServiceSelectedDays' id='inServiceSelectedDays_"+itemDetailDay.dayId+"' value='"+itemDetailDay.remainingcapacity+"' class='usercheck'></span>";
						  allContacts = allContacts + "&nbsp;&nbsp;<span class='name' style='margin-left: 50px;'>"+itemDetailDay.day + " [" +itemDetailDay.remainingcapacity+"]</span>";
					  allContacts = allContacts + "</div>";
					  allContacts = allContacts + "<input type='hidden' name='memberprice' id='memberprice_"+itemDetailDay.dayId+"' value='"+itemDetailDay.memberprice+"'>";
					  allContacts = allContacts + "<input type='hidden' name='nonmemberprice' id='nonmemberprice_"+itemDetailDay.dayId+"' value='"+itemDetailDay.nonmemberprice+"'>";
					  allContacts = allContacts + "<input type='hidden' name='hasPastDates' id='hasPastDates_"+itemDetailDay.dayId+"' value='"+itemDetailDay.hasPastDates+"'>";
					  allContacts = allContacts + "<input type='hidden' name='signupDate' id='signupDate_"+itemDetailDay.dayId+"' value='"+convertJsonDate(itemDetailDay.date_c)+"'>";
					  allContacts = allContacts + "<input type='hidden' name='isCapacity' id='isCapacity_"+itemDetailId+"_"+itemDetailDay.dayId+"' value='"+itemDetailDay.remainingcapacity+"'>";
					  
					  allContacts = allContacts + "<div style='float:left; width:200px;'>";
					  allContacts = allContacts + "&nbsp;&nbsp;<span>Date:</span>&nbsp;<span>["+convertJsonDate(itemDetailDay.date_c)+"]</span>";
					  allContacts = allContacts + "</div>";
					 
					  allContacts = allContacts + "<div style='float:left;'>";
					  allContacts = allContacts + "&nbsp;&nbsp;<span>Member Price:</span>&nbsp;<span>["+currencyFormat(itemDetailDay.memberprice)+"]</span>";
					  allContacts = allContacts + "&nbsp;/&nbsp;<span>Nonmember Price:</span>&nbsp;<span>["+currencyFormat(itemDetailDay.nonmemberprice)+"]</span>";
					  allContacts = allContacts + "</div>";
					  
					  allContacts = allContacts + "</div>";
					  allContacts = allContacts + "<div style='clear: both;'></div> ";
					  
				  });
				  allContacts = allContacts + "<input type='hidden' name='hasPastDateInDays' id='hasPastDateInDays' value='"+hasPastDateInDays+"'>";
				  
				  $("#selectInServiceDays").slideDown();
				  $("#allinservicedays").html(allContacts);
				  if(hasPastDateInDays){
					  $("#pkgPriceInfo").html("");
					  $("#pkgPriceInfo").hide();
				  }
				  $("input[type='checkbox']").uniform();
				  //$("#input[name='membertype_']").kendoDropDownList();
			  }
		  },
		  error: function( xhr,status,error ){
			  //alert("1" +status);
			  console.log(xhr);
			 
		  }
	});
}

function populateItemDetailIds(item_Detail_Ids){
	var savedItemDetailId = $.sessionStorage.getItem('itemDetailsId');
	//var itemDetailIdLst = item_Detail_Ids.join(',');
	//alert(savedItemDetailId);
	if(savedItemDetailId!=null && typeof savedItemDetailId !='undefined'){
		var array = savedItemDetailId.split(",");
		$.each(array,function(i){
			if($.inArray(array[i], item_Detail_Ids) == -1){
			   //alert(array[i]);
			   item_Detail_Ids.push(array[i]);
			}
		});
	}
	
	$.sessionStorage.setItem('itemDetailsId', unique(item_Detail_Ids).join(","));
	return item_Detail_Ids;
}

function getUniqueItemDetailIdsFromCart(cartObj){
	var other = [], id;
	
	$.each(cartObj.contents, function(i,itemsData) {
	   id = itemsData.item.itemDetailsId; 
	   // create it and assign it to a new empty array
	   if($.inArray(id, other) == -1)
	      other.push(id);
	
	});
	
	//console.log(other);
	return other;
}

function initProducts(){
	var itemDetailsId = $.sessionStorage.getItem('itemDetailsId');
	//console.log("  itemDetailsId   "+itemDetailsId);
	$.ajax({
		  type: "GET",
		  async: false,
		  url:"getallProducts?ids="+itemDetailsId,
		  dataType: "json",
		  success: function( products ) {
			  items.read({ data: products });
		  },
		  error: function( xhr,status,error ){
			  console.log(xhr);
		  }
	 });
}

function getItemFromDataSource(id){
	var object = null ;
	if (typeof(items) != "undefined") {
		var data = items.data();
		for (var i = 0; i < data.length; i++) {
			var d = data[i] ;	
			if (id == d.id) {
				object = d;
			}
		}
	}
	return object ;
}

function getContactDataSource(cId){
	var object = null ;
	if (typeof(contacts) != "undefined") {
		var data = contacts.data();
		for (var i = 0; i < data.length; i++) {
			var d = data[i] ;	
			if (cId == d.id) {
				object = d;
			}
		}
	}
	return object ;
}

function isValidPricingRule(contact,pricingRule,item){
	var isValid = false ;
	if (contact.age >= pricingRule.fromAge && contact.age <= pricingRule.toAge) {
		isValid = true;
	}
	/*if(item.programType != 'EVENT'){
		var pricing = getPricingRule(contact,item);
		if (pricingRule.id != pricing.id) {
			isValid = false;
		}
	}*/
	return isValid;
}

function getPricingRule(contact,item, priceOption){
	var pricingRule = null ;
	for(var j=0; j<item.signuppriceArr.length; j++){
		var pricing  = item.signuppriceArr[j];
		if (contact.age >= pricing.fromAge && contact.age <= pricing.toAge && (priceOption == undefined || priceOption == pricing.priceOption )) {
			pricingRule = pricing;
			break ;
		}
	}
	if (pricingRule == null) {
		pricingRule = item.signuppriceArr[0];
	}
	return pricingRule;
}

function getPriceByAge(contact,item,memberField,nonNemberField, priceOption) {
	var pricingRule = getPricingRule(contact,item, priceOption);
	return getPriceByMemberNonMember(contact,pricingRule,memberField,nonNemberField);
}

function getPriceByMemberNonMember(contact,pricingRule,memberField,nonNemberField){
	if(contact.isMember) return pricingRule[memberField];
	else 	return pricingRule[nonNemberField];
}

function monthDiff(d1, d2) {
    var months;
    months = (d2.getFullYear() - d1.getFullYear()) * 12;
    months -= d1.getMonth() + 1;
    months += d2.getMonth() + 1;
    return months <= 0 ? 0 : months;
}

function getSelectedContacts(){
	var selectedContacts = {};
	$('#allmembers').find('input[class="usercheck"]').each(function(){
		if($("#user_"+this.value).is(':checked')){
			var cId = this.value.split("_")[1];
			var contact = getContactDataSource(cId);
			selectedContacts[cId] = contact ;
		}
	});
	return selectedContacts ;
}

function getPrimaryOrFirstAdultContact(){
	var selectedContacts = getSelectedContacts() ;
	var c = null ;
	for (var cid in selectedContacts) {
		var sc = selectedContacts[cid] ;
		if (sc.isPrimary) {
			c = sc;
			break;
		}
	}
	if (c == null) {
		for (var cid in selectedContacts) {
			var sc = selectedContacts[cid] ;
			if (sc.age >= 18) {
				c = sc;
				break;
			}
		}		
	}
	return c ;

}

function addfamilyMemberCommon(){
	$.ajax({
		  type: "GET",
		  url:"addMember",
		  async:false,
		  success: function( data ) {
			  $("#popup_add_common").html(data);
			 // $("#popup_add").show();
		  },
		  error: function( xhr,status,error ){
			  console.log(xhr);
		  }
	});
}

$(document).on('click', '#addmemberBtn', function(){							
	var url = "isEmailExists";
	var validator1 = $(document).find("#addMembershipForm").validate({
		debug: true,
		errorElement: "em",			
		errorPlacement: function(error, element) {
			$element = $(element);			
			//console.log($element.hasClass("dateOfBirth"));
			if($element.attr("id") == "dateOfBirthAdd"){
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
				date: true
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
		    	  date: "Please enter valid date"
					
		    },
	        "Email" : {				
				email: "Please enter correct email address"				
			}
		}				
	});	
	
	/*$(document).on('change', '#dobMonth, #dobDate, #dobYear', function(){
	//$(document).find("#dobMonth, #dobDate, #dobYear").on('change',function(e){
		updateDOB();
	});*/
	
	if(!$("#addMembershipForm").valid()) {
		 //console.log("")
		 return false;
	} else {
		//console.log("Validation Success submittting form");
		
		var dobMonth = $("#dobMonth").val();
		var dobDate = $("#dobDate").val();
		var dobYear = $("#dobYear").val();
		
		var dob = new Date(dobYear,dobMonth,dobDate);
		var today = new Date();
		
		if(dob >= today){
			$("#dop-er").html("Please enter valid date of birth");
			$("#dop-er").show();
			return;
		}else{
			$("#dop-er").hide();
		}
		
		$("#dateOfBirthAdd").val(dobMonth+"/"+dobDate+"/"+dobYear);
		
		if(document.getElementById("genderM").checked || document.getElementById("genderF").checked) {
			// do nothing
		}else{
			$("#gender-er").html("Please select gender");
			$("#gender-er").show();
			return;
		}
		
		addcurrentfamilymember();
		
		//if(ValidateDate($("#dateOfBirthAdd").val())){
			//addcurrentfamilymember();
		/*} else {
			$("#dop-er").html("Please enter valid date");
			$("#dop-er").show();
		}*/
	}
	
	
});

function ValidateDate(dtValue){
	var dtRegex = new RegExp(/\b\d{1,2}[\/-]\d{1,2}[\/-]\d{4}\b/);
	//var dtRegex = /^(0?[1-9]|1[012])[\/\-](0?[1-9]|[12][0-9]|3[01])[\/\-]\d{4}$/;
	return dtRegex.test(dtValue);
}


function addcurrentfamilymember(){
		$(".k-loading-mask").show();
		$(".k-loading-text").html("Please wait while the member is added");
	
	 	$("#tcSuccessSpan").css("display", "none");		
	 	$("#tcSuccessSpan" ).html("");	
	 	$("#tcErrorSpan").css("display", "none");		
	 	$( "#tcErrorSpan" ).html("");
  
		//alert($('#addMembershipForm').serialize());
		$.ajax({
			  type: "POST",
			  url: $('#addMembershipForm').attr( "action"),
			  data: $('#addMembershipForm').serialize(),
			  success: function( data ) {
				  var jsonDataArr = jQuery.parseJSON(data);
				  if(jsonDataArr != null && jsonDataArr.length > 0){			  
				  	  if(jsonDataArr[0] != null && jsonDataArr[0].Success != null && jsonDataArr[0].Success =='Success'){
					  	  $("#tcErrorSpanAddMember").css("display", "none");		
						  $("#tcErrorSpanAddMember" ).html("");	
						  //$("#tcSuccessSpan").css("display", "block");		
						  //$("#tcSuccessSpan" ).html("Profile Information Updated successfully.");
						  setTimeout(function(){$(".k-loading-text").fadeOut("slow");}, 100);
						  setTimeout(function(){$(".k-loading-text").html("Member was added successfully");$(".k-loading-text").fadeIn("slow");}, 300);
					  	  setTimeout(function(){$(".k-loading-text").fadeOut("slow");}, 500);
					  	  //setTimeout(function(){location.href='view_membership';}, 7000);
					  	  //setTimeout(function(){location.reload();}, 7000);
					  	  var familyContactPageType = $("#familyContactPageType").val();
					  	  //console.log("  familyContactPageType  "+familyContactPageType);
					  	  if(familyContactPageType && familyContactPageType != ''){
					  		  if(familyContactPageType == 'FAMILY'){
					  			getFamilymembers();
					  		  }else if(familyContactPageType == 'EMG'){
					  			showEmergencyContact();
					  		  }else if(familyContactPageType == 'AUTH'){
					  			showAuthorisedContact();
					  		  }else if(familyContactPageType == 'MYPROFILE'){
						  			$(".k-loading-mask").show();						  			
						  			setTimeout(function(){$(".k-loading-mask").show();$(".k-loading-text").html("Member was added successfully");$(".k-loading-text").fadeIn("slow");}, 300);
						  			setTimeout(function(){$(".k-loading-text").fadeOut("slow");}, 3000);
						  			setTimeout(function(){location.href='view_membership';}, 3000);					  			  
					  		  }else{
					  			getFamilymembers();
					  		  }
					  	  }else{
					  		 getFamilymembers();
					  	  }
					  	  closeAllwindow();
					  	  $(".k-loading-mask").hide();
					  	//location.href='view_membership';
					  	  
				  	  }
				  	 else if(jsonDataArr[0] != null && jsonDataArr[0].Duplicate != null && jsonDataArr[0].Duplicate=='Duplicate'){
				  		  console.log(" User Information already exists!  ");
				  		  $("#tcSuccessSpanAddMember").css("display", "none");		
						  $("#tcSuccessSpanAddMember" ).html("");	
						  $("#tcErrorSpanAddMember").css("display", "block");		
						  $( "#tcErrorSpanAddMember" ).html("User Information already exists!");
						  $(".k-loading-mask").hide();
				  	  }
				  	  else{
				  		  $("#tcSuccessSpanAddMember").css("display", "none");		
						  $("#tcSuccessSpanAddMember" ).html("");	
						  $("#tcErrorSpanAddMember").css("display", "block");		
						  $( "#tcErrorSpanAddMember" ).html("There was some error. Please try again later");
						  $(".k-loading-mask").hide();
				  	  }
				  }else{					  
					  $("#tcSuccessSpanAddMember").css("display", "none");		
					  $("#tcSuccessSpanAddMember" ).html("");	
					  $("#tcErrorSpanAddMember").css("display", "block");		
					  $( "#tcErrorSpanAddMember" ).html("There was some error. Please try again later");
					  $(".k-loading-mask").hide();
				  }
				  //$(".k-loading-mask").hide();
			  },
			  error: function( xhr,status,error ){
				  //alert("1" +status);
				  console.log(xhr);
				  $("#tcSuccessSpanAddMember").css("display", "none");		
				  $("#tcSuccessSpanAddMember" ).html("");	
				  $("#tcErrorSpanAddMember").css("display", "block");		
				  $( "#tcErrorSpanAddMember" ).html("There was some error. Please try again later");
				  console.log(" priceForDiscount  ::  "+priceForDiscount);			  $(".k-loading-mask").hide();
			  }
		});
}

function getPromo(promos, autoPromo, promoRuleType){
	var result = null;
	for (var key in promos) {
		if (promos.hasOwnProperty(key)) {
			//console.log(key + " -> " + promos[key]);
			var promo = promos[key];
			if((!autoPromo || (autoPromo && promo.AutoPromo == 'Yes')) && promo.PromoRuleType == promoRuleType){
				result = promo;
				break;
			}
		}
	}
	return result;
}

function getPromoDiscount(promo, amount, isRecurring){
	var result;
	
	//console.log(" amount  ::  "+amount);
	
	if(promo.RecurringPeriod == 1){
		// One time promo
		var amt = computePromoDiscount(promo, amount);
		result = '{ "Status":"Success", "amt":"'+amt+'", "month": "0" }';
	}else if(promo.RecurringPeriod > 1 && isRecurring){
		// Recurring promo and recurring sign up
		var amt = computePromoDiscount(promo, amount);
		result = '{ "Status":"Success", "amt":"'+amt+'", "month": "'+promo.RecurringPeriod+'" }';
	}else if(!isRecurring){
		// Auto Promotion is not applied
		var result = '{ "Status":"Denied", "amt":"'+amt+'", "month": "0" }';
	}
	
	return result;
}

function computePromoDiscount(promo, amount, noOfTicketsOrPackages){
	
	if(noOfTicketsOrPackages == undefined){
		noOfTicketsOrPackages = 1;
	}
	
	if(noOfTicketsOrPackages > 0){
		var discountAmt = 0;
		if(promo.PromoType == '$'){
			discountAmt = promo.PromoDiscountValue;
		}else if(promo.PromoType == '%'){
			discountAmt = (promo.PromoDiscountValue * (amount * noOfTicketsOrPackages))/100;
		}
		
		/*if(discountAmt > amount){
			// Promo code is denied
			discountAmt = 'NA';
		}*/
	}
	
	/*console.log(" PromoType   :: "+promo.PromoType);
	console.log(" PromoDiscountValue   :: "+promo.PromoDiscountValue);
	console.log(" amount   :: "+amount);
	console.log(" Discount value   :: "+discountAmt);*/
	return discountAmt;
}

function getDiscountFromMap(current, type){
	var discount = 0;
	if(type == 'Sign Up'){
		if(current.promoMap.signUpAutoPromo != null && current.promoMap.signUpAutoPromo != undefined && current.promoMap.signUpAutoPromo.actualDiscountValue > 0){
			discount = parseFloat(current.promoMap.signUpAutoPromo.actualDiscountValue);
		}
	}else if(type == 'Deposit'){
		if(current.promoMap.depositAutoPromo != null && current.promoMap.depositAutoPromo != undefined && current.promoMap.depositAutoPromo.actualDiscountValue > 0){
			discount = parseFloat(current.promoMap.depositAutoPromo.actualDiscountValue);
		}
	}else if(type == 'Registration'){
		if(current.promoMap.regAutoPromo != null && current.promoMap.regAutoPromo != undefined && current.promoMap.regAutoPromo.actualDiscountValue > 0){
			discount = parseFloat(current.promoMap.regAutoPromo.actualDiscountValue);
		}
	}else if(type == 'SetUpFee'){
		if(current.promoMap.setupFeeAutoPromo != null && current.promoMap.setupFeeAutoPromo != undefined && current.promoMap.setupFeeAutoPromo.actualDiscountValue > 0){
			discount = parseFloat(current.promoMap.setupFeeAutoPromo.actualDiscountValue);
		}
	}else if(type == 'JoinFee'){
		if(current.promoMap.joinFeeAutoPromo != null && current.promoMap.joinFeeAutoPromo != undefined && current.promoMap.joinFeeAutoPromo.actualDiscountValue > 0){
			discount = parseFloat(current.promoMap.joinFeeAutoPromo.actualDiscountValue);
		}
	}
	return discount;
}

function getPromoFromMap(current, type){
	if(type == 'Sign Up'){
		if(current.promoMap.signUpAutoPromo != null && current.promoMap.signUpAutoPromo != undefined){
			return current.promoMap.signUpAutoPromo;
		}
	}else if(type == 'Deposit'){
		if(current.promoMap.depositAutoPromo != null && current.promoMap.depositAutoPromo != undefined){
			return current.promoMap.depositAutoPromo;
		}
	}else if(type == 'Registration'){
		if(current.promoMap.regAutoPromo != null && current.promoMap.regAutoPromo != undefined){
			return current.promoMap.regAutoPromo;
		}
	}else if(type == 'SetUpFee'){
		if(current.promoMap.setupFeeAutoPromo != null && current.promoMap.setupFeeAutoPromo != undefined){
			return current.promoMap.setupFeeAutoPromo;
		}
	}else if(type == 'JoinFee'){
		if(current.promoMap.joinFeeAutoPromo != null && current.promoMap.joinFeeAutoPromo != undefined){
			return current.promoMap.joinFeeAutoPromo;
		}
	}
}

function convertToPromoString(promotionArray){
	
	var str = "";
	if(promotionArray != undefined && promotionArray.length > 0){
		
		str += "[";
		
		for(var i=0; i<promotionArray.length; i++){
			
			var promo = promotionArray[i];
			
			//console.log(" PROMO  (PromoId:"+promo.PromoId+", PromoCode:"+promo.PromoCode+", discountValue:"+promo.actualDiscountValue+", RecurringPeriod:"+promo.RecurringPeriod);
			
			str += "{PromoId:\""+promo.PromoId+"\",";
			
			str += "PromoCode:\""+promo.PromoCode+"\",";
			
			str += "discountValue:\""+promo.discountValue+"\",";
			
			str += "actualDiscountValue:\""+promo.actualDiscountValue+"\",";
			
			str += "remDiscount:\""+promo.remDiscount+"\",";
			
			str += "monthlyDiscountAmount:\""+promo.monthlyDiscountAmount+"\"";
					
			if(i != promotionArray.length - 1){
				str += "},";
			}else{
				str += "}]";
			}
		}
		
		//console.log("  str  "+str);
		
		/*str = str.replace(/"/g, "");
		
		str = str.replace(/{/g, "");
		
		str = str.replace(/}/g, "");
		
		str = str.replace(/:/g, "_OR_");
		
		str = str.replace(/,/g, " ");
		*/
		//console.log("  str  "+str);
		
		//str = "'"+str+"'";
		
		//console.log("  str  "+str);
		
		return str;
	}
}

function convertToInvoiceString(invoiceArray){
	
	var str = "";
	if(invoiceArray != undefined && invoiceArray.length > 0){
		
		str += "[";
		
		for(var i=0; i<invoiceArray.length; i++){
			
			var invoice = invoiceArray[i];
			
			//console.log(" INVOICE  (invoiceAmt:"+invoice.invoiceAmt+", billDate:"+convertDateToMMDDYYYY(invoice.billDate)+", dueDate:"+convertDateToMMDDYYYY(invoice.dueDate)+", invoiceDiscountAmt:"+invoice.invoiceDiscountAmt+", remDiscountAmt:"+invoice.remDiscountAmt+")");
			
			str += "{invoiceAmt:\""+invoice.invoiceAmt+"\",";
			
			str += "invoiceDiscountAmt:\""+invoice.invoiceDiscountAmt+"\",";
			
			str += "remDiscountAmt:\""+invoice.remDiscountAmt+"\",";
			
			if(invoice.billDate != null){
				str += "billDate:\""+convertDateToMMDDYYYY(invoice.billDate)+"\",";
			}else{
				str += "billDate:\"null\"";
			}
			
			if(invoice.dueDate != null){
				str += "dueDate:\""+convertDateToMMDDYYYY(invoice.dueDate)+"\"";
			}else{
				str += "dueDate:\"null\"";
			}
			
			if(i != invoiceArray.length - 1){
				str += "},";
			}else{
				str += "}]";
			}
		}
		
		return str;
	}
}

function gotocartpage(){
	$("#program_details").css("border-width", "1px");	
	 $("#program_details").css("margin-top", "20px");	
	 
	 $("#tcSuccessSpan").css("display", "none");		
	 $("#tcSuccessSpan" ).html("");	
	 $("#tcErrorSpan").css("display", "none");		
	 $( "#tcErrorSpan" ).html("");
	 
	$("#program").hide();
	$("#purchase").fadeOut();
	$("#payment_cart").hide();
	$("#familymembers").hide();
	$("#checkout_content").show();
	$("#details-checkout").show();
	$("#add-to-cart").hide();
	$("#contactHealthHistoryDiv").hide();
	$("#dspErr").hide();
	
	location.href = '#/checkout';
	$("#backtofamily").hide();
	$("select#signuppricingDD.kendodrop").kendoDropDownList();
	setTimeout(function(){
		if(cartPreviewModel.totalPrice()=='$0.00'){
			$("#promo").hide();
		}else{
			$("#promo").show();
		}
	}, 500);
}

function fnApplyPromo(isFromUpdateCart){
	 $("#tcSuccessSpan").css("display", "none");		
	 $("#tcSuccessSpan" ).html("");	
	 $("#tcErrorSpan").css("display", "none");		
	 $( "#tcErrorSpan" ).html("");
	 
	 var promocode = $( "#c_promocode" ).val();
	 
	 if((promocode=='' || typeof promocode=='undefined') && isFromUpdateCart){
		 return;
	 }
	 
	 if(promocode=='' || typeof promocode=='undefined'){
		  $("#tcSuccessSpan").css("display", "none");		
		  $("#tcSuccessSpan" ).html("");	
		  $("#tcErrorSpan").css("display", "block");		
		  $( "#tcErrorSpan" ).html("Please enter Valid Promco code");
		  return;
	 }
	 var cartObj = this.cart;
	 var found = false;
	 
	 for (var c = 0; c < cartObj.contents.length; c ++) {
		 
		 var current = cartObj.contents[c];
		 var discountJSON;
		// var promos = current.promos;
		 
		 var promotionMap = new Array();
			
		var signupPriceForPromo = 0;
		if(current.proRatedSignupPrice > 0){
			signupPriceForPromo = current.proRatedSignupPrice; 
		} else if(current.signupPrice > 0){
			signupPriceForPromo = current.signupPrice;
		}
		
		var cartItems = getCartItemsInMap(this.cart);
		
		//console.log("  cartObj.contents   ::   "+cartObj.contents.join('_AND_'));
		
		var amountMap = '{ "signupPrice":"'+signupPriceForPromo+'", "setupFee":"'+current.setupfee+'", "joinFee":"'+current.joinFee+'", "registrationFee":"'+current.registrationPrice+'", "depositAmount":"'+current.depositAmount+'"}';
		
		$.ajax({
			  type: "GET",
			  async: false,
			  url:"getPromoMap?itemId="+current.item.itemDetailsId+"&contactId="+current.contact.contactId+"&isAuto=false&isRecurring="+current.isRecurring+"&amountJSON="+amountMap+"&selectedStartDate="+current.selectedStartDate+"&lstCartItem="+cartItems.join('_AND_')+"&urlItemContactPromo=",
			  //url:"getPromoMap",
			  //data: { 'itemId':item, 'contactId': contact, 'isAuto': 'true' },
			  dataType: "json",
			  success: function( data ) {
				  
				  //console.log("  data  "+data);
				//  console.log("  JSON.stringify(data)   ::   "+JSON.stringify(data));
				  
				  //var json = JSON.parse(data);
				  promotionMap = data.promos;
				  
				//  console.log("  promotionMap   ::::   "+promotionMap);
				  
				  /*if(data.length>0){
					  inServiceWithChildCareProperty = data;
				  }*/
			  },
			  error: function( xhr,status,error ){
				  console.log(xhr);
			  }
		});
		
		//console.log("  promotionMap   -->   "+promotionMap.length);
	 
		// console.log("  promos length   "+promos.length);
		if(promotionMap != null && promotionMap != undefined){
		 for(var i = 0; i<promotionMap.length; i++){
			//console.log("  PromoCode >>>  "+promos[i].PromoCode);
			 
			 
			var promo = promotionMap[i];
			 
			//var isSignupDateIn = isSignupDateInRange(current, promo);
			//console.log("  isSignupDateIn >> "+isSignupDateIn);
			
			if(promo.AutoPromo == 'No' && promo.PromoCode == promocode){	// && isSignupDateIn
				 
				//console.log("  AutoPromo >>>  "+promo.AutoPromo+",  PromoCode ==  PromoCode "+promo.PromoCode);
				 
				/*var priceForDiscount = 0; 
				if(promo.PromoRuleType == 'Sign Up'){
					priceForDiscount = current.signupPrice;
				}else if(promo.PromoRuleType == 'Deposit'){
					priceForDiscount = current.depositAmount;
				}else if(promo.PromoRuleType == 'Registration'){
					priceForDiscount = current.registrationPrice;
				}else if(promo.PromoRuleType == 'JoinFee'){
					priceForDiscount = current.joinFee;
				}else if(promo.PromoRuleType == 'SetUpFee'){
					priceForDiscount = current.setupfee;
				}
				
				var discount = getPromoDiscount(promo, priceForDiscount, current.isRecurring);
				discountJSON = JSON.parse(discount);*/
				
				//console.log(" Promo Code denied. "+discountJSON.Status);
			 	
			 	/*if(discountJSON.Status == 'Denied'){
			 		console.log(" Promo Code denied. ");
			 	}else{*/
			 		
					/*console.log(" discount  ::  "+discount);
					console.log(" discountJSON.amt  ::  "+discountJSON.amt);
					console.log(" discountJSON.month  ::  "+discountJSON.month);*/
			 	
					//current.set("ManualPromoEntered", promocode);
					
					/*promo.discountValue = promo.discountValue;
					promo.noOfDiscountMonths = promo.noOfDiscountMonths;*/
					
					var promotionMap = current.promotionMap;
					
					//console.log(" promotionMap.length :: "+promotionMap.length);
					//console.log(" promotionMap :: "+JSON.stringify(current.promotionMap));
					
					if(promotionMap != undefined){
						var isAlreadyExist = false;
						for(var a=0; a<promotionMap.length; a++){
							if(promotionMap[a].PromoCode == promo.PromoCode){
								isAlreadyExist = true;
							}
						}
						
						if(!isAlreadyExist){
							promotionMap.push(promo);
							current.set("promotionMap", promotionMap);
							
							//console.log("   current.promotionMap.length   ::   " + current.promotionMap.length);
							//validatePromotions(this.cart.contents);
							cartPreviewModel.updateCart(current,promo);
							//cartPreviewModel.finalPrice(current);
						}
					}else{
						var promotionMap = new Array();
						promotionMap.push(promo);
						current.set("promotionMap", promotionMap);
						//validatePromotions(this.cart.contents);
						cartPreviewModel.updateCart(current,promo);
						//cartPreviewModel.finalPrice(current);
					}
					found = true;
			 	//}
			 }
		 }
		}
	 }
	 
	 $.sessionStorage.setItem('manualPromoEntered', promocode);
	 
	 if(found){
		 $.sessionStorage.setItem('cart', JSON.stringify(this.cart.contents));
		 //$( ".custom_promo" ).show();
		 //cartPreviewModel.updateCart(cartObj.contents[c],parseFloat(discountJSON.amt),promocode);
	 }
	 
	 if (!found) {
		 $("#tcSuccessSpan").css("display", "none");		
		  $("#tcSuccessSpan" ).html("");	
		  $("#tcErrorSpan").css("display", "block");		
		  $( "#tcErrorSpan" ).html("The Promo code you enetered is invalid");
	  }else{
		  
		 //window.location.reload();
		 // window.location.href = '#/checkout';
		  //showCartInfo();
		  //$.sessionStorage.setItem('cart', JSON.stringify(this.cart.contents));
		  //kendo.render($("#checkout-template"));
		  //kendo.render($("#checkout-template"), JSON.stringify(this.cart.contents));
		  //cartPreviewModel.updateCart(cartObj.contents[c],0,'Man');
	  }
}

function fnUpdatePromo(){
	 $("#tcSuccessSpan").css("display", "none");		
	 $("#tcSuccessSpan" ).html("");	
	 $("#tcErrorSpan").css("display", "none");		
	 $( "#tcErrorSpan" ).html("");
	 //alert();
	console.log(this);
}

function applyPromotionFor(uniqueId, promoCode, promoRuleType){
	//console.log("  uniqueId  >> "+uniqueId+",  promoCode >> "+promoCode+",  promoRuleType  >> "+ promoRuleType);
	
	promoCode = decodeURIComponent(promoCode);
	promoRuleType = decodeURIComponent(promoRuleType);
	
	var promoUniqueId = uniqueId+"_"+promoCode+"_"+promoRuleType.replace(/ /g, '');
	
	var promoCode = $("#manual_promo_"+promoUniqueId).val();
	var oldPromoCode = $("#old_manual_promo_"+promoUniqueId).val();
	
	/*console.log("  promoUniqueId   ::  "+promoUniqueId);
	console.log("  promoCode ::  "+promoCode);
	console.log("  oldPromoCode ::  "+oldPromoCode);*/
	
	var cartObj = this.cart;
	
	for (var c = 0; c < cartObj.contents.length; c ++) {
		
		var current = cartObj.contents[c];
		if(current.uniqueId == uniqueId){
			
			var discountJSON;
			//var promos = current.promos;
			//console.log("  Promos   >>>   "+JSON.stringify(promos));
			
			
			
			var promotionMap = new Array();
				
			var signupPriceForPromo = 0;
			if(current.proRatedSignupPrice > 0){
				signupPriceForPromo = current.proRatedSignupPrice; 
			} else if(current.signupPrice > 0){
				signupPriceForPromo = current.signupPrice;
			}
			
			var amountMap = '{ "signupPrice":"'+signupPriceForPromo+'", "setupFee":"'+current.setupfee+'", "joinFee":"'+current.joinFee+'", "registrationFee":"'+current.registrationPrice+'", "depositAmount":"'+current.depositAmount+'"}';
			
			var cartItems = getCartItemsInMap(this.cart);
			
			$.ajax({
				  type: "GET",
				  async: false,
				  url:"getPromoMap?itemId="+current.item.itemDetailsId+"&contactId="+current.contact.contactId+"&isAuto=false&isRecurring="+current.isRecurring+"&amountJSON="+amountMap+"&selectedStartDate="+current.selectedStartDate+"&lstCartItem="+cartItems.join('_AND_')+"&urlItemContactPromo=",
				  //url:"getPromoMap",
				  //data: { 'itemId':item, 'contactId': contact, 'isAuto': 'true' },
				  dataType: "json",
				  success: function( data ) {
					  
					console.log("  data  "+data);
					//  console.log("  JSON.stringify(data)   ::   "+JSON.stringify(data));
					  
					  //var json = JSON.parse(data);
					  promotionMap = data.promos;
					  
					//  console.log("  promotionMap   ::::   "+promotionMap);
					  
					  /*if(data.length>0){
						  inServiceWithChildCareProperty = data;
					  }*/
				  },
				  error: function( xhr,status,error ){
					  console.log(xhr);
				  }
			});
			
			//console.log("  promotionMap   -->   "+promotionMap.length);
			
			
			
			
			var found = false;
			if(promotionMap != null && promotionMap != undefined){
				for(var i = 0; i<promotionMap.length; i++){
	
					 var promo = promotionMap[i];
					 //console.log("  AutoPromo >>>  "+promos[i].AutoPromo+",  PromoCode >>> "+promos[i].PromoCode+",  promo.PromoRuleType  >>>  "+promo.PromoRuleType);
					 if(promo.AutoPromo == 'No' && promo.PromoCode == promoCode && promoRuleType == promo.PromoRuleType){
						 
					 	//console.log("  AutoPromo >>>  "+promos[i].AutoPromo+"  PromoCode ==  PromoCode "+promos[i].PromoCode);
						
						// signupPrice: signupPrice, setupFee: setupfee, registrationPrice: registrationPrice, depositAmount: depositAmount
						
					 	/*var priceForDiscount = 0; 
						if(promo.PromoRuleType == 'Sign Up'){
							priceForDiscount = current.signupPrice;
						}else if(promo.PromoRuleType == 'Deposit'){
							priceForDiscount = current.depositAmount;
						}else if(promo.PromoRuleType == 'Registration'){
							priceForDiscount = current.registrationPrice;
						}else if(promo.PromoRuleType == 'JoinFee'){
							priceForDiscount = current.joinFee;
						}else if(promo.PromoRuleType == 'SetUpFee'){
							priceForDiscount = current.setupfee;
						}
						
					 	var discount = getPromoDiscount(promo, priceForDiscount, current.isRecurring);
						
					 	discountJSON = JSON.parse(discount);
						
					 	console.log(" discount status >>>  "+discountJSON.Status);*/
					 	
					 	/*if(discountJSON.Status == 'Denied'){
					 		console.log(" Promo Code denied. ");
					 	}else{*/
					 		
							//console.log(" discount  ::  "+discount);
							//console.log(" discountJSON.amt  ::  "+discountJSON.amt);
							//console.log(" discountJSON.month  ::  "+discountJSON.month);
					 	
							/*promo.discountValue = discountJSON.amt;
							promo.noOfDiscountMonths = discountJSON.month;*/
							
						 
						 
							var promotionMap = current.promotionMap;
							
							//console.log(" promotionMap.length :: "+promotionMap.length);
							//console.log(" promotionMap :: "+JSON.stringify(current.promotionMap));
							
							if(promotionMap != undefined){
								var isAlreadyExist = false;
								for(var a=0; a<promotionMap.length; a++){
									if(promotionMap[a].PromoCode == promo.PromoCode){
										isAlreadyExist = true;
									}
								}
								
								if(!isAlreadyExist){
									current.promotionMap.push(promo);
									removePromoCodeFromMap(current, oldPromoCode);
									//$("#old_manual_promo_"+uniqueId).val(promo.PromoCode);
									cartPreviewModel.updateCart(current,promo);
								}
							}/*else{
								var promotionMap = new Array();
								promotionMap.push(promo);
								//removePromoCodeFromMap(current, oldPromoCode);
								current.set("promotionMap", promotionMap);
								cartPreviewModel.updateCart(current,promo);
							}*/
							found = true;
					 	//}
					}
				}
			}
			
			if (!found) {
				$("#tcSuccessSpan").css("display", "none");		
				$("#tcSuccessSpan" ).html("");	
				$("#tcErrorSpan").css("display", "block");		
				$( "#tcErrorSpan" ).html("The Promo code ("+promoCode+") you enetered is invalid");
			}else{
				validatePromotions(this.cart.contents);
			}
		}
	}
}

function hidePromotion(uniqueId, promoCode, promoRuleType){
	//console.log("  uniqueId  >> "+uniqueId);
	
	promoCode = decodeURIComponent(promoCode);
	promoRuleType = decodeURIComponent(promoRuleType);
	
	if(promoRuleType == 'Sign Up'){
		//console.log(" sign up ");
		$("#manual_promo_signup_"+uniqueId).hide('slow');
	}else if(promoRuleType == 'SetUpFee'){
		$("#manual_promo_setupfee_"+uniqueId).hide();
	}else if(promoRuleType == 'JoinFee'){
		$("#manual_promo_joinfee_"+uniqueId).hide();
	}else if(promoRuleType == 'Registration'){
		$("#manual_promo_registration_"+uniqueId).hide();
	}
	
	var cartObj = this.cart;
	 
	//console.log("  cartObj.contents.length ::  "+cartObj.contents.length);
	 
	for (var c = 0; c < cartObj.contents.length; c ++) {
		 
		var current = cartObj.contents[c];
		
		//console.log(" promotionMap.length :: "+current.promotionMap.length);
		//console.log(" promotionMap :: "+JSON.stringify(current.promotionMap));
		
		removePromoCodeFromMap(current, promoCode);
		
		/*if(promotionMap != undefined){
			for(var a=0; a<promotionMap.length; a++){
				if(promotionMap[a].PromoCode == promoCode){
					
					console.log(" splice  "+a);

					current.promotionMap.splice(a, 1);
					//cartPreviewModel.updateCart(current,promo);
				}
			}
		}*/
		
		//console.log(" promotionMap after :: "+JSON.stringify(current.promotionMap));
	 }
}

function removePromoCodeFromMap(current, promoCode){
	
	//console.log("  remove ::  "+promoCode);
	var promotionMap = current.promotionMap;
	if(promotionMap != undefined){
		for(var a=0; a<promotionMap.length; a++){
			if(promotionMap[a].PromoCode == promoCode){
				current.promotionMap.splice(a, 1);
			}
		}
	}
}

function removePromo(promotionMap, promoCode){
	
	//console.log("  remove ::  "+promoCode);
	if(promotionMap != undefined){
		for(var a=0; a<promotionMap.length; a++){
			if(promotionMap[a].PromoCode == promoCode){
				promotionMap.splice(a, 1);
			}
		}
	}
	return promotionMap;
}

function removeAllManualPromos(current){
	
	//console.log("  removeAllManualPromos  ::  ");
	var promotionMap = current.promotionMap;
	if(promotionMap != undefined){
		for(var a=0; a<promotionMap.length; a++){
			if(promotionMap[a].AutoPromo == 'No'){
				current.promotionMap.splice(a, 1);
			}
		}
	}
}

function updateDOB(){
	var dobMonth = $("#dobMonth").val();
	var dobDate = $("#dobDate").val();
	var dobYear = $("#dobYear").val();
	
	//var dob = new Date(dobYear,dobMonth - 1,dobDate);	
	//var age =  18;
	//var age =  $("#adultAgeValidationLowerLimit").html();
	//var kidAge =  23;
	var age =  $("#kidsAgeValidation").html();
	var kidAge =  $("#kidsAgeValidation").html();
	var mydate = new Date();
	mydate.setFullYear(dobYear, dobMonth-1, dobDate);
	/*var inpDate = $(document).find("#dateOfBirthAdd").val();
    var day;
	var month;
	var year;*/
	//var age =  $("#adultAgeValidation").html();
	
    /*if(inpDate != null){
    	var dateArr = inpDate.split("/");	    	
    	month = dateArr[0];
    	day = dateArr[1];
    	year = dateArr[2];
    }*/

    var currdate = new Date();
    currdate.setFullYear(currdate.getFullYear() - age);
    
    var currChilddate = new Date();
    currChilddate.setFullYear(currChilddate.getFullYear() - kidAge);

    if(currdate >= mydate){			    	
    	$(document).find("#contact-validation-txt").css("display", "inline");
    }else{
    	$(document).find("#contact-validation-txt").css("display", "none");
    }
    if(currChilddate <= mydate){			    	
    	$(document).find("#addToMemTr").css("display", "");
    	$(document).find("#isAdultInputId").attr("value","false");
    }else{
    	$(document).find("#addToMemTr").css("display", "none");
    	$(document).find("#isAdultInputId").attr("value","true");
    }
}

function isSignupDateInRange(current, promo){
	//console.log("  current.selectedStartDate  >>   "+current.selectedStartDate);
	var signUpDate = new Date();
	if(current.selectedStartDate != ''){
		signUpDate = new Date(current.selectedStartDate);
	}
	
	var promoStartDate = new Date(promo.StartDate);
	var promoEndDate = new Date(promo.EndDate);
	
	return (signUpDate >= promoStartDate && signUpDate <= promoEndDate) ? true : false;
	
}

function validatePromotions(contents){
	
	//console.log("  validatePromotions  :: 1 "+JSON.stringify(contents));
	
	for (var p = 0; p < contents.length; p ++) {
        var current = contents[p];
        
        validatePromo('callForCart', current, null, null);
	}
	
	$.sessionStorage.setItem('cart', JSON.stringify(contents));
}

function computeAndUpdatePromotionMap(current, promoRuleType, signUpPrice){
	var remSignUpPrice = signUpPrice;
	if(current.promotionMap && current.promotionMap != null && current.promotionMap != undefined){
		for(var m=0; m < current.promotionMap.length; m++){
			var promo = current.promotionMap[m];
			console.log("  current.promotionMap[m].actualDiscountValue 11 "+current.promotionMap[m].actualDiscountValue);
			
			if(promo.PromoRuleType == promoRuleType){
				if(remSignUpPrice > 0 && promo.remDiscount > 0){
					if(promo.monthlyDiscountAmount > remSignUpPrice){
						actualDiscount = parseFloat(parseFloat(remSignUpPrice).toFixed(2));
					}else{
						actualDiscount = parseFloat(parseFloat(promo.monthlyDiscountAmount).toFixed(2));
					}
				}
				console.log("  actualDiscount  "+actualDiscount);
				remSignUpPrice -= parseFloat(actualDiscount);
				current.promotionMap[m].actualDiscountValue += actualDiscount;
				current.promotionMap[m].remDiscount -= parseFloat(actualDiscount);
			}
		}
	}
}

function validatePromo(callFor, current, promoMap, amountMap){
	var promotionMap = null, amountJSON = null;
	if(callFor == 'callForCart'){
		promotionMap = current.promotionMap;
	}else{
		promotionMap = promoMap;
		amountJSON = JSON.parse(amountMap);
	}
	if(current != null && current.promotionMap && current.promotionMap != undefined && current.promotionMap != null){
		for(var m=0; m < current.promotionMap.length; m++){
			var promo = current.promotionMap[m];
			if(promo.PromoRuleType == "Sign Up"){
				current.promotionMap[m].remDiscount = current.promotionMap[m].discountValue;
			}
			current.promotionMap[m].actualDiscountValue = 0;
		}
		
		var signupDiscount = 0, regDiscount = 0, depositDiscount = 0, setupFeeDiscount = 0, joinFeeDiscount = 0;
		var signupPromoLen = 0, regPromoLen = 0, depositPromoLen = 0, setupPromoLen = 0, joinPromoLen = 0;
	}
    if(promotionMap && promotionMap != undefined && promotionMap != null){
        for(var j=0; j < promotionMap.length; j++){
			var promo = promotionMap[j];
			if(promo.PromoRuleType == 'Sign Up'){
				signupDiscount += parseFloat(promo.discountValue);
				signupPromoLen++;
			} else if(promo.PromoRuleType == 'Registration'){
				regDiscount += parseFloat(promo.discountValue);
				regPromoLen++;
			} else if(promo.PromoRuleType == 'SetUpFee'){
				setupFeeDiscount += parseFloat(promo.discountValue);
				setupPromoLen++;
			} else if(promo.PromoRuleType == 'JoinFee'){
				joinFeeDiscount += parseFloat(promo.discountValue);
				joinPromoLen++;
			} else if(promo.PromoRuleType == 'Deposit'){
				depositDiscount += parseFloat(promo.discountValue);
				depositPromoLen++;
			}
		}
    }
    
    console.log(" signupDiscount : "+signupDiscount+", regDiscount : "+regDiscount+", depositDiscount : "+depositDiscount+", setupFeeDiscount : "+setupFeeDiscount+", joinFeeDiscount : "+joinFeeDiscount);
    
    var signUpPrice = 0, regFee = 0, setupFee = 0, depositAmt = 0, joinFee = 0, invoice1SignupPrice = 0, invoice2SignupPrice = 0;
    var areMultipleInvoices = false;
    if(callFor == 'callForCart'){
		if(current.proRatedSignupPriceForFutureSelectedStartDate > 0){
			signUpPrice = parseFloat(current.proRatedSignupPriceForFutureSelectedStartDate);
		}else if(current.proRatedSignupPrice > 0){
			signUpPrice = parseFloat(current.proRatedSignupPrice);
		}else{
			signUpPrice = parseFloat(current.signupPrice);
		}
		
		//console.log("  current.proRatedSignupPrice :: "+current.proRatedSignupPrice+",  current.signupPrice :: "+current.signupPrice);
		
		// are 2 invoices ?
		if(current.isRecurring && current.proRatedSignupPrice > current.signupPrice){
			areMultipleInvoices = true;
			invoice1SignupPrice = current.proRatedSignupPrice - current.signupPrice;
			invoice2SignupPrice = current.signupPrice;
		}
		
		regFee = current.registrationPrice;
		setupFee = current.setupfee;
		joinFee = current.joinFee;
    	depositAmt = current.depositAmount;
    }else{
    	if(amountJSON != null && parseFloat(amountJSON.signupPrice) > 0)
    		signUpPrice = parseFloat(amountJSON.signupPrice);
    	
    	if(amountJSON != null && parseFloat(amountJSON.registrationFee) > 0)
    		regFee = amountJSON.registrationFee;
    	
    	if(amountJSON != null && parseFloat(amountJSON.setupFee) > 0)
    		setupFee = amountJSON.setupFee;
    	
    	if(amountJSON != null && parseFloat(amountJSON.joinFee) > 0)
    		joinFee = amountJSON.joinFee;
    	
    	if(amountJSON != null && parseFloat(amountJSON.depositAmount) > 0)
    		depositAmt = amountJSON.depositAmount;
    }

    //console.log("  areMultipleInvoices "+areMultipleInvoices+", invoice1SignupPrice "+invoice1SignupPrice+", invoice2SignupPrice "+invoice2SignupPrice);
    
	if(signUpPrice > 0){	// && signUpPrice < signupDiscount
		//console.log("  SignupPrice < SignupDiscount ");
		if(callFor == 'callForCart'){
			
			//console.log("   before update map ::  "+JSON.stringify(current));
			
			if(!areMultipleInvoices){
				computeAndUpdatePromotionMap(current, "Sign Up", signUpPrice);
			}else{
				computeAndUpdatePromotionMap(current, "Sign Up", invoice1SignupPrice);
				computeAndUpdatePromotionMap(current, "Sign Up", invoice2SignupPrice);
			}
			
			//console.log("   updated map ::  "+JSON.stringify(current));
			
			/*var remSignUpPrice = signUpPrice;
			for(var m=0; m < current.promotionMap.length; m++){
				var promo = current.promotionMap[m];
				if(promo.PromoRuleType == 'Sign Up'){
					if(remSignUpPrice > 0){
						if(promo.discountValue > remSignUpPrice){
							current.promotionMap[m].actualDiscountValue = remSignUpPrice;
						}else{
							current.promotionMap[m].actualDiscountValue = promo.discountValue;
						}
						remSignUpPrice = parseFloat(remSignUpPrice) - parseFloat(promo.discountValue);
					}else{
						current.promotionMap[m].actualDiscountValue = 0;
					}
				}
			}*/
		}else{
			var remSignUpPrice = signUpPrice;
			if(promotionMap && promotionMap != undefined && promotionMap != null){
				for(var m=0; m < promotionMap.length; m++){
					var promo = promotionMap[m];
					if(promo.PromoRuleType == 'Sign Up'){
						if(remSignUpPrice > 0){
							if(promo.discountValue > remSignUpPrice){
								promotionMap[m].actualDiscountValue = remSignUpPrice;
							}else{
								promotionMap[m].actualDiscountValue = promo.discountValue;
							}
							remSignUpPrice = parseFloat(remSignUpPrice) - parseFloat(promo.discountValue);
						}else{
							promotionMap[m].actualDiscountValue = 0;
						}
					}
				}
			}
		}
	}
	
	if(regFee > 0 ){	//&& regFee < regDiscount
		//console.log("  regFee < regDiscount ");
		if(callFor == 'callForCart'){
			var remFee = regFee;
			if(current != null && current.promotionMap && current.promotionMap != undefined && current.promotionMap != null){
				for(var m=0; m < current.promotionMap.length; m++){
					var promo = current.promotionMap[m];
					if(promo.PromoRuleType == 'Registration'){
						if(remFee > 0){
							if(promo.discountValue > remFee){
								current.promotionMap[m].actualDiscountValue = remFee;
							}else{
								current.promotionMap[m].actualDiscountValue = promo.discountValue;
							}
							remFee = parseFloat(remFee) - parseFloat(promo.discountValue);
						}else{
							current.promotionMap[m].actualDiscountValue = 0;
						}
					}
				}
			}
		}else{
			var remFee = regFee;
			if(promotionMap && promotionMap != undefined && promotionMap != null){
				for(var m=0; m < promotionMap.length; m++){
					var promo = promotionMap[m];
					if(promo.PromoRuleType == 'Registration'){
						if(remFee > 0){
							if(promo.discountValue > remFee){
								promotionMap[m].actualDiscountValue = remFee;
							}else{
								promotionMap[m].actualDiscountValue = promo.discountValue;
							}
							remFee = parseFloat(remFee) - parseFloat(promo.discountValue);
						}else{
							promotionMap[m].actualDiscountValue = 0;
						}
					}
				}
			}
		}
	}
	
	if(setupFee > 0 ){	//&& setupFee < setupFeeDiscount
		//console.log("  regFee < regDiscount ");
		if(callFor == 'callForCart'){
			var remFee = setupFee;
			if(current != null && current.promotionMap && current.promotionMap != undefined && current.promotionMap != null){
				for(var m=0; m < current.promotionMap.length; m++){
					var promo = current.promotionMap[m];
					if(promo.PromoRuleType == 'SetUpFee'){
						if(remFee > 0){
							if(promo.discountValue > remFee){
								current.promotionMap[m].actualDiscountValue = remFee;
							}else{
								current.promotionMap[m].actualDiscountValue = promo.discountValue;
							}
							remFee = parseFloat(remFee) - parseFloat(promo.discountValue);
						}else{
							current.promotionMap[m].actualDiscountValue = 0;
						}
					}
				}
			}
		}else{
			var remFee = setupFee;
			if(promotionMap && promotionMap != undefined && promotionMap != null){
				for(var m=0; m < promotionMap.length; m++){
					var promo = promotionMap[m];
					if(promo.PromoRuleType == 'SetUpFee'){
						if(remFee > 0){
							if(promo.discountValue > remFee){
								promotionMap[m].actualDiscountValue = remFee;
							}else{
								promotionMap[m].actualDiscountValue = promo.discountValue;
							}
							remFee = parseFloat(remFee) - parseFloat(promo.discountValue);
						}else{
							promotionMap[m].actualDiscountValue = 0;
						}
					}
				}
			}
		}
	}
	
	if(joinFee > 0 ){ //&& joinFee < joinFeeDiscount
		//console.log("  regFee < regDiscount ");
		if(callFor == 'callForCart'){
			var remFee = joinFee;
			if(current != null && current.promotionMap && current.promotionMap != undefined && current.promotionMap != null){
				for(var m=0; m < current.promotionMap.length; m++){
					var promo = current.promotionMap[m];
					if(promo.PromoRuleType == 'JoinFee'){
						if(remFee > 0){
							if(promo.discountValue > remFee){
								current.promotionMap[m].actualDiscountValue = remFee;
							}else{
								current.promotionMap[m].actualDiscountValue = promo.discountValue;
							}
							remFee = parseFloat(remFee) - parseFloat(promo.discountValue);
						}else{
							current.promotionMap[m].actualDiscountValue = 0;
						}
					}
				}
			}
		}else{
			var remFee = joinFee;
			if(promotionMap && promotionMap != undefined && promotionMap != null){
				for(var m=0; m < promotionMap.length; m++){
					var promo = promotionMap[m];
					if(promo.PromoRuleType == 'JoinFee'){
						if(remFee > 0){
							if(promo.discountValue > remFee){
								promotionMap[m].actualDiscountValue = remFee;
							}else{
								promotionMap[m].actualDiscountValue = promo.discountValue;
							}
							remFee = parseFloat(remFee) - parseFloat(promo.discountValue);
						}else{
							promotionMap[m].actualDiscountValue = 0;
						}
					}
				}
			}
		}
	}
	
	if(depositAmt > 0 ){	//&& depositAmt < joinFeeDiscount
		//console.log("  regFee < regDiscount ");
		if(callFor == 'callForCart'){
			var remFee = depositAmt;
			if(current != null && current.promotionMap && current.promotionMap != undefined && current.promotionMap != null){
				for(var m=0; m < current.promotionMap.length; m++){
					var promo = current.promotionMap[m];
					if(promo.PromoRuleType == 'Deposit'){
						if(remFee > 0){
							if(promo.discountValue > remFee){
								current.promotionMap[m].actualDiscountValue = remFee;
							}else{
								current.promotionMap[m].actualDiscountValue = promo.discountValue;
							}
							remFee = parseFloat(remFee) - parseFloat(promo.discountValue);
						}else{
							current.promotionMap[m].actualDiscountValue = 0;
						}
					}
				}
			}
		}else{
			var remFee = depositAmt;
			if(promotionMap && promotionMap != undefined && promotionMap != null){
				for(var m=0; m < promotionMap.length; m++){
					var promo = promotionMap[m];
					if(promo.PromoRuleType == 'Deposit'){
						if(remFee > 0){
							if(promo.discountValue > remFee){
								promotionMap[m].actualDiscountValue = remFee;
							}else{
								promotionMap[m].actualDiscountValue = promo.discountValue;
							}
							remFee = parseFloat(remFee) - parseFloat(promo.discountValue);
						}else{
							promotionMap[m].actualDiscountValue = 0;
						}
					}
				}
			}
		}
	}
	if(callFor != 'callForCart'){
		return promotionMap;
	}
}

function getCartItemsInMap(cart, ignoreItemFromCart){
	var cartItems = [];
	if(cart.length>0){
		$.each(cart, function(i,cartItem) {
			
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
			
			if(!cartItem.ignoreWhileRemoveForPromoFlag){
				cartItems.push(JSON.stringify(cartItemsMap));
			}
			
		});
	}
	return cartItems;
}

function checkIfRecurring(billingFreqency){
	var isRecurring = false;
	if(billingFreqency == 'Recurring'){
		isRecurring = true;
	}
	return isRecurring;
}

function isMemberRadioChange(thisVar){
	$thisObj = $(thisVar);	
	if($thisObj.attr("id") == 'Yes'){
		$(document).find("#contact-validation-txt").css("display", "none");
	}
	if($thisObj.attr("id") == 'No'){
		$(document).find("#contact-validation-txt").css("display", "inline");
		updateDOB();
	}
	
}

function getURLItemContactPromo(urlPromoItemDetailId, urlPromoContactId, urlPromoCode){
	if(urlPromoContactId != undefined && urlPromoContactId != null && urlPromoContactId != ''){
		 selectItemContact = urlPromoItemDetailId+"_"+urlPromoContactId+"_"+urlPromoCode;
	}else{
		 selectItemContact = urlPromoItemDetailId+"_0_"+urlPromoCode; 
	}
		 
	return selectItemContact;
}

function getDueDate(dueDateOptionItem, dueDateOffsetItem, item_start_date, selectedStartDate, billDate, isInServiceWithChildCareForContact, inServiceWithChildCareProperty){
	var dueDate = null;
	var signUpDate = null;
	if(selectedStartDate && selectedStartDate != ''){
		signUpDate = new Date(selectedStartDate);
	}else{
		signUpDate = new Date(); 
	}
	
	var dueDateOption = '';
	var dueDateOffset = '';
	
	if(isInServiceWithChildCareForContact){
		if(inServiceWithChildCareProperty.hasOwnProperty('IN_SERVICE_WITH_CHILD_CARE_DUE_DATE')){
			dueDateOption = inServiceWithChildCareProperty['IN_SERVICE_WITH_CHILD_CARE_DUE_DATE'];
		}
		if(inServiceWithChildCareProperty.hasOwnProperty('IN_SERVICE_WITH_CHILD_CARE_DUE_DATE_OFFSET')){
			dueDateOffset = inServiceWithChildCareProperty['IN_SERVICE_WITH_CHILD_CARE_DUE_DATE_OFFSET'];
		}
	}else{
		if(dueDateOptionItem && dueDateOptionItem != null && dueDateOptionItem != '' && dueDateOptionItem != undefined){
			dueDateOption = dueDateOptionItem;
		}
		dueDateOffset = dueDateOffsetItem;
	}
	
	/*console.log("   selectedStartDate   "+selectedStartDate);
	console.log("   signUpDate   		"+signUpDate);
	console.log("   dueDateOption   	"+dueDateOption);
	console.log("   dueDateOffset   	"+dueDateOffset);*/
	//console.log("   programStartDate   	"+programStartDate);
	
	if(dueDateOption != ''){
		if(dueDateOption == 'Sign Up Date'){
			dueDate = addDaysToDate(signUpDate, parseInt(dueDateOffset));
			//console.log("  dueDate 0 "+dueDate);
		}/*else if(dueDateOption == 'Next Bill Date'){
			//dueDate.setDate(signUpDate + dueDateOffset; ToDo
		}*/else if(dueDateOption == 'Program Start Date'){
			var programStartDate = new Date(convertJsonDate(item_start_date));
			//console.log("   programStartDate   	"+programStartDate);
			dueDate = addDaysToDate(programStartDate, parseInt(dueDateOffset));
			//console.log("  dueDate 1 "+dueDate);
		}/*else if(dueDateOption == 'Monthly Date'){
			var monthOfSignup = signUpDate.getMonth() + 1;
			var yearOfSignup = signUpDate.getFullYear();
			dueDate = new Date(monthOfSignup+"/"+dueDateOffset+"/"+yearOfSignup);
			console.log("  dueDate 2 "+dueDate);
		}*/else if(dueDateOption == 'Month of Sign Up Date'){
			var monthOfSignup = signUpDate.getMonth() + 1;
			var yearOfSignup = signUpDate.getFullYear();
			var monthOfSignupDate = new Date(monthOfSignup+"/01/"+yearOfSignup);
			dueDate = addDaysToDate(monthOfSignupDate, parseInt(dueDateOffset));
			//console.log("  dueDate 3 "+monthOfSignupDate);
		}else if(dueDateOption == 'Bill Date'){
			//console.log("   billDate   	"+billDate);
			if(billDate != null){
				dueDate = addDaysToDate(billDate, parseInt(dueDateOffset));
				//console.log("  dueDate 1 "+dueDate);
			}
		}
	}
	return dueDate;
}

function getBillDate(billDateOptionItem, billDateOffsetItem, selectedStartDate, invoiceDate, isInServiceWithChildCareForContact, inServiceWithChildCareProperty){
	var billDate = null;
	var signUpDate = null;
	if(selectedStartDate && selectedStartDate != ''){
		signUpDate = new Date(selectedStartDate);
	}else{
		signUpDate = new Date(); 
	}
	
	var billDateOption = '';
	var billDateOffset = '';
	
	if(isInServiceWithChildCareForContact){
		if(inServiceWithChildCareProperty != null && inServiceWithChildCareProperty.length > 0){
			if(inServiceWithChildCareProperty.hasOwnProperty('IN_SERVICE_WITH_CHILD_CARE_BILL_DATE')){
				billDateOption = inServiceWithChildCareProperty['IN_SERVICE_WITH_CHILD_CARE_BILL_DATE'];
			}
			if(inServiceWithChildCareProperty.hasOwnProperty('IN_SERVICE_WITH_CHILD_CARE_BILL_DATE_OFFSET')){
				billDateOffset = inServiceWithChildCareProperty['IN_SERVICE_WITH_CHILD_CARE_BILL_DATE_OFFSET'];
			}
		}
	}else{
		if(billDateOptionItem && billDateOptionItem != null && billDateOptionItem != '' && billDateOptionItem != undefined){
			billDateOption = billDateOptionItem;
		}
		if(billDateOffsetItem && !isNaN(billDateOffsetItem)){
			billDateOffset = billDateOffsetItem;
		}
	}
	
	/*console.log("   selectedStartDate   "+selectedStartDate);
	console.log("   signUpDate   		"+signUpDate);
	console.log("   billDateOption   	"+billDateOption);
	console.log("   billDateOffset   	"+parseInt(billDateOffset));
	console.log("   invoiceDate      	"+invoiceDate);*/
	
	if(billDateOption != '' && billDateOffset != null){
		if(billDateOption == 'Month of Sign Up Date'){
			var monthOfSignup = signUpDate.getMonth() + 1;
			var yearOfSignup = signUpDate.getFullYear();
			var monthOfSignupDate = new Date(monthOfSignup+"/01/"+yearOfSignup);
			//console.log(" bill date monthOfSignupDate "+monthOfSignupDate);
			billDate = addDaysToDate(monthOfSignupDate, parseInt(billDateOffset));
			//console.log(" bill date "+billDate);
		}else if(billDateOption == 'Current Date'){
			billDate = new Date();
		}else if(billDateOption == 'Month of Invoice Date'){
			var monthOfInvoice = invoiceDate.getMonth() + 1;
			var yearOfInvoice = invoiceDate.getFullYear();
			var monthOfInvoiceDate = new Date(monthOfInvoice+"/01/"+yearOfInvoice);
			//console.log(" bill date monthOfSignupDate "+monthOfSignupDate);
			billDate = addDaysToDate(monthOfInvoiceDate, parseInt(billDateOffset));
		}
	}
	return billDate;
}

function getNextBillDate(priceOption, billDate, intendedNextBillDate, billingFrequency){
	var nextBillDate = null;
	//var billDate1 = billDate;
	var isRecurring = checkIfRecurring(billingFrequency);
	
	if(isRecurring){
		
		if(intendedNextBillDate != null){
			nextBillDate = convertDateStrToDate(intendedNextBillDate); 
		}else{
			nextBillDate = convertDateStrToDate(billDate);
		}
		
		/*if(nextBillDate instanceof Date){
			// do nothing
		}else{*/
			//billDate1 = convertDateStrToDate(billDate1);
		//}
		
		if(priceOption != null & nextBillDate != null && nextBillDate instanceof Date){
			if(priceOption == 'Monthly'){
				var datePlusOneMonth = new Date(nextBillDate.setMonth(nextBillDate.getMonth()+1));
				nextBillDate = datePlusOneMonth;
			}else if(priceOption == 'Weekly'){
				var datePlusOneWeek = addDaysToDate(nextBillDate, 7);
				nextBillDate = datePlusOneWeek;
			}else if(priceOption == 'Annual' || priceOption == 'Package'){
				var datePlusOneYear = new Date(nextBillDate.setFullYear(nextBillDate.getFullYear()+1));
				nextBillDate = datePlusOneYear;
			}
		}
		
		/*var signUpDate = null;
		if(selectedStartDate && selectedStartDate != ''){
			signUpDate = new Date(selectedStartDate);
		}else{
			signUpDate = new Date(); 
		}
		
		var billDateOffset = '';
		if(isInServiceWithChildCareForContact){
			if(inServiceWithChildCareProperty != null && inServiceWithChildCareProperty.length > 0){
				if(inServiceWithChildCareProperty.hasOwnProperty('IN_SERVICE_WITH_CHILD_CARE_BILL_DATE_OFFSET')){
					billDateOffset = inServiceWithChildCareProperty['IN_SERVICE_WITH_CHILD_CARE_BILL_DATE_OFFSET'];
				}
			}
		}else{
			if(item.billDateOffset && !isNaN(item.billDateOffset)){
				billDateOffset = item.billDateOffset;
			}
		}
		
		if(isRecurring && billDateOffset != ''){
			var monthOfSignup = signUpDate.getMonth() + 1;
			var yearOfSignup = signUpDate.getFullYear();
			var monthOfSignupStartDate = new Date(monthOfSignup+"/01/"+yearOfSignup);
			var monthOfSignupPlusOneMonth = new Date(monthOfSignupStartDate.setMonth(monthOfSignupStartDate.getMonth()+1));
			nextBillDate = addDaysToDate(monthOfSignupPlusOneMonth, parseInt(billDateOffset));
		}*/
	}
	//console.log("priceOption >> "+priceOption+"  billDate >> "+billDate+" billingFrequency >> "+billingFrequency+" nextBillDate >> "+nextBillDate);
	return nextBillDate;
}

function getComputedInvoiceDate(selectedStartDate){
	var invoiceDt;
	if(selectedStartDate && selectedStartDate != ''){
		invoiceDt = new Date(selectedStartDate);
	}else{
		invoiceDt = new Date(); 
	}
	return invoiceDt;
}

function getBillDateOnInvoice(billDate){
	var billDateOnInvoice = null;
	var currentDate = new Date();
	if(currentDate > billDate){
		billDateOnInvoice = currentDate;
	}else{
		billDateOnInvoice = billDate;
	}
	return billDateOnInvoice;
}

function getDueDateOnInvoice(dueDate){
	var dueDateOnInvoice = null;
	var currentDate = new Date();
	if(currentDate > dueDate){
		dueDateOnInvoice = currentDate;
	}else{
		dueDateOnInvoice = dueDate;
	}
	return dueDateOnInvoice;
}

function addDaysToDate(theDate, days) {
    return new Date(theDate.getTime() + days*24*60*60*1000);
}

function getInvoiceArr(item, selectedStartDate, isInServiceWithChildCareForContact, inServiceWithChildCareProperty, signupPriceMap, intendedNextBillDate, priceOption){
	var invoiceArr = new Array();
	
	// Create 1st invoice
	
	var invoiceDate1 = getComputedInvoiceDate(selectedStartDate);
	var invoiceAmt1 = 0;
	var billDate1 = getBillDate(item.billDateOption, item.billDateOffset, selectedStartDate, invoiceDate1, isInServiceWithChildCareForContact, inServiceWithChildCareProperty);
	var dueDate1 = getDueDate(item.dueDateOption, item.dueDateOffset, item.start_date, selectedStartDate, billDate1, isInServiceWithChildCareForContact, inServiceWithChildCareProperty);	
	
	var billDateOnInvoice1 = getBillDateOnInvoice(billDate1);
	var dueDateOnInvoice1 = getDueDateOnInvoice(dueDate1);
	
	if(signupPriceMap != null && signupPriceMap.currentProRatedSignupPrice != null && signupPriceMap.currentProRatedSignupPrice > 0){
		invoiceAmt1 = signupPriceMap.currentProRatedSignupPrice;
	}else{
		invoiceAmt1 = signupPriceMap.totalProRatedSignupPrice;
	}
	
	var invoiceMap1 = new Object();
	//invoiceMap1.invoiceDate = invoiceDate1;
	invoiceMap1.invoiceAmt = invoiceAmt1;
	invoiceMap1.billDate = billDateOnInvoice1;
	invoiceMap1.dueDate = dueDateOnInvoice1;
	invoiceArr[0] = invoiceMap1;
	
	// Determine if 2nd invoice is required
	var nextBillDate = convertDateStrToDate(intendedNextBillDate);
	var currentDate = new Date();
	//console.log("  nextBillDate ::: "+nextBillDate);
	if(nextBillDate == null || currentDate <= nextBillDate){
		// do nothing
	}else{
		
		// Create 2nd invoice
		
		if(priceOption != null & nextBillDate != null && nextBillDate instanceof Date){
			if(priceOption == 'Monthly'){
				var datePlusOneMonth = new Date(nextBillDate.setMonth(nextBillDate.getMonth()+1));
				nextBillDate = datePlusOneMonth;
			}else if(priceOption == 'Weekly'){
				var datePlusOneWeek = addDaysToDate(nextBillDate, 7);
				nextBillDate = datePlusOneWeek;
			}else if(priceOption == 'Annual'){
				var datePlusOneYear = new Date(nextBillDate.setFullYear(nextBillDate.getFullYear()+1));
				nextBillDate = datePlusOneYear;
			}
		}
		var invoiceDate2 = nextBillDate;
		
		var invoiceAmt2 = 0;
		var billDate2 = getBillDate(item.billDateOption, item.billDateOffset, selectedStartDate, invoiceDate2, isInServiceWithChildCareForContact, inServiceWithChildCareProperty);
		var dueDate2 = getDueDate(item.dueDateOption, item.dueDateOffset, item.start_date, selectedStartDate, billDate2, isInServiceWithChildCareForContact, inServiceWithChildCareProperty);	
		
		var billDateOnInvoice2 = getBillDateOnInvoice(billDate2);
		var dueDateOnInvoice2 = getDueDateOnInvoice(dueDate2);
		
		if(signupPriceMap != null && signupPriceMap.currentProRatedSignupPrice != null && signupPriceMap.currentProRatedSignupPrice > 0){
			invoiceAmt2 = signupPriceMap.totalProRatedSignupPrice - signupPriceMap.currentProRatedSignupPrice;
		}
		
		// Create 2nd invoice
		var invoiceMap2 = new Object();
		//invoiceMap2.invoiceDate = invoiceDate2;
		invoiceMap2.invoiceAmt = invoiceAmt2;
		invoiceMap2.billDate = billDateOnInvoice2;
		invoiceMap2.dueDate = dueDateOnInvoice2;
		invoiceArr[1] = invoiceMap2;
		
	}
	//console.log("  	invoiceArr   ::::   "+JSON.stringify(invoiceArr));
	return invoiceArr;
}

function confirmProgram(sId){
	
	//console.log(" sid  "+sId);
	
	$.ajax({
		  type: "GET", 
		  url:"confirmProgram?sId="+sId,
		  async:false,
		  dataType: "json",
		  success: function( data ) {
			  //console.log(" got resp ");
			  if(data && data.RESULT == 'YES'){
				  alert("Program confirmed successfully.");
				  window.location.href = 'viewAllPrograms';
			  }else{
				  alert("Failed to confirm program. Please try again later.");
			  }
		  },
		  error: function( xhr,status,error ){
			  alert("Failed 1 "+xhr+"  err  "+error+"   st  "+status);
		  }
	});
	
	
}
