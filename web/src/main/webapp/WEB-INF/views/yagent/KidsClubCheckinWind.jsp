<%@ page language="java" contentType="text/html; charset=ISO-8859-1"    pageEncoding="ISO-8859-1"%>
<%    	String contextPath = request.getContextPath();   %>
<%@ include file="../../layouts/include_taglib.jsp"%>
<div >
<c:choose>
	<c:when test="${noSignup != null}">
		<div class="k-block k-error-colored" role="alert" style="height:40px;padding: 10px;">
			You are not a member.
		</div>
	</c:when>
<c:otherwise> 
	
	
	
	
	
<table>  
<tr>
<td>
<div id="member_profile_1">
	<div id="tabstrip" class="k-block" style="border-color:#fcaf17;  margin-top: 10px;">
		<ul>
			<li class="k-state-active">
				<span class="head">PRIMARY FAMILY CONTACT INFORMATION</span>
			</li>
		</ul>
		 <div>
			<div id="team1">
				<div id="team_info" style="float: left; padding-top: 41px; margin-left: 4px; width: 119px;">
					<span class="name" style="margin-left:1px;">${usInfo.personFirstName } ${usInfo.personLastName }</span>
					<br>
					<span id="agecal" >Age:  ${usInfo.age}      </span>
					<input type="hidden" id="primContId" name="primContId" style="display:none;" value="${usInfo.contactId}"/>
					
				</div>
				
				
			</div>
			<span class="profilepic" style="background: url('<%=contextPath %>/${usInfo.profileImage}') transparent no-repeat 0 0;">&nbsp;</span>
		
		 	 
		</div>
	</div>
</div>

</td>
<td colspan="">
<div id="membershipChikin">
	<div id="tabstrip3" class="k-block" style="min-height: 0px; padding: 5px 5px 25px; background-color: white; margin-left: -7px; width: 698px; height: 193px; margin-top: -10px;">
	
	<h1 align="center">${usInfo.personFirstName } ${usInfo.personLastName }</h1>
	<c:choose>
	<c:when test="${signup!=null }">
	
	<h3 align="center">${signup.itemDetail.description }</h3>
	<h3 align="center">${signup.location.recordName }</h3>
	<input type="hidden" id="status1" value="${signup.status}"/>
	<br>
	</c:when>
	<c:otherwise>
	<h3 align="center"> Access Denied.!!</h3>
	<h3 align="center">Member has no ACTIVE Signup.</h3>
	 
	</c:otherwise>
	</c:choose>
	<div id="inactiveMem" style="display:none;">
	<b> Access Denied.!! No ACTIVE Signup.</b>
	
	</div>
	<c:if test="${diffLocation != null }">
	<div id="locDiff" style="background-color:red;"><b >${diffLocation }</b><br></div>
	</c:if>
	<span> Total No.Of Time Kids Club Used : ${noOfTimesKidCheckinCount}</span>	
	</div><br>
	
</div>

</td>
</tr>
<tr>
<td>
<c:if test="${ userCount >= 1}">
<div  id="member_profile_2">
	<div id="tabstrip1" class="k-block">
		<ul>
			<li class="k-state-active">
				<span class="head">KIDS TO BE CHECK-IN</span>
			</li>
		</ul>
		 <div>
		 	<span style="float: right; width: 100%; right: 0px; top: -30px; position: relative;">
		 		<!-- <a id="addmemberLink" href="#" style="float:right;" onclick="add_member();">Add Member</a> -->
		 	</span>
		 	<c:forEach var="member" items="${userS}" varStatus="count">
			  	<div id="team">
			
			 
			  	
					<div id="team_info" style="padding-top: 50px; float: left; width: 116px; margin-left: 4px;">
						<span class="name">${member.personFirstName } ${member.personLastName }</span><br>
						<br>
					<span >Age:  ${member.age}       </span>
					</div>
					<input type="checkbox" id="${ member.contactId }"  value="${ member.contactId }" class="kidCheckin" style="margin-top: 98px; margin-left: 70px;"/>
					</div>
					<%-- <span class="profilepic" style="background: url('${.profileImage}'); transparent no-repeat 0 0;height: 113px;">&nbsp;</span> --%>
					<span class="profilepic" style="background: url('<%=contextPath %>/${member.profileImage}') transparent no-repeat 0 0;">&nbsp;</span>
				<c:if test="${!count.last}"><hr></c:if>
			</c:forEach>
			
		
		</div>
	</div>
