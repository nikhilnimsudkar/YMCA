<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%
    	String contextPath = request.getContextPath();
    
    %>
<head>
    <%-- <meta name="_csrf" content="${_csrf.token}"/>
    
    <meta name="_csrf_header" content="${_csrf.headerName}"/> --%>
<link rel="stylesheet" href="<%=contextPath %>/resources/css/style1.css" type="text/css" media="screen"/>
<script src="<%=contextPath %>/resources/js/jquery.validate.min.js"></script>
<script src="<%=contextPath %>/resources/js/maskedinput.min.js"></script>
<script src="<%=contextPath %>/resources/js/additional-methods.min.js"></script>
</head>   
<%@ include file="../../layouts/include_taglib.jsp"%>
<div class="k-window1" id="popup_ChckinMember" style="display: none;"></div>
<div class="k-window1" id="popup_add" style="display: none;"></div>

<div ><div class="border-bottom-h3">
				            	<span id="locSelMsg" style="margin-right: 140px; color: red;"> Please select YMCA location to search a customer</span>
				           
							<select name="location" id="location" style="width:250px; margin-right: 150px;" ><br />
          <option value="0">--Select Location--</option>
          <c:forEach var="location" items="${locations}" varStatus="count">
           <c:if test="${ location.recordName != 'All Branches' && location.recordName != 'Bay Area' && location.branch == 'true' }">
            <option value="${location.id }">${location.recordName }</option>
           </c:if>
          </c:forEach>
       </select> </div></div>

<div  class="k-block" style="background-color: rgb(255, 255, 255);margin-bottom: 10px; ">
<table id="serchCustomerTable" cellpadding="0" cellspacing="0" width="99%"  rules="none" style="padding-left:4px;padding-top:4px;">
	
		<tr ><span class="head" width="100%" style="margin-left:4px;SERACH">SEARCH CUSTOMER </span></tr>
		 <tr>&nbsp;</tr>
		<tr>
		  <td style="padding-bottom:3px;padding-left:4px;"><input type="text" placeholder="Enter First Name" id="fname" name="fname"></td>
		  <td style="padding-bottom:3px;"><input type="text" placeholder="Enter Last Name" id="lname" name="lname"></td>
		 <td style="padding-bottom:3px;padding-left:4px;"><input type="text" placeholder="Enter Email" id="email" name="email"></td>
		 <td style="padding-bottom:3px;padding-left:4px;"><input type="text" placeholder="Enter Phone Number" id="MobNo" name="MobNo"></td>
		 <td style="padding-bottom:3px;padding-left:4px;"><input type="text" placeholder="Enter MembershipId" id="memId" name="memId"></td>
		 </tr> 
		 <tr>
		  <td style="padding-bottom:3px;padding-left:4px;"></td>
		  <td style="padding-bottom:3px;padding-left:4px;"></td>
		  <td style="padding-bottom:3px;" align="center"><span id="serchUser" class="k-button" onclick="searchUser()" style="text-shadow: none; width: 81px; margin-top: 4px; margin-left: -50px;">SEARCH</span></td>
		  <td style="padding-bottom:3px;"></td>
		 </tr> 
	</table>
</div>
<div id="kgrid"  ></div>



<script type="text/javascript">
$(document).ready(function() {
	$("#dateOfBirth").kendoDatePicker();
	$("#dateOfBirthAdd").kendoDatePicker();
	$("#top_profile").attr('class', 'current');
	$("#top_login").attr('class', '');
	$("#phoneNumber").mask("(999) 999-9999");
	$("#kgrid").hide();
	$("#serchCustomerTable").hide();
	$("#memId").focus();
	$("#tabstrip").kendoTabStrip({
		animation : {
			open : {
				effects : "fadeIn"
			}
		}
	});

	$("#tabstrip1").kendoTabStrip({
		animation : {
			open : {
				effects : "fadeIn"
			}
		}
	});
	$("#tabstrip2").kendoTabStrip({
		animation : {
			open : {
				effects : "fadeIn"
			}
		}
	});

	 /* $('.scroller').slimScroll({
		height : '110px',
		width : '160px',
		alwaysVisible : true,
		color : '#666666',
		distance : '4px',
		railVisible : true,
		allowPageScroll : true
	}).parent().css({
		border : '1px solid #cccccc'
	});  */
	
});	


