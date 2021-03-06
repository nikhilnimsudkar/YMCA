<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
   <%  int tabIndex = 0; %>
<%-- 
<div id="main">
	<div class="k-loading-mask" style="width:100%;height:100%; position:absolute; top:114px; left:0px;display:none; z-index:9999">
		<span class="k-loading-text">Loading... Please wait</span>
		<div class="k-loading-image">
			<div class="k-loading-color"></div>
		</div>
	</div>
	<div style="width:100%;height:100%;margin-top:5px;margin-bottom:5px;">
		<form:form style="padding:5px;" action="#">
			
			<div style="height:50px;>
				<div style="height:20px;"><span style="font: normal;">Contact Health History</span></div>
				<c:choose>
					<c:when test="${fn:length(contactHealthHistoryList)!=0}">
						<c:forEach var="contactHH" items="${contactHealthHistoryList}" varStatus="status">
							<td>${contactHH.firstName}</td><td>${contactHH.lastName}</td>
						</c:forEach>
					</c:when>
				</c:choose>
			</div>
			<div align="center" style="height:50px; padding-top: 30px;" >
					
					<button type="button" id="updateContactHealthHistoryBtn" class="k-button" style="font-size: small; width: 150px;">Update</button>
				
					<div id="statusBlock">
						<span class="k-block k-success-colored" id="tcSuccessSpan1" style="display:none;"></span>
						<span class="k-block k-error-colored" id="tcErrorSpan1" style="display:none;"></span>
					</div>
			</div>
		</form:form>
	</div>
</div> --%>

<form:form commandName="paymentMethod" id="contactHHForm" method="post" action="#">
<table style="padding-left:4px;padding-top:4px; width: 100%;">
	<tr>
		<td colspan="4" align="left">
			<span class="head" style="margin-left:4px;">
				Contact Health History
			</span>
		</td>
	</tr>
	<tr><td><br><hr></td></tr>
	<tr>
	<td id="ContactHealthHistorySectionID">
	<%-- <c:choose>
		<c:when test="${fn:length(contactHealthHistoryList)!=0}">
			<c:forEach var="contact" items="${contactHealthHistoryList}" varStatus="status">
				<div id="contactHealthHistory_${contact.contactId}" style="display: none;">
					<table style="width: 100%;">
						<tr>
							<td align="right"><b>Contact </b></td>
							<td colspan="3"><b>${contact.firstName} ${contact.lastName}</b>
								<input type="hidden" name="contactId" id="contactId" value="${contact.contactId}">
								<input type="hidden" name="updateFlag_${contact.contactId}" id="updateFlag_${contact.contactId}" value="N">
								<input type="hidden" name="healthHistoryId_${contact.contactId}" id="healthHistoryId_${contact.contactId}" value="${contact.healthHistoryId}">
							</td>
						</tr>
						<tr><td colspan="4"><br></td></tr>
						<tr style="height: auto;">
							<td width="20%" align="right">Insurance company</td>
							<td width="30%"><input type="text" class="k-textbox" name="InsuranceCompany_${contact.contactId}" id="InsuranceCompany_${contact.contactId}" maxlength="100" style="width: 200px;" size="250px;" value="${contact.insuranceCompany}" tabIndex="<%= ++tabIndex %>"></td>
							<td width="20%" align="right">Instructions</td>
							<td width="30%"><textarea class="k-textbox" name="Instructions_${contact.contactId}" id="Instructions_${contact.contactId}" maxlength="100" style="width: 200px;" cols="250" rows="2" tabIndex="<%= ++tabIndex %>">${contact.instructions}</textarea></td>
						</tr>
						<tr>
							<td width="20%" align="right">List Current Medications</td>
							<td width="30%"><textarea class="k-textbox" name="listCurrentMedications_${contact.contactId}" id="listCurrentMedications_${contact.contactId}" maxlength="100" style="width: 200px;" cols="250" rows="2" tabIndex="<%= ++tabIndex %>">${contact.listCurrentMedications}</textarea></td>
							<td width="20%" align="right">Current Medication Purpose</td>
							<td width="30%"><textarea class="k-textbox" name="currentMedicationPurpose_${contact.contactId}" id="currentMedicationPurpose_${contact.contactId}" maxlength="100" style="width: 200px;" cols="250" rows="2" tabIndex="<%= ++tabIndex %>">${contact.currentMedicationPurpose}</textarea></td>
						</tr>
						<tr><td colspan="4"><br><hr></td></tr>
					</table>
				</div>
			</c:forEach>
		
		</c:when>
	</c:choose> --%>
	</td>
	</tr>
	<tr align="left">
		<td align="left">
			<div align="right" style="height:50px; padding-top: 30px; padding-right: 20px;" >
				<button type="button" id="updateContactHealthHistoryBtn" class="k-button" style="font-size: small; width: 250px;" tabIndex="<%= ++tabIndex %>">Update Health History</button>
			</div>
			<div align="left" style="height:50px; padding-top: 30px; padding-right: 20px;" >
				<div class="statusBlock">
					<span class="k-block k-success-colored" id="tcSuccessSpan1" style="display:none;"></span>
					<span class="k-block k-error-colored" id="tcErrorSpan1" style="display:none;"></span>
				</div>
			</div>
		</td>
	</tr>
