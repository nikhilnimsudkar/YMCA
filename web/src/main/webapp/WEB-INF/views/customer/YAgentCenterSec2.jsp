<%@ include file="../../layouts/include_taglib.jsp"%>




<div id="tabstrip3" class="k-block" style="background-color: rgb(255, 255, 255); margin-left: -56px; width: 533px;">
 &nbsp;
		<table cellpadding="0" cellspacing="0" width="99%"  rules="none" style="padding-left:4px;padding-top:4px;">
		<tr ><span class="head" width="100%" style="margin-left:4px;SERACH">SERACH CUSTOMER </span></tr>
		 <tr>&nbsp;</tr>
<tr>
  <td style="padding-bottom:3px;padding-left:4px;"><input type="text" placeholder="Enter First Name" id="fname" name="fname"></td>
  <td style="padding-bottom:3px;"><input type="text" placeholder="Enter Last Name" id="lname" name="lname"></td>
  <!-- <td style="padding-bottom:3px;"><input type="text" placeholder="Enter Full Name" id="filname" name="fulname"></td> -->
 <td style="padding-bottom:3px;padding-left:4px;"><input type="text" placeholder="Enter Email" id="email" name="email"></td>
 </tr> 
 <!-- <tr>
  <td style="padding-bottom:3px;padding-left:4px;"><input type="text" placeholder="Enter Email" id="email" name="email"></td>
  <td style="padding-bottom:3px;"><input type="text" placeholder="Enter Phone No" id="phno" name="phno"></td>
  <td style="padding-bottom:3px;"><input type="text"  id="dob" name="dob"></td>
 
 </tr>  -->
 <!-- <tr>
  <td style="padding-bottom:3px;padding-left:4px;"><input type="radio" id="gender" name="male" value="">MALE</td>
  <td style="padding-bottom:3px;"><input type="radio" id="gender" name="female" value="">FEMALE</td>
  <td style="padding-bottom:3px;"><input type="text"  id="MembershipType" name="MembershipType"></td>
 
 </tr>  -->
 <tr>
  <td style="padding-bottom:3px;padding-left:4px;"></td>
  <td style="padding-bottom:3px;" align="center"><span id="serchUser" class="k-button" onclick="searchUser()" style="width: 70px; text-shadow: none;">SEARCH</span></td>
  <td style="padding-bottom:3px;"></td>
 
 </tr> 

</table>
<div id="kgrid"></div>
</div>
<!--  &nbsp;
<div id="tabstrip3" class="k-block" style="background-color: rgb(255, 255, 255); margin-left: -56px; width: 533px;">
<table cellpadding="0" cellspacing="0" width="99%"  rules="none" style="padding-left:4px;padding-top:4px;">
		<tr><span class="head" width="100%" style="margin-left:4px;">SEARCH PROGRAM/EVENTS/CAMP/MEMBERSHIP/FACILITY</span></tr>
		
<tr>
  <td style="padding-bottom:3px;padding-left:4px;">SELECT</td>
  <td style="padding-bottom:3px;"><input type="text" id="prgList" name="prgList"> </td>
  <td style="padding-bottom:3px;"><td style="padding-bottom:3px;" align="center"><span id="serchProg" class="k-button" onclick="searchPrograms()" style="width: 70px; text-shadow: none;">SEARCH</span></td>
 
 </tr> 
 </table>
 <div id="serachProgramGrid"></div>
 </div>
  -->
 &nbsp;
