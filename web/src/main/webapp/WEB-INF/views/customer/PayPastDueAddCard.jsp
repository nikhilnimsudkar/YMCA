
<%

    	
    System.out.println("contextPath--->"+contextPath);
   
	String currentDueAmount = request.getParameter("currentDueAmount");
    String pastDueAmount = request.getParameter("pastDueAmount");
    String selectCurrentDue = request.getParameter("selectCurrentDue");
    String invoiceArr = request.getParameter("invoiceArr");    
    double currentPastDueAmount=0.0, selectCurrentDueAmount = 0.0;
     if(currentDueAmount!=null)
       {
      currentPastDueAmount=Double.parseDouble(currentDueAmount)+Double.parseDouble(pastDueAmount);
      System.out.println("kkkkkk "+ currentPastDueAmount);
      }
     if(selectCurrentDue!=null && !"".equals(selectCurrentDue)){
    	 selectCurrentDueAmount = Double.parseDouble(selectCurrentDue);    
     }
   System.out.println("currentDueAmount--"+currentDueAmount+"  past  "+pastDueAmount);
%>
<script src="<%=contextPath %>/resources/js/app/payment.js"></script>
<script src="<%=contextPath %>/resources/js/sha512.js"></script>
<%@ include file="../../layouts/include_taglib.jsp"%>
<script src="<%=contextPath %>/resources/js/jquery.validate.min.js"></script>

<script id="max-amount-ErrorBox" type="text/x-kendo-template">
    <p class="error-message-p">Amount entered can not be greater than Invoice balance.</p>
	
    <div class="confirmbutton" align="center"><button class="confirm-terms-conditions k-button" style=" width: 35%;">Ok</button>   
</script>

<style>
	#invoiceTableId .collapse{
		display:none;
	}
	
	#invoiceTableId .expand{
		display:block;
	}
	
	#invoiceTableId .sectionHead{
		background-color: #CFE1FB; border-radius: 6px; margin-bottom: 10px; margin-top: 10px; padding: 10px;
	}
	
	#invoiceTableId .showSection{
		background: url('resources/img/show_section.gif') transparent no-repeat;
	}
	
	#invoiceTableId .hideSection{
		background: url('resources/img/hide_section.gif') transparent no-repeat;
	}
	
	#invoiceTableId .viewall{
		cursor:pointer;
	}
	

	#invoiceTableId {
	    border-collapse:separate;
	    border:solid #c5c5c5 1px;
	    border-radius:6px;
	    -moz-border-radius:6px;
	}
	
	#invoiceTableId td, th {
	    border-left:solid #c5c5c5 1px;
	    border-top:solid #c5c5c5 1px;
	}
	
	#invoiceTableId th {
	    background-color: #01A490;
	    border-top: none;
	    padding : 6px;
	    color : white !important;
	}
	#invoiceTableId td:first-child {
	     border-left: none;
	}	
</style>

