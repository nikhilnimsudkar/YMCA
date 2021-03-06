<%@ include file="../../layouts/include_taglib.jsp" %>
<%

String contextPath = request.getContextPath();

%>

<script src="<%=contextPath %>/resources/js/jquery.validate.min.js"></script>
<script src="<%=contextPath %>/resources/js/additional-methods.min.js"></script>

<link href="<%=contextPath %>/resources/css/smart_wizard.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<%=contextPath %>/resources/js/jquery.smartWizard.js"></script>
<script src="<%=contextPath %>/resources/js/maskedinput.min.js"></script>

<script id="terms-conditions-ErrorBox" type="text/x-kendo-template">
    <p class="error-message-p">Please agree the terms and conditions before Proceeding</p>
	
    <div class="confirmbutton" align="center"><button class="confirm-terms-conditions k-button" style=" width: 35%;">Ok</button>   
</script>
<script type="text/javascript">
var emailValid = "false";

$(document).ready(function(){
	$("#page_name").text("Registration Wizard");
	$("#phoneNumber").mask("(999) 999-9999");
    $("#dob").kendoDatePicker();
   
    
    
    $("#addConEthnicitySelectId").kendoDropDownList();
    var Dropdownlist = $("#addConEthnicitySelectId").data("kendoDropDownList");
    Dropdownlist.select(0);
    $("#addConEthnicitySelectId").on('change',function(e){  
        if(Dropdownlist.text() != '--Select Ethnicity--'){
       // alert("{ change}"+Dropdownlist);
        var x = $(this).val();
       // alert(x);
      	$("#addConEthnicityTxt").val(x);
      //	alert($("#addConEthnicityTxt").val());
       
        }else if(Dropdownlist.text() == '--Select Ethnicity--'){
            
           // var x = $(this).val();
           // alert(x);
         //  alert($("#addConEthnicityTxt").val());
          	$("#addConEthnicityTxt").val('');
          // alert($("#addConEthnicityTxt").val());
        }
       
    });
    
    
    
    var dobYearForm = new Date().getFullYear();
    dobYearForm = parseInt(dobYearForm.toString());
    for(var i=0; i<100 ; i++){
    	$('#dobYearForm').append($('<option>', {value: dobYearForm,text: dobYearForm}));
    	dobYearForm = dobYearForm - 1;
    }
    $("#dobYearForm").kendoDropDownList();    
    $("#dobDateForm").kendoDropDownList();
    $("#dobMonthForm").kendoDropDownList();
    
    var dobMonthForm = $("#dobMonthForm").val();
	var dobDateForm = $("#dobDateForm").val();
	var dobYearForm = $("#dobYearForm").val();
			
	$("#dob").val(dobMonthForm+"/"+dobDateForm+"/"+dobYearForm);
    /* $("#email").on('change', function (){	 
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
        				   				        
        				  emailValid = "true";
        			  	  
        		  	  }else if(data == 'Exists'){
        		  		  $("#emailSuccessSpan").css("display", "none");		
        				  $("#emailSuccessSpan" ).html("");	
        				  $("#emailErrorSpan").css("display", "block");	
        				  $("#email").css("border-color", "red");
        				  $( "#emailErrorSpan" ).html("You appear to be associated with a customer account already.  Please speak with the primary contact for that account");
        				       				      
        				  emailValid = "false";	        				  
        				  $(".k-loading-mask").hide();
        		  	  }else if(data == 'EMAIL-INVALID'){
        		  		  $("#emailSuccessSpan").css("display", "none");		
        				  $("#emailSuccessSpan" ).html("");	
        				  $("#emailErrorSpan").css("display", "block");	
        				  $("#email").css("border-color", "red");
        				  $( "#emailErrorSpan" ).html("Please Enter the E-mail.");
        				  
        				  emailValid = "false";
        				  $(".k-loading-mask").hide();
        		  	  }else {
        		  		  $("#emailSuccessSpan").css("display", "none");		
        				  $("#emailSuccessSpan" ).html("");	
        				  $("#emailErrorSpan").css("display", "block");		
        				  $("#email").css("border-color", "red");
        				  $( "#emailErrorSpan" ).html("There was some error. Please try again later");
        				  
        				  emailValid = "false";
        				  $(".k-loading-mask").hide();
        		  	  }					  
        		  },
        		  error: function( xhr,status,error ){
        			  $("#emailSuccessSpan").css("display", "none");		
        			  $("#emailSuccessSpan" ).html("");	
        			  $("#emailErrorSpan").css("display", "block");		
        			  $( "#emailErrorSpan" ).html("There was some error. Please try again later");        			  	
    				  $("#email").css("border-color", "red");
    				  
        			  emailValid = "false";
        			  $(".k-loading-mask").hide();
        		  }
        	});	
    	}
    	
    });
     */
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
			$label.addClass("success");
			//$("#wizard"). smartWizard("fixHeight");
			
		},
		rules: {
			"tcname": {
				required: true,
				minlength: 2,
				//maxlength: 20,
				check_fName_lName : true
			}
		},
		messages: {
			"tcname": {				
				required: "Please enter your Full Name",
				minlength: "Full Name must consist of at least 2 characters",
				//maxlength: "Full Name can not be more than 20 characters",
				check_fName_lName : "Please enter the primary contact's First Name and Last Name."
			}
		}
	});	
	
	$.validator.addMethod("check_fName_lName", function(value, element) {
		var firstName = $("#firstName").val();
		var lastName = $("#lastName").val();
		var fullName = firstName.trim() + " " + lastName.trim();
		var tcname = $("#tcname").val().trim();
		if(tcname != fullName){
			return false;
		} else{
			return true;
		}
		
	}, "Please enter the primary contact's First Name and Last Name.");
	
	
	var url = "isEmailExists";
	var urlPrimary = "isEmailExistsPrimaryUser";
	var validator1 = $("#signupForm").validate({
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
			"userLst[0].firstName": {
				required: true,
				minlength: 2,
				maxlength: 20
			},
			"userLst[0].lastName": {
				required: true,
				minlength: 2,
				maxlength: 20
			},
			"userLst[0].emailAddress" : {
				required: true,
				validate_email_format: true,				
                remote: {
                    url: url,  
                    type:"GET"                    	
                }
                
			},
			<c:if test="${sessionScope.agentUid == null}">
			
			"userLst[0].password": {
				required: true,
				minlength: 5
			},
			"userLst[0].confirmPassword": {
				required: true,
				minlength: 5,
				equalTo: "#password"
			},
			</c:if>
			"userLst[0].dateOfBirth": { 
				required: true,
				check_date_of_birth: true
			},
			"postalCode" : {
				required: true,
				number: true,
				minlength: 5
			},
			"userLst[0].ethnicity": {
				required: true
			},
			"userLst[0].formattedPhoneNumber" : "required",			
			"addressLine1" : "required",			
			"city" : "required",
			"state" : "required"
			
		},
		messages: {
			"userLst[0].firstName": {				
				required: "Please enter your First Name",
				minlength: "First Name must consist of at least 2 characters"
			},
			
			"userLst[0].lastName": {				
				required: "Please enter your First Name",
				minlength: "Last Name must consist of at least 2 characters"
			},
			"userLst[0].emailAddress" : {
				required: "Please enter your email address",
				validate_email_format : "Please enter valid email address",				
				remote: "You appear to be associated with a customer account already.  Please speak with the primary contact for that account"
				/* ,
				check_Email_valid : "You appear to be associated with a customer account already.  Please speak with the primary contact for that account" */
			},
			"userLst[0].formattedPhoneNumber" : "Please enter your Phone Number",
			<c:if test="${sessionScope.agentUid == null}">
			"userLst[0].password": {
				required: "Please provide a password",
				minlength: "Your password must be at least 5 characters long"
			},
			"userLst[0].confirmPassword": {
				required: "Please provide a password",
				minlength: "Your password must be at least 5 characters long",
				equalTo: "Please enter the same password as above"
			},
			</c:if>
			"userLst[0].dateOfBirth": { 
				required: "Please enter your Date of Birth",
				check_date_of_birth: "You must be "+ $("#adultAgeValidationLowerLimit").html() +" years of age"
			},
			"postalCode" : {
				required: "Please enter Postal Code",
				number: "Please enter numbers only",
				minlength: "Postal Code must consist of at least 5 characters"
			},
			"userLst[0].ethnicity": {
				required: "Please select Ethnicity"
				
			},
			"addressLine1" : "Please enter address line 1",			
			"city" : "Please enter yout City",
			"state" : "Please enter your State",
			agree: "Please accept our policy"
		}
	});	
	//"userLst[0].dateOfBirth" : "required",
	//"userLst[0].dateOfBirth" : "Please enter your Date of Birth",
	$.validator.addMethod("check_date_of_birth", function(value, element) {
		/* var day = $("#dob_day").val();
	    var month = $("#dob_month").val();
	    var year = $("#dob_year").val(); */
	    //11/18/2014
	    var inpDate = $("#dob").val();
	    var day;
    	var month;
    	var year;
    	
    	var adultLowerAgeLimit =  $("#adultAgeValidationLowerLimit").html();
    	var adultUpperAgeLimit =  $("#adultAgeValidationUpperLimit").html();
    	
	    if(inpDate != null){
	    	var dateArr = inpDate.split("/");	    	
	    	month = dateArr[0];
	    	day = dateArr[1];
	    	year = dateArr[2];
	    }
	    var validation  =  false;

	    var adultLowerAge = new Date();
	    adultLowerAge.setFullYear(adultLowerAge.getFullYear() - adultLowerAgeLimit);
	    
	    var adultUpperAge = new Date();
	    adultUpperAge.setFullYear(adultUpperAge.getFullYear() - adultUpperAgeLimit);
	    	
	    	
	    var mydate = new Date();
	    mydate.setFullYear(year, month-1, day);

	    //var currdate = new Date();
	    //currdate.setFullYear(currdate.getFullYear() - age);
	    var upperFlag = false;
	    if(adultUpperAge < mydate){
	    	upperFlag = true;
	    }
	    var lowerFlag = false;
	    if(adultLowerAge > mydate){
	    	lowerFlag = true;
	    }
	    /* if(upperFlag == true && lowerFlag == true){
	    	return true;
	    }else {
	    	return false;
	    } */
	    if(lowerFlag == true){
	    	return true;
	    }else {
	    	return false;
	    }
	    
	}, "You must be "+ $("#adultAgeValidationLowerLimit").html() +" years of age");
	
	$.validator.addMethod("validate_email_format", function(value, element) {	
		var re = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
		var flag = false;
		if(value){			
			flag = re.test(value);
		}else {
			flag = true;
		}
		//console.log(flag);
	    return flag;
	}, "Please enter valid email address");
	
	/* $.validator.addMethod("check_Email_valid", function(value, element) {	
		var email = $("#email" ).val();
		if(email){
			$.ajax({
				  type: "GET",
				  url: "isEmailExists/"+email,				  
				  success: function( data ) {				  	  
				  	  if(data=='Not-Exists'){					  	 
						  return true;					  	  
				  	  }else {
				  		return false;	
				  	  }					  
				  },
				  error: function( xhr,status,error ){
					  return false;	
				  }
			});	
		}

		}, "You appear to be associated with a customer account already.  Please speak with the primary contact for that account");
  		
	$('#email').rules("add", { "check_Email_valid" : true} ); */
      
	});


