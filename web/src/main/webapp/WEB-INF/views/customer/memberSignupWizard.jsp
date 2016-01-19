<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<body>
	<%@ include file="../../layouts/include_taglib.jsp"%>
	<script src="<%=contextPath %>/resources/js/payment/luhn.js"></script>
	<script src="<%=contextPath %>/resources/js/payment/view.js"></script>
	<script src="<%=contextPath %>/resources/js/app/common_new.js"></script>
	<script src="<%=contextPath %>/resources/js/customer/membership/common-membership.js"></script>
	
	<div id="main544">
		<div class="k-loading-mask"
			style="width: 100%; height: 100%; position: absolute; top: 114px; left: 0px; display: none; z-index: 9999">
			<span class="k-loading-text">Loading... Please wait</span>
			<div class="k-loading-image">
				<div class="k-loading-color"></div>
			</div>
		</div>
		<div id="content33">
			<div id="contentFormDiv223">
				
				<div id="payment-Error-Box" type="text/x-kendo-template" style="display:none;">
				    <p class="error-message-p payment-Error-Box-msg-p"></p>
					
				    <div class="confirmbutton" align="center"><button class="confirm-payment-process k-button" style=" width: 35%;">Ok</button>  </div> 
				</div>
				
				<table align="center" border="0" cellpadding="0" cellspacing="0">
				<tr><td> 
				<!-- Smart Wizard -->
				        
				  		<div id="wizard" class="swMain">
				  			<ul>
				  				<li>
					  				<a href="#step-1">
					                <!-- <label class="stepNumber">1</label> -->
					                <span class="stepDesc">
					                  1. MEMBERSHIP INFO<br />
					                   <!-- <small>&nbsp;</small> -->
					                </span>
					            	</a>
				            	</li>
				  				<li><a href="#step-2">
				                <!-- <label class="stepNumber">1</label> -->
				                <span class="stepDesc">
				                  2. USER INFORMATION	<br />
				                   <!-- <small>&nbsp;</small> -->
				                </span>
				            </a></li>
				  				<li><a href="#step-3">
				               <!--  <label class="stepNumber">2</label> -->
				                <span class="stepDesc">
				                   3. T & C<br />
				                   <!-- <small>Step 2 description</small> -->
				                </span>
				            </a></li>
				            <li><a href="#step-4">
				               <!--  <label class="stepNumber">2</label> -->
				                <span class="stepDesc">
				                   4. Payment Information<br />
				                   <!-- <small>Step 2 description</small> -->
				                </span>
				            </a></li>
				            <li><a href="#step-5">
				               <!--  <label class="stepNumber">2</label> -->
				                <span class="stepDesc">
				                   5. Summary<br />
				                   <!-- <small>Step 2 description</small> -->
				                </span>
				            </a></li>
				  			</ul>
				  			<div id="step-1">	
				            <p>
				            <div class="border-bottom-h3">
				            	<span style="margin-right: 15px;"> Please select YMCA location</span>
				            <select name="location" id="location" style="width:250px;" ><br />
										<option value="0">--Select Location--</option>
										<c:forEach var="location" items="${locations}" varStatus="count">
											<c:if test="${ location.recordName != 'All Branches' && location.recordName != 'Bay Area' && location.branch == 'true' }">
												<c:if test="${ location.recordName != 'El Camino YMCA'}">
													<option value="${location.id }">${location.recordName }</option>
												</c:if>
												<c:if test="${ location.recordName == 'El Camino YMCA'}">
													<option value="${location.id }" selected="selected">${location.recordName }</option>
												</c:if>
												
											</c:if>
										</c:forEach>
							</select> 
							
							<%-- <select style="width:250px; display: none; margin-left : 10px;" id="pricingOption" name="prefLocation" >
								<option value="0">--Select Pricing Options--</option>
								<c:forEach var="location" items="${locations}" varStatus="count">
									<c:if test="${ location.branchName == 'All Branches' || location.branchName == 'Bay Area' }">
										<option value="${location.locationId }">${location.branchName }</option>
									</c:if>
								</c:forEach>
								
								<option value="This Branch Only">This Branch Only</option>																	
							</select> --%>
							</div>	
							
							<%-- <div id="allLocations" style="display:none;">
								<option value="0">--Select Preferred Location--</option>
								<c:forEach var="location" items="${locations}" varStatus="count">
									<c:if test="${ location.branchName != 'All Branches' && location.branchName != 'Bay Area' }">
										<option value="${location.locationId }">${location.branchName }</option>
									</c:if>					
								</c:forEach>	
							</div>
							
							<div id="bayAreaLocations" style="display:none;">
								<option value="0">--Select Preferred Location--</option>
								<c:forEach var="location1" items="${bayAreaLocations}" varStatus="count">
									<option value="${location1.locationId }">${location1.branchName }</option>
								</c:forEach>
							</div> --%>	
							
							<!-- <div id="example" style="margin-top: 10px;">
				            	<div id="grid"></div>
				            </div> -->    
				            <div style="height: 370px;">	
				            <span style="display : none;" id="kidsAgeValidation">${kidsAgeValidation }</span>	
				            <%-- <span style="display : none;" id="adultAgeValidation">${adultAgeValidation }</span> --%>
				            <span style="display : none;" id="adultAgeValidationLowerLimit">${adultAgeValidationLowerLimit }</span>
  							<span style="display : none;" id="adultAgeValidationUpperLimit">${adultAgeValidationUpperLimit }</span>
  							<span style="display : none;" id="seniorAgeValidationLimit">${seniorAgeValidationLimit }</span>  							
				            <span style="display : none;" id="youthAgeValidationLowerLimit">${youthAgeValidationLowerLimit }</span>
				            <span style="display : none;" id="youthAgeValidationUpperLimit">${youthAgeValidationUpperLimit }</span>
				            <c:if test="${pageContext.request.userPrincipal.isAuthenticated()}">
				            	<span style="display : none;" id="isUserLoggedIn">true</span>
				            </c:if>
				            <c:if test="${!pageContext.request.userPrincipal.isAuthenticated()}">
				            	<span style="display : none;" id="isUserLoggedIn">false</span>
				            </c:if>
				            <c:if test="${not empty existingMemberJoinFee}">
				            	<input 	style="display : none;" name="existingMemberJoinFeeInput" id="existingMemberJoinFeeInput" value="${existingMemberJoinFee}"/>
				             </c:if>  
				            <c:if test="${empty existingMemberJoinFee}">
				            	<input 	style="display : none;" name="existingMemberJoinFeeInput" id="existingMemberJoinFeeInput" value="0"/>
				             </c:if> 				            
				               
				            <ul id="panels">
						
										<!-- <li>
											<div class="k-block k-block-div">
												<div class="k-header k-shadow k-header-custom-gray" >Adult</div>
												<div>
													<div class="price-div">$ 90</div>
													<div class="product-description-div">One Adult<br />No Kids</div>
													<div class="product-location-div">All Locations</div>
													<div class="border-bottom-line"></div>
													<div class="product-join-fee-text-div">One Time Join Fee</div>
													<div class="product-join-fee-text-val-div">$105</div>
													<div class="k-button product-register-div">Register</div>
												</div>						
											</div>
										</li>
										<li>
											<div class="k-block k-block-div">
												<div class="k-header k-shadow k-header-custom-orange">Youth</div>
												<div>
													<div class="price-div">$ 90</div>
													<div class="product-description-div">One Youth Unlimited Kids</div>
													<div class="product-location-div">All Locations</div>
													<div class="border-bottom-line"></div>
													<div class="product-join-fee-text-div">One Time Join Fee</div>
													<div class="product-join-fee-text-val-div">$105</div>
													<div class="k-button product-register-div">Register</div>
												</div>	
											</div>
										</li>
										<li>
											<div class="k-block k-block-div">
												<div class="k-header k-shadow k-header-custom-gray">One Adult<br />w/ Kids</div>
												<div>
													<div class="price-div">$ 90</div>
													<div class="product-description-div">One Adult Unlimited Kids</div>
													<div class="product-location-div">All Locations</div>
													<div class="border-bottom-line"></div>
													<div class="product-join-fee-text-div">One Time Join Fee</div>
													<div class="product-join-fee-text-val-div">$105</div>
													<div class="k-button product-register-div">Register</div>
												</div>	
											</div>
										</li>
										<li>
											<div class="k-block k-block-div">
												<div class="k-header k-shadow k-header-custom-orange">Two Adults</div>
												<div>
													<div class="price-div">$ 90</div>
													<div class="product-description-div">One Adult Unlimited Kids</div>
													<div class="product-location-div">All Locations</div>
													<div class="border-bottom-line"></div>
													<div class="product-join-fee-text-div">One Time Join Fee</div>
													<div class="product-join-fee-text-val-div">$105</div>
													<div class="k-button product-register-div">Register</div>
												</div>	
											</div>
										</li>
										<li>
											<div class="k-block k-block-div">
												<div class="k-header k-shadow k-header-custom-gray">Two Adults<br />w/ Kids</div>
												<div>
													<div class="price-div">$ 90</div>
													<div class="product-description-div">One Adult Unlimited Kids</div>
													<div class="product-location-div">All Locations</div>
													<div class="border-bottom-line"></div>
													<div class="product-join-fee-text-div">One Time Join Fee</div>
													<div class="product-join-fee-text-val-div">$105</div>
													<div class="k-button product-register-div">Register</div>
												</div>	
											</div>
										</li>
										<li>
											<div class="k-block k-block-div">
												<div class="k-header k-shadow k-header-custom-orange">Three Adults<br />w/ or w/o kids</div>
												<div>
													<div class="price-div">$ 90</div>
													<div class="product-description-div">One Adult Unlimited Kids</div>
													<div class="product-location-div">All Locations</div>
													<div class="border-bottom-line"></div>
													<div class="product-join-fee-text-div">One Time Join Fee</div>
													<div class="product-join-fee-text-val-div">$105</div>
													<div class="k-button product-register-div">Register</div>
												</div>	
											</div>
										</li> -->
						
									</ul>
								</div> 
							
							</div>
							<div id="step-2">			
							<div id="registerDiv" >
				            	<form:form commandName="account" method="post" action="becomeMemberRegister" id="becomeMemberForm" style="border : none; padding : 0px; margin : 0px; max-width: 100%;" autocomplete="off"
							              class="bootstrap-frm cmxform" >	
							              
							        <input type="hidden" id="signupContactId" name="signupContactId" value="">
	
									<input type="hidden" id="urlPromoItemDetailId" value="">
									<input type="hidden" id="urlPromoContactId" value="">
									<input type="hidden" id="urlPromoCode" value="">
									<input type="hidden" id="selectedProductRadioInputId" value="">
								
									<input type="hidden" id="signUpPromoDiscountHiddenInput" name="signUpPromoDiscountHiddenInput" value="">
									<input type="hidden" id="otherPromoDiscountHiddenInput" name="otherPromoDiscountHiddenInput" value="">
									<input type="hidden" id="sumOfAdditivesHiddenInput" name="sumOfAdditivesHiddenInput" value="">
									<input type="hidden" id="isRecurringHiddenInput" name="isRecurringHiddenInput" value="">
									<input type="hidden" id="faAmtHiddenInput" name="faAmtHiddenInput" value="">
									
									<input type="hidden" id="invoiceDate" name="invoiceDate" value="">
									<input type="hidden" id="billDate" name="billDate" value="">
									<input type="hidden" id="dueDate" name="dueDate" value="">
									<input type="hidden" id="nextBillDate" name="nextBillDate" value="">
									<input type="hidden" id="billDateOnInvoice" name="billDateOnInvoice" value="">
									<input type="hidden" id="dueDateOnInvoice" name="dueDateOnInvoice" value="">
									<input type="hidden" id="paymentFrequency" name="paymentFrequency" value="">
									
									<input type="hidden" id="joinFeeMonthly" name="joinFeeMonthly" value="">
									<input type="hidden" id="joinFeeYealy" name="joinFeeYealy" value="">
									<input type="hidden" id="signupPriceMonthly" name="signupPriceMonthly" value="">
									<input type="hidden" id="signupPriceYearly" name="signupPriceYearly" value="">
									<input type="hidden" id="registrationFee" name="registrationFee" value="">
									<input type="hidden" id="depositAmount" name="depositAmount" value="">
									<!-- <input type="text" id="productId" name="productId" value=""> -->
									<input type="hidden" id="monthlyBillingFreqency" name="monthlyBillingFreqency" value="">
									<input type="hidden" id="annualBillingFreqency" name="annualBillingFreqency" value="">
							         <table border="0" width="100%" id="youthInfoFormTable">		
									 					 	
									 </table>     		        
									 <table border="0" width="100%" id="userInfoTb" class="primaryUserInfoTable">					 						 
									 	<tr>
									 		<td ><h2 style="margin-top: 5px; margin-bottom : 5px;"><span id="primaryMemberTxtSpan">PRIMARY MEMBER</span></h2> </td>
									 		
									 		<td > 
									 			<span id="memberSignInSpan" class="k-button" style="width: 130px;text-shadow: none;float: right; margin-left:10px; display:none;">Have Login? SignIn</span>
									 			<select  style="width:200px;" id="selectContactPrimaryAdult" class="selectContactDropDw">
													<option value="0">--Select contact--</option>
													<c:forEach var="adultUser" items="${userLstInfo}" varStatus="count">
														<c:if test="${ adultUser != null}">
															<c:set var="dobVar" ><fmt:formatDate type="date" pattern="MM/dd/yyyy" value="${adultUser.dateOfBirth }" /></c:set>															
															<option value="${adultUser.firstName }||${adultUser.lastName }||${adultUser.formattedPhoneNumber }||${adultUser.emailAddress }||${dobVar }||${adultUser.gender }||${adultUser.contactId }">${adultUser.firstName } ${adultUser.lastName }</option>															  
														</c:if>
													</c:forEach>
													<option value="Other">New Member</option>
												</select>
									 		</td>
									 		<td>&nbsp;</td>
									 		<td >
									 			<c:if test="${pageContext.request.userPrincipal.isAuthenticated()}">
									 				<span id="selectFamilyMemberbtn" class="k-button" style="width: 115px;text-shadow: none;float: left; display:none;">Select Member</span>
									 			</c:if>	
											</td>
									 		<!-- <span id="newMemberSpan" class="k-button" style="width: 70px;text-shadow: none;float: right; ">New Member?</span> -->
									 	</tr>
									 	<tr>
											<c:choose>
												<c:when test="${primaryUser != null && primaryUser.firstName != null }">
													<td><form:input cssClass="form-control" path="userLst[0].firstName" title="Please enter your First Name" name="firstName" id="firstName" maxlength="50" placeholder="First Name" value="" /></td>
												</c:when>
												<c:otherwise>
													<td><form:input cssClass="form-control" path="userLst[0].firstName" title="Please enter your First Name" name="firstName" id="firstName" maxlength="50" placeholder="First Name"  /></td>
												</c:otherwise>
											</c:choose>
																		 		
									 		<td>&nbsp;</td>
									 		<td><form:input cssClass="form-control" path="userLst[0].lastName" title="Please enter your Last Name" name="lastName" id="lastName" maxlength="50" placeholder="Last Name" /></td>
									 		<td>&nbsp;</td>
									 	</tr>
									 	<tr>		 		
									 		<td style="padding-bottom: 8px;">
									 			<form:input  path="userLst[0].dateOfBirth"  name="dob" id="dob" maxlength="50" placeholder="Date of Birth"  style="width: 1px; height: 1px; left : -1000px; margin: 0; padding: 0;  " />
									 			<span class="">Date of Birth : </span> <br />
												<span>
													<select name="dobMonth" id="dobMonthForm" style="width :90px;">													
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
													<select name="dobDate" id="dobDateForm" style="width :45px;">													
														<option value="01">1</option>
														<option value="02">2</option>
														<option value="03">3</option>
														<option value="04">4</option>
														<option value="05">5</option>
														<option value="06">6</option>
														<option value="07">7</option>
														<option value="08">8</option>
														<option value="09">9</option>
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
													<select name="dobYear" id="dobYearForm" style="width :70px;">
													</select>
												</span>
									 		</td>		
									 		<td id="dop-er"></td>
									 		<td><form:input cssClass="form-control" path="userLst[0].formattedPhoneNumber" title="Please enter your Phone Number" name="phoneNumber" id="phoneNumber" maxlength="50" placeholder="Phone Number" /></td>
									 		<td></td>
									 	</tr>
									 	
										<tr>		 		
									 		<td><form:input cssClass="form-control" path="userLst[0].emailAddress"  name="email" id="email"  maxlength="50" placeholder="Email"  /></td>			 							 		
									 		<td></td>
									 		<td>
									 		<%-- <span><form:radiobutton cssClass="form-control" path="workNumber" value="Male" id="genderM" />&nbsp;Male&nbsp;<form:radiobutton cssClass="form-control" path="workNumber" value="Female" id="genderF" />&nbsp;Female</span> --%>
									 			<span >
									 				<span><span><form:radiobutton cssClass="form-control primary-user-gender" path="userLst[0].gender" style="width : 15px;" value="Male" id="genderM" title="Please select gender"/></span><span style="margin-left : 5px;">Male</span></span>
									 				<span><span><form:radiobutton cssClass="form-control primary-user-gender" path="userLst[0].gender"  style="width : 15px;" value="Female" id="genderF" title="Please select gender" /></span><span style="margin-left : 5px;">Female</span></span>
									 			</span> 
									 		</td>
									 		<td></td>
									 	</tr>
									 	<%-- </c:if>
									 	<c:if test="${pageContext.request.userPrincipal.isAuthenticated()}">
										<tr>		 		
									 		<td></td>			 							 		
									 		<td></td>
									 		<td>
									 		<span><form:radiobutton cssClass="form-control" path="workNumber" value="Male" id="genderM" />&nbsp;Male&nbsp;<form:radiobutton cssClass="form-control" path="workNumber" value="Female" id="genderF" />&nbsp;Female</span>
									 			<span >
									 				<span><span><form:radiobutton cssClass="form-control primary-user-gender" path="userLst[0].gender" style="width : 15px;" value="Male" id="genderM" title="Please select gender"/></span><span style="margin-left : 5px;">Male</span></span>
									 				<span><span><form:radiobutton cssClass="form-control primary-user-gender" path="userLst[0].gender"  style="width : 15px;" value="Female" id="genderF" title="Please select gender" /></span><span style="margin-left : 5px;">Female</span></span>
									 			</span> 
									 		</td>
									 		<td></td>
									 	</tr>
									 	</c:if> --%>
									 	<tr>		 		
									 		<td>
									 			<span class="k-block k-success-colored" id="emailSuccessSpan"></span>
												<span class="k-block k-error-colored" id="emailErrorSpan"></span>
											</td>			 							 		
									 		<td></td>
									 		<td></td>
									 		<td></td>
									 	</tr>
									 	<c:if test="${!pageContext.request.userPrincipal.isAuthenticated() && sessionScope.agentUid == null}">
										<tr id="passwordTRremove">
									 		<td><form:password cssClass="form-control" path="userLst[0].password" name="password" id="password" maxlength="50" placeholder="Password" /></td>
									 		<td></td>
									 		<td><form:password cssClass="form-control" path="userLst[0].confirmPassword" id="confirm_password" name="confirm_password" maxlength="50" placeholder="Confirm Password" /></td>
									 		<td></td>
									 	</tr>
									 	</c:if>
									 	<c:if test="${sessionScope.agentUid != null}">
									 	<tr id="">
									 		<td colspan="2"><form:checkbox path="sendEmail" style="width : 15px !important;"/>Click here to send the reset password email</td>									 		
									 		<td></td>
									 		<td></td>
									 	</tr>
									 	</c:if>
									 	<tr>
									 		<td><form:input cssClass="form-control" path="userLst[0].referrerEmail" title="Please enter correct email" name="referrerEmail" id="referrerEmail" maxlength="50" placeholder="Referrer Email"  /></td>					 		
									 		<td>&nbsp;</td>
									 		<td>&nbsp;</td>
									 		<td>&nbsp;</td>
									 	</tr>									 	
									 </table>
									 <table border="0" width="100%" id="familyInfoFormTable">		
									 					 	
									 </table>
									 <table border="0" width="100%" id="userInfoTb">
										 <tr>
										 		<td colspan="2"><h2 style="margin-top: 5px; margin-bottom : 5px;"><span>ADDRESS</span></h2> </td>
										 		<td></td>
										 		<td></td>
										 		<td></td>
										 </tr>
										 <tr>
										 		<td><form:input cssClass="form-control" path="addressLine1" name="addressLine1" id="addressLine1" maxlength="50" placeholder="Address 1" value="${account.addressLine1}"/></td>
										 		<td></td>
										 		<td><form:input cssClass="form-control" path="addressLine2" name="addressLine2" id="addressLine2" maxlength="50" placeholder="Address 2" value="${account.addressLine2}"/></td>
										 		<td></td>
										 </tr>
									 	<tr>
									 		<td><form:input cssClass="form-control" path="city" name="city" id="city" maxlength="50" placeholder="City" title="Please enter yout City" value="${account.city}"/></td>						
											<td></td>
											<td>
												<select size="1" id="state" name="state" style="width: 205px;  line-height: 15px; height: 30px; margin-bottom: 5px; padding: 5px 0px 5px 5px;">
													<option value="">--Select State/Province--</option>
													<option value="ZZ">Not USA or Canada</option>
													<option value="AA">APO AA</option>
													<option value="AE">APO AE</option>
													<option value="AP">APO AP</option>
													<option value="AL">Alabama</option>
													<option value="AK">Alaska</option>
													<option value="AB">Alberta</option>
													<option value="AS">American Samoa</option>
													<option value="AZ">Arizona</option>
													<option value="AR">Arkansas</option>
													<option value="BC">British Columbia</option>
													<option value="CA" selected="">California</option>
													<option value="CO">Colorado</option>
													<option value="CT">Connecticut</option>
													<option value="DE">Delaware</option>
													<option value="DC">District of Columbia</option>
													<option value="FL">Florida</option>
													<option value="GA">Georgia</option>
													<option value="GU">Guam</option>
													<option value="HI">Hawaii</option>
													<option value="ID">Idaho</option>
													<option value="IL">Illinois</option>
													<option value="IN">Indiana</option>
													<option value="IA">Iowa</option>
													<option value="KS">Kansas</option>
													<option value="KY">Kentucky</option>
													<option value="LA">Louisiana</option>
													<option value="ME">Maine</option>
													<option value="MB">Manitoba</option>
													<option value="MD">Maryland</option>
													<option value="MA">Massachusetts</option>
													<option value="MI">Michigan</option>
													<option value="MN">Minnesota</option>
													<option value="MS">Mississippi</option>
													<option value="MO">Missouri</option>
													<option value="MT">Montana</option>
													<option value="NE">Nebraska</option>
													<option value="NV">Nevada</option>
													<option value="NB">New Brunswick</option>
													<option value="NH">New Hampshire</option>
													<option value="NJ">New Jersey</option>
													<option value="NM">New Mexico</option>
													<option value="NY">New York</option>
													<option value="NL">Newfoundland and Labrador</option>
													<option value="NC">North Carolina</option>
													<option value="ND">North Dakota</option>
													<option value="MP">Northern Mariana Islands</option>
													<option value="NT">Northwest Territories</option>
													<option value="NS">Nova Scotia</option>
													<option value="NU">Nunavut</option>
													<option value="OH">Ohio</option>
													<option value="OK">Oklahoma</option>
													<option value="ON">Ontario</option>
													<option value="OR">Oregon</option>
													<option value="PA">Pennsylvania</option>
													<option value="PE">Prince Edward Island</option>
													<option value="PR">Puerto Rico</option>
													<option value="QC">Quebec</option>
													<option value="RI">Rhode Island</option>
													<option value="SK">Saskatchewan</option>
													<option value="SC">South Carolina</option>
													<option value="SD">South Dakota</option>
													<option value="TN">Tennessee</option>
													<option value="TX" >Texas</option>
													<option value="UM">US Minor Outlying Islands</option>
													<option value="UT">Utah</option>
													<option value="VT">Vermont</option>
													<option value="VI">Virgin Islands, US</option>
													<option value="VA">Virginia</option>
													<option value="WA">Washington</option>
													<option value="WV">West Virginia</option>
													<option value="WI">Wisconsin</option>
													<option value="WY">Wyoming</option>
													<option value="YT">Yukon Territory</option>
												</select>						  
											</td>
											<td></td>
									 	</tr>
									 	<tr>
										 		<td><form:input cssClass="form-control" path="postalCode" name="postalCode" id="postalCode" maxlength="20" placeholder="Postal Code" value="${account.postalCode}"/></td>
										 		<td>&nbsp; </td>
										 		<td>&nbsp; </td>
										 		<td>&nbsp; </td>
										 </tr>
										 
									 					
									</table>
									<form:input style="display:none;" path="tcDescription" id="userTC" value="" />	
									<form:input style="display:none;" path="productId" id="productIdHidInput" value="" />
									<form:input style="display:none;" path="paymentMethodId" id="paymentMethodIdHidInput" value="" />
									<form:input style="display:none;" path="transId" id="paymentTransId" value="" />	
									<form:input style="display:none;" path="joiningFee" id="prodJoiningFeeInput" value="" />
									<form:input style="display:none;" path="productPrice" id="prodPriceInput" value="" />
									<form:input style="display:none;" path="locationId" id="locationIdInput" value="" />									
									<form:input style="display:none;" path="userLst[0].isAdult" value="true"/>
									<form:input style="display:none;" path="userLst[0].usrSequence" value="0" />
									
									<form:input style="display:none;" path="signUpProductType" id="signUpProductType" value="" />
									<form:input style="display:none;" path="membershipFrequency" id="membershipFrequencyId" value="" />
									
									<form:input style="display:none;" path="expirationMonth" id="expirationMonthHid" value="" />									
									<form:input style="display:none;" path="expirationYear" id="expirationYearHid" value="" />									
									<form:input style="display:none;" path="nickName" id="nickNameHid" value="" />	
									<form:input style="display:none;" path="loggedInUserEmailId" id="loggedInUserEmailId" value="" />
									<form:input style="display:none;" id="primaryMembershipAgeCategory" path="userLst[0].membershipAgeCategory" value="Adult"/>	
									<div style="display:none;">									
										<input 	name="finalAmount" id="finalAmountInput" value=""/>
										<input 	name="discountAmount" id="discountAmountInput" value=""/>
										<input 	name="faAmount" id="faAmountInput" value=""/>
										<input 	name="signupPrice" id="signupPriceInput" value=""/>
										<input 	name="setUpFee" id="setUpFeeInput" value=""/>
										<input 	name="registrationFee" id="registrationFeeInput" value=""/>
										<input 	name="deposit" id="depositInput" value=""/>
										<input 	name="faPercentage" id="faPercentageInput" value=""/>
										<input 	name="faStartDate" id="faStartDateInput" value=""/>
										<input 	name="faEndDate" id="faEndDateInput" value=""/>
										<input 	name="joinFeeAmt" id="joinFeeAmt" value=""/>
										<input 	name="membershipStartDate" id="membershipStartDateInput" value=""/>
										<input 	name="promotionMapInput" id="promotionMapInput" value=""/>																					
									</div>	
																	
									<%-- <form:input style="display:none;" path="signup[0].finalAmount" id="progFinalAmtIdHidInput" value="" /> --%>
								</form:form>
								</div>				
								<div id="loginDiv" style="display:none;">
									<%@ include file="../../views/customer/memberLogin.jsp" %> 
								</div>
				                     			
				        </div>
				  			<div id="step-3">
				  				<c:if test="${empty agentUid}">
					            	<div class="termsDiv">           	
					            	</div>           	         	
					            	<div>
					            		<span>
					            			<form id="tcForm" action="#" class="cmxform" style="margin-top : 10px;"> 
					            				<table border="0">
							            			<tr>
												 		<td><input class="form-control bootstrap-Inputcss" name="tcname" id="tcname" maxlength="50" placeholder="Full Name" style="width: 230px;"/> </td>
												 		<td>&nbsp; </td>							 		
													 </tr> 
													 
												 </table>             				
					            			</form>
					            		</span>
					            	</div>
					        		<input type="checkbox" name="chkTermsAndCond" style="margin-top: 10px;">&nbsp;I accept terms and conditions
					        		<div style="height:20px;"></div>        
					        	</c:if>
					        	<c:if test="${not empty agentUid}">
									<div class="agentTerms" style="margin-left : 20px;"><h4>Please ensure the user signs the Terms and Conditions.</h4></div>
									<form id="tcForm" action="#" class="cmxform" style="margin-top : 10px;">
									</form>
								</c:if>    
				        	</div>    
				        	
				        	<div id="step-4">
				        		<div id="purchase_info" style="height: 260px;">
				        		<form  id="addCardInfoForm" action="#">
									<!-- <div align="center"><span class="alertNotice" style="font-size :12px;">If you need to add a 3rd party payer, please stop and contact a Y agent to continue the sign up process</span></div><br /> -->
									<div class="addcarddiv">
										<div style="height:20px;">Please select Payment Method</div>
										<select id="paymentInfoRadio" name="paymentInfoRadio" style="width:300px;">
											<c:forEach var="paymentInfo" items="${paymentInfoLst}" varStatus="count">
												<c:choose>
												<c:when test="${ paymentInfo[4] == 'CREDIT' }">
													<option value="${ paymentInfo[4] }__${ paymentInfo[0] }__${ paymentInfo[8] }"> ${ paymentInfo[3] } ${paymentInfo[2] } ${ paymentInfo[5] }/${ paymentInfo[6] } </option>
												</c:when>
												<c:when test="${ paymentInfo[4] == 'ACH' }">
													<option value="${ paymentInfo[4] }__${ paymentInfo[0] }__${ paymentInfo[8] }"> ${ paymentInfo[3] }, ${paymentInfo[2] } </option>
												</c:when>
												</c:choose>
												<%-- 
												<c:if test="${ paymentInfo.portalStatus == 'Active' && paymentInfo.paymentType == 'Credit' }">
													<c:choose>
														<c:when test="${ paymentInfo.isPrimary == '1' }">
															<option selected value="${paymentInfo.paymentId }">${paymentInfo.nickName } ${paymentInfo.cardNumber } ${paymentInfo.expirationMonth }/${paymentInfo.expirationYear }</option>
														</c:when>
														<c:when test="${ paymentInfo.isPrimary == '0' || paymentInfo.isPrimary == null}">
															<option value="${paymentInfo.paymentId }">${paymentInfo.nickName } ${paymentInfo.cardNumber } ${paymentInfo.expirationMonth }/${paymentInfo.expirationYear }</option>
														</c:when>
													</c:choose>	
												</c:if>--%>
												
											</c:forEach>
											<option value="New">Add New Card</option>
											<option value="NewBank">Add New Bank Info</option>						
										</select>
									</div>									
									<div class="addcarddiv" style="margin-left: 42px;">
										<div style="height:20px;">Please select Payment Frequency</div>
										<select id="paymentFrequencySelect" name="paymentFrequencySelect" style="width:150px;">	
											<!-- <option value="Monthly" selected="selected">Monthly</option>
											<option value="Annual">Annual</option> -->						
										</select>
									</div>
									<div class="addcarddiv">
										<div style="height:20px;">Start Date</div>
										<input type="text" id="start" name="startDate" style="width:150px;">
									</div>
									<div class="addcarddiv" id="endDateDiv" style="display:none;">
										<div style="height:20px;">End Date</div>
										<input type="text" id="end" name="type" readonly style="width:150px;">
									</div>
									<br /><br />
										
									<div id="addcard" style="margin-top:10px; margin-bottom:10px; height:44px; display:none;   float: left; ">
										  
											
											<div style="height:50px;">
												<div class="addcarddiv">
													<div style="height:20px;">Name on card</div>
													<div><input type="text" class="k-textbox" placeholder="Full Name on Card" name="fullName" id="fullName" value="" tabIndex="1"></div>
												</div>
												<div class="addcarddiv" style="margin-right : 30px;">
													<div style="height:20px;">Card Number</div>
													<!-- <input type="text"  name="cardType" id="cardType" value="">	 -->			
						<!-- <div><input type="text" class="k-textbox" placeholder="Card Number" tabIndex="2" autocomplete="off" maxlength="16" size="16" value="" onchange="handleCCTyping(this.form, event);" onkeyup="handleCCTyping(this.form, event);" id="cardNumber" name="cardNumber" placeholder="Credit Card Number" style="width: 230px;"></div> -->
													<form id="paymentForm" action="#" > 
													<div>
														<input type="text" class="k-textbox" placeholder="Card Number" maxlength="16" name="cardNum" id="cardNum" value="" tabIndex="2" onchange="handleCCTyping(this.form, event);" onkeyup="handleCCTyping(this.form, event);">
														<img src="resources/img/payment_credit_cards/cards/invalid.png" name="cardimage" width="40"  height="22" hspace="10" vspace="0" align="top">
													</div>
													</form> 
												</div>
												
												<div class="addcarddiv">
													<div style="height:20px;">Expiration date</div>
													<div>
														<span>
															<select id="expirationMonth" name="expirationMonth" style="width:50px;">
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
															<select id="addCardExpirationYear" name="expirationYear" style="width:70px;">																
															</select>
														</span>
													</div>
												</div>
												<div class="addcarddiv">
													<div style="height:20px;">Security Code</div>
													<div>
														<input type="password" class="k-textbox" placeholder="Security Code" style="width: 90px;" name="securityCode" id="securityCode" maxlength="4" value="" tabIndex="3" title="CVV Help"> 
													</div>
												</div>
												<div class="addcarddiv">
													<div style="height:20px;">&nbsp;</div>
													<div>
														<a href="#" class="tooltip_display">
															<img src="resources/img/question-mark.png" height="25px" width="25px"></img>
														</a>
														<div id="large">
															<div class="ttip">
																<span><a class="note"
																	style="float: right; height: 25px; width: 25px;"><img
																		src="resources/img/close.jpg" height="20px"
																		width="20px"></img></a></span>
																<div class="contents">
																	<!-- <span class="profilepic"
																		style="background: url('resources/img/cv_card.gif'); width: 250px; height: 160px;">&nbsp;</span>
																	<span class="profilepic"
																		style="background: url('resources/img/cv_amex_card.gif'); width: 250px; height: 160px;">&nbsp;</span> -->
																		<span><img src="resources/img/cv_card.gif" height="130px" width="160px"></img></span>
																		<span><img src="resources/img/cv_amex_card.gif" height="130px" width="160px"></img></span>																		
																</div>
																<div id="background"></div>
															</div>
														</div> 
													</div>
												</div>
											<div style="height:50px;">
												<div class="addcarddiv">
													<div style="height:20px;">Address Line 1</div>
													<div><!-- <textarea placeholder="Billing AddressLine1" name="billingAddressLine1" id="billingAddressLine1" class="k-textbox" style="width: 279px; height: 46px;"></textarea> -->
														<input type="text" class="k-textbox" placeholder="Billing AddressLine1" name="billingAddressLine1" id="billingAddressLine1" value="" tabIndex="4">
													</div> 
												</div>
												<div class="addcarddiv">
													<div style="height:20px;">Address Line 2</div>
													<div><input type="text" class="k-textbox" placeholder="Billing AddressLine2" name="billingAddressLine2" id="billingAddressLine2" value="" tabIndex="5"></div> 
												</div>
												<div class="addcarddiv">
													<div style="height:20px;">City</div>
													<div><input type="text" class="k-textbox" placeholder="Billing City" name="billingCity" id="billingCity" value="" tabIndex="6"></div> 
												</div>
												<div class="addcarddiv" style="width:45px;">
													<div style="height:20px;">State</div>
													<div><input type="text" class="k-textbox" placeholder="" name="billingState" id="billingState" value="" style="width:45px;" tabIndex="7"></div> 
												</div>
												<div class="addcarddiv" style="width:60px">
													<div style="height:20px;">Zip</div>
													<div><input type="text" class="k-textbox" placeholder="Zip" name="billingZip" id="billingZip" value="" style="width:60px;" tabIndex="8"></div> 
												</div>
												<div class="addcarddiv" style="display:none;">
													 <span id="paymentTokenIdSpan"></span>
													 <span id="paymentAmountSpan"></span>
												</div>
												
											</div>
											
											<div style="height:50px;">
												
												<div id="savecardcheckbox" class="addcarddiv" style="width:150px">
													<div><input type="checkbox" checked="checked" disabled="disabled" name="SaveCard" id="SaveCard">&nbsp;&nbsp;Save Payment Method</div>
													
												</div>
												<div id="nickname" class="addcarddiv" >
													<div style="height:20px;">Nick Name</div>
													<div><input type="text" class="k-textbox" placeholder="Nick Name" name="nickName" id="nickName" value=""></div> 
												</div>
											</div>
										
									</div>
									<div style="float: right; width: 300px;">
									<!-- <div style="margin-right: 100px;">
										<table style="margin:0px; padding: 7px; border: 1px solid #ddd;" class="summary-table-border">							
												<tbody style="min-height: 300px;">	
													<tr>
														<td colspan="2" style="text-align: center;">
															 <h3>
															   Payment Information
															 </h3>
														</td>
														<td></td>
													</tr>							
													<tr>
														<td ><span><b>Join Fee</b></span></td>
														<td id="paymentJoinFeeTd"></td>
													</tr>
													<tr>
														<td><span><b>Price</b></span></td>
														<td id="paymentTierTd"></td>
													</tr>
													<tr style="display:none;">
														<td><span><b>Payment Frequency</b></span></td>
														<td id="paymentFrequencyTd"></td>
													</tr>
													<tr style="display:none;">
														<td><span><b>Billing cycle</b></span></td>
														<td id="paymentBillingcycleTd"></td>
													</tr>													
													<tr>
														<td><span><b>Total Price</b></span></td>
														<td ><span></span><span id="paymentTotalPriceTd"></span></td>
													</tr>														
												</tbody>
										</table>
									</div> -->
										
										</div>
									</div>
									
									<div id="addBank" style="margin-top:10px; margin-bottom:10px; height:44px; display:none;   float: left; ">										
											<div style="height:50px;">
												<!-- <div class="addcarddiv">
													<div style="height:20px;"><u><b>The check number needs to be used only registering new ACH, this check number should be voided after registration.</b></u></div>
												</div> -->
												<div class="addcarddiv">
													<div style="height:20px;">Account Number</div>
													<div><input type="text" class="k-textbox" placeholder="Account Number" name="bankAccountNumber" id="bankAccountNumber" value=""  style="width: 15.4em;" tabIndex="1"></div>
												</div>
												<div class="addcarddiv">
													<div style="height:20px;">Bank Routing Number</div>
													<div><input type="text" class="k-textbox" placeholder="Bank Routing Number" name="bankRoutingNumber" id="bankRoutingNumber" value=""  style="width: 15.4em;" tabIndex="2"></div>
												</div>
												<div class="addcarddiv">
													<div style="height:20px;">Check Number</div>
													<div><input type="text" class="k-textbox" placeholder="Check Number" name="checkNumber" id="checkNumber" value=""  style="width: 15.4em;" tabIndex="3"></div>
												</div>
												<div class="addcarddiv">
													<div style="height:20px;">Card Name</div>
													<div><input type="text" class="k-textbox" placeholder="Card Name" name="cardName" id="cardName" value=""  style="width: 15.4em;" tabIndex="4"></div>
												</div>
											</div>	
											<div style="height:50px;">
												<div class="addcarddiv">
													<div style="height:20px;">Address Line 1</div>
													<div><!-- <textarea placeholder="Billing AddressLine1" name="billingAddressLine1" id="billingAddressLine1" class="k-textbox" style="width: 279px; height: 46px;"></textarea> -->
														<input type="text" class="k-textbox" placeholder="Billing AddressLine1" name="achbillingAddressLine1" id="billingAddressLine1" style="width: 15.4em;" value="" tabIndex="5">
													</div> 
												</div>
												<div class="addcarddiv">
													<div style="height:20px;">Address Line 2</div>
													<div><input type="text" class="k-textbox" placeholder="Billing AddressLine2" name="achbillingAddressLine2" id="billingAddressLine2" style="width: 15.4em;" value="" tabIndex="6"></div> 
												</div>
												<div class="addcarddiv">
													<div style="height:20px;">City</div>
													<div><input type="text" class="k-textbox" placeholder="Billing City" name="billingCity" id="achbillingCity" style="width: 15.4em;" value="" tabIndex="7"></div> 
												</div>
												<div class="addcarddiv" style="width:45px;">
													<div style="height:20px;">State</div>
													<div><input type="text" class="k-textbox" placeholder="" name="billingState" id="achbillingState" value="" style="width:45px;" tabIndex="8"></div> 
												</div>
												<div class="addcarddiv" style="width:60px">
													<div style="height:20px;">Zip</div>
													<div><input type="text" class="k-textbox" placeholder="Zip" name="billingZip" id="achbillingZip" value="" style="width:60px;" tabIndex="9"></div> 
												</div>
												<div class="addcarddiv" style="display:none;">
													 <span id="paymentTokenIdSpan"></span>
													 <span id="paymentAmountSpan"></span>
												</div>
												
											</div>
											
											<div style="height:50px;">
												
												<div id="savecardcheckbox" class="addcarddiv" style="width:150px">
													<div><input type="checkbox" checked="checked" disabled="disabled" name="SaveCard" id="SaveCard">&nbsp;&nbsp;Save Payment Method</div>
													
												</div>
												<div id="nickname" class="addcarddiv" >
													<div style="height:20px;">Nick Name</div>
													<div><input type="text" class="k-textbox" placeholder="Nick Name" name="nickName" id="nickName" value=""></div> 
												</div>
											</div>										
									</div><br />
							</form>
									<div id="statusBlock-payment" style="display: none; margin-top: 85px;">
										<span class="k-block k-success-colored"	id="tcloginSuccessSpan-payment" style="display: none"></span>
										<span class="k-block k-error-colored" id="tcloginErrorSpan-payment" style="display: none"></span>
									</div>
									<div id="statusBlock-payment" style="margin-top: 85px;">
										<span class="k-block k-success-colored" id="tcPaymentSuccessSpan"></span>
										<span class="k-block k-error-colored" id="tcPaymentErrorSpan"></span>
									</div>
											
											
										<!--        	
				            	<div class="bootstrap-frm" style="max-width : auto;">
									<h2 style="color: #888; text-align: center;">Summary</h2>					
										<table width="100%">							
											<tbody id="confirmationDataBody" style="min-height: 300px;">
											
												<tr>
													<td width="50%">
												 		<h2>
												   			<span>PRODUCT INFO</span>
												 		</h2>
												 	</td>
													<td ></td>
												</tr>
											
												<tr>
													<td width="50%"><span><b>Product Id</b></span></td>
													<td id="productIdTd"></td>
												</tr>
												<tr>
													<td><span><b>Product Name</b></span></td>
													<td id="productNameTd"></td>
												</tr>								
												<tr>
													<td><span><b>Product Description</b></span></td>
													<td id="productDescriptionTd"></td>
												</tr>
												<tr>
													<td><span><b>Product Type</b></span></td>
													<td id="productTypeTd"></td>
												</tr>
												<tr>
													<td><span><b>Product Duration</b></span></td>
													<td id="productDurationTd"></td>
												</tr>
												<tr>
													<td><span><b>Product Price</b></span></td>
													<td id="productPriceTd"></td>
												</tr>	
											
											<tr>
													<td width="50%">
									 <h2>
									   <span>USER INFO</span>
									 </h2></td>
													<td ></td>
												</tr>
											
												<tr>
													<td width="50%"><span><b>First Name</b></span></td>
													<td id="firstNameTd"></td>
												</tr>
												<tr>
													<td><span><b>Last Name</b></span></td>
													<td id="lastNameTd"></td>
												</tr>								
												<tr>
													<td><span><b>Date of Birth</b></span></td>
													<td id="dobTd"></td>
												</tr>
												<tr>
													<td><span><b>Phone Number</b></span></td>
													<td id="phoneNumberTd"></td>
												</tr>
												<tr>
													<td><span><b>Email</b></span></td>
													<td id="emailTd"></td>
												</tr>	
																	
												<tr>
													<td><h2>
									   <span>ADDRESS</span>
									 </h2> </td>
													<td ></td>
												</tr>
												
												
												<tr>
													<td><span><b>Address Line 1</b></span></td>
													<td id="addressLine1Td"></td>
												</tr>
												<tr>
													<td><span><b>Address Line 2</b></span></td>
													<td id="addressLine2Td"></td>
												</tr>
												<tr>
													<td><span><b>City</b></span></td>
													<td id="cityTd"></td>
												</tr>
												<tr>
													<td><span><b>State</b></span></td>
													<td id="stateTd"></td>
												</tr>
												<tr>
													<td><span><b>Postal Code</b></span></td>
													<td id="postalCodeTd"></td>
												</tr>
												<tr>
													<td></td>
													<td ><span id="" style="float:right; margin-top:10px;"><a href="/ymca-web/login">Go to Login Page</a></span></td>
												</tr>
												
											</tbody>
										</table> 
									</div>	 -->	
								         
				        	</div> 
				        	</div>
				        	
				        	<div id="step-5">
				            	 <div id="contentFormDiv" >
				            	<div class="border-bottom-h3" style="margin-bottom : 0px;" align="center">
				        		<h2 style="color: #888; text-align: center; margin-top: 5px; margin-bottom: 5px;">Summary</h2>	
				        		<br />
				        		<!-- <span class="alertNotice">If you need to add a 3rd party payer, please stop and contact a Y agent to continue the sign up process</span> -->
								<br>
				        	</div>
				        	<!-- <div style="max-width : auto;" class="memberSummaryPage">
									<table style="" border="0" width="100%">							
												<tbody >	
													<tr>
														<td width="50%">
															<table style="/* float: left; margin-left: 10px; */"
																class="summary-table-border">
																<tbody style="min-height: 300px;">
																	<tr>
																		<td colspan="2" style="border-bottom: 1px solid #ddd;">
																			<h3>USER INFORMATION</h3>
																		</td>
																		<td></td>
																	</tr>

																	<tr>
																		<td><span><b>First Name</b></span></td>
																		<td id="firstNameTd"></td>
																	</tr>
																	<tr>
																		<td><span><b>Last Name</b></span></td>
																		<td id="lastNameTd"></td>
																	</tr>
																	<tr>
																		<td><span><b>Date of Birth</b></span></td>
																		<td id="dobTd"></td>
																	</tr>
																	<tr>
																		<td><span><b>Phone Number</b></span></td>
																		<td id="phoneNumberTd"></td>
																	</tr>
																	<tr>
																		<td><span><b>Email</b></span></td>
																		<td id="emailTd"></td>
																	</tr>
																</tbody>
															</table>

														</td>
														<td width="50%">
															<table style="/* float: left; margin-left: 10px; */"
																class="summary-table-border">
																<tbody style="min-height: 300px;">
																	<tr>
																		<td colspan="2" style="border-bottom: 1px solid #ddd;"><h3>
																				ADDRESS INFORMATION</h3></td>
																		<td></td>
																	</tr>


																	<tr>
																		<td><span><b>Address Line 1</b></span></td>
																		<td id="addressLine1Td"></td>
																	</tr>
																	<tr>
																		<td><span><b>Address Line 2</b></span></td>
																		<td id="addressLine2Td"></td>
																	</tr>
																	<tr>
																		<td><span><b>City</b></span></td>
																		<td id="cityTd"></td>
																	</tr>
																	<tr>
																		<td><span><b>State</b></span></td>
																		<td id="stateTd"></td>
																	</tr>
																	<tr>
																		<td><span><b>Postal Code</b></span></td>
																		<td id="postalCodeTd"></td>
																	</tr>

																</tbody>
															</table>
														</td>
													</tr>							
													<tr>
														<td width="50%">
															<table style="/* float: left; margin-left: 5px; */"
																class="summary-table-border">
																<tbody style="min-height: 300px;">
																	<tr>
																		<td colspan="2" style="border-bottom: 1px solid #ddd;">
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
																		<td id="productNameTd"></td>
																	</tr>
																	<tr>
																		<td><span><b>Product Description</b></span></td>
																		<td id="productDescriptionTd"></td>
																	</tr>
																	<tr>
																		<td><span><b>Product Type</b></span></td>
																		<td id="productTypeTd"></td>
																	</tr>
																	<tr style="display:none">
																		<td><span><b>Product Duration</b></span></td>
																		<td id="productDurationTd"></td>
																	</tr>
																	<tr>
																		<td><span><b>Product Price</b></span></td>
																		<td ><span><b>$</b></span><b><span id="productPriceTd"></span></b></td>
																	</tr>
																</tbody>
															</table>

														</td>
														<td width="50%">
															<table style="/* float: left; margin-left: 5px; */"
																class="summary-table-border">
																<tbody style="min-height: 300px;">
																	<tr>
																		<td colspan="2" style="border-bottom: 1px solid #ddd;">
																			<h3>PAYMENT INFORMATION</h3>
																		</td>
																		<td></td>
																	</tr>
																						<tr>
																		<td ><span><b>Product Id</b></span></td>
																		<td id="productIdTd"></td>
																	</tr>
																	<tr>
																		<td><span><b>Join Fee</b></span></td>
																		<td ><span>$</span><span id="sumJoinFeeTd"></span></td>
																	</tr>
																	<tr>
																		<td><span><b>Price</b></span></td>
																		<td ><span>$</span><span id="sumTierPriceTd"></span></td>
																	</tr>
																	<tr>
																		<td><span><b>Payment Frequency</b></span></td>
																		<td id="sumPaymentFreqTd"></td>
																	</tr>
																	<tr>
																		<td><span><b>Total Price</b></span></td>
																		<td id="sumTotalPriceTd"></td>
																		<td><span>$</span><span id="sumTotalPriceTd"></span></td>
																	</tr>
																	<tr style="display:none;" id="sumAutoApplyTr">
																		<td><span><b>EARLYBIRD</b></span></td>
																		<td ><span style="color: red;">-$</span><span id="sumEarlyBirdDiscount" style="color: red;"></span></td>
																	</tr>
																	<tr style="display:none;" id="sumPromoCodeTr">
																		<td valign="bottom"><b><span id="sumPromoCodeName"></span></b></td>
																		<td>
																			<span style="color: red;">-$</span>
																			<span id="sumPromoCodeVal" style="color: red;"></span>
																			
																			<span class="cross_img" id="removePromoCodeBtn" style="margin-left: 15px;">
																				<img width="40" src="resources/img/tick_cross.jpg">
																			</span>
																		</td>
																	</tr>	
																	<tr>
																		<td><span><b>Final Amount</b></span></td>
																		<td id="sumTotalPriceTd"></td>
																		<td><span>$</span><span id="sumFinalAmountTd"></span></td>
																	</tr>																
																	<tr>
																		<td colspan="2">
																			<span>
																				<b><span>Promo Code:</span></b>
																				&nbsp;&nbsp;
																				<span><input id="c_promocode" type="text" class="k-textbox"></span>
																				<span id="applypromo" class="k-button" style="text-shadow: none;" >Apply</span>
																			</span>
																		</td>																		
																		<td>&nbsp;</td>
																	</tr>
																	<tr>
																		<td colspan="2">
																			<div id="statusBlock">
																				<span class="k-block k-success-colored" id="tcSuccessSpan"></span>
																				<span class="k-block k-error-colored" id="tcErrorSpan"></span>
																			</div>
																		</td>																		
																		<td>&nbsp;</td>
																	</tr>
																</tbody>
															</table>

														</td>
													</tr>
																										
												</tbody>
										</table>
										
							</div> -->
							<div style="max-width : auto;" class="memberSummaryPage">
								
								<table style="" border="0" width="100%">							
										
										<tr>
											<td>
												<table style="" border="0" width="100%">							
															<tbody >	
																<tr>
																	<td width="100%">
																		<table width="100%" style=""	class="summary-table-border">
																			<tbody style="">
																				<tr>
																					<td colspan="3" style="border-bottom: 1px solid #ddd;">
																						<h3>MEMBER INFORMATION</h3>
																					</td>
																					
																				</tr>
																			</tbody>
																		</table>
																		<table style="" class="summary-table-border" id="firstUserSummary">
																			<tbody style="min-height: 300px;">																			
																				<tr>
																					<td colspan="2" style="">
																						<span style="font-size: 13px;"><b><h4><u>PRIMARY MEMBER<u></u></h4></b></span>
																					</td>
																					<td></td>
																				</tr>
								
																				<tr>
																					<td><span><b>First Name</b></span></td>
																					<td id="firstNameTd"></td>
																				</tr>
																				<tr>
																					<td><span><b>Last Name</b></span></td>
																					<td id="lastNameTd"></td>
																				</tr>
																				<tr>
																					<td><span><b>Date of Birth</b></span></td>
																					<td id="dobTd"></td>
																				</tr>
																				<tr>
																					<td><span><b>Phone Number</b></span></td>
																					<td id="phoneNumberTd"></td>
																				</tr>
																				<tr>
																					<td><span><b>Email</b></span></td>
																					<td id="emailTd"></td>
																				</tr>
																				<tr>
																					<td><span><b>Gender</b></span></td>
																					<td id="genderTd"></td>
																				</tr>
																			</tbody>
																		</table>
								
																	</td>
																	
																</tr>
																<tr id="secMemberTr" style="display : none;">
																	<td width="100%">
																		<table width="100%" style="/* float: left; margin-left: 10px; */"
																			class="summary-table-border">
																			<tbody style="min-height: 300px;">
																				<tr>
																					<td colspan="2" style="">
																						<span style="font-size: 13px;"><b><h4><u><span id="secondUserSummHeader">SECONDARY MEMBER</span><u></u></h4></b></span>
																					</td>
																					<td></td>
																				</tr>
								
																				<tr>
																					<td><span><b>First Name</b></span></td>
																					<td id="secMemFirstNameTd"></td>
																				</tr>
																				<tr>
																					<td><span><b>Last Name</b></span></td>
																					<td id="secMemLastNameTd"></td>
																				</tr>
																				<tr>
																					<td><span><b>Date of Birth</b></span></td>
																					<td id="secMemDobTd"></td>
																				</tr>
																				<tr>
																					<td><span><b>Phone Number</b></span></td>
																					<td id="secMemPhoneNumberTd"></td>
																				</tr>
																				<tr>
																					<td><span><b>Email</b></span></td>
																					<td id="secMemEmailTd"></td>
																				</tr>
																				<tr>
																					<td><span><b>Gender</b></span></td>
																					<td id="secGenderTd"></td>
																				</tr>
																			</tbody>
																		</table>
								
																	</td>
																</tr>	
																<tr id="thirdMemberTr" width="100%" style="display : none;">
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
																					<td id="thirdMemFirstNameTd"></td>
																				</tr>
																				<tr>
																					<td><span><b>Last Name</b></span></td>
																					<td id="thirdMemLastNameTd"></td>
																				</tr>
																				<tr>
																					<td><span><b>Date of Birth</b></span></td>
																					<td id="thirdMemDobTd"></td>
																				</tr>
																				<tr>
																					<td><span><b>Phone Number</b></span></td>
																					<td id="thirdMemPhoneNumberTd"></td>
																				</tr>
																				<tr>
																					<td><span><b>Email</b></span></td>
																					<td id="thirdMemEmailTd"></td>
																				</tr>
																				<tr>
																					<td><span><b>Gender</b></span></td>
																					<td id="thirdGenderTd"></td>
																				</tr>
																			</tbody>
																		</table>
								
																	</td>
																</tr>
																<tr>
																	<td>
																		<table id="kidsInformationTable" width="100%" style="display : none; margin-left: 5px;">	
																			<thead>
																				<tr>
																					<td colspan="2" style="border-bottom: 1px solid #ddd; color: #888;">
																						<h3>KIDS INFORMATION</h3>
																					</td>
																					<td></td>
																				</tr>	
																			</thead>
																			<tbody id="kidsInformationTableTbody">
																			
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
																					<td colspan="2" style="border-bottom: 1px solid #ddd;">
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
																					<td id="productNameTd"></td>
																				</tr>
																				<tr>
																					<td><span><b>Product Description</b></span></td>
																					<td id="productDescriptionTd"></td>
																				</tr>
																				<tr>
																					<td><span><b>Product Type</b></span></td>
																					<td id="productTypeTd"></td>
																				</tr>
																				<tr style="display:none">
																					<td><span><b>Product Duration</b></span></td>
																					<td id="productDurationTd"></td>
																				</tr>
																				<tr>
																					<td><span><b>Product Price</b></span></td>
																					<td ><span><b>$</b></span><b><span id="productPriceTd"></span></b></td>
																				</tr>
																			</tbody>
																		</table>
								
																	</td>
																</tr>
																													
															</tbody>
													</table>
													
											</td>	
											<td>
												<table style="" border="0" width="100%">							
															<tbody >	
																<tr>
																	<td width="100%">
																		<table style="/* float: left; margin-left: 10px; */"
																			class="summary-table-border">
																			<tbody style="min-height: 300px;">
																				<tr>
																					<td colspan="2" style="border-bottom: 1px solid #ddd;"><h3>
																							ADDRESS INFORMATION</h3></td>
																					<td></td>
																				</tr>
								
								
																				<tr>
																					<td><span><b>Address Line 1</b></span></td>
																					<td id="addressLine1Td"></td>
																				</tr>
																				<tr>
																					<td><span><b>Address Line 2</b></span></td>
																					<td id="addressLine2Td"></td>
																				</tr>
																				<tr>
																					<td><span><b>City</b></span></td>
																					<td id="cityTd"></td>
																				</tr>
																				<tr>
																					<td><span><b>State</b></span></td>
																					<td id="stateTd"></td>
																				</tr>
																				<tr>
																					<td><span><b>Postal Code</b></span></td>
																					<td id="postalCodeTd"></td>
																				</tr>
								
																			</tbody>
																		</table>
																	</td>
																</tr>
																<tr>
																	<td width="100%">
																		<table style="/* float: left; margin-left: 5px; */"
																			class="summary-table-border" width="80%">
																				<tr>
																					<td colspan="2" style="border-bottom: 1px solid #ddd;">
																						<h3>PAYMENT INFORMATION</h3>
																					</td>
																				</tr>
																				<tr>
																					<td><span><b>Payment Frequency</b></span></td>
																					<td align="left" id="sumPaymentFreqTd"></td>
																				</tr>
																				<tr id="paymentBillingcycleTr">
																					<td><span><b>Billing cycle</b></span></td>
																					<td align="left" id="paymentBillingcycleTd"></td>
																				</tr> 
																				<tr style="display:none;" id="sumJoinFeeTR">
																					<td><span><b>Join Fee</b></span></td>
																					<td align="right"><span>$</span><span id="sumJoinFeeTd"></span></td>
																				</tr>
																				<tr style="display:none;" id="JoinFeePromoDiscount" >
																					<td colspan="2">
																						<table id="JoinFeePromoDiscountTable" style="width: 100%;">
																						
																						</table>
																					</td>
																				</tr>
																				<tr>
																					<td width="60%"><span><b>Signup Price</b></span></td>
																					<td width="40%" align="right"><span>$</span><span id="sumTierPriceTd"></span><span style="margin-left : 5px;">will be charged starting next billing cycle</span></td>
																				</tr>
																				<tr style="display:none;" id="SignUpPromoDiscount" >
																					<td colspan="2">
																						<table id="SignUpPromoDiscountTable" style="width: 100%;">
																						
																						</table>
																					</td>
																				</tr>
																				<tr style="display:none;" id="sumRegistrationFeeTR">
																					<td><span><b>Registration Fee</b></span></td>
																					<td align="right"><span>$</span><span id="sumRegistrationFee"></span></td>
																				</tr>
																				<tr style="display:none;" id="RegFeePromoDiscount" >
																					<td colspan="2">
																						<table id="RegFeePromoDiscountTable" style="width: 100%;">
																						
																						</table>
																					</td>
																				</tr>
																				<tr style="display:none;" id="sumDepositAmountTR">
																					<td><span><b>Deposit Amount</b></span></td>
																					<td align="right"><span>$</span><span id="sumDepositAmount"></span></td>
																				</tr>
																				<tr style="display:none;" id="DepositPromoDiscount" >
																					<td colspan="2">
																						<table id="DepositPromoDiscountTable" style="width: 100%;">
																						
																						</table>
																					</td>
																				</tr>
																				<tr>
																					<td><span><b>Total Price</b></span></td>
																					<td align="right"><span>$</span><span id="sumTotalPriceTd"></span><input type="hidden" id="totalDiscountHiddenInput"></td>
																				</tr>
																				<tr style="display:none;" id="sumAutoApplyTr">
																					<td><span><b>EARLYBIRD</b></span></td>
																					<td align="right"><span style="color: red;">-$</span><span id="sumEarlyBirdDiscount" style="color: red;"></span></td>
																				</tr>
																				<tr id="faAmountTr">
																					<td><span><b>FA Amount</b></span></td>
																					<td align="right"><span style="color: red;">-$</span><span id="faAmountTD" style="color: red;">0</span></td>
																				</tr>
																				
																				<tr style="display:none;" id="sumPromoCodeTr">
																					<td valign="bottom"><b><span id="sumPromoCodeName"></span></b></td>
																					<td align="right">
																						<span style="color: red;">-$</span>
																						<span id="sumPromoCodeVal" style="color: red;"></span>
																						
																						<span class="cross_img" id="removePromoCodeBtn" style="margin-left: 15px;">
																							<img width="40" src="resources/img/tick_cross.jpg">
																						</span>
																					</td>
																				</tr>
																				<tr style="display:none;" id="sumPromoCodeTr">
																					<td valign="bottom"><b><span id="sumPromoCodeName"></span></b></td>
																					<td align="right">
																						<span style="color: red; vertical-align: top;">-$</span>
																						<span id="sumPromoCodeVal" style="color: red; vertical-align: top;"></span>
																						
																						<span class="" id="removePromoCodeBtn" style="margin-left: 15px; ">
																							<img width="20" src="resources/img/close.png" style="opacity: 0.72; margin-top: -6px;">
																						</span>
																					</td>
																				</tr>
																				<tr>
																					<td><span><b>Final Amount</b></span></td>
																					<!-- <td id="sumTotalPriceTd"></td> -->
																					<td align="right"><span>$</span><span id="sumFinalAmountTd"></span></td>
																				</tr>																
																				<tr>
																					<td colspan="2">
																						<span>
																							<p class='auto_promo'>
																							<b><span>Promo Code:</span></b>
																							&nbsp;&nbsp;
																							<span class='table-price'><span><input id="c_promocode" type="text" class="k-textbox" style="width: 180px"><input id="c_promocode_old" type="hidden" ></span>
																							<!-- <span  class="k-button" style="text-shadow: none;" >Apply</span> -->
																							<span class='tick_img' id="applypromo" ><img width='22px' height="27px" src='resources/img/tick_only.jpg' /></span>
																							<span class='tick_img' id="hidepromo" style="top: 14px;" ><img width='22px' height="28px" src='resources/img/cross_only.jpg' /></span></span>
																							</p>
																						</span>
																					</td>																		
																					<td>&nbsp;</td>
																				</tr>
																				 <tr>
																					<td colspan="2">
																						<div id="statusBlock">
																							<span class="k-block k-success-colored" id="tcSuccessSpan"></span>
																							<span class="k-block k-error-colored" id="tcErrorSpan"></span>
																						</div>
																					</td>																		
																					<td>&nbsp;</td>
																				</tr>
																		</table>
								
																	</td>
																</tr>
																<tr>
																	<td width="100%">
																		<table id="kidsInformationTableOdd" width="100%" style="display : none; margin-left: 5px; color: #888;">	
																			<thead>
																				<tr>
																					<td colspan="2" style="border-bottom: 1px solid #ddd; color: #888;">
																						<h3>KIDS INFORMATION</h3>
																					</td>
																					<td></td>
																				</tr>	
																			</thead>
																			<tbody id="kidsInformationTableTbodyOdd">
																			
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
				           <div class="bootstrap-frm" id="paymentSuccessDiv" style="display:none">
							<h2><span>Payment Process Completed Successfully.</span></h2>
						</div>
						<div class="bootstrap-frm" id="paymentFailuresDiv" style="display:none">
							<h2><span>Payment process Failed. Please try after sometime. </span></h2>
						</div> 	
				            	                 
				        	</div>                   
				  			
				  		</div>
				  		
				<!-- End SmartWizard Content -->  		
				 		
				</td></tr>
				</table>
			
			</div>
		</div>
	</div>