//-------location select-----------
$("#location").kendoDropDownList(); 
var locationDropdownlist = $("#location").data("kendoDropDownList");
locationDropdownlist.select(0);
$("#location").on('change',function(e){  
    if(locationDropdownlist.text() != '--Select Location--'){
  //  alert("{location change}");
  	$("#locSelMsg").hide();
    $("#kgrid").show();
	$("#serchCustomerTable").show();
    }else if(locationDropdownlist.text() == '--Select Location--'){
       // alert("{location change to first pos}");
        $("#kgrid").hide();
    	$("#serchCustomerTable").hide();
    	$("#locSelMsg").show();
        }

    $("#memId").focus();
});


$("#memId").on('change',function(e){  
	var mId = $.trim($("#memId").val());
	if (mId != '') {
		mId = mId.substring(1);
		display_CheckInWindow(mId);	
	}
});


var source ;  
function initGrid(){
	 source = getRemoteData();
	 $("#kgrid").kendoGrid ( {
		 	dataSource: source,    	
		 	autoBind: false,
			height: 300,
	        filterable: false,
	        sortable: true,
	        pageable: true,
	        resizable: true,
	        columns: 
				[ {field: "customerName", title: "Customer Name" ,template: "<a href='\\#' class='link' onclick='display_CheckInWindow(#=customerId#,null)'>#=customerName# </a>"},
				  {field: "FirstName", title: "First Name"},
				 {field: "LastName", title: "Last  Name"},
 				 {field: "EmailAddress", title: "Email id "},
				 {field: "FormattedPhoneNumber", title: "Phone No "},					 
				 {field: "DateOfBirth", title: "Date Of Birth"} 				 
 				]
	 });
}
	
function getRemoteData() { 
	source = new kendo.data.DataSource({
        autoSync: false,
        transport: {
            read: {
                type: "GET",
                url: "getUsersBySearchCriteria",
                //data: {"fName" : $("#fname").val(),"lName" :  $("#lname").val(),"eMail" : $("#email").val()},
                 data: function() {
                     return {
                    	 fName : $("#fname").val(),lName :  $("#lname").val(),eMail : $("#email").val(),phno : $("#MobNo").val()
                     }
                 },
                dataType: "json",                
                cache: false
            }    	            
        },
        pageSize: 5
    });
    return source;
}
		 
function searchUser(){
	source.read();
	return true;
}
$(document).ready(function() { 
	initGrid();
});



//for displyaing popup Checkin Window

function display_CheckInWindow(customerId,mId) {
	//alert("u clik an user button");	
	//alert(customerId);
	var location= $('#location').val();
	//alert(loction);
	var url = "YAgentConsoleMembershipSearch?customerId="+customerId+"&location="+location ;
	if (mId != null) {
		url = url + "&mId="+mId;
	}
	var window_add = $("#popup_ChckinMember");
	window_add.clone().kendoWindow({
		title : "Check-In Member",
		content : url,
		modal : false,
		width : "1150px",
		height : "650px",
		close : onClose,
		deactivate : function(e) {
			this.destroy();
		}
	}).data("kendoWindow").center().open();
	$('[data-role="draggable"]').css('top', '20px');
}
var onClose = function() {
	this.destroy();   
};