function registerContact(){	
	//alert(isLoggedInUserAgent());
	var dobMonthForm = $("#dobMonthForm").val();
	var dobDateForm = $("#dobDateForm").val();
	var dobYearForm = $("#dobYearForm").val();
			
	$("#dob").val(dobMonthForm+"/"+dobDateForm+"/"+dobYearForm);
	if(!$("input[name='chkTermsAndCond']:checked").val() && !isLoggedInUserAgent()){
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
	        $('#wizard').smartWizard('setError',{stepnum:2,iserror:true});
			//$('#wizard').smartWizard('showMessage','Please agree the terms and conditions before Proceeding');	
 			return false;
 	}else if(!$("#tcForm").valid()){
 		 $('#wizard').smartWizard('setError',{stepnum:2,iserror:true});
		 return false;
 	}else{
 		$('#wizard').smartWizard('setError',{stepnum:2,iserror:false});
		$('#wizard').smartWizard('hideMessage');
		document.forms["signupForm"].submit();
		return true;
 	}
}

function nextClick(obj, context){	
	//alert("Next Clicked");
	//alert("Leaving step " + context.fromStep + " to go to step " + context.toStep);
	if( context.fromStep == 1 && context.toStep == 2 ){	
		var dobMonthForm = $("#dobMonthForm").val();
		var dobDateForm = $("#dobDateForm").val();
		var dobYearForm = $("#dobYearForm").val();
				
		$("#dob").val(dobMonthForm+"/"+dobDateForm+"/"+dobYearForm);
			
		if(!$("#signupForm").valid()){
			$('#wizard').smartWizard('setError',{stepnum:1,iserror:true});
			//$('#wizard').smartWizard('showMessage','Please correct the Form!');
			return false;
		}else {
			$('#wizard').smartWizard('setError',{stepnum:1,iserror:false});
			$('#wizard').smartWizard('hideMessage');
			/* if(emailValid =="false"){
      		  $("#emailErrorSpan").css("display", "block");	
				  $("#email").css("border-color", "red");
				  $( "#emailErrorSpan" ).html("You appear to be associated with a customer account already.  Please speak with the primary contact for that account");
				  return false;
			} */
			return true;
		}
		
	}/* else if( context.fromStep == 2 && context.toStep == 1 ){	
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
					//$('#wizard').smartWizard('showMessage','Please agree the terms and conditions before Proceeding');			        
	     	return false;
	     }else{
	    	 $('#wizard').smartWizard('setError',{stepnum:2,iserror:false});
				$('#wizard').smartWizard('hideMessage');
	    	 return true;
	     }
	} */else {	
		
		return true;
	}
	
}

