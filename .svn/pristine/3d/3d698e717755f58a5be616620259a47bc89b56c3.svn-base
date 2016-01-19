<%@ include file="../../layouts/include_taglib.jsp" %>
<div id="main">	
	<div id="content">
		<div id="content">		
			<form:form commandName="interaction" class="bootstrap-frm" id="cancellationFrm" method="post" action="requestcancellation">				
			<h2>
				Received Cancellation Request
			</h2> 
        	
        	<div id="statusBlock">
				<span class="k-block k-success-colored" id="tcSuccessSpan"></span>
				<span class="k-block k-error-colored" id="tcErrorSpan"></span>
			</div>
			</form:form>
		</div>		
	</div>		
</div>

<script type="text/javascript">
	$(document).ready(function(){	
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