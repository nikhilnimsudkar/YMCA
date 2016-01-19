<%@ include file="../../layouts/include_taglib.jsp" %>
<%

String contextPath = request.getContextPath();

%>

<script src="<%=contextPath %>/resources/js/maskedinput.min.js"></script>
<script src="<%=contextPath %>/resources/js/jquery.validate.min.js"></script>
<script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.13.0/additional-methods.min.js"></script>
<script type="text/javascript" src="<%=contextPath %>/resources/js/steps/modernizr-2.6.2.min.js"></script>
<script type="text/javascript" src="<%=contextPath %>/resources/js/steps/jquery.steps.js"></script>

<%-- <link rel="stylesheet" href="<%=contextPath %>/resources/css/steps/normalize.css">
<link rel="stylesheet" href="<%=contextPath %>/resources/css/steps/main.css"> --%>
<link rel="stylesheet" href="<%=contextPath %>/resources/css/steps/jquery.steps.css"> 

<div id="wizard">
<div class="">

<script id="product-select-ErrorBox" type="text/x-kendo-template">
    <p class="error-message-p">Please select the Product before Proceeding</p>
	
    <div class="confirmbutton" align="center"><button class="confirm-product-select k-button" style=" width: 35%;">Ok</button>   
</script>

<script id="terms-conditions-ErrorBox" type="text/x-kendo-template">
    <p class="error-message-p">Please agree the terms and conditions before Proceding</p>
	
    <div class="confirmbutton" align="center"><button class="confirm-terms-conditions k-button" style=" width: 35%;">Ok</button>   
</script>

<script>
var emailValid = "false";
function forceLower(strInput) {
	strInput.value=strInput.value.toLowerCase();
}


