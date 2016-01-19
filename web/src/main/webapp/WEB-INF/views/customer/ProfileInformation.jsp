<%@ include file="../../layouts/include_taglib.jsp" %> 

<div>
	<div><h3>A. Personal Information</h3></div>
	<label><input type="text" placeholder="Primary Member First Name" name="firstName" id="firstName"></label>
	<label><input type="text" placeholder="Primary Member Last Name" name="lastName" id="lastName"></label>
	<label><input type="text" placeholder="Phone Number" name="phoneNumber" id="phoneNumber"></label>
	<input type="text" placeholder="Todays Date" name="cancellationDate" id="cancellationDate" style="width:210px; height:22px;"><br><br>
	<label><input type="text" placeholder="Street" name="street" id="street" value="" /></label>
	<label><input type="text" placeholder="City" name="city" id="city" value="" /></label>
	<label><input type="text" placeholder="Zip Code" name="postalCode" id="postalCode" value="" /></label> 
	<label><input type="text" placeholder="Email" name="email" id="email" value="" /></label>
	
	<script>
		$(document).ready(function() {
			// create DatePicker from input HTML element
			$("#cancellationDate").kendoDatePicker();
			
			var d = new Date();

			var month = d.getMonth()+1;
			var day = d.getDate();

			var output = (month<10 ? '0' : '') + month + '/' + 
				(day<10 ? '0' : '') + day + '/' +
				d.getFullYear();
			$("#cancellationDate").val(output);
		});
	</script>
</div>