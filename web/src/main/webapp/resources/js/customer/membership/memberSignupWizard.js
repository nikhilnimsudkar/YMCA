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

var registerSelected = false;

var secKidCount = 1;

var paymentOrderId = 0;

var isUserLoggedInCheck = 'false';
var months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
/* $("#grid").data("kendoGrid").dataSource.data([ ]);

var productsGrid = $('#grid').data('kendoGrid');
var dataSource = productsGrid.dataSource;
dataSource.add({ id: "testId", productName: 'A Johns Product 1', productDescription: "desc"});
dataSource.sync(); */
var currentFullYear = new Date().getFullYear();
$(document).ready(function(){	
	
	var amountMap = null;
	var promotionMap = new Array();
	
	var dobYearForm = new Date().getFullYear();
	dobYearForm = parseInt(dobYearForm.toString());
	for(var i=0; i<100 ; i++){
		$('#dobYearForm').append($('<option>', {value: dobYearForm,text: dobYearForm}));
		dobYearForm = dobYearForm - 1;
	}
	$("#dobYearForm").kendoDropDownList();    
	$("#dobDateForm").kendoDropDownList();
	$("#dobMonthForm").kendoDropDownList();	
	populateInputDOBfromDropdownMenu($("#dobMonthForm").val(), $("#dobDateForm").val(), $("#dobYearForm").val(), "dob");
	/*
	TestData	
	var dropdownlist = $("#dobYearForm").data("kendoDropDownList");
	dropdownlist.value("1984");
	$("#phoneNumber").val("1111111111");
	*/
	
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
		  $("#sumFinalAmountTd").html(finalAmount);
	});
	
	

	$('.note').on('click', function() {
	    $('.ttip').hide(500);
	    $("#background").fadeOut("slow");
	    $("#large").fadeOut("slow");

	});
		
	$("#phoneNumber").mask("(999) 999-9999");
	
    $("#dob").kendoDatePicker();
    $("#location").kendoDropDownList();    
    //$(".selectContactDropDown").kendoDropDownList();
    $("#selectContactPrimaryAdult").kendoDropDownList();    
    //$("#pricingOption").kendoDropDownList();
    //var pricingOption = $("#pricingOption").data("kendoDropDownList");
    //pricingOption.select(0);
    
    var locationDropdownlist = $("#location").data("kendoDropDownList");    
    getProductsByLocationId();
    //locationDropdownlist.select(0);
    //var prefLocationDropdownlist = $("#prefLocation").data("kendoDropDownList");
    //prefLocationDropdownlist.select(0);
   // $("#prefLocation").closest(".k-widget").hide(); 
    $("#paymentFrequencySelect").kendoDropDownList();
    var paymentFreq = $("#paymentFrequencySelect").data("kendoDropDownList");
    paymentFreq.select(0);
    //$("#membershipFrequencyId").attr("value","Monthly");
    
    $("#paymentFrequencyTd").html(paymentFreq.text());
	$("#sumPaymentFreqTd").html(paymentFreq.text());    
	//$("#paymentBillingcycleTr").css("display", "none");
	//$("#paymentBillingcycleTd").html("");
	
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
	
	        var parts = str.split("/");
	
	        var year = parts[2] && parseInt( parts[2], 10 );
	        var month = parts[0] && parseInt( parts[0], 10 );
	        var day = parts[1] && parseInt( parts[1], 10 );
	        var duration = 1;
	
	        if( day <= 31 && day >= 1 && month <= 12 && month >= 1 ) {
	
	            var expiryDate = new Date( year, month - 1, day );
	            expiryDate.setFullYear( expiryDate.getFullYear() + duration );
	
	            var day = ( '0' + expiryDate.getDate() ).slice( -2 );
	            var month = ( '0' + ( expiryDate.getMonth() + 1 ) ).slice( -2 );
	            var year = expiryDate.getFullYear();
	
	            $("#end").val(month + "/" + day + "/" + year);
	            //alert($("#end").val());
	            $("#end").data('kendoDatePicker').value($("#end").val());
	
	        } else {
	            alert("Error Occured while setting Membership End Date");
	        }
	    }
	    $("#paymentBillingcycleTr").css("display", "none");
	    $("#paymentBillingcycleTd").html("");
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
    
    
    
    
    
    var currentYear = new Date().getFullYear();
	currentYear = parseInt(currentYear.toString());
	for(var i=0; i<30 ; i++){
		$('#addCardExpirationYear').append($('<option>', {value: currentYear,text: currentYear}));
		currentYear = currentYear +1;
	}
	$("#addCardExpirationYear").kendoDropDownList();    
    $("#expirationMonth").kendoDropDownList();
    
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
   
    var today = kendo.date.today();
    var dateToday = new Date();    
    var d = new Date();
    d.setMonth(d.getMonth() + 2);
    var start = $("#start").kendoDatePicker({
    	format: "MM/dd/yyyy",
    	value: today,      
    	max: d,
    	min: dateToday,
    	change: function (e) {
    		var prev = $(this).data("previous");
			var todayDate = kendo.toString(kendo.parseDate(new Date()), 'MM/dd/yyyy');	
			var prevDate = kendo.toString(kendo.parseDate(prev), 'MM/dd/yyyy');
			var changeDate = new Date(e.sender.value());	
			
			if(changeDate < dateToday){
				showDateError();	
				var thisDatepicker = $('#'+e.sender.element[0].id).data("kendoDatePicker");
				if(prevDate){
					thisDatepicker.value(prevDate);
				}else{
					thisDatepicker.value(todayDate);
				}
			}else{
				changeEndDatePaymentScreen(); 
				$(this).data("previous", this.value());
			}
        },
    	parseFormats: ["MM/dd/yyyy", "dd/MM/yyyy"]
    }).data("kendoDatePicker");
	$(start).data("previous", "");   
	
    var end = $("#end").kendoDatePicker({
    	format: "MM/dd/yyyy",
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
		
		var dobYearForm = new Date().getFullYear();
		dobYearForm = parseInt(dobYearForm.toString());
    	for(var i=0; i<100 ; i++){
    		$('#familyInfoFormTable').find('.kidDobYearForm').last().append($('<option>', {value: dobYearForm,text: dobYearForm}));
    		dobYearForm = dobYearForm - 1;
    	}
		$('#familyInfoFormTable').find('.kidDobYearForm').last().kendoDropDownList();
		$('#familyInfoFormTable').find('.kidDobMonthForm').last().kendoDropDownList();
		$('#familyInfoFormTable').find('.kidDobDateForm').last().kendoDropDownList();
		var secChildYear = $('#familyInfoFormTable').find('.kidDobYearForm').last().data("kendoDropDownList");
		var secChildMonth = $('#familyInfoFormTable').find('.kidDobMonthForm').last().data("kendoDropDownList");
		var secChildDate = $('#familyInfoFormTable').find('.kidDobDateForm').last().data("kendoDropDownList");
		var dob = secChildMonth.value()+ "/" +secChildDate.value()+ "/" +secChildYear.value();
		$('#familyInfoFormTable').find(".dateOfBirthValidation").last().attr("value", dob);
		
		/*$(document).find('.selectsecondaryContactKid').each(function(i, obj) {				
			var isKendolist = $(obj).attr("data-role");	
			if(isKendolist != "dropdownlist"){
				$('.selectsecondaryContactKid').kendoDropDownList();
				//var dropdownlist = $(this).data("kendoDropDownList");
				//dropdownlist.enable(true);
				//$(this).css("display", "");
			}else{				
				console.log("Already initialised with kendo dropdown");
			}		
		});*/
		
		 
		//$('.dob-class').kendoDatePicker();
		//$("#wizard").smartWizard("fixHeight");
    });
    
   /* $(document).on('change','.selectsecondaryContactKid',function(e){   	
   		var kidForm = $(this).parent().parent().parent().parent().parent().parent().find('.secondaryKidsInfoForm');
   		
   		var selectContactKid = $(this).data("kendoDropDownList");
   		if(selectContactKid){
           	var contactVal = selectContactKid.value();
       		//var contactVal = $(".selectContactKid").data("kendoDropDownList").value();
           	//console.log(contactVal);
           	if(contactVal != 0){
           		var contactInfoArr = contactVal.split("_");
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
           		
           	}else if(contactVal == 0){
           		kidForm.find("input.firstName").attr("value","");
           		kidForm.find("input.lastName").attr("value","");
           		kidForm.find("input.dateOfBirth").attr("value","");
           		var $radiosReset = kidForm.find(".gender");
           		$radiosReset.filter('[value=Male]').prop('checked', false);
           		$radiosReset.filter('[value=Female]').prop('checked', false);	           		
           	}
   		}       	
  }); */
    
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
    	
    	console.log("     Register onClick() ..........      ");
    	
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
    		//alert($(this).closest(".k-block-div").find( "input[type='radio']:checked" ).val());
    		var productObj = $(this).closest(".k-block-div").find( "input[type='radio']:checked" ).parent().find(".itemDetailProduct-Id").html();
    		console.log(productObj);
    		proceedToRegister1(this, productObj);
    		
    	}
    });
    
   /* function proceedToRegister(thisProd){
    	
    	console.log("  proceedToRegister  ....  "+thisProd);
    	
    	if(thisProd != null && thisProd != undefined){
    		
    		console.lod(" product selected ")
    		
    	}else{
    		
    		console.lod(" promo product ")
    		
    		var urlPromoItemDetailId = $("#urlPromoItemDetailId").val();
    		var urlPromoContactId = $("#urlPromoContactId").val();
    		var urlPromoCode = $("#urlPromoCode").val();

    		console.log(" urlPromoItemDetailId  : "+urlPromoItemDetailId);
    		console.log(" urlPromoContactId : "+urlPromoContactId);
    		console.log(" urlPromoCode : "+urlPromoCode);
    		
    	}
    	
    	//var form = $("#becomeMemberForm");
        //form.validate().resetForm();      // clear out the validation errors
          
    	//$("#becomeMemberForm").data('validator').resetForm();
    	//alert("click");
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
    	//selectedProductTotalPrice = $(this).parent().parent().find( ".prod-total-price-div" ).text();
    	selectedProdTandc = $(thisProd).parent().parent().find( ".prod-tandc-div" ).html();
    	
    	selectedAutoPromoDiscount = $(thisProd).parent().parent().find( ".prod-autoPromoDiscount-div" ).html();
    	$("#locationIdInput").attr("value", $("#location").kendoDropDownList().val());  
 
    	
    	
    	var pricingOptionHtml = $(thisProd).parent().parent().find( ".prod-price-option-dropdown-div" ).html();
    	$("#paymentFrequencySelect").html(pricingOptionHtml);
    	$("#paymentFrequencySelect").kendoDropDownList();
    	//var paymentFrequencySelectDropDown = $("#paymentFrequencySelect").data("kendoDropDownList");    	
    	
        var paymentFreq = $("#paymentFrequencySelect").data("kendoDropDownList");
        paymentFreq.search("Monthly");
        //paymentFreq.select(0);
        selectPricingOptionAndPopulateEndDate();
    	
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

    	$("#sumRegistrationFee").html(registrationFee);	
    	$("#sumDepositAmount").html(depositAmount);
    	
    	var signupPrice = 0;
    	
    	if($(thisProd).parent().parent().find( "input:radio[name=paymentTypeSelect]" ).is(":checked")){    	
    		var paymentType = $(thisProd).parent().parent().find( "input:radio[name=paymentTypeSelect]:checked" ).val();
    		
    		console.log("   paymentType  :::   "+paymentType);
    		
    		if(paymentType == 'AllArea'){
    			var allAreaPrice = $(thisProd).parent().parent().find( ".all-area-price-td" ).text();    		
    			selectedProductTotalPrice = parseFloat(allAreaPrice) + parseFloat(selectedJoiningFeeInfo) + parseFloat(registrationFee) + parseFloat(depositAmount);       
    			$("#sumTierPriceTd").html(allAreaPrice);
    			$("#signUpProductType").attr("value","All Branches");
    			
    			signupPrice = allAreaPrice;
    			
    			console.log("  allAreaPrice = "+allAreaPrice);
    			
    		}else if(paymentType == 'BayArea'){
    			var bayAreaPrice = $(thisProd).parent().parent().find( ".bay-area-price-td" ).text();    		
    			selectedProductTotalPrice = parseFloat(bayAreaPrice) + parseFloat(selectedJoiningFeeInfo) + parseFloat(registrationFee) + parseFloat(depositAmount);    
    			$("#sumTierPriceTd").html(bayAreaPrice);
    			$("#signUpProductType").attr("value","Bay Area");
    			
    			signupPrice = bayAreaPrice;
    			
    			console.log("  bayAreaPrice = "+bayAreaPrice);
    			
    		}else if(paymentType == 'ThisBranchOnly'){
    			var thisBranchPrice = $(thisProd).parent().parent().find( ".this-branch-price-td" ).text();    		
    			selectedProductTotalPrice = parseFloat(thisBranchPrice) + parseFloat(selectedJoiningFeeInfo) + parseFloat(registrationFee) + parseFloat(depositAmount);       			
    			$("#sumTierPriceTd").html(thisBranchPrice);    	
    			$("#signUpProductType").attr("value","This Branch Only");
    			
    			signupPrice = thisBranchPrice;
    			
    			console.log("  thisBranchPrice = "+thisBranchPrice);
    			
    		}    		
    	} else{
			selectedProductTotalPrice = parseFloat(selectedTierPriceInfo) + parseFloat(selectedJoiningFeeInfo) + parseFloat(registrationFee) + parseFloat(depositAmount); 
			$("#sumTierPriceTd").html(selectedTierPriceInfo);
			$("#signUpProductType").attr("value","This Branch Only");
			
			signupPrice = selectedTierPriceInfo;
			
			console.log("  selectedTierPriceInfo = "+selectedTierPriceInfo);
			
		}
    	
    	console.log("  selectedProdctId ::: "+selectedProdctId+",  selectedProductName  ::: "+selectedProductName);
    	
    	var setupfee = 0;
    	//console.log("  selectedProductTotalPrice  "+selectedProductTotalPrice);
    	//console.log("  signupPrice  "+signupPrice+"  setupfee  "+setupfee+"	 selectedJoiningFeeInfo  "+selectedJoiningFeeInfo+"   registrationFee  "+registrationFee+"   depositAmount  "+depositAmount);
    	amountMap = '{ "signupPrice":"'+signupPrice+'", "setupFee":"'+setupfee+'", "joinFee":"'+selectedJoiningFeeInfo+'", "registrationFee":"'+registrationFee+'", "depositAmount":"'+depositAmount+'"}';
    	
    	var membershipFreq = $("#membershipFrequencyId").val();
    	
    	//console.log("		selectedMonthlyBillingFreqency    "+selectedMonthlyBillingFreqency);
    	promotionMap = getAutoPromotionMap(selectedProdctId, selectedMonthlyBillingFreqency, amountMap, selectedProductTotalPrice);
    	
    	
    	
    	$('#applypromo').click(function() {
    		
    		$("#tcSuccessSpan").css("display", "none");		
    		$("#tcSuccessSpan" ).html("");	
    		$("#tcErrorSpan").css("display", "none");		
    		$( "#tcErrorSpan" ).html("");
    		var d_promocode = $("#c_promocode").val();
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
    		}
    	    
    	});
    	
    	
    	$('#hidepromo').click(function() {
    		
    		console.log(" hide promo ");
    		
    		
    		var d_promocode = $("#c_promocode").val();
    		var d_promocode_old = $("#c_promocode_old").val();
    		
    		if(d_promocode && d_promocode != ''){
    			promotionMap = removePromo(promotionMap, d_promocode);
    		}
    		
    		if(d_promocode_old && d_promocode_old != ''){
    			promotionMap = removePromo(promotionMap, d_promocode_old);
    		}
    		
    		refreshPaymentInfo(promotionMap, amountMap, totalPriceWithoutDiscount);
    		
    		$("#c_promocode").val("");
    		$("#c_promocode_old").val("");
    		
    	});
    	
    	 $("#paymentFrequencySelect").on('change',function(e){
    		 
    		// console.log("  paymentFrequencySelect change  ... ");
    		 
	    	$("#paymentFrequencyTd").html(paymentFreq.text());
	    	$("#sumPaymentFreqTd").html(paymentFreq.text());    
	    	//$("#paymentBillingcycleTr").css("display", "none");
	    	//$("#paymentBillingcycleTd").html("");
	    	
	    	//console.log("  paymentFrequencySelect change  ... "+paymentFreq.text());
	    	
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
	
		            var parts = str.split("/");
	
		            var year = parts[2] && parseInt( parts[2], 10 );
		            var month = parts[0] && parseInt( parts[0], 10 );
		            var day = parts[1] && parseInt( parts[1], 10 );
		            var duration = 1;
	
		            if( day <= 31 && day >= 1 && month <= 12 && month >= 1 ) {
	
		                var expiryDate = new Date( year, month - 1, day );
		                expiryDate.setFullYear( expiryDate.getFullYear() + duration );
	
		                var day = ( '0' + expiryDate.getDate() ).slice( -2 );
		                var month = ( '0' + ( expiryDate.getMonth() + 1 ) ).slice( -2 );
		                var year = expiryDate.getFullYear();
	
		                $("#end").val(month + "/" + day + "/" + year);
		                $("#end").data('kendoDatePicker').value($("#end").val());
	
		            } else {
		                alert("Error Occured while setting Membership End Date");
		            }
		        }
		        $("#paymentBillingcycleTr").css("display", "none");
		        $("#paymentBillingcycleTd").html("");
	        
		        promotionMap = getAutoPromotionMap(selectedProdctId, selectedAnnualBillingFreqency, amountMap, selectedProductTotalPrice);
	        
	    	}else{
	    		$("#endDateDiv").css("display","none");    		
	    		$("#sumTotalPriceTd").html(selectedProductTotalPrice);
	    		//$("#paymentBillingcycleTd").html($("#start").val() + "(Of each month)");
	    		$("#membershipFrequencyId").attr("value","Monthly");
	    		//$("#end").attr("value", "");    	
	    		if(selectedTierPriceInfo != null){    			
	    			$("#sumTierPriceTd").html(parseFloat(selectedTierPriceInfo));
	    		}    
	    		
	    		promotionMap = getAutoPromotionMap(selectedProdctId, selectedMonthlyBillingFreqency, amountMap, selectedProductTotalPrice);
	    	}
	    });
    	
    	$("#productIdTd").html(selectedProdctId);
    	$("#productIdHidInput").attr("value", selectedProdctId);
    	$("#productNameTd").html(selectedProductName);
    	$("#productDescriptionTd").html(selectedProdDescInfo);
    	$("#productTypeTd").html($(thisProd).parent().parent().find( ".prod-type-div" ).text());    	
    	
    	//$("#productPriceTd").html(selectedProductTotalPrice);
    	
    	$(".termsDiv").html(selectedProdTandc);
    	$("#userTC").attr("value", selectedProdTandc);
    	
    	$("#paymentJoinFeeTd").html(selectedJoiningFeeInfo);
    	$("#sumJoinFeeTd").html(selectedJoiningFeeInfo);
    	
    	var oneAdultWithKidsHtml = getKidsInfoHeader() + getKidsInfoHtml("1");
    	var youthHtml = getYouthInfoHeader() + getYouthUserInfoData("1");
    	var twoAdultsHtml = getSecondUserInfoHeader() + getSecondUserInfoData("1");
    	var twoAdultsWithKidsHtml = getSecondUserInfoHeader() + getSecondUserInfoData("1") + getKidsInfoHeader() + getKidsInfoHtml("2");
    	var threeAdultsWithKidsHtml = getSecondUserInfoHeader() + getSecondUserInfoData("1") + getThirdUserInfoHeader() + getThirdUserInfoData("2") + getKidsInfoHeader() + getKidsInfoHtml("3");    
    	var threeAdultsHtml = getSecondUserInfoHeader() + getSecondUserInfoData("1") + getThirdUserInfoHeader() + getThirdUserInfoData("2");
    	validateEmail(0);
    	$("#primaryMembershipAgeCategory").attr("value", "Adult");
    	if(selectedHeaderInfo == 'Adult'){   
    		$("#primaryMemberTxtSpan").html("PRIMARY MEMBER");	
    		$('#familyInfoFormTable').css("display" , "block");
    		$('#familyInfoFormTable').html("");
    		validateDOBAdult(0);
    		validateAdult(0);
    	}else if(selectedHeaderInfo == 'Senior'){
    		$("#primaryMemberTxtSpan").html("PRIMARY MEMBER");
    		$('#familyInfoFormTable').css("display" , "block");
    		$('#familyInfoFormTable').html("");    		
    		validateAdult(0);
    		validateDOBSenior(0);
    		$("#primaryMembershipAgeCategory").attr("value", "Senior");
    	}else if(selectedHeaderInfo == 'Youth'){
    		$("#primaryMemberTxtSpan").html("Primary Adult Contact");
    		$('#familyInfoFormTable').css("display" , "inline-table");
    		$('#familyInfoFormTable').html(youthHtml);
    		$('#familyInfoFormTable').find(".dob-class").kendoDatePicker();    		
    		validateDOBAdult(0);
    		validateAdult(0);
    		validateYouth(1);
    	}else if(selectedHeaderInfo == 'One Adultw/ Kids'){
    		$("#primaryMemberTxtSpan").html("PRIMARY MEMBER");
    		$('#familyInfoFormTable').css("display" , "inline-table");
    		$('#familyInfoFormTable').html(oneAdultWithKidsHtml);
    		$('#familyInfoFormTable').find(".dob-class").kendoDatePicker();
    		secKidCount = 2;
    		validateDOBAdult(0);
    		validateAdult(0);
    		validatekid(1);
    	}else if(selectedHeaderInfo == 'Two Adults' ){
    		$("#primaryMemberTxtSpan").html("PRIMARY MEMBER");
    		$('#familyInfoFormTable').css("display" , "inline-table");
    		$('#familyInfoFormTable').html(twoAdultsHtml);    		
    		$('#familyInfoFormTable').find(".dob-class").kendoDatePicker();  
    		validateDOBAdult(0);
    		validateAdult(0);
    		validateAdult(1);
    	}else if(selectedHeaderInfo == 'Two Adultsw/ Kids'){    
    		$("#primaryMemberTxtSpan").html("PRIMARY MEMBER");
    		$('#familyInfoFormTable').css("display" , "inline-table");
    		$('#familyInfoFormTable').html(twoAdultsWithKidsHtml);
    		$('#familyInfoFormTable').find(".dob-class").kendoDatePicker();
    		validateDOBAdult(0);
    		validateAdult(0);
    		validateAdult(1);
    		validatekid(2);
    		secKidCount = 3;
    	}else if(selectedHeaderInfo == 'Three Adults'){
    		$("#primaryMemberTxtSpan").html("PRIMARY MEMBER");
    		$('#familyInfoFormTable').css("display" , "inline-table");
    		$('#familyInfoFormTable').html(threeAdultsHtml);
    		$('#familyInfoFormTable').find(".dob-class").kendoDatePicker();
    		validateDOBAdult(0);
    		validateAdult(0);
    		validateAdult(1);
    		validateAdult(2);    		
    	} else if(selectedHeaderInfo == 'Three Adultsw/ or w/o kids'){
    		$("#primaryMemberTxtSpan").html("PRIMARY MEMBER");
    		$('#familyInfoFormTable').css("display" , "inline-table");
    		$('#familyInfoFormTable').html(threeAdultsWithKidsHtml);
    		$('#familyInfoFormTable').find(".dob-class").kendoDatePicker();    
    		validateDOBAdult(0);
    		secKidCount = 4;  
    		validateAdult(0);
    		validateAdult(1);
    		validateAdult(2);
    		validatekid(3);
    		
    	}else{
    		$('#familyInfoFormTable').css("display" , "none");
    	}    
    	$(".phone-number").mask("(999) 999-9999");    	
    	$("#wizard"). smartWizard("fixHeight");
    	registerSelected = true;   
    	
    	$("#selectContactSecMember").kendoDropDownList();
        $("#selectContactSecMember").on('change',function(e){    	
         	var contactVal = $(this).val();
         	//console.log(contactVal);
         	var contactInfoArr = contactVal.split("_");
         	var thisConSelObj = $(this);
         	var flag = true;
         	$(document).find('select.selectContactDropDw').each(function(i, obj) {				
         		var selectedContacts = $(obj).data("kendoDropDownList");
         		var dropDownVal = selectedContacts.value();
         		var dropDownArr = dropDownVal.split("_");          		
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
       	var contactInfoArr = contactVal.split("_");
       	var thisConSelObj = $(this);
       	var flag = true;
       	$(document).find('select.selectContactDropDw').each(function(i, obj) {				
       		var selectedContacts = $(obj).data("kendoDropDownList");
       		var dropDownVal = selectedContacts.value();
       		var dropDownArr = dropDownVal.split("_");          		
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
           	var contactInfoArr = contactVal.split("_");
           	var thisConSelObj = $(this);
           	var flag = true;
           	$(document).find('select.selectContactDropDw').each(function(i, obj) {				
           		var selectedContacts = $(obj).data("kendoDropDownList");
           		var dropDownVal = selectedContacts.value();
           		var dropDownArr = dropDownVal.split("_");          		
           		if(dropDownVal != 0 && dropDownVal != 'Other' && thisConSelObj.attr("id") != obj.id && contactInfoArr[contactInfoArr.length - 1] == dropDownArr[dropDownArr.length - 1]){
           			//alert("Contact Already selected");
           			duplicateContactSelectedError();        			
           			flag = false;
           		}
           	});

           	if(contactVal != 0 && contactVal != 'Other' && flag){
           		
           		populateYouthMember(contactInfoArr[0], contactInfoArr[1], contactInfoArr[4], contactInfoArr[5]);
           		$(document).find('#secFirstName').prop('readonly', true);
         		$(document).find('#secLastName').prop('readonly', true);
         		$(document).find('#secDOB').prop('readonly', true);
         		$(document).find('#secPhoneNumber').prop('readonly', true);
         		//$(document).find('#secEmail').attr("value", "");		
         		$(document).find('#secGenderM').prop('readonly', true);
         		$(document).find('#secGenderF').prop('readonly', true);
           	}else if(contactVal == 0 || contactVal == 'Other' || flag == false){
           		resetYouthMember();
           		$(document).find('#secFirstName').prop('readonly', false);
         		$(document).find('#secLastName').prop('readonly', false);
         		$(document).find('#secDOB').prop('readonly', false); 
         		$(document).find('#secPhoneNumber').prop('readonly', false);
         		//$(document).find('#secEmail').attr("value", "");		
         		$(document).find('#secGenderM').prop('readonly', false);
         		$(document).find('#secGenderF').prop('readonly', false);
         		populateInputDOBfromDropdownMenu($("#youthDobMonthForm").val(), $("#youthDobDateForm").val(), $("#youthDobYearForm").val(), "secDOB");
         }
       	
      });
       	
       	$(".selectPrimaryContactKid").kendoDropDownList();
       	$(".selectPrimaryContactKid").on('change',function(e){      		
       		var selectContactKid = $(this).data("kendoDropDownList");
       		if(selectContactKid){
	           	var contactVal = selectContactKid.value();
	       		//var contactVal = $(".selectContactKid").data("kendoDropDownList").value();
	           	//console.log(contactVal);
	           	var contactInfoArr = contactVal.split("_");
	           	var thisConSelObj = $(this);
	           	var flag = true;
	           	$(document).find('select.selectContactDropDw').each(function(i, obj) {				
	        		var selectedContacts = $(obj).data("kendoDropDownList");
	        		var dropDownVal = selectedContacts.value();
	        		var dropDownArr = dropDownVal.split("_");          		
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
       	populateInputDOBfromDropdownMenu($("#secDobMonthForm").val(), $("#secDobDateForm").val(), $("#secDobYearForm").val(), "secDOB");
       	populateInputDOBfromDropdownMenu($("#youthDobMonthForm").val(), $("#youthDobDateForm").val(), $("#youthDobYearForm").val(), "secDOB");
       	populateInputDOBfromDropdownMenu($("#thirdDobMonthForm").val(), $("#thirdDobDateForm").val(), $("#thirdDobYearForm").val(), "thirdDOB");
       	populateInputDOBfromDropdownMenu($("#firstKidDobMonthForm").val(), $("#firstKidDobDayForm").val(), $("#firstKidDobYearForm").val(), "firstKidDOBInp");       	
    	//$(".selectContactDropDown").kendoDropDownList();
    	//validator1.resetForm();
       	
    	$('#wizard').smartWizard('goToStep',2);
   
    }*/
    //});
    	
   
    
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
   	 
   	if(selectedHeaderInfo == 'Adult' || selectedHeaderInfo == 'Senior' || selectedHeaderInfo == 'Youth' || selectedHeaderInfo == 'Two Adults' || selectedHeaderInfo == 'Three Adults'){
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
		var firstName = "";
		var lastName = "";
		var dobMonth = $("#youthDobMonthForm").val();
		var dobDate = $("#youthDobDateForm").val();
		var dobYear = $("#youthDobYearForm").val();
		
		var age =  $("#adultAgeValidationLowerLimit").html();
		//var kidAge =  $("#kidsAgeValidation").html();
		var mydate = new Date();
		mydate.setFullYear(dobYear, dobMonth-1, dobDate);
		
	    var currdate = new Date();
	    currdate.setFullYear(currdate.getFullYear() - age);
	    
		if(selectedHeaderInfo == 'Youth' && currdate > mydate){
			firstName = $("#secFirstName").val().trim();
			lastName = $("#secLastName").val().trim();			
		}else{
			firstName = $("#firstName").val().trim();
			lastName = $("#lastName").val().trim();			
		}
		var fullName = firstName + " " + lastName;
		var tcname = $("#tcname").val().trim();
		if(tcname != fullName){
			return false;
		} else{
			return true;
		}
		
	}, "Please enter the primary contact's First Name and Last Name.");
	
	
	
	/*"userLst[0].emailAddress" : {
		required: true,
		email: true,				
         remote: {
             url: url,  
             type:"GET"                    	
          }
	},
	
	"userLst[0].emailAddress" : {
				required: "Please enter your email address",
				email : "Please enter valid email address",				
				remote: "You appear to be associated with a customer account already.  Please speak with the primary contact for that account"
				
			},
	
	*/
	
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
			
		}
	});
	
	//"userLst[0].dateOfBirth" : "required",
	//"userLst[0].dateOfBirth" : "Please enter your Date of Birth",
	/*$.validator.addMethod("check_date_of_birth", function(value, element) {		
	     var day = $("#dob_day").val();
	    var month = $("#dob_month").val();
	    var year = $("#dob_year").val(); 
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
	    
	}, "You must be between "+ $("#adultAgeValidationLowerLimit").html() +" to "+ $("#adultAgeValidationUpperLimit").html() +" years of age, or choose another membership product");
	
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
	
	$.validator.addMethod("check_duplicate_email", function(value, element) {			
	    var inputEmailValue = value;	   
	    $element = $(element);
	    /*if($element.attr("id") == "email" && $("#email" ).val() == ""){
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
	
	function getProductsByLocationId() {
		$(".k-loading-mask").show();
		$(".k-loading-text").html("Please wait");	
		$("#tcloginErrorSpan-payment").css("display", "none");	
		$( "#tcloginErrorSpan-payment" ).html("");
		$("#panels").html("");
		$.ajax({
			  type: "GET",
			  url: "getProductDetailsByLocation",	
			  data: {"locationId" : $("#location").kendoDropDownList().val()},
			  success: function( data ) {
				  //console.log(data);
				  var pricingBoxHtml = '';
				  var jsonDataArr = jQuery.parseJSON(data);
				  if(jsonDataArr != null && jsonDataArr.length > 0){
					  for(var i = 0; i<jsonDataArr.length; i++){
						  var monthlyTierPrice = 0;
						  var monthlyJoiningFee = 0;
						  var monthlyBillingFreq = '';
						  var annualTierPrice = 0;
						  var annualJoiningFee = 0;
						  var annualBillingFreq = '';
						  
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
						  var productPricingOptionRadioHtml = getPriceOptionHTML(jsonDataArr[i].signupPrice);
						  var thisBranchProductItemId = jsonDataArr[i].id;
						  var allAreaProductItemId = 0;
						  var bayAreaProductItemId = 0;
						  
						  //console.log("  productPricingOptionRadioHtml  ::  "+productPricingOptionRadioHtml);
						  
						  if(jsonDataArr[i].signupPrice){    						 
		    				  for(var s = 0; s<jsonDataArr[i].signupPrice.length; s++){
		    					  if(jsonDataArr[i].signupPrice[s].Monthly){
		    						  /*if(productPricingOptionRadioHtml.indexOf('Monthly') === -1 ){
		    							  productPricingOptionRadioHtml = productPricingOptionRadioHtml + '<option value="Monthly" >Monthly</option>';
		    						  }*/    		    						  
		    						  monthlyTierPrice = jsonDataArr[i].signupPrice[s].Monthly;
		    						  monthlyJoiningFee = jsonDataArr[i].signupPrice[s].joiningPrice;
		    						  if(jsonDataArr[i].signupPrice[s].billingFrequency == 'Recurring')
		    							  monthlyBillingFreq = jsonDataArr[i].signupPrice[s].billingFrequency;
		    					  }else if(jsonDataArr[i].signupPrice[s].Annual){
		    						  /*if(productPricingOptionRadioHtml.indexOf('Annual') === -1){
		    							  productPricingOptionRadioHtml = productPricingOptionRadioHtml + '<option value="Annual" selected="selected">Annual</option>';
		    						  }*/    		    						  
		    						  annualTierPrice = jsonDataArr[i].signupPrice[s].Annual;
		    						  annualJoiningFee = jsonDataArr[i].signupPrice[s].joiningPrice;
		    						  if(jsonDataArr[i].signupPrice[s].billingFrequency == 'Recurring')
		    							  annualBillingFreq = jsonDataArr[i].signupPrice[s].billingFrequency;
		    					  }
		    				  }	    						  
						  }
						 
						  if(jsonDataArr[i].allAreaPrice){    						 
							  for(var a = 0; a<jsonDataArr[i].allAreaPrice.length; a++){
								  if(jsonDataArr[i].allAreaPrice[a].Monthly){	    								  
									  allAreamonthlyTierPrice = jsonDataArr[i].allAreaPrice[a].Monthly;
									  allAreamonthlyJoiningFee = jsonDataArr[i].allAreaPrice[a].joiningPrice;
								  }else if(jsonDataArr[i].allAreaPrice[a].Annual){	    								  
									  allAreaannualTierPrice = jsonDataArr[i].allAreaPrice[a].Annual;
									  allAreaannualJoiningFee = jsonDataArr[i].allAreaPrice[a].joiningPrice;
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
								  }else if(jsonDataArr[i].bayAreaPrice[b].Annual){	    								  
									  bayAreaannualTierPrice = jsonDataArr[i].bayAreaPrice[b].Annual;
									  bayAreaannualJoiningFee = jsonDataArr[i].bayAreaPrice[b].joiningPrice;
								  }else if(jsonDataArr[i].bayAreaPrice[b].bayAreaItemDetailId){	    								  
									  bayAreaProductItemId = jsonDataArr[i].bayAreaPrice[b].bayAreaItemDetailId;
									  //console.log("bayAreaProductItemId 11" + bayAreaProductItemId);
								  }
							  }	    						  
							}
						  
						  //console.log("		monthlyBillingFreq   "+monthlyBillingFreq);
						  //console.log(allAreamonthlyTierPrice);
						  //console.log(allAreamonthlyJoiningFee);
						  //console.log(allAreaannualTierPrice);
						  //console.log(allAreaannualJoiningFee);
						  //<option value="Monthly" selected="selected">Monthly</option>
							//<option value="Annual">Annual</option>	
						  //alert(monthlyJoiningFee);
						  monthlyJoiningFee = monthlyJoiningFee - parseFloat($("#existingMemberJoinFeeInput").val());
						  annualJoiningFee = annualJoiningFee - parseFloat($("#existingMemberJoinFeeInput").val());
						  //alert(monthlyJoiningFee);
						  if(monthlyJoiningFee < 0){
							  monthlyJoiningFee = 0;
						  }
						  if( annualJoiningFee < 0){
							  annualJoiningFee = 0;
						  }
						  pricingBoxHtml += '<li>';
						  pricingBoxHtml += '<div class="k-block k-block-div" style="position: relative;">';
						  //console.log("   jsonDataArr[i].productName ::::::: "+jsonDataArr[i].productName);
						  var prodHeaderInfo = getProductHeader(jsonDataArr[i].productName);
						  //console.log("   prodHeaderInfo ::::::: "+prodHeaderInfo);
						  if(i % 2 == 0){
							  pricingBoxHtml += '<div class="k-header k-shadow k-header-custom-gray" id="k-header">'+prodHeaderInfo+'</div>';
						  }else{
							  pricingBoxHtml += '<div class="k-header k-shadow k-header-custom-orange" id="k-header">'+prodHeaderInfo+'</div>';
						  }
						  pricingBoxHtml += '<div>';
						  pricingBoxHtml += '<span style="font-size: x-large; font-family: cursive;">$ <div class="price-div" id="price-div">'+monthlyTierPrice+'</div></span>';
						  
						  pricingBoxHtml += '<div class="prod-id-div" id="prod-id-div" style="display:none">'+thisBranchProductItemId+'</div>';
						  pricingBoxHtml += '<div class="prod-name-div" id="prod-name-div" style="display:none">'+jsonDataArr[i].productName+'</div>';
						  pricingBoxHtml += '<div class="prod-total-price-div" id="prod-total-price-div" style="display:none">'+jsonDataArr[i].productPrice+'</div>';
						  pricingBoxHtml += '<div class="prod-tandc-div" id="prod-tandc-div" style="display:none">'+jsonDataArr[i].tandc+'</div>';
						  pricingBoxHtml += '<div class="prod-billDateOption-div" id="prod-billDateOption-div" style="display:none">'+jsonDataArr[i].billDateOption+'</div>';
						  pricingBoxHtml += '<div class="prod-billDateOffset-div" id="prod-billDateOffset-div" style="display:none">'+jsonDataArr[i].billDateOffset+'</div>';
						  pricingBoxHtml += '<div class="prod-dueDateOption-div" id="prod-dueDateOption-div" style="display:none">'+jsonDataArr[i].dueDateOption+'</div>';
						  pricingBoxHtml += '<div class="prod-dueDateOffset-div" id="prod-dueDateOffset-div" style="display:none">'+jsonDataArr[i].dueDateOffset+'</div>';
						  pricingBoxHtml += '<div class="prod-autoPromoDiscount-div" id="prod-autoPromoDiscount-div" style="display:none">'+jsonDataArr[i].autoPromoDiscount+'</div>';
						  pricingBoxHtml += '<div class="prod-type-div" id="prod-type-div" style="display:none">'+jsonDataArr[i].productType+'</div>';
						  pricingBoxHtml += '<div class="prod-itemDetailsId-div" id="prod-itemDetailsId-div" style="display:none">'+jsonDataArr[i].itemDetailsId+'</div>';	    					  
						  pricingBoxHtml += '<div class="prod-registration-fee-div" id="prod-registration-fee-div" style="display:none">'+jsonDataArr[i].registrationPrice+'</div>';
						  pricingBoxHtml += '<div class="prod-deposit-amount-div" id="prod-deposit-amount-div" style="display:none">'+jsonDataArr[i].depositPrice+'</div>';
						  pricingBoxHtml += '<div class="prod-annual-tier-price-div" id="prod-annual-tier-price-div" style="display:none">'+annualTierPrice+'</div>';	    					    					  
						  pricingBoxHtml += '<div class="prod-annual-join-price-div" id="prod-annual-join-price-div" style="display:none">'+annualJoiningFee+'</div>';
						  pricingBoxHtml += '<div class="prod-monthly-billing-freq-div" id="prod-monthly-billing-freq-div" style="display:none">'+monthlyBillingFreq+'</div>';
						  pricingBoxHtml += '<div class="prod-annual-billing-freq-div" id="prod-annual-billing-freq-div" style="display:none">'+annualBillingFreq+'</div>';
						  
						  pricingBoxHtml += '<div class="prod-price-option-dropdown-div" id="prod-price-option-dropdown-div" style="display:none">'+productPricingOptionRadioHtml+'</div>';	  
						  
						  pricingBoxHtml += '<div class="product-description-div" id="product-description-div">'+jsonDataArr[i].productDescription+'</div>';
						  pricingBoxHtml += '<div class="product-location-div" id="product-location-div">'+locationDropdownlist.text()+'</div>';
						  pricingBoxHtml += '<div class="border-bottom-line" id="border-bottom-line"></div>';
						  pricingBoxHtml += '<div class="product-join-fee-text-div" id="product-join-fee-text-div">One Time Join Fee</div>';
						  pricingBoxHtml += '<span style="font-size: small; font-family: cursive;">$ <div class="product-join-fee-text-val-div" id="product-join-fee-text-val-div">'+monthlyJoiningFee+'</div></span>';	    					    					  
						  
						  pricingBoxHtml += '<div>';
						  pricingBoxHtml += '<table border="0" style="text-align: left;" id="productPriceTable">';
						  
						  //if(jsonDataArr[i].allAreaPrice != null && typeof jsonDataArr[i].allAreaPrice != "undefined"){
						  if(allAreamonthlyTierPrice != 0){	    						  
							  pricingBoxHtml += '<tr>';
							  pricingBoxHtml += '<td ><input type="radio" name="paymentTypeSelect" id="paymentTypeSelectAllArea'+allAreaProductItemId+'" class="paymentTypeSelectClass" value="AllArea"/><span class="itemDetailProduct-Id" style="display:none;">'+allAreaProductItemId +'</span></td>';
							  pricingBoxHtml += '<td width="65%" style="font-size: 11px;">All Branches</td>';
							  pricingBoxHtml += '<td width="30%" style="font-size: smaller;font-weight: bolder;">$ <span class="all-area-price-td">'+allAreamonthlyTierPrice+'</span></td>';
							  pricingBoxHtml += '</tr>';
						  }
						  //if(jsonDataArr[i].bayAreaPrice != null && typeof jsonDataArr[i].bayAreaPrice != "undefined"){
						  if(bayAreamonthlyTierPrice != 0){	    					  
							  pricingBoxHtml += '<tr>';
							  pricingBoxHtml += '<td ><input type="radio" name="paymentTypeSelect" id="paymentTypeSelectBayArea'+bayAreaProductItemId+' class="paymentTypeSelectClass" value="BayArea"/><span class="itemDetailProduct-Id" style="display:none;">'+bayAreaProductItemId +'</span></td>';
							  pricingBoxHtml += '<td width="65%" style="font-size: 11px;">Bay Area</td>';
							  pricingBoxHtml += '<td width="30%" style="font-size: smaller;font-weight: bolder;">$ <span class="bay-area-price-td">'+bayAreamonthlyTierPrice+'</span></td>';
							  pricingBoxHtml += '</tr>';
						  }
						  
						  pricingBoxHtml += '<tr>';	    					  
						  pricingBoxHtml += '<td ><input type="radio" name="paymentTypeSelect" id="paymentTypeSelectThisBranchOnly'+jsonDataArr[i].id+'" class="paymentTypeSelectClass" value="ThisBranchOnly"/><span class="itemDetailProduct-Id" style="display:none;">'+jsonDataArr[i].id +'</span></td>';	    					 	    					  
						  pricingBoxHtml += '<td width="65%" style="font-size: 11px;">This Branch Only</td>';
						  pricingBoxHtml += '<td width="30%" style="font-size: smaller;font-weight: bolder;">$ <span class="this-branch-price-td">'+monthlyTierPrice+'</span></td>';
						  pricingBoxHtml += '</tr>';
						  pricingBoxHtml += '</table>'; 
						  pricingBoxHtml += '</div>'; 
							    					  
						  if(i % 2 == 0){
							  pricingBoxHtml += '<div class="k-button product-register-div" id="paymentTypeSelectAllArea'+jsonDataArr[i].id+'" style="bottom: 8px; margin-right: 29px;position: absolute;right: 8px; text-shadow: none; background-color: #929292 !important; color: #ffffff !important; background-image:none !important; font-weight:bold !important;">Register</div>';
						  }else{
							  pricingBoxHtml += '<div class="k-button product-register-div" id="paymentTypeSelectAllArea'+jsonDataArr[i].id+'" style="bottom: 8px; margin-right: 29px;position: absolute;right: 8px; text-shadow: none; background-color: #eb8120 !important; color: #ffffff !important; background-image:none !important; font-weight:bold !important;">Register</div>';
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
			
			var win = document.getElementById("childIframeId").contentWindow;
			win.postMessage(jsonData, '*'); 		
			
			//$(".k-loading-mask").hide();
			
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
	
	var totalPrice = parseFloat($("#sumTotalPriceTd").text());
	
	$("#sumPromoCodeVal").html("");
    var locationDropdownlist = $("#location").data("kendoDropDownList");
    var paymentFreq2 = $("#paymentFrequencySelect").data("kendoDropDownList");
    if(paymentFreq2.text() == 'Annual'){
		$("#endDateDiv").css("display","inline");
		if(totalPrice != null){    			
			$("#sumTotalPriceTd").html(parseFloat(totalPrice) * 12);
		} 	
	}else{
		$("#endDateDiv").css("display","none");    		
		$("#sumTotalPriceTd").html(totalPrice);		
	}    
    
    
	/*$("#paymentTypeSelectThisBranchOnly300000002350297").attr("checked",true);
	$("#paymentTypeSelectThisBranchOnly300000002350297").prop("checked",true);
	$("input:radio[id=paymentTypeSelectThisBranchOnly300000002350297]").prop("checked",true);
	$("input:radio[id=paymentTypeSelectThisBranchOnly300000002350297]").attr("checked",true);

	$('input:radio[id=paymentTypeSelectThisBranchOnly300000002350297]').checked = true;
		
	$('#paymentTypeSelectClass.paymentTypeSelectThisBranchOnly300000002350297').prop('checked', true);
	$('#paymentTypeSelectClass.paymentTypeSelectThisBranchOnly300000002350297').attr('checked', true);
	$('#paymentTypeSelectClass.paymentTypeSelectThisBranchOnly300000002350297').checked = true;
	$('#paymentTypeSelectClass.paymentTypeSelectThisBranchOnly300000002350297').checked = 'checked';
	
	$('input:radio[class=paymentTypeSelectClass][id=paymentTypeSelectThisBranchOnly300000002350297]').prop('checked', true);
	$('input:radio[class=paymentTypeSelectClass][id=paymentTypeSelectThisBranchOnly300000002350297]').attr('checked', true);
	$('input:radio[class=paymentTypeSelectClass][id=paymentTypeSelectThisBranchOnly300000002350297]').checked = true;
	$('input:radio[class=paymentTypeSelectClass][id=paymentTypeSelectThisBranchOnly300000002350297]').checked = 'checked';
	
	console.log("  paymentTypeSelect1 Check BOx ::::  "+$("#paymentTypeSelectThisBranchOnly300000002350297").find( "input:radio[name=paymentTypeSelect][value='ThisBranchOnly']" ));
	console.log("  paymentTypeSelect1 Check BOx Checked ::::  "+$("#paymentTypeSelectThisBranchOnly300000002350297").find( "input:radio[name=paymentTypeSelect][value='ThisBranchOnly']" ).checked);
	//$("#paymentTypeSelect1"+areaType+selectedProdctId).find( "input:radio[name=paymentTypeSelect][value='"+areaType+"']" ).prop("checked",true);
	$("#paymentTypeSelectThisBranchOnly300000002350297").find( "input:radio[name=paymentTypeSelect][value='ThisBranchOnly']" ).attr("checked",true);
	
    */
    
    if(context.toStep == 5 ){
    	
    	
    	console.log("   next step is 5   ");
    	
    	populatePaymentInfoSection();
    	
    	/*var totalAmount = 0;
    	if($("#sumJoinFeeTd").text() != ''){
    		totalAmount = totalAmount + parseFloat($("#sumJoinFeeTd").text());
    	} 
    	if($("#sumTierPriceTd").text() != ''){
    		totalAmount = totalAmount + parseFloat($("#sumTierPriceTd").text());
    	} 
    	if( $("#sumRegistrationFee").text() != ''){
    		totalAmount = totalAmount + parseFloat($("#sumRegistrationFee").text());
    	} 
    	if( $("#sumDepositAmount").text() != ''){
    		totalAmount = totalAmount + parseFloat($("#sumDepositAmount").text());
    	}
    	
    	if( $("#totalDiscountHiddenInput").val() != ''){
    		totalAmount = totalAmount - parseFloat($("#totalDiscountHiddenInput").val());
    	}
    	
    	console.log("  totalDiscountHiddenInput ::  "+$("#totalDiscountHiddenInput").val());
    	console.log("  totalAmount ::  "+totalAmount);
    	
    	$("#sumTotalPriceTd").html(totalAmount);
    	
    	var finalAmount = 0;
    
    	finalAmount = parseFloat($("#sumTotalPriceTd").text());
    	
    	if($("#faAmountTD").text() != ''){
    		finalAmount = finalAmount - parseFloat($("#faAmountTD").text());
    	}  	
    	
    	 $("#sumFinalAmountTd").html(finalAmount);*/
    	 $("#expirationMonthHid").attr("value", $("#expirationMonth").val());
    	 $("#expirationYearHid").attr("value", $("#addCardExpirationYear").val());
    	 $("#nickNameHid").attr("value", $("#nickName").val());
    	 
    	 var paymentFreqDropDown = $("#paymentFrequencySelect").data("kendoDropDownList");
    	 var str = $("#start").val();    			
    	 if(str != null) {
    	 	var parts = str.split("/");	
    	 	var month = parts[0] && parseInt( parts[0], 10 );
    	 	var day = parts[1] && parseInt( parts[1], 10 );
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
    	 $("#productPriceTd").html($("#sumTierPriceTd").text());
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
	    		
	    		console.log("  message is here .............  ");
	    		 
	    		var tdIsHere = document.getElementById('paymentTypeSelectThisBranchOnly300000002350297');
	    		console.log("  tdIsHere    ::     "+tdIsHere);
	    		
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
    			$("#paymentInfoRadio").kendoDropDownList();
    			
    			var paymentInfoRadioLength = $('#paymentInfoRadio').children('option').length;
    			if(paymentInfoRadioLength == 2){
    				$("#addcard").css("display","inline");
    			}
    			/*
    			var paymentMethodHtml = '';
    			paymentMethodHtml += '<option value="0" >--Select Payment Method--</option>';
    			paymentMethodHtml += '<option value="New" selected="selected">Add New Card</option>';
    			paymentMethodHtml += '<option value="NewBank">Add New Bank Info</option>';
    			$("#paymentInfoRadio").html(paymentMethodHtml);
    			$("#paymentInfoRadio").kendoDropDownList();*/  
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
	 		 
	 		 console.log("   Get promos here   ");
	 		 
	 		 
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

function getSecondUserInfoData(userNo){
	var secondUserInfoTableHtml = '';
	secondUserInfoTableHtml += '<tr>';
	secondUserInfoTableHtml += '<td><input type="text" class="form-control" value="" title="Please enter yout First Name" name="userLst['+userNo+'].firstName" id="secFirstName" maxlength="50" placeholder="First Name"  /></td>';
	secondUserInfoTableHtml += '<td>&nbsp;</td>';
	secondUserInfoTableHtml += '<td><input type="text" class="form-control" value="" title="Please enter yout Last Name" name="userLst['+userNo+'].lastName" id="secLastName" maxlength="50" placeholder="Last Name" /></td>';
	secondUserInfoTableHtml += '<td>&nbsp;</td>';
	secondUserInfoTableHtml += '</tr>';
	
	secondUserInfoTableHtml += '<tr>';
	secondUserInfoTableHtml += '<td style="padding-bottom: 8px;"><input type="text" class="dob-class"  title="Please enter your Date of Birth" name="userLst['+userNo+'].dateOfBirth" id="secDOB" maxlength="50" placeholder="Date of Birth"  style="width: 1px; height: 1px; left : -1000px; margin: 0; padding: 0; " /> <br />';
	
	secondUserInfoTableHtml += getDOBHtml("secDobYearForm",  "secDobMonthForm", "secDobDateForm");
	secondUserInfoTableHtml += '</td>';
	
	secondUserInfoTableHtml += '<td id="dop-er"></td>';
	secondUserInfoTableHtml += '<td><input type="text" class="form-control phone-number" title="Please enter your Phone Number" name="userLst['+userNo+'].formattedPhoneNumber" id="secPhoneNumber" maxlength="50" placeholder="Phone Number" /></td>';
	secondUserInfoTableHtml += '<td>&nbsp;</td>';
	secondUserInfoTableHtml += '</tr>';	
	
	secondUserInfoTableHtml += '<tr>';
	secondUserInfoTableHtml += '<td><input type="text" class="form-control email-check"  name="userLst['+userNo+'].emailAddress" id="secEmail"  maxlength="50" placeholder="Email" /></td>';
	secondUserInfoTableHtml += '<td id="dop-er"></td>';

	secondUserInfoTableHtml += '<td>';
	secondUserInfoTableHtml += '<span>';
	secondUserInfoTableHtml += '<span><span><input id="secGenderM" name="userLst['+userNo+'].gender" class="form-control" style="width: 15px;" type="radio" value="Male"></span><span style="margin-left : 5px;">Male</span></span>';
	secondUserInfoTableHtml += '<span><span><input id="secGenderF" name="userLst['+userNo+'].gender" class="form-control" style="width : 15px;" type="radio" value="Female"></span><span style="margin-left : 5px;">Female</span></span>';
	secondUserInfoTableHtml += '</span>';
	secondUserInfoTableHtml += '</td>';	
	secondUserInfoTableHtml += '<td>&nbsp;</td>';
	secondUserInfoTableHtml += '</tr>';
	
	/*if(isUserLoggedInCheck != 'true'){
	secondUserInfoTableHtml += '<tr>';
	secondUserInfoTableHtml += '<td><input type="text" class="form-control"  name="userLst['+userNo+'].emailAddress" id="secEmail"  maxlength="50" placeholder="Email"  /></td>';
	secondUserInfoTableHtml += '<td id="dop-er"></td>';

	secondUserInfoTableHtml += '<td>';
	secondUserInfoTableHtml += '<span>';
	secondUserInfoTableHtml += '<span><span><input id="secGenderM" name="userLst['+userNo+'].gender" class="form-control" style="width: 15px;" type="radio" value="Male"></span><span style="margin-left : 5px;">Male</span></span>';
	secondUserInfoTableHtml += '<span><span><input id="secGenderF" name="userLst['+userNo+'].gender" class="form-control" style="width : 15px;" type="radio" value="Female"></span><span style="margin-left : 5px;">Female</span></span>';
	secondUserInfoTableHtml += '</span>';
	secondUserInfoTableHtml += '</td>';	
	secondUserInfoTableHtml += '<td>&nbsp;</td>';
	secondUserInfoTableHtml += '</tr>';
	}else{
		secondUserInfoTableHtml += '<tr>';
		secondUserInfoTableHtml += '<td ></td>';
		secondUserInfoTableHtml += '<td id="dop-er"></td>';

		secondUserInfoTableHtml += '<td>';
		secondUserInfoTableHtml += '<span>';
		secondUserInfoTableHtml += '<span><span><input id="secGenderM" name="userLst['+userNo+'].gender" class="form-control" style="width: 15px;" type="radio" value="Male"></span><span style="margin-left : 5px;">Male</span></span>';
		secondUserInfoTableHtml += '<span><span><input id="secGenderF" name="userLst['+userNo+'].gender" class="form-control" style="width : 15px;" type="radio" value="Female"></span><span style="margin-left : 5px;">Female</span></span>';
		secondUserInfoTableHtml += '</span>';
		secondUserInfoTableHtml += '</td>';	
		secondUserInfoTableHtml += '<td>&nbsp;</td>';
		secondUserInfoTableHtml += '</tr>';
	}*/
	
	secondUserInfoTableHtml += '<tr style="display : none;">';
	secondUserInfoTableHtml += '<td><input style="display:none;" name="userLst['+userNo+'].isAdult" value="true"/><input style="display:none;" name="userLst['+userNo+'].membershipAgeCategory" value="Adult"/></td>';
	secondUserInfoTableHtml += '<td>&nbsp;</td>';
	secondUserInfoTableHtml += '<td><input style="display:none;" name="userLst['+userNo+'].usrSequence" value="'+userNo+'" /></td>';
	secondUserInfoTableHtml += '<td>&nbsp;</td>';
	secondUserInfoTableHtml += '</tr>';
	
	return secondUserInfoTableHtml;
}

function getYouthUserInfoData(userNo){
	var secondUserInfoTableHtml = '';
	secondUserInfoTableHtml += '<tr>';
	secondUserInfoTableHtml += '<td><input type="text" class="form-control" value="" title="Please enter yout First Name" name="userLst['+userNo+'].firstName" id="secFirstName" maxlength="50" placeholder="First Name"  /></td>';
	secondUserInfoTableHtml += '<td>&nbsp;</td>';
	secondUserInfoTableHtml += '<td><input type="text" class="form-control" value="" title="Please enter yout Last Name" name="userLst['+userNo+'].lastName" id="secLastName" maxlength="50" placeholder="Last Name" /></td>';
	secondUserInfoTableHtml += '<td>&nbsp;</td>';
	secondUserInfoTableHtml += '</tr>';
	
	secondUserInfoTableHtml += '<tr>';
	secondUserInfoTableHtml += '<td style="padding-bottom: 8px;"><input type="text" class="dob-class"  title="Please enter your Date of Birth" name="userLst['+userNo+'].dateOfBirth" id="secDOB" maxlength="50" placeholder="Date of Birth"  style="width: 1px; height: 1px; left : -1000px; margin: 0; padding: 0;" /><br />';
	secondUserInfoTableHtml += getDOBHtml("youthDobYearForm",  "youthDobMonthForm", "youthDobDateForm");
	secondUserInfoTableHtml += '</td>';
	secondUserInfoTableHtml += '<td id="dop-er"></td>';
	
	secondUserInfoTableHtml += '<td>';
	secondUserInfoTableHtml += '<span>';
	secondUserInfoTableHtml += '<span><span><input id="secGenderM" name="userLst['+userNo+'].gender" class="form-control" style="width: 15px;" type="radio" value="Male"></span><span style="margin-left : 5px;">Male</span></span>';
	secondUserInfoTableHtml += '<span><span><input id="secGenderF" name="userLst['+userNo+'].gender" class="form-control" style="width : 15px;" type="radio" value="Female"></span><span style="margin-left : 5px;">Female</span></span>';
	secondUserInfoTableHtml += '</span>';
	secondUserInfoTableHtml += '</td>';		
	secondUserInfoTableHtml += '<td>&nbsp;</td>';
	secondUserInfoTableHtml += '</tr>';		
	
	secondUserInfoTableHtml += '<tr style="display:none" id="youthEmailPhoneNumber">';
	secondUserInfoTableHtml += '<td><input type="text" class="form-control email-check"  name="userLst['+userNo+'].emailAddress" id="secEmail"  maxlength="50" placeholder="Email" /></td>';
	secondUserInfoTableHtml += '<td id="dop-er"></td>';
	secondUserInfoTableHtml += '<td><input type="text" class="form-control phone-number" title="Please enter your Phone Number" name="userLst['+userNo+'].formattedPhoneNumber" id="secPhoneNumber" maxlength="50" placeholder="Phone Number" /></td>';
	secondUserInfoTableHtml += '<td>&nbsp;</td>';
	secondUserInfoTableHtml += '</tr>';
	
	if(isUserLoggedInCheck != 'true'){	
		secondUserInfoTableHtml += '<tr id="youthPasswordTR" style="display:none">';
		secondUserInfoTableHtml += '<td><input type="password" class="form-control"  name="userLst['+userNo+'].password" id="youthPassword"  maxlength="50" placeholder="Password" /></td>';
		secondUserInfoTableHtml += '<td></td>';
		secondUserInfoTableHtml += '<td><input type="password" class="form-control" title="Please confirm Password" name="userLst['+userNo+'].confirmPassword" id="youth_confirm_password" maxlength="50" placeholder="Confirm Password" /></td>';
		secondUserInfoTableHtml += '<td>&nbsp;</td>';
		secondUserInfoTableHtml += '</tr>';
	}
	
	secondUserInfoTableHtml += '<tr style="display : none;">';
	secondUserInfoTableHtml += '<td><input style="display:none;"  name="userLst['+userNo+'].isAdult" value="true"/><input style="display:none;" name="userLst['+userNo+'].membershipAgeCategory" value="Youth"/></td>';
	secondUserInfoTableHtml += '<td>&nbsp;</td>';
	secondUserInfoTableHtml += '<td><input style="display:none;"  name="userLst['+userNo+'].usrSequence" value="'+userNo+'" /></td>';
	secondUserInfoTableHtml += '<td>&nbsp;</td>';
	secondUserInfoTableHtml += '</tr>';
	
	return secondUserInfoTableHtml;
}

function getThirdUserInfoData(userNo){
	var secondUserInfoTableHtml = '';
	secondUserInfoTableHtml += '<tr>';
	secondUserInfoTableHtml += '<td><input type="text" class="form-control" value="" title="Please enter yout First Name" name="userLst['+userNo+'].firstName" id="thirdFirstName" maxlength="50" placeholder="First Name"  /></td>';
	secondUserInfoTableHtml += '<td>&nbsp;</td>';
	secondUserInfoTableHtml += '<td><input type="text" class="form-control" value="" title="Please enter yout Last Name" name="userLst['+userNo+'].lastName" id="thirdLastName" maxlength="50" placeholder="Last Name" /></td>';
	secondUserInfoTableHtml += '<td>&nbsp;</td>';
	secondUserInfoTableHtml += '</tr>';
	
	secondUserInfoTableHtml += '<tr>';
	secondUserInfoTableHtml += '<td style="padding-bottom: 8px;"><input type="text" class="dob-class"  title="Please enter your Date of Birth" name="userLst['+userNo+'].dateOfBirth" id="thirdDOB" maxlength="50" placeholder="Date of Birth"  style="width: 1px; height: 1px; left : -1000px; margin: 0; padding: 0;" /><br />';
	secondUserInfoTableHtml += getDOBHtml("thirdDobYearForm",  "thirdDobMonthForm", "thirdDobDateForm");
	secondUserInfoTableHtml += '</td>';
	secondUserInfoTableHtml += '<td id="dop-er"></td>';
	secondUserInfoTableHtml += '<td><input type="text" class="form-control phone-number" title="Please enter your Phone Number" name="userLst['+userNo+'].formattedPhoneNumber" id="thirdPhoneNumber" maxlength="50" placeholder="Phone Number" /></td>';
	secondUserInfoTableHtml += '<td>&nbsp;</td>';
	secondUserInfoTableHtml += '</tr>';	
	
	secondUserInfoTableHtml += '<tr>';
	secondUserInfoTableHtml += '<td><input type="text" class="form-control email-check"  name="userLst['+userNo+'].emailAddress" id="thirdEmail"  maxlength="50" placeholder="Email"/></td>';
	secondUserInfoTableHtml += '<td id="dop-er"></td>';

	secondUserInfoTableHtml += '<td>';
	secondUserInfoTableHtml += '<span>';
	secondUserInfoTableHtml += '<span><span><input id="thirdGenderM" name="userLst['+userNo+'].gender" class="form-control" style="width: 15px;" type="radio" value="Male"></span><span style="margin-left : 5px;">Male</span></span>';
	secondUserInfoTableHtml += '<span><span><input id="thirdGenderF" name="userLst['+userNo+'].gender" class="form-control" style="width : 15px;" type="radio" value="Female"></span><span style="margin-left : 5px;">Female</span></span>';
	secondUserInfoTableHtml += '</span>';
	secondUserInfoTableHtml += '</td>';	
	
	secondUserInfoTableHtml += '<td>&nbsp;</td>';
	secondUserInfoTableHtml += '</tr>';
	
	/*if(isUserLoggedInCheck  != 'true'){
	secondUserInfoTableHtml += '<tr>';
	secondUserInfoTableHtml += '<td><input type="text" class="form-control"  name="userLst['+userNo+'].emailAddress" id="thirdEmail"  maxlength="50" placeholder="Email"  /></td>';
	secondUserInfoTableHtml += '<td id="dop-er"></td>';

	secondUserInfoTableHtml += '<td>';
	secondUserInfoTableHtml += '<span>';
	secondUserInfoTableHtml += '<span><span><input id="thirdGenderM" name="userLst['+userNo+'].gender" class="form-control" style="width: 15px;" type="radio" value="Male"></span><span style="margin-left : 5px;">Male</span></span>';
	secondUserInfoTableHtml += '<span><span><input id="thirdGenderF" name="userLst['+userNo+'].gender" class="form-control" style="width : 15px;" type="radio" value="Female"></span><span style="margin-left : 5px;">Female</span></span>';
	secondUserInfoTableHtml += '</span>';
	secondUserInfoTableHtml += '</td>';	
	
	secondUserInfoTableHtml += '<td>&nbsp;</td>';
	secondUserInfoTableHtml += '</tr>';
	}else{
		secondUserInfoTableHtml += '<tr>';
		secondUserInfoTableHtml += '<td ></td>';
		secondUserInfoTableHtml += '<td id="dop-er"></td>';

		secondUserInfoTableHtml += '<td>';
		secondUserInfoTableHtml += '<span>';
		secondUserInfoTableHtml += '<span><span><input id="thirdGenderM" name="userLst['+userNo+'].gender" class="form-control" style="width: 15px;" type="radio" value="Male"></span><span style="margin-left : 5px;">Male</span></span>';
		secondUserInfoTableHtml += '<span><span><input id="thirdGenderF" name="userLst['+userNo+'].gender" class="form-control" style="width : 15px;" type="radio" value="Female"></span><span style="margin-left : 5px;">Female</span></span>';
		secondUserInfoTableHtml += '</span>';
		secondUserInfoTableHtml += '</td>';	
		
		secondUserInfoTableHtml += '<td>&nbsp;</td>';
		secondUserInfoTableHtml += '</tr>';
	}*/
	
	secondUserInfoTableHtml += '<tr style="display : none;">';
	secondUserInfoTableHtml += '<td><input style="display:none;" name="userLst['+userNo+'].isAdult" value="true"/><input style="display:none;" name="userLst['+userNo+'].membershipAgeCategory" value="Adult"/></td>';
	secondUserInfoTableHtml += '<td>&nbsp;</td>';
	secondUserInfoTableHtml += '<td><input style="display:none;" name="userLst['+userNo+'].usrSequence" value="'+userNo+'" /></td>';
	secondUserInfoTableHtml += '<td>&nbsp;</td>';
	secondUserInfoTableHtml += '</tr>';
	
	return secondUserInfoTableHtml;
}

function getKidsInfoHtml(kidNo){
	var kidsInfoTableHtml = '';
	kidsInfoTableHtml += '<tr>';
	kidsInfoTableHtml += '<td colspan="4">';
	kidsInfoTableHtml += '<table border="0" width="101%" class="firstKidInfoForm">';
	kidsInfoTableHtml += '<tr>';
	kidsInfoTableHtml += '<td><input type="text" class="form-control firstName personFirstName" value="" title="Please enter yout First Name" name="userLst['+kidNo+'].firstName" id="firstName" maxlength="50" placeholder="First Name"  /></td>';
	kidsInfoTableHtml += '<td>&nbsp;</td>';
	kidsInfoTableHtml += '<td><input type="text" class="form-control lastName personLastName" value="" title="Please enter yout Last Name" name="userLst['+kidNo+'].lastName" id="lastName" maxlength="50" placeholder="Last Name" /></td>';
	kidsInfoTableHtml += '<td>&nbsp;</td>';
	kidsInfoTableHtml += '</tr>';
	
	kidsInfoTableHtml += '<tr>';
	kidsInfoTableHtml += '<td style="padding-bottom: 8px;"><input type="text" class="form-control dob-class dateOfBirth dateOfBirthValidation"  title="Please enter your Date of Birth" name="userLst['+kidNo+'].dateOfBirth"  maxlength="50" placeholder="Date of Birth" id="firstKidDOBInp"  style="width: 1px; height: 1px; left : -1000px; margin: 0; padding: 0;" /> <br />';
	kidsInfoTableHtml += getDOBHtml("firstKidDobYearForm",  "firstKidDobMonthForm", "firstKidDobDayForm");
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
	kidsInfoTableHtml += '<td style="padding-bottom: 8px;"><input type="text" class="form-control dob-class dateOfBirth dateOfBirthValidation"  title="Please enter your Date of Birth" name="userLst['+kidNo+'].dateOfBirth"  maxlength="50" placeholder="Date of Birth"  style="width: 1px; height: 1px; left : -1000px; margin: 0; padding: 0;" /><br />';
	kidsInfoTableHtml += getDOBHtmlbyClass("kidDobYearForm",  "kidDobMonthForm", "kidDobDateForm");
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
	kidsInfoTableHtml += '<td><input type="text" class="form-control firstName fval"  title="Please enter yout First Name" name="userLst['+count+'].firstName"  maxlength="50" placeholder="First Name"  value="'+kidFirstName+'"/></td>';
	kidsInfoTableHtml += '<td>&nbsp;</td>';
	kidsInfoTableHtml += '<td><input type="text" class="form-control lastName"  title="Please enter yout Last Name" name="userLst['+count+'].lastName"  maxlength="50" placeholder="Last Name" value="'+kidLastName+'"/></td>';
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
	//$(document).find('#secEmail').attr("value", "");		
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

function populateYouthMember(adultFirstName, adultLastName, adultDob, adultGender, adultPhoneNumber, adultEmail){
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

function populateFirstKidInfo(kidFirstName, kidLastName, kidDob, kidGender){
	$(".firstKidInfoForm").find(".firstName").attr("value",kidFirstName);
	$(".firstKidInfoForm").find(".lastName").attr("value",kidLastName);
	$(".firstKidInfoForm").find("input.dateOfBirth").attr("value",kidDob);
	var $radios = $(".firstKidInfoForm").find(".gender");
	if(kidGender == "Male") {
		$radios.filter('[value=Male]').prop('checked', true);
	}else{
		$radios.filter('[value=Female]').prop('checked', true);
	}
	var dobMonthForm = $('#becomeMemberForm').find("#firstKidDobMonthForm").data("kendoDropDownList");
	var dobDateForm = $('#becomeMemberForm').find("#firstKidDobDayForm").data("kendoDropDownList");
	var dobYearForm = $('#becomeMemberForm').find("#firstKidDobYearForm").data("kendoDropDownList");    
	//10/14/1984
	if(kidDob){
		var dateArr = kidDob.split("/");
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

function resetFirstKid(){
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

function changeEndDatePaymentScreen(){
	
	var str = $("#start").val();    
	$("#progStartDateIdHidInput").attr("value", $("#start").val());    		
	if(str != null) {

		var parts = str.split("/");

		var year = parts[2] && parseInt( parts[2], 10 );
		var month = parts[0] && parseInt( parts[0], 10 );
		var day = parts[1] && parseInt( parts[1], 10 );
		var duration = 1;
		var paymentFreq = $("#paymentFrequencySelect").data("kendoDropDownList");
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

			$("#end").val(month + "/" + day + "/" + year);
			$("#end").data('kendoDatePicker').value($("#end").val());
		} else {
			$("#paymentBillingcycleTr").css("display", "none");
			$("#paymentBillingcycleTd").html("");
			alert("Error Occured while setting Membership End Date");
		}
	}
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
		try {
		if(selectedAnnualJoinPrice != null){    			
			$("#sumJoinFeeTd").html(parseFloat(selectedAnnualJoinPrice));
		}
		} catch(e) {
			$("#sumJoinFeeTd").html("0");
		}
		//$("#end").attr("value", "");
		var str = $("#start").val();  		
	
	    if(str != null) {
	
	        var parts = str.split("/");
	
	        var year = parts[2] && parseInt( parts[2], 10 );
	        var month = parts[0] && parseInt( parts[0], 10 );
	        var day = parts[1] && parseInt( parts[1], 10 );
	        var duration = 1;
	
	        if( day <= 31 && day >= 1 && month <= 12 && month >= 1 ) {
	
	            var expiryDate = new Date( year, month - 1, day );
	            expiryDate.setFullYear( expiryDate.getFullYear() + duration );
	
	            var day = ( '0' + expiryDate.getDate() ).slice( -2 );
	            var month = ( '0' + ( expiryDate.getMonth() + 1 ) ).slice( -2 );
	            var year = expiryDate.getFullYear();
	
	            $("#end").val(month + "/" + day + "/" + year);
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

function getDOBHtmlbyClass(yearId,  monthId, dateId){
	var dobInfoHtml = '';
	dobInfoHtml += '<span class="">Date of Birth : </span> <br />';	
	dobInfoHtml += '<span>';
	dobInfoHtml += '<select name="dobMonth" class="'+monthId+'" style="width :90px;">';
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
	dobInfoHtml += '<select name="dobDate" class="'+dateId+'" style="width :45px;">';
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
	dobInfoHtml += '<select name="dobYear" class="'+yearId+'" style="width :70px;">';
	dobInfoHtml += '</select>';
	dobInfoHtml += '</span>';
	return dobInfoHtml;
}

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

function populateInputDOBfromDropdownMenu(month, date, year, inputId){
	var dob = month+ "/" +date+ "/" +year;
	$("#"+inputId).val(dob);	
}

function proceedToRegister1(thisProd, itemDetailIdVal){
	
	var urlPromoItemDetailId = $("#urlPromoItemDetailId").val();
	var urlPromoContactId = $("#urlPromoContactId").val();
	var urlPromoCode = $("#urlPromoCode").val();
	var itemDetailId = 0;
	console.log("  proceedToRegister  ....  "+thisProd);

	var registrationFee = 0;
	var depositAmount = 0;
	var areaType = '';
	var pricingOptionHtml = '';
	var allAreaPrice = 0;
	var bayAreaPrice = 0;
	var thisBranchPrice = 0;
	var signupContactId = 0;
	
	if(thisProd != null && thisProd != undefined){
		itemDetailId = itemDetailIdVal;
		//selectedProdctId = $(thisProd).parent().parent().find( ".prod-id-div" ).text();
		//itemDetailId = selectedProdctId;
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
		//selectedProductTotalPrice = $(this).parent().parent().find( ".prod-total-price-div" ).text();
		selectedProdTandc = $(thisProd).parent().parent().find( ".prod-tandc-div" ).html();
		//selectedAutoPromoDiscount = $(thisProd).parent().parent().find( ".prod-autoPromoDiscount-div" ).html();
		selectedProdType = $(thisProd).parent().parent().find( ".prod-type-div" ).text();
		pricingOptionHtml = $(thisProd).parent().parent().find( ".prod-price-option-dropdown-div" ).html();
		allAreaPrice = $(thisProd).parent().parent().find( ".all-area-price-td" ).text(); 
		bayAreaPrice = $(thisProd).parent().parent().find( ".bay-area-price-td" ).text();
		thisBranchPrice = $(thisProd).parent().parent().find( ".this-branch-price-td" ).text();
		billDateOption = $(thisProd).parent().parent().find( ".prod-billDateOption-div" ).text();
		billDateOffset = $(thisProd).parent().parent().find( ".prod-billDateOffset-div" ).text();
		dueDateOption = $(thisProd).parent().parent().find( ".prod-dueDateOption-div" ).text();
		dueDateOffset = $(thisProd).parent().parent().find( ".prod-dueDateOffset-div" ).text();
		
		var regFee = $(thisProd).parent().parent().find( ".prod-registration-fee-div" ).text();
		if(regFee != "undefined"){
			registrationFee = regFee;
		}
		
		var depAmt = $(thisProd).parent().parent().find( ".prod-deposit-amount-div" ).text();
		if(depAmt != "undefined"){
			depositAmount = depAmt;
		}   	
		selectPricingOptionAndPopulateEndDate();
		$("#paymentFrequencySelect").html(pricingOptionHtml);	
		*/	
		
	}else if(urlPromoItemDetailId != null && urlPromoItemDetailId != undefined){		
		console.log(" promo product ");		
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
				  
				  console.log(data);
				  
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
				  console.log("	  location_RecordName ::   " + data.location_RecordName);
				  console.log("	  billDateOption ::   " + data.billDateOption);
				  console.log("	  billDateOffset ::   " + data.billDateOffset);
				  console.log("	  dueDateOption ::   " + data.dueDateOption);
				  console.log("	  dueDateOffset ::   " + data.dueDateOffset);
				  
				  	selectedHeaderInfo = getProductHeader(data.RecordName);
					selectedTierPriceInfo = data.signupMonthlyTierPrice;
					selectedProdDescInfo = data.Description;
					selectedJoiningFeeInfo = data.signupMonthlyJoinPrice;     	
					selectedAnnualTierPrice =  data.signupAnnualTierPrice;
					selectedMonthlyBillingFreqency = data.signupMonthlyBillingFrequency;
					selectedAnnualBillingFreqency = data.signupAnnualBillingFrequency;
					selectedAnnualJoinPrice = data.signupAnnualJoinPrice;
					selectedProdctId = itemDetailId;
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
					
				    selectPricingOptionAndPopulateEndDate();
				    
				    console.log("	 selectedJoiningFeeInfo ::   " + selectedJoiningFeeInfo);
				    console.log("	 existingMemberJoinFeeInput ::   " + $("#existingMemberJoinFeeInput").val());
				    selectedJoiningFeeInfo = selectedJoiningFeeInfo - $("#existingMemberJoinFeeInput").val();
				    
					console.log("	 updated selectedJoiningFeeInfo ::   " + selectedJoiningFeeInfo);
				    
					//console.log("	  pricingOptionHtml ::   " + pricingOptionHtml);
					//console.log("	  paymentFreq ::   " + paymentFreq.text());
					
					pricingOptionHtml =  getPriceOptionHTML(data.signupPrice);
					$("#paymentFrequencySelect").html(pricingOptionHtml);
					$("#paymentFrequencySelect").kendoDropDownList();
				    var paymentFreq = $("#paymentFrequencySelect").data("kendoDropDownList");
				    paymentFreq.select(0);
				    
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
	
	console.log("	bm  selectedHeaderInfo   ::   " + selectedHeaderInfo);
	console.log("	  selectedTierPriceInfo   ::   " + selectedTierPriceInfo);
	console.log("	  selectedJoiningFeeInfo   ::   " + selectedJoiningFeeInfo);
	console.log("	  selectedAnnualTierPrice   ::   " + selectedAnnualTierPrice);
	console.log("	  selectedAnnualJoinPrice   ::   " + selectedAnnualJoinPrice);
	console.log("	  registrationFee   ::   " + registrationFee);
	console.log("	  depositAmount   ::   " + depositAmount);
	
	var paymentFreq = $("#paymentFrequencySelect").data("kendoDropDownList");
    paymentFreq.search("Monthly");
	
	$("#joinFeeMonthly").val(selectedJoiningFeeInfo);
	$("#joinFeeYealy").val(selectedAnnualJoinPrice);
	$("#signupPriceMonthly").val(selectedTierPriceInfo);
	$("#signupPriceYearly").val(selectedAnnualTierPrice);
	$("#registrationFee").val(registrationFee);
	$("#depositAmount").val(depositAmount);
	$("#productIdHidInput").val(selectedProdctId);
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
	
	var paymentType = '';
		
	console.log("   areaType  :::   "+areaType);
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
	
	console.log("   paymentType  :::   "+paymentType);
		
	var signupPrice = 0;
	if(paymentType != ''){
		
		if(paymentType == 'AllArea'){
			//var allAreaPrice = $(thisProd).parent().parent().find( ".all-area-price-td" ).text();    		
			selectedProductTotalPrice = parseFloat(allAreaPrice) + parseFloat(selectedJoiningFeeInfo) + parseFloat(registrationFee) + parseFloat(depositAmount);       
			$("#sumTierPriceTd").html(allAreaPrice);
			$("#signUpProductType").attr("value","All Branches");
			
			signupPrice = allAreaPrice;
			
			console.log("  allAreaPrice = "+allAreaPrice);
			
		}else if(paymentType == 'BayArea'){
			//var bayAreaPrice = $(thisProd).parent().parent().find( ".bay-area-price-td" ).text();    		
			selectedProductTotalPrice = parseFloat(bayAreaPrice) + parseFloat(selectedJoiningFeeInfo) + parseFloat(registrationFee) + parseFloat(depositAmount);    
			$("#sumTierPriceTd").html(bayAreaPrice);
			$("#signUpProductType").attr("value","Bay Area");
			
			signupPrice = bayAreaPrice;
			
			console.log("  bayAreaPrice = "+bayAreaPrice);
			
		}else if(paymentType == 'ThisBranchOnly'){
			//var thisBranchPrice = $(thisProd).parent().parent().find( ".this-branch-price-td" ).text();    		
			selectedProductTotalPrice = parseFloat(thisBranchPrice) + parseFloat(selectedJoiningFeeInfo) + parseFloat(registrationFee) + parseFloat(depositAmount);       			
			$("#sumTierPriceTd").html(thisBranchPrice);    	
			$("#signUpProductType").attr("value","This Branch Only");
			
			signupPrice = thisBranchPrice;
			
			console.log("  thisBranchPrice = "+thisBranchPrice);
			
		}    		
	}else{
		selectedProductTotalPrice = parseFloat(selectedTierPriceInfo) + parseFloat(selectedJoiningFeeInfo) + parseFloat(registrationFee) + parseFloat(depositAmount); 
		$("#sumTierPriceTd").html(selectedTierPriceInfo);
		$("#signUpProductType").attr("value","This Branch Only");
		
		signupPrice = selectedTierPriceInfo;
		
		console.log("  selectedTierPriceInfo = "+selectedTierPriceInfo);
		
	}
	
	console.log("  selectedProdctId ::: "+selectedProdctId+",  selectedProductName  ::: "+selectedProductName);
	
	var setupfee = 0;
	//console.log("  selectedProductTotalPrice  "+selectedProductTotalPrice);
	//console.log("  signupPrice  "+signupPrice+"  setupfee  "+setupfee+"	 selectedJoiningFeeInfo  "+selectedJoiningFeeInfo+"   registrationFee  "+registrationFee+"   depositAmount  "+depositAmount);
	amountMap = '{ "signupPrice":"'+signupPrice+'", "setupFee":"'+setupfee+'", "joinFee":"'+selectedJoiningFeeInfo+'", "registrationFee":"'+registrationFee+'", "depositAmount":"'+depositAmount+'"}';
	
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
	
	//console.log("   convertDateToDateStr invoiceDate   "+);
	//console.log("   convertDateToDateStr billDate   "+convertDateToDateStr(billDate));
	
	$("#invoiceDate").val(convertDateToDateStr(invoiceDate));
	$("#billDate").val(convertDateToDateStr(billDate));
	$("#dueDate").val(convertDateToDateStr(dueDate));
	$("#nextBillDate").val(convertDateToDateStr(nextBillDate));
	$("#billDateOnInvoice").val(convertDateToDateStr(billDateOnInvoice));
	$("#dueDateOnInvoice").val(convertDateToDateStr(dueDateOnInvoice));
	
	
	/*promotionMap =  validatePromo('callForMembership', null, promotionMap, amountMap);
	var totalDiscount = applyPromos(promotionMap);
	
	console.log(" Total Discount :: "+totalDiscount);
	
	var totalPriceWithoutDiscount = selectedProductTotalPrice;
	
	selectedProductTotalPrice = parseFloat(totalPriceWithoutDiscount) - parseFloat(totalDiscount);
	
	console.log("  selectedProductTotalPrice  "+selectedProductTotalPrice);
	
	$("#totalDiscountHiddenInput").val(totalDiscount);
	$("#paymentAmountSpan").html(selectedProductTotalPrice);
	$("#paymentTotalPriceTd").html(selectedProductTotalPrice);	
	$("#sumTotalPriceTd").html(selectedProductTotalPrice); 	
	
	console.log("  selectedProductTotalPrice 11  "+selectedProductTotalPrice);*/
	
	$('#applypromo').click(function() {
		
		$("#tcSuccessSpan").css("display", "none");		
		$("#tcSuccessSpan" ).html("");	
		$("#tcErrorSpan").css("display", "none");		
		$( "#tcErrorSpan" ).html("");
		
		populatePaymentInfoSection();
		
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
		
		if(d_promocode!=''){
			
			var manualPromos = getManualPromoMap(selectedProdctId, contactId, isRecurring, amountMap, selectedStartDate, cartItems);
			
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
		
		populatePaymentInfoSection();
		
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
		
		
		$("#c_promocode_old").val("");*/
		
	});
	
	 $("#paymentFrequencySelect").on('change',function(e){
		 
		console.log("  paymentFrequencySelect change  ... ");
		
		var paymentFreqOnChange = $("#paymentFrequencySelect").data("kendoDropDownList");
	    $("#paymentFrequency").val(paymentFreq.text());
	     
    	$("#paymentFrequencyTd").html(paymentFreq.text());
    	$("#sumPaymentFreqTd").html(paymentFreq.text());    
    	//$("#paymentBillingcycleTr").css("display", "none");
    	//$("#paymentBillingcycleTd").html("");
    	
    	//console.log("  paymentFrequencySelect change  ... "+paymentFreq.text());
    	urlPromoCode = $("#urlPromoCode").val();
    	
    	var billDateVal = $("#billDate").val();
    	var	nextBillDateOnChange = getNextBillDate(paymentFreq.text(), billDateVal, null); //, null, null, null, null);
    	$("#nextBillDate").val(nextBillDateOnChange);
    	
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

	            var parts = str.split("/");

	            var year = parts[2] && parseInt( parts[2], 10 );
	            var month = parts[0] && parseInt( parts[0], 10 );
	            var day = parts[1] && parseInt( parts[1], 10 );
	            var duration = 1;

	            if( day <= 31 && day >= 1 && month <= 12 && month >= 1 ) {

	                var expiryDate = new Date( year, month - 1, day );
	                expiryDate.setFullYear( expiryDate.getFullYear() + duration );

	                var day = ( '0' + expiryDate.getDate() ).slice( -2 );
	                var month = ( '0' + ( expiryDate.getMonth() + 1 ) ).slice( -2 );
	                var year = expiryDate.getFullYear();

	                $("#end").val(month + "/" + day + "/" + year);
	                $("#end").data('kendoDatePicker').value($("#end").val());

	            } else {
	                alert("Error Occured while setting Membership End Date");
	            }
	        }
	        $("#paymentBillingcycleTr").css("display", "none");
	        $("#paymentBillingcycleTd").html("");
        
	        amountMap = '{ "signupPrice":"'+$("#sumTierPriceTd").text()+'", "setupFee":"'+setupfee+'", "joinFee":"'+$("#sumJoinFeeTd").text()+'", "registrationFee":"'+registrationFee+'", "depositAmount":"'+depositAmount+'"}';
	        
	        //promotionMap = getAutoPromotionMap(selectedProdctId, selectedAnnualBillingFreqency, amountMap, selectedProductTotalPrice, urlPromoCode);
        
    	}else{
    		$("#endDateDiv").css("display","none");    		
    		$("#sumTotalPriceTd").html(selectedProductTotalPrice);
    		//$("#paymentBillingcycleTd").html($("#start").val() + "(Of each month)");
    		$("#membershipFrequencyId").attr("value","Monthly");
    		//$("#end").attr("value", "");
    		if(selectedTierPriceInfo != null){
    			$("#sumTierPriceTd").html(parseFloat(selectedTierPriceInfo));
    		}
    		if(selectedAnnualJoinPrice != null){    			
    			$("#sumJoinFeeTd").html(parseFloat(selectedJoiningFeeInfo));
    		}
    		
    		//amountMap = '{ "signupPrice":"'+$("#sumTierPriceTd").text()+'", "setupFee":"'+setupfee+'", "joinFee":"'+$("#sumJoinFeeTd").text()+'", "registrationFee":"'+registrationFee+'", "depositAmount":"'+depositAmount+'"}';
    		
    		//promotionMap = getAutoPromotionMap(selectedProdctId, selectedMonthlyBillingFreqency, amountMap, selectedProductTotalPrice, urlPromoCode);
    	}
    	
    });
	
	 
	/*if(selectedAutoPromoDiscount != 0){
		  $("#sumAutoApplyTr").css("display","");			  
		  $("#sumEarlyBirdDiscount").html(selectedAutoPromoDiscount);
	  }else{
		  $("#sumAutoApplyTr").css("display","none");			 
		  $("#sumEarlyBirdDiscount").html("");
	  }
	
	 $("#sumPromoCodeTr").css("display","none");			  
	 $("#sumPromoCodeName").html("");
	 $("#sumPromoCodeVal").html("");		 */
	
	//alert(selectedAutoPromoDiscount);
	//selectedItemDetailsId = $(thisProd).parent().parent().find( ".prod-itemDetailsId-div" ).html();
	

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
	$("#productIdHidInput").attr("value", selectedProdctId);
	$("#productNameTd").html(selectedProductName);
	$("#productDescriptionTd").html(selectedProdDescInfo);
	$("#productTypeTd").html(selectedProdType);    	
	
	//$("#productPriceTd").html(selectedProductTotalPrice);
	
	$(".termsDiv").html(selectedProdTandc);
	$("#userTC").attr("value", selectedProdTandc);
	
	$("#paymentJoinFeeTd").html(selectedJoiningFeeInfo);
	$("#sumJoinFeeTd").html(selectedJoiningFeeInfo);
	
	var oneAdultWithKidsHtml = getKidsInfoHeader() + getKidsInfoHtml("1");
	var youthHtml = getYouthInfoHeader() + getYouthUserInfoData("1");
	var twoAdultsHtml = getSecondUserInfoHeader() + getSecondUserInfoData("1");
	var twoAdultsWithKidsHtml = getSecondUserInfoHeader() + getSecondUserInfoData("1") + getKidsInfoHeader() + getKidsInfoHtml("2");
	var threeAdultsWithKidsHtml = getSecondUserInfoHeader() + getSecondUserInfoData("1") + getThirdUserInfoHeader() + getThirdUserInfoData("2") + getKidsInfoHeader() + getKidsInfoHtml("3");    
	var threeAdultsHtml = getSecondUserInfoHeader() + getSecondUserInfoData("1") + getThirdUserInfoHeader() + getThirdUserInfoData("2");
	validateEmail(0);
	console.log("  selectedHeaderInfo  "+selectedHeaderInfo);
	$('#youthInfoFormTable').css("display" , "none");
	$('#youthInfoFormTable').html('');	
	$(".primaryUserInfoTable").css("display", "");
	$("#primaryMembershipAgeCategory").attr("value", "Adult");
	if(selectedHeaderInfo == 'Adult'){   
		$("#primaryMemberTxtSpan").html("PRIMARY MEMBER");	
		$('#familyInfoFormTable').css("display" , "block");
		$('#familyInfoFormTable').html("");
		validateDOBAdult(0);
		validateAdult(0);
	}else if(selectedHeaderInfo == 'Senior'){
		$("#primaryMemberTxtSpan").html("PRIMARY MEMBER");
		$('#familyInfoFormTable').css("display" , "block");
		$('#familyInfoFormTable').html("");    		
		validateAdult(0);
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
	}else if(selectedHeaderInfo == 'One Adultw/ Kids'){
		$("#primaryMemberTxtSpan").html("PRIMARY MEMBER");
		$('#familyInfoFormTable').css("display" , "inline-table");
		$('#familyInfoFormTable').html(oneAdultWithKidsHtml);
		$('#familyInfoFormTable').find(".dob-class").kendoDatePicker();
		secKidCount = 2;
		validateDOBAdult(0);
		validateAdult(0);
		validatekid(1);
	}else if(selectedHeaderInfo == 'Two Adults' ){
		$("#primaryMemberTxtSpan").html("PRIMARY MEMBER");
		$('#familyInfoFormTable').css("display" , "inline-table");
		$('#familyInfoFormTable').html(twoAdultsHtml);    		
		$('#familyInfoFormTable').find(".dob-class").kendoDatePicker();  
		validateDOBAdult(0);
		validateAdult(0);
		validateAdult(1);
	}else if(selectedHeaderInfo == 'Two Adultsw/ Kids'){    
		$("#primaryMemberTxtSpan").html("PRIMARY MEMBER");
		$('#familyInfoFormTable').css("display" , "inline-table");
		$('#familyInfoFormTable').html(twoAdultsWithKidsHtml);
		$('#familyInfoFormTable').find(".dob-class").kendoDatePicker();
		validateDOBAdult(0);
		validateAdult(0);
		validateAdult(1);
		validatekid(2);
		secKidCount = 3;
	}else if(selectedHeaderInfo == 'Three Adults'){
		$("#primaryMemberTxtSpan").html("PRIMARY MEMBER");
		$('#familyInfoFormTable').css("display" , "inline-table");
		$('#familyInfoFormTable').html(threeAdultsHtml);
		$('#familyInfoFormTable').find(".dob-class").kendoDatePicker();
		validateDOBAdult(0);
		validateAdult(0);
		validateAdult(1);
		validateAdult(2);    		
	} else if(selectedHeaderInfo == 'Three Adultsw/ or w/o kids'){
		$("#primaryMemberTxtSpan").html("PRIMARY MEMBER");
		$('#familyInfoFormTable').css("display" , "inline-table");
		$('#familyInfoFormTable').html(threeAdultsWithKidsHtml);
		$('#familyInfoFormTable').find(".dob-class").kendoDatePicker();    
		validateDOBAdult(0);
		secKidCount = 4;  
		validateAdult(0);
		validateAdult(1);
		validateAdult(2);
		validatekid(3);
		
	}else{
		$('#familyInfoFormTable').css("display" , "none");
	}    
	$(".phone-number").mask("(999) 999-9999");    	
	$("#wizard"). smartWizard("fixHeight");
	registerSelected = true;   
	
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
     	//checkIsUserAdult();
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
     		$(document).find('#secPhoneNumber').prop('readonly', true);
     		$(document).find('#secEmail').prop('readonly', true);
     		//$(document).find('#secEmail').attr("value", "");		
     		$(document).find('#secGenderM').prop('readonly', true);
     		$(document).find('#secGenderF').prop('readonly', true);
       	}else if(contactVal == 0 || contactVal == 'Other' || flag == false){
       		resetYouthMember();
       		$(document).find('#secFirstName').prop('readonly', false);
     		$(document).find('#secLastName').prop('readonly', false);
     		$(document).find('#secDOB').prop('readonly', false); 
     		$(document).find('#secPhoneNumber').prop('readonly', false);
     		$(document).find('#secEmail').prop('readonly', false);
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
   	populateInputDOBfromDropdownMenu($("#secDobMonthForm").val(), $("#secDobDateForm").val(), $("#secDobYearForm").val(), "secDOB");
   	populateInputDOBfromDropdownMenu($("#youthDobMonthForm").val(), $("#youthDobDateForm").val(), $("#youthDobYearForm").val(), "secDOB");
   	populateInputDOBfromDropdownMenu($("#thirdDobMonthForm").val(), $("#thirdDobDateForm").val(), $("#thirdDobYearForm").val(), "thirdDOB");
   	populateInputDOBfromDropdownMenu($("#firstKidDobMonthForm").val(), $("#firstKidDobDayForm").val(), $("#firstKidDobYearForm").val(), "firstKidDOBInp");       	
	//$(".selectContactDropDown").kendoDropDownList();
	//validator1.resetForm();
	$('#wizard').smartWizard('goToStep',2);
	
	
}

function validateEmail(n){  
	var url = "isEmailExists";
	$('#becomeMemberForm')
    .find('input[name^="userLst['+ n +'].emailAddress"]').rules('remove',"required");
	$('#becomeMemberForm')
    .find('input[name^="userLst['+ n +'].emailAddress"]').rules('remove',"email");
	$('#becomeMemberForm')
    .find('input[name^="userLst['+ n +'].emailAddress"]').rules('remove',"remote");
	$('#becomeMemberForm')
    .find('input[name^="userLst['+ n +'].emailAddress"]').rules('remove',"check_duplicate_email");
	
	$('#becomeMemberForm')
    .find('input[name^="userLst['+ n +'].emailAddress"]').rules('add', {
    	//required: true,
    	validate_email_format: true,
		remote: {
			url: url,  
			type:"GET"                    	
		 },
		 check_duplicate_email : true,
      messages: {
    	  //required: "Please enter your email address",
    	  validate_email_format : "Please enter valid email address",
  		remote: "You appear to be associated with a customer account already.  Please speak with the primary contact for that account",
  		check_duplicate_email : "Please enter unique email"
			
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

function validateAdult(n){
	validateFirstName(n)
	validateLastName(n);
	validatePhoneNumber(n);
	if(isUserLoggedInCheck != 'true'){
		validateEmail(n);
	}else{
		requiredEmail(n);
	}
	
	validateDOBAdult(n);
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

function requiredEmail(n){    	   
	$('#becomeMemberForm')
    .find('input[name^="userLst['+ n +'].emailAddress"]').rules('remove',"required");
	$('#becomeMemberForm')
    .find('input[name^="userLst['+ n +'].emailAddress"]').rules('remove',"email");
	$('#becomeMemberForm')
    .find('input[name^="userLst['+ n +'].emailAddress"]').rules('remove',"remote");
	$('#becomeMemberForm')
    .find('input[name^="userLst['+ n +'].emailAddress"]').rules('remove',"check_duplicate_email");
	
	$('#becomeMemberForm')
    .find('input[name^="userLst['+ n +'].emailAddress"]').rules('add', {
    	//required: true,
    	validate_email_format: true,
		check_duplicate_email : true,
      messages: {
    	  //required: "Please enter your email address",
    	  validate_email_format : "Please enter valid email address",
  		check_duplicate_email : "Please enter unique email"
      }
    });
}

function validatekid(n){
	validateFirstName(n);
	validateLastName(n);		
	validateDOBKid(n);
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

function validateYouth(n){
	validateFirstName(n)
	validateLastName(n);
	//validatePhoneNumber(n);
	if(isUserLoggedInCheck != 'true'){
		validateEmail(n);
	}else{
		requiredEmail(n);
	}		
	validateDOBYouth(n);
}
function validateAdult(n){
	validateFirstName(n)
	validateLastName(n);
	validatePhoneNumber(n);
	if(isUserLoggedInCheck != 'true'){
		validateEmail(n);
	}else{
		requiredEmail(n);
	}
	
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

function requiredEmail(n){    	   
	$('#becomeMemberForm')
    .find('input[name^="userLst['+ n +'].emailAddress"]').rules('remove',"required");
	$('#becomeMemberForm')
    .find('input[name^="userLst['+ n +'].emailAddress"]').rules('remove',"email");
	$('#becomeMemberForm')
    .find('input[name^="userLst['+ n +'].emailAddress"]').rules('remove',"remote");
	$('#becomeMemberForm')
    .find('input[name^="userLst['+ n +'].emailAddress"]').rules('remove',"check_duplicate_email");
	
	$('#becomeMemberForm')
    .find('input[name^="userLst['+ n +'].emailAddress"]').rules('add', {
    	//required: true,
    	validate_email_format: true,
		check_duplicate_email : true,
      messages: {
    	  //required: "Please enter your email address",
    	  validate_email_format : "Please enter valid email address",
  		check_duplicate_email : "Please enter unique email"
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

function showDateError(){
	/*var kendoDateErrWindow = $("<div />").kendoWindow({
		title: "Error",
		resizable: false,
		modal: true,
		width:400
	})

	kendoDateErrWindow.data("kendoWindow")
	 .content($("#billing-date-ErrorBox").html())
	 .center().open()

	kendoDateErrWindow
	.find(".confirm-billingDate")
	.click(function() {
		if ($(this).hasClass("confirm-billingDate")) {         		
			kendoDateErrWindow.data("kendoWindow").close();
		}
	})
	.end()*/
	alert("Please select correct date using datepicker");
}

function onShowStep(obj, context){	
	console.log("  onShowStep  "+context.fromStep+" -> "+context.toStep);
	if(context.toStep == 1 ){
		$('.swMain .actionBar').css("display", "none");		
	}else{		
		$('.swMain .actionBar').css("display", "block");		
	}	
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
