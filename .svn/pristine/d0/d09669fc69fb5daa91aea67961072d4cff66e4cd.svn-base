$(document).ready(function() {		
	$( "#cart-info" ).click(function(){
		gotocartpage();
	});
	
});

var paymentOrderId = Math.floor((Math.random()*1000000000000)+1);
var payment_method_id = 0;

function gotocartpage(){
	$("#program_details").css("border-width", "1px");	
	 $("#program_details").css("margin-top", "20px");	
	 $("#dspErr").slideUp();
	 
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

function fnGotoFamilyMember(){
	$("#familymembers").show();
	$("#checkout_content").hide();
}

function emptycart(){
	$("#program").fadeIn(100);
	getProgramSession();
}

function continueshop(){
	$(".k-loading-mask").show();
	
	$("#checkout_content").fadeOut(200);
	$("#program").fadeIn(100);
	getProgramSession();
	//$("#details-checkout").hide();
	//location.href = '#/';
	
	$(".k-loading-mask").hide();
}

// payment cart page
	/*
	function submitform(){
		if($("#paymentInfoRadio").val()=='New'){
			if($('#SaveCard').is(":checked")){
				// check if nick name is not blank
				
				if($("#nickName").val()==""){
					 $("#tcErrorSpan").css("display", "block");		
					 $( "#tcErrorSpan" ).html("Please provide Nick Name");
				}
				else{
					//var payId = addcccard();
					processpayment();
					//proceedtosignup(payId);
				}
			}
			else{
				var paymentId = "0";
				processpayment();
				//alert();
				//proceedtosignup(paymentId);
			}
		} 
		else {
			proceed = true;
			var paymentId = $('#paymentInfoRadio').val();  
			//console.log(paymentId);
			//alert(paymentId);
			
			if(paymentId=='' || typeof paymentId === "undefined"){
					//alert($("#statusBlock").html());
				  $("#tcSuccessSpan").css("display", "none");		
				  $("#tcSuccessSpan" ).html("");	
				  $("#tcErrorSpan1").css("display", "block");		
				  $( "#tcErrorSpan1" ).html("Please select mode of payment");
				  return;
			}
			
			//proceedtosignup(paymentId);
			processpaymentbytoken(paymentId);
		}
	}

	function proceedtosignup(paymentId, jp_request_hash){
		$(".k-loading-mask").show();
		var sessionData1 = [], contactData1 = [],
			data = $('form').find('input:not([name="selectedItemSession"])').serialize();
	
		var cartItems = [];
		if(cart.contents.length>0){
				$.each(cart.contents, function(i,itemDetailsSession) {
					var itemprice = 0;
					
					if(itemDetailsSession.contact.isMember)
						itemprice = (parseFloat(itemDetailsSession.item.memberprice) - parseFloat(itemDetailsSession.item.memberdiscount) - parseFloat(itemDetailsSession.discount1)) * itemDetailsSession.quantity;
					else
						itemprice = (parseFloat(itemDetailsSession.item.nonmemberprice) - parseFloat(itemDetailsSession.item.nonmemberdiscount) - parseFloat(itemDetailsSession.discount1)) * itemDetailsSession.quantity;
					
					cartItems.push(itemDetailsSession.item.prodId+"__"+itemDetailsSession.contact.contactId+"__"+itemprice);
				});
		}
		
		data += '&jp_request_hash='+jp_request_hash+'&paymentId='+paymentId+'&cartItems='+cartItems.join(',');
		//alert(data);
		$.ajax({
			  type: "POST",
			  url: $('#signupFrm').attr( "action"),
			  async:false,
			  data: data,
			  success: function( data ) {
				  //alert(data);
				 // console.log(data);
				 data_s = data.split("__");
				 if(data_s[0]=="SUCCESS"){
					  $.sessionStorage.clear();
					  cart.clear();
					  $(".k-loading-mask").hide();
					  $("#payment_cart").html("");
					  $("#payment_cart").html('<div id="statusBlock" class="k-block k-success-colored" style="width: 90%;margin: 20px; padding: 10px;">Thank you for enrolling to the Program.<br>Your transaction Id is: '+data_s[1]+'</div>');
					  
					  $("#tcErrorSpan").css("display", "none");		
					  $("#tcErrorSpan" ).html("");	
					  $("#tcSuccessSpan").css("display", "none");		
					  $("#tcSuccessSpan" ).html("");
				 }
				 else{
					  $("#tcErrorSpan").css("display", "block");		
					  $("#tcErrorSpan" ).html("There is some error, please try after some time");	
					  $("#tcSuccessSpan").css("display", "none");		
					  $("#tcSuccessSpan" ).html("");
					  $(".k-loading-mask").hide();
				 }
			  },
			  error: function( xhr,status,error ){
				  //alert("1" +status);
				  $("#tcErrorSpan").css("display", "block");		
				  $("#tcErrorSpan" ).html("There is some error, please try after some time");	
				  $("#tcSuccessSpan").css("display", "none");		
				  $("#tcSuccessSpan" ).html("");
				  $(".k-loading-mask").hide();
			  }
			});
	}
	
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
	
	function processpayment(){
		
		orderamount = $("#ordertotal").text();
		transactionAmount = $.trim(orderamount.replace("$",""));
		var jsSha = new jsSHA(merchantId+transactionAmount+JetDirectToken+paymentOrderId);
		hash = jsSha.getHash("SHA-512", "HEX");	
		var jsonData = {
					name : $("#fullName").val(),
					cardNum : $("#cardNumber").val(),
					cscNumber : $("#securityCode").val(),
					AddressLine1 : $("#billingAddressLine1").val(),
					AddressLine2 : $("#billingAddressLine2").val(),
					City : $("#billingCity").val(),
					state : $("#billingState").val(),
					zipcode : $("#billingZip").val(),
					email : "",
					amount : transactionAmount,
					expMonth : $("#expirationMonth").val(),
					expYr : $("#addCardExpirationYear").val(),
					contry : "US",
					jetPayHash : hash.toString(),
					paymentOrderId : paymentOrderId.toString()
					
		};
		var win = document.getElementById("childIframeId").contentWindow;
		win.postMessage(jsonData, '*');
	}
	
	function processpaymentbytoken(payId){
		orderamount = $("#ordertotal").text();
		transactionAmount = $.trim(orderamount.replace("$",""));
		var jp_request_hash = hash;
		
		$.ajax({
			  type: "POST",
			  url: "processPaymentByTokenId",	
			  data: { token: "KKLICIMMKOKJJHKINHKLOLHM", totalAmount : Math.ceil(transactionAmount)},
			  success: function( data ) {
				  if(data != null && data.responseText == "APPROVED"){	
					  proceedtosignup(payId,jp_request_hash);
				  }else {
					  $("#tcSuccessSpan").css("display", "none");		
					  $("#tcErrorSpan").css("display", "block");	
					  $( "#tcErrorSpan" ).html("The transaction failed..");	
				  }
				  $(".k-loading-mask").hide();
			  },
			  error: function( xhr,status,error ){
				  $("#tcSuccessSpan").css("display", "none");		
				  $("#tcErrorSpan").css("display", "block");	
				  $( "#tcErrorSpan" ).html("There was some error. Please try again later");							  		 
			  }
		});	
	}*/
	
// search program page

	function rangeSliderOnSlide(e) {
        //kendoConsole.log("Slide :: new slide values are: " + e.value.toString().replace(",", " - "));
    }

    function rangeSliderOnChange(e) {
        //kendoConsole.log("Change :: new values are: " + e.value.toString().replace(",", " - "));
    }
	
	function getProgramSession(){
		$("#program_details").css("border-width", "0");	
		$("#program_details").css("margin-top", "9px");	
		$("#contactHealthHistoryDiv").hide();
		var category = $("#class").val();
		var productname = $("#subclass").val();
		var location = $("#location").val();
		var age_min = $("#age_min").val();
		var age_max = $("#age_max").val();
		
		var datestart = $("#datepicker").val();
		var dateend = $("#datepickerend").val();
		
		// Set filter in session
		$.sessionStorage.setItem("search_program_filter", JSON.stringify( { _category: category, _productname: productname, _location: location,
																			_age_min: age_min, _age_max: age_max, 
																			_datestart: datestart, _dateend: dateend } ));
		
		$.ajax({
			  type: "GET",
			  url:"getProgramDetails?strLocation="+location+"&productname="+productname+"&category="+category+"&age_min="+age_min+"&age_max="+age_max+"&datestart="+datestart+"&dateend="+dateend,
			  success: function( data ) {
				 //console.log(data);
			  	 //console.log(data.length);
			  	 if(data.length>0){
			  		 var itemDetailsId = "";
				  	 var price = "";
				  	 var days = "";
				  	 var dspDays = "";
				  	 /*
				  	 var item_Details = "<table id='programDetail' class='program_desc' width='100%'>";
				  	 $.each(data, function(i,itemDetailsSession) {
				  		 	if(itemDetailsId=="" || itemDetailsId!=itemDetailsSession[11]){
				  				itemDetailsId = itemDetailsSession[11];
				  				
				  				item_Details = item_Details + "<tr>";
					  				item_Details = item_Details + "<td><span class='name boldorange' style='font-weight:bold;'>Program Name:</span></td>";
					  				item_Details = item_Details + "<td><span class='name'><a id='programname"+itemDetailsId+"' href='#' onclick='showProgramSessionTble("+itemDetailsId+")'>"+itemDetailsSession[12]+"</a></span></td>";
					  				item_Details = item_Details + "<td><span class='name'>"+formatDate(itemDetailsSession[9])+"</span><span class='name'> - </span><span class='name'>"+formatDate(itemDetailsSession[10])+"</span></td>";
						  		item_Details = item_Details + "</tr>";
				  		 	}
				  	 });
				  	 item_Details = item_Details + "</table>";
				  	//console.log(item_Details);
				  	 $("#program_session").html(item_Details);
				  	 */
				  	 
				  	 var productId = "";
				  	 var item_session = "";
				  	 //var item_session = "<div style='margin-bottom: 20px;'><span class='head'>NEW PROGRAM SIGN UP</span><span id='shoppingcart1' style='float:right;'></span></div>";
				  	 item_session = item_session + "<table id='program_signup' class='program_desc' style='table-layout: fixed; width: 782px;'>";
				  	 item_session = item_session + "<colgroup>";
					  	 item_session = item_session + "<col style='width: 30px' />";
					  	 item_session = item_session + "<col style='width: 80px' />";
					  	 item_session = item_session + "<col style='width: 85px' />";
					  	 item_session = item_session + "<col style='width: 62px;'>";
					  	 item_session = item_session + "<col style='width: 84px;' />";
					  	 item_session = item_session + "<col style='width: 84px;' />";
					  	 item_session = item_session + "<col style='width: 65px;' />";
					  	 item_session = item_session + "<col style='width: 65px;' />";
					  	 item_session = item_session + "<col style='width: 52px;' />";
					  	 item_session = item_session + "<col style='width: 60px;' />";
					  	 item_session = item_session + "<col style='width: 65px;' />";
					  	 item_session = item_session + "<col style='width: 70px;' />";
			         item_session = item_session + "</colgroup>";
				  	 item_session = item_session + "<thead><tr>";
				  		item_session = item_session + "<th data-field='randomFieldName'>&nbsp;</th>";
				  		item_session = item_session + "<th data-field='programName'>Program Name</th>";
				  		item_session = item_session + "<th data-field='location'>Location</th>";
				  		item_session = item_session + "<th data-field='instructor'>Instructor</th>";
				  		item_session = item_session + "<th data-field='startDate'>Start Date</th>";
				  		item_session = item_session + "<th data-field='endDate'>End Date</th>";
				  		item_session = item_session + "<th data-field='startTime'>Start Time</th>";
				  		item_session = item_session + "<th data-field='endTime'>End Time</th>";
				  		item_session = item_session + "<th data-field='capacity'>Capacity</th>";
				  		item_session = item_session + "<th data-field='ageRange'>Age Range</th>";
				  		item_session = item_session + "<th data-field='membershipPrice'>Membership Price</th>";
				  		item_session = item_session + "<th data-field='nonmembershipPrice'>Non membership Price</th>";
				  	 item_session = item_session + "</tr></thead> <tbody>";
				  	
				  	var obj = jQuery.parseJSON(data);
				  	 $.each(obj, function(i,itemDetails) {
				  		    var showtbl = "false"
				  		    if(productId=="" || productId!=itemDetails.productId){
					  			productId = itemDetails.productId;
					  			showtbl = "true";
					  			//item_session = item_session + "<div id='session_detail"+productId+"' style='display:block;'><table class='program_desc' width='90%'>";
				  		    }
				  			
					  		var stTime = new Date(itemDetails.startTime.time);
					  		var endTime = new Date(itemDetails.endTime.time);
					  		
					  		item_session = item_session + "<tr>";
					  			item_session = item_session + "<td><span><input type='hidden' id='itemDetailsSessionId"+itemDetails.id+"' value='"+itemDetails.id+"'><input type='checkbox' name='selectedItemSession' id='item_"+itemDetails.id+"' value='"+itemDetails.id+"'></span></td>";
				  				item_session = item_session + "<td><span class='name'>"+itemDetails.productDesc+"</span></td>";
				  				
				  				item_session = item_session + "<td><span class='name'>"+itemDetails.branchName+"</span></td>";

				  				item_session = item_session + "<td><span class='name'>"+itemDetails.instructorName+"</span></td>";

				  				item_session = item_session + "<td><span class='name'>"+convertJsonDate(itemDetails.startDate)+"</span></td>";
				  				item_session = item_session + "<td><span class='name'>"+convertJsonDate(itemDetails.endDate)+"</span></td>";
				  				item_session = item_session + "<td><span class='name'>"+formatTime(stTime)+"</span></td>";
				  				item_session = item_session + "<td><span class='name'>"+formatTime(endTime)+"</span></td>";
					  		
				  				item_session = item_session + "<td><span class='name'>"+itemDetails.remainingCapacity+"</span>"
				  				if(itemDetails.remainingCapacity==0)
				  					item_session = item_session + "<span> (WL)</span></td>";
				  				
				  				item_session = item_session + "<td><span class='name'>"+itemDetails.minAge + " - " + itemDetails.maxAge+"</span></td>";
				  				item_session = item_session + "<td><span class='name'>"+computePrice(itemDetails.priceArr,'M')+"</span></td>";
				  				item_session = item_session + "<td><span class='name'>"+computePrice(itemDetails.priceArr,'NM')+"</span></td>";
					  		
					  		item_session = item_session + "</tr>";
					  		
					  	if(showtbl=="true"){
					  		
					  	}
				  	 });
				  	item_session = item_session + " </tbody></table><br><br>";

				  	//console.log(item_session);
				  	$("#program_session").html(item_session);
				  	
				  	 //$("#program_cost").text("$"+price+" per person");
				  	// $("input[type='checkbox']").uniform();
				  	 
				  	//$("#add-to-cart").appendTo("#nextbtn");
				  	
				  	$("#add-to-cart").show();
				  	$("#familymembers").hide();
				  	$("#purchase").hide();
				  	$("#checkout_content").fadeOut("fast");
				  	$(".k-loading-mask").hide();
			  	 }
			  	 else{
			  		$("#program_session").html("");
				  	$("#program_cost").text("");
				  	 $("#tcSuccessSpan").css("display", "block");		
					 $("#tcSuccessSpan" ).html("No Programs found for searched query");	
					 $("#tcErrorSpan").css("display", "none");		
					 $( "#tcErrorSpan" ).html("");
					 $("#add-to-cart").hide();
			  	 }
			  	
			  	
			  	$("#program_signup").kendoGrid({
			  		dataSource: {
		            	pageSize: 20
		            	//sort: { field: "Location", dir: "asc" }
					},
		            sortable: true,
		            pageable: true,
		            resizable:true,
		            scrollbar:false
		            /*
		            columns: [
                           { field: "" },
                           { field: "Program Name" },
                           { field: "Location", title: "Location"},
                           { field: "Instructor", title: "Instructor" },
                           { field: "Start Date" },
                           { field: "End Date" },
                           { field: "Start Time", title: "Start Time"},
                           { field: "End Time", title: "End Time"},
                           { field: "Capacity", title: "End Time"},
                           { field: "Membership Price", title: "End Time"},
                           { field: "Non-Membership Price", title: "End Time"}
                    ]*/
				});
			  	
			  	$("input[type='checkbox']").uniform();
			  	$(".k-loading-mask").hide();
			  },
			  error: function( xhr,status,error ){
				  //alert("1" +status+"-"+error);
				  console.log(xhr);
				  $("#tcSuccessSpan").css("display", "none");		
				  $("#tcSuccessSpan" ).html("");	
				  $("#tcErrorSpan").css("display", "block");		
				  $( "#tcErrorSpan" ).html("Error while fetching programs. Please try again after some time");
				  $(".k-loading-mask").hide();
			  }
		});
	}
	
	function showProgramSessionTble(itemId){
		$("#program").html("");
		$("#program").fadeOut();
		$("#programsessiom").fadeIn();
		$("#programDetail").fadeOut();
		$("#session_detail"+itemId).fadeIn();

	}
	
	function populateProducts(){
		/*
		var category = $("#class").val();
		$.ajax({
			  type: "GET",
			  async: false,
			  url:"getProductsbyCategory?category="+category,
			  dataType: "json",
			  success: function( data ) {
			  	 // alert(data);
			  	 
			  	 //$("#subclass").data("kendoDropDownList").dataSource.data(data);
			  	 $('#subclass').data('kendoDropDownList').enable(true);
			  	 //$("#subclass").data("kendoDropDownList").setDataSource("");
			     $("#subclass").data("kendoDropDownList").dataSource.data(""); // clears dataSource
			  	 $("#subclass").data("kendoDropDownList").text(""); // clears visible text
			  	 $("#subclass").data("kendoDropDownList").value(""); // clears invisible value

			  	 $.each(data, function(i,product) {
			  		 //console.log(product);
			  		$("#subclass").data("kendoDropDownList").dataSource.add({ text: product, value: product });
				});
			  	 
			  },
			  error: function( xhr,status,error ){
				  //alert("1" +status);
				  console.log(xhr);
				 
			  }
		});
		
		getProgramSession();
		*/
	}