<div id="selectMemberDiv" style="display:none;">
    
</div>  
	
<script id="product-select-ErrorBox" type="text/x-kendo-template">
    <p class="error-message-p">Please select the Product before Proceeding</p>
	
    <div class="confirmbutton" align="center"><button class="confirm-product-select k-button" style=" width: 35%;">Ok</button>   
</script>

<script id="terms-conditions-ErrorBox" type="text/x-kendo-template">
    <p class="error-message-p">Please agree the terms and conditions before Proceding</p>
	
    <div class="confirmbutton" align="center"><button class="confirm-terms-conditions k-button" style=" width: 35%;">Ok</button>   
</script>

<script id="terms-conditions-LocationBox" type="text/x-kendo-template">
    <p class="error-message-p">Please select the YMCA Location.</p>
	
    <div class="confirmbutton" align="center"><button class="confirm-location-select k-button" style=" width: 35%;">Ok</button>   
</script>

<script id="terms-conditions-PricingBox" type="text/x-kendo-template">
    <p class="error-message-p">Please select the pricing option and click register.</p>
	
    <div class="confirmbutton" align="center"><button class="confirm-pricing-select k-button" style=" width: 35%;">Ok</button>   
</script>
<div id="memberSelectDiv" style="display : none;">
<p class="error-message-p">You can not select the <span id="memberSelectCount"></span> number of Adults for the selected Membership.</p>
	