</div>
</c:if>
<c:if test="${ userCount >= 1}">
<span id="KidsCheckInButn" class="k-button" style=" margin-left:165px;"  >CHECK-IN</span>
<!-- <span id="temop" class="k-button" style=" margin-left:px;" onclick="displayFamilyMemDropDown()" >CHECK-IN demo</span> -->

</c:if>
</td>

<td>
<c:if test="${ chkoutKidCount >= 1}">
<div  id="member_profile_2">
	<div id="tabstrip1" class="k-block" style="width:580px;">
		<ul>
			<li class="k-state-active">
				<span class="head">KIDS TO BE CHECKED-OUT</span>
			</li>
		</ul>
		 <div>
		 	<span style="float: right; width: 100%; right: 0px; top: -30px; position: relative;">
		 		<!-- <a id="addmemberLink" href="#" style="float:right;" onclick="add_member();">Add Member</a> -->
		 	</span>
		 	<c:forEach var="member" items="${ChkoutKid}" varStatus="count">
		 	<div id="ChkoutSecDiv${ member.contact.contactId }">
			  	<div id="team" style="margin-right: 170px;">
				<!-- <input type="hidden" id="varCount" value=count /> -->
			 
			  	
					<div id="team_info" style="padding-top: 50px; float: left; width: 116px; margin-left: 4px;">
						<span class="name">${member.contact.personFirstName } ${member.contact.personLastName }</span><br>
						<br>
					<span >Age:  ${member.contact.age}       </span>
					
					
					</div>
					
					<input type="checkbox" id="${ member.contact.contactId }"  value="${ member.contact.contactId }" class="kidCheckout" style="margin-top: 98px; margin-left: 0px;"/>
					<div style="margin-left: 265px; margin-bottom: -20px; margin-top: -146px;">
						<div style="float: left; width: 127px; margin-left: -93px; padding-top: 39px;" id="chkdInID${member.checkInPerson.contactId }">
						<psan>Checked-In Person</span>
						<br>
						<span class="name" style="margin-left:1px;">${member.checkInPerson.personFirstName } ${member.checkInPerson.personLastName }</span>
						<input type="hidden" id="chkInPrsnId${ member.contact.contactId }" class="chkInPrsn" value="${member.checkInPerson.contactId }"/>
						</div>
						<span class="profilepic" style=" margin-left: 56px; margin-top: 39px; background: url('<%=contextPath %>/${member.checkInPerson.profileImage}') transparent no-repeat 0 0; ">&nbsp;</span>
						</div>
					</div>
					<%-- <span class="profilepic" style="background: url('${.profileImage}'); transparent no-repeat 0 0;height: 113px;">&nbsp;</span> --%>
						<span class="profilepic" style="background: url('<%=contextPath %>/${member.contact.profileImage}') transparent no-repeat 0 0;">&nbsp;</span>
						<span>Checked-In Time:<fmt:setTimeZone value="America/Los_Angeles"/><fmt:formatDate  pattern="MM/dd/yy hh:mm:ss a" value="${member.createdDate }" timeZone="America/Los_Angeles"/></span>
						<%-- <div style="margin-left: 15px; margin-right: 50px; "><span>Checked-In Time:<fmt:formatDate  pattern="hh:mm:ss" value="${member.createdDate }" /></span></div> --%>
				</div>
				<c:if test="${!count.last}"><hr></c:if>
			</c:forEach>
			
		
		</div>
	</div>
</div>
</c:if>
<c:if test="${ chkoutKidCount >= 1}">
<span id="KidsCheckOutButn" class="k-button" style=" margin-left:295px;"  >CHECK-OUT</span>
</c:if>
</td>
</tr>


