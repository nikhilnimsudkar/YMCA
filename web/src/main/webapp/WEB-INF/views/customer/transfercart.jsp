<%@ include file="../../layouts/include_taglib.jsp"%>

<div id="payment_cart">
	<div class="pay_header">
		<span class="autopaySpan" >
			<button id="addCardBankDetail" class="k-button">Add Card/Bank Info</button>
		</span>
		<br>
		<div id="purchase_info" >
			<table width="100%">
				<c:forEach var="paymentInfo" items="${paymentInfoLst}" varStatus="count">
					<c:if test="${ paymentInfo.portalStatus == 'Active' && paymentInfo.paymentMethodType == 'Credit' }">
						<tr>
							<c:if test="${ paymentInfo.isPrimary == '1' }">
								<td><input type="radio" name="paymentInfoRadio" checked="checked" class="bootstrapRadio"></td>
							</c:if>
							<c:if test="${ paymentInfo.isPrimary == '0' || paymentInfo.isPrimary == null}">
								<td><input type="radio" name="paymentInfoRadio" class="bootstrapRadio"></td>
							</c:if>	
							<td><span>Credit & Debit Cards</span></td>
							<!-- <td><span>XXXX-XXXX-XXXX-1234</span></td> -->
							<td><span>${paymentInfo.cardNumber }</span><span class="pmMakePrimaryPaymentId" style="display:none;">${paymentInfo.paymentId }</span></td>
							<td></td>
							<td>Expiration Date</td>
							<!-- <td>07/20</td> -->
							<td>${paymentInfo.expirationMonth }/${paymentInfo.expirationYear }</td>
						</tr>
					</c:if>
				</c:forEach>
				<c:forEach var="paymentInfo" items="${paymentInfoLst}" varStatus="count">
					<c:if test="${ paymentInfo.portalStatus == 'Active' && paymentInfo.paymentMethodType == 'EFT' }">
						<tr>
							<c:if test="${ paymentInfo.isPrimary == '1' }">
								<td><input type="radio" name="paymentInfoRadio" checked="checked" class="bootstrapRadio"></td>
							</c:if>
							<c:if test="${ paymentInfo.isPrimary == '0' || paymentInfo.isPrimary == null }">
								<td><input type="radio" name="paymentInfoRadio" class="bootstrapRadio"></td>
							</c:if>	
													
							<td><span>Bank Details</span></td>
							<!-- <td><span>XXXXX-XXXXX</span></td> -->
							<td><span>${paymentInfo.bankRoutingNumber }</span><span class="pmMakePrimaryPaymentId" style="display:none;">${paymentInfo.paymentId }</span></td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
					</c:if>
				</c:forEach>			
			</table>
			<div id="movefromcart" style="width:100%; position:absolute; top:535px; left:-195px;" align="right">
				<span id="gotoUpdate" class="k-button"><< Previous</span>
				<span id="gototandc" class="k-button">Next >></span>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</div>
			<div id="movefromcart1" style="width:100%; position:absolute; top:535px; left:-195px; display:none;" align="right">
				<span id="gotochangemembership" class="k-button"><< Previous</span>
				<span id="gotochangetandc" class="k-button">Next >></span>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</div>
		</div>
	</div>
</div>

<div id="addBankCardInfoDiv" class="k-window1" style="display: none;">
	
</div>	
<style>
	div.bootstrapBorder{
		margin-left:0px;
	}
</style>

<script>
$(document).ready(function (){  
	$('#addCardBankDetail').on( "click", function() {        	
	   
	    var window_add = $("#addBankCardInfoDiv");
		window_add.clone().kendoWindow({
			title : "Add card",
			content : "addcard",
			modal : false,
			width : "700px",
			close : onClose,
			deactivate : function(e) {
				this.destroy();
			}
		}).data("kendoWindow").center().open();
		$('[data-role="draggable"]').css('top', '20px');
    });
	
	var onClose = function() {
		//this.destroy();   
	};
});
</script>