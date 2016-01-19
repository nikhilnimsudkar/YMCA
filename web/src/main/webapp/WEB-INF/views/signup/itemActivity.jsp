<%@ page language="java" contentType="text/html; charset=ISO-8859-1"   pageEncoding="ISO-8859-1"%>
<%@ include file="../../layouts/include_taglib.jsp"%>

<span class="head">Please select preferred priority for camp activities or click on Next button for the default priority</span>
<div id="itemActivity" style="margin-top: 15px;">
	<%--  <table id="activityTable" ></table> --%>
</div>
<div style="float:right;margin-top: 95px;">
	<button class="k-button" style="font-size: small; width: 150px;" onclick="loadTransportGrid();">Next</button>
</div>
<script type="text/javascript">
	var respData  ; 
	var activityDataSource  ; 
	var transportDataSource   ; 
	
	function initActivityGrid(){
		 $("#itemActivity").kendoGrid ( {
			 	dataSource: getActivityDataSource(),    	
		        filterable: false,
		        sortable: true,
		        pageable: false,
		        scrollable:true,
		        resizable: true,
		        editable: true,
		        groupable: false,
		        columns: [ 
						  {field: "groupField", title: "Sign Up",hidden:true},
						  {field: "cId", title: "Person",hidden:true},
		                  {field: "id", title: "Id",hidden:true},
						  {field: "type", title: "Type",hidden:true},
						  {field: "parentItemId", title: "Camp",hidden:true},
						  {field: "name", title: "Activity Name"},
						  {field: "priority", title: "Priority"}
						]
		 });
	}
	
	function loadActivityTransportData(item_Detail_Ids,cIds,hideActivityGrid) {
		$.ajax({
			url: "./sc/agent/getItemActivityAndTransportService?itemDetailId="+item_Detail_Ids + "&cIds="+cIds,
			cache: false
			})
			.done(function(data) {
				respData = data ;
				initActivityGrid();
				activityDataSource.read();
				var actData = activityDataSource.data();
				if (actData.length > 0 && !hideActivityGrid) {
					$("#contactActivityDiv").slideDown();	
				} else {
					loadTransportGrid();
				}
			});
	}
	
	$(document).ready(function(){
		var loadActivityTransportWizrad= "${loadActivityTransportWizrad}";
		if (loadActivityTransportWizrad == "true") {
			$(".k-loading-mask").show();
			var item_Detail_Ids = "${signUpItemId}";
			var cIds = "${signUpItemIdContactId}";
			
			var cartItems = JSON.parse($.sessionStorage.getItem('cart'));
			$("#program").hide();
			$("#statusBlock").hide();
			$("#tcSuccessSpan").css("display", "none");
			hideAllWizrad();
			loadContacts();
			//$.sessionStorage.setItem('itemDetailsId',item_Detail_Ids);
			loadActivityTransportData(item_Detail_Ids,cIds,true);
			$(".k-loading-mask").hide();
		}
	});
	
	
	function getActivityDataSource(){
		activityDataSource  = new kendo.data.DataSource({
            data: respData,
            schema: {
          	  model: {
                id: "id",
                fields: {
                		id: { editable: false},
                		groupField: {  editable: false},
                		cId: {  editable: false},
                		type: {  editable: false},
	                	name: {  editable: false},
	                	priority: {  editable: true,type: "number"}
            	    }
        	    }
    	    },
		    filter : [{
		        field: "type", operator: "eq", value: "Activity"
		    }],
		    groupable: false,
	        group: [{
	            field: "groupField",
	            dir: "asc"}],
	           sort: {
	                field: "priority",
	                dir: "asc"
	            }
   	 	});
    	return activityDataSource;
	}
</script>