<%@ include file="../../layouts/include_taglib.jsp"%>
    <script src="<%=contextPath %>/resources/js/app/common.js"></script>
<script src="<%=contextPath %>/resources/js/app/payment.js"></script>
<script src="<%=contextPath %>/resources/js/sha512.js"></script>
<script src="<%=contextPath %>/resources/js/jquery.validate.min.js"></script>
<!-- <table style="padding-left:4px;padding-top:4px; width: 100%;">
		
	<div id="tabstrip4" class="k-block" style=" background-color:#FFFFFF;margin-left:-572px;width:768px;"> -->

		<table cellpadding="0" cellspacing="0" width="95%"  rules="none" style="padding-left:4px;padding-top:4px;">
					
					<span class="head" width="100%"style="margin-left:4px;" >List of Facilities for Rent</span>	
					<tr><td>
        &nbsp;
        <!--you just need a space in a row-->
    </td></tr>	
					<tr>
								<td style="color: black;padding-left:4px;padding:3px;">Facility Name</td>
								<td style="color: black;">Facility Location</td>
								 
							 

					</tr>
						<c:forEach var="facility" items="${facilityList}" varStatus="status">
						
	    

							<tr style="outline:thin solid; padding-left:4px;">
								<td style="color: red;padding-left:4px;padding:3px;">${facility.Item}</td>
								<td style="color: red;">${facility.location}</td>
								 
							 <!--  <td style="padding-bottom:3px;padding-left:4px;"></td> -->
														 <!--  <td style="padding-bottom:3px;" align="center"><span id="requestBooking" onclick ="location.href='requestBooking'" class="k-button" style="width: 70px; text-shadow: none;">REQUEST BOOKING</span></td>-->
							<!--  <td style="padding-bottom:3px;" align="center"> <span class="k-button" id="requestBooking">REQUEST BOOKING</span></td>-->
							<td style="padding-bottom:3px;" align="center">  <button id="requestBooking" class="k-button">REQUEST BOOKING</button></td>
							  <!-- <td style="padding-bottom:3px;"></td> -->

							</tr>
							<tr>
    <td>
        &nbsp;
        <!--you just need a space in a row-->
    </td>
</tr>


</c:forEach>
	
</table>



<script>
  $(document).ready(function() {

	$( "#requestBooking" ).click(function(){
		
	var isUserLoggedin = "${pageContext.request.userPrincipal.isAuthenticated()}";
	if(isUserLoggedin){
			  document.location.href="requestBooking";
			}
			else{
				var window = $("#popup_add");
				window.clone().kendoWindow({
					title : "Login",
					content : "loginPopup?dispatchTo=Facility",
					modal : true,
					width : "500px",
					height : "550px",
					data: { dispatchTo: 'Facility' },
					close : onClose
									
				}).data("kendoWindow").center().open();
				
				var onClose = function() {
					 
			    }
				
				$('Div[data-role="draggable"]').css('top', '50px');
			}
		
});
});
</script>

	

					

	
