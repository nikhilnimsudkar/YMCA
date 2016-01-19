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
<div ><div class="border-bottom-h3">
				            	<span id="locSelMsg" style="margin-right: 140px; color: red;"> Please select YMCA location to view already checked-in kids.</span>
				           
							<%-- <select name="location" id="location" style="width:250px; margin-right: 150px;" ><br />
          <option value="0">--Select Location--</option>
          <c:forEach var="location" items="${locations}" varStatus="count">
           <c:if test="${ location.recordName != 'All Branches' && location.recordName != 'Bay Area' && location.branch == 'true' }">
            <option value="${location.id }">${location.recordName }</option>
           </c:if>
          </c:forEach>
       </select> --%>
       
       <select name="location" id="location" style="width:250px;" ><br />
										<option value="0">--Select Location--</option>
										<c:forEach var="location" items="${locations}" varStatus="count">
											<c:if test="${ location.recordName != 'All Branches' && location.recordName != 'Bay Area' && location.branch == 'true' }">
												<c:if test="${ location.recordName != 'El Camino YMCA'}">
													<option value="${location.id }">${location.recordName }</option>
												</c:if>
												<c:if test="${ location.recordName == 'El Camino YMCA'}">
													<option value="${location.id }" selected="selected">${location.recordName }</option>
												</c:if>
												
											</c:if>
										</c:forEach>
							</select> 
       
        </div></div>

<!-- <span id="viewkidsGrid" class="k-button" style=" margin-left:295px;"  >View Kids </span> -->
<div id="kgrid"  ></div>


<script type="text/javascript">

 
function initGrid(){
	 source = getRemoteData();
	 $("#kgrid").kendoGrid ( {
		 	dataSource: source,    	
		 	autoBind: false,
			
	        filterable: false,
	        sortable: true,
	        pageable: true,
	        resizable: true,
	        columns: 
				[ 
				  {field: "ProfilePic", title: " ",template: '<img src="#=ProfilePic #" alt="image" />'},
				 {field: "KidName", title: "Kid Name"},
				 {field: "CheckedInTime", title: "Checked-in Time"} ,
 				 {field: "CheckedInPerson", title: "Checked-In Person "},
				 {field: "CheckInPersonId", title: "Checked-In Person Email "},	
 				 {field: "CheckInPersonPhn", title: "Checked-In Person Phone "},	
 				 {field: "CheckedInLoc", title: "Checked-In Location "}	
				 				 
 				]
	 });
}
var source ; 	
function getRemoteData() { 
	var location= $('#location').val();
	
	source = new kendo.data.DataSource({
        autoSync: false,
        transport: {
            read: {
                type: "GET",
                url: "kidsPaginationajax?location="+location ,
                
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


function setfailurecolor(){
	var gridData = $("#kgrid").data("kendoGrid");
	var data = gridData.dataSource.data();
	
	gridData.tbody.find('>tr').each(function () {
        var dataItem = gridData.dataItem(this);
        if (dataItem.CheckedInhrs != null && dataItem.CheckedInhrs>=2) {
        	//alert("clr chnge");
        	//var firstItem = $('#GridName').data().kendoGrid.dataSource.data()[0];
			//dataItem.set('CheckedInTime',new Date());
			//alert(new Date())
			
            $(this).css('color', 'red');
        }
    });
	setTimeout(function(){setfailurecolor();}, 1000);
}

$('#viewkidsGrid').click(function(e){
		initGrid();
		//onclick="searchUser()"
		/* var stDate=$("#start").val();
 	var eDate=$("#end").val();
 	var pType= $('#type').data('kendoDropDownList').value();
 	var pStatus= $('#status').data('kendoDropDownList').value();
 	var username= $('#contact').val(); */
 	//alert("serch user clicked");
 	searchUser();
	setTimeout(function(){setfailurecolor(); $("#kgrid").show(); }, 500);
}); 



$(document).ready(function() { 
	//initGrid();
	
	$("#location").kendoDropDownList(); 
	var locationDropdownlist = $("#location").data("kendoDropDownList");
	initGrid();
	searchUser();
	setfailurecolor();
	//locationDropdownlist.select(0);
	//locationDropdownlist.select(300000002167025);
	$("#location").on('change',function(e){  
	    if(locationDropdownlist.text() != '--Select Location--'){
	  //  alert("{location change}");
	  //	$("#locSelMsg").hide();
	   // $("#kgrid").show();
		//$("#serchCustomerTable").show();
		
	    	initGrid();
			//onclick="searchUser()"
			/* var stDate=$("#start").val();
	 	var eDate=$("#end").val();
	 	var pType= $('#type').data('kendoDropDownList').value();
	 	var pStatus= $('#status').data('kendoDropDownList').value();
	 	var username= $('#contact').val(); */
	 	//alert("serch user clicked");
	 	searchUser();
		setTimeout(function(){setfailurecolor(); $("#kgrid").show(); }, 500);
	    }else if(locationDropdownlist.text() == '--Select Location--'){
	       // alert("{location change to first pos}");
	      //  $("#kgrid").hide();
	    	//$("#serchCustomerTable").hide();
	    //	$("#locSelMsg").show();
	        }

	   // $("#memId").focus();
	});
});
</script>