//Primary member checkin
function primaryMbershipCheck(memberContactId){
		//alert("Membershipid contact id primary meber");
		//alert(memberContactId);
		//$("#checkPriMemship").hide();
		// document.getElementById("tabstrip3").style.backgroundColor = "red";
		// $("#accDeny").show();
		//$("#m_id").val(12);
		var location= $('#location').val();
		alert(location);
		var totalchkedincount=$("#totalChkdInCountHiden"+memberContactId).val();
		 if (memberContactId != '') {
			
			 $.ajax({
					url : "checkIn_PrimaryMember?memberContactId=" + memberContactId+"&location="+location,
					success : function(data) {
						//alert(data);
						 if(data.indexOf('GRANTED') > -1){
							// alert("granted");
							 $("#membershipChikin"+memberContactId).show();
							// $("#checkPriMemship").hide();
							 $("#checkinMember"+memberContactId).hide();
							// document.getElementById("tabstrip3"+memberContactId).style.backgroundColor = "green";
							 $("#tabstrip3"+memberContactId).css('background-color', 'green');
							 $("#allowGuest").show();
							 $("#accGranted"+memberContactId).html("<h3  align='center'> ACCESS "+ data + "...!! </h3>");
							 $("#accGranted"+memberContactId).show();
							 totalchkedincount=parseInt(totalchkedincount)+1;
							 $( "#totalChkdCountOld"+memberContactId ).replaceWith( "<div>Checked-In Count:"+totalchkedincount+"</div>" );
							 $("#lastChkedInDate").replaceWith("<div>Last Checked-In Date:"+ kendo.toString(new Date(), "d")+"</div>");
						 }else if(data=='DENY')
							 {
							 
							 $("#membershipChikin"+memberContactId).show();
							// $("#checkPriMemship").hide();
							 $("#checkinMember"+memberContactId).hide();
							// document.getElementById("tabstrip3"+memberContactId).style.backgroundColor = "red";
							$("#tabstrip3"+memberContactId).css('background-color', 'red');
							 $("#accDeny"+memberContactId).show();
							 $("#unAuthAccess"+memberContactId).show();
							 
							 $("#allowGuest").show();
							 }
						
					}
				});
		} 
	}
	


//Family Member Checkin

function membercheckin(memberContactId){
	//alert("Membershipid contact id");
	//alert(memberContactId);
	//$("#m_id").val(12);
	var sexOffenderRed=$("#sexOffenderRed"+memberContactId).val();
	//alert(sexOffenderRed);
	 $("#sexOffResonListSecMem"+memberContactId).kendoDropDownList(); 

/* var SexOffenderReasonSecMemDropdownlist = $("#sexOffResonListSecMem"+memberContactId).data("kendoDropDownList");
SexOffenderReasonSecMemDropdownlist.select(0);	 
 */

	var location= $('#location').val();
	if (memberContactId != '') {
		
		$.ajax({
			url : "checkIn_member?memberContactId=" + memberContactId+"&location="+location,
			success : function(data) {
				var jsonDataArr = jQuery.parseJSON(data);
				if(jsonDataArr != null && jsonDataArr.length > 0)
				{
				// if(data=='SUCCESS'){
					if(jsonDataArr[0] != null )
					{
							 $("#checkinMember"+memberContactId).hide();
							// $("#chkdmem"+memberContactId).show();
							// $("#membershipChikin").hide();
							
							 $("#Add_member_profile"+memberContactId).show();
							 
							 $("#secMemLocation"+memberContactId).html(jsonDataArr[0].secUserLoc);
							 //$("#signupMemShip"+memberContactId).html(jsonDataArr[0].signupMemShipName);
							 if(jsonDataArr[0].Success != null && jsonDataArr[0].Success =='Success'){
								$("#tabstrip3"+memberContactId).css('background-color', 'green');
								$("#secMemChkdInDate"+memberContactId).html("Last Checked-In dat:"+ kendo.toString(new Date(), "d"));
								// $("#AccessGranted"+memberContactId).html(jsonDataArr[0].accessGranted);
								 $("#accGranted"+memberContactId).html(jsonDataArr[0].accessGranted);
								 $("#accGranted"+memberContactId).show();
								 $("#totalChkdInCountHiden"+memberContactId).val(jsonDataArr[0].ChkedIncount);
								   $("#secMemChkdInCount"+memberContactId).html("Checked-In Count:"+jsonDataArr[0].ChkedIncount);
								  
								   $("#secMemChkdInDate"+memberContactId).html(kendo.toString(jsonDataArr[0].secLastChkedInDate, "d"));
							 }else if(jsonDataArr[0].SexOffender != null && jsonDataArr[0].SexOffender =='SexOffender') {
								$("#tabstrip3"+memberContactId).css('background-color', 'red');
								$("#accDeny"+memberContactId).show();
								$("#accDenySexOff"+memberContactId).show();
								$("#unAuthAccess"+memberContactId).show();
								//$("#sexOffResonDivSec"+memberContactId).show();
								$("#totalChkdInCountHiden"+memberContactId).val(jsonDataArr[0].ChkedIncount);
								   $("#secMemChkdInCount"+memberContactId).html("Checked-In Count:"+jsonDataArr[0].ChkedIncount);
								  
								   $("#secMemChkdInDate"+memberContactId).html(kendo.toString(jsonDataArr[0].secLastChkedInDate, "d"));
								
							 }
							 else{
								$("#accDeny"+memberContactId).show();
								$("#unAuthAccess"+memberContactId).show();
							 
							  
							 
							  $("#totalChkdInCountHiden"+memberContactId).val(jsonDataArr[0].ChkedIncount);
							   $("#secMemChkdInCount"+memberContactId).html("Checked-In Count:"+jsonDataArr[0].ChkedIncount);
							  
							   $("#secMemChkdInDate"+memberContactId).html(kendo.toString(jsonDataArr[0].secLastChkedInDate, "d"));
							 }
					 }
				}

			}
		});
	}
}








