
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
					                  1. TRAIL PASS INFO<br />
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
				             <li><a href="#step-5">
				               <!--  <label class="stepNumber">2</label> -->
				                <span class="stepDesc">
				                   4. SUMMARY<br />
				                   <!-- <small>Step 2 description</small> -->
				                </span>
				            </a></li>
				  			</ul>
				  			
				  			<!-- STEP ONE CODE -->
				  			<div id="step-1">	
				            <p>
				            <div class="border-bottom-h3">
				            	<span style="margin-right: 15px;"> Please select YMCA location</span>
				            <%-- <select name="location" id="location" style="width:250px;" ><br />
										<option value="0">--Select Location--</option>
										<c:forEach var="location" items="${locations}" varStatus="count">
											<c:if test="${ location.branch != 'All Branches' && location.branch != 'Bay Area' }">
												<option value="${location.id }">${location.branch }</option>
											</c:if>
										</c:forEach>
							</select>  --%>
							<select name="location" id="location" style="width:250px;" ><br />
          <option value="0">--Select Location--</option>
          <c:forEach var="location" items="${locations}" varStatus="count">
           <c:if test="${ location.recordName != 'All Branches' && location.recordName != 'Bay Area' && location.branch == 'true' }">
            <option value="${location.id }">${location.recordName }</option>
           </c:if>
          </c:forEach>
       </select>
				  			<div style="height: 315px;">		
				               
				            <ul id="panels">
				            
									</ul>
								</div> 
								</div>
							
							</div>
								<div id="step-2">			
							<div id="registerDiv" >
				            	<form:form commandName="account" method="post" action="becomeTPMemberRegister" id="becomeTPMemberRegister" style="border : none; padding : 0px; margin : 0px; max-width: 100%;" autocomplete="off"
							              class="bootstrap-frm cmxform" >			        
									 <table border="0" width="100%" id="userInfoTb">					 						 
									 	<tr>
									 		<td colspan="2"><h2 style="margin-top: 5px; margin-bottom : 5px;"><span>PRIMARY MEMBER</span></h2> </td>
									 		<td >&nbsp;</td>
									 		<td > <span id="memberSignInSpan" class="k-button" style="width: 70px;text-shadow: none;float: right; margin-left:10px; ">Sign in</span></td>
									 		<!-- <span id="newMemberSpan" class="k-button" style="width: 70px;text-shadow: none;float: right; ">New Member?</span> -->
									 	</tr>
									 	<tr>
									 		<td><form:input cssClass="form-control" path="userLst[0].personFirstName" title="Please enter yout First Name" name="firstName" id="firstName" maxlength="50" placeholder="First Name"  /></td>					 		
									 		<td>&nbsp;</td>
									 		<td><form:input cssClass="form-control" path="userLst[0].personLastName" title="Please enter yout Last Name" name="lastName" id="lastName" maxlength="50" placeholder="Last Name" /></td>
									 		<td>&nbsp;</td>
									 	</tr>
									 	<tr>		 		
									 		<td>
									 		
									 		<form:input  path="userLst[0].dateOfBirth" title="Please enter your Date of Birth" name="dob" id="dob" maxlength="50" placeholder="Date of Birth"  style="width: 1px; height : 1px; left : -100px; top : -100px; position : absolute;" />
					 			<span class="">Date of Birth (MM/DD/YYYY) : </span> <br />
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
								</span><%-- <form:input  path="userLst[0].dateOfBirth" title="Please enter your Date of Birth" name="dob" id="dob" maxlength="50" placeholder="Date of Birth"  style="width: 205px; " /> --%></td>		
									 		<td id="dop-er"></td>
									 		<td><form:input cssClass="form-control" path="userLst[0].primaryFormattedPhoneNumber" title="Please enter your Phone Number" name="phoneNumber" id="phoneNumber" maxlength="50" placeholder="Phone Number" /></td>
									 		<td></td>
									 	</tr>
										<tr>		 		
									 		<td><form:input cssClass="form-control" path="userLst[0].primaryEmailAddress"  name="email" id="email"  maxlength="50" placeholder="Email"  /></td>			 							 		
									 		<td></td>
									 		<td>
									 		<%-- <span><form:radiobutton cssClass="form-control" path="workNumber" value="Male" id="genderM" />&nbsp;Male&nbsp;<form:radiobutton cssClass="form-control" path="workNumber" value="Female" id="genderF" />&nbsp;Female</span> --%>
									 			<span >
									 				<span><span><form:radiobutton cssClass="form-control" path="userLst[0].gender" style="width : 15px;" value="Male" id="genderM" /></span><span style="margin-left : 5px;">Male</span></span>
									 				<span><span><form:radiobutton cssClass="form-control" path="userLst[0].gender"  style="width : 15px;" value="Female" id="genderF" /></span><span style="margin-left : 5px;">Female</span></span>
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
									 	<c:if test="${sessionScope.agentUid == null}">
										<tr>
									 		<td><form:password cssClass="form-control" path="userLst[0].password" name="password" id="password" maxlength="50" placeholder="Password" /></td>
									 		<td></td>
									 		<td><form:password cssClass="form-control" path="userLst[0].confirmPassword" id="confirm_password" name="confirm_password" maxlength="50" placeholder="Confirm Password" /></td>
									 		<td></td>
									 	</tr>
									 	</c:if>
									 </table>
									 <!-- <table border="0" width="100%" id="oneAdultWithKidsTable">		
									 					 	
									 </table> -->
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
									<form:input style="display:none;" path="tcDescription" id="userTC" value="" />	
									<form:input style="display:none;" path="productId" id="productIdHidInput" value="" />
									<%-- <form:input style="display:none;" path="paymentMethodId" id="paymentMethodIdHidInput" value="" />
									<form:input style="display:none;" path="transId" id="paymentTransId" value="" /> --%>	
									<form:input style="display:none;" path="joiningFee" id="prodJoiningFeeInput" value="" />
									<form:input style="display:none;" path="productPrice" id="prodPriceInput" value="" />								
									<%-- <form:input style="display:none;" path="signup[0].finalAmount" id="progFinalAmtIdHidInput" value="" /> --%>
									<form:input style="display:none;" path="locationId" id="locationIdInput" value="" />									
									<form:input style="display:none;" path="userLst[0].isAdult" value="true"/>
									<form:input style="display:none;" path="userLst[0].usrSequence" value="0" />
								</form:form>
								</div>				
								<div id="loginDiv" style="display:none;">
									<%@ include file="../../views/customer/memberloginTrialPass.jsp" %> 
								</div>
				                     			
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
																		<!-- <table style="/* float: left; margin-left: 5px; */"
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
																		</table> -->
								
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
							<h2><span> Process Completed Successfully.</span></h2>
						</div>
						<div class="bootstrap-frm" id="paymentFailuresDiv" style="display:none">
							<h2><span> process Failed. Please try after sometime. </span></h2>
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
    <p class="error-message-p">Please select the Pricing Options.</p>
	
    <div class="confirmbutton" align="center"><button class="confirm-pricing-select k-button" style=" width: 35%;">Ok</button>   
</script>


