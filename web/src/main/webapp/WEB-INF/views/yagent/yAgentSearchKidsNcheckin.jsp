<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%
    	String contextPath = request.getContextPath();
    
    %>
<head>
    <%-- <meta name="_csrf" content="${_csrf.token}"/>
    
    <meta name="_csrf_header" content="${_csrf.headerName}"/> --%>
<link rel="stylesheet" href="<%=contextPath %>/resources/css/style1.css" type="text/css" media="screen"/>
<script src="<%=contextPath %>/resources/js/jquery.validate.min.js"></script>
<script src="<%=contextPath %>/resources/js/maskedinput.min.js"></script>
<script src="<%=contextPath %>/resources/js/additional-methods.min.js"></script>
</head>   
<%@ include file="../../layouts/include_taglib.jsp"%>
<div class="k-window1" id="popup_ChckinKids" style="display: none;"></div>
<div class="k-window1" id="popup_add" style="display: none;"></div>
<div class="k-loading-mask"
			style="width: 100%; height: 100%; position: absolute; top: 114px; left: 0px; display: none; z-index: 9999">
			<span class="k-loading-text">Loading... Please wait</span>
			<div class="k-loading-image">
				<div class="k-loading-color"></div>
			</div>
		</div>
<div ><div class="border-bottom-h3">
				            	<span id="locSelMsg" style="margin-right: 140px; color: red;"> Please select YMCA location to search a Customer</span>
				           
							<select name="location" id="location" style="width:250px; margin-right: 150px;" ><br />
          <option value="0">--Select Location--</option>
          <c:forEach var="location" items="${locations}" varStatus="count">
           <c:if test="${ location.recordName != 'All Branches' && location.recordName != 'Bay Area' && location.branch == 'true' }">
            <option value="${location.id }">${location.recordName }</option>
           </c:if>
          </c:forEach>
       </select> </div></div>

<div  class="k-block" style="background-color: rgb(255, 255, 255);margin-bottom: 10px; ">
<table id="serchCustomerTable" cellpadding="0" cellspacing="0" width="99%"  rules="none" style="padding-left:4px;padding-top:4px;">
	
		<tr ><span class="head" width="100%" style="margin-left:4px;SERACH">SEARCH CUSTOMER </span></tr>
		 <tr>&nbsp;</tr>
		<tr>
		  <td style="padding-bottom:3px;padding-left:4px;"><input type="text" placeholder="Enter First Name" id="fname" name="fname"></td>
		  <td style="padding-bottom:3px;"><input type="text" placeholder="Enter Last Name" id="lname" name="lname"></td>
		 <td style="padding-bottom:3px;padding-left:4px;"><input type="text" placeholder="Enter Email" id="email" name="email"></td>
		 <td style="padding-bottom:3px;padding-left:4px;"><input type="text" placeholder="Enter Phone Number" id="MobNo" name="MobNo"></td>
		 <td style="padding-bottom:3px;padding-left:4px;"><input type="text" placeholder="Enter MembershipId" id="memId" name="memId"></td>
		 </tr> 
		 <tr>
		  <td style="padding-bottom:3px;padding-left:4px;"></td>
		  <td style="padding-bottom:3px;padding-left:4px;"></td>
		  <td style="padding-bottom:3px;" align="center"><span id="serchUser" class="k-button" onclick="searchUser()" style="text-shadow: none; width: 81px; margin-top: 4px; margin-left: -50px;">SEARCH</span></td>
		  <td style="padding-bottom:3px;"></td>
		 </tr> 
	</table>
</div>
<div id="kgrid"  ></div>


<script type="text/javascript">
$(document).ready(function() {
	$("#dateOfBirth").kendoDatePicker();
	$("#dateOfBirthAdd").kendoDatePicker();
	$("#top_profile").attr('class', 'current');
	$("#top_login").attr('class', '');
	$("#phoneNumber").mask("(999) 999-9999");
	$("#kgrid").hide();
	$("#serchCustomerTable").hide();
	$("#memId").focus();
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
	});  */
	
});	


//-------location select-----------
$("#location").kendoDropDownList(); 
var locationDropdownlist = $("#location").data("kendoDropDownList");
locationDropdownlist.select(0);
$("#location").on('change',function(e){  
    if(locationDropdownlist.text() != '--Select Location--'){
  //  alert("{location change}");
  	$("#locSelMsg").hide();
    $("#kgrid").show();
	$("#serchCustomerTable").show();
    }else if(locationDropdownlist.text() == '--Select Location--'){
       // alert("{location change to first pos}");
        $("#kgrid").hide();
    	$("#serchCustomerTable").hide();
    	$("#locSelMsg").show();
        }

    $("#memId").focus();
});


$("#memId").on('change',function(e){  
	var mId = $.trim($("#memId").val());
	if (mId != '') {
		mId = mId.substring(1);
		display_CheckInWindow(mId);	
	}
});


var source ;  
function initGrid(){
	 source = getRemoteData();
	 $("#kgrid").kendoGrid ( {
		 	dataSource: source,    	
		 	autoBind: false,
			height: 300,
	        filterable: false,
	        sortable: true,
	        pageable: true,
	        resizable: true,
	        columns: 
				[ {field: "customerName", title: "Customer Name" ,template: "<a href='\\#' class='link' onclick='display_kidsCheckInWindow(#=customerId#,null)'>#=customerName# </a>"},
				  {field: "FirstName", title: "First Name"},
				 {field: "LastName", title: "Last  Name"},
 				 {field: "EmailAddress", title: "Email id "},
				 {field: "FormattedPhoneNumber", title: "Phone No "},					 
				 {field: "DateOfBirth", title: "Date Of Birth"} 				 
 				]
	 });
}
	
function getRemoteData() { 
	source = new kendo.data.DataSource({
        autoSync: false,
        transport: {
            read: {
                type: "GET",
                url: "getUsersBySearchCriteria",
                //data: {"fName" : $("#fname").val(),"lName" :  $("#lname").val(),"eMail" : $("#email").val()},
                 data: function() {
                     return {
                    	 fName : $("#fname").val(),lName :  $("#lname").val(),eMail : $("#email").val(),phno : $("#MobNo").val()
                     }
                 },
                dataType: "json",                
                cache: false
            }    	            
        },
        pageSize: 5
    });
    return source;
}
		 
function searchUser(){
	source.read();
	return true;
}
$(document).ready(function() { 
	initGrid();
});


//for displyaing popup Checkin Window

function display_kidsCheckInWindow(customerId,mId) {
	//alert("u clik an user button");	
	//alert(customerId);
	var location= $('#location').val();
	//alert(loction);
	var url = "YAgentConsoleKidsCheckInWind?customerId="+customerId+"&location="+location ;
	if (mId != null) {
		url = url + "&mId="+mId;
	}
	var window_add = $("#popup_ChckinKids");
	window_add.clone().kendoWindow({
		title : "Check-In Kids",
		//actions: ["Refresh", "Close"],
		content : url,
		modal : false,
		width : "1150px",
		height : "650px",
		close : onClose,
		//refresh: onRefresh,
		deactivate : function(e) {
			this.destroy();
		}
	}).data("kendoWindow").center().open();
	$('[data-role="draggable"]').css('top', '20px');
}
var onClose = function() {
	this.destroy();   
};
function onRefresh(e) {
    kendoConsole.log("event :: refresh");
}

function closeAllKidwindowk(){

	$("div.k-window1").each(function(index, element){
		var windowall = $(this);
		if (!windowall.data("kendoWindow")) {
			windowall.kendoWindow({
				width: "1050px"
			});
		}
		windowall.data("kendoWindow").close();
	});
} 
</script>