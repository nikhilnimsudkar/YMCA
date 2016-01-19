<%@ include file="../../layouts/include_taglib.jsp"%>



<%-- <div id="tabstrip4" class="k-block"	style="background-color: #FFFFFF; margin-left: -572px; width: 768px;">
	<table cellpadding="0" cellspacing="0" width="95%" rules="none"	style="padding-left: 4px; padding-top: 4px;">
		<span class="head" width="100%" style="margin-left: 4px;">MY RECENT DONATIONS</span>
		<c:forEach var="donation" items="${donationList}" varStatus="status">
			<tr style="outline: thin solid; padding-left: 4px;">
				<td style="color: red; padding-left: 4px; padding: 3px;">
					<fmt:formatDate	type="date" pattern="MM/dd/yyyy" value="${donation.donationDate}" /></td>
				<td style="color: red;">$${donation.donationAmount}0</td>
			</tr>
			<tr>
				<td>&nbsp; <!--you just need a space in a row-->
				</td>
			</tr>
		</c:forEach>		
	</table>
	<span class="k-button" id="opptyDonateNow" style="margin-left:5px; margin-top: 8px;">Donate Now!</span><br>
</div> --%>

<c:if test="${fn:length(donationOpptyLst)>0}">		
		<table cellpadding="0" cellspacing="0"  id="fullDonationTable" style="padding-left:4px;padding-top:4px; border : thin 1px solid grey; margin-top : 8px;">
			<colgroup>                
                 <col style="width:10px">
                 <col  />
                 <col  />
                 <col  />
            </colgroup>
            <thead>
			<tr>	
				<th style="5px;">&nbsp;</th>
				<th>Campaign Drive</th>	
				<th>Amount</th>	
				<th>Date</th>				
		 	</tr>	
		 	</thead>		
		 	 <tbody>		
			<c:forEach var="opportunity" items="${donationOpptyLst}" varStatus="statusOpp">
				<c:forEach var="opptyRevenue" items="${opportunity.opptyRevenue}" varStatus="status">				
					<c:if test="${opptyRevenue != null && opptyRevenue.itemDetail != null}">
						<fmt:formatDate type="date" pattern="MM/dd/yyyy" value="${opportunity.effectiveDate }" var="formattedDate"/>																			
						<tr >							
							<td>&nbsp;</td>											
		 					<td>
		 						<a href="annualDonationDonate?opptyId=${opportunity.optyId }">${opptyRevenue.itemDetail.recordName}</a>
		 					</td>
		 					<td>${opportunity.revenue}</td>		
		 					<td>${formattedDate }</td>				 							 					 					
		 				</tr>
					</c:if>	
								
				</c:forEach>		
 			</c:forEach>
 			 </tbody>
 		</table><br />
		<!-- <span class="k-button" id="opptyDonateNow" style="margin-left:5px; margin-top: 8px;">Donate Now!</span><br> -->

</c:if>

<script type="text/javascript">
 $(document).ready(function(){	
	 $("#fullDonationTable").kendoGrid ( {	
	        filterable: false,	       
	        sortable: true,
	        pageable: true,
	        dataSource: {
	            pageSize: 10
	        },
	        resizable: true
	 });
 });
</script>
<style>
.k-grid {
    width: 50%;    
  float: left;
  margin-top: -396px;
  margin-left: 420px;
}
</style>


