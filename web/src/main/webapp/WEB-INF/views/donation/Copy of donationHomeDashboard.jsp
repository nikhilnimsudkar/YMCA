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
});
</script>

<div id="tabstrip4" class="k-block"
	style="background-color: #FFFFFF; margin-left: -71px; padding: 20px; width: 835px;">	
			<span class="k-button" id="updProfile"	onclick="location.href='donationDonate'" style="margin-left: 5px;">Give	to the Y</span></br></br>
			<table cellpadding="2" cellspacing="0" width="95%" 	style=""  id="donationHistoryTable">
				<span class="head" width="100%" style="margin-left: 4px;">MY RECENT DONATIONS</span>
				<c:if test="${fn:length(donationList) >0 }">
					<tr>
						<th style="font-weight: bold; color: #006B6B; width : 1%; ">&nbsp;</th>
						<th class="head" style="font-weight: bold; color: #006B6B; width : 13%; ">Date</th>
						<th class="head" style="font-weight: bold; color: #006B6B; width : 12%;">Amount</th>
						<th class="head" style="font-weight: bold; color: #006B6B; width : 12%;">Pledge Amt.</th>
						<th class="head" style="font-weight: bold; color: #006B6B; width : 14%;">Payment Freq.</th>
						<th class="head" style="font-weight: bold; color: #006B6B; width : 10%;">Recognized As</th>						
						<c:if test="${donation.signup.signUpPricingOption == 'Monthly'}">
							<th class="head" style="font-weight: bold; color: #006B6B; width : 17%; ">&nbsp;</th>
						</c:if>
						<c:if test="${donation.signup.signUpPricingOption != 'Monthly'}">
							<th class="head" style="font-weight: bold; color: #006B6B; width : 17%; ">&nbsp;</th>
						</c:if>
					</tr>
					
				</c:if>			
				<c:forEach var="donationSignup" items="${donationSignupMap}" varStatus="status">					
						<fmt:parseNumber var="signupDonationAmount"  type="number" value="${donationSignup.finalAmount}" />
						<c:if test="${donationSignup.paymentFrequency != 'Continuous'}">	
							<fmt:parseNumber var="signupDonationPaymentFrequency"  type="number" value="${donationSignup.paymentFrequency}" />
						</c:if>							
						<fmt:parseNumber var="pledgeAmount"  type="number" value="${signupDonationAmount * signupDonationPaymentFrequency}" />					
						<c:set var="formattedDonationAmt" value="${fn:split(signupDonationAmount.toString(), '.')}" />
						<c:set var="formattedPledgeAmount" value="${fn:split(pledgeAmount.toString(), '.')}" />
						
						<c:if test="${status.count % 2 == 0}">															
							<tr style="background-color : #F0FFFF; padding-left: 4px; " class="expandable border-radius5px">
						</c:if>
						<c:if test="${status.count % 2 != 0}">
							<tr style=" padding-left: 4px; " class="expandable sectionHead viewall border-radius5px">
						</c:if>
							<!-- <td><input type="button" value="+" style="font-weight: bold;  font-size: 14px; color: #006B6B;"/></td> -->
							<td><a class="add-child-lnk"><span>+</span></a></td>
							<td style="color: blue; padding-left: 4px; padding: 3px;"><fmt:formatDate type="date" pattern="MM/dd/yyyy" value="${donationSignup.signupDate}" /></td>
							<td style="color: blue; ">$${formattedDonationAmt[0]}</td>		
							<c:if test="${donationSignup.signUpPricingOption == 'One Time'}">	
								<td style="color: blue; ">$${formattedDonationAmt[0]}</td>
								<td style="color: blue; ">One Time</td>
							</c:if>	
							<c:if test="${donationSignup.signUpPricingOption == 'Monthly'}">									
								<c:if test="${donationSignup.paymentFrequency != 'Continuous'}">
									<td style="color: blue; ">$${formattedPledgeAmount[0]}</td>	
									<td style="color: blue; ">Monthly for ${donationSignup.paymentFrequency} Months</td>
								</c:if>
								<c:if test="${donationSignup.paymentFrequency == 'Continuous'}">
									<td style="color: blue; ">$${formattedDonationAmt[0]}</td>
									<td style="color: blue; ">${donationSignup.paymentFrequency}</td>
								</c:if>
							</c:if>	
							<td style="color: blue; ">${donationSignup.recognizeAs}</td>						
							<c:if test="${donationSignup.signUpPricingOption == 'Monthly' && donationSignup.isCancelled != true}">
								<td style="color: blue; margin-left : 20px;"><span class="donationIdHidSpan" style="display:none;">${donationSignup.signupId}</span><span class="cancelDonationCls" style="cursor: pointer; font-size: 11px;">Cancel future payments</span></td>
							</c:if>
							<c:if test="${donationSignup.signUpPricingOption == 'Monthly' && donationSignup.isCancelled == true}">
								<td style="color: blue; margin-left : 20px; "><span class="donationIdHidSpan" style="display:none;">${donationSignup.signupId}</span><span style="font-size: 11px;">Cancelled</span></td>
							</c:if>
							<c:if test="${donationSignup.signUpPricingOption != 'Monthly'}">
								<td style="">&nbsp;</td>
							</c:if>
						</tr>
										
						<c:forEach var="donation" items="${donationList}" varStatus="status1">
							<c:if test="${donation.signup.signupId == donationSignup.signupId}">
								
									<fmt:parseNumber var="donationAmount"  type="number" value="${donation.amount}" />
									<c:if test="${donation.signup.paymentFrequency != 'Continuous'}">	
										<fmt:parseNumber var="donationPaymentFrequency"  type="number" value="${donation.signup.paymentFrequency}" />
									</c:if>							
									<fmt:parseNumber var="pledgeAmount"  type="number" value="${donationAmount * donationPaymentFrequency}" />					
									<c:set var="formattedDonationAmt" value="${fn:split(donationAmount.toString(), '.')}" />
									<c:set var="formattedPledgeAmount" value="${fn:split(pledgeAmount.toString(), '.')}" />
																		
									<tr style="   padding-left: 4px;   border-radius: 4px;">
										<td>&nbsp;</td>
										<td style="color: blue; padding-left: 4px; padding: 3px;"><fmt:formatDate type="date" pattern="MM/dd/yyyy" value="${donation.paymentDate}" /></td>
										<td style="color: blue; ">$${formattedDonationAmt[0]}</td>		
										<c:if test="${donation.signup.signUpPricingOption == 'One Time'}">	
											<td style="color: blue; ">$${formattedDonationAmt[0]}</td>
											<td style="color: blue; ">One Time</td>
										</c:if>	
										<c:if test="${donation.signup.signUpPricingOption == 'Monthly'}">									
											<c:if test="${donation.signup.paymentFrequency != 'Continuous'}">
												<td style="color: blue; ">$${formattedPledgeAmount[0]}</td>	
												<td style="color: blue; ">Monthly for ${donation.signup.paymentFrequency} Months</td>
											</c:if>
											<c:if test="${donation.signup.paymentFrequency == 'Continuous'}">
												<td style="color: blue; ">$${formattedDonationAmt[0]}</td>
												<td style="color: blue; ">${donation.signup.paymentFrequency}</td>
											</c:if>
										</c:if>	
										<td style="color: blue; ">${donation.signup.recognizeAs}</td>
										<td style="">&nbsp;</td>						
										<%-- <c:if test="${donation.signup.signUpPricingOption == 'Monthly' && donation.isCancelled != true}">
											<td style="color: blue; margin-left : 20px;"><span class="donationIdHidSpan" style="display:none;">${donation.signup.signupId}</span><span class="cancelDonationCls" style="cursor: pointer; font-size: 11px;">Cancel future payments</span></td>
										</c:if>
										<c:if test="${donation.signup.signUpPricingOption == 'Monthly' && donation.isCancelled == true}">
											<td style="color: blue; margin-left : 20px; border-right: thin solid;"><span class="donationIdHidSpan" style="display:none;">${donation.signup.signupId}</span><span style="font-size: 11px;">Cancelled</span></td>
										</c:if>
										<c:if test="${donation.signup.signUpPricingOption != 'Monthly'}">
											<td style="border-right: thin solid;">&nbsp;</td>
										</c:if> --%>
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