// Grant Unauthorized Access-----------------------------------------------------------

function unAuthAccess(memberContactId){
		
		var totalchkedincount=$("#totalChkdInCountHiden"+memberContactId).val();
		var unAuthAccCount=$("#unAuthAccCountHidden"+memberContactId).val();
		if(unAuthAccCount==null || unAuthAccCount=="null" || unAuthAccCount==""){
			unAuthAccCount=0;	
		} 
		var location= $('#location').val();
		var sexOffReason=$("#sexOffResonList"+memberContactId).text();
	//	alert(secMemSexOffReason)
		/* var secMemSexOffReason=$("#sexOffResonListSecMem"+memberContactId).text();
		if(secMemSexOffReason!=null){
		sexOffReason=secMemSexOffReason;
		//alert(secMemSexOffReason)
		} */
		 if (memberContactId != '') {
			
			 $.ajax({
					url : "allowUnAuthorisedAccess?memberContactId=" + memberContactId+"&location="+location+"&sexOffReason="+sexOffReason,
					success : function(data) {
						//alert(data);
						 if(data=='GRANTED'){
							// alert("granted");
							// $("#checkPriMemship").hide();
							 $("#checkinMember"+memberContactId).hide();
							// document.getElementById("tabstrip3"+memberContactId).style.backgroundColor = "green";
							 $("#tabstrip3"+memberContactId).css('background-color', 'green');
							 $("#unAuthAccess"+memberContactId).hide();
							 $("#unAuthAccButton"+memberContactId).hide();
							 $("#accGranted"+memberContactId).show();
							 $("#accDeny"+memberContactId).hide();
							 $("#inactiveMembership").hide();
							 $("#unAuthMsg").show();
							 unAuthAccCount=parseInt(unAuthAccCount)+1;
							 $( "#unAuthAccCountOld"+memberContactId ).replaceWith( "<p style='font-size: 11px;'>Unauthorized Access Count:"+unAuthAccCount+"</div>" );
							 totalchkedincount=parseInt(totalchkedincount)+1;
							 $( "#totalChkdCountOld"+memberContactId ).replaceWith( "<div>Checked-In Count:"+totalchkedincount+"</div>" );
							 $("#lastChkedInDate"+memberContactId).replaceWith("<div>Last Checked-In Date:"+kendo.toString(new Date(), "d")+"</div>");
							 $("#secMemChkdInCount"+memberContactId).hide();
							 //$("#signupMemShip"+memberContactId).html(jsonDataArr[0].signupMemShipName);
						 }else if(data=='DENY')
							 {
							 $("#unAuthAccess"+memberContactId).hide();
							// $("#checkPriMemship").hide();
							// document.getElementById("tabstrip3"++memberContactId).style.backgroundColor = "red";
							 $("#tabstrip3"+memberContactId).css('background-color', 'red');
							 $("#accDeny"+memberContactId).show();
							 $("#unAuthAccButton"+memberContactId).hide();
							 $("#unAuthMSgDenied").show();
							 //$("#sexOffResonDiv"+memberContactId).hide();
							 $("#sexOffResonDiv"+memberContactId).hide();
							// $("#sexOffResonDivSec"+memberContactId).hide();
							 //$("#signupMemShip"+memberContactId).html(jsonDataArr[0].signupMemShipName);
							// $("#secMemChkdInCount"+memberContactId).hide();
							 }
						
					}
				});
		} 
	}
	
	
	
	
	
	// Add Guest----------------------------------------------------
	
	
	function add_Guest() {
	var window_add1 = $("#popup_add",window.parent);
	window_add1.kendoWindow({
		title : "Add Guest Contact",
		content : "addGuest",
		modal : false,
		width : "400px",
		close : onClose,
		deactivate : function(e) {
			this.destroy();
		}
	}).data("kendoWindow").center().open();
	$('[data-role="draggable"]').css('top', '20px');
}
var onClose = function() {
	//this.destroy();   
};


	
	$(document).on('click', '#addGuestBtn', function(){	
	//alert("u clik on addguest button serch pg ");
	var primContId=$("#primContId").val();
//alert("serchpg"+primContId);
		var url = "isEmailExists";
		
		var dobMonth = $("#dobMonth").val();
		var dobDate = $("#dobDate").val();
		var dobYear = $("#dobYear").val();
		
		var dob = new Date(dobYear,dobMonth,dobDate);
		var today = new Date();
		
		if(dob >= today){
			
			$("#dop-er").css("color", "red");
			$("#dop-er").html("Please enter valid date of birth");
			$("#dop-er").show();
			return;
		}else{
			$("#dop-er").hide();
		}
		
		$("#dateOfBirthAdd").val(dobMonth+"/"+dobDate+"/"+dobYear);
		
		if(!$("#addGuestForm").valid()) {
			 //console.log("")
			 return false;
		} else {
			//console.log("Validation Success submittting form");
			addGuestmember();
		}
		
		
	});
	





	

	




