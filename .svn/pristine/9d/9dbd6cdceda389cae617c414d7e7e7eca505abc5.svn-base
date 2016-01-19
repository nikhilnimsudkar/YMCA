<div id="main">	
	<span style="text-shadow: none; float: right; width: 90px; margin-right: 20px;" class="k-button" id="newMemberSpan">Register</span>
	<div id="content">
		<div id="content">
		<form class="bootstrap-frm cmxform" id="loginForm" method="post" action="memberAuth" style="border:none; padding : 0px;">			
			
        	<div id="formBlock" align="center" style="width: 550px;"> 
        	<div id="statusBlock" style="display:none;">
				<span class="k-block k-success-colored" id="tcloginSuccessSpan" style="display:none"></span>
				<span class="k-block k-error-colored" id="tcloginErrorSpan" style="display:none"></span>				
			</div>   
    			
					<table border="0" style="border: 1px solid rgb(221, 221, 221); border-radius: 5px; padding: 33px;">
						<tr>
							<td><div align="left">
									<h2>Sign in to YMCA</h2>
								</div></td>
						</tr>
						<tr>
							<td><input class="form-control bootstrap-Inputcss" name="username" id="username" maxlength="50"	placeholder="Email" style="width: 230px;" /></td>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td><input type="password" class="form-control bootstrap-Inputcss" name="password" id="userPassword" maxlength="50"	placeholder="Password" style="width: 230px;height: 15px;" /></td>
							<td>&nbsp;</td>
							
						</tr>
						<tr>
							<td><span id="loginbuttonSpan" class="k-button">Sign in</span></td>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td style="text-align:right;">
								<span id="forgotPW"> 
									<a href="recovery/resetpassword" style="margin-left: 10px;">Forgot Email Address or Password</a>
								</span>
							</td>
						</tr>
						
						
					</table>
				 
				<!-- <input type="text" placeholder="Email" name="username" id="username"></label>
				<label><input type="password" placeholder="Password" name="password" id="userPassword"></label> -->	
				
			</div>
			
			<!-- <div id="recoveryblock">				
				<span id="forgotPW">
					<a href="recovery/resetpassword" style="margin-left: 10px;">Forgot Email Address or Password</a>
				</span>
				<span id="notMember"><a href="#" style="margin-left: 10px;">Not a Member?</a></span>
			</div> -->			
		</form>
		</div>		
	</div>		
</div>

