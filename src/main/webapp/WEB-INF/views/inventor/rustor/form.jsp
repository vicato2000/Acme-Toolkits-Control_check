<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form readonly="${readonly}">
	<jstl:if test="${acme:anyOf(command, 'show, update, delete')}">
		<acme:input-textbox readonly="true" code="inventor.rustor.form.label.code" path="code"/>
	</jstl:if>
	<jstl:if test="${command == 'create'}">
		<acme:input-textbox placeholder="09-120122" code="inventor.rustor.form.label.code" path="code"/>	
	</jstl:if>
	<acme:input-textbox code="inventor.rustor.form.label.theme" path="theme"/>
	<acme:input-textarea code="inventor.rustor.form.label.statement" path="statement"/>
	<acme:input-moment readonly="true" code="inventor.rustor.form.label.creationMoment" path="creationMoment"/>	
	<acme:input-moment code="inventor.rustor.form.label.startDate" path="startDate"/>
	<acme:input-moment code="inventor.rustor.form.label.finishDate" path="finishDate"/>
	
	<jstl:choose>
		<jstl:when test="${showDefaultCurrency}">	
			<acme:input-money code="inventor.rustor.form.label.share" path="defaultCurrency"/>
			<acme:input-money code="inventor.rustor.form.label.share" path="share" readonly="true"/>
		</jstl:when>
		<jstl:otherwise>
			<acme:input-money code="inventor.rustor.form.label.share" path="share"/>
		</jstl:otherwise>
	</jstl:choose>

	<acme:input-textbox code="inventor.rustor.form.label.moreInfo" path="moreInfo"/>
	<acme:input-textbox readonly="true" code="inventor.rustor.form.label.itemName" path="itemName"/>

	<jstl:choose>
		<jstl:when test="${acme:anyOf(command, 'show, update, delete')}">
			<acme:submit code="inventor.rustor.form.button.update" action="/inventor/rustor/update"/>
			<acme:submit code="inventor.rustor.form.button.delete" action="/inventor/rustor/delete"/>
		</jstl:when>
		<jstl:when test="${command == 'create'}">
			<acme:submit code="inventor.rustor.form.button.create" action="/inventor/rustor/create?itemId=${itemId}"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>