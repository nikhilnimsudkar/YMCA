<%@ include file="../../layouts/include_taglib.jsp" %> 
<script type="text/javascript">
$(document).ready(function(){	
	$(".termsAndConditionContent").html($("#commonTAncHidden").html());
	$("#ReqforFA").hide;
	 $("#reqforFAError" ).hide();	
	  $("#reqforFAInfo").hide();
	  $("#popUp2").hide();
	$("#popUp3").hide();
	
	$( "#updMembership" ).click(function(){
		if($("#tcCheckbox").is(':checked')){
			$("#tcErrorSpan").css("display", "none");		
			$( "#tcErrorSpan" ).html("");	
			//$( "#updateMembershipForm" ).submit();	
			var dobMonthForm = $("#dobMonthForm").val();
			var dobDateForm = $("#dobDateForm").val();
			var dobYearForm = $("#dobYearForm").val();
					
			$("#dateOfBirth").val(dobMonthForm+"/"+dobDateForm+"/"+dobYearForm);
			
			$(".k-loading-mask").show();
			updateMember();
		} else {
			$("#updateProfInfo").css("display", "none");		
			$("#updateProfInfo" ).html("");	
			$("#tcErrorSpan").css("display", "block");		
			$( "#tcErrorSpan" ).html("You must Agree the Terms and Conditions.");
			return false;
		}		
	});		
	$( "#updateTermsConditionsSpan" ).click(function(){		
		var kendoWindow = $("<div />").kendoWindow({
			title: "Terms & Conditions",
			resizable: false,
			modal: true,
			width:700,
			height : 500
		});

		kendoWindow.data("kendoWindow")
		 .content($("#updateTermsConditions").html())
		 .center().open();

		kendoWindow
		.find(".confirm-updateTermsConditions")
		.click(function() {
			if ($(this).hasClass("confirm-updateTermsConditions")) {     				
				kendoWindow.data("kendoWindow").close();
			}
		})
		.end();
	});	
});

function updateMember(){
	//alert($('#updateMembershipForm').serialize()+"&volunteerActivity=");
	//var checkedValues = $('input[name="volunteerActivity"]:checkbox:checked').map(function() {
	//    return this.value;
	//}).get();
	
	$.ajax({
		  type: "POST",
		  url: $('#updateMembershipForm').attr( "action"),
		  data: $('#updateMembershipForm').serialize(),
		  success: function( data ) {
		  	  //alert(data);
		  	  if(data=='SUCCESS'){
			  	  $("#tcErrorSpan").css("display", "none");		
				  $("#tcErrorSpan" ).html("");	
				  $("#updateProfInfo").css("display", "block");		
				  $("#updateProfInfo" ).html("Profile Information Updated successfully.");
		  	  }else if(data == 'LOGIN'){
		  		  $("#tcErrorSpan").css("display", "none");		
				  $("#tcErrorSpan" ).html("");	
				  $("#updateProfInfo").css("display", "block");		
				  $("#updateProfInfo" ).html("Profile Information Updated successfully. Please login with updated Information.");		  		  
		  	  }else if(data == 'PRIMARY_EMAIL_EXISTS'){
		  		$("#ReqforFA").hide;
		  		  $("#updateProfInfo").css("display", "none");		
				  $("#updateProfInfo" ).html("");	
				  //$("#tcErrorSpan").css("display", "block");		
				  //$( "#tcErrorSpan" ).html("Email already exists in system, please enter new one");
				  emailErrorDialoguePopup("Email already exists in system, please enter new one");
				  $(".k-loading-mask").hide();
		  	  }else if(data == 'SECONDARY_EMAIL_EXISTS'){
			  		$("#ReqforFA").hide;
			  		  $("#updateProfInfo").css("display", "none");		
					  $("#updateProfInfo" ).html("");	
					  //$("#tcErrorSpan").css("display", "block");		
					  //$( "#tcErrorSpan" ).html("Email already exists in system, either blank it out or make it unique");
					  emailErrorDialoguePopup("Email already exists in system, either blank it out or make it unique");
					  $(".k-loading-mask").hide();
			  }else if(data == 'PRIMARY-USER-EMAIL-UPTATE-ERROR'){
				  $("#updateProfInfo").css("display", "none");		
				  $("#updateProfInfo" ).html("");	
				  //$("#tcErrorSpan").css("display", "block");		
				  //$( "#tcErrorSpan" ).html("Email already exists in system, either blank it out or make it unique");
				  emailErrorDialoguePopup("Primary User Email can not be blank. Please enter unique email");
				  $(".k-loading-mask").hide();
		  	  }else if(data == 'ERROR'){
			  		$("#ReqforFA").hide;
			  		  $("#updateProfInfo").css("display", "none");		
					  $("#updateProfInfo" ).html("");	
					  //$("#tcErrorSpan").css("display", "block");		
					  //$( "#tcErrorSpan" ).html("There was some error. Please try again later");
					  emailErrorDialoguePopup("There was some error. Please try again later");
					  $(".k-loading-mask").hide();
			  }else{
		  		$("#ReqforFA").hide;
		  		  $("#updateProfInfo").css("display", "none");		
				  $("#updateProfInfo" ).html("");	
				  $("#tcErrorSpan").css("display", "block");		
				  $( "#tcErrorSpan" ).html("There was some error. Please try again later");
				  $(".k-loading-mask").hide();
		  	  }
			  $(".k-loading-mask").hide();
		  },
		  error: function( xhr,status,error ){
			  //alert("1" +status);
			  console.log(xhr);
			  $("#updateProfInfo").css("display", "none");		
			  $("#updateProfInfo" ).html("");	
			  $("#tcErrorSpan").css("display", "block");		
			  $( "#tcErrorSpan" ).html("There was some error. Please try again later");
			  $(".k-loading-mask").hide();
		  }
	});
}