<div id="main">
<div id="purchaseinfoPg" style="margin:0">
	<div class="k-loading-mask" style="width:100%;height:100%; position:absolute; top:114px; left:0px;display:none; z-index:9999">
		<span class="k-loading-text">Loading... Please wait</span>
		<div class="k-loading-image">
			<div class="k-loading-color"></div>
		</div>
	</div>
	<!-- hidden fields -->
	<span id="pastdueamount1" style="display:none"><%= pastDueAmount %> </span>
	<span id="currentPastDueAmount" style="display:none"><%= currentPastDueAmount %></span>
	<span id="selectCurrentDueAmount" style="display:none"><%= selectCurrentDueAmount %></span>
	<span id="invoiceArr" style="display:none"><%= invoiceArr %></span>	
	<input type="hidden" name="AccountID" id="AccountID" value="${ accInfo.accountId }">
	<!--<input type="hidden" name="pastdueamount1" id="pastdueamount1" value="${pastDueAmount}">
		<input type="hidden" name="currentPastDueAmount" id="currentPastDueAmount" value="${currentPastDueAmount}">-->
	<div id="tabstrip3" style="width:800px;margin:0">
		<div id="payment_cart">
			<form:form commandName="account" id="addCardInfoForm" method="post" action="pastDuePaymentProcess" >
			<div class="pay_header">
				
				<span class="head" style="margin-left:3px;">PAYMENT INFORMATION</span>
				<!--<span class="autopaySpan">
					<button id="pmPrimary" class="k-button" style="margin-right: 10px; text-shadow: none;">Make Primary</button>
					<button id="autopayBtn" class="k-button">AUTOPAY</button>
				</span>-->
				
				<div id="purchase_info" >
					<div>
						<select id="paymentInfoRadio" name="paymentInfoRadio" style="width:350px;">
							
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
								</c:if> --%>
								
							</c:forEach>
							<option value="NewCard">Add New Card</option>
							<!-- <option value="NewBankInfo">Add New Bank Info</option> -->
							<c:if test="${sessionScope.agentUid != null}">
									<option value="Cash">Cash</option>
									<option value="Check">Check</option>
									
								</c:if>
						</select>
					</div>
						
					<div id="addcard" style="margin-top:10px; margin-bottom:10px; height:44px; display:none;">
						  
							
							<div style="height:50px;">
								<div class="addcarddiv">
									<div style="height:20px;">Name on card</div>
									<div><input type="text" class="k-textbox" placeholder="Full Name on Card" name="fullName" id="fullName" value="" tabIndex="1" maxlength="50" ></div>
								</div>
								<div class="addcarddiv">
									<div style="height:20px;">Card Number</div>
									<div><input autocomplete="off" type="text" class="k-textbox" placeholder="Card Number" name="cardNum" id="cardNum" value="" tabIndex="2" maxlength="16"></div>
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
									<div><input  autocomplete="off" type="password" class="k-textbox" placeholder="Security Code" name="securityCode" id="securityCode" value="" tabIndex="3" maxlength="4"></div>
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
									<div><input type="text" class="k-textbox" placeholder="State" name="billingState" id="billingState" value="" style="width:45px;" tabIndex="7"></div> 
								</div>
								<div class="addcarddiv" style="width:60px">
									<div style="height:20px;">Zip</div>
									<div><input type="text" class="k-textbox" placeholder="Zip" name="billingZip" id="billingZip" value="" style="width:60px;" tabIndex="8"></div> 
								</div>
								
							</div>
							
							<div style="height:50px;">
								
								<div id="savecardcheckbox" class="addcarddiv" style="width:150px">
									<div><input type="checkbox" name="SaveCard" id="SaveCard">&nbsp;&nbsp;Save Payment Method</div>
								</div>
								<div id="nickname" class="addcarddiv" style="display:none;">
									<div style="height:20px;">Nick Name</div>
									<div><input type="text" class="k-textbox" placeholder="Nick Name" name="nickName" id="nickName" value="" tabIndex="9"></div> 
								</div>
							</div>
						
					</div>
					
					<div id="addBank" style="margin-top:10px; margin-bottom:10px; height:140px; display:none;   float: left; ">										
							<div style="height:50px;">
							<div class="addcarddiv">
								<div style="height:20px;">Bank Account Type</div>
								<div>
									<span>
										<select id="bankAccountTypeSelect" name="bankAccountTypeSelect" tabIndex="1" style="width:130px;">
						                      <option value="CHECKING">Checking</option>
						                      <option value="SAVINGS">Savings</option>
										</select>
									</span>
								</div>
							</div>
							</div>
							<div style="height:50px;">
								<div class="addcarddiv">
									<div style="height:20px;">Bank Account Number</div>
									<div><input type="text" class="k-textbox" placeholder="Bank Account Number" name="bankAccountNumber" id="bankAccountNumber" value=""  style="width: 15.4em;" tabIndex="1"></div>
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
									<div style="height:20px;">Account Name</div>
									<div><input type="text" class="k-textbox" placeholder="Account Name" name="bankAccountName" id="bankAccountName" value=""  style="width: 15.4em;" tabIndex="4"></div>
								</div>
							</div>	
							<div style="height:50px;">
								<div id="savecardcheckbox" class="addcarddiv" style="width:150px">
									<div><input type="checkbox" disabled="disabled" name="SaveCardACH" id="SaveCardACH">&nbsp;&nbsp;Save Payment Method</div>
									
								</div>
								<div id="nickname" class="addcarddiv" >
									<div style="height:20px;">Nick Name</div>
									<div><input type="text" class="k-textbox" placeholder="Nick Name" name="nickNameACH" id="nickNameACH" value=""  tabIndex="5"></div> 
								</div>
								<div class="addcarddiv" style="display:none;">
									 <span id="paymentTokenIdSpan"></span>
									 <span id="paymentAmountSpan"></span>
								</div>
							</div>										
					</div>
				</div>
			</div>
			<div style="clear: both;"></div> 
			<div class="pay_header">
				<!--<span class="head" style="margin-left:3px;">CART ITEMS</span>-->
				<div id="cart_info">
					<table width="100%">
						<tr>
							<td id="cartitem" colspan="4">
							
							</td>
						</tr>
						<tr height="40px"><td></td></tr>
						<c:if test="${fn:length(invoiceLst) gt 0}">
						<tr>
							<td colspan="4">
								<table id="invoiceTableId" cellpadding="5" cellspacing="0" width="98%" 	style="font-size: 12px;">
									<tr>
										<th class="head" style="font-weight: bold; color: #006B6B;">Program</th>
										<th class="head" style="font-weight: bold; color: #006B6B;">Contact</th>
										<th class="head" style="font-weight: bold; color: #006B6B;">DUE DATE</th>
										<th class="head" style="font-weight: bold; color: #006B6B;">BALANCE</th>
										<th class="head" style="font-weight: bold; color: #006B6B;">Payment Amount</th>
										
									</tr>																	
									<c:forEach var="invoice" items="${invoiceLst}" varStatus="status">
										<c:if test="${status.count % 2 == 0}">															
											<tr style="background-color : #F0FFFF; padding-left: 4px; " class="expandable border-radius5px">
										</c:if>
										<c:if test="${status.count % 2 != 0}">
											<tr style=" padding-left: 4px; " class="expandable sectionHead viewall border-radius5px">
										</c:if>
											<td style="color: blue; ">${invoice.signup.itemDetail.recordName} ${invoice.signup.contact.firstName} ${invoice.signup.contact.lastName}</td>
											<td style="color: blue; ">${invoice.signup.contact.firstName} ${invoice.signup.contact.lastName}</td>
											<td style="color: blue; "><fmt:formatDate type="date" pattern="MM/dd/yyyy" value="${invoice.dueDate}" /></td>
											<td style="color: blue; "><input type="text" class="" disabled="disabled" value="${invoice.balance}" /></td>	
											<td style="color: blue; ">
												<%-- <input type="text" class="invoiceSeperateAmt ${invoice.amount}" onchange="calculateTotalAmount(this);" value="${invoice.amount}" /> --%>
												<form:input style="display : none;" cssClass="${invoice.balance}" path="invoice[${status.count - 1}].invoiceId" name="invoiceId" onchange="calculateTotalAmount(this);" value="${invoice.invoiceId}" />
												<form:input cssClass="invoiceSeperateAmt ${invoice.balance}" path="invoice[${status.count -1}].balance" name="balance" onchange="calculateTotalAmount(this);" value="${invoice.balance}" />
											</td>										
										</tr>
									</c:forEach>
									<tr  >
										<td colspan="4" style="color: blue; text-align: center; font-size: 13px; font-weight: bold;">Payment Total</td>
										<td style="color: blue; font-size: 13px; font-weight: bold;"   id="paymentTotalTd"><%= selectCurrentDueAmount %></td>
									</tr>																
								</table>
							</td>
						</tr>
						</c:if>
						<tr height="20px"><td></td></tr>
						<tr>
							<td ><strong><span id="pastDuePayment" style="font-weight: bold; display : none; font-size: 13px;">PastDueAmount</span></strong></td>
							<td align="left"><span id="pastDueAmount" style="font-weight: bold; display: none; font-size: 13px;">
							<input type="text" id="mytext" size="4" disabled="disabled" value=""></span></td>
													
						</tr>
						<tr height="40px">
							<td></td>
						</tr>
						<tr>
