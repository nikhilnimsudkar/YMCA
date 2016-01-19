<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%
    	String contextPath = request.getContextPath();
    %>
    <script src="<%=contextPath %>/resources/js/app/common_new.js"></script>
<body>

<div id="main">
	
	<div id="content">
		<div class="k-loading-mask" style="width:100%;height:100%; position:absolute; top:114px; left:0px;display:none; z-index:9999">
			<span class="k-loading-text">Loading... Please wait</span>
			<div class="k-loading-image">
				<div class="k-loading-color"></div>
			</div>
		</div>
				
		<div id="searchprogram" class="k-block" style="width:22%">
			<%@ include file="searchallprogram.jsp" %> 
		</div>
		
		<div id="programs">
			<div id="tabstrip3" class="k-block" style=" background-color:#FFFFFF;margin-left:-52px;width:885px;min-height:0px; padding: 5px 5px 25px;">
				<div id="currentConfirmedProgram" class="viewall">
					<div class="sectionHead">
						<span class="hideSection">&nbsp;&nbsp;&nbsp;&nbsp;</span>
						<span class="head" style="margin-left:4px;">
							Current Confirmed Programs 
						</span>
					</div>
					<div id="currentConfirmedProgramDiv" class="expand">
						<%@ include file="currentConfirmedProgram.jsp" %> 
					</div>
				</div>
				
				<div id="currentWaitlistedProgram" class="viewall">
					<div class="sectionHead">
						<span class="showSection">&nbsp;&nbsp;&nbsp;&nbsp;</span>
						<span class="head" style="margin-left:4px;">
							Current Wait listed Programs 
						</span>
					</div>
					<div id="currentWaitlistedProgramDiv" class="collapse">
						<%@ include file="currentWaitlistedProgram.jsp" %> 
					</div>
				</div>
				
				<div id="upcomingConfirmedProgram" class="viewall">
					<div class="sectionHead">
						<span class="showSection">&nbsp;&nbsp;&nbsp;&nbsp;</span>
						<span class="head" style="margin-left:4px;">
							Upcoming Confirmed Programs 
						</span>
					</div>
					<div id="upcomingConfirmedProgramDiv" class="collapse">
						<%@ include file="upcomingConfirmedProgram.jsp" %>
					</div> 
				</div>
				
				<div id="upcomingWaitlistedProgram" class="viewall">
					<div class="sectionHead">
						<span class="showSection">&nbsp;&nbsp;&nbsp;&nbsp;</span>
						<span class="head" style="margin-left:4px;">
							Upcoming Wait listed Programs 
						</span>
					</div>
					
					<div id="upcomingWaitlistedProgramDiv" class="collapse">
						<%@ include file="upcomingWaitlistedProgram.jsp" %> 
					</div>
				</div>
				
				<div id="cancelledProgram" class="viewall"> 
					<div class="sectionHead">
						<span class="showSection">&nbsp;&nbsp;&nbsp;&nbsp;</span>
						<span class="head" style="margin-left:4px;">
							Cancelled Programs 
						</span>
					</div>
					
					<div id="cancelledProgramDiv" class="collapse">
						<%@ include file="cancelledProgram.jsp" %> 
					</div>
				</div>
				
				<div id="pastConfirmedProgram" class="viewall">
					<div class="sectionHead">
						<span class="showSection">&nbsp;&nbsp;&nbsp;&nbsp;</span>
						<span class="head" style="margin-left:4px;">
						Past Confirmed Programs 
					</span>
					</div>
					
					<div id="pastConfirmedProgramDiv" class="collapse">
						<%@ include file="pastConfirmedPrograms.jsp" %> 
					</div>
				</div>
				
				<div id="futureCancellationProgram" class="viewall"> 
					<div class="sectionHead">
						<span class="showSection">&nbsp;&nbsp;&nbsp;&nbsp;</span>
						<span class="head" style="margin-left:4px;">
							Future Cancellations Program
						</span>
					</div>
					
					<div id="futureCancellationProgramDiv" class="collapse">
						<%@ include file="futureCancellationProgram.jsp" %> 
					</div>
				</div>
			</div>
		
		</div>
		
		<div style="clear: both;"></div> 
		<div id="statusBlock">
			<span class="k-block k-success-colored" id="tcSuccessSpan"></span>
			<span class="k-block k-error-colored" id="tcErrorSpan"></span>
		</div>

	</div>
</div>

</body>

<style>
	table tr td {
	    padding-left: 5px;
	}
	
	.collapse{
		display:none;
	}
	
	.expand{
		display:block;
	}
	
	.sectionHead{
		background-color: rgb(135, 206, 235); border-radius: 6px; margin-bottom: 10px; margin-top: 10px; padding: 10px;
	}
	
	.showSection{
		background: url('resources/img/show_section.gif') transparent no-repeat;
	}
	
	.hideSection{
		background: url('resources/img/hide_section.gif') transparent no-repeat;
	}
	
	.viewall{
		cursor:pointer;
	}
</style>

<script>
$(document).ready(function(){ 
	$( ".viewall" ).click(function(){
		//alert(this.id);
		$(".sectionHead").find(".hideSection").attr("class","showSection");
		$('.viewall').each(function(){
			$( "#"+this.id+"Div" ).attr("class","collapse");
		});
		
		$( "#"+this.id+"Div" ).attr("class","expand");
		$( "#"+this.id).find("span.showSection").attr("class","hideSection");
	});
});
</script>