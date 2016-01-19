<%@ include file="../../layouts/include_taglib.jsp" %> 

<div>
	<div><h1>Membership Cancellation</h1></div>
	
	<p><strong>Please tell us if your financial situation has changed so we can discuss financial assistance.</strong></p>
	
	<div>
		<span>We're sorry you are leaving the YMCA and would appreciate learning why.</span><br><br>
		<div id="cancellationreason">
			<div>
				<span><input type="checkbox" name="reason1" id="reason1" value="NOT USING FACILITY"></span>
				<span><strong>NOT USING FACILITY :</strong> I do not have time or am no longer interested in going to a health club/fitness facility.</span>
			</div>
			<div>
				<span><input type="checkbox" name="reason2" id="reason2" value="COST"></span>
				<span><strong>COST :</strong> I can no longer afford to continue my membership. Please discuss the possibility of financial assistance with us.</span>
			</div>
			<div>
				<span><input type="checkbox" name="reason3" id="reason3" value="DISSATISFIED WITH THE Y"></span>
				<span><strong>DISSATISFIED WITH THE Y:</strong> Please tell us why:</span><br>
				<span><textarea name="whydissatisfied" id="whydissatisfied"></textarea></span><br>
				<span style="margin-left:48px;">How can we improve?</span><br>
				<span><textarea name="howtoimprove" id="howtoimprove"></textarea></span>
			</div>
			<div>
				<span><input type="checkbox" name="reason4" id="reason4" value="JOINED A NON-YMCA HEALTH CLUB"></span>
				<span><strong>JOINED A NON-YMCA HEALTH CLUB:</strong> I found another health club/fitness facility that is better for me.</span>
				<span>If so, which health club/fitness facility did you join?</span>
				<span><textarea name="otherjoined" id="otherjoined" ></textarea></span>
			</div>
			<div>
				<span><input type="checkbox" name="reason5" id="reason5" value="RELOCATION"></span>
				<span><strong>RELOCATION :</strong> I am moving out of the area or my workplace location is changing.</span>
			</div>
			<div>
				<span><input type="checkbox" name="reason6" id="reason6" value="OTHER REASON"></span>
				<span><strong>OTHER REASON :</strong> Please choose or write the reason below.</span>
				<div>
					<span><input type="radio" name="otherreason" id="otherreason" value="Transfer to another YMCA"></span>
					<span>Transfer to another YMCA</span>
					<span><input type="radio" name="otherreason" id="otherreason" value="Back to school"></span>
					<span>Back to school</span>
					<span><input type="radio" name="otherreason" id="otherreason" value="Medical"></span>
					<span>Medical</span>
					<span><input type="radio" name="otherreason" id="otherreason" value="Schedule Conflict"></span>
					<span>Schedule Conflict</span><br>
					<span><input type="radio" name="otherreason" id="otherreason" value="Other"></span>
					<span>Other</span><br>
					<span><textarea name="otherreason1" id="otherreason1"></textarea></span>
				</div>
			</div>
		</div>
	</div>
</div>