</table>
</form:form>
<script>
	$(document).ready(function() {
	
		 $( "#updateContactHealthHistoryBtn" ).click(function(){
			$("#contactHHForm").submit(); //Submit contact health history FORM
		 });
			
		$("#contactHHForm").submit(function(e)
		{
			//console.log(" submitted ");
		    var postData = $(this).serializeArray();
		    var formURL = "saveContactHistory";

		    //console.log(" postData "+postData);
		    //console.log(" formURL "+formURL);
		    
		    var contactIds = $("[name='contactId']");
		    for(var i=0; i<contactIds.length; i++){
				var contactIdElement = contactIds[i];
				var contactId = contactIdElement.value;
				console.log("   contactId  "+contactId);
				
				var isValid = true;
				var msg = '';
				var elementId = '';
				
				if($("#InsuranceCompany_"+contactId) && $("#InsuranceCompany_"+contactId).val() == ''){
					msg = "Please enter Insurance Company.";
					elementId = "InsuranceCompany_"+contactId;
					isValid = false;
				}
				
				if(isValid && $("#Instructions_"+contactId) && $("#Instructions_"+contactId).val() == ''){
					msg = "Please enter Instructions.";
					elementId = "Instructions_"+contactId;
					isValid = false;
				}
				
				if(isValid && $("#listCurrentMedications_"+contactId) && $("#listCurrentMedications_"+contactId).val() == ''){
					msg = "Please enter Current Medications.";
					elementId = "listCurrentMedications_"+contactId;
					isValid = false;
				}
				
				if(isValid && $("#currentMedicationPurpose_"+contactId) && $("#currentMedicationPurpose_"+contactId).val() == ''){
					msg = "Please enter Current Medication Purpose.";
					elementId = "currentMedicationPurpose_"+contactId;
					isValid = false;
				}
				
				if(!isValid){
					$("#tcSuccessSpan1").css("display", "none");
				  	$("#tcSuccessSpan1" ).html("");
				  	$("#tcErrorSpan1").css("display", "block");
				  	$("#tcErrorSpan1" ).html(msg);
				  	$("#"+elementId).focus();
				  	return false;
				}
		    }
		    
		    if(isValid){
		    
			    $.ajax(
			    {
			        url : formURL,
			        type: "POST",
			        crossDomain: true,
			        data : postData,
			        dataType : "text",
			        success:function(data, textStatus, jqXHR) 
			        {
			            //console.log("Result: "+data);
			            
			            if(data && data == 'SUCCESS'){
		            		$("#contactHealthHistoryDiv").hide();
							//$("#checkout_content").show();
						}else{
							console.log(" Failed to update.");
							$("#contactHealthHistoryDiv").hide();
							//$("#checkout_content").show();
						}
			            showEmergencyContactIfFlagIsSet();
			        },
			        error: function(jqXHR, textStatus, errorThrown) 
			        {
			            //if fails   
			        	 console.log(" Error occured ");   
			        }
			    });
			    e.preventDefault();
		    }
		     //STOP default action
		    //e.unbind();
		});
	});

	function getContactHistory(contacts){
		
		if(contacts && contacts.length > 0){
			
			var contactStr = '';
			for(var i=0; i<contacts.length; i++){
				//console.log(" contacts "+contacts[i]);
				contactStr = contactStr.concat(contacts[i]);
				contactStr = contactStr.concat(',');
			}
			
			contactStr = contactStr.substr(0, contactStr.length - 1);
			
			$.ajax({
				type: "GET",
				url:"getContactHealthHistory?contacts="+contactStr,
				success: function( data ) {
					
					var contactDiv = '';
						
					if(data && data.length > 0){
						var obj = jQuery.parseJSON(data);
					  	 $.each(obj, function(i,contact) {
					  		
						  		contactDiv += "<div id='contactHealthHistory_"+contact.contactId+"'> <table style='width: 100%;'>"+
								"<tr>"+
								"<td align='right'><b>Contact </b></td>"+
								"<td colspan='3'><b>"+contact.firstName+" "+contact.lastName+"</b>"+
									"<input type='hidden' name='contactId' id='contactId' value='"+contact.contactId+"'>"+
										"<input type='hidden' name='updateFlag_"+contact.contactId+"' id='updateFlag_"+contact.contactId+"' value='N'>"+
										"<input type='hidden' name='healthHistoryId_"+contact.contactId+"' id='healthHistoryId_"+contact.contactId+"' value='"+contact.healthHistoryId+"'>"+
									"</td>"+
								"</tr>"+
								"<tr><td colspan='4'><br></td></tr>"+
								"<tr style='height: auto;'>"+
									"<td width='20%' align='right'>Insurance company</td>"+
									"<td width='30%'><input type='text' class='k-textbox' name='InsuranceCompany_"+contact.contactId+"' id='InsuranceCompany_"+contact.contactId+"' maxlength='100' style='width: 200px;' size='250px;' value='"+contact.insuranceCompany+"' tabIndex='1'></td>"+
									"<td width='20%' align='right'>Instructions</td>"+
									"<td width='30%'><textarea class='k-textbox' name='Instructions_"+contact.contactId+"' id='Instructions_"+contact.contactId+"' maxlength='100' style='width: 200px;' cols='250' rows='2' tabIndex='2'>"+contact.instructions+"</textarea></td>"+
								"</tr>"+
								"<tr>"+
									"<td width='20%' align='right'>List Current Medications</td>"+
									"<td width='30%'><textarea class='k-textbox' name='listCurrentMedications_"+contact.contactId+"' id='listCurrentMedications_"+contact.contactId+"' maxlength='100' style='width: 200px;' cols='250' rows='2' tabIndex='3'>"+contact.listCurrentMedications+"</textarea></td>"+
									"<td width='20%' align='right'>Current Medication Purpose</td>"+
									"<td width='30%'><textarea class='k-textbox' name='currentMedicationPurpose_"+contact.contactId+"' id='currentMedicationPurpose_"+contact.contactId+"' maxlength='100' style='width: 200px;' cols='250' rows='2' tabIndex='4'>"+contact.currentMedicationPurpose+"</textarea></td>"+
								"</tr>"+
								"<tr><td colspan='4'><br><hr></td></tr>"+
							"</table>"+
							"</div>";
					  		
					  	 });
					}
					
					$("#ContactHealthHistorySectionID").html(contactDiv);
				},
				error: function( xhr,status,error ){
					console.log(xhr);
					console.log("  Error  "+error);
				}
			});
			
			$("#contactHealthHistoryDiv").slideDown();
			
		}else{
			console.log(" No contacts for Health History. ");
		}
	}
	
</script>
