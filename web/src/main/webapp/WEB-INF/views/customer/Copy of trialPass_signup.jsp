<%@ include file="../../layouts/include_taglib.jsp" %>
<%

String contextPath = request.getContextPath();

%>
<script type="text/javascript" src="<%=contextPath %>/resources/js/steps/modernizr-2.6.2.min.js"></script>
<script type="text/javascript" src="<%=contextPath %>/resources/js/steps/jquery.steps.js"></script>
<script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.13.0/jquery.validate.min.js"></script>
<script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.13.0/additional-methods.min.js"></script>

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
    $(document).ready(function ()
    {
    	//$("#signupForm").validate();
    	$("#signupForm").validate({
			rules: {
				password: {
					required: true,
					minlength: 5
				},
				confirm_password: {
					required: true,
					minlength: 5,
					equalTo: "#password"
				}
			},
			messages: {
				password: {
					required: "Please provide a password",
					minlength: "Your password must be at least 5 characters long"
				},
				confirm_password: {
					required: "Please provide a password",
					minlength: "Your password must be at least 5 characters long",
					equalTo: "Please enter the same password as above"
				}
			}
		});
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
                if (currentIndex === 0){                  	
                    if(!$("input[name='termsAndCondition']:checked").val()){                    	
                    	//alert("Please select the Product before Proceeding");  
                    	//$("#errorDialogMessage").text("Please select the Product before Proceeding");
		  	  			//$("#errorGenericDialogBox").dialog( "open" );
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
				         
                    	return true;
                    }else{
                    	if(newIndex === 1){
                    		$('.wizard > .content').css("min-height", "40em");
                    	}else{
                    		$('.wizard > .content').css("min-height", "28em");
                    	}
                    	return true;
                    }
                }
                if (currentIndex === 1){                 	
                	if ($('#signupForm').valid()){
                		if(newIndex === 2){
                    		$('.wizard > .content').css("min-height", "28em");
                    	}else{
                    		$('.wizard > .content').css("min-height", "40em");
                    	}
                		return true;
                	}else{
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
                if (currentIndex === 2){          
                	
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
                    	if(newIndex === 3){
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
        });
        
        
        
        $("#membershipData").kendoGrid({                
            columns: [
				{ field: "field0", title: "" },
                { field: "field1", title: "ID" },
                { field: "field2", title: "Product Name" },
                { field: "field3", title: "Product Description" },
                { field: "field4", title: "Product Type" },
                { field: "field5", title: "Product Duration" },
                { field: "field6", title: "Product Price" }
            ]
        });    
        
        $('.termsAndCondition').on( "click", function() {        	
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
        });
         
    });
    
    function checkEmail() {	
		$(".k-loading-mask").show();
		$(".k-loading-text").html("Please wait while the System validates the Email...");		
		var email = $("#email" ).val();
		$.ajax({
			  type: "GET",
			  url: "isEmailExists/"+email,				  
			  success: function( data ) {				  	  
			  	  if(data=='Not-Exists'){
				  	  $("#emailErrorSpan").css("display", "none");		
					  $("#emailErrorSpan" ).html("");							 
				  	  $("#emailSuccessSpan").css("display", "block");		
					  $("#emailSuccessSpan" ).html("Email Validated successfully");					  	 
				  	  
			  	  }else if(data == 'Exists'){
			  		  $("#emailSuccessSpan").css("display", "none");		
					  $("#emailSuccessSpan" ).html("");	
					  $("#emailErrorSpan").css("display", "block");		
					  $( "#emailErrorSpan" ).html("Email Exists. Please try with other Email Addess.");
					  $(".k-loading-mask").hide();
			  	  }else if(data == 'EMAIL-INVALID'){
			  		  $("#emailSuccessSpan").css("display", "none");		
					  $("#emailSuccessSpan" ).html("");	
					  $("#emailErrorSpan").css("display", "block");		
					  $( "#emailErrorSpan" ).html("Please Enter the E-mail.");
					  $(".k-loading-mask").hide();
			  	  }else {
			  		  $("#emailSuccessSpan").css("display", "none");		
					  $("#emailSuccessSpan" ).html("");	
					  $("#emailErrorSpan").css("display", "block");		
					  $( "#emailErrorSpan" ).html("There was some error. Please try again later");
					  $(".k-loading-mask").hide();
			  	  }					  
			  },
			  error: function( xhr,status,error ){
				  $("#emailSuccessSpan").css("display", "none");		
				  $("#emailSuccessSpan" ).html("");	
				  $("#emailErrorSpan").css("display", "block");		
				  $( "#emailErrorSpan" ).html("There was some error. Please try again later");
				  $(".k-loading-mask").hide();
			  }
		});
		
	}	
</script>

<div id="signup_wizard">
   <h2>Product Information </h2>   
   <section>
   
<table border="1" id="membershipData">
	<thead>
		<tr>
			<th>&nbsp;</th>
			<th>ID</th>
			<th>Product Name</th>
			<th>Product Description</th>
			<th>Product Type</th>
			<th>Product Duration</th>
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
				<td>${product.productType }</td>
				<td>${product.duration }</td>
				<td>$ 0.00</td>
				<%-- <td>${product.pricingRule.tierPrice }</td> --%>
				
				
            </tr>
        </c:forEach>	
	</tbody>
</table>  
</section>
	<br />

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
	<form:form commandName="account" method="post" action="trialPassRegister" id="signupForm" autocomplete="off"
               class="bootstrap-frm cmxform" onsubmit="">        
        
		 <table border="0">
		 	<tr>
		 		<td colspan="2"><h2><span>USER INFORMATION</span></h2> </td>
		 	</tr>
			<tr id="emailTr">		 		
		 		<td>
		 			<label>
		 				<form:input cssClass="form-control" path="userLst[0].email" name="email" id="email" maxlength="50" placeholder="Email" data-rule-required="true" data-rule-email="true" data-msg-required="Please enter your email address" data-msg-email="Please enter a valid email address" onblur="checkEmail()"/>
		 			</label>
		 			<!-- <span id="checkEmailBtn" class="k-button" style="width: 100px; text-shadow: none;">Check Eamil</span> -->
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
		 	
		<%-- <label><form:input cssClass="form-control" path="userLst[0].passwordHint" name="passwordHint" id="passwordHint" maxlength="50" placeholder="passwordHint"/></label> --%>	
		<%-- <label><form:input cssClass="form-control" path="passwordHint" name="passwordHint" id="passwordHint" maxlength="50" placeholder="Password Hint"/></label> --%>
		<%-- <label><form:input cssClass="form-control" path="userLst[0].website" name="website" id="website" maxlength="50" placeholder="Website"/></label> --%>
		
		 <tr>
		 		<td colspan="2"><h2><span>ADDRESS</span></h2> </td>
		 	</tr>
		 <tr>
		 		<td><label><form:input cssClass="form-control" path="addressLine1" name="addressLine1" id="addressLine1" maxlength="50" placeholder="Address 1" data-rule-required="true" data-msg-required="Please enter yout Address 1"/></label><td>
		 		<td><label><form:input cssClass="form-control" path="addressLine2" name="addressLine2" id="addressLine2" maxlength="50" placeholder="Address 2" data-rule-required="true" data-msg-required="Please enter yout Address 2"/></label><td>
		 	</tr>
		 	<tr>
		 		<td><label><form:input cssClass="form-control" path="city" name="city" id="city" maxlength="50" placeholder="City" data-rule-required="true" data-msg-required="Please enter yout City"/></label><td>
		 		<td><label><form:input cssClass="form-control" path="state" name="state" id="state" maxlength="50" placeholder="State" data-rule-required="true" data-msg-required="Please enter yout Last Name"/></label><td>
		 	</tr>
		<%-- <label><form:input cssClass="form-control" path="country" name="country" id="country" maxlength="50" placeholder="Country"/></label> --%>
		
		</table>
		<form:input style="display:none;" path="tcDescription" id="userTC"/>		
		
		<input style="display:none;"   maxlength="50" id="memProductId"/>
		<input style="display:none;"   maxlength="50" id="memPricingRuleId"/>
		<input style="display:none;"  maxlength="50" id="memMembershipPrice"/>	
		<input style="display:none;"   maxlength="50" id="memMembershipType"/>
		<input style="display:none;"   maxlength="50" id="memMembershipDuration"/>	 
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
        <div class="terms"></div>
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
							<tr>
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
							<!-- <tr>
								<td>User Name</td>
								<td id="sumUsername"></td>
							</tr> -->
							<!-- <tr>
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


<br />
</div>
        

