$(document).ready(function() {		
	$( "#cart-info" ).click(function(){
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
		
		location.href = '#/checkout';
		$("#backtofamily").hide();
		setTimeout(function(){
			if(cartPreviewModel.totalPrice()=='$0.00'){
				$("#promo").hide();
			}else{
				$("#promo").show();
			}
		}, 500);
	});
	
});

var paymentOrderId = Math.floor((Math.random()*1000000000000)+1);

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
	getCampSession();
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
	
	function getCampSession(){
		$("#program_details").css("border-width", "0");	
		$("#program_details").css("margin-top", "9px");	
		
		var category = $("#type").val();
		var productname = $("#type").val();
		var location = $("#location").val();
		var type = $("#type").val();
		var age_min = $("#age_min").val();
		var age_max = $("#age_max").val();
		
		var datestart = $("#datepicker").val();
		var dateend = $("#datepickerend").val();
		var keyword = $("#keyword").val();
		//alert("category="+category+"type="+type+" age_min="+age_min+" age_max="+age_max+" datestart="+datestart+" dateend="+dateend+" keyword="+keyword+" location="+location);
		
		// Set filter in session
		$.sessionStorage.setItem("search_camp_filter", JSON.stringify( { _category: category, _productname: productname, _location: location,
																			_age_min: age_min, _age_max: age_max, 
																			_datestart: datestart, _dateend: dateend } ));
		
         $.ajax({
			  type: "GET",
			  url:"camp/search?location="+location+"&category="+category+"&keyword="+keyword+"&age_min="+age_min+"&age_max="+age_max+"&datestart="+datestart+"&dateend="+dateend,
			  success: function( data ) {
			  	 if(data.length>0){
			  		 var itemDetailsId = "";
				  	 var price = "";
				  	 var days = "";
				  	 var dspDays = "";
				  	 
				  	 var productId = "";
				  	 var item_session = "";
				  	 //alert("change4");
				  	 //var item_session = "<div style='margin-bottom: 20px;'><span class='head'>NEW PROGRAM SIGN UP</span><span id='shoppingcart1' style='float:right;'></span></div>";
				  	 item_session = item_session + "<table id='program_signup' class='camp_desc' style='table-layout: auto; width: 1100px;'>";
				  	 item_session = item_session + "<colgroup>";
					  	 item_session = item_session + "<col style='width: 60px' />";
					  	 item_session = item_session + "<col style='width: 81px' />";
					  	 item_session = item_session + "<col style='width: 54px' />";
					  	 item_session = item_session + "<col style='width: 90px;'>";
					  	 item_session = item_session + "<col style='width: 110px' />";
					  	 item_session = item_session + "<col style='width: 86px;' />";
					  	 item_session = item_session + "<col style='width: 84px;' />";
					  	 item_session = item_session + "<col style='width: 70px;' />";
					  	 item_session = item_session + "<col style='width: 80px;' />";
					  	 item_session = item_session + "<col style='width: 90px;' />";
					  	 item_session = item_session + "<col style='width: 80px;' />";
					  	 item_session = item_session + "<col style='width: 90px;' />";
					  	 item_session = item_session + "<col style='width: 80px;' />";
					  	item_session = item_session + "<col style='width: 80px;' />";
					  	item_session = item_session + "<col style='width: 80px;' />";
					  	item_session = item_session + "<col style='width: 80px;' />";
			         item_session = item_session + "</colgroup>";
				  	 item_session = item_session + "<thead><tr>";
				  		item_session = item_session + "<th data-field='randomFieldName'>&nbsp;</th>";
				  		item_session = item_session + "<th data-field='type'>Type</th>";
				  		item_session = item_session + "<th data-field='campName'>Camp Name</th>";
				  		item_session = item_session + "<th data-field='type'>Description</th>";
				  		item_session = item_session + "<th data-field='type'>Loge</th>";
				  		item_session = item_session + "<th data-field='gender'>Gender</th>";
				  		item_session = item_session + "<th data-field='location'>Location</th>";
				  		item_session = item_session + "<th data-field='location'>Instructor</th>";
				  		item_session = item_session + "<th data-field='startDate'>Start Date</th>";
				  		item_session = item_session + "<th data-field='endDate'>End Date</th>";
				  		item_session = item_session + "<th data-field='startDate'>Start Time</th>";
				  		item_session = item_session + "<th data-field='endDate'>End Time</th>";				  		
				  		item_session = item_session + "<th data-field='ageRange'>Age Range</th>";
				  		item_session = item_session + "<th data-field='remainingslots'>Capacity</th>";
				  		item_session = item_session + "<th data-field='signup'>MemberShip Price</th>";
				  		item_session = item_session + "<th data-field='signup'>Non MemberShip Price</th>";
				  	 item_session = item_session + "</tr></thead> <tbody>";
				  	//alert("headers set");
				  	var obj = data;
				  	//alert("after parsing="+obj);
				  	 $.each(obj, function(i,itemDetails) {
				  		console.log(itemDetails);
				  		console.log(itemDetails.id);
				  		
				  		var showtbl = "false"
				  		if(productId=="" || productId!=itemDetails.productId){
				  			productId = itemDetails.productId;
				  			showtbl = "true";
				  			//item_session = item_session + "<div id='session_detail"+productId+"' style='display:block;'><table class='program_desc' width='90%'>";
				  			
				  		}
				  		//alert("setting time");
				  			
					  		var stTime = new Date(itemDetails.startTime.time);
					  		var endTime = new Date(itemDetails.endTime.time);
					  		//alert("after setting time");
					  		
					  		item_session = item_session + "<tr>";
					  			item_session = item_session + "<td><span><input type='hidden' id='itemDetailsSessionId"+itemDetails.id+"' value='"+itemDetails.id+"'><input type='checkbox' name='selectedItemSession' id='item_"+itemDetails.id+"' value='"+itemDetails.id+"'></span></td>";
				  				item_session = item_session + "<td><span class='name'>"+itemDetails.productCategory+"</span></td>";
					  			item_session = item_session + "<td><span class='name'>"+itemDetails.productName+"</span></td>";
				  				item_session = item_session + "<td><span class='name'>"+itemDetails.description+"</span></td>";
				  				
				  				// camp related fields
				  				item_session = item_session + "<td><span class='name'>"+itemDetails.lodgingType+"</span></td>";
				  				item_session = item_session + "<td><span class='name'>"+itemDetails.gender+"</span></td>";
				  				
				  				item_session = item_session + "<td><span class='name'>"+itemDetails.branchName+"</span></td>";

				  				item_session = item_session + "<td><span class='name'>"+ itemDetails.instructorName+"</span></td>";
				  				
				  				item_session = item_session + "<td><span class='name'>"+ itemDetails.startDate+"</span></td>";
				  				item_session = item_session + "<td><span class='name'>"+ itemDetails.endDate+"</span></td>";
				  				
				  				item_session = item_session + "<td><span class='name'>"+ itemDetails.startTime+"</span></td>";
				  				item_session = item_session + "<td><span class='name'>"+ itemDetails.endTime+"</span></td>";
				  				
				  				item_session = item_session + "<td><span class='name'>"+itemDetails.capacity+"</span></td>";
				  				item_session = item_session + "<td><span class='name'>"+ itemDetails.minAge+ "-" + itemDetails.maxAge +"</span></td>";

				  				item_session = item_session + "<td><span class='name'>$"+itemDetails.memberprice+"</span></td>";
				  				item_session = item_session + "<td><span class='name'>$"+itemDetails.nonmembertierPrice+"</span></td>";
					  		
					  		item_session = item_session + "</tr>";					  		
					  	if(showtbl=="true"){
					  		
					  	}
				  	 });
				  	item_session = item_session + " </tbody></table><br><br>";

				  	//console.log(item_session);
				  	$("#program_session").html(item_session);
				  	
				  	
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
					 $("#tcSuccessSpan" ).html("No Camps found for searched query");	
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

				});
			  	
			  	$("input[type='checkbox']").uniform();
			  	$(".k-loading-mask").hide();
			  },
			  error: function( xhr,status,error ){
				  alert("1" +status+"-"+error);
				  console.log(xhr);
				 
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
	
	var source ;  
	function initGrid(){
		 source = getRemoteData();
		 $("#program_signup").kendoGrid ( {
			 	dataSource: source,    	
			 	autoBind: false,
				height: 300,
		        filterable: false,
		        sortable: true,
		        pageable: true,
		        resizable: true,
		        columns: 
					[ 
					  {field: "id", title: "&nbsp;" ,width: 30,template: '<input type="checkbox" name="selectedItemSession" value="#=id#" />'},
					  {field: "productCategory", title: "Type"},
					  {field: "productName", title: "Name",hidden:true},
	 				  {field: "description", title: "Description",hidden :true},
					  {field: "lodgingType", title: "Lodging"},					 
					  {field: "branchName", title: "Location"},
					  {field: "gender", title: "Gender"},
					  {field: "instructorName", title: "Instructor"},
					  {field: "startDate", title: "Start Date",template: "#= kendo.toString(new Date(startDate), 'MM/dd/yyyy') #" },
					  {field: "endDate", title: "End Date",template: "#= kendo.toString(new Date(endDate), 'MM/dd/yyyy') #" },
					  {field: "startTime", title: "Start Time",template: "#= kendo.toString(new Date(startTime), 'hh:mm tt') #" },
					  {field: "endTime", title: "End Time",template: "#= kendo.toString(new Date(endTime), 'hh:mm tt') #" },
					  {field: "age", title: "Age Range"},
					  {field: "capacity", title: "Capacity"},
					  {field: "memberprice", title: "Member Price"},
					  {field: "nonmemberprice", title: "Non Member Price"}
	 				]
		 });
		setTimeout(1000,function () {$("input[type='checkbox']").uniform()});
	}
		
	function getRemoteData() { 
		source = new kendo.data.DataSource({
	        autoSync: false,
	        transport: {
	            read: {
	                type: "GET",
	                url: "camp/search",
	                 data: function() {
	                     return {
	                    	 location : $("#location").val(),category :   $("#type").val(),keyword :$("#keyword").val(),age_min:$("#age_min").val(),age_max:$("#age_max").val(),datestart:$("#datepicker").val(),dateend:$("#datepickerend").val()
	                     }
	                 },
	                dataType: "json",                
	                cache: false
	            }    	            
	        },
	        pageSize: 20
	    });
	    return source;
	}
			 
	function search(){
		source.read();
		$("#add-to-cart").show();
	  	$("#familymembers").hide();
	  	$("#purchase").hide();
	  	$("#checkout_content").fadeOut("fast");
	  	$(".k-loading-mask").hide();
	  	return true;
	}