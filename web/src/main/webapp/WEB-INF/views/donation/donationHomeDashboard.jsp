<%-- <div id="main">
	<div class="k-loading-mask" style="width:100%;height:100%; position:absolute; top:114px; left:0px;display:none; z-index:9999">
		<span class="k-loading-text">Loading... Please wait</span>
		<div class="k-loading-image">
			<div class="k-loading-color"></div>
		</div>
	</div>
	<div id="contentas" style="margin-bottom:30px;" class="bootstrap-frm donation-right-sec-block">
			<div id="contentFormDiv" >
				<a href="<%=contextPath %>/donationRegisterAndDonate">Register and donate</a><br /><br />
				<a href="<%=contextPath %>/donateAsGuest">Donate as a guest</a>
			</div>
	</div>
</div> --%>
<%@ include file="../../layouts/include_taglib.jsp" %>

<style>
	.collapse{
		display:none;
	}
	
	.expand{
		display:block;
	}
	
	.sectionHead{
		background-color: #CFE1FB; border-radius: 6px; margin-bottom: 10px; margin-top: 10px; padding: 10px;
	}
	
	.showSection{
		background: url('resources/img/show_section.gif') transparent no-repeat;
	}
	
	.hideSection{
		background: url('resources/img/hide_section.gif') transparent no-repeat;
	}
	
	.viewall{
		cursor:pointer;
	}
	

	table {
	    border-collapse:separate;
	    border:solid #c5c5c5 1px;
	    border-radius:6px;
	    -moz-border-radius:6px;
	}
	
	td, th {
	    border-left:solid #c5c5c5 1px;
	    border-top:solid #c5c5c5 1px;
	}
	
	th {
	    background-color: #01A490;
	    border-top: none;
	    padding : 6px;
	    color : white !important;
	}
	td:first-child {
	     border-left: none;
	}	
</style>


<script>
$(document).ready(function(){ 
	/* $( ".viewall" ).click(function(){
		//alert(this.id);
		$(".sectionHead").find(".hideSection").attr("class","showSection");
		$('.viewall').each(function(){
			$( "#"+this.id+"Div" ).attr("class","collapse");
		});
		
		$( "#"+this.id+"Div" ).attr("class","expand");
		$( "#"+this.id).find("span.showSection").attr("class","hideSection");
	}); */
	/* $("#donationHistoryTable").kendoGrid({
		 pageable: true,
		 dataSource: {
			 pageSize: 10
		 }
	 }); */
	 $("#UpdateDraftCycleDiv").hide();
	 
	$('.expandable').click(function () {
        $('.expandable').not(this).nextAll('tr').not('.expandable').hide();
        ////$('.expandable').not(this).find('input[type="button"]').val("+");
        $('.expandable').not(this).find('a span').text("+");
        
        //$(this).nextAll('tr').toggle(350);
        $(this).nextAll('tr').each(function () {
	        if (!($(this).is('.expandable'))){
	        	$(this).toggle(350);	         
	        }else if (($(this).is('.expandable'))){
	        	return false;
	        }
	    });
        
        if( $(this).find('a span').text() == "+")
        {
        	$(this).find('a span').text("-");
            //$(this).find('input[type="button"]').val("-");
        }
        else
        {
            $(this).find('a span').text("+");
            $('.expandable').nextAll('tr').each(function () {
    	        if (!($(this).is('.expandable'))) 
    	        {$(this).hide();
    	         
    	        }
    	    });
        }
        
    });
    $('.expandable').nextAll('tr').each(function () {
        if (!($(this).is('.expandable'))) 
        {$(this).hide();
         
        }
    });
    
	$("#saveDraftCycle").click( function (e) {
		
		var signupId = $("#signupIdToUpdate").val();
		var draftCycleNumber = $("#draftCycleNumber").val(); 
		
		var dataMap = new Object();
		dataMap.signupId = signupId;
		dataMap.draftCycleNumber = draftCycleNumber;
		
		$.ajax({
			type: "POST",
			url: "updateSignup",
			async:false,
			data: { dataMap: JSON.stringify(dataMap) },
			success: function( data ) {
				if(data){
					//window.location.href = "viewProgramDetails?sId="+signupId+"&showSuccessMsg=true";
					// updateDraftCycle.data("kendoWindow").close();
					//updateDraftCycle1.hide();
					//linkToDraftCycleUpdate.show();
					window.location.reload();
				}
			},
			error: function( xhr,status,error ){
				console.log(" Error 242 ");
				$("#error_msg_div").show();
			}
		});
		
		
    });
});

