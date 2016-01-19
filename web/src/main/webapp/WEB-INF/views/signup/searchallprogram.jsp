<%@ include file="../../layouts/include_taglib.jsp"%>
<form:form name="signupFrm" id="signupFrm" action="signupProgram" method="post">
	<div style="height:40px;">
		<span class="head">SEARCH PROGRAMS</span>
	</div>
	
	<div class="searchdiv">
		<span class="head searchFields">Item Type</span>
		<span class="searchFields">
			<select id="itemtype" name="itemtype" style="width:250px;">
				<option value="">All</option>
				<option value="PROGRAM">Programs</option>
				<option value="Event">Event</option>
				<option value="childcare">Child Care</option>
			</select>
		</span>
	</div>	
	
	<div class="searchdiv">
		<span class="head searchFields">Contact</span>
		<span class="searchFields">
			<select id="contact" name="contact" style="width:250px;">
				<option value="" selected="selected">All</option>
				<c:forEach var="member" items="${AlluserS}" varStatus="count">
					<option value="${member.personFirstName } ${member.personLastName }">${member.personFirstName } ${member.personLastName }</option>
				</c:forEach>
			</select>
		</span>
	</div>
	<div class="searchdiv">
		<span class="head searchFields">Date Range</span>
		<span class="searchFields">
			<span><input name="datepicker" id="datepicker" value="" style="width:130px;"/></span>
			<span> - </span>
			<span><input name="datepickerend" id="datepickerend" value="" style="width:130px;"/></span>
		</span>
	</div>
	<div class="searchdiv">
		<span class="head searchFields">&nbsp;</span>
		<span class="searchFields">
			<span class="k-button" id="SearchProgramBtn">Go >></span>
		</span>
	</div>

</form:form>

<script>
	$(document).ready(function(){ 
		var pStartDate = getParameterByName('programStDt');
		var pEndDate = getParameterByName('programEndDt');
		var contactName = getParameterByName('contactName');
		var type = getParameterByName('itemType');
		
		if(pStartDate != null && pStartDate != ""){
			$("#datepicker").val(pStartDate);
		}
		if(pEndDate != null && pEndDate != ""){
			$("#datepickerend").val(pEndDate);
		}
		if(contactName != null && contactName != ""){
			$("#contact").val(contactName);
		}
		if(type != null && type != ""){
			$("#itemtype").val(type);
		}
		
		//alert(pStartDate);
		$("#datepicker").kendoDatePicker({
			value: new Date(),
			format: "MM/dd/yyyy"
		});
		
		$("#datepickerend").kendoDatePicker({
			value: new Date(new Date().getTime() + 24 * 7 * 60 * 60 * 1000),
			format: "MM/dd/yyyy"
		});
		
		$("#contact").kendoDropDownList();
		$("#itemtype").kendoDropDownList();
		
		$( "#SearchProgramBtn" ).click(function(){
			 $("#tcSuccessSpan").css("display", "none");
			 $("#tcErrorSpan").css("display", "none");
			 $(".k-loading-mask").show();
			 $("#program").fadeIn(100);
			 //$("#details-checkout").fadeOut("fast");
			 
			 var itemType = $("#itemtype").val();
			 var contactName = $("#contact").val();
			 var programStDt = $("#datepicker").val();
			 var programEndDt = $("#datepickerend").val();
			 
			 getAllPrograms(itemType,contactName,programStDt,programEndDt);
		});
		
		
	});
	
	function getAllPrograms(itemType,contactName,programStDt,programEndDt){
		/*
		$.ajax({
			  type: "GET",
			  url:"viewAllPrograms?itemType="+itemType+"&contactName="+contactName+"&programStDt="+programStDt,
			  success: function( data ) {
				 //console.log(data);
			  	 //console.log(data.length);
			  	 $(".k-loading-mask").hide();
			  	 $("#program").html(data);
			  	 
			  }
		});*/
		location.href = "viewAllPrograms?itemType="+itemType+"&contactName="+contactName+"&programStDt="+programStDt+"&programEndDt="+programEndDt;
	}
</script>