<div class="confirmbutton" align="center"><button class="confirm-member-select-Error k-button" style=" width: 35%;">Ok</button></div>
</div>

<script id="member-select-ErrorBox" type="text/x-kendo-template">
       
</script>
<script id="member-kid-select-ErrorBox" type="text/x-kendo-template">
	<p class="error-message-p">You can not select Kid(s) for the selected Membership.</p>
	
    <div class="confirmbutton" align="center"><button class="confirm-kid-select-ok k-button" style=" width: 35%;">Ok</button>
</script>

<script id="member-youth-select-ErrorBox" type="text/x-kendo-template">
	<p class="error-message-p">You can not select Youth(s) for the selected Membership.</p>
	
    <div class="confirmbutton" align="center"><button class="confirm-youth-select-ok k-button" style=" width: 35%;">Ok</button>
</script>

<script id="contact-select-duplicate-ErrorBox" type="text/x-kendo-template">
	<p class="error-message-p">The contact has already selected. Please other contact.</p>
	
    <div class="confirmbutton" align="center"><button class="contact-select-duplicate-ErrorBox-ok k-button" style=" width: 35%;">Ok</button>
</script>
<script id="date-ErrorBox" type="text/x-kendo-template">     
	<p class="error-message-p">Please select the correct date from datepicker</p>
	
    <div class="confirmbutton" align="center"><button class="confirm-DateCls k-button" style=" width: 35%;">Ok</button>  
