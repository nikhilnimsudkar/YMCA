<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<base target="_self" />


<body>
<script type="text/javascript">
var merchantId = "YMCA07021001";
//var merchantId = "TESTTERMINAL";
//var transactionAmount = "10.00";
var JetDirectToken	= "1234567890ABCDEFGHIJKabcdefghijk";
var hash = '';
var paymentOrderId = Math.floor((Math.random()*1000000000000)+1);
var addressLine1 = "1234 Fifth Street";
var city = "Beaumont";
var country = "USA";
var state = "TX";
var zipcode = "77708";
var email = "test@gmail.com";
var addressLine1 = "";


$(document).ready(function(){
	//alert("Test Process");
	
		var currentYear1 = new Date().getFullYear();
		var currentYearText = new Date().getFullYear();
		currentYear1 = parseInt(currentYear1.toString().substring(2, 4));
		currentYearText = parseInt(currentYearText.toString());
		for(var i=0; i<30 ; i++){
			$('#expYr').append($('<option>', {value: currentYear1,text: currentYearText}));
			currentYear1 = currentYear1 +1;
			currentYearText = currentYearText +1;
		}	
	var eventMethod = window.addEventListener ? "addEventListener" : "attachEvent";
	var eventer = window[eventMethod];
	var messageEvent = eventMethod == "attachEvent" ? "onmessage" : "message";
	eventer(messageEvent,function(e) {
	    var key = e.message ? "message" : "data";
	    var data = e[key];
	    //alert("Test");
	   /* console.log("Inside iframe");
	    console.log(data);
	    var paymentAmount = 0;
	    
	    if(data.amount != null){
	    	//var amountArr = data.amount.split(" ");
	    	//paymentAmount = amountArr[1];
	    	paymentAmount = 
	    }
	    var jsSha = new jsSHA(merchantId+paymentAmount+JetDirectToken+paymentOrderId);
	    //var jsSha = new jsSHA(merchantId+transactionAmount+JetDirectToken+paymentOrderId);
	    
		hash = jsSha.getHash("SHA-512", "HEX");*/
		
		
	    if(data.trans_type != null){
			$("#trans_type").attr("value", data.trans_type);
			console.log(" transaction type == > "+data.trans_type)
			
			if(data.trans_type == 'TOKENIZE'){
				$("#dataUrl").attr("value", "https://test-integration.ymcasv.org/ymca-web/processPaymentMethod");
				console.log(" TOKENIZE dataURL set...  ");
			}
			
		}else{
			console.log(" transaction type == > SALE ")
			$("#trans_type").attr("value","SALE");
		}
		
		/* if(data.trans_type != null && data.trans_type == "TOKEN"){
			// Do nothing
			$("#amountDebit").attr("value", "0.00");
		}else{ */
			$("#amountDebit").attr("value", data.amount);
		//}
		
		$("#ud1").attr("value", data.ud1);
		$("#ud2").attr("value", data.ud2);
		$("#merData0").attr("value", data.expMonth);
		$("#merData1").attr("value", data.expYr);
		$("#merData2").attr("value", data.isSaveCard);
		$("#name").attr("value", data.name);
		$("#cardNum").attr("value",data.cardNum);
		$("#cvv").attr("value",data.cscNumber);
		$("select#expMo option").each(function() { this.selected = (this.text == data.expMonth); });
		$("select#expYr option").each(function() { this.selected = (this.text == data.expYr); });
		
		
		$("#order_number").attr("value",data.paymentOrderId);
		$("#jp_request_hash").attr("value",data.jetPayHash);		
		if(data.AddressLine1 != null){
			$("#element_6_1").attr("value",data.AddressLine1);		
		}else{
			$("#element_6_1").attr("value",addressLine1);
		}
		if(data.AddressLine2 != null){
			$("#element_6_2").attr("value",data.AddressLine2);
		}else{
			$("#element_6_2").attr("value","");
		}
		if(data.City != null){
			$("#element_6_3").attr("value",data.City);
		}else{
			$("#element_6_3").attr("value",city);
		}
		if(data.state != null){
			$("#element_6_4").attr("value",data.state);
		}else{
			$("#element_6_4").attr("value",state);
		}
		if(data.zipcode != null){
			$("#element_6_5").attr("value",data.zipcode);
		}else{
			$("#element_6_5").attr("value",zipcode);
		}
		if(data.email != null){
			$("#customerEmail").attr("value",data.email);
		}else{
			$("#customerEmail").attr("value",email);
		}
		if(data.contry != null){
			$("select#element_6_6 option").each(function() { this.selected = (this.text == data.contry); }); 
		}else{
			$("select#element_6_6 option").each(function() { this.selected = (this.text == country); }); 
		}		
		$("#paymentPage").submit();	      		
	},false); 
 }); 

