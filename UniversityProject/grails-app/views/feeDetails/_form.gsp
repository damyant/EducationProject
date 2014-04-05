<%@ page import="examinationproject.FeeDetails" %>



<div class="fieldcontain ${hasErrors(bean: feeDetailsInstance, field: 'feeType', 'error')} required">
	<label for="feeType">
		<g:message code="feeDetails.feeType.label" default="Fee Type" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="feeType" name="feeType.id" from="${examinationproject.FeeType.list()}" optionKey="id" required="" value="${feeDetailsInstance?.feeType?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: feeDetailsInstance, field: 'paymentMode', 'error')} ">
	<label for="paymentMode">
		<g:message code="feeDetails.paymentMode.label" default="Payment Mode" />
		
	</label>
	<g:textField name="paymentMode" value="${feeDetailsInstance?.paymentMode}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: feeDetailsInstance, field: 'paymentDate', 'error')} required">
	<label for="paymentDate">
		<g:message code="feeDetails.paymentDate.label" default="Payment Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="paymentDate" precision="day"  value="${feeDetailsInstance?.paymentDate}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: feeDetailsInstance, field: 'draftNumber', 'error')} ">
	<label for="draftNumber">
		<g:message code="feeDetails.draftNumber.label" default="Draft Number" />
		
	</label>
	<g:textField name="draftNumber" value="${feeDetailsInstance?.draftNumber}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: feeDetailsInstance, field: 'draftDate', 'error')} required">
	<label for="draftDate">
		<g:message code="feeDetails.draftDate.label" default="Draft Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="draftDate" precision="day"  value="${feeDetailsInstance?.draftDate}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: feeDetailsInstance, field: 'issuingBank', 'error')} ">
	<label for="issuingBank">
		<g:message code="feeDetails.issuingBank.label" default="Issuing Bank" />
		
	</label>
	<g:textField name="issuingBank" value="${feeDetailsInstance?.issuingBank}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: feeDetailsInstance, field: 'issuingBranch', 'error')} ">
	<label for="issuingBranch">
		<g:message code="feeDetails.issuingBranch.label" default="Issuing Branch" />
		
	</label>
	<g:textField name="issuingBranch" value="${feeDetailsInstance?.issuingBranch}"/>
</div>

