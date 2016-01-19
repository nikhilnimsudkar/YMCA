var emailValid = "false";
var loginValid = "false";
var signInSelected = "false";
var isLoggedIn = "false";
var paymentFreq1 = $("#paymentFrequencySelect").data("kendoDropDownList");

var selectedHeaderInfo = "";
var selectedTierPriceInfo = "";
var selectedProdDescInfo = "";
var selectedJoiningFeeInfo = "";

var selectedProdctId = "";
var selectedProductName = "";
var selectedProductTotalPrice = "";
var selectedProdTandc = "";

var selectedAutoPromoDiscount = "";
var selectedItemDetailsId = "";
var selectedAnnualTierPrice = 0;
var selectedAnnualJoinPrice = 0;

var selectedMonthlyBillingFreqency = 0;
var selectedAnnualBillingFreqency = 0;


var signedUpTierPriceInfo = 0;
var signedUpJoiningFeeInfo =  0;
var signedUpProdctId =  "";
var signedUpProductName =  ""; 						  
var signedUpAutoPromoDiscount =  "";
var signedUpAllAreaPrice =  0;
var signedUpBayAreaPrice =  0;

var registerSelected = false;


var tierPriceVal = 0;	    					  
var allLocationVal = 0;
var bayAreaVal = 0;

var signedUpProductsValue = 0;

var secKidCount = 1;
var paymentOrderId = 0;

var paymentOrderId = 0;

var isUserLoggedInCheck = 'false';
var months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
/* $("#grid").data("kendoGrid").dataSource.data([ ]);

var productsGrid = $('#grid').data('kendoGrid');
var dataSource = productsGrid.dataSource;
dataSource.add({ id: "testId", productName: 'A Johns Product 1', productDescription: "desc"});
dataSource.sync(); */

function formatDate(dateStr) {
	//2015-02-25 17:48:52
	var date = dateStr.split(" ");
	var dateArr =  date[0].split("-");
	var dateOfMonth = dateArr[2];
	var month = dateArr[1];
	var year  = dateArr[0];
	var formattedDate = month + "/" + dateOfMonth + "/" + year;
	return formattedDate;
}

function days_between(date1, date2) {

    // The number of milliseconds in one day
    var ONE_DAY = 1000 * 60 * 60 * 24

    // Convert both dates to milliseconds
    var date1_ms = date1.getTime()
    var date2_ms = date2.getTime()

    // Calculate the difference in milliseconds
    var difference_ms = Math.abs(date1_ms - date2_ms)

    // Convert back to days and return
    return Math.round(difference_ms/ONE_DAY)

}

Date.isLeapYear = function (year) { 
    return (((year % 4 === 0) && (year % 100 !== 0)) || (year % 400 === 0)); 
};

Date.getDaysInMonth = function (year, month) {
    return [31, (Date.isLeapYear(year) ? 29 : 28), 31, 30, 31, 30, 31, 31, 30, 31, 30, 31][month];
};

Date.prototype.isLeapYear = function () { 
    return Date.isLeapYear(this.getFullYear()); 
};

Date.prototype.getDaysInMonth = function () { 
    return Date.getDaysInMonth(this.getFullYear(), this.getMonth());
};

Date.prototype.addMonths = function (value) {
    var n = this.getDate();
    this.setDate(1);
    this.setMonth(this.getMonth() + value);
    this.setDate(Math.min(n, this.getDaysInMonth()));
    return this;
};

