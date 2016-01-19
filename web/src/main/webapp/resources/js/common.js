$(document).ready(function() {		
	$( "#cart-info" ).click(function(){
		//console.log(" common.cart-info ");
		$("#contactActivityDiv").hide();
		$("#contactTransportDiv").hide();
		$("#authorisedContact").hide();
		gotocartpage();
	});
	
});

var paymentOrderId = Math.floor((Math.random()*1000000000000)+1);
var payment_method_id = 0;

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

function fnGotoFamilyMember(){
	$("#familymembers").show();
	$("#checkout_content").hide();
}

function emptycart(){
	$("#program").fadeIn(100);
	commonSearch();
}

function continueshop(){
	$(".k-loading-mask").show();
	
	$("#checkout_content").fadeOut(200);
	$("#program").fadeIn(100);
	commonSearch();
	//$("#details-checkout").hide();
	//location.href = '#/';
	
	$(".k-loading-mask").hide();
}

	
// search program page

	function rangeSliderOnSlide(e) {
        //kendoConsole.log("Slide :: new slide values are: " + e.value.toString().replace(",", " - "));
    }

    function rangeSliderOnChange(e) {
        //kendoConsole.log("Change :: new values are: " + e.value.toString().replace(",", " - "));
    }
	
    function commonSearch(){
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
    	
		//$("#program_session").hide();
		initGrid();
		search();			
		//$("#program_session").show();
    }
    
    

	
	function showProgramSessionTble(itemId){
		$("#program").html("");
		$("#program").fadeOut();
		$("#programsessiom").fadeIn();
		$("#programDetail").fadeOut();
		$("#session_detail"+itemId).fadeIn();

	}
	
	var source ;
	//var searchGrid ;
	function initGrid(){
		 source = getRemoteData();
		 var div = $("<div id ='searchGrid'></div>");
		 var searchGrid =  div.kendoGrid ( {
			 	dataSource: source,    	
			 	autoBind: false,
				height: 300,
		        filterable: false,
		        sortable: true,
		        pageable: true,
		        resizable: true,
		        columns: getColumns(),
	 			dataBound: function(e) {
	 				var selectSearchResult = $("#selectSearchResult").val();
	 				//alert($("#selectSearchResult").val());
	 				if ($("#selectSearchResult").val() == "true") {
	 					//alert($("#selectSearchResult").val());
	 					$("input[type='checkbox']").prop( "checked", true );	 					
	 				}
	 				$("input[type='checkbox']").uniform();
	 			 }
		 });
		 $("#program_session").html(div);
	}
		
	function getRemoteData() { 
		var pageType = $("#pageType").val();
		pageType =pageType.toLowerCase();
		var pageUrl = "search/"+pageType ;
		source = new kendo.data.DataSource({
	        autoSync: false,
	        transport: {
	            read: {
	                 type: "GET",
	                 url: "search/"+pageType,
	                 data: function() {
	                     return {
	                    	 l : ($("#location").length > 0 ? $("#location").val() : 1),cat :   $("#class").val(),key :$("#subclass").val(),age_min:$("#age_min").val(),
	                    	 age_max:$("#age_max").val(),datestart:$("#datepicker").val(),dateend:$("#datepickerend").val(),
	                    	 ids:$("#itemDetailIds").val()
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
		$("#program_session").show();
		$("#add-to-cart").show();
	  	$("#familymembers").hide();
	  	$("#purchase").hide();
	  	$("#checkout_content").fadeOut("fast");
	  	$(".k-loading-mask").hide();
	  	return true;
	}
	
	function getColumns() {
		var agentLoginFlag = !isLoggedInUserAgent();
		var userLoginFlag = !agentLoginFlag;
		var campType = $("#class").val();
		var hideDayCampFields= false;
		var hideResiCampFields= false;
		var hideLodging= true;
		if (campType == "Day Camp") {
			hideDayCampFields = true ;
		}
		if (campType == "Residence Camp") {
			hideResiCampFields = true ;
		}
		if (campType == "Family Camp") {
			hideLodging = false ;
		}

		var pageType = $("#pageType").val();
		var selectSearchResult = $("#selectSearchResult").val();
		var autoSelect = (selectSearchResult ? "checked=true":"") ;
		//alert(autoSelect);
		var columns = [] ;
		switch(pageType) {
			case 'Camp':
				columns = [ 
				  {field: "id", title: "&nbsp;" ,width: 30,template: '<input type="checkbox" #=checkBoxState# name="selectedItemSession" value="#=id#" /><input type="hidden" name="itemDetailInfo" id="itemDetailInfo_#=id#" value="#=capacity#_#=productName#" />'},
				  {field: "productCategory", title: "Type",hidden:true},
				  {field: "productName", title: "Name",hidden:false},
				  {field: "description", title: "Description",hidden :true},
				  {field: "lodgingType", title: "Lodging",hidden:hideLodging},					 
				  {field: "branchName", title: "Location"},
				  {field: "gender",width:'7%', title: "Gender",hidden:hideDayCampFields},
				  {field: "instructorName", title: "Instructor",hidden:true},
				  {field: "startDate", title: "Start Date",template: "#= kendo.toString(new Date(startDate), 'MM/dd/yyyy') # <br>" + "#= kendo.toString(new Date(startTime), 'hh:mm tt') #" },
				  {field: "endDate", title: "End Date",template: "#= kendo.toString(new Date(endDate), 'MM/dd/yyyy') # <br>" + "#= kendo.toString(new Date(endTime), 'hh:mm tt') #" },
				  {field: "age", width:'7%',title: "Age Range",template: "#= minAge # - " + "#= maxAge #"},
				  {field: "capacity", title: "Capacity", hidden : userLoginFlag},
				  {field: "actualRemainingCapacity", title: "Capacity", hidden : agentLoginFlag},
				  {field: "capacity",width:'7%', title: "Web Capacity", hidden : agentLoginFlag},				  				  
				  {field: "memberprice", title: "Non Member / <br>Member Price",template: '<label>#= nonmembertierPrice #'+ '/' + ' #= memberprice #</label>'}
				]
				break;
			case 'Event':
				columns = [ 
				  {field: "id", title: "&nbsp;" ,width: 30,template: '<input type="checkbox" #=checkBoxState# name="selectedItemSession" value="#=id#" /><input type="hidden" name="itemDetailInfo" id="itemDetailInfo_#=id#" value="#=capacity#_#=productName#" />'},
				  {field: "productName", title: "Name"},
				  {field: "branchName", title: "Location"},
				  {field: "instructorName", title: "Instructor"},
				  {field: "startDate", title: "Start Date",template: "#= kendo.toString(new Date(startDate), 'MM/dd/yyyy') #" },
				  {field: "endDate", title: "End Date",template: "#= kendo.toString(new Date(endDate), 'MM/dd/yyyy') #" },
				  {field: "startTime", title: "Start Time",template: "#= kendo.toString(new Date(startTime), 'hh:mm tt') #" },
				  {field: "endTime", title: "End Time",template: "#= kendo.toString(new Date(endTime), 'hh:mm tt') #" },
				  {field: "age", title: "Age Range",template: "#= minAge # - " + "#= maxAge #"},
				  {field: "capacity", title: "Capacity", hidden : userLoginFlag},
				  {field: "actualRemainingCapacity", title: "Capacity", hidden : agentLoginFlag},
				  {field: "capacity", title: "Web Capacity", hidden : agentLoginFlag},
				  {field: "memberprice", title: "Member Price"},
				  {field: "nonmembertierPrice", title: "Non Member Price"}
				]
				break;
			case 'Facility':
				columns = [ 
				  {field: "id", title: "&nbsp;" ,width: 30,template: '<input type="checkbox" name="selectedItemSession" value="#=id#" />'},
				  {field: "productName", title: "Name"},
				  {field: "branchName", title: "Location"},
				  {field: "startDate", title: "Start Date",template: "#= kendo.toString(new Date(startDate), 'MM/dd/yyyy') #" },
				  {field: "endDate", title: "End Date",template: "#= kendo.toString(new Date(endDate), 'MM/dd/yyyy') #" },
				  {field: "startTime", title: "Start Time",template: "#= kendo.toString(new Date(startTime), 'hh:mm tt') #" },
				  {field: "endTime", title: "End Time",template: "#= kendo.toString(new Date(endTime), 'hh:mm tt') #" },
				  {field: "capacity", title: "Capacity", hidden : userLoginFlag},
				  {field: "actualRemainingCapacity", title: "Capacity", hidden : agentLoginFlag},
				  {field: "capacity", title: "Web Capacity", hidden : agentLoginFlag},
				  {field: "memberprice", title: "Member Price"},
				  {field: "nonmembertierPrice", title: "Non Member Price"}
				]
				break;				
			default:
				columns = [ 
				  {field: "id", title: "&nbsp;" ,width: 30,template: '<input type="checkbox" #=checkBoxState# name="selectedItemSession" value="#=id#" /><input type="hidden" name="itemDetailInfo" id="itemDetailInfo_#=id#" value="#=capacity#_#=productName#" />'},
				  {field: "productName", title: "Name"},
				  {field: "branchName", title: "Location"},
				  {field: "instructorName", title: "Instructor"},
				  {field: "startDate", title: "Start Date",template: "#= kendo.toString(new Date(startDate), 'MM/dd/yyyy') #" },
				  {field: "endDate", title: "End Date",template: "#= kendo.toString(new Date(endDate), 'MM/dd/yyyy') #" },
				  {field: "startTime", title: "Start Time",template: "#= kendo.toString(new Date(startTime), 'hh:mm tt') #" },
				  {field: "endTime", title: "End Time",template: "#= kendo.toString(new Date(endTime), 'hh:mm tt') #" },
				  {field: "age", title: "Age Range",template: "#= minAge # - " + "#= maxAge #"},
				  {field: "capacity", title: "Capacity", hidden : userLoginFlag},
				  {field: "actualRemainingCapacity", title: "Capacity", hidden : agentLoginFlag},
				  {field: "capacity", title: "Web Capacity", hidden : agentLoginFlag},
				  {field: "memberprice", title: "Member Price"},
				  {field: "nonmemberprice", title: "Non Member Price"}
				]
				break;
			}
		return columns ;
	}
		
	
	