<script type="text/javascript">
$(document).ready(function(){
	//$.sessionStorage.clear();
	//cart.clear();	
	
  //var token = $("meta[name='_csrf']").attr("content");
  //var header = $("meta[name='_csrf_header']").attr("content");
	 $('body').on( "change", "#paymentInfoRadio", function() {			
		if($("#paymentInfoRadio").val()=='New'){
			$('#addBank').hide();	
			$('#addcard').show();			
			$(".stepContainer").height($(".stepContainer").find("div:not(:hidden)").height());
			/* $("#wizard"). smartWizard("fixHeight");			
			$('.stepContainer').css("height", "240px");
			$('#step-5').css("height", "225px");  */			
		}else if($("#paymentInfoRadio").val()=='NewBank'){
			$('#addcard').hide();
			$('#addBank').show();			
			$(".stepContainer").height($(".stepContainer").find("div:not(:hidden)").height());
		} else{
			$('#addcard').hide();
			$('#addBank').hide();	
			$(".stepContainer").height($(".stepContainer").find("div:not(:hidden)").height());
			$("#paymentTokenIdSpan").html($("#paymentInfoRadio").val());
			$("#paymentMethodIdHidInput").attr("value", $("#paymentInfoRadio").val()); 
			/* $("#wizard"). smartWizard("fixHeight");			
			$('.stepContainer').css("height", "240px");
			$('#step-5').css("height", "225px");  */
		}
	});
	
	$( "#SaveCard" ).click(function(){
		if($('#SaveCard').is(":checked")){
			$("#nickname").show();
			$("div#savecardcheckbox").css("padding-top","21px");
		}
		else{
			$("#nickname").hide();
			//$("div#savecardcheckbox").css("padding-top","5px");
		}
	});
	
	
	$( "#loginbuttonSpan" ).click(function(  ) {
		var userName = $("#username").val();
		var userPassword = $("#userPassword").val();
		if(!$("#loginForm").valid()) {
			$('#wizard').smartWizard('setError',{stepnum:2,iserror:true});		
			return false;
		}else {
			 $("#statusBlock").css("display", "none");	
			 $("#tcloginErrorSpan").css("display", "none");	
			 $( "#tcloginErrorSpan" ).html("");
			 $(".k-loading-mask").show();
				$(".k-loading-text").html("Please wait");			
				$.ajax({
					  type: "POST",
					  url: $('#loginForm').attr( "action"),
					  data: { username: userName, password: userPassword },				  
					  success: function( dat ) {				  	  
					  	  if(dat=='Success'){
					  		isLoggedIn = "true";
					  		$('#loggedInUserEmailId').attr("value", userName);
					  		$("#tcloginErrorSpan").css("display", "none");	
							$( "#tcloginErrorSpan" ).html("");	
							$(".k-loading-mask").hide();							
							//$("#email").attr("value", userName);
						  	//window.location.href = "http://localhost:8080/ymca-web/view_membership";
						  	$.ajax({
								  type: "GET",
								  url: "viewPaymentInformationByLoggedInUser",								  
								  success: function( data ) {				  	  
									  if(data != null && data.length > 0){	
										  $.ajax({
											  type: "GET",
											  url: "getACHPaymentInformationByLoggedInUser",								  
											  success: function( achData ) {											  										  
												  var paymentMethodHtml = '';											  
												  if(data != null && data.length > 0){														 
													  for(var i = 0; i<data.length; i++){        
														   var dataArr = data[i];
														   //p.portalStatus, p.paymentType, p.isPrimary, p.paymentId, p.nickName, p.cardNumber, p.expirationMonth, p.expirationYear										       
														   //paymentInfoRadio						   
														   /* if(dataArr[2]){
															   paymentMethodHtml += '<option selected value="'+ dataArr[8] +'">'+ dataArr[4] +' '+dataArr[5] +' '+dataArr[6] +'/'+dataArr[7]+'</option>';
															   $("#paymentTokenIdSpan").html(dataArr[8]);
														   }else{
															   paymentMethodHtml += '<option value="'+ dataArr[8] +'">'+ dataArr[4] +' '+dataArr[5] +' '+dataArr[6] +'/'+dataArr[7]+'</option>';
														   }	 */				
														   if(dataArr[2]){
															   paymentMethodHtml += '<option selected value="'+ dataArr[6] +'">'+ dataArr[7] +' '+dataArr[3] +' '+dataArr[4] +'/'+dataArr[5]+'</option>';
															   $("#paymentTokenIdSpan").html(dataArr[6]);
															}else{
															   paymentMethodHtml += '<option value="'+ dataArr[6] +'">'+ dataArr[7] +' '+dataArr[3] +' '+dataArr[4] +'/'+dataArr[5]+'</option>';
															}
													  }
												  }
										  		  if(achData != null && achData.length > 0){
													  for(var i = 0; i<achData.length; i++){        
													       var dataArr = achData[i];
													       //p.transId,  p.bankRoutingNumber, p.fullName, p.paymentType,  p.checkingAccountNumber, p.driversLicenseNumber, p.stateOfDL, p.phoneNumber, p.nickName, p.portalStatus,  p.tokenNumber , p.paymentId, p.isPrimary											       
													       if(dataArr[12]){
													    	   paymentMethodHtml += '<option selected value="'+ dataArr[10] +'">'+ dataArr[2] +', '+dataArr[4] +'</option></span>';
													    	   $("#paymentTokenIdSpan").html(dataArr[8]);
													       }else{
													    	   paymentMethodHtml += '<option value="'+ dataArr[10] +'">'+ dataArr[2] +', '+dataArr[4] +'</option></span>';
													       }													       
												      }
										  		  }
											      paymentMethodHtml += '<option value="New">Add New Card</option>';
											      paymentMethodHtml += '<option value="NewBank">Add New Bank Info</option>';
											      $("#paymentInfoRadio").html(paymentMethodHtml);
											      $("#paymentInfoRadio").kendoDropDownList();
											      $("#paymentTokenIdSpan").html($("#paymentInfoRadio").val());
											      $("#paymentMethodIdHidInput").attr("value", $("#paymentInfoRadio").val());
											      //$('#wizard').smartWizard('goToStep',3);	
											      //signInSelected = "false";
											      //$('#loginDiv').css("display","none");
											      //$('#registerDiv').css("display","inline");
											  },
											  error: function( xhr,status,error ){
												  $("#statusBlock").css("display", "block");	
												  $("#tcloginErrorSpan").css("display", "block");	
												  $( "#tcloginErrorSpan" ).html("There was some error. Please try again later");	
												  $(".k-loading-mask").hide();								  		 
											  }
											});
										}										 
								  },
								  error: function( xhr,status,error ){
									  $("#statusBlock").css("display", "block");	
									  $("#tcloginErrorSpan").css("display", "block");	
									  $( "#tcloginErrorSpan" ).html("There was some error. Please try again later");	
									  $(".k-loading-mask").hide();								  		 
								  }
							});
						  	$('#select-family-member-Box').html("");
						  	$.ajax({
						  	  type: "POST",
						  	  url: "getAccountDetailsByEmail",	
						  	  data: { emailId: userName},											 
						  	  success: function( accData ) {												  
						  		  if(accData != null && accData.length > 0){	
						  			var addFamilyHtml = '';
						  			addFamilyHtml += '<p class="add-family-member-p">';
						  			addFamilyHtml += '<div>';

						  			addFamilyHtml += '<div>Please select the family member(s).</div><br />';

						  			addFamilyHtml += '<div>';
						  			addFamilyHtml += '<table cellpadding="5px" border="1" id="selectFamilyMemberTable" style="border-collapse: collapse;"> ';
						  			addFamilyHtml += '<tr> ';
						  			addFamilyHtml += '<th>&nbsp;</th>';
						  			addFamilyHtml += '<th>First Name</th> ';
						  			addFamilyHtml += '<th>Last Name</th>';
						  			addFamilyHtml += '<th>Gender</th> ';
						  			addFamilyHtml += '<th style="display :none;">&nbsp;</th>';
						  			addFamilyHtml += '<th style="display :none;">&nbsp;</th>';
						  			addFamilyHtml += '<th style="display :none;">&nbsp;</th>';
						  			addFamilyHtml += '</tr>';
						  			var addFamilyKid = '';
						  			var kidsPresent = false;

						  			  for(var i = 0; i<accData.length; i++){        
						  				   var dataArr = accData[i];
						  				   if(dataArr != null && dataArr.userLst.length > 0){
							  				   for(var k = 0; k<dataArr.userLst.length; k++){        
									  				var userArr = dataArr.userLst[k];									  				
									  				var birthDate = new Date(userArr.dateOfBirth);
									  				var month = birthDate.getMonth()+1; 
									  				var date = birthDate.getDate();
									  				var year  = birthDate.getFullYear();
									  				var formattedDate = month +"/"+date+"/"+year;
									                
									  				if(isAdult(birthDate)){
										  				addFamilyHtml += '<tr>';
										  				addFamilyHtml += '<td><input type="checkbox" name="selectMemberCheckBox" class="selectMemberCheckBox"></td> ';
										  				addFamilyHtml += '<td class="userFNameTd">'+userArr.personFirstName+'</td> ';
										  				addFamilyHtml += '<td class="userLNameTd">'+userArr.personLastName+'</td> ';
										  				addFamilyHtml += '<td class="userGenderTd">'+userArr.gender+'</td>';
										  				addFamilyHtml += '<td style="display :none;" class="userBdayTd">'+formattedDate+'</td>';
										  				addFamilyHtml += '<td style="display :none;" class="userPhoneNoTd">'+userArr.primaryFormattedPhoneNumber+'</td> ';
										  				addFamilyHtml += '<td style="display :none;" class="userEmailAddTd">'+userArr.primaryEmailAddress+'</td> ';
										  				addFamilyHtml += '</tr> ';
									  				}else {									  				
										  				addFamilyKid += '<tr> ';
										  				addFamilyKid += '<td><input type="checkbox" class="selectChildCheckBox"></td> ';
										  				addFamilyKid += '<td class="kidFirstName">'+userArr.personFirstName+'</td> ';
										  				addFamilyKid += '<td class="kidLastName">'+userArr.personLastName+'</td> ';
										  				addFamilyKid += '<td class="kidGender">'+userArr.gender+'</td> ';
										  				addFamilyKid += '<td style="display :none;" class="kidDob">'+formattedDate+'</td> ';
										  				addFamilyKid += '</tr>';
										  				kidsPresent = true;
									  				}
							  				   }
						  				   }
						  				 	addFamilyHtml += '</table> ';
						  					addFamilyHtml += '</div><br />';
						  					
											if(kidsPresent){
							  					addFamilyHtml += '<div id="kidsSelectKendoBox">Please select the kid(s) Information.</div><br />';
							  					addFamilyHtml += '<div>';
	
							  					addFamilyHtml += '<table cellpadding="5px" border="1" id="selectKidsInfoTable" style="border-collapse: collapse;"> ';
							  					addFamilyHtml += '<tr>';
							  					addFamilyHtml += '<th>&nbsp;</th>';
							  					addFamilyHtml += '<th>First Name</th> ';
							  					addFamilyHtml += '<th>Last Name</th> ';
							  					addFamilyHtml += '<th>Gender</th> ';
							  					addFamilyHtml += '<th style="display :none;" >&nbsp;</th>';
							  					addFamilyHtml += '</tr>';
							  					
							  					addFamilyHtml += addFamilyKid;
												
							  					addFamilyHtml += '</table> ';
							  					addFamilyHtml += '</div>';
							  					addFamilyHtml += '</div>';
											}
						  					addFamilyHtml += '</p>';
						  					addFamilyHtml += '<div class="confirmbutton" align="center"><button class="confirm-family-member-select k-button" style=" width: 35%;">Ok</button></div>';
						  					
						  					
						  					/* $('#firstNameTd').html(dataArr[5]);
						  					$('#lastNameTd').html(dataArr[6]);
						  					$('#dobTd').html(dataArr[7]);
						  					$('#phoneNumberTd').html(dataArr[8]);
						  					$('#emailTd').html(dataArr[9]);
						  					
						  					$('#firstName').attr("value", dataArr[5]);
						  					$('#lastName').attr("value", dataArr[6]);										    			
						  					$('#phoneNumber').attr("value", dataArr[8]);
						  					$('#email').attr("value", dataArr[9]); */
						  					
						  					$('#addressLine1Td').html(dataArr.addressLine1);
						  					$('#addressLine2Td').html(dataArr.addressLine2);
						  					$('#cityTd').html(dataArr.city);
						  					$('#stateTd').html(dataArr.state);
						  					$('#postalCodeTd').html(dataArr.postalCode);	
						  					
						  					$('#addressLine1').attr("value", dataArr.addressLine1);
						  					$('#addressLine2').attr("value", dataArr.addressLine2);
						  					$('#city').attr("value", dataArr.city);
						  					$('#state').attr("value", dataArr.state);
						  					$('#postalCode').attr("value", dataArr.postalCode);
						  					
						  					/* 
						  					
						  					$(document).find('#selectMemberDiv .secUserFNameTd').html(dataArr[5]);
						  					$(document).find('#selectMemberDiv .secUserLNameTd').html(dataArr[6]);
						  					$(document).find('#selectMemberDiv .secUserGenderTd').html(dataArr[8]);
						  					$(document).find('#selectMemberDiv .secUserBdayTd').html(dataArr[9]);
						  					$(document).find('#selectMemberDiv .secUserPhoneNoTd').html(dataArr[5]);
						  					$(document).find('#selectMemberDiv .secUserEmailAddTd').html(dataArr[8]); */
						  					
						  					$("#passwordTRremove").remove();
						  					$(document).find('#selectMemberDiv').html(addFamilyHtml);	
						  					//console.log(selectedHeaderInfo);
						  					//console.log($(document).find('#kidsSelectKendoBox'));
						  					/* if(selectedHeaderInfo == 'Adult' || selectedHeaderInfo == 'Youth' || selectedHeaderInfo == 'Two Adults'){
						  						kendoWindowSelect.find("#kidsSelectKendoBox").css("display", "none");
						  					}else if(selectedHeaderInfo == 'One Adultw/ Kids' || selectedHeaderInfo == 'Two Adultsw/ Kids' || selectedHeaderInfo == 'Three Adultsw/ or w/o kids'){
						  						kendoWindowSelect.find("#kidsSelectKendoBox").css("display", "inline");
						  					}else{
						  						kendoWindowSelect.find("#kidsSelectKendoBox").css("display", "none");
						  					}  */
						  					
						  					signInSelected = "false";
						  					$('#memberSignInSpan').css("display","none");
						  					$('#selectFamilyMemberbtn').css("display","inline");
						  					$('#loginDiv').css("display","none");
						  					$('#registerDiv').css("display","inline");						  					
						  					$('#select-family-member-Box').html($("#selectMemberDiv").html());
						  					$(".stepContainer").height($(".stepContainer").find("div:not(:hidden)").height());
						  			  }
						  			  
						  		  }
						  	  },
						  	  error: function( xhr,status,error ){
						  		  $("#statusBlock").css("display", "block");	
						  		  $("#tcloginErrorSpan").css("display", "block");	
						  		  $( "#tcloginErrorSpan" ).html("There was some error. Please try again later");	
						  		  $(".k-loading-mask").hide();								  		 
						  	  }
						  });
						  	
					  	  }else if(dat == 'Failure'){
					  		$("#statusBlock").css("display", "block");	
					  		$("#tcloginErrorSpan").css("display", "block");	
							$( "#tcloginErrorSpan" ).html("User name or Password did not match. Please try again.");	
							$(".k-loading-mask").hide();
					  	  }else {
					  		$("#statusBlock").css("display", "block");	
					  		$("#tcloginErrorSpan").css("display", "block");	
							$( "#tcloginErrorSpan" ).html("There was some error. Please try again later");	
							$(".k-loading-mask").hide();					  		 
					  	  }					  
					  },
					  error: function( xhr,status,error ){
						  $("#statusBlock").css("display", "block");	
						  $("#tcloginErrorSpan").css("display", "block");	
							$( "#tcloginErrorSpan" ).html("There was some error. Please try again later");	
							$(".k-loading-mask").hide();
					  		 
					  }
				});
		 }		  
	});	
	
});
</script>