</script>
<%-- <c:if test="${ primaryUser.firstName != null}">	 
 					<tr> 
						<td><input type="checkbox" name="selectMemberCheckBox" class="selectMemberCheckBox"></td> 
 						<td class="userFNameTd">${primaryUser.firstName } </td> 
 						<td class="userLNameTd">${primaryUser.lastName }</td> 
 						<td class="userGenderTd">${primaryUser.gender }</td>
						<td style="display :none;" class="userBdayTd"><fmt:formatDate type="date" pattern="MM/dd/yyyy" value="${primaryUser.dateOfBirth }" /></td> 
 						<td style="display :none;" class="userPhoneNoTd">${primaryUser.formattedPhoneNumber }</td> 
 						<td style="display :none;" class="userEmailAddTd">${primaryUser.emailAddress }</td> 
 					</tr> 
					</c:if>	
					<c:if test="${ secUser.firstName != null}">	 
 					<tr> 
						<td><input type="checkbox" name="selectMemberCheckBox" class="selectMemberCheckBox"></td> 
 						<td class="userFNameTd">${secUser.firstName } </td> 
 						<td class="userLNameTd">${secUser.lastName }</td> 
 						<td class="userGenderTd">${secUser.gender }</td>
						<td style="display :none;" class="userBdayTd"><fmt:formatDate type="date" pattern="MM/dd/yyyy" value="${secUser.dateOfBirth }" /></td> 
 						<td style="display :none;" class="userPhoneNoTd">${secUser.formattedPhoneNumber }</td> 
 						<td style="display :none;" class="userEmailAddTd">${secUser.emailAddress }</td> 
 					</tr> 
					</c:if>	
					<c:if test="${ thirdUser.firstName != null}">	
					<tr> 
						<td><input type="checkbox" name="selectThirdMemberCheckBox" class="selectThirdMemberCheckBox"></td> 
 						<td class="userFNameTd">${thirdUser.firstName } </td> 
 						<td class="userLNameTd">${thirdUser.lastName }</td> 
 						<td class="userGenderTd">${thirdUser.gender }</td>
						<td style="display :none;" class="userBdayTd"><fmt:formatDate type="date" pattern="MM/dd/yyyy" value="${thirdUser.dateOfBirth }" /></td> 
 						<td style="display :none;" class="userPhoneNoTd">${thirdUser.formattedPhoneNumber }</td> 
 						<td style="display :none;" class="userEmailAddTd">${thirdUser.emailAddress }</td> 
 					</tr>
					</c:if>	 --%>


