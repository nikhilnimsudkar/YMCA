<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>


<body>

<%@ include file="../../layouts/include_taglib.jsp"%>
<script src="<%=contextPath %>/resources/js/sha512.js"></script>
<script src="<%=contextPath %>/resources/js/payment/luhn.js"></script>
<script src="<%=contextPath %>/resources/js/payment/view.js"></script>
<script src="<%=contextPath %>/resources/js/jquery.validate.min.js"></script>
<script src="<%=contextPath %>/resources/js/maskedinput.min.js"></script>
<script src="<%=contextPath %>/resources/js/app/payment_new.js"></script>


<script id="terms-conditions-ErrorBox" type="text/x-kendo-template">
    <p class="error-message-p">Please agree the terms and conditions before Proceding</p>
	
    <div class="confirmbutton" align="center"><button class="confirm-terms-conditions k-button" style=" width: 35%;">Ok</button>   
</script>

<script id="plan-End-ErrorBox" type="text/x-kendo-template">     
	<p class="error-message-p">Please select appropriate Frequency and Number of Installments such that Billing End Date should not be greater than <span id="planEndDateSpanId"><fmt:formatDate type="date" pattern="MM/dd/yyyy" value="${annualDonationItemDetailObj.planEndDate}" /></span></p>
	
    <div class="confirmbutton" align="center"><button class="confirm-plan-end k-button" style=" width: 35%;">Ok</button>  
</script>

<script id="billing-start-ErrorBox" type="text/x-kendo-template">     
	<p class="error-message-p">Billing Start Date should not be greater than <span id="planEndDateSpanId"><fmt:formatDate type="date" pattern="MM/dd/yyyy" value="${annualDonationItemDetailObj.planEndDate}" /></span></p>
	
    <div class="confirmbutton" align="center"><button class="confirm-billing-date k-button" style=" width: 35%;">Ok</button>  
</script>

<script id="billing-date-ErrorBox" type="text/x-kendo-template">     
	<p class="error-message-p">Billing Date should not be greater than <span id="planEndDateSpanId"><fmt:formatDate type="date" pattern="MM/dd/yyyy" value="${annualDonationItemDetailObj.planEndDate}" /></span></p>
	
    <div class="confirmbutton" align="center"><button class="confirm-billingDate k-button" style=" width: 35%;">Ok</button>  
</script>

<script id="billing-setupError-ErrorBox" type="text/x-kendo-template">     
	<p class="error-message-p">Please generate Billing Setup before proceeding.</p>
	
    <div class="confirmbutton" align="center"><button class="confirm-billingsetup k-button" style=" width: 35%;">Ok</button>  
</script>


<%@ include file="../../layouts/include_taglib.jsp" %>
<div id="main">

<div id="content" style="margin-bottom:30px; margin-top: -5px;">
<div class="k-loading-mask"
		style="width: 100%; height: 1000px; position: absolute; top: 150px; left: 0px; display: none; z-index: 9999">
	<span class="k-loading-text">Please wait...</span>
	<div class="k-loading-image">
		<div class="k-loading-color"></div>
	</div>