<td colspan="4" align="right" style="padding-right:10px;">
								<div id="cartbtns" style="font-size:13px">
									<!--<span id="backtoprogram" class="k-button" onclick="backtoregistration()" style="font-weight:bold">Continue shopping</span>-->
									&nbsp;&nbsp;&nbsp;&nbsp;
									<span class="k-button" id="backBtn" onclick="location.href='payNow'" style="width: 80px; font-weight: bold;">Back</span>
									<span style=""><button id="placeorderBtn" class="k-button">Make Payment</button></span>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<input type="hidden" id="orderNumberInput" name="orderNumber" value="">
			<input type="hidden" id="paymentIdInput" name="paymentId" value="">
			<input type="hidden" id="paymentModeInput" name="paymentMode" value="">
			<input type="hidden" id="tagInput" name="tag" value="">
			<input type="hidden" id="bankAccountNumberInput" name="bankAccountNumber" value="">
			<input type="hidden" id="bankRoutingNumberInput" name="bankRoutingNumber" value="">
			<input type="hidden" id="checkNumberInput" name="checkNumber" value="">
			<input type="hidden" id="accountNameInput" name="accountName" value="">
			<input type="hidden" id="totalAmtInput" name="totalAmt" value="">			
			</form:form>

		</div>
	</div>
</div>
<div>
<script>
var path = window.location.pathname;
//alert("path-- "+path);
var transactionAmount = $("#pastdueamount1").text();

var transactionAmount1=$("#currentPastDueAmount").text();

var selectCurrentDueAmount = $("#selectCurrentDueAmount").text();

var tag="PASTDUE";
if(path=="/ymca-web/payCurrentPastDue")
{
	$("#pastDuePayment").html("CurrentAndPastDueAmount");
	if(selectCurrentDueAmount && selectCurrentDueAmount != '0.0' ){
		$("#pastDuePayment").html("Current Due Amount");
		//$('#mytext').val(selectCurrentDueAmount);
		tag="SELECTEDCURRENTDUE";
	}else if(transactionAmount==0.0)
	{
		
		$("#pastDuePayment").html("CurrentDueAmount");
		//$('#mytext').val(transactionAmount1);
		tag="CURRENTPASTDUE";
	}
	$("#pastDueAmount").html(transactionAmount1);
	$('#mytext').val(transactionAmount1);
	
}