</table>

<div>
	<span id="CloseKidsChekinWind" class="k-button"  style="margin-top: 4px; width: 113px; margin-left: 475px;" onclick="closeAllKidwindowk()" > DONE </span>
</div>
<div class="k-window2" id="popup_seleContact" style="display: none;">

</div>
<div class="k-window2" id="popup_seleChkOutContact" style="display: none;">

</div>
<c:forEach var="personChkIn" items="${respForKidChkIn}" varStatus="count">
          <input type="hidden" id="personsexOff${personChkIn.contactId }" value="${personChkIn.sexOffender }">
            
 </c:forEach>
</c:otherwise>
	</c:choose>
</div>
<script id="alertDiffId" type="text/x-kendo-template">
    <p class="delete-message">Check-Out person is different then Checked-in person.</p>
	
    <div class="confirmbutton"><button class="delete-confirm k-button">Check-Out</button>&nbsp;&nbsp;&nbsp;
    <span class="delete-cancel k-button">Cancel</span></div>
</script>




<script>
$(document).ready(function() {
	
	 var status1=$("#status1").val();
		if(status1=='ACTIVE'){
		$("#tabstrip3").css('background-color', 'green');	
		}else{
			$("#tabstrip3").css('background-color', 'red');
			$("#inactiveMem").show();
			$("#locDiff").css('background-color', 'green');
			
		} 
		
	
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
	
	
	
});	


/* 
 $("#KidsCheckOutButn").click(function(){
	 
	var date_list='';
	var primContID=$("#primContId").val();
	alert(primContID);
	var date_list_counter=1;
	$('.kidCheckout:checked').each(function(){
	   if(date_list_counter !=1){
	   	 date_list=date_list+',';	
	   }
	   date_list =date_list + jQuery(this).val();
	   date_list_counter++;
	});
	if(date_list==''){
		alert("You must have select at least one checkbox");
		return false;
	}
alert(date_list);
      $.ajax({
	  // type :'POST',	
	   url: "checkOutKids?primContID=" + primContID+"&date_list="+date_list,
	   success : function(data) {

		if(data==='Success'){
			$('.kidCheckout:checked').each(function(){
				   if(date_list_counter !=1){
				   	 //date_list=date_list+',';	
					   $('.kidCheckout:checked').prop('disabled', true);
				   }
				   
				});
		}
	   }
	}); 

});  */

//===========================KIDS CHECKIN PROCESS=======================
	
	$("#KidsCheckInButn").click(function(){
		 
			var date_list='';
			var date_list_counter=1;
			$('.kidCheckin:checked').each(function(){
			   if(date_list_counter !=1){
			   	 date_list=date_list+',';	
			   }
			   date_list =date_list + jQuery(this).val();
			   date_list_counter++;
			});
			if(date_list==''){
				
				//alert("You must have select at least one checkbox");
				var message="Please select a kid for check-in.";
				showAlert(message);
				return false;
			}else{
				displayFamilyMemDropDown();
			}
		
		
	});

