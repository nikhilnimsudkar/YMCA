<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   
<%
   String contextPath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<script src="<%=contextPath %>/resources/js/sha512.js"></script>
<script type="text/javascript">
var merchantId = "TESTTERMINAL";  
var transactionAmount = "11.00";
var JetDirectToken	= "1234567890ABCDEFGHIJKabcdefghijk";
var hash = '';
var paymentOrderId = Math.floor((Math.random()*1000000000000)+1);	

$(document).ready(function(){
	//var jsSha = new jsSHA("TESTTERMINAL15.001234567890ABCDEFGHIJKabcdefghijk20120103-A");
	var jsSha = new jsSHA(merchantId+transactionAmount+JetDirectToken+paymentOrderId);
	hash = jsSha.getHash("SHA-512", "HEX");
	/*if(hash != null){
		hash = hash.toUpperCase();
	}*/
	//alert(hash);
	console.log(hash);
	console.log(paymentOrderId);
	$("#jp_request_hash").val(hash.toString());
	$("#order_number").val(paymentOrderId.toString());
	
	/*$( "#paymentPage" ).submit(function( event ) {
	  alert( "Handler for .submit() called." );
	  $("#jp_request_hash").attr("value","123");
	  $("#order_number").attr("value",paymentOrderId);
	  
	});*/
	
});
</script>
</head>
<body id="main_body" >
<div id="form_container">

  <form name="paymentPage" id="paymentPage" class="tcaspr"  method="post" action="https://testapp1.jetpay.com/jetdirect/post/cc/process_cc.php">
    <!--action="https://extapp1.jetpay.com/jetdirect/post/jp_handler.php"-->
    <div class="form_description">
      <h2>&nbsp;Card and Billing Informaton.</h2>
      <p><font color="#990000">&nbsp;&nbsp;&nbsp;All fields are required.</font></p>
    </div>
    <ul >
      <li id="li_1" >
        <label class="description" for="element_1">Card Holder Name *</label>
        <div id="sprytextfield1">
          <input id="name" name="name" class="element text medium" maxlength="50" value="" />
        </div>
        <p class="guidelines" id="guide_1"><small>Name as it appears on card.</small></p>
      </li>
      <li id="li_4" >
        <label class="description" for="element_4">Card Number *</label>
        <div id="sprytextfield2">
          <input id="cardNum" name="cardNum" class="element text medium" type="text" maxlength="16" onchange="handleCCTyping(this.form, event);" onkeyup="handleCCTyping(this.form, event);" value="" autocomplete="off"/>
          <img src="../images/cards/invalid.gif" name="cardimage" width="34"  height="22" hspace="10" vspace="0" align="top"> </div>
        <p class="guidelines" id="guide_4"><small>Enter the card number do not include dashes "-" or spaces " ".</small></p>
      </li>
      <li id="li_8" >
        <label class="description" for="element_8">Expiration Date *</label>
				<div id="spryselect1">
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
				</div>
				<p class="guidelines" id="expMo"><small>Expiration Date is required for all card types.</small></p>
        <p class="guidelines" id="expYr"><small>Expiration Date is required for all card types.</small></p>
      </li>
      <li id="li_6" >
        <label class="description" for="element_6">Security Code *</label>
        <div id="sprytextfield3">
          <input id="cvv" name="cvv" class="element text small" type="text" maxlength="4" value="" autocomplete="off"/>
        </div>
        <p class="guidelines" id="guide_6"><small>On Visa and MasterCards this is the three (3) digit number on the back next to the signature line. For American Express or Discover this is the four (4) digit number stamped on the front of the card just under the card number.</small></p>
      </li>
      <!-- End Card Detail Block ---> 
      
      <!-- Start Payment Amount Block ---> 
      <!-- Total Amount will not be included if the transType is Token Only -->
      <li class="section_break">
        <h3>Total Donation Amount</h3>
        <p><strong><font color="#990000" style="font-size: large">$</font></strong>&nbsp;</p>
        <p><small>The above amount will be charged to your account.</small></p>
      </li>
      
      <!-- End Payment Amount Block ---> 
      
      <!-- Start Card Billing Address Block --->
      <li class="section_break">
        <h3>Billing Address</h3>
        <!-- <p>You will be asked for your card information on the next page.</p> --> 
      </li>
      <li id="li_6" >
        <label class="description" for="element_6">Address *</label>
        <div>
          <input id="element_6_1" name="billingAddress1" class="element text large" value="" type="text" />
          <label for="element_6_1">Street Address*</label>
        </div>
        <div>
          <input id="element_6_2" name="billingAddress2" class="element text large" value="" type="text" />
          <label for="element_6_2">Address Line 2</label>
        </div>
        <div class="left">
          <input id="element_6_3" name="billingCity" class="element text medium" value="" type="text" />
          <label for="element_6_3">City*</label>
        </div>
        <div class="right">
          <input id="element_6_4" name="billingState" class="element text medium" value="" maxlength="2" type="text" />
          <label for="element_6_4">State*</label>
        </div>
        <div class="left">
          <input id="element_6_5" name="billingZip" class="element text medium" maxlength="5" value="" type="text" />
          <label for="element_6_5">Zip Code*</label>
        </div>
        <div class="right">
          <select class="element select medium" id="element_6_6" name="billingCountry">
            <option value="USA" >United States</option>
          </select>
          <label for="element_6_6">Country</label>
        </div>
      </li>
      <li id="li_1" >
        <label class="description" for="customerEmail">Email Address *</label>
        <div id="sprytextfield1">
          <input id="customerEmail" name="customerEmail" class="element text medium" maxlength="50" value="" />
        </div>
        <p class="guidelines" id="guide_1"><small>Please provide a valid email address.<br />
          A receipt of your purchase will sent to this address.</small></p>
      </li>
      <br />
      <li class="buttons">
        <input id="saveForm" class="button_text" type="submit" name="submit" value="Submit" />
        <input type="hidden" name="cid" value="nrc9fnu3g0e8dnfnh56qlikhu6" />
        <input type="hidden" name="jp_tid" id="jp_tid" value="TESTTERMINAL" />
        <input type="hidden" name="jp_key" id="jp_key" value="1234567890abcdefghijk" />
        <input type="hidden" name="jp_request_hash" id="jp_request_hash" value="" />
        <input type="hidden" name="order_number" id="order_number" value="" />
        <input type="hidden" name="amount" value="11.00" />
        <input type="hidden" name="trans_type" value="SALE" />
        <!-- <input type="hidden" name="customerEmail" value="" /> -->
        <!-- Optional Merchant Use Only -->
        <input type="hidden" name="ud1" value="" />
        <!-- Optional Merchant Use Only -->
        <input type="hidden" name="ud2" value="" />
        <!-- Optional Merchant Use Only -->
        <input type="hidden" name="ud3" value="" />
        <!-- Optional Merchant Use Only -->
        
        <!-- <input type="hidden" name="billingZip" value="77708" /> -->
        
        <input type="hidden" name="merData0" value="" />
        <!-- Optional Merchant Use Only -->
        <input type="hidden" name="merData1" value="" />
        <!-- Optional Merchant Use Only -->
        <input type="hidden" name="merData2" value="" />
        <!-- Optional Merchant Use Only -->
        <input type="hidden" name="merData3" value="" />
        <!-- Optional Merchant Use Only -->
        <input type="hidden" name="merData4" value="" />
        <!-- Optional Merchant Use Only -->
        <input type="hidden" name="merData5" value="" />
        <!-- Optional Merchant Use Only -->
        <input type="hidden" name="merData6" value="" />
        <!-- Optional Merchant Use Only -->
        <input type="hidden" name="merData7" value="" />
        <!-- Optional Merchant Use Only -->
        <input type="hidden" name="merData8" value="" />
        <!-- Optional Merchant Use Only -->
        <input type="hidden" name="merData9" value="" />
        <!-- Optional Merchant Use Only -->
        <!-- <input type="hidden" name="trans_type" value="SALE" />   -->  
        <input type="hidden" name="retUrl" value="https://lowesinstall.serenecorp.com/ymca-web-1.1.0.RELEASE/redirectSuccess" />
		<input type="hidden" name="decUrl" value="https://lowesinstall.serenecorp.com/ymca-web-1.1.0.RELEASE/redirectFailure" />
		<input type="hidden" name="dataUrl" value="https://lowesinstall.serenecorp.com/ymca-web-1.1.0.RELEASE/ProcessDirectPayment" />
	</li>
    </ul>
  </form>
</div>
</body>
</html>