<script id="select-family-member-Box" type="text/x-kendo-template">
    <p class="add-family-member-p">
		<div>
			<div>
				Please select the family member(s).
			</div><br />
			<div>
				 <table cellpadding="5px" border="1" id="selectFamilyMemberTable" style="border-collapse: collapse;"> 
					<tr> 
						<th>&nbsp;</th>
 						<th>First Name</th> 
 						<th>Last Name</th> 
 						<th>Gender</th> 
						<th style="display :none;">&nbsp;</th> 
 						<th style="display :none;">&nbsp;</th> 
						<th style="display :none;">&nbsp;</th> 
 					</tr>
					<c:forEach var="adultUser" items="${userLstInfo}" varStatus="count">
						<c:if test="${ adultUser != null}">
						  <tr> 
							<td><input type="checkbox" name="selectMemberCheckBox" class="selectMemberCheckBox"></td> 
 							<td class="userFNameTd">${adultUser.firstName }</td> 
 							<td class="userLNameTd">${adultUser.lastName }</td> 
 							<td class="userGenderTd">${adultUser.gender }</td>
							<td style="display :none;" class="userBdayTd"><fmt:formatDate type="date" pattern="MM/dd/yyyy" value="${adultUser.dateOfBirth }" /></td> 
 							<td style="display :none;" class="userPhoneNoTd">${adultUser.formattedPhoneNumber }</td> 
 							<td style="display :none;" class="userEmailAddTd">${adultUser.emailAddress }</td>
						  </tr>  
						</c:if>
					</c:forEach>
								
 				</table> 
			</div><br />
			<c:if test="${fn:length(youthUserLstInfo) gt 0}">
				<div id="youthInfoDivision">
					<div id="youthSelectKendoBox">
						Please select the Youth Member Information.
					</div><br />
				<div>
		 		<table cellpadding="5px" border="1" id="selectYouthInfoTable" style="border-collapse: collapse;"> 
					<tr> 
						<th>&nbsp;</th>
						<th>First Name</th> 
						<th>Last Name</th> 
						<th>Gender</th> 
						<th style="display :none;" >&nbsp;</th>
					</tr> 
				<c:forEach var="youth" items="${youthUserLstInfo}" varStatus="count">
					<c:if test="${ youth != null}">				
						<tr> 
							<td><input type="checkbox" class="selectYouthCheckBox"></td> 
							<td class="youthFirstName">${youth.firstName }</td> 
							<td class="youthLastName">${youth.lastName }</td> 
							<td class="youthGender">${youth.gender }</td> 
							<td style="display :none;" class="youthDob"><fmt:formatDate type="date" pattern="MM/dd/yyyy" value="${youth.dateOfBirth }" /></td>  
							<td style="display :none;" class="youthPhoneNo">${youth.formattedPhoneNumber }</td> 
							<td style="display :none;" class="youthEmailAdd">${youth.emailAddress }</td>
						
						</tr>	
					</c:if>
				</c:forEach>			
			</table> 
		</div>
		</div><br />
			</c:if>
			<c:if test="${fn:length(kidsInfo) gt 0}">
			<div id="kidsInfoDivision">
			<div id="kidsSelectKendoBox">
				Please select the kid(s) Information.
			</div><br />
			<div>
				 <table cellpadding="5px" border="1" id="selectKidsInfoTable" style="border-collapse: collapse;"> 
					<tr> 
						<th>&nbsp;</th>
 						<th>First Name</th> 
 						<th>Last Name</th> 
 						<th>Gender</th> 
						<th style="display :none;" >&nbsp;</th>
 					</tr> 
					<c:forEach var="kid" items="${kidsInfo}" varStatus="count">
						<c:if test="${ kid != null}">				
 							<tr> 
								<td><input type="checkbox" class="selectChildCheckBox"></td> 
 								<td class="kidFirstName">${kid.firstName }</td> 
 								<td class="kidLastName">${kid.lastName }</td> 
 								<td class="kidGender">${kid.gender }</td> 
								<td style="display :none;" class="kidDob"><fmt:formatDate type="date" pattern="MM/dd/yyyy" value="${kid.dateOfBirth }" /></td> 
 							</tr>	
						</c:if>
					</c:forEach>			
 				</table> 
			</div>
			</div>
			</c:if>	
		</div>
	</p>
	
    <div class="confirmbutton" align="center"><button class="confirm-family-member-select k-button" style=" width: 35%;">Ok</button>   