<!--  <div id="tabstrip3" class="k-block" style="background-color: rgb(255, 255, 255); margin-left: -56px; width: 533px;">
<span class="head" width="100%" style="margin-left:4px;">MEMBERSHP CHECKIN</span><br>
Click here to check your Membership: <span id="checkMemship" class="k-button" onclick="#" style="width: 100px; text-shadow: none;">CHECK-IN</span>
</div> -->
<script>
/* $(document).ready(function() {	 */
	function initGrid(){
    	//alert("in initGrid");
    	
		 $("#kgrid").kendoGrid ( {
			 dataSource: GetRemoteData($("#fname").val(),$("#lname").val(),$("#email").val()),    	
				 height: 300,
		        filterable: false,
		        sortable: true,
		        pageable: true,
		       resizable: true,
		       columns: 
					[ {field: "customerId", title: "CustomerId " ,template: '<a href="YAgentConsoleContactSearch?customerId=211" target="_self">#=customerId# </a>'},
					  {field: "PersonFirstName", title: "First Name"},
					 {field: "PersonLastName", title: "Last  Name"},
  				 {field: "PrimaryEmailAddress", title: "Email id "},
					 {field: "FormattedPhoneNumber", title: "Phone No "},					 
					 {field: "DateOfBirth", title: "DOB"} 				 
  				 
  				 ]
  				
		
		 });
		 
	}
		 
		 function GetRemoteData(fName,lName,eMail) { 
			 //alert("in get DAata");
				var  source = new kendo.data.DataSource({
			        autoSync: true,
			        transport: {
			            read: {
			                type: "GET",
			                url: "getUsersBySearchCriteria",
			                data: {"fName" : $("#fname").val(),"lName" : $("#lname").val(),"eMail" : $("#email").val()},
			                dataType: "json",                
			                cache: false,
			            }    	            
			        },
			        pageSize: 5
			    });
			    source.fetch(function () {
			        var data = this.data();
			        
			        
			        
			        
			    });
			    return source;
	     	}
		 
	 
	//$("#kgrid").hide();
	/* $("#serachProgramGrid").hide();
		 $("#MembershipType").kendoDropDownList({
        	dataTextField: "text",
            dataValueField: "value",
            dataSource:
            	[
				 { text: "Program", value:"Program" },
                 { text: "Event", value:"Event" },
                 { text: "Camp", value:"Camp" },
                 { text: "Facility", value:"Facility" },
                 { text: "Membership", value:"Membership" }
             ]
        }); */
		 
		/*  $("#prgList").kendoDropDownList({
	        	dataTextField: "text",
	            dataValueField: "value",
	            dataSource:
	            	[
					 { text: "Program", value:"Program" },
	                 { text: "Event", value:"Event" },
	                 { text: "Camp", value:"Camp" },
	                 { text: "Child Care", value:"Child Care" },
	                 { text: "Facility", value:"Facility Booking" },
	                 { text: "Membership", value:"Membership" }
	             ]
	        }); */
	 
		 /* $("#serachProgramGrid").kendoGrid({
			 height: 300,
		        filterable: false,
		        sortable: true,
		        pageable: true,
		       resizable: true,
		       columns: 
					[{field: "Type", title: "Type",width:"90px"},
					 {field: "satrtDate", title: "Start Date",width:"85px"},
   				 {field: "Duration", title: "Duration",width:"80px"},
   				 {field: "Location", title: "Location ",width:"75px"},
   				
   				{field: "Fees", title: "Fees",width:"75px"}
   				 
   				 ]
   				
		
		 });
		 var today = kendo.date.today(); */
		 /*  $("#dob").kendoDatePicker({
	            format: "yyyy-MM-dd",
	            value: today,                
	            parseFormats: ["MM/dd/yyyy", "dd/MM/yyyy"]
	        }); */
		 /*  $("#dob").kendoDatePicker({
	        	value: new Date(new Date().getTime() - 24 * 30 * 60 * 60 * 1000),
	        	format: "yyyy-MM-dd",               
	            parseFormats: ["MM/dd/yyyy", "dd/MM/yyyy"]
	        }); */
	        


/* function searchPrograms(){
	
	$("#serachProgramGrid").show();
}
 */

	/*  }); */
function searchUser(){
	 initGrid();
	//$("#kgrid").show();
	
	var fName=$("#fname").val();
	var lName=$("#lname").val();
	var eMail= $("#email").val();
	//alert(fName,lName,eMail);
}
	
	</script>
