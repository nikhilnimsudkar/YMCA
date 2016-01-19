
	<%@ include file="../../layouts/include_taglib.jsp"%>
	
			<form:form commandName="account" id="facilityBookingForm"
				method="post" action="bookFacility">
				 
				 
				 
	 <div id="form" class="bootstrap-frm">
	 
	 <div id="general">
 
	  <h2>
	   <span>FACILITY BOOKING FORM</span>
	  </h2>
	 </div> <br />
	 
 <div id="profileinfo">
 
	  <h2>
	   <span>CONTACT INFORMATION</span>
	  </h2>
	  
	 
	  
	  <input type="text" path="firstName" placeholder="First Name" name="firstName" id="firstName" value="${usInfo.firstName}" />
	  <input type="text" path="lastName" placeholder="Last Name" name="lastName" id="lastName" value="${usInfo.lastName}" />
	  
	  <input type="text" path="phoneNumber"  placeholder="Cell Number" name="phoneNumber" id="phoneNumber" value="${usInfo.formattedPhoneNumber}" />
	  
	  <input type="text"  path="email" placeholder="Email" name="Email" id="Email" value="${usInfo.emailAddress}" />  
	  <fmt:formatDate type="date" pattern="MM/dd/yyyy" value="${usInfo.dateOfBirth}" var="primaryUserDOB"/>
	  <input type="text" style="width: 310px; height: 30px; margin: 0; padding: 0;" path="dateOfBirth" placeholder="Date Of Birth" name="dateOfBirth" id="dateOfBirth" value="${primaryUserDOB}" /> 
	 
	 </div> <br />
	  <div id="customerAddress">
	  <h2>
	   <span>ADDRESS</span>
	  </h2>
	  
	  <input type="text" path="addressLine1" placeholder="Number/Street" name="addressLine1" id="addressLine1" value="${accInfo.addressLine1}" />
	  <input type="text" path="addressLine2"  placeholder="Address 2" name="addressLine2" id="addressLine2" value="${accInfo.addressLine2}" /> 
	  <input type="text" path="city" placeholder="City" name="city" id="city" value="${accInfo.city}" />
	  <input type="text" path="state" placeholder="State" name="state" id="state" value="${accInfo.state}" />
	  <input type="text" path="country" placeholder="Zip Code" name="postalCode" id="postalCode" value="${accInfo.country}" />
	 
	 </div> <br/>
	 
	  <div id="additionalInfo">
	  <h2>
	   <span>ADDITIONAL INFO</span>
	  </h2>
	  
	  <input type="text" path="purpose" placeholder="Purpose" name="purpose" id="purpose" />
	  <input type="text" path="size" placeholder="Party Size" name="size" id="size" />
	  <input type="text" path="dateTime" placeholder="Date/Time/Duration" name="dateTime" id="dateTime" />
	  <input type="text" path="other" placeholder="Other Details" name="other" id="other" />
	 <div>
		 <span id="submit" class="k-button" style="width: 70px; text-shadow: none;">SUBMIT</span> 					
	</div>
	 </div> 
	  </div>
		</form:form>	
			
<script type="text/javascript">
$(document).ready(function(){	
	
	
	
	$( "#submit" ).click(function(){
		
	bookFacility();
		
			
	});		   
});

function bookFacility(){
	
	$.ajax({
		  type: "POST",
		  url: $('#facilityBookingForm').attr( "action"),
		  data: $('#facilityBookingForm').serialize(),
		  success: function( data ) {
		  	  
		  	  if(data=='SUCCESS'){
		  		 
		  	  }
		  },
		  
	});
}




</script>
		