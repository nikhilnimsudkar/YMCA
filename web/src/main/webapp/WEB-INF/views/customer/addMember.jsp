<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%
    	String contextPath = request.getContextPath();
    %>
   <%@ include file="../../layouts/include_taglib.jsp" %>
   <head>
   <script src="<%=contextPath %>/resources/js/maskedinput.min.js"></script>
<style type="text/css">
.input-Width{
	width: 250px !important;
}
#userInfoTb tr td {
	width : 50% !important;
}

</style>

<script>
	$(document).ready(function() {
		$("#phoneNumberAdd").mask("(999) 999-9999");
		var dobYear = new Date().getFullYear();
		dobYear = parseInt(dobYear.toString());
		for(var i=0; i<100 ; i++){
			$('#dobYear').append($('<option>', {value: dobYear,text: dobYear}));
			dobYear = dobYear - 1;
		}
		$("#dobYear").kendoDropDownList();    
		$("#dobDate").kendoDropDownList();
		$("#dobMonth").kendoDropDownList();
		updateDOB();
		$(document).on('change', '#dobMonth, #dobDate, #dobYear', function(){
			//$(document).find("#dobMonth, #dobDate, #dobYear").on('change',function(e){
			updateDOB();
		});
		$("#addConEthnicitySelectId").kendoDropDownList();
		/* var ethnicityDropdownlist = $("#addConEthnicitySelectId").data("kendoDropDownList");  
		$("#addConEthnicityTxt").attr("value", ethnicityDropdownlist.text());
		$("#addConEthnicitySelectId").on('change',function(e){  
			if(ethnicityDropdownlist.text() == 'Other'){
				$("#addConEthnicityTxt").attr("value", "");
				$("#addConEthnicityTxt").css("display", "");
			}else{
				$("#addConEthnicityTxt").css("display", "none");
				$("#addConEthnicityTxt").attr("value", ethnicityDropdownlist.text());
			}
		});	 */
		
		
		$("#genderM").click(function() {
			 var gen=$("#genderM").val();
			
			 $("#genderTxt").val(gen);
			 
			 });
		
		$("#genderF").click(function() {
			var gen=$("#genderF").val();
			
			 $("#genderTxt").val(gen);
			 
			 });
		
		var url = "isEmailExists";
		var validator1 = $(document).find("#addMembershipForm").validate({
			debug: true,
			errorElement: "em",			
			errorPlacement: function(error, element) {
				$element = $(element);			
				//console.log($element.hasClass("dateOfBirth"));
				if($element.attr("id") == "dateOfBirthAdd"){
					$element.parent().parent().parent().next("td").html("");
					element.addClass("inputErrorr");
					error.appendTo(element.parent().parent().parent().next("td"));
					element.parent().parent().parent().next("td").addClass("textMsgError");
					$element.parent().parent().parent().next("td").css("color","red");
				}else{
					element.addClass("inputErrorr");
					error.appendTo(element.parent("td").next("td"));
					element.parent("td").next("td").addClass("textMsgError");
					$element.parent("td").next("td").css("color","red");
				}
				//$("#wizard"). smartWizard("fixHeight");
				/* element.addClass("inputErrorr");
				error.appendTo(element.parent("td").next("td"));
				element.parent("td").next("td").addClass("textMsgError"); */
			},
			success: function(label, element) {
				//alert("success");
				//label.text("ok!").addClass("success");
				var $element = $(element);
				var $label = $(label);
		       	$element.removeClass("inputErrorr");
				$element.removeClass("error");
				$element.addClass("success");
				
				$label.removeClass("textMsgError");
				$label.removeClass("error");
				$label.addClass("success");
				//$("#wizard"). smartWizard("fixHeight");
				
			},
			rules: {
				"firstName": {
					required: true,
					minlength: 2,
					maxlength: 30
				},
				"lastName": {
					required: true,
					minlength: 2,
					maxlength: 30
				},
				"genderChk": {
					required: true
					
				},
				"dateOfBirth": {
					required: true,
					//check_date_of_birth: true,
				},
				"email" : {				
					validate_email_format: true,
					remote: {
						url: url,  
						type:"GET"                    	
					 }
				},
				"ethnicity": {
					required: true,
					minlength: 2,
					maxlength: 30
				}
				
			},
			messages: {
				"firstName": {				
					required: "Please enter your First Name",
					minlength: "First Name must consist of at least 2 characters",
					maxlength : "First Name must be less than of 30 characters"
				},
				
				"lastName": {				
					required: "Please enter your Last Name",
					minlength: "Last Name must consist of at least 2 characters",
					maxlength : "Last Name must be less than of 30 characters"
				},
				"genderChk" : {				
					required: "Please select your Gender"
				},
				"dateOfBirth" : {
			    	  required: "Please enter your Date of Birth",
			    	  //check_date_of_birth : "You must be less than "+ $("#kidsAgeValidation").html() +" years of age, or choose another membership product"
						
			    },
		        "email" : {				
		        	validate_email_format : "Please enter valid email address",
					remote: "This email address belongs to different contact. Please use a unique email address or leave it blank"
				},
				
				"ethnicity": {				
					required: "Please enter ethnicity",
					minlength: "ethnicity must consist of at least 2 characters",
					maxlength : "ethnicity must be less than of 30 characters"
				},
			}				
		});	
		
		$.validator.addMethod("validate_email_format", function(value, element) {	
			var re = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
			var flag = false;
			if(value){			
				flag = re.test(value);
			}else {
				flag = true;
			}
			//console.log(flag);
			return flag;
		}, "Please enter valid email address");
		
		
		
		
		//alert("Ready Test");
		/* $("#dateOfBirthAdd").kendoDatePicker({
			format: "MM/dd/yyyy",
			change: function (e) {    		
	    		//alert("Change Test");
				var inpDate = $(document).find("#dateOfBirthAdd").val();
			    var day;
		    	var month;
		    	var year;
		    	//var age =  $("#adultAgeValidation").html();
		    	var age =  23;
		    	var kidAge =  12;
			    if(inpDate != null){
			    	var dateArr = inpDate.split("/");	    	
			    	month = dateArr[0];
			    	day = dateArr[1];
			    	year = dateArr[2];
			    }

			    var mydate = new Date();
			    mydate.setFullYear(year, month-1, day);

			    var currdate = new Date();
			    currdate.setFullYear(currdate.getFullYear() - age);
			    
			    var currChilddate = new Date();
			    currChilddate.setFullYear(currChilddate.getFullYear() - kidAge);

			    if(currdate >= mydate){
			    	//$(document).find("#dop-er").text("Note : You are adding a contact, not a membe");
			    	//$(document).find("#dop-er").css("color","blue")
			    	$(document).find("#contact-validation-txt").css("display", "inline");
			    }else{
			    	$(document).find("#contact-validation-txt").css("display", "none");
			    }
			    if(currChilddate <= mydate){			    	
			    	$(document).find("#addToMemTr").css("display", "");
			    }else{
			    	$(document).find("#addToMemTr").css("display", "none");
			    	
			    }
	        }
		});  */
	});
	
	
