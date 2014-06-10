<%@ page import="examinationproject.Bank" %>



<div class="fieldcontain ${hasErrors(bean: bankInstance, field: 'bankName', 'error')} ">
	<label for="bankName">
		<g:message code="bank.bankName.label" default="Bank Name" />
		
	</label>
	<g:textField name="bankName" value="${bankInstance?.bankName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bankInstance, field: 'branch', 'error')} ">
	<label for="branch">
		<g:message code="bank.branch.label" default="Branch" />
		
	</label>
	<g:select name="branch" from="${examinationproject.Branch.list()}" multiple="multiple" optionKey="id" size="5" value="${bankInstance?.branch*.id}" class="many-to-many"/>
</div>