//$("#kidChekinPerson").kendoDropDownList(); 
//var locationDropdownlist = $("#kidChekinPerson").data("kendoDropDownList");
//locationDropdownlist.select(0);
function displayFamilyMemDropDown(){
	//$("#kidChekinPerson").kendoDropDownList(); 
	//var locationDropdownlist = $("#kidChekinPerson").data("kendoDropDownList");
	//locationDropdownlist.select(0);
	$("#popup_seleContact").html("");
	var adultMemberHtml = '';
	adultMemberHtml += '<div>';
	adultMemberHtml += '<select id="kidChekinPerson"  onchange="selectPerson(this)">';
	adultMemberHtml += '<option value="0">--Select Person--</option>';
	adultMemberHtml += '<c:forEach var="personChkIn" items="${respForKidChkIn}" varStatus="count">';
	adultMemberHtml += '<option value="${personChkIn.contactId }">${personChkIn.personFirstName } ${personChkIn.personLastName }</option>';
	adultMemberHtml += '</c:forEach>';
	adultMemberHtml += '</select>';
	adultMemberHtml += '</div>';
	adultMemberHtml += '<div id="selMemErrMsg" >Please Select a member for Check-in.</div>';
	$("#popup_seleContact").html(adultMemberHtml);
	var window_add = $("#popup_seleContact");
	window_add.clone().kendoWindow({
		title : "Select Check-In Person",
		
		modal : false,
		width : "350px",
		height : "150px",
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

function selectPerson(ele){
	var locationDropdownlist = $(ele).find("option:selected").text();
	//var contId= $("#kidChekinPerson").val();
	var selectedValue = $(ele).val();
	
	//alert("selected person="+locationDropdownlist +"selected val="+selectedValue);
   	if(ele.value >= 1){
   		$(document).find("#selMemErrMsg").hide();
   	    	//alert("member selected for checkin");
   	    	checkinKidsByMem(selectedValue);
   	    }else if(ele.value== 0){
   	     //  alert("please select a person");
   	    $(document).find("#selMemErrMsg").show();
   	        } 
   	}	
 

/* $("#kidChekinPerson").on('change',function(e){ 
	var selectedText = $(this).find("option:selected").text();
	var selectedValue = $(this).val();
    alert("Selected Text: " + selectedText + " Value: " + selectedValue);
    if(selectedText != '--Select Location--'){
  //  alert("{location change}");
    	$("#selMemErrMsg").hide();
	    	alert("member selected for checkin");
    }else if(selectedText == '--Select Location--'){
       // alert("{location change to first pos}");
    	$("#selMemErrMsg").show();
        }

    
}); */
function contactSystemDirector(memberContactId,message){
	//alert(memberContactId);
	var checkedInLocation= $('#location').val();
	var url = "contactSystemDirector?memberContactId="+memberContactId+"&checkedInLocation="+checkedInLocation;
	$("#unAuthAccButton"+memberContactId).hide();
	$.ajax({
		  type: "GET",
		  async: false,
		  url:url,
		  success: function( data ) {
			 //alert(data);
			 showAlert(message);
			 closeAllwindow1();
		  },
		  error: function( xhr,status,error ){
			  console.log(xhr);
		  }
	});
	
}
   	
function checkinKidsByMem(chkinMemId){
	
	var memSexOffStatus=$("#personsexOff"+chkinMemId).val();
	var message="Cannot check-in to Kids Club. Please contact system director";
	 //alert(chkinMemId);
	 var location= $('#location').val();
	// alert("seelected Location"+location);
	var date_list='';
	var primContID=$("#primContId").val();
	//alert(primContID);
	var date_list_counter=1;
	$('.kidCheckin:checked').each(function(){
	   if(date_list_counter !=1){
	   	 date_list=date_list+',';	
	   }
	   date_list =date_list + jQuery(this).val();
	   date_list_counter++;
	});
	if (memSexOffStatus === 'Yes'){
		
		contactSystemDirector(chkinMemId,message);
	}else{
      $.ajax({
	  // type :'POST',	
	   url: "checkInKids?primContID=" + primContID+"&date_list="+date_list+"&chkinMemId="+chkinMemId+"&location="+location,
	   success : function(data) {

		if(data!=null){
			var accId=data;
			//alert("acc id="+accId+"data val="+data);
			$('.kidCheckin:checked').each(function(){
				   if(date_list_counter !=1){
				   	 //date_list=date_list+',';	
					   $('.kidCheckin:checked').prop('disabled', true);
					   closeAllwindow1();
					  

					   ///closeAllwindow();
					  // display_kidsCheckInWindow(accId); 
					   
				   }
				   
				});
			
			closeAllwindow1();
			closeAllwindow();
			   display_kidsCheckInWindow(accId); 
		}
	   }
	}); 
	}

}
 	
//========================================CHECKOUT PROCESS================================= 	
 
	//-----on clicking CHECKOUT Button----
 $("#KidsCheckOutButn").click(function(){
	 
	var date_list='';
	//var primContID=$("#primContId").val();
	//alert(primContID);
	var date_list_counter=1;
	$('.kidCheckout:checked').each(function(){
	   if(date_list_counter !=1){
	   	 date_list=date_list+',';	
	   }
	   date_list =date_list + jQuery(this).val();
	   date_list_counter++;
	});
	if(date_list==''){
		//alert("You must have select at least one checkbox");
		var message="Please select a kid for check-out.";
				showAlert(message);
		return false;
	}else{
		displayChkOutFamilyMemDropDown();
	}


});  
	
	
	
	//disply CheckOut Persons list PopUp
 
 function displayChkOutFamilyMemDropDown(){
	//$("#kidChekinPerson").kendoDropDownList(); 
	//var locationDropdownlist = $("#kidChekinPerson").data("kendoDropDownList");
	//locationDropdownlist.select(0);
	$("#popup_seleChkOutContact").html("");
	var adultMemberHtml = '';
	adultMemberHtml += '<div>';
	adultMemberHtml += '<select id="kidChekoutPerson"  onchange="selectChkOutPerson(this)">';
	adultMemberHtml += '<option value="0">--Select Person--</option>';
	adultMemberHtml += '<c:forEach var="personChkIn" items="${respForKidChkIn}" varStatus="count">';
	adultMemberHtml += '<option value="${personChkIn.contactId }">${personChkIn.personFirstName } ${personChkIn.personLastName }</option>';
	adultMemberHtml += '</c:forEach>';
	adultMemberHtml += '</select>';
	adultMemberHtml += '</div>';
	adultMemberHtml += '<div id="selMemErrMsgChkOut" >Please Select a member for Check-Out.</div>';        
	$("#popup_seleChkOutContact").html(adultMemberHtml);
	
	var window_add = $("#popup_seleChkOutContact");
		window_add.clone().kendoWindow({
		title : "Select Check-Out Person",
		modal : false,
		width : "350px",
		height : "150px",
		close : onClose,
		deactivate : function(e) {
			this.destroy();
		}
	}).data("kendoWindow").center().open();
	$('[data-role="draggable"]').css('top', '40px');
}
var onClose = function() {
	this.destroy();   
};



 //display a dropdown list on chkout person-popup list .
 
 function selectChkOutPerson(ele){
	var locationDropdownlist = $(ele).find("option:selected").text();
	//var contId= $("#kidChekinPerson").val();
	var selectedValue = $(ele).val();
 	if(ele.value >= 1){
   		$(document).find("#selMemErrMsgChkOut").hide();
   	    	//alert("member selected for checkout");
   	    	checkOutKidsByMem(selectedValue);
   	    }else if(ele.value== 0){
   	     //  alert("please select a person");
   	    $(document).find("#selMemErrMsgChkOut").show();
   	        } 
   	}	
 
 //function to chkout kid by respective member
 
 function checkOutKidsByMem(chkOutMemId){
	 //  alert("Chkout memId sele"+chkOutMemId);
	 	var date_list='';
		var primContID=$("#primContId").val();
		var location= $('#location').val();
		// alert("seelected Location"+location);
		//alert(primContID);
		var memSexOffStatus=$("#personsexOff"+chkOutMemId).val();
		//alert(chkOutMemId);
		var message="Cannot check-out from Kids Club. Please contact system director";
		var date_list_counter=1;
		$('.kidCheckout:checked').each(function(){
		   if(date_list_counter !=1){
		   	 date_list=date_list+',';	
		   }
		  	 
		   date_list =date_list + jQuery(this).val();
		   date_list_counter++;
		});
	
		var status = false;
		 $('.kidCheckout:checked').each(function(){
			  var kidId=$(this).val();
			  // alert("kid-id"+kidId);
			   
			   var ChkdinpId=$("#chkInPrsnId"+kidId).val();
			  // alert("ChkedIn P id= "+ChkdinpId +" and Chkout P id ="+chkOutMemId);
			   
			 
			   if(chkOutMemId != ChkdinpId){ 
			  		
				
				 $("#ChkoutSecDiv"+kidId).css('background-color', 'red');
				   status = true;
			   		//alert("status"+status);
			   		
			 
			   }
		   }); 
		
		if (memSexOffStatus === 'Yes'){
			
			contactSystemDirector(chkOutMemId,message);
		}else{
			  
						  
			  	 
			
					if(status == false){
							      $.ajax({
								  // type :'POST',	
								   url: "checkOutKids?primContID=" + primContID+"&date_list="+date_list+"&chkOutMemId="+chkOutMemId+"&location="+location,
								   success : function(data) {
						
									//if(data==='Success'){
										if(data!=null){
										$('.kidCheckout:checked').each(function(){
											   if(date_list_counter !=1){
											   	 //date_list=date_list+',';	
												   $('.kidCheckout:checked').prop('disabled', true);
												  // $("#popup_seleChkOutContact").data("kendoWindow").close();
												//  window.parent.$("#popup_seleChkOutContact").data("kendoWindow").close();
												  closeAllwindow1();
												  
											   }
											   
											});
										
										setTimeout(function() {
											//location.reload();
										}, 10000);
									  closeAllwindow();
									  display_kidsCheckInWindow(data); 
									}
								   }
								}); 
		 			} else{
	    	  
			 
								 var kendoWindow = $("<div />").kendoWindow({
										title : "Alert",
										resizable : false,
										modal : true,
										width : 400
									});
								 
								 kendoWindow.data("kendoWindow").content($("#alertDiffId").html()).center().open();

							kendoWindow.find(".delete-confirm,.delete-cancel").click(
								function() {
									if ($(this).hasClass("delete-confirm")) {
										
										$.ajax({
											  // type :'POST',	
											   url: "checkOutKids?primContID=" + primContID+"&date_list="+date_list+"&chkOutMemId="+chkOutMemId+"&location="+location,
											   success : function(data) {
		
												//if(data==='Success'){
													if(data!=null){
													$('.kidCheckout:checked').each(function(){
														   if(date_list_counter !=1){
														   	 //date_list=date_list+',';	
															   $('.kidCheckout:checked').prop('disabled', true);
															  // $("#popup_seleChkOutContact").data("kendoWindow").close();
															//  window.parent.$("#popup_seleChkOutContact").data("kendoWindow").close();
															  closeAllwindow1();
														   }
														   
														});
													setTimeout(function() {
														//location.reload();
													}, 10000);
												  closeAllwindow();
												  display_kidsCheckInWindow(data); 
												}
											   }
											}); 
									
								}
								closeAllwindow1();
								kendoWindow.data("kendoWindow").close();
							}).end();
					 
	      			}
	    	  
		 }    
	 
 }

 function closeAllwindow1(){

		$("div.k-window2").each(function(index, element){
			var windowall = $(this);
			if (!windowall.data("kendoWindow")) {
				windowall.kendoWindow({
					width: "1050px"
				});
			}
			windowall.data("kendoWindow").close();
		});
	} 
 
 function closeAllwindow(){

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
 
 
 
 function showAlert(message){
		var kendoWindow = $("<div />").kendoWindow({
     	title: "Alert",
     	resizable: false,
     	modal: true,
     	width:400
     });
		
		
		kendoWindow.data("kendoWindow")
      .content($("#alertBox").html())
      .center().open();
		
		$(".alertBoxMessage").html(message);
		
     kendoWindow
     .find(".ok-confirm")
     .click(function() {
     	if ($(this).hasClass("ok-confirm")) {         		
     		kendoWindow.data("kendoWindow").close();
     	}
     })
     .end();
	}
</script>

<script id="alertBox" type="text/x-kendo-template">
<div class="alertBoxMessage">Confirm?</div>
	
<div class="confirmbutton">
		<button class="ok-confirm k-button">Ok</button>
		&nbsp;&nbsp;&nbsp;
		<span class="delete-cancel k-button" style="display:none;">Cancel</span>
	</div>
</script>
 
 
 