$(document).ready(function ()
    {
    	
    	/* $('.termsAndCondition').on( "click", function() {        	
            if($('input:radio[name=type]:checked')){	            	
            	var termsVar = $(this).parent().find('input.tcSpan').val(); 
            	$(".terms").html(termsVar);
            	var test = $(".terms").text();
            	$(".terms").html(test);	            	
            	$("#memProductId").attr("value", $(this).parent().find('span.memProductIdSpan').text());
            	$("#memPricingRuleId").attr("value", $(this).parent().find('span.memPricingRuleIdSpan').text());
            	$("#memMembershipPrice").attr("value", $(this).parent().find('span.memTierPriceSpan').text());            	
            	$("#memProductName").attr("value", $(this).parent().find('span.memProductNameSpan').text());
            	$("#memProductDesc").attr("value", $(this).parent().find('span.memProductDescSpan').text());            	
            	$("#userTC").attr("value", test);
            	
            	$("#sumProductName").text($(this).parent().find('span.memProductNameSpan').text());
            	$("#sumProductDesc").text($(this).parent().find('span.memProductDescSpan').text());
            	$("#sumMembershipPrice").text($(this).parent().find('span.memTierPriceSpan').text());
            }
        }); */
        $("#signup_wizard").steps({
        	onStepChanging: function(event, currentIndex, newIndex){
                /* if (currentIndex === 0){                  	
                    if(!$("input[name='termsAndCondition']:checked").val()){                    	
                    	//alert("Please select the Product before Proceeding");  
                    	//$("#errorDialogMessage").text("Please select the Product before Proceeding");
		  	  			//$("#errorGenericDialogBox").dialog( "open" );
		  	  			 //$(".error-message-p").text("Please select the Product before Proceeding");
		  	  			 var kendoWindow = $("<div />").kendoWindow({
					        	title: "Error",
					        	resizable: false,
					        	modal: true,
					        	width:400
					        });
					
			  	  			kendoWindow.data("kendoWindow")
					         .content($("#product-select-ErrorBox").html())
					         .center().open();
			  	  			
					        kendoWindow
					        .find(".confirm-product-select")
					        .click(function() {
					        	if ($(this).hasClass("confirm-product-select")) {         		
					        		kendoWindow.data("kendoWindow").close();
					        	}
					        })
					        .end()					        
				         
                    	return false;
                    }else{
                    	if(newIndex === 1){
                    		$('.wizard > .content').css("min-height", "40em");
                    	}else{
                    		$('.wizard > .content').css("min-height", "28em");
                    	}
                    	return true;
                    }
                } */
                if (currentIndex === 0){                 	
                	if ($('#signupForm').valid() && emailValid =="true"){
                		if(newIndex === 1){
                    		$('.wizard > .content').css("min-height", "28em");
                    	}else{
                    		$('.wizard > .content').css("min-height", "40em");
                    	}
                		return true;
                	}else{
                		if(emailValid =="false"){
                		  $("#emailErrorSpan").css("display", "block");	
        				  $("#email").css("border-color", "red");
        				  $( "#emailErrorSpan" ).html("Email Exists. Please try with other Email Addess.");
        				  setTimeout(function(){
        					  $("#emailErrorSpan" ).html("");	
        					  $("#emailErrorSpan").css("display", "none");	
     				      }, 5000);
                		}
                		return false;
                	}
                     
                    /* if(!(validateSignup($($("#signupForm"))))){  
                    	alert("Validation Failed");
                    	return false;
                    }else{
                    	alert("Validation Failed");
                    	return true;
                    } */
                	return true;
                } 
                if (currentIndex === 1){          
                	
                    if(!$("input[name='chkTermsAndCond']:checked").val()){                    	
                    	//alert("Please agree the terms and conditions before Proceding");
                    	//$("#errorDialogMessage").text("Please agree the terms and conditions before Proceding");
		  	  			//$("#errorGenericDialogBox").dialog( "open" );
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
					        .end()	
                    	return false;
                    }else{
                    	$("#sumProductId").text($("#memProductId").attr("value"));
                    	$("#sumProductName").text($("#memProductName").attr("value"));
                    	$("#sumProductDesc").text($("#memProductDesc").attr("value"));                    	
                    	$("#sumProductType").text($("#memMembershipType").attr("value"));
                    	$("#sumProductDuration").text($("#memMembershipDuration").attr("value"));                    	
                    	$("#sumMembershipPrice").text($("#memMembershipPrice").attr("value"));
                    	$("#sumUsername").text($("#username").val());
                    	$("#sumPasswordHint").text($("#passwordHint").val());
                    	$("#sumFirstName").text($("#firstName").val());
                    	$("#sumLastName").text($("#lastName").val());
                    	$("#sumEmail").text($("#email").val());
                    	$("#sumPhoneNumber").text($("#phoneNumber").val());
                    	$("#sumWebsite").text($("#website").val());
                    	$("#sumAddress").text($("#addressLine1").val());
                    	$("#sumCity").text($("#city").val());
                    	$("#sumState").text($("#state").val());
                    	//$("#sumZip").text($("#address\\.postalCode").val());
                    	$("#sumCountry").text($("#country").val());
                    	$("#sumTc").html($("#userTC").attr("value"));
                    	if(newIndex === 2){
                    		$('.wizard > .content').css("min-height", "28em");
                    	}else{
                    		$('.wizard > .content').css("min-height", "40em");
                    	}    
                    	 /* $("#summaryData").kendoGrid({               
                             columns: [
                 				{ field: "field0", title: "Field" },
                                 { field: "field1", title: "Value" }
                             ]
                         }); */
                         $("#summaryData").kendoGrid();
                    	return true;
                    }
                }
                return true;
            },
            onFinishing: function (event, currentIndex) {
            	$("#signupForm").submit();
            	return true;
            },
            headerTag: "h2",
            bodyTag: "section",
            transitionEffect: "slideLeft"
        }).validate({
            errorPlacement: function(error, element) {
                //element.before(error);
                error.appendTo( element.next() )
            }
        });
        
        
        
        $("#membershipData").kendoGrid({                
            columns: [
				{ field: "field0", title: "" },
                { field: "field1", title: "ID" },
                { field: "field2", title: "Product Name" },
                { field: "field3", title: "Product Description" },                
                { field: "field6", title: "Product Price" }
            ]
        });    
        $("#email").on('keyup change', function (){	 
        	$(".k-loading-mask").show();
        	$(".k-loading-text").html("Please wait while the System validates the Email...");		
        	var email = $("#email" ).val();
        	if(email){
	        	$.ajax({
	        		  type: "GET",
	        		  url: "isEmailExists/"+email,				  
	        		  success: function( data ) {				  	  
	        		  	  if(data=='Not-Exists'){
	        			  	  $("#emailErrorSpan").css("display", "none");		
	        				  $("#emailErrorSpan" ).html("");							 
	        			  	  $("#emailSuccessSpan").css("display", "block");		
	        				  $("#emailSuccessSpan" ).html("Email Available");
	        				  $("#email").css("border-color", "lightgreen");
	        				  setTimeout(function(){
	        					  $("#emailSuccessSpan" ).html("");	
	        					  $("#emailSuccessSpan").css("display", "none");	
	     				      }, 5000);   
	        				  emailValid = "true";
	        			  	  
	        		  	  }else if(data == 'Exists'){
	        		  		  $("#emailSuccessSpan").css("display", "none");		
	        				  $("#emailSuccessSpan" ).html("");	
	        				  $("#emailErrorSpan").css("display", "block");	
	        				  $("#email").css("border-color", "red");
	        				  $( "#emailErrorSpan" ).html("Email Exists. Please reset/set up new password.");
	        				  setTimeout(function(){
	        					  $("#emailErrorSpan" ).html("");	
	        					  $("#emailErrorSpan").css("display", "none");	
	     				      }, 5000);
	        				  emailValid = "false";	        				  
	        				  $(".k-loading-mask").hide();
	        		  	  }else if(data == 'EMAIL-INVALID'){
	        		  		  $("#emailSuccessSpan").css("display", "none");		
	        				  $("#emailSuccessSpan" ).html("");	
	        				  $("#emailErrorSpan").css("display", "block");	
	        				  $("#email").css("border-color", "red");
	        				  $( "#emailErrorSpan" ).html("Please Enter the E-mail.");
	        				  setTimeout(function(){
	        					  $("#emailErrorSpan" ).html("");	
	        					  $("#emailErrorSpan").css("display", "none");	
	     				      }, 5000);
	        				  emailValid = "false";
	        				  $(".k-loading-mask").hide();
	        		  	  }else {
	        		  		  $("#emailSuccessSpan").css("display", "none");		
	        				  $("#emailSuccessSpan" ).html("");	
	        				  $("#emailErrorSpan").css("display", "block");		
	        				  $("#email").css("border-color", "red");
	        				  $( "#emailErrorSpan" ).html("There was some error. Please try again later");
	        				  setTimeout(function(){
	        					  $("#emailErrorSpan" ).html("");	
	        					  $("#emailErrorSpan").css("display", "none");	
	     				      }, 5000);
	        				  emailValid = "false";
	        				  $(".k-loading-mask").hide();
	        		  	  }					  
	        		  },
	        		  error: function( xhr,status,error ){
	        			  $("#emailSuccessSpan").css("display", "none");		
	        			  $("#emailSuccessSpan" ).html("");	
	        			  $("#emailErrorSpan").css("display", "block");		
	        			  $( "#emailErrorSpan" ).html("There was some error. Please try again later");
	        			  setTimeout(function(){
	    					  $("#emailErrorSpan" ).html("");	
	    					  $("#email").css("border-color", "red");
	    					  $("#emailErrorSpan").css("display", "none");	
	 				      }, 5000);
	        			  emailValid = "false";
	        			  $(".k-loading-mask").hide();
	        		  }
	        	});	
        	}
        	
        });
        
        $("#phoneNumber").mask("(999) 999-9999");
        $("#dob").kendoDatePicker();
        /* $('.termsAndCondition').on( "click", function() {        	
            if($('input:radio[name=type]:checked')){	            	
            	var termsVar = $(this).parent().find('input.tcSpan').val(); 
            	$(".terms").html(termsVar);
            	var test = $(".terms").text();
            	//$(".terms").html(test);	            	
            	$("#memProductId").attr("value", $(this).parent().find('span.memProductIdSpan').text());
            	$("#memPricingRuleId").attr("value", $(this).parent().find('span.memPricingRuleIdSpan').text());
            	$("#memMembershipPrice").attr("value", $(this).parent().find('span.memTierPriceSpan').text()); 
            	$("#memMembershipType").attr("value", $(this).parent().find('span.memProductTypeSpan').text());
            	$("#memMembershipDuration").attr("value", $(this).parent().find('span.memProductDurationSpan').text());
            	$("#memProductName").attr("value", $(this).parent().find('span.memProductNameSpan').text());
            	$("#memProductDesc").attr("value", $(this).parent().find('span.memProductDescSpan').text());            	
            	$("#userTC").attr("value", test);
            }
        }); */
    });
    

