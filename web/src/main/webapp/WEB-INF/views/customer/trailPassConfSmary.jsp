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
			<%-- <c:when test="${alreadyUSedTP==null }"> --%>
			
			<div style="max-width : auto;" class="">
				<table style="" border="0" width="100%">							
	
					<tr>
						<td style="vertical-align: top;">
							<table style="" border="0" width="100%">							
										<tbody >	
											<tr>
												<td width="100%">
													<table style="/* float: left; margin-left: 10px; */"
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
																<td id="firstNameTd">${primaryUser.personFirstName}</td>
															</tr>
															<tr>
																<td><span><b>Last Name</b></span></td>
																<td id="lastNameTd">${primaryUser.personLastName}</td>
															</tr>
															<tr>
																<td><span><b>Date of Birth</b></span></td>
																<td id="dobTd"><fmt:formatDate type="date" pattern="MM/dd/yyyy" value="${primaryUser.dateOfBirth}" /></td>
															</tr>
															<tr>
																<td><span><b>Phone Number</b></span></td>
																<td id="phoneNumberTd">${primaryUser.primaryFormattedPhoneNumber}</td>
															</tr>
															<tr>
																<td><span><b>Email</b></span></td>
																<td id="emailTd">${primaryUser.primaryEmailAddress}</td>
															</tr>
															<tr>
																<td><span><b>Gender</b></span></td>
																<td id="genderTd">${primaryUser.gender}</td>
															</tr>
														</tbody>
													</table>
				
												</td>
												
											</tr>
											<c:if test="${secUser != null}">
											<tr id="secMemberTr" >
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
																<td id="secMemFirstNameTd">${secUser.personFirstName}</td>
															</tr>
															<tr>
																<td><span><b>Last Name</b></span></td>
																<td id="secMemLastNameTd">${secUser.personLastName}</td>
															</tr>
															<tr>
																<td><span><b>Date of Birth</b></span></td>
																<td id="secMemDobTd">${secUser.dateOfBirth}</td>
															</tr>
															<tr>
																<td><span><b>Phone Number</b></span></td>
																<td id="secMemPhoneNumberTd">${secUser.primaryFormattedPhoneNumber}</td>
															</tr>
															<tr>
																<td><span><b>Email</b></span></td>
																<td id="secMemEmailTd">${secUser.primaryEmailAddress}</td>
															</tr>
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
													<table style="/* float: left; margin-left: 10px; */"
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
																<td id="thirdMemFirstNameTd">${thirdUser.personFirstName}</td>
															</tr>
															<tr>
																<td><span><b>Last Name</b></span></td>
																<td id="thirdMemLastNameTd">${thirdUser.personLastName}</td>
															</tr>
															<tr>
																<td><span><b>Date of Birth</b></span></td>
																<td id="thirdMemDobTd">${thirdUser.dateOfBirth}</td>
															</tr>
															<tr>
																<td><span><b>Phone Number</b></span></td>
																<td id="thirdMemPhoneNumberTd">${thirdUser.primaryFormattedPhoneNumber}</td>
															</tr>
															<tr>
																<td><span><b>Email</b></span></td>
																<td id="thirdMemEmailTd">${thirdUser.primaryEmailAddress}</td>
															</tr>
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
																	<td>${kid.personFirstName }</td>
																</tr>
															
																<tr>
																	<td><span><b>Last Name</b></span></td>
																	<td>${kid.personLastName }</td>
																</tr>
															
																<tr>
																	<td><span><b>Date of Birth</b></span></td>
																	<td>${kid.dateOfBirth }</td>
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
													<%-- <table style="/* float: left; margin-left: 5px; */"
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
															<tr>
																<td ><span><b>Payment Transaction Id</b></span></td>
																<c:if test="${paymentTransId != null}">
																	<td id="productIdTd">${paymentTransId}</td>
																</c:if>																	
															</tr>
															<tr>
																<td><span><b>Join Fee</b></span></td>
																<c:if test="${prodJoinFee != null}">
																	<td id="productIdTd"><span>$ </span>${prodJoinFee}</td>
																</c:if>
																
															</tr>
															<tr>
																<td><span><b>Price</b></span></td>
																<c:if test="${productPrice != null}">
																	<td id="productIdTd"><span>$ </span>${productPrice}</td>
																</c:if>
																
															</tr>
															<!-- <tr>
																<td><span><b>Payment Frequency</b></span></td>
																<td id="sumPaymentFreqTd"></td>
															</tr> -->
															<tr>
																<td><span><b>Total Price</b></span></td>
																<!-- <td id="sumTotalPriceTd"></td> -->
																<c:if test="${productTotalPrice != null}">
																	<td id="productIdTd"><span>$ </span>${productTotalPrice}</td>
																</c:if>																	
															</tr>
															
														</tbody>
													</table>
				 --%>
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
					<tr>
						<td></td>
						<td ><span id="" style="float:right; margin-top:10px;"><a href="/ymca-web/login">Go to Login Page</a></span></td>
					</tr>
				</table>					
										
			</div>
			<%-- </c:when>
			<c:otherwise>
			
			<h3>You have already used your Trial Pass..!!</h3>
			
			</c:otherwise> --%>
		</div>		
	</div>
		



<script>
	/* $(document).ready(function() {
		//$("#confirmationData").kendoGrid();
		
	}); */
	$(document).ready(function() {
		$("#page_name").html("Trialpass Membership Wizard");
	});
</script>

</body>