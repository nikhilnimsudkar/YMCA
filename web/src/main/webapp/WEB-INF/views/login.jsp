<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file="../layouts/include_taglib.jsp" %> 
<%@ page import="org.springframework.security.saml.SAMLCredential" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="org.springframework.security.core.Authentication" %>
<%@ page import="org.opensaml.saml2.core.Attribute" %>
<%@ page import="org.springframework.security.saml.util.SAMLUtil" %>
<%@ page import="org.opensaml.xml.util.XMLHelper" %>

<div id="main">	
	<div id="content">
		<div id="content">
		<form class="bootstrap-frm" id="loginForm" method="post" action="login" style="">
			<input type="hidden" id="urlPromoItemDetailId" name="urlPromoItemDetailId" value="">
			<input type="hidden" id="urlPromoContactId" name="urlPromoContactId" value="">
			<input type="hidden" id="urlPromoCode" name="urlPromoCode" value="">			
			<h2>
				Sign in to YMCA
			</h2>
			<div id="statusBlock">
				<span class="k-block k-success-colored" id="tcSuccessSpan"></span>
				<span class="k-block k-error-colored" id="tcErrorSpan"></span>
			</div>
        	
        	<div id="formBlock">      
				<label><input type="text" placeholder="Email" name="username" id="username"></label>
				<label><input type="password" placeholder="Password" name="password" id="password"></label>	
				<button id="loginbutton" class="k-button">Sign in</button>
			</div>
			
			<div id="recoveryblock">
				<span id="customerRegistrationSpan">
					<a href="trailPassReg" style="margin-left: 10px;">Trial Pass Register</a>
				</span>
				<span id="customerRegistrationSpan">
					<a href="becomeMember" style="margin-left: 10px;">Become Member</a>
				</span>
				<span id="customerRegistrationSpan">
					<a href="signup" style="margin-left: 10px;">Register</a>
				</span>
				<span id="forgotPW">
					<a href="recovery/resetpassword" style="margin-left: 10px;">Forgot Email Address or Password</a>
				</span>
				<!-- <span id="notMember"><a href="#" style="margin-left: 10px;">Not a Member?</a></span> -->
			</div>			
		</form>
		</div>		
	</div>		
</div>

<script type="text/javascript">
$(document).ready(function(){
	
	var urlPromoItemDetailId = '${urlPromoItemDetailId}';
	var urlPromoContactId = '${urlPromoContactId}';
	var urlPromoCode = '${urlPromoCode}';

	console.log(" urlPromoItemDetailId  : "+urlPromoItemDetailId);
	console.log(" urlPromoContactId : "+urlPromoContactId);
	console.log(" urlPromoCode : "+urlPromoCode);
	
	$("#urlPromoItemDetailId").val(urlPromoItemDetailId);
	$("#urlPromoContactId").val(urlPromoContactId);
	$("#urlPromoCode").val(urlPromoCode);
	
	if(urlPromoItemDetailId && urlPromoItemDetailId != null && urlPromoItemDetailId != undefined){
		//console.log("  document.loginForm.action ");
		 $("#loginForm").attr("action","quickSignIn");
		//$("#loginForm").attr("method","get"); 
		//console.log("  document.loginForm.action ");
	}
	
	//$.sessionStorage.clear();
	//cart.clear();
	$( "#loginForm" ).submit(function( event ) {
		
		
			
			
		//}else{
			 var userName = $("#username").val();
			 var userPassword = $("#password").val();
			 if((userName == null || userName == '') && (userPassword == null || userPassword == '')){
				 $("#tcErrorSpan").css("display", "block");	
				 $( "#tcErrorSpan" ).html("Please enter the Email and Password");
				 event.preventDefault();
				 return false;
			 }
			 if(userName == null || userName == ''){
				 $("#tcErrorSpan").css("display", "block");	
				 $( "#tcErrorSpan" ).html("Please enter the Email");
				 event.preventDefault();
				 return false;
			 }
			 if(userPassword == null || userPassword == ''){
				 $("#tcErrorSpan").css("display", "block");	
				 $( "#tcErrorSpan" ).html("Please enter the Password");
				 event.preventDefault();
				 return false;
			 }			
			 if(!validateUserName(userName)){
				$("#tcErrorSpan").css("display", "block");	
			 	$( "#tcErrorSpan" ).html("Please enter the correct Email");
			 	event.preventDefault();
			 	return false;
			 }
			 
			 if(urlPromoItemDetailId && urlPromoItemDetailId != null && urlPromoItemDetailId != undefined){
					event.preventDefault();
					
					$.ajax({
						  type: "GET", 
						  url:"quickSignIn?username="+userName+"&password="+userPassword+"&urlPromoItemDetailId="+urlPromoItemDetailId+"&urlPromoContactId="+urlPromoContactId+"&urlPromoCode="+urlPromoCode,
						  async:false,
						  //dataType: "json",
						  success: function( data ) {
							  console.log(" quickSignIn success "+data);
							  location.href="changeMembershipShowWizard?urlPromoItemDetailId="+urlPromoItemDetailId+"&urlPromoContactPartyId=&urlPromoContactId="+urlPromoContactId+"&urlPromoCode="+urlPromoCode+"";
						  },
						  error: function( xhr,status,error ){
							  console.log(" quickSignIn failed "+xhr);
						  }
					});
					
					
					return false;
			 }
		//}
	});
	
	if('${errMsg }'!=""){
		$("#tcErrorSpan").css("display", "block");	
		$( "#tcErrorSpan" ).html('${errMsg }');
	}
	if('${successMsg }'!=""){
		$("#tcSuccessSpan").css("display", "block");	
		$( "#tcSuccessSpan" ).html('${successMsg }');
	}
});
</script>