</script>

<script type="text/javascript">
$(document).ready(function(){


console.log("  memberSignupWizard onload ");


var urlPromoItemDetailId = '${urlPromoItemDetailId}';
var urlPromoContactId = '${urlPromoContactId}';
var urlPromoCode = '${urlPromoCode}';

console.log(" urlPromoItemDetailId  : "+urlPromoItemDetailId);
console.log(" urlPromoContactId : "+urlPromoContactId);
console.log(" urlPromoCode : "+urlPromoCode);

$("#urlPromoItemDetailId").val(urlPromoItemDetailId);
$("#urlPromoContactId").val(urlPromoContactId);
$("#urlPromoCode").val(urlPromoCode);



//proceedToRegister();



var eventMethod = window.addEventListener ? "addEventListener" : "attachEvent";
var eventer = window[eventMethod];
var messageEvent = eventMethod == "attachEvent" ? "onmessage" : "message";
eventer(messageEvent,function(e) {
	var key = e.message ? "message" : "data";
	var data = e[key];
	//$("#contentFormDiv").css("display", "none");			
	if(data.view.toString() == "Success"){
		
		var joinFee = $('#sumJoinFeeTd').text();
		  var sumTierPriceTd = $('#sumTierPriceTd').text();
		  
		  $("#paymentTransId").attr("value", paymentOrderId); 
	      if(joinFee != null){
	    		$("#prodJoiningFeeInput").attr("value", joinFee);
	      }else{
	    		$("#prodJoiningFeeInput").attr("value", "0");  
	      }
		    	  
	      if(sumTierPriceTd != null){
	    		$("#prodPriceInput").attr("value", sumTierPriceTd);    
	      }else{
	    		$("#prodPriceInput").attr("value", "0");     
	      }
	
		//$("#paymentSuccessDiv").css("display", "block");	
		/* var joinFee = $('#sumJoinFeeTd').text();
		  var sumTierPriceTd = $('#sumTierPriceTd').text();
		  
		  
		  var joinFeeAmount = 0;
		    if(joinFee != null){
		    	var amountArr = joinFee.split(" ");
		    	joinFeeAmount = amountArr[1];
		    	if(joinFeeAmount){
		    		$("#prodJoiningFeeInput").attr("value", joinFeeAmount);
		    	}else{
		    		$("#prodJoiningFeeInput").attr("value", "0");  
		    	}
		    	  
		    	
		    }
		    var sumTierPriceTdAmount = 0;
		    if(sumTierPriceTd != null){
		    	var amountArr = sumTierPriceTd.split(" ");
		    	sumTierPriceTdAmount = amountArr[1];
		    	if(sumTierPriceTdAmount){
		    		$("#prodPriceInput").attr("value", sumTierPriceTdAmount);    
		    	}else{
		    		$("#prodPriceInput").attr("value", "0");     
		    	}
		    	
		    } */
		    $("#statusBlock-payment").css("display", "none");	
		    $("#tcloginErrorSpan-payment").css("display", "none");	
		    $( "#tcloginErrorSpan-payment" ).html("");
		document.forms["becomeMemberForm"].submit();
		$('#wizard').smartWizard('setError',{stepnum:4,iserror:false});
		$(".k-loading-mask").hide();
	}
	if(data.view.toString() == "Failure"){
		$('#childIframeId').attr("src", "<%=contextPath %>/viewPaymentForm");
		//document.getElementById("childIframeId").contentDocument.location.reload(true);
		$('#wizard').smartWizard('goToStep',4);
		$('#wizard').smartWizard('setError',{stepnum:4,iserror:true});
		  /* var errorMsg = '';
		  errorMsg += "Error occurred while processing Payment.<br />";			  					  
		  $("p.payment-Error-Box-msg-p").html(errorMsg);						  
		  var kendoWindow = $("<div />").kendoWindow({
	        	title: "Error",
	        	resizable: false,
	        	modal: true,
	        	width:400
	        });		
  			kendoWindow.data("kendoWindow")
	         .content($("#payment-Error-Box").html())
	         .center().open();	  			
	        kendoWindow
	        .find(".confirm-payment-process")
	        .click(function() {
	        	if ($(this).hasClass("confirm-payment-process")) {  
	        		$("#payment-Error-Box").css("display", "none");
	        		kendoWindow.data("kendoWindow").close();
	        	}
	        })
	        .end(); */
	        $("#statusBlock-payment").css("display", "block");	
	        $("#tcloginErrorSpan-payment").css("display", "block");	
	        $( "#tcloginErrorSpan-payment" ).html("Error occurred while processing the payment. Please try again later");
	        $(".k-loading-mask").hide();
		//$("#paymentFailuresDiv").css("display", "block");
	}
	//$("#form_container").html("Success Redirection " + data.view);			
},false);

	if(urlPromoItemDetailId != null && urlPromoItemDetailId != undefined && urlPromoItemDetailId != ''){
		proceedToRegister1();
	}

});

