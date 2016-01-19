<%@ include file="../../layouts/include_taglib.jsp" %>
<script id="terms-conditions-ErrorBox" type="text/x-kendo-template">
    <p class="error-message-p">Please agree the terms and conditions before Proceding</p>
	
    <div class="confirmbutton" align="center"><button class="confirm-terms-conditions k-button" style=" width: 35%;">Ok</button>   
</script>
<div id="main">	
	<div id="content">
		<div id="content">
		
		<form:form commandName="activity" method="post"  action="requestcancellation" id="cancellationFrm" class="bootstrap-frm  cmxform"  >			
			<!-- <h2>
				Request for Cancellation
			</h2> -->
        	
        	<div id="formBlock">
        		
				<div id="cancellation">
					<%@ include file="MembershipCancellation.jsp" %> 
				</div>
	        
        	</div>
        	<c:if test="${empty agentUid}">
        	<!-- <div id="TAndCHoldMembershipDiv" class="termsAndConditionParentDiv" style="width : auto !important;">
				<div class="termsAndConditionContent">
					
				</div>									
			</div> -->
			<input type="checkbox" name="chkTermsAndCondition" style="margin-top: 10px;">&nbsp;I accept terms and conditions
			<span style="display : none;" id="commonTAncHidden">${waiversTAndC.tcDescription }</span>
			</c:if>	
        	<div id="statusBlock">
				<span class="k-block k-success-colored" id="tcSuccessSpan"></span>
				<span class="k-block k-error-colored" id="tcErrorSpan"></span>
			</div>
        	
        	<span id="ymcabutton" class="k-button" style="text-shadow: none;">Request Cancellation</span>
        	
		 </form:form>
		</div>		
	</div>		
</div>
<script id="delete-confirmation" type="text/x-kendo-template">
    <p class="delete-message">Are you Sure you want to cancel your membership?</p>
	
    <div class="confirmbutton"><button class="delete-confirm k-button">Yes</button>&nbsp;&nbsp;&nbsp;
    <span class="delete-cancel k-button">No</span></div>
</script>

<script type="text/javascript">
$(document).ready(function(){	
	$(".termsAndConditionContent").html($("#commonTAncHidden").html());
	 $('#reason6').change(function() {		 
	        if(!$(this).is(":checked")) {	        	
	        	$('#otherreason1').attr("value", "");
	        	//$('input:radio').attr('checked', false);	        	
	        }	               
	  });
	 
	 $('#reason3').change(function() {		 
	        if(!$(this).is(":checked")) {	        	
	        	$('#whydissatisfied').attr("value", "");	        		        	
	        }	               
	  });
	
	 $('#reason4').change(function() {		 
	        if(!$(this).is(":checked")) {	        	
	        	$('#otherjoined').attr("value", "");	        		        	
	        }	               
	  });
	
	$( "#ymcabutton" ).click(function(){
		  $("#tcErrorSpan").css("display", "none");		
		  $("#tcErrorSpan" ).html("");	
		  $("#tcSuccessSpan").css("display", "none");		
		  $("#tcSuccessSpan" ).html("");
		  
		var reasonSel = 0;
		var reason3Sel = 0;
		var reason4Sel = 0;
		var reason6Sel = 0;
		if($("#reason1").is(':checked')){
			reasonSel = 1;
		}
		else if($("#reason2").is(':checked')){
			reasonSel = 1;
		}
		if($("#reason3").is(':checked')){
			reasonSel = 1;
			reason3Sel = 1;
		}
		if($("#reason4").is(':checked')){
			reasonSel = 1;
			reason4Sel = 1;
		}
		else if($("#reason5").is(':checked')){
			reasonSel = 1;
		}
		if($("#reason6").is(':checked')){
			reasonSel = 1;
			reason6Sel = 1;
		}else{
			
		}
		if(reasonSel==0){
			  $("#tcSuccessSpan").css("display", "none");		
			  $("#tcSuccessSpan" ).html("");	
			  $("#tcErrorSpan").css("display", "block");		
			  $( "#tcErrorSpan" ).html("Please select atleast one reason");
			  return;
		}
		
		if(reason3Sel==0){
			$('#whydissatisfied').val("");
			$('#howtoimprove').val("");
		}
		
		if(reason4Sel==0){
			$('#otherjoined').val("");
		}
		
		if(!$("input[name='chkTermsAndCondition']:checked").val() && !isLoggedInUserAgent()){ 
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
			$('#wizard').smartWizard('showMessage','Please agree the terms and conditions before Proceeding');			
			return;
		 } else if(reason6Sel==1){
			if($('input:radio:checked').length <=0){
				  $("#tcSuccessSpan").css("display", "none");		
				  $("#tcSuccessSpan" ).html("");	
				  $("#tcErrorSpan").css("display", "block");		
				  $( "#tcErrorSpan" ).html("Please select atleast one other reason");
				  return;
			}
			else{
				if($('input:radio:checked').val()=='Other'){
					if($('#otherreason1').val()==''){
						  $("#tcSuccessSpan").css("display", "none");		
						  $("#tcSuccessSpan" ).html("");	
						  $("#tcErrorSpan").css("display", "block");		
						  $( "#tcErrorSpan" ).html("Please write the reason");
						  return;
					}
				}
				else{
					$('#otherreason1').val("");
				}
			}
		}
		else{
			$('input:radio').prop('checked', false);
			$('#otherreason1').val("");
			
		}
		
		var kendoWindow = $("<div />").kendoWindow({
            title: "Confirm",
            resizable: false,
            modal: true,
            width:400
        });
		
		kendoWindow.data("kendoWindow")
        .content($("#delete-confirmation").html())
        .center().open();
		
		 kendoWindow
	        .find(".delete-confirm,.delete-cancel")
	            .click(function() {
	                if ($(this).hasClass("delete-confirm")) {
	                    //alert("Deleting record...");
	                	cancel_member();
	                }
	                
	                kendoWindow.data("kendoWindow").close();
	            })
	            .end()
		
		//if(confirm("Are you Sure you want to cancel your membership?")){
			
		//}
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
	
	var reason1 = "";
	var reason2 = "";
	var reason3 = "";
	var reason4 = "";
	var reason5 = "";
	var reason6 = "";
	
	if($("#reason1").is(':checked')){
		reason1 = $("#reason1").val();
	}
	if($("#reason2").is(':checked')){
		reason2 = $("#reason2").val();
	}
	if($("#reason3").is(':checked')){
		reason3 = $("#reason3").val();
	}
	if($("#reason4").is(':checked')){
		reason4 = $("#reason4").val();
	}
	if($("#reason5").is(':checked')){
		reason5 = $("#reason5").val();
	}
	if($("#reason6").is(':checked')){
		reason6 = $("#reason6").val();
	}	
	var howtoimprove = $("#howtoimprove").val();
	var otherjoined = $('#otherjoined').val();
	var otherreason = $('#otherreason').val();
	var otherreason1 = $('#otherreason1').val();	
	var whydissatisfied = $('#whydissatisfied').val();	
	
	$.ajax({
		  type: "POST",
		  url: $('#cancellationFrm').attr( "action"),
		  data: { "reason1" : reason1, "reason2" : reason2, "reason3" : reason3, "reason4" : reason4, "reason5" : reason5, "reason6" : reason6, 
			  "howtoimprove" : howtoimprove, "otherjoined" : otherjoined, "otherreason" : otherreason, "otherreason1" : otherreason1, "whydissatisfied" : whydissatisfied, "reasontext" : ""}, 
		  //data: $('#cancellationFrm').serialize(),
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