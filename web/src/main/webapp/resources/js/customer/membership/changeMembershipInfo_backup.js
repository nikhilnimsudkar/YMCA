/**
 * 
 */
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

var signedUpTierPriceInfo = "";
var signedUpJoiningFeeInfo =  "";
var signedUpProdctId =  "";
var signedUpProductName =  ""; 						  
var signedUpAutoPromoDiscount =  "";
var signedUpAllAreaPrice =  "";
var signedUpBayAreaPrice =  "";
var registerSelected = false;

var tierPriceVal = 0;	    					  
var allLocationVal = 0;
var bayAreaVal = 0;

var signedUpProductsValue = 0;

var secKidCount = 1;
var paymentOrderId = 0;


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
$(document).ready(function(){	
	$("#statusBlock").css("display", "none");	
	 $(".k-loading-mask").show();
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
								    /*if(dataArr[2]){
									   paymentMethodHtml += '<option selected value="'+ dataArr[8] +'">'+ dataArr[4] +' '+dataArr[5] +' '+dataArr[6] +'/'+dataArr[7]+'</option>';
									   $("#paymentTokenIdSpan").html(dataArr[8]);
								   }else{
									   paymentMethodHtml += '<option value="'+ dataArr[8] +'">'+ dataArr[4] +' '+dataArr[5] +' '+dataArr[6] +'/'+dataArr[7]+'</option>';
								   }*/	 				
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
	});
	/*if($("#paymentInfoRadio").val()=='New'){
		$('#addBank').hide();	
		$('#addcard').show();			
		$(".stepContainer").height($(".stepContainer").find("div:not(:hidden)").height());
		 $("#wizard"). smartWizard("fixHeight");			
		$('.stepContainer').css("height", "240px");
		$('#step-5').css("height", "225px");  			
	}*/
	//$("#paymentInfoRadio").kendoDropDownList();	
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
	
	/* $(document).on('change', 'input:[name=paymentTypeSelect]', function(){
	
	if($("input:checkbox[name=paymentTypeSelect]").is(":checked")){
		$("input:checkbox[name=paymentTypeSelect]").prop('checked', false); 
	}
    $(this).prop('checked', false); 
    alert( $(this).val());
	selectedProductTotalPrice = parseFloat(selectedTierPriceInfo) + parseFloat(selectedJoiningFeeInfo);
	alert(selectedProductTotalPrice);
});  */	
	
	/*$(document).on('change', 'input[name=paymentTypeSelect]', function(){
		var paymentType = $(this).val();
		var currentMembershipTxt = $(this).parent().parent().parent().parent().parent().parent().find( ".isCurrentMembership" ).html();
		var signUpProductType = $('#signUpProductType').val();
		if(currentMembershipTxt == 'Current Membership'){
			if((paymentType == 'AllArea' && signUpProductType != 'All Branches') || (paymentType == 'BayArea' && signUpProductType != 'Bay Area') || (paymentType == 'ThisBranchOnly' && signUpProductType != 'This Branch Only')){	
				$( ".is-current-membership-change" ).addClass( "product-register-div" );
			}else{
				$( ".is-current-membership-change" ).removeClass( "product-register-div" );
			}
		}else {			
			$( ".is-current-membership-change" ).removeClass( "product-register-div" );
		}
		    	
	});*/
	
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
		  $("#sumFinalAmountTd").html(finalAmount);
	});
	
	$('#applypromo').click(function() {
		
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
													  
													  /*var finalAmount = 0;
													  if($("#sumEarlyBirdDiscount").text() != '' && $("#sumPromoCodeVal").text() != ''){
													  	finalAmount = parseFloat($("#sumTotalPriceTd").text()) - parseFloat($("#sumEarlyBirdDiscount").text()) - parseFloat($("#sumPromoCodeVal").text());
													  }else if($("#sumEarlyBirdDiscount").text() != ''){
													  	finalAmount = parseFloat($("#sumTotalPriceTd").text()) - parseFloat($("#sumEarlyBirdDiscount").text());
													  }else if($("#sumPromoCodeVal").text() != ''){
													  	finalAmount = parseFloat($("#sumTotalPriceTd").text()) - parseFloat($("#sumPromoCodeVal").text());
													  }else {
													  	finalAmount = parseFloat($("#sumTotalPriceTd").text());
													  }
													  $("#sumFinalAmountTd").html(finalAmount);*/
													  
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
	    
	});
	
	

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
    //alert(parseInt($('#signedUplocationId').val()));
    //locationDropdownlist.select(parseInt($('#signedUplocationId').val()));    
   
	 // selects item if its value is equal to "test" using predicate function
    locationDropdownlist.select(function(dataItem) {
	     return dataItem.value=== $('#signedUplocationId').val();
	 });
    getProductsByLocationId();
    //var prefLocationDropdownlist = $("#prefLocation").data("kendoDropDownList");
    //prefLocationDropdownlist.select(0);
   // $("#prefLocation").closest(".k-widget").hide(); 
    $("#paymentFrequencySelect").kendoDropDownList();
    var paymentFreq = $("#paymentFrequencySelect").data("kendoDropDownList");
    paymentFreq.select(0);
    var currentYear = new Date().getFullYear();
	currentYear = parseInt(currentYear.toString().substring(2, 4));
	for(var i=0; i<30 ; i++){
		$('#addCardExpirationYear').append($('<option>', {value: currentYear,text: currentYear}));
		currentYear = currentYear +1;
	}
	$("#addCardExpirationYear").kendoDropDownList();    
    $("#expirationMonth").kendoDropDownList();
          
    $("#paymentFrequencySelect").on('change',function(e){   	
    	$("#paymentFrequencyTd").html(paymentFreq.text());
    	$("#sumPaymentFreqTd").html(paymentFreq.text());    
    	
    	if(paymentFreq.text() == 'Annual'){
    		$("#endDateDiv").css("display","inline");
    		if(selectedProductTotalPrice != null){    			
    			$("#sumTotalPriceTd").html(parseFloat(selectedProductTotalPrice) * 12);
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
                $("#end").data('kendoDatePicker').value($("#end").val());

            } else {
                alert("Error Occured while setting Membership End Date");
            }
        }
    	}else{
    		$("#endDateDiv").css("display","none");    		
    		$("#sumTotalPriceTd").html(selectedProductTotalPrice);
    		$("#paymentBillingcycleTd").html($("#start").val() + "(Of each month)");
    		//$("#end").attr("value", "");    		
    	}
    });
    
    
    var today = kendo.date.today();
    var d = new Date();
    d.setMonth(d.getMonth() + 2);
    var start = $("#start").kendoDatePicker({
    	format: "yyyy-MM-dd",
    	value: today,      
    	max: d,
    	change: function (e) {
    		var str = $("#start").val();
    		$("#paymentBillingcycleTd").html($("#start").val() + "(Of each month)");
    		$("#progStartDateIdHidInput").attr("value", $("#start").val());    		
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
                    $("#end").data('kendoDatePicker').value($("#end").val());

                } else {
                    alert("Error Occured while setting Membership End Date");
                }
            }
        },
    	parseFormats: ["MM/dd/yyyy", "dd/MM/yyyy"]
    });    
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
    });   */ 
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
	})
     
   // $('.dob-class').kendoDatePicker();
    $(document).on('click', '.product-register-div', function(){
    	    
    	//alert("click");
    	/* console.log("header " + $(this).parent().parent().find( ".k-header" ).text());
    	console.log("price-div " + $(this).parent().parent().find( ".price-div" ).text());
    	console.log("product-description-div " +$(this).parent().parent().find( ".product-description-div" ).text());
    	console.log("product-join-fee-text-val-div " + $(this).parent().parent().find( ".product-join-fee-text-val-div" ).text());  */
    	selectedHeaderInfo = $(this).parent().parent().find( ".k-header" ).text();
    	selectedTierPriceInfo = $(this).parent().parent().find( ".price-div" ).text();
    	selectedProdDescInfo = $(this).parent().parent().find( ".product-description-div" ).text();
    	selectedJoiningFeeInfo = $(this).parent().parent().find( ".product-join-fee-text-val-div" ).text();  
    	
    	selectedProdctId = $(this).parent().parent().find( ".prod-id-div" ).text();
    	//$("#itemDetailsIdInput").attr("value", selectedProdctId);
    	selectedProductName = $(this).parent().parent().find( ".prod-name-div" ).text();
    	//selectedProductTotalPrice = $(this).parent().parent().find( ".prod-total-price-div" ).text();
    	selectedProdTandc = $(this).parent().parent().find( ".prod-tandc-div" ).html();
    	
    	selectedAutoPromoDiscount = $(this).parent().parent().find( ".prod-autoPromoDiscount-div" ).html();
    	$("#locationIdInput").attr("value", $("#location").kendoDropDownList().val());  
    	
    	//$("input:radio[name=paymentTypeSelect]").change(function() {
    	/* $(document).on('change', 'input:[name=paymentTypeSelect]', function(){
    	
    		if($("input:checkbox[name=paymentTypeSelect]").is(":checked")){
    			$("input:checkbox[name=paymentTypeSelect]").prop('checked', false); 
    		}
    	    $(this).prop('checked', false); 
    	    alert( $(this).val());
    		selectedProductTotalPrice = parseFloat(selectedTierPriceInfo) + parseFloat(selectedJoiningFeeInfo);
    		alert(selectedProductTotalPrice);
    	});  */
    	
    	/* $(document).on('click', '.paymentTypeSelectClass', function() {
    		if($("input:checkbox[name=paymentTypeSelect]").is(":checked")){
    			$("input:checkbox[name=paymentTypeSelect]").prop('checked', false); 
    		}
    	    $(this).prop('checked', true); 
    	    alert( $(this).val());
    		selectedProductTotalPrice = parseFloat(selectedTierPriceInfo) + parseFloat(selectedJoiningFeeInfo);
    		alert(selectedProductTotalPrice);
    	}); */
    	//if($("input:radio[name=paymentTypeSelect]").is(":checked")){
    	var sumChangePrice = 0; 
    	var selectedProductPriceProCal = 0;
    	if($(this).parent().parent().find( "input:radio[name=paymentTypeSelect]" ).is(":checked")){    	
    		var paymentType = $(this).parent().parent().find( "input:radio[name=paymentTypeSelect]:checked" ).val();
    		if(paymentType == 'AllArea'){
    			var allAreaPrice = $(this).parent().parent().find( ".all-area-price-td" ).text();    		
    			selectedProductTotalPrice = parseFloat(allAreaPrice) + parseFloat(selectedJoiningFeeInfo);     			
    			$("#sumTierPriceTd").html(allAreaPrice); 
    			selectedProductPriceProCal = parseFloat(allAreaPrice);
    			sumChangePrice = parseFloat(allAreaPrice) - parseFloat(signedUpProductsValue);
    			//$("#signUpProductTypeNew").attr("value", "All Branches");
    		}else if(paymentType == 'BayArea'){
    			var bayAreaPrice = $(this).parent().parent().find( ".bay-area-price-td" ).text();    		
    			selectedProductTotalPrice = parseFloat(bayAreaPrice) + parseFloat(selectedJoiningFeeInfo);        			
    			$("#sumTierPriceTd").html(bayAreaPrice);    	
    			selectedProductPriceProCal = parseFloat(bayAreaPrice);
    			sumChangePrice = parseFloat(bayAreaPrice) - parseFloat(signedUpProductsValue);
    			//$("#signUpProductTypeNew").attr("value", "Bay Area");
    		}else if(paymentType == 'ThisBranchOnly'){
    			var thisBranchPrice = $(this).parent().parent().find( ".this-branch-price-td" ).text();    		
    			selectedProductTotalPrice = parseFloat(thisBranchPrice) + parseFloat(selectedJoiningFeeInfo);        			
    			$("#sumTierPriceTd").html(thisBranchPrice);    	
    			selectedProductPriceProCal = parseFloat(thisBranchPrice);
    			sumChangePrice = parseFloat(thisBranchPrice) - parseFloat(signedUpProductsValue);
    			//$("#signUpProductTypeNew").attr("value", "This Branch Only");    			
    		}  
    		
    	} else{
			selectedProductTotalPrice = parseFloat(selectedTierPriceInfo) + parseFloat(selectedJoiningFeeInfo);  
			$("#sumTierPriceTd").html(selectedTierPriceInfo);
			selectedProductPriceProCal = parseFloat(selectedTierPriceInfo);
			sumChangePrice = parseFloat(selectedTierPriceInfo) - parseFloat(signedUpProductsValue);
		}
    	
    	/*Prorate price calculation Formula 
    	E = ((ExistingMembershipPrice/30) * (nextBillDate - CurrentDate))
    	N=((NewMembershipPrice/30) * (nextBillDate - CurrentDate)
    	RN=((NewMembershipPrice/30)*((currentDate+1Month)-NextBillDate))
    	Pro-rated formula = -(e-n)+rn
    	*/    	
    	
    	var proratedPriceVal = 0;
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
        		var nextMonthDate = new Date();
        		nextMonthDate = nextMonthDate.addMonths(1);
        		var signedUpProdDayVal = signedUpProductsValue/30;
        		var newProdDayVal = selectedProductPriceProCal/30;
        		var noOfDays = days_between(nextBillingDateFormatted, currentDate);
        		var nextMonthDays = days_between(nextMonthDate, nextBillingDateFormatted); 
        		
        		var existingMembershipPriceVal = (signedUpProdDayVal * noOfDays);        		
        		var newMembershipPriceVal = (newProdDayVal * noOfDays);        		
        		var rn = (newProdDayVal * nextMonthDays);
        		
        		/*var existingMembershipPriceVal = ((signedUpProductsValue/30) * (nextBillingDateFormatted - currentDate));
        		
        		var newMembershipPriceVal = ((selectedProductPriceProCal/30) * (nextBillingDateFormatted - currentDate));
        		
        		var rn = ((selectedProductPriceProCal/30) * (nextMonthDate - nextBillingDateFormatted));*/
        		
        		var proratedProductPriceValue = - existingMembershipPriceVal + newMembershipPriceVal + rn;
        		if(proratedProductPriceValue != null){    	    	
	    	    	$("#sumProRatePriceTr").css("display","");
		    		$("#sumProRatePriceTd").html(Math.round(proratedProductPriceValue));   	    	
	    	    }else{
	    	    	$("#sumProRatePriceTr").css("display","none");
		    		$("#sumProRatePriceTd").html(0); 
	    	    }   
        		
        		if(Math.round(proratedProductPriceValue) != null && Math.round(proratedProductPriceValue) < 0){
           		 $("#refundStatusBlock").css("display", "block");	
       			 $("#refundSuccess").css("display", "block");	
       			 $("#refundSuccess" ).html("You may be eligble for $"+Math.round(proratedProductPriceValue).toString().replace('-', '' ) + " refund. Please contact Y Agent.");							  		 
       		  
	           	}else{
	           		$("#refundStatusBlock").css("display", "none");	
	           		$("#refundSuccess").css("display", "none");	
	       			$( "#refundSuccess" ).html("");	
	           	}
        		
        		/*var currentDate = new Date();
        	    var dateOfMonth = currentDate.getDate();
        	    if(dateOfMonth < 30){
    	    		var prodOneDayPrice = selectedProductPriceProCal/30; //30 days in month taken as constant.    		
    	    	    var remainingDaysOfMonth = (30 - dateOfMonth);
    	    	    if(remainingDaysOfMonth >= 1){
    	    	    	proratedPriceVal = parseFloat(prodOneDayPrice)  * parseFloat(remainingDaysOfMonth);
    	    	    }    	    
    	    	    if(proratedPriceVal != null && proratedPriceVal >= 0){    	    	
    	    	    	$("#sumProRatePriceTr").css("display","");
    		    		$("#sumProRatePriceTd").html(Math.round(proratedPriceVal));   	    	
    	    	    }else{
    	    	    	$("#sumProRatePriceTr").css("display","none");
    		    		$("#sumProRatePriceTd").html(0); 
    	    	    }
        	    }else{
        	    	$("#sumProRatePriceTr").css("display","none");
    	    		$("#sumProRatePriceTd").html(0);
        	    }*/
    		}
    		
    	}
    	
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
    	selectedItemDetailsId = $(this).parent().parent().find( ".prod-itemDetailsId-div" ).html();
    	
    	$("#productIdTd").html(selectedProdctId);
    	$("#productIdHidInput").attr("value", selectedProdctId);
    	$("#productNameTd").html(selectedProductName);
    	$("#productDescriptionTd").html(selectedProdDescInfo);
    	$("#productTypeTd").html($(this).parent().parent().find( ".prod-type-div" ).text());    	
    	
    	$("#productPriceTd").html(selectedProductTotalPrice);
    	$("#paymentAmountSpan").html(selectedProductTotalPrice);
    	$("#paymentTotalPriceTd").html(selectedProductTotalPrice);	
    	$("#sumTotalPriceTd").html(selectedProductTotalPrice);
    	
    	$(".termsDiv").html(selectedProdTandc);
    	$("#userTC").attr("value", selectedProdTandc);
    	
    	$("#paymentJoinFeeTd").html(selectedJoiningFeeInfo);
    	$("#sumJoinFeeTd").html(selectedJoiningFeeInfo);
    	
    	var oneAdultWithKidsHtml = getKidsInfoHeader() + getKidsInfoHtml("1");
    	var twoAdultsHtml = getSecondUserInfoHeader() + getSecondUserInfoData("1");
    	var twoAdultsWithKidsHtml = getSecondUserInfoHeader() + getSecondUserInfoData("1") + getKidsInfoHeader() + getKidsInfoHtml("2");
    	var threeAdultsWithKidsHtml = getSecondUserInfoHeader() + getSecondUserInfoData("1") + getThirdUserInfoHeader() + getThirdUserInfoData("2") + getKidsInfoHeader() + getKidsInfoHtml("3");
    	
    	if(selectedHeaderInfo == 'One Adultw/ Kids'){
    		$('#familyInfoFormTable').css("display" , "inline-table");
    		$('#familyInfoFormTable').html(oneAdultWithKidsHtml);
    		$('#familyInfoFormTable').find(".dob-class").kendoDatePicker();
    		secKidCount = 2;
    	}else if(selectedHeaderInfo == 'Two Adults' ){
    		$('#familyInfoFormTable').css("display" , "inline-table");
    		$('#familyInfoFormTable').html(twoAdultsHtml);    		
    		$('#familyInfoFormTable').find(".dob-class").kendoDatePicker();   		
    	}else if(selectedHeaderInfo == 'Two Adultsw/ Kids'){
    		$('#familyInfoFormTable').css("display" , "inline-table");
    		$('#familyInfoFormTable').html(twoAdultsWithKidsHtml);
    		$('#familyInfoFormTable').find(".dob-class").kendoDatePicker();
    		
    		secKidCount = 3;
    	}else if(selectedHeaderInfo == 'Three Adultsw/ or w/o kids'){
    		$('#familyInfoFormTable').css("display" , "inline-table");
    		$('#familyInfoFormTable').html(threeAdultsWithKidsHtml);
    		$('#familyInfoFormTable').find(".dob-class").kendoDatePicker();
    		
    		secKidCount = 4;
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
    	
    	/*if(sumChangePrice != null && sumChangePrice < 0){
    		 $("#refundStatusBlock").css("display", "block");	
			 $("#refundSuccess").css("display", "block");	
			 $("#refundSuccess" ).html("You may be eligble for $"+sumChangePrice.toString().replace('-', '' ) + " refund. Please contact Y Agent.");							  		 
		  
    	}else{
    		$("#refundStatusBlock").css("display", "none");	
    		$("#refundSuccess").css("display", "none");	
			$( "#refundSuccess" ).html("");	
    	}  */	
    	
    	$(".phone-number").mask("(999) 999-9999");    	
    	$("#wizard"). smartWizard("fixHeight");
    	registerSelected = true;
    	$('#wizard').smartWizard('goToStep',2);
    	
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
		onShowStep: null,  // triggers when showing a step
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
		var fullName = firstName + " " + lastName;
		var tcname = $("#tcname").val();
		if(tcname != fullName){
			return false;
		} else{
			return true;
		}
		
	}, "Please enter the primary contact's First Name and Last Name.");
	
	
	
	
	
	

	
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
         			$("#productIdHidInput").attr("value", $(this).html());
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
	 
	 $('body').on( "click", "#memberSignInSpan", function() { 	 
		 signInSelected = "true";
		 $('#loginDiv').css("display","inline");
		 $('#registerDiv').css("display","none");
	 });
	 
	 $('body').on( "click", "#newMemberSpan", function() { 	 
		 signInSelected = "false";
		 $('#loginDiv').css("display","none");
		 $('#registerDiv').css("display","inline");
	 });
	 
	 
  	
	$("#wizard"). smartWizard("fixHeight");
      
	});
	
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
	if(!$("input[name='chkTermsAndCond']:checked").val()){ 
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
 		$('#wizard').smartWizard('setError',{stepnum:2,iserror:false});
		$('#wizard').smartWizard('hideMessage');		
		$("#progFinalAmtIdHidInput").attr("value", $("#paymentTotalPriceTd").text());	
		$("#progStartDateIdHidInput").attr("value", $("#start").val());
		var selectedPaymentMethod = $("#paymentInfoRadio").data("kendoDropDownList").text();
		if($("#paymentInfoRadio").val()!='New' && $("#paymentInfoRadio").val()!='NewBank'){
			if (selectedPaymentMethod.indexOf(",") ==-1) {			    
				$(".k-loading-mask").show();
				$(".k-loading-text").html("Please wait");
				$("#statusBlock-payment").css("display", "none");	
				$("#tcloginErrorSpan-payment").css("display", "none");	
				$( "#tcloginErrorSpan-payment" ).html("");
				
				$.ajax({
					  type: "POST",
					  url: "processPaymentByTokenId",	
					  data: { token: $("#paymentTokenIdSpan").text(), totalAmount : $("#sumFinalAmountTd").text()},
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
			
			var jsonData = {
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
			};
			//console.log(jsonData);
			var win = document.getElementById("childIframeId").contentWindow;
			win.postMessage(jsonData, '*'); 				
			//document.forms["becomeMemberForm"].submit();
		}		 
		//document.forms["becomeMemberForm"].submit();
		return true;
 	}
}

function nextClick(obj, context){	
	//$("#wizard"). smartWizard("fixHeight");	
	//$(".stepContainer").height($(".stepContainer").find("div:not(:hidden)").height());
	//$("#paymentTotalPriceTd").html($("#paymentAmountSpan").text());
	//var pricingOption = $("#pricingOption").data("kendoDropDownList");  
	
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
    var finalAmount = 0;
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
		} else if(selectedHeaderInfo == 'Two Adults' ){
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
    /*if(context.toStep == 4 ){
    	if($("#sumEarlyBirdDiscount").text() != '' && $("#sumPromoCodeVal").text() != ''){
    		finalAmount = parseFloat($("#sumTotalPriceTd").text()) - parseFloat($("#sumEarlyBirdDiscount").text()) - parseFloat($("#sumPromoCodeVal").text());
    	}else if($("#sumEarlyBirdDiscount").text() != ''){
    		finalAmount = parseFloat($("#sumTotalPriceTd").text()) - parseFloat($("#sumEarlyBirdDiscount").text());
    	}else if($("#sumPromoCodeVal").text() != ''){
    		finalAmount = parseFloat($("#sumTotalPriceTd").text()) - parseFloat($("#sumPromoCodeVal").text());
    	}else {
    		finalAmount = parseFloat($("#sumTotalPriceTd").text());
    	}   	
    }*/
    
    if(context.toStep == 5 ){
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
    	if(finalAmount < 0){
    		$("#sumFinalAmountTd").html(finalAmount);
    	}else{
    		$("#sumFinalAmountTd").html(finalAmount);
    	}
    	 
    	$("#expirationMonthHid").attr("value", $("#expirationMonth").val());
   	 $("#expirationYearHid").attr("value", $("#addCardExpirationYear").val());
   	 $("#nickNameHid").attr("value", $("#nickName").val());
    	
    }
    
    	
	var paymentFreq2 = $("#paymentFrequencySelect").data("kendoDropDownList");
    $("#paymentFrequencyTd").html(paymentFreq2.text());
    $("#sumPaymentFreqTd").html(paymentFreq2.text());
	$(".stepContainer").height($(".stepContainer").find("div:not(:hidden)").height());

	if( context.fromStep == 1 ){	    	
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
    }else if( context.fromStep == 3 && context.toStep == 4 ){	
		 if(!$("input[name='chkTermsAndCond']:checked").val()){ 
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
	}else {	
		//$("#wizard"). smartWizard("fixHeight");	
		return true;
	}
	
}

function forceLower(strInput) {
	strInput.value=strInput.value.toLowerCase();
}

function getSecondUserInfoHeader(){
	var secondUserInfoTableHtml = '';
	secondUserInfoTableHtml += '<tr>';
	secondUserInfoTableHtml += '<td colspan="2"><h2 style="margin-top: 5px; margin-bottom : 5px;"><span>MEMBER 2 INFORMATION</span></h2> </td>';
	secondUserInfoTableHtml += '<td >&nbsp;</td>';
	secondUserInfoTableHtml += '<td >&nbsp;</td>';
	secondUserInfoTableHtml += '<td >&nbsp;</td>';
	secondUserInfoTableHtml += '</tr>';
	return secondUserInfoTableHtml;
}

function getThirdUserInfoHeader(){
	var secondUserInfoTableHtml = '';
	secondUserInfoTableHtml += '<tr>';
	secondUserInfoTableHtml += '<td colspan="2"><h2 style="margin-top: 5px; margin-bottom : 5px;"><span>MEMBER 3 INFORMATION</span></h2> </td>';
	secondUserInfoTableHtml += '<td >&nbsp;</td>';
	secondUserInfoTableHtml += '<td >&nbsp;</td>';
	secondUserInfoTableHtml += '<td >&nbsp;</td>';
	secondUserInfoTableHtml += '</tr>';
	return secondUserInfoTableHtml;
}

function getSecondUserInfoData(userNo){
	var secondUserInfoTableHtml = '';
	secondUserInfoTableHtml += '<tr>';
	secondUserInfoTableHtml += '<td><input type="text" class="form-control" value="" title="Please enter yout First Name" name="userLst['+userNo+'].personFirstName" id="secFirstName" maxlength="50" placeholder="First Name"  /></td>';
	secondUserInfoTableHtml += '<td>&nbsp;</td>';
	secondUserInfoTableHtml += '<td><input type="text" class="form-control" value="" title="Please enter yout Last Name" name="userLst['+userNo+'].personLastName" id="secLastName" maxlength="50" placeholder="Last Name" /></td>';
	secondUserInfoTableHtml += '<td>&nbsp;</td>';
	secondUserInfoTableHtml += '</tr>';
	
	secondUserInfoTableHtml += '<tr>';
	secondUserInfoTableHtml += '<td style="padding-bottom: 8px;"><input type="text" class="dob-class"  title="Please enter your Date of Birth" name="userLst['+userNo+'].dateOfBirth" id="secDOB" maxlength="50" placeholder="D.O.B"  style="width: 205px; height: 30px; margin: 0; padding: 0;" /></td>';
	secondUserInfoTableHtml += '<td id="dop-er"></td>';
	secondUserInfoTableHtml += '<td><input type="text" class="form-control phone-number" title="Please enter your Phone Number" name="userLst['+userNo+'].primaryFormattedPhoneNumber" id="secPhoneNumber" maxlength="50" placeholder="Phone Number" /></td>';
	secondUserInfoTableHtml += '<td>&nbsp;</td>';
	secondUserInfoTableHtml += '</tr>';	
	
	secondUserInfoTableHtml += '<tr>';
	secondUserInfoTableHtml += '<td><input type="text" class="form-control"  name="userLst['+userNo+'].primaryEmailAddress" id="secEmail"  maxlength="50" placeholder="Email"  /></td>';
	secondUserInfoTableHtml += '<td id="dop-er"></td>';

	secondUserInfoTableHtml += '<td>';
	secondUserInfoTableHtml += '<span>';
	secondUserInfoTableHtml += '<span><span><input id="secGenderM" name="userLst['+userNo+'].gender" class="form-control" style="width: 15px;" type="radio" value="Male"></span><span style="margin-left : 5px;">Male</span></span>';
	secondUserInfoTableHtml += '<span><span><input id="secGenderF" name="userLst['+userNo+'].gender" class="form-control" style="width : 15px;" type="radio" value="Female"></span><span style="margin-left : 5px;">Female</span></span>';
	secondUserInfoTableHtml += '</span>';
	secondUserInfoTableHtml += '</td>';	
	
	secondUserInfoTableHtml += '<td>&nbsp;</td>';
	secondUserInfoTableHtml += '</tr>';
	
	secondUserInfoTableHtml += '<tr style="display : none;">';
	secondUserInfoTableHtml += '<td><input style="display:none;" name="userLst['+userNo+'].isAdult" value="true"/></td>';
	secondUserInfoTableHtml += '<td>&nbsp;</td>';
	secondUserInfoTableHtml += '<td><input style="display:none;" name="userLst['+userNo+'].usrSequence" value="'+userNo+'" /></td>';
	secondUserInfoTableHtml += '<td>&nbsp;</td>';
	secondUserInfoTableHtml += '</tr>';
	
	return secondUserInfoTableHtml;
}

function getThirdUserInfoData(userNo){
	var secondUserInfoTableHtml = '';
	secondUserInfoTableHtml += '<tr>';
	secondUserInfoTableHtml += '<td><input type="text" class="form-control" value="" title="Please enter yout First Name" name="userLst['+userNo+'].personFirstName" id="thirdFirstName" maxlength="50" placeholder="First Name"  /></td>';
	secondUserInfoTableHtml += '<td>&nbsp;</td>';
	secondUserInfoTableHtml += '<td><input type="text" class="form-control" value="" title="Please enter yout Last Name" name="userLst['+userNo+'].personLastName" id="thirdLastName" maxlength="50" placeholder="Last Name" /></td>';
	secondUserInfoTableHtml += '<td>&nbsp;</td>';
	secondUserInfoTableHtml += '</tr>';
	
	secondUserInfoTableHtml += '<tr>';
	secondUserInfoTableHtml += '<td style="padding-bottom: 8px;"><input type="text" class="dob-class"  title="Please enter your Date of Birth" name="userLst['+userNo+'].dateOfBirth" id="thirdDOB" maxlength="50" placeholder="D.O.B"  style="width: 205px; height: 30px; margin: 0; padding: 0;" /></td>';
	secondUserInfoTableHtml += '<td id="dop-er"></td>';
	secondUserInfoTableHtml += '<td><input type="text" class="form-control phone-number" title="Please enter your Phone Number" name="userLst['+userNo+'].primaryFormattedPhoneNumber" id="thirdPhoneNumber" maxlength="50" placeholder="Phone Number" /></td>';
	secondUserInfoTableHtml += '<td>&nbsp;</td>';
	secondUserInfoTableHtml += '</tr>';	
	
	secondUserInfoTableHtml += '<tr>';
	secondUserInfoTableHtml += '<td><input type="text" class="form-control"  name="userLst['+userNo+'].primaryEmailAddress" id="thirdEmail"  maxlength="50" placeholder="Email"  /></td>';
	secondUserInfoTableHtml += '<td id="dop-er"></td>';

	secondUserInfoTableHtml += '<td>';
	secondUserInfoTableHtml += '<span>';
	secondUserInfoTableHtml += '<span><span><input id="thirdGenderM" name="userLst['+userNo+'].gender" class="form-control" style="width: 15px;" type="radio" value="Male"></span><span style="margin-left : 5px;">Male</span></span>';
	secondUserInfoTableHtml += '<span><span><input id="thirdGenderF" name="userLst['+userNo+'].gender" class="form-control" style="width : 15px;" type="radio" value="Female"></span><span style="margin-left : 5px;">Female</span></span>';
	secondUserInfoTableHtml += '</span>';
	secondUserInfoTableHtml += '</td>';	
	
	secondUserInfoTableHtml += '<td>&nbsp;</td>';
	secondUserInfoTableHtml += '</tr>';
	
	secondUserInfoTableHtml += '<tr style="display : none;">';
	secondUserInfoTableHtml += '<td><input style="display:none;" name="userLst['+userNo+'].isAdult" value="true"/></td>';
	secondUserInfoTableHtml += '<td>&nbsp;</td>';
	secondUserInfoTableHtml += '<td><input style="display:none;" name="userLst['+userNo+'].usrSequence" value="'+userNo+'" /></td>';
	secondUserInfoTableHtml += '<td>&nbsp;</td>';
	secondUserInfoTableHtml += '</tr>';
	
	return secondUserInfoTableHtml;
}

function getKidsInfoHeader(){
	var kidsInfoTableHtml = '';
	kidsInfoTableHtml += '<tr>';
	kidsInfoTableHtml += '<td colspan="2"><h2 style="margin-top: 5px; margin-bottom : 5px; color: #888;"><span>Kids INFORMATION</span></h2> </td>';
	//kidsInfoTableHtml += '<td >&nbsp;</td>';
	kidsInfoTableHtml += '<td >&nbsp;</td>';
	kidsInfoTableHtml += '<td ><a class="add-child-lnk">Add Child &nbsp; <span>+</span></a></td>';
	kidsInfoTableHtml += '</tr>';
	return kidsInfoTableHtml;
}

function getKidsInfoHtml(kidNo){
	var kidsInfoTableHtml = '';
	kidsInfoTableHtml += '<tr>';
	kidsInfoTableHtml += '<td colspan="4">';
	kidsInfoTableHtml += '<table border="0" width="101%" class="firstKidInfoForm">';
	kidsInfoTableHtml += '<tr>';
	kidsInfoTableHtml += '<td><input type="text" class="form-control personFirstName" value="" title="Please enter yout First Name" name="userLst['+kidNo+'].personFirstName" id="firstName" maxlength="50" placeholder="First Name"  /></td>';
	kidsInfoTableHtml += '<td>&nbsp;</td>';
	kidsInfoTableHtml += '<td><input type="text" class="form-control personLastName" value="" title="Please enter yout Last Name" name="userLst['+kidNo+'].personLastName" id="lastName" maxlength="50" placeholder="Last Name" /></td>';
	kidsInfoTableHtml += '<td>&nbsp;</td>';
	kidsInfoTableHtml += '</tr>';
	
	kidsInfoTableHtml += '<tr>';
	kidsInfoTableHtml += '<td style="padding-bottom: 8px;"><input type="text" class="form-control dob-class dateOfBirth"  title="Please enter your Date of Birth" name="userLst['+kidNo+'].dateOfBirth"  maxlength="50" placeholder="D.O.B"  style="width: 205px; height: 30px; margin: 0; padding: 0;" /></td>';
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
	kidsInfoTableHtml += '<td><input style="display:none;" name="userLst['+kidNo+'].isAdult" value="false"/></td>';
	kidsInfoTableHtml += '<td>&nbsp;</td>';	
	kidsInfoTableHtml += '<td><input style="display:none;" name="userLst['+kidNo+'].usrSequence" value="'+kidNo+'" /></td>';		
	kidsInfoTableHtml += '<td>&nbsp;</td>';
	kidsInfoTableHtml += '</tr>';
	
	return kidsInfoTableHtml;
}

function getSecondaryKidsInfoHtml(kidNo){
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
	kidsInfoTableHtml += '<td><input type="text" class="form-control personFirstName" value="" title="Please enter yout First Name" name="userLst['+kidNo+'].personFirstName" id="firstName" maxlength="50" placeholder="First Name"  /></td>';
	kidsInfoTableHtml += '<td>&nbsp;</td>';
	kidsInfoTableHtml += '<td><input type="text" class="form-control personLastName" value="" title="Please enter yout Last Name" name="userLst['+kidNo+'].personLastName" id="lastName" maxlength="50" placeholder="Last Name" /></td>';
	kidsInfoTableHtml += '<td><a class="remove-child-lnk">Remove Child &nbsp; <span>-</span></a></td>';
	kidsInfoTableHtml += '</tr>';
	
	kidsInfoTableHtml += '<tr>';
	kidsInfoTableHtml += '<td style="padding-bottom: 8px;"><input type="text" class="form-control dob-class dateOfBirth"  title="Please enter your Date of Birth" name="userLst['+kidNo+'].dateOfBirth"  maxlength="50" placeholder="D.O.B"  style="width: 205px; height: 30px; margin: 0; padding: 0;" /></td>';
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
	kidsInfoTableHtml += '<td><input style="display:none;" name="userLst['+kidNo+'].isAdult" value="false"/></td>';
	kidsInfoTableHtml += '<td>&nbsp;</td>';	
	kidsInfoTableHtml += '<td><input style="display:none;" name="userLst['+kidNo+'].usrSequence" value="'+kidNo+'" /></td>';		
	kidsInfoTableHtml += '<td>&nbsp;</td>';
	kidsInfoTableHtml += '</tr>';
	
	return kidsInfoTableHtml;
}

function populateKidsSummaryInformation(){
	var firstKidFName = $(".firstKidInfoForm").find(".personFirstName").val();
	var firstKidLName = $(".firstKidInfoForm").find(".personLastName").val();
	var firstKidDob = $(".firstKidInfoForm").find("input.dateOfBirth").val();
	var firstKidGender = $(".firstKidInfoForm").find(".gender:checked").val();

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
		kidsInfoHtmlUpd += '<td>'+ $(obj).find(".personFirstName").val() +'</td>';
		kidsInfoHtmlUpd += '</tr>';
		
		kidsInfoHtmlUpd += '<tr>';
		kidsInfoHtmlUpd += '<td><span><b>Last Name</b></span></td>';
		kidsInfoHtmlUpd += '<td>'+ $(obj).find(".personLastName").val() +'</td>';
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

$('body').on( "click", "#selectFamilyMemberbtn", function() { 	 
	 var kendoWindow = $("<div />").kendoWindow({
			title: "Select family member",
			resizable: false,
			modal: true,
			width:400
		});
	 kendoWindow.data("kendoWindow")
	 .content($("#select-family-member-Box").html())
	 .center().open();
	 
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
	   		}
	   	})
	   	.end();
	   	kendoWindowSelect = kendoWindow;
	   });	
	 

/*	kendoWindow
	.find(".confirm-family-member-select")
	.click(function() {
		if ($(this).hasClass("confirm-family-member-select")) {     		
			var secUserFNameTd = "";
			var secUserLNameTd = "";
			var secUserGenderTd = "";
			var secUserBdayTd = "";
			var secUserPhoneNoTd = "";
			var secUserEmailAddTd = "";
			
			var thirdUserFNameTd = "";
			var thirdUserLNameTd = "";
			var thirdUserGenderTd = "";
			var thirdUserBdayTd = "";
			var thirdUserPhoneNoTd = "";
			var thirdUserEmailAddTd = "";
			
			 if($("input[name='selectSecMemberCheckBox']:checked").val()){
				 secUserFNameTd = $(this).parent().parent().find(".secUserFNameTd").html();
				 secUserLNameTd = $(this).parent().parent().find(".secUserLNameTd").html();
				 secUserGenderTd = $(this).parent().parent().find(".secUserGenderTd").html();
				 secUserBdayTd = $(this).parent().parent().find(".secUserBdayTd").html();
				 secUserPhoneNoTd = $(this).parent().parent().find(".secUserPhoneNoTd").html();
				 secUserEmailAddTd = $(this).parent().parent().find(".secUserEmailAddTd").html();				 
			 }else{
				 
			 }
			 
			 if($("input[name='selectThirdMemberCheckBox']:checked").val()){
				  thirdUserFNameTd = $(this).parent().parent().find(".thirdUserFNameTd").html();
				  thirdUserLNameTd = $(this).parent().parent().find(".thirdUserLNameTd").html();
				  thirdUserGenderTd = $(this).parent().parent().find(".thirdUserGenderTd").html();
				  thirdUserBdayTd = $(this).parent().parent().find(".thirdUserBdayTd").html();
				  thirdUserPhoneNoTd = $(this).parent().parent().find(".thirdUserPhoneNoTd").html();
				  thirdUserEmailAddTd = $(this).parent().parent().find(".thirdUserEmailAddTd").html();				
			 }else{
				 
			 }
			 
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
		    
		    if(selectedHeaderInfo == 'One Adultw/ Kids'){		    	
		    	addKidsInfoFunction(kendoWindow);
			}else if(selectedHeaderInfo == 'Two Adults' ){
				if($(document).find("input[name='selectSecMemberCheckBox']:checked").val()){
					$('#secFirstName').attr("value", secUserFNameTd);
					$('#secLastName').attr("value", secUserLNameTd);
					$('#secDOB').attr("value", secUserBdayTd); 
					$('#secPhoneNumber').attr("value", secUserPhoneNoTd); 
					$('#secEmail').attr("value", secUserEmailAddTd);					
				    if(thirdUserGenderTd == "Male") {
				    	$('#secGenderM').prop('checked', true);
				    }else{
				    	$('#secGenderF').prop('checked', true);
				    }				 
				 }
				if($(document).find("input[name='selectThirdMemberCheckBox']:checked").val()){
					$('#secFirstName').attr("value", thirdUserFNameTd);
					$('#secLastName').attr("value", thirdUserLNameTd);
					$('#secDOB').attr("value", thirdUserBdayTd); 
					$('#secPhoneNumber').attr("value", thirdUserPhoneNoTd); 
					$('#secEmail').attr("value", thirdUserEmailAddTd);					
				    if(secUserGenderTd == "Male") {
				    	$('#secGenderM').prop('checked', true);
				    }else{
				    	$('#secGenderF').prop('checked', true);
				    }				
				 }
				
											  		
			}else if(selectedHeaderInfo == 'Two Adultsw/ Kids'){
				if($(document).find("input[name='selectSecMemberCheckBox']:checked").val()){
					$('#secFirstName').attr("value", secUserFNameTd);
					$('#secLastName').attr("value", secUserLNameTd);
					$('#secDOB').attr("value", secUserBdayTd); 
					$('#secPhoneNumber').attr("value", secUserPhoneNoTd); 
					$('#secEmail').attr("value", secUserEmailAddTd);					
				    if(secUserGenderTd == "Male") {
				    	$('#secGenderM').prop('checked', true);
				    }else{
				    	$('#secGenderF').prop('checked', true);
				    }				 
				 }
				if($(document).find("input[name='selectThirdMemberCheckBox']:checked").val()){
					$('#secFirstName').attr("value", thirdUserFNameTd);
					$('#secLastName').attr("value", thirdUserLNameTd);
					$('#secDOB').attr("value", thirdUserBdayTd); 
					$('#secPhoneNumber').attr("value", thirdUserPhoneNoTd); 
					$('#secEmail').attr("value", thirdUserEmailAddTd);					
				    if(thirdUserGenderTd  == "Male") {
				    	$('#secGenderM').prop('checked', true);
				    }else{
				    	$('#secGenderF').prop('checked', true);
				    }				
				 }
				addKidsInfoFunction(kendoWindow);
				
			}else if(selectedHeaderInfo == 'Three Adultsw/ or w/o kids'){
				if($(document).find("input[name='selectSecMemberCheckBox']:checked").val()){
					$('#secFirstName').attr("value", secUserFNameTd);
					$('#secLastName').attr("value", secUserLNameTd);
					$('#secDOB').attr("value", secUserBdayTd); 
					$('#secPhoneNumber').attr("value", secUserPhoneNoTd); 
					$('#secEmail').attr("value", secUserEmailAddTd);					
				    if(secUserGenderTd == "Male") {
				    	$('#secGenderM').prop('checked', true);
				    }else{
				    	$('#secGenderF').prop('checked', true);
				    }				 
				 }
				if($(document).find("input[name='selectThirdMemberCheckBox']:checked").val()){
					$('#thirdFirstName').attr("value", thirdUserFNameTd);
					$('#thirdLastName').attr("value", thirdUserLNameTd);
					$('#thirdDOB').attr("value", thirdUserBdayTd); 
					$('#thirdPhoneNumber').attr("value", thirdUserPhoneNoTd); 
					$('#thirdEmail').attr("value", thirdUserEmailAddTd);					
				    if(thirdUserGenderTd  == "Male") {
				    	$('#thirdGenderM').prop('checked', true);
				    }else{
				    	$('#thirdGenderF').prop('checked', true);
				    }				
				 }
				addKidsInfoFunction(kendoWindow);
				
			}else{
				$('#familyInfoFormTable').css("display" , "none");
			} 
			 
			kendoWindow.data("kendoWindow").close();
		}
	})
	.end();
});	*/

function populateAdultFunction(kendoWindow){
	var primaryKidInfoInserted = 0;
	var count =  0;
	kendoWindow.find("#selectFamilyMemberTable input[type=checkbox]").each(function () {
		 if(this.checked){	
			 count = count +1;
		 }
	});
	
	if((selectedHeaderInfo == 'Adult' || selectedHeaderInfo == 'Youth' || selectedHeaderInfo == 'One Adultw/ Kids') && (count > 1)){
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
	}else if((selectedHeaderInfo == 'Two Adults' || selectedHeaderInfo == 'Two Adultsw/ Kids')  && (count > 2)){
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
	}else if(selectedHeaderInfo == 'Three Adultsw/ or w/o kids'  && (count > 3) ){
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
	}else{	
		kendoWindow.find("#selectFamilyMemberTable input[type=checkbox]").each(function () {
			 if(this.checked){	
				var adultFirstName = $(this).parent().parent().find(".userFNameTd").html();
				var adultLastName = $(this).parent().parent().find(".userLNameTd").html();
				var adultGender = $(this).parent().parent().find(".userGenderTd").html();
				var adultDob = $(this).parent().parent().find(".userBdayTd").html();
				var adultPhoneNumber = $(this).parent().parent().find(".userPhoneNoTd").html();
				var adultEmail = $(this).parent().parent().find(".userEmailAddTd").html();
				
				
				if(selectedHeaderInfo == 'Adult' || selectedHeaderInfo == 'Youth'){
						populatePrimaryMember(adultFirstName, adultLastName, adultPhoneNumber, adultEmail, adultDob, adultGender);   				
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
					}else if(selectedHeaderInfo == 'Three Adultsw/ or w/o kids'){
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
	kidsInfoTableHtml += '<td><input type="text" class="form-control personFirstName"  title="Please enter yout First Name" name="userLst['+count+'].personFirstName" id="firstName" maxlength="50" placeholder="First Name"  value="'+kidFirstName+'"/></td>';
	kidsInfoTableHtml += '<td>&nbsp;</td>';
	kidsInfoTableHtml += '<td><input type="text" class="form-control personLastName"  title="Please enter yout Last Name" name="userLst['+count+'].personLastName" id="lastName" maxlength="50" placeholder="Last Name" value="'+kidLastName+'"/></td>';
	kidsInfoTableHtml += '<td><a class="remove-child-lnk">Remove Child &nbsp; <span>-</span></a></td>';
	kidsInfoTableHtml += '</tr>';
	
	kidsInfoTableHtml += '<tr>';
	kidsInfoTableHtml += '<td style="padding-bottom: 8px;"><input type="text" class="form-control dob-class dateOfBirth"  title="Please enter your Date of Birth" name="userLst['+count+'].dateOfBirth"  maxlength="50" placeholder="D.O.B"  style="width: 205px; height: 30px; margin: 0; padding: 0;" value="'+kidDob+'"/></td>';
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
			  var jsonDataArr = jQuery.parseJSON(data);
			  if(jsonDataArr != null && jsonDataArr.length > 0){
				  for(var i = 0; i<jsonDataArr.length; i++){
					  //console.log(jsonDataArr[i].id); 
					  if(jsonDataArr[i].id == signUpProductId){
						  signedUpTierPriceInfo = jsonDataArr[i].tierPrice;
						  signedUpJoiningFeeInfo = jsonDataArr[i].joiningPrice;
						  signedUpProdctId = jsonDataArr[i].id;
						  signedUpProductName = jsonDataArr[i].productName;	    						  
						  signedUpAutoPromoDiscount = jsonDataArr[i].autoPromoDiscount;
						  signedUpAllAreaPrice = jsonDataArr[i].allAreaPrice;
						  signedUpBayAreaPrice = jsonDataArr[i].bayAreaPrice;
						  break;
					  }
				  }
				  var signUpProductType = $('#signUpProductType').val();
				  if(signUpProductType == 'All Branches' || signUpProductType == 'Bay Area') {
					  $( ".is-current-membership-change" ).addClass( "product-register-div" );
				  }else{
					  $( ".is-current-membership-change" ).removeClass( "product-register-div" );
				  }
				  
				  for(var i = 0; i<jsonDataArr.length; i++){
					  //console.log(jsonDataArr[i].id);  
					  pricingBoxHtml += '<li>';
					  if(jsonDataArr[i].id != signUpProductId){
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
					  
					  if(jsonDataArr[i].id != signUpProductId){
					      tierPriceVal = parseFloat(jsonDataArr[i].tierPrice) - parseFloat(signedUpTierPriceInfo);	    					    
					      allLocationVal = parseFloat(jsonDataArr[i].allAreaPrice) - parseFloat(signedUpAllAreaPrice);
					      bayAreaVal = parseFloat(jsonDataArr[i].bayAreaPrice) - parseFloat(signedUpBayAreaPrice);
					    
					      var signUpProductType = $('#signUpProductType').val();
      					  if(signUpProductType == 'All Branches'){
      						signedUpProductsValue = signedUpAllAreaPrice;
      					  }else if(signUpProductType == 'Bay Area') {
      						signedUpProductsValue = signedUpBayAreaPrice;
      					  }else {
      						signedUpProductsValue = signedUpTierPriceInfo;
      					  }
					  }
					  
					  pricingBoxHtml += '<div>';
					  if(jsonDataArr[i].id == signUpProductId && jsonDataArr[i].id == signUpProductId){
						  pricingBoxHtml += '<b><span style="font-size: 12.5px;" class="isCurrentMembership">Current Membership</span></b><br />';
					  }else {	    					  
						  pricingBoxHtml += '<b><span style="display:none;" class="isCurrentMembership">Membership</span></b>';
					  }
					  
					  pricingBoxHtml += '<span style="font-size: x-large; font-family: cursive;">$ <div class="price-div">'+ jsonDataArr[i].tierPrice +'</div></span>';
					  
					  pricingBoxHtml += '<div class="prod-id-div" style="display:none">'+jsonDataArr[i].id+'</div>';
					  pricingBoxHtml += '<div class="prod-name-div" style="display:none">'+jsonDataArr[i].productName+'</div>';
					  pricingBoxHtml += '<div class="prod-total-price-div" style="display:none">'+jsonDataArr[i].productPrice+'</div>';
					  pricingBoxHtml += '<div class="prod-tandc-div" style="display:none">'+jsonDataArr[i].tandc+'</div>';
					  pricingBoxHtml += '<div class="prod-autoPromoDiscount-div" style="display:none">'+jsonDataArr[i].autoPromoDiscount+'</div>';
					  pricingBoxHtml += '<div class="prod-type-div" style="display:none">'+jsonDataArr[i].productType+'</div>';
					  pricingBoxHtml += '<div class="prod-itemDetailsId-div" style="display:none">'+jsonDataArr[i].itemDetailsId+'</div>';
					  
					  
					  pricingBoxHtml += '<div class="product-description-div">'+jsonDataArr[i].productDescription+'</div>';
					  pricingBoxHtml += '<div class="product-location-div">'+locationDropdownlist.text()+'</div>';
					  pricingBoxHtml += '<div class="border-bottom-line"></div>';
					  pricingBoxHtml += '<div class="product-join-fee-text-div">One Time Join Fee</div>';
					  //pricingBoxHtml += '<span style="font-size: small; font-family: cursive;">$ <div class="product-join-fee-text-val-div">'+jsonDataArr[i].joiningPrice+'</div></span>';
					  pricingBoxHtml += '<span style="font-size: small; font-family: cursive;">$ <span class="product-join-fee-text-val-div">0</span></span>';
					  //pricingBoxHtml += '<span class="product-join-fee-text-val-div" style="display:none;">'+jsonDataArr[i].joiningPrice+'</span>';
					  pricingBoxHtml += '<div>';
					  pricingBoxHtml += '<table border="0" style="text-align: left;" id="productPriceTable">';
					  //if(jsonDataArr[i].allAreaPrice != null){
					  if(jsonDataArr[i].allAreaPrice != null && typeof jsonDataArr[i].allAreaPrice != "undefined"){
    					  pricingBoxHtml += '<tr>';
    					  if(jsonDataArr[i].id == signUpProductId && signUpProductType == 'All Branches'){
    						  pricingBoxHtml += '<td><input type="radio" name="paymentTypeSelect" checked  class="paymentTypeSelectClass" value="AllArea"/></td>';
						  }else{
							  pricingBoxHtml += '<td><input type="radio" name="paymentTypeSelect" class="paymentTypeSelectClass" value="AllArea"/></td>';
						  }
    					  
    					  pricingBoxHtml += '<td width="65%" style="font-size: 11px;">All Branches</td>';
    					  pricingBoxHtml += '<td width="30%" style="font-size: smaller;font-weight: bolder;">$ <span class="all-area-price-td">'+jsonDataArr[i].allAreaPrice+'</span></td>';
    					  pricingBoxHtml += '</tr>';
					  }
					  //if(jsonDataArr[i].bayAreaPrice != null){
					  if(jsonDataArr[i].bayAreaPrice != null && typeof jsonDataArr[i].bayAreaPrice != "undefined"){
    					  pricingBoxHtml += '<tr>';    					  
    					  if(jsonDataArr[i].id == signUpProductId && signUpProductType == 'Bay Area') {
    						  pricingBoxHtml += '<td><input type="radio" name="paymentTypeSelect" checked class="paymentTypeSelectClass" value="BayArea"/></td>';
    					  }else {
    						  pricingBoxHtml += '<td><input type="radio" name="paymentTypeSelect" class="paymentTypeSelectClass" value="BayArea"/></td>';
    					  }
    					  
    					  pricingBoxHtml += '<td width="65%" style="font-size: 11px;">Bay Area</td>';
    					  pricingBoxHtml += '<td width="30%" style="font-size: smaller;font-weight: bolder;">$ <span class="bay-area-price-td">'+jsonDataArr[i].bayAreaPrice+'</span></td>';
    					  pricingBoxHtml += '</tr>'; 
					  }
					  
					  pricingBoxHtml += '<tr>';
					  
					  if(jsonDataArr[i].id == signUpProductId && signUpProductType == 'This Branch Only') {
						  pricingBoxHtml += '<td><input type="radio" name="paymentTypeSelect" checked class="paymentTypeSelectClass" value="ThisBranchOnly"/></td>';
					  }else {
						  pricingBoxHtml += '<td><input type="radio" name="paymentTypeSelect" class="paymentTypeSelectClass" value="ThisBranchOnly"/></td>';
					  }
					  
					  pricingBoxHtml += '<td width="65%" style="font-size: 11px;">This Branch Only</td>';
					  pricingBoxHtml += '<td width="30%" style="font-size: smaller;font-weight: bolder;">$ <span class="this-branch-price-td">'+jsonDataArr[i].tierPrice+'</span></td>';
					  pricingBoxHtml += '</tr>';
					  pricingBoxHtml += '</table>'; 
					  pricingBoxHtml += '</div>'; 
						    					  
					  if(i % 2 == 0){
						  if(jsonDataArr[i].id != signUpProductId){
							  pricingBoxHtml += '<div class="k-button product-register-div" style="position: absolute; bottom: 8px; right: 29px; right: 29px; text-shadow: none; background-color: #929292 !important; color: #ffffff !important; background-image:none !important; font-weight:bold !important;">Register</div>';
    					  }else {	    					  
    						  pricingBoxHtml += '<div class="k-button is-current-membership-change" style="position: absolute; bottom: 8px; font-size: 11px;  right: 25px; text-shadow: none; background-color: #929292 !important; color: #ffffff !important; background-image:none !important; font-weight:bold !important;">Change Price</div>';
    					  }
						  
					  }else{
						  if(jsonDataArr[i].id != signUpProductId){
							  pricingBoxHtml += '<div class="k-button product-register-div" style="position: absolute; bottom: 8px;  right: 29px; text-shadow: none; background-color: #eb8120 !important; color: #ffffff !important; background-image:none !important; font-weight:bold !important;">Register</div>';
    					  }else {	    					  
    						  pricingBoxHtml += '<div class="k-button is-current-membership-change" style="position: absolute;  bottom: 8px;  right: 25px; font-size: 11px; text-shadow: none; background-color: #eb8120 !important; color: #ffffff !important; background-image:none !important; font-weight:bold !important;">Change Price</div>';
    					  }
						  
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
}

function resetPrimaryMember(){
	$('#becomeMemberForm').find('#firstName').attr("value", "");
	$('#becomeMemberForm').find('#lastName').attr("value", "");										    			
	$('#becomeMemberForm').find('#phoneNumber').attr("value", "");
	$('#becomeMemberForm').find('#email').attr("value", "");
	$('#becomeMemberForm').find('#dob').attr("value", "");
	$('#becomeMemberForm').find('#genderM').prop('checked', false);
	$('#becomeMemberForm').find('#genderF').prop('checked', false);
	
}

function resetSecondaryMember(){
	$('#becomeMemberForm').find('#secFirstName').attr("value", "");
	$('#becomeMemberForm').find('#secLastName').attr("value", "");
	$('#becomeMemberForm').find('#secDOB').attr("value", ""); 
	$('#becomeMemberForm').find('#secPhoneNumber').attr("value", ""); 
	$('#becomeMemberForm').find('#secEmail').attr("value", "");		
	$('#becomeMemberForm').find('#secGenderM').prop('checked', false);
	$('#becomeMemberForm').find('#secGenderF').prop('checked', false);
	
}

function resetThirdMember(){
	$('#becomeMemberForm').find('#thirdFirstName').attr("value", "");
	$('#becomeMemberForm').find('#thirdLastName').attr("value", "");
	$('#becomeMemberForm').find('#thirdDOB').attr("value", ""); 
	$('#becomeMemberForm').find('#thirdPhoneNumber').attr("value", ""); 
	$('#becomeMemberForm').find('#thirdEmail').attr("value", "");		
	$('#becomeMemberForm').find('#thirdGenderM').prop('checked', false);
	$('#becomeMemberForm').find('#thirdGenderF').prop('checked', false);
	
}