function updateDraftCycleShow(signupId, draftCycle){
	
	var dc = draftCycle.split(",");
	
	if(dc.length == 2){
		
		$("#signupIdToUpdate").val(signupId); 
		$("#draftCycleSpan").html(draftCycle); 
		$("#draftCycleNumber").val(dc[1]); 
		
		$("#tabstrip4").hide();
		$("#UpdateDraftCycleDiv").show();
	}
}

function backToRecentDonations(){
	$("#tabstrip4").show();
	$("#UpdateDraftCycleDiv").hide();
}

function updatePaymentMethodShow(signupId){
	$("#signupIdToUpdate").val(signupId);
	window.location.href = "viewUpdPM?sId="+signupId;
}

</script>

<div id="tabstrip4" class="k-block"
	style="background-color: #FFFFFF; margin-left: -71px; padding-top: 20px; padding-left: 20px; width: 838px;">
			<c:choose>
			    <c:when test="${accInfo != null && accInfo.customerType != null && accInfo.customerType eq 'Organization'}">
			       <span class="k-button" id="updProfile"	onclick="location.href='donationDonate'" style="margin-left: 5px;">Give	to the Y</span>
			       <span class="k-button" id="employerMatch"	onclick="location.href='donationEmployerMatch'" style="margin-left: 10px;">Employer Match</span>
			    </c:when>		   						    
			    <c:otherwise>
			        <span class="k-button" id="updProfile"	onclick="location.href='donationDonate'" style="margin-left: 5px;">Give	to the Y</span>
			    </c:otherwise>
			</c:choose>		
			</br></br>			
			<table cellpadding="2" cellspacing="0" width="98%" 	style=""  id="donationHistoryTable">
				<span class="head" width="100%" style="margin-left: 4px;">MY RECENT DONATIONS</span><br />
				<span style="color: black;background-color: yellow;">If you want to pay off balance, please contact Y agent prior to cancellation.<br></span><br>
				<c:if test="${fn:length(donationList) >0 }">
					<tr>
						<th style="font-weight: bold; color: #006B6B; width : 1%; ">&nbsp;</th>
						<th class="head" style="font-weight: bold; color: #006B6B; width : 9%; ">Date</th>
						<th class="head" style="font-weight: bold; color: #006B6B; width : 9%;">Amount</th>
						<th class="head" style="font-weight: bold; color: #006B6B; width : 9%;">Pledge Amt.</th>
						<th class="head" style="font-weight: bold; color: #006B6B; width : 10%;">Payment Freq.</th>
						<th class="head" style="font-weight: bold; color: #006B6B; width : 10%;">Recognized As</th>		
						<th class="head" style="font-weight: bold; color: #006B6B; width : 10%; ">&nbsp;</th>
						<th class="head" style="font-weight: bold; color: #006B6B; width : 10%; ">&nbsp;</th>
						<th class="head" style="font-weight: bold; color: #006B6B; width : 10%; ">&nbsp;</th>				
					</tr>
				</c:if>			
				<c:forEach var="donationSignup" items="${donationSignupMap}" varStatus="status">												
						<fmt:parseNumber var="pledgeAmount"  type="number" value="${donationSignup.pledgeAmount}" />					
						<fmt:parseNumber var="signupDonationPaymentFrequency"  type="number" value="${donationSignup.pledgeAmountFrequency}" />
						<c:set var="formattedPledgeAmount" value="${fn:split(pledgeAmount.toString(), '.')}" />
						
						<c:if test="${status.count % 2 == 0}">															
							<tr style="background-color : #F0FFFF; padding-left: 4px; " class="expandable border-radius5px">
						</c:if>
						<c:if test="${status.count % 2 != 0}">
							<tr style=" padding-left: 4px; " class="expandable sectionHead viewall border-radius5px">
						</c:if>
							
							<td><a class="add-child-lnk"><span>+</span></a></td>
							<td style="color: blue; padding-left: 4px; padding: 3px;"><fmt:formatDate type="date" pattern="MM/dd/yyyy" value="${donationSignup.signupDate}" timeZone="Pacific/Guam"/></td>								
							<td style="color: blue; ">&nbsp;</td>
							<td style="color: blue; ">$${formattedPledgeAmount[0]}</td>	
							<c:if test="${donationSignup.pledgeAmountFrequency != null}">	
								<fmt:parseNumber var="donationPaymentFrequency"  type="number" value="${donationSignup.pledgeAmountFrequency}" />
							</c:if>
							<c:if test="${donationPaymentFrequency != null && donationPaymentFrequency == 1}">											
								<td style="color: blue; ">One Time</td>
							</c:if>	
							<c:if test="${donationPaymentFrequency != null && donationPaymentFrequency > 1}">
								<td style="color: blue; ">${donationSignup.pledgeAmountFrequency} ${donationSignup.signUpPricingOption}</td>											
							</c:if>	
												
							<td style="color: blue; ">${donationSignup.recognizeAs}</td>	
							<c:choose>
							    <c:when test="${donationSignup.isCancelled != true && donationSignup.isCancelEnabled == true && donationPaymentFrequency > 1}">
							       <td style="color: blue; margin-left : 20px;"><span class="donationIdHidSpan" style="display:none;">${donationSignup.signupId}</span><span class="cancelDonationCls" style="cursor: pointer; font-size: 11px;">Cancel future payments</span></td>
							    </c:when>    
							    <c:when test="${donationSignup.isCancelled == true && donationPaymentFrequency > 1}">
							       <td style="color: blue; margin-left : 20px; "><span class="donationIdHidSpan" style="display:none;">${donationSignup.signupId}</span><span style="font-size: 11px;">Cancelled</span></td>
							    </c:when>							    
							    <c:otherwise>
							        <td >&nbsp;</td>
							    </c:otherwise>
							</c:choose>	
							<c:choose>
							<c:when test="${donationSignup.draftCycle != null}">
								<td> <a href="#" onclick="updateDraftCycleShow('${donationSignup.signupId}', '${donationSignup.draftCycle}')" >Update Draft Cycle</a> </td>
							</c:when>
						    <c:otherwise>
						        <td >&nbsp;</td>
						    </c:otherwise>
						    </c:choose>
					        <td> <a href="#" onclick="updatePaymentMethodShow('${donationSignup.signupId}')">Update Payment Method</a> </td>
							
							
							
							
							<%-- <c:if test="${donationSignup.isCancelled != true && donationSignup.isCancelEnabled == true && donationPaymentFrequency > 1}">							
								<td style="color: blue; margin-left : 20px;"><span class="donationIdHidSpan" style="display:none;">${donationSignup.signupId}</span><span class="cancelDonationCls" style="cursor: pointer; font-size: 11px;">Cancel future payments</span></td>
							</c:if>
							<c:if test="${donationSignup.isCancelled == true && donationSignup.isCancelEnabled != true && donationPaymentFrequency > 1}">
								<td style="color: blue; margin-left : 20px; "><span class="donationIdHidSpan" style="display:none;">${donationSignup.signupId}</span><span style="font-size: 11px;">Cancelled</span></td>
							</c:if>
							<c:if test="${donationPaymentFrequency <= 1}">
								<td >&nbsp;</td>
							</c:if> --%>
						</tr>										
						<c:forEach var="donation" items="${donationList}" varStatus="status1">
							<c:if test="${donation.signup.signupId == donationSignup.signupId && donation.paymentMode != null && donation.paymentMode ne 'CMWriteOff'}">
								
									<fmt:parseNumber var="donationAmount"  type="number" value="${donation.amount}" />
									<c:if test="${donation.signup.pledgeAmountFrequency != null}">	
										<fmt:parseNumber var="donationPaymentFrequency"  type="number" value="${donation.signup.pledgeAmountFrequency}" />
									</c:if>											
									<c:set var="formattedDonationAmt" value="${fn:split(donationAmount.toString(), '.')}" />
									<tr style="padding-left: 4px;border-radius: 4px;">
										<td>&nbsp;</td>
										<td style="color: blue; padding-left: 4px; padding: 3px;"><fmt:formatDate type="date" pattern="MM/dd/yyyy" value="${donation.paymentDate}" timeZone="Pacific/Guam"/></td>
										<td style="color: blue; ">$${formattedDonationAmt[0]}</td>	
										<td style="color: blue; ">&nbsp;</td>	
										<c:if test="${donationPaymentFrequency != null && donationPaymentFrequency == 1}">											
											<td style="color: blue; ">One Time</td>
										</c:if>	
										<c:if test="${donationPaymentFrequency != null && donationPaymentFrequency > 1}">
											<td style="color: blue; ">${donation.signup.pledgeAmountFrequency} ${donation.signup.signUpPricingOption}</td>											
										</c:if>	
										<td style="color: blue; ">${donation.signup.recognizeAs}</td>
										<td style="">&nbsp;</td>
										<td >&nbsp;</td>
										<td >&nbsp;</td>									
									</tr>
							</c:if>
							<c:if test="${donation.signup.signupId == donationSignup.signupId && donation.paymentMode != null && donation.paymentMode eq 'CMWriteOff'}">
								
									<fmt:parseNumber var="donationAmount"  type="number" value="${donation.amount}" />
									<c:if test="${donation.signup.pledgeAmountFrequency != null}">	
										<fmt:parseNumber var="donationPaymentFrequency"  type="number" value="${donation.signup.pledgeAmountFrequency}" />
									</c:if>											
									<c:set var="formattedDonationAmt" value="${fn:split(donationAmount.toString(), '.')}" />
									<tr style="padding-left: 4px;border-radius: 4px;">
										<td>&nbsp;</td>
										<td style="color: red; padding-left: 4px; padding: 3px;"><fmt:formatDate type="date" pattern="MM/dd/yyyy" value="${donation.paymentDate}" timeZone="Pacific/Guam"/></td>
										<td style="color: red; ">$${formattedDonationAmt[0]}</td>	
										<td style="color: red; ">&nbsp;</td>	
										<c:if test="${donationPaymentFrequency != null && donationPaymentFrequency == 1}">											
											<td style="color: red; ">One Time</td>
										</c:if>	
										<c:if test="${donationPaymentFrequency != null && donationPaymentFrequency > 1}">
											<td style="color: red; ">${donation.signup.pledgeAmountFrequency} ${donation.signup.signUpPricingOption}</td>											
										</c:if>	
										<td style="color: red; ">${donation.signup.recognizeAs}</td>
										<td style="color: red; "><span style="font-size: 9px;"><fmt:formatDate type="date" pattern="MM/dd/yyyy" value="${donation.invoice.dueDate}" timeZone="Pacific/Guam"/> : Cancelled</span></td>
										<td >&nbsp;</td>
										<td >&nbsp;</td>									
									</tr>
							</c:if>								
						</c:forEach>
					
					<tr style="">
						<td colspan="7">&nbsp;</td>
					</tr>
				</c:forEach>
			</table>
			<div id="statusBlock">
				<span class="k-block k-success-colored" id="tcSuccessSpan"></span>
				<span class="k-block k-error-colored" id="tcErrorSpan"></span>
			</div>