function getSecondUserInfoHeader(){
	var secondUserInfoTableHtml = '';
	secondUserInfoTableHtml += '<tr>';
	secondUserInfoTableHtml += '<td><h2 style="margin-top: 5px; margin-bottom : 5px;"><span>MEMBER 2 INFORMATION</span></h2> </td>';	
	secondUserInfoTableHtml += '<td >';
	secondUserInfoTableHtml += '<select  style="width:200px;" id="selectContactSecMember" class="selectContactDropDw">';	
	secondUserInfoTableHtml += '<option value="0">--Select contact--</option>';
	secondUserInfoTableHtml += '<c:forEach var="adultUser" items="${userLstInfo}" varStatus="count">';
	secondUserInfoTableHtml += '<c:if test="${ adultUser != null}">';
	secondUserInfoTableHtml += '<c:set var="dobSecVar" ><fmt:formatDate type="date" pattern="MM/dd/yyyy" value="${adultUser.dateOfBirth }" /></c:set>';
	secondUserInfoTableHtml += '<option value="${adultUser.firstName }||${adultUser.lastName }||${adultUser.formattedPhoneNumber }||${adultUser.emailAddress }||${dobSecVar }||${adultUser.gender }||${adultUser.contactId }">${adultUser.firstName } ${adultUser.lastName }</option>';
	secondUserInfoTableHtml += '</c:if>';	
	secondUserInfoTableHtml += '</c:forEach>';
	secondUserInfoTableHtml += '<option value="Other">New Member</option>';
	secondUserInfoTableHtml += '</select>';
	secondUserInfoTableHtml += '</td >';
	secondUserInfoTableHtml += '<td >&nbsp;</td>';	
	secondUserInfoTableHtml += '<td >&nbsp;</td>';
	secondUserInfoTableHtml += '</tr>';
	return secondUserInfoTableHtml;
}