</script>
<div class="border-FivePX" >
<div id="signup_wizard">
   <%-- <h2>Product Information </h2>   
   <section>
   
<table border="1" id="membershipData">
	<thead>
		<tr>
			<th>&nbsp;</th>
			<th>ID</th>
			<th>Product Name</th>
			<th>Product Description</th>			
			<th>Product Price</th>			
					
		</tr>
	</thead>
	<tbody id="salesAccData" style="min-height: 200px;">
		<c:forEach var="product" items="${products }">
            <tr>
            	<td>
					<input type="radio" name="termsAndCondition" class="termsAndCondition" />
				  	<input class="tcSpan" style="display:none;" value="<c:out value='${product.waiversAndTC.tcDescription}'/>"></input>	
				  	<span class="memProductIdSpan"	style="display:none;"><c:out value="${product.productId}"/></span>  
				  	<span class="memProductNameSpan"	style="display:none;"><c:out value="${product.productName}"/></span>  
				  	<span class="memProductDescSpan"	style="display:none;"><c:out value="${product.description}"/></span>  
				  	<span class="memProductTypeSpan"	style="display:none;"><c:out value="${product.productType}"/></span>  
				  	<span class="memProductDurationSpan"	style="display:none;"><c:out value="${product.duration}"/></span>  
				  	<span class="memPricingRuleIdSpan"	style="display:none;"><c:out value="${product.pricingRule.pricingRuleId}"/></span>
				  	<span class="memTierPriceSpan"	style="display:none;"><c:out value="${product.pricingRule.tierPrice}"/></span>						
				</td>
            	<td>${product.productId }</td>
				<td>${product.productName }</td>
				<td>${product.description }</td>				
				<td>$ ${product.pricingRule.tierPrice}</td>
				<td>${product.pricingRule.tierPrice }</td>
				
				
            </tr>
        </c:forEach>	
	</tbody>
</table>  
</section>
	<br /> --%>

<h2>User Information</h2>
	<section>

<spring:bind path="account.*">
        <c:if test="${not empty status.errorMessages}">
            <div class="alert alert-danger alert-dismissable">
                <a href="#" data-dismiss="alert" class="close">&times;</a>
                <c:forEach var="error" items="${status.errorMessages}">
                    <c:out value="${error}" escapeXml="false"/><br/>
                </c:forEach>
            </div>
        </c:if>
    </spring:bind>