var merchantId = "TESTTERMINAL";  
var transactionAmount = "0.00";
var JetDirectToken	= "1234567890ABCDEFGHIJKabcdefghijk";
var paymentOrderId = Math.floor((Math.random()*1000000000000)+1);
var paymentOrderNumber = paymentOrderId.toString();
$(document).ready(function() {
		
		var currentYear = new Date().getFullYear();
		
		for(var i=0; i<30 ; i++){
			//var currentYear2digit = parseInt(currentYear.toString().substring(2, 4));
			$('#addCardExpirationYearSelect').append($('<option>', {value: currentYear,text: currentYear}));
			currentYear = currentYear +1;
		}
		
		$("#addCardExpirationMonthSelect").kendoDropDownList({
			width:50
		});
		$("#addCardExpirationYearSelect").kendoDropDownList();
		$("#bankAccountTypeSelect").kendoDropDownList();
		$("#paymentInfoRadio").kendoDropDownList();
		
		$("#paymentInfoRadio").change(function(){      
			if($("#paymentInfoRadio").val()=='NewCard'){
				$('#addcard').slideDown();
				$('#addBank').slideUp();
			}
			else if($("#paymentInfoRadio").val()=='NewBankInfo'){
				$('#addBank').slideDown();
				$('#addcard').slideUp();
$("#nickname").show();
$('div#nickname').css("display", "");	
			}
			else if($("#paymentInfoRadio").val()=='Check'){
				$('#addBank').slideDown();
				$('#addcard').slideUp();
				$('div#savecardcheckbox').css("display", "none");	
                  $("#nickname").hide();	
$('div#nickname').css("display", "none");					  
				//$('#addCheck').slideUp();

				
			}
			else{
				$('#addcard').slideUp();
				$('#addBank').slideUp();
			}
		});
		
		$( "#SaveCard" ).click(function(){
			if($('#SaveCard').is(":checked")){
				$("#nickname").show();
				$("div#savecardcheckbox").css("padding-top","21px");
			}
			else{
				$("#nickname").hide();
				
			}
		});
			
		
		// place order event
  $( "#placeorderBtn" ).click(function(){
			var proceed = false;
			$("#tcSuccessSpan").css("display", "none");		
			$("#tcSuccessSpan" ).html("");	
			$("#tcErrorSpan").css("display", "none");		
			$( "#tcErrorSpan" ).html("");
			//alert("hi validate inside ");
			$("#addCardInfoForm").validate({
				//alert("hi validate inside ");
				rules: {
					fullName:"required",
					cardNum: {
						required: true,
						creditcard: true
					},
					securityCode: {
						required: true,
						digits: true,
						minlength: 3,
						maxlength: 4
					},
					billingAddressLine1:"required",
					billingCity:"required",
					billingState:"required",
					billingZip:"required",
				    checkNumber:"required",
				
					bankAccountNumber: {
		                    required: function(){
		                        return $("#paymentInfoRadio").val() == "NewBankInfo";
		                  }
		            },
					bankRoutingNumber: {
		                    required: function(){
		                        return $("#paymentInfoRadio").val() == "NewBankInfo";
		                  }
		            },
					bankAccountName: {
		                    required: function(){
		                        return $("#paymentInfoRadio").val() == "NewBankInfo";
		                  }
		            },
				},
				messages: {
					fullName: "Please provide your full name",
					cardNum: {
						required: "Please provide Card Number",
						creditcard: "Please provide Valid Card Number"
					},
					securityCode: {
						required: "Please provide Security code",
						digits: "Please enter only Numeric value",
						minlength: "Security code must be at least {0} digits",
						maxlength: "Security code must be at most {0} digits"
					},
					billingAddressLine1:"Please provide Billing Address",
					billingCity:"Please provide Billing City",
					billingState:"Please provide Billing State",
					billingZip:"Please provide Billing Zip",
					bankAccountNumber:"Please provide Bank Account Number",
					bankRoutingNumber:"Please provide Bank Routing Number",
					checkNumber:"Please provide Check Number",
					bankAccountName:"Please provide Bank Account Name"
				},
				errorPlacement: function(error, element){
					for(j=1; j<=8; j++){
						if(element[0].tabIndex==j){
							//alert(j);
							//alert($( "#tcErrorSpan" ).html());
							 if($( "#tcErrorSpan" ).html()==""){
								 $("#tcErrorSpan").css("display", "block");		
								 $( "#tcErrorSpan" ).html(error);
								 element[0].focus();
							 }
							 break;
						}
					}
					
				},
				
				submitHandler: function(form) {
					//submitPlaceOrderForm();

	if($("#paymentInfoRadio").val()=='NewCard' || $("#paymentInfoRadio").val()=='NewBankInfo'){
		if(($("#paymentInfoRadio").val()=='NewCard' && $('#SaveCard').is(":checked") && $("#nickName").val()=="") 
				|| ($("#paymentInfoRadio").val()=='NewBankInfo' && $("#nickNameACH").val()=="")){
			 console.log(" Please provide Nick Name ");
			 showErrorMessageForSignup("Please provide Nick Name");
			 return;
		}else{
			console.log(" in else ");
			if($("#paymentInfoRadio").val()=='NewCard'){
				processCCpayment();
			}else{
                processACHpayment();
			}
		}
	}else if($("#paymentInfoRadio").val()=='Cash' || $("#paymentInfoRadio").val()=='None' || $("#paymentInfoRadio").val()=='Check'){
		proceedtosignup('0','','0',$("#paymentInfoRadio").val());
	}else{
		var selectedPaymentMethod = $("#paymentInfoRadio").data("kendoDropDownList").text();
		var paymentDDvalue = $('#paymentInfoRadio').val();
		var paymentDDValueSplit = paymentDDvalue.split("__");
		
		console.log("  type  --> "+paymentDDValueSplit[0]);
		console.log("  token --> "+paymentDDValueSplit[1]);
		console.log("  token --> "+paymentDDValueSplit[2]);
		
		var paymentMethodType = paymentDDValueSplit[0];
		var paymentMethodToken = paymentDDValueSplit[1];
		var paymentMethodId = paymentDDValueSplit[2];
		//alert("jkjkjk");
		if (paymentMethodType && paymentMethodType == 'CREDIT') {
			//alert("jkjkjk CREDIT");
			processCCpaymentbytoken(paymentMethodId, paymentMethodToken);
			}else{
				//alert("jkjkjk ACH");
			processACHpaymentbytoken(paymentMethodId, paymentMethodToken);
			//processACHpaymentbytoken();
		    }
	   }
			console.log("HI after submitHandler");
				}
			});
				
			
  });
		//
});
	function showErrorMessageForSignup(msg){
	$("#tcErrorSpan").css("display", "block");		
	$("#tcErrorSpan").html(msg);
	$("#tcSuccessSpan").css("display", "none");
}

