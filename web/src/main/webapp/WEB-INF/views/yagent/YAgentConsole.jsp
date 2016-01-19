<%@ page language="java" contentType="text/html; charset=ISO-8859-1"   pageEncoding="ISO-8859-1"%>

<div id="main2">
	<div id="content1" >
		<div id="myprofile" >
			<%@ include file="YAgentProfile.jsp" %> 
		</div>
		
		<div id="CenterSecYagent2" style="width: 70%;margin-left: 0px;"  >
			<%@ include file="YAgentCenterSec.jsp" %> 
		</div>
		<div id="recoveryblock" style="margin-top: 5px;">
				<span id="customerRegistrationSpan">
					<a style="margin-left: 10px;" target="_self" href="../trailPassReg">Trial Pass Register</a>
				</span>
				<span id="customerRegistrationSpan">
					<a style="margin-left: 10px;" target="_self" href="../becomeMember">Become Member</a>
				</span>
				<span id="customerRegistrationSpan">
					<a style="margin-left: 10px;" target="_self" href="../signup">Register</a>
				</span>
				<span id="forgotPW">
					<a style="margin-left: 10px;" target="_self" href="../recovery/resetpassword">Forgot Email Address or Password</a>
				</span>
				<!-- <span id="notMember"><a href="#" style="margin-left: 10px;">Not a Member?</a></span> -->
		</div>		
	</div>
</div>