function reqForFA() {
	   
	var kendoWindow = $("<div />").kendoWindow({
		title : "Confirm",
		resizable : false,
		modal : true,
		width : 400
	});
	  var kendoWindow2 =$("#popUp3"). kendoWindow({
		title : "Requested",
		resizable : false,
		modal : true,
		width : 400
	});  
	  var kendoWindow1 =$("#popUp2"). kendoWindow({
			title : "Requesting",
			resizable : false,
			modal : true,
			width : 400
		});  

	kendoWindow.data("kendoWindow").content($("#requestForFA").html()).center().open();

	kendoWindow.find(".delete-confirm,.delete-cancel").click(
			function() {
				if ($(this).hasClass("delete-confirm")) {
					$(".k-loading-mask").show();
					//alert("go to controller or make ajax call");
					$.ajax({
						  type: "POST",
						  url: "ReqforFA",
						  success: function( data ) {
						  	  //alert(data);
						  	  if(data=='SUCCESS'){
						  		  
						  		kendoWindow1.data("kendoWindow").content($("#popUp2").html()).center().open();							  	   $("#reqforFAError").css("display", "none");		
								  $("#reqforFAError" ).html("");	
								  $("#reqforFAInfo").css("display", "block");		
								  $("#reqforFAInfo" ).html("You wil revieve an Email for further instrutions.");
								  setTimeout(function(){$(".k-loading-text").fadeOut("slow");}, 100);
								  setTimeout(function(){$(".k-loading-text").html("Requesting for FA.");$(".k-loading-text").fadeIn("slow");}, 100);
							  	  setTimeout(function(){$(".k-loading-text").fadeOut("slow");}, 100);
							  //	alert("in success block");
							  	  closeAllwindow();
							  	  $(".k-loading-mask").hide();
							  	  
						  	  }
						  	 else if(data=='DUPE'){
						  		kendoWindow2.data("kendoWindow").content($("#popUp3").html()).center().open();
						  		 $("#reqforFAInfo").css("display", "none");		
								  $("#reqforFAInfo" ).html("");	
								  $("#reqforFAError").css("display", "block");		
								  $( "#reqforFAError" ).html("You have already requested for FA.");
								  $(".k-loading-mask").hide(); 
								  
								 // alert("in duplicate block");
						  	  }
						  	  else{
						  		  $("#reqforFAInfo").css("display", "none");		
								  $("#reqforFAInfo" ).html("");	
								  $("#reqforFAError").css("display", "block");		
								  $( "#reqforFAError" ).html("There was some error. Please try again later");
								  $(".k-loading-mask").hide();
						  	  }
							  //$(".k-loading-mask").hide();
						  },
						  error: function( xhr,status,error ){
							  //alert("1" +status);
							  console.log(xhr);
							  $("#reqforFAInfo").css("display", "none");		
							  $("#reqforFAInfo" ).html("");	
							  $("#reqforFAError").css("display", "block");		
							  $( "#reqforFAError" ).html("There was some error. Please try again later");
							  $(".k-loading-mask").hide();
						  }
					});
					
					kendoWindow.data("kendoWindow").close();
					$(".k-loading-mask").hide();
				}

				kendoWindow.data("kendoWindow").close();
			}).end();
}
				

 function closePopUp3(){
		
			//alert("in popup3 method --afer ok click");
			$('body').find("#popUp3").data("kendoWindow").close();
		
	} 
	 function closePopUp2(){
		// alert("in popup2 method --afer ok click");
			$('body').find("#popUp2").data("kendoWindow").close();
	 }