</script>
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
   <!-- <input id="saveForm" class="button_text" type="submit" name="submit" value="Submit" /> -->
<input type="hidden" name="cid" value="nrc9fnu3g0e8dnfnh56qlikhu6" />
<input type="hidden" name="jp_tid" id="jp_tid" value="YMCA07021001" /> 
<!--<input type="hidden" name="jp_tid" id="jp_tid" value="TESTTERMINAL" />-->
<input type="hidden" name="jp_key" id="jp_key" value="PiEBdhEO1j7dc1K8duLq3jTp" />
<input type="hidden" name="jp_request_hash" id="jp_request_hash" value="" />
<input type="hidden" name="order_number" id="order_number" value="" />
<input type="hidden" name="amount" id="amountDebit" value="" />
<input type="hidden" name="trans_type" id="trans_type" value="SALE" />
   <input type="hidden" name="ud1" id="ud1" value="" />
   <input type="hidden" name="ud2" id="ud2" value="" />
   <input type="hidden" name="ud3" id="ud3" value="" />
   <input type="hidden" name="merData0" id="merData0" value="" />
   <input type="hidden" name="merData1" id="merData1" value="" />
   <input type="hidden" name="merData2" id="merData2" value="" />
   <input type="hidden" name="merData3" id="merData3" value="" />
   <input type="hidden" name="merData4" id="merData4" value="" />
   <input type="hidden" name="merData5" id="merData5" value="" />
   <input type="hidden" name="merData6" id="merData6" value="" />
   <input type="hidden" name="merData7" id="merData7" value="" />
   <input type="hidden" name="merData8" id="merData8" value="" />
   <input type="hidden" name="merData9" id="merData9" value="" />    
<input type="hidden" name="retUrl" value="https://localhost:8443/ymca-web/redirectSuccess" />
<input type="hidden" name="decUrl" value="https://localhost:8443/ymca-web/redirectFailure" />
<!-- <input type="hidden" name="retUrl" value="https://localhost:8443/ymca-web/redirectSuccess" />
<input type="hidden" name="decUrl" value="https://localhost:8443/ymca-web/redirectFailure" /> -->
<!-- <input type="hidden" name="dataUrl" value="https://lowesinstall.serenecorp.com/ymca-web-1.1.0.RELEASE/ProcessDirectPayment" /> -->
<!-- <input type="hidden" name="dataUrl" value="https://localhost:8443/ymca-web/ProcessDirectPayment" /> --> 	
<!-- <input type="hidden" name="dataUrl" value="https://166.78.104.119:8443/ymca-web/ProcessDirectPayment" />	 -->	
<!-- <input type="hidden" name="retUrl" value="https://test-integration.ymcasv.org/ymca-web/redirectSuccess" />
<input type="hidden" name="decUrl" value="https://test-integration.ymcasv.org/ymca-web/redirectFailure" /> -->
<input type="hidden" name="dataUrl" id="dataUrl" value="https://test-integration.ymcasv.org/ymca-web/ProcessDirectPayment" />

	
 </form>

</body>
</html>