//$(document).ready(function(){	
function proceedToChangeMembershipSignup(){
	console.log("  changeMembership 111 ");
	
	$("#selectContactPrimaryAdult").kendoDropDownList();
	var dobYearForm = new Date().getFullYear();
	dobYearForm = parseInt(dobYearForm.toString());
	for(var i=0; i<100 ; i++){
		$('#dobYearForm').append($('<option>', {value: dobYearForm,text: dobYearForm}));
		dobYearForm = dobYearForm - 1;
	}
	$("#dobYearForm").kendoDropDownList();    
	$("#dobDateForm").kendoDropDownList();
	$("#dobMonthForm").kendoDropDownList();	
	//console.log($("#dobMonthForm").val() + "/" + $("#dobDateForm").val()+ "/" +  $("#dobYearForm").val());
	
	$(document).on('change', '#dobMonthForm, #dobDateForm, #dobYearForm', function(){
		var dobMonthForm = $("#dobMonthForm").val();
		var dobDateForm = $("#dobDateForm").val();
		var dobYearForm = $("#dobYearForm").val();			
		$("#dob").val(dobMonthForm+"/"+dobDateForm+"/"+dobYearForm);
		//$("#becomeMemberForm").valid();		
	});
	
	$(document).on('change', '#secDobMonthForm, #secDobDateForm, #secDobYearForm', function(){
		var dobMonthForm = $("#secDobMonthForm").val();
		var dobDateForm = $("#secDobDateForm").val();
		var dobYearForm = $("#secDobYearForm").val();			
		$("#secDOB").val(dobMonthForm+"/"+dobDateForm+"/"+dobYearForm);
		//$("#becomeMemberForm").valid();		
	});
	
	$(document).on('change', '#youthDobMonthForm, #youthDobDateForm, #youthDobYearForm', function(){
		var dobMonthForm = $("#youthDobMonthForm").val();
		var dobDateForm = $("#youthDobDateForm").val();
		var dobYearForm = $("#youthDobYearForm").val();			
		$("#secDOB").val(dobMonthForm+"/"+dobDateForm+"/"+dobYearForm);
		//$("#becomeMemberForm").valid();
		checkIsUserAdult();
	});
	
	$(document).on('change', '#thirdDobMonthForm, #thirdDobDateForm, #thirdDobYearForm', function(){
		var dobMonthForm = $("#thirdDobMonthForm").val();
		var dobDateForm = $("#thirdDobDateForm").val();
		var dobYearForm = $("#thirdDobYearForm").val();			
		$("#thirdDOB").val(dobMonthForm+"/"+dobDateForm+"/"+dobYearForm);
		//$("#becomeMemberForm").valid();		
	});
	
	$(document).on('change', '#firstKidDobMonthForm, #firstKidDobDayForm, #firstKidDobYearForm', function(){
		var dobMonthForm = $("#firstKidDobMonthForm").val();
		var dobDateForm = $("#firstKidDobDayForm").val();
		var dobYearForm = $("#firstKidDobYearForm").val();			
		$("#firstKidDOBInp").val(dobMonthForm+"/"+dobDateForm+"/"+dobYearForm);
		//$("#becomeMemberForm").valid();		
	});
	
	$(document).on('change', '.kidDobYearForm, .kidDobMonthForm, .kidDobDateForm', function(){
		var thisObj = $(this);		
		var kidForm = thisObj.closest( "tr" );
		var dobMonthForm = kidForm.find("select.kidDobMonthForm").val();
		var dobDateForm = kidForm.find("select.kidDobDateForm").val();
		var dobYearForm = kidForm.find("select.kidDobYearForm").val();
		
		kidForm.find("input.dateOfBirth").attr("value",dobMonthForm+"/"+dobDateForm+"/"+dobYearForm);		
		//$("#becomeMemberForm").valid();		
	});
	$("#selectContactPrimaryAdult").on('change',function(e){    	
    	var contactVal = $(this).val();
    	//console.log(contactVal);
    	var contactInfoArr = contactVal.split("\|\|");
    	var thisConSelObj = $(this);
    	var flag = true;
    	$(document).find('select.selectContactDropDw').each(function(i, obj) {				
    		var selectedContacts = $(obj).data("kendoDropDownList");
    		var dropDownVal = selectedContacts.value();
    		var dropDownArr = dropDownVal.split("\|\|");          		
    		if(dropDownVal != 0 && dropDownVal != 'Other' && thisConSelObj.attr("id") != obj.id && contactInfoArr[contactInfoArr.length - 1] == dropDownArr[dropDownArr.length - 1]){
    			//alert("Contact Already selected");
    			duplicateContactSelectedError();        			
    			flag = false;
    		}
    	});

    	if(contactVal != 0 && contactVal != 'Other' && flag){
    		
    		populatePrimaryMember(contactInfoArr[0], contactInfoArr[1], contactInfoArr[2], contactInfoArr[3], contactInfoArr[4], contactInfoArr[5]);
    		$(document).find('#firstName').prop('readonly', true);
    		$(document).find('#lastName').prop('readonly', true);										    			
    		$(document).find('#phoneNumber').prop('readonly', true);
    		$(document).find('#email').prop('readonly', true);
    		$(document).find('#dob').prop('readonly', true);
    		$(document).find('#genderM').prop('readonly', true);
    		$(document).find('#genderF').prop('readonly', true);
    	}else if(contactVal == 0 || contactVal == 'Other' || flag == false){
    		resetPrimaryMember();
    		$(document).find('#firstName').prop('readonly', false);
    		$(document).find('#lastName').prop('readonly', false);										    			
    		$(document).find('#phoneNumber').prop('readonly', false);
    		$(document).find('#email').prop('readonly', false);
    		$(document).find('#dob').prop('readonly', false);
    		$(document).find('#genderM').prop('readonly', false);
    		$(document).find('#genderF').prop('readonly', false);
    		populateInputDOBfromDropdownMenu($("#dobMonthForm").val(), $("#dobDateForm").val(), $("#dobYearForm").val(), "dob");
    	}
    	checkIsUserAdult();
   });  
	
	
	$("#statusBlock").css("display", "none");	
	 $("#paymentInfoRadio").kendoDropDownList();	 
	 signedUpProductsValue = $("#signedUpProductsValueSpann").val();
	 var paymentInfoRadioselect = $("#paymentInfoRadio").data("kendoDropDownList");
	 paymentInfoRadioselect.select(0);
	 $("#paymentTokenIdSpan").html($("#paymentInfoRadio").val());
	 $("#paymentMethodIdHidInput").attr("value", $("#paymentInfoRadio").val());
	 /*$(".k-loading-mask").show();
	 $(".k-loading-text").html("Please wait");			
		$.ajax({
		  type: "GET",
		  url: "viewPaymentInformationByLoggedInUser",								  
		  success: function( data ) {				  	  
			  if(data != null && data.length > 0){	
				  $.ajax({
					  type: "GET",
					  url: "getACHPaymentInformationByLoggedInUser",								  
					  success: function( achData ) {											  										  
						  var paymentMethodHtml = '';											  
						  if(data != null && data.length > 0){														 
							  for(var i = 0; i<data.length; i++){        
								   var dataArr = data[i];
								   //p.portalStatus, p.paymentType, p.isPrimary, p.paymentId, p.nickName, p.cardNumber, p.expirationMonth, p.expirationYear										       
								   //paymentInfoRadio						   
								    if(dataArr[2]){
									   paymentMethodHtml += '<option selected value="'+ dataArr[8] +'">'+ dataArr[4] +' '+dataArr[5] +' '+dataArr[6] +'/'+dataArr[7]+'</option>';
									   $("#paymentTokenIdSpan").html(dataArr[8]);
								   }else{
									   paymentMethodHtml += '<option value="'+ dataArr[8] +'">'+ dataArr[4] +' '+dataArr[5] +' '+dataArr[6] +'/'+dataArr[7]+'</option>';
								   }	 				
								   if(dataArr[2]){
									   paymentMethodHtml += '<option selected value="'+ dataArr[6] +'">'+ dataArr[7] +' '+dataArr[3] +' '+dataArr[4] +'/'+dataArr[5]+'</option>';
									   $("#paymentTokenIdSpan").html(dataArr[6]);
									}else{
									   paymentMethodHtml += '<option value="'+ dataArr[6] +'">'+ dataArr[7] +' '+dataArr[3] +' '+dataArr[4] +'/'+dataArr[5]+'</option>';
									}
							  }
						  }
						  if(achData != null && achData.length > 0){
							  for(var i = 0; i<achData.length; i++){        
								   var dataArr = achData[i];
								   //p.transId,  p.bankRoutingNumber, p.fullName, p.paymentType,  p.checkingAccountNumber, p.driversLicenseNumber, p.stateOfDL, p.phoneNumber, p.nickName, p.portalStatus,  p.tokenNumber , p.paymentId, p.isPrimary											       
								   if(dataArr[12]){
									   paymentMethodHtml += '<option selected value="'+ dataArr[10] +'">'+ dataArr[2] +', '+dataArr[4] +'</option></span>';
									   $("#paymentTokenIdSpan").html(dataArr[8]);
								   }else{
									   paymentMethodHtml += '<option value="'+ dataArr[10] +'">'+ dataArr[2] +', '+dataArr[4] +'</option></span>';
								   }													       
							  }
						  }
						  paymentMethodHtml += '<option value="New">Add New Card</option>';
						  paymentMethodHtml += '<option value="NewBank">Add New Bank Info</option>';
						  $("#paymentInfoRadio").html(paymentMethodHtml);
						  $("#paymentInfoRadio").kendoDropDownList();
						  $("#paymentTokenIdSpan").html($("#paymentInfoRadio").val());
						  $("#paymentMethodIdHidInput").attr("value", $("#paymentInfoRadio").val());
						  //$('#wizard').smartWizard('goToStep',3);	
						  $(".k-loading-mask").hide();
					  },
					  error: function( xhr,status,error ){
						  $("#statusBlock").css("display", "block");	
						  $("#tcloginErrorSpan").css("display", "block");	
						  $( "#tcloginErrorSpan" ).html("There was some error. Please try again later");	
						  $(".k-loading-mask").hide();								  		 
					  }
					});
				}		
			  $(".k-loading-mask").hide();
		  },
		  error: function( xhr,status,error ){
			  $("#statusBlock").css("display", "block");	
			  $("#tcloginErrorSpan").css("display", "block");	
			  $( "#tcloginErrorSpan" ).html("There was some error. Please try again later");	
			  $(".k-loading-mask").hide();								  		 
		  }
	});*/
	 //alert($("#paymentInfoRadio").val());
	 if($("#paymentInfoRadio").val()=='New'){
			$('#addBank').hide();	
			$('#addcard').show();			
			$(".stepContainer").height($(".stepContainer").find("div:not(:hidden)").height());
			/* $("#wizard"). smartWizard("fixHeight");			
			$('.stepContainer').css("height", "240px");
			$('#step-5').css("height", "225px");  */			
		}else if($("#paymentInfoRadio").val()=='NewBank'){
			$('#addcard').hide();
			$('#addBank').show();			
			$(".stepContainer").height($(".stepContainer").find("div:not(:hidden)").height());
		} else{
			$('#addcard').hide();
			$('#addBank').hide();	
			$(".stepContainer").height($(".stepContainer").find("div:not(:hidden)").height());
			$("#paymentTokenIdSpan").html($("#paymentInfoRadio").val());
			$("#paymentMethodIdHidInput").attr("value", $("#paymentInfoRadio").val()); 
			/* $("#wizard"). smartWizard("fixHeight");			
			$('.stepContainer').css("height", "240px");
			$('#step-5').css("height", "225px");  */
		}
	
		$('body').on( "change", "#paymentInfoRadio", function() {			
			if($("#paymentInfoRadio").val()=='New'){
				$('#addBank').hide();	
				$('#addcard').show();			
				$(".stepContainer").height($(".stepContainer").find("div:not(:hidden)").height());
				/* $("#wizard"). smartWizard("fixHeight");			
				$('.stepContainer').css("height", "240px");
				$('#step-5').css("height", "225px");  */			
			}else if($("#paymentInfoRadio").val()=='NewBank'){
				$('#addcard').hide();
				$('#addBank').show();			
				$(".stepContainer").height($(".stepContainer").find("div:not(:hidden)").height());
			} else{
				$('#addcard').hide();
				$('#addBank').hide();	
				$(".stepContainer").height($(".stepContainer").find("div:not(:hidden)").height());
				$("#paymentTokenIdSpan").html($("#paymentInfoRadio").val());
				$("#paymentMethodIdHidInput").attr("value", $("#paymentInfoRadio").val()); 
				/* $("#wizard"). smartWizard("fixHeight");			
				$('.stepContainer').css("height", "240px");
				$('#step-5').css("height", "225px");  */
			}
		});
		
		$(document).on('change', 'input[name=paymentTypeSelect]', function(){
			var paymentType = $(this).val();
			var currentMembershipTxt = $(this).parent().parent().parent().parent().parent().parent().find( ".isCurrentMembership" ).html();
			var signUpProductType = $('#signUpProductType').val();
			/*if(currentMembershipTxt == 'Current Membership'){
				if((paymentType == 'AllArea' && signUpProductType != 'All Branches') || (paymentType == 'BayArea' && signUpProductType != 'Bay Area') || (paymentType == 'ThisBranchOnly' && signUpProductType != 'This Branch Only')){	
					$( ".is-current-membership-change" ).addClass( "product-register-div" );
				}else{
					$( ".is-current-membership-change" ).removeClass( "product-register-div" );
				}
			}else {			
				$( ".is-current-membership-change" ).removeClass( "product-register-div" );
			}*/
			    	
		});
		
	var isUserLoggedInVar = $("#isUserLoggedIn").html();
	if(isUserLoggedInVar == 'true'){
		isUserLoggedInCheck = 'true';
	}else{
		isUserLoggedInCheck = 'false';
	}
	
	$("input#cardNum").on({
		  keydown: function(e) {
		    if (e.which === 32 || e.which == 173)
		      return false;
		  },
		  change: function() {
		    this.value = this.value.replace(/\s/g, "");
		  }
		});
	
	$('.tooltip_display').click(function() {
	    var $this = $(this);
	    /* $("#background").css({
	        "opacity": "0.3"
	    }).fadeIn("slow"); */


	    $("#large").html(function() {
	        $('.ttip').css({
	            left: $this.position() + '20px',
	            top: $this.position() + '50px'
	        }).show(500)

	    }).fadeIn("slow");


	});
	
	$('#removePromoCodeBtn').click(function() {
		$("#sumPromoCodeTr").css("display","none");
		$("#sumPromoCodeName").html("");
		$("#sumPromoCodeVal").html("");
		
		var finalAmount = 0;
		  if($("#sumEarlyBirdDiscount").text() != '' && $("#sumPromoCodeVal").text() != ''){
		  	finalAmount = parseFloat($("#sumTotalPriceTd").text()) - parseFloat($("#sumEarlyBirdDiscount").text()) - parseFloat($("#sumPromoCodeVal").text());
		  }else if($("#sumEarlyBirdDiscount").text() != ''){
		  	finalAmount = parseFloat($("#sumTotalPriceTd").text()) - parseFloat($("#sumEarlyBirdDiscount").text());
		  }else if($("#sumPromoCodeVal").text() != ''){
		  	finalAmount = parseFloat($("#sumTotalPriceTd").text()) - parseFloat($("#sumPromoCodeVal").text());
		  }else {
		  	finalAmount = parseFloat($("#sumTotalPriceTd").text());
		  }
		  if($("#sumJoinFeeTd").text() != ''){
			  	finalAmount = finalAmount + parseFloat($("#sumJoinFeeTd").text());
		  }
		  $("#sumFinalAmountTd").html(finalAmount);
	});
	
	/*$('#applypromo').click(function() {
		
		$("#tcSuccessSpan").css("display", "none");		
		 $("#tcSuccessSpan" ).html("");	
		 $("#tcErrorSpan").css("display", "none");		
		 $( "#tcErrorSpan" ).html("");
		var d_promocode = $("#c_promocode").val();
		var found = false;

		if(d_promocode!=''){
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
											  if(selectedItemDetailsId == itemInfo.itemDetailsId){
													  var p_price = parseFloat(0);
													  p_price = parseFloat($("#sumTotalPriceTd").text());
													  var d_price = parseFloat(0);
													  var discount_type = promo.discounttype;
													  if(discount_type=='AMOUNT')
														  d_price = promo.discountValue;
													  else if(discount_type=='PERCENTAGE'){
														  d_price = parseFloat(promo.discountPercentage) * parseFloat(p_price);
														  d_price = d_price/100;
													  }													  
													  found = true;
													  if(d_price != 0){
														  $("#sumPromoCodeTr").css("display","");
														  $("#sumPromoCodeName").html(d_promocode);
														  $("#sumPromoCodeVal").html(d_price);
													  }else{
														  $("#sumPromoCodeTr").css("display","none");
														  $("#sumPromoCodeName").html("");
														  $("#sumPromoCodeVal").html("");
													  }
													  
													  var finalAmount = 0;
													  if($("#sumEarlyBirdDiscount").text() != '' && $("#sumPromoCodeVal").text() != ''){
													  	finalAmount = parseFloat($("#sumTotalPriceTd").text()) - parseFloat($("#sumEarlyBirdDiscount").text()) - parseFloat($("#sumPromoCodeVal").text());
													  }else if($("#sumEarlyBirdDiscount").text() != ''){
													  	finalAmount = parseFloat($("#sumTotalPriceTd").text()) - parseFloat($("#sumEarlyBirdDiscount").text());
													  }else if($("#sumPromoCodeVal").text() != ''){
													  	finalAmount = parseFloat($("#sumTotalPriceTd").text()) - parseFloat($("#sumPromoCodeVal").text());
													  }else {
													  	finalAmount = parseFloat($("#sumTotalPriceTd").text());
													  }
													  $("#sumFinalAmountTd").html(finalAmount);
													  
													  var finalAmount = 0;												
													    
												    	if($("#sumEarlyBirdDiscount").text() != '' && $("#sumPromoCodeVal").text() != ''){
												    		finalAmount = (parseFloat($("#sumChangePriceTd").text()) + parseFloat($("#sumProRatePriceTd").text())) - parseFloat($("#sumEarlyBirdDiscount").text()) - parseFloat($("#sumPromoCodeVal").text());
												    	}else if($("#sumEarlyBirdDiscount").text() != ''){
												    		finalAmount = (parseFloat($("#sumChangePriceTd").text()) + parseFloat($("#sumProRatePriceTd").text())) - parseFloat($("#sumEarlyBirdDiscount").text());
												    	}else if($("#sumPromoCodeVal").text() != ''){
												    		finalAmount = (parseFloat($("#sumChangePriceTd").text()) + parseFloat($("#sumProRatePriceTd").text())) - parseFloat($("#sumPromoCodeVal").text());
												    	}else {
												    		finalAmount = (parseFloat($("#sumChangePriceTd").text()) + parseFloat($("#sumProRatePriceTd").text()));
												    	}   
												    	finalAmount = Math.round(finalAmount);
												    	if($("#sumJoinFeeTd").text() != ''){
														  	finalAmount = finalAmount + parseFloat($("#sumJoinFeeTd").text());
													    }
												    	$("#sumFinalAmountTd").html(finalAmount);
													 
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
						  
						  $("#sumPromoCodeTr").css("display","none");
						  $("#sumPromoCodeName").html("");
						  $("#sumPromoCodeVal").html("");
						  return;
					  }
				  },
				  error: function( xhr,status,error ){
					  //alert("1" +status);
					  console.log(xhr);
					 
				  }
			});
		}

		if (!found) {
			  $("#tcSuccessSpan").css("display", "none");		
			  $("#tcSuccessSpan" ).html("");	
			  $("#tcErrorSpan").css("display", "block");		
			  $( "#tcErrorSpan" ).html("The Promo code you enetered is invalid");
		}
	    
	});*/
	
	

	$('.note').on('click', function() {
	    $('.ttip').hide(500);
	    $("#background").fadeOut("slow");
	    $("#large").fadeOut("slow");

	});
		
	$("#phoneNumber").mask("(999) 999-9999");
	
    $("#dob").kendoDatePicker();
    $("#location").kendoDropDownList();    
    //$("#pricingOption").kendoDropDownList();
    //var pricingOption = $("#pricingOption").data("kendoDropDownList");
    //pricingOption.select(0);
    
    var locationDropdownlist = $("#location").data("kendoDropDownList");
    locationDropdownlist.select(0);
    locationDropdownlist.select(function(dataItem) {
	     return dataItem.value=== $('#signedUplocationId').val();
	 });
    getProductsByLocationId();
    //var prefLocationDropdownlist = $("#prefLocation").data("kendoDropDownList");
    //prefLocationDropdownlist.select(0);
   // $("#prefLocation").closest(".k-widget").hide();  
    
    
    var currentYear = new Date().getFullYear();
	currentYear = parseInt(currentYear.toString());
	for(var i=0; i<30 ; i++){
		$('#addCardExpirationYear').append($('<option>', {value: currentYear,text: currentYear}));
		currentYear = currentYear +1;
	}
	$("#addCardExpirationYear").kendoDropDownList();    
    $("#expirationMonth").kendoDropDownList();
          
    var today = kendo.date.today();
    var dateToday = new Date();    
    var d = new Date();
    d.setMonth(d.getMonth() + 2);
    //value: today,
    //max: d,
	//min: dateToday,
    var start = $("#start").kendoDatePicker({
    	format: "yyyy-MM-dd",   
    	max: d,
    	min: dateToday,
    	change: function (e) {
    		
    		var str = $("#start").val();    		
    		$("#progStartDateIdHidInput").attr("value", $("#start").val());    		
            if(str != null) {

                var parts = str.split("-");

                var year = parts[0] && parseInt( parts[0], 10 );
                var month = parts[1] && parseInt( parts[1], 10 );
                var day = parts[2] && parseInt( parts[2], 10 );
                var duration = 1;

                if( day <= 31 && day >= 1 && month <= 12 && month >= 1 ) {
                	//$("#paymentBillingcycleTr").css("display", "");
                	if(paymentFreq.text() != 'Annual'){
                		/*if(day%10 == 1){
                			$("#paymentBillingcycleTd").html(day + " st (Of each month)");
                		}else if(day%10 == 2){
                			$("#paymentBillingcycleTd").html(day + " nd (Of each month)");
                		}else if(day%10 == 3){
                			$("#paymentBillingcycleTd").html(day + " rd (Of each month)");
                		}else{
                			$("#paymentBillingcycleTd").html(day + " th (Of each month)");
                		}*/
                	}else{
                		$("#paymentBillingcycleTr").css("display", "none");
                		$("#paymentBillingcycleTd").html("");
                		/*if(day%10 == 1){
                			$("#paymentBillingcycleTd").html(day + "st (Of each year)");
                		}else if(day%10 == 2){
                			$("#paymentBillingcycleTd").html(day + "nd (Of each year)");
                		}else if(day%10 == 3){
                			$("#paymentBillingcycleTd").html(day + "rd (Of each year)");
                		}else{
                			$("#paymentBillingcycleTd").html(day + "th (Of each year)");
                		}*/
                	}               	
                    var expiryDate = new Date( year, month - 1, day );
                    expiryDate.setFullYear( expiryDate.getFullYear() + duration );

                    var day = ( '0' + expiryDate.getDate() ).slice( -2 );
                    var month = ( '0' + ( expiryDate.getMonth() + 1 ) ).slice( -2 );
                    var year = expiryDate.getFullYear();

                    $("#end").val(year + "-" +month + "-" + day);
                    $("#end").data('kendoDatePicker').value($("#end").val());

                } else {
                	$("#paymentBillingcycleTr").css("display", "none");
                	$("#paymentBillingcycleTd").html("");
                    alert("Error Occured while setting Membership End Date");
                }
            }
        },
    	parseFormats: ["MM/dd/yyyy", "dd/MM/yyyy"]
    });    
    
    var nextBillDateMonth = $("#nextBillDateMonth").val();
	var nextBillDateDay = $("#nextBillDateDay").val();
	var nextBillDateYear = $("#nextBillDateYear").val();
	nextBillDateMonth = parseInt(nextBillDateMonth) - 1;
	var formattedStatDate = nextBillDateYear + "-" + nextBillDateMonth + "-" + nextBillDateDay;
	
	$("#start").attr("value", formattedStatDate);
	console.log(formattedStatDate)
    var end = $("#end").kendoDatePicker({
    	format: "yyyy-MM-dd",
    	value: today,                
    	parseFormats: ["MM/dd/yyyy", "dd/MM/yyyy"]
    });
    
    
   
    /*$("#location").on('change',function(e){    	    	 
    	$('#grid').data('kendoGrid').dataSource.read({"locationId":$("#location").kendoDropDownList().val()});
    	$("#grid").data("kendoGrid").refresh();
    	var grid = $("#grid").data("kendoGrid");
    	var count = grid.dataSource.total();    	
    	if(count != null && count == 0){
    		 var tableDataHtml = '';
	    	tableDataHtml += '<tr role="row" data-uid="44b95541-b9d3-45df-8dc2-093934cabdbb"><td role="gridcell" colspan="3">No Product Information found for selected Location. Please select different Location.</td></tr>';
	    	$("#grid tbody").html(tableDataHtml); 
    	}	    
    	*/
    	/* if(locationDropdownlist.text() == 'All Branches' || locationDropdownlist.text() == 'Bay Area'){
    		if(locationDropdownlist.text() == 'All Branches'){
    			$("#prefLocation").html("");
    			$("#prefLocation").html($("#allLocations").html());
    			$("#prefLocation").kendoDropDownList();
    		}
    		if(locationDropdownlist.text() == 'Bay Area'){
    			$("#prefLocation").html("");
    			$("#prefLocation").html($("#bayAreaLocations").html());
    			$("#prefLocation").kendoDropDownList();
    		}	    		
    		$("#prefLocation").closest(".k-widget").show();    		 
    	} else{ 
    		var dropdownlist = $("#prefLocation").data("kendoDropDownList");
    		dropdownlist.select(0);
    		$("#prefLocation").closest(".k-widget").hide();    		
    	} 
    });*/    
    $(document).on('click', '.add-child-lnk', function(){
    	var count = secKidCount;
    	
    	$(document).find('.secondaryKidsInfoForm').each(function(i, obj) {				
    		count = count +1;				
		});    
    	var secondaryKidInfo = getSecondaryKidsInfoHtml(count);
    	$('#familyInfoFormTable').css("display" , "inline-table");
		$('#familyInfoFormTable').append(secondaryKidInfo);
		$(".stepContainer").height($(".stepContainer").find("div:not(:hidden)").height());
		$('#familyInfoFormTable').find(".dob-class").each(function() {		    
		    if(!$( this ).hasClass( "k-datepicker" )){
		    	$( this ).kendoDatePicker();
			}
		 });
		$('#familyInfoFormTable').find(".dob-class").css("width", "205px");	
		validatekid(count);
		$('#familyInfoFormTable').find('.selectsecondaryContactKid').last().kendoDropDownList();
		/*var dobYearForm = new Date().getFullYear();
		dobYearForm = parseInt(dobYearForm.toString());
    	for(var i=0; i<100 ; i++){
    		$('#familyInfoFormTable').find('.kidDobYearForm').last().append($('<option>', {value: dobYearForm,text: dobYearForm}));
    		dobYearForm = dobYearForm - 1;
    	}*/
		$('#familyInfoFormTable').find('.kidDobYearForm').last().kendoDropDownList();
		$('#familyInfoFormTable').find('.kidDobMonthForm').last().kendoDropDownList();
		$('#familyInfoFormTable').find('.kidDobDateForm').last().kendoDropDownList();
		var secChildYear = $('#familyInfoFormTable').find('.kidDobYearForm').last().data("kendoDropDownList");
		var secChildMonth = $('#familyInfoFormTable').find('.kidDobMonthForm').last().data("kendoDropDownList");
		var secChildDate = $('#familyInfoFormTable').find('.kidDobDateForm').last().data("kendoDropDownList");
		var dob = secChildMonth.value()+ "/" +secChildDate.value()+ "/" +secChildYear.value();
		$('#familyInfoFormTable').find(".dateOfBirthValidation").last().attr("value", dob);	
		//$('.dob-class').kendoDatePicker();
		//$("#wizard").smartWizard("fixHeight");
    });
    
    $(document).on('click', '.remove-child-lnk', function(){
    	$(this).parent().parent().parent().parent().parent().parent().remove();
    /* 
    	$(this).parent().parent().next().remove();
    	$(this).parent().parent().prev().remove();
    	$(this).parent().parent().remove(); */
    	$(".stepContainer").height($(".stepContainer").find("div:not(:hidden)").height());
		//$("#wizard").smartWizard("fixHeight");
    });
    $(document).on('click', '.paymentTypeSelectClass', function() {		
    	registerSelected = false;    	
	});
     
   // $('.dob-class').kendoDatePicker();
    $(document).on('click', '.product-register-div', function(){
    	if (!$(this).closest(".k-block-div").find( "input:radio[name=paymentTypeSelect]" ).is(":checked")){
	    	var kendoWindow = $("<div />").kendoWindow({
	    		title: "Error",
	    		resizable: false,
	    		modal: true,
	    		width:400
	    	});   	
	    	kendoWindow.data("kendoWindow")
	    	 .content($("#terms-conditions-PricingBox").html())
	    	 .center().open();
	    		
	    	kendoWindow
	    	.find(".confirm-pricing-select")
	    	.click(function() {
	    		if ($(this).hasClass("confirm-pricing-select")) {         		
	    			kendoWindow.data("kendoWindow").close();
	    		}
	    	})
	    	.end();
    	}else{
    		var productObj = $(this).closest(".k-block-div").find( "input[type='radio']:checked" ).parent().find(".itemDetailProduct-Id").html();
    		//console.log(productObj);
    		proceedToRegisterChangeMembership(this, productObj);
    	
    	}
    });
    	
    
    
    $('body').on( "click", "#selectFamilyMemberbtn", function() { 	 
   	 var kendoWindow = $("<div />").kendoWindow({
   			title: "Select family member",
   			resizable: false,
   			modal: true,
   			width:500
   		});
   	 kendoWindow.data("kendoWindow")
   	 .content($("#select-family-member-Box").html())
   	 .center().open();
   	 
   	if(selectedHeaderInfo == 'Adult'  || selectedHeaderInfo == 'Senior' || selectedHeaderInfo == 'Youth' || selectedHeaderInfo == 'Two Adults' || selectedHeaderInfo == 'Three Adults'){
  		 kendoWindow.find("#kidsSelectKendoBox").css("display", "none");
	 }else if(selectedHeaderInfo == 'One Adultw/ Kids' || selectedHeaderInfo == 'Two Adultsw/ Kids' || selectedHeaderInfo == 'Three Adultsw/ or w/o kids'){
		 kendoWindow.find("#kidsSelectKendoBox").css("display", "inline");
	 }else{
		 kendoWindow.find("#kidsSelectKendoBox").css("display", "none");
	 }

   	kendoWindow
   	.find(".confirm-family-member-select")
   	.click(function() {
   		if ($(this).hasClass("confirm-family-member-select")) {

   		//Initialize kids info as blank before processing
   		$(".firstKidInfoForm").find(".personFirstName").attr("value","");
   		$(".firstKidInfoForm").find(".personLastName").attr("value","");
   		$(".firstKidInfoForm").find("input.dateOfBirth").attr("value","");
   		var $radios = $(".firstKidInfoForm").find(".gender");
   		$radios.filter('[value=Male]').prop('checked', false);
   		$radios.filter('[value=Female]').prop('checked', false);
   		$(document).find('.secondaryKidsInfoForm').each(function(i, obj) {				
   			$(this).parent().parent().remove();		
   			$(".stepContainer").height($(".stepContainer").find("div:not(:hidden)").height());
   		});  

   		resetPrimaryMember();
   		resetSecondaryMember();
   		resetThirdMember();	   		    
   		populateAdultFunction(kendoWindow);
   		if(selectedHeaderInfo == 'One Adultw/ Kids' || selectedHeaderInfo == 'Two Adultsw/ Kids' || selectedHeaderInfo == 'Three Adultsw/ or w/o kids'){
   			//kendoWindow.find("#kidsInfoDivision").css("display", "inline");
   				addKidsInfoFunction(kendoWindow);	   				
   		}else{
   			//kendoWindow.find("#kidsInfoDivision").css("display", "none");
   		} 
   		kendoWindow.data("kendoWindow").close();
   		$("#becomeMemberForm").valid();	
   		}
   	})
   	.end();
   	kendoWindowSelect = kendoWindow;
   });	
    
    $("#location").on('change',function(e){  
	    if(locationDropdownlist.text() != '--Select Location--'){
	    	/* var locId = 0;
	    	if(pricingOption.text() == 'All Branches' || pricingOption.text() == 'Bay Area'){
	    		locId = $("#pricingOption").kendoDropDownList().val(); */
	    		/* if(locationDropdownlist.text() == 'All Branches'){
	    			$("#prefLocation").html("");
	    			$("#prefLocation").html($("#allLocations").html());
	    			$("#prefLocation").kendoDropDownList();
	    		}
	    		if(locationDropdownlist.text() == 'Bay Area'){
	    			$("#prefLocation").html("");
	    			$("#prefLocation").html($("#bayAreaLocations").html());
	    			$("#prefLocation").kendoDropDownList();
	    		} */	    		
	    		//$("#prefLocation").closest(".k-widget").show();    		 
	    	/* } else{ 
	    		locId = $("#location").kendoDropDownList().val();
    		} */
	    	getProductsByLocationId();
	    	
    			
    	}else if(locationDropdownlist.text() == '--Select Location--'){  
   			var kendoWindow = $("<div />").kendoWindow({
   	        	title: "Error",
   	        	resizable: false,
   	        	modal: true,
   	        	width:400
   	        });   	
     		kendoWindow.data("kendoWindow")
   	         .content($("#terms-conditions-LocationBox").html())
   	         .center().open();
     			
   	        kendoWindow
   	        .find(".confirm-location-select")
   	        .click(function() {
   	        	if ($(this).hasClass("confirm-location-select")) {         		
   	        		kendoWindow.data("kendoWindow").close();
   	        	}
   	        })
   	        .end();
   			//e.preventDefault();
   	     	//pricingOption.select(0);
    	}else{
    		var kendoWindow = $("<div />").kendoWindow({
   	        	title: "Error",
   	        	resizable: false,
   	        	modal: true,
   	        	width:400
   	        });   	
     		kendoWindow.data("kendoWindow")
   	         .content($("#terms-conditions-PricingBox").html())
   	         .center().open();
     			
   	        kendoWindow
   	        .find(".confirm-pricing-select")
   	        .click(function() {
   	        	if ($(this).hasClass("confirm-pricing-select")) {         		
   	        		kendoWindow.data("kendoWindow").close();
   	        	}
   	        })
   	        .end();
   	     
    	}
    });
    
    /* $("#prefLocation").on('change',function(e){
    	//getProductDetails($(this).val());
    }); */    
    
    
	$('#wizard').smartWizard({
		// Properties
		selected: 0,  // Selected Step, 0 = first step   
		keyNavigation: false, // Enable/Disable key navigation(left and right keys are used if enabled)
		enableAllSteps: false,  // Enable/Disable all steps on first load
		transitionEffect: 'slideleft', // Effect on navigation, none/fade/slide/slideleft
		contentURL:null, // specifying content url enables ajax content loading
		contentURLData:null, // override ajax query parameters
		contentCache:true, // cache step contents, if false content is fetched always from ajax url
		cycleSteps: false, // cycle step navigation
		enableFinishButton: false, // makes finish button enabled always
		hideButtonsOnDisabled: true, // when the previous/next/finish buttons are disabled, hide them instead
		errorSteps:[],    // array of step numbers to highlighting as error steps
		labelNext:'Next', // label for Next button
		labelPrevious:'Previous', // label for Previous button
		labelFinish:'Place Order',  // label for Finish button        
		noForwardJumping:false,
		ajaxType: 'POST',
		// Events
		onLeaveStep: nextClick, // triggers when leaving a step
		onShowStep: onShowStep,  // triggers when showing a step
		onFinish: registerContact,  // triggers when Finish button is clicked
		includeFinishButton : true   // Add the finish button
	});
	
	var validator2 = $("#tcForm").validate({
		debug: true,
		errorElement: "em",			
		errorPlacement: function(error, element) {			
			element.addClass("inputErrorr");
			error.appendTo(element.parent("td").next("td"));
			element.parent("td").next("td").addClass("textMsgError");			
		},
		success: function(label, element) {			
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
			"tcname": {
				required: true,
				minlength: 2,
				maxlength: 40,
				check_fName_lName : true				
			}
		},
		messages: {
			"tcname": {				
				required: "Please enter your Full Name",
				minlength: "Full Name must consist of at least 2 characters",
				maxlength: "Full Name can not be more than 40 characters",
				check_fName_lName : "Please enter the primary contact's First Name and Last Name."
			}
		}
	});	
	
	$.validator.addMethod("check_fName_lName", function(value, element) {
		var firstName = $("#primaryContactFName").attr("value");
		var lastName = $("#primaryContactLName").attr("value");
		//var firstName = "";
		//var lastName = "";
		var dobMonth = $("#youthDobMonthForm").val();
		var dobDate = $("#youthDobDateForm").val();
		var dobYear = $("#youthDobYearForm").val();
		
		var age =  $("#adultAgeValidationLowerLimit").html();
		//var kidAge =  $("#kidsAgeValidation").html();
		var mydate = new Date();
		mydate.setFullYear(dobYear, dobMonth-1, dobDate);
		
	    var currdate = new Date();
	    currdate.setFullYear(currdate.getFullYear() - age);
	    
		/*if(selectedHeaderInfo == 'Youth' && currdate > mydate){
			firstName = $("#secFirstName").val().trim();
			lastName = $("#secLastName").val().trim();			
		}else{
			firstName = $("#firstName").val().trim();
			lastName = $("#lastName").val().trim();			
		}*/
		var fullName = firstName.trim() + " " + lastName.trim();
		var tcname = $("#tcname").val().trim();
		if(tcname != fullName){
			return false;
		} else{
			return true;
		}
		
	}, "Please enter the primary contact's First Name and Last Name.");
	
	$.validator.addMethod("check_duplicate_email", function(value, element) {			
	    var inputEmailValue = value;	   
	    $element = $(element);
	    /*if($element.attr("id") == "email") && && $("#email" ).val() == ""){
			return false;
		}else */if($element.attr("id") == "secEmail" && $("#secEmail" ).val() == ""){
			return true;
		}else if($element.attr("id") == "thirdEmail" && $( "#thirdEmail" ).val() == ""){
			return true;
		}else if(($element.attr("id") == "email") && (inputEmailValue == $( "#secEmail" ).val() || inputEmailValue == $( "#thirdEmail" ).val())){
	    	return false;
		}else if(($element.attr("id") == "secEmail") && (inputEmailValue == $( "#email" ).val() || inputEmailValue == $( "#thirdEmail" ).val())){
	    	return false;
		}else if(($element.attr("id") == "thirdEmail") && (inputEmailValue == $( "#email" ).val() || inputEmailValue == $( "#secEmail" ).val())){
	    	return false;
		}else{    		  
			return true;
		}
	}, "Please enter unique email");
	
	$.validator.addMethod("validate_email_format", function(value, element) {	
		var re = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
		var flag = false;
		if(value){			
			flag = re.test(value);
		}else {
			flag = true;
		}
		//console.log(flag);
		return flag;
	}, "Please enter valid email address");
	
	
	
	
	
	var url = "isEmailExists";
	//var urlPrimary = "isPrimaryUserEmail";
	var validator1 = $("#becomeMemberForm").validate({
		debug: true,
		errorElement: "em",			
		errorPlacement: function(error, element) {
			$element = $(element);			
			//console.log($element.hasClass("dateOfBirth"));
			if($element.attr("id") == "dob" || $element.attr("id") == "secDOB" || $element.attr("id") == "thirdDOB" || $element.hasClass("dateOfBirthValidation")){
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
			"userLst[0].firstName": {
				required: true,
				minlength: 2,
				maxlength: 20
			},
			"userLst[0].lastName": {
				required: true,
				minlength: 2,
				maxlength: 20
			},
			"userLst[0].emailAddress" : {
				required: true,
				email: true/*,				
                 remote: {
                     url: url,  
                     type:"GET"                    	
                  }*/
			},
			"userLst[0].password": {
				required: true,
				minlength: 5
			},
			"userLst[0].confirmPassword": {
				required: true,
				minlength: 5,
				equalTo: "#password"
			},		
			"userLst[1].password": {
				required: true,
				minlength: 5
			},
			"userLst[1].confirmPassword": {
				required: true,
				minlength: 5,
				equalTo: "#youthPassword"
			},				
			"userLst[0].referrerEmail" : {				
				email: true				
			},
			"userLst[0].formattedPhoneNumber" : "required",
			"userLst[0].gender" : "required",
			"addressLine1" : "required",			
			"city" : "required",
			"postalCode" : {
				required: true,
				number: true,
				minlength: 5
			},
			"state" : "required"
			
		},
		messages: {
			"userLst[0].firstName": {				
				required: "Please enter your First Name",
				minlength: "First Name must consist of at least 2 characters"
			},
			
			"userLst[0].lastName": {				
				required: "Please enter your Last Name",
				minlength: "Last Name must consist of at least 2 characters"
			},
			"userLst[0].emailAddress" : {
				required: "Please enter your email address",
				email : "Please enter valid email address"/*,				
				remote: "You appear to be associated with a customer account already.  Please speak with the primary contact for that account"
				 ,
				check_Email_valid : "You appear to be associated with a customer account already.  Please speak with the primary contact for that account" */
			},
			"userLst[0].formattedPhoneNumber" : "Please enter your Phone Number",			
			"userLst[0].password": {
				required: "Please provide a password",
				minlength: "Your password must be at least 5 characters long"
			},
			"userLst[0].confirmPassword": {
				required: "Please provide a password",
				minlength: "Your password must be at least 5 characters long",
				equalTo: "Please enter the same password as above"
			},
			"userLst[1].password": {
				required: "Please provide a password",
				minlength: "Your password must be at least 5 characters long"
			},
			"userLst[1].confirmPassword": {
				required: "Please provide a password",
				minlength: "Your password must be at least 5 characters long",
				equalTo: "Please enter the same password as above"
			},		
			"userLst[0].gender" : "Please select gender",
			"userLst[0].referrerEmail" : {				
				email: "Please enter correct email address"				
			},
			
			"postalCode" : {
				required: "Please enter Postal Code",
				number: "Please enter numbers only",
				minlength: "Postal Code must consist of at least 5 characters"
			},
			"addressLine1" : "Please enter address line 1",			
			"city" : "Please enter yout City",
			"state" : "Please enter your State",
			agree: "Please accept our policy"
		}
	});	
	
	$("#addCardInfoForm").validate({
		rules: {
			fullName:"required",
			cardNum: {
				required: true,
				creditcard: true
			},
			securityCode: {
				required: true,
				digits: true,
				minlength: 3,
				maxlength: 4
			},
			billingAddressLine1:"required",
			billingCity:"required",
			billingState:"required",
			billingZip:"required",
			bankAccountNumber:"required",
			bankRoutingNumber:"required",
			checkNumber:"required",
			bankAccountName:"required"
		},
		messages: {
			fullName: "Please provide your full name",
			cardNum: {
				required: "Please provide Card Number",
				creditcard: "Please provide Valid Card Number"
			},
			securityCode: {
				required: "Please provide Security code",
				digits: "Please enter only Numeric value",
				minlength: "Security code must be at least {0} digits",
				maxlength: "Security code must be at most {0} digits"
			},
			billingAddressLine1:"Please provide Billing Address",
			billingCity:"Please provide Billing City",
			billingState:"Please provide Billing State",
			billingZip:"Please provide Billing Zip",
			bankAccountNumber:"Please provide Bank Account Number",
			bankRoutingNumber:"Please provide Bank Routing Number",
			checkNumber:"Please provide Check Number",
			bankAccountName:"Please provide Bank Account Name"
		},
		errorPlacement: function(error, element){
			//alert();
			//console.log(element);
			//console.log(error);
			for(j=1; j<=8; j++){
				if(element[0].tabIndex==j){
					//alert(j);
					//alert($( "#tcErrorSpan" ).html());
					 if($( "#tcPaymentErrorSpan" ).html()==""){
						 $("#tcPaymentErrorSpan").css("display", "block");		
						 $( "#tcPaymentErrorSpan" ).html(error);
						 element[0].focus();
					 }
					 break;
				}
			}
			
		},
		success: function(label, element) {
			$("#tcPaymentErrorSpan").css("display", "none");		
			$( "#tcPaymentErrorSpan" ).html("");
			$("#tcPaymentSuccessSpan").css("display", "none");		
			$( "#tcPaymentSuccessSpan" ).html("");
		}
	});
	//"userLst[0].dateOfBirth" : "required",
	//"userLst[0].dateOfBirth" : "Please enter your Date of Birth",
	/*$.validator.addMethod("check_date_of_birth", function(value, element) {		
	    
	    //11/18/2014
	    var inpDate = value;
	    var day;
    	var month;
    	var year;
    	var age =  $("#adultAgeValidation").html();
	    if(inpDate != null){
	    	var dateArr = inpDate.split("/");	    	
	    	month = dateArr[0];
	    	day = dateArr[1];
	    	year = dateArr[2];
	    }

	    var mydate = new Date();
	    mydate.setFullYear(year, month-1, day);

	    var currdate = new Date();
	    currdate.setFullYear(currdate.getFullYear() - age);

	    return currdate >= mydate;

	}, "You must be at least "+ $("#adultAgeValidation").html() +"years of age, or choose another membership product");*/
	
	$.validator.addMethod("check_date_of_birth", function(value, element) {
		/* var day = $("#dob_day").val();
	    var month = $("#dob_month").val();
	    var year = $("#dob_year").val(); */
	    //11/18/2014
	    var inpDate = value;
	    var day;
    	var month;
    	var year;
    	
    	var adultLowerAgeLimit =  $("#adultAgeValidationLowerLimit").html();
    	var adultUpperAgeLimit =  $("#adultAgeValidationUpperLimit").html();
    	
	    if(inpDate != null){
	    	var dateArr = inpDate.split("/");	    	
	    	month = dateArr[0];
	    	day = dateArr[1];
	    	year = dateArr[2];
	    }
	    var validation  =  false;

	    var adultLowerAge = new Date();
	    adultLowerAge.setFullYear(adultLowerAge.getFullYear() - adultLowerAgeLimit);
	    
	    var adultUpperAge = new Date();
	    adultUpperAge.setFullYear(adultUpperAge.getFullYear() - adultUpperAgeLimit);
	    	
	    	
	    var mydate = new Date();
	    mydate.setFullYear(year, month-1, day);

	    //var currdate = new Date();
	    //currdate.setFullYear(currdate.getFullYear() - age);
	    var upperFlag = false;
	    if(adultUpperAge < mydate){
	    	upperFlag = true;
	    }
	    var lowerFlag = false;
	    if(adultLowerAge > mydate){
	    	lowerFlag = true;
	    }
	    if(upperFlag == true && lowerFlag == true){
	    	return true;
	    }else {
	    	return false;
	    }
	    
	}, "You must be between "+ $("#adultAgeValidationLowerLimit").html() +" to "+ $("#adultAgeValidationUpperLimit").html() +" years of age");
	
	
	$.validator.addMethod("check_date_of_birth_senior", function(value, element) {		    
	    //11/18/2014
	    var inpDate = value;
	    var day;
	    var month;
	    var year;
	    var age =  $("#seniorAgeValidationLimit").html();
	    if(inpDate != null){
	    	var dateArr = inpDate.split("/");	    	
	    	month = dateArr[0];
	    	day = dateArr[1];
	    	year = dateArr[2];
	    }

	    var mydate = new Date();
	    mydate.setFullYear(year, month-1, day);

	    var currdate = new Date();
	    currdate.setFullYear(currdate.getFullYear() - age);

	    return currdate >= mydate;

	}, "You must be at least "+ $("#seniorAgeValidationLimit").html() +" years of age, or choose another membership product");
	
	$.validator.addMethod("check_date_of_birth_kid", function(value, element) {

	    /* var day = $("#dob_day").val();
	    var month = $("#dob_month").val();
	    var year = $("#dob_year").val(); */
	    //11/18/2014
	    var inpDate = value;
	    var day;
    	var month;
    	var year;
    	var age =  $("#kidsAgeValidation").html();
	    if(inpDate != null){
	    	var dateArr = inpDate.split("/");	    	
	    	month = dateArr[0];
	    	day = dateArr[1];
	    	year = dateArr[2];
	    }

	    var mydate = new Date();
	    mydate.setFullYear(year, month-1, day);

	    var currdate = new Date();
	    currdate.setFullYear(currdate.getFullYear() - age);

	    return currdate <= mydate;

	}, "You must be less than "+ $("#kidsAgeValidation").html() +"years of age, or choose another membership product");
	
	$.validator.addMethod("check_date_of_birth_youth", function(value, element) {

	    /* var day = $("#dob_day").val();
	    var month = $("#dob_month").val();
	    var year = $("#dob_year").val(); */
	    //11/18/2014
	    var inpDate = value;
	    var day;
    	var month;
    	var year;
    	var youthLowerAgeLimit =  $("#youthAgeValidationLowerLimit").html();
    	var youthUpperAgeLimit =  $("#youthAgeValidationUpperLimit").html();
    	
	    if(inpDate != null){
	    	var dateArr = inpDate.split("/");	    	
	    	month = dateArr[0];
	    	day = dateArr[1];
	    	year = dateArr[2];
	    }
	    var validation  =  false;

	    var youthLowerAge = new Date();
	    youthLowerAge.setFullYear(youthLowerAge.getFullYear() - youthLowerAgeLimit);
	    
	    var youthUpperAge = new Date();
	    youthUpperAge.setFullYear(youthUpperAge.getFullYear() - youthUpperAgeLimit);
	    	
	    	
	    var mydate = new Date();
	    mydate.setFullYear(year, month-1, day);

	    //var currdate = new Date();
	    //currdate.setFullYear(currdate.getFullYear() - age);
	    var upperFlag = false;
	    if(youthUpperAge < mydate){
	    	upperFlag = true;
	    }
	    var lowerFlag = false;
	    if(youthLowerAge > mydate){
	    	lowerFlag = true;
	    }
	    if(upperFlag == true && lowerFlag == true){
	    	return true;
	    }else {
	    	return false;
	    }

	}, "You must be between "+ $("#youthAgeValidationLowerLimit").html() +"to "+ $("#youthAgeValidationUpperLimit").html() +"years of age, or choose another membership product");
	
	
	/* function resizeGrid(d) {
	    var gridElement = $("#grid");
	    var dataArea = gridElement.find(".k-grid-content");
	    var newHeight = gridElement.parent().innerHeight() - 2;
	    var diff = gridElement.innerHeight() - dataArea.innerHeight();
	    gridElement.height(newHeight);
	    dataArea.height(newHeight - diff);
	    return d;
	}
	
	$(window).resize(function(){
	    resizeGrid();
	}); */
	
	var validator3 = $("#loginForm").validate({
		debug: true,
		errorElement: "em",			
		errorPlacement: function(error, element) {
			$element = $(element);	
			element.addClass("inputErrorr");
			error.appendTo(element.parent("td").next("td"));
			element.parent("td").next("td").addClass("textMsgError");
			$element.parent("td").next("td").css("color","red");
		},
		success: function(label, element) {			
			var $element = $(element);
			var $label = $(label);
	       	$element.removeClass("inputErrorr");
			$element.removeClass("error");
			$element.addClass("success");
			
			$label.removeClass("textMsgError");
			$label.removeClass("error");
			$label.addClass("success");			
		},
		rules: {			
			"username" : {
				required: true,
				email: true				
			},
			"password": {
				required: true,
				minlength: 5
			}			
		},
		messages: {			
			"username" : {
				required: "Please enter your email address",
				email : "Please enter valid email address"			
			},
						
			"password": {
				required: "Please provide a password",
				minlength: "Your password must be at least 5 characters long"
			}
		}
	});	

	
	var location = $("#location").kendoDropDownList().val();
	/* $("#grid").kendoGrid({
		dataSource: GetRemoteData($("#location").kendoDropDownList().val()),
        height: 270,*/
        /* filterable: true, */
        /*sortable: true,
        pageable: true,*/
        /* dataBound: resizeGrid, */
       /* columns: [
			{ 			
				template: "<input type='radio' name='termsAndCondition' class='termsAndCondition' />", 
				width: "3%"
			},      
			{ field: "id", title: "ID",width: "0%", hidden: true }, 
			{ field: "productName", title: "Name",width: "30%" },
			{ field: "productDescription", title: "Description",width: "53%" },
			{ field: "productType", title: "Product Type",width: "0%", hidden: true }, 
			{ field: "productDuration", title: "Product Duration",width: "0%", hidden: true }, 
			{ field: "productPrice", title: "Price", hidden: true },
			{ field: "tandc", title: "T & C", hidden: true },
			{ field: "pricingJsonArray", title: "Pricing Breakup", hidden: true },
			{ field: "joiningPrice", title: "Price", hidden: true },
			{ field: "tierPrice", title: "Price",width: "14%" }
	        
    	]        
    }); */
	
	 $('body').on( "click", "input.termsAndCondition", function() { 	 
         if($('input:radio[name=type]:checked')){        	
         	var $termsVar = $(this).parent().parent().find('td');
         	$termsVar.each(function(index){
         		if(index && index == '1'){
         			$("#productIdTd").html($(this).html());
         			//$("#productIdHidInput").attr("value", $(this).html());
         		}
				if(index && index == '2'){
					$("#productNameTd").html($(this).html());     			
				}
				if(index && index == '3'){
					$("#productDescriptionTd").html($(this).html());	
				}
				if(index && index == '4'){
					$("#productTypeTd").html($(this).html());	
				}
				if(index && index == '5'){
					$("#productDurationTd").html($(this).html());	
				}
				if(index && index == '6'){
					$("#productPriceTd").html("$ "+$(this).html());
					$("#paymentAmountSpan").html($(this).html());
					$("#paymentTotalPriceTd").html("$ "+$(this).html());	
					$("#sumTotalPriceTd").html("$ "+$(this).html());
				}				
				if(index && index == '7'){				
					$(".termsDiv").html($(this).text());
					$("#userTC").attr("value", $(this).text());
				}
				if(index && index == '9'){				
					$("#paymentJoinFeeTd").html("$ "+$(this).text());
					$("#sumJoinFeeTd").html("$ "+$(this).text());					
				}
				if(index && index == '10'){				
					$("#paymentTierTd").html("$ "+$(this).text());
					$("#sumTierPriceTd").html("$ "+$(this).text());	
				}
				if(index && index == '8'){				
					var priceRuleStr = $(this).text();
					if(priceRuleStr != null){
						var priceRuleArr= priceRuleStr.split(";;");
						for(var i = 0; i<priceRuleArr.length; i++){
							if(priceRuleArr[i] != null && priceRuleArr[i] != ""){
								var ruleArray = priceRuleArr[i].split(":");
								var ruleName = ruleArray[0];
								var rulePrice = ruleArray[1];	
								if(ruleName == 'Join Fee'){
									$("#paymentJoinFeeTd").html("$ "+rulePrice);
									$("#sumJoinFeeTd").html("$ "+rulePrice);									
								}
								if(ruleName == 'Tier 1' || ruleName == 'Tier 2' || ruleName == 'Tier 3'){
									$("#paymentTierTd").html("$ "+rulePrice);
									$("#sumTierPriceTd").html("$ "+rulePrice);									
								}						
							}
						}
					}
				}
				
         	    //console.log($(this).html())
         	});
         }
	 });
	 
	 /*$('body').on( "click", "#memberSignInSpan", function() { 	 
		 signInSelected = "true";
		 $('#loginDiv').css("display","inline");
		 $('#registerDiv').css("display","none");
	 });*/
	 
	 $('body').on( "click", "#newMemberSpan", function() { 	 
		 signInSelected = "false";
		 $('#loginDiv').css("display","none");
		 $('#registerDiv').css("display","inline");
	 });
	
  	
	$("#wizard"). smartWizard("fixHeight");
      
}
	
function GetRemoteData(location) {	
   var  source = new kendo.data.DataSource({
        autoSync: true,
        transport: {
            read: {
                type: "GET",
                url: "getProductDetailsByLocation",
                data: {"locationId" : $("#location").kendoDropDownList().val()},
                dataType: "json",                
                cache: false
            }
            
        },
        schema: {
        	 model: {
	        	fields: {
	        		radioButton : {},
	            	id: { type: "string" }, 
	            	productName: { type: "string" },
	            	productDescription: { type: "string" },
	            	productType: { type: "string" },
	            	productDuration: { type: "string" }, 
	            	productPrice: { type: "string" },
	            	tandc : { type: "string" },
	            	pricingJsonArray : { type: "string" },
	            	joiningPrice : { type: "string" },
	            	tierPrice: { type: "string" }
	            	
	            }
        	 }
       
    	},
        
        pageSize: 12
    });
    source.fetch(function () {
        var data = this.data();
    });
    return source;
}

function registerContact(){	
	if(!$("input[name='chkTermsAndCond']:checked").val() && !isLoggedInUserAgent()){ 
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
	        $('#wizard').smartWizard('setError',{stepnum:2,iserror:true});
			$('#wizard').smartWizard('showMessage','Please agree the terms and conditions before Proceeding');			
 			return false;
 	}else{	
 		$("#finalAmountInput").attr("value", $("#sumFinalAmountTd").text());
 		$("#faAmountInput").attr("value", $("#faAmountTD").text());
 		$("#signupPriceInput").attr("value", $("#sumTierPriceTd").text());
 		//$("#setUpFeeInput").attr("value", $("#").text());
 		$("#registrationFeeInput").attr("value", $("#sumRegistrationFee").text());
 		$("#depositInput").attr("value", $("#sumDepositAmount").text());
 		$("#joinFeeAmt").attr("value", $("#sumJoinFeeTd").text());
 		$("#membershipStartDateInput").attr("value", $("#start").val());
 		
 		
 		
 		$('#wizard').smartWizard('setError',{stepnum:2,iserror:false});
		$('#wizard').smartWizard('hideMessage');		
		$("#progFinalAmtIdHidInput").attr("value", $("#paymentTotalPriceTd").text());	
		$("#progStartDateIdHidInput").attr("value", $("#start").val());
		var selectedPaymentMethod = $("#paymentInfoRadio").data("kendoDropDownList").text();
		if($("#paymentInfoRadio").val()!='New' && $("#paymentInfoRadio").val()!='NewBank'){
			
			var paymentDDvalue = $('#paymentInfoRadio').val();
			var paymentDDValueSplit = paymentDDvalue.split("__");			
			var paymentMethodType = paymentDDValueSplit[0];
			var paymentMethodToken = paymentDDValueSplit[1];
			var paymentMethodId = paymentDDValueSplit[2];
			
			if (paymentMethodType && paymentMethodType == 'CREDIT') {	
				if(parseFloat($("#sumFinalAmountTd").text()) > 0){
					$(".k-loading-mask").show();
					$(".k-loading-text").html("Please wait");
					$("#statusBlock-payment").css("display", "none");	
					$("#tcloginErrorSpan-payment").css("display", "none");	
					$( "#tcloginErrorSpan-payment" ).html("");
					
					$.ajax({
						  type: "POST",
						  url: "processPaymentByTokenId",	
						  data: { token: paymentMethodToken, totalAmount : $("#sumFinalAmountTd").text()},
						  success: function( data ) {						  
							  var paymentMethodHtml = '';
							  if(data != null && data.responseText == "APPROVED"){	
								  $('#wizard').smartWizard('setError',{stepnum:4,iserror:false});
								  $("#paymentTransId").attr("value", data.transId); 
								  
								  var joinFee = $('#sumJoinFeeTd').text();
								  var sumTierPriceTd = $('#sumTierPriceTd').text();
								  
								 
							      if(joinFee != null){
							    		$("#prodJoiningFeeInput").attr("value", joinFee);
							      }else{
							    		$("#prodJoiningFeeInput").attr("value", "0");  
							      }
								    	  
							      if(sumTierPriceTd != null){
							    		$("#prodPriceInput").attr("value", sumTierPriceTd);    
							      }else{
							    		$("#prodPriceInput").attr("value", "0");     
							      } 						  
								    $("#statusBlock-payment").css("display", "none");	
								    $("#tcloginErrorSpan-payment").css("display", "none");	
								    $( "#tcloginErrorSpan-payment" ).html("");
								  
								  document.forms["becomeMemberForm"].submit();
							      //alert(data.responseText);	
							      //alert(data.transId);
							      //alert(data.actCode);
							  }else {
								  $('#wizard').smartWizard('goToStep',4);
								  $('#wizard').smartWizard('setError',{stepnum:4,iserror:true});
								  /* var errorMsg = '';
								  //if(data != null && data.responseText == "UNKNOWN TOKEN"){					  
									//  errorMsg = "ActionCode&nbsp;:&nbsp;<b>"+data.actCode+"</b>&nbsp;Error message&nbsp;:&nbsp;"+data.responseText;
								  //}else if(data != null && data.responseText == "INVALID MESSAGE TYPE"){
									//  errorMsg = "";
								  //}else if(data != null && data.responseText == "DECLINED"){
									//  errorMsg = "";
								  //}else{
									 // errorMsg = "";
								  //} 
								  
								  errorMsg += "Error occurred while processing Payment.<br />";
								  errorMsg += "ActionCode :<b>"+data.actCode+"</b> Error message : "+data.responseText;						  
								  $("p.payment-Error-Box-msg-p").html(errorMsg);						  
								  var kendoWindow = $("<div />").kendoWindow({
							        	title: "Error",
							        	resizable: false,
							        	modal: true,
							        	width:400
							        });
							
						  			kendoWindow.data("kendoWindow")
							         .content($("#payment-Error-Box").html())
							         .center().open();
						  			
							        kendoWindow
							        .find(".confirm-payment-process")
							        .click(function() {
							        	if ($(this).hasClass("confirm-payment-process")) {  
							        		$("#payment-Error-Box").css("display", "none");
							        		kendoWindow.data("kendoWindow").close();
							        	}
							        })
							        .end();	 */		
							        
								  $("#statusBlock-payment").css("display", "block");	
							        $("#tcloginErrorSpan-payment").css("display", "block");	
							        $( "#tcloginErrorSpan-payment" ).html("Error occurred while processing the payment. Please try again later <br />ActionCode :<b>"+data.actCode+"</b> Error message : "+data.responseText);
							  }
							  $(".k-loading-mask").hide();
						  },
						  error: function( xhr,status,error ){
							  $("#statusBlock-payment").css("display", "block");	
							  $("#tcloginErrorSpan-payment").css("display", "block");	
							  $( "#tcloginErrorSpan-payment" ).html("There was some error. Please try again later");							  		 
						  }
					});
				}else{
					var joinFee = $('#sumJoinFeeTd').text();
					  var sumTierPriceTd = $('#sumTierPriceTd').text();
					  
					  $("#paymentTransId").attr("value", paymentOrderId); 
					  if(joinFee != null){
							$("#prodJoiningFeeInput").attr("value", joinFee);
					  }else{
							$("#prodJoiningFeeInput").attr("value", "0");  
					  }
							  
					  if(sumTierPriceTd != null){
							$("#prodPriceInput").attr("value", sumTierPriceTd);    
					  }else{
							$("#prodPriceInput").attr("value", "0");     
					  }

						$("#statusBlock-payment").css("display", "none");	
						$("#tcloginErrorSpan-payment").css("display", "none");	
						$( "#tcloginErrorSpan-payment" ).html("");
					document.forms["becomeMemberForm"].submit();				
				}
			}else if(selectedPaymentMethod.indexOf(",") > -1) {
				
				$(".k-loading-mask").show();
				$(".k-loading-text").html("Please wait");
				$("#statusBlock-payment").css("display", "none");	
				$("#tcloginErrorSpan-payment").css("display", "none");	
				$( "#tcloginErrorSpan-payment" ).html("");
				
				var amount =  $("#sumFinalAmountTd").text();				
				if(selectedPaymentMethod != null) {
					var selectedPaymentMethodArr = selectedPaymentMethod.split(", ");
					var checkNumber = selectedPaymentMethodArr[1];
					var cardName = selectedPaymentMethodArr[0];
					
					$.ajax({
						  type: "POST",
						  url: "processACHPaymentByTokenId",	
						  data: { token: $('#paymentInfoRadio').val(), totalAmount : $("#sumFinalAmountTd").text(), checkNumber : checkNumber, cardName : cardName},
						  success: function( data ) {				 
							  if(data != null && data.responseText == "CHECK ACCEPTED"){
								  $('#wizard').smartWizard('setError',{stepnum:4,iserror:false});
								  $("#paymentTransId").attr("value", data.transId); 
								  
								  var joinFee = $('#sumJoinFeeTd').text();
								  var sumTierPriceTd = $('#sumTierPriceTd').text();
								  
								 
							      if(joinFee != null){
							    		$("#prodJoiningFeeInput").attr("value", joinFee);
							      }else{
							    		$("#prodJoiningFeeInput").attr("value", "0");  
							      }
								    	  
							      if(sumTierPriceTd != null){
							    		$("#prodPriceInput").attr("value", sumTierPriceTd);    
							      }else{
							    		$("#prodPriceInput").attr("value", "0");     
							      } 						  
								    $("#statusBlock-payment").css("display", "none");	
								    $("#tcloginErrorSpan-payment").css("display", "none");	
								    $( "#tcloginErrorSpan-payment" ).html("");
								  
								  document.forms["becomeMemberForm"].submit();	
									  
								  }else {
									  $('#wizard').smartWizard('goToStep',4);
									  $('#wizard').smartWizard('setError',{stepnum:4,iserror:true});								  			        
									  $("#statusBlock-payment").css("display", "block");	
								        $("#tcloginErrorSpan-payment").css("display", "block");	
								        $( "#tcloginErrorSpan-payment" ).html("Error occurred while processing the payment. Please try again later <br />ActionCode :<b>"+data.actCode+"</b> Error message : "+data.responseText);
								  }
								  $(".k-loading-mask").hide();
						  },
						  error: function( xhr,status,error ){
							  $("#statusBlock-payment").css("display", "block");	
							  $("#tcloginErrorSpan-payment").css("display", "block");	
							  $( "#tcloginErrorSpan-payment" ).html("There was some error. Please try again later");							  		 
						  }
					});
				}else{
					alert("Please select the Payment Method");
				}
				
			}
		}else if($("#paymentInfoRadio").val()=='NewBank'){	
			$(".k-loading-mask").show();
			$(".k-loading-text").html("Please wait");
			$("#statusBlock-payment").css("display", "none");	
			$("#tcloginErrorSpan-payment").css("display", "none");	
			$( "#tcloginErrorSpan-payment" ).html("");
			
			var bankAccountNumber =  $("#bankAccountNumber").val();
			var bankRoutingNumber =  $("#bankRoutingNumber").val();
			var checkNumber =  $("#checkNumber").val();
			var cardName =  $("#cardName").val();
			var totalAmt =  $("#sumFinalAmountTd").text();
			
			var billingAddressLine1 =  $("#achbillingAddressLine1").val();
			var billingAddressLine2 =  $("#achbillingAddressLine2").val();
			var billingCity =  $("#achbillingCity").val();
			var billingState =  $("#achbillingState").val();
			var billingZip =  $("#achbillingZip").text();
			
			$.ajax({
				  type: "POST",
				  url: "processACHPayment",	
				  data: { bankAccountNumber: bankAccountNumber, bankRoutingNumber : bankRoutingNumber, checkNumber : checkNumber, cardName : cardName, totalAmt : totalAmt},
				  success: function( data ) {
					  
					  var paymentMethodHtml = '';
					  if(data != null && data.responseText == "CHECK ACCEPTED"){
						  
						  $.ajax({
								type: "POST",
								url: "insertPaymentDetailsForAchTransaction",	
								data: { bankAccountNumber: bankAccountNumber, bankRoutingNumber : bankRoutingNumber, checkNumber : checkNumber , cardName : cardName,
									 totalAmt : totalAmt , transId : data.transId, responseText :  data.responseText, actCode :  data.actCode, apprCode :  data.apprCode, 
									 billingAddress1 :  billingAddressLine1, billingAddress2 :  billingAddressLine2, billingCity :  billingCity, billingState :  billingState, billingZip :  billingZip,
								},
								success: function( dat ) {
									$('#wizard').smartWizard('setError',{stepnum:4,iserror:false});
									  $("#paymentTransId").attr("value", data.transId); 
									  
									  var joinFee = $('#sumJoinFeeTd').text();
									  var sumTierPriceTd = $('#sumTierPriceTd').text();
									  
							    	  if(joinFee){
							    		  $("#prodJoiningFeeInput").attr("value", joinFee);
							    	  }else{
							    		  $("#prodJoiningFeeInput").attr("value", "0");  
							    	  }						    	  
								    	if(sumTierPriceTd){
								    		$("#prodPriceInput").attr("value", sumTierPriceTd);    
								    	}else{
								    		$("#prodPriceInput").attr("value", "0");     
								    	}
									    $("#statusBlock-payment").css("display", "none");	
									    $("#tcloginErrorSpan-payment").css("display", "none");	
									    $( "#tcloginErrorSpan-payment" ).html("");
									  
									  document.forms["becomeMemberForm"].submit();
								},
								error: function( xhr,status,error ){
								  $("#statusBlock-payment").css("display", "block");	
								  $("#tcloginErrorSpan-payment").css("display", "block");	
								  $( "#tcloginErrorSpan-payment" ).html("There was some error. Please try again later");							  		 
								}
							});
						  
					      //alert(data.responseText);	
					      //alert(data.transId);
					      //alert(data.actCode);
					  }else {
						  $('#wizard').smartWizard('goToStep',4);
						  $('#wizard').smartWizard('setError',{stepnum:4,iserror:true});
						  /* var errorMsg = '';
						  //if(data != null && data.responseText == "UNKNOWN TOKEN"){					  
							//  errorMsg = "ActionCode&nbsp;:&nbsp;<b>"+data.actCode+"</b>&nbsp;Error message&nbsp;:&nbsp;"+data.responseText;
						  //}else if(data != null && data.responseText == "INVALID MESSAGE TYPE"){
							//  errorMsg = "";
						  //}else if(data != null && data.responseText == "DECLINED"){
							//  errorMsg = "";
						  //}else{
							 // errorMsg = "";
						  //} 
						  
						  errorMsg += "Error occurred while processing Payment.<br />";
						  errorMsg += "ActionCode :<b>"+data.actCode+"</b> Error message : "+data.responseText;						  
						  $("p.payment-Error-Box-msg-p").html(errorMsg);						  
						  var kendoWindow = $("<div />").kendoWindow({
					        	title: "Error",
					        	resizable: false,
					        	modal: true,
					        	width:400
					        });
					
				  			kendoWindow.data("kendoWindow")
					         .content($("#payment-Error-Box").html())
					         .center().open();
				  			
					        kendoWindow
					        .find(".confirm-payment-process")
					        .click(function() {
					        	if ($(this).hasClass("confirm-payment-process")) {  
					        		$("#payment-Error-Box").css("display", "none");
					        		kendoWindow.data("kendoWindow").close();
					        	}
					        })
					        .end();	 */		
					        
						  $("#statusBlock-payment").css("display", "block");	
					        $("#tcloginErrorSpan-payment").css("display", "block");	
					        $( "#tcloginErrorSpan-payment" ).html("Error occurred while processing the payment. Please try again later <br />ActionCode :<b>"+data.actCode+"</b> Error message : "+data.responseText);
					  }
					  $(".k-loading-mask").hide();
				  },
				  error: function( xhr,status,error ){
					  $("#statusBlock-payment").css("display", "block");	
					  $("#tcloginErrorSpan-payment").css("display", "block");	
					  $( "#tcloginErrorSpan-payment" ).html("There was some error. Please try again later");							  		 
				  }
			});
		} else{
			$(".k-loading-mask").show();
			$(".k-loading-text").html("Please wait");
			
			var merchantId = "TESTTERMINAL";  
			var JetDirectToken	= "1234567890ABCDEFGHIJKabcdefghijk";
			var hash = '';
			var paymentAmount = $("#sumFinalAmountTd").text() + ".00";
			paymentOrderId = Math.floor((Math.random()*1000000000000)+1);
			var jsSha = new jsSHA(merchantId+paymentAmount+JetDirectToken+paymentOrderId);
			hash = jsSha.getHash("SHA-512", "HEX");			
			
			/*var jsonData = {
				name : $("#fullName").val(),
				cardNum : $("#cardNum").val(),
				cscNumber : $("#securityCode").val(),
				AddressLine1 : $("#billingAddressLine1").val(),
				AddressLine2 : $("#billingAddressLine2").val(),
				City : $("#billingCity").val(),
				state : $("#billingState").val(),
				zipcode : $("#billingZip").val(),
				email : "test@gmail.com",
				amount : paymentAmount,
				expMonth : $("#expirationMonth").val(),
				expYr : $("#addCardExpirationYear").val(),
				contry : "US",
				jetPayHash : hash.toString(),
				paymentOrderId : paymentOrderId.toString()
			};*/
			if(parseFloat(paymentAmount) > 0){
				var saveCard = 'N';
				if($('.SaveCard').is(":checked")){
					saveCard = 'Y';
				}
				var jsonData = {
						trans_type : "SALE", // "TOKENIZE" // "AUTHONLY", "SALE"
						ud1 : $("#loggedInAccountId").val(),
						ud2 : $("#nickName").val(),
						name : $("#fullName").val(),
						cardNum : $("#cardNum").val(),
						cscNumber : $("#securityCode").val(),
						expMonth : $("#expirationMonth").val(),
						expYr : $("#addCardExpirationYear").val(),
						amount : paymentAmount,
						AddressLine1 : $("#billingAddressLine1").val(),
						AddressLine2 : $("#billingAddressLine2").val(),
						City : $("#billingCity").val(),
						state : $("#billingState").val(),
						zipcode : $("#billingZip").val(),
						email : "test@gmail.com",
						contry : "US",
						jetPayHash : hash.toString(),
						paymentOrderId : paymentOrderId.toString(),
						isSaveCard : saveCard
					};
					var win = document.getElementById("childIframeId").contentWindow;
					win.postMessage(jsonData, '*');
			}else{
				var joinFee = $('#sumJoinFeeTd').text();
				  var sumTierPriceTd = $('#sumTierPriceTd').text();
				  
				  $("#paymentTransId").attr("value", paymentOrderId); 
			      if(joinFee != null){
			    		$("#prodJoiningFeeInput").attr("value", joinFee);
			      }else{
			    		$("#prodJoiningFeeInput").attr("value", "0");  
			      }
				    	  
			      if(sumTierPriceTd != null){
			    		$("#prodPriceInput").attr("value", sumTierPriceTd);    
			      }else{
			    		$("#prodPriceInput").attr("value", "0");     
			      }
			
				    $("#statusBlock-payment").css("display", "none");	
				    $("#tcloginErrorSpan-payment").css("display", "none");	
				    $( "#tcloginErrorSpan-payment" ).html("");
				document.forms["becomeMemberForm"].submit();				
			}
			
			$('#wizard').smartWizard('setError',{stepnum:4,iserror:false});
			$(".k-loading-mask").hide();
			
		}		 
		//document.forms["becomeMemberForm"].submit();
		return true;
			
			/*var joinFee = $('#sumJoinFeeTd').text();
			  var sumTierPriceTd = $('#sumTierPriceTd').text();
			  
			  $("#paymentTransId").attr("value", paymentOrderId); 
		      if(joinFee != null){
		    		$("#prodJoiningFeeInput").attr("value", joinFee);
		      }else{
		    		$("#prodJoiningFeeInput").attr("value", "0");  
		      }
			    	  
		      if(sumTierPriceTd != null){
		    		$("#prodPriceInput").attr("value", sumTierPriceTd);    
		      }else{
		    		$("#prodPriceInput").attr("value", "0");     
		      }
		
			    $("#statusBlock-payment").css("display", "none");	
			    $("#tcloginErrorSpan-payment").css("display", "none");	
			    $( "#tcloginErrorSpan-payment" ).html("");
			document.forms["becomeMemberForm"].submit();
		}*/
 	}
}

function nextClick(obj, context){	
	//$("#wizard"). smartWizard("fixHeight");	
	//$(".stepContainer").height($(".stepContainer").find("div:not(:hidden)").height());
	//$("#paymentTotalPriceTd").html($("#paymentAmountSpan").text());
	//var pricingOption = $("#pricingOption").data("kendoDropDownList");  
	
	if(context.fromStep > context.toStep){
		console.log("  set radio here ..........  ");
		//var tdIsHere = document.getElementById('paymentTypeSelect1ThisBranchOnly300000002350297');
		
		selectedProductRadioInputId = $("#selectedProductRadioInputId").val();
		
		$("#"+selectedProductRadioInputId).attr("checked",true);
		//console.log("  tdIsHere    ::     "+tdIsHere);
	}
	
	$("#sumPromoCodeVal").html("");
    var locationDropdownlist = $("#location").data("kendoDropDownList");
    var paymentFreq2 = $("#paymentFrequencySelect").data("kendoDropDownList");
    if(paymentFreq2.text() == 'Annual'){
		$("#endDateDiv").css("display","inline");
		if(selectedProductTotalPrice != null){    			
			$("#sumTotalPriceTd").html(parseFloat(selectedProductTotalPrice) * 12);
		} 	
	}else{
		$("#endDateDiv").css("display","none");    		
		$("#sumTotalPriceTd").html(selectedProductTotalPrice);		
	}    
    
    if(context.toStep == 5 ){
    	
    	console.log("   next step is 5   ");
    	
    	populateChangePaymentInfoSection();
    	
    	/*var totalAmount = 0;
    	if($("#sumJoinFeeTd").text() != ''){
    		totalAmount = totalAmount + parseFloat($("#sumJoinFeeTd").text());
    	} 
    	if($("#sumTierPriceTd").text() != ''){
    		if($("#sumProRatePriceTd").text() != '' && parseFloat($("#sumProRatePriceTd").text()) > 0){
    			totalAmount = totalAmount + parseFloat($("#sumProRatePriceTd").text());
    		}else{
    			totalAmount = totalAmount + parseFloat($("#sumTierPriceTd").text());
    		}
    		//console.log("  sumTierPriceTd :: "+ parseFloat($("#sumTierPriceTd").text()));
    		//console.log("  sumProRatePriceTd :: "+ parseFloat($("#sumProRatePriceTd").text()));
    	}
    	if( $("#sumRegistrationFee").text() != ''){
    		totalAmount = totalAmount + parseFloat($("#sumRegistrationFee").text());
    	} 
    	if( $("#sumDepositAmount").text() != ''){
    		totalAmount = totalAmount + parseFloat($("#sumDepositAmount").text());
    	}
    	var totalDiscount = parseFloat($("#totalDiscountHiddenInput").val());
    	
    	console.log(" totalDiscount::  "+totalDiscount);
    	totalAmount = parseFloat(totalAmount) - parseFloat(totalDiscount);
    	
    	$("#sumTotalPriceTd").html(totalAmount);*/
    	
    	//var finalAmount = 0;
    	//Membership calculation
    	/*if($("#sumEarlyBirdDiscount").text() != '' && $("#sumPromoCodeVal").text() != '' && $("#faAmountTD").text() != ''){
    		finalAmount = parseFloat($("#sumTotalPriceTd").text()) - parseFloat($("#sumEarlyBirdDiscount").text()) - parseFloat($("#sumPromoCodeVal").text()) - parseFloat($("#faAmountTD").text());
    	}else if($("#sumEarlyBirdDiscount").text() != '' && $("#faAmountTD").text() != ''){
    		finalAmount = parseFloat($("#sumTotalPriceTd").text()) - parseFloat($("#sumEarlyBirdDiscount").text()) - parseFloat($("#faAmountTD").text());
    	}else if($("#sumPromoCodeVal").text() != '' && $("#faAmountTD").text() != ''){
    		finalAmount = parseFloat($("#sumTotalPriceTd").text()) - parseFloat($("#sumPromoCodeVal").text()) - parseFloat($("#faAmountTD").text());
    	}else if( $("#faAmountTD").text() != ''){
    		finalAmount = parseFloat($("#sumTotalPriceTd").text()) - parseFloat($("#faAmountTD").text());
    	} else {
    		finalAmount = parseFloat($("#sumTotalPriceTd").text());
    	} */ 
    	//Change Membership calculation
    	/*if($("#sumEarlyBirdDiscount").text() != '' && $("#sumPromoCodeVal").text() != '' && $("#faAmountTD").text() != ''){
    		finalAmount = (parseFloat($("#sumChangePriceTd").text()) + parseFloat($("#sumProRatePriceTd").text())) - parseFloat($("#sumEarlyBirdDiscount").text()) - parseFloat($("#sumPromoCodeVal").text()) - parseFloat($("#faAmountTD").text());
    	}else if($("#sumEarlyBirdDiscount").text() != '' && $("#faAmountTD").text() != ''){
    		finalAmount = (parseFloat($("#sumChangePriceTd").text()) + parseFloat($("#sumProRatePriceTd").text())) - parseFloat($("#sumEarlyBirdDiscount").text()) - parseFloat($("#faAmountTD").text());
    	}else if($("#sumPromoCodeVal").text() != '' && $("#faAmountTD").text() != ''){
    		finalAmount = (parseFloat($("#sumChangePriceTd").text()) + parseFloat($("#sumProRatePriceTd").text())) - parseFloat($("#sumPromoCodeVal").text()) - parseFloat($("#faAmountTD").text());
    	}else if( $("#faAmountTD").text() != ''){
    		finalAmount = (parseFloat($("#sumChangePriceTd").text()) + parseFloat($("#sumProRatePriceTd").text())) - parseFloat($("#faAmountTD").text());
    	} else {
    		finalAmount = (parseFloat($("#sumChangePriceTd").text()) + parseFloat($("#sumProRatePriceTd").text()));
    	}*/
    	
    	/*console.log("  totalAmount   "+totalAmount);
    	
    	finalAmount = Math.round(totalAmount);
    	
    	if($("#faAmountTD").text() != ''){
    		finalAmount = finalAmount - parseFloat($("#faAmountTD").text());
    	}
    	//finalAmount = parseFloat(finalAmount) - parseFloat($("#faAmountTD").text());
    	
    	console.log("  finalAmount  :::>> "+finalAmount);
    	if(finalAmount < 0){
    		$("#sumFinalAmountTd").html(finalAmount);
    	}else{
    		$("#sumFinalAmountTd").html(finalAmount);
    	}    */	 
    	 $("#expirationMonthHid").attr("value", $("#expirationMonth").val());
    	 $("#expirationYearHid").attr("value", $("#addCardExpirationYear").val());
    	 $("#nickNameHid").attr("value", $("#nickName").val());
    	 $("#productPriceTd").html($("#sumTierPriceTd").text());
    	 
    	 var paymentFreqDropDown = $("#paymentFrequencySelect").data("kendoDropDownList");
    	 var str = $("#start").val();    			
    	 if(str != null) {
    	 	var parts = str.split("-");	
    	 	var month = parts[1] && parseInt( parts[1], 10 );
    	 	var day = parts[2] && parseInt( parts[2], 10 );
    	 	if(paymentFreqDropDown.text() != 'Annual'){   
    	 		$("#paymentBillingcycleTr").css("display", "");
    	 		if(parseInt((day%100) / 10) != 1){
    	 			if(day%10 == 1){
    	 				$("#paymentBillingcycleTd").html(day + "st of each month");
    	 			}else if(day%10 == 2){
    	 				$("#paymentBillingcycleTd").html(day + "nd of each month");
    	 			}else if(day%10 == 3){
    	 				$("#paymentBillingcycleTd").html(day + "rd of each month");
    	 			}else{
    	 				$("#paymentBillingcycleTd").html(day + "th of each month");
    	 			}
    	 		} else{
    	 			$("#paymentBillingcycleTd").html(day + "th of each month");
    	 		}
    	 		
    	 	} else {
    	 		$("#paymentBillingcycleTr").css("display", "none");
    	 		$("#paymentBillingcycleTd").html("");
    	 	}
    	 } else {
    	 	$("#paymentBillingcycleTr").css("display", "none");
    	 	$("#paymentBillingcycleTd").html("");
    	 }
    }   
    	
	var paymentFreq2 = $("#paymentFrequencySelect").data("kendoDropDownList");
    $("#paymentFrequencyTd").html(paymentFreq2.text());
    $("#sumPaymentFreqTd").html(paymentFreq2.text());
	$(".stepContainer").height($(".stepContainer").find("div:not(:hidden)").height());
	
	if( context.fromStep == 2){	
		$('#firstNameTd').html($('#firstName').val());
		$('#lastNameTd').html($('#lastName').val());
		$('#dobTd').html($('#dob').val());
		$('#phoneNumberTd').html($('#phoneNumber').val());
		$('#emailTd').html($('#email').val());
		if($('#genderM').is(':checked')) { 
			$('#genderTd').html($('#genderM').val()); 				
		}else if($('#genderF').is(':checked')) { 
			$('#genderTd').html($('#genderF').val()); 				
		}
		
		$('#addressLine1Td').html($('#addressLine1').val());
		$('#addressLine2Td').html($('#addressLine2').val());
		$('#cityTd').html($('#city').val());
		$('#stateTd').html($('#state').val());
		$('#postalCodeTd').html($('#postalCode').val());	
		if(selectedHeaderInfo == 'One Adultw/ Kids' ){
			populateKidsSummaryInformation();
		} else if(selectedHeaderInfo == 'Two Adults' || selectedHeaderInfo == 'Youth'){
			$('#secMemFirstNameTd').html($('#secFirstName').val());
			$('#secMemLastNameTd').html($('#secLastName').val());
			$('#secMemDobTd').html($('#secDOB').val());
			$('#secMemPhoneNumberTd').html($('#secPhoneNumber').val());
			$('#secMemEmailTd').html($('#secEmail').val());
			if($('#secGenderM').is(':checked')) { 
				$('#secGenderTd').html($('#secGenderM').val()); 				
			}else if($('#secGenderF').is(':checked')) { 
				$('#secGenderTd').html($('#secGenderF').val()); 				
			}			
			$('#secMemberTr').css("display", "inline");
			
			$('#thirdMemFirstNameTd').html("");
			$('#thirdMemLastNameTd').html("");
			$('#thirdMemDobTd').html("");
			$('#thirdMemPhoneNumberTd').html("");
			$('#thirdMemEmailTd').html("");
			
			if($('#thirdGenderM').is(':checked')) { 
				$('#thirdGenderTd').html(""); 				
			}else if($('#thirdGenderF').is(':checked')) { 
				$('#thirdGenderTd').html(""); 				
			}
			$('#thirdMemberTr').css("display", "none");
		} else if(selectedHeaderInfo == 'Two Adultsw/ Kids'){
			$('#secMemFirstNameTd').html($('#secFirstName').val());
			$('#secMemLastNameTd').html($('#secLastName').val());
			$('#secMemDobTd').html($('#secDOB').val());
			$('#secMemPhoneNumberTd').html($('#secPhoneNumber').val());
			$('#secMemEmailTd').html($('#secEmail').val());
			if($('#secGenderM').is(':checked')) { 
				$('#secGenderTd').html($('#secGenderM').val()); 				
			}else if($('#secGenderF').is(':checked')) { 
				$('#secGenderTd').html($('#secGenderF').val()); 				
			}			
			$('#secMemberTr').css("display", "inline");
			
			$('#thirdMemFirstNameTd').html("");
			$('#thirdMemLastNameTd').html("");
			$('#thirdMemDobTd').html("");
			$('#thirdMemPhoneNumberTd').html("");
			$('#thirdMemEmailTd').html("");
			
			if($('#thirdGenderM').is(':checked')) { 
				$('#thirdGenderTd').html(""); 				
			}else if($('#thirdGenderF').is(':checked')) { 
				$('#thirdGenderTd').html(""); 				
			}
			$('#thirdMemberTr').css("display", "none");
			populateKidsSummaryInformation();
			
		}  else if(selectedHeaderInfo == 'Three Adults'){
			$('#secMemFirstNameTd').html($('#secFirstName').val());
			$('#secMemLastNameTd').html($('#secLastName').val());
			$('#secMemDobTd').html($('#secDOB').val());
			$('#secMemPhoneNumberTd').html($('#secPhoneNumber').val());
			$('#secMemEmailTd').html($('#secEmail').val());
			if($('#secGenderM').is(':checked')) { 
				$('#secGenderTd').html($('#secGenderM').val()); 				
			}else if($('#secGenderF').is(':checked')) { 
				$('#secGenderTd').html($('#secGenderF').val()); 				
			}			
			$('#secMemberTr').css("display", "inline");
			
			$('#thirdMemFirstNameTd').html($('#thirdFirstName').val());
			$('#thirdMemLastNameTd').html($('#thirdLastName').val());
			$('#thirdMemDobTd').html($('#thirdDOB').val());
			$('#thirdMemPhoneNumberTd').html($('#thirdPhoneNumber').val());
			$('#thirdMemEmailTd').html($('#thirdEmail').val());
			
			if($('#thirdGenderM').is(':checked')) { 
				$('#thirdGenderTd').html($('#thirdGenderM').val()); 				
			}else if($('#thirdGenderF').is(':checked')) { 
				$('#thirdGenderTd').html($('#thirdGenderF').val()); 				
			}
			$('#thirdMemberTr').css("display", "inline");		
		} else if(selectedHeaderInfo == 'Three Adultsw/ or w/o kids'){
			$('#secMemFirstNameTd').html($('#secFirstName').val());
			$('#secMemLastNameTd').html($('#secLastName').val());
			$('#secMemDobTd').html($('#secDOB').val());
			$('#secMemPhoneNumberTd').html($('#secPhoneNumber').val());
			$('#secMemEmailTd').html($('#secEmail').val());
			if($('#secGenderM').is(':checked')) { 
				$('#secGenderTd').html($('#secGenderM').val()); 				
			}else if($('#secGenderF').is(':checked')) { 
				$('#secGenderTd').html($('#secGenderF').val()); 				
			}			
			$('#secMemberTr').css("display", "inline");
			
			$('#thirdMemFirstNameTd').html($('#thirdFirstName').val());
			$('#thirdMemLastNameTd').html($('#thirdLastName').val());
			$('#thirdMemDobTd').html($('#thirdDOB').val());
			$('#thirdMemPhoneNumberTd').html($('#thirdPhoneNumber').val());
			$('#thirdMemEmailTd').html($('#thirdEmail').val());
			
			if($('#thirdGenderM').is(':checked')) { 
				$('#thirdGenderTd').html($('#thirdGenderM').val()); 				
			}else if($('#thirdGenderF').is(':checked')) { 
				$('#thirdGenderTd').html($('#thirdGenderF').val()); 				
			}
			$('#thirdMemberTr').css("display", "inline");
			populateKidsSummaryInformation();
			
		}else{
			$('#secMemFirstNameTd').html("");
			$('#secMemLastNameTd').html("");
			$('#secMemDobTd').html("");
			$('#secMemPhoneNumberTd').html("");
			$('#secMemEmailTd').html("");
			if($('#secGenderM').is(':checked')) { 
				$('#secGenderTd').html(""); 				
			}else if($('#secGenderF').is(':checked')) { 
				$('#secGenderTd').html(""); 				
			}			
			
			
			$('#thirdMemFirstNameTd').html("");
			$('#thirdMemLastNameTd').html("");
			$('#thirdMemDobTd').html("");
			$('#thirdMemPhoneNumberTd').html("");
			$('#thirdMemEmailTd').html("");
			
			if($('#thirdGenderM').is(':checked')) { 
				$('#thirdGenderTd').html(""); 				
			}else if($('#thirdGenderF').is(':checked')) { 
				$('#thirdGenderTd').html(""); 				
			}
			
			
			$('#secMemberTr').css("display", "none");
			$('#thirdMemberTr').css("display", "none");
		}
	}
	if( context.fromStep == 1 ){
		console.log(" changeMembership 2310 location validation  ");
		var urlPromoItemDetailId = $("#urlPromoItemDetailId").val();
		console.log("  urlPromoItemDetailId   ::::    "+urlPromoItemDetailId);
		if(urlPromoItemDetailId != null && urlPromoItemDetailId != undefined){
			
			$('#wizard').smartWizard('setError',{stepnum:1,iserror:false});
	    	return true;
			
		}else{
		
	    	if(locationDropdownlist.text() == '--Select Location--'){  
	   			var kendoWindow = $("<div />").kendoWindow({
	   	        	title: "Error",
	   	        	resizable: false,
	   	        	modal: true,
	   	        	width:400
	   	        });   	
	     		kendoWindow.data("kendoWindow")
	   	         .content($("#terms-conditions-LocationBox").html())
	   	         .center().open();
	     			
	   	        kendoWindow
	   	        .find(".confirm-location-select")
	   	        .click(function() {
	   	        	if ($(this).hasClass("confirm-location-select")) {         		
	   	        		kendoWindow.data("kendoWindow").close();
	   	        	}
	   	        })
	   	        .end();
	   			//e.preventDefault();
	   	     	//pricingOption.select(0);
	   	     	$('#wizard').smartWizard('setError',{stepnum:1,iserror:true});
	   	     	resetPrimaryMember();
		       //$("#wizard"). smartWizard("fixHeight");	
			   return false;
	    	} else if (!$(document).find( "input:radio[name=paymentTypeSelect]" ).is(":checked")){
		    	var kendoWindow = $("<div />").kendoWindow({
		    		title: "Error",
		    		resizable: false,
		    		modal: true,
		    		width:400
		    	});   	
		    	kendoWindow.data("kendoWindow")
		    	 .content($("#terms-conditions-PricingBox").html())
		    	 .center().open();
		    		
		    	kendoWindow
		    	.find(".confirm-pricing-select")
		    	.click(function() {
		    		if ($(this).hasClass("confirm-pricing-select")) {         		
		    			kendoWindow.data("kendoWindow").close();
		    		}
		    	})
		    	.end();
		    	$('#wizard').smartWizard('setError',{stepnum:1,iserror:true});
		    	return false;
		    }else if (!registerSelected){
		    	var kendoWindow = $("<div />").kendoWindow({
		    		title: "Error",
		    		resizable: false,
		    		modal: true,
		    		width:400
		    	});   	
		    	kendoWindow.data("kendoWindow")
		    	 .content($("#terms-conditions-PricingBox").html())
		    	 .center().open();
		    		
		    	kendoWindow
		    	.find(".confirm-pricing-select")
		    	.click(function() {
		    		if ($(this).hasClass("confirm-pricing-select")) {         		
		    			kendoWindow.data("kendoWindow").close();
		    		}
		    	})
		    	.end();
		    	$('#wizard').smartWizard('setError',{stepnum:1,iserror:true});
		    	return false;
		    }else{
		    	$('#wizard').smartWizard('setError',{stepnum:1,iserror:false});
		    	return true;
		    }
		}
    }else if( context.fromStep == 2 && context.toStep == 3 ){
    	if(signInSelected == "true"){
    		if(!$("#loginForm").valid()) {
    			$('#wizard').smartWizard('setError',{stepnum:2,iserror:true});		
    			return false;
    		} else if(isLoggedIn == 'false'){
    			$('#wizard').smartWizard('setError',{stepnum:2,iserror:true});	
    			$("#statusBlock").css("display", "block");	
		  		$("#tcloginErrorSpan").css("display", "block");	
				$( "#tcloginErrorSpan" ).html("Please login");
    			return false;
    		} else {
    			$("#statusBlock").css("display", "none");	
   			 	$("#tcloginErrorSpan").css("display", "none");	
   			 	$( "#tcloginErrorSpan" ).html("");
   			 
    			$('#wizard').smartWizard('setError',{stepnum:2,iserror:false});	
    			
    			return true;
    		}
    	} else{
    		if(!$("#becomeMemberForm").valid()){
    			$('#wizard').smartWizard('setError',{stepnum:2,iserror:true});	
    			//$("#wizard"). smartWizard("fixHeight");	
    			return false;
    		}else{
    			$('#wizard').smartWizard('setError',{stepnum:2,iserror:false});	
    			
    			/*var paymentMethodHtml = '';
    			paymentMethodHtml += '<option value="0" >--Select Payment Method--</option>';
    			paymentMethodHtml += '<option value="New" selected="selected">Add New Card</option>';
    			paymentMethodHtml += '<option value="NewBank">Add New Bank Info</option>';
    			$("#paymentInfoRadio").html(paymentMethodHtml);
    			$("#paymentInfoRadio").kendoDropDownList(); */ 
    			//$("#addcard").css("display","inline");
    			//$("#wizard"). smartWizard("fixHeight");	
    			return true;
    		}
    	}    	
		
	}else if( context.fromStep == 3 && context.toStep == 4 ){	
		 if(!$("input[name='chkTermsAndCond']:checked").val() && !isLoggedInUserAgent()){ 
				$("#tcForm").valid();
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
			        $('#wizard').smartWizard('setError',{stepnum:3,iserror:true});		
			       // $("#wizard"). smartWizard("fixHeight");	
	     	return false;
	     }else if(!$("#tcForm").valid()){
	 		 $('#wizard').smartWizard('setError',{stepnum:3,iserror:true});
	 		//$("#wizard"). smartWizard("fixHeight");	
			 return false;
	 	 }else{
	    	 $('#wizard').smartWizard('setError',{stepnum:3,iserror:false});	
	    	 //$("#wizard"). smartWizard("fixHeight");	
	    	 return true;
	     }
	}else if(context.fromStep == 4 ){
		if(!$("#addCardInfoForm").valid()){
			$('#wizard').smartWizard('setError',{stepnum:4,iserror:true});			
			return false;
		}else{
			$('#wizard').smartWizard('setError',{stepnum:4,iserror:false});	
			return true;
		}
			
	} else {	
		//$("#wizard"). smartWizard("fixHeight");	
		return true;
	}
	
}

function isAdult(bdate){
	var age =  18;
	var currdate = new Date();
    currdate.setFullYear(currdate.getFullYear() - age);
    return currdate > bdate;
}

function forceLower(strInput) {
	strInput.value=strInput.value.toLowerCase();
}





function getSecondaryKidsInfoHtml(kidNo){
	var kidsInfoTableHtml = '';
	kidsInfoTableHtml += getSecondaryKidInfoHeader();
	
	kidsInfoTableHtml += '<tr>';
	kidsInfoTableHtml += '<td><input type="text" class="form-control firstName" value="" title="Please enter yout First Name" name="userLst['+kidNo+'].firstName" id="firstName" maxlength="50" placeholder="First Name"  /></td>';
	kidsInfoTableHtml += '<td>&nbsp;</td>';
	kidsInfoTableHtml += '<td><input type="text" class="form-control lastName" value="" title="Please enter yout Last Name" name="userLst['+kidNo+'].lastName" id="lastName" maxlength="50" placeholder="Last Name" /></td>';
	kidsInfoTableHtml += '<td><a class="remove-child-lnk">Remove Child &nbsp; <span>-</span></a></td>';
	kidsInfoTableHtml += '</tr>';
	
	kidsInfoTableHtml += '<tr>';
	//kidsInfoTableHtml += '<td style="padding-bottom: 8px;"><input type="text" class="form-control dob-class dateOfBirth dateOfBirthValidation"  title="Please enter your Date of Birth" name="userLst['+kidNo+'].dateOfBirth"  maxlength="50" placeholder="Date of Birth"  style="width: 205px; height: 30px; margin: 0; padding: 0;" /></td>';
	kidsInfoTableHtml += '<td style="padding-bottom: 8px;"><input type="text" class="form-control dob-class dateOfBirth dateOfBirthValidation"  title="Please enter your Date of Birth" name="userLst['+kidNo+'].dateOfBirth"  maxlength="50" placeholder="Date of Birth"  style="width: 1px; height: 1px; left : -1000px; margin: 0; padding: 0;" /><br />';
	kidsInfoTableHtml += getDOBHtmlbyClass("kidDobYearForm",  "kidDobMonthForm", "kidDobDateForm", "" , "" , "");
	kidsInfoTableHtml += '</td>';
	kidsInfoTableHtml += '<td id="dop-er"></td>';

	kidsInfoTableHtml += '<td>';
	kidsInfoTableHtml += '<span>';
	kidsInfoTableHtml += '<span><span><input  name="userLst['+kidNo+'].gender" class="form-control gender" style="width: 15px;" type="radio" value="Male"></span><span style="margin-left : 5px;">Male</span></span>';
	kidsInfoTableHtml += '<span><span><input  name="userLst['+kidNo+'].gender" class="form-control gender" style="width : 15px;" type="radio" value="Female"></span><span style="margin-left : 5px;">Female</span></span>';
	kidsInfoTableHtml += '</span>';
	kidsInfoTableHtml += '</td>';	
	
	kidsInfoTableHtml += '<td>&nbsp;</td>';
	kidsInfoTableHtml += '</tr>';	
	kidsInfoTableHtml += '</table>';
	kidsInfoTableHtml += '</td>';
	kidsInfoTableHtml += '</tr>';
	
	kidsInfoTableHtml += '<tr style="display : none;">';
	kidsInfoTableHtml += '<td><input style="display:none;" name="userLst['+kidNo+'].isAdult" value="false"/><input style="display:none;" name="userLst['+kidNo+'].membershipAgeCategory" value="Kid"/></td>';
	kidsInfoTableHtml += '<td>&nbsp;</td>';	
	kidsInfoTableHtml += '<td><input style="display:none;" name="userLst['+kidNo+'].usrSequence" value="'+kidNo+'" /></td>';		
	kidsInfoTableHtml += '<td>&nbsp;</td>';
	kidsInfoTableHtml += '</tr>';
	
	return kidsInfoTableHtml;
}

function populateKidsSummaryInformation(){
	var firstKidFName = $(".firstKidInfoForm").find(".firstName").val();
	var firstKidLName = $(".firstKidInfoForm").find(".lastName").val();
	var firstKidDob = $(".firstKidInfoForm").find("input.dateOfBirth").val();
	var firstKidGender = $(".firstKidInfoForm").find(".gender:checked").val()

	var kidsInfoHtml = "";
	kidsInfoHtml += '<tr>';
	kidsInfoHtml += '<td><span><b>First Name</b></span></td>';
	kidsInfoHtml += '<td>'+ firstKidFName +'</td>';
	kidsInfoHtml += '</tr>';

	kidsInfoHtml += '<tr>';
	kidsInfoHtml += '<td><span><b>Last Name</b></span></td>';
	kidsInfoHtml += '<td>'+ firstKidLName +'</td>';
	kidsInfoHtml += '</tr>';

	kidsInfoHtml += '<tr>';
	kidsInfoHtml += '<td><span><b>Date of Birth</b></span></td>';
	kidsInfoHtml += '<td>'+ firstKidDob +'</td>';
	kidsInfoHtml += '</tr>';

	kidsInfoHtml += '<tr>';
	kidsInfoHtml += '<td><span><b>Gender</b></span></td>';
	kidsInfoHtml += '<td>'+ firstKidGender +'</td>';
	kidsInfoHtml += '</tr>';

	$('#kidsInformationTable').css("display","");
	$('#kidsInformationTableTbody').html("");
	$('#kidsInformationTableTbody').html(kidsInfoHtml);
	$('#kidsInformationTableTbodyOdd').html("");

	$(document).find('.secondaryKidsInfoForm').each(function(i, obj) {	
		var kidsInfoHtmlUpd = "";			
		kidsInfoHtmlUpd += '<tr>';
		kidsInfoHtmlUpd += '<td>&nbsp;</td>';
		kidsInfoHtmlUpd += '<td>&nbsp;</td>';
		kidsInfoHtmlUpd += '</tr>';

		kidsInfoHtmlUpd += '<tr>';
		kidsInfoHtmlUpd += '<td><span><b>First Name</b></span></td>';
		kidsInfoHtmlUpd += '<td>'+ $(obj).find(".firstName").val() +'</td>';
		kidsInfoHtmlUpd += '</tr>';
		
		kidsInfoHtmlUpd += '<tr>';
		kidsInfoHtmlUpd += '<td><span><b>Last Name</b></span></td>';
		kidsInfoHtmlUpd += '<td>'+ $(obj).find(".lastName").val() +'</td>';
		kidsInfoHtmlUpd += '</tr>';
		
		kidsInfoHtmlUpd += '<tr>';
		kidsInfoHtmlUpd += '<td><span><b>Date of Birth</b></span></td>';
		kidsInfoHtmlUpd += '<td>'+ $(obj).find("input.dateOfBirth").val() +'</td>';
		kidsInfoHtmlUpd += '</tr>';
		
		kidsInfoHtmlUpd += '<tr>';
		kidsInfoHtmlUpd += '<td><span><b>Gender</b></span></td>';
		kidsInfoHtmlUpd += '<td>'+ $(obj).find(".gender:checked").val() +'</td>';
		kidsInfoHtmlUpd += '</tr>';			
		if(i % 2 == 0){
			$('#kidsInformationTableOdd').css("display","");					
			$('#kidsInformationTableTbodyOdd').append(kidsInfoHtmlUpd);					
		}else{
			$('#kidsInformationTable').css("display","");
			$('#kidsInformationTableTbody').append(kidsInfoHtmlUpd);
		}
	});
}




function addKidsInfoFunction(kendoWindow){
	var primaryKidInfoInserted = 0;
	kendoWindow.find("#selectKidsInfoTable input[type=checkbox]").each(function () {
		 if(this.checked){	
			 var kidFirstName = $(this).parent().parent().find(".kidFirstName").html();
			 var kidLastName = $(this).parent().parent().find(".kidLastName").html();
			 var kidGender = $(this).parent().parent().find(".kidGender").html();
			 var kidDob = $(this).parent().parent().find(".kidDob").html();	
			 if(primaryKidInfoInserted == 0){
			    $(".firstKidInfoForm").find(".firstName").attr("value",kidFirstName);
				$(".firstKidInfoForm").find(".lastName").attr("value",kidLastName);
				$(".firstKidInfoForm").find("input.dateOfBirth").attr("value",kidDob);
				var $radios = $(".firstKidInfoForm").find(".gender");
			    if(kidGender == "Male") {
			        $radios.filter('[value=Male]').prop('checked', true);
			    }else{
			    	$radios.filter('[value=Female]').prop('checked', true);
			    }
			    primaryKidInfoInserted = 1;
			 }else{
				 populateSecondaryKidsInfoWithData(kidFirstName, kidLastName, kidDob, kidGender)
			 }						    					 
		 }else {
			 
		 }
	       
	 });
}


function populateSecondaryKidsInfoWithData(kidFirstName, kidLastName, kidDob, kidGender){
	var count = secKidCount;							
	$(document).find('.secondaryKidsInfoForm').each(function(i, obj) {				
		count = count +1;				
	});    
	
	var kidsInfoTableHtml = '';
	kidsInfoTableHtml += '<tr>';
	kidsInfoTableHtml += '<td colspan="4">';
	kidsInfoTableHtml += '<table border="0" width="101%" class="secondaryKidsInfoForm">';
	kidsInfoTableHtml += '<tr>';
	kidsInfoTableHtml += '<td>&nbsp;</td>';
	kidsInfoTableHtml += '<td>&nbsp;</td>';
	kidsInfoTableHtml += '<td>&nbsp;</td>';
	kidsInfoTableHtml += '<td>&nbsp;</td>';
	kidsInfoTableHtml += '</tr>';
	
	kidsInfoTableHtml += '<tr>';
	kidsInfoTableHtml += '<td><input type="text" class="form-control firstName"  title="Please enter yout First Name" name="userLst['+count+'].firstName" id="firstName" maxlength="50" placeholder="First Name"  value="'+kidFirstName+'"/></td>';
	kidsInfoTableHtml += '<td>&nbsp;</td>';
	kidsInfoTableHtml += '<td><input type="text" class="form-control lastName"  title="Please enter yout Last Name" name="userLst['+count+'].lastName" id="lastName" maxlength="50" placeholder="Last Name" value="'+kidLastName+'"/></td>';
	kidsInfoTableHtml += '<td><a class="remove-child-lnk">Remove Child &nbsp; <span>-</span></a></td>';
	kidsInfoTableHtml += '</tr>';
	
	kidsInfoTableHtml += '<tr>';
	kidsInfoTableHtml += '<td style="padding-bottom: 8px;"><input type="text" class="form-control dob-class dateOfBirth"  title="Please enter your Date of Birth" name="userLst['+count+'].dateOfBirth"  maxlength="50" placeholder="Date of Birth"  style="width: 205px; height: 30px; margin: 0; padding: 0;" value="'+kidDob+'"/></td>';
	kidsInfoTableHtml += '<td id="dop-er"></td>';

	kidsInfoTableHtml += '<td>';
	kidsInfoTableHtml += '<span>';
	if(kidGender == "Male") {
		kidsInfoTableHtml += '<span><span><input  name="userLst['+count+'].gender" checked class="form-control gender" style="width: 15px;" type="radio" value="Male"></span><span style="margin-left : 5px;">Male</span></span>';
		kidsInfoTableHtml += '<span><span><input  name="userLst['+count+'].gender" class="form-control gender" style="width : 15px;" type="radio" value="Female"></span><span style="margin-left : 5px;">Female</span></span>';
    }else{
    	kidsInfoTableHtml += '<span><span><input  name="userLst['+count+'].gender" class="form-control gender" style="width: 15px;" type="radio" value="Male"></span><span style="margin-left : 5px;">Male</span></span>';
		kidsInfoTableHtml += '<span><span><input  name="userLst['+count+'].gender" checked class="form-control gender" style="width : 15px;" type="radio" value="Female"></span><span style="margin-left : 5px;">Female</span></span>';
    }						
	kidsInfoTableHtml += '</span>';
	kidsInfoTableHtml += '</td>';	
	
	kidsInfoTableHtml += '<td>&nbsp;</td>';
	kidsInfoTableHtml += '</tr>';	
	kidsInfoTableHtml += '</table>';
	kidsInfoTableHtml += '</td>';
	kidsInfoTableHtml += '</tr>';
	
	kidsInfoTableHtml += '<tr style="display : none;">';
	kidsInfoTableHtml += '<td><input style="display:none;" name="userLst['+count+'].isAdult" value="false"/></td>';
	kidsInfoTableHtml += '<td>&nbsp;</td>';	
	kidsInfoTableHtml += '<td><input style="display:none;" name="userLst['+count+'].usrSequence" value="'+count+'" /></td>';		
	kidsInfoTableHtml += '<td>&nbsp;</td>';
	kidsInfoTableHtml += '</tr>';
	
	
	//var secondaryKidInfo = getSecondaryKidsInfoHtml(count);
	$('#familyInfoFormTable').css("display" , "inline-table");
	$('#familyInfoFormTable').append(kidsInfoTableHtml);
	$(".stepContainer").height($(".stepContainer").find("div:not(:hidden)").height());
	$('#familyInfoFormTable').find(".dob-class").each(function() {		    
	    if(!$( this ).hasClass( "k-datepicker" )){
	    	$( this ).kendoDatePicker();
		}
	 });
	$('#familyInfoFormTable').find(".dob-class").css("width", "205px");
}

function populatePrimaryMember(adultFirstName, adultLastName, adultPhoneNumber, adultEmail, adultDob, adultGender){
	$('#userInfoTb #firstName').attr("value", adultFirstName);
	$('#userInfoTb #lastName').attr("value", adultLastName);										    			
	$('#userInfoTb #phoneNumber').attr("value", adultPhoneNumber);
	$('#userInfoTb #email').attr("value", adultEmail);
	$('#userInfoTb #dob').attr("value", adultDob);
	if(adultGender == "Male") {
		$('#genderM').prop('checked', true);
	}else{
		$('#genderF').prop('checked', true);
	}
}

function populateSecondaryMember(adultFirstName, adultLastName, adultPhoneNumber, adultEmail, adultDob, adultGender){
	$('#secFirstName').attr("value", adultFirstName);
	$('#secLastName').attr("value", adultLastName);
	$('#secDOB').attr("value", adultDob); 
	$('#secPhoneNumber').attr("value", adultPhoneNumber); 
	$('#secEmail').attr("value", adultEmail);					
	if(adultGender == "Male") {
		$('#secGenderM').prop('checked', true);
	}else{
		$('#secGenderF').prop('checked', true);
	}
}

function populateThirdMember(adultFirstName, adultLastName, adultPhoneNumber, adultEmail, adultDob, adultGender){
	$('#thirdFirstName').attr("value", adultFirstName);
	$('#thirdLastName').attr("value", adultLastName);
	$('#thirdDOB').attr("value", adultDob); 
	$('#thirdPhoneNumber').attr("value", adultPhoneNumber); 
	$('#thirdEmail').attr("value", adultEmail);					
	if(adultGender  == "Male") {
	$('#thirdGenderM').prop('checked', true);
	}else{
	$('#thirdGenderF').prop('checked', true);
	}
}

function resetPrimaryMember(){
	$(document).find('#firstName').attr("value", "");
	$(document).find('#lastName').attr("value", "");										    			
	$(document).find('#phoneNumber').attr("value", "");
	$(document).find('#email').attr("value", "");
	$(document).find('#dob').attr("value", "");
	$(document).find('#genderM').prop('checked', false);
	$(document).find('#genderF').prop('checked', false);
	
}

function resetSecondaryMember(){
	$(document).find('#secFirstName').attr("value", "");
	$(document).find('#secLastName').attr("value", "");
	$(document).find('#secDOB').attr("value", ""); 
	$(document).find('#secPhoneNumber').attr("value", ""); 
	$(document).find('#secEmail').attr("value", "");		
	$(document).find('#secGenderM').prop('checked', false);
	$(document).find('#secGenderF').prop('checked', false);
	
}

function resetThirdMember(){
	$(document).find('#thirdFirstName').attr("value", "");
	$(document).find('#thirdLastName').attr("value", "");
	$(document).find('#thirdDOB').attr("value", ""); 
	$(document).find('#thirdPhoneNumber').attr("value", ""); 
	$(document).find('#thirdEmail').attr("value", "");		
	$(document).find('#thirdGenderM').prop('checked', false);
	$(document).find('#thirdGenderF').prop('checked', false);
	
}

function getFAObj(itemDetailId){
	var FAObj;
	$.ajax({
		  type: "GET",
		  async: false,
		  url:"getFA?itemDetailId="+itemDetailId,
		  dataType: "json",
		  success: function( data ) {
			  console.log(data);
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

function populateAdultFunction(kendoWindow){
	var primaryKidInfoInserted = 0;
	var count =  0;
	kendoWindow.find("#selectFamilyMemberTable input[type=checkbox]").each(function () {
		 if(this.checked){	
			 count = count +1;
		 }
	});
	
	var kidCount =  0;
	kendoWindow.find("#selectKidsInfoTable input[type=checkbox]").each(function () {
		 if(this.checked){	
			 kidCount = kidCount +1;
		 }
	});
	
	var youthCount =  0;
	kendoWindow.find("#selectYouthInfoTable input[type=checkbox]").each(function () {
		 if(this.checked){	
			 youthCount = youthCount +1;
		 }
	});
	
	if((selectedHeaderInfo == 'Adult' || selectedHeaderInfo == 'Senior'  || selectedHeaderInfo == 'One Adultw/ Kids') && (count > 1)){
		adultCountSelectedError(count); 				
	}else if((selectedHeaderInfo == 'Two Adults' || selectedHeaderInfo == 'Two Adultsw/ Kids')  && (count > 2)){
		adultCountSelectedError(count);  									
	}else if((selectedHeaderInfo == 'Three Adultsw/ or w/o kids' || selectedHeaderInfo == 'Three Adults')  && (count > 3) ){
		adultCountSelectedError(count);  
	}else if((selectedHeaderInfo == 'Adult' || selectedHeaderInfo == 'Senior' || selectedHeaderInfo == 'Youth' || selectedHeaderInfo == 'Two Adults' || selectedHeaderInfo == 'Three Adults') && (kidCount > 0)){
		kidSelectedError();
	}else if((selectedHeaderInfo == 'Adult' || selectedHeaderInfo == 'Senior' || selectedHeaderInfo == 'Two Adults' || selectedHeaderInfo == 'Three Adults' || selectedHeaderInfo == 'One Adultw/ Kids' || selectedHeaderInfo == 'Two Adultsw/ Kids' || selectedHeaderInfo == 'Three Adultsw/ or w/o kids') && (youthCount > 0)){
		youthSelectedError();
	}else{	
		kendoWindow.find("#selectFamilyMemberTable input[type=checkbox], #selectYouthInfoTable input[type=checkbox]").each(function () {
			 if(this.checked){	
				var adultFirstName = $(this).parent().parent().find(".userFNameTd").html();
				var adultLastName = $(this).parent().parent().find(".userLNameTd").html();
				var adultGender = $(this).parent().parent().find(".userGenderTd").html();
				var adultDob = $(this).parent().parent().find(".userBdayTd").html();
				var adultPhoneNumber = $(this).parent().parent().find(".userPhoneNoTd").html();
				var adultEmail = $(this).parent().parent().find(".userEmailAddTd").html();
				
				var youthFirstName = $(this).parent().parent().find(".youthFirstName").html();
				var youthLastName = $(this).parent().parent().find(".youthLastName").html();
				var youthGender = $(this).parent().parent().find(".youthGender").html();
				var youthDob = $(this).parent().parent().find(".youthDob").html();
				var youthPhoneNumber = $(this).parent().parent().find(".youthPhoneNo").html();
				var youthEmail = $(this).parent().parent().find(".youthEmailAdd").html();
				
				
					if(selectedHeaderInfo == 'Adult' || selectedHeaderInfo == 'Senior' ){
						populatePrimaryMember(adultFirstName, adultLastName, adultPhoneNumber, adultEmail, adultDob, adultGender);   
					}else if(selectedHeaderInfo == 'Youth'){
						if( $('#becomeMemberForm').find('#firstName').val() == null || $('#becomeMemberForm').find('#firstName').val() == ""){
							populatePrimaryMember(adultFirstName, adultLastName, adultPhoneNumber, adultEmail, adultDob, adultGender);	
						}else{
							populateSecondaryMember(youthFirstName, youthLastName, youthPhoneNumber, youthEmail, youthDob, youthGender);	
						}     				
					}else if(selectedHeaderInfo == 'One Adultw/ Kids'){		    	
						populatePrimaryMember(adultFirstName, adultLastName, adultPhoneNumber, adultEmail, adultDob, adultGender);	
						//addKidsInfoFunction(kendoWindow);
					}else if(selectedHeaderInfo == 'Two Adults' ){
						if( $('#becomeMemberForm').find('#firstName').val() == null || $('#becomeMemberForm').find('#firstName').val() == ""){
							populatePrimaryMember(adultFirstName, adultLastName, adultPhoneNumber, adultEmail, adultDob, adultGender);	
						}else{
							populateSecondaryMember(adultFirstName, adultLastName, adultPhoneNumber, adultEmail, adultDob, adultGender);	
						}   														
					}else if(selectedHeaderInfo == 'Two Adultsw/ Kids'){
						if($('#becomeMemberForm').find('#firstName').val() == null || $('#becomeMemberForm').find('#firstName').val() == ""){
							populatePrimaryMember(adultFirstName, adultLastName, adultPhoneNumber, adultEmail, adultDob, adultGender);	
						}else{
							populateSecondaryMember(adultFirstName, adultLastName, adultPhoneNumber, adultEmail, adultDob, adultGender);	
						}
						//addKidsInfoFunction(kendoWindow);   					
					}else if(selectedHeaderInfo == 'Three Adultsw/ or w/o kids' || selectedHeaderInfo == 'Three Adults'){
						var secUserFirstName =  $(document).find('#secFirstName').val();
						var thirdUserFirstName =  $(document).find('#thirdFirstName').val();
						if($('#becomeMemberForm').find('#firstName').val() == ""){
							populatePrimaryMember(adultFirstName, adultLastName, adultPhoneNumber, adultEmail, adultDob, adultGender);	
						}else if(secUserFirstName == ""){
							populateSecondaryMember(adultFirstName, adultLastName, adultPhoneNumber, adultEmail, adultDob, adultGender);	
						}else if(thirdUserFirstName == ""){
							populateThirdMember(adultFirstName, adultLastName, adultPhoneNumber, adultEmail, adultDob, adultGender);
						}
						/*else if($(document).find('#userInfoTb #secFirstName').val() == ""){
							populateSecondaryMember(adultFirstName, adultLastName, adultPhoneNumber, adultEmail, adultDob, adultGender);	
						}else if($(document).find('#userInfoTb #thirdFirstName').val() == ""){
							populateThirdMember(adultFirstName, adultLastName, adultPhoneNumber, adultEmail, adultDob, adultGender);
						}*/
						//addKidsInfoFunction(kendoWindow);	
					}else{
						$('#familyInfoFormTable').css("display" , "none");
					} 						    					 
			 }else {
				 
			 }
		       
		 });
	}
}

function addKidsInfoFunction(kendoWindow){
	var primaryKidInfoInserted = 0;
	kendoWindow.find("#selectKidsInfoTable input[type=checkbox]").each(function () {
		 if(this.checked){	
			 var kidFirstName = $(this).parent().parent().find(".kidFirstName").html();
			 var kidLastName = $(this).parent().parent().find(".kidLastName").html();
			 var kidGender = $(this).parent().parent().find(".kidGender").html();
			 var kidDob = $(this).parent().parent().find(".kidDob").html();	
			 if(primaryKidInfoInserted == 0){
			    $(".firstKidInfoForm").find(".personFirstName").attr("value",kidFirstName);
				$(".firstKidInfoForm").find(".personLastName").attr("value",kidLastName);
				$(".firstKidInfoForm").find("input.dateOfBirth").attr("value",kidDob);
				var $radios = $(".firstKidInfoForm").find(".gender");
			    if(kidGender == "Male") {
			        $radios.filter('[value=Male]').prop('checked', true);
			    }else{
			    	$radios.filter('[value=Female]').prop('checked', true);
			    }
			    primaryKidInfoInserted = 1;
			 }else{
				 populateSecondaryKidsInfoWithData(kidFirstName, kidLastName, kidDob, kidGender)
			 }						    					 
		 }else {
			 
		 }
	       
	 });
}

function addKidInfoForm(){
	var count = secKidCount;
	
	$(document).find('.secondaryKidsInfoForm').each(function(i, obj) {				
		count = count +1;				
	});    
	var secondaryKidInfo = getSecondaryKidsInfoHtml(count);
	$('#familyInfoFormTable').css("display" , "inline-table");
	$('#familyInfoFormTable').append(secondaryKidInfo);
	$(".stepContainer").height($(".stepContainer").find("div:not(:hidden)").height());
	$('#familyInfoFormTable').find(".dob-class").each(function() {		    
	    if(!$( this ).hasClass( "k-datepicker" )){
	    	$( this ).kendoDatePicker();
		}
	 });
	$('#familyInfoFormTable').find(".dob-class").css("width", "205px");	
}

function populateSecondaryKidsInfoWithData(kidFirstName, kidLastName, kidDob, kidGender){
	var count = secKidCount;							
	$(document).find('.secondaryKidsInfoForm').each(function(i, obj) {				
		count = count +1;				
	});    
	
	var kidsInfoTableHtml = '';
	kidsInfoTableHtml += '<tr>';
	kidsInfoTableHtml += '<td colspan="4">';
	kidsInfoTableHtml += '<table border="0" width="101%" class="secondaryKidsInfoForm">';
	kidsInfoTableHtml += '<tr>';
	kidsInfoTableHtml += '<td>&nbsp;</td>';
	kidsInfoTableHtml += '<td>&nbsp;</td>';
	kidsInfoTableHtml += '<td>&nbsp;</td>';
	kidsInfoTableHtml += '<td>&nbsp;</td>';
	kidsInfoTableHtml += '</tr>';
	
	kidsInfoTableHtml += '<tr>';
	kidsInfoTableHtml += '<td><input type="text" class="form-control firstName personFirstName"  title="Please enter yout First Name" name="userLst['+count+'].personFirstName" id="firstName" maxlength="50" placeholder="First Name"  value="'+kidFirstName+'"/></td>';
	kidsInfoTableHtml += '<td>&nbsp;</td>';
	kidsInfoTableHtml += '<td><input type="text" class="form-control lastName personLastName"  title="Please enter yout Last Name" name="userLst['+count+'].personLastName" id="lastName" maxlength="50" placeholder="Last Name" value="'+kidLastName+'"/></td>';
	kidsInfoTableHtml += '<td><a class="remove-child-lnk">Remove Child &nbsp; <span>-</span></a></td>';
	kidsInfoTableHtml += '</tr>';
	
	kidsInfoTableHtml += '<tr>';
	kidsInfoTableHtml += '<td style="padding-bottom: 8px;"><input type="text" class="form-control dob-class dateOfBirth"  title="Please enter your Date of Birth" name="userLst['+count+'].dateOfBirth"  maxlength="50" placeholder="Date of Birth"  style="width: 205px; height: 30px; margin: 0; padding: 0;" value="'+kidDob+'"/></td>';
	kidsInfoTableHtml += '<td id="dop-er"></td>';

	kidsInfoTableHtml += '<td>';
	kidsInfoTableHtml += '<span>';
	if(kidGender == "Male") {
		kidsInfoTableHtml += '<span><span><input  name="userLst['+count+'].gender" checked class="form-control gender" style="width: 15px;" type="radio" value="Male"></span><span style="margin-left : 5px;">Male</span></span>';
		kidsInfoTableHtml += '<span><span><input  name="userLst['+count+'].gender" class="form-control gender" style="width : 15px;" type="radio" value="Female"></span><span style="margin-left : 5px;">Female</span></span>';
    }else{
    	kidsInfoTableHtml += '<span><span><input  name="userLst['+count+'].gender" class="form-control gender" style="width: 15px;" type="radio" value="Male"></span><span style="margin-left : 5px;">Male</span></span>';
		kidsInfoTableHtml += '<span><span><input  name="userLst['+count+'].gender" checked class="form-control gender" style="width : 15px;" type="radio" value="Female"></span><span style="margin-left : 5px;">Female</span></span>';
    }						
	kidsInfoTableHtml += '</span>';
	kidsInfoTableHtml += '</td>';	
	
	kidsInfoTableHtml += '<td>&nbsp;</td>';
	kidsInfoTableHtml += '</tr>';	
	kidsInfoTableHtml += '</table>';
	kidsInfoTableHtml += '</td>';
	kidsInfoTableHtml += '</tr>';
	
	kidsInfoTableHtml += '<tr style="display : none;">';
	kidsInfoTableHtml += '<td><input style="display:none;" name="userLst['+count+'].isAdult" value="false"/></td>';
	kidsInfoTableHtml += '<td>&nbsp;</td>';	
	kidsInfoTableHtml += '<td><input style="display:none;" name="userLst['+count+'].usrSequence" value="'+count+'" /></td>';		
	kidsInfoTableHtml += '<td>&nbsp;</td>';
	kidsInfoTableHtml += '</tr>';
	
	
	//var secondaryKidInfo = getSecondaryKidsInfoHtml(count);
	$('#familyInfoFormTable').css("display" , "inline-table");
	$('#familyInfoFormTable').append(kidsInfoTableHtml);
	$(".stepContainer").height($(".stepContainer").find("div:not(:hidden)").height());
	$('#familyInfoFormTable').find(".dob-class").each(function() {		    
	    if(!$( this ).hasClass( "k-datepicker" )){
	    	$( this ).kendoDatePicker();
		}
	 });
	$('#familyInfoFormTable').find(".dob-class").css("width", "205px");
}

function populatePrimaryMember(adultFirstName, adultLastName, adultPhoneNumber, adultEmail, adultDob, adultGender){
	$('#becomeMemberForm').find('#firstName').attr("value", adultFirstName);
	$('#becomeMemberForm').find('#lastName').attr("value", adultLastName);										    			
	$('#becomeMemberForm').find('#phoneNumber').attr("value", adultPhoneNumber);
	$('#becomeMemberForm').find('#email').attr("value", adultEmail);
	$('#becomeMemberForm').find('#dob').attr("value", adultDob);
	if(adultGender == "Male") {
		$('#becomeMemberForm').find('#genderM').prop('checked', true);
	}else{
		$('#becomeMemberForm').find('#genderF').prop('checked', true);
	}
	var dobMonthForm = $('#becomeMemberForm').find("#dobMonthForm").data("kendoDropDownList");
	var dobDateForm = $('#becomeMemberForm').find("#dobDateForm").data("kendoDropDownList");
	var dobYearForm = $('#becomeMemberForm').find("#dobYearForm").data("kendoDropDownList");    
	//10/14/1984
	if(adultDob){
		var dateArr = adultDob.split("/");
		dobMonthForm.select(dateArr[0] - 1);
		dobDateForm.select(dateArr[1] - 1);
		dobYearForm.search(dateArr[2]);	
		dobMonthForm.enable(false);
		dobDateForm.enable(false);
		dobYearForm.enable(false);
	}
}

function populateSecondaryMember(adultFirstName, adultLastName, adultPhoneNumber, adultEmail, adultDob, adultGender){
	$('#becomeMemberForm').find('#secFirstName').attr("value", adultFirstName);
	$('#becomeMemberForm').find('#secLastName').attr("value", adultLastName);
	$('#becomeMemberForm').find('#secDOB').attr("value", adultDob); 
	$('#becomeMemberForm').find('#secPhoneNumber').attr("value", adultPhoneNumber); 
	$('#becomeMemberForm').find('#secEmail').attr("value", adultEmail);					
	if(adultGender == "Male") {
		$('#becomeMemberForm').find('#secGenderM').prop('checked', true);
	}else{
		$('#becomeMemberForm').find('#secGenderF').prop('checked', true);
	}
	var dobMonthForm = $('#becomeMemberForm').find("#secDobMonthForm").data("kendoDropDownList");
	var dobDateForm = $('#becomeMemberForm').find("#secDobDateForm").data("kendoDropDownList");
	var dobYearForm = $('#becomeMemberForm').find("#secDobYearForm").data("kendoDropDownList");    
	//10/14/1984
	if(adultDob){
		var dateArr = adultDob.split("/");
		dobMonthForm.select(dateArr[0] - 1);
		dobDateForm.select(dateArr[1] - 1);
		dobYearForm.search(dateArr[2]);		
		dobMonthForm.enable(false);
		dobDateForm.enable(false);
		dobYearForm.enable(false);
	}
}

function populateThirdMember(adultFirstName, adultLastName, adultPhoneNumber, adultEmail, adultDob, adultGender){
	$('#becomeMemberForm').find('#thirdFirstName').attr("value", adultFirstName);
	$('#becomeMemberForm').find('#thirdLastName').attr("value", adultLastName);
	$('#becomeMemberForm').find('#thirdDOB').attr("value", adultDob); 
	$('#becomeMemberForm').find('#thirdPhoneNumber').attr("value", adultPhoneNumber); 
	$('#becomeMemberForm').find('#thirdEmail').attr("value", adultEmail);					
	if(adultGender  == "Male") {
		$('#becomeMemberForm').find('#thirdGenderM').prop('checked', true);
	}else{
		$('#becomeMemberForm').find('#thirdGenderF').prop('checked', true);
	}
	var dobMonthForm = $('#becomeMemberForm').find("#thirdDobMonthForm").data("kendoDropDownList");
	var dobDateForm = $('#becomeMemberForm').find("#thirdDobDateForm").data("kendoDropDownList");
	var dobYearForm = $('#becomeMemberForm').find("#thirdDobYearForm").data("kendoDropDownList");    
	//10/14/1984
	if(adultDob){
		var dateArr = adultDob.split("/");
		dobMonthForm.select(dateArr[0] - 1);
		dobDateForm.select(dateArr[1] - 1);
		dobYearForm.search(dateArr[2]);		
		dobMonthForm.enable(false);
		dobDateForm.enable(false);
		dobYearForm.enable(false);
	}
}

function populateYouthMember(adultFirstName, adultLastName, adultDob, adultGender, adultPhoneNumber, adultEmail){
	$('#becomeMemberForm').find('#secFirstName').attr("value", adultFirstName);
	$('#becomeMemberForm').find('#secLastName').attr("value", adultLastName);
	$('#becomeMemberForm').find('#secDOB').attr("value", adultDob); 						
	if(adultGender == "Male") {
		$('#becomeMemberForm').find('#secGenderM').prop('checked', true);
	}else{
		$('#becomeMemberForm').find('#secGenderF').prop('checked', true);
	}
	$('#becomeMemberForm').find('#secPhoneNumber').attr("value", adultPhoneNumber);
	$('#becomeMemberForm').find('#secEmail').attr("value", adultEmail);
	var dobMonthForm = $('#becomeMemberForm').find("#youthDobMonthForm").data("kendoDropDownList");
	var dobDateForm = $('#becomeMemberForm').find("#youthDobDateForm").data("kendoDropDownList");
	var dobYearForm = $('#becomeMemberForm').find("#youthDobYearForm").data("kendoDropDownList");    
	//10/14/1984
	if(adultDob){
		var dateArr = adultDob.split("/");
		dobMonthForm.select(dateArr[0] - 1);
		dobDateForm.select(dateArr[1] - 1);
		dobYearForm.search(dateArr[2]);		
		dobMonthForm.enable(false);
		dobDateForm.enable(false);
		dobYearForm.enable(false);
	}
}

function resetPrimaryMember(){
	$('#becomeMemberForm').find('#firstName').attr("value", "");
	$('#becomeMemberForm').find('#lastName').attr("value", "");										    			
	$('#becomeMemberForm').find('#phoneNumber').attr("value", "");
	$('#becomeMemberForm').find('#email').attr("value", "");
	$('#becomeMemberForm').find('#dob').attr("value", "");
	$('#becomeMemberForm').find('#password').attr("value", "");
	$('#becomeMemberForm').find('#confirm_password').attr("value", "");
	$('#becomeMemberForm').find('#genderM').prop('checked', false);
	$('#becomeMemberForm').find('#genderF').prop('checked', false);
	var dobMonthForm = $('#becomeMemberForm').find("#dobMonthForm").data("kendoDropDownList");
	var dobDateForm = $('#becomeMemberForm').find("#dobDateForm").data("kendoDropDownList");
	var dobYearForm = $('#becomeMemberForm').find("#dobYearForm").data("kendoDropDownList");
	dobMonthForm.select(0);
	dobDateForm.select(0);
	dobYearForm.select(0);
	dobMonthForm.enable(true);
	dobDateForm.enable(true);
	dobYearForm.enable(true);
	
}

function resetSecondaryMember(){
	$('#becomeMemberForm').find('#secFirstName').attr("value", "");
	$('#becomeMemberForm').find('#secLastName').attr("value", "");
	$('#becomeMemberForm').find('#secDOB').attr("value", ""); 
	$('#becomeMemberForm').find('#secPhoneNumber').attr("value", ""); 
	$('#becomeMemberForm').find('#secEmail').attr("value", "");		
	$('#becomeMemberForm').find('#secGenderM').prop('checked', false);
	$('#becomeMemberForm').find('#secGenderF').prop('checked', false);
	var dobMonthForm = $('#becomeMemberForm').find("#secDobMonthForm").data("kendoDropDownList");
	var dobDateForm = $('#becomeMemberForm').find("#secDobDateForm").data("kendoDropDownList");
	var dobYearForm = $('#becomeMemberForm').find("#secDobYearForm").data("kendoDropDownList");
	dobMonthForm.select(0);
	dobDateForm.select(0);
	dobYearForm.select(0);
	dobMonthForm.enable(true);
	dobDateForm.enable(true);
	dobYearForm.enable(true);	
}

function resetYouthMember(){
	$('#becomeMemberForm').find('#secFirstName').attr("value", "");
	$('#becomeMemberForm').find('#secLastName').attr("value", "");
	$('#becomeMemberForm').find('#secDOB').attr("value", "");
	$('#becomeMemberForm').find('#secPhoneNumber').attr("value", ""); 
	$('#becomeMemberForm').find('#secEmail').attr("value", "");	
	$('#becomeMemberForm').find('#youthPassword').attr("value", ""); 
	$('#becomeMemberForm').find('#youth_confirm_password').attr("value", "");				
	$('#becomeMemberForm').find('#secGenderM').prop('checked', false);
	$('#becomeMemberForm').find('#secGenderF').prop('checked', false);
	var dobMonthForm = $('#becomeMemberForm').find("#youthDobMonthForm").data("kendoDropDownList");
	var dobDateForm = $('#becomeMemberForm').find("#youthDobDateForm").data("kendoDropDownList");
	var dobYearForm = $('#becomeMemberForm').find("#youthDobYearForm").data("kendoDropDownList");
	dobMonthForm.select(0);
	dobDateForm.select(0);
	dobYearForm.select(0);
	dobMonthForm.enable(true);
	dobDateForm.enable(true);
	dobYearForm.enable(true);
}

function resetThirdMember(){
	$('#becomeMemberForm').find('#thirdFirstName').attr("value", "");
	$('#becomeMemberForm').find('#thirdLastName').attr("value", "");
	$('#becomeMemberForm').find('#thirdDOB').attr("value", ""); 
	$('#becomeMemberForm').find('#thirdPhoneNumber').attr("value", ""); 
	$('#becomeMemberForm').find('#thirdEmail').attr("value", "");		
	$('#becomeMemberForm').find('#thirdGenderM').prop('checked', false);
	$('#becomeMemberForm').find('#thirdGenderF').prop('checked', false);
	var dobMonthForm = $('#becomeMemberForm').find("#thirdDobMonthForm").data("kendoDropDownList");
	var dobDateForm = $('#becomeMemberForm').find("#thirdDobDateForm").data("kendoDropDownList");
	var dobYearForm = $('#becomeMemberForm').find("#thirdDobYearForm").data("kendoDropDownList"); 
	dobMonthForm.select(0);
	dobDateForm.select(0);
	dobYearForm.select(0);
	dobMonthForm.enable(true);
	dobDateForm.enable(true);
	dobYearForm.enable(true);	
}

function resetFirstKidFormElements(){
	$(".firstKidInfoForm").find(".personFirstName").attr("value","");
	$(".firstKidInfoForm").find(".personLastName").attr("value","");
	$(".firstKidInfoForm").find("input.dateOfBirth").attr("value","");
	var $radios = $(".firstKidInfoForm").find(".gender");
	$radios.filter('[value=Male]').prop('checked', false);
	$radios.filter('[value=Female]').prop('checked', false);	
	var dobMonthForm = $('#becomeMemberForm').find("#firstKidDobMonthForm").data("kendoDropDownList");
	var dobDateForm = $('#becomeMemberForm').find("#firstKidDobDayForm").data("kendoDropDownList");
	var dobYearForm = $('#becomeMemberForm').find("#firstKidDobYearForm").data("kendoDropDownList");
	dobMonthForm.select(0);
	dobDateForm.select(0);
	dobYearForm.select(0);
	dobMonthForm.enable(true);
	dobDateForm.enable(true);
	dobYearForm.enable(true);	
}

function adultCountSelectedError(count){
	var kendoWindow = $("<div />").kendoWindow({
    	title: "Error",
    	resizable: false,
    	modal: true,
    	width:400
    });   
$(document).find("#memberSelectDiv #memberSelectCount").html(count);
$("#member-select-ErrorBox").html($("#memberSelectDiv").html());
	kendoWindow.data("kendoWindow")
     .content($("#member-select-ErrorBox").html())
     .center().open();
		
    kendoWindow
    .find(".confirm-member-select-Error")
    .click(function() {
    	if ($(this).hasClass("confirm-member-select-Error")) {         		
    		kendoWindow.data("kendoWindow").close();
    	}
    })
    .end();
}


function kidSelectedError(){
	var kendoWindow = $("<div />").kendoWindow({
    	title: "Error",
    	resizable: false,
    	modal: true,
    	width:400
    }); 
	kendoWindow.data("kendoWindow")
     .content($("#member-kid-select-ErrorBox").html())
     .center().open();
		
    kendoWindow
    .find(".confirm-kid-select-ok")
    .click(function() {
    	//if ($(this).hasClass("confirm-member-select-Error")) {         		
    		kendoWindow.data("kendoWindow").close();
    	//}
    })
    .end();
}

function youthSelectedError(){
	var kendoWindow = $("<div />").kendoWindow({
    	title: "Error",
    	resizable: false,
    	modal: true,
    	width:400
    }); 
	kendoWindow.data("kendoWindow")
     .content($("#member-youth-select-ErrorBox").html())
     .center().open();
		
    kendoWindow
    .find(".confirm-youth-select-ok")
    .click(function() {
    	//if ($(this).hasClass("confirm-member-select-Error")) {         		
    		kendoWindow.data("kendoWindow").close();
    	//}
    })
    .end();
}

function getProductsByLocationId(){
	$(".k-loading-mask").show();
	$(".k-loading-text").html("Please wait");	
	$("#tcloginErrorSpan-payment").css("display", "none");	
	$( "#tcloginErrorSpan-payment" ).html("");
	$("#panels").html("");
	var locationDropdownlist = $("#location").data("kendoDropDownList");
	$.ajax({
		  type: "GET",
		  url: "getProductDetailsByLocation",	
		  data: {"locationId" : $("#location").kendoDropDownList().val()},
		  success: function( data ) {
			  //console.log(data);
			  var pricingBoxHtml = '';
			  var signUpProductId = $("#signupProductId").attr("value");	
			  var signUpProductType = $('#signUpProductType').val();
			  var jsonDataArr = jQuery.parseJSON(data);
			  if(jsonDataArr != null && jsonDataArr.length > 0){
				  for(var k = 0; k<jsonDataArr.length; k++){
					  //console.log(jsonDataArr[k].id); 
					  if(jsonDataArr[k].id == signUpProductId){
						  //signedUpTierPriceInfo = jsonDataArr[k].tierPrice;
						  //signedUpJoiningFeeInfo = jsonDataArr[k].joiningPrice;
						  
						  signedUpProdctId = jsonDataArr[k].id;
						  signedUpProductName = jsonDataArr[k].productName;	    						  
						  signedUpAutoPromoDiscount = jsonDataArr[k].autoPromoDiscount;
						  signedUpAllAreaPrice = jsonDataArr[k].allAreaPrice;
						  signedUpBayAreaPrice = jsonDataArr[k].bayAreaPrice;
						 
	  					  
						  if(jsonDataArr[k].signupPrice){    						 
							  for(var p = 0; p<jsonDataArr[k].signupPrice.length; p++){	
								  if(jsonDataArr[k].signupPrice[p].Monthly){
									  signedUpTierPriceInfo = jsonDataArr[k].signupPrice[p].Monthly;
								  }else if(jsonDataArr[k].signupPrice[p].Annual){
									  signedUpTierPriceInfo = jsonDataArr[k].signupPrice[p].Annual;
								  }
								  if(jsonDataArr[k].signupPrice[p].joiningPrice && jsonDataArr[k].signupPrice[p].joiningPrice > 0){
									  signedUpJoiningFeeInfo = jsonDataArr[k].signupPrice[p].joiningPrice;								 
									  break;	
								  }								  						  
							  }	    						  
						  }
						  /*if(signUpProductType == 'All Branches'){
	  						signedUpProductsValue = signedUpAllAreaPrice;
	  					  }else if(signUpProductType == 'Bay Area') {
	  						signedUpProductsValue = signedUpBayAreaPrice;
	  					  }else {
	  						signedUpProductsValue = signedUpTierPriceInfo;
	  					  }*/
						  break;
					  }
				  }
				  //console.log("signedUpProductsValue" + signedUpProductsValue);
				  /*var signUpProductType = $('#signUpProductType').val();
				  if(signUpProductType == 'All Branches' || signUpProductType == 'Bay Area') {
					  $( ".is-current-membership-change" ).addClass( "product-register-div" );
				  }else{
					  $( ".is-current-membership-change" ).removeClass( "product-register-div" );
				  }*/
				  
				 
				  
				  for(var i = 0; i<jsonDataArr.length; i++){
					  var monthlyTierPrice = 0;
					  var monthlyJoiningFee = 0;
					  var annualTierPrice = 0;
					  var annualJoiningFee = 0;
					  var billingFreqAllArea = '';
					  var billingFreqBayArea = '';
					  var thisBranchMonthlyBillingFreq = '';
					  var thisBranchAnnualBillingFreq = '';
					  var allAreamonthlyBillingFreq = '';
					  var allAreaannualBillingFreq = '';
					  var bayAreamonthlyBillingFreq = '';
					  var bayAreaannualBillingFreq = '';
					  
					  var allAreamonthlyTierPrice = 0;
					  var allAreamonthlyJoiningFee = 0;
					  var allAreaannualTierPrice = 0;
					  var allAreaannualJoiningFee = 0;
					  
					  var bayAreamonthlyTierPrice = 0;
					  var bayAreamonthlyJoiningFee = 0;
					  var bayAreaannualTierPrice = 0;
					  var bayAreaannualJoiningFee = 0;
					  //console.log(jsonDataArr[i].id);
					  //console.log(jsonDataArr[i].signupPrice);
					  var productPricingOptionRadioHtml = '';
					  console.log("  signedUpProdctId "+signedUpProdctId);
					  var signedUpPricingOptionValue = $("#signedUpPricingOption").val();
					  var thisBranchProductItemId = jsonDataArr[i].id;
					  var allAreaProductItemId = 0;
					  var bayAreaProductItemId = 0;
					  
					  if(jsonDataArr[i].signupPrice){    						 
						  for(var s = 0; s<jsonDataArr[i].signupPrice.length; s++){
							  //console.log(jsonDataArr[i].signupPrice);
							  if(jsonDataArr[i].signupPrice[s].Monthly){
								  if(productPricingOptionRadioHtml.indexOf('Monthly') === -1 && signedUpPricingOptionValue == 'Monthly'){
									  productPricingOptionRadioHtml = productPricingOptionRadioHtml + '<option value="Monthly" selected="selected">Monthly</option>';
								  }								  
								  monthlyTierPrice = jsonDataArr[i].signupPrice[s].Monthly;
								  monthlyJoiningFee = jsonDataArr[i].signupPrice[s].joiningPrice;
								  if(jsonDataArr[i].signupPrice[s].billingFrequency == 'Recurring')
									  thisBranchMonthlyBillingFreq = jsonDataArr[i].signupPrice[s].billingFrequency;
								  //console.log("  4300 billingFreqThisBranch "+billingFreqThisBranch);
							  }else if(jsonDataArr[i].signupPrice[s].Annual){
								  if(productPricingOptionRadioHtml.indexOf('Annual') === -1 && signedUpPricingOptionValue == 'Annual'){
									  productPricingOptionRadioHtml = productPricingOptionRadioHtml + '<option value="Annual">Annual</option>';
								  }								  
								  annualTierPrice = jsonDataArr[i].signupPrice[s].Annual;
								  annualJoiningFee = jsonDataArr[i].signupPrice[s].joiningPrice;
								  if(jsonDataArr[i].signupPrice[s].billingFrequency == 'Recurring')
									  thisBranchAnnualBillingFreq = jsonDataArr[i].signupPrice[s].billingFrequency;
								  //console.log("  4311 billingFreqThisBranch "+billingFreqThisBranch);
							  }							  
						  }	    						  
					  }
					 
					  if(jsonDataArr[i].allAreaPrice){    						 
						  for(var a = 0; a<jsonDataArr[i].allAreaPrice.length; a++){
							  if(jsonDataArr[i].allAreaPrice[a].Monthly){	    								  
								  allAreamonthlyTierPrice = jsonDataArr[i].allAreaPrice[a].Monthly;
								  allAreamonthlyJoiningFee = jsonDataArr[i].allAreaPrice[a].joiningPrice;
								  if(jsonDataArr[i].allAreaPrice[a].billingFrequency == 'Recurring')
									  allAreamonthlyBillingFreq = jsonDataArr[i].allAreaPrice[a].billingFrequency;
								  //console.log("  4322 billingFreqAllArea "+billingFreqAllArea);
							  }else if(jsonDataArr[i].allAreaPrice[a].Annual){	    								  
								  allAreaannualTierPrice = jsonDataArr[i].allAreaPrice[a].Annual;
								  allAreaannualJoiningFee = jsonDataArr[i].allAreaPrice[a].joiningPrice;
								  if(jsonDataArr[i].allAreaPrice[a].billingFrequency == 'Recurring')
									  allAreaannualBillingFreq = jsonDataArr[i].allAreaPrice[a].billingFrequency;
								  //console.log("  4327 billingFreqAllArea "+billingFreqAllArea);
							  }else if(jsonDataArr[i].allAreaPrice[a].allBranchItemDetailId){	    								  
								  allAreaProductItemId = jsonDataArr[i].allAreaPrice[a].allBranchItemDetailId;	
								  //console.log("allAreaProductItemId 11" + allAreaProductItemId);
							  }
							  
						  }	    						  
						}
					  if(jsonDataArr[i].bayAreaPrice){    						 
						  for(var b = 0; b<jsonDataArr[i].bayAreaPrice.length; b++){
							  if(jsonDataArr[i].bayAreaPrice[b].Monthly){	    								  
								  bayAreamonthlyTierPrice = jsonDataArr[i].bayAreaPrice[b].Monthly;
								  bayAreamonthlyJoiningFee = jsonDataArr[i].bayAreaPrice[b].joiningPrice;
								  if(jsonDataArr[i].bayAreaPrice[b].billingFrequency == 'Recurring')
									  bayAreamonthlyBillingFreq = jsonDataArr[i].bayAreaPrice[b].billingFrequency;
//								  console.log("  4338 billingFreqBayArea "+billingFreqBayArea);
							  }else if(jsonDataArr[i].bayAreaPrice[b].Annual){	    								  
								  bayAreaannualTierPrice = jsonDataArr[i].bayAreaPrice[b].Annual;
								  bayAreaannualJoiningFee = jsonDataArr[i].bayAreaPrice[b].joiningPrice;
								  if(jsonDataArr[i].bayAreaPrice[b].billingFrequency == 'Recurring')
									  bayAreaannualBillingFreq = jsonDataArr[i].bayAreaPrice[b].billingFrequency;
//								  console.log("  4343 billingFreqBayArea "+billingFreqBayArea);
							  }else if(jsonDataArr[i].bayAreaPrice[b].bayAreaItemDetailId){	    								  
								  bayAreaProductItemId = jsonDataArr[i].bayAreaPrice[b].bayAreaItemDetailId;
								  //console.log("bayAreaProductItemId 11" + bayAreaProductItemId);
							  }
						  }	    						  
						}
					  
					  monthlyJoiningFee = monthlyJoiningFee - parseFloat(signedUpJoiningFeeInfo);
					  annualJoiningFee = annualJoiningFee - parseFloat(signedUpJoiningFeeInfo);
					  
					  //alert(monthlyJoiningFee);
					  if(monthlyJoiningFee < 0){
						  monthlyJoiningFee = 0;
					  }
					  if( annualJoiningFee < 0){
						  annualJoiningFee = 0;
					  }
					  
					  
					  
					  //console.log(jsonDataArr[i].id);  
					  pricingBoxHtml += '<li>';
					  //if(jsonDataArr[i].id != signUpProductId){
					  if(thisBranchProductItemId != signUpProductId && allAreaProductItemId != signUpProductId && bayAreaProductItemId != signUpProductId){
						  pricingBoxHtml += '<div class="k-block k-block-div" style="position: relative;">';
					  }else {	    					  
						  pricingBoxHtml += '<div class="k-block k-block-div" style="position: relative; background: lightgreen;">';
					  }
					  var prodHeaderInfo = '';
					  if(jsonDataArr[i].productName != null && (jsonDataArr[i].productName =='One Adult w/ Kids' || jsonDataArr[i].productName =='Two Adults w/ Kids' || jsonDataArr[i].productName =='Three Adults w/ or w/o kids')){
						  if(jsonDataArr[i].productName =='One Adult w/ Kids'){
							  prodHeaderInfo = 'One Adult<br />w/ Kids';
						  }else if(jsonDataArr[i].productName =='Two Adults w/ Kids'){
							  prodHeaderInfo = 'Two Adults<br />w/ Kids';
						  }else{
							  prodHeaderInfo = 'Three Adults<br />w/ or w/o kids';
						  }
					  }else{
						  prodHeaderInfo = jsonDataArr[i].productName;
					  }
					  if(i % 2 == 0){
						  pricingBoxHtml += '<div class="k-header k-shadow k-header-custom-gray" >'+prodHeaderInfo+'</div>';
					  }else{
						  pricingBoxHtml += '<div class="k-header k-shadow k-header-custom-orange" >'+prodHeaderInfo+'</div>';
					  }			  
					  
					  
					  pricingBoxHtml += '<div>';
					  //if(jsonDataArr[i].id == signUpProductId && jsonDataArr[i].id == signUpProductId){
					  if(thisBranchProductItemId == signUpProductId || allAreaProductItemId == signUpProductId || bayAreaProductItemId == signUpProductId){
						  pricingBoxHtml += '<b><span style="font-size: 12.5px;" class="isCurrentMembership">Current Membership</span></b><br />';
					  }else {	    					  
						  pricingBoxHtml += '<b><span style="display:none;" class="isCurrentMembership">Membership</span></b>';
					  }
					  
					  pricingBoxHtml += '<span style="font-size: x-large; font-family: cursive;">$ <div class="price-div">'+ monthlyTierPrice +'</div></span>';
					  
					  pricingBoxHtml += '<div class="prod-id-div" style="display:none">'+thisBranchProductItemId+'</div>';
					  pricingBoxHtml += '<div class="prod-name-div" style="display:none">'+jsonDataArr[i].productName+'</div>';
					  pricingBoxHtml += '<div class="prod-total-price-div" style="display:none">'+jsonDataArr[i].productPrice+'</div>';
					  pricingBoxHtml += '<div class="prod-tandc-div" style="display:none">'+jsonDataArr[i].tandc+'</div>';
					  pricingBoxHtml += '<div class="prod-autoPromoDiscount-div" style="display:none">'+jsonDataArr[i].autoPromoDiscount+'</div>';
					  pricingBoxHtml += '<div class="prod-type-div" style="display:none">'+jsonDataArr[i].productType+'</div>';
					  pricingBoxHtml += '<div class="prod-itemDetailsId-div" style="display:none">'+jsonDataArr[i].itemDetailsId+'</div>';
					  
					  pricingBoxHtml += '<div class="prod-registration-fee-div" style="display:none">'+jsonDataArr[i].registrationPrice+'</div>';
					  pricingBoxHtml += '<div class="prod-deposit-amount-div" style="display:none">'+jsonDataArr[i].depositPrice+'</div>';
					  pricingBoxHtml += '<div class="prod-annual-tier-price-div" style="display:none">'+annualTierPrice+'</div>';	    					    					  
					  pricingBoxHtml += '<div class="prod-annual-join-price-div" style="display:none">'+annualJoiningFee+'</div>';	
					  pricingBoxHtml += '<div class="prod-monthly-billing-freq-div" style="display:none">'+thisBranchMonthlyBillingFreq+'</div>';
					  pricingBoxHtml += '<div class="prod-annual-billing-freq-div" style="display:none">'+thisBranchAnnualBillingFreq+'</div>';
					  pricingBoxHtml += '<div class="prod-price-option-dropdown-div" style="display:none">'+productPricingOptionRadioHtml+'</div>';	 					 
					  //pricingBoxHtml += '<div class="prod-billing-freq-this-branch-div" style="display:none">'+billingFreqThisBranch+'</div>';
					  //pricingBoxHtml += '<div class="prod-annual-billing-freq-div" style="display:none">'+annualBillingFreq+'</div>';
					  //pricingBoxHtml += '<div class="prod-billing-freqency-div" style="display:none">'+billingFreqency+'</div>';
					  pricingBoxHtml += '<div class="product-description-div">'+jsonDataArr[i].productDescription+'</div>';
					  pricingBoxHtml += '<div class="product-location-div">'+locationDropdownlist.text()+'</div>';
					  pricingBoxHtml += '<div class="border-bottom-line"></div>';
					  pricingBoxHtml += '<div class="product-join-fee-text-div">One Time Join Fee</div>';
					  //pricingBoxHtml += '<span style="font-size: small; font-family: cursive;">$ <div class="product-join-fee-text-val-div">'+jsonDataArr[i].joiningPrice+'</div></span>';
					  pricingBoxHtml += '<span style="font-size: small; font-family: cursive;">$ <span class="product-join-fee-text-val-div">'+monthlyJoiningFee+'</span></span>';
					  //pricingBoxHtml += '<span class="product-join-fee-text-val-div" style="display:none;">'+jsonDataArr[i].joiningPrice+'</span>';
					 
					  
					  pricingBoxHtml += '<div>';
					  pricingBoxHtml += '<table border="0" style="text-align: left;" id="productPriceTable">';
					  //if(jsonDataArr[i].allAreaPrice != null){
					  console.log(" thisBranchProductItemId "+ thisBranchProductItemId);
					  console.log(" allAreaProductItemId "+ allAreaProductItemId);
					  console.log(" bayAreaProductItemId "+ bayAreaProductItemId);
					  console.log(" signUpProductId "+ signUpProductId);
					  if(jsonDataArr[i].allAreaPrice != null && typeof jsonDataArr[i].allAreaPrice != "undefined"){
    					  pricingBoxHtml += '<tr>';
    					  if((thisBranchProductItemId == signUpProductId || allAreaProductItemId == signUpProductId || bayAreaProductItemId == signUpProductId) && signUpProductType == 'All Branches'){
    						  pricingBoxHtml += '<td><input type="radio" name="paymentTypeSelect" id="paymentTypeSelectAllArea'+allAreaProductItemId+'" checked  class="paymentTypeSelectClass" value="AllArea"/><span class="itemDetailProduct-Id" style="display:none;">'+allAreaProductItemId +'</span></td>';
    					  }else{
							  pricingBoxHtml += '<td><input type="radio" name="paymentTypeSelect" id="paymentTypeSelectAllArea'+allAreaProductItemId+'" class="paymentTypeSelectClass" value="AllArea"/><span class="itemDetailProduct-Id" style="display:none;">'+allAreaProductItemId +'</span></td>';
						  }
    					  
    					  pricingBoxHtml += '<td width="65%" style="font-size: 11px;">All Branches</td>';
    					  pricingBoxHtml += '<td width="30%" style="font-size: smaller;font-weight: bolder;">$ <span class="all-area-price-td">'+allAreamonthlyTierPrice+'</span><input type="hidden" id="billingFreqAllArea"></td>';
    					  pricingBoxHtml += '</tr>';
					  }
					  //if(jsonDataArr[i].bayAreaPrice != null){
					  if(jsonDataArr[i].bayAreaPrice != null && typeof jsonDataArr[i].bayAreaPrice != "undefined"){
    					  pricingBoxHtml += '<tr>';    					  
    					  if((thisBranchProductItemId == signUpProductId || allAreaProductItemId == signUpProductId || bayAreaProductItemId == signUpProductId) && signUpProductType == 'Bay Area') {
    						  pricingBoxHtml += '<td><input type="radio" name="paymentTypeSelect" id="paymentTypeSelectBayArea'+bayAreaProductItemId+'" checked class="paymentTypeSelectClass" value="BayArea"/><span class="itemDetailProduct-Id" style="display:none;">'+bayAreaProductItemId +'</span></td>';
    					  }else {
    						  pricingBoxHtml += '<td><input type="radio" name="paymentTypeSelect" id="paymentTypeSelectBayArea'+bayAreaProductItemId+'" class="paymentTypeSelectClass" value="BayArea"/><span class="itemDetailProduct-Id" style="display:none;">'+bayAreaProductItemId +'</span></td>';
    					  }
    					  
    					  pricingBoxHtml += '<td width="65%" style="font-size: 11px;">Bay Area</td>';
    					  pricingBoxHtml += '<td width="30%" style="font-size: smaller;font-weight: bolder;">$ <span class="bay-area-price-td">'+bayAreamonthlyTierPrice+'</span><input type="hidden" id="billingFreqBayArea"></td>';
    					  pricingBoxHtml += '</tr>'; 
					  }
					  
					  pricingBoxHtml += '<tr>';
					  
					  if((thisBranchProductItemId == signUpProductId || allAreaProductItemId == signUpProductId || bayAreaProductItemId == signUpProductId) && signUpProductType == 'This Branch Only') {
						  pricingBoxHtml += '<td><input type="radio" name="paymentTypeSelect" id="paymentTypeSelectThisBranchOnly'+jsonDataArr[i].id+'" checked class="paymentTypeSelectClass" value="ThisBranchOnly"/><span class="itemDetailProduct-Id" style="display:none;">'+jsonDataArr[i].id +'</span></td>';	    					 	    					  
					  }else {
						  pricingBoxHtml += '<td><input type="radio" name="paymentTypeSelect" id="paymentTypeSelectThisBranchOnly'+jsonDataArr[i].id+'" class="paymentTypeSelectClass" value="ThisBranchOnly"/><span class="itemDetailProduct-Id" style="display:none;">'+jsonDataArr[i].id +'</span></td>';
					  }
					  
					  pricingBoxHtml += '<td width="65%" style="font-size: 11px;">This Branch Only</td>';
					  pricingBoxHtml += '<td width="30%" style="font-size: smaller;font-weight: bolder;">$ <span class="this-branch-price-td">'+monthlyTierPrice+'</span><input type="hidden" id="billingFreqThisBranch"></td>';
					  pricingBoxHtml += '</tr>';
					  pricingBoxHtml += '</table>'; 
					  pricingBoxHtml += '</div>'; 
						    					  
					  if(i % 2 == 0){
						  pricingBoxHtml += '<div class="k-button product-register-div" style="position: absolute; bottom: 8px; right: 29px; right: 29px; text-shadow: none; background-color: #929292 !important; color: #ffffff !important; background-image:none !important; font-weight:bold !important;">Register</div>';
						  /*if(jsonDataArr[i].id != signUpProductId){
							  pricingBoxHtml += '<div class="k-button product-register-div" style="position: absolute; bottom: 8px; right: 29px; right: 29px; text-shadow: none; background-color: #929292 !important; color: #ffffff !important; background-image:none !important; font-weight:bold !important;">Register</div>';
    					  }else {	    					  
    						  pricingBoxHtml += '<div class="k-button is-current-membership-change" style="position: absolute; bottom: 8px; font-size: 11px;  right: 25px; text-shadow: none; background-color: #929292 !important; color: #ffffff !important; background-image:none !important; font-weight:bold !important;">Change Price</div>';
    					  }*/
						  
					  }else{
						  pricingBoxHtml += '<div class="k-button product-register-div" style="position: absolute; bottom: 8px;  right: 29px; text-shadow: none; background-color: #eb8120 !important; color: #ffffff !important; background-image:none !important; font-weight:bold !important;">Register</div>';
						  /*if(jsonDataArr[i].id != signUpProductId){
							  pricingBoxHtml += '<div class="k-button product-register-div" style="position: absolute; bottom: 8px;  right: 29px; text-shadow: none; background-color: #eb8120 !important; color: #ffffff !important; background-image:none !important; font-weight:bold !important;">Register</div>';
    					  }else {	    					  
    						  pricingBoxHtml += '<div class="k-button is-current-membership-change" style="position: absolute;  bottom: 8px;  right: 25px; font-size: 11px; text-shadow: none; background-color: #eb8120 !important; color: #ffffff !important; background-image:none !important; font-weight:bold !important;">Change Price</div>';
    					  }*/
						  
					  }
					  pricingBoxHtml += '</div>';
					  pricingBoxHtml += '</div>';
					  pricingBoxHtml += '</li>';
				  }
				  $("#panels").html(pricingBoxHtml);
				  
			  }else {			  
				  $("#panels").html("No Product Information found for selected Location. Please select different Location.");
				  //pricingOption.select(0);
			  }
			  $(".k-loading-mask").hide();
		  },
		  error: function( xhr,status,error ){		 
			  $("#tcloginErrorSpan-payment").css("display", "block");	
			  $( "#tcloginErrorSpan-payment" ).html("There was some error. Please try again later");							  		 
		  }
	
});
}


function selectPricingOptionAndPopulateEndDate(){
	$("#paymentFrequencySelect").kendoDropDownList();
    var paymentFreq = $("#paymentFrequencySelect").data("kendoDropDownList");
    //paymentFreq.select(0);
    //$("#membershipFrequencyId").attr("value","Monthly");
    
    $("#paymentFrequencyTd").html(paymentFreq.text());
	$("#sumPaymentFreqTd").html(paymentFreq.text());    
	//$("#paymentBillingcycleTr").css("display", "none");
	//$("#paymentBillingcycleTd").html("");
	//console.log(paymentFreq.text());
	if(paymentFreq.text() == 'Annual'){
		$("#endDateDiv").css("display","inline");
		$("#membershipFrequencyId").attr("value","Annual");
		if(selectedAnnualTierPrice != null){    			
			$("#sumTierPriceTd").html(parseFloat(selectedAnnualTierPrice));
		}		
		if(selectedAnnualJoinPrice != null){    			
			$("#sumJoinFeeTd").html(parseFloat(selectedAnnualJoinPrice));
		}
		//$("#end").attr("value", "");
		var str = $("#start").val();  		
	
	    if(str != null) {
	
	        var parts = str.split("-");
	
	        var year = parts[0] && parseInt( parts[0], 10 );
	        var month = parts[1] && parseInt( parts[1], 10 );
	        var day = parts[2] && parseInt( parts[2], 10 );
	        var duration = 1;
	
	        if( day <= 31 && day >= 1 && month <= 12 && month >= 1 ) {
	
	            var expiryDate = new Date( year, month - 1, day );
	            expiryDate.setFullYear( expiryDate.getFullYear() + duration );
	
	            var day = ( '0' + expiryDate.getDate() ).slice( -2 );
	            var month = ( '0' + ( expiryDate.getMonth() + 1 ) ).slice( -2 );
	            var year = expiryDate.getFullYear();
	
	            $("#end").val(year + "-" +month + "-" + day);
	            //alert($("#end").val());
	            $("#end").data('kendoDatePicker').value($("#end").val());
	
	        } else {
	            alert("Error Occured while setting Membership End Date");
	        }
	    }
	}else{
		$("#endDateDiv").css("display","none");    		
		$("#sumTotalPriceTd").html(selectedProductTotalPrice);
		//$("#paymentBillingcycleTd").html($("#start").val() + "(Of each month)");
		$("#membershipFrequencyId").attr("value","Monthly");
		//$("#end").attr("value", "");    	
		if(selectedTierPriceInfo != null){    			
			$("#sumTierPriceTd").html(parseFloat(selectedTierPriceInfo));
		}    
	}
}


function populateAdditionalKidInfo(val){   
		var thisObj = $(val);
   		var kidForm = thisObj.parent().parent().parent().parent().parent().parent().find('.secondaryKidsInfoForm');
   		
   		var selectContactKid = thisObj.data("kendoDropDownList");
   		if(selectContactKid){
           	var contactVal = selectContactKid.value();
       		//var contactVal = $(".selectContactKid").data("kendoDropDownList").value();
           	//console.log(contactVal);
           	var flag = true;
           	var contactInfoArr = contactVal.split("\|\|");
           	$(document).find('select.selectContactDropDw').each(function(i, obj) {				
        		var selectedContacts = $(obj).data("kendoDropDownList");
        		var dropDownVal = selectedContacts.value();
        		var dropDownArr = dropDownVal.split("\|\|");       		
        		if(dropDownVal != 0 && dropDownVal != 'Other' && thisObj.attr("id") != obj.id && contactInfoArr[contactInfoArr.length - 1] == dropDownArr[dropDownArr.length - 1]){
        			//alert("Contact Already selected");
        			duplicateContactSelectedError();        			
        			flag = false;
        		}
    		});
           	  
           	
           	if(contactVal != 0 && contactVal != 'Other' && flag){           		
           		kidForm.find("input.firstName").attr("value",contactInfoArr[0]);
           		kidForm.find("input.lastName").attr("value",contactInfoArr[1]);
           		kidForm.find("input.dateOfBirth").attr("value",contactInfoArr[2]);
           		var kidGender = contactInfoArr[3];
           		var $radios = kidForm.find(".gender");
           		if(kidGender == "Male") {
           			$radios.filter('[value=Male]').prop('checked', true);
           		}else{
           			$radios.filter('[value=Female]').prop('checked', true);
           		}
           		var dobMonthForm = kidForm.find("select.kidDobMonthForm").data("kendoDropDownList");
           		var dobDateForm = kidForm.find("select.kidDobDateForm").data("kendoDropDownList");
           		var dobYearForm = kidForm.find("select.kidDobYearForm").data("kendoDropDownList");    
           		//10/14/1984
           		if(contactInfoArr[2]){
           			var dateArr = contactInfoArr[2].split("/");
           			dobMonthForm.select(dateArr[0] - 1);
           			dobDateForm.select(dateArr[1] - 1);
           			dobYearForm.search(dateArr[2]);		
           			dobMonthForm.enable(false);
           			dobDateForm.enable(false);
           			dobYearForm.enable(false);
           		}
           		//disable the form elements - Read only
           		kidForm.find("input.firstName").prop('readonly', true);
           		kidForm.find("input.lastName").prop('readonly', true);
           		kidForm.find("input.dateOfBirth").attr('readonly','readonly');
           		$radios.filter('[value=Male]').prop('readonly', true);
           		$radios.filter('[value=Female]').prop('readonly', true);        
           		//var datepicker = kidForm.find("input.dateOfBirth").data("kendoDatePicker");
           		//datepicker.enable(true);
           	}else if(contactVal == 0 || contactVal == 'Other' || flag == false){
           		kidForm.find("input.firstName").attr("value","");
           		kidForm.find("input.lastName").attr("value","");
           		kidForm.find("input.dateOfBirth").attr("value","");
           		var $radiosReset = kidForm.find(".gender");
           		$radiosReset.filter('[value=Male]').prop('checked', false);
           		$radiosReset.filter('[value=Female]').prop('checked', false);	 
           		
           		kidForm.find("input.firstName").prop('readonly', false);
           		kidForm.find("input.lastName").prop('readonly',  false);
           		kidForm.find("input.dateOfBirth").attr('readonly', false);
           		$radiosReset.filter('[value=Male]').prop('readonly',  false);
           		$radiosReset.filter('[value=Female]').prop('readonly', true);
           		
           		var dobMonthForm = kidForm.find("select.kidDobMonthForm").data("kendoDropDownList");
           		var dobDateForm = kidForm.find("select.kidDobDateForm").data("kendoDropDownList");
           		var dobYearForm = kidForm.find("select.kidDobYearForm").data("kendoDropDownList");
           		dobMonthForm.select(0);
           		dobDateForm.select(0);
           		dobYearForm.select(0);
           		dobMonthForm.enable(true);
       			dobDateForm.enable(true);
       			dobYearForm.enable(true);
       			
       			var dob = dobMonthForm.value()+ "/" +dobDateForm.value()+ "/" +dobYearForm.value();
       			kidForm.find("input.dateOfBirthValidation").attr('value', dob);       			
           		//var datepicker = kidForm.find("input.dateOfBirth").data("kendoDatePicker");
           		//datepicker.enable(false);
           	}
   		}       	
  } 


function duplicateContactSelectedError(){
	var kendoWindow = $("<div />").kendoWindow({
    	title: "Error",
    	resizable: false,
    	modal: true,
    	width:400
    }); 
	kendoWindow.data("kendoWindow")
     .content($("#contact-select-duplicate-ErrorBox").html())
     .center().open();
		
    kendoWindow
    .find(".contact-select-duplicate-ErrorBox-ok")
    .click(function() {
    	//if ($(this).hasClass("confirm-member-select-Error")) {         		
    		kendoWindow.data("kendoWindow").close();
    	//}
    })
    .end();
}


function getDOBHtml(yearId,  monthId, dateId){
	var dobInfoHtml = '';
	dobInfoHtml += '<span class="">Date of Birth : </span> <br />';	
	dobInfoHtml += '<span>';
	dobInfoHtml += '<select name="dobMonth" id="'+monthId+'" style="width :90px;">';
	dobInfoHtml += '<option value="1">January</option>';
	dobInfoHtml += '<option value="2">February</option>';
	dobInfoHtml += '<option value="3">March</option>';
	dobInfoHtml += '<option value="4">April</option>';
	dobInfoHtml += '<option value="5">May</option>';
	dobInfoHtml += '<option value="6">June</option>';
	dobInfoHtml += '<option value="7">July</option>';
	dobInfoHtml += '<option value="8">August</option>';
	dobInfoHtml += '<option value="9">September</option>';
	dobInfoHtml += '<option value="10">October</option>';
	dobInfoHtml += '<option value="11">November</option>';
	dobInfoHtml += '<option value="12">December</option>';
	dobInfoHtml += '</select>';
	dobInfoHtml += '</span>';
	
	dobInfoHtml += '<span>';
	dobInfoHtml += '<select name="dobDate" id="'+dateId+'" style="width :45px;">';
	dobInfoHtml += '<option value="01">1</option>';
	dobInfoHtml += '<option value="02">2</option>';
	dobInfoHtml += '<option value="03">3</option>';
	dobInfoHtml += '<option value="04">4</option>';
	dobInfoHtml += '<option value="05">5</option>';
	dobInfoHtml += '<option value="06">6</option>';
	dobInfoHtml += '<option value="07">7</option>';
	dobInfoHtml += '<option value="08">8</option>';
	dobInfoHtml += '<option value="09">9</option>';
	dobInfoHtml += '<option value="10">10</option>';
	dobInfoHtml += '<option value="11">11</option>';
	dobInfoHtml += '<option value="12">12</option>';	
	dobInfoHtml += '<option value="13">13</option>';
	dobInfoHtml += '<option value="14">14</option>';
	dobInfoHtml += '<option value="15">15</option>';
	dobInfoHtml += '<option value="16">16</option>';
	dobInfoHtml += '<option value="17">17</option>';
	dobInfoHtml += '<option value="18">18</option>';
	dobInfoHtml += '<option value="19">19</option>';
	dobInfoHtml += '<option value="20">20</option>';
	dobInfoHtml += '<option value="21">21</option>';
	dobInfoHtml += '<option value="22">22</option>';
	dobInfoHtml += '<option value="23">23</option>';
	dobInfoHtml += '<option value="24">24</option>';
	dobInfoHtml += '<option value="25">25</option>';
	dobInfoHtml += '<option value="26">26</option>';
	dobInfoHtml += '<option value="27">27</option>';
	dobInfoHtml += '<option value="28">28</option>';
	dobInfoHtml += '<option value="29">29</option>';
	dobInfoHtml += '<option value="30">30</option>';
	dobInfoHtml += '<option value="31">31</option>';	
	dobInfoHtml += '</select>';	
	dobInfoHtml += '</span>';
	
	dobInfoHtml += '<span>';
	dobInfoHtml += '<select name="dobYear" id="'+yearId+'" style="width :70px;">';
	dobInfoHtml += '</select>';
	dobInfoHtml += '</span>';
	return dobInfoHtml;
}

/*function refreshPaymentInfo(promotionMap, amountMap, totalPriceWithoutDiscount){
	promotionMap =  validatePromo('callForMembership', null, promotionMap, amountMap);
	var totalDiscount = applyPromos(promotionMap);
	totalPrice = parseFloat(totalPriceWithoutDiscount) - parseFloat(totalDiscount);
	
	console.log("  totalPriceWithoutDiscount  "+totalPriceWithoutDiscount+"  totalDiscount  "+totalDiscount+"  totalPrice  "+totalPrice);
	
	$("#totalDiscountHiddenInput").val(totalDiscount);
	$("#paymentAmountSpan").html(totalPrice);
	$("#paymentTotalPriceTd").html(totalPrice);	
	$("#sumTotalPriceTd").html(totalPrice); 	
	
	var finalAmount = parseFloat($("#sumTotalPriceTd").text());
	
	if($("#faAmountTD").text() != ''){
		finalAmount = finalAmount - parseFloat($("#faAmountTD").text());
	}  	
	console.log(" finalAmount :: "+finalAmount);
	$("#sumFinalAmountTd").html(finalAmount);
	
	$("#promotionMapInput").attr("value", convertToPromoString(promotionMap));
}*/

/*function applyPromos(promotionMap){
	
	var totalDiscount = 0;

	if(promotionMap != undefined){
		
		var signUpPromoDiscount = "", signUpPromoCount = 0;
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
				totalDiscount += parseFloat(promo.discountValue);
			}else if(promo.PromoRuleType == 'Deposit'){
				//console.log(" Deposit :: discount value ::  "+ parseFloat(promo.discountValue));
				depositPromoCount++;
				depositPromoDiscount += "<tr><td width='60%'>"+promo.PromoCode+"</td>";
				depositPromoDiscount += "<td width='40%' align='right' style='padding-right: 0px;'><span style='color: red;'>-$</span><span style='color: red;'>"+promo.discountValue+"</span></td></tr>";
				totalDiscount += parseFloat(promo.discountValue);
			}else if(promo.PromoRuleType == 'SetUpFee'){
				//console.log(" Set up fee :: discount value ::  "+ parseFloat(promo.discountValue));
				setupFeePromoCount++;
				setupFeePromoDiscount += "<tr><td width='60%'>"+promo.PromoCode+"</td>";
				setupFeePromoDiscount += "<td width='40%' align='right' style='padding-right: 0px;'><span style='color: red;'>-$</span><span style='color: red;'>"+promo.discountValue+"</span></td></tr>";
				totalDiscount += parseFloat(promo.discountValue);
			}else if(promo.PromoRuleType == 'Registration'){
				//console.log(" Reg :: discount value ::  "+ parseFloat(promo.discountValue));
				regFeePromoCount++;
				regFeePromoDiscount += "<tr><td width='60%'>"+promo.PromoCode+"</td>";
				regFeePromoDiscount += "<td width='40%' align='right' style='padding-right: 0px;'><span style='color: red;'>-$</span><span style='color: red;'>"+promo.discountValue+"</span></td></tr>";
				totalDiscount += parseFloat(promo.discountValue);
			}else if(promo.PromoRuleType == 'JoinFee'){
				//console.log(" Join fee :: discount value ::  "+ parseFloat(promo.discountValue));
				joinFeePromoCount++;
				joinFeePromoDiscount += "<tr><td width='60%'>"+promo.PromoCode+"</td>";
				joinFeePromoDiscount += "<td width='40%' align='right' style='padding-right: 0px;'><span style='color: red;'>-$</span><span style='color: red;'>"+promo.discountValue+"</span></td></tr>";
				totalDiscount += parseFloat(promo.discountValue);
			}
		}
		
		if(signUpPromoCount > 0){
			$("#SignUpPromoDiscount").show();
			$("#SignUpPromoDiscountTable").html(signUpPromoDiscount);
		}
		if(depositPromoCount > 0){
			$("#DepositPromoDiscount").show();
			$("#DepositPromoDiscountTable").html(depositPromoDiscount);
		}
		if(setupFeePromoCount > 0){
			$("#SetUpFeePromoDiscount").show();
			$("#SetUpFeePromoDiscountTable").html(setupFeePromoDiscount);
		}
		if(regFeePromoCount > 0){
			$("#RegFeePromoDiscount").show();
			$("#RegFeePromoDiscountTable").html(regFeePromoDiscount);
		}
		if(joinFeePromoCount > 0){
			$("#JoinFeePromoDiscount").show();
			$("#JoinFeePromoDiscountTable").html(joinFeePromoDiscount);
		}
	}
	return totalDiscount;
}*/

function getDOBHtmlbyClass(yearId,  monthId, dateId, month, date, year){
	var dobInfoHtml = '';
	dobInfoHtml += '<span class="">Date of Birth : </span> <br />';	
	dobInfoHtml += '<span>';
	dobInfoHtml += '<select name="dobMonth" class="'+monthId+'" style="width :90px;">';
	if(parseInt(month) == 1){
		dobInfoHtml += '<option value="1" selected="selected">January</option>';
	}else {
		dobInfoHtml += '<option value="1">January</option>';
	}	
	if(parseInt(month) == 2){
		dobInfoHtml += '<option value="2" selected="selected">February</option>';	
	}else {
		dobInfoHtml += '<option value="2">February</option>';
	}
	if(parseInt(month) == 3){
		dobInfoHtml += '<option value="3" selected="selected">March</option>';
	}else {
		dobInfoHtml += '<option value="3">March</option>';
	}
	if(parseInt(month) == 4){
		dobInfoHtml += '<option value="4" selected="selected">April</option>';
	}else {
		dobInfoHtml += '<option value="4">April</option>';
	}
	if(parseInt(month) == 5){
		dobInfoHtml += '<option value="5" selected="selected">May</option>';
	}else {
		dobInfoHtml += '<option value="5">May</option>';
	}
	if(parseInt(month) == 6){
		dobInfoHtml += '<option value="6" selected="selected">June</option>';
	}else {
		dobInfoHtml += '<option value="6">June</option>';
	}
	if(parseInt(month) == 7){
		dobInfoHtml += '<option value="7" selected="selected">July</option>';
	}else {
		dobInfoHtml += '<option value="7">July</option>';
	}
	if(parseInt(month) == 8){
		dobInfoHtml += '<option value="8" selected="selected">August</option>';
	}else {
		dobInfoHtml += '<option value="8">August</option>';
	}
	if(parseInt(month) == 9){
		dobInfoHtml += '<option value="9" selected="selected">September</option>';
	}else {
		dobInfoHtml += '<option value="9">September</option>';
	}
	if(parseInt(month) == 10){
		dobInfoHtml += '<option value="10" selected="selected">October</option>';
	}else {
		dobInfoHtml += '<option value="10">October</option>';
	}
	if(parseInt(month) == 11){ 
		dobInfoHtml += '<option value="11" selected="selected">November</option>';
	}else {
		dobInfoHtml += '<option value="11">November</option>';
	}
	if(parseInt(month) == 12){
		dobInfoHtml += '<option value="12" selected="selected">December</option>';
	}else {
		dobInfoHtml += '<option value="12">December</option>';
	}
	dobInfoHtml += '</select>';
	dobInfoHtml += '</span>';
	
	dobInfoHtml += '<span>';
	dobInfoHtml += '<select name="dobDate" class="'+dateId+'" style="width :45px;">';
	for(var i=1; i<32 ; i++){
		if(i == parseInt(date)){
			if(i<10){
				dobInfoHtml += '<option value="0'+i+'" selected="selected">'+i+'</option>';
			}else{
				dobInfoHtml += '<option value="'+i+'" selected="selected">'+i+'</option>';
			}						
		} else{
			dobInfoHtml += '<option value="'+i+'">'+i+'</option>';				
		}
		
	}	
	dobInfoHtml += '</select>';	
	dobInfoHtml += '</span>';
	
	dobInfoHtml += '<span>';
	dobInfoHtml += '<select name="dobYear" class="'+yearId+'" style="width :70px;">';
	var dobYearForm = new Date().getFullYear();
	dobYearForm = parseInt(dobYearForm.toString());
	for(var i=0; i<100 ; i++){
		if(parseInt(year) == dobYearForm){
			dobInfoHtml += '<option value="'+dobYearForm+'" selected="selected">'+dobYearForm+'</option>';			
			dobYearForm = dobYearForm - 1;
		} else{
			dobInfoHtml += '<option value="'+dobYearForm+'">'+dobYearForm+'</option>';			
			dobYearForm = dobYearForm - 1;
		}
		
	}
	dobInfoHtml += '</select>';
	dobInfoHtml += '</span>';
	return dobInfoHtml;
}

function populateInputDOBfromDropdownMenu(month, date, year, inputId){
	var dob = month+ "/" +date+ "/" +year;
	$("#"+inputId).val(dob);	
}


function onShowStep(obj, context){	
	console.log("  onShowStep  "+context.fromStep+" -> "+context.toStep);
	if(context.toStep == 1 ){
		$('.swMain .actionBar').css("display", "none");		
	}else{		
		$('.swMain .actionBar').css("display", "block");		
	}	
}

function proceedToRegisterChangeMembership(thisProd, itemDetailIdVal){
	
	var urlPromoItemDetailId = $("#urlPromoItemDetailId").val();
	var urlPromoContactId = $("#urlPromoContactId").val();
	var urlPromoCode = $("#urlPromoCode").val();
	var itemDetailId = 0;
	
	var registrationFee = 0;
	var depositAmount = 0;
	var allAreaPrice = 0;
	var bayAreaPrice = 0;
	var thisBranchPrice = 0;
	var pricingOptionHtml = '';
	var signupContactId = 0;
	signedUpProductsValue = $("#signedUpProductsValueSpann").val();
	
	if(thisProd != null && thisProd != undefined){
		itemDetailId = itemDetailIdVal;
		/*console.log(" product selected ");
		
		selectedHeaderInfo = $(thisProd).parent().parent().find( ".k-header" ).text();
		selectedTierPriceInfo = $(thisProd).parent().parent().find( ".price-div" ).text();
		selectedProdDescInfo = $(thisProd).parent().parent().find( ".product-description-div" ).text();
		selectedJoiningFeeInfo = $(thisProd).parent().parent().find( ".product-join-fee-text-val-div" ).text();     	
		selectedAnnualTierPrice = $(thisProd).parent().parent().find( ".prod-annual-tier-price-div" ).text();
		
		selectedMonthlyBillingFreqency = $(thisProd).parent().parent().find( ".prod-monthly-billing-freq-div" ).text();
		selectedAnnualBillingFreqency = $(thisProd).parent().parent().find( ".prod-annual-billing-freq-div" ).text();
		
		selectedAnnualJoinPrice = $(thisProd).parent().parent().find( ".prod-annual-join-price-div" ).text();
		
		selectedProdctId = $(thisProd).parent().parent().find( ".prod-id-div" ).text();
		selectedProductName = $(thisProd).parent().parent().find( ".prod-name-div" ).text();
		//selectedProductTotalPrice = $(thisProd).parent().parent().find( ".prod-total-price-div" ).text();
		selectedProdTandc = $(thisProd).parent().parent().find( ".prod-tandc-div" ).html();
		selectedProdType = $(thisProd).parent().parent().find( ".prod-type-div" ).text();
		pricingOptionHtml = $(thisProd).parent().parent().find( ".prod-price-option-dropdown-div" ).html();
		allAreaPrice = $(thisProd).parent().parent().find( ".all-area-price-td" ).text(); 
		bayAreaPrice = $(thisProd).parent().parent().find( ".bay-area-price-td" ).text();
		thisBranchPrice = $(thisProd).parent().parent().find( ".this-branch-price-td" ).text();
		billDateOption = $(thisProd).parent().parent().find( ".prod-billDateOption-div" ).text();
		billDateOffset = $(thisProd).parent().parent().find( ".prod-billDateOffset-div" ).text();
		dueDateOption = $(thisProd).parent().parent().find( ".prod-dueDateOption-div" ).text();
		dueDateOffset = $(thisProd).parent().parent().find( ".prod-dueDateOffset-div" ).text();
		selectedAutoPromoDiscount = $(thisProd).parent().parent().find( ".prod-autoPromoDiscount-div" ).html();
		$("#locationIdInput").attr("value", $("#location").kendoDropDownList().val());  
		
		var pricingOptionHtml = $(thisProd).parent().parent().find( ".prod-price-option-dropdown-div" ).html();
		$("#paymentFrequencySelect").html(pricingOptionHtml);
		$("#paymentFrequencySelect").kendoDropDownList();
	    
	    //paymentFreq.select(0);
	    
	    var registrationFee = 0;
		var depositAmount = 0;
		var regFee = $(thisProd).parent().parent().find( ".prod-registration-fee-div" ).text();
		if(regFee != "undefined"){
			registrationFee = regFee;
		}
		
		var depAmt = $(thisProd).parent().parent().find( ".prod-deposit-amount-div" ).text();
		if(depAmt != "undefined"){
			depositAmount = depAmt;
		} 
	    
	    selectPricingOptionAndPopulateEndDate();*/
	}else if(urlPromoItemDetailId != null && urlPromoItemDetailId != undefined){
		
		console.log(" promo product ")
		
		console.log(" urlPromoItemDetailId  : "+urlPromoItemDetailId);
		console.log(" urlPromoContactId : "+urlPromoContactId);
		console.log(" urlPromoCode : "+urlPromoCode);
		signupContactId = urlPromoContactId;
		itemDetailId = urlPromoItemDetailId;	
	}
	$("#urlPromoItemDetailId").val(itemDetailId);
	$("#prod-id-div").html(itemDetailId);
	$.ajax({
		  type: "GET",
		  url: "getMembershipProductDetail?itemDetailId="+itemDetailId,	
		  async: false,
		  dataType: "json",
		  success: function( data ) {
			  if(data){
				  
				  console.log("  Data Found. ");
				  
				  console.log("	  RecordName ::   " + data.RecordName);
				  console.log("	  signupMonthlyTierPrice ::   " + data.signupMonthlyTierPrice);
				  console.log("	  signupMonthlyJoinPrice ::   " + data.signupMonthlyJoinPrice);
				  console.log("	  signupMonthlyBillingFrequency ::   " + data.signupMonthlyBillingFrequency);
				  console.log("	  signupAnnualTierPrice ::   " + data.signupAnnualTierPrice);
				  console.log("	  signupAnnualJoinPrice ::   " + data.signupAnnualJoinPrice);
				  console.log("	  signupAnnualBillingFrequency ::   " + data.signupAnnualBillingFrequency);
				  console.log("	  registrationPrice ::   " + data.registrationPrice);
				  console.log("	  registrationBillingFrequency ::   " + data.registrationBillingFrequency);
				  console.log("	  depositPrice ::   " + data.depositPrice);
				  console.log("	  depositBillingFrequency ::   " + data.depositBillingFrequency);
				  console.log("	  ProductType ::   " + data.ProductType);
				  console.log("	  billDateOption ::   " + data.billDateOption);
				  console.log("	  billDateOffset ::   " + data.billDateOffset);
				  console.log("	  dueDateOption ::   " + data.dueDateOption);
				  console.log("	  dueDateOffset ::   " + data.dueDateOffset);
				  //console.log("	  tAndC ::   " + data.tAndC);
				  console.log("	  productLocationId   ::   " + data.productLocationId);
				  
				  $("#signedUplocationId").val(data.productLocationId);
				  
				  /*var locationDropdownlist = $("#location").data("kendoDropDownList");
				    locationDropdownlist.select(0);
				    locationDropdownlist.select(function(dataItem) {
					     return dataItem.value=== $('#signedUplocationId').val();
					 });
				    getProductsByLocationId();*/
				  
				    
				  
				  	selectedHeaderInfo = getProductHeader(data.RecordName);
					selectedTierPriceInfo = data.signupMonthlyTierPrice
					selectedProdDescInfo = data.Description;
					selectedJoiningFeeInfo = data.signupMonthlyJoinPrice;     	
					selectedAnnualTierPrice =  data.signupAnnualTierPrice;
					selectedMonthlyBillingFreqency = data.signupMonthlyBillingFrequency;
					selectedAnnualBillingFreqency = data.signupAnnualBillingFrequency;
					selectedAnnualJoinPrice = data.signupAnnualJoinPrice;
					selectedProdctId = urlPromoItemDetailId;
					selectedProductName = data.RecordName;
					selectedProdTandc = data.tAndC;
					registrationFee = data.registrationPrice;
					depositAmount = data.depositPrice;
					areaType = getAreaType(data.location_RecordName);
					selectedProdType = data.ProductType;
					billDateOption = data.billDateOption;
					billDateOffset = data.billDateOffset;
					dueDateOption = data.dueDateOption;
					dueDateOffset = data.dueDateOffset;
					//var pricingOptionHtml = $(thisProd).parent().parent().find( ".prod-price-option-dropdown-div" ).html();
					
				    selectPricingOptionAndPopulateEndDate();
				    
				    console.log("	 selectedJoiningFeeInfo ::   " + selectedJoiningFeeInfo);
				    console.log("	 existingMemberJoinFeeInput ::   " + $("#existingMemberJoinFeeInput").val());
				    selectedJoiningFeeInfo = selectedJoiningFeeInfo - $("#existingMemberJoinFeeInput").val();
				    
					console.log("	 updated selectedJoiningFeeInfo ::   " + selectedJoiningFeeInfo);
					//console.log("	  paymentFreq ::   " + paymentFreq.text());
					
					pricingOptionHtml =  getPriceOptionHTML(data.signupPrice);
					$("#paymentFrequencySelect").html(pricingOptionHtml);
					$("#paymentFrequencySelect").kendoDropDownList();
					
				    
					selectedHeaderInfo = selectedHeaderInfo.replace("<br />", "");
					
					$(thisProd).parent().parent().find( ".all-area-price-td" ).text(); 
					 $(thisProd).parent().parent().find( ".bay-area-price-td" ).text();
					 $(thisProd).parent().parent().find( ".this-branch-price-td" ).text();
					
					if(areaType == 'AllArea'){
						allAreaPrice = selectedTierPriceInfo;
					}else if(areaType == 'BayArea'){
						bayAreaPrice = selectedTierPriceInfo;
					}else if(areaType == 'ThisBranchOnly'){
						thisBranchPrice = selectedTierPriceInfo;
					}
			  }
		  },
		  error: function( xhr,status,error ){
			  console.log(xhr);
		  }
	});
		
	
	console.log("	cm  selectedHeaderInfo   ::   " + selectedHeaderInfo);
	console.log("	  selectedTierPriceInfo   ::   " + selectedTierPriceInfo);
	console.log("	  selectedJoiningFeeInfo   ::   " + selectedJoiningFeeInfo);
	console.log("	  selectedAnnualTierPrice   ::   " + selectedAnnualTierPrice);
	console.log("	  selectedAnnualJoinPrice   ::   " + selectedAnnualJoinPrice);
	console.log("	  registrationFee   ::   " + registrationFee);
	console.log("	  depositAmount   ::   " + depositAmount);
	
/*	$("#signupContactId").val(signupContactId);
	$("#sumRegistrationFee").html(registrationFee);	
	$("#sumDepositAmount").html(depositAmount);*/
	
	$("#paymentFrequencySelect").kendoDropDownList();
	
	var paymentFreq = $("#paymentFrequencySelect").data("kendoDropDownList");
    paymentFreq.search("Monthly");
	
	$("#joinFeeMonthly").val(selectedJoiningFeeInfo);
	$("#joinFeeYealy").val(selectedAnnualJoinPrice);
	$("#signupPriceMonthly").val(selectedTierPriceInfo);
	$("#signupPriceYearly").val(selectedAnnualTierPrice);
	$("#registrationFee").val(registrationFee);
	$("#depositAmount").val(depositAmount);
	$("#productIdHidInput").val(itemDetailId);
	$("#paymentFrequency").val(paymentFreq.text());
	
	$("#monthlyBillingFreqency").val(selectedMonthlyBillingFreqency);
	$("#annualBillingFreqency").val(selectedAnnualBillingFreqency);
	
	$("#signupContactId").val(signupContactId);
	
	$("#locationIdInput").attr("value", $("#location").kendoDropDownList().val());
	if(registrationFee > 0){
		$("#sumRegistrationFeeTR").show();
		$("#sumRegistrationFee").html(registrationFee);	
	}else{
		$("#sumRegistrationFeeTR").hide();
		$("#sumRegistrationFee").html("0");	
	}
	if(depositAmount > 0){
		$("#sumDepositAmountTR").show();
		$("#sumDepositAmount").html(depositAmount);
	}else{
		$("#sumDepositAmountTR").hide();
		$("#sumDepositAmount").html("0");
	}
	
	//$("input:radio[name=paymentTypeSelect]").change(function() {
	/* $(document).on('change', 'input:[name=paymentTypeSelect]', function(){
	
		if($("input:checkbox[name=paymentTypeSelect]").is(":checked")){
			$("input:checkbox[name=paymentTypeSelect]").prop('checked', false); 
		}
	    $(thisProd).prop('checked', false); 
	    alert( $(thisProd).val());
		selectedProductTotalPrice = parseFloat(selectedTierPriceInfo) + parseFloat(selectedJoiningFeeInfo);
		alert(selectedProductTotalPrice);
	});  */
	
	/* $(document).on('click', '.paymentTypeSelectClass', function() {
		if($("input:checkbox[name=paymentTypeSelect]").is(":checked")){
			$("input:checkbox[name=paymentTypeSelect]").prop('checked', false); 
		}
	    $(thisProd).prop('checked', true); 
	    alert( $(thisProd).val());
		selectedProductTotalPrice = parseFloat(selectedTierPriceInfo) + parseFloat(selectedJoiningFeeInfo);
		alert(selectedProductTotalPrice);
	}); */
	//if($("input:radio[name=paymentTypeSelect]").is(":checked")){

	/*if($(thisProd).parent().parent().find( "input:radio[name=paymentTypeSelect]" ).is(":checked")){    	
		var paymentType = $(thisProd).parent().parent().find( "input:radio[name=paymentTypeSelect]:checked" ).val();
		if(paymentType == 'AllArea'){
			var allAreaPrice = $(thisProd).parent().parent().find( ".all-area-price-td" ).text();    		
			selectedProductTotalPrice = parseFloat(allAreaPrice) + parseFloat(selectedJoiningFeeInfo) + parseFloat(registrationFee) + parseFloat(depositAmount);       
			$("#sumTierPriceTd").html(allAreaPrice);
			$("#signUpProductType").attr("value","All Branches");
			
		}else if(paymentType == 'BayArea'){
			var bayAreaPrice = $(thisProd).parent().parent().find( ".bay-area-price-td" ).text();    		
			selectedProductTotalPrice = parseFloat(bayAreaPrice) + parseFloat(selectedJoiningFeeInfo) + parseFloat(registrationFee) + parseFloat(depositAmount);    
			$("#sumTierPriceTd").html(bayAreaPrice);
			$("#signUpProductType").attr("value","Bay Area");
		}else if(paymentType == 'thisProdBranchOnly'){
			var thisProdBranchPrice = $(thisProd).parent().parent().find( ".this-branch-price-td" ).text();    		
			selectedProductTotalPrice = parseFloat(thisBranchPrice) + parseFloat(selectedJoiningFeeInfo) + parseFloat(registrationFee) + parseFloat(depositAmount);       			
			$("#sumTierPriceTd").html(thisBranchPrice);    	
			$("#signUpProductType").attr("value","This Branch Only");
		}    		
	} else{
		selectedProductTotalPrice = parseFloat(selectedTierPriceInfo) + parseFloat(selectedJoiningFeeInfo) + parseFloat(registrationFee) + parseFloat(depositAmount); 
		$("#sumTierPriceTd").html(selectedTierPriceInfo);
		$("#signUpProductType").attr("value","This Branch Only");
	}*/
	
	var sumChangePrice = 0, signupPrice = 0;     	
	if($(thisProd).parent().parent().find( "input:radio[name=paymentTypeSelect]" ).is(":checked")){    	
		var paymentType = $(thisProd).parent().parent().find( "input:radio[name=paymentTypeSelect]:checked" ).val();
		
		console.log("   paymentType  :::   "+paymentType);
		
		if(paymentType == 'AllArea'){
			var allAreaPrice = $(thisProd).parent().parent().find( ".all-area-price-td" ).text();    		
			selectedProductTotalPrice = parseFloat(allAreaPrice) + parseFloat(selectedJoiningFeeInfo) + parseFloat(registrationFee) + parseFloat(depositAmount);       
			$("#sumTierPriceTd").html(allAreaPrice); 
			selectedProductPriceProCal = parseFloat(allAreaPrice);
			sumChangePrice = parseFloat(allAreaPrice) - parseFloat(signedUpProductsValue);
			$("#signUpProductTypeNew").attr("value", "All Branches");
			signupPrice = parseFloat(allAreaPrice);
		}else if(paymentType == 'BayArea'){
			var bayAreaPrice = $(thisProd).parent().parent().find( ".bay-area-price-td" ).text();    		
			selectedProductTotalPrice = parseFloat(bayAreaPrice) + parseFloat(selectedJoiningFeeInfo) + parseFloat(registrationFee) + parseFloat(depositAmount);    
			$("#sumTierPriceTd").html(bayAreaPrice);    	
			selectedProductPriceProCal = parseFloat(bayAreaPrice);
			sumChangePrice = parseFloat(bayAreaPrice) - parseFloat(signedUpProductsValue);
			$("#signUpProductTypeNew").attr("value", "Bay Area");
			signupPrice = parseFloat(bayAreaPrice);
		}else if(paymentType == 'ThisBranchOnly'){
			var thisBranchPrice = $(thisProd).parent().parent().find( ".this-branch-price-td" ).text();    		
			selectedProductTotalPrice = parseFloat(thisBranchPrice) + parseFloat(selectedJoiningFeeInfo) + parseFloat(registrationFee) + parseFloat(depositAmount);       			
			$("#sumTierPriceTd").html(thisBranchPrice);    	
			selectedProductPriceProCal = parseFloat(thisBranchPrice);
			sumChangePrice = parseFloat(thisBranchPrice) - parseFloat(signedUpProductsValue);
			$("#signUpProductTypeNew").attr("value", "This Branch Only");
			signupPrice = parseFloat(thisBranchPrice);
		}  
		
	} else{
		console.log("   in else  selectedTierPriceInfo "+selectedTierPriceInfo);
		selectedProductTotalPrice = parseFloat(selectedTierPriceInfo) + parseFloat(selectedJoiningFeeInfo) + parseFloat(registrationFee) + parseFloat(depositAmount); 
		$("#sumTierPriceTd").html(selectedTierPriceInfo);
		selectedProductPriceProCal = parseFloat(selectedTierPriceInfo);
		sumChangePrice = parseFloat(selectedTierPriceInfo) - parseFloat(signedUpProductsValue);
	    $("#signUpProductTypeNew").attr("value", "This Branch Only");
	    signupPrice = parseFloat(selectedTierPriceInfo);
	}
	
	
	console.log("  selectedProductTotalPrice  ::   "+selectedProductTotalPrice);
	console.log("  selectedTierPriceInfo  ::   "+selectedTierPriceInfo);
	console.log("  signedUpProductsValue  ::   "+signedUpProductsValue);
	console.log("  sumChangePrice  ::   "+sumChangePrice);
	
	var selectedProductRadioInputId = '';
	if($(thisProd).parent().parent().find( "input:radio[name=paymentTypeSelect]" ).is(":checked")){    	
		paymentType = $(thisProd).parent().parent().find( "input:radio[name=paymentTypeSelect]:checked" ).val();
		selectedProductRadioInputId = $(thisProd).parent().parent().find( "input:radio[name=paymentTypeSelect]:checked" ).id;
	}else if(areaType != ''){
		
		console.log("   type id   :::   paymentTypeSelect"+areaType+selectedProdctId);
		//$(document).find('#paymentTypeSelect'+areaType+selectedProdctId).prop('checked', 'checked');
		
		selectedProductRadioInputId = "paymentTypeSelect"+areaType+selectedProdctId;
		
		paymentType = areaType;
	}
		

	$("#selectedProductRadioInputId").val(selectedProductRadioInputId);
	
	/*Prorate price calculation Formula 
	E = ((ExistingMembershipPrice/30) * (nextBillDate - CurrentDate))
	N=((NewMembershipPrice/30) * (nextBillDate - CurrentDate)
	RN=((NewMembershipPrice/30)*((currentDate+1Month)-NextBillDate))
	Pro-rated formula = -(e-n)+rn
	*/    	
	
	//var proratedPriceVal = 0;
	var signUpPricingOption = $("#signedUpPricingOption").val();
	
	var sumChangePrice = 0;    
	var proratedProductPriceValue = 0;
	if(signUpPricingOption != 'Annual'){
    	if(selectedProductPriceProCal != null && selectedProductPriceProCal != 0){
    		var billingDate = $("#signUpProductNextBillingDate").val();
    		if(billingDate != null && billingDate != ""){
    			var date = billingDate.split(" ");
        		var dateArr =  date[0].split("-");
        		var dateOfMonth = dateArr[2];
        		var month = dateArr[1];
        		var year  = dateArr[0];
        		var nextBillingDateFormatted = new Date(year, month - 1, dateOfMonth,0,0,0,0);
        		var currentDate = new Date();
        		var nextMonthDate = new Date(currentDate.getFullYear(), currentDate.getMonth(), currentDate.getDate(),0,0,0,0);
        		nextMonthDate = nextMonthDate.addMonths(1);
        		var signedUpProdDayVal = signedUpProductsValue/30;
        		var newProdDayVal = selectedProductPriceProCal/30;
        		var noOfDays = days_between(nextBillingDateFormatted, currentDate);
        		var nextMonthDays = days_between(nextMonthDate, nextBillingDateFormatted); 
        		
        		//new proration formula
        		//((NewMembershipPrice/30) - (ExistingMembershipPrice/30)) * (nextBillDate - CurrentDate)
        		var productValDiff = newProdDayVal - signedUpProdDayVal;
        		proratedProductPriceValue = productValDiff * noOfDays;
        		proratedProductPriceValue = Math.round(proratedProductPriceValue);
        		/*var existingMembershipPriceVal = (signedUpProdDayVal * noOfDays);        		
        		var newMembershipPriceVal = (newProdDayVal * noOfDays);        		
        		var rn = (newProdDayVal * nextMonthDays);
        		        		
        		var proratedProductPriceValue = - existingMembershipPriceVal + newMembershipPriceVal + rn;*/
        		if(proratedProductPriceValue != null){    	    	
	    	    	$("#sumProRatePriceTr").css("display","");
		    		$("#sumProRatePriceTd").html(proratedProductPriceValue);   	    	
	    	    }else{
	    	    	$("#sumProRatePriceTr").css("display","none");
		    		$("#sumProRatePriceTd").html(0); 
	    	    }   
        		
        		if(proratedProductPriceValue != null && proratedProductPriceValue < 0){
           		 $("#refundStatusBlock").css("display", "none");	
       			 $("#refundSuccess").css("display", "none");	
       			 //$("#refundSuccess" ).html("You may be eligble for $"+Math.round(proratedProductPriceValue).toString().replace('-', '' ) + " refund. Please contact Y Agent.");
       			//$("#refundSuccess" ).html("You may be eligble for refund. Please contact Y Agent.");
       			$("#refundSuccess" ).html("");
       			$("#sumProRatePriceTr").css("display","none");
       			$("#sumFinalAmountTr").css("display","none");
       		  
	           	}else{
	           		$("#refundStatusBlock").css("display", "none");	
	           		$("#refundSuccess").css("display", "none");	
	       			$( "#refundSuccess" ).html("");	
	       			$("#sumFinalAmountTr").css("display","");
	           	}   
        		
        		console.log("  proratedProductPriceValue "+proratedProductPriceValue);
    		}
    		
    	}
	}else{
		proratedProductPriceValue = selectedProductPriceProCal - signedUpProductsValue;
		proratedProductPriceValue = Math.round(proratedProductPriceValue);
		if(proratedProductPriceValue != null){    	    	
	    	$("#sumProRatePriceTr").css("display","");
    		$("#sumProRatePriceTd").html(proratedProductPriceValue);   	    	
	    }else{
	    	$("#sumProRatePriceTr").css("display","none");
    		$("#sumProRatePriceTd").html(0); 
	    }   
		
		if(proratedProductPriceValue != null && proratedProductPriceValue < 0){
   		 $("#refundStatusBlock").css("display", "block");	
			 $("#refundSuccess").css("display", "block");	
			 //$("#refundSuccess" ).html("You may be eligble for $"+Math.round(proratedProductPriceValue).toString().replace('-', '' ) + " refund. Please contact Y Agent.");
			$("#refundSuccess" ).html("You may be eligble for refund. Please contact Y Agent.");
			$("#sumProRatePriceTr").css("display","none");
			$("#sumFinalAmountTr").css("display","none");
		  
       	}else{
       		$("#refundStatusBlock").css("display", "none");	
       		$("#refundSuccess").css("display", "none");	
   			$( "#refundSuccess" ).html("");	
   			$("#sumFinalAmountTr").css("display","");
       	}
		console.log("  proratedProductPriceValue "+proratedProductPriceValue);
		
	}
	
	$("#proratedSignupPrice").val(proratedProductPriceValue);
	
	console.log("  proratedProductPriceValue "+proratedProductPriceValue);
	
	if(selectedAutoPromoDiscount != 0){
		  $("#sumAutoApplyTr").css("display","");			  
		  $("#sumEarlyBirdDiscount").html(selectedAutoPromoDiscount);
	  }else{
		  $("#sumAutoApplyTr").css("display","none");			 
		  $("#sumEarlyBirdDiscount").html("");
	  }
	
	
	 $("#sumPromoCodeTr").css("display","none");			  
	 $("#sumPromoCodeName").html("");
	 $("#sumPromoCodeVal").html("");		 
	
	//alert(selectedAutoPromoDiscount);
	selectedItemDetailsId = $(thisProd).parent().parent().find( ".prod-itemDetailsId-div" ).html();
	

	/*var FAobj = getFAObj(selectedProdctId);
	var FAamount = 0;
	if(FAobj!=null && FAobj.length>0 && $("#sumTierPriceTd").text() != ''){
		var FApercent = FAobj[0].FApercent;
		FAamount = (FApercent * parseFloat($("#sumTierPriceTd").text()))/100;    	
	}
	if(FAamount != 0){
		  $("#faAmountTr").css("display","");			  
		  $("#faAmountTD").html(FAamount);
	}else{
		  $("#faAmountTr").css("display","none");			 
		  $("#faAmountTD").html("");
	}*/   	
	$("#productIdTd").html(selectedProdctId);
	//$("#productIdHidInput").attr("value", selectedProdctId);
	$("#productNameTd").html(selectedProductName);
	$("#productDescriptionTd").html(selectedProdDescInfo);
	$("#productTypeTd").html($(thisProd).parent().parent().find( ".prod-type-div" ).text());    	
	
	//$("#productPriceTd").html(selectedProductTotalPrice);
	$("#paymentAmountSpan").html(selectedProductTotalPrice);
	$("#paymentTotalPriceTd").html(selectedProductTotalPrice);	
	$("#sumTotalPriceTd").html(selectedProductTotalPrice); 	
	
	$(".termsDiv").html(selectedProdTandc);
	$("#userTC").attr("value", selectedProdTandc);
	
	$("#paymentJoinFeeTd").html(selectedJoiningFeeInfo);
	$("#sumJoinFeeTd").html(selectedJoiningFeeInfo);
	
	
	console.log("  	  selectedProdctId ::: "+selectedProdctId+",  selectedProductName  ::: "+selectedProductName);
	console.log("	  selectedTierPriceInfo   ::   " + selectedTierPriceInfo);
	console.log("	  selectedJoiningFeeInfo   ::   " + selectedJoiningFeeInfo);
	console.log("	  selectedAnnualTierPrice   ::   " + selectedAnnualTierPrice);
	console.log("	  selectedAnnualJoinPrice   ::   " + selectedAnnualJoinPrice);
	console.log("	  registrationFee   ::   " + registrationFee);
	console.log("	  depositAmount   ::   " + depositAmount);
	
	
	//var setupfee = 0;
	//console.log("  selectedProductTotalPrice  "+selectedProductTotalPrice);
	//console.log("  signupPrice  "+signupPrice+"  setupfee  "+setupfee+"	 selectedJoiningFeeInfo  "+selectedJoiningFeeInfo+"   registrationFee  "+registrationFee+"   depositAmount  "+depositAmount);
	//amountMap = '{ "signupPrice":"'+signupPrice+'", "setupFee":"'+setupfee+'", "joinFee":"'+selectedJoiningFeeInfo+'", "registrationFee":"'+registrationFee+'", "depositAmount":"'+depositAmount+'"}';
	
	var membershipFreq = $("#membershipFrequencyId").val();
	
	
	console.log("  selectedProductTotalPrice = "+selectedProductTotalPrice);
	//console.log("		selectedMonthlyBillingFreqency    "+selectedMonthlyBillingFreqency);
	//promotionMap = getAutoPromotionMap(selectedProdctId, selectedMonthlyBillingFreqency, amountMap, selectedProductTotalPrice, urlPromoCode);
	
	console.log("  paymentFreq.text()  "+paymentFreq.text());
	var invoiceDate = getComputedInvoiceDate();
	
	var billDate = getBillDate(billDateOption, billDateOffset, null, invoiceDate, null, null);
	var dueDate = getDueDate(billDateOption, billDateOffset, null, billDate, null, null);
	
	var	nextBillDate = getNextBillDate(paymentFreq.text(), billDate, null); //, null, null, null, null);
	var billDateOnInvoice = getBillDateOnInvoice(billDate);
	var dueDateOnInvoice = getDueDateOnInvoice(dueDate);
	
	console.log("   invoiceDate   "+invoiceDate);
	console.log("   billDate   "+billDate);
	console.log("   dueDate   "+dueDate);
	console.log("   nextBillDate   "+nextBillDate);
	console.log("   billDateOnInvoice   "+billDateOnInvoice);
	console.log("   dueDateOnInvoice   "+dueDateOnInvoice);
	
	$("#invoiceDate").val(invoiceDate);
	$("#billDate").val(billDate);
	$("#dueDate").val(dueDate);
	$("#nextBillDate").val(nextBillDate);
	$("#billDateOnInvoice").val(billDateOnInvoice);
	$("#dueDateOnInvoice").val(dueDateOnInvoice);
	$("#paymentFrequency").val(paymentFreq.text());
	
	//console.log("  promotionMap  "+promotionMap);
	
	$('#applypromo').click(function() {
		
		$("#tcSuccessSpan").css("display", "none");		
		$("#tcSuccessSpan" ).html("");	
		$("#tcErrorSpan").css("display", "none");		
		$( "#tcErrorSpan" ).html("");
		
		populateChangePaymentInfoSection();
		
		/*var d_promocode = $("#c_promocode").val();
		var d_promocode_old = $("#c_promocode_old").val();
		
		if(d_promocode == d_promocode_old){
			return;
		}
		
		if(d_promocode_old && d_promocode_old != ''){
			promotionMap = removePromo(promotionMap, d_promocode_old);
			refreshPaymentInfo(promotionMap, amountMap, totalPriceWithoutDiscount);
		}
		$("#c_promocode_old").val(d_promocode);
		
		var found = false;

		console.log("    Apply Promo   "+ d_promocode );
		console.log("    AmountMap ::  "+JSON.stringify(amountMap));
		//console.log("    promotionMap ::  "+JSON.stringify(promotionMap));
		
		if(d_promocode!=''){
			var manualPromos = null;
			$.ajax({
				  type: "GET",
				  async: false,
				  url:"getPromoMap?itemId="+selectedProdctId+"&contactId="+contactId+"&isAuto=false&isRecurring="+isRecurring+"&amountJSON="+amountMap+"&selectedStartDate="+selectedStartDate+"&lstCartItem="+cartItems.join('_AND_'), //+"&setupfee="+setupfee+"&registrationPrice="+registrationFee+"&depositAmount="+depositAmount+"&joinFee="+selectedJoiningFeeInfo+"&selectedStartDate="+selectedStartDate,
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
			
			if(manualPromos != null && manualPromos != undefined){
				for(var j=0; j < manualPromos.length; j++){
					var promo = manualPromos[j];
					console.log("  PROMO ::  "+promo.PromoRuleType+"  "+promo.discountValue);
					if(promo.PromoCode == d_promocode){
						
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
				if(found){
					refreshPaymentInfo(promotionMap, amountMap, totalPriceWithoutDiscount);
				}
			}
		}

		if (!found) {
			  $("#tcSuccessSpan").css("display", "none");		
			  $("#tcSuccessSpan" ).html("");	
			  $("#tcErrorSpan").css("display", "block");		
			  $( "#tcErrorSpan" ).html("The Promo code you enetered is invalid");
		}*/
	    
	});
	
	
	$('#hidepromo').click(function() {
		
		console.log(" hide promo ");
		
		$("#c_promocode").val("");
		
		populateChangePaymentInfoSection();
		
		$("#tcSuccessSpan").css("display", "none");		
		$("#tcSuccessSpan" ).html("");	
		$("#tcErrorSpan").css("display", "none");		
		$( "#tcErrorSpan" ).html("");
		
		
		/*var d_promocode = $("#c_promocode").val();
		var d_promocode_old = $("#c_promocode_old").val();
		
		if(d_promocode && d_promocode != ''){
			promotionMap = removePromo(promotionMap, d_promocode);
		}
		
		if(d_promocode_old && d_promocode_old != ''){
			promotionMap = removePromo(promotionMap, d_promocode_old);
		}
		
		refreshPaymentInfo(promotionMap, amountMap, totalPriceWithoutDiscount);
		
		$("#c_promocode").val("");
		$("#c_promocode_old").val("");*/
		
	});
	
	
	
	var oneAdultWithKidsHtml = getKidsInfoHeader() + getKidsInfoHtml("1");
	var youthHtml = getYouthInfoHeader() + getYouthUserInfoData("1");
	var twoAdultsHtml = getSecondUserInfoHeader() + getSecondUserInfoData("1");
	var twoAdultsWithKidsHtml = getSecondUserInfoHeader() + getSecondUserInfoData("1") + getKidsInfoHeader() + getKidsInfoHtml("2");
	var threeAdultsWithKidsHtml = getSecondUserInfoHeader() + getSecondUserInfoData("1") + getThirdUserInfoHeader() + getThirdUserInfoData("2") + getKidsInfoHeader() + getKidsInfoHtml("3");    
	var threeAdultsHtml = getSecondUserInfoHeader() + getSecondUserInfoData("1") + getThirdUserInfoHeader() + getThirdUserInfoData("2");
	validateEmail(0);
	$('#youthInfoFormTable').css("display" , "none");
	$('#youthInfoFormTable').html('');	
	$(".primaryUserInfoTable").css("display", "");
	$("#primaryMembershipAgeCategory").attr("value", "Adult");
	if(selectedHeaderInfo == 'Adult'){
		$("#primaryMemberTxtSpan").html("PRIMARY MEMBER");
		$('#familyInfoFormTable').css("display" , "block");
		$('#familyInfoFormTable').html("");
		validateDOBAdult(0);
	}else if(selectedHeaderInfo == 'Senior'){
		$("#primaryMemberTxtSpan").html("PRIMARY MEMBER");
		$('#familyInfoFormTable').css("display" , "block");
		$('#familyInfoFormTable').html("");
		validateDOBSenior(0);
		$("#primaryMembershipAgeCategory").attr("value", "Senior");
	}else if(selectedHeaderInfo == 'Youth'){  
		$('#familyInfoFormTable').html("");
		//$(".primaryUserInfoTable").css("display", "none");
		$("#primaryMemberTxtSpan").html("Primary Adult Contact");
		$('#youthInfoFormTable').css("display" , "inline-table");
		$('#youthInfoFormTable').html(youthHtml);
		$('#youthInfoFormTable').find(".dob-class").kendoDatePicker();  		    		
		validateDOBAdult(0);
		validateAdult(0);
		validateYouth(1);
		//checkIsUserAdult();
	}else if(selectedHeaderInfo == 'One Adultw/ Kids'){
		$("#primaryMemberTxtSpan").html("PRIMARY MEMBER");
		$('#familyInfoFormTable').css("display" , "inline-table");
		$('#familyInfoFormTable').html(oneAdultWithKidsHtml);
		$('#familyInfoFormTable').find(".dob-class").kendoDatePicker();    		
		secKidCount = 2;
		validateDOBAdult(0);
		validatekid(1);
		populateSecondaryKidsInfoWithDataByDefault();
	}else if(selectedHeaderInfo == 'Two Adults' ){
		$("#primaryMemberTxtSpan").html("PRIMARY MEMBER");
		$('#familyInfoFormTable').css("display" , "inline-table");
		$('#familyInfoFormTable').html(twoAdultsHtml);    		
		$('#familyInfoFormTable').find(".dob-class").kendoDatePicker();  
		validateDOBAdult(0);
		validateAdult(1);
	}else if(selectedHeaderInfo == 'Two Adultsw/ Kids'){  
		$("#primaryMemberTxtSpan").html("PRIMARY MEMBER");
		$('#familyInfoFormTable').css("display" , "inline-table");
		$('#familyInfoFormTable').html(twoAdultsWithKidsHtml);
		$('#familyInfoFormTable').find(".dob-class").kendoDatePicker();    		
		validateDOBAdult(0);
		validateAdult(1);
		validatekid(2);
		secKidCount = 3;
		populateSecondaryKidsInfoWithDataByDefault();
	}else if(selectedHeaderInfo == 'Three Adults'){
		$("#primaryMemberTxtSpan").html("PRIMARY MEMBER");
		$('#familyInfoFormTable').css("display" , "inline-table");
		$('#familyInfoFormTable').html(threeAdultsHtml);
		$('#familyInfoFormTable').find(".dob-class").kendoDatePicker();
		validateDOBAdult(0);
		validateAdult(1);
		validateAdult(2);    		
	} else if(selectedHeaderInfo == 'Three Adultsw/ or w/o kids'){
		$("#primaryMemberTxtSpan").html("PRIMARY MEMBER");
		$('#familyInfoFormTable').css("display" , "inline-table");
		$('#familyInfoFormTable').html(threeAdultsWithKidsHtml);
		$('#familyInfoFormTable').find(".dob-class").kendoDatePicker();       		
		validateDOBAdult(0);
		secKidCount = 4;  
		populateSecondaryKidsInfoWithDataByDefault();
		validateAdult(1);
		validateAdult(2);
		validatekid(3);
		
	}else{
		$('#familyInfoFormTable').css("display" , "none");
	} 
	
	if(sumChangePrice != null && sumChangePrice != 0){
		$("#sumChangePriceTr").css("display","none");
		//$("#sumChangePriceTr").css("display","");
		$("#sumChangePriceTd").html(Math.round(0)); 
		//$("#sumChangePriceTd").html(Math.round(sumChangePrice));
	}else{
		$("#sumChangePriceTr").css("display","none");
		$("#sumChangePriceTd").html(0); 
	}      	
	$(".phone-number").mask("(999) 999-9999");    	
	$("#wizard"). smartWizard("fixHeight");
	registerSelected = true;  
	$('#familyInfoFormTable').find('.selectsecondaryContactKid').kendoDropDownList();
	$("#selectContactSecMember").kendoDropDownList();
    $("#selectContactSecMember").on('change',function(e){    	
     	var contactVal = $(this).val();
     	//console.log(contactVal);
     	var contactInfoArr = contactVal.split("\|\|");
     	var thisConSelObj = $(this);
     	var flag = true;
     	$(document).find('select.selectContactDropDw').each(function(i, obj) {				
     		var selectedContacts = $(obj).data("kendoDropDownList");
     		var dropDownVal = selectedContacts.value();
     		var dropDownArr = dropDownVal.split("\|\|");         		
     		if(dropDownVal != 0 && dropDownVal != 'Other' && thisConSelObj.attr("id") != obj.id && contactInfoArr[contactInfoArr.length - 1] == dropDownArr[dropDownArr.length - 1]){
     			//alert("Contact Already selected");
     			duplicateContactSelectedError();        			
     			flag = false;
     		}
     	});
     	
     	if(contactVal != 0  && contactVal != 'Other' && flag){         		
     		populateSecondaryMember(contactInfoArr[0], contactInfoArr[1], contactInfoArr[2], contactInfoArr[3], contactInfoArr[4], contactInfoArr[5]);
     		$(document).find('#secFirstName').prop('readonly', true);
     		$(document).find('#secLastName').prop('readonly', true);
     		$(document).find('#secDOB').prop('readonly', true);
     		$(document).find('#secPhoneNumber').prop('readonly', true);
     		$(document).find('#secEmail').prop('readonly', true);		
     		$(document).find('#secGenderM').prop('readonly', true);
     		$(document).find('#secGenderF').prop('readonly', true);
     	}else if(contactVal == 0 || contactVal == 'Other' || flag == false){
     		resetSecondaryMember();
     		$(document).find('#secFirstName').prop('readonly', false);
     		$(document).find('#secLastName').prop('readonly', false);
     		$(document).find('#secDOB').prop('readonly', false); 
     		$(document).find('#secPhoneNumber').prop('readonly', false);
     		$(document).find('#secEmail').prop('readonly', false);
     		$(document).find('#secGenderM').prop('readonly', false);
     		$(document).find('#secGenderF').prop('readonly', false);
     		populateInputDOBfromDropdownMenu($("#secDobMonthForm").val(), $("#secDobDateForm").val(), $("#secDobYearForm").val(), "secDOB");
     	}         	
    });    	
    $("#selectContactThirdMember").kendoDropDownList();          
   $("#selectContactThirdMember").on('change',function(e){    	
   	var contactVal = $(this).val();
   	//console.log(contactVal);
   	var contactInfoArr = contactVal.split("\|\|");
   	var thisConSelObj = $(this);
   	var flag = true;
   	$(document).find('select.selectContactDropDw').each(function(i, obj) {				
   		var selectedContacts = $(obj).data("kendoDropDownList");
   		var dropDownVal = selectedContacts.value();
   		var dropDownArr = dropDownVal.split("\|\|");       		
   		if(dropDownVal != 0 && dropDownVal != 'Other' && thisConSelObj.attr("id") != obj.id && contactInfoArr[contactInfoArr.length - 1] == dropDownArr[dropDownArr.length - 1]){
   			//alert("Contact Already selected");
   			duplicateContactSelectedError();        			
   			flag = false;
   		}
   	});
   	if(contactVal != 0 && contactVal != 'Other' && flag){
   		
   		populateThirdMember(contactInfoArr[0], contactInfoArr[1], contactInfoArr[2], contactInfoArr[3], contactInfoArr[4], contactInfoArr[5]);
   		$(document).find('#thirdFirstName').prop('readonly', true);
   		$(document).find('#thirdLastName').prop('readonly', true);
   		$(document).find('#thirdDOB').prop('readonly', true);
   		$(document).find('#thirdPhoneNumber').prop('readonly', true);
   		$(document).find('#thirdEmail').prop('readonly', true);	
   		$(document).find('#thirdGenderM').prop('readonly', true);
   		$(document).find('#thirdGenderF').prop('readonly', true);	
   	}else if(contactVal == 0 || contactVal == 'Other' || flag == false){
   		resetThirdMember();
   		$(document).find('#thirdFirstName').prop('readonly', false);
   		$(document).find('#thirdLastName').prop('readonly', false);
   		$(document).find('#thirdDOB').prop('readonly', false);
   		$(document).find('#thirdPhoneNumber').prop('readonly', false);
   		$(document).find('#thirdEmail').prop('readonly', false);	
   		$(document).find('#thirdGenderM').prop('readonly', false);
   		$(document).find('#thirdGenderF').prop('readonly', false);	
   		populateInputDOBfromDropdownMenu($("#thirdDobMonthForm").val(), $("#thirdDobDateForm").val(), $("#thirdDobYearForm").val(), "thirdDOB");
   	}
   });
   	$("#selectContactYouth").kendoDropDownList();
   	$("#selectContactYouth").on('change',function(e){    	
       	var contactVal = $(this).val();
       	//console.log(contactVal);
       	var contactInfoArr = contactVal.split("\|\|");
       	var thisConSelObj = $(this);
       	var flag = true;
       	$(document).find('select.selectContactDropDw').each(function(i, obj) {				
       		var selectedContacts = $(obj).data("kendoDropDownList");
       		var dropDownVal = selectedContacts.value();
       		var dropDownArr = dropDownVal.split("\|\|");         		
       		if(dropDownVal != 0 && dropDownVal != 'Other' && thisConSelObj.attr("id") != obj.id && contactInfoArr[contactInfoArr.length - 1] == dropDownArr[dropDownArr.length - 1]){
       			//alert("Contact Already selected");
       			duplicateContactSelectedError();        			
       			flag = false;
       		}
       	});

       	if(contactVal != 0 && contactVal != 'Other' && flag){
       		
       		populateYouthMember(contactInfoArr[0], contactInfoArr[1], contactInfoArr[4], contactInfoArr[5], contactInfoArr[2], contactInfoArr[3]);
       		$(document).find('#secFirstName').prop('readonly', true);
     		$(document).find('#secLastName').prop('readonly', true);
     		$(document).find('#secDOB').prop('readonly', true);
     		$(document).find('#secEmail').prop('readonly', true);
     		$(document).find('#secPhoneNumber').prop('readonly', true);
     		//$(document).find('#secEmail').attr("value", "");		
     		$(document).find('#secGenderM').prop('readonly', true);
     		$(document).find('#secGenderF').prop('readonly', true);
       	}else if(contactVal == 0 || contactVal == 'Other' || flag == false){
       		resetYouthMember();
       		$(document).find('#secFirstName').prop('readonly', false);
     		$(document).find('#secLastName').prop('readonly', false);
     		$(document).find('#secDOB').prop('readonly', false); 
     		$(document).find('#secEmail').prop('readonly', false);
     		$(document).find('#secPhoneNumber').prop('readonly', false);
     		//$(document).find('#secEmail').attr("value", "");		
     		$(document).find('#secGenderM').prop('readonly', false);
     		$(document).find('#secGenderF').prop('readonly', false);
     		populateInputDOBfromDropdownMenu($("#youthDobMonthForm").val(), $("#youthDobDateForm").val(), $("#youthDobYearForm").val(), "secDOB");
     }
       	checkIsUserAdult(); 
  });
   	
   	$(".selectPrimaryContactKid").kendoDropDownList();
   	$(".selectPrimaryContactKid").on('change',function(e){      		
   		var selectContactKid = $(this).data("kendoDropDownList");
   		if(selectContactKid){
           	var contactVal = selectContactKid.value();
       		//var contactVal = $(".selectContactKid").data("kendoDropDownList").value();
           	//console.log(contactVal);
           	var contactInfoArr = contactVal.split("\|\|");
           	var thisConSelObj = $(this);
           	var flag = true;
           	$(document).find('select.selectContactDropDw').each(function(i, obj) {				
        		var selectedContacts = $(obj).data("kendoDropDownList");
        		var dropDownVal = selectedContacts.value();
        		var dropDownArr = dropDownVal.split("\|\|");         		
        		if(dropDownVal != 0 && dropDownVal != 'Other' && thisConSelObj.attr("id") != obj.id && contactInfoArr[contactInfoArr.length - 1] == dropDownArr[dropDownArr.length - 1]){
        			//alert("Contact Already selected");
        			duplicateContactSelectedError();        			
        			flag = false;
        		}
    		});
           	
           	if(contactVal != 0 && contactVal != 'Other' && flag){	           		
           		populateFirstKidInfo(contactInfoArr[0], contactInfoArr[1],contactInfoArr[2], contactInfoArr[3]);
           		$(".firstKidInfoForm").find(".firstName").prop('readonly', true);
           		$(".firstKidInfoForm").find(".lastName").prop('readonly', true);
           		$(".firstKidInfoForm").find("input.dateOfBirth").prop('readonly', true);
           		var $radios = $(".firstKidInfoForm").find(".gender");
           		$radios.filter('[value=Male]').prop('readonly', true);
           		$radios.filter('[value=Female]').prop('readonly', true);	           		
           	}else if(contactVal == 0 || contactVal == 'Other' || flag == false){
           		resetFirstKidFormElements();
           		$(".firstKidInfoForm").find(".firstName").prop('readonly', false);
           		$(".firstKidInfoForm").find(".lastName").prop('readonly',  false);
           		$(".firstKidInfoForm").find("input.dateOfBirth").prop('readonly',  false);
           		var $radios = $(".firstKidInfoForm").find(".gender");
           		$radios.filter('[value=Male]').prop('readonly',  false);
           		$radios.filter('[value=Female]').prop('readonly',  false);
           		populateInputDOBfromDropdownMenu($("#firstKidDobMonthForm").val(), $("#firstKidDobDayForm").val(), $("#firstKidDobYearForm").val(), "firstKidDOBInp");       	
           	}
   		}       	
  });  
   	var secDobYearForm = new Date().getFullYear();
	secDobYearForm = parseInt(secDobYearForm.toString());
	for(var i=0; i<100 ; i++){
		$('#secDobYearForm').append($('<option>', {value: secDobYearForm,text: secDobYearForm}));
		secDobYearForm = secDobYearForm - 1;
	}
	
	var thirdDobYearForm = new Date().getFullYear();
	thirdDobYearForm = parseInt(thirdDobYearForm.toString());
	for(var i=0; i<100 ; i++){
		$('#youthDobYearForm').append($('<option>', {value: thirdDobYearForm,text: thirdDobYearForm}));
		thirdDobYearForm = thirdDobYearForm - 1;
	}
	var youthDobYearForm = new Date().getFullYear();
	youthDobYearForm = parseInt(youthDobYearForm.toString());
	for(var i=0; i<100 ; i++){
		$('#thirdDobYearForm').append($('<option>', {value: youthDobYearForm,text: youthDobYearForm}));
		youthDobYearForm = youthDobYearForm - 1;
	}
	var fkidDobYearForm = new Date().getFullYear();
	fkidDobYearForm = parseInt(fkidDobYearForm.toString());
	for(var i=0; i<100 ; i++){
		$('#firstKidDobYearForm').append($('<option>', {value: fkidDobYearForm,text: fkidDobYearForm}));
		fkidDobYearForm = fkidDobYearForm - 1;
	}
   	$('#secDobMonthForm').kendoDropDownList();
   	$('#secDobDateForm').kendoDropDownList();
   	$('#secDobYearForm').kendoDropDownList();
   	$('#youthDobYearForm').kendoDropDownList();
   	$('#youthDobMonthForm').kendoDropDownList();
   	$('#youthDobDateForm').kendoDropDownList();
   	$('#thirdDobYearForm').kendoDropDownList();
   	$('#thirdDobMonthForm').kendoDropDownList();
   	$('#thirdDobDateForm').kendoDropDownList();
   	$('#firstKidDobYearForm').kendoDropDownList();
   	$('#firstKidDobMonthForm').kendoDropDownList();
   	$('#firstKidDobDayForm').kendoDropDownList();
   	/*var dobYearForm = new Date().getFullYear();
	dobYearForm = parseInt(dobYearForm.toString());
	for(var i=0; i<100 ; i++){
		$('#familyInfoFormTable').find('.kidDobYearForm').last().append($('<option>', {value: dobYearForm,text: dobYearForm}));
		dobYearForm = dobYearForm - 1;
	}*/
	$('#familyInfoFormTable').find('.kidDobYearForm').kendoDropDownList();
	$('#familyInfoFormTable').find('.kidDobMonthForm').kendoDropDownList();
	$('#familyInfoFormTable').find('.kidDobDateForm').kendoDropDownList();
	populatePrimarySecondaryThirdYouthMember();
	//validator1.resetForm();
	checkIsUserAdult();
	$('#wizard').smartWizard('goToStep',2);
}

function validateYouth(n){
	validateFirstName(n)
	validateLastName(n);
	//validatePhoneNumber(n);
	if(isUserLoggedInCheck != 'true'){
	//validateEmail(n);
	}	
	validateEmail(n);
	validateDOBYouth(n);
}
function validateAdult(n){
	validateFirstName(n)
	validateLastName(n);
	validatePhoneNumber(n);
	if(isUserLoggedInCheck != 'true'){
	//validateEmail(n);
	}
	validateEmail(n);
	validateDOBAdult(n);
}
function validatekid(n){
	validateFirstName(n);
	validateLastName(n);		
	validateDOBKid(n);
}

function validateFirstName(n){  
	$('#becomeMemberForm')
    .find('input[name^="userLst['+ n +'].firstName"]').rules('add', {
    	required: true,
		minlength: 2,
		maxlength: 30,
      messages: {
    	  required: "Please enter your First Name",
		  minlength: "First Name must consist of at least 2 characters",
		  maxlength: "Please enter the First Name less than 30 characters"
      }
    });
}
function validateLastName(n){  
	$('#becomeMemberForm')
    .find('input[name^="userLst['+ n +'].lastName"]').rules('add', {
    	required: true,
		minlength: 2,
		maxlength: 30,
      messages: {
    	  required: "Please enter your Last Name",
			minlength: "Last Name must consist of at least 2 characters",
			  maxlength: "Please enter the Last Name less than 30 characters"
      }
    });
}

function validatePhoneNumber(n){        	
	$('#becomeMemberForm')
    .find('input[name^="userLst['+ n +'].formattedPhoneNumber"]').rules('add', {
    	required: true,
      messages: {
    	  required: "Please enter your Phone Number"
			
      }
    });
}

function validateEmail(n){    	    	
	$('#becomeMemberForm')
    .find('input[name^="userLst['+ n +'].emailAddress"]').rules('add', {
    	//required: true,
    	validate_email_format: true,
		check_duplicate_email : true,
		/*remote: {
			url: url,  
			type:"GET"                    	
		 },*/
      messages: {
    	 // required: "Please enter your email address",
    	validate_email_format : "Please enter valid email address",
  		check_duplicate_email : "Please enter unique email"
  			/*,
  		
  		remote: "You appear to be associated with a customer account already.  Please speak with the primary contact for that account"
		*/	
      }
    });
}

function validateDOBAdult(n){ 
	$('#becomeMemberForm')
    .find('input[name^="userLst['+ n +'].dateOfBirth"]').rules('remove',"check_date_of_birth");
	$('#becomeMemberForm')
    .find('input[name^="userLst['+ n +'].dateOfBirth"]').rules('remove',"check_date_of_birth_kid");
	$('#becomeMemberForm')
    .find('input[name^="userLst['+ n +'].dateOfBirth"]').rules('remove',"check_date_of_birth_youth");
	$('#becomeMemberForm')
    .find('input[name^="userLst['+ n +'].dateOfBirth"]').rules('remove',"check_date_of_birth_senior");    	
	
	$('#becomeMemberForm')
    .find('input[name^="userLst['+ n +'].dateOfBirth"]').rules('add', {
    	required: true,
		check_date_of_birth: true,
      messages: {
    	  required: "Please enter your Date of Birth",
    	  check_date_of_birth: "You must be between "+ $("#adultAgeValidationLowerLimit").html() +" to "+ $("#adultAgeValidationUpperLimit").html() +" years of age, or choose another membership product"
			
      }
    });
}

function validateDOBSenior(n){
	$('#becomeMemberForm')
    .find('input[name^="userLst['+ n +'].dateOfBirth"]').rules('remove',"check_date_of_birth");
	$('#becomeMemberForm')
    .find('input[name^="userLst['+ n +'].dateOfBirth"]').rules('remove',"check_date_of_birth_kid");
	$('#becomeMemberForm')
    .find('input[name^="userLst['+ n +'].dateOfBirth"]').rules('remove',"check_date_of_birth_youth");
	$('#becomeMemberForm')
    .find('input[name^="userLst['+ n +'].dateOfBirth"]').rules('remove',"check_date_of_birth_senior");
	$('#becomeMemberForm')
    .find('input[name^="userLst['+ n +'].dateOfBirth"]').rules('add', {
    	required: true,
    	check_date_of_birth_senior: true,
      messages: {
    	  required: "Please enter your Date of Birth",
    	  check_date_of_birth_senior: "You must be at least "+ $("#seniorAgeValidationLimit").html() +" years of age, or choose another membership product"
			
      }
    });
}

function validateDOBKid(n){    
	$('#becomeMemberForm')
    .find('input[name^="userLst['+ n +'].dateOfBirth"]').rules('remove',"check_date_of_birth");
	$('#becomeMemberForm')
    .find('input[name^="userLst['+ n +'].dateOfBirth"]').rules('remove',"check_date_of_birth_kid");
	$('#becomeMemberForm')
    .find('input[name^="userLst['+ n +'].dateOfBirth"]').rules('remove',"check_date_of_birth_youth");
	$('#becomeMemberForm')
    .find('input[name^="userLst['+ n +'].dateOfBirth"]').rules('remove',"check_date_of_birth_senior");
	
	$('#becomeMemberForm')
    .find('input[name^="userLst['+ n +'].dateOfBirth"]').rules('add', {
    	required: true,
    	check_date_of_birth_kid: true,
      messages: {
    	  required: "Please enter your Date of Birth",
    	  check_date_of_birth_kid: "You must be less than "+ $("#kidsAgeValidation").html() +" years of age, or choose another membership product"
			
      }
    });
}     

function validateDOBYouth(n){    
	$('#becomeMemberForm')
    .find('input[name^="userLst['+ n +'].dateOfBirth"]').rules('remove',"check_date_of_birth");
	$('#becomeMemberForm')
    .find('input[name^="userLst['+ n +'].dateOfBirth"]').rules('remove',"check_date_of_birth_kid");
	$('#becomeMemberForm')
    .find('input[name^="userLst['+ n +'].dateOfBirth"]').rules('remove',"check_date_of_birth_youth");
	$('#becomeMemberForm')
    .find('input[name^="userLst['+ n +'].dateOfBirth"]').rules('remove',"check_date_of_birth_senior");
	
	$('#becomeMemberForm')
    .find('input[name^="userLst['+ n +'].dateOfBirth"]').rules('add', {
    	required: true,
    	check_date_of_birth_youth: true,
      messages: {
    	  required: "Please enter your Date of Birth",
    	  check_date_of_birth_youth: "You must be between "+ $("#youthAgeValidationLowerLimit").html() +" to "+ $("#youthAgeValidationUpperLimit").html() +" years of age, or choose another membership product"
			
      }
    });
} 


function checkIsUserAdult(){
	if(selectedHeaderInfo == 'Youth'){
		var dobMonth = $("#youthDobMonthForm").val();
		var dobDate = $("#youthDobDateForm").val();
		var dobYear = $("#youthDobYearForm").val();
		
		var age =  $("#adultAgeValidationLowerLimit").html();
		//var kidAge =  $("#kidsAgeValidation").html();
		var mydate = new Date();
		mydate.setFullYear(dobYear, dobMonth-1, dobDate);
		
	    var currdate = new Date();
	    currdate.setFullYear(currdate.getFullYear() - age);
	    
	    if(currdate < mydate){		
	    	$(".primaryUserInfoTable").css("display", "");
	    	$("#youthEmailPhoneNumber").css("display", "none");
	    	$("#youthPasswordTR").css("display", "none"); 
	    	
	    	$("#firstUserSummary").css("display", "");
	    	$("#secMemberTr").css("display", "none"); 
	    	$("#secondUserSummHeader").text("SECONDARY MEMBER");
	    	
	    	$('#becomeMemberForm').find('#secPhoneNumber').attr("value", ""); 
	    	$('#becomeMemberForm').find('#secEmail').attr("value", "");	
	    	$('#becomeMemberForm').find('#youthPassword').attr("value", ""); 
	    	$('#becomeMemberForm').find('#youth_confirm_password').attr("value", "");
	    }else{
	    	$(".primaryUserInfoTable").css("display", "none");
	    	$("#youthEmailPhoneNumber").css("display", "");
	    	$("#youthPasswordTR").css("display", "");
	    	$("#firstUserSummary").css("display", "none");    	
	    	$("#secMemberTr").css("display", "inline");    	
	    	$("#secondUserSummHeader").text("PRIMARY MEMBER");
	    	resetPrimaryMember();
	    }
	    $(".stepContainer").height($(".stepContainer").find("div:not(:hidden)").height());
	}
}

