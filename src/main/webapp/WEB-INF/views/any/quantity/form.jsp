<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form readonly="${readonly}">
	<acme:input-textbox code="any.quantity.form.label.code" path="item.code"/>	
	<acme:input-textbox code="any.quantity.form.label.name" path="item.name"/>
	<acme:input-textbox code="any.quantity.form.label.technology" path="item.technology"/>
	<acme:input-money code="any.quantity.form.label.retailPrice" path="item.retailPrice"/>
	
	<jstl:if test="${showDefaultCurrency}">
		<acme:input-money code="any.quantity.form.label.retailPrice" path="defaultCurrency" readonly="true"/>
	</jstl:if>
	
	<acme:input-textbox code="any.quantity.form.label.description" path="item.description"/>
	<acme:input-textbox code="any.quantity.form.label.inventor" path="inventor.fullName"/>
	<acme:input-url code="any.quantity.form.label.link" path="item.link"/>
	<acme:input-textbox code="any.quantity.form.label.type" path="item.type"/>
	<acme:input-textbox code="any.quantity.form.label.quantity" path="amount"/>
	
</acme:form>