</script>

</head>

<div id="main">
	<div class="k-loading-mask" style="width:100%;height:100%; position:absolute; top:114px; left:0px;display:none; z-index:9999">
		<span class="k-loading-text">Loading... Please wait</span>
		<div class="k-loading-image">
			<div class="k-loading-color"></div>
		</div>
	</div>
	<div id="content" style="margin-top:30px;margin-bottom:30px;">
		<form:form commandName="account" class="bootstrap-frm cmxform" id="addMembershipForm" method="post" action="addMember" style="padding:40px;">  
			<div style="" id="addAdultForm">
				  <h2><span>ADD FAMILY CONTACT</span></h2>
				  <c:if test="${primarySignupRec != null && primarySignupRec.itemDetail != null && primarySignupRec.itemDetail.recordName != null }">
						<c:if test="${primarySignupRec.itemDetail.recordName == 'One Adult w/ Kids' || primarySignupRec.itemDetail.recordName == 'Two Adults w/ Kids' || primarySignupRec.itemDetail.recordName == 'Three Adults w/ or w/o kids'}">
				  			<div style="margin: 5px 0 5px 0; display : none; color : blue;" id="contact-validation-txt">Note : You are adding a contact, not a member</div>
				  		</c:if>
				  </c:if>
				  <table border="0" width="100%" id="userInfoTb">
					<tr>
						<td><form:input cssClass="form-control input-Width" path="firstName" title="Please enter your First Name" name="firstName" id="firstName" maxlength="50" placeholder="First Name"  /></td>
															
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td><form:input cssClass="form-control input-Width" path="lastName" title="Please enter your Last Name" name="lastName" id="lastName" maxlength="50" placeholder="Last Name" /></td>
						<td>&nbsp;</td>
					</tr>
					<tr>		 		
						<td>
							<span>	  		
						  		<select name="ethnicity" id="addConEthnicitySelectId" style="width : 100%">													
									<c:forEach var="ethnicity" items="${ethnicityLst}" varStatus="count">
										<c:if test="${ethnicity != null}">
											<option value="${ethnicity.keyValue }">${ethnicity.keyValue }</option>
										</c:if>	
									</c:forEach>
									<option value="Other">Other</option>
								</select>
						 	</span><br />	 
	  						<%--<label><input type="text" placeholder="Other Ethnicity" name="ethnicity" id="addConEthnicityTxt" style="display : none;" /></label>
							 <form:input cssClass="form-control input-Width" path="ethnicity"   id="addEthnicity"  maxlength="50" placeholder="Ethnicity"  /> --%>
						</td>			 							 		
						<td></td>
					</tr>
					<tr>
						<td><form:input cssClass="form-control input-Width" path="phoneNumber" title="Please enter your Phone Number" name="phoneNumber" id="phoneNumberAdd" maxlength="50" placeholder="Phone Number" /></td>
						<td></td>
					</tr>
					<tr>		 		
						<td><form:input cssClass="form-control input-Width" path="email"   id="Email"  maxlength="50" placeholder="Email"  /></td>			 							 		
						<td></td>
					</tr>
					
					<tr id="addEmployerTr">		 		
						<td><form:input cssClass="form-control input-Width" path="employer"   id="addEmployer"  maxlength="50" placeholder="Employer"  /></td>			 							 		
						<td></td>
					</tr>
					<tr>
					<tr>		 		
						<td style="padding-bottom: 8px;"><span>
							<form:hidden  path="dateOfBirth"  name="dob" id="dateOfBirthAdd" maxlength="50" placeholder="Date of Birth"  style="width: 250px;" />
								<span class="">Date of Birth : </span><br />
								<span>
									<select name="dobMonth" id="dobMonth" style="width :90px;">													
										<option value="1">January</option>
										<option value="2">February</option>
										<option value="3">March</option>
										<option value="4">April</option>
										<option value="5">May</option>
										<option value="6">June</option>
										<option value="7">July</option>
										<option value="8">August</option>
										<option value="9">September</option>
										<option value="10">October</option>
										<option value="11">November</option>
										<option value="12">December</option>
									</select>
								</span>
								<span>
									<select name="dobDate" id="dobDate" style="width :45px;">													
										<option value="1">1</option>
										<option value="2">2</option>
										<option value="3">3</option>
										<option value="4">4</option>
										<option value="5">5</option>
										<option value="6">6</option>
										<option value="7">7</option>
										<option value="8">8</option>
										<option value="9">9</option>
										<option value="10">10</option>
										<option value="11">11</option>
										<option value="12">12</option>
										<option value="13">13</option>
										<option value="14">14</option>
										<option value="15">15</option>
										<option value="16">16</option>
										<option value="17">17</option>
										<option value="18">18</option>
										<option value="19">19</option>
										<option value="20">20</option>
										<option value="21">21</option>
										<option value="22">22</option>
										<option value="23">23</option>
										<option value="24">24</option>
										<option value="25">25</option>
										<option value="26">26</option>
										<option value="27">27</option>
										<option value="28">28</option>
										<option value="29">29</option>
										<option value="30">30</option>
										<option value="31">31</option>
									</select>
								</span>
								<span>
									<select name="dobYear" id="dobYear" style="width :70px;">
									</select>
								</span>
							</span></td>		
						<td><span id="dop-er" style="font-style:italic; color: red;"></span></td>
					</tr>					
					
					<tr>
						<td>
						<input type="text" id="genderTxt" name="genderChk" value="" style="left: -1000px; width: 1px; position: relative; size: 1px; height: 1px;" />
							<span >
								<span><span><form:radiobutton cssClass="form-control primary-user-gender" path="gender"  style="width : 15px;" value="Male" id="genderM" title="Please select gender"/></span><span style="margin-left : 5px;">Male</span></span>
								<span><span><form:radiobutton cssClass="form-control primary-user-gender" path="gender"   style="width : 15px;" value="Female" id="genderF" title="Please select gender" /></span><span style="margin-left : 5px;">Female</span></span>
							</span> 
						</td>
						<td><span id="gender-er" style="font-style:italic; color: red;"></span></td>
					</tr>
					<c:if test="${primarySignupRec != null && primarySignupRec.itemDetail != null && primarySignupRec.itemDetail.recordName != null }">
						<c:if test="${primarySignupRec.itemDetail.recordName == 'One Adult w/ Kids' || primarySignupRec.itemDetail.recordName == 'Two Adults w/ Kids' || primarySignupRec.itemDetail.recordName == 'Three Adults w/ or w/o kids'}">
							<tr id="addToMemTr" style="display : none;">		 		
								<td>
									<!-- Add to Membership:&nbsp;Yes <input type="radio" name="addToMembership" class="addToMembership" value="Yes"/>&nbsp;No <input type="radio" name="addToMembership" class="addToMembership" value="No"/>&nbsp; -->
									Is a Member:&nbsp;
									<form:radiobutton cssClass="form-control" path="addToMembership" onchange="isMemberRadioChange(this);"  value="Yes" id="Yes" title="Please select Membership option"/>&nbsp;Yes&nbsp;
									<form:radiobutton cssClass="form-control" path="addToMembership" onchange="isMemberRadioChange(this);" value="No" id="No" title="Please select Membership option"/>&nbsp;No&nbsp;
								</td>			 							 		
								<td></td>
							</tr>
						</c:if>
					</c:if>				
					</table>
				  <!-- <label><input autofocus type="text" placeholder="First Name" name="firstName" id="firstName" value="" /></label>
				  <label><input type="text" placeholder="Last Name" name="lastName" id="lastName" value="" /></label>
				  <input type="text" placeholder="Date of Birth" name="dateOfBirth" id="dateOfBirth" value="" style="width:495px;" />
				  
				  <label style="margin-top: 11px;"><input type="text" placeholder="Email" name="Email" id="Email" value="" /></label> -->
				  
				  <div id="statusBlock">
					<span class="k-block k-success-colored" id="tcSuccessSpanAddMember" style="display:none; padding : 5px;"></span>
					<span class="k-block k-error-colored" id="tcErrorSpanAddMember" style="display:none; padding : 5px;"></span>
				  </div>
					
				  <div align="center"><span id="addmemberBtn" class="k-button" style="padding : 5px 0px; text-shadow: none;">Add</span></div>
				  <input style="display:none;" name="isAdult" id="isAdultInputId" value=""/>
			</div>
		</form:form>
		<span style="display : none;" id="kidsAgeValidation">${kidsAgeValidation }</span>	
		<span style="display : none;" id="adultAgeValidationLowerLimit">${adultAgeValidationLowerLimit }</span>
	</div>
</div>