</div>

<div id="UpdateDraftCycleDiv" class="k-block" style="background-color: #FFFFFF; margin-left: -71px; padding-top: 20px; padding-left: 20px; width: 838px;">
	<span class="k-button" id="updProfile"	onclick="location.href='donationDonate'" style="margin-left: 5px;">Give	to the Y</span></br></br>
	
	<div class="k-block" align="center" style="width: 300px; padding-top: 10px;">
			<input type="hidden" class="k-textbox" id="signupIdToUpdate" name="signupIdToUpdate" value="" style="width: 50px" maxlength="2">
			<b>Draft Cycle :</b> <span id="draftCycleSpan"></span>
			
			<input type="text" class="k-textbox" id="draftCycleNumber" name="draftCycleNumber" value="" style="width: 50px" maxlength="2">
			<br>
			<br>
			<br>	
	</div>
	<br>
	<div style="width: 300px;" align="center">
			<br><span id="saveDraftCycle" class="k-button" >Save</span>
			<span class="k-button" id="backToRecentDonations" onclick="backToRecentDonations()" style="margin-left: 5px;">Back</span>
	</div>
	<br><br>
	
         <!-- <table style="width: 40%" border="0">
         	<tr align="center">
         		<td colspan="2">Draft Cycle : <span id="draftCycleSpan"></span>
         			<input type="text" class="k-textbox" id="draftCycleNumber" name="draftCycleNumber" value="" style="width: 50px" maxlength="2">
         		</td>'
         		
         	</tr>
            <tr style="vertical-align: top;" align="center">
            	<td><div style="text-align: center;">
					<br><span id="saveDraftCycle" class="k-button" >Save</span>
				</div>
				</td>
				<td>
					<div style="text-align: center;">
						<br><span class="k-button" id="updProfile"	onclick="backToRecentDonations()" style="margin-left: 5px;">Back</span>
					</div>
				</td>
			</tr>
          </table> -->
</div>
