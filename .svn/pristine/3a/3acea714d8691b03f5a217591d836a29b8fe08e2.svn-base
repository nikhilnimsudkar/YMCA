<%@ include file="../../layouts/include_taglib.jsp"%>

<div  class="k-block" style="background-color: rgb(255, 255, 255);margin-bottom: 10px; ">
	<table cellpadding="0" cellspacing="0" width="99%"  rules="none" style="padding-left:4px;padding-top:4px;">
		<tr ><span class="head" width="100%" style="margin-left:4px;SERACH">SERACH CUSTOMER </span></tr>
		 <tr>&nbsp;</tr>
		<tr>
		  <td style="padding-bottom:3px;padding-left:4px;"><input type="text" placeholder="Enter First Name" id="fname" name="fname"></td>
		  <td style="padding-bottom:3px;"><input type="text" placeholder="Enter Last Name" id="lname" name="lname"></td>
		 <td style="padding-bottom:3px;padding-left:4px;"><input type="text" placeholder="Enter Email" id="email" name="email"></td>
		 <td style="padding-bottom:3px;padding-left:4px;"><input type="text" placeholder="Enter Phone Number" id="MobNo" name="MobNo"></td>
		 </tr> 
		 <tr>
		  <td style="padding-bottom:3px;padding-left:4px;" colspan="4"><input type="text" placeholder="Enter Customer Name" id="cname" name="cname"></td>		  
		 </tr>
		 <tr>
		  <td style="padding-bottom:3px;padding-left:4px;"></td>
		  <td style="padding-bottom:3px;" align="center"><span id="serchUser" class="k-button" onclick="searchUser()" style="width: 70px; text-shadow: none;">SEARCH</span></td>
		  <td style="padding-bottom:3px;"></td>
		 </tr> 
	</table>
</div>
<div id="kgrid"  ></div>

<!-- <div id="tabstrip3" class="k-block" style="background-color: rgb(255, 255, 255); margin-left: 0px; width: 803px;">
<span class="head" width="100%" style="margin-left:4px;">MEMBERSHP CHECKIN</span><br>
Click here to check your Membership: <span id="checkMemship" class="k-button" onclick="membershipCheck()" style="width: 100px; text-shadow: none;">CHECK-IN</span>
</div> -->

<script id="checkMembership" type="text/x-kendo-template">
    <p class="delete-message">Membeship checkin process.</p>
	
    <div class="confirmbutton"><button class="delete-confirm k-button">Manual Check</button>&nbsp;&nbsp;&nbsp;
    <span class="delete-cancel k-button">Barcode Scan</span></div>
</script>

<script type="text/javascript">
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
				[ {field: "customerName", title: "Customer Name" ,template: '<a href="YAgentConsoleContactSearch?customerId=#=customerId#" target="_self">#=customerName# </a>'},
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
                    	 fName : $("#fname").val(),lName :  $("#lname").val(),eMail : $("#email").val(),phno : $("#MobNo").val(), cname : $("#cname").val()
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
	$("#top_profile").attr('class','');
	$("#top_login").attr('class','');
	$("#top_payment").attr('class','');
	$("#top_donation").attr('class','');
	$("#top_yagentConsole").attr('class','current');	
	
	$("#page_name").html("YMCA AGENT CONSOLE");
	
	var donationPath = $("#top_donation").attr("href");
	///ymca-web/donationHome
	if(donationPath){
		var pathArr = donationPath.split("/");
		donationPath = pathArr[1];		
	}
	var donationLIHtml = '<a href="#" id="top_donation">Donations</a>';
	donationLIHtml += '<ul>';
	donationLIHtml += '<li><a href="/'+ donationPath +'/donationEmployerMatch">Employer Donations</a></li>';
	donationLIHtml += '</ul>';
	$("#top_donation_li").html(donationLIHtml);	
});


function membershipCheck() {
	   
	var kendoWindow = $("<div />").kendoWindow({
		title : "Check",
		resizable : false,
		modal : true,
		width : 400
	});

	kendoWindow.data("kendoWindow").content(
			$("#checkMembership").html()).center().open();

	kendoWindow.find(".delete-confirm,.delete-cancel").click(
			function() {
				if ($(this).hasClass("delete-confirm")) {
					//alert("Deleting record...");
					$(".k-loading-mask").show();
					//delete_record(member_id);
					window.location = "yAgentSearchUser";
				}

				kendoWindow.data("kendoWindow").close();
			}).end();
}
</script>