<%@ page language="java" contentType="text/html; charset=ISO-8859-1"    pageEncoding="ISO-8859-1"%>
<%    	String contextPath = request.getContextPath();   %>
<%@ include file="../../layouts/include_taglib.jsp"%>
<link href="<%=contextPath %>/resources/css/offender.css" rel="stylesheet" media="screen">

<div id="main">
	<div id="pageLoad" class="k-loading-mask" style="width:100%;height:100%; position:absolute; top:114px; left:0px; z-index:9999">
		<span class="k-loading-text">Loading...</span>
		<div class="k-loading-image">
			<div class="k-loading-color"></div>
		</div>
	</div>
	
	<!-- hidden parameters -->
	<input type="hidden" name="agentUid" id="agentUid" value="${agentUid }">
	<input type="hidden" name="pageNo" id="pageNo" value="1">
	<c:forEach var="s" items="${sexOffenderDetails}" varStatus="count">	
		<input type="hidden" name="sexOffenderDetails" id="sexOffenderDetails_${count.count }" value="${s.sexOffenderIdNumber }">
		<input type="hidden" name="sexOffenderPicture" id="sexOffenderPicture_${count.count }" value="${s.photo }">
	</c:forEach>
	
	<div id="sexOffenderInfo" style="display:none;">
		<div id="contact-details" class="k-block contact-details">
			<div style="float:left">
				<span class="memberNumber">${memberNumber }</span><br>
				<span class="name"></span>
			</div>
			
			<div style="float:right">
				<span id="address1"></span><br>
				<span id="addressInfo"></span>
			</div>
		</div>
		
		<div id="offender-header" class="k-block offender-header">
			<span style="float:left">Sex Offender Details</span>
			
			<span style="float:right">
				<span style="position: relative; top:-8px">&nbsp;&nbsp;<input id="SaveBtn" type="button" class="k-button" value="Save" style="width:100px;">&nbsp;&nbsp;</span>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<span id="PrevLink" class="disableLink"><img src="<%=contextPath %>/resources/img/previous.jpg" width="20"> </span>
				<span> <span id="currentPage">1</span> of ${fn:length(sexOffenderDetails) } </span>
				<span id="NextLink" class="enableLink"> <img src="<%=contextPath %>/resources/img/next.jpg" width="20"></span>
				<span>&nbsp;&nbsp; </span>
			</span>
		</div>
		
		<div id="content-block">
			<div id="top-block" style="margin-top:4px; height:543px;">
				<div id="top-block-details" style="height:538px;">
					<div class="k-block block-header">
						Offender Details
					</div>
					<div id="info" style="margin-top:4px; padding:8px; height:300px;">
						<div class="k-block" id="picture">
							<img id="sexOffenderPhoto" src="${sexOffenderDetails[0].photo }" width="170px" height="190px">
						</div>
						<div id="details">
							<div id="sectionLoad1" class="k-loading-image" style="position:relative; text-align:center; height:300px;">
								<div class="k-loading-color"></div>
							</div>
							<div style="display:none;" id="detailsDiv">
								
							</div>
						</div>
					</div>
					<div id="statusRemarks" style="margin-top:4px; padding:8px; "></div>
					<div id="offencesDetails" style="margin-top:4px; padding:8px; "></div>
				</div>
				
				<div style="float:right; height:540px; width:38%;">
					<div id="top-block-confirmation">
						<div class="k-block block-header">
							Offender Confirmation
						</div>
						<div id="info" style="margin-top:4px; padding:8px;">
							<div id="sectionLoad6" class="k-loading-image" style="position:relative; text-align:center; height:190px;">
								<div class="k-loading-color"></div>
							</div>
							<div id="offenderConfirmation" style="padding: 8px; display:none;">
								Is <span class="name"></span> 
								Offender ID <span id="SexOffenderIdNumber"></span> 
								the same person as member number <span class="memberNumber">${memberNumber }</span>?
								<div style="margin-top:12px;">
									<span><input type="radio" checked="checked" name="isSexOffender" value="Yes"></span>&nbsp;&nbsp;<span>Yes</span>
									<span style="margin-left:60px; margin-right:60px;">&nbsp;</span>
									<span><input type="radio" name="isSexOffender" value="No"></span>&nbsp;&nbsp;<span>No</span>
								</div>
								<div style="margin-top:12px;">
									<span>Comment:</span><br>
									<span><textarea cols="10" rows="5" style="width:385px;" name="comment" id="comment" placeHolder="Comment"></textarea></span>
								</div>
							</div>
						</div>
					</div>
					
					<div id="aliases" class="common-block1" >
						<div class="k-block block-header">
							Aliases
						</div>
						<div id="info" style="margin-top:4px; padding:8px;">
							<div id="sectionLoad2" class="k-loading-image" style="position:relative; text-align:center; height:80px;">
								<div class="k-loading-color"></div>
							</div>
							<div style="display:none;" id="aliasdiv">
									
							</div>
						</div>
					</div>
					
					<div id="vandvinformation" class="common-block1">
						<div class="k-block block-header">
							Vehicle and Vessel Information
						</div>
						<div id="info" style="margin-top:4px; padding:8px;">
							<div id="sectionLoad3" class="k-loading-image" style="position:relative; text-align:center; height:80px;">
								<div class="k-loading-color"></div>
							</div>
							<div style="display:none;" id="vehicleDiv">
							
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<!--  
			<div id="middle-block" style="margin-top:4px; height:185px;">
				<div id="aliases" class="common-block" style="float:left;">
					<div class="k-block block-header">
						Aliases
					</div>
					<div id="info" style="margin-top:4px; padding:8px;">
						<div id="sectionLoad2" class="k-loading-image" style="position:relative; text-align:center; height:100px;">
							<div class="k-loading-color"></div>
						</div>
						<div style="display:none;" id="aliasdiv">
								
						</div>
					</div>
				</div>
				
				<div id="vandvinformation" class="common-block" style="float:right;">
					<div class="k-block block-header">
						Vehicle and Vessel Information
					</div>
					<div id="info" style="margin-top:4px; padding:8px;">
						<div id="sectionLoad3" class="k-loading-image" style="position:relative; text-align:center; height:100px;">
							<div class="k-loading-color"></div>
						</div>
						<div style="display:none;" id="vehicleDiv">
						
						</div>
					</div>
				</div>
			</div>
			 -->
			 
			<div id="middle-block1" style="margin-top:4px; height:185px;">
				<div id="marks" class="common-block" style="float:left;">
					<div class="k-block block-header">
						Scars, Marks and Tattoos
					</div>
					<div id="info" style="margin-top:4px; padding:8px;">
						<div id="sectionLoad4" class="k-loading-image" style="position:relative; text-align:center; height:100px;">
							<div class="k-loading-color"></div>
						</div>
						<div style="display:none;" id="scarsDiv">
						
						</div>
					</div>
				</div>
				
				<div id="addressinformation" class="common-block" style="float:right;">
					<div class="k-block block-header">
						Address Information
					</div>
					<div id="info" style="margin-top:4px; padding:8px;">
						<div id="sectionLoad5" class="k-loading-image" style="position:relative; text-align:center; height:100px;">
							<div class="k-loading-color"></div>
						</div>
						<div style="display:none;" id="addressDiv">
						
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div style="clear: both;"></div> 
	<div id="info_Block">
		<div class="k-block k-success-colored" id="tcSuccessSpan"></div>
		<div class="k-block k-error-colored" id="tcErrorSpan"></div>
	</div>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		var sexOffenderIdNumber = '${sexOffenderDetails[0].sexOffenderIdNumber}';
		if(sexOffenderIdNumber==null || sexOffenderIdNumber==""){
			hideLoadingGraphic();
			$("#sexOffenderInfo").hide();
			$("#tcErrorSpan").html("No Sex Offender Details found for this contact!");
			$("#tcErrorSpan").show();
			return;
		}
		
		var totalRecords = '${recordscount}';

		if(totalRecords==1){
			$("#NextLink").removeClass("enableLink");
			$("#NextLink").addClass("disableLink");
		}
		
		$("#NextLink").click(function(){

			if($(this).attr("class")=='disableLink')
				return;
			
			var currentPage = $("#pageNo").val();
			currentPage++;
			
			$("#pageNo").val(currentPage);
			$("#currentPage").html(currentPage);
			
			$("#PrevLink").removeClass("disableLink");
			$("#PrevLink").addClass("enableLink");

			if(currentPage==totalRecords){
				$("#NextLink").removeClass("enableLink");
				$("#NextLink").addClass("disableLink");
			}
			
			var sexOffenderIdNumber = $("#sexOffenderDetails_"+currentPage).val();
			fetchIndividualDetails(sexOffenderIdNumber);
			
			var sexOffenderPicture = $("#sexOffenderPicture_"+currentPage).val();
			$("#sexOffenderPhoto").attr("src",sexOffenderPicture);
			
		});
		
		$("#PrevLink").click(function(){
			if($(this).attr("class")=='disableLink')
				return;
			
			var currentPage = $("#pageNo").val();
			currentPage= currentPage-1;
			
			$("#pageNo").val(currentPage);
			$("#currentPage").html(currentPage);
			
			if(currentPage==1){
				$("#PrevLink").removeClass("enableLink");
				$("#PrevLink").addClass("disableLink");
			}
			
			if(currentPage<totalRecords){
				$("#NextLink").removeClass("disableLink");
				$("#NextLink").addClass("enableLink");
			}

			var sexOffenderIdNumber = $("#sexOffenderDetails_"+currentPage).val();
			fetchIndividualDetails(sexOffenderIdNumber);
			
			var sexOffenderPicture = $("#sexOffenderPicture_"+currentPage).val();
			$("#sexOffenderPhoto").attr("src",sexOffenderPicture);
		});
		
		setTimeout(function(){
			$("#pageLoad").hide();
			$("#sexOffenderInfo").fadeIn();
		}, 2000);
		
		setTimeout(function(){
			hideLoadingGraphic();
		 }, 5000);
		
		setTimeout(function(){
			fetchIndividualDetails(sexOffenderIdNumber);
		}, 4000);
		
		
		$("#SaveBtn").click(function(){
			var isSexOffenderRadio = $("input:radio[name=isSexOffender]:checked").val();
			var comment = $("#comment").val();
			
			if(isSexOffenderRadio=='No'){
				//alert(comment);
				if(comment==""){
					// comment is required 
					showAlert("Comment is required");
					return;
				}
			}
			
			var currentPage = $("#pageNo").val();
			var sexOffenderIdNumber = $("#sexOffenderDetails_"+currentPage).val();
			
			var url = "saveSexOffenderInfo?memberContactId="+getParameterByName("cId")+"&agentUid="+getParameterByName("agentUid")+"&Comment="+comment+"&sexOffender="+isSexOffenderRadio+"&sexOffenderIdNumber="+sexOffenderIdNumber;
			//alert(url);
			$.ajax({
				  type: "GET",
				  async: false,
				  url:url,
				  success: function( data ) {
					 //alert(data);
					 //$("#unAuthAccButton"+memberContactId).html("Request Sent");
					 if(data){
						 showAlert("The information has been Saved");
					 } else {
						 showAlert("There was some error, Please try again later");
					 }
				  },
				  error: function( xhr,status,error ){
					  console.log(xhr);
				  }
			});
		});
	});
	
	function fetchIndividualDetails(sexOffenderIdNumber){
		 $("#sectionLoad1").show();
		 $("#sectionLoad2").show();
		 $("#sectionLoad3").show();
		 $("#sectionLoad4").show();
		 $("#sectionLoad5").show();
		 $("#sectionLoad6").show();
		 
		 $("#detailsDiv").hide();
		 $("#aliasdiv").hide();
		 $("#vehicleDiv").hide();
		 $("#scarsDiv").hide();
		 $("#addressDiv").hide();
		 $("#offenderConfirmation").hide();
		 $("#statusRemarks").hide();
		 $("#offencesDetails").hide();
		 
		var url = "FetchIndividualDetailsBySexOffenderIdNumber?SexOffenderIdNumber="+sexOffenderIdNumber;
		//alert(url);
		$.ajax({
			  type: "GET",
			  async: false,
			  url:url,
			  success: function( data ) {
				 //console.log(data);

				 var jsonDataArr = jQuery.parseJSON(data);
				 if(jsonDataArr != null && jsonDataArr.length > 0){	
					 var offenderDetailsContent = "";
					 var scarContent = "";
					 var aliasContent = "";
					 var vehicleContent = "";
					 var addressContent = "";
					 
					 $.each(jsonDataArr, function(i,jsonData) {
						 //console.log(jsonData);
						 $.each(jsonData.offenderDetails, function(j,o) {
							 	//alert(eval("o."+offenderFieldData));
							 	// offender details label
								var offenderFieldName = "Offender Id,Eye color,Designation,Hair Color,Name,Height,Gender,Weight,Birth Date,Race,SexOffenderDatabaseId,SocialSecurityNumber,Age,Citizenship,Ethnicity,Complexion,BodyBuild,SexOffenderRegisterDate,SexOffenderRiskLevel,SexOffenderClassification";
								var offenderFieldData = "SexOffenderIdNumber,EyeColor,Designation,HairColor,Name,Height,Gender,Weight,DOB,Race,SexOffenderDatabaseId,SocialSecurityNumber,Age,Citizenship,Ethnicity,Complexion,BodyBuild,SexOffenderRegisterDate,SexOffenderRiskLevel,SexOffenderClassification";
							 	var offenderFieldDataArray = offenderFieldData.split(',');
							 	var offenderFieldNameArray = offenderFieldName.split(',');
							 	
								for(var i = 0; i < offenderFieldDataArray.length; i++) {
									if(i%2==0){
								 	offenderDetailsContent += '<div class="divsection">';
									}
								 		offenderDetailsContent += '<div class="detailSection">';
									 		offenderDetailsContent += '<span>'+offenderFieldNameArray[i]+':</span>&nbsp;';
									 		offenderDetailsContent += '<span id="'+offenderFieldDataArray[i]+'Val">'+eval("o."+offenderFieldDataArray[i])+'</span>';
									 	offenderDetailsContent += '</div>';
									 	/*
									 	offenderDetailsContent += '<div class="detailSection">';
									 		offenderDetailsContent += '<span>Eye color:</span>&nbsp;';
									 		offenderDetailsContent += '<span id="eyeColorVal"></span>';
									 	offenderDetailsContent += '</div>';
									 	*/
									
									if(i%2==1){
									offenderDetailsContent += '</div>';
									}
								}
								
								$("#sectionLoad1").hide();
								$("#detailsDiv").html(offenderDetailsContent);
								$("#detailsDiv").show();
								
								$(".name").html(o.Name);
								$("#SexOffenderIdNumber").html(o.SexOffenderIdNumber);
								
								$("#statusRemarks").html(o.StatusRemarks);
								$("#statusRemarks").show();
								var offencesHtml = "<span><strong><u>Offences Details</u></strong></span><br>";
								 $.each(o.offenderOffences, function(k,off) {
									 //console.log(off);
									 //alert(off.OffenseDescription);
									 offencesHtml += "<span>"+off.OffenseDescription+"</span><br>"
								 });
								$("#offencesDetails").html(offencesHtml);
								$("#offencesDetails").show();
								
						 });
						 
						 $.each(jsonData.aliasCollection, function(j,a) {
							 	// Alias details label
								var aliasFieldName = "FirstName,MiddleName,LastName,DOB";
								var aliasFieldData = "FirstName,MiddleName,LastName,DOB";
							 	var aliasFieldDataArray = aliasFieldData.split(',');
							 	var aliasFieldNameArray = aliasFieldName.split(',');
							 	
								for(var i = 0; i < aliasFieldDataArray.length; i++) {
									if(i%2==0){
										aliasContent += '<div class="divsection">';
									}
										aliasContent += '<div class="detailSection" style="width:180px;">';
											aliasContent += '<span>'+aliasFieldNameArray[i]+':</span>&nbsp;';
											aliasContent += '<span id="'+aliasFieldDataArray[i]+'Val">'+eval("a."+aliasFieldDataArray[i])+'</span>';
										aliasContent += '</div>';
									
									if(i%2==1){
									aliasContent += '</div>';
									}
								}
								
								$("#sectionLoad2").hide();
								$("#aliasdiv").html(aliasContent);
								$("#aliasdiv").show();
						 });
						 
						 $.each(jsonData.vehicleInformation, function(j,v) {
							 	// Alias details label
								var vehicleFieldName = "DriversLicenseNumber,DriversLicenseState,DriversLicenseExpirationYear";
								var vehicleFieldData = "DriversLicenseNumber,DriversLicenseState,DriversLicenseExpirationYear";
							 	var vehicleFieldDataArray = vehicleFieldData.split(',');
							 	var vehicleFieldNameArray = vehicleFieldName.split(',');
							 	
								for(var i = 0; i < vehicleFieldDataArray.length; i++) {
									if(i%2==0){
										vehicleContent += '<div class="divsection">';
									}
										vehicleContent += '<div class="detailSection" style="width:180px;">';
											vehicleContent += '<span>'+vehicleFieldNameArray[i]+':</span>&nbsp;';
											vehicleContent += '<span id="'+vehicleFieldDataArray[i]+'Val">'+eval("v."+vehicleFieldDataArray[i])+'</span>';
										vehicleContent += '</div>';
									
									if(i%2==1){
									vehicleContent += '</div>';
									}
								}
								
								$("#sectionLoad3").hide();
								$("#vehicleDiv").html(vehicleContent);
								$("#vehicleDiv").show();
						 });
						
						 $.each(jsonData.scarMarksCollection, function(j,s) {
							 	//alert(s.Description);
								scarContent += s.Description +', ';
						 });
						 scarContent = replaceLastChar($.trim(scarContent));
						 $("#sectionLoad4").hide();
						 $("#scarsDiv").html(scarContent);
						 $("#scarsDiv").show();
						 
						 $.each(jsonData.addressCollection, function(j,ad) {
							 	// Alias details label
								var addressFieldName = "Address1,Address2,City,State,PostalCode,PhoneNumber,AddressVerifiedDate,AddressReportedDate";
								var addressFieldData = "Address1,Address2,City,State,PostalCode,PhoneNumber,AddressVerifiedDate,AddressReportedDate";
							 	var addressFieldDataArray = addressFieldData.split(',');
							 	var addressFieldNameArray = addressFieldName.split(',');
							 	
								for(var i = 0; i < addressFieldDataArray.length; i++) {
									if(i%2==0){
									addressContent += '<div class="divsection">';
									}
										addressContent += '<div class="detailSection">';
											addressContent += '<span>'+addressFieldNameArray[i]+':</span>&nbsp;';
											addressContent += '<span id="'+addressFieldDataArray[i]+'Val">'+eval("ad."+addressFieldDataArray[i])+'</span>';
										addressContent += '</div>';
									
									if(i%2==1){
									addressContent += '</div>';
									}
								}
								
								$("#sectionLoad5").hide();
								$("#addressDiv").html(addressContent);
								$("#addressDiv").show();
								
								$("#address1").html(ad.Address1);
								$("#addressInfo").html(ad.City + ", " + ad.State +", " + ad.PostalCode);
						 });
						 
						 $.each(jsonData.notes, function(j,n) {
							 $("#sectionLoad6").hide();
							 var comment = n.noteDescription;
							 //alert(comment);
							 var isUserSexOffender = n.offenderMatch;
							 $("#comment").val("");
							 if(typeof comment == 'undefined')
								 $("#comment").attr("placeHolder", "");
							 else
							 	$("#comment").attr("placeHolder", comment);
							 if(isUserSexOffender=='No'){
								 $("input:radio[value=No]").prop("checked", true);
							 }else{
								 $("input:radio[value=Yes]").prop("checked", true)
							 }
							 $("#offenderConfirmation").show();
						 });
						 
					 });
				 }
				
				 

			  },
			  error: function( xhr,status,error ){
				  console.log(xhr);
			  }
		});
		
		hideLoadingGraphic();
	}
	
	function replaceLastChar(str){
		
		var res = str.substring(0, str.length-1);
		//alert(res);
		return res;
	}
	
	function getParameterByName(name) {
	    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
	    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
	        results = regex.exec(location.search);
	    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
	}
	
	function hideLoadingGraphic(){
		 $("#pageLoad").hide();
		 $("#sectionLoad1").hide();
		 $("#sectionLoad2").hide();
		 $("#sectionLoad3").hide();
		 $("#sectionLoad4").hide();
		 $("#sectionLoad5").hide();
		 $("#sectionLoad6").hide();
	}
	
	function showAlert(message){
		var kendoWindow = $("<div />").kendoWindow({
        	title: "Alert",
        	resizable: false,
        	modal: true,
        	width:400
        });
		
		
		kendoWindow.data("kendoWindow")
         .content($("#alertBox").html())
         .center().open();
		
		$(".alertBoxMessage").html(message);
		
        kendoWindow
        .find(".ok-confirm")
        .click(function() {
        	if ($(this).hasClass("ok-confirm")) {         		
        		kendoWindow.data("kendoWindow").close();
        	}
        })
        .end();
	}
</script>

<script id="alertBox" type="text/x-kendo-template">
   <div class="alertBoxMessage">Confirm?</div>
	
   <div class="confirmbutton">
		<button class="ok-confirm k-button">Ok</button>
		&nbsp;&nbsp;&nbsp;
   		<span class="delete-cancel k-button" style="display:none;">Cancel</span>
	</div>
</script>