</div>
		<div id="contentFormDiv" >
		<form:form commandName="signup" class="bootstrap-frm cmxform" id="createDonationForm" method="post" action="annualDonationDonate" style="padding:20px; max-width: 800px;">  
			<div>
				  <!-- <h2 style="margin-left:270px; ">
				  		<span>Every gift makes a difference.<br /></span>
				  		<span>Every one has role to play.<br /></span>
				  		<span>Together, we can achieve so much more.<br /></span>					  		  		
				  </h2> -->
				  <input type="hidden" id="loggedInAccountId" value="${account.accountId }">
				  <input type="hidden" id="paymentOrderId" name="paymentOrderId" value=""/>
				  <input type="hidden" id="paymentMethodId" name="paymentMethodId" value=""/>	
				  <input type="hidden" style="position : absolute; left : -1000; top : -1000px;" id="todayKendoDate" name="todayKendoDate" value=""/>
				  <c:if test="${annualDonationItemDetailObj != null && annualDonationItemDetailObj.planEndDate != null}">
					  <fmt:formatDate type="date" pattern="MM" var="annualDonationDueDtMonth" value="${annualDonationItemDetailObj.planEndDate}" />
					  <fmt:formatDate type="date" pattern="dd" var="annualDonationDueDtDate" value="${annualDonationItemDetailObj.planEndDate}" />
					  <fmt:formatDate type="date" pattern="yyyy" var="annualDonationDueDtYear" value="${annualDonationItemDetailObj.planEndDate}" />
				  </c:if>	
				  <input type="text" name="itemDetailId" id="donationTCItemDetailId" value="${annualDonationItemDetailObj.id }" style="width: 250px;display : none;">
				  <input type="text" id="associatedItemDetailId" value="${annualDonationItemDetailObj.id }" style="width: 250px;display : none;">
				  		  
				  <h4><span style="float:right;"><span style="color:red">*</span> Required Fields.<br></span></h4>
				  <h3><span>My Donation<br /></span></h3>	
				  
				  <span class="label-header1">Donation Amount<span style="color:red">*</span><br /></span>		
				  <input type="radio" name="finalAmountRadio" value="1500" > $1,500
				  <input type="radio" name="finalAmountRadio" value="500"> $500 
				  <input type="radio" name="finalAmountRadio" value="250"> $250
				  <input type="radio" name="finalAmountRadio" value="50"> $50
				  <c:if test="${param != null && param.opptyId != null}">
				  <input type="radio" name="finalAmountRadio" id="donationAmtOther"  checked="checked" value="Other" > Other: 
				  </c:if>
				  <c:if test="${param != null && param.opptyId == null}">
				  <input type="radio" name="finalAmountRadio" id="donationAmtOther"   value="Other" > Other: 
				  </c:if>
				  <%-- <form:input cssClass="form-control" path="finalAmountRadio" title="Please enter your First Name" name="finalAmountRadio" id="firstName" maxlength="50" placeholder="First Name" value="" /> --%>
				  <input type="text" name="finalAmountRadio" id="donationAmtOtherTxt" style="width: 250px;" onkeypress="return isNumberKey(event)" value="${donationOppty.revenue }">
				  <span id="donationAmtErr" style="margin-left : 8px; font-style: italic; color: red; display:none;"></span>				  
				  
				  
				  <br />				 
					  <%-- <table border="0" class="" width="100%">
					  		<tr>
					  			<td >
					  				<span class="label-header1">Payment Frequency<span style="color:red">*</span></span>
					  			</td>				  			
					  			
					  		</tr>
					  		<tr>
					  			<td>
					  			 	<c:forEach var="pricingRule" items="${pricingRuleList}" varStatus="count">
					  			 		<c:if test="${pricingRule != null && pricingRule.priceOption == 'One Time'}">				  		
				  							<input type="radio"  name="signUpPricingOption" class="donationFrequencyStr" value="One Time"> One Time
				  						</c:if>
				  						<c:if test="${pricingRule != null && pricingRule.priceOption == 'Monthly'}">				  		
				  							<input type="radio" name="signUpPricingOption" class="donationFrequencyStr" value="Monthly"> Monthly
							  				<select id="donationFrequencyMonthsSel" name="paymentFrequency" style="width : 170px; display : none; margin-left : 5px;">
										  		<option value="0">--Select No of Months--</option>				  		
										  		<option>1</option>
										  		<option>2</option>
										  		<option>3</option>
										  		<option>4</option>
										  		<option>5</option>
										  		<option>6</option>
										  		<option>7</option>
										  		<option>8</option>
										  		<option>9</option>
										  		<option>10</option>
										  		<option>11</option>
										  		<option>12</option>
										  		<option selected="selected" value="Continuous">Continuous</option>				  		
										  </select>
				  						</c:if>					  													  	
									</c:forEach>
								  <span id="donationFreqErr" style="margin-left : 8px; font-style: italic; color: red;display:none; "></span>
					  			</td>				  							  			
					  		</tr>				  		
					  </table> --%>
				  
				  <table border="0" class="" id="billingSetUpTableId" width="50%">
				  		<tr>
				  			<td><span class="label-header1">Billing Setup</span></td>	
				  			<td>&nbsp;</td>				  			
				  		</tr>
				  		<tr id="billingStartDateTr" style="display:none;">
				  			<td>Billing Start Date</td>	
				  			<td><input type="text" placeholder="Billing Start Date" name="nextBillDate" id="billingStartDate" value="" style="margin: 0px; width : 240px;" /></td>				  			
				  		</tr>
				  		<tr>
				  			<td>Frequency</td>
				  			<td>
				  				<select id="billingFrequency" name="signUpPricingOption" style="width :  100%;">
				  					<!-- <option value="Weekly">Weekly</option>
				  					<option value="Semi-Monthly">Semi-Monthly</option>
				  					<option value="Monthly">Monthly</option>
				  					<option value="Quarterly">Quarterly</option>
				  					<option value="Annual">Annual</option>   -->
				  					 <c:forEach var="pricingRule" items="${pricingRuleList}" varStatus="count">
									  		<option value="${pricingRule.priceOption }">${pricingRule.priceOption }</option>
									  </c:forEach>					  		
							  	</select>
				  			</td>			  			
				  		</tr>
				  		<tr>
				  			<td>Number of Installments</td>
				  			<td>
				  				<select id="numberOfInstallments" name="pledgeAmountFrequency" style="width : 100%;">  					  		
							  	</select>
				  			</td>			  			
				  		</tr>
				  		
				  		<c:if test="${AutomaticPaymentRequired != null && AutomaticPaymentRequired == 'No'}">				  		
				  		<tr>
				  			<td>Billing Type</td>
				  			<td>
				  				<select id="billingOption" name="billingOption" style="width : 100%;">
				  					<option value="Automatic">Automatic Payment</option>
				  					<option value="Manual">Statement Billing</option>				  										  		
							  	</select>
				  			</td>			  			
				  		</tr>
				  		</c:if>
				  		<tr>
				  			<td>Balance Due</td>
				  			<td><input type="text" placeholder="Balance Due" name="balanceDue" id="balanceDue" value="${donationOppty.revenue }" style="" readonly="readonly"/></td>			  			
				  		</tr>
				  		<tr>
				  			<td>Down Payment<br /><br /><span style="color : blue;">(Will be charged now)</span></td>
				  			<td><input type="text" placeholder="Down Payment" class="" name="downPayment" id="downPayment" value="" style=""/></td>			  			
				  		</tr>
				  		<tr>
				  			<td>Billing Amount</td>
				  			<td><input type="text" placeholder="Billing Amount" name="billingAmount" id="billingAmount" value="${donationOppty.revenue }" style="" readonly="readonly"/></td>			  			
				  		</tr>
				  		<tr>
				  			<td colspan="2" align="center"><span class="k-button" id="generateBillingSetup" onclick="return false;" style="margin-left:5px; text-shadow: none;">Generate Billing Setup</span></td>				  						  			
				  		</tr>			  					  		
				  </table>
				  
				  <table border="0" class="borderTableClass" id="billingInstalmentsTableId" width="100%" style="display : none;">
				  		<tr>
				  			<td colspan="3"><span class="label-header1">Billing Setup</span></td>
				  		</tr>
				  		<tr>
				  			<th class="head" style="font-weight: bold; color: #006B6B; width : 12%">Installment #</th>	
				  			<th class="head" style="font-weight: bold; color: #006B6B; width : 43%">Billing Date</th>	
				  			<th class="head" style="font-weight: bold; color: #006B6B; width : 43%">Billing Amount</th>			  			
				  		</tr>
				  		<tr>
				  			<td colspan="3">
				  				<table width="102%">
				  					<tbody id="billingInstalmentsTbody">				  					
				  					</tbody>
				  				</table>
				  			</td>
				  		</tr>				  						  				  		
				  </table>	 
				  <input type="text" style="display:none;" name="finalAmount" id="donationAmtStr" value="${donationOppty.revenue }">
				  <input type="text" style="display:none;" name="donationPledgeTotalAmt" id="donationPledgeTotalAmt" value="${donationOppty.revenue }">
				  <input type="text" style="display:none;" name="donationFrequency" id="donationFreq" value="">
				  <input type="text" style="display:none;" name="donationOpptyId" id="donationOpptyId" value="${donationOppty.optyId }">	
				  <span id="donationFreqMonthlySelMsgSpan" style="color : blue; display : none;">
				  	<span>Note : i) Your card information will be stored in your profile.</span>&nbsp;&nbsp;&nbsp;
				  	<span id="amtChanrgedNoteSpan"></span>&nbsp;&nbsp;&nbsp;
				  	<span id="amtPledgingNoteSpan"></span>
				  	<br /><br />
				  </span>	  	  
				   
				  <!-- <span style="display : none;">
				  		<span>If a YMCA campaigner asked you to make this donation, please enter his/her name here:</span>
				  		<input type="text" name="campaigner" style="width : 50%">
				   </span> -->
				   
				   <!-- <span style="float : left;"><span style="float : left;"><input type="checkbox" autocomplete="off" name="donationGiftChk"></span><span> Would you like to make this gift in honor or in memory of someone? If so, please check this box. You will be asked for the appropriate information on the next screen.</span></span><br /><br /> -->
				   <table border="0" class="table-33td" width="100%">
				  		<tr>
				  			<td >
				  				<span class="label-header1"><span><input type="checkbox" name="employerMatching" value="Yes"></span><span>Employer matching</span></span>
				  			</td>
				  			<td >
				  				<span class="label-header1">Apply my gift to the following YMCA branch: </span>
				  			</td>			
				  			<td>
				  				<span style="">
							  		<span class="label-header1">If a YMCA campaigner asked you to make this donation, please enter his/her name here:</span>		  		
							   </span>
				  			</td>	  			
				  		</tr>
				  		<tr>
				  			<td >
				  				<span style="">
						   			<select id="employerMatchingSelect" name="employer" style="width : 260px; ">
						   				<option value="-1">--Select Employer--</option>
									  <c:forEach var="employer" items="${employerLst}" varStatus="count">
									  		<option value="${employer.accountId }">${employer.name }</option>
									  </c:forEach>	
									  <option value="0">Other</option>	
									</select>
						   		</span>
				  			</td>
				  			<td >
				  				<c:forEach var="revenueItem" items="${donationOppty.opptyRevenue}" varStatus="count">					  											
									<c:choose>
										<c:when test="${ revenueItem != null && revenueItem.itemDetail != null && revenueItem.itemDetail.location != null && revenueItem.itemDetail.location.id != null}">
											<c:set var="associatedLocationId" value="${revenueItem.itemDetail.location.id }" />																					
										</c:when>
										<c:otherwise>											
										</c:otherwise>
									</c:choose>																																				
								</c:forEach>
				  				<select name="specialRequest" id="donationBranch" style="width:200px;" ><br />
									<option value="0">--Select Location--</option>
									<c:forEach var="location" items="${locations}" varStatus="count">									
										<c:if test="${ associatedLocationId != null && location.id != null && associatedLocationId eq location.id }">
											<option value="${location.recordName }_${location.itemId }" selected="selected">${location.recordName }</option>
										</c:if>
										<c:if test="${ associatedLocationId != null && location.id != null && associatedLocationId ne location.id }">
											<option value="${location.recordName }_${location.itemId }">${location.recordName }</option>
										</c:if>
										<%-- <c:if test="${ location.recordName != 'All Branches' && location.recordName != 'Bay Area' && location.branch == 'true' && location.recordName == 'El Camino YMCA' }">
											<option value="${location.recordName }" selected="selected">${location.recordName }</option>
										</c:if>
										<c:if test="${ location.recordName != 'All Branches' && location.recordName != 'Bay Area' && location.branch == 'true' && location.recordName != 'El Camino YMCA'}">
											<option value="${location.recordName }">${location.recordName }</option>
										</c:if> --%>
									</c:forEach>	
									<option value="Other">Other Branch</option>					
								</select>				
								<input type="text" placeholder="YMCA Other branch" name="specialRequest" id="YmcaBranchTxt" style="width: 150px; display: none; margin-left : 5px;" />
				  			</td>
				  			<td>
				  				<c:if test="${param != null && param.opptyId != null}">
					  				<select id="campaignerSelect" name="campaigner" style="width : 120px; ">						   			
										<c:forEach var="campaigner" items="${donationOppty.opptyContact}" varStatus="count">
											<c:if test="${ campaigner != null && campaigner.contact != null && campaigner.role == 'CAMP'}">
												<option value="${campaigner.contact.contactId }">${campaigner.contact.firstName }</option>	
											</c:if>
											<%-- <c:if test="${ campaigner != null && campaigner.contact != null && campaigner.role != 'CAMP'}">
												<option value="${campaigner.contact.contactId }">${campaigner.contact.firstName }</option>	
											</c:if>	 --%>																																
										</c:forEach>
										<option value="Other">Other</option>										
									</select>&nbsp;&nbsp;
									<input type="text" name="otherCampaigner" id="campaignerSelectOther" style="width: 100px;  font-size: 11px; display:none;" placeholder="Other Campaigner">
									<%-- <span style="display : none;" id="campaignerIdOpp">${donationOppty.contact_c }</span> --%>
								</c:if>
								&nbsp;																							
							</td>						  				  			
				  		</tr>		
				  		<tr>
				  			<td >
				  				<span id="donationEmpMatchErr" style="margin-left : 8px; font-style: italic; color: red; display:none;"></span>
				  			</td>
				  			<td >
				  				&nbsp;
				  			</td>	
				  			<td >
				  				&nbsp;
				  			</td>					  					  			
				  		</tr>		  		
				  </table>		   
				   
				   	<div id="employerMatchInfoDiv" style="display:none;">
						<div>
							 <table border="0" width="100%" id="employerMatchInfoTable">
								<tr>
									<td><input type="text" maxlength="20" size="15" value="" id="companyName" name="companyName" placeholder="Company name" style="width: 200px;"></td>
									<td >&nbsp;</td>
									<td><input type="text" maxlength="50" size="15" value="" id="companyAddress" name="companyAddress" placeholder="Company Address" style="width: 200px;"></td>
									<td >&nbsp;</td>					 		
								</tr>
								<tr>
									<td><input type="text" placeholder="Company Phone" name="companyPhone" id="companyPhone" value=""  style="width: 200px;"/></td>
									<td >&nbsp;</td>
									<td><input type="text" placeholder="Employer Contact Email address" name="employeeEmailId" id="employeeEmailId" value="" style="width: 200px;"/></td>
									<td >&nbsp;</td>					 		
								</tr>
							</table>
							
						</div>
					</div>
					
					<table border="0" class="table-33td" width="100%">
				  		<tr>
				  			<td >
				  				<span class="label-header1">In Honor Of</span>
				  			</td>
				  			<td >
				  				<span class="label-header1" style="display : none;">Donation Date</span>
				  			</td>				  			
				  			<td >
				  				<span class="label-header1" style="display : none;">Planned Gift</span>
				  			</td>
				  			
				  		</tr>
				  		<tr>
				  			<td >
				  				<input type="text" placeholder="In Honor Of" name="inHonorOf" id="inHonorOf" value="" style="width:220px;" />
				  			</td>	
				  			<td >
				  				<input type="text" placeholder="Donation Date" name="donationDate" id="donationDate" value="" style="width:220px; margin: 0px; display : none;" />
				  			</td>				  				
				  			<td >
				  				<input type="text" placeholder="Planned Gift" name="donationDate" id="plannedGift" value="" style="width:220px; display : none;" />
				  			</td>		  			
				  		</tr>				  		
				  </table>
				  <span></span> 
				   
				   <h2><span>My Information</span></h2>		
				  <!--  <span><input type="radio"  value="" name="recognizeAsRadioInput">&nbsp;<span class="recognizedAsSpanCls">Anonymous</span></span>				   			
				   <span><input type="radio"  value="" name="recognizeAsRadioInput" id="recognizedAsName"><span class="recognizedAsSpanCls"><span id="recognisedAsFName"></span>&nbsp;<span id="recognisedAsLName"></span></span></span> -->
				   	
				   <!-- <table border="0">
								<tr>
									<td>
										<input type="text" placeholder="Contact Name" name="recognizeAs" id="recognizeAsOtherTxt" value="" style="width: 100px; display: none;" />
									</td>
									<td></td>
								</tr>
							</table> 
							 <input type="text" id="recognizedAsHidInput" name="recognizeAs" value="" style="display: none;" /> -->
				   
				   	   
				   <table border="0" width="100%" id="billingInfoTable">
						<tr>
							<td><input type="text" maxlength="20" size="15" value="${usInfo.firstName}" id="donerFirstName" name="donerFirstName" placeholder="First Name" style="width: 200px;"></td>
							<td >&nbsp;</td>
							<td><input type="text" maxlength="50" size="15" value="${usInfo.lastName}" id="donerLastName" name="donerLastName" placeholder="Last Name" style="width: 200px;"></td>
							<td >&nbsp;</td>					 		
						</tr>
						
						<c:if test="${!pageContext.request.userPrincipal.isAuthenticated()}">
				   			<tr>
								<!-- <td><input type="text" maxlength="20" size="15" id="dateOfBirth" placeholder="D.O.B" name="dateOfBirth" style="width: 200px;"></td> -->
								<td> 
									<span>
										<span class="">Date of Birth (MM/DD/YYYY) : </span><br />
										<span>
											<span>
												<select name="dobMonth" id="dobMonth" style="width :90px;">													
													<option value="1">January</option>
													<option value="2">February</option>
													<option value="3">March</option>
													<option value="4">April</option>
													<option value="5">May</option>
													<option value="6">June</option>
													<option value="7">July</option>
													<option value="8">August</option>
													<option value="9">September</option>
													<option value="10">October</option>
													<option value="11">November</option>
													<option value="12">December</option>
												</select>
											</span>
											<span>
												<select name="dobDate" id="dobDate" style="width :45px;">													
													<option value="1">1</option>
													<option value="2">2</option>
													<option value="3">3</option>
													<option value="4">4</option>
													<option value="5">5</option>
													<option value="6">6</option>
													<option value="7">7</option>
													<option value="8">8</option>
													<option value="9">9</option>
													<option value="10">10</option>
													<option value="11">11</option>
													<option value="12">12</option>
													<option value="13">13</option>
													<option value="14">14</option>
													<option value="15">15</option>
													<option value="16">16</option>
													<option value="17">17</option>
													<option value="18">18</option>
													<option value="19">19</option>
													<option value="20">20</option>
													<option value="21">21</option>
													<option value="22">22</option>
													<option value="23">23</option>
													<option value="24">24</option>
													<option value="25">25</option>
													<option value="26">26</option>
													<option value="27">27</option>
													<option value="28">28</option>
													<option value="29">29</option>
													<option value="30">30</option>
													<option value="31">31</option>
												</select>
											</span>
											<span>
												<select name="dobYear" id="dobYear" style="width :70px;">													
												</select>
											</span>											
										</span>
									</span>
								</td>
								<td >&nbsp;</td>
								<td>
									<span >
										<span><span><input type="radio" name="gender" style="width : 15px;" value="Male" id="genderM" title="Please select gender"/></span><span style="margin-left : 5px;">Male</span></span>
										<span><span><input type="radio" name="gender" style="width : 15px;" value="Female" id="genderF" title="Please select gender" /></span><span style="margin-left : 5px;">Female</span></span>
									</span>								
								</td>
								<td >&nbsp;</td>					 		
							</tr>
				   		</c:if>
				   		<tr>
				   			<td><input type="text" placeholder="E-mail Address" name="email" id="email" value="${usInfo.emailAddress}" style="width: 200px;"/><br /></td>
							<td >&nbsp;</td>
							<td><input type="text" placeholder="Preferred Phone" name="preferredPhone" id="preferredPhone" value="${usInfo.formattedPhoneNumber}" style="width: 200px;"/></td>
							<td >&nbsp;</td>												 		
						</tr>
						<tr>							
							<td colspan="2"><span style="float : left;"><span><input type="checkbox" name="contact.doNotEmail" checked=""></span><span>Please add me to your e-mail distribution list.</span></span><br /><br /></td>
							<td >&nbsp;</td>
							<td >&nbsp;</td>												 		
						</tr>
					</table>
												
					<span id="recognizedAsSpan" style="display : none;">
				   		<span class="label-header1">Recognized as<span style="color:red">*</span> : </span>
				   		<span>				   			
				   			<span><input type="radio"  value="Anonymous" name="recognizeAsRadioInput">&nbsp;<span class="recognizedAsSpanCls">Anonymous</span></span>				   			
				   			<span><input type="radio"  value="" name="recognizeAsRadioInput" id="recognizedAsName"><span class="recognizedAsSpanCls"><span id="recognisedAsFName"></span>&nbsp;<span id="recognisedAsLName"></span></span></span>
				   			<span><input type="radio" value="" name="recognizeAsRadioInput" id="recognizedAsContactName"></span>&nbsp;
				   							   			
				   		</span>&nbsp;&nbsp;		   		
				   		<select id="contactLstSelect"  style="width : 200px;">
				   			<option>--Select Contact--</option>
				   			<c:if test="${pageContext.request.userPrincipal.isAuthenticated()}">
								  <c:forEach var="contact" items="${contactLst}" varStatus="count">
								  		<option value="${contact.contactId }">${contact.firstName }&nbsp;${contact.lastName }</option>
								  </c:forEach>
							</c:if> 
							<option value="2">Other</option>		
						</select>
						<input type="text" placeholder="Contact Name" name="recognizeAsTextName" onkeypress="return contactNameChange(event)" id="recognizeAsOtherTxt" style="width: 100px; display: none; margin-left : 5px;" />					
						<span id="donationContactSelErr" style="margin-left : 8px; font-style: italic; color: red; display:none;"></span>
						<input type="text" id="recognizedAsHidInput" name="recognizeAs" value="" style="display: none;" />	
				   </span><br />
				  <!-- <span>
				  		<input type="radio" value="MC" name="cardTypeStr" autocomplete="off"> MasterCard&nbsp;
				  		<input type="radio" value="VS" name="cardTypeStr" autocomplete="off"> Visa&nbsp;
				  		<input type="radio" value="DC" name="cardTypeStr" autocomplete="off"> Discover&nbsp;	<br />	
				  		<input type="text" style="display:none;" name="cardType" id="cardType" value="">				
						<input type="text" autocomplete="off" maxlength="20" size="16" value="" id="cardNumber" name="cardNumber" placeholder="Credit Card Number" style="width: 230px;">
						<input type="password" autocomplete="off" maxlength="5" size="4" value="" id="cscNumber" name="cscNumber" placeholder="CSC" style="width: 230px;">
						<select size="1" id="expMonth" name="expMonth" style="width: 237px;" >
							<option value="0">--Exp Mo--</option>
							<option value="01">01</option>
							<option value="02">02</option>
							<option value="03">03</option>
							<option value="04">04</option>
							<option value="05">05</option>
							<option value="06">06</option>
							<option value="07">07</option>
							<option value="08">08</option>
							<option value="09">09</option>
							<option value="10">10</option>
							<option value="11">11</option>
							<option value="12">12</option>
						</select>						
						<select size="1" name="expYear" id="createDonationExpYr" style="width: 237px;">
							<option value="0">--Exp Yr--</option>							
						</select><br />
						<input type="text" maxlength="50" size="30" value="" id="nameOnCard" name="nameOnCard" placeholder="Name (as it appears on card)">
				</span> -->
				<div id="purchase_info" >
					<c:if test="${pageContext.request.userPrincipal.isAuthenticated()}">
						<div style="  margin-left: -18px; margin-bottom: 8px; font-weight: bold;" class="label-header1">
							<label >Payment Method :</label>
						</div>
						<div>
							<select id="paymentInfoRadio" name="paymentInfoRadio" style="width:350px;  margin-left: -18px;">
								
								<c:forEach var="paymentInfo" items="${paymentInfoLst}" varStatus="count">
									<c:choose>
									<c:when test="${ paymentInfo[4] == 'CREDIT' }">
										<option value="${ paymentInfo[4] }__${ paymentInfo[0] }__${ paymentInfo[8] }"> ${ paymentInfo[3] } ${paymentInfo[2] } ${ paymentInfo[5] }/${ paymentInfo[6] } </option>
									</c:when>
									<c:when test="${ paymentInfo[4] == 'ACH' }">
										<option value="${ paymentInfo[4] }__${ paymentInfo[0] }__${ paymentInfo[8] }"> ${ paymentInfo[3] }, ${paymentInfo[2] } </option>
									</c:when>
									</c:choose>								
								</c:forEach>							
								<option value="New">Add New Card</option>		
								<c:if test="${sessionScope.agentUid != null}">
							         <option value="Cash">Cash</option>
							         <option value="Check">Check</option>
							         <option value="None">None</option>
							         <option value="Stock">Stock</option>
						        </c:if>				
							</select>
						</div>
					</c:if>
						
					<div id="addcard" style="margin-top:10px; margin-bottom:10px; height:44px; display:none;">
						  
							
							<div style="height:50px;">
								<div class="addcarddiv">
									<div style="height:20px;">Name on card</div>
									<div><input type="text" style="height:25px;" class="k-textbox" placeholder="Full Name on Card" name="fullName" id="fullName" value="" tabIndex="1" maxlength="50" ></div>
								</div>
								<div class="addcarddiv">
									<div style="height:20px; ">Card Number</div>
									<div>
										<input autocomplete="off" type="text" style="height:25px; width: 130px;" class="k-textbox" placeholder="Card Number" name="cardNum" id="cardNum" value="" tabIndex="2" maxlength="16" onchange="handleCCTyping(this.form, event);" onkeyup="handleCCTyping(this.form, event);">
										<img src="resources/img/payment_credit_cards/cards/invalid.png" name="cardimage" ID="cardimage" width="40"  height="22" align="top">
									</div>
								</div>
								<div class="addcarddiv">
									<div style="height:20px;">Expiration date</div>
									<div>
										<span>
											<select id="addCardExpirationMonthSelect" name="addCardExpirationMonthSelect" style="width:50px;">
							                      <option value="01">01</option>
							                      <option value="02">02</option>
							                      <option value="03">03</option>
							                      <option value="04">04</option>
							                      <option value="05">05</option>
							                      <option value="06">06</option>
							                      <option value="07">07</option>
							                      <option value="08">08</option>
							                      <option value="09">09</option>
							                      <option value="10">10</option>
							                      <option value="11">11</option>
							                      <option value="12">12</option>
											</select>
										</span>
										<span>
											<select id="addCardExpirationYearSelect" name="addCardExpirationYearSelect" style="width:70px;">
											</select>
										</span>										 
									</div>
								</div>
								<div class="addcarddiv">
									<div style="height:20px;">Security Code</div>
									<div><input  autocomplete="off" type="password" style="height:25px;" class="k-textbox" placeholder="Security Code" name="securityCode" id="securityCode" value="" tabIndex="3" maxlength="4"></div>
								</div>
							</div><br />
							
							<div style="height:50px;">
								<div class="addcarddiv">
									<div style="height:20px;">Address Line 1</div>
									<div><!-- <textarea placeholder="Billing AddressLine1" name="billingAddressLine1" id="billingAddressLine1" class="k-textbox" style="width: 279px; height: 46px;"></textarea> -->
										<input type="text" class="k-textbox" style="height:25px;" placeholder="AddressLine1" name="billingAddressLine1" id="billingAddressLine1" value="" tabIndex="4">
									</div> 
								</div>
								<div class="addcarddiv">
									<div style="height:20px;">Address Line 2</div>
									<div><input type="text" class="k-textbox" style="height:25px;" placeholder="AddressLine2" name="billingAddressLine2" id="billingAddressLine2" value="" tabIndex="5"></div> 
								</div>
								<div class="addcarddiv">
									<div style="height:20px;">City</div>
									<div><input type="text" class="k-textbox" style="height:25px;" placeholder="City" name="billingCity" id="billingCity" value="" tabIndex="6"></div> 
								</div>
								<div class="addcarddiv" style="width:45px;">
									<div style="height:20px;">State</div>
									<div><input type="text" class="k-textbox" style="height:25px;" placeholder="State" name="billingState" id="billingState" value="" style="width:45px;" tabIndex="7"></div> 
								</div>
								<div class="addcarddiv" style="width:60px">
									<div style="height:20px;">Zip</div>
									<div><input type="text" class="k-textbox" style="height:25px;" placeholder="ZipCode" name="billingZip" id="billingZip" value="" style="width:60px;" tabIndex="8"></div> 
								</div>
								
							</div><br />
							
							<div style="height:50px;">
								
								<div id="savecardcheckbox" class="addcarddiv" style="width:124px">
									<div><input type="checkbox" class="SaveCard" name="SaveCard" id="SaveCard">&nbsp;&nbsp;Save Card</div>
								</div>
								<div id="nicknamediv" class="addcarddiv" >
									<div style="height:20px;">Nick Name</div>
									<div><input type="text" style="height:25px;" class="k-textbox" placeholder="Nick Name" name="nickName" id="nickName" value="" tabIndex="9"></div> 
								</div>
							</div>
						
					</div>
					<div id="statusBlock-payment" style="display: none; margin-top: 145px;">
						<span class="k-block k-success-colored"	id="tcloginSuccessSpan-payment" style="display: none"></span>
						<span class="k-block k-error-colored" id="tcloginErrorSpan-payment" style="display: none"></span>
					</div>
				</div>
				
				
				
				<br /><br />
				<c:if test="${annualDonationItemDetailObj != null && annualDonationItemDetailObj.waiversAndTC != null && empty agentUid && annualDonationItemDetailObj.waiversAndTC.needTC_c == 'Yes' } ">
					<div id="TAndCHoldMembershipDiv" class="termsAndConditionParentDiv" style="width : auto !important;">
						<div class="termsAndConditionContent" style="padding: 25px;">
							
						</div>									
					</div>
					<input type="checkbox" name="chkTermsAndCondition" style="margin-top: 10px;">&nbsp;I accept terms and conditions
					<span style="display : none;" id="commonTAncHidden">${annualDonationItemDetailObj.waiversAndTC.tcDescription }</span>
				</c:if>
				  <div id="statusBlock">
					<span class="k-block k-success-colored" id="addDonationSuccessSpan"></span>
					<span class="k-block k-error-colored" id="addDonationErrorSpan"></span>
				  </div>
					
				  <span id="createDonationButton" class="k-button" style="width: 150px; text-shadow: none;">Donate Now ></span>
			</div>
			<div id="PaymentInformationBackLnk" align="right">
	        		<a href="/ymca-web/donationHome">Back to Donation Information</a>
	        </div>
		</form:form>
		<iframe src="<%=contextPath %>/viewPaymentForm" height="1px" width="1px" id="childIframeId" style=" position:absolute; bottom:-10px; left:-10px;"></iframe>
		<div id="form_container" >
			<!-- <iframe>			
			<form name="paymentPage" id="paymentPage" class="tcaspr"  method="post" action="https://testapp1.jetpay.com/jetdirect/post/cc/process_cc.php">    
			   <input id="name" name="name" class="element text medium" maxlength="50" value="" />
			   <input id="cardNum" name="cardNum" class="element text medium" type="text" maxlength="16" onchange="handleCCTyping(this.form, event);" onkeyup="handleCCTyping(this.form, event);" value="" autocomplete="off"/>
			   <select autocomplete="off" class="element select small" id="expMo" name="expMo"> 
					<option value=""></option>
					<option value="01">01</option>
					<option value="02">02</option>
					<option value="03">03</option>
					<option value="04">04</option>
					<option value="05">05</option>
					<option value="06">06</option>
					<option value="07">07</option>
					<option value="08">08</option>
					<option value="09">09</option>
					<option value="10">10</option>
					<option value="11">11</option>
					<option value="12">12</option>
				</select> mo. 					
				<select autocomplete="off" class="element select small"	id="expYr" name="expYr"> 
					<option value=""></option>
					<option value="12">2011</option>
					<option value="12">2012</option>
					<option value="13">2013</option>
					<option value="14">2014</option>
					<option value="15">2015</option>
					<option value="16">2016</option>
					<option value="17">2017</option>
					<option value="18">2018</option>
					<option value="19">2019</option>
					<option value="20">2020</option>
					<option value="21">2021</option>
				</select> yr.
				<input id="cvv" name="cvv" class="element text small" type="text" maxlength="4" value="" autocomplete="off"/>
			    <input id="element_6_1" name="billingAddress1" class="element text large" value="" type="text" />
			    <input id="element_6_2" name="billingAddress2" class="element text large" value="" type="text" />
			    <input id="element_6_3" name="billingCity" class="element text medium" value="" type="text" />
			    <input id="element_6_4" name="billingState" class="element text medium" value="" maxlength="2" type="text" />
			    <input id="element_6_5" name="billingZip" class="element text medium" maxlength="5" value="" type="text" />
			    <select class="element select medium" id="element_6_6" name="billingCountry">
					<option value="USA" >United States</option>
				</select>
			    <input id="customerEmail" name="customerEmail" class="element text medium" maxlength="50" value="" />
			    <input id="saveForm" class="button_text" type="submit" name="submit" value="Submit" />
				<input type="hidden" name="cid" value="nrc9fnu3g0e8dnfnh56qlikhu6" />
				<input type="hidden" name="jp_tid" id="jp_tid" value="TESTTERMINAL" />
				<input type="hidden" name="jp_key" id="jp_key" value="1234567890abcdefghijk" />
				<input type="hidden" name="jp_request_hash" id="jp_request_hash" value="" />
				<input type="hidden" name="order_number" id="order_number" value="" />
				<input type="hidden" name="amount" value="10.00" />
				<input type="hidden" name="trans_type" value="SALE" />
			    <input type="hidden" name="ud1" value="" />
			    <input type="hidden" name="ud2" value="" />
			    <input type="hidden" name="ud3" value="" />
			    <input type="hidden" name="merData0" value="" />
			    <input type="hidden" name="merData1" value="" />
			    <input type="hidden" name="merData2" value="" />
			    <input type="hidden" name="merData3" value="" />
			    <input type="hidden" name="merData4" value="" />
			    <input type="hidden" name="merData5" value="" />
			    <input type="hidden" name="merData6" value="" />
			    <input type="hidden" name="merData7" value="" />
			    <input type="hidden" name="merData8" value="" />
			    <input type="hidden" name="merData9" value="" />    
				<input type="hidden" name="retUrl" value="https://lowesinstall.serenecorp.com/ymca-web-1.1.0.RELEASE/redirectSuccess" />
				<input type="hidden" name="decUrl" value="https://lowesinstall.serenecorp.com/ymca-web-1.1.0.RELEASE/redirectFailure" />
				<input type="hidden" name="dataUrl" value="https://lowesinstall.serenecorp.com/ymca-web-1.1.0.RELEASE/ProcessDirectPayment" /> 				
			  </form>			
		</iframe> -->
			</div> 
			
		</div> 
		<div class="bootstrap-frm" id="paymentSuccessDiv" style="display:none">
			<h2><span>Payment Process Completed Successfully.</span></h2>
		</div>
		<div class="bootstrap-frm" id="paymentFailuresDiv" style="display:none">
			<h2><span>Payment process Failed. Please try after sometime. </span></h2>
		</div>
	</div>
	
	
