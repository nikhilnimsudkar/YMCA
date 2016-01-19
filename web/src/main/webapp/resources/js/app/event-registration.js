/* Event registration related functions */
$(document).ready(function() {
	$( "#cart-info" ).click(function(){
		$("#event_details").css("border-width", "1px");	
		$("#event_details").css("margin-top", "20px");	
		 
		$("#tcSuccessSpan").css("display", "none");		
		$("#tcSuccessSpan" ).html("");	
		$("#tcErrorSpan").css("display", "none");		
		$( "#tcErrorSpan" ).html("");
		 
		$("#event").hide();
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
	});
});

function getEventSession(){
	//alert(" get event session ");
	//console.log(" get event session ");
	$("#event_details").css("border-width", "0");	
	$("#event_details").css("margin-top", "9px");
	$("#contactHealthHistoryDiv").hide();
	var category = $("#class").val();
	var productname = $("#subclass").val();
	var location = ''; //$("#location").val();
	var age_min = $("#age_min").val();
	var age_max = $("#age_max").val();
	
	var datestart = $("#datepicker").val();
	var dateend = $("#datepickerend").val();
	
	// Set filter in session
	$.sessionStorage.setItem("search_event_filter", JSON.stringify( { _category: category, _productname: productname, _location: location,
																		_age_min: age_min, _age_max: age_max, 
																		_datestart: datestart, _dateend: dateend } ));
	
	$.ajax({
		  type: "GET",
		  url:"getEventDetails?strLocation="+location+"&productname="+productname+"&category="+category+"&minAgeStr="+age_min+"&maxAgeStr="+age_max+"&datestart="+datestart+"&dateend="+dateend,
		  success: function( data ) {
			 // console.log(data);
		  	 // console.log("  ==> "+data.length);
			  if(data.length>0){
		  		 var itemDetailsId = "";
			  	 var price = "";
			  	 var days = "";
			  	 var dspDays = "";
			  	 var productId = "";
			  	 var item_session = "";
			  	 //var item_session = "<div style='margin-bottom: 20px;'><span class='head'>NEW PROGRAM SIGN UP</span><span id='shoppingcart1' style='float:right;'></span></div>";
			  	 item_session = item_session + "<table id='event_signup' class='program_desc' style='table-layout: fixed; width: 782px;'>";
			  	 item_session = item_session + "<colgroup>";
				  	 item_session = item_session + "<col style='width: 30px' />";
				  	 item_session = item_session + "<col style='width: 80px' />";
				  	 item_session = item_session + "<col style='width: 85px' />";
				  	 item_session = item_session + "<col style='width: 62px;'>";
				  	 item_session = item_session + "<col style='width: 86px;' />";
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
			  		item_session = item_session + "<th data-field='programName'>Event Name</th>";
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
//			  		console.log(''+itemDetails);
			  		console.log(' itemDetails.id >>> '+itemDetails.id);
			  		
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
				  		
			  				item_session = item_session + "<td><span class='name'>"+itemDetails.remainingCapacity+"</span></td>";
			  				item_session = item_session + "<td><span class='name'>"+itemDetails.minAge + " - " + itemDetails.maxAge+"</span></td>";

			  				item_session = item_session + "<td><span class='name'>"+computePrice(itemDetails.priceArr,'M')+"</span></td>";
			  				item_session = item_session + "<td><span class='name'>"+computePrice(itemDetails.priceArr,'NM')+"</span></td>";
				  		
				  		item_session = item_session + "</tr>";
				  		
				  	if(showtbl=="true"){
				  		
				  	}
			  	 });
			  	item_session = item_session + " </tbody></table><br><br>";

			  	//console.log(item_session);
			  	$("#event_session").html(item_session);
			  	
			  	 //$("#program_cost").text("$"+price+" per person");
			  	// $("input[type='checkbox']").uniform();
			  	 
			  	//$("#add-to-cart").appendTo("#nextbtn");
			  	
			  	$("#add-to-cart").show();
			  	$("#familymembers").hide();
			  	$("#purchase").hide();
			  	$("#checkout_content").fadeOut("fast");
			  	$(".k-loading-mask").hide();
		  	}else{
		  		 $("#event_session").html("");
			  	 $("#program_cost").text("");
			  	 $("#tcSuccessSpan").css("display", "block");		
				 $("#tcSuccessSpan" ).html("No Events found for searched query");	
				 $("#tcErrorSpan").css("display", "none");		
				 $( "#tcErrorSpan" ).html("");
				 $("#add-to-cart").hide();
		  	}
		  	
		  	$("#event_signup").kendoGrid({
		  		dataSource: {
	            	pageSize: 20
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

function rangeSliderOnSlide(e) {
    //kendoConsole.log("Slide :: new slide values are: " + e.value.toString().replace(",", " - "));
}

function rangeSliderOnChange(e) {
    //kendoConsole.log("Change :: new values are: " + e.value.toString().replace(",", " - "));
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

function fnGotoFamilyMember(){
	$("#familymembers").show();
	$("#checkout_content").hide();
}

function continueshop(){
	$(".k-loading-mask").show();
	
	$("#checkout_content").fadeOut(200);
	$("#event").fadeIn(100);
	getEventSession();
	//$("#details-checkout").hide();
	//location.href = '#/';
	
	$(".k-loading-mask").hide();
}

function emptycart(){
	$("#event").fadeIn(100);
	getEventSession();
}
