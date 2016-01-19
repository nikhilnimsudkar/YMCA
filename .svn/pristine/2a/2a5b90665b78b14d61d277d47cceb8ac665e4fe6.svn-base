<%@ include file="../../layouts/include_taglib.jsp" %>
<div id="main">	
	<div id="content">
		<div id="content">
		<form:form commandName="interaction" class="bootstrap-frm" id="holdFrm" method="post" action="">			
			<!-- <h2>
				Request for Cancellation
			</h2> -->
        	
        	<div id="formBlock">
        		
				<div id="cancellation">
					<%@ include file="MembershipHold.jsp" %> 
				</div>
	        
        	</div>
        	
        	<div id="statusBlock">
				<span class="k-block k-success-colored" id="tcSuccessSpan"></span>
				<span class="k-block k-error-colored" id="tcErrorSpan"></span>
			</div>
        	
        	<span id="ymcabutton" class="k-button">Hold</span>
        	
		 </form:form>
		</div>		
	</div>		
</div>
<script id="delete-confirmation" type="text/x-kendo-template">
    <p class="delete-message">Are you Sure you want to Hold your membership?</p>
	
    <div class="confirmbutton"><button class="delete-confirm k-button">Yes</button>&nbsp;&nbsp;&nbsp;
    <span class="delete-cancel k-button">No</span></div>
</script>

<script type="text/javascript">
$(document).ready(function(){
	
	$( "#ymcabutton" ).click(function(){
		 	//alert($('#cancellationFrm').serialize());
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

function cancel_member(){
	$.ajax({
		  type: "POST",
		  url: $('#cancellationFrm').attr( "action"),
		  data: $('#cancellationFrm').serialize(),
		  success: function( data ) {
		  	  //alert(data);
		  	  location.href='receivecancellation?success=1';
		  	  //$("#tcErrorSpan").css("display", "none");		
			 // $("#tcErrorSpan" ).html("");	
			  //$("#tcSuccessSpan").css("display", "block");		
			//  $("#tcSuccessSpan" ).html("Your request for Cancellation has been recieved.");
		  },
		  error: function( xhr,status,error ){
			  //alert("1" +status);
			  console.log(xhr);
			  console.log(status);
			  console.log(error);
			  location.href='receivecancellation?err=1';
			 // $("#tcSuccessSpan").css("display", "none");		
			 // $("#tcSuccessSpan" ).html("");	
			 // $("#tcErrorSpan").css("display", "block");		
			//  $( "#tcErrorSpan" ).html("There was some error. Please try again later");
		  }
	});
}
</script>