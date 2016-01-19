<%@ page language="java" contentType="text/html; charset=ISO-8859-1"   pageEncoding="ISO-8859-1"%>
<%@ include file="../../layouts/include_taglib.jsp"%>

<span class="head">Please select transportation service for camp if needed</span>
<div id="itemTransportDiv" style="margin-top: 15px;">
</div>
<div style="float:right;margin-top: 95px;clear: both;">
	<button class="k-button" style="font-size: small; width: 150px;" onclick="afterTransport();">Next</button>
</div>
<script type="text/javascript">
	function initTransportGrid(){
		 $("#itemTransportDiv").kendoGrid ( {
			 	dataSource: getTransportDataSource(),    	
		        filterable: false,
		        autoSync: true,
		        sortable: true,
		        pageable: false,
		        scrollable:true,
		        resizable: true,
		        editable: true,
		        edit: function (e) {
		            var fieldName = e.container.find("input").attr("name");
		            if (fieldName == 'flightName' || fieldName == 'flightTime') {
			            var grid = e.sender;
			            var row = e.container.closest("tr");
			            var currentDataItem = grid.dataItem(row);
						if (currentDataItem.serviceType == 'Bus Stop') {
							this.closeCell(); // prevent editing	
						}	
		            }
		        },
		        groupable: false,
		        columns: [ 
						  {field: "groupField", title: "Sign Up",hidden:true},
						  {field: "agreed", title: "&nbsp;" ,width: 30,template: '<input type="checkbox" onclick="onCheckUnCheck(this);" class="chkbx" name="selectedActivity" data-bind="checked:agreed" />'},
						  {field: "cId", title: "Person",hidden:true},
		                  {field: "id", title: "Id",hidden:true},
						  {field: "serviceType", title: "Transport Type"},
						  {field: "parentItemId", title: "Camp",hidden:true},
						  {field: "location", title: "Location"},
						  {field: "direction", title: "Direction"},
						  {field: "startDate", title: "Time",template: "#= kendo.toString(new Date(startDate), 'MM/dd/yyyy') # <br>" + "#= kendo.toString(new Date(startTime), 'hh:mm tt') #" },
						  {field: "memberprice", title: "Non Member / <br>Member Price",template: '<label>#= nonmemberprice #'+ '/' + ' #= memberPrice #</label>'},
						  {field: "flightName", title: "Flight Name/#"},
						  {field: "flightTime", title: "Flight Time"}
						],
	 			dataBound: function(e) {
	 				var selectedActivity = $("#selectedActivity").val();
	 				if ($("#selectedActivity").val() == "true") {
	 					$("input[type='checkbox']").prop( "checked", true );	 					
	 				}
	 				$("input[type='checkbox']").uniform();
	 			 }
		 });
		 
		 $("#itemTransportDiv").kendoTooltip({
	          filter: "input[type='text']",
	          position: "right", 
	          content: function(e){
				var content =  "Format should be 9:00 AM";
	            return content;
	          }
	        }).data("kendoTooltip");
	}
	
	$(document).ready(function(){
		//loadActivityTransportData();
	});	
	
	
	function getTransportDataSource(){
		transportDataSource = new kendo.data.DataSource({
	        autoSync: false,
            data: respData,
            schema: {
            model: {
                id: "id",
                fields: {
                	groupField: {  editable: false},
                	agreed: { editable: true,type:"boolean"},
                	id: { editable: false},
                	cId: {  editable: false},
                	serviceType: {  editable: false},
                	location: {  editable: false},
                	direction: {  editable: false},
                	startDate: {  editable: false},
                	startTime: {  editable: false},
                	memberprice: {  editable: false},
                	nonmemberprice: { editable: false},
                    flightName: { editable: true},
                    flightTime: {  editable: true}
                }
            }
        },
	    filter : [{
	        field: "type", operator: "eq", value: "Transportation"
	    }],
	    groupable: false,
        group: [{
            field: "groupField",
            dir: "asc"}],
            sort: {
            	field: "serviceType",
        		dir: "asc"
            }
   	 	});
    	return transportDataSource;
	}
	
	function loadTransportGrid() {
		var itemActivity = []  ;
		var actData = activityDataSource.data();
		if(checkIfUniquePriorities(actData)){
			for (var i = 0; i < actData.length; i++) {
				var actParam = actData[i].cId + "_" + actData[i].parentId +  "_" + actData[i].associatedItemId + "_"  + actData[i].priority;
				itemActivity.push(actParam);
			}
			$.sessionStorage.setItem("selectedItemActivity",itemActivity);
			$("#contactActivityDiv").hide();
			initTransportGrid();
			transportDataSource.read();
			var transportData = transportDataSource.data();
			if (transportData.length > 0) {
				$("#tcSuccessSpan").css("display", "none");
				$("#tcSuccessSpan" ).html("");
				$("#tcErrorSpan").css("display", "none");
				$("#tcErrorSpan" ).html("");
				$("#contactTransportDiv").slideDown();	
			} else {
				gotocheckout();
			}
		}else{
			$("#tcSuccessSpan").css("display", "none");
			$("#tcSuccessSpan" ).html("");
			$("#tcErrorSpan").css("display", "block");
			$("#tcErrorSpan" ).html("Please enter unique priorities");
		}
	}
	
	function checkIfUniquePriorities(actData){
		var result = true;
		//console.log(" actData.length "+actData.length);
		for (var i = 0; i < actData.length; i++) {
			if(actData[i].type == 'Activity'){
				for (var j = 0; j < actData.length; j++) {
					// console.log("  actData i >> "+actData[i]+",   actData j >> "+actData[j]);
					if(actData[j].type == 'Activity'){
						if(i != j && actData[i].contactId == actData[j].contactId && actData[i].parentItemId == actData[j].parentItemId 
								&& actData[i].priority != null && actData[j].priority != null && actData[i].priority == actData[j].priority){
							result = false;
						}
					}
				}
			}
		}
		return result;
	}
	
	function afterTransport(){
		var itemTransport  = [] ;
		var transportData = transportDataSource.data();
		var transportItems = {} ;
		for (var i = 0; i < transportData.length; i++) {
			if (transportData[i].agreed) {
				for (var j = 0; j < transportData.length; j++) {
					if (transportData[j].agreed) {
						if(i != j && transportData[i].contactId == transportData[j].contactId && transportData[i].direction == transportData[j].direction 
								&& transportData[i].serviceType != transportData[j].serviceType){
							// console.log(" Cannot select both "+transportData[i].serviceType+" and "+transportData[j].serviceType+" for "+transportData[j].direction);
							$("#tcSuccessSpan").css("display", "none");
							$("#tcSuccessSpan" ).html("");
							$("#tcErrorSpan").css("display", "block");
							$("#tcErrorSpan" ).html("Cannot select both "+transportData[i].serviceType+" and "+transportData[j].serviceType+" for "+transportData[j].direction);
							return;
						}
					}
				}
			}
		}
		for (var i = 0; i < transportData.length; i++) {
			if (transportData[i].agreed) {
				var actParam = transportData[i].cId + "_" + transportData[i].parentId +  "_" + transportData[i].id + "_"  + transportData[i].agreed;
				itemTransport.push(transportData[i].associatedItemId);
 			}
		}
		$.ajax({ url: "getallProducts?ids="+itemTransport.join(","), 
			  type: "GET",
			  async: false,
			  dataType: "json",
			  success: function(data) {
				  	$.each(data, function(i,member) {
						transportItems[member.itemDetailsId] = member;	
				 	});
	            },
		  	  error: function( xhr,status,error ){
				  console.log(xhr);
			  }
	    });
		for (var i = 0; i < transportData.length; i++) {
			if (transportData[i].agreed) {
				var item = transportItems[transportData[i].associatedItemId];
				var parentSignUpItem = transportData[i].contactId+ "#" + transportData[i].parentItemId ;
				var contact = getContactFromDatasource(transportData[i].contactId); 
				try {
					cart.add(item,contact,parentSignUpItem);
				} catch (e) {
					console.log(e);
				}
			}
		}
		gotocheckout();	
		var loadActivityTransportWizrad= "${loadActivityTransportWizrad}";
		if (loadActivityTransportWizrad == "true") {
			$("#cart-info").trigger("click");	
		}
	}
	
	function onCheckUnCheck(e){
	    var checked = $(e).is(':checked');
	    var grid = $('#itemTransportDiv').data().kendoGrid;
	    var dataItem = grid.dataItem($(e).closest('tr'));
	    dataItem.agreed = checked;
	    dataItem.dirty = true;
	}
	
	function getContactFromDatasource(contactId){
		var contact ;
		var datasourcedata = contacts.data()
		 for (var i = 0; i < datasourcedata.length; i++) {
			 if (datasourcedata[i].contactId == contactId) {
				 contact = datasourcedata[i];
				 return contact;
			 }
		 }
		console.log("Error no contact found in the datasource");		
	}
	
</script>