function processCCpayment(){
	console.log("  processpayment  ");
	
	console.log("  AccountID  "+$("#AccountID").val());
	console.log("  cardNum  "+$("#cardNum").val());
	console.log("  addCardExpirationMonthSelect  "+$("#addCardExpirationMonthSelect").val());
	console.log("  addCardExpirationYearSelect  "+$("#addCardExpirationYearSelect").val());
	
	
	$(".k-loading-mask").show();
	$.ajax({
		async:false,
		type: "GET",
		dataType: "json",
		url: "isCardAlreadyExist?accountID="+$("#AccountID").val()+"&cardNumber="+$("#cardNum").val()+"&expMonth="+$("#addCardExpirationMonthSelect").val()+"&expYear="+$("#addCardExpirationYearSelect").val(),
		success: function( data ) {
		  console.log(" got data ::  ");
  		  
  		  if(data){
	  		  	console.log(" got data ::  >> "+data);
	  		    console.log(" got data paymentId ::  >> "+data.paymentId);
				if($('#SaveCard').is(":checked") && data.paymentId && data.paymentId > 0){
					$(".k-loading-mask").hide();
					showErrorMessageForSignup("Card Information already exist or inactivated. To activate please contact Y Agent.");
				   	console.log("Card Information already exist or inactivated. To activate please contact Y Agent.");
					return;
				}else{							
					var saveCard = 'N';
					if($('#SaveCard').is(":checked")){
						saveCard = 'Y';
					}
					//alert("HI paymentOrderNumber "+paymentOrderNumber);
					//var transactionAmount = "140";
					var transactionAmount = $("#pastDueAmount").text() + "0";
					//alert("HI transactionAmount-->>> "+transactionAmount);
					//transactionAmount = $.trim(orderamount.replace("$",""));
					console.log(" transactionAmount  "+transactionAmount);
					//alert("HI transactionAmount "+transactionAmount);
					var jsSha = new jsSHA(merchantId+transactionAmount+JetDirectToken+paymentOrderId);
					hash = jsSha.getHash("SHA-512", "HEX");	
					console.log("  paymentOrderNumber  "+paymentOrderNumber);
					console.log("  hash  "+hash.toString());
					console.log("  transactionAmount  "+transactionAmount);
					
					console.log("  nickName11  "+$("#nickName").val());
					console.log("  fullName11  "+$("#fullName").val());
					console.log("  securityCode  "+$("#securityCode").val());
					console.log("  billingAddressLine1  "+$("#billingAddressLine1").val());
					console.log("  billingAddressLine2  "+$("#billingAddressLine2").val());
					console.log("  billingCity  "+$("#billingCity").val());
					console.log("  billingState  "+$("#billingState").val());
					console.log("  billingZip  "+$("#billingZip").val());
					console.log("  saveCard  "+saveCard);
					var jsonData = {
						trans_type : "SALE", // "TOKENIZE" // "AUTHONLY", "SALE"
						ud1 : $("#AccountID").val(),
						ud2 : $("#nickName").val(),
						name : $("#fullName").val(),
						cardNum : $("#cardNum").val(),
						cscNumber : $("#securityCode").val(),
						expMonth : $("#addCardExpirationMonthSelect").val(),
						expYr : $("#addCardExpirationYearSelect").val(),
						amount : transactionAmount,
						AddressLine1 : $("#billingAddressLine1").val(),
						AddressLine2 : $("#billingAddressLine2").val(),
						City : $("#billingCity").val(),
						state : $("#billingState").val(),
						zipcode : $("#billingZip").val(),
						email : "test@gmail.com",
						contry : "USA",
						jetPayHash : hash.toString(),
						paymentOrderId : paymentOrderNumber,
						isSaveCard : saveCard
					};
					var win = document.getElementById("childIframeId").contentWindow;
					win.postMessage(jsonData, '*');
					
					console.log(" Payment request sent. ");
					$.ajax({
						type: "POST",
						url: "checkPaymentProcessStatus",
						data: { "orderNumber" : paymentOrderNumber },
						success: function( data ) {
						  console.log(" got data :: 11 ");
				  		  
				  		  if(data){
					  		  	console.log(" got data ::  >> "+data);
							    data_split_add_cart = data.split("__");
								if(data_split_add_cart[0]=='SUCCESS'){
									
									console.log(" Token : "+data_split_add_cart[1]);
									console.log(" PaymentMethodId : "+data_split_add_cart[2]);
									
									payId = data_split_add_cart[2];
//alert(":hwereeee");
									proceedtosignup(payId,'',paymentOrderNumber,"CREDIT");
			
									
								}else{
									//proceedtosignup(payId,'',paymentOrderNumber,"CREDIT");
									console.log(" failed status : "+data_split_add_cart[1]);
									$(".k-loading-mask").hide();
									showErrorMessageForSignup("Error occured while processing payment. \n"+data_split_add_cart[1]);
													//  $("#payment_cart").html("");
									}
					  	  }else{
					  		  $(".k-loading-mask").hide();
					  		  showErrorMessageForSignup("There was some error. Please try again later.");
							
					  	  }
					  },
					  error: function( xhr,status,error ){
						  $(".k-loading-mask").hide();
						  showErrorMessageForSignup("There was some error. Please try again later.");
					  }
					});
					}
	  	  }else{
	  		  $(".k-loading-mask").hide();
	  		  showErrorMessageForSignup("There was some error. Please try again later.");
			  return;
	  	  }
	  },
	  error: function( xhr,status,error ){
		  $(".k-loading-mask").hide();
  		  showErrorMessageForSignup("There was some error. Please try again later.");
		  return;
	  }
	});
}