<script type="text/javascript">
 /*  $(document).ready(function() {
      // Initialize Smart Wizard
        $('#wizard').smartWizard();
  });  */
  
  var emailValid = "false";
  var loginValid = "false";
  var signInSelected = "false";
  var isLoggedIn = "false";
 // var paymentFreq1 = $("#paymentFrequencySelect").data("kendoDropDownList");

  var selectedHeaderInfo = "";
  var selectedTierPriceInfo = "";
  var selectedProdDescInfo = "";
  var selectedJoiningFeeInfo = "";

  var selectedProdctId = "";
  var selectedProductName = "";
  var selectedProductTotalPrice = "";
  var selectedProdTandc = "";

  var selectedAutoPromoDiscount = "";
  var selectedItemDetailsId = "";
  
  var secKidCount = 1;
  
  $("#phoneNumber").mask("(999) 999-9999");
  $("#dob").kendoDatePicker();
  $("#location").kendoDropDownList(); 
  
  var locationDropdownlist = $("#location").data("kendoDropDownList");
  locationDropdownlist.select(0);
  
  
  
  
  
  
		
		 $(document).on('click', '.product-register-div', function(){
		    	//alert("click");
		    	/* console.log("header " + $(this).parent().parent().find( ".k-header" ).text());
		    	console.log("price-div " + $(this).parent().parent().find( ".price-div" ).text());
		    	console.log("product-description-div " +$(this).parent().parent().find( ".product-description-div" ).text());
		    	console.log("product-join-fee-text-val-div " + $(this).parent().parent().find( ".product-join-fee-text-val-div" ).text());  */
		    	
		    	selectedHeaderInfo = $(this).parent().parent().find( ".k-header" ).text();
		    	selectedTierPriceInfo = $(this).parent().parent().find( ".price-div" ).text();
		    	selectedProdDescInfo = $(this).parent().parent().find( ".product-description-div" ).text();
		    	selectedJoiningFeeInfo = $(this).parent().parent().find( ".product-join-fee-text-val-div" ).text();  
		    	
		    	selectedProdctId = $(this).parent().parent().find( ".prod-id-div" ).text();
		    	selectedProductName = $(this).parent().parent().find( ".prod-name-div" ).text();
		    	//selectedProductTotalPrice = $(this).parent().parent().find( ".prod-total-price-div" ).text();
		    	selectedProdTandc = $(this).parent().parent().find( ".prod-tandc-div" ).html();
		    	
		    	selectedAutoPromoDiscount = $(this).parent().parent().find( ".prod-autoPromoDiscount-div" ).html();
		    	
		    	//$("input:radio[name=paymentTypeSelect]").change(function() {
		    	/* $(document).on('change', 'input:[name=paymentTypeSelect]', function(){
		    	
		    		if($("input:checkbox[name=paymentTypeSelect]").is(":checked")){
		    			$("input:checkbox[name=paymentTypeSelect]").prop('checked', false); 
		    		}
		    	    $(this).prop('checked', false); 
		    	    alert( $(this).val());
		    		selectedProductTotalPrice = parseFloat(selectedTierPriceInfo) + parseFloat(selectedJoiningFeeInfo);
		    		alert(selectedProductTotalPrice);
		    	});  */
		    	
		    	/* $(document).on('click', '.paymentTypeSelectClass', function() {
		    		if($("input:checkbox[name=paymentTypeSelect]").is(":checked")){
		    			$("input:checkbox[name=paymentTypeSelect]").prop('checked', false); 
		    		}
		    	    $(this).prop('checked', true); 
		    	    alert( $(this).val());
		    		selectedProductTotalPrice = parseFloat(selectedTierPriceInfo) + parseFloat(selectedJoiningFeeInfo);
		    		alert(selectedProductTotalPrice);
		    	}); */
		    	//if($("input:radio[name=paymentTypeSelect]").is(":checked")){
		    	if($(this).parent().parent().find( "input:radio[name=paymentTypeSelect]" ).is(":checked")){    	
		    		var paymentType = $(this).parent().parent().find( "input:radio[name=paymentTypeSelect]:checked" ).val();
		    		if(paymentType == 'AllArea'){
		    			var allAreaPrice = $(this).parent().parent().find( ".all-area-price-td" ).text();    		
		    			selectedProductTotalPrice = parseFloat(allAreaPrice) + parseFloat(selectedJoiningFeeInfo);       
		    			$("#sumTierPriceTd").html(allAreaPrice);
		    		}else if(paymentType == 'BayArea'){
		    			var bayAreaPrice = $(this).parent().parent().find( ".bay-area-price-td" ).text();    		
		    			selectedProductTotalPrice = parseFloat(bayAreaPrice) + parseFloat(selectedJoiningFeeInfo);    
		    			$("#sumTierPriceTd").html(bayAreaPrice);
		    		}    		
		    	} else{
					selectedProductTotalPrice = parseFloat(selectedTierPriceInfo) + parseFloat(selectedJoiningFeeInfo);  
					$("#sumTierPriceTd").html(selectedTierPriceInfo);
				}
		    	
		    	if(selectedAutoPromoDiscount != 0){
					  $("#sumAutoApplyTr").css("display","");			  
					  $("#sumEarlyBirdDiscount").html(selectedAutoPromoDiscount);
				  }else{
					  $("#sumAutoApplyTr").css("display","none");			 
					  $("#sumEarlyBirdDiscount").html("");
				  }
		    	
		    	
				 $("#sumPromoCodeTr").css("display","none");			  
				 $("#sumPromoCodeName").html("");
				 $("#sumPromoCodeVal").html("");		 
				
		    	//alert(selectedAutoPromoDiscount);
		    	selectedItemDetailsId = $(this).parent().parent().find( ".prod-itemDetailsId-div" ).html();
		    	
		    	$("#productIdTd").html(selectedProdctId);
		    	$("#productIdHidInput").attr("value", selectedProdctId);
		    	//alert(selectedProdctId);
		    	$("#productNameTd").html(selectedProductName);
		    	$("#productDescriptionTd").html(selectedProdDescInfo);
		    	$("#productTypeTd").html($(this).parent().parent().find( ".prod-type-div" ).text());    	
		    	
		    	$("#productPriceTd").html(selectedProductTotalPrice);
		    	$("#paymentAmountSpan").html(selectedProductTotalPrice);
		    	$("#paymentTotalPriceTd").html(selectedProductTotalPrice);	
		    	$("#sumTotalPriceTd").html(selectedProductTotalPrice);
		    	
		    	$(".termsDiv").html(selectedProdTandc);
		    	$("#userTC").attr("value", selectedProdTandc);
		    	
		    	$("#paymentJoinFeeTd").html(selectedJoiningFeeInfo);
		    	$("#sumJoinFeeTd").html(selectedJoiningFeeInfo);		
		    	
		    	
		    		    	
		    	
		    	var oneAdultWithKidsHtml = getKidsInfoHeader() + getKidsInfoHtml("1");
		    	var twoAdultsHtml = getSecondUserInfoHeader() + getSecondUserInfoData("1");
		    	var twoAdultsWithKidsHtml = getSecondUserInfoHeader() + getSecondUserInfoData("1") + getKidsInfoHeader() + getKidsInfoHtml("1");
		    	var threeAdultsWithKidsHtml = getSecondUserInfoHeader() + getSecondUserInfoData("1") + getThirdUserInfoHeader() + getSecondUserInfoData("2") + getKidsInfoHeader() + getKidsInfoHtml("1");
		    	
		    	if(selectedHeaderInfo == 'One Adultw/ Kids'){
		    		$('#oneAdultWithKidsTable').css("display" , "inline-table");
		    		$('#oneAdultWithKidsTable').html(oneAdultWithKidsHtml);
		    		
		    	}else if(selectedHeaderInfo == 'Two Adults'){
		    		$('#oneAdultWithKidsTable').css("display" , "inline-table");
		    		$('#oneAdultWithKidsTable').html(twoAdultsHtml);
		    	}else if(selectedHeaderInfo == 'Two Adultsw/ Kids'){
		    		$('#oneAdultWithKidsTable').css("display" , "inline-table");
		    		$('#oneAdultWithKidsTable').html(twoAdultsWithKidsHtml);
		    	}else if(selectedHeaderInfo == 'Three Adultsw/ or w/o kids'){
		    		$('#oneAdultWithKidsTable').css("display" , "inline-table");
		    		$('#oneAdultWithKidsTable').html(threeAdultsWithKidsHtml);
		    	}else{
		    		$('#oneAdultWithKidsTable').css("display" , "none");
		    	}    	
		    	$("#wizard"). smartWizard("fixHeight");
		    	$('#wizard').smartWizard('goToStep',2);
		    	
		    });
		    	
		    	
		    	
		    	
		    	
		    	
		    $("#location").on('change',function(e){  
			    if(locationDropdownlist.text() != '--Select Location--'){
			    	/* var locId = 0;
			    	if(pricingOption.text() == 'All Branches' || pricingOption.text() == 'Bay Area'){
			    		locId = $("#pricingOption").kendoDropDownList().val(); */
			    		/* if(locationDropdownlist.text() == 'All Branches'){
			    			$("#prefLocation").html("");
			    			$("#prefLocation").html($("#allLocations").html());
			    			$("#prefLocation").kendoDropDownList();
			    		}
			    		if(locationDropdownlist.text() == 'Bay Area'){
			    			$("#prefLocation").html("");
			    			$("#prefLocation").html($("#bayAreaLocations").html());
			    			$("#prefLocation").kendoDropDownList();
			    		} */	    		
			    		//$("#prefLocation").closest(".k-widget").show();    		 
			    	/* } else{ 
			    		locId = $("#location").kendoDropDownList().val();
		    		} */
			    	
			    	$(".k-loading-mask").show();
					$(".k-loading-text").html("Please wait");	
					$("#tcloginErrorSpan-payment").css("display", "none");	
					$( "#tcloginErrorSpan-payment" ).html("");
					$("#panels").html("");
					$.ajax({
						  type: "GET",
						  url: "getTrailProductDetailsByLocation",	
						  data: {"locationId" : $("#location").kendoDropDownList().val()},
						  success: function( data ) {
							  //console.log(data);
							  var pricingBoxHtml = '';
							  var jsonDataArr = jQuery.parseJSON(data);
							  if(jsonDataArr != null && jsonDataArr.length > 0){
			    				  for(var i = 0; i<jsonDataArr.length; i++){
			    					  //console.log(jsonDataArr[i].id); 	    					  
			    					  pricingBoxHtml += '<li>';
			    					  pricingBoxHtml += '<div class="k-block k-block-div" style="position: relative;height: 310px;">';
			    					  var prodHeaderInfo = '';
			    					  if(jsonDataArr[i].productName != null && (jsonDataArr[i].productName =='One Adult w/ Kids' || jsonDataArr[i].productName =='Two Adults w/ Kids' || jsonDataArr[i].productName =='Three Adults w/ or w/o kids')){
			    						  if(jsonDataArr[i].productName =='One Adult w/ Kids'){
			    							  prodHeaderInfo = 'One Adult<br />w/ Kids';
			    						  }else if(jsonDataArr[i].productName =='Two Adults w/ Kids'){
			    							  prodHeaderInfo = 'Two Adults<br />w/ Kids';
			    						  }else{
			    							  prodHeaderInfo = 'Three Adults<br />w/ or w/o kids';
			    						  }
			    					  }else{
			    						  prodHeaderInfo = jsonDataArr[i].productName;
			    					  }
			    					  if(i % 2 == 0){
			    						  pricingBoxHtml += '<div class="k-header k-shadow k-header-custom-gray" >'+prodHeaderInfo+'</div>';
			    					  }else{
			    						  pricingBoxHtml += '<div class="k-header k-shadow k-header-custom-orange" >'+prodHeaderInfo+'</div>';
			    					  }
			    					  pricingBoxHtml += '<div>';
			    					 pricingBoxHtml += '<span style="font-size: x-large; font-family: cursive;">$ <div class="price-div">'+jsonDataArr[i].tierPrice+'</div></span>';
			    					  //pricingBoxHtml += '<span style="font-size: x-large; font-family: cursive;">$ <div class="price-div"> 0.0</div></span>';
			    					  pricingBoxHtml += '<div class="prod-id-div" style="display:none">'+jsonDataArr[i].id+'</div>';
			    					  pricingBoxHtml += '<div class="prod-name-div" style="display:none">'+jsonDataArr[i].productName+'</div>';
			    					  pricingBoxHtml += '<div class="prod-total-price-div" style="display:none">'+jsonDataArr[i].productPrice+'</div>';
			    					  pricingBoxHtml += '<div class="prod-tandc-div" style="display:none">'+jsonDataArr[i].tandc+'</div>';
			    					  pricingBoxHtml += '<div class="prod-autoPromoDiscount-div" style="display:none">'+jsonDataArr[i].autoPromoDiscount+'</div>';
			    					  pricingBoxHtml += '<div class="prod-type-div" style="display:none">'+jsonDataArr[i].productType+'</div>';
			    					  pricingBoxHtml += '<div class="prod-itemDetailsId-div" style="display:none">'+jsonDataArr[i].itemDetailsId+'</div>';
			    					  
			    					  
			    					  pricingBoxHtml += '<div class="product-description-div">'+jsonDataArr[i].productDescription+'</div>';
			    					  pricingBoxHtml += '<div class="product-location-div">'+locationDropdownlist.text()+'</div>';
			    					  pricingBoxHtml += '<div class="border-bottom-line"></div>';
			    					  pricingBoxHtml += '<div class="product-join-fee-text-div">One Time Join Fee</div>';
			    					 pricingBoxHtml += '<span style="font-size: small; font-family: cursive;">$ <div class="product-join-fee-text-val-div">'+jsonDataArr[i].joiningPrice+'</div></span>';	    					    					  
			    					  // pricingBoxHtml += '<span style="font-size: small; font-family: cursive;">$ <div class="product-join-fee-text-val-div">0.0</div></span>';
			    					  pricingBoxHtml += '<div>';
			    					  pricingBoxHtml += '<table border="0" style="text-align: left;" id="productPriceTable">';
			    					  pricingBoxHtml += '<tr>';
			    					 /*  pricingBoxHtml += '<td><input type="radio" name="paymentTypeSelect" class="paymentTypeSelectClass" value="AllArea"/></td>'; */
			    					  pricingBoxHtml += '<td width="65%" style="font-size: 11px;">All Branches</td>';
			    					  pricingBoxHtml += '<td width="30%" style="font-size: smaller;font-weight: bolder;">$ <span class="all-area-price-td">'+jsonDataArr[i].allAreaPrice+'</span></td>';
			    					  pricingBoxHtml += '</tr>';
			    					  /* pricingBoxHtml += '<tr>';
			    					  pricingBoxHtml += '<td><input type="radio" name="paymentTypeSelect" class="paymentTypeSelectClass" value="BayArea"/></td>';
			    					  pricingBoxHtml += '<td width="65%" style="font-size: 11px;">Bay Area</td>';
			    					  pricingBoxHtml += '<td width="30%" style="font-size: smaller;font-weight: bolder;">$ <span class="bay-area-price-td">'+jsonDataArr[i].bayAreaPrice+'</span></td>';
			    					  pricingBoxHtml += '</tr>';  */
			    					  pricingBoxHtml += '</table>'; 
			    					  pricingBoxHtml += '</div>'; 
			    						    					  
			    					  if(i % 2 == 0){
			    						  pricingBoxHtml += '<div class="k-button product-register-div" style="bottom: 8px; margin-right: 29px;position: absolute;right: 8px; text-shadow: none; background-color: #929292 !important; color: #ffffff !important; background-image:none !important; font-weight:bold !important;">Register</div>';
			    					  }else{
			    						  pricingBoxHtml += '<div class="k-button product-register-div" style="bottom: 8px; margin-right: 29px;position: absolute;right: 8px; text-shadow: none; background-color: #eb8120 !important; color: #ffffff !important; background-image:none !important; font-weight:bold !important;">Register</div>';
			    					  }
			    					  pricingBoxHtml += '</div>';
			    					  pricingBoxHtml += '</div>';
			    					  pricingBoxHtml += '</li>';
			    				  }
			    				  $("#panels").html(pricingBoxHtml);
			    				  $(".buttonNext").show();
							  }else {			  
								  $("#panels").html("No Product Information found for selected Location. Please select different Location.");
								  //pricingOption.select(0);
								  $(".buttonNext").hide();
							  }
							  $(".k-loading-mask").hide();
						  },
						  error: function( xhr,status,error ){		 
							  $("#tcloginErrorSpan-payment").css("display", "block");	
							  $( "#tcloginErrorSpan-payment" ).html("There was some error. Please try again later");							  		 
						  }
					});
		    			
		    	}else if(locationDropdownlist.text() == '--Select Location--'){  
		   			var kendoWindow = $("<div />").kendoWindow({
		   	        	title: "Error",
		   	        	resizable: false,
		   	        	modal: true,
		   	        	width:400
		   	        });   	
		     		kendoWindow.data("kendoWindow")
		   	         .content($("#terms-conditions-LocationBox").html())
		   	         .center().open();
		     			
		   	        kendoWindow
		   	        .find(".confirm-location-select")
		   	        .click(function() {
		   	        	if ($(this).hasClass("confirm-location-select")) {         		
		   	        		kendoWindow.data("kendoWindow").close();
		   	        	}
		   	        })
		   	        .end();
		   			//e.preventDefault();
		   	     	//pricingOption.select(0);
		    	}else{
		    		var kendoWindow = $("<div />").kendoWindow({
		   	        	title: "Error",
		   	        	resizable: false,
		   	        	modal: true,
		   	        	width:400
		   	        });   	
		     		kendoWindow.data("kendoWindow")
		   	         .content($("#terms-conditions-PricingBox").html())
		   	         .center().open();
		     			
		   	        kendoWindow
		   	        .find(".confirm-pricing-select")
		   	        .click(function() {
		   	        	if ($(this).hasClass("confirm-pricing-select")) {         		
		   	        		kendoWindow.data("kendoWindow").close();
		   	        	}
		   	        })
		   	        .end();
		   	     
		    	}
		    });
		    
		    
		    $(document).on('click', '.product-register-div', function(){
		    	//alert("click");
		    	/* console.log("header " + $(this).parent().parent().find( ".k-header" ).text());
		    	console.log("price-div " + $(this).parent().parent().find( ".price-div" ).text());
		    	console.log("product-description-div " +$(this).parent().parent().find( ".product-description-div" ).text());
		    	console.log("product-join-fee-text-val-div " + $(this).parent().parent().find( ".product-join-fee-text-val-div" ).text());  */
		    	selectedHeaderInfo = $(this).parent().parent().find( ".k-header" ).text();
		    	selectedTierPriceInfo = $(this).parent().parent().find( ".price-div" ).text();
		    	selectedProdDescInfo = $(this).parent().parent().find( ".product-description-div" ).text();
		    	selectedJoiningFeeInfo = $(this).parent().parent().find( ".product-join-fee-text-val-div" ).text();  
		    	
		    	selectedProdctId = $(this).parent().parent().find( ".prod-id-div" ).text();
		    	selectedProductName = $(this).parent().parent().find( ".prod-name-div" ).text();
		    	//selectedProductTotalPrice = $(this).parent().parent().find( ".prod-total-price-div" ).text();
		    	selectedProdTandc = $(this).parent().parent().find( ".prod-tandc-div" ).html();
		    	
		    	selectedAutoPromoDiscount = $(this).parent().parent().find( ".prod-autoPromoDiscount-div" ).html();
		    	$("#locationIdInput").attr("value", $("#location").kendoDropDownList().val()); 
		    	
		    	//$("input:radio[name=paymentTypeSelect]").change(function() {
		    	/* $(document).on('change', 'input:[name=paymentTypeSelect]', function(){
		    	
		    		if($("input:checkbox[name=paymentTypeSelect]").is(":checked")){
		    			$("input:checkbox[name=paymentTypeSelect]").prop('checked', false); 
		    		}
		    	    $(this).prop('checked', false); 
		    	    alert( $(this).val());
		    		selectedProductTotalPrice = parseFloat(selectedTierPriceInfo) + parseFloat(selectedJoiningFeeInfo);
		    		alert(selectedProductTotalPrice);
		    	});  */
		    	
		    	/* $(document).on('click', '.paymentTypeSelectClass', function() {
		    		if($("input:checkbox[name=paymentTypeSelect]").is(":checked")){
		    			$("input:checkbox[name=paymentTypeSelect]").prop('checked', false); 
		    		}
		    	    $(this).prop('checked', true); 
		    	    alert( $(this).val());
		    		selectedProductTotalPrice = parseFloat(selectedTierPriceInfo) + parseFloat(selectedJoiningFeeInfo);
		    		alert(selectedProductTotalPrice);
		    	}); */
		    	//if($("input:radio[name=paymentTypeSelect]").is(":checked")){
		    	if($(this).parent().parent().find( "input:radio[name=paymentTypeSelect]" ).is(":checked")){    	
		    		var paymentType = $(this).parent().parent().find( "input:radio[name=paymentTypeSelect]:checked" ).val();
		    		if(paymentType == 'AllArea'){
		    			var allAreaPrice = $(this).parent().parent().find( ".all-area-price-td" ).text();    		
		    			selectedProductTotalPrice = parseFloat(allAreaPrice) + parseFloat(selectedJoiningFeeInfo);       
		    			$("#sumTierPriceTd").html(allAreaPrice);
		    		}else if(paymentType == 'BayArea'){
		    			var bayAreaPrice = $(this).parent().parent().find( ".bay-area-price-td" ).text();    		
		    			selectedProductTotalPrice = parseFloat(bayAreaPrice) + parseFloat(selectedJoiningFeeInfo);    
		    			$("#sumTierPriceTd").html(bayAreaPrice);
		    		}    		
		    	} else{
					selectedProductTotalPrice = parseFloat(selectedTierPriceInfo) + parseFloat(selectedJoiningFeeInfo);  
					$("#sumTierPriceTd").html(selectedTierPriceInfo);
				}
		    	
		    	if(selectedAutoPromoDiscount != 0){
					  $("#sumAutoApplyTr").css("display","");			  
					  $("#sumEarlyBirdDiscount").html(selectedAutoPromoDiscount);
				  }else{
					  $("#sumAutoApplyTr").css("display","none");			 
					  $("#sumEarlyBirdDiscount").html("");
				  }
		    	
		    	
				 $("#sumPromoCodeTr").css("display","none");			  
				 $("#sumPromoCodeName").html("");
				 $("#sumPromoCodeVal").html("");		 
				
		    	//alert(selectedAutoPromoDiscount);
		    	selectedItemDetailsId = $(this).parent().parent().find( ".prod-itemDetailsId-div" ).html();
		    	
		    	$("#productIdTd").html(selectedProdctId);
		    	$("#productIdHidInput").attr("value", selectedProdctId);
		    	$("#productNameTd").html(selectedProductName);
		    	$("#productDescriptionTd").html(selectedProdDescInfo);
		    	$("#productTypeTd").html($(this).parent().parent().find( ".prod-type-div" ).text());    	
		    	
		    	$("#productPriceTd").html(selectedProductTotalPrice);
		    	$("#paymentAmountSpan").html(selectedProductTotalPrice);
		    	$("#paymentTotalPriceTd").html(selectedProductTotalPrice);	
		    	$("#sumTotalPriceTd").html(selectedProductTotalPrice);
		    	
		    	$(".termsDiv").html(selectedProdTandc);
		    	$("#userTC").attr("value", selectedProdTandc);
		    	
		    	$("#paymentJoinFeeTd").html(selectedJoiningFeeInfo);
		    	$("#sumJoinFeeTd").html(selectedJoiningFeeInfo);		
		    	
		    	
		    		    	
		    	
		    	var oneAdultWithKidsHtml = getKidsInfoHeader() + getKidsInfoHtml("1");
		    	var twoAdultsHtml = getSecondUserInfoHeader() + getSecondUserInfoData("1");
		    	var twoAdultsWithKidsHtml = getSecondUserInfoHeader() + getSecondUserInfoData("1") + getKidsInfoHeader() + getKidsInfoHtml("2");
		    	var threeAdultsWithKidsHtml = getSecondUserInfoHeader() + getSecondUserInfoData("1") + getThirdUserInfoHeader() + getThirdUserInfoData("2") + getKidsInfoHeader() + getKidsInfoHtml("3");
		    	
		    	if(selectedHeaderInfo == 'One Adultw/ Kids'){
		    		$('#familyInfoFormTable').css("display" , "inline-table");
		    		$('#familyInfoFormTable').html(oneAdultWithKidsHtml);
		    		$('#familyInfoFormTable').find(".dob-class").kendoDatePicker();
		    		secKidCount = 2;
		    	}else if(selectedHeaderInfo == 'Two Adults' ){
		    		$('#familyInfoFormTable').css("display" , "inline-table");
		    		$('#familyInfoFormTable').html(twoAdultsHtml);    		
		    		$('#familyInfoFormTable').find(".dob-class").kendoDatePicker();   		
		    	}else if(selectedHeaderInfo == 'Two Adultsw/ Kids'){
		    		$('#familyInfoFormTable').css("display" , "inline-table");
		    		$('#familyInfoFormTable').html(twoAdultsWithKidsHtml);
		    		$('#familyInfoFormTable').find(".dob-class").kendoDatePicker();
		    		
		    		secKidCount = 3;
		    	}else if(selectedHeaderInfo == 'Three Adultsw/ or w/o kids'){
		    		$('#familyInfoFormTable').css("display" , "inline-table");
		    		$('#familyInfoFormTable').html(threeAdultsWithKidsHtml);
		    		$('#familyInfoFormTable').find(".dob-class").kendoDatePicker();
		    		
		    		secKidCount = 4;
		    	}else{
		    		$('#familyInfoFormTable').css("display" , "none");
		    	}    
		    	$(".phone-number").mask("(999) 999-9999");    	
		    	$("#wizard"). smartWizard("fixHeight");
		    	$('#wizard').smartWizard('goToStep',2);
		    	
		    });
		    	
		   
		       	
  $('#wizard').smartWizard({
		// Properties
		selected: 0,  // Selected Step, 0 = first step   
		keyNavigation: false, // Enable/Disable key navigation(left and right keys are used if enabled)
		enableAllSteps: false,  // Enable/Disable all steps on first load
		transitionEffect: 'slideleft', // Effect on navigation, none/fade/slide/slideleft
		contentURL:null, // specifying content url enables ajax content loading
		contentURLData:null, // override ajax query parameters
		contentCache:true, // cache step contents, if false content is fetched always from ajax url
		cycleSteps: false, // cycle step navigation
		enableFinishButton: false, // makes finish button enabled always
		hideButtonsOnDisabled: true, // when the previous/next/finish buttons are disabled, hide them instead
		errorSteps:[],    // array of step numbers to highlighting as error steps
		labelNext:'Next', // label for Next button
		labelPrevious:'Previous', // label for Previous button
		labelFinish:'Register',  // label for Finish button        
		noForwardJumping:false,
		ajaxType: 'POST',
		// Events
		onLeaveStep: nextClick, // triggers when leaving a step
		onShowStep: null,  // triggers when showing a step
		onFinish: registerContact,  // triggers when Finish button is clicked
		includeFinishButton : true   // Add the finish button
	});
  
  
  var validator2 = $("#tcForm").validate({
		debug: true,
		errorElement: "em",			
		errorPlacement: function(error, element) {			
			element.addClass("inputErrorr");
			error.appendTo(element.parent("td").next("td"));
			element.parent("td").next("td").addClass("textMsgError");			
		},
		success: function(label, element) {			
			var $element = $(element);
			var $label = $(label);
	       	$element.removeClass("inputErrorr");
			$element.removeClass("error");
			$element.addClass("success");
			
			$label.removeClass("textMsgError");
			$label.removeClass("error");
		//	$label.addClass("success");
			//$("#wizard"). smartWizard("fixHeight");
			
		},
		rules: {
			"tcname": {
				required: true,
				minlength:2 ,
				maxlength: 40,
				check_fName_lName : true				
			}
		},
		messages: {
			"tcname": {				
				required: "Please enter your Full Name",
				minlength: "Full Name must consist of at least 2 characters",
				maxlength: "Full Name can not be more than 40 characters",
				check_fName_lName : "Please enter the primary contact's First Name and Last Name."
			}
		}
	});	
  
  
  $.validator.addMethod("check_fName_lName", function(value, element) {
		var firstName = $("#firstName").val();
		var lastName = $("#lastName").val();
		var fullName = firstName + " " + lastName;
		var tcname = $("#tcname").val();
		if(tcname != fullName){
			return false;
		} else{
			return true;
		}
		
	}, "Please enter the primary contact's First Name and Last Name.");
	
  
  
  var url = "isEmailExists";
	var validator1 = $("#becomeTPMemberRegister").validate({
		debug: true,
		errorElement: "em",			
		errorPlacement: function(error, element) {
			$element = $(element);			
			if($element.attr("id") == "dob"){
				
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
			"userLst[0].personFirstName": {
				required: true,
				minlength: 2,
				maxlength: 20
			},
			"userLst[0].personLastName": {
				required: true,
				minlength: 2,
				maxlength: 20
			},
			"userLst[0].primaryEmailAddress" : {
				required: true,
				email: true,
				remote: {
                    url: url,  
                    type:"GET"                    	
                 }
			},
			"userLst[0].password": {
				required: true,
				minlength: 5
			},
			"userLst[0].confirmPassword": {
				required: true,
				minlength: 5,
				equalTo: "#password"
			},
			"userLst[0].dateOfBirth": { 
				required: true,
				check_date_of_birth: true
			},
			"postalCode" : {
				required: true,
				number: true,
				minlength: 5
			},
			"userLst[0].primaryFormattedPhoneNumber" : "required",			
			"addressLine1" : "required",			
			"city" : "required",
			"state" : "required"
			
		},
		messages: {
			"userLst[0].personFirstName": {				
				required: "Please enter your First Name",
				minlength: "First Name must consist of at least 2 characters"
			},
			
			"userLst[0].personLastName": {				
				required: "Please enter your First Name",
				minlength: "Last Name must consist of at least 2 characters"
			},
			"userLst[0].primaryEmailAddress" : {
				required: "Please enter your email address",
				email : "Please enter valid email address",
				remote: "You appear to be associated with a customer account already.  Please speak with the primary contact for that account"
				/* ,
				check_Email_valid : "You appear to be associated with a customer account already.  Please speak with the primary contact for that account" */
			},
			"userLst[0].primaryFormattedPhoneNumber" : "Please enter your Phone Number",			
			"userLst[0].password": {
				required: "Please provide a password",
				minlength: "Your password must be at least 5 characters long"
			},
			"userLst[0].confirmPassword": {
				required: "Please provide a password",
				minlength: "Your password must be at least 5 characters long",
				equalTo: "Please enter the same password as above"
			},
			"userLst[0].dateOfBirth": { 
				required: "Please enter your Date of Birth",
				check_date_of_birth: "Age should be more than 18 Years"
				
			},
			"postalCode" : {
				required: "Please enter Postal Code",
				number: "Please enter numbers only",
				minlength: "Postal Code must consist of at least 5 characters"
			},
			"addressLine1" : "Please enter address line 1",			
			"city" : "Please enter yout City",
			"state" : "Please enter your State",
			agree: "Please accept our policy"
		}
	});	
	
	
	$.validator.addMethod("check_date_of_birth", function(value, element) {

	    /* var day = $("#dob_day").val();
	    var month = $("#dob_month").val();
	    var year = $("#dob_year").val(); */
	    //11/18/2014
	    
		var dobMonth = $("#dobMonth").val();
		/* var dobDate = $("#dobDate").val();
		var dobYear = $("#dobYear").val();
		
		var dob = new Date(dobYear,dobMonth,dobDate);
		var today = new Date();
		
		if(dob >= today){
			
			$("#dop-er").css("color", "red");
			$("#dop-er").html("Please enter valid date of birth");
			$("#dop-er").show();
			return;
		}else{
			$("#dop-er").hide();
		} */
	    
	    
	    
	    var inpDate = $("#dob").val();
	    var day;
    	var month;
    	var year;
    	var age =  18;
	    if(inpDate != null){
	    	var dateArr = inpDate.split("/");	    	
	    	month = dateArr[0];
	    	day = dateArr[1];
	    	year = dateArr[2];
	    }

	    var mydate = new Date();
	    mydate.setFullYear(year, month-1, day);
//alert(mydate.setFullYear(year, month-1, day));
	    var currdate = new Date();
	    currdate.setFullYear(currdate.getFullYear() - age);
//alert(currdate > mydate);

	    return currdate > mydate;

	}, "You must be at least 18 years of age.");
  
  
	var validator3 = $("#loginForm").validate({
		debug: true,
		errorElement: "em",			
		errorPlacement: function(error, element) {
			$element = $(element);	
			element.addClass("inputErrorr");
			error.appendTo(element.parent("td").next("td"));
			element.parent("td").next("td").addClass("textMsgError");
			$element.parent("td").next("td").css("color","red");
		},
		success: function(label, element) {			
			var $element = $(element);
			var $label = $(label);
	       	$element.removeClass("inputErrorr");
			$element.removeClass("error");
			$element.addClass("success");
			
			$label.removeClass("textMsgError");
			$label.removeClass("error");
			$label.addClass("success");			
		},
		rules: {			
			"username" : {
				required: true,
				email: true				
			},
			"password": {
				required: true,
				minlength: 5
			}			
		},
		messages: {			
			"username" : {
				required: "Please enter your email address",
				email : "Please enter valid email address"			
			},
						
			"password": {
				required: "Please provide a password",
				minlength: "Your password must be at least 5 characters long"
			}
		}
	});	

  
  function nextClick(obj, context){	
		//$("#wizard"). smartWizard("fixHeight");	
		//$("#paymentTotalPriceTd").html($("#paymentAmountSpan").text());
		//var pricingOption = $("#pricingOption").data("kendoDropDownList");   
	    var locationDropdownlist = $("#location").data("kendoDropDownList");
	   // var paymentFreq2 = $("#paymentFrequencySelect").data("kendoDropDownList");
	   /*  if(paymentFreq2.text() == 'Annual'){
			$("#endDateDiv").css("display","inline");
			if(selectedProductTotalPrice != null){    			
				$("#sumTotalPriceTd").html(parseFloat(selectedProductTotalPrice) * 12);
			} 	
		}else{
			$("#endDateDiv").css("display","none");    		
			$("#sumTotalPriceTd").html(selectedProductTotalPrice);		
		}
	    var finalAmount = 0;
	    if(context.toStep == 5 ){
	    	if($("#sumEarlyBirdDiscount").text() != '' && $("#sumPromoCodeVal").text() != ''){
	    		finalAmount = parseFloat($("#sumTotalPriceTd").text()) - parseFloat($("#sumEarlyBirdDiscount").text()) - parseFloat($("#sumPromoCodeVal").text());
	    	}else if($("#sumEarlyBirdDiscount").text() != ''){
	    		finalAmount = parseFloat($("#sumTotalPriceTd").text()) - parseFloat($("#sumEarlyBirdDiscount").text());
	    	}else if($("#sumPromoCodeVal").text() != ''){
	    		finalAmount = parseFloat($("#sumTotalPriceTd").text()) - parseFloat($("#sumPromoCodeVal").text());
	    	}else {
	    		finalAmount = parseFloat($("#sumTotalPriceTd").text());
	    	}
	    	
	    }
	    $("#sumFinalAmountTd").html(finalAmount); */
	    	
		var paymentFreq2 = $("#paymentFrequencySelect").data("kendoDropDownList");
	   // $("#paymentFrequencyTd").html(paymentFreq2.text());
	 //   $("#sumPaymentFreqTd").html(paymentFreq2.text());
		$(".stepContainer").height($(".stepContainer").find("div:not(:hidden)").height());
		
		if( context.fromStep == 2){	
			var dobMonthForm = $("#dobMonthForm").val();
			var dobDateForm = $("#dobDateForm").val();
			var dobYearForm = $("#dobYearForm").val();
					
			$("#dob").val(dobMonthForm+"/"+dobDateForm+"/"+dobYearForm);
		
			$('#firstNameTd').html($('#firstName').val());
			$('#lastNameTd').html($('#lastName').val());
			$('#dobTd').html($('#dob').val());
			$('#phoneNumberTd').html($('#phoneNumber').val());
			$('#emailTd').html($('#email').val());
			if($('#genderM').is(':checked')) { 
				$('#genderTd').html($('#genderM').val()); 				
			}else if($('#genderF').is(':checked')) { 
				$('#genderTd').html($('#genderF').val()); 				
			}
			
			$('#addressLine1Td').html($('#addressLine1').val());
			$('#addressLine2Td').html($('#addressLine2').val());
			$('#cityTd').html($('#city').val());
			$('#stateTd').html($('#state').val());
			$('#postalCodeTd').html($('#postalCode').val());	
			if(selectedHeaderInfo == 'One Adultw/ Kids' ){
				populateKidsSummaryInformation();
			} else if(selectedHeaderInfo == 'Two Adults' ){
				$('#secMemFirstNameTd').html($('#secFirstName').val());
				
				$('#secMemLastNameTd').html($('#secLastName').val());
				$('#secMemDobTd').html($('#secDOB').val());
				$('#secMemPhoneNumberTd').html($('#secPhoneNumber').val());
				$('#secMemEmailTd').html($('#secEmail').val());
				if($('#secGenderM').is(':checked')) { 
					$('#secGenderTd').html($('#secGenderM').val()); 				
				}else if($('#secGenderF').is(':checked')) { 
					$('#secGenderTd').html($('#secGenderF').val()); 				
				}			
				$('#secMemberTr').css("display", "inline");
				
				$('#thirdMemFirstNameTd').html("");
				$('#thirdMemLastNameTd').html("");
				$('#thirdMemDobTd').html("");
				$('#thirdMemPhoneNumberTd').html("");
				$('#thirdMemEmailTd').html("");
				
				if($('#thirdGenderM').is(':checked')) { 
					$('#thirdGenderTd').html(""); 				
				}else if($('#thirdGenderF').is(':checked')) { 
					$('#thirdGenderTd').html(""); 				
				}
				$('#thirdMemberTr').css("display", "none");
			} else if(selectedHeaderInfo == 'Two Adultsw/ Kids'){
				$('#secMemFirstNameTd').html($('#secFirstName').val());
				$('#secMemLastNameTd').html($('#secLastName').val());
				$('#secMemDobTd').html($('#secDOB').val());
				$('#secMemPhoneNumberTd').html($('#secPhoneNumber').val());
				$('#secMemEmailTd').html($('#secEmail').val());
				if($('#secGenderM').is(':checked')) { 
					$('#secGenderTd').html($('#secGenderM').val()); 				
				}else if($('#secGenderF').is(':checked')) { 
					$('#secGenderTd').html($('#secGenderF').val()); 				
				}			
				$('#secMemberTr').css("display", "inline");
				
				$('#thirdMemFirstNameTd').html("");
				$('#thirdMemLastNameTd').html("");
				$('#thirdMemDobTd').html("");
				$('#thirdMemPhoneNumberTd').html("");
				$('#thirdMemEmailTd').html("");
				
				if($('#thirdGenderM').is(':checked')) { 
					$('#thirdGenderTd').html(""); 				
				}else if($('#thirdGenderF').is(':checked')) { 
					$('#thirdGenderTd').html(""); 				
				}
				$('#thirdMemberTr').css("display", "none");
				populateKidsSummaryInformation();
				
			} else if(selectedHeaderInfo == 'Three Adultsw/ or w/o kids'){
				$('#secMemFirstNameTd').html($('#secFirstName').val());
				$('#secMemLastNameTd').html($('#secLastName').val());
				$('#secMemDobTd').html($('#secDOB').val());
				$('#secMemPhoneNumberTd').html($('#secPhoneNumber').val());
				$('#secMemEmailTd').html($('#secEmail').val());
				if($('#secGenderM').is(':checked')) { 
					$('#secGenderTd').html($('#secGenderM').val()); 				
				}else if($('#secGenderF').is(':checked')) { 
					$('#secGenderTd').html($('#secGenderF').val()); 				
				}			
				$('#secMemberTr').css("display", "inline");
				
				$('#thirdMemFirstNameTd').html($('#thirdFirstName').val());
				$('#thirdMemLastNameTd').html($('#thirdLastName').val());
				$('#thirdMemDobTd').html($('#thirdDOB').val());
				$('#thirdMemPhoneNumberTd').html($('#thirdPhoneNumber').val());
				$('#thirdMemEmailTd').html($('#thirdEmail').val());
				
				if($('#thirdGenderM').is(':checked')) { 
					$('#thirdGenderTd').html($('#thirdGenderM').val()); 				
				}else if($('#thirdGenderF').is(':checked')) { 
					$('#thirdGenderTd').html($('#thirdGenderF').val()); 				
				}
				$('#thirdMemberTr').css("display", "inline");
				populateKidsSummaryInformation();
				
			}else{
				$('#secMemFirstNameTd').html("");
				$('#secMemLastNameTd').html("");
				$('#secMemDobTd').html("");
				$('#secMemPhoneNumberTd').html("");
				$('#secMemEmailTd').html("");
				if($('#secGenderM').is(':checked')) { 
					$('#secGenderTd').html(""); 				
				}else if($('#secGenderF').is(':checked')) { 
					$('#secGenderTd').html(""); 				
				}			
				
				
				$('#thirdMemFirstNameTd').html("");
				$('#thirdMemLastNameTd').html("");
				$('#thirdMemDobTd').html("");
				$('#thirdMemPhoneNumberTd').html("");
				$('#thirdMemEmailTd').html("");
				
				if($('#thirdGenderM').is(':checked')) { 
					$('#thirdGenderTd').html(""); 				
				}else if($('#thirdGenderF').is(':checked')) { 
					$('#thirdGenderTd').html(""); 				
				}
				
				
				$('#secMemberTr').css("display", "none");
				$('#thirdMemberTr').css("display", "none");
			}
		}
		if( context.fromStep == 1 && context.toStep == 2 ){	
			
			if(locationDropdownlist.text() == '--Select Location--'){  
	   			var kendoWindow = $("<div />").kendoWindow({
	   	        	title: "Error",
	   	        	resizable: false,
	   	        	modal: true,
	   	        	width:400
	   	        });   	
	     		kendoWindow.data("kendoWindow")
	   	         .content($("#terms-conditions-LocationBox").html())
	   	         .center().open();
	     			
	   	        kendoWindow
	   	        .find(".confirm-location-select")
	   	        .click(function() {
	   	        	if ($(this).hasClass("confirm-location-select")) {         		
	   	        		kendoWindow.data("kendoWindow").close();
	   	        	}
	   	        })
	   	        .end();
	   			//e.preventDefault();
	   	     	//pricingOption.select(0);
	   	     	$('#wizard').smartWizard('setError',{stepnum:1,iserror:true});
		       //$("#wizard"). smartWizard("fixHeight");	
			   return false;
	    	} else{
	         	$('#wizard').smartWizard('setError',{stepnum:1,iserror:false});	
	         	$("#wizard"). smartWizard("fixHeight");	
	    		return true;
	        } 
	    }else if( context.fromStep == 2 && context.toStep == 3 ){
	    	if(signInSelected == "true"){
	    		if(!$("#loginForm").valid()) {
	    			$('#wizard').smartWizard('setError',{stepnum:2,iserror:true});		
	    			return false;
	    		} else if(isLoggedIn == 'false'){
	    			$('#wizard').smartWizard('setError',{stepnum:2,iserror:true});	
	    			$("#statusBlock").css("display", "block");	
			  		$("#tcloginErrorSpan").css("display", "block");	
					$( "#tcloginErrorSpan" ).html("Please login");
	    			return false;
	    		} else {
	    			$("#statusBlock").css("display", "none");	
	   			 	$("#tcloginErrorSpan").css("display", "none");	
	   			 	$( "#tcloginErrorSpan" ).html("");
	   			 
	    			$('#wizard').smartWizard('setError',{stepnum:2,iserror:false});	
	    			var dobMonthForm = $("#dobMonthForm").val();
	    			var dobDateForm = $("#dobDateForm").val();
	    			var dobYearForm = $("#dobYearForm").val();
	    					
	    			$("#dob").val(dobMonthForm+"/"+dobDateForm+"/"+dobYearForm);
	    			$('#firstNameTd').html($('#firstName').val());
	    			$('#lastNameTd').html($('#lastName').val());
	    			$('#dobTd').html($('#dob').val());
	    			$('#phoneNumberTd').html($('#phoneNumber').val());
	    			$('#emailTd').html($('#email').val());
	    			
	    			$('#addressLine1Td').html($('#addressLine1').val());
	    			$('#addressLine2Td').html($('#addressLine2').val());
	    			$('#cityTd').html($('#city').val());
	    			$('#stateTd').html($('#state').val());
	    			$('#postalCodeTd').html($('#postalCode').val());	
	    			//$("#wizard"). smartWizard("fixHeight");	
	    			return true;
	    		}
	    	} else{
	    		if(!$("#becomeTPMemberRegister").valid()){
	    			$('#wizard').smartWizard('setError',{stepnum:2,iserror:true});	
	    			//$("#wizard"). smartWizard("fixHeight");	
	    			return false;
	    		}else{
	    			$('#wizard').smartWizard('setError',{stepnum:2,iserror:false});	
	    			$('#firstNameTd').html($('#firstName').val());
	    			$('#lastNameTd').html($('#lastName').val());
	    			var dobMonthForm = $("#dobMonthForm").val();
	    			var dobDateForm = $("#dobDateForm").val();
	    			var dobYearForm = $("#dobYearForm").val();
	    					
	    			$("#dob").val(dobMonthForm+"/"+dobDateForm+"/"+dobYearForm);
	    			$('#dobTd').html($('#dob').val());
	    			$('#phoneNumberTd').html($('#phoneNumber').val());
	    			$('#emailTd').html($('#email').val());
	    			
	    			$('#addressLine1Td').html($('#addressLine1').val());
	    			$('#addressLine2Td').html($('#addressLine2').val());
	    			$('#cityTd').html($('#city').val());
	    			$('#stateTd').html($('#state').val());
	    			$('#postalCodeTd').html($('#postalCode').val());	
	    			
	    			/* var paymentMethodHtml = '';
	    			paymentMethodHtml += '<option value="0" >--Select Payment Method--</option>';
	    			paymentMethodHtml += '<option value="New" selected="selected">Add New Card</option>';
	    			paymentMethodHtml += '<option value="NewBank" selected="selected">Add New Bank Info</option>';
	    			$("#paymentInfoRadio").html(paymentMethodHtml);
	    			$("#paymentInfoRadio").kendoDropDownList();  
	    			$("#addcard").css("display","inline"); */
	    			//$("#wizard"). smartWizard("fixHeight");	
	    			return true;
	    		}
	    	}
			 
					
		}else if( context.fromStep == 3 && context.toStep == 4 ){	
			 if(!$("input[name='chkTermsAndCond']:checked").val()){ 
					$("#tcForm").valid();
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
				        $('#wizard').smartWizard('setError',{stepnum:3,iserror:true});		
				       // $("#wizard"). smartWizard("fixHeight");	
		     	return false;
		     }else if(!$("#tcForm").valid()){
		 		 $('#wizard').smartWizard('setError',{stepnum:3,iserror:true});
		 		//$("#wizard"). smartWizard("fixHeight");	
				 return false;
		 	 }else{
		    	 $('#wizard').smartWizard('setError',{stepnum:3,iserror:false});	
		    	 //$("#wizard"). smartWizard("fixHeight");	
		    	 return true;
		     }
		}else {	
			//$("#wizard"). smartWizard("fixHeight");	
			return true;
		}
		
	}
  
  
  $(document).on('click', '.add-child-lnk', function(){
  	var count = secKidCount;
  	
  	$(document).find('.secondaryKidsInfoForm').each(function(i, obj) {				
  		count = count +1;				
		});    
  	var secondaryKidInfo = getSecondaryKidsInfoHtml(count);
  	$('#familyInfoFormTable').css("display" , "inline-table");
		$('#familyInfoFormTable').append(secondaryKidInfo);
		$(".stepContainer").height($(".stepContainer").find("div:not(:hidden)").height());
		$('#familyInfoFormTable').find(".dob-class").each(function() {		    
		    if(!$( this ).hasClass( "k-datepicker" )){
		    	$( this ).kendoDatePicker();
			}
		 });
		$('#familyInfoFormTable').find(".dob-class").css("width", "205px");	
		//$('.dob-class').kendoDatePicker();
		//$("#wizard").smartWizard("fixHeight");
  });
  
  $(document).on('click', '.remove-child-lnk', function(){
  	$(this).parent().parent().parent().parent().parent().parent().remove();
  /* 
  	$(this).parent().parent().next().remove();
  	$(this).parent().parent().prev().remove();
  	$(this).parent().parent().remove(); */
  	$(".stepContainer").height($(".stepContainer").find("div:not(:hidden)").height());
		//$("#wizard").smartWizard("fixHeight");
  });
  
  
  $('body').on( "click", "input.termsAndCondition", function() { 	 
      if($('input:radio[name=type]:checked')){        	
      	var $termsVar = $(this).parent().parent().find('td');
      	$termsVar.each(function(index){
      		if(index && index == '1'){
      			$("#productIdTd").html($(this).html());
      			$("#productIdHidInput").attr("value", $(this).html());
      		}
				if(index && index == '2'){
					$("#productNameTd").html($(this).html());     			
				}
				if(index && index == '3'){
					$("#productDescriptionTd").html($(this).html());	
				}
				if(index && index == '4'){
					$("#productTypeTd").html($(this).html());	
				}
				if(index && index == '5'){
					$("#productDurationTd").html($(this).html());	
				}
				if(index && index == '6'){
					$("#productPriceTd").html("$ "+$(this).html());
					$("#paymentAmountSpan").html($(this).html());
					$("#paymentTotalPriceTd").html("$ "+$(this).html());	
					$("#sumTotalPriceTd").html("$ "+$(this).html());
				}				
				if(index && index == '7'){				
					$(".termsDiv").html($(this).text());
					$("#userTC").attr("value", $(this).text());
				}
				if(index && index == '9'){				
					$("#paymentJoinFeeTd").html("$ "+$(this).text());
					$("#sumJoinFeeTd").html("$ "+$(this).text());					
				}
				if(index && index == '10'){				
					$("#paymentTierTd").html("$ "+$(this).text());
					$("#sumTierPriceTd").html("$ "+$(this).text());	
				}
				if(index && index == '8'){				
					var priceRuleStr = $(this).text();
					if(priceRuleStr != null){
						var priceRuleArr= priceRuleStr.split(";;");
						for(var i = 0; i<priceRuleArr.length; i++){
							if(priceRuleArr[i] != null && priceRuleArr[i] != ""){
								var ruleArray = priceRuleArr[i].split(":");
								var ruleName = ruleArray[0];
								var rulePrice = ruleArray[1];	
								if(ruleName == 'Join Fee'){
									$("#paymentJoinFeeTd").html("$ "+rulePrice);
									$("#sumJoinFeeTd").html("$ "+rulePrice);									
								}
								if(ruleName == 'Tier 1' || ruleName == 'Tier 2' || ruleName == 'Tier 3'){
									$("#paymentTierTd").html("$ "+rulePrice);
									$("#sumTierPriceTd").html("$ "+rulePrice);									
								}						
							}
						}
					}
				}
				
      	    //console.log($(this).html())
      	});
      }
	 });
	 
	 $('body').on( "click", "#memberSignInSpan", function() { 	 
		 signInSelected = "true";
		 $('#loginDiv').css("display","inline");
		 $('#registerDiv').css("display","none");
	 });
	 
	 $('body').on( "click", "#newMemberSpan", function() { 	 
		 signInSelected = "false";
		 $('#loginDiv').css("display","none");
		 $('#registerDiv').css("display","inline");
	 });
	 
	 
	
	
	//$("#wizard"). smartWizard("fixHeight");
	
	
	
	function getSecondUserInfoHeader(){
		var secondUserInfoTableHtml = '';
		secondUserInfoTableHtml += '<tr>';
		secondUserInfoTableHtml += '<td colspan="2"><h2 style="margin-top: 5px; margin-bottom : 5px;"><span>SECONDARY MEMBER</span></h2> </td>';
		secondUserInfoTableHtml += '<td >&nbsp;</td>';
		secondUserInfoTableHtml += '<td >&nbsp;</td>';
		secondUserInfoTableHtml += '<td >&nbsp;</td>';
		secondUserInfoTableHtml += '</tr>';
		return secondUserInfoTableHtml;
	}

	function getThirdUserInfoHeader(){
		var secondUserInfoTableHtml = '';
		secondUserInfoTableHtml += '<tr>';
		secondUserInfoTableHtml += '<td colspan="2"><h2 style="margin-top: 5px; margin-bottom : 5px;"><span>THIRD MEMBER</span></h2> </td>';
		secondUserInfoTableHtml += '<td >&nbsp;</td>';
		secondUserInfoTableHtml += '<td >&nbsp;</td>';
		secondUserInfoTableHtml += '<td >&nbsp;</td>';
		secondUserInfoTableHtml += '</tr>';
		return secondUserInfoTableHtml;
	}

	function getSecondUserInfoData(userNo){
		var secondUserInfoTableHtml = '';
		secondUserInfoTableHtml += '<tr>';
		secondUserInfoTableHtml += '<td><input type="text" class="form-control" value="" title="Please enter yout First Name" name="userLst['+userNo+'].personFirstName" id="secFirstName" maxlength="50" placeholder="First Name"  /></td>';
		secondUserInfoTableHtml += '<td>&nbsp;</td>';
		secondUserInfoTableHtml += '<td><input type="text" class="form-control" value="" title="Please enter yout Last Name" name="userLst['+userNo+'].personLastName" id="secLastName" maxlength="50" placeholder="Last Name" /></td>';
		secondUserInfoTableHtml += '<td>&nbsp;</td>';
		secondUserInfoTableHtml += '</tr>';
		
		secondUserInfoTableHtml += '<tr>';
		secondUserInfoTableHtml += '<td style="padding-bottom: 8px;"><input type="text" class="dob-class"  title="Please enter your Date of Birth" name="userLst['+userNo+'].dateOfBirth" id="secDOB" maxlength="50" placeholder="Date of Birth"  style="width: 205px; height: 30px; margin: 0; padding: 0;" /></td>';
		secondUserInfoTableHtml += '<td id="dop-er"></td>';
		secondUserInfoTableHtml += '<td><input type="text" class="form-control phone-number" title="Please enter your Phone Number" name="userLst['+userNo+'].primaryFormattedPhoneNumber" id="secPhoneNumber" maxlength="50" placeholder="Phone Number" /></td>';
		secondUserInfoTableHtml += '<td>&nbsp;</td>';
		secondUserInfoTableHtml += '</tr>';	
		
		secondUserInfoTableHtml += '<tr>';
		secondUserInfoTableHtml += '<td><input type="text" class="form-control"  name="userLst['+userNo+'].primaryEmailAddress" id="secEmail"  maxlength="50" placeholder="Email"  /></td>';
		secondUserInfoTableHtml += '<td id="dop-er"></td>';

		secondUserInfoTableHtml += '<td>';
		secondUserInfoTableHtml += '<span>';
		secondUserInfoTableHtml += '<span><span><input id="secGenderM" name="userLst['+userNo+'].gender" class="form-control" style="width: 15px;" type="radio" value="Male"></span><span style="margin-left : 5px;">Male</span></span>';
		secondUserInfoTableHtml += '<span><span><input id="secGenderF" name="userLst['+userNo+'].gender" class="form-control" style="width : 15px;" type="radio" value="Female"></span><span style="margin-left : 5px;">Female</span></span>';
		secondUserInfoTableHtml += '</span>';
		secondUserInfoTableHtml += '</td>';	
		
		secondUserInfoTableHtml += '<td>&nbsp;</td>';
		secondUserInfoTableHtml += '</tr>';
		
		secondUserInfoTableHtml += '<tr style="display : none;">';
		secondUserInfoTableHtml += '<td><input style="display:none;" name="userLst['+userNo+'].isAdult" value="true"/></td>';
		secondUserInfoTableHtml += '<td>&nbsp;</td>';
		secondUserInfoTableHtml += '<td><input style="display:none;" name="userLst['+userNo+'].usrSequence" value="'+userNo+'" /></td>';
		secondUserInfoTableHtml += '<td>&nbsp;</td>';
		secondUserInfoTableHtml += '</tr>';
		
		return secondUserInfoTableHtml;
	}

	
	
	function getThirdUserInfoData(userNo){
		var secondUserInfoTableHtml = '';
		secondUserInfoTableHtml += '<tr>';
		secondUserInfoTableHtml += '<td><input type="text" class="form-control" value="" title="Please enter yout First Name" name="userLst['+userNo+'].personFirstName" id="thirdFirstName" maxlength="50" placeholder="First Name"  /></td>';
		secondUserInfoTableHtml += '<td>&nbsp;</td>';
		secondUserInfoTableHtml += '<td><input type="text" class="form-control" value="" title="Please enter yout Last Name" name="userLst['+userNo+'].personLastName" id="thirdLastName" maxlength="50" placeholder="Last Name" /></td>';
		secondUserInfoTableHtml += '<td>&nbsp;</td>';
		secondUserInfoTableHtml += '</tr>';
		
		secondUserInfoTableHtml += '<tr>';
		secondUserInfoTableHtml += '<td style="padding-bottom: 8px;"><input type="text" class="dob-class"  title="Please enter your Date of Birth" name="userLst['+userNo+'].dateOfBirth" id="thirdDOB" maxlength="50" placeholder="Date of Birth"  style="width: 205px; height: 30px; margin: 0; padding: 0;" /></td>';
		secondUserInfoTableHtml += '<td id="dop-er"></td>';
		secondUserInfoTableHtml += '<td><input type="text" class="form-control phone-number" title="Please enter your Phone Number" name="userLst['+userNo+'].primaryFormattedPhoneNumber" id="thirdPhoneNumber" maxlength="50" placeholder="Phone Number" /></td>';
		secondUserInfoTableHtml += '<td>&nbsp;</td>';
		secondUserInfoTableHtml += '</tr>';	
		
		secondUserInfoTableHtml += '<tr>';
		secondUserInfoTableHtml += '<td><input type="text" class="form-control"  name="userLst['+userNo+'].primaryEmailAddress" id="thirdEmail"  maxlength="50" placeholder="Email"  /></td>';
		secondUserInfoTableHtml += '<td id="dop-er"></td>';

		secondUserInfoTableHtml += '<td>';
		secondUserInfoTableHtml += '<span>';
		secondUserInfoTableHtml += '<span><span><input id="thirdGenderM" name="userLst['+userNo+'].gender" class="form-control" style="width: 15px;" type="radio" value="Male"></span><span style="margin-left : 5px;">Male</span></span>';
		secondUserInfoTableHtml += '<span><span><input id="thirdGenderF" name="userLst['+userNo+'].gender" class="form-control" style="width : 15px;" type="radio" value="Female"></span><span style="margin-left : 5px;">Female</span></span>';
		secondUserInfoTableHtml += '</span>';
		secondUserInfoTableHtml += '</td>';	
		
		secondUserInfoTableHtml += '<td>&nbsp;</td>';
		secondUserInfoTableHtml += '</tr>';
		
		secondUserInfoTableHtml += '<tr style="display : none;">';
		secondUserInfoTableHtml += '<td><input style="display:none;" name="userLst['+userNo+'].isAdult" value="true"/></td>';
		secondUserInfoTableHtml += '<td>&nbsp;</td>';
		secondUserInfoTableHtml += '<td><input style="display:none;" name="userLst['+userNo+'].usrSequence" value="'+userNo+'" /></td>';
		secondUserInfoTableHtml += '<td>&nbsp;</td>';
		secondUserInfoTableHtml += '</tr>';
		
		return secondUserInfoTableHtml;
	}


	function getKidsInfoHeader(){
		var kidsInfoTableHtml = '';
		kidsInfoTableHtml += '<tr>';
		kidsInfoTableHtml += '<td colspan="2"><h2 style="margin-top: 5px; margin-bottom : 5px;"><span>Kids INFORMATION</span></h2> </td>';
		//kidsInfoTableHtml += '<td >&nbsp;</td>';
		kidsInfoTableHtml += '<td >&nbsp;</td>';
		kidsInfoTableHtml += '<td ><a class="add-child-lnk">Add Child &nbsp; <span>+</span></a></td>';
		kidsInfoTableHtml += '</tr>';
		return kidsInfoTableHtml;
	}

	function getKidsInfoHtml(kidNo){
		var kidsInfoTableHtml = '';
		kidsInfoTableHtml += '<tr>';
		kidsInfoTableHtml += '<td colspan="4">';
		kidsInfoTableHtml += '<table border="0" width="101%" class="firstKidInfoForm">';
		kidsInfoTableHtml += '<tr>';
		kidsInfoTableHtml += '<td><input type="text" class="form-control personFirstName" value="" title="Please enter yout First Name" name="userLst['+kidNo+'].personFirstName" id="firstName" maxlength="50" placeholder="First Name"  /></td>';
		kidsInfoTableHtml += '<td>&nbsp;</td>';
		kidsInfoTableHtml += '<td><input type="text" class="form-control personLastName" value="" title="Please enter yout Last Name" name="userLst['+kidNo+'].personLastName" id="lastName" maxlength="50" placeholder="Last Name" /></td>';
		kidsInfoTableHtml += '<td>&nbsp;</td>';
		kidsInfoTableHtml += '</tr>';
		
		kidsInfoTableHtml += '<tr>';
		kidsInfoTableHtml += '<td style="padding-bottom: 8px;"><input type="text" class="form-control dob-class dateOfBirth"  title="Please enter your Date of Birth" name="userLst['+kidNo+'].dateOfBirth"  maxlength="50" placeholder="Date of Birth"  style="width: 205px; height: 30px; margin: 0; padding: 0;" /></td>';
		kidsInfoTableHtml += '<td id="dop-er"></td>';
		
		kidsInfoTableHtml += '<td>';
		kidsInfoTableHtml += '<span>';
		kidsInfoTableHtml += '<span><span><input  name="userLst['+kidNo+'].gender" class="form-control gender" style="width: 15px;" type="radio" value="Male"></span><span style="margin-left : 5px;">Male</span></span>';
		kidsInfoTableHtml += '<span><span><input  name="userLst['+kidNo+'].gender" class="form-control gender" style="width : 15px;" type="radio" value="Female"></span><span style="margin-left : 5px;">Female</span></span>';
		kidsInfoTableHtml += '</span>';
		kidsInfoTableHtml += '</td>';	
		
		kidsInfoTableHtml += '<td>&nbsp;</td>';
		kidsInfoTableHtml += '</tr>';	
		kidsInfoTableHtml += '</table>';
		kidsInfoTableHtml += '</td>';
		kidsInfoTableHtml += '</tr>';
		
		kidsInfoTableHtml += '<tr style="display : none;">';
		kidsInfoTableHtml += '<td><input style="display:none;" name="userLst['+kidNo+'].isAdult" value="false"/></td>';
		kidsInfoTableHtml += '<td>&nbsp;</td>';	
		kidsInfoTableHtml += '<td><input style="display:none;" name="userLst['+kidNo+'].usrSequence" value="'+kidNo+'" /></td>';		
		kidsInfoTableHtml += '<td>&nbsp;</td>';
		kidsInfoTableHtml += '</tr>';
		
		return kidsInfoTableHtml;
	}

	function getSecondaryKidsInfoHtml(kidNo){
		var kidsInfoTableHtml = '';
		kidsInfoTableHtml += '<tr>';
		kidsInfoTableHtml += '<td>&nbsp;</td>';
		kidsInfoTableHtml += '<td>&nbsp;</td>';
		kidsInfoTableHtml += '<td>&nbsp;</td>';
		kidsInfoTableHtml += '<td>&nbsp;</td>';
		kidsInfoTableHtml += '</tr>';
		
		kidsInfoTableHtml += '<tr>';
		kidsInfoTableHtml += '<td><input type="text" class="form-control" value="" title="Please enter yout First Name" name="userLst['+kidNo+'].firstName" id="firstName" maxlength="50" placeholder="First Name"  /></td>';
		kidsInfoTableHtml += '<td>&nbsp;</td>';
		kidsInfoTableHtml += '<td><input type="text" class="form-control" value="" title="Please enter yout Last Name" name="userLst['+kidNo+'].lastName" id="lastName" maxlength="50" placeholder="Last Name" /></td>';
		kidsInfoTableHtml += '<td><a class="remove-child-lnk">Remove Child &nbsp; <span>-</span></a></td>';
		kidsInfoTableHtml += '</tr>';
		
		kidsInfoTableHtml += '<tr>';
		kidsInfoTableHtml += '<td><input type="text" class="form-control dob-class"  title="Please enter your Date of Birth" name="userLst['+kidNo+'].dateOfBirth"  maxlength="50" placeholder="Date of Birth"  style="width: 205px; " /></td>';
		kidsInfoTableHtml += '<td id="dop-er"></td>';

		kidsInfoTableHtml += '<td>';
		kidsInfoTableHtml += '<span>';
		kidsInfoTableHtml += '<span><span><input id="genderM" name="userLst['+kidNo+'].gender" class="form-control" style="width: 15px;" type="radio" value="Male"></span><span style="margin-left : 5px;">Male</span></span>';
		kidsInfoTableHtml += '<span><span><input id="genderF" name="userLst['+kidNo+'].gender" class="form-control" style="width : 15px;" type="radio" value="Female"></span><span style="margin-left : 5px;">Female</span></span>';
		kidsInfoTableHtml += '</span>';
		kidsInfoTableHtml += '</td>';	
		
		kidsInfoTableHtml += '<td>&nbsp;</td>';
		kidsInfoTableHtml += '</tr>';	
		return kidsInfoTableHtml;
	}
	function getSecondaryKidsInfoHtml(kidNo){
		var kidsInfoTableHtml = '';
		kidsInfoTableHtml += '<tr>';
		kidsInfoTableHtml += '<td colspan="4">';
		kidsInfoTableHtml += '<table border="0" width="101%" class="secondaryKidsInfoForm">';
		kidsInfoTableHtml += '<tr>';
		kidsInfoTableHtml += '<td>&nbsp;</td>';
		kidsInfoTableHtml += '<td>&nbsp;</td>';
		kidsInfoTableHtml += '<td>&nbsp;</td>';
		kidsInfoTableHtml += '<td>&nbsp;</td>';
		kidsInfoTableHtml += '</tr>';
		
		kidsInfoTableHtml += '<tr>';
		kidsInfoTableHtml += '<td><input type="text" class="form-control personFirstName" value="" title="Please enter yout First Name" name="userLst['+kidNo+'].personFirstName" id="firstName" maxlength="50" placeholder="First Name"  /></td>';
		kidsInfoTableHtml += '<td>&nbsp;</td>';
		kidsInfoTableHtml += '<td><input type="text" class="form-control personLastName" value="" title="Please enter yout Last Name" name="userLst['+kidNo+'].personLastName" id="lastName" maxlength="50" placeholder="Last Name" /></td>';
		kidsInfoTableHtml += '<td><a class="remove-child-lnk">Remove Child &nbsp; <span>-</span></a></td>';
		kidsInfoTableHtml += '</tr>';
		
		kidsInfoTableHtml += '<tr>';
		kidsInfoTableHtml += '<td style="padding-bottom: 8px;"><input type="text" class="form-control dob-class dateOfBirth"  title="Please enter your Date of Birth" name="userLst['+kidNo+'].dateOfBirth"  maxlength="50" placeholder="Date of Birth"  style="width: 205px; height: 30px; margin: 0; padding: 0;" /></td>';
		kidsInfoTableHtml += '<td id="dop-er"></td>';

		kidsInfoTableHtml += '<td>';
		kidsInfoTableHtml += '<span>';
		kidsInfoTableHtml += '<span><span><input  name="userLst['+kidNo+'].gender" class="form-control gender" style="width: 15px;" type="radio" value="Male"></span><span style="margin-left : 5px;">Male</span></span>';
		kidsInfoTableHtml += '<span><span><input  name="userLst['+kidNo+'].gender" class="form-control gender" style="width : 15px;" type="radio" value="Female"></span><span style="margin-left : 5px;">Female</span></span>';
		kidsInfoTableHtml += '</span>';
		kidsInfoTableHtml += '</td>';	
		
		kidsInfoTableHtml += '<td>&nbsp;</td>';
		kidsInfoTableHtml += '</tr>';	
		kidsInfoTableHtml += '</table>';
		kidsInfoTableHtml += '</td>';
		kidsInfoTableHtml += '</tr>';
		
		kidsInfoTableHtml += '<tr style="display : none;">';
		kidsInfoTableHtml += '<td><input style="display:none;" name="userLst['+kidNo+'].isAdult" value="false"/></td>';
		kidsInfoTableHtml += '<td>&nbsp;</td>';	
		kidsInfoTableHtml += '<td><input style="display:none;" name="userLst['+kidNo+'].usrSequence" value="'+kidNo+'" /></td>';		
		kidsInfoTableHtml += '<td>&nbsp;</td>';
		kidsInfoTableHtml += '</tr>';
		
		return kidsInfoTableHtml;
	}
	
	
	function registerContact(){	
		
		var dobMonthForm = $("#dobMonthForm").val();
		var dobDateForm = $("#dobDateForm").val();
		var dobYearForm = $("#dobYearForm").val();
				
		$("#dob").val(dobMonthForm+"/"+dobDateForm+"/"+dobYearForm);
		
		if(!$("input[name='chkTermsAndCond']:checked").val()){ 
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
		        $('#wizard').smartWizard('setError',{stepnum:2,iserror:true});
				$('#wizard').smartWizard('showMessage','Please agree the terms and conditions before Proceeding');			
	 			return false;
	 	}else{	
	 		$('#wizard').smartWizard('setError',{stepnum:2,iserror:false});
			$('#wizard').smartWizard('hideMessage');		
			$("#progFinalAmtIdHidInput").attr("value", $("#paymentTotalPriceTd").text());	
			$("#progStartDateIdHidInput").attr("value", $("#start").val());
			
			if($("#paymentInfoRadio").val()!='New' && $("#paymentInfoRadio").val()!='NewBank'){	
				$(".k-loading-mask").show();
				$(".k-loading-text").html("Please wait");
				$("#statusBlock-payment").css("display", "none");	
				$("#tcloginErrorSpan-payment").css("display", "none");	
				$( "#tcloginErrorSpan-payment" ).html("");
				//var paymentAmount = 0;
			    if($("#paymentAmountSpan").text() != null){
			    	var amountArr = $("#paymentAmountSpan").text().split(" ");
			    	paymentAmount = amountArr[1];
			    	
			    } 
				
					
				//document.forms["becomeTPMemberRegister"].submit();
			}		 
			document.forms["becomeTPMemberRegister"].submit();
			return true;
	 	}
	}


	function forceLower(strInput) {
		strInput.value=strInput.value.toLowerCase();
	}

	function populateKidsSummaryInformation(){
		var firstKidFName = $(".firstKidInfoForm").find(".personFirstName").val();
		var firstKidLName = $(".firstKidInfoForm").find(".personLastName").val();
		var firstKidDob = $(".firstKidInfoForm").find("input.dateOfBirth").val();
		var firstKidGender = $(".firstKidInfoForm").find(".gender:checked").val()

		var kidsInfoHtml = "";
		kidsInfoHtml += '<tr>';
		kidsInfoHtml += '<td><span><b>First Name</b></span></td>';
		kidsInfoHtml += '<td>'+ firstKidFName +'</td>';
		kidsInfoHtml += '</tr>';

		kidsInfoHtml += '<tr>';
		kidsInfoHtml += '<td><span><b>Last Name</b></span></td>';
		kidsInfoHtml += '<td>'+ firstKidLName +'</td>';
		kidsInfoHtml += '</tr>';

		kidsInfoHtml += '<tr>';
		kidsInfoHtml += '<td><span><b>Date of Birth</b></span></td>';
		kidsInfoHtml += '<td>'+ firstKidDob +'</td>';
		kidsInfoHtml += '</tr>';

		kidsInfoHtml += '<tr>';
		kidsInfoHtml += '<td><span><b>Gender</b></span></td>';
		kidsInfoHtml += '<td>'+ firstKidGender +'</td>';
		kidsInfoHtml += '</tr>';

		$('#kidsInformationTable').css("display","");
		$('#kidsInformationTableTbody').html("");
		$('#kidsInformationTableTbody').html(kidsInfoHtml);
		$('#kidsInformationTableTbodyOdd').html("");

		$(document).find('.secondaryKidsInfoForm').each(function(i, obj) {	
			var kidsInfoHtmlUpd = "";			
			kidsInfoHtmlUpd += '<tr>';
			kidsInfoHtmlUpd += '<td>&nbsp;</td>';
			kidsInfoHtmlUpd += '<td>&nbsp;</td>';
			kidsInfoHtmlUpd += '</tr>';

			kidsInfoHtmlUpd += '<tr>';
			kidsInfoHtmlUpd += '<td><span><b>First Name</b></span></td>';
			kidsInfoHtmlUpd += '<td>'+ $(obj).find(".personFirstName").val() +'</td>';
			kidsInfoHtmlUpd += '</tr>';
			
			kidsInfoHtmlUpd += '<tr>';
			kidsInfoHtmlUpd += '<td><span><b>Last Name</b></span></td>';
			kidsInfoHtmlUpd += '<td>'+ $(obj).find(".personLastName").val() +'</td>';
			kidsInfoHtmlUpd += '</tr>';
			
			kidsInfoHtmlUpd += '<tr>';
			kidsInfoHtmlUpd += '<td><span><b>Date of Birth</b></span></td>';
			kidsInfoHtmlUpd += '<td>'+ $(obj).find("input.dateOfBirth").val() +'</td>';
			kidsInfoHtmlUpd += '</tr>';
			
			kidsInfoHtmlUpd += '<tr>';
			kidsInfoHtmlUpd += '<td><span><b>Gender</b></span></td>';
			kidsInfoHtmlUpd += '<td>'+ $(obj).find(".gender:checked").val() +'</td>';
			kidsInfoHtmlUpd += '</tr>';			
			if(i % 2 == 0){
				$('#kidsInformationTableOdd').css("display","");					
				$('#kidsInformationTableTbodyOdd').append(kidsInfoHtmlUpd);					
			}else{
				$('#kidsInformationTable').css("display","");
				$('#kidsInformationTableTbody').append(kidsInfoHtmlUpd);
			}
		});
	}
	
	
	
</script>
</body>