function getYouthInfoHeader(){
	var secondUserInfoTableHtml = '';
	secondUserInfoTableHtml += '<tr>';
	secondUserInfoTableHtml += '<td><h2 style="margin-top: 5px; margin-bottom : 5px;"><span>YOUTH MEMBER</span></h2> </td>';	
	secondUserInfoTableHtml += '<td >';
	secondUserInfoTableHtml += '<select  style="width:200px;" id="selectContactYouth" class="selectContactDropDw">';	
	secondUserInfoTableHtml += '<option value="0">--Select contact--</option>';
	secondUserInfoTableHtml += '<c:forEach var="youthUser" items="${youthUserLstInfo}" varStatus="count">';
	secondUserInfoTableHtml += '<c:if test="${ youthUser != null}">';
	secondUserInfoTableHtml += '<c:set var="dobYouthVar" ><fmt:formatDate type="date" pattern="MM/dd/yyyy" value="${youthUser.dateOfBirth }" /></c:set>';
	secondUserInfoTableHtml += '<option value="${youthUser.firstName }||${youthUser.lastName }||${youthUser.formattedPhoneNumber }||${youthUser.emailAddress }||${dobYouthVar }||${youthUser.gender }||${youthUser.contactId }">${youthUser.firstName } ${youthUser.lastName }</option>';
	secondUserInfoTableHtml += '</c:if>';	
	secondUserInfoTableHtml += '</c:forEach>';
	secondUserInfoTableHtml += '<option value="Other">New Member</option>';
	secondUserInfoTableHtml += '</select>';
	secondUserInfoTableHtml += '</td >';
	secondUserInfoTableHtml += '<td >&nbsp;</td>';	
	secondUserInfoTableHtml += '<td >&nbsp;</td>';
	secondUserInfoTableHtml += '</tr>';
	return secondUserInfoTableHtml;
}

function getThirdUserInfoHeader(){
	var secondUserInfoTableHtml = '';
	secondUserInfoTableHtml += '<tr>';
	secondUserInfoTableHtml += '<td ><h2 style="margin-top: 5px; margin-bottom : 5px;"><span>MEMBER 3 INFORMATION</span></h2> </td>';
	secondUserInfoTableHtml += '<td >';
	secondUserInfoTableHtml += '<select  style="width:200px;" id="selectContactThirdMember" class="selectContactDropDw">';	
	secondUserInfoTableHtml += '<option value="0">--Select contact--</option>';
	secondUserInfoTableHtml += '<c:forEach var="adultUser" items="${userLstInfo}" varStatus="count">';
	secondUserInfoTableHtml += '<c:if test="${ adultUser != null}">';
	secondUserInfoTableHtml += '<c:set var="dobThirdVar" ><fmt:formatDate type="date" pattern="MM/dd/yyyy" value="${adultUser.dateOfBirth }" /></c:set>';
	secondUserInfoTableHtml += '<option value="${adultUser.firstName }||${adultUser.lastName }||${adultUser.formattedPhoneNumber }||${adultUser.emailAddress }||${dobThirdVar }||${adultUser.gender }||${adultUser.contactId }">${adultUser.firstName } ${adultUser.lastName }</option>';
	secondUserInfoTableHtml += '</c:if>';	
	secondUserInfoTableHtml += '</c:forEach>';
	secondUserInfoTableHtml += '<option value="Other">New Member</option>';
	secondUserInfoTableHtml += '</select>';
	secondUserInfoTableHtml += '</td >';
	secondUserInfoTableHtml += '<td >&nbsp;</td>';
	secondUserInfoTableHtml += '<td >&nbsp;</td>';
	secondUserInfoTableHtml += '</tr>';
	return secondUserInfoTableHtml;
}

function getKidsInfoHeader(){
	var kidsInfoTableHtml = '';
	kidsInfoTableHtml += '<tr>';
	kidsInfoTableHtml += '<td><h2 style="margin-top: 5px; margin-bottom : 5px; color: #888;"><span>KIDS INFORMATION</span></h2> </td>';	
	kidsInfoTableHtml += '<td >';
	kidsInfoTableHtml += '<select  style="width:200px;" id="primaryKidSelectHeader" class="selectPrimaryContactKid selectContactDropDw">';	
	kidsInfoTableHtml += '<option value="0">--Select contact--</option>';
	kidsInfoTableHtml += '<c:forEach var="kidUser" items="${kidsInfo}" varStatus="count">';
	kidsInfoTableHtml += '<c:if test="${ kidUser != null}">';
	kidsInfoTableHtml += '<c:set var="dobkidUserVar" ><fmt:formatDate type="date" pattern="MM/dd/yyyy" value="${kidUser.dateOfBirth }" /></c:set>';
	kidsInfoTableHtml += '<option value="${kidUser.firstName }||${kidUser.lastName }||${dobkidUserVar }||${kidUser.gender }||${kidUser.contactId }">${kidUser.firstName } ${kidUser.lastName }</option>';
	kidsInfoTableHtml += '</c:if>';		
	kidsInfoTableHtml += '</c:forEach>';
	kidsInfoTableHtml += '<option value="Other">New Member</option>';
	kidsInfoTableHtml += '</select>';
	kidsInfoTableHtml += '</td >';
	kidsInfoTableHtml += '<td >&nbsp;</td>';
	kidsInfoTableHtml += '<td ><a class="add-child-lnk">Add Child &nbsp; <span>+</span></a></td>';
	kidsInfoTableHtml += '</tr>';
	return kidsInfoTableHtml;
}

function getSecondaryKidInfoHeader() {
	var kidsInfoTableHtml = '';
	kidsInfoTableHtml += '<tr>';
	kidsInfoTableHtml += '<td colspan="4">';
	kidsInfoTableHtml += '<table border="0" width="101%" class="secondaryKidsInfoForm">';
	kidsInfoTableHtml += '<tr>';
	kidsInfoTableHtml += '<td>&nbsp;</td>';	
	kidsInfoTableHtml += '<td >'; 
	kidsInfoTableHtml += '<select  style="width:200px;" id="selectsecondaryContactKid'+Math.floor((Math.random() * 1000) + 1) +'" onchange="populateAdditionalKidInfo(this)" class="selectsecondaryContactKid selectContactDropDw">';	
	kidsInfoTableHtml += '<option value="0">--Select contact--</option>';
	kidsInfoTableHtml += '<c:forEach var="kidUser" items="${kidsInfo}" varStatus="count">';
	kidsInfoTableHtml += '<c:if test="${ kidUser != null}">';
	kidsInfoTableHtml += '<c:set var="dobkidUserVar" ><fmt:formatDate type="date" pattern="MM/dd/yyyy" value="${kidUser.dateOfBirth }" /></c:set>';
	kidsInfoTableHtml += '<option value="${kidUser.firstName }||${kidUser.lastName }||${dobkidUserVar }||${kidUser.gender }||${kidUser.contactId }">${kidUser.firstName } ${kidUser.lastName }</option>';
	kidsInfoTableHtml += '</c:if>';		
	kidsInfoTableHtml += '</c:forEach>';
	kidsInfoTableHtml += '<option value="Other">New Member</option>';
	kidsInfoTableHtml += '</select>';
	kidsInfoTableHtml += '</td >';
	kidsInfoTableHtml += '<td>&nbsp;</td>';
	kidsInfoTableHtml += '<td>&nbsp;</td>';
	kidsInfoTableHtml += '</tr>';
	return kidsInfoTableHtml;
}

</script>
</body>