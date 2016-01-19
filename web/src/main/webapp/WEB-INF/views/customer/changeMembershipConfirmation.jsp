<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ include file="../../layouts/include_taglib.jsp"%>
    <%
    	String contextPath = request.getContextPath();
    %>
    
<body>

<div id="main">
	
	<div id="content" class="bootstrap-frm" style="border: 1px solid #DDD; max-width:950px;">
		<h2 style="color: #888; text-align: center;">Confirmation</h2>
		<h4 style="color: #888; text-align: center;">Please print the Registration Information.</h4>
			
			
			<div style="max-width : auto;" class="">
				<table style="" border="0" width="100%">							
	
					<tr>
						<td style="vertical-align: top;">
							<table style="" border="0" width="100%">							
										<tbody >	
											<tr>
												<td width="100%">
													<table width="100%" style="/* float: left; margin-left: 10px; */"
														class="summary-table-border">
														<tbody style="min-height: 300px;">
															<tr>
																<td colspan="2" style=" ">
																	<h3>MEMBER INFORMATION</h3>
																</td>
																<td></td>
															</tr>
															
															<tr>
																<td colspan="2" style="">
																	<span style="font-size: 13px;"><b><h4><u>PRIMARY MEMBER<u></u></h4></b></span>
																</td>
																<td></td>
															</tr>
				
															<tr>
																<td><span><b>First Name</b></span></td>
																<td id="firstNameTd">${primaryUser.firstName}</td>
															</tr>
															<tr>
																<td><span><b>Last Name</b></span></td>
																<td id="lastNameTd">${primaryUser.lastName}</td>
															</tr>
															<fmt:formatDate type="date" pattern="MM/dd/yyyy" value="${primaryUser.dateOfBirth}" var="primaryUserDOB"/>
															<tr>
																<td><span><b>Date of Birth</b></span></td>
																<td id="dobTd">${primaryUserDOB}</td>
															</tr>
															<tr>
																<td><span><b>Phone Number</b></span></td>
																<td id="phoneNumberTd">${primaryUser.formattedPhoneNumber}</td>
															</tr>
															<c:if test="${primaryUser.emailAddress != null}">
															<tr>
																<td><span><b>Email</b></span></td>
																<td id="emailTd">${primaryUser.emailAddress}</td>
															</tr>
															</c:if>
															<tr>
																<td><span><b>Gender</b></span></td>
																<td id="genderTd">${primaryUser.gender}</td>
															</tr>
														</tbody>
													</table>
				
												</td>
												
											</tr>
											<c:if test="${secUser != null}">
											
												<c:choose>
													<c:when test="${product.recordName eq 'Youth' && isYouthUserAdult}">
														<tr id="secMemberTr" style="display : none;">
													</c:when>
													<c:otherwise>
														<tr id="secMemberTr" >
													</c:otherwise>
												</c:choose>												
														<td width="100%">
															<table width="100%" style="/* float: left; margin-left: 10px; */"
																class="summary-table-border">
																<tbody style="min-height: 300px;">
																	<tr>
																		<td colspan="2" style="">
																			<span style="font-size: 13px;"><b><h4><u>SECONDARY MEMBER<u></u></h4></b></span>
																		</td>
																		<td></td>
																	</tr>																			
																	<tr>
																		<td><span><b>First Name</b></span></td>
																		<td id="secMemFirstNameTd">${secUser.firstName}</td>
																	</tr>
																	<tr>
																		<td><span><b>Last Name</b></span></td>
																		<td id="secMemLastNameTd">${secUser.lastName}</td>
																	</tr>
																	<fmt:formatDate type="date" pattern="MM/dd/yyyy" value="${secUser.dateOfBirth}" var="secondaryUserDOB"/>
																	<tr>
																		<td><span><b>Date of Birth</b></span></td>
																		<td id="secMemDobTd">${secondaryUserDOB}</td>
																	</tr>
																	<tr>
																		<td><span><b>Phone Number</b></span></td>
																		<td id="secMemPhoneNumberTd">${secUser.formattedPhoneNumber}</td>
																	</tr>
																	<c:if test="${secUser.emailAddress != null}">
																	<tr>
																		<td><span><b>Email</b></span></td>
																		<td id="secMemEmailTd">${secUser.emailAddress}</td>
																	</tr>
																	</c:if>
																	<tr>
																		<td><span><b>Gender</b></span></td>
																		<td id="secGenderTd">${secUser.gender}</td>
																	</tr>
																</tbody>
															</table>
						
														</td>
													</tr>
												
											</c:if>
											<c:if test="${thirdUser != null}">
											<tr id="thirdMemberTr" width="100%" >
												<td width="100%">
													<table width="100%" style="/* float: left; margin-left: 10px; */"
														class="summary-table-border">
														<tbody style="min-height: 300px;">
															<tr>
																<td colspan="2" style="">
																	<span style="font-size: 13px;"><b><h4><u>THIRD MEMBER<u></u></h4></b></span>
																</td>
																<td></td>
															</tr>
				
															<tr>
																<td><span><b>First Name</b></span></td>
																<td id="thirdMemFirstNameTd">${thirdUser.firstName}</td>
															</tr>
															<tr>
																<td><span><b>Last Name</b></span></td>
																<td id="thirdMemLastNameTd">${thirdUser.lastName}</td>
															</tr>
															<fmt:formatDate type="date" pattern="MM/dd/yyyy" value="${thirdUser.dateOfBirth}" var="thirdUserDOB"/>
															<tr>
																<td><span><b>Date of Birth</b></span></td>
																<td id="thirdMemDobTd">${thirdUserDOB}</td>
															</tr>
															<tr>
																<td><span><b>Phone Number</b></span></td>
																<td id="thirdMemPhoneNumberTd">${thirdUser.formattedPhoneNumber}</td>
															</tr>
															<c:if test="${thirdUser.emailAddress != null}">
															<tr>
																<td><span><b>Email</b></span></td>
																<td id="thirdMemEmailTd">${thirdUser.emailAddress}</td>
															</tr>
															</c:if>
															<tr>
																<td><span><b>Gender</b></span></td>
																<td id="thirdGenderTd">${thirdUser.gender}</td>
															</tr>
														</tbody>
													</table>
				
												</td>
											</tr>
											</c:if>
											<c:if test="${fn:length(kidsInfo) gt 0}">
											<tr>
												<td>
													<table id="kidsInformationTable" width="100%" style="margin-left: 5px;">	
														<thead>
															<tr>
																<td colspan="2" style="  color: #888;">
																	<h3>KIDS INFORMATION</h3>
																</td>
																<td></td>
															</tr>	
														</thead>
														<tbody id="kidsInformationTableTbody">
															<c:forEach var="kid" items="${kidsInfo}" varStatus="count">	
																<tr>
																	<td>&nbsp;</td>
																	<td>&nbsp;</td>
																</tr>
															
																<tr>
																	<td><span><b>First Name</b></span></td>
																	<td>${kid.firstName }</td>
																</tr>
															
																<tr>
																	<td><span><b>Last Name</b></span></td>
																	<td>${kid.lastName }</td>
																</tr>
																<fmt:formatDate type="date" pattern="MM/dd/yyyy" value="${kid.dateOfBirth}" var="kidDOB"/>
																<tr>
																	<td><span><b>Date of Birth</b></span></td>
																	<td>${kidDOB }</td>
																</tr>
															
																<tr>
																	<td><span><b>Gender</b></span></td>
																	<td>${kid.gender }</td>
																</tr>
															</c:forEach>
														</tbody>																
													</table>
													
												</td>
											</tr>
											</c:if>																							
										</tbody>
								</table>
								
						</td>	
						<td style="vertical-align: top;">
							<table style="" border="0" width="100%">							
										<tbody >	
											<tr>
												<td width="100%">
													<table style="/* float: left; margin-left: 10px; */"
														class="summary-table-border">
														<tbody style="min-height: 300px;">
															<tr>
																<td colspan="2" style=" "><h3>
																		ADDRESS INFORMATION</h3></td>
																<td></td>
															</tr>
				
				
															<tr>
																<td><span><b>Address Line 1</b></span></td>
																<td id="addressLine1Td">${account.addressLine1}</td>
															</tr>
															<tr>
																<td><span><b>Address Line 2</b></span></td>
																<td id="addressLine2Td">${account.addressLine2}</td>
															</tr>
															<tr>
																<td><span><b>City</b></span></td>
																<td id="cityTd">${account.city}</td>
															</tr>
															<tr>
																<td><span><b>State</b></span></td>
																<td id="stateTd">${account.state}</td>
															</tr>
															<tr>
																<td><span><b>Postal Code</b></span></td>
																<td id="postalCodeTd">${account.postalCode}</td>
															</tr>
				
														</tbody>
													</table>
												</td>
											</tr>							
											<tr>
												<td width="100%">
													<table style="/* float: left; margin-left: 5px; */"
														class="summary-table-border">
														<tbody style="min-height: 300px;">
															<tr>
																<td colspan="2" style=" ">
																	<h3>PAYMENT INFORMATION</h3>
																</td>
																<td></td>
															</tr>
																				<!-- <tr>
																<td ><span><b>Product Id</b></span></td>
																<td id="productIdTd"></td>
															</tr> -->
															<c:if test="${paymentTransId != null}">
															<tr>
																<td ><span><b>Payment Transaction Id</b></span></td>
																
																	<td id="productIdTd">${paymentTransId}</td>
																															
															</tr>
															</c:if>	
															<c:if test="${signup.joinFee != null}">	
															<tr>
																<td><span><b>Join Fee</b></span></td>
																<td ><span>$</span><span id="sumJoinFeeTd"></span>${signup.joinFee }</td>
															</tr>
															</c:if>
															<c:if test="${signup.signupPrice != null}">
															<tr>
																<td><span><b>Signup Price</b></span></td>
																<td ><span>$</span><span id="sumTierPriceTd">${signup.signupPrice }</span></td>
															</tr>
															</c:if>
															<c:if test="${signup.registrationFee != null}">
																<fmt:parseNumber var="registrationFee" type="number" value="${signup.registrationFee}" />
																<c:if test="${registrationFee > 0}">
																	<tr >
																		<td><span><b>Registration Fee</b></span></td>
																		<td ><span>$</span><span id="sumRegistrationFee">${signup.registrationFee }</span></td>
																	</tr>
																</c:if>
															</c:if>
															<c:if test="${signup.deposit != null}">
															<fmt:parseNumber var="deposit" type="number" value="${signup.deposit}" />
																<c:if test="${deposit > 0}">
																<tr >
																	<td><span><b>Deposit Amount</b></span></td>
																	<td ><span>$</span><span id="sumDepositAmount">${signup.deposit }</span></td>
																</tr>
																</c:if>
															</c:if>															
															
															<c:if test="${signup.finalAmount != null}">
																<fmt:parseNumber var="finalAmount" type="number" value="${signup.finalAmount}" />
																<c:if test="${finalAmount > 0}">
																	<tr>
																		<td><span><b>Total Price</b></span></td>
																		<td id="productIdTd"><span>$ </span>${signup.finalAmount }</td>																															
																	</tr>
																</c:if>															
															</c:if>
															
														</tbody>
													</table>
				
												</td>
											</tr>
											<tr>
												<td width="100%">
													<table style="" class="summary-table-border">
														<tbody style="min-height: 300px;">
															<tr>
																<td colspan="2" style=" ">
																	<h3>MEMBERSHIP INFORMATION</h3>
																</td>
																<td></td>
															</tr>
															<tr style="display:none">
																<td ><span><b>Product Id</b></span></td>
																<td id="productIdTd"></td>
															</tr>
															<tr style="display: none">
																<td><span><b>Product Id</b></span></td>
																<td id="productIdTd"></td>
															</tr>
															<tr>
																<td><span><b>Product Name</b></span></td>
																<td id="productNameTd">${product.recordName}</td>
															</tr>
															<tr>
																<td><span><b>Product Description</b></span></td>
																<td id="productDescriptionTd">${product.description}</td>
															</tr>
															<tr>
																<td><span><b>Product Type</b></span></td>
																<td id="productTypeTd">${product.type}</td>
															</tr>
												</tbody>
													</table>
												</td>
											</tr>
								</tbody>
								</table>
							
						<td>			
					<tr>
				</table>					
										
			</div>
		</div>		
	</div>
		
	</div>


<script>
	$(document).ready(function() {
		//$("#confirmationData").kendoGrid();
		$("#page_name").html("Change Member Wizard");
		
	});
</script>

</body>