<div>
	<form:form commandName="account" method="post" action="register" id="signupForm" style="border : none; padding : 0px; margin : 0px;" autocomplete="off"
               class="bootstrap-frm cmxform" onsubmit="">        
        <label>
		 	<form:input cssClass="form-control" path="userLst[0].email" name="email" id="email" onkeyup="return forceLower(this);" maxlength="50" placeholder="Email" data-rule-required="true" data-rule-email="true" data-msg-required="Please enter your email address" data-msg-email="Please enter a valid email address"  />
		 </label><label></label>
		 <table border="0">
		 	<tr>
		 		<td colspan="2"><h2><span>USER INFORMATION</span></h2> </td>
		 	</tr>
			<tr>		 		
		 		<td>
		 			<label>
		 				<form:input cssClass="form-control" path="userLst[0].email" name="email" id="email" onkeyup="return forceLower(this);" maxlength="50" placeholder="Email" data-rule-required="true" data-rule-email="true" data-msg-required="Please enter your email address" data-msg-email="Please enter a valid email address"  />
		 			</label>
		 			<span class="k-block k-success-colored" id="emailSuccessSpan"></span>
					<span class="k-block k-error-colored" id="emailErrorSpan"></span>
		 		<td>
		 		<td><label><form:input cssClass="form-control" path="userLst[0].phoneNumber" name="phoneNumber" id="phoneNumber" maxlength="50" placeholder="Phone Number" data-rule-required="true" data-msg-required="Please enter yout Phone Number"/></label><td>
		 	</tr>
			<tr>
		 		<td><label><form:password cssClass="form-control" path="userLst[0].password" name="password" id="password" maxlength="50" placeholder="Password" data-rule-required="true" data-rule-minlength="5" data-msg-required="Please enter your Password"/></label><td>
		 		<td><label><form:password cssClass="form-control" path="userLst[0].confirmPassword" id="confirm_password" name="confirm_password" maxlength="50" placeholder="Confirm Password" data-rule-required="true" data-rule-minlength="5" data-msg-required="Please enter your Password"/></label><td>
		 	</tr>
		 	<tr>
		 		<td><label><form:input cssClass="form-control" path="userLst[0].firstName" name="firstName" id="firstName" maxlength="50" placeholder="First Name" data-rule-required="true" data-msg-required="Please enter yout First Name"/></label>
		 		
		 		<td>
		 		<td><label><form:input cssClass="form-control" path="userLst[0].lastName" name="lastName" id="lastName" maxlength="50" placeholder="Last Name" data-rule-required="true" data-msg-required="Please enter yout Last Name"/></label><td>
		 	</tr>
		 	<tr>
		 		<td><label><form:input cssClass="form-control" style="width: 430px;" path="userLst[0].firstName" name="dob" id="dob" maxlength="50" placeholder="D.O.B" data-rule-required="true" data-msg-required="Please select D.O.B"/></label></td>
		 		<td></td>
		 		<td><td>
		 	</tr>
		 	
		<%-- <label><form:input cssClass="form-control" path="userLst[0].passwordHint" name="passwordHint" id="passwordHint" maxlength="50" placeholder="passwordHint"/></label> --%>	
		<%-- <label><form:input cssClass="form-control" path="passwordHint" name="passwordHint" id="passwordHint" maxlength="50" placeholder="Password Hint"/></label> --%>
		<%-- <label><form:input cssClass="form-control" path="userLst[0].website" name="website" id="website" maxlength="50" placeholder="Website"/></label> --%>
		
		 <tr>
		 		<td colspan="2"><h2><span>ADDRESS</span></h2> </td>
		 	</tr>
		 <tr>
		 		<%-- <td><label><form:input cssClass="form-control" path="addressLine1" name="addressLine1" id="addressLine1" maxlength="50" placeholder="Address 1" data-rule-required="true" data-msg-required="Please enter yout Address 1"/></label><td>
		 		<td><label><form:input cssClass="form-control" path="addressLine2" name="addressLine2" id="addressLine2" maxlength="50" placeholder="Address 2" /></label><td> --%>
		 		<td><select size="1" id="state" name="state" style="width: 430px;">
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
						<option value="CA">California</option>
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
						<option value="TX" selected="">Texas</option>
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
						</select></td><td></td>
					  <td>				  
					  <select id="contry" name="contry" size="1" style="width: 430px;">
							<option value="">--Select Country--</option>
							<option value="AF">AFGHANISTAN</option>
							<option value="AL">ALBANIA</option>
							<option value="DZ">ALGERIA</option>
							<option value="AS">AMERICAN SAMOA</option>
							<option value="AD">ANDORRA</option>
							<option value="AO">ANGOLA</option>
							<option value="AI">ANGUILLA</option>
							<option value="AQ">ANTARCTICA</option>
							<option value="AG">ANTIGUA AND BARBUDA</option>
							<option value="AR">ARGENTINA</option>
							<option value="AM">ARMENIA</option>
							<option value="AW">ARUBA</option>
							<option value="AU">AUSTRALIA</option>
							<option value="AT">AUSTRIA</option>
							<option value="AZ">AZERBAIJAN</option>
							<option value="BS">BAHAMAS</option>
							<option value="BH">BAHRAIN</option>
							<option value="BD">BANGLADESH</option>
							<option value="BB">BARBADOS</option>
							<option value="BY">BELARUS</option>
							<option value="BE">BELGIUM</option>
							<option value="BZ">BELIZE</option>
							<option value="BJ">BENIN</option>
							<option value="BM">BERMUDA</option>
							<option value="BT">BHUTAN</option>
							<option value="BO">BOLIVIA</option>
							<option value="BA">BOSNIA AND HERZEGOVINA</option>
							<option value="BW">BOTSWANA</option>
							<option value="BV">BOUVET ISLAND</option>
							<option value="BR">BRAZIL</option>
							<option value="IO">BRITISH INDIAN OCEAN TERRITORY</option>
							<option value="BN">BRUNEI DARUSSALAM</option>
							<option value="BG">BULGARIA</option>
							<option value="BF">BURKINA FASO</option>
							<option value="BI">BURUNDI</option>
							<option value="KH">CAMBODIA</option>
							<option value="CM">CAMEROON</option>
							<option value="CA">CANADA</option>
							<option value="CV">CAPE VERDE</option>
							<option value="KY">CAYMAN ISLANDS</option>
							<option value="CF">CENTRAL AFRICAN REPUBLIC</option>
							<option value="TD">CHAD</option>
							<option value="CL">CHILE</option>
							<option value="CN">CHINA</option>
							<option value="CX">CHRISTMAS ISLAND</option>
							<option value="CC">COCOS (KEELING) ISLANDS</option>
							<option value="CO">COLOMBIA</option>
							<option value="KM">COMOROS</option>
							<option value="CG">CONGO (BRAZZAVILLE)</option>
							<option value="CD">CONGO (KINSHASA)</option>
							<option value="CK">COOK ISLANDS</option>
							<option value="CR">COSTA RICA</option>
							<option value="HR">CROATIA</option>
							<option value="CU">CUBA</option>
							<option value="CY">CYPRUS</option>
							<option value="CZ">CZECH REPUBLIC</option>
							<option value="CI">CÔTE D'IVOIRE</option>
							<option value="DK">DENMARK</option>
							<option value="DJ">DJIBOUTI</option>
							<option value="DM">DOMINICA</option>
							<option value="DO">DOMINICAN REPUBLIC</option>
							<option value="EC">ECUADOR</option>
							<option value="EG">EGYPT</option>
							<option value="SV">EL SALVADOR</option>
							<option value="GQ">EQUATORIAL GUINEA</option>
							<option value="ER">ERITREA</option>
							<option value="EE">ESTONIA</option>
							<option value="ET">ETHIOPIA</option>
							<option value="FK">FALKLAND ISLANDS (MALVINAS)</option>
							<option value="FO">FAROE ISLANDS</option>
							<option value="FJ">FIJI</option>
							<option value="FI">FINLAND</option>
							<option value="FR">FRANCE</option>
							<option value="GF">FRENCH GUIANA</option>
							<option value="TF">FRENCH SOUTHERN TERRITORIES</option>
							<option value="GA">GABON</option>
							<option value="GM">GAMBIA</option>
							<option value="GE">GEORGIA</option>
							<option value="DE">GERMANY</option>
							<option value="GH">GHANA</option>
							<option value="GI">GIBRALTAR</option>
							<option value="GR">GREECE</option>
							<option value="GL">GREENLAND/GROENLAND</option>
							<option value="GD">GRENADA</option>
							<option value="GP">GUADELOUPE</option>
							<option value="GU">GUAM</option>
							<option value="GT">GUATEMALA</option>
							<option value="GN">GUINEA</option>
							<option value="GW">GUINEA BISSAU</option>
							<option value="GY">GUYANA</option>
							<option value="HT">HAITI</option>
							<option value="HM">HEARD ISLAND AND MCDONALD ISLANDS</option>
							<option value="VA">HOLY SEE (VATICAN CITY STATE)</option>
							<option value="HN">HONDURAS</option>
							<option value="HK">HONG KONG</option>
							<option value="HU">HUNGARY</option>
							<option value="IS">ICELAND</option>
							<option value="IN">INDIA</option>
							<option value="ID">INDONESIA</option>
							<option value="IR">IRAN (ISLAMIC REPUBLIC OF)</option>
							<option value="IQ">IRAQ</option>
							<option value="IE">IRELAND</option>
							<option value="IL">ISRAEL</option>
							<option value="IT">ITALY</option>
							<option value="JM">JAMAICA</option>
							<option value="JP">JAPAN</option>
							<option value="JO">JORDAN</option>
							<option value="KZ">KAZAKHSTAN</option>
							<option value="KE">KENYA</option>
							<option value="KI">KIRIBATI</option>
							<option value="KP">KOREA, DEMOCRATIC PEOPLE'S REPUBLIC OF</option>
							<option value="KR">KOREA, REPUBLIC OF</option>
							<option value="KW">KUWAIT</option>
							<option value="KG">KYRGYZSTAN</option>
							<option value="LA">LAO PEOPLE'S DEMOCRATIC REPUBLIC</option>
							<option value="LV">LATVIA</option>
							<option value="LB">LEBANON</option>
							<option value="LS">LESOTHO</option>
							<option value="LR">LIBERIA</option>
							<option value="LY">LIBYAN ARAB JAMAHIRIYA</option>
							<option value="LI">LIECHTENSTEIN</option>
							<option value="LT">LITHUANIA</option>
							<option value="LU">LUXEMBOURG</option>
							<option value="MO">MACAO</option>
							<option value="MK">MACEDONIA, THE FORMER YUGOSLAV REPUBLIC OF</option>
							<option value="MG">MADAGASCAR</option>
							<option value="MW">MALAWI</option>
							<option value="MY">MALAYSIA</option>
							<option value="MV">MALDIVES</option>
							<option value="ML">MALI</option>
							<option value="MT">MALTA</option>
							<option value="MH">MARSHALL ISLANDS</option>
							<option value="MQ">MARTINIQUE</option>
							<option value="MR">MAURITANIA</option>
							<option value="MU">MAURITIUS</option>
							<option value="YT">MAYOTTE</option>
							<option value="MX">MEXICO</option>
							<option value="FM">MICRONESIA (FEDERATED STATES OF)</option>
							<option value="MD">MOLDOVA, REPUPLIC OF</option>
							<option value="MC">MONACO</option>
							<option value="MN">MONGOLIA</option>
							<option value="MS">MONTSERRAT</option>
							<option value="MA">MOROCCO</option>
							<option value="MZ">MOZAMBIQUE</option>
							<option value="MM">MYANMAR</option>
							<option value="NA">NAMIBIA</option>
							<option value="NR">NAURU</option>
							<option value="NP">NEPAL</option>
							<option value="NL">NETHERLANDS</option>
							<option value="AN">NETHERLANDS ANTILLES</option>
							<option value="NZ">NEW ZEALAND</option>
							<option value="NI">NICARAGUA</option>
							<option value="NE">NIGER</option>
							<option value="NG">NIGERIA</option>
							<option value="NU">NIUE</option>
							<option value="NF">NORFOLK ISLAND</option>
							<option value="MP">NORTHERN MARIANA ISLANDS</option>
							<option value="NO">NORWAY</option>
							<option value="OM">OMAN</option>
							<option value="PK">PAKISTAN</option>
							<option value="PS">PALESTINIAN TERRITORY</option>
							<option value="PA">PANAMA</option>
							<option value="PG">PAPUA NEW GUINEA</option>
							<option value="PY">PARAGUAY</option>
							<option value="PE">PERU</option>
							<option value="PH">PHILIPPINES</option>
							<option value="PN">PITCAIRN</option>
							<option value="PL">POLAND</option>
							<option value="PT">PORTUGAL</option>
							<option value="PR">PUERTO RICO</option>
							<option value="QA">QATAR</option>
							<option value="RO">ROMANIA</option>
							<option value="RU">RUSSIA</option>
							<option value="RW">RWANDA</option>
							<option value="SH">SAINT HELENA</option>
							<option value="KN">SAINT KITTS AND NEVIS</option>
							<option value="LC">SAINT LUCIA</option>
							<option value="PM">SAINT PIERRE AND MIQUELON</option>
							<option value="VC">SAINT VINCENT AND THE GRENADINES</option>
							<option value="WS">SAMOA</option>
							<option value="ST">SAO TOME AND PRINCIPE</option>
							<option value="SA">SAUDI ARABIA</option>
							<option value="SN">SENEGAL</option>
							<option value="CS">SERBIA AND MONTENEGRO</option>
							<option value="SC">SEYCHELLES</option>
							<option value="SL">SIERRA LEONE</option>
							<option value="SG">SINGAPORE</option>
							<option value="SK">SLOVAKIA</option>
							<option value="SI">SLOVENIA</option>
							<option value="SB">SOLOMON ISLANDS</option>
							<option value="SO">SOMALIA</option>
							<option value="ZA">SOUTH AFRICA</option>
							<option value="GS">SOUTH GEORGIA AND THE SOUTH SANDWICH ISLANDS</option>
							<option value="ES">SPAIN</option>
							<option value="LK">SRI LANKA</option>
							<option value="SD">SUDAN</option>
							<option value="SR">SURINAME</option>
							<option value="SJ">SVALBARD AND JAN MAYEN</option>
							<option value="SZ">SWAZILAND</option>
							<option value="SE">SWEDEN</option>
							<option value="CH">SWITZERLAND</option>
							<option value="SY">SYRIAN ARAB REPUBLIC</option>
							<option value="TW">TAIWAN</option>
							<option value="TJ">TAJIKISTAN</option>
							<option value="TZ">TANZANIA, UNITED REPUBLIC OF</option>
							<option value="TH">THAILAND</option>
							<option value="TL">TIMOR-LESTE</option>
							<option value="TG">TOGO</option>
							<option value="TK">TOKELAU</option>
							<option value="TT">TRINIDAD AND TOBAGO</option>
							<option value="TN">TUNISIA</option>
							<option value="TR">TURKEY</option>
							<option value="TM">TURKMENISTAN</option>
							<option value="TC">TURKS AND CAICOS ISLANDS</option>
							<option value="TV">TUVALU</option>
							<option value="UG">UGANDA</option>
							<option value="UA">UKRAINE</option>
							<option value="AE">UNITED ARAB EMIRATES</option>
							<option value="GB">UNITED KINGDOM</option>
							<option value="USA" selected="">UNITED STATES</option>
							<option value="UM">UNITED STATES MINOR OUTLYING ISLANDS</option>
							<option value="UY">URUGUAY</option>
							<option value="UZ">UZBEKISTAN</option>
							<option value="VU">VANUATU</option>
							<option value="VE">VENEZUELA</option>
							<option value="VN">VIETNAM</option>
							<option value="VG">VIRGIN ISLANDS, BRITISH</option>
							<option value="VI">VIRGIN ISLANDS, U.S.</option>
							<option value="WF">WALLIS AND FUTUNA</option>
							<option value="EH">WESTERN SAHARA</option>
							<option value="YE">YEMEN</option>
							<option value="ZM">ZAMBIA</option>
							<option value="ZW">ZIMBABWE</option>
							</select>
							</td>
		 </tr>
		 	<tr>
		 		<td><label><form:input cssClass="form-control" path="city" name="city" id="city" maxlength="50" placeholder="City" data-rule-required="true" data-msg-required="Please enter yout City"/></label><td>
		 		<td><label><form:input cssClass="form-control" path="state" name="state" id="state" maxlength="50" placeholder="State" data-rule-required="true" data-msg-required="Please enter yout Last Name"/></label><td>
		 	</tr>
		<%-- <label><form:input cssClass="form-control" path="country" name="country" id="country" maxlength="50" placeholder="Country"/></label> --%>
		
		</table>
		<form:input style="display:none;" path="tcDescription" id="userTC" value="${waiversAndTC.tcDescription}" />		
		
		<%-- <form:input style="display:none;"  path="membership.productId" maxlength="50" id="memProductId"/>
		<form:input style="display:none;"  path="membership.pricingRuleId" maxlength="50" id="memPricingRuleId"/>
		<form:input style="display:none;"  path="membership.membershipPrice" maxlength="50" id="memMembershipPrice"/>	
		<form:input style="display:none;"  path="membership.membershipType" maxlength="50" id="memMembershipType"/>
		<form:input style="display:none;"  path="membership.membershipDuration" maxlength="50" id="memMembershipDuration"/>	 --%>
		<%-- <form:input style="display:none;" path="membership.productId" id="memProductId" maxlength="50"/>
        <form:input style="display:none;" path="membership.pricingRuleId" id="memPricingRuleId" maxlength="50"/>
         --%>
        <%--  <label><form:input cssClass="form-control" style="display:none;" path="name" name="name" id="name" maxlength="50" value="Nikhil"/></label> --%>
					                       
		<%-- <table border="1">
			<tbody>
				<tr>
					<td>User Name : </td><td><label><form:input cssClass="form-control" path="username" name="username" id="username" maxlength="50"/></label></td>
				</tr>
				 <tr>
					<td>Password : </td><td><form:password cssClass="form-control" path="password" name="password" id="password" maxlength="50"/></td>
				</tr>
				<tr>
					<td>Password Hint : </td><td><form:input cssClass="form-control" path="passwordHint" name="passwordHint" id="passwordHint" maxlength="50"/></td>
				</tr>
				<tr>
					<td>First Name : </td><td><form:input cssClass="form-control" path="firstName" name="firstName" id="firstName" maxlength="50"/></td>
				</tr>
				<tr>
					<td>Last Name : </td><td><form:input cssClass="form-control" path="lastName" name="lastName" id="lastName" maxlength="50"/></td>
				</tr>
				<tr>
					<td>Email : </td><td><form:input cssClass="form-control" path="email" name="email" id="email" maxlength="50"/></td>
				</tr>
				<tr>
					<td>Phone Number : </td><td><form:input cssClass="form-control" path="phoneNumber" name="phoneNumber" id="phoneNumber" maxlength="50"/></td>
				</tr>
				<tr>
					<td>Website : </td><td><form:input cssClass="form-control" path="website" name="website" id="website" maxlength="50"/></td>
				</tr>
				<tr>
					<td>Address : </td><td><form:input cssClass="form-control" path="addressLine1" name="addressLine1" id="addressLine1" maxlength="50"/></td>
				</tr>
				<tr>
					<td>Address : </td><td><form:input cssClass="form-control" path="addressLine2" name="addressLine2" id="addressLine2" maxlength="50"/></td>
				</tr>			
				<tr>
					<td>City : </td><td><form:input cssClass="form-control" path="city" name="city" id="city" maxlength="50"/></td>
				</tr>
				<tr>
					<td>State : </td><td><form:input cssClass="form-control" path="state" name="state" id="state" maxlength="50"/></td>
				</tr>
				<tr>
					<td>Postal Code : </td><td><form:input cssClass="form-control" path="postalCode" name="postalCode" id="postalCode" maxlength="50"/></td>
				</tr>
				
				<tr>
					<td>Country : </td><td><form:input cssClass="form-control" path="country" name="country" id="country" maxlength="50"/></td>
				</tr>			
				<form:input style="display:none;" path="membership.productId" id="memProductId" maxlength="50"/>
		        <form:input style="display:none;" path="membership.pricingRuleId" id="memPricingRuleId" maxlength="50"/>
		        <form:input style="display:none;" path="membership.membershipPrice" id="memMembershipPrice" maxlength="50"/> 
		        <form:input style="display:none;" path="tcDescription" id="userTC" maxlength="50"/>  					
			</tbody>
		</table>  --%>
		
		
		 
		 <%-- <c:forEach begin="0" end="1" step="1" varStatus="gridRow">
			<spring:bind path="account.user[${gridRow.index}].username">
			  
			  <form:input  path="user[${gridRow.index}].username" id="username"> </form:input>
			 <form:input cssClass="form-control" path="account.user[${gridRow.index}].firstName" id="firstName" maxlength="50"/>
			  </spring:bind>
			
		</c:forEach> --%>
		
		<%-- <spring:bind path="account.user[0]">
        <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
        </spring:bind> --%>
            <%-- <ymca:label styleClass="control-label" key="user.username"/> 
            <form:input cssClass="form-control" path="user[0].username" id="username" autofocus="true"/> --%>
            <%-- <form:errors path="user[0].username" cssClass="help-block"/>
        </div> --%>
         <%-- <spring:bind path="account.user[0].firstName">
            <div class="col-sm-6 form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
            </spring:bind>
                <ymca:label styleClass="control-label" key="user.firstName"/>
                <form:input cssClass="form-control" path="user[0].firstName" id="firstName" maxlength="50"/>
                <form:errors path="user[0].firstName" cssClass="help-block"/>
            </div> --%>
        <!-- <button type="submit" class="btn btn-primary" name="save" onclick="bCancel=false">
        	<i class="icon-ok icon-white"></i> Submit
        </button> -->
	</form:form>
	<input style="display:none;"   maxlength="50" id="memProductName"/>
	<input style="display:none;"   maxlength="50" id="memProductDesc"/>        
	</section>
	
	<h2>Terms And Conditions</h2>  
      <section>
        <div class="terms">${waiversAndTC.tcDescription}</div>
        <input type="checkbox" name="chkTermsAndCond" style="margin-top: 10px;">&nbsp;I accept terms and conditions
		</section>
		<h2>Summary</h2>
		<section>
			<div>
				
					<table border="1" cellpadding="7" id="summaryData">
						<thead>
							<tr>
								<th>Field</th>
								<th>Value</th>									
							</tr>
						</thead>
						<tbody id="summaryDataBody" style="min-height: 300px;">
							<!--<tr>
								<td width="30%">Product Id</td>
								<td id="sumProductId"></td>
							</tr>
							<tr>
								<td>Product Name</td>
								<td id="sumProductName"></td>
							</tr>
							 <tr>
								<td>Product Description</td>
								<td id="sumProductDesc"></td>
							</tr>
							<tr>
								<td>Product Type</td>
								<td id="sumProductType"></td>
							</tr> 
							<tr>
								<td>Product Duration</td>
								<td id="sumProductDuration"></td>
							</tr>
							<tr>
								<td>Membership Price</td>
								<td id="sumMembershipPrice"></td>
							</tr>						
							<tr>
								<td>User Name</td>
								<td id="sumUsername"></td>
							</tr> 
							<tr>
								<td>Password Hint</td>
								<td id="sumPasswordHint"></td>
							</tr> -->
							<tr>
								<td>First Name</td>
								<td id="sumFirstName"></td>
							</tr>
							<tr>
								<td>Last Name</td>
								<td id="sumLastName"></td>
							</tr>
							<tr>
								<td>Email</td>
								<td id="sumEmail"></td>
							</tr>
							<tr>
								<td>PhoneNumber</td>
								<td id="sumPhoneNumber"></td>
							</tr>
							<!-- <tr>
								<td>Website</td>
								<td id="sumWebsite"></td>
							</tr> -->
							
							<tr>
								<td>Address</td>
								<td id="sumAddress"></td>
							</tr>
							<tr>
								<td>City</td>
								<td id="sumCity"></td>
							</tr>
							<tr>
								<td>State</td>
								<td id="sumState"></td>
							</tr>
							<!-- <tr>
								<td>Zip</td>
								<td id="sumZip"></td>
							</tr> -->
							<!-- <tr>
								<td>Country</td>
								<td id="sumCountry"></td>
							</tr> -->
							<!-- <tr>
								<td style="vertical-align:top;">Terms And Condition</td>
								<td id="sumTc"></td>
							</tr> -->
						</tbody>
					</table>
			</div>
		</section>
</div><br /><br />

</div>
</div>

<br />
</div>
        