function proceedtosignup(paymentId, jp_request_hash, orderNumber, paymentMode){
	//alert("fdggg");
	console.log("  proceedtosignup  ");
	var transactionAmount = $("#pastDueAmount").text() + "0";
	//var transactionAmount = $.trim(orderamount.replace("$",""));
	transactionAmount = $.trim(transactionAmount.replace(".00",""));
//	var transactionAmount="10";
	var bankAccountNumber =  $("#bankAccountNumber").val();
	var bankRoutingNumber =  $("#bankRoutingNumber").val();
	var checkNumber =  $("#checkNumber").val();
	var accountName =  $("#bankAccountName").val();
	var totalAmt =  transactionAmount;
	
	$("#orderNumberInput").attr("value", orderNumber);
	$("#paymentIdInput").attr("value", paymentId);
	$("#paymentModeInput").attr("value", paymentMode);
	$("#tagInput").attr("value", tag);
	$("#bankAccountNumberInput").attr("value", bankAccountNumber);
	$("#bankRoutingNumberInput").attr("value", bankRoutingNumber);
	$("#checkNumberInput").attr("value", checkNumber);
	$("#accountNameInput").attr("value", accountName);
	$("#totalAmtInput").attr("value", totalAmt);
	
	$(".k-loading-mask").show();
		//var paymentId = payId;
		//var orderNumber = orderNumber;
					//alert("HI tag "+tag);
			data= '&orderNumber='+orderNumber+'&paymentId='+paymentId+'&paymentMode='+paymentMode+'&tag='+tag+'&bankAccountNumber='+bankAccountNumber +'&bankRoutingNumber='+bankRoutingNumber +'&checkNumber='+checkNumber +'&accountName='+accountName+'&totalAmt='+totalAmt;
			$.ajax({
			type: "POST",
			//url: "pastDuePaymentProcess",
			url: $('#addCardInfoForm').attr( "action"),			
			//data: { "orderNumber" : paymentOrderId.toString() },
			  data: $('#addCardInfoForm').serialize(),
			success: function( data ) {
			 // console.log(" got data :: 11 ");
			 
			   data_s = data.split("__");
			 //console.log(" cartType "+cartType);
			// console.log(" data_s "+data_s);
			 if(data_s[0]=="SUCCESS"){
				 $(".k-loading-mask").hide();
				 
				 /////
				 $("#payment_cart").html("");
				  //alert(data_s[2]);
				  var successMsg = '';
				  
				  successMsg += '<div id="statusBlock" class="k-block k-success-colored" style="width: 90%;margin: 20px; padding: 10px;">Thank you for your payment.<br>';
				  if(orderNumber && orderNumber != '' && orderNumber != '0'){
					  successMsg += 'Your transaction Id is: '+orderNumber+'';  
				  }
				  successMsg += '</div>';
				  /*
				  if(data_s[2]==false || data_s[2]=='false'){
					  successMsg += '<div style="width: 90%;margin: 20px; padding: 10px;"><br><input id="newsignup" type="button" class="k-button" value="Signup for New Child Care Program" onclick="gotochildcareprogram();"></div>';
				  }else{
					  successMsg += '<div style="width: 90%;margin: 20px; padding: 10px;"><br><input id="newsignup" type="button" class="k-button" value="Signup for New Program" onclick="gotosignupprogram();"></div>';
				  }
				  */
				  successMsg += '<div style="width: 90%;margin: 20px; padding: 10px;"><br><input id="newsignup" type="button" class="k-button" value="Go to Home" onclick="location.href=\'home\'"></div>';
				  $("#payment_cart").html(successMsg);
				 
				 
				 /////
				 
				 
				 
				 
				// $("#payment_cart").html("");
				 // $("#payment_cart").html('<div id="statusBlock" class="k-block k-success-colored" style="width: 90%;margin: 20px; padding: 10px;">Thank you for making payment.<br>Your transaction Id is: '+orderNumber+'</div>');
				  
				  $("#tcErrorSpan").css("display", "none");		
				  $("#tcErrorSpan" ).html("");	
				  $("#tcSuccessSpan").css("display", "none");		
				  $("#tcSuccessSpan" ).html("");
			  ///
			  	  }else{
		  		 console.log(" NOT got data ::  >> ");
				  console.log(" 466  ");
				  $("#tcErrorSpan").css("display", "block");		
				  $("#tcErrorSpan" ).html("There is some error, please try after some time");	
				  $("#tcSuccessSpan").css("display", "none");		
				  $("#tcSuccessSpan" ).html("");
				  $(".k-loading-mask").hide();
		  	  }
	  		//  $(".k-loading-mask").hide();
		  },
		  	  error: function( xhr,status,error ){
			  //alert("1" +status);
			  console.log(" 476  ");
			  console.log(xhr);
			  console.log(status);
			  console.log(error);
			  $("#tcErrorSpan").css("display", "block");		
			  $("#tcErrorSpan" ).html("There is some error, please try after some time");	
			  $("#tcSuccessSpan").css("display", "none");		
			  $("#tcSuccessSpan" ).html("");
			  $(".k-loading-mask").hide();
		  }
		});
}

