<%@ include file="../../layouts/include_taglib.jsp"%>
<script id="date-ErrorBox" type="text/x-kendo-template">     
	<p class="error-message-p">Date entered should not be less than today's Date.</p>
	
    <div class="confirmbutton" align="center"><button class="confirm-Date k-button" style=" width: 35%;">Ok</button>  
</script>
<div id="programinfoPg">
	<div class="k-loading-mask" style="width:100%;height:100%; position:absolute; top:114px; left:0px;display:none; z-index:9999">
		<span class="k-loading-text">Loading... Please wait</span>
		<div class="k-loading-image">
			<div class="k-loading-color"></div>
		</div>
	</div>
	<div id="tabstrip3">	
		<form:form commandName="ItemDetailsSession" name="signupFrm" id="signupFrm" action="signupProgram" method="post">
			<div>
				<span class="head">NEW ${pageType} SIGN UP</span>
				<input type="hidden" id="pageType" value="${pageType}" />	
				<input type="hidden" id="loadSearch" value="${prvParam.loadSearch}" />
				<input type="hidden" id="selectSearchResult" value="${prvParam.selectSearchResult}" />
				<input type="hidden" id="itemDetailIds" value="${prvParam.itemDetailIds}" />
				<input type="hidden" id="optyId" value="${prvParam.optyId}" />
			</div>
			<div style="margin-top: 5px; margin-bottom: 5px;">
				<span style="font-size: .7em;">We recommend you use our website to explore the many programs the Y of Silicon Valley offers and to determine what your
		home branch should be.</span>
			</div>
			
			<div class="searchdiv">
				<span class="head searchFields">Category</span>
				<span class="searchFields">
					<select id="class" name="class" style="width:250px;">
						<c:if test="${pageType ne 'CAMP'}">
							<option value="All">All</option>
						</c:if>		
						<c:forEach var="productcategory" items="${productcategories}" varStatus="count">
							<option value="${productcategory}">${productcategory}</option>
						</c:forEach>
					</select>
				</span>
			</div>
			<c:choose>
				<c:when test="${pageType ne 'EVENT'}">	
					<div class="searchdiv">
						<span class="head searchFields">Location</span>
						<span class="searchFields">
							<select name="location" id="location" style="width:250px;">
								<option value="1">All Location</option>
								<c:forEach var="location" items="${locations}" varStatus="count">
									<c:choose>
										<c:when test="${ location.recordName == 'All Branches' }">
										</c:when>
										<c:otherwise>
									      	<option value="${location.id }">${location.recordName }</option>
									    </c:otherwise>
								    </c:choose>
								</c:forEach>
							</select>
						</span>
					</div>
				</c:when>
				<c:otherwise>
						<input type="hidden" value="1"  name="location" />
				</c:otherwise>
			</c:choose>
			<div class="searchdiv">
				<span class="head searchFields">Age Range</span>
				<div id="agerangeslider" class="searchFields" style=" margin: 5px 0 0 7px; width: 170px;">
					<span><input name="age_min" id="age_min" value=""></span>
					<span><input name="age_max" id="age_max" value=""></span>
				</div>
			</div>
			<c:choose>
				<c:when test="${pageType eq 'CAMP1'}">
					<div class="searchdiv">
        				<span class="head searchFields">Type:</span>
        				<span class="searchFields">
						<select name="type" id="type" style="width:250px;">
							<option value="All">All</option>
								<c:forEach var="category" items="${productcategories}" varStatus="count">
									<option value="${category}">${category}</option>
								</c:forEach>
						</select>
						</span>
					</div>
				</c:when>
			</c:choose>			
			<div class="searchdiv">
				<span class="head searchFields">Keyword<br>(Program Name/ Description)</span>
				<span class="searchFields">
					<input name="subclass" id="subclass" class="k-textbox" style="width: 250px;" value="">
				</span>
			</div>
			<div class="searchdiv">
				<span class="head searchFields">Program Start Date Range</span>
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
	</div>	 
