<%@ include file="../layouts/include_taglib.jsp" %>
<%
   	String contextPath = request.getContextPath();
%>
<div id="main">	
	<div id="content">
		<div id="content">
		<form class="bootstrap-frm" id="loginForm" method="post" action="resetusername">			
			<h2>
				Email Assistance
			</h2>
			<div id="statusBlock">
				<span class="k-block k-success-colored" id="tcSuccessSpan"></span>
				<span class="k-block k-error-colored" id="tcErrorSpan"></span>
			</div>
        	
        	<div id="formBlock">
	        	<span>Enter the details below, it should exactly match with our records:</span><br><br>
	        	<input type="text" placeholder="Date of Birth" name="dob" id="dob" style="width:210px;"><br><br>
	        	<input type="text" placeholder="First Name" name="firstName" id="firstName">
	        	<input type="text" placeholder="Last Name" name="lastName" id="lastName">
	        	<input type="text" placeholder="Address1" name="address1" id="address1">
	        	<input type="text" placeholder="Zip" name="zip" id="zip">
	        	<button id="loginbutton" class="k-button">Continue</button>
        	</div>
        	<div id="recoveryblock">
	        	<span id="forgotPW">
	        		<a href="<%=contextPath %>/login">Go to Login Page</a>
	        	</span>
	        </div>
		</form>
		</div>		
	</div>		
</div>

<script>
	$(document).ready(function() {
		// create DatePicker from input HTML element
		$("#dob").kendoDatePicker();
		
		$( "#loginForm" ).submit(function( event ) {
			var dob = $("#dob").val();
			var address1 = $("#address1").val();
			var zip = $("#zip").val();
			
			var firstName = $("#firstName").val();
			var lastName = $("#lastName").val();
			
			if(dob == null || dob == ''){
				 $("#tcErrorSpan").css("display", "block");	
				 $( "#tcErrorSpan" ).html("Please enter your Date for Birth");
				 event.preventDefault();
				 return false;
			 }
			
			if(firstName == null || firstName == ''){
				 $("#tcErrorSpan").css("display", "block");	
				 $( "#tcErrorSpan" ).html("Please enter your First Name");
				 event.preventDefault();
				 return false;
			 }
			  
			if(lastName == null || lastName == ''){
				 $("#tcErrorSpan").css("display", "block");	
				 $( "#tcErrorSpan" ).html("Please enter your Last Name");
				 event.preventDefault();
				 return false;
			 }
			
			if(address1 == null || address1 == ''){
				 $("#tcErrorSpan").css("display", "block");	
				 $( "#tcErrorSpan" ).html("Please enter your Address");
				 event.preventDefault();
				 return false;
			 }
			  
			if(zip == null || zip == ''){
				 $("#tcErrorSpan").css("display", "block");	
				 $( "#tcErrorSpan" ).html("Please enter your Zip");
				 event.preventDefault();
				 return false;
			 }
			  
			  
		});
		
		if('${errMsg }'!=""){
			$("#formBlock").css("display", "");
			$("#tcErrorSpan").css("display", "block");	
			$( "#tcErrorSpan" ).html('${errMsg }');
		}
		if('${successMsg }'!=""){
			$("#formBlock").css("display", "none");
			$("#tcSuccessSpan").css("display", "block");	
			$( "#tcSuccessSpan" ).html('${successMsg }');
		}
	});
</script>