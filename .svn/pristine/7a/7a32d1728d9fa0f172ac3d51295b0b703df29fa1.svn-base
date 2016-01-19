<%@ include file="../../layouts/include_taglib.jsp" %>
<div id="main">	
	<div id="content">
		<div id="content">
		<!-- <form class="bootstrap-frm" id="loginForm" method="post" action="requestcancellation">			
			<h2>
				Request for Cancellation
			</h2>
        	<div id="statusBlock">
				<span class="k-block k-success-colored" id="tcSuccessSpan"></span>
				<span class="k-block k-error-colored" id="tcErrorSpan"></span>
			</div>
        	
        	<div id="formBlock">
	        	 <span>Reason for Cancellation</span><br><br>
	        	<label><textarea placeholder="Reason for Cancellation" name="reason" id="reason" style="width:490px;"></textarea></label>
	        	<button id="loginbutton" class="k-button">Request Cancellation</button>
        	</div>
        	
		</form> -->

			<!-- Dialog box for showing Locations  -->
			<div id="locationGenericDialogBox" title="Locations">
				<p style="font-size: 12px;" id="locationDialogMessage"></p>
			</div>
		</div>		
	</div>		
</div>

<script type="text/javascript">
$(document).ready(function(){
	$("#locationGenericDialogBox").dialog({
        autoOpen: false,
        modal:true,
        resizable: false,
        width:400,        
        buttons: {
    		Ok: function() {
      			$(this).dialog("close");
      			    			
    		} 
		}
    });
	
	$("#locationDialogMessage").text("Location details are as follows");
	$("#locationGenericDialogBox").dialog( "open" );
});
</script>