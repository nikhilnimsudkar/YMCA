<%@ include file="../../layouts/include_taglib.jsp" %> 

<div>
	<div><h1>Membership Hold</h1></div>
	
	<p>
	YMCA Facility Memberships may be placed on hold for a maximum of 4 months and a minimum of 1 month per calendar year. We require a 15-business
	day notice prior to your draft date to put a membership on hold. A $20 processing fee is charged for each hold request. By signing below you understand
	that all holds will coincide with your draft cycle and that your draft will automatically resume at the end of the hold period.
	</p>
	
	<div>
		<span>Hold to <strong>START</strong> on</span>&nbsp;&nbsp;
		<input type="text" placeholder="Start On" name="starton" id="starton" style="width:210px; margin:0px;">&nbsp;&nbsp;&nbsp;&nbsp;
		<span>Hold to <strong>END</strong> on</span>&nbsp;&nbsp;
		<input type="text" placeholder="End On" name="endon" id="endon" style="width:210px; margin:0px;">
		<br><br>
		<span><input type="checkbox" name="reason" class="k-checkbox"></span>
		<span>1 Month</span>
		<span><input type="checkbox" name="reason" class="k-checkbox"></span>
		<span>2 Months</span><br>
		<span><input type="checkbox" name="reason" class="k-checkbox"></span>
		<span>3 Months</span>
		<span><input type="checkbox" name="reason" class="k-checkbox"></span>
		<span>4 Months</span><br>
		<span><input type="checkbox" name="reason" class="k-checkbox"></span>
		<span>Annual Membership OR</span><br>
		<span><input type="checkbox" name="reason" class="k-checkbox"></span>
		<span>Monthly Draft will automatically resume on</span>
		<span><input type="text" placeholder="" name="firstName" id="firstName"></span>
	</div>
</div>

<script>
	$(document).ready(function() {
		// create DatePicker from input HTML element
		$("#starton").kendoDatePicker();
		$("#endon").kendoDatePicker();
	});
</script>

