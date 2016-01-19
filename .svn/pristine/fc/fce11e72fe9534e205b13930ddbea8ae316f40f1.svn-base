<%@ page language="java" contentType="text/html; charset=ISO-8859-1"    pageEncoding="ISO-8859-1"%>

<div class="alert alert-info" role="alert" style="width: 80%">This process will signup the customer for this opportunity, Please click on the button to continue</div>


<div class="alert alert-success" role="alert" id="sucess" style="width: 80%;display:none; ">Customer has sucessfully sign up for this opportunity, Please wait for 5 minutes to get sign up sync with sales cloud</div>

<div class="alert alert-danger" role="alert" id="error" style="width: 80%;display:none; " ></div>

<button type="button" id="createSignUp" class="btn btn-default" aria-label="Left Align">
  <span aria-hidden="true">Sign Up this Opportunity</span>
</button>



<script type="text/javascript">
$(function() {
	$('#createSignUp').on('click', function(event) {
	  event.preventDefault(); // To prevent following the link (optional)
	  $.ajax({
		  url: "facilitybooking/create",
		  data: {o:'${param.o}',sso:'${param.sso}'},
		  context: document.body
		  })
		   .done(function() {
			  	$("#sucess").show();
			  	$("#error").hide();
			})
			.fail(function(error) {
				var errorMessage = error.responseJSON.message;
				errorMessage  =errorMessage.split(":");
				errorMessage  =errorMessage[1];
				$("#sucess").hide();
			  	$("#error").html(errorMessage);
			  	$("#error").show();
			});
	});
});	
</script>
