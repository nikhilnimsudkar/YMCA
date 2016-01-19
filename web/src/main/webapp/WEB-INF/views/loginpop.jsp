
<%
	String dispatchTo = request.getParameter("dispatchTo");
	String contextPath = request.getContextPath();
%>
<%@ include file="../layouts/include_taglib.jsp" %>
<div id="main">	
	<div id="content">
		<div id="content">
		<form class="bootstrap-frm" id="loginForm" method="post" action="quickSignIn">
			<input type="hidden" name="dispatchTo" id="dispatchTo" value="<%= dispatchTo %>">			
			<h2>
				Sign in to YMCA
			</h2>
			<div id="statusBlock">
				<span class="k-block k-success-colored" id="tcloginSuccessSpan" style="display:none"></span>
				<span class="k-block k-error-colored" id="tcloginErrorSpan" style="display:none"></span>
				<div class="k-block k-error-colored" style="padding: 7px;">Please login in order to complete your sign up.</div>
			</div>
        	
        	<div id="formBlock">      
				<label><input type="text" placeholder="Email" name="username" id="username"></label>
				<label><input type="password" placeholder="Password" name="password" id="password"></label>	
				<button id="loginbutton" class="k-button">Sign in</button>
			</div>
			
			<div id="recoveryblock">			
				<span id="registerSpan" >
				 <c:if test="${!fn:containsIgnoreCase(param.dispatchTo, 'Donation')}">
					<span id="customerRegistrationSpan">
						<a href="trialPass" style="margin-left: 10px;">Become Member</a>
					</span>
					<span id="customerRegistrationSpan">
						<a href="signup" style="margin-left: 10px;">Register</a>
					</span>
					<span id="forgotPW">
						<a href="recovery/resetpassword" style="margin-left: 10px;">Forgot Email Address or Password</a>
					</span>
					</c:if>
				</span>
				<span id="donationSpan" >								
				   <c:if test="${fn:containsIgnoreCase(param.dispatchTo, 'Donation')}">
					   <span id="customerRegistrAndDonate">
							<a href="signup?dispatchTo=Donation" style="margin-left: 10px;">Register and donate</a>
						</span>
						<span id="forgotPW">
							<a href="<%=contextPath %>/donationDonate" style="margin-left: 10px;">Donate as a guest</a>
						</span>
					</c:if>
				</span>
				
				<!-- <span id="notMember"><a href="#" style="margin-left: 10px;">Not a Member?</a></span> -->
			</div>			
		</form>
		</div>		
	</div>		
</div>

<script type="text/javascript">
$(document).ready(function(){
	//$.sessionStorage.clear();
	//cart.clear();		
	/* var $windowURLStr = window.location.href.toString();
	if(window.location.href.toString().contains("donation")){
		$("#donationSpan").css("display", "inline");
		$("#registerSpan").css("display", "none");
	}else{
		$("#donationSpan").css("display", "none");
		$("#registerSpan").css("display", "inline");
	} */
	$( "#loginForm" ).submit(function( event ) {
		var userName = $("#username").val();
		var userPassword = $("#password").val();
		if((userName == null || userName == '') && (userPassword == null || userPassword == '')){
			 $("#tcloginErrorSpan").css("display", "block");	
			 $( "#tcloginErrorSpan" ).html("Please enter the Email and Password");
			 event.preventDefault();
			 return false;
		 }
		if(userName == null || userName == ''){
			 $("#tcloginErrorSpan").css("display", "block");	
			 $( "#tcloginErrorSpan" ).html("Please enter the Email");
			 event.preventDefault();
			 return false;
		 }
		if(userPassword == null || userPassword == ''){
			 $("#tcloginErrorSpan").css("display", "block");	
			 $( "#tcloginErrorSpan" ).html("Please enter the Password");
			 event.preventDefault();
			 return false;
		 }			
		 if(!validateUserName(userName)){
			$("#tcloginErrorSpan").css("display", "block");	
		 	$( "#tcloginErrorSpan" ).html("Please enter the correct Email");
		 	event.preventDefault();
		 	return false;
		 }		  
	});
	
	if('${errMsg }'!=""){
		$("#tcloginErrorSpan").css("display", "block");	
		$( "#tcloginErrorSpan" ).html('${errMsg }');
	}
	if('${successMsg }'!=""){
		$("#tcloginSuccessSpan").css("display", "block");	
		$( "#tcloginSuccessSpan" ).html('${successMsg }');
	}
});
</script>