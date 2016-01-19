<%@ include file="../layouts/include_taglib.jsp" %>
<%
   	String contextPath = request.getContextPath();
%>
<div id="main">	
	<div id="content">
		<div id="content">
		<form class="bootstrap-frm" id="loginForm" method="post" action="save">			
			<h2>
				Reset your Password
			</h2>
        	<div id="statusBlock">
				<span class="k-block k-success-colored" id="tcSuccessSpan"></span>
				<span class="k-block k-error-colored" id="tcErrorSpan"></span>
			</div>
        	
        	<div id="formBlock">
	        	<c:if test="${fn:trim(errMsg)==''}">
	        		<input type="hidden" name="token" id="token" value="${token }">
		        	<label><input type="password" placeholder="New Password" name="newpassword" id="newpassword"></label>
		        	<label><input type="password" placeholder="Re-enter Password" name="repassword" id="repassword"></label>
		        	<button id="loginbutton" class="k-button">Reset</button>
		        </c:if>
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

<script type="text/javascript">
$(document).ready(function(){
	$( "#loginForm" ).submit(function( event ) {
		var newpassword = $("#newpassword").val();
		var repassword = $("#repassword").val();
		
		if(newpassword == null || newpassword == ''){
			 $("#tcErrorSpan").css("display", "block");	
			 $( "#tcErrorSpan" ).html("Please enter the Password");
			 event.preventDefault();
			 return false;
		 }else{
			 if(newpassword.length<6){
				 $("#tcErrorSpan").css("display", "block");	
				 $( "#tcErrorSpan" ).html("Password length should be atleast 6 characters");
				 event.preventDefault();
				 return false;
			 }
		 }
		
		if(repassword == null || repassword == ''){
			 $("#tcErrorSpan").css("display", "block");	
			 $( "#tcErrorSpan" ).html("Please re-enter the Password");
			 event.preventDefault();
			 return false;
		 } else {
			 if(newpassword != repassword){
				 $("#tcErrorSpan").css("display", "block");	
				 $( "#tcErrorSpan" ).html("Password didn't match");
				 event.preventDefault();
				 return false;
			 }
		 }
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