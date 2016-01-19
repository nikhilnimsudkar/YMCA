<%@ include file="../../layouts/include_taglib.jsp"%>
<div id="programinfoPg">
	<div class="k-loading-mask" style="width:100%;height:100%; position:absolute; top:114px; left:0px;display:none; z-index:9999">
		<span class="k-loading-text">Loading... Please wait</span>
		<div class="k-loading-image">
			<div class="k-loading-color"></div>
		</div>
	</div>
	<div id="tabstrip3" class="k-block" style=" background-color:#FFFFFF; padding:12px;">	
		<form:form commandName="ItemDetailsSession" name="signupFrm" id="signupFrm" action="signupProgram" method="post">
		<span class="head">NEW PROGRAM SIGN UP</span>
		<span id="shoppingcart" style="float:right;"></span>
		<div style="margin-top:10px; margin-bottom:10px;">
			<select name="location" id="location" style="width:400px;">
				
				<c:forEach var="location" items="${locations}" varStatus="count">
					<option value="${location.locationId }">${location.branchName }</option>
				</c:forEach>
			</select>
		</div>
		<span style="font-size: .7em;">We recommend you use our website to explore the many programs the Y of Silicon Valley offers and to determine what your
home branch should be.</span>
		<br><br>
		<table>
			<tr>
				<td valign="top">
					<div>
						<span class="head">MEMBER(S)</span><br>
						
							<c:forEach var="member" items="${AlluserS}" varStatus="count">
								<span><input type="checkbox" name="user_${member.id }" id="user_${member.id }" value="${member.id }" class="usercheck"></span>
								<span class="name">${member.firstName } ${member.lastName }</span>
								<br>
							</c:forEach>
						
					</div>
					
					<br>
					<span class="head">CLASSES</span><br><br>
					<div>
						<select id="class" name="class" onchange="populateProducts()" style="width:130px;">
							<c:forEach var="productcategory" items="${productcategories}" varStatus="count">
								<option value="${productcategory}">${productcategory}</option>
							</c:forEach>
						</select>
					</div><br>
					<div>
						<select id="subclass" name="subclass" style="width:130px;">
							<option value=""></option>
						</select>
					</div><br>
					<span class="k-button" id="SearchProgramBtn">Search</span>
				</td>
				<td valign="top" align="left">
					<span style="font-size: .7em;margin-right: 10px;">
						Select Calendar view: 
						<select name="daypicker" id="daypicker" style="width:100px">
							<option value="DAY">DAY</option>
							<option value="MONTH">MONTH</option>
						</select>
						<input name="datepicker" id="datepicker" value=""/>
						<input name="monthpicker" id="monthpicker"/>
					</span>
					<span class="tortoise">PRINT</span><br>
					<span style="font-size: .7em;margin-right: 40px;">Select </span>
					<span style="font-size: .7em;"><span class="tortoise">All</span>&nbsp;&nbsp;&nbsp;&nbsp;<span class="tortoise">None</span></span>
					<br>
					<br>
					<div style="font-size:0">
						<div id="program_session">
						
						</div>
					</div>
				</td>
			</tr>
			<tr height="20"><td colspan="2" align="right"></td></tr>
			<tr>
				<td colspan="2">
					<div id="statusBlock">
						<span class="k-block k-success-colored" id="tcSuccessSpan"></span>
						<span class="k-block k-error-colored" id="tcErrorSpan"></span>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<span class="head">PROGRAM COST</span>
				</td>
				<td align="right">
					<span id="program_cost" style="margin-right: 237px;"></span>
					<!-- <span class="k-button" id="payBtn">PAY</span> -->
					<span id="nextbtn"></span>
				</td>
			</tr>
		</table>
		
		
		</form:form>
	</div>	 