function emailErrorDialoguePopup(errorMsg){
	var kendoWindow = $("<div />").kendoWindow({
		title: "Error",
		resizable: false,
		modal: true,
		width:400
	});
	$("#form-update-error-p").html(errorMsg);
	$("#form-update-error").html($("#form-update-error-div").html());
	kendoWindow.data("kendoWindow")
	 .content($("#form-update-error").html())
	 .center().open();

	kendoWindow
	.find(".form-update-error-confirm")
	.click(function() {
		if ($(this).hasClass("form-update-error-confirm")) {     				
			kendoWindow.data("kendoWindow").close();
		}
	})
	.end();
}	 
	

</script>
<script id="requestForFA" type="text/x-kendo-template">
    <p class="delete-message">Are you sure you want to request for FA.</p>
	
    <div class="confirmbutton"><button class="delete-confirm k-button">Yes</button>&nbsp;&nbsp;&nbsp;
    <span class="delete-cancel k-button">No</span></div>
</script>

<script id="updateTermsConditions" type="text/x-kendo-template">
    <p class="error-message-p">${waiversTAndC.tcDescription }</p>	
    <div class="confirmbutton" align="center"><button class="confirm-updateTermsConditions k-button" style=" width: 35%;">Ok</button>   
</script>
	<!-- 
	<div>
		<div class="bootstrap-frm">
			<h2><span>I (WE) WAS (WERE) REFERRED BY</span></h2><span>We would like to send them a thank you.</span><br />
			<div style="margin-top:5px; margin-bottom:5px;">
				<a href="#">Click here</a> to learn more about our Friends & Family and loyalty programs.
			</div>
			<label><input type="text" placeholder="Member Name" name="memberName" id="memberName"></label>
		</div>		
	</div><br />
	 -->
	<div>
		<c:if test="${primarySignupRec != null && primarySignupRec.itemDetail != null && primarySignupRec.itemDetail.recordName != null }">
			<c:if test="${primarySignupRec.itemDetail.recordName != 'One Adult w/ Kids' && primarySignupRec.itemDetail.recordName != 'Two Adults w/ Kids' && primarySignupRec.itemDetail.recordName != 'Three Adults w/ or w/o kids'}">								
				<div class="bootstrap-frm" style="padding :10px;">
					<b>Note : If you want to add your child to the membership, please upgrade to a family membership, using Change Membership</b>
				</div><br />
			</c:if>
		</c:if>
		<div class="bootstrap-frm">		
			<h2><span>NEWS & UPDATES</span></h2>			
			<label><input type="radio" name="news_upd" > Please send me information regarding [my] areas of interest </label>
			<label><input type="radio" name="news_upd" > Send me regular Y updates, including my Branch newsletter </label>
			<label><input type="radio" name="news_upd" > Show programs on my page </label>
		</div>		
	</div>
	<%-- <div id="cancellation">
		<c:choose>
			<c:when test="${cancelled_Member=='true'}">
				<span id="cancellation" class="k-button" style="width:316px;">Membership cancellation in Process</span>
			</c:when>
			<c:otherwise>
				<span id="cancellation" class="k-button" onclick="location.href='requestcancellation'">Request for FA </span>
			</c:otherwise>
		</c:choose>
	</div> --%>
	<BR>
	<BR>
	<div id="reqForFA" >
	<%-- <span id="spmn">${reqFA }  </span> --%>
	<span id="requestForFA" class="k-button" onclick="reqForFA()" style="font-size: 14px;">REQUEST FOR FINANCIAL ASSISTANCE</span>
	
	</div>
	<div id="statusBlock">
		<span class="k-block k-success-colored" id="updateProfInfo"></span><br />
		<span class="k-block k-error-colored" id="tcErrorSpan"></span><br />
	</div>
	<div id="reqforFAStatus">
		<span class="k-block k-success-colored" id="reqforFAInfo"></span><br />
		<span class="k-block k-error-colored" id="reqforFAError"></span><br />
	</div>
	<div>
		<span class="k-button" id="updMembership">Update</span>	
		<span id="backbutton" class="k-button" onclick="location.href='home'">Back</span>
				
	</div>	
	
	
	<span style="font-size:8px;">Privacy Policy: <span id="updateTermsConditionsSpan" style="cursor: pointer; color: blue; font-size: 12px; margin-left :8px;">Click here to view T&C</span><br />
	<input type="checkbox" id="tcCheckbox"/> <span>I have read and agreed to the terms and conditions of usage of this site.</span> </span>
	
	<div id="popUp3" type="text/x-kendo-template">
    <p class="delete-message" id="msg2">You have already requested for FA..</p>
	
    <div class="confirmbutton"><button class="delete-confirm k-button" id="ok1" onclick="closePopUp3()"  >Ok</button></div>
   	</div>
   	
   	<div id="popUp2" type="text/x-kendo-template">
    <p class="delete-message" id="msg1">You will recieve an Email for further instrutions.</p>
	
    <div class="confirmbutton"><button class="delete-confirm k-button" id="ok2" onclick="closePopUp2()">Ok</button></div>
   	</div>
	
