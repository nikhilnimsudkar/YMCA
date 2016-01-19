<%@ include file="../../layouts/include_taglib.jsp"%>
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
				<span class="head">NEW PROGRAM SIGN UP</span>
				
			</div>
			<div style="margin-top: 5px; margin-bottom: 5px;">
				<span style="font-size: .7em;">We recommend you use our website to explore the many programs the Y of Silicon Valley offers and to determine what your
		home branch should be.</span>
			</div>
			
			<div class="searchdiv">
				<span class="head searchFields">Category</span>
				<span class="searchFields">
					<select id="class" name="class" onchange="populateProducts()" style="width:250px;">
						<c:forEach var="productcategory" items="${productcategories}" varStatus="count">
							<option value="${productcategory}">${productcategory}</option>
						</c:forEach>
					</select>
				</span>
			</div>	
			<div class="searchdiv">
				<span class="head searchFields">Location</span>
				<span class="searchFields">
					<select name="location" id="location" style="width:250px;">
						<c:forEach var="location" items="${locations}" varStatus="count">
							<c:choose>
								<c:when test="${ location.recordName == 'All Branches' }">
									<option value="${location.id }" selected="selected">${location.recordName }</option>
								</c:when>
								<c:otherwise>
							    </c:otherwise>
							</c:choose>
						</c:forEach>
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
			<div class="searchdiv">
				<span class="head searchFields">Age Range</span>
				<div id="agerangeslider" class="searchFields" style=" margin: 5px 0 0 7px; width: 170px;">
					<span><input name="age_min" id="age_min" value=""></span>
					<span><input name="age_max" id="age_max" value=""></span>
				</div>
			</div>
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
		
		$("#datepicker").kendoDatePicker({
			value: new Date(),
			format: "MM/dd/yyyy",
			min: new Date()
		});
		$("#datepickerend").kendoDatePicker({
			value: new Date(new Date().getTime() + 24 * 7 * 60 * 60 * 1000),
			format: "MM/dd/yyyy",
			min: new Date()
		});
		
		$("#location").kendoDropDownList();
		$("#class").kendoDropDownList();
		
		//populateProducts();
		
		$( "#SearchProgramBtn" ).click(function(){
			 $("#tcSuccessSpan").css("display", "none");
			 $("#tcErrorSpan").css("display", "none");
			 $(".k-loading-mask").show();
			 $("#program").fadeIn(100);
			 //$("#details-checkout").fadeOut("fast");
			 getProgramSession();
		});
		
		if($.sessionStorage.getItem("search_program_filter") != null){
			var filterJson = JSON.parse($.sessionStorage.getItem('search_program_filter'));
			$("#subclass").val(filterJson._productname);
			$("#age_min").val(filterJson._age_min);
			$("#age_max").val(filterJson._age_max);
			$("#datepicker").val(filterJson._datestart);
			$("#datepickerend").val(filterJson._dateend);
		}
		
	});
</script>