</div>
<script>
	$(document).ready(function(){ 
		
		if($.sessionStorage.getItem("search_program_filter") != null){
			var filterJson = JSON.parse($.sessionStorage.getItem('search_program_filter'));
			$("#class").val(filterJson._category);
			$("#location").val(filterJson._location);
		}
		
		$("#cart-header").appendTo("#shoppingcart");
		
		$("#agerangeslider").kendoRangeSlider({
            change: rangeSliderOnChange,
            slide: rangeSliderOnSlide,
            min: 1,
            max: 100,
            smallStep: 1,
            largeStep: 2,
            tickPlacement: "both"
        });
		
		$( ".tortoise" ).click(function(){
			//alert($(this).text());
			if($(this).text()=="All") { // check select status
				$('form').find('input[name="selectedItemSession"]').each(function(){
					//alert(this.value);
					//$("#uniform-item_"+this.value +" span").attr("class", "checked");
					//$("#item_"+this.value).attr("checked", true);
					
					$("#uniform-item_"+this.value +" span").attr("class", "checked");
					(this.value).attr("checked", true);

				});
	        }else if($(this).text()=="None"){
	            $('input[name="selectedItemSession"]').each(function() { //loop through each checkbox
	            	//$("#uniform-item_"+this.value +" span").attr("class", "");
	            	//$("#item_"+this.value).attr("checked", false);
	            	
	            	$("#uniform-item_"+this.value +" span").attr("class", "");
	            	$(this).attr("checked", false);                      

	            });         
	        }
		});	
		
		if(isLoggedInUserAgent()){
			$("#datepicker").kendoDatePicker({
				value: new Date(new Date().getTime() + 24 * 7 * 60 * 60 * 1000),
				format: "MM/dd/yyyy"				
			});
		}else{
			/* $("#datepicker").kendoDatePicker({
				value: new Date(new Date().getTime() + 24 * 7 * 60 * 60 * 1000),
				format: "MM/dd/yyyy",
				min: new Date()
			}); */
			
			var datepicker = $("#datepicker").kendoDatePicker({
				format: "MM/dd/yyyy",
				value: new Date(new Date().getTime() + 24 * 7 * 60 * 60 * 1000),
				change: function (e) {					
					var prev = $(this).data("previous");
					var todayDate = kendo.toString(kendo.parseDate(new Date()), 'MM/dd/yyyy');	
					var prevDate = kendo.toString(kendo.parseDate(prev), 'MM/dd/yyyy');
					var enteredDate = new Date(e.sender.value());				
					//var todayDateObject = new Date();	
					var todayWithZeroTime = new Date();
					todayWithZeroTime.setHours(0,0,0,0);
					if(enteredDate <= todayWithZeroTime){
						showDateError();	
						var thisDatepicker = $('#'+e.sender.element[0].id).data("kendoDatePicker");
						if(prevDate){
							thisDatepicker.value(prevDate);
						}else{
							thisDatepicker.value(todayDate);
						}					
					} else{
						 $(this).data("previous", this.value());
					}	
				},
				min: new Date()
			}).data("kendoDatePicker");
			$(datepicker).data("previous", "");		
		}
		
		$("#datepickerend").kendoDatePicker({
			value: new Date(new Date().getTime() + 24 * 7 * 60 * 60 * 1000),
			format: "MM/dd/yyyy",
			min: new Date()
		});
		
		$("#location").kendoDropDownList();
		$("#class").kendoDropDownList();
		$("#type").kendoDropDownList();
		
		$( "#SearchProgramBtn" ).click(function(){
			 $("#tcSuccessSpan").css("display", "none");
			 $("#tcErrorSpan").css("display", "none");
			 $(".k-loading-mask").show();
			 $("#program").fadeIn(100);
			 $("#dspErr").hide();
			 hideAllWizrad();
			 //$("#details-checkout").fadeOut("fast");
			 commonSearch();
			 
		});
		
		if($.sessionStorage.getItem("search_program_filter") != null){
			var filterJson = JSON.parse($.sessionStorage.getItem('search_program_filter'));
			$("#subclass").val(filterJson._productname);
			$("#age_min").val(filterJson._age_min);
			$("#age_max").val(filterJson._age_max);
			$("#datepicker").val(filterJson._datestart);
			$("#datepickerend").val(filterJson._dateend);
		}
		if ($("#loadSearch").val()) {
			commonSearch();
		}
	});
	
	function showDateError(){
		var kendoWindow = $("<div />").kendoWindow({
			title: "Error",
			resizable: false,
			modal: true,
			width:400
		});

		kendoWindow.data("kendoWindow")
		 .content($("#date-ErrorBox").html())
		 .center().open();

		kendoWindow
		.find(".confirm-Date")
		.click(function() {
			if ($(this).hasClass("confirm-Date")) {         		
				kendoWindow.data("kendoWindow").close();
			}
		})
		.end();		
	}
</script>