function addGuestmember(){
	$(".k-loading-mask").show();
	$(".k-loading-text").html("Please wait while the guest is added");
	
	 $("#tcSuccessSpan").css("display", "none");		
	 $("#tcSuccessSpan" ).html("");	
	 $("#tcErrorSpan1").css("display", "none");		
	 $( "#tcErrorSpan1" ).html("");
	 
	  var formdata= $('#addGuestForm').serialize();
	  
	  var primContId=$("#primContId").val();
	 
	  formdata=formdata+"&primContId="+primContId;
	 // alert("form data"+formdata);
	  
	//alert($('#addMembershipForm').serialize());
	$.ajax({
		  type: "POST",
		  url: $('#addGuestForm').attr( "action"),
		  data: formdata,
		  success: function( data ) {
			  var jsonDataArr = jQuery.parseJSON(data);
			  if(jsonDataArr != null && jsonDataArr.length > 0){			  
			  	  if(jsonDataArr[0] != null && jsonDataArr[0].Success != null && jsonDataArr[0].Success =='Success'){
				  	  $("#tcErrorSpan").css("display", "none");		
					  $("#tcErrorSpan" ).html("");	
					  //$("#tcSuccessSpan").css("display", "block");		
					  //$("#tcSuccessSpan" ).html("Profile Information Updated successfully.");
					  setTimeout(function(){$(".k-loading-text").fadeOut("slow");}, 100);
					  setTimeout(function(){$(".k-loading-text").html("Guest was added successfully");$(".k-loading-text").fadeIn("slow");}, 300);
				  	  setTimeout(function(){$(".k-loading-text").fadeOut("slow");}, 500);
				  	  //setTimeout(function(){location.href='view_membership';}, 7000);
				  	  //setTimeout(function(){location.reload();}, 7000);
				  	  //getFamilymembers();
				  	  $("#tcSuccessSpan1").css("display", "block");		
					  $("#tcSuccessSpan1" ).html("You are checking-in a guest member..");	
					  $("#guestCount").css("display", "block");	
					  $("#guestCount").html(jsonDataArr[0].guestCount);
					  $("#tcErrorSpan1").css("display", "none");		
					  $( "#tcErrorSpan1" ).html("");
					  $("#addGuestBtn").hide();
					//  setTimeout(function(){location.href='view_membership';}, 4000);
				  	  //closeAllwindow();
				  	  $(".k-loading-mask").hide();
				  	//location.href='view_membership';
				  	  
			  	  }
			  	 else if(jsonDataArr[0] != null && jsonDataArr[0].Duplicate != null && jsonDataArr[0].Duplicate=='Duplicate'){
			  		  $("#tcSuccessSpan1").css("display", "none");		
					  $("#tcSuccessSpan1" ).html("");	
					  $("#tcErrorSpan1").css("display", "block");		
					  $( "#tcErrorSpan1" ).html("You have already checked-in this  guest 5 times..!!");
					  $(".k-loading-mask").hide();
			  	  }
			  	else if(jsonDataArr[0] != null && jsonDataArr[0].AccExists != null && jsonDataArr[0].AccExists=='AccExists'){
			  		  $("#tcSuccessSpan1").css("display", "none");		
					  $("#tcSuccessSpan1" ).html("");	
					  $("#tcErrorSpan1").css("display", "block");		
					  $( "#tcErrorSpan1" ).html("User exists with same email address but different name. Either provide a different email or leave email blank.");
					  $(".k-loading-mask").hide();
			  	  }
			  	  else{
			  		  $("#tcSuccessSpan1").css("display", "none");		
					  $("#tcSuccessSpan1" ).html("");	
					  $("#tcErrorSpan1").css("display", "block");		
					  $( "#tcErrorSpan1" ).html("There was some error. Please try again later");
					  $(".k-loading-mask").hide();
			  	  }
			  }else{					  
				  $("#tcSuccessSpan1").css("display", "none");		
				  $("#tcSuccessSpan1" ).html("");	
				  $("#tcErrorSpan1").css("display", "block");		
				  $( "#tcErrorSpan1" ).html("There was some error. Please try again later");
				  $(".k-loading-mask").hide();
			  }
			  //$(".k-loading-mask").hide();
		  },
		  error: function( xhr,status,error ){
			  //alert("1" +status);
			  console.log(xhr);
			  $("#tcSuccessSpan1").css("display", "none");		
			  $("#tcSuccessSpan1" ).html("");	
			  $("#tcErrorSpan1").css("display", "block");		
			  $( "#tcErrorSpan1" ).html("There was some error. Please try again later");
			  $(".k-loading-mask").hide();
		  }
	});
}
 
 /* function CloseCheckInWind(){
	 $("#popup_ChckinMember").data("kendoWindow").close();
 } */
function displayReasonList(memberContactId){
   	$("#sexOffResonList"+memberContactId).kendoDropDownList(); 
   	var SexOffenderReasonDropdownlist = $("#sexOffResonList"+memberContactId).data("kendoDropDownList");
   	//SexOffenderReasonDropdownlist.select(0);
   	$("#unAuthAccess"+memberContactId).hide();
   	$("#sexOffResonDiv"+memberContactId).show();
   }
 
 
function selectReason(memberContactId,ele){
   	if(ele.value== 1){
   	    	// $("#unAuthAccess"+memberContactId).show();
   	    	$("#accDenySexOff"+memberContactId).hide();
   		unAuthAccess(memberContactId);
   	    }else if(ele.value== 0){
   	       //alert("{location change to first pos}");
   	    	//$("#unAuthAccess"+memberContactId).hide();
   	    	$("#accDenySexOff"+memberContactId).show();
   	        } 
   	}	



function closeAllwindow1(){

	$("div.k-window1").each(function(index, element){
		var windowall = $(this);
		if (!windowall.data("kendoWindow")) {
			windowall.kendoWindow({
				width: "1050px"
			});
		}
		windowall.data("kendoWindow").close();
	});
} 
</script>