function forceLower(strInput) {
	strInput.value=strInput.value.toLowerCase();
}
    
</script>

</head><body>




<table align="center" border="0" cellpadding="0" cellspacing="0">
<tr><td> 
<!-- Smart Wizard -->
        
  		<div id="wizard" class="swMain">
  			<ul>
  				<li><a href="#step-1">
                <!-- <label class="stepNumber">1</label> -->
                <span class="stepDesc">
                  1. USER INFORMATION	<br />
                   <!-- <small>&nbsp;</small> -->
                </span>
            </a></li>
  				<li><a href="#step-2">
               <!--  <label class="stepNumber">2</label> -->
                <span class="stepDesc" style="width: 200px;">
                   2. TERMS AND CONDITIONS<br />
                   <!-- <small>Step 2 description</small> -->
                </span>
            </a></li>
  			</ul>
  			<%-- <span style="display : none;" id="adultAgeValidation">${adultAgeValidation }</span> --%>
  			<span style="display : none;" id="adultAgeValidationLowerLimit">${adultAgeValidationLowerLimit }</span>
  			<span style="display : none;" id="adultAgeValidationUpperLimit">${adultAgeValidationUpperLimit }</span>  			  			
  			<div id="step-1">	
            <P>
            	<form:form commandName="account" method="post" action="register" id="signupForm" style="border : none; padding : 0px; margin : 0px;" autocomplete="off"
			              class="bootstrap-frm cmxform" >			        
					 <table border="0" width="100%" id="userInfoTable">
					 	<tr>
					 		<td colspan="2"><h2 style="margin-top: 5px; margin-bottom : 5px;"><span>USER INFORMATION</span></h2> </td><td >&nbsp;</td><td >&nbsp;</td><td >&nbsp;</td>
					 	</tr>
					 	<tr>
					 		<td><form:input cssClass="form-control" path="userLst[0].firstName" title="Please enter yout First Name" name="firstName" id="firstName" maxlength="50" placeholder="First Name"  /></td>					 		
					 		<td>&nbsp;</td>
					 		<td><form:input cssClass="form-control" path="userLst[0].lastName" title="Please enter yout Last Name" name="lastName" id="lastName" maxlength="50" placeholder="Last Name" /></td>
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
								</span>
					 		</td>		
					 		<td id="dop-er"></td>
					 		<td><form:input cssClass="form-control" path="userLst[0].formattedPhoneNumber" title="Please enter your Phone Number" name="phoneNumber" id="phoneNumber" maxlength="50" placeholder="Phone Number" /></td>
					 		<td></td>
					 	</tr>
						<tr>		 		
					 		<td><form:input cssClass="form-control" path="userLst[0].emailAddress"  name="email" id="email" onkeyup="return forceLower(this);" maxlength="50" placeholder="Email"  /></td>			 							 		
					 		<td></td>
					 		<td>
					 			<span >
					 				<span><span><form:radiobutton cssClass="form-control primary-user-gender" path="userLst[0].gender" style="width : 15px;" value="Male" id="genderM" /></span><span style="margin-left : 5px;">Male</span></span>
					 				<span><span><form:radiobutton cssClass="form-control primary-user-gender" path="userLst[0].gender"  style="width : 15px;" value="Female" id="genderF" /></span><span style="margin-left : 5px;">Female</span></span>
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
					 	<tr>		 		
						<td>
						<input type="text"  name="userLst[0].ethnicity" id="addConEthnicityTxt" value="" style="left: -1000px; width: 1px; position: relative; size: 1px; height: 1px;" />
							<span>	  		
						  		<select name="ethnicitySel" class="required" id="addConEthnicitySelectId" style="width : 100%">
						  		 <option value="" >--Select Ethnicity--</option>													
									<c:forEach var="ethnicity" items="${ethnicityLst}" varStatus="count">
									
										<c:if test="${ethnicity != null}">
											<option value="${ethnicity.keyValue }">${ethnicity.keyValue }</option>
										</c:if>	
									</c:forEach>
									<option value="Other">Other</option>
								</select>
						 	</span><br />	 
	  						<%--<label><input type="text" placeholder="Other Ethnicity" name="ethnicity" id="addConEthnicityTxt" style="display : none;" /></label>
							 <form:input cssClass="form-control input-Width" path="ethnicity"   id="addEthnicity"  maxlength="50" placeholder="Ethnicity"  /> --%>
						</td>			 							 		
						<td></td>
						<td><form:input cssClass="form-control input-Width" path="employer"   id="addEmployer"  maxlength="50" placeholder="Employer"  /></td>			 							 		
						<td></td>
					</tr>
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
					<form:input style="display:none;" path="tcDescription" id="userTC" value="${waiversAndTC.tcDescription}" />	
					<input style="display:none;" type="hidden" name="dispatchTo" id="dispatchTo" value="${param.dispatchTo }" />	
					<form:input style="display:none;" path="userLst[0].isAdult" value="true"/>
					<form:input style="display:none;" path="userLst[0].membershipAgeCategory" value="Adult"/>					
				</form:form>
            </p>          			
        </div>
  			<div id="step-2">
  				<c:if test="${empty agentUid}">
            		<div class="terms" style="padding : 20px;">${waiversAndTC.tcDescription}</div>
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
            	</c:if>
            	<c:if test="${not empty agentUid}">
            		<div class="agentTerms" style="margin-left : 20px;"><h4>Please ensure the user signs the Terms and Conditions.</h4></div>
            		<form id="tcForm" action="#" class="cmxform" style="margin-top : 10px;">
            		</form>
            	</c:if>
            	            
        	</div>                      
  			
  		</div>
<!-- End SmartWizard Content -->  		
 		
</td></tr>
</table>
    		
</body>



        

