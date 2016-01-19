<div id="emergencyContacts" style="width: 780px;; padding: 12px;">
	<div>
		<span class="head" style="float:left;">Select Emergency Contact:</span>
		<div style="float:left; margin-left: 50px;"><span>OR</span></div>
		<div style="margin-left: 50px; float: left; margin-top: -5px;"><span  class="k-button" id="add_member_emg_con" style="float:left;">Add New Family Contact</span></div>
	</div>
	<div style="clear: both;"></div> 
	<div id="emergency_family_details" style="margin: 20px 20px 30px;  min-height: 220px;">
		<div id="allEmergencyContacts"></div>
		
	</div>
	<input type="hidden" name="EmgContactCounter" id="EmgContactCounter" value='0'>
	<div id="cartbtns" align="right">
		<span id="goAuthorisedNextScreen" class="k-button">Next >></span>
	</div>
</div>

<script>
$(document).ready(function(){
	$( "#goAuthorisedNextScreen" ).click(function(){
		//gotocheckout();
		if(isValidEmergencyContact()){
			showAuthorisedContactIfFlagIsSet();
		}
	});
	
	var popup_add_common_window = $("#popup_add_common");
	$("#add_member_emg_con").bind("click", function() {	
		$("#familyContactPageType").val("EMG");
		addfamilyMemberCommon();
		popup_add_common_window.data("kendoWindow").center().open();
		$("#add_member_common").hide();
		$('Div[data-role="draggable"]').css('top', '20px');
	});

	var onClose = function() {
		 $("#add_member_common").show();
	}

	if (!popup_add_common_window.data("kendoWindow")) {
		popup_add_common_window.kendoWindow({
	        width: "700px",
	        title : "Add Family Contact",
	        modal : true,
	        close: onClose
	    });
	}

});
</script>