function processACHpayment(){
	
	console.log(" processACHpayment ");
	//var orderamount = $("#ordertotal").text();
var transactionAmount = $("#pastDueAmount").text() + "0";
	//var transactionAmount = $.trim(orderamount.replace("$",""));
	transactionAmount = $.trim(transactionAmount.replace(".00",""));
//	var transactionAmount="10";
	var bankAccountNumber =  $("#bankAccountNumber").val();
	var bankRoutingNumber =  $("#bankRoutingNumber").val();
	var checkNumber =  $("#checkNumber").val();
	var accountName =  $("#bankAccountName").val();
	var totalAmt =  transactionAmount;
	var accountID =  $("#AccountID").val();
	var nickName =  $("#nickNameACH").val();
	var accountType =  $("#bankAccountTypeSelect").val();
	
	$(".k-loading-mask").show();
	$.ajax({
		async:false,
		type: "GET",
		dataType: "json",
		url: "isBankInfoAlreadyExist?accountID="+accountID+"&bankAccountNumber="+bankAccountNumber,
		success: function( data ) {
		  console.log(" got data ::  ");
  		  
  		  if(data){
	  		  	console.log(" got data ::  >> "+data);
	  		    console.log(" got data paymentId ::  >> "+data.paymentId);
				if(data.paymentId && data.paymentId > 0){
					showErrorMessageForSignup("Bank Information already exist or inactivated. To activate please contact Y Agent.");
					$(".k-loading-mask").hide();
					return;
				}else{
	
					$.ajax({
						  type: "POST",
						  url: "processACHPayment",	
						  data: { bankAccountNumber: bankAccountNumber, bankRoutingNumber : bankRoutingNumber, checkNumber : checkNumber, 
							  	  accountName : accountName, totalAmt : totalAmt, accountType : accountType, nickName : nickName, accountID : accountID},
						  success: function( data ) {
							  
							  if(data){
								  	console.log(" data :: "+data);
								  
								  	data_split_add_cart = data.split("__");
									if(data_split_add_cart[0]=='SUCCESS'){
										
										console.log(" Token : "+data_split_add_cart[1]);
										console.log(" PaymentMethodId : "+data_split_add_cart[2]);
										console.log(" TransactionId : "+data_split_add_cart[3]);
										
										payId = data_split_add_cart[2];
										transactionId = data_split_add_cart[3];
										
										proceedtosignup(payId,"",transactionId,"ACH");
										
									}else{
										console.log(" failed status : "+data_split_add_cart[1]);
										$(".k-loading-mask").hide();
										showErrorMessageForSignup("Error occured while processing payment. \n"+data_split_add_cart[1]);
										
									}
							  }else {
								  $(".k-loading-mask").hide();
								  showErrorMessageForSignup("Error occurred while processing the payment. Please try again later.");
							  }
						  },
						  error: function( xhr,status,error ){
							  $(".k-loading-mask").hide();
							  showErrorMessageForSignup("Error occurred while processing the payment. Please try again later.");
						  }
					});
				}
		  }else{
			  $(".k-loading-mask").hide();
			  showErrorMessageForSignup("There was some error. Please try again later.");
			  return;
	  	  }
	  },
	  error: function( xhr,status,error ){
		  $(".k-loading-mask").hide();
		  showErrorMessageForSignup("There was some error. Please try again later.");
		  return;
	  }
});
}