</div>

<script>

var billingFlag = false;
	$(document).ready(function() {
		
		
		<c:if test="${annualDonationItemDetailObj.allowToPickStartDate == 'Yes'}">		
			$("#billingStartDateTr").css("display", "");			
		</c:if>
		<c:if test="${annualDonationItemDetailObj.allowToPickStartDate == 'No'}">		
			$("#billingStartDateTr").css("display", "none");			
		</c:if>		
		var dobYear = new Date().getFullYear();
		dobYear = parseInt(dobYear.toString());
		for(var i=0; i<100 ; i++){
			$('#dobYear').append($('<option>', {value: dobYear,text: dobYear}));
			dobYear = dobYear - 1;
		}
		$("#dobYear").kendoDropDownList();    
		$("#dobDate").kendoDropDownList();
		$("#dobMonth").kendoDropDownList();
		
		for(var i=1; i<=48 ; i++){
			$('#numberOfInstallments').append($('<option>', {value: i,text: i}));			
		}
		$("#numberOfInstallments").kendoDropDownList();
		$("#billingOption").kendoDropDownList();
		$("#billingFrequency").kendoDropDownList();				
		var billingFrequency = $("#billingFrequency").data("kendoDropDownList");
		$("#donationFreq").attr("value", billingFrequency.text());
		
		var currentYear = new Date().getFullYear();
		currentYear = parseInt(currentYear.toString());
		for(var i=0; i<30 ; i++){
			$('#addCardExpirationYearSelect').append($('<option>', {value: currentYear,text: currentYear}));
			currentYear = currentYear +1;
		}
		$("#addCardExpirationYearSelect").kendoDropDownList();    
	    $("#addCardExpirationMonthSelect").kendoDropDownList();
		
		<c:if test="${pageContext.request.userPrincipal.isAuthenticated()}">		
			$("#donerFirstName").attr("disabled","disabled");
			$("#donerLastName").attr("disabled","disabled");
			$("#email").attr("disabled","disabled");			
		</c:if>
	
		$(".termsAndConditionContent").html($("#commonTAncHidden").html());
		$("#preferredPhone").mask("(999) 999-9999");
		$("#companyPhone").mask("(999) 999-9999");	
		$("#dateOfBirth").kendoDatePicker();		 
		var currentYear = new Date().getFullYear();
		currentYear = parseInt(currentYear.toString());
		for(var i=0; i<30 ; i++){
			$('#addCardExpirationYear').append($('<option>', {value: currentYear,text: currentYear}));
			currentYear = currentYear +1;
		}
		$('#employerMatchingSelect').kendoDropDownList();		
		var employerMatchingSelect = $("#employerMatchingSelect").data("kendoDropDownList");
		employerMatchingSelect.select(0);
	    
		
		/* $('#donationFrequencyMonthsSel').kendoDropDownList();
		var donationFrequencyMonthsSel = $("#donationFrequencyMonthsSel").data("kendoDropDownList");
		donationFrequencyMonthsSel.select(0); */
		
		
		<c:if test="${param != null && param.opptyId != null}">
			$('#campaignerSelect').kendoDropDownList();
			var campaignerSelect = $("#campaignerSelect").data("kendoDropDownList");
			$('#campaignerSelectOther').attr("value", "");
			if(campaignerSelect.text()=='Other'){					
				$('#campaignerSelectOther').css("display","");
			}else{
				$('#campaignerSelectOther').css("display","none");
			}		
			$(document).on( "change", "#campaignerSelect", function() {
				$('#campaignerSelectOther').attr("value", "");
				if(campaignerSelect.text()=='Other'){					
					$('#campaignerSelectOther').css("display","");
				}else{
					$('#campaignerSelectOther').css("display","none");
				}
			 });
		</c:if>
		
		$(document).on('change', '#numberOfInstallments, #billingFrequency, #balanceDue', function(){
			clearInvoiceGrid();
		});
		
		$(document).on('change', '#billingFrequency', function(){
			$("#donationFreq").attr("value", billingFrequency.text());
		});
		
		$(document).on("keyup keypress change", "#downPayment", function(event){
			clearInvoiceGrid();
		});
		/* ,
		success: function( data ) {
			console.log(data);
			//return true;
			if(data == true){
				console.log("true");
				return "true";
			}else { 
				console.log("false");
				return "false";
			}								
		} */
		/* 
		var donerFirstName = $(document).find("#donerFirstName").val();
		var donerLastName = $(document).find("#donerLastName").val();
		var dobMonth = $('#dobMonth').val();
		var dobDate = $('#dobDate').val();
		var dobYear = $('#dobYear').val(); */
		
		
		/*Payment Process Credit Card */
		var eventMethod = window.addEventListener ? "addEventListener" : "attachEvent";
		var eventer = window[eventMethod];
		var messageEvent = eventMethod == "attachEvent" ? "onmessage" : "message";
		eventer(messageEvent,function(e) {
			var key = e.message ? "message" : "data";
			var data = e[key];
			//$("#contentFormDiv").css("display", "none");			
			if(data.view.toString() == "Success"){
				   			      
				    $("#statusBlock-payment").css("display", "none");	
				    $("#tcloginErrorSpan-payment").css("display", "none");	
				    $( "#tcloginErrorSpan-payment" ).html("");
				document.forms["createDonationForm"].submit();				
				$(".k-loading-mask").hide();
				$('#contentFormDiv').unblock(); 
			}
			if(data.view.toString() == "Failure"){
				$('#childIframeId').attr("src", "<%=contextPath %>/viewPaymentForm");				
			        $("#statusBlock-payment").css("display", "block");	
			        $("#tcloginErrorSpan-payment").css("display", "block");	
			        $( "#tcloginErrorSpan-payment" ).html("Error occurred while processing the payment. Please try again later");
			        $(".k-loading-mask").hide();
			        $('#contentFormDiv').unblock();
				
			}	
		},false);
		/*Payment Process Credit Card - End*/
		
		var url = "isEmailExistsWithUserData";
		var validator1 = $("#createDonationForm").validate({
			debug: true,
			errorElement: "em",			
			errorPlacement: function(error, element) {
				$element = $(element);			
				//console.log($element.hasClass("dateOfBirth"));
				if($element.attr("id") == "dateOfBirth"){
					$element.parent().parent().parent().next("td").html("");
					element.addClass("inputErrorr");
					error.appendTo(element.parent().parent().parent().next("td"));
					element.parent().parent().parent().next("td").addClass("textMsgError");
					$element.parent().parent().parent().next("td").css("color","red");
				}else{ 
					element.addClass("inputErrorr");
					error.appendTo(element.parent("td").next("td"));
					element.parent("td").next("td").addClass("textMsgError");
					$element.parent("td").next("td").css("color","red");
				}
				//$("#wizard"). smartWizard("fixHeight");
				/* element.addClass("inputErrorr");
				error.appendTo(element.parent("td").next("td"));
				element.parent("td").next("td").addClass("textMsgError"); */
			},
			success: function(label, element) {
				//alert("success");
				//label.text("ok!").addClass("success");
				var $element = $(element);
				var $label = $(label);
		       	$element.removeClass("inputErrorr");
				$element.removeClass("error");
				$element.addClass("success");
				
				$label.removeClass("textMsgError");
				$label.removeClass("error");
				$label.addClass("success");
				//$("#wizard"). smartWizard("fixHeight");
				
			},
			rules: {
				"donerFirstName": {
					required: true,
					minlength: 2,
					maxlength: 20
				},
				"donerLastName": {
					required: true,
					minlength: 2,
					maxlength: 20
				},
				"dateOfBirth" : "required",		
				"preferredPhone" : "required",				
				"addressLine1" : "required",			
				"city" : "required",
				"zipcode" : {
					required: true,
					number: true,
					minlength: 5
				},
				"state" : "required",
				"companyName": "required",
				"companyAddress" : "required",
				"companyPhone" : {
					required: true,
					//number: true,
					minlength: 4
				},
				"employeeEmailId" :  {					
					email: true
				}
				<c:if test="${!pageContext.request.userPrincipal.isAuthenticated()}">
					,"email" :  {
						required: true,
						email: true,
						remote: {
							url: url,  
							type:"GET",
							data: {
								  donerFirstName: function() {
						            return $(document).find("#donerFirstName").val();
						          },
						          donerLastName: function() {
							            return $(document).find("#donerLastName").val();
							      },
							      dobMonth: function() {
						            return $('#dobMonth').val();
						          },
						          dobDate: function() {
						            return $('#dobDate').val();
						          },
						          dobYear: function() {
						            return $('#dobYear').val();
						          }					
						    }
						}
					}
				</c:if>
				<c:if test="${pageContext.request.userPrincipal.isAuthenticated()}">
				,"email" :  {
					required: true,
					email: true
				}
			</c:if>
				
				
			},
			messages: {
				"donerFirstName": {				
					required: "Please enter your First Name",
					minlength: "First Name must consist of at least 2 characters"
				},
				
				"donerLastName": {				
					required: "Please enter your Last Name",
					minlength: "Last Name must consist of at least 2 characters"
				},
				"dateOfBirth" : "Please enter your Date of Birth",				
				"preferredPhone" : "Please enter your Phone Number",			
				"zipcode" : {
					required: "Please enter Postal Code",
					number: "Please enter numbers only",
					minlength: "Postal Code must consist of at least 5 characters"
				},
				"addressLine1" : "Please enter address line 1",			
				"city" : "Please enter yout City",
				"state" : "Please enter your State",
				agree: "Please accept our policy",
				"companyName": "Please enter your Company Name",
				"companyAddress" : "Please enter your Company Address",
				"companyPhone" : {
					required: "Please enter Company Phone",
					//number: "Please enter numbers only",
					minlength: "Phone must consist of at least 4 characters"
				},
				"employeeEmailId" :  {					
					email : "Please enter valid email address"
				}
				<c:if test="${!pageContext.request.userPrincipal.isAuthenticated()}">
					,"email" :  {
						required: "Please enter your Email address",
						email : "Please enter valid email address",
						remote: "This email is associated with another contact. Please enter the correct first name, last name and date of birth or enter a different email"
					}
				</c:if>
				<c:if test="${pageContext.request.userPrincipal.isAuthenticated()}">
				,"email" :  {
					required: "Please enter your Email address",
					email : "Please enter valid email address"
				}
			</c:if>
				
			}
		});	
		
		var paymentFreq = $("#paymentInfoRadio").data("kendoDropDownList");
		/* $("#paymentFrequencySelect").on('change',function(e){   	
		    	if(paymentFreq.text() == 'Annual'){
		    		
		    	}
		 } */		 
		if($("#paymentInfoRadio").val()=='New'){
			$('#addBank').hide();	
			$('#addcard').show();				
		}else if($("#paymentInfoRadio").val()=='NewBank'){
			$('#addcard').hide();
			$('#addBank').show();						
		} else{
			$('#addcard').hide();
			$('#addBank').hide();				
		}	
		 <c:if test="${!pageContext.request.userPrincipal.isAuthenticated()}">
		 	$('#addcard').show();
		 </c:if>
		 $('body').on( "change", "#employerMatchingSelect", function() {
			 if(employerMatchingSelect.text() == '--Select Employer--'){
			    	$("#donationEmpMatchErr").css("display", "");
		    		$("#donationEmpMatchErr").text("Please select the Employer");	
			    }else{
			    	$("#donationEmpMatchErr").css("display", "none");
		    		$("#donationEmpMatchErr").text("");	
			    }
				if(employerMatchingSelect.text()=='Other'){
					$("#employerMatchInfoDiv").css("display", "");
				}else{
					$("#employerMatchInfoDiv").css("display", "none");
				}
		 });
		 
		$('body').on( "change", "#paymentInfoRadio", function() {			
			if($("#paymentInfoRadio").val()=='New'){
				$('#addBank').hide();	
				$('#addcard').show();				
   				var fName = $(document).find("#donerFirstName").val();
   				var lName = $(document).find("#donerLastName").val();   				
   				$("#fullName").attr("value", fName + " " + lName);    		
							
			}else if($("#paymentInfoRadio").val()=='NewBank'){
				$('#addcard').hide();
				$('#addBank').show();							
			} else{
				$('#addcard').hide();
				$('#addBank').hide();	
				$("#fullName").attr("value", "");
			}
		});
		
		$('input[name=recognizeAsRadioInput]').change(function () {	
			//alert($(this).val());
			var recognizedAsTxt = $(this).parent().find(".recognizedAsSpanCls").text();
			$("#recognizedAsHidInput").attr("value", recognizedAsTxt);
			$("#donationContactSelErr").css("display", "none");
    		$("#donationContactSelErr").text("");				
	    });
		
		var fName = $(document).find("#donerFirstName").val();
		var lName = $(document).find("#donerLastName").val();
		if(fName && lName){
			$("#recognizedAsSpan").css("display", "");
			$(document).find("#recognisedAsFName").html(fName);
			$(document).find("#recognisedAsLName").html(lName);		
			///$("#recognizedAsName").attr("value", fName + " " +lName);			  				
			$("#fullName").attr("value", fName + " " + lName);
		}else{
			$("#recognizedAsSpan").css("display", "none");
			$(document).find("#recognisedAsFName").html("");
			$(document).find("#recognisedAsLName").html("");
			//$("#recognizedAsName").attr("value", "");
			$("#fullName").attr("value", "");
		}
		
		$(document).find("#donerFirstName, #donerLastName").on('blur mouseup',function(){
			var fName = $(document).find("#donerFirstName").val();
			var lName = $(document).find("#donerLastName").val();
			if(fName && lName){
				$("#recognizedAsSpan").css("display", "");
				$(document).find("#recognisedAsFName").html(fName);
				$(document).find("#recognisedAsLName").html(lName);		
				//$("#recognizedAsName").attr("value", fName + " " +lName);
				$("#fullName").attr("value", fName + " " + lName);
			}else{
				$("#recognizedAsSpan").css("display", "none");
				$(document).find("#recognisedAsFName").html("");
				$(document).find("#recognisedAsLName").html("");
				//$("#recognizedAsName").attr("value", "");
				$("#fullName").attr("value", "");
			}
		});
		
		
		$('#donationBranch').kendoDropDownList();
		var donationBranch = $("#donationBranch").data("kendoDropDownList");
		//donationBranch.select(0);
		$("#donationBranch").on('change',function(e){    	
			 var donationBranchText = $("#donationBranch").val();
		     if(donationBranchText && donationBranchText == 'Other'){
		    	 $("#YmcaBranchTxt").css("display", "");	
		    	 $("#donationTCItemDetailId").attr("value", $("#associatedItemDetailId").val());		    	 
		     }else{
		    	 $("#YmcaBranchTxt").css("display", "none");
		    	 if(donationBranchText){
			    	 var branchArr = donationBranchText.split("_");
			    	 var locationName = branchArr[0];
			    	 var itemDetailId = branchArr[1];
			    	 if(itemDetailId){
			    		$("#donationTCItemDetailId").attr("value", itemDetailId);
			    	 }
			     }
		     }
		     
		});
		var today = kendo.date.today();
	    var dateToday = new Date();  
	    
		$("#donationDate").kendoDatePicker({
			format: "MM/dd/yyyy",
			value: today,
			min: dateToday			
		}); 
		
		$("#todayKendoDate").kendoDatePicker({
			format: "MM/dd/yyyy",
			value: today,
			min: dateToday			
		}); 
		/* $("#billingStartDate").kendoDatePicker({
			format: "MM/dd/yyyy",
			value: today,
			change: function (e) {
				clearInvoiceGrid();
			},
			min: dateToday			
		}); */
		
		var billingStartDate = $("#billingStartDate").kendoDatePicker({
			format: "MM/dd/yyyy",
			value: today,
			change: function (e) {
				clearInvoiceGrid();
				var prev = $(this).data("previous");
				var todayDate = kendo.toString(kendo.parseDate(new Date()), 'MM/dd/yyyy');	
				var prevDate = kendo.toString(kendo.parseDate(prev), 'MM/dd/yyyy');
				var endBillDate = new Date(e.sender.value());				
				var annualPlanEndDate = new Date("${annualDonationDueDtYear}","${annualDonationDueDtMonth - 1}","${annualDonationDueDtDate}");						
				if(endBillDate > annualPlanEndDate){
					showBillingDateError();	
					var thisDatepicker = $('#'+e.sender.element[0].id).data("kendoDatePicker");
					if(prevDate){
						thisDatepicker.value(prevDate);
					}else{
						thisDatepicker.value(todayDate);
					}					
				} else{
					 $(this).data("previous", this.value());
				}	
			},
			min: dateToday
		}).data("kendoDatePicker");
		$(billingStartDate).data("previous", "");
		
		
		
		$('#paymentInfoRadio').kendoDropDownList();	
		/*var donationFrequencyMonthsSel = $("#donationFrequencyMonthsSel").data("kendoDropDownList");
		$("#donationFrequencyMonthsSel").closest(".k-widget").hide();
		$(".donationFrequencyStr").on('change',function(e){
			$("#donationFreqErr").css("display", "none");
    		$("#donationFreqErr").text("");
			var donationFreqVal = $(this).val();
			if(donationFreqVal == "One Time"){
				$("#donationFrequencyMonthsSel").closest(".k-widget").hide();	
				donationFrequencyMonthsSel.select(0);
				$("#donationFreqMonthlySelMsgSpan").css("display", "none");
			}
			if(donationFreqVal == "Monthly"){
				$("#donationFrequencyMonthsSel").closest(".k-widget").show();
				$("#donationFreqMonthlySelMsgSpan").css("display", "");
				donationFrequencyMonthsSel.search("Continuous");
			}
		}); */
		
		$('#contactLstSelect').kendoDropDownList();
		
		var contactLst = $("#contactLstSelect").data("kendoDropDownList");
		
		/* $("#donationFrequencyMonthsSel").on('change',function(e){    	
			 var donationFrequency = donationFrequencyMonthsSel.text();
		     if(donationFrequency && donationFrequency == '--Select No of Months--'){
		    	 $("#donationFreqErr").css("display", "");
		    	 $("#donationFreqErr").text("Please select the number of Months");
		    	 $("#amtPledgingNoteSpan").html("");
		     }else{
		    	 $("#donationFreqErr").css("display", "none");
		    	 $("#donationFreqErr").text("");
		    	 var donationAmt = $("#donationAmtStr").val();
		    	 if(donationAmt && donationFrequency && donationAmt != 0 && donationFrequency != "Continuous"){
		    		 var amount = parseFloat(donationAmt);
		    		 var frequency = parseFloat(donationFrequency);
		    		 $("#amtPledgingNoteSpan").html("iii) You are pledging $"+ amount * frequency);	
		    	 }
		    	 	    	 
		     }
		}); */

		 $("#contactLstSelect").on('change',function(e){    	
				var selectedContact = contactLst.text();
			    if(selectedContact && selectedContact == 'Other'){		    	
					$("#recognizeAsOtherTxt").css("display", "inline");						
			    }else{
			    	$("#recognizeAsOtherTxt").css("display", "none");
			    	$("#recognizedAsHidInput").attr("value",  $("#contactLstSelect").val());
			    }
		    	var fName = $(document).find("#donerFirstName").val();
				var lName = $(document).find("#donerLastName").val();
				//$(document).find("#recognisedAsFName").html(fName);
				//$(document).find("#recognisedAsLName").html(lName);	
				$("#recognizedAsContactName").attr("value",  $("#contactLstSelect").val());						
				$("#donationContactSelErr").css("display", "none");
	    		$("#donationContactSelErr").text("");
		 });
		 
		 $('#contry').kendoDropDownList();
		 $('#state').kendoDropDownList();
		
		/* var eventMethod = window.addEventListener ? "addEventListener" : "attachEvent";
		var eventer = window[eventMethod];
		var messageEvent = eventMethod == "attachEvent" ? "onmessage" : "message";
		eventer(messageEvent,function(e) {
			var key = e.message ? "message" : "data";
			var data = e[key];
			$("#contentFormDiv").css("display", "none");			
			if(data.view.toString() == "Success"){
				$("#paymentSuccessDiv").css("display", "block");
			}
			if(data.view.toString() == "Failure"){
				$("#paymentFailuresDiv").css("display", "block");
			}
			//$("#form_container").html("Success Redirection " + data.view);			
		},false); */
				
		//$("#jp_request_hash").val(hash.toString());
		//$("#order_number").val(paymentOrderId.toString());
		
		var currentYear = new Date().getFullYear();
		currentYear = parseInt(currentYear.toString().substring(2, 4));
		for(var i=0; i<30 ; i++){
			$('#createDonationExpYr').append($('<option>', {value: currentYear,text: currentYear}));
			currentYear = currentYear +1;
		}
		
		$('input[name=finalAmountRadio]').change(function () {			
			if($(this).val() == 'Other'){
				$("#donationAmtStr").attr("value", $("#donationAmtOtherTxt").val());				
    		}else{
    			$("#donationAmtStr").attr("value", $(this).val());	    		
    		}		
	        //alert($(this).val()); 
	        //$("#donationAmtStr").attr("value", $(this).val());	
	        $("#donationAmtErr").css("display", "none");
			$("#donationAmtErr").text("");			
			if($(this).val() == 'Other' && $("#donationAmtOtherTxt").html().trim() == ''){
				$("#amtChanrgedNoteSpan").html("");
				$("#donationAmtErr").css("display", "");
				$("#donationAmtErr").text("Please enter the donation amount");
			}else if($(this).val() == 'Other'){
				$("#amtChanrgedNoteSpan").html("ii) You will be charged $" + $("#donationAmtOtherTxt").html() +" per month");
			}else{
				$("#amtChanrgedNoteSpan").html("ii) You will be charged $" + $(this).val() +" per month");
				//$("#donationAmtOtherTxt").attr("value", "");
			}			
			var donationAmt = $("#donationAmtStr").val();
			$("#balanceDue").attr("value", donationAmt);
			$("#billingAmount").attr("value", donationAmt);
			clearInvoiceGrid();
			/* var donationFrequency = donationFrequencyMonthsSel.text();
			if(donationAmt && donationFrequency && donationAmt != 0 && donationFrequency != "Continuous"){
				try{
					var amount = parseFloat(donationAmt);
					var frequency = parseFloat(donationFrequency);
					if(amount && frequency && amount != "NaN" && frequency != "NaN"){
						$("#amtPledgingNoteSpan").html("iii) You are pledging $"+ amount * frequency);
					}					
				}catch(err){
					$("#amtPledgingNoteSpan").html("Please enter correct amount" + err.message);
				}
				 	
			} */
	    });
		
		/* $('input[name=donationFrequencyStr]').change(function () {
	        //alert($(this).val());
	        $("#donationFreq").attr("value", $(this).val());
	    }); */
		
		$('input[name=cardTypeStr]').change(function () {
	        //alert($(this).val());
	        $("#cardType").attr("value", $(this).val());
	    });
		/* $( "#createDonationButton" ).click(function(){	
			$(".k-loading-mask").show();
			$(".k-loading-text").html("Please wait while the Donation Information created...");			
			$.ajax({
				  type: "POST",
				  url: $('#createDonationForm').attr( "action"),
				  data: $('#createDonationForm').serialize(),
				  success: function( data ) {				  	  
				  	  if(data=='SUCCESS'){
					  	  $("#addDonationErrorSpan").css("display", "none");		
						  $("#addDonationErrorSpan" ).html("");	
						  setTimeout(function(){$(".k-loading-text").fadeOut("slow");}, 4000);
						  setTimeout(function(){$(".k-loading-text").html("Donation Information Added successfully");$(".k-loading-text").fadeIn("slow");}, 5000);
					  	  setTimeout(function(){$(".k-loading-text").fadeOut("slow");}, 6900);
					  	  
					  	  $("#addDonationSuccessSpan").css("display", "block");		
						  $("#addDonationSuccessSpan" ).html("Donation Information Added successfully");
					  	  
					  	  //setTimeout(function(){location.reload();}, 7000);
					  	  
				  	  }else if(data == 'NOT_FOUND'){
				  		  $("#addDonationSuccessSpan").css("display", "none");		
						  $("#addDonationSuccessSpan" ).html("");	
						  $("#addDonationErrorSpan").css("display", "block");		
						  $( "#addDonationErrorSpan" ).html("Customer Information Not Found.");
						  $(".k-loading-mask").hide();
				  	  }else {
				  		  $("#addDonationSuccessSpan").css("display", "none");		
						  $("#addDonationSuccessSpan" ).html("");	
						  $("#addDonationErrorSpan").css("display", "block");		
						  $( "#addDonationErrorSpan" ).html("There was some error. Please try again later");
						  $(".k-loading-mask").hide();
				  	  }					  
				  },
				  error: function( xhr,status,error ){
					  $("#addDonationSuccessSpan").css("display", "none");		
					  $("#addDonationSuccessSpan" ).html("");	
					  $("#addDonationErrorSpan").css("display", "block");		
					  $( "#addDonationErrorSpan" ).html("There was some error. Please try again later");
					  $(".k-loading-mask").hide();
				  }
			});
			
		}); */	
		
		$("#createDonationButton").click(function(){
			console.log("createDonationButton click");
			 var jsonData = {
					name : $("#nameOnCard").val(),
					cardNum : $("#cardNumber").val(),
					cscNumber : $("#cscNumber").val(),
					AddressLine1 : $("#AddressLine1").val(),
					AddressLine2 : $("#AddressLine2").val(),
					City : $("#City").val(),
					state : $("#state").val(),
					zipcode : $("#zipcode").val(),
					email : $("#email").val(),
					amount : $("#donationAmtOtherTxt").val(),
					expMonth : $("#expMonth").val(),
					expYr : $("#createDonationExpYr").val(),
					contry : $("#contry").val()
					
			 };
			 //console.log(jsonData);
			 var win = document.getElementById("childIframeId").contentWindow;
			 //win.postMessage(jsonData, '*');
			 if(!$("#createDonationForm").valid()) {
	    		console.log("Validation failed");
	    	} else if(!$(document).find( "input:radio[name=finalAmountRadio]" ).is(":checked")){	    		
	    		$("#donationAmtErr").css("display", "");
	    		$("#donationAmtErr").text("Please select the Donation Amount");	    		
	    	} 
			 
	    	/* else if(!$(document).find( "input:radio[name=signUpPricingOption]" ).is(":checked")){	    		
	    		$("#donationFreqErr").css("display", "");
	    		$("#donationFreqErr").text("Please select the Donation Frequency");	    		
	    	} */ 
	    	
	    	else if($("input:checkbox[name=employerMatching]" ).is(":checked") && employerMatchingSelect.text() == '--Select Employer--'){	    		
	    		$("#donationEmpMatchErr").css("display", "");
	    		$("#donationEmpMatchErr").text("Please select the Employer");	    		
	    	} else if(!$(document).find( "input:radio[name=recognizeAsRadioInput]" ).is(":checked")){	
	    		$("#donationContactSelErr").css("display", "");
	    		$("#donationContactSelErr").text("Please select Recognized As option");
	    	} else if(!billingFlag){	
	    		showBillingSetupErrorBox();
	    	} else {	  
	    		/* if($('input:radio[name=finalAmountRadio]:checked').val() == 'Other' && $("#donationAmtOtherTxt").val() == ''){
	    			$("#donationAmtErr").css("display", "");
		    		$("#donationAmtErr").text("Please enter the Donation Amount");
	    		}else  */
	    		var amountVal = $('input[name=finalAmountRadio]:checked').val();	    		
	    		var donationAmtTxt = $("#donationAmtOtherTxt").val().trim();
	    		if(amountVal == 'Other' && donationAmtTxt == ''){
	    			$("#donationAmtErr").css("display", "");
		    		$("#donationAmtErr").text("Please enter the Donation Amount");
	    		}/* else if($('input:radio[name=signUpPricingOption]:checked').val() == 'Monthly' && donationFrequencyMonthsSel.text() == '--Select No of Months--'){	    			
	    			$("#donationFreqErr").css("display", "");
		    		$("#donationFreqErr").text("Please select the number of Months");
	    		} */ else if($('#recognizedAsContactName').is(':checked') && contactLst.text() == '--Select Contact--'){
	    			$("#donationContactSelErr").css("display", "");
		    		$("#donationContactSelErr").text("Please select the contact");
	    		} else if($('#recognizedAsContactName').is(':checked') && contactLst.text() == 'Other' && $("#recognizeAsOtherTxt").val().trim() == ""){
	    			$("#donationContactSelErr").css("display", "");
		    		$("#donationContactSelErr").text("Please enter contact name");
	    		} 
	    		<c:if test="${annualDonationItemDetailObj != null && annualDonationItemDetailObj.waiversAndTC != null && not empty annualDonationItemDetailObj.waiversAndTC.tcDescription}">
		    		else if(!$("input[name='chkTermsAndCondition']:checked").val()){ 
		    			
		    			var kendoWindow = $("<div />").kendoWindow({
		    				title: "Error",
		    				resizable: false,
		    				modal: true,
		    				width:400
		    			});
	
		    			kendoWindow.data("kendoWindow")
		    			 .content($("#terms-conditions-ErrorBox").html())
		    			 .center().open();
	
		    			kendoWindow
		    			.find(".confirm-terms-conditions")
		    			.click(function() {
		    				if ($(this).hasClass("confirm-terms-conditions")) {         		
		    					kendoWindow.data("kendoWindow").close();
		    				}
		    			})
		    			.end();
		    		}
	    		</c:if>
		    	else{
	    			// alert(contactLst.text());
	    			if($('#recognizedAsContactName').is(':checked') && contactLst.text() == 'Other'){
	    				$("#recognizedAsHidInput").attr("value",  $("#recognizeAsOtherTxt").val());	    				
		    		}  
	    			//console.log("Valication Success");
	    			$("#donationPledgeTotalAmt").attr("value",  $("#donationAmtStr").val());	
	    			processJetPayPayment();
	    			//document.forms["createDonationForm"].submit();
	    		}
	    		
	    	}
		});	
		
		$("#generateBillingSetup").click(function(){
			var billingStartDate = $("#billingStartDate").val();
			var annualPlanEndDate = new Date("${annualDonationDueDtYear}","${annualDonationDueDtMonth - 1}","${annualDonationDueDtDate}");
			//console.log(annualPlanEndDate);
			var noOfInstallment = parseInt($("#numberOfInstallments").val());			
			var billingFreq = $("#billingFrequency").val();				
			var endBillDate = new Date(billingStartDate);
			var startBillDate = new Date(billingStartDate);
			for(var i = 1; i<= noOfInstallment; i++){				
				if(billingFreq){
					if(billingFreq == 'Weekly'){
						endBillDate.setDate(endBillDate.getDate()+7);
					}else if(billingFreq == 'Semi-Monthly'){
						endBillDate.setDate(endBillDate.getDate()+15);
					}else if(billingFreq == 'Monthly'){
						endBillDate.setMonth(endBillDate.getMonth()+1);
					}else if(billingFreq == 'Quarterly'){
						endBillDate.setMonth(endBillDate.getMonth()+3);
					}else if(billingFreq == 'Yearly'){
						endBillDate.setMonth(endBillDate.getMonth()+12);
					}
				}
			}
			if(annualPlanEndDate < startBillDate){
				showBillingStartDateError();
				return false;
			}else if(startBillDate <= annualPlanEndDate && noOfInstallment == 1){
				processBilling();
			}else if(endBillDate > annualPlanEndDate){
				showPlanEndDateErrorDialogue();
				return false;
			} else{
				processBilling();
			}						
		});		
		
		$(document).on("keyup keypress blur change", ".checkInvAmt", function(event){
		    
		    var charCode = (event.which) ? event.which : event.keyCode;
		       //var thisObject = $(thisObj);	      
		       if (charCode != 46 && charCode > 31 && (charCode < 48 || charCode > 57)){
		    	   if($("#donationAmtOtherTxt").val().trim() == ''){
		   				$("#donationAmtErr").css("display", "");
			    		$("#donationAmtErr").text("Please enter the Numbers only");
			   		}
		    	   return false;    	   
		    	}else{
		    		$("#donationAmtErr").css("display", "none");
					$("#donationAmtErr").text("");
					var donationAmtVal = $(this).attr("value");
					if(donationAmtVal){
						var donationAmtValArr = donationAmtVal.split(".");
						if(donationAmtValArr && donationAmtValArr.length > 2){
							return false; 
						}else if(donationAmtValArr && donationAmtValArr[1] && donationAmtValArr[1].length >1){
							return false;
						}
					}
					var totalAmt = 0;
					$(document).find('.checkInvAmt').each(function(i, obj) {	
						var amt = $(obj).attr("value");
						if(amt && parseFloat(amt) >0){
							totalAmt = parseFloat(totalAmt) + parseFloat(amt);
						}
						
					});
					$("#donationAmtOtherTxt").attr("value", totalAmt);
					$("#balanceDue").attr("value", totalAmt);	
					$("#billingAmount").attr("value", totalAmt);
					//console.log(totalAmt);
					return true;
		    	}
		});
		
		/* $("#donationAmtOtherTxt").on('blur mouseup',function(){
			//alert($("#donationAmtOtherTxt").text());
			if($('input:radio[name=finalAmountRadio]:checked').val() == 'Other' && $("#donationAmtOtherTxt").html().trim() == ''){
    			$("#donationAmtErr").css("display", "");
	    		$("#donationAmtErr").text("Please enter the Donation Amount");
    		}else{
    			$("#donationAmtErr").css("display", "none");
	    		$("#donationAmtErr").text("");
    		}			
		});	 */	
		
		/* $(document).on('change', 'input:[name=donationAmtString]', function(){
			$("#donationAmtErr").css("display", "none");
			$("#donationAmtErr").text("");	
		}); */
	});
	
	function getBillingSetupHtml(){
		var noOfInstallment = parseInt($("#numberOfInstallments").val());
		var revenueAmt = $("#balanceDue").val();
		var downPaymentAmt = $("#downPayment").val();
		if(downPaymentAmt && downPaymentAmt >0){
			revenueAmt = revenueAmt - parseFloat(downPaymentAmt);
		}
		var intervalAmt = 0;
		if(revenueAmt && revenueAmt>0){
			intervalAmt = (revenueAmt/noOfInstallment).toFixed(2);
		}
		var totalAmtWithFraction = intervalAmt * noOfInstallment;
		var adjustAmt = (revenueAmt - totalAmtWithFraction).toFixed(2);
		adjustAmt = (parseFloat(adjustAmt) + parseFloat(intervalAmt)).toFixed(2);
		
		var billingStartDate = new Date($("#billingStartDate").val());
		var billingFreq = $("#billingFrequency").val();			
		var billingSetupHtml = '';
		var nextBillDate = new Date(billingStartDate);
		for(var i = 1; i<= noOfInstallment; i++){	
			var tempDate = nextBillDate;
			tempDate = formatDate(tempDate);		
			if(i % 2 == 0){
				billingSetupHtml += '<tr style="background-color : #F0FFFF; padding-left: 4px; ">';
			}
			if(i  % 2 != 0){
				billingSetupHtml += '<tr style="background-color : white; padding-left: 4px; ">';
			}
			
			billingSetupHtml += '<td style="width : 12%" align="center">'+i+'</td>';
			billingSetupHtml += '<td style="width : 43%"><input type="text" placeholder="Billing Start Date" name="invoices['+ (i - 1)+'].dueDate" value="'+tempDate+'" class="billingSetUpBillDate" id="billingSetUpBillDate'+i+'" style="margin: 0px; width : 200px;"/><span style="display :none;"  class="billingSetupHidDate">'+tempDate+'</span></td>';
			if(i == 1){
				billingSetupHtml += '<td style="width : 43%"><input type="text" placeholder="Billing Amount" class="checkInvAmt firstInvAmt"  name="invoices['+ (i - 1)+'].amount" id="billingStartDate" value="'+adjustAmt+'" style="margin: 0px; width : 240px;" /></td>';
			}else{
				billingSetupHtml += '<td style="width : 43%"><input type="text" placeholder="Billing Amount" class="checkInvAmt"  name="invoices['+ (i - 1)+'].amount" id="billingStartDate" value="'+intervalAmt+'" style="margin: 0px; width : 240px;" /></td>';	
			}						  			
			billingSetupHtml += '</tr>';
			if(billingFreq){
				if(billingFreq == 'Weekly'){
					nextBillDate.setDate(nextBillDate.getDate()+7);
				}else if(billingFreq == 'Semi-Monthly'){
					nextBillDate.setDate(nextBillDate.getDate()+15);
				}else if(billingFreq == 'Monthly'){
					nextBillDate.setMonth(nextBillDate.getMonth()+1);
				}else if(billingFreq == 'Quarterly'){
					nextBillDate.setMonth(nextBillDate.getMonth()+3);
				}else if(billingFreq == 'Yearly'){
					nextBillDate.setMonth(nextBillDate.getMonth()+12);
				}
			}
		}
		return billingSetupHtml;
	}
	
	function formatDate(inputDate) {
		function pad(s) {
			return (s < 10) ? '0' + s : s;
		}
		var d = new Date(inputDate);
		return [ pad(d.getMonth() + 1), pad(d.getDate()), d.getFullYear() ]
				.join('/'); 			
	}
	
	function isNumberKey(evt) {
       var charCode = (evt.which) ? evt.which : evt.keyCode;
       if (charCode != 46 && charCode > 31 && (charCode < 48 || charCode > 57)){
    	   if($("#donationAmtOtherTxt").val().trim() == ''){
   				$("#donationAmtErr").css("display", "");
	    		$("#donationAmtErr").text("Please enter the Numbers only");
	   		}
    	   return false;    	   
    	}else{
    		$("#donationAmtErr").css("display", "none");
			$("#donationAmtErr").text("");
			clearInvoiceGrid();
			return true;
    	}
          
       
    }
	
	function contactNameChange(evt) {
		if($('#recognizedAsContactName').is(':checked') && contactLst.text() == 'Other' && $("#recognizeAsOtherTxt").val().trim() == ""){
		
		}else {
			
		}	       
	}
	
	function processJetPayPayment(){
		$("#paymentOrderId").attr("value", "");
		$("#paymentMethodId").attr("value", "");
		console.log("payment" + $("#paymentInfoRadio").val());
		
		var downpaymentAmt = $("#downPayment").val();
		console.log("downpaymentAmt" +downpaymentAmt);
		//var amountPaid = $("#donationAmtStr").val();
		var amountPaid = 0;
		var todayWithZeroTime = new Date();
		todayWithZeroTime.setHours(0,0,0,0);
		var firstInstallmentDate = new Date($("input.billingSetUpBillDate:first").val());
		console.log("firstInstallmentDate paid" +firstInstallmentDate);
		var firstInstallmentAmount = $("input.firstInvAmt:first").val();
		console.log("firstInstallmentAmount paid" +firstInstallmentAmount);
		if(downpaymentAmt){
			amountPaid = parseFloat(amountPaid) + parseFloat(downpaymentAmt);
		}
		if(firstInstallmentDate == firstInstallmentDate && firstInstallmentAmount){
			amountPaid = parseFloat(amountPaid) + parseFloat(firstInstallmentAmount);
		}
		//console.log("amount paid" +amountPaid);
		
		var selectedPaymentMethod = $("#paymentInfoRadio").data("kendoDropDownList").text();
		if($("#paymentInfoRadio").val() && $("#paymentInfoRadio").val()!='New' && $("#paymentInfoRadio").val()!='NewBank' && $("#paymentInfoRadio").val()!='Cash' && $("#paymentInfoRadio").val()!='Check' && $("#paymentInfoRadio").val()!='None' && $("#paymentInfoRadio").val()!='Stock'){
			
			var paymentDDvalue = $('#paymentInfoRadio').val();
			var paymentDDValueSplit = paymentDDvalue.split("__");			
			var paymentMethodType = paymentDDValueSplit[0];
			var paymentMethodToken = paymentDDValueSplit[1];
			var paymentMethodId = paymentDDValueSplit[2];
			$("#paymentMethodId").attr("value", paymentMethodId);
			if (paymentMethodType && paymentMethodType == 'CREDIT') {	
				if(parseFloat($("#donationAmtStr").val()) > 0){
					//$(".k-loading-mask").show();
					//$(".k-loading-text").html("Please wait");
					$('#contentFormDiv').block({ 
		                message: '<h1>Please wait..</h1>', 
		                css: { border: '3px solid #a00' } 
		            }); 					
					$("#statusBlock-payment").css("display", "none");	
					$("#tcloginErrorSpan-payment").css("display", "none");	
					$( "#tcloginErrorSpan-payment" ).html("");
					
					$.ajax({
						  type: "POST",
						  url: "processPaymentByTokenId",	
						  data: { token: paymentMethodToken, totalAmount : amountPaid},
						  success: function( data ) {						  
							  var paymentMethodHtml = '';
							  if(data != null && data.responseText == "APPROVED"){							  
								  //$("#paymentTransId").attr("value", data.transId); 						  
									$("#statusBlock-payment").css("display", "none");	
									$("#tcloginErrorSpan-payment").css("display", "none");	
									$( "#tcloginErrorSpan-payment" ).html("");						  
								  document.forms["createDonationForm"].submit();						  
							  }else {							
								  $("#statusBlock-payment").css("display", "block");	
									$("#tcloginErrorSpan-payment").css("display", "block");	
									$( "#tcloginErrorSpan-payment" ).html("Error occurred while processing the payment. Please try again later <br />ActionCode :<b>"+data.actCode+"</b> Error message : "+data.responseText);
									$.unblock(); 
							  }
							  //$(".k-loading-mask").hide();
							  $.unblock(); 
						  },
						  error: function( xhr,status,error ){
							  $("#statusBlock-payment").css("display", "block");	
							  $("#tcloginErrorSpan-payment").css("display", "block");	
							  $( "#tcloginErrorSpan-payment" ).html("There was some error. Please try again later");	
							  $(".k-loading-mask").hide();
							  $('#contentFormDiv').unblock();
						  }
					});
				}else{
					$("#statusBlock-payment").css("display", "block");	
					  $("#tcloginErrorSpan-payment").css("display", "block");	
					  $( "#tcloginErrorSpan-payment" ).html("Please enter the correct donation amount");	
					  $(".k-loading-mask").hide();
					  $('#contentFormDiv').unblock();
				}
			}
		} else if(typeof $("#paymentInfoRadio").val() == "undefined" || $("#paymentInfoRadio").val()=='New'){
			$('#contentFormDiv').block({ 
                message: '<h1>Please wait..</h1>', 
                css: { border: '3px solid #a00' } 
            }); 
			//$(".k-loading-mask").show();
			//$(".k-loading-text").html("Please wait");	
			var merchantId = "TESTTERMINAL";  
			var JetDirectToken	= "1234567890ABCDEFGHIJKabcdefghijk";
			var hash = '';
			var paymentAmount = $("#donationAmtStr").val() + ".00";
			paymentOrderId = Math.floor((Math.random()*1000000000000)+1);
			$("#paymentOrderId").attr("value", paymentOrderId);
			var jsSha = new jsSHA(merchantId+paymentAmount+JetDirectToken+paymentOrderId);
			hash = jsSha.getHash("SHA-512", "HEX");		
			if(parseFloat(paymentAmount) > 0){
				var saveCard = 'N';
				if($('.SaveCard').is(":checked")){
					saveCard = 'Y';
				}
				var jsonData = {
						trans_type : "SALE", // "TOKENIZE" // "AUTHONLY", "SALE"
						ud1 : $("#loggedInAccountId").val(),
						ud2 : $("#nickName").val(),
						name : $("#fullName").val(),
						cardNum : $("#cardNum").val(),
						cscNumber : $("#securityCode").val(),
						expMonth : $("#addCardExpirationMonthSelect").val(),
						expYr : $("#addCardExpirationYearSelect").val(),
						amount : amountPaid,
						AddressLine1 : $("#billingAddressLine1").val(),
						AddressLine2 : $("#billingAddressLine2").val(),
						City : $("#billingCity").val(),
						state : $("#billingState").val(),
						zipcode : $("#billingZip").val(),
						email : "test@gmail.com",
						contry : "US",
						jetPayHash : hash.toString(),
						paymentOrderId : paymentOrderId.toString(),
						isSaveCard : saveCard
					};
					var win = document.getElementById("childIframeId").contentWindow;
					win.postMessage(jsonData, '*');
			}else{
				$("#statusBlock-payment").css("display", "block");	
					  $("#tcloginErrorSpan-payment").css("display", "block");	
					  $( "#tcloginErrorSpan-payment" ).html("Please enter the correct donation amount");				
			}	
			$(".k-loading-mask").hide();
			$.unblock(); 
		}else {
			document.forms["createDonationForm"].submit();
		}
		$(".k-loading-mask").hide();
		$.unblock(); 
	}
	function clearInvoiceGrid(){
		$('#billingInstalmentsTableId').css("display", "none");
		$('#billingInstalmentsTbody').html("");
		billingFlag = false;
	}
	
	function showPlanEndDateErrorDialogue(){
		var kendoWindow = $("<div />").kendoWindow({
			title: "Error",
			resizable: false,
			modal: true,
			width:400
		});

		kendoWindow.data("kendoWindow")
		 .content($("#plan-End-ErrorBox").html())
		 .center().open();

		kendoWindow
		.find(".confirm-plan-end")
		.click(function() {
			if ($(this).hasClass("confirm-plan-end")) {         		
				kendoWindow.data("kendoWindow").close();
			}
		})
		.end();
		
	}
	
	function showBillingStartDateError(){
		var kendoWindow = $("<div />").kendoWindow({
			title: "Error",
			resizable: false,
			modal: true,
			width:400
		});

		kendoWindow.data("kendoWindow")
		 .content($("#billing-start-ErrorBox").html())
		 .center().open();

		kendoWindow
		.find(".confirm-billing-date")
		.click(function() {
			if ($(this).hasClass("confirm-billing-date")) {         		
				kendoWindow.data("kendoWindow").close();
			}
		})
		.end();
		
	}
	
	function showBillingDateError(){
		var kendoWindow = $("<div />").kendoWindow({
			title: "Error",
			resizable: false,
			modal: true,
			width:400
		});

		kendoWindow.data("kendoWindow")
		 .content($("#billing-date-ErrorBox").html())
		 .center().open();

		kendoWindow
		.find(".confirm-billingDate")
		.click(function() {
			if ($(this).hasClass("confirm-billingDate")) {         		
				kendoWindow.data("kendoWindow").close();
			}
		})
		.end();		
	}
	
	function processBilling(){
		billingFlag = true;
		var billingSetupHtml = '';
		billingSetupHtml += getBillingSetupHtml();
		var dateToday = new Date();  
		$('#billingInstalmentsTbody').html(billingSetupHtml);	
		var datePicker = $(".billingSetUpBillDate").kendoDatePicker({
			format: "MM/dd/yyyy",
			change: function (e) {							
				var prev = $(this).data("previous");
				var todayDate = kendo.toString(kendo.parseDate(new Date()), 'MM/dd/yyyy');	
				var prevDate = kendo.toString(kendo.parseDate(prev), 'MM/dd/yyyy');	
				var endBillDate = new Date(e.sender.value());				
				var annualPlanEndDate = new Date("${annualDonationDueDtYear}","${annualDonationDueDtMonth - 1}","${annualDonationDueDtDate}");						
				if(endBillDate > annualPlanEndDate){
					showBillingDateError();						
					var thisDatepicker = $('#'+e.sender.element[0].id).data("kendoDatePicker");
					if(prevDate){
						thisDatepicker.value(prevDate);
					}else{
						thisDatepicker.value(todayDate);
					}										
				} else{
					 $(this).data("previous", this.value());
				}		        
			},
			min: dateToday
		}).data("kendoDatePicker");
		$(datePicker).data("previous", "");
			
		$('#billingInstalmentsTableId').css("display", "");
	}
	
	function showBillingSetupErrorBox(){
		var kendoWindow = $("<div />").kendoWindow({
			title: "Error",
			resizable: false,
			modal: true,
			width:400
		});

		kendoWindow.data("kendoWindow")
		 .content($("#billing-setupError-ErrorBox").html())
		 .center().open();

		kendoWindow
		.find(".confirm-billingsetup")
		.click(function() {
			if ($(this).hasClass("confirm-billingsetup")) {         		
				kendoWindow.data("kendoWindow").close();
			}
		})
		.end();		
	}
	
	/* function formatDonationAmount(evt, thisObj) {
	       var charCode = (evt.which) ? evt.which : evt.keyCode;
	       var thisObject = $(thisObj);	      
	       if (charCode != 46 && charCode > 31 && (charCode < 48 || charCode > 57)){
	    	   if($("#donationAmtOtherTxt").val().trim() == ''){
	   				$("#donationAmtErr").css("display", "");
		    		$("#donationAmtErr").text("Please enter the Numbers only");
		   		}
	    	   return false;    	   
	    	}else{
	    		$("#donationAmtErr").css("display", "none");
				$("#donationAmtErr").text("");
				var donationAmtVal = thisObject.attr("value");
				if(donationAmtVal){
					var donationAmtValArr = donationAmtVal.split(".");
					if(donationAmtValArr && donationAmtValArr.length > 2){
						return false; 
					}else if(donationAmtValArr && donationAmtValArr[1] && donationAmtValArr[1].length >1){
						return false;
					}
				}
				var totalAmt = 0;
				$(document).find('.donationInvoiceAmtCls').each(function(i, obj) {	
					console.log($(obj).attr("value"));
					totalAmt = totalAmt + parseFloat($(obj).attr("value"));
				});
				console.log(totalAmt);
				return true;
	    	}
	          
	       
	    } */
</script>

</body>