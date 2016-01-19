<%@ page language="java" contentType="text/html; charset=ISO-8859-1"    pageEncoding="ISO-8859-1"%>
<%    	String contextPath = request.getContextPath();   %>
<%@ include file="../../layouts/include_taglib.jsp"%>
<div> 
	<div class="k-loading-mask" style="width:100%;height:100%; position:absolute; top:0px; left:0px;display:none; z-index:9999; background-color:#EEEEEE;
    color: white;">
		<span class="k-loading-text">Please wait..</span>
		<div class="k-loading-image">
			<div class="k-loading-color"></div>
		</div>
	</div>
	<c:choose>
	<c:when test="${empty signup}">
		<div class="k-block k-error-colored" role="alert" style="height:40px;padding: 10px;">
			Invalid membership number, Please verify membership number and try again
		</div>
	</c:when>
	<c:otherwise>
		<div id="myprofile">
			<%@ include file="testLeft.jsp" %> 
		</div>
		<div id="programs">
			<%@ include file="testRight.jsp" %> 
		</div>
	</c:otherwise>
	</c:choose>
</div>

<script type="text/javascript">

var memberContactId=$("#primContId").val();
$(document).ready(function() {
	 $(".k-loading-mask").show();
	$("#dateOfBirth").kendoDatePicker();
	$("#dateOfBirthAdd").kendoDatePicker();
	$("#top_profile").attr('class', 'current');
	$("#top_login").attr('class', '');
	
	$("#phoneNumber").mask("(999) 999-9999");
	//var memberContactId=$("#primContId").val();
	var sexoffenderRed=$("#sexOffenderRed"+memberContactId).val();
	
	if(sexoffenderRed=='Yes'){
		contactSystemDirector(memberContactId); 
		$("#checkinMember"+memberContactId).hide();
		// document.getElementById("tabstrip3"+memberContactId).style.backgroundColor = "red";
		$("#tabstrip3"+memberContactId).css('background-color', 'red');
		$("#accDeny"+memberContactId).show();
		//$("#sexOffResonDiv"+memberContactId).show();
		// $("#accDenySexOff"+memberContactId).show();
		$("#unAuthAccess"+memberContactId).show();
		$("#allowGuest").show();
		
		 $(".k-loading-mask").hide();
	} else {
		var sendSexOffenderEmail = '${sendSexOffenderEmail}';
		var allowcheckin = '${allowcheckin}';
		
		//alert(allowcheckin);
		if(allowcheckin=="false"){ //alert("Checkin not allowed")
			$("#checkinMember"+memberContactId).hide();
			// document.getElementById("tabstrip3"+memberContactId).style.backgroundColor = "red";
			$("#tabstrip3"+memberContactId).css('background-color', 'red');
			$("#accDeny"+memberContactId).show();
			//$("#sexOffResonDiv"+memberContactId).show();
			// $("#accDenySexOff"+memberContactId).show();
			$("#unAuthAccess"+memberContactId).show();
			$("#allowGuest").show();
		}
		
		//alert(sendSexOffenderEmail)
		
		if(sendSexOffenderEmail=="true"){
			contactSystemDirector(memberContactId);
			//alert("Email sent");
		}
		
		$(".k-loading-mask").hide();
	}
	
	
	
		
	

	$("#tabstrip").kendoTabStrip({
		animation : {
			open : {
				effects : "fadeIn"
			}
		}
	});

	$("#tabstrip1").kendoTabStrip({
		animation : {
			open : {
				effects : "fadeIn"
			}
		}
	});
	$("#tabstrip2").kendoTabStrip({
		animation : {
			open : {
				effects : "fadeIn"
			}
		}
	});
	
	
	
	

	/* $('.scroller').slimScroll({
		height : '110px',
		width : '160px',
		alwaysVisible : true,
		color : '#666666',
		distance : '4px',
		railVisible : true,
		allowPageScroll : true
	}).parent().css({
		border : '1px solid #cccccc'
	}); */
	
});	

function add_Guest() {
	
	var window_add1 = $(document).find("#popup_add");
	window_add1.clone().kendoWindow({
		actions: [ "Refresh", "Close"],
		title : "Add Guest Contact",
		content : "addGuest",
		modal : false,
		width : "430px",
		close : onClose,
		deactivate : function(e) {
			this.destroy();
		}
	}).data("kendoWindow").center().open();
	$('[data-role="draggable"]').css('top', '20px');
}
var onClose = function() {
	//this.destroy();   
};

function contactSystemDirector(memberContactId){
	//alert(memberContactId);
	var checkedInLocation= $('#location').val();
	var url = "contactSystemDirector?memberContactId="+memberContactId+"&checkedInLocation="+checkedInLocation;
	$("#unAuthAccButton"+memberContactId).hide();
	$.ajax({
		  type: "GET",
		  async: false,
		  url:url,
		  success: function( data ) {
			 //alert(data);
			 $("#unAuthAccButton"+memberContactId).removeClass("k-button");
			 $("#unAuthAccButton"+memberContactId).addClass("success-msg k-block k-success-colored");
			 $("#unAuthAccButton"+memberContactId).html("Request Sent");
			 $("#unAuthAccButton"+memberContactId).show();
		  },
		  error: function( xhr,status,error ){
			  console.log(xhr);
		  }
	});
	
}
  
/* function displayReasonList(memberContactId){
   	$("#sexOffResonList"+memberContactId).kendoDropDownList(); 
   	var SexOffenderReasonDropdownlist = $("#sexOffResonList"+memberContactId).data("kendoDropDownList");
   	//SexOffenderReasonDropdownlist.select(0);
   	$("#unAuthAccess"+memberContactId).hide();
   	$("#sexOffResonDiv"+memberContactId).show();
   } */


   /* function selectReason(memberContactId,ele){
   	if(ele.value== 1){
   	    	// $("#unAuthAccess"+memberContactId).show();
   	    	$("#accDenySexOff"+memberContactId).hide();
   		unAuthAccess(memberContactId);
   	    }else if(ele.value== 0){
   	       alert("{location change to first pos}");
   	    	//$("#unAuthAccess"+memberContactId).hide();
   	    	$("#accDenySexOff"+memberContactId).show();
   	        } 
   	}	  */   
   
/* $("#CloseChekinWind").click( function (e) {
	 /* $("#popup_ChckinMember").data("kendoWindow").close();
   }); */
   




</script>