function processCCpaymentbytoken(payId,token){
			console.log(" processpaymentbytoken  ");
	//transactionAmount = $("#ordertotal").text();
	//transactionAmount = $.trim(orderamount.replace("$",""));
	var transactionAmount = $("#pastDueAmount").text() + "0";
//var token=paymentMethodToken;
	
	console.log(" transactionAmount  >>  "+transactionAmount);
	$(".k-loading-mask").show();
	$.ajax({
		  type: "POST",
		  url: "processPaymentByTokenId",	
		  data: { token: token, totalAmount : Math.ceil(transactionAmount)},
		  success: function( data ) {
			  console.log(" processPaymentByTokenId ");
			  if(data != null && data.responseText == "APPROVED"){	
				  console.log(" Success processPaymentByTokenId >> ");
				  
				  var transactionId = data.transId;
				  console.log(" transactionId  >>  "+transactionId);
				 proceedtosignup(payId,'',transactionId,"CREDIT");
				 
			  }else {
				  $(".k-loading-mask").hide();
				  $("#tcSuccessSpan").css("display", "none");		
				  $("#tcErrorSpan").css("display", "block");	
				  $( "#tcErrorSpan" ).html("The transaction failed..");	
			  }
		  },
		  error: function( xhr,status,error ){
			  console.log(" Error processPaymentByTokenId ");
			  $(".k-loading-mask").hide();
			  $("#tcSuccessSpan").css("display", "none");		
			  $("#tcErrorSpan").css("display", "block");	
			  $( "#tcErrorSpan" ).html("There was some error. Please try again later");							  		 
		  }
	});
}

function processACHpaymentbytoken(payId,token){
	//function processACHpaymentbytoken(){
	
				console.log(" processACHpaymentbytoken  ");
	
	//orderamount = $("#ordertotal").text();
	//transactionAmount = $.trim(orderamount.replace("$",""));
	//transactionAmount = $.trim(transactionAmount.replace(".00",""));
	var transactionAmount = $("#pastDueAmount").text() + "0";
	transactionAmount = $.trim(transactionAmount.replace(".00",""));
//var token=paymentMethodToken;
	console.log(" transactionAmount  >>  "+transactionAmount);
	//console.log(" payId  >>  "+payId);
	//console.log(" token  >>  "+token);
	
	$(".k-loading-mask").show();
	
	$.ajax({
		  type: "POST",
		  url: "processACHPaymentByTokenId",	
		  data: { token: token, totalAmount : transactionAmount, checkNumber : '', cardName : ''},
		  success: function( data ) {				 
			  if(data != null && data.actCode == "000"){
				  var transactionId = data.transId; 
				  console.log(" Success processACHPaymentByTokenId >> ");
				  console.log(data);
				proceedtosignup(payId,'',transactionId,"ACH");
				
			 }else{
				  $(".k-loading-mask").hide();
				  console.log(" Error processACHPaymentByTokenId ");
				  $("#tcSuccessSpan").css("display", "none");		
				  $("#tcErrorSpan").css("display", "block");	
				  $( "#tcErrorSpan" ).html("Error occurred while processing the payment. Please try again later <br />ActionCode :<b>"+data.actCode+"</b> Error message : "+data.responseText);
			 }
		  },
		  error: function( xhr,status,error ){
			  $(".k-loading-mask").hide();
			  $("#statusBlock-payment").css("display", "block");	
			  $("#tcloginErrorSpan-payment").css("display", "block");	
			  $( "#tcloginErrorSpan-payment" ).html("There was some error. Please try again later");							  		 
		  }
	});
}
	
function calculateTotalAmount(thisObject){
	var thisObj = $(thisObject);
	var thisClass = thisObj.attr('class');
	if(thisClass){
		var thisClassArr = thisClass.split(" ");
		var maxInvAmt = parseFloat(thisClassArr[1]);
		if(parseFloat(thisObj.val()) > maxInvAmt){
			thisObj.attr("value", maxInvAmt);
			maxAmtError();
		}else{
			thisObj.attr("value", thisObj.val());
		}
		var totalBalance = 0;
		$(document).find('.invoiceSeperateAmt').each(function(i, obj) {												
			totalBalance = totalBalance + parseFloat($(this).val());		
		});
		if(totalBalance && totalBalance > 0){
			$("#mytext").attr("value", totalBalance);
			$("#paymentTotalTd").text(totalBalance);
			//$("#pastDueAmount").html(totalBalance);
		}
	}
}

function maxAmtError(){
	var kendoWindow = $("<div />").kendoWindow({
		title: "Error",
		resizable: false,
		modal: true,
		width:400
	});

	kendoWindow.data("kendoWindow")
	 .content($("#max-amount-ErrorBox").html())
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
</script>