</div>
<script>
	$(document).ready(function(){
		$("#cart-header").appendTo("#shoppingcart");
		$("#daypicker").kendoDropDownList({
			width:"500px"
		});
		
		$( ".tortoise" ).click(function(){
			//alert($(this).text());
			if($(this).text()=="All") { // check select status
				$('form').find('input[name="selectedItemSession"]').each(function(){
					//alert(this.value);
					$("#uniform-item_"+this.value +" span").attr("class", "checked");
					$("#item_"+this.value).attr("checked", true);
				});
	        }else if($(this).text()=="None"){
	            $('input[name="selectedItemSession"]').each(function() { //loop through each checkbox
	            	$("#uniform-item_"+this.value +" span").attr("class", "");
	            	$("#item_"+this.value).attr("checked", false);                      
	            });         
	        }
		});	
		
		$( "#daypicker" ).change(function(){	
			
			if($( "#daypicker" ).val()=='MONTH'){
				$( "#datepicker" ).hide();
				$('#datepicker').data('kendoDatePicker').enable(false);
				
				$( "#monthpicker" ).show();
				$('#monthpicker').data('kendoDatePicker').enable(true);
				
				$('[class="k-picker-wrap k-state-default"]').show();
				$('[class="k-picker-wrap k-state-default"]').css("width","100px");
				$('[class="k-picker-wrap k-state-disabled"]').hide();
				
			}
			else{
				$( "#datepicker" ).show();
				//$("#datepicker").kendoDatePicker();
				$('#datepicker').data('kendoDatePicker').enable(true);
				
				$( "#monthpicker" ).hide();
				$('#monthpicker').data('kendoDatePicker').enable(false);
				
				$('[class="k-picker-wrap k-state-default"]').show();
				$('[class="k-picker-wrap k-state-default"]').css("width","100px");
				$('[class="k-picker-wrap k-state-disabled"]').hide();
			}
		});
		
		$("#datepicker").kendoDatePicker({
			value: new Date(),
		});
		$("#monthpicker").kendoDatePicker({
            // defines the start view
            start: "year",

            // defines when the calendar should return date
            depth: "year",

            // display month and year in the input
            format: "MMMM yyyy"
        });
		
		// disable and hide month picker by default
		$( "#datepicker" ).show();
		//$("#datepicker").kendoDatePicker();
		$('#datepicker').data('kendoDatePicker').enable(true);
		
		$( "#monthpicker" ).hide();
		$('#monthpicker').data('kendoDatePicker').enable(false);
		
		$('[class="k-picker-wrap k-state-default"]').show();
		$('[class="k-picker-wrap k-state-default"]').css("width","100px");
		$('[class="k-picker-wrap k-state-disabled"]').hide();
		
		
		$("#location").kendoDropDownList();
		$("#class").kendoDropDownList();
		$("#subclass").kendoDropDownList();
		$('#subclass').data('kendoDropDownList').enable(false);
		
		populateProducts();
		
		$( "#SearchProgramBtn" ).click(function(){
			 $("#tcSuccessSpan").css("display", "none");
			 $("#tcErrorSpan").css("display", "none");
			$(".k-loading-mask").show();
			getProgramSession();
		});	
		
		$( "#payBtn" ).click(function(){
			// validation start
			var programData = [];
			$('form').find('input[name="selectedItemSession"]').each(function(){
				if($("#item_"+this.value).is(':checked')){
					programData.push(this.value);
				}
			});
			if(programData==""){
				  $("#tcSuccessSpan").css("display", "none");		
				  $("#tcSuccessSpan" ).html("");	
				  $("#tcErrorSpan").css("display", "block");		
				  $( "#tcErrorSpan" ).html("Please select atleast one signup program");
				  return;
			}
			
			var contactData = [];
			$('form').find('input[class="usercheck"]').each(function(){
				if($("#user_"+this.value).is(':checked')){
					contactData.push(this.value);
				}
			});
			//alert(contactData)
			if(contactData==""){
				  $("#tcSuccessSpan").css("display", "none");		
				  $("#tcSuccessSpan" ).html("");	
				  $("#tcErrorSpan").css("display", "block");		
				  $( "#tcErrorSpan" ).html("Please select atleast one Member");
				  return;
			}
			var totalmembers = contactData.length;
			//alert(contactDatalength);
			// validation end
			
			$(".k-loading-mask").show();
			$("#program").fadeOut(1000);
			$("#purchase").fadeIn("slow", function(){
				$(".k-loading-mask").show();
				$("#pmPrimary").hide();
				$("#autopayBtn").hide();
				//$("#cart_info").html("");
				
				
				var sessionData = [],
				    data = $('form').find('input:not([name="selectedItemSession"])').serialize();
				
				$('form').find('input[name="selectedItemSession"]').each(function(){
					if($("#item_"+this.value).is(':checked')){
						sessionData.push(this.value);
					}
				});
				data += '&selectedItemSession='+sessionData.join(',');
				//console.log(data);
				
				// create cart item rows
				var trrow = "";
				var ordertotal = 0;
				$.ajax({
					  type: "POST",
					  async: false,
					  url: "getcartItems",
					  data: data,
					  success: function( data ) {
					  	  //alert(data);
					  	  if(data.length>0){
						  	  $.each(data, function(i,itemDetailsSession) {
						  		  console.log(itemDetailsSession);
							  		var stTime = new Date(itemDetailsSession.starttime);
							  		var endTime = new Date(itemDetailsSession.endtime);
							  		var stDt = new Date(itemDetailsSession.itemDetails.startdate);
							  		var endDt = new Date(itemDetailsSession.itemDetails.enddate);
						  			trrow = trrow + "<tr>";
						  			trrow = trrow + "<td><span><strong>" + itemDetailsSession.sessionName + "</strong></span></td>";
						  			trrow = trrow + "<td><span>" + itemDetailsSession.days + "</span></td>";
						  			trrow = trrow + "<td><span>" + formatDate(stDt) + " - " + formatDate(endDt) + "</span></td>";
						  			trrow = trrow + "<td><span>" + formatTime(stTime) + " - " + formatTime(endTime) + "</span></td>";
						  			trrow = trrow + "</tr>";
						  			
						  			trrow = trrow + "<tr>";
						  			trrow = trrow + "<td><span>Sub Total:" + itemDetailsSession.itemDetails.price + "</span></td>";
						  			trrow = trrow + "</tr>";
						  			
						  			ordertotal = ordertotal + parseInt(itemDetailsSession.itemDetails.price);
						  	  });
					  	  }
					  },
					  error: function( xhr,status,error ){
						  //alert("1" +status);
						  console.log(xhr);
						  
					  }
				});
				
				var row = $(trrow);
			    row.prependTo("#cart_info table"); 
				//$("#cart_info tr:first").after("<td>Lavy</td>");
				// multiple program total with per person
				ordertotal = ordertotal * parseInt(totalmembers);
				$("#ordertotal").text("$"+ordertotal);
				
				$(".k-loading-mask").hide();
			});
		});
	});
	
	function getProgramSession(){
		var category = $("#class").val();
		var productname = $("#subclass").val();
		var location = $("#location").val();
		var dayview = $("#daypicker").val();
		if(dayview=='MONTH'){
			var dateormonth = $("#monthpicker").val();
		}
		else{
			var dateormonth = $("#datepicker").val();
		}
		
		
		$.ajax({
			  type: "GET",
			  url:"getItemSessionDetails?location="+location+"&productname="+productname+"&category="+category+"&dayview="+dayview+"&dateormonth="+dateormonth,
			  success: function( data ) {
			  	 //console.log(data.length);
			  	 if(data.length>0){
				  	 var price = "";
				  	 var days = "";
				  	 var dspDays = "";
				  	 var item_session = "<table class='program_desc' width='100%'>";
				  	 $.each(data, function(i,itemDetailsSession) {
				  		
				  		if(itemDetailsSession[4]==days){
				  			dspDays = "";
				  		}
				  		else{
				  			days = itemDetailsSession[4];
				  			dspDays = days;
				  		}
				  		 
				  		price = itemDetailsSession[7];
				  		var stTime = new Date(itemDetailsSession[2]);
				  		var endTime = new Date(itemDetailsSession[3]);
				  		
				  		item_session = item_session + "<tr>";
				  			item_session = item_session + "<td><span class='name boldorange' style='font-weight:bold;'>"+dspDays+"</span></td>";
				  			item_session = item_session + "<td><span><input type='hidden' id='itemDetailsSessionId"+itemDetailsSession[9]+"' value='"+itemDetailsSession[9]+"'><input type='checkbox' name='selectedItemSession' id='item_"+itemDetailsSession[9]+"' value="+itemDetailsSession[9]+"></span></td>";
				  			item_session = item_session + "<td><span class='name'>"+formatTime(stTime)+" - "+formatTime(endTime)+"</span></td>";
				  			//item_session = item_session + "<td><span class='name'>7:00 AM - 1:00 PM</span></td>";
				  			item_session = item_session + "<td><span class='name'>"+itemDetailsSession[1]+" - "+itemDetailsSession[0]+"</span></td>";
				  			item_session = item_session + "<td><span class='name'>"+itemDetailsSession[5]+"</span></td>";
				  			item_session = item_session + "<td></td>";
				  		item_session = item_session + "</tr>";
				  		item_session = item_session + "<tr><td></td><td colspan='5'><hr></td></tr>";
						
				  	 });
				  	 item_session = item_session + "</table>";
				  	
				  	 //console.log(item_session);
				  	 $("#program_session").html(item_session);
				  	 $("#program_cost").text("$"+price+" per person");
				  	 $("input[type='checkbox']").uniform();
				  	 
				  	 $("#add-to-cart").appendTo("#nextbtn");
				  	 $("#add-to-cart").show();
			  	 }
			  	 else{
			  		$("#program_session").html("");
				  	$("#program_cost").text("");
				  	 $("#tcSuccessSpan").css("display", "block");		
					 $("#tcSuccessSpan" ).html("No Programs found for searched query");	
					 $("#tcErrorSpan").css("display", "none");		
					 $( "#tcErrorSpan" ).html("");
					 $("#add-to-cart").hide();
			  	 }
			  	$(".k-loading-mask").hide();
			  },
			  error: function( xhr,status,error ){
				  alert("1" +status+"-"+error);
				  console.log(xhr);
				 
			  }
		});
	}
	
	function populateProducts(){
		var category = $("#class").val();
		$.ajax({
			  type: "GET",
			  async: false,
			  url:"getProductsbyCategory?category="+category,
			  dataType: "json",
			  success: function( data ) {
			  	 // alert(data);
			  	 
			  	 //$("#subclass").data("kendoDropDownList").dataSource.data(data);
			  	 $('#subclass').data('kendoDropDownList').enable(true);
			  	 //$("#subclass").data("kendoDropDownList").setDataSource("");
			     $("#subclass").data("kendoDropDownList").dataSource.data(""); // clears dataSource
			  	 $("#subclass").data("kendoDropDownList").text(""); // clears visible text
			  	 $("#subclass").data("kendoDropDownList").value(""); // clears invisible value

			  	 $.each(data, function(i,product) {
			  		 //console.log(product);
			  		$("#subclass").data("kendoDropDownList").dataSource.add({ text: product, value: product });
				});
			  	 
			  },
			  error: function( xhr,status,error ){
				  //alert("1" +status);
				  console.log(xhr);
				 
			  }
		});
		
		getProgramSession();
	}
	
	function formatTime(date) {
		  var hours = date.getHours();
		  var minutes = date.getMinutes();
		  var ampm = hours >= 12 ? 'pm' : 'am';
		  hours = hours % 12;
		  hours = hours ? hours : 12; // the hour '0' should be '12'
		  minutes = minutes < 10 ? '0'+minutes : minutes;
		  var strTime = hours + ':' + minutes + ' ' + ampm;
		  return strTime;
	}
	
	function formatDate(inputFormat) {
		  function pad(s) { return (s < 10) ? '0' + s : s; }
		  var d = new Date(inputFormat);
		  return [pad(d.getMonth()+1), pad(d.getDate())].join('/');
		}
</script>
