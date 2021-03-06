<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<body>
	<%@ include file="../../layouts/include_taglib.jsp"%>
	<script src="<%=contextPath %>/resources/js/payment/luhn.js"></script>
	<script src="<%=contextPath %>/resources/js/payment/view.js"></script>
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
				               <!--  <label class="stepNumber">1</label> -->
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
											<c:if test="${ location.branch != 'All Branches' && location.branch != 'Bay Area' }">
												<option value="${location.id }">${location.branch }</option>
											</c:if>
										</c:forEach>
							</select> 
							<div style="display: none;">
								<input type="hidden" id="primaryContactFName" value="${primaryUser.firstName }">
								<input type="hidden" id="primaryContactLName" value="${primaryUser.lastName }">
								<input type="hidden" id="signupProductId" value="${signUpProduct.itemDetail.id }">
								<input type="hidden" id="signUpProductType" value="${signUpProduct.signUpProductType }">
								<input type="hidden" id="signUpProductNextBillingDate" value="${signUpProduct.membersshipFeeNextBillingDate }">								
							</div> 
							</div>
							<div style="height: 370px;">
								<ul id="panels">
								</ul>
							</div>

							</div>
							<div id="step-2">
							<input style="display:none;" path="itemDetailsId" id="signedUplocationId" value="${signUpProduct.location.id }" />
							<form:form commandName="account" method="post" action="changeMembershipRegister" id="becomeMemberForm" style="border : none; padding : 0px; margin : 0px;  max-width: 100%;" autocomplete="off"
			              class="bootstrap-frm cmxform" >			        
								 <table border="0" width="100%" id="userInfoTable">
									<tr>
										<td colspan="2"><h2 style="margin-top: 5px; margin-bottom : 5px;"><span>MEMBER 1 INFORMATION</span></h2> </td><td >&nbsp;</td>
										<td >
											<span id="selectFamilyMemberbtn" class="k-button" style="width: 115px;text-shadow: none;float: right; margin-left:10px; ">Select Member</span>
										</td>
									</tr>
									<tr>
										<td><form:input cssClass="form-control" path="userLst[0].personFirstName" value="" title="Please enter yout First Name" name="firstName" id="firstName" maxlength="50" placeholder="First Name"  /></td>					 		
										<td>&nbsp;</td>
										<td><form:input cssClass="form-control" path="userLst[0].personLastName"  value="" title="Please enter yout Last Name" name="lastName" id="lastName" maxlength="50" placeholder="Last Name" /></td>
										<td>&nbsp;</td>
									</tr>
									<tr>		 		
										<td><form:input  path="userLst[0].dateOfBirth" title="Please enter your Date of Birth" name="dob" id="dob" maxlength="50" placeholder="D.O.B" value=""   style="width: 205px; " /></td>		
										<td id="dop-er"></td>
										<td><form:input cssClass="form-control" path="userLst[0].primaryFormattedPhoneNumber" value="" title="Please enter your Phone Number" name="phoneNumber" id="phoneNumber" maxlength="50" placeholder="Phone Number" /></td>
										<td></td>
									</tr>
									<tr>		 		
										<td><form:input cssClass="form-control" path="userLst[0].primaryEmailAddress"  name="email" id="email" value="" onkeyup="return forceLower(this);" maxlength="50" placeholder="Email"  /></td>			 							 		
										<td></td>
										<td>
											<span >
												<span><span><input type="radio" value="Male" style="width : 15px;" checked  class="form-control primary-user-gender" name="userLst[0].gender" id="genderM"></span><span style="margin-left : 5px;">Male</span></span>
												<span><span><input type="radio" value="Female" style="width : 15px;" class="form-control primary-user-gender" name="userLst[0].gender" id="genderF"></span><span style="margin-left : 5px;">Female</span></span>
													
												<%-- <c:if test="${primaryUser.gender == 'Male' }">
													<span><span><input type="radio" value="Male" style="width : 15px;" checked  class="form-control primary-user-gender" name="userLst[0].gender" id="genderM"></span><span style="margin-left : 5px;">Male</span></span>
													<span><span><input type="radio" value="Female" style="width : 15px;" class="form-control primary-user-gender" name="userLst[0].gender" id="genderF"></span><span style="margin-left : 5px;">Female</span></span>
													
												</c:if>
												<c:if test="${primaryUser.gender == 'Female' }">
													<span><span><input type="radio" value="Male" style="width : 15px;" class="form-control primary-user-gender" name="userLst[0].gender" id="genderM"></span><span style="margin-left : 5px;">Male</span></span>
													<span><span><input type="radio" value="Female" style="width : 15px;" checked  class="form-control primary-user-gender" name="userLst[0].gender" id="genderF"></span><span style="margin-left : 5px;">Female</span></span>
													
												</c:if> --%>
												
											</span>					 		
										</td>
										<td></td>
									</tr>
									<tr>		 		
										<td>
											<span class="k-block k-success-colored" id="emailSuccessSpan"></span>
											<span class="k-block k-error-colored" id="emailErrorSpan"></span>
										</td>			 							 		
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<%-- <tr>
										<td><form:password cssClass="form-control" path="userLst[0].password" name="password" id="password" maxlength="50" placeholder="Password" /></td>
										<td></td>
										<td><form:password cssClass="form-control" path="userLst[0].confirmPassword" id="confirm_password" name="confirm_password" maxlength="50" placeholder="Confirm Password" /></td>
										<td></td>
									</tr> --%>
									</table>
									<table border="0" width="100%" id="familyInfoFormTable">		
									 					 	
									 </table>
									<table border="0" width="100%">
									 <tr>
											<td colspan="2"><h2 style="margin-top: 5px; margin-bottom : 5px;"><span>ADDRESS</span></h2> </td>
											<td></td>
											<td></td>
											<td></td>
									 </tr>
									 <tr>
											<td><form:input cssClass="form-control" path="addressLine1" name="addressLine1" id="addressLine1" maxlength="50" placeholder="Address 1" /></td>
											<td></td>
											<td><form:input cssClass="form-control" path="addressLine2" name="addressLine2" id="addressLine2" maxlength="50" placeholder="Address 2" /></td>
											<td></td>
									 </tr>
									<tr>
										<td><form:input cssClass="form-control" path="city" name="city" id="city" maxlength="50" placeholder="City" title="Please enter yout City"/></td>						
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
											<td><form:input cssClass="form-control" path="postalCode" name="postalCode" id="postalCode" maxlength="20" placeholder="Postal Code" /></td>
											<td>&nbsp; </td>
											<td>&nbsp; </td>
											<td>&nbsp; </td>
									 </tr>
									
													
								</table>
								
								<form:input style="display:none;" path="itemDetailsId" id="itemDetailsId" value="${signUpProduct.itemDetail.id }" />
									
								<form:input style="display:none;" path="transId" id="paymentTransId" value="" />	
								<form:input style="display:none;" path="joiningFee" id="prodJoiningFeeInput" value="" />
								<form:input style="display:none;" path="productPrice" id="prodPriceInput" value="" />
								
								<form:input style="display:none;" path="expirationMonth" id="expirationMonthHid" value="" />									
								<form:input style="display:none;" path="expirationYear" id="expirationYearHid" value="" />									
								<form:input style="display:none;" path="nickName" id="nickNameHid" value="" />						
								
							</form:form>	       
				        	</div> 
							
				  			<div id="step-3">
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
				        	</div>    
				        	
				        	<div id="step-4">
				        		<div id="purchase_info" style="height: 260px;">
									
									<div class="addcarddiv">
										<div style="height:20px;">Please select Payment Method</div>
										<select id="paymentInfoRadio" name="paymentInfoRadio" style="width:300px;">		
											
											<option value="New">Add New Card</option>
											<option value="NewBank">Add New Bank Info</option>															
										</select>
									</div>									
									<div class="addcarddiv" style="margin-left: 42px;">
										<div style="height:20px;">Please select Payment Frequency</div>
										<select id="paymentFrequencySelect" name="paymentFrequencySelect" style="width:150px;">	
											<option value="Monthly" selected="selected">Monthly</option>
											<option value="Annual">Annual</option>						
										</select>
									</div>
									<div class="addcarddiv">
										<div style="height:20px;">Start Date</div>
										<input type="text" id="start" name="startDate" style="width:150px;">
									</div>
									<div class="addcarddiv" id="endDateDiv" style="display:none;">
										<div style="height:20px;">End Date</div>
										<input type="text" id="end" name="type" readonly style="width:150px;" >
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
														<input type="text" class="k-textbox" placeholder="Card Number" name="cardNum" maxlength="16" id="cardNum" value="" tabIndex="2" onchange="handleCCTyping(this.form, event);" onkeyup="handleCCTyping(this.form, event);">
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
														<input type="password" class="k-textbox" placeholder="Security Code" style="width: 90px;" name="securityCode" id="securityCode" value="" tabIndex="3" title="CVV Help"> 
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
												
												<div id="savecardcheckbox" class="addcarddiv" style="width:124px">
													<div><input type="checkbox" checked="checked" disabled="disabled" name="SaveCard" id="SaveCard">&nbsp;&nbsp;Save Card</div>
													
												</div>
												<div id="nickname" class="addcarddiv" >
													<div style="height:20px;">Nick Name</div>
													<div><input type="text" class="k-textbox" placeholder="Nick Name" name="nickName" id="nickName" value=""></div> 
												</div>
											</div>
										
									</div>
									<div style="float: right; width: 300px;">
												
										</div>
									</div>
									
									<div id="addBank" style="margin-top:10px; margin-bottom:10px; height:44px; display:none;   float: left; ">										
											<div style="height:50px;">
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
												
												<div id="savecardcheckbox" class="addcarddiv" style="width:124px">
													<div><input type="checkbox" checked="checked" disabled="disabled" name="SaveCard" id="SaveCard">&nbsp;&nbsp;Save Card</div>
													
												</div>
												<div id="nickname" class="addcarddiv" >
													<div style="height:20px;">Nick Name</div>
													<div><input type="text" class="k-textbox" placeholder="Nick Name" name="nickName" id="nickName" value=""></div> 
												</div>
											</div>										
									</div><br />
									<div id="statusBlock-payment" style="display: none; margin-top: 100px;">
										<span class="k-block k-success-colored"
											id="tcloginSuccessSpan-payment" style="display: none"></span>
										<span class="k-block k-error-colored"
											id="tcloginErrorSpan-payment" style="display: none"></span>
									</div>
											
											
										
								         
				        	</div> 
				        	</div>
				        	
				        	<div id="step-5">
				            	 <div id="contentFormDiv" >
				            	<div class="border-bottom-h3" style="margin-bottom : 0px;">
				        		<h2 style="color: #888; text-align: center; margin-top: 5px; margin-bottom: 5px;">Summary</h2>	
				        	</div>
				        	
							<div style="max-width : auto;" class="memberSummaryPage">
								
								<table style="" border="0" width="100%">							
										
										<tr>
											<td>
												<table style="" border="0" width="100%">							
															<tbody >	
																<tr>
																	<td width="100%">
																		<table style="/* float: left; margin-left: 10px; */"
																			class="summary-table-border">
																			<tbody style="min-height: 300px;">
																				<tr>
																					<td colspan="2" style="border-bottom: 1px solid #ddd;">
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
																					<td id="firstNameTd">${primaryContact.personFirstName }</td>
																				</tr>
																				<tr>
																					<td><span><b>Last Name</b></span></td>
																					<td id="lastNameTd">${primaryContact.personLastName }</td>
																				</tr>
																				<tr>
																					<td><span><b>Date of Birth</b></span></td>
																					<c:if test="${ primaryContact.dateOfBirth != null }">
																						<c:set var="dob" value="${fn:split(primaryContact.dateOfBirth, ' ')}"/>
																						<td id="dobTd">${dob[0] }</td>
																					</c:if>
																					<c:if test="${ primaryContact.dateOfBirth == null }">
																						
																						<td id="dobTd">&nbsp;</td>
																					</c:if>
																					
																				</tr>
																				<tr>
																					<td><span><b>Phone Number</b></span></td>
																					<td id="phoneNumberTd">${primaryContact.primaryFormattedPhoneNumber }</td>
																				</tr>
																				<tr>
																					<td><span><b>Email</b></span></td>
																					<td id="emailTd">${primaryContact.primaryEmailAddress }</td>
																				</tr>
																				<tr>
																					<td><span><b>Gender</b></span></td>
																					<td id="genderTd">${primaryContact.gender }</td>
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
																						<span style="font-size: 13px;"><b><h4><u>SECONDARY MEMBER<u></u></h4></b></span>
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
																					<td colspan="2" style="border-bottom: 1px solid #ddd;">
																						<h3>PAYMENT INFORMATION</h3>
																					</td>
																					<td></td>
																				</tr>
																									<!-- <tr>
																					<td ><span><b>Product Id</b></span></td>
																					<td id="productIdTd"></td>
																				</tr> -->
																				<tr>
																					<td><span><b>Join Fee</b></span></td>
																					<td ><span>$</span><span id="sumJoinFeeTd"></span></td>
																				</tr>
																				<tr style="display:none;">
																					<td><span><b>Price</b></span></td>
																					<td ><span>$</span><span id="sumTierPriceTd"></span></td>
																				</tr>																				
																				<tr>
																					<td><span><b>Payment Frequency</b></span></td>
																					<td id="sumPaymentFreqTd"></td>
																				</tr>
																				<tr>
																					<td style="border-bottom: 1px solid #ddd; padding-bottom: 5px;"><span><b>Total Price</b></span></td>
																					<!-- <td id="sumTotalPriceTd"></td> -->
																					<td style="border-bottom: 1px solid #ddd; padding-bottom: 5px;"><span>$</span><span id="sumTotalPriceTd"></span></td>
																				</tr>
																				<tr style="display:none;" id="sumChangePriceTr">
																					<td><span><b>Change Price(Current Month)</b></span></td>
																					<td ><span>$</span><span id="sumChangePriceTd"></span></td>
																				</tr>
																				<tr style="display:none;" id="sumProRatePriceTr">
																					<td><span><b>ProRate Price(Current Month)</b></span></td>
																					<td ><span>$</span><span id="sumProRatePriceTd"></span></td>
																				</tr>
																				<tr style="display:none;" id="sumAutoApplyTr">
																					<td><span><b>EARLYBIRD</b></span></td>
																					<td ><span style="color: red;">-$</span><span id="sumEarlyBirdDiscount" style="color: red;"></span></td>
																				</tr>
																				<tr style="display:none;" id="sumPromoCodeTr">
																					<td valign="bottom"><b><span id="sumPromoCodeName"></span></b></td>
																					<td>
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
																				<tr>
																					<td colspan="2">
																						<div id="refundStatusBlock">
																							<span class="k-block k-success-colored" id="refundSuccess"></span>
																							<span class="k-block k-error-colored" id="refundfail"></span>
																						</div>
																					</td>																		
																					<td>&nbsp;</td>
																				</tr>
																			</tbody>
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
					<c:if test="${ primaryUser.personFirstName != null}">	 
 					<tr> 
						<td><input type="checkbox" name="selectMemberCheckBox" class="selectMemberCheckBox"></td> 
 						<td class="userFNameTd">${primaryUser.personFirstName } </td> 
 						<td class="userLNameTd">${primaryUser.personLastName }</td> 
 						<td class="userGenderTd">${primaryUser.gender }</td>
						<td style="display :none;" class="userBdayTd">${primaryUser.dateOfBirth } </td> 
 						<td style="display :none;" class="userPhoneNoTd">${primaryUser.primaryFormattedPhoneNumber }</td> 
 						<td style="display :none;" class="userEmailAddTd">${primaryUser.primaryEmailAddress }</td> 
 					</tr> 
					</c:if>	
					<c:if test="${ secUser.personFirstName != null}">	 
 					<tr> 
						<td><input type="checkbox" name="selectMemberCheckBox" class="selectMemberCheckBox"></td> 
 						<td class="userFNameTd">${secUser.personFirstName } </td> 
 						<td class="userLNameTd">${secUser.personLastName }</td> 
 						<td class="userGenderTd">${secUser.gender }</td>
						<td style="display :none;" class="userBdayTd">${secUser.dateOfBirth } </td> 
 						<td style="display :none;" class="userPhoneNoTd">${secUser.primaryFormattedPhoneNumber }</td> 
 						<td style="display :none;" class="userEmailAddTd">${secUser.primaryEmailAddress }</td> 
 					</tr> 
					</c:if>	
					<c:if test="${ thirdUser.personFirstName != null}">	
					<tr> 
						<td><input type="checkbox" name="selectThirdMemberCheckBox" class="selectThirdMemberCheckBox"></td> 
 						<td class="userFNameTd">${thirdUser.personFirstName } </td> 
 						<td class="userLNameTd">${thirdUser.personLastName }</td> 
 						<td class="userGenderTd">${thirdUser.gender }</td>
						<td style="display :none;" class="userBdayTd">${thirdUser.dateOfBirth } </td> 
 						<td style="display :none;" class="userPhoneNoTd">${thirdUser.primaryFormattedPhoneNumber }</td> 
 						<td style="display :none;" class="userEmailAddTd">${thirdUser.primaryEmailAddress }</td> 
 					</tr>
					</c:if>				
 				</table> 
			</div><br />
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
 								<td class="kidFirstName">${kid.personFirstName } </td> 
 								<td class="kidLastName">${kid.personLastName }</td> 
 								<td class="kidGender">${kid.gender }</td> 
								<td style="display :none;" class="kidDob">${kid.dateOfBirth }</td> 
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

<script>
$(document).ready(function(){
	
	var eventMethod = window.addEventListener ? "addEventListener" : "attachEvent";
	var eventer = window[eventMethod];
	var messageEvent = eventMethod == "attachEvent" ? "onmessage" : "message";
	eventer(messageEvent,function(e) {
		var key = e.message ? "message" : "data";
		var data = e[key];
		//$("#contentFormDiv").css("display", "none");			
		if(data.view.toString() == "Success"){
			
			 $("#paymentTransId").attr("value", paymentOrderId);
			var joinFee = $('#sumJoinFeeTd').text();
			  var sumTierPriceTd = $('#sumTierPriceTd').text